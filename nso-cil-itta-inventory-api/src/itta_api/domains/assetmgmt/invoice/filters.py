"""Search filter for Invoice and its translation to upstream query parameters."""
from __future__ import annotations

from datetime import date, datetime
from decimal import Decimal
from uuid import UUID

from pydantic import Field

from itta_core.models import ApiModel, LifecycleStatus
from itta_api.domains.assetmgmt.invoice.schemas import *  # noqa: F401,F403


class InvoiceFilter(ApiModel):
    """Query-string filter for Invoice search."""

    q: str | None = Field(None, description="Free text over code, name and description")
    status: list[LifecycleStatus] | None = None
    code: str | None = Field(None, description="Code contains")
    name: str | None = Field(None, description="Name contains")
    external_ref: str | None = Field(None, description="External Ref contains")
    invoice_number: str | None = Field(None, description="Invoice Number contains")
    po_id: UUID | None = None
    amount_min: Decimal | None = None
    amount_max: Decimal | None = None
    due_date_from: date | None = None
    due_date_to: date | None = None
    paid: bool | None = None
    cost_center: str | None = Field(None, description="Cost Center contains")
    effective_from_from: date | None = None
    effective_from_to: date | None = None
    effective_to_from: date | None = None
    effective_to_to: date | None = None
    labels: str | None = Field(None, description="Labels contains")


def to_query(flt: InvoiceFilter) -> dict[str, str]:
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
    if flt.invoice_number:
        params["invoiceNumber"] = flt.invoice_number.strip()
    if flt.po_id is not None:
        params["poId"] = str(flt.po_id)
    if flt.amount_min is not None:
        params["amountMin"] = str(flt.amount_min)
    if flt.amount_max is not None:
        params["amountMax"] = str(flt.amount_max)
    if flt.due_date_from is not None:
        params["dueDateFrom"] = flt.due_date_from.isoformat()
    if flt.due_date_to is not None:
        params["dueDateTo"] = flt.due_date_to.isoformat()
    if flt.paid is not None:
        params["paid"] = "true" if flt.paid else "false"
    if flt.cost_center:
        params["costCenter"] = flt.cost_center.strip()
    if flt.effective_from_from is not None:
        params["effectiveFromFrom"] = flt.effective_from_from.isoformat()
    if flt.effective_from_to is not None:
        params["effectiveFromTo"] = flt.effective_from_to.isoformat()
    if flt.effective_to_from is not None:
        params["effectiveToFrom"] = flt.effective_to_from.isoformat()
    if flt.effective_to_to is not None:
        params["effectiveToTo"] = flt.effective_to_to.isoformat()
    if flt.labels:
        params["labels"] = flt.labels.strip()
    return params
