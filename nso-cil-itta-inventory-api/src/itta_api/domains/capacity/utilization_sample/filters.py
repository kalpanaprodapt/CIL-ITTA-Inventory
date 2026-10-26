"""Search filter for Utilization Sample and its translation to upstream query parameters."""
from __future__ import annotations

from datetime import date, datetime
from decimal import Decimal
from uuid import UUID

from pydantic import Field

from itta_core.models import ApiModel, LifecycleStatus
from itta_api.domains.capacity.utilization_sample.schemas import *  # noqa: F401,F403


class UtilizationSampleFilter(ApiModel):
    """Query-string filter for Utilization Sample search."""

    q: str | None = Field(None, description="Free text over code, name and description")
    status: list[LifecycleStatus] | None = None
    code: str | None = Field(None, description="Code contains")
    name: str | None = Field(None, description="Name contains")
    external_ref: str | None = Field(None, description="External Ref contains")
    target_ref: str | None = Field(None, description="Target Ref contains")
    metric: str | None = Field(None, description="Metric contains")
    sampled_at_from: datetime | None = None
    sampled_at_to: datetime | None = None
    value_pct_min: Decimal | None = None
    value_pct_max: Decimal | None = None
    peak: bool | None = None
    lifecycle_stage: list[UtilizationSampleLifecycleStage] | None = None
    source_system: str | None = Field(None, description="Source System contains")
    region: str | None = Field(None, description="Region contains")
    owner: str | None = Field(None, description="Owner contains")
    criticality: list[UtilizationSampleCriticality] | None = None


def to_query(flt: UtilizationSampleFilter) -> dict[str, str]:
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
    if flt.target_ref:
        params["targetRef"] = flt.target_ref.strip()
    if flt.metric:
        params["metric"] = flt.metric.strip()
    if flt.sampled_at_from is not None:
        params["sampledAtFrom"] = flt.sampled_at_from.isoformat()
    if flt.sampled_at_to is not None:
        params["sampledAtTo"] = flt.sampled_at_to.isoformat()
    if flt.value_pct_min is not None:
        params["valuePctMin"] = str(flt.value_pct_min)
    if flt.value_pct_max is not None:
        params["valuePctMax"] = str(flt.value_pct_max)
    if flt.peak is not None:
        params["peak"] = "true" if flt.peak else "false"
    if flt.lifecycle_stage:
        params["lifecycleStage"] = ",".join(v.value for v in flt.lifecycle_stage)
    if flt.source_system:
        params["sourceSystem"] = flt.source_system.strip()
    if flt.region:
        params["region"] = flt.region.strip()
    if flt.owner:
        params["owner"] = flt.owner.strip()
    if flt.criticality:
        params["criticality"] = ",".join(v.value for v in flt.criticality)
    return params
