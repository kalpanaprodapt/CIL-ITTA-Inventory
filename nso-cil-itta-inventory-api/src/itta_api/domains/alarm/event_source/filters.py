"""Search filter for Event Source and its translation to upstream query parameters."""
from __future__ import annotations

from datetime import date, datetime
from decimal import Decimal
from uuid import UUID

from pydantic import Field

from itta_core.models import ApiModel, LifecycleStatus
from itta_api.domains.alarm.event_source.schemas import *  # noqa: F401,F403


class EventSourceFilter(ApiModel):
    """Query-string filter for Event Source search."""

    q: str | None = Field(None, description="Free text over code, name and description")
    status: list[LifecycleStatus] | None = None
    code: str | None = Field(None, description="Code contains")
    name: str | None = Field(None, description="Name contains")
    external_ref: str | None = Field(None, description="External Ref contains")
    source_name: str | None = Field(None, description="Source Name contains")
    protocol: list[EventSourceProtocol] | None = None
    endpoint: str | None = Field(None, description="Endpoint contains")
    heartbeat_seconds_min: int | None = None
    heartbeat_seconds_max: int | None = None
    labels: str | None = Field(None, description="Labels contains")
    lifecycle_stage: list[EventSourceLifecycleStage] | None = None
    source_system: str | None = Field(None, description="Source System contains")
    region: str | None = Field(None, description="Region contains")
    owner: str | None = Field(None, description="Owner contains")


def to_query(flt: EventSourceFilter) -> dict[str, str]:
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
    if flt.source_name:
        params["sourceName"] = flt.source_name.strip()
    if flt.protocol:
        params["protocol"] = ",".join(v.value for v in flt.protocol)
    if flt.endpoint:
        params["endpoint"] = flt.endpoint.strip()
    if flt.heartbeat_seconds_min is not None:
        params["heartbeatSecondsMin"] = str(flt.heartbeat_seconds_min)
    if flt.heartbeat_seconds_max is not None:
        params["heartbeatSecondsMax"] = str(flt.heartbeat_seconds_max)
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
