"""Search filter for Asset and its translation to upstream query parameters."""
from __future__ import annotations

from datetime import date, datetime
from decimal import Decimal
from uuid import UUID

from pydantic import Field

from itta_core.models import ApiModel, LifecycleStatus
from itta_api.domains.assetmgmt.asset.schemas import *  # noqa: F401,F403


class AssetFilter(ApiModel):
    """Query-string filter for Asset search."""

    q: str | None = Field(None, description="Free text over code, name and description")
    status: list[LifecycleStatus] | None = None
    code: str | None = Field(None, description="Code contains")
    name: str | None = Field(None, description="Name contains")
    external_ref: str | None = Field(None, description="External Ref contains")
    asset_tag: str | None = Field(None, description="Asset Tag contains")
    asset_class: str | None = Field(None, description="Asset Class contains")
    acquired_on_from: date | None = None
    acquired_on_to: date | None = None
    purchase_price_min: Decimal | None = None
    purchase_price_max: Decimal | None = None
    current_value_min: Decimal | None = None
    current_value_max: Decimal | None = None
    custodian: str | None = Field(None, description="Custodian contains")
    labels: str | None = Field(None, description="Labels contains")
    lifecycle_stage: list[AssetLifecycleStage] | None = None
    source_system: str | None = Field(None, description="Source System contains")
    region: str | None = Field(None, description="Region contains")
    owner: str | None = Field(None, description="Owner contains")


def to_query(flt: AssetFilter) -> dict[str, str]:
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
    if flt.asset_tag:
        params["assetTag"] = flt.asset_tag.strip()
    if flt.asset_class:
        params["assetClass"] = flt.asset_class.strip()
    if flt.acquired_on_from is not None:
        params["acquiredOnFrom"] = flt.acquired_on_from.isoformat()
    if flt.acquired_on_to is not None:
        params["acquiredOnTo"] = flt.acquired_on_to.isoformat()
    if flt.purchase_price_min is not None:
        params["purchasePriceMin"] = str(flt.purchase_price_min)
    if flt.purchase_price_max is not None:
        params["purchasePriceMax"] = str(flt.purchase_price_max)
    if flt.current_value_min is not None:
        params["currentValueMin"] = str(flt.current_value_min)
    if flt.current_value_max is not None:
        params["currentValueMax"] = str(flt.current_value_max)
    if flt.custodian:
        params["custodian"] = flt.custodian.strip()
    if flt.labels:
        params["labels"] = flt.labels.strip()
    if flt.lifecycle_stage:
        params["lifecycleStage"] = ",".join(v.value for v in flt.lifecycle_stage)
    if flt.source_system:
        params["sourceSystem"] = flt.source_system.strip()
    if flt.region:
        params["region"] = flt.region.strip()
    if flt.owner:
        params["owner"] = flt.owner.strip()
    return params
