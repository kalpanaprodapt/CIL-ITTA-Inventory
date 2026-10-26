"""Search filter for Service Catalog Item and its translation to upstream query parameters."""
from __future__ import annotations

from datetime import date, datetime
from decimal import Decimal
from uuid import UUID

from pydantic import Field

from itta_core.models import ApiModel, LifecycleStatus
from itta_api.domains.service.service_catalog_item.schemas import *  # noqa: F401,F403


class ServiceCatalogItemFilter(ApiModel):
    """Query-string filter for Service Catalog Item search."""

    q: str | None = Field(None, description="Free text over code, name and description")
    status: list[LifecycleStatus] | None = None
    code: str | None = Field(None, description="Code contains")
    name: str | None = Field(None, description="Name contains")
    external_ref: str | None = Field(None, description="External Ref contains")
    item_name: str | None = Field(None, description="Item Name contains")
    family: list[ServiceCatalogItemFamily] | None = None
    base_price_min: Decimal | None = None
    base_price_max: Decimal | None = None
    billing_unit: str | None = Field(None, description="Billing Unit contains")
    orderable: bool | None = None
    effective_to_from: date | None = None
    effective_to_to: date | None = None
    labels: str | None = Field(None, description="Labels contains")
    lifecycle_stage: list[ServiceCatalogItemLifecycleStage] | None = None
    source_system: str | None = Field(None, description="Source System contains")
    region: str | None = Field(None, description="Region contains")


def to_query(flt: ServiceCatalogItemFilter) -> dict[str, str]:
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
    if flt.item_name:
        params["itemName"] = flt.item_name.strip()
    if flt.family:
        params["family"] = ",".join(v.value for v in flt.family)
    if flt.base_price_min is not None:
        params["basePriceMin"] = str(flt.base_price_min)
    if flt.base_price_max is not None:
        params["basePriceMax"] = str(flt.base_price_max)
    if flt.billing_unit:
        params["billingUnit"] = flt.billing_unit.strip()
    if flt.orderable is not None:
        params["orderable"] = "true" if flt.orderable else "false"
    if flt.effective_to_from is not None:
        params["effectiveToFrom"] = flt.effective_to_from.isoformat()
    if flt.effective_to_to is not None:
        params["effectiveToTo"] = flt.effective_to_to.isoformat()
    if flt.labels:
        params["labels"] = flt.labels.strip()
    if flt.lifecycle_stage:
        params["lifecycleStage"] = ",".join(v.value for v in flt.lifecycle_stage)
    if flt.source_system:
        params["sourceSystem"] = flt.source_system.strip()
    if flt.region:
        params["region"] = flt.region.strip()
    return params
