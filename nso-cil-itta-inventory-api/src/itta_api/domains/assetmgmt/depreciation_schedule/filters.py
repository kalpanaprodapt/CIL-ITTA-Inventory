"""Search filter for Depreciation Schedule and its translation to upstream query parameters."""
from __future__ import annotations

from datetime import date, datetime
from decimal import Decimal
from uuid import UUID

from pydantic import Field

from itta_core.models import ApiModel, LifecycleStatus
from itta_api.domains.assetmgmt.depreciation_schedule.schemas import *  # noqa: F401,F403


class DepreciationScheduleFilter(ApiModel):
    """Query-string filter for Depreciation Schedule search."""

    q: str | None = Field(None, description="Free text over code, name and description")
    status: list[LifecycleStatus] | None = None
    code: str | None = Field(None, description="Code contains")
    name: str | None = Field(None, description="Name contains")
    external_ref: str | None = Field(None, description="External Ref contains")
    asset_id: UUID | None = None
    method: list[DepreciationScheduleMethod] | None = None
    useful_life_months_min: int | None = None
    useful_life_months_max: int | None = None
    salvage_value_min: Decimal | None = None
    salvage_value_max: Decimal | None = None
    start_date_from: date | None = None
    start_date_to: date | None = None
    effective_from_from: date | None = None
    effective_from_to: date | None = None
    effective_to_from: date | None = None
    effective_to_to: date | None = None
    labels: str | None = Field(None, description="Labels contains")
    lifecycle_stage: list[DepreciationScheduleLifecycleStage] | None = None
    source_system: str | None = Field(None, description="Source System contains")


def to_query(flt: DepreciationScheduleFilter) -> dict[str, str]:
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
    if flt.asset_id is not None:
        params["assetId"] = str(flt.asset_id)
    if flt.method:
        params["method"] = ",".join(v.value for v in flt.method)
    if flt.useful_life_months_min is not None:
        params["usefulLifeMonthsMin"] = str(flt.useful_life_months_min)
    if flt.useful_life_months_max is not None:
        params["usefulLifeMonthsMax"] = str(flt.useful_life_months_max)
    if flt.salvage_value_min is not None:
        params["salvageValueMin"] = str(flt.salvage_value_min)
    if flt.salvage_value_max is not None:
        params["salvageValueMax"] = str(flt.salvage_value_max)
    if flt.start_date_from is not None:
        params["startDateFrom"] = flt.start_date_from.isoformat()
    if flt.start_date_to is not None:
        params["startDateTo"] = flt.start_date_to.isoformat()
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
    if flt.lifecycle_stage:
        params["lifecycleStage"] = ",".join(v.value for v in flt.lifecycle_stage)
    if flt.source_system:
        params["sourceSystem"] = flt.source_system.strip()
    return params
