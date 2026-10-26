"""Search filter for Threshold Policy and its translation to upstream query parameters."""
from __future__ import annotations

from datetime import date, datetime
from decimal import Decimal
from uuid import UUID

from pydantic import Field

from itta_core.models import ApiModel, LifecycleStatus
from itta_api.domains.capacity.threshold_policy.schemas import *  # noqa: F401,F403


class ThresholdPolicyFilter(ApiModel):
    """Query-string filter for Threshold Policy search."""

    q: str | None = Field(None, description="Free text over code, name and description")
    status: list[LifecycleStatus] | None = None
    code: str | None = Field(None, description="Code contains")
    name: str | None = Field(None, description="Name contains")
    external_ref: str | None = Field(None, description="External Ref contains")
    policy_name: str | None = Field(None, description="Policy Name contains")
    metric: str | None = Field(None, description="Metric contains")
    warn_pct_min: Decimal | None = None
    warn_pct_max: Decimal | None = None
    crit_pct_min: Decimal | None = None
    crit_pct_max: Decimal | None = None
    evaluation_window_minutes_min: int | None = None
    evaluation_window_minutes_max: int | None = None
    region: str | None = Field(None, description="Region contains")
    owner: str | None = Field(None, description="Owner contains")
    criticality: list[ThresholdPolicyCriticality] | None = None
    cost_center: str | None = Field(None, description="Cost Center contains")


def to_query(flt: ThresholdPolicyFilter) -> dict[str, str]:
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
    if flt.policy_name:
        params["policyName"] = flt.policy_name.strip()
    if flt.metric:
        params["metric"] = flt.metric.strip()
    if flt.warn_pct_min is not None:
        params["warnPctMin"] = str(flt.warn_pct_min)
    if flt.warn_pct_max is not None:
        params["warnPctMax"] = str(flt.warn_pct_max)
    if flt.crit_pct_min is not None:
        params["critPctMin"] = str(flt.crit_pct_min)
    if flt.crit_pct_max is not None:
        params["critPctMax"] = str(flt.crit_pct_max)
    if flt.evaluation_window_minutes_min is not None:
        params["evaluationWindowMinutesMin"] = str(flt.evaluation_window_minutes_min)
    if flt.evaluation_window_minutes_max is not None:
        params["evaluationWindowMinutesMax"] = str(flt.evaluation_window_minutes_max)
    if flt.region:
        params["region"] = flt.region.strip()
    if flt.owner:
        params["owner"] = flt.owner.strip()
    if flt.criticality:
        params["criticality"] = ",".join(v.value for v in flt.criticality)
    if flt.cost_center:
        params["costCenter"] = flt.cost_center.strip()
    return params
