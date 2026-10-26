"""Search filter for Rack and its translation to upstream query parameters."""
from __future__ import annotations

from datetime import date, datetime
from decimal import Decimal
from uuid import UUID

from pydantic import Field

from itta_core.models import ApiModel, LifecycleStatus
from itta_api.domains.facility.rack.schemas import *  # noqa: F401,F403


class RackFilter(ApiModel):
    """Query-string filter for Rack search."""

    q: str | None = Field(None, description="Free text over code, name and description")
    status: list[LifecycleStatus] | None = None
    code: str | None = Field(None, description="Code contains")
    name: str | None = Field(None, description="Name contains")
    external_ref: str | None = Field(None, description="External Ref contains")
    room_id: UUID | None = None
    rack_units_min: int | None = None
    rack_units_max: int | None = None
    width_mm_min: int | None = None
    width_mm_max: int | None = None
    depth_mm_min: int | None = None
    depth_mm_max: int | None = None
    max_weight_kg_min: Decimal | None = None
    max_weight_kg_max: Decimal | None = None
    row_label: str | None = Field(None, description="Row Label contains")
    position: str | None = Field(None, description="Position contains")
    labels: str | None = Field(None, description="Labels contains")
    lifecycle_stage: list[RackLifecycleStage] | None = None
    source_system: str | None = Field(None, description="Source System contains")
    region: str | None = Field(None, description="Region contains")
    owner: str | None = Field(None, description="Owner contains")


def to_query(flt: RackFilter) -> dict[str, str]:
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
    if flt.room_id is not None:
        params["roomId"] = str(flt.room_id)
    if flt.rack_units_min is not None:
        params["rackUnitsMin"] = str(flt.rack_units_min)
    if flt.rack_units_max is not None:
        params["rackUnitsMax"] = str(flt.rack_units_max)
    if flt.width_mm_min is not None:
        params["widthMmMin"] = str(flt.width_mm_min)
    if flt.width_mm_max is not None:
        params["widthMmMax"] = str(flt.width_mm_max)
    if flt.depth_mm_min is not None:
        params["depthMmMin"] = str(flt.depth_mm_min)
    if flt.depth_mm_max is not None:
        params["depthMmMax"] = str(flt.depth_mm_max)
    if flt.max_weight_kg_min is not None:
        params["maxWeightKgMin"] = str(flt.max_weight_kg_min)
    if flt.max_weight_kg_max is not None:
        params["maxWeightKgMax"] = str(flt.max_weight_kg_max)
    if flt.row_label:
        params["rowLabel"] = flt.row_label.strip()
    if flt.position:
        params["position"] = flt.position.strip()
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
