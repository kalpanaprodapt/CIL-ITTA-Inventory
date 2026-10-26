"""Search filter for Purchase Order and its translation to upstream query parameters."""
from __future__ import annotations

from datetime import date, datetime
from decimal import Decimal
from uuid import UUID

from pydantic import Field

from itta_core.models import ApiModel, LifecycleStatus
from itta_api.domains.assetmgmt.purchase_order.schemas import *  # noqa: F401,F403


class PurchaseOrderFilter(ApiModel):
    """Query-string filter for Purchase Order search."""

    q: str | None = Field(None, description="Free text over code, name and description")
    status: list[LifecycleStatus] | None = None
    code: str | None = Field(None, description="Code contains")
    name: str | None = Field(None, description="Name contains")
    external_ref: str | None = Field(None, description="External Ref contains")
    po_number: str | None = Field(None, description="Po Number contains")
    vendor_id: UUID | None = None
    ordered_on_from: date | None = None
    ordered_on_to: date | None = None
    total_amount_min: Decimal | None = None
    total_amount_max: Decimal | None = None
    currency: str | None = Field(None, description="Currency contains")
    owner: str | None = Field(None, description="Owner contains")
    criticality: list[PurchaseOrderCriticality] | None = None
    cost_center: str | None = Field(None, description="Cost Center contains")
    effective_from_from: date | None = None
    effective_from_to: date | None = None


def to_query(flt: PurchaseOrderFilter) -> dict[str, str]:
    """Translate a filter into the query parameters understood by the inventory service."""
    params: dict[str, str] = {}
    if flt.q and flt.q.strip():
        params["q"] = flt.q.strip()
    if flt.status:
        params["status"] = ",".join(v.value for v in flt.status)
    if flt.code:
        params["code"] = flt.code.strip()
    if flt.name:
        params["name"] = flt.name.strip()
    if flt.external_ref:
        params["externalRef"] = flt.external_ref.strip()
    if flt.po_number:
        params["poNumber"] = flt.po_number.strip()
    if flt.vendor_id is not None:
        params["vendorId"] = str(flt.vendor_id)
    if flt.ordered_on_from is not None:
        params["orderedOnFrom"] = flt.ordered_on_from.isoformat()
    if flt.ordered_on_to is not None:
        params["orderedOnTo"] = flt.ordered_on_to.isoformat()
    if flt.total_amount_min is not None:
        params["totalAmountMin"] = str(flt.total_amount_min)
    if flt.total_amount_max is not None:
        params["totalAmountMax"] = str(flt.total_amount_max)
    if flt.currency:
        params["currency"] = flt.currency.strip()
    if flt.owner:
        params["owner"] = flt.owner.strip()
    if flt.criticality:
        params["criticality"] = ",".join(v.value for v in flt.criticality)
    if flt.cost_center:
        params["costCenter"] = flt.cost_center.strip()
    if flt.effective_from_from is not None:
        params["effectiveFromFrom"] = flt.effective_from_from.isoformat()
    if flt.effective_from_to is not None:
        params["effectiveFromTo"] = flt.effective_from_to.isoformat()
    return params
