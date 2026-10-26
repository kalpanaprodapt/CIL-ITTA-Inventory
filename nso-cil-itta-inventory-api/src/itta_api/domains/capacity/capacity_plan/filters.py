"""Search filter for Capacity Plan and its translation to upstream query parameters."""
from __future__ import annotations

from datetime import date, datetime
from decimal import Decimal
from uuid import UUID

from pydantic import Field

from itta_core.models import ApiModel, LifecycleStatus
from itta_api.domains.capacity.capacity_plan.schemas import *  # noqa: F401,F403


class CapacityPlanFilter(ApiModel):
    """Query-string filter for Capacity Plan search."""

    q: str | None = Field(None, description="Free text over code, name and description")
    status: list[LifecycleStatus] | None = None
    code: str | None = Field(None, description="Code contains")
    name: str | None = Field(None, description="Name contains")
    external_ref: str | None = Field(None, description="External Ref contains")
    plan_name: str | None = Field(None, description="Plan Name contains")
    horizon_months_min: int | None = None
    horizon_months_max: int | None = None
    target_utilization_pct_min: Decimal | None = None
    target_utilization_pct_max: Decimal | None = None
    owner: str | None = Field(None, description="Owner contains")
    approved_on_from: date | None = None
    approved_on_to: date | None = None
    effective_to_from: date | None = None
    effective_to_to: date | None = None
    labels: str | None = Field(None, description="Labels contains")
    lifecycle_stage: list[CapacityPlanLifecycleStage] | None = None
    source_system: str | None = Field(None, description="Source System contains")
    region: str | None = Field(None, description="Region contains")


def to_query(flt: CapacityPlanFilter) -> dict[str, str]:
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
    if flt.plan_name:
        params["planName"] = flt.plan_name.strip()
    if flt.horizon_months_min is not None:
        params["horizonMonthsMin"] = str(flt.horizon_months_min)
    if flt.horizon_months_max is not None:
        params["horizonMonthsMax"] = str(flt.horizon_months_max)
    if flt.target_utilization_pct_min is not None:
        params["targetUtilizationPctMin"] = str(flt.target_utilization_pct_min)
    if flt.target_utilization_pct_max is not None:
        params["targetUtilizationPctMax"] = str(flt.target_utilization_pct_max)
    if flt.owner:
        params["owner"] = flt.owner.strip()
    if flt.approved_on_from is not None:
        params["approvedOnFrom"] = flt.approved_on_from.isoformat()
    if flt.approved_on_to is not None:
        params["approvedOnTo"] = flt.approved_on_to.isoformat()
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
