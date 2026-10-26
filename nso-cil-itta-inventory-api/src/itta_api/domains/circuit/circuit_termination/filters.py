"""Search filter for Circuit Termination and its translation to upstream query parameters."""
from __future__ import annotations

from datetime import date, datetime
from decimal import Decimal
from uuid import UUID

from pydantic import Field

from itta_core.models import ApiModel, LifecycleStatus
from itta_api.domains.circuit.circuit_termination.schemas import *  # noqa: F401,F403


class CircuitTerminationFilter(ApiModel):
    """Query-string filter for Circuit Termination search."""

    q: str | None = Field(None, description="Free text over code, name and description")
    status: list[LifecycleStatus] | None = None
    code: str | None = Field(None, description="Code contains")
    name: str | None = Field(None, description="Name contains")
    external_ref: str | None = Field(None, description="External Ref contains")
    circuit_id: UUID | None = None
    side: list[CircuitTerminationSide] | None = None
    port_id: UUID | None = None
    xconnect_id: str | None = Field(None, description="Xconnect Id contains")
    patch_info: str | None = Field(None, description="Patch Info contains")
    cost_center: str | None = Field(None, description="Cost Center contains")
    effective_from_from: date | None = None
    effective_from_to: date | None = None
    effective_to_from: date | None = None
    effective_to_to: date | None = None
    labels: str | None = Field(None, description="Labels contains")


def to_query(flt: CircuitTerminationFilter) -> dict[str, str]:
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
    if flt.side:
        params["side"] = ",".join(v.value for v in flt.side)
    if flt.port_id is not None:
        params["portId"] = str(flt.port_id)
    if flt.xconnect_id:
        params["xconnectId"] = flt.xconnect_id.strip()
    if flt.patch_info:
        params["patchInfo"] = flt.patch_info.strip()
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
