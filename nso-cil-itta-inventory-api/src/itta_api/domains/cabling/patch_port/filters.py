"""Search filter for Patch Port and its translation to upstream query parameters."""
from __future__ import annotations

from datetime import date, datetime
from decimal import Decimal
from uuid import UUID

from pydantic import Field

from itta_core.models import ApiModel, LifecycleStatus
from itta_api.domains.cabling.patch_port.schemas import *  # noqa: F401,F403


class PatchPortFilter(ApiModel):
    """Query-string filter for Patch Port search."""

    q: str | None = Field(None, description="Free text over code, name and description")
    status: list[LifecycleStatus] | None = None
    code: str | None = Field(None, description="Code contains")
    name: str | None = Field(None, description="Name contains")
    external_ref: str | None = Field(None, description="External Ref contains")
    panel_id: UUID | None = None
    port_number_min: int | None = None
    port_number_max: int | None = None
    connected_cable_id: UUID | None = None
    polarity: str | None = Field(None, description="Polarity contains")
    effective_to_from: date | None = None
    effective_to_to: date | None = None
    labels: str | None = Field(None, description="Labels contains")
    lifecycle_stage: list[PatchPortLifecycleStage] | None = None
    source_system: str | None = Field(None, description="Source System contains")
    region: str | None = Field(None, description="Region contains")


def to_query(flt: PatchPortFilter) -> dict[str, str]:
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
    if flt.panel_id is not None:
        params["panelId"] = str(flt.panel_id)
    if flt.port_number_min is not None:
        params["portNumberMin"] = str(flt.port_number_min)
    if flt.port_number_max is not None:
        params["portNumberMax"] = str(flt.port_number_max)
    if flt.connected_cable_id is not None:
        params["connectedCableId"] = str(flt.connected_cable_id)
    if flt.polarity:
        params["polarity"] = flt.polarity.strip()
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
