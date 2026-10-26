"""Search filter for Backhaul Link and its translation to upstream query parameters."""
from __future__ import annotations

from datetime import date, datetime
from decimal import Decimal
from uuid import UUID

from pydantic import Field

from itta_core.models import ApiModel, LifecycleStatus
from itta_api.domains.radio.backhaul_link.schemas import *  # noqa: F401,F403


class BackhaulLinkFilter(ApiModel):
    """Query-string filter for Backhaul Link search."""

    q: str | None = Field(None, description="Free text over code, name and description")
    status: list[LifecycleStatus] | None = None
    code: str | None = Field(None, description="Code contains")
    name: str | None = Field(None, description="Name contains")
    external_ref: str | None = Field(None, description="External Ref contains")
    base_station_id: UUID | None = None
    link_medium: list[BackhaulLinkLinkMedium] | None = None
    capacity_mbps_min: int | None = None
    capacity_mbps_max: int | None = None
    latency_ms_min: Decimal | None = None
    latency_ms_max: Decimal | None = None
    circuit_ref: str | None = Field(None, description="Circuit Ref contains")
    cost_center: str | None = Field(None, description="Cost Center contains")
    effective_from_from: date | None = None
    effective_from_to: date | None = None
    effective_to_from: date | None = None
    effective_to_to: date | None = None
    labels: str | None = Field(None, description="Labels contains")


def to_query(flt: BackhaulLinkFilter) -> dict[str, str]:
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
    if flt.base_station_id is not None:
        params["baseStationId"] = str(flt.base_station_id)
    if flt.link_medium:
        params["linkMedium"] = ",".join(v.value for v in flt.link_medium)
    if flt.capacity_mbps_min is not None:
        params["capacityMbpsMin"] = str(flt.capacity_mbps_min)
    if flt.capacity_mbps_max is not None:
        params["capacityMbpsMax"] = str(flt.capacity_mbps_max)
    if flt.latency_ms_min is not None:
        params["latencyMsMin"] = str(flt.latency_ms_min)
    if flt.latency_ms_max is not None:
        params["latencyMsMax"] = str(flt.latency_ms_max)
    if flt.circuit_ref:
        params["circuitRef"] = flt.circuit_ref.strip()
    if flt.cost_center:
        params["costCenter"] = flt.cost_center.strip()
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
    return params
