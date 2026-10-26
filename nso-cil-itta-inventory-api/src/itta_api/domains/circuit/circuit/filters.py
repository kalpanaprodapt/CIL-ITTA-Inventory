"""Search filter for Circuit and its translation to upstream query parameters."""
from __future__ import annotations

from datetime import date, datetime
from decimal import Decimal
from uuid import UUID

from pydantic import Field

from itta_core.models import ApiModel, LifecycleStatus
from itta_api.domains.circuit.circuit.schemas import *  # noqa: F401,F403


class CircuitFilter(ApiModel):
    """Query-string filter for Circuit search."""

    q: str | None = Field(None, description="Free text over code, name and description")
    status: list[LifecycleStatus] | None = None
    code: str | None = Field(None, description="Code contains")
    name: str | None = Field(None, description="Name contains")
    external_ref: str | None = Field(None, description="External Ref contains")
    circuit_id: str | None = Field(None, description="Circuit Id contains")
    circuit_type: list[CircuitCircuitType] | None = None
    bandwidth_mbps_min: int | None = None
    bandwidth_mbps_max: int | None = None
    provider_id: UUID | None = None
    a_site_id: UUID | None = None
    z_site_id: UUID | None = None
    labels: str | None = Field(None, description="Labels contains")
    lifecycle_stage: list[CircuitLifecycleStage] | None = None
    source_system: str | None = Field(None, description="Source System contains")
    region: str | None = Field(None, description="Region contains")
    owner: str | None = Field(None, description="Owner contains")


def to_query(flt: CircuitFilter) -> dict[str, str]:
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
    if flt.circuit_id:
        params["circuitId"] = flt.circuit_id.strip()
    if flt.circuit_type:
        params["circuitType"] = ",".join(v.value for v in flt.circuit_type)
    if flt.bandwidth_mbps_min is not None:
        params["bandwidthMbpsMin"] = str(flt.bandwidth_mbps_min)
    if flt.bandwidth_mbps_max is not None:
        params["bandwidthMbpsMax"] = str(flt.bandwidth_mbps_max)
    if flt.provider_id is not None:
        params["providerId"] = str(flt.provider_id)
    if flt.a_site_id is not None:
        params["aSiteId"] = str(flt.a_site_id)
    if flt.z_site_id is not None:
        params["zSiteId"] = str(flt.z_site_id)
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
