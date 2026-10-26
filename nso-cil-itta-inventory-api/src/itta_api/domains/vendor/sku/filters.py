"""Search filter for Sku and its translation to upstream query parameters."""
from __future__ import annotations

from datetime import date, datetime
from decimal import Decimal
from uuid import UUID

from pydantic import Field

from itta_core.models import ApiModel, LifecycleStatus
from itta_api.domains.vendor.sku.schemas import *  # noqa: F401,F403


class SkuFilter(ApiModel):
    """Query-string filter for Sku search."""

    q: str | None = Field(None, description="Free text over code, name and description")
    status: list[LifecycleStatus] | None = None
    code: str | None = Field(None, description="Code contains")
    name: str | None = Field(None, description="Name contains")
    external_ref: str | None = Field(None, description="External Ref contains")
    model_id: UUID | None = None
    sku_code: str | None = Field(None, description="Sku Code contains")
    list_price_min: Decimal | None = None
    list_price_max: Decimal | None = None
    currency: str | None = Field(None, description="Currency contains")
    lead_time_days_min: int | None = None
    lead_time_days_max: int | None = None
    source_system: str | None = Field(None, description="Source System contains")
    region: str | None = Field(None, description="Region contains")
    owner: str | None = Field(None, description="Owner contains")
    criticality: list[SkuCriticality] | None = None
    cost_center: str | None = Field(None, description="Cost Center contains")


def to_query(flt: SkuFilter) -> dict[str, str]:
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
    if flt.model_id is not None:
        params["modelId"] = str(flt.model_id)
    if flt.sku_code:
        params["skuCode"] = flt.sku_code.strip()
    if flt.list_price_min is not None:
        params["listPriceMin"] = str(flt.list_price_min)
    if flt.list_price_max is not None:
        params["listPriceMax"] = str(flt.list_price_max)
    if flt.currency:
        params["currency"] = flt.currency.strip()
    if flt.lead_time_days_min is not None:
        params["leadTimeDaysMin"] = str(flt.lead_time_days_min)
    if flt.lead_time_days_max is not None:
        params["leadTimeDaysMax"] = str(flt.lead_time_days_max)
    if flt.source_system:
        params["sourceSystem"] = flt.source_system.strip()
    if flt.region:
        params["region"] = flt.region.strip()
    if flt.owner:
        params["owner"] = flt.owner.strip()
    if flt.criticality:
        params["criticality"] = ",".join(v.value for v in flt.criticality)
    if flt.cost_center:
        params["costCenter"] = flt.cost_center.strip()
    return params
