"""Search filter for Path Trace and its translation to upstream query parameters."""
from __future__ import annotations

from datetime import date, datetime
from decimal import Decimal
from uuid import UUID

from pydantic import Field

from itta_core.models import ApiModel, LifecycleStatus
from itta_api.domains.topology.path_trace.schemas import *  # noqa: F401,F403


class PathTraceFilter(ApiModel):
    """Query-string filter for Path Trace search."""

    q: str | None = Field(None, description="Free text over code, name and description")
    status: list[LifecycleStatus] | None = None
    code: str | None = Field(None, description="Code contains")
    name: str | None = Field(None, description="Name contains")
    external_ref: str | None = Field(None, description="External Ref contains")
    source_ref: str | None = Field(None, description="Source Ref contains")
    target_ref: str | None = Field(None, description="Target Ref contains")
    hop_count_min: int | None = None
    hop_count_max: int | None = None
    total_latency_ms_min: Decimal | None = None
    total_latency_ms_max: Decimal | None = None
    trace_method: str | None = Field(None, description="Trace Method contains")
    traced_at_from: datetime | None = None
    traced_at_to: datetime | None = None
    lifecycle_stage: list[PathTraceLifecycleStage] | None = None
    source_system: str | None = Field(None, description="Source System contains")
    region: str | None = Field(None, description="Region contains")
    owner: str | None = Field(None, description="Owner contains")
    criticality: list[PathTraceCriticality] | None = None


def to_query(flt: PathTraceFilter) -> dict[str, str]:
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
    if flt.source_ref:
        params["sourceRef"] = flt.source_ref.strip()
    if flt.target_ref:
        params["targetRef"] = flt.target_ref.strip()
    if flt.hop_count_min is not None:
        params["hopCountMin"] = str(flt.hop_count_min)
    if flt.hop_count_max is not None:
        params["hopCountMax"] = str(flt.hop_count_max)
    if flt.total_latency_ms_min is not None:
        params["totalLatencyMsMin"] = str(flt.total_latency_ms_min)
    if flt.total_latency_ms_max is not None:
        params["totalLatencyMsMax"] = str(flt.total_latency_ms_max)
    if flt.trace_method:
        params["traceMethod"] = flt.trace_method.strip()
    if flt.traced_at_from is not None:
        params["tracedAtFrom"] = flt.traced_at_from.isoformat()
    if flt.traced_at_to is not None:
        params["tracedAtTo"] = flt.traced_at_to.isoformat()
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
