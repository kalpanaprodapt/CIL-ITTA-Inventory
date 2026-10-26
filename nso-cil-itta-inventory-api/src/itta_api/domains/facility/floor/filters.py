"""Search filter for Floor and its translation to upstream query parameters."""
from __future__ import annotations

from datetime import date, datetime
from decimal import Decimal
from uuid import UUID

from pydantic import Field

from itta_core.models import ApiModel, LifecycleStatus
from itta_api.domains.facility.floor.schemas import *  # noqa: F401,F403


class FloorFilter(ApiModel):
    """Query-string filter for Floor search."""

    q: str | None = Field(None, description="Free text over code, name and description")
    status: list[LifecycleStatus] | None = None
    code: str | None = Field(None, description="Code contains")
    name: str | None = Field(None, description="Name contains")
    external_ref: str | None = Field(None, description="External Ref contains")
    building_id: UUID | None = None
    level_min: int | None = None
    level_max: int | None = None
    area_sqm_min: Decimal | None = None
    area_sqm_max: Decimal | None = None
    load_capacity_kg_min: Decimal | None = None
    load_capacity_kg_max: Decimal | None = None
    has_raised_floor: bool | None = None
    cost_center: str | None = Field(None, description="Cost Center contains")
    effective_from_from: date | None = None
    effective_from_to: date | None = None
    effective_to_from: date | None = None
    effective_to_to: date | None = None
    labels: str | None = Field(None, description="Labels contains")


def to_query(flt: FloorFilter) -> dict[str, str]:
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
    if flt.building_id is not None:
        params["buildingId"] = str(flt.building_id)
    if flt.level_min is not None:
        params["levelMin"] = str(flt.level_min)
    if flt.level_max is not None:
        params["levelMax"] = str(flt.level_max)
    if flt.area_sqm_min is not None:
        params["areaSqmMin"] = str(flt.area_sqm_min)
    if flt.area_sqm_max is not None:
        params["areaSqmMax"] = str(flt.area_sqm_max)
    if flt.load_capacity_kg_min is not None:
        params["loadCapacityKgMin"] = str(flt.load_capacity_kg_min)
    if flt.load_capacity_kg_max is not None:
        params["loadCapacityKgMax"] = str(flt.load_capacity_kg_max)
    if flt.has_raised_floor is not None:
        params["hasRaisedFloor"] = "true" if flt.has_raised_floor else "false"
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
