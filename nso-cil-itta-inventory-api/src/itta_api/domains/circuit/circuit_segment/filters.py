"""Search filter for Circuit Segment and its translation to upstream query parameters."""
from __future__ import annotations

from datetime import date, datetime
from decimal import Decimal
from uuid import UUID

from pydantic import Field

from itta_core.models import ApiModel, LifecycleStatus
from itta_api.domains.circuit.circuit_segment.schemas import *  # noqa: F401,F403


class CircuitSegmentFilter(ApiModel):
    """Query-string filter for Circuit Segment search."""

    q: str | None = Field(None, description="Free text over code, name and description")
    status: list[LifecycleStatus] | None = None
    code: str | None = Field(None, description="Code contains")
    name: str | None = Field(None, description="Name contains")
    external_ref: str | None = Field(None, description="External Ref contains")
    circuit_id: UUID | None = None
    segment_order_min: int | None = None
    segment_order_max: int | None = None
    a_termination_id: UUID | None = None
    z_termination_id: UUID | None = None
    latency_ms_min: Decimal | None = None
    latency_ms_max: Decimal | None = None
    source_system: str | None = Field(None, description="Source System contains")
    region: str | None = Field(None, description="Region contains")
    owner: str | None = Field(None, description="Owner contains")
    criticality: list[CircuitSegmentCriticality] | None = None
    cost_center: str | None = Field(None, description="Cost Center contains")


def to_query(flt: CircuitSegmentFilter) -> dict[str, str]:
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
    if flt.circuit_id is not None:
        params["circuitId"] = str(flt.circuit_id)
    if flt.segment_order_min is not None:
        params["segmentOrderMin"] = str(flt.segment_order_min)
    if flt.segment_order_max is not None:
        params["segmentOrderMax"] = str(flt.segment_order_max)
    if flt.a_termination_id is not None:
        params["aTerminationId"] = str(flt.a_termination_id)
    if flt.z_termination_id is not None:
        params["zTerminationId"] = str(flt.z_termination_id)
    if flt.latency_ms_min is not None:
        params["latencyMsMin"] = str(flt.latency_ms_min)
    if flt.latency_ms_max is not None:
        params["latencyMsMax"] = str(flt.latency_ms_max)
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
