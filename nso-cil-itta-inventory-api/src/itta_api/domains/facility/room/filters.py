"""Search filter for Room and its translation to upstream query parameters."""
from __future__ import annotations

from datetime import date, datetime
from decimal import Decimal
from uuid import UUID

from pydantic import Field

from itta_core.models import ApiModel, LifecycleStatus
from itta_api.domains.facility.room.schemas import *  # noqa: F401,F403


class RoomFilter(ApiModel):
    """Query-string filter for Room search."""

    q: str | None = Field(None, description="Free text over code, name and description")
    status: list[LifecycleStatus] | None = None
    code: str | None = Field(None, description="Code contains")
    name: str | None = Field(None, description="Name contains")
    external_ref: str | None = Field(None, description="External Ref contains")
    floor_id: UUID | None = None
    room_type: list[RoomRoomType] | None = None
    area_sqm_min: Decimal | None = None
    area_sqm_max: Decimal | None = None
    cooling_tons_min: Decimal | None = None
    cooling_tons_max: Decimal | None = None
    fire_zone: str | None = Field(None, description="Fire Zone contains")
    effective_from_from: date | None = None
    effective_from_to: date | None = None
    effective_to_from: date | None = None
    effective_to_to: date | None = None
    labels: str | None = Field(None, description="Labels contains")
    lifecycle_stage: list[RoomLifecycleStage] | None = None
    source_system: str | None = Field(None, description="Source System contains")


def to_query(flt: RoomFilter) -> dict[str, str]:
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
    if flt.floor_id is not None:
        params["floorId"] = str(flt.floor_id)
    if flt.room_type:
        params["roomType"] = ",".join(v.value for v in flt.room_type)
    if flt.area_sqm_min is not None:
        params["areaSqmMin"] = str(flt.area_sqm_min)
    if flt.area_sqm_max is not None:
        params["areaSqmMax"] = str(flt.area_sqm_max)
    if flt.cooling_tons_min is not None:
        params["coolingTonsMin"] = str(flt.cooling_tons_min)
    if flt.cooling_tons_max is not None:
        params["coolingTonsMax"] = str(flt.cooling_tons_max)
    if flt.fire_zone:
        params["fireZone"] = flt.fire_zone.strip()
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
    if flt.lifecycle_stage:
        params["lifecycleStage"] = ",".join(v.value for v in flt.lifecycle_stage)
    if flt.source_system:
        params["sourceSystem"] = flt.source_system.strip()
    return params
