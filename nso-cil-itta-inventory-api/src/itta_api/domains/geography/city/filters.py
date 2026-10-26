"""Search filter for City and its translation to upstream query parameters."""
from __future__ import annotations

from datetime import date, datetime
from decimal import Decimal
from uuid import UUID

from pydantic import Field

from itta_core.models import ApiModel, LifecycleStatus
from itta_api.domains.geography.city.schemas import *  # noqa: F401,F403


class CityFilter(ApiModel):
    """Query-string filter for City search."""

    q: str | None = Field(None, description="Free text over code, name and description")
    status: list[LifecycleStatus] | None = None
    code: str | None = Field(None, description="Code contains")
    name: str | None = Field(None, description="Name contains")
    external_ref: str | None = Field(None, description="External Ref contains")
    region_id: UUID | None = None
    postal_prefix: str | None = Field(None, description="Postal Prefix contains")
    population_min: int | None = None
    population_max: int | None = None
    latitude_min: Decimal | None = None
    latitude_max: Decimal | None = None
    longitude_min: Decimal | None = None
    longitude_max: Decimal | None = None
    metro_area: str | None = Field(None, description="Metro Area contains")
    effective_from_from: date | None = None
    effective_from_to: date | None = None
    effective_to_from: date | None = None
    effective_to_to: date | None = None
    labels: str | None = Field(None, description="Labels contains")
    lifecycle_stage: list[CityLifecycleStage] | None = None


def to_query(flt: CityFilter) -> dict[str, str]:
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
    if flt.region_id is not None:
        params["regionId"] = str(flt.region_id)
    if flt.postal_prefix:
        params["postalPrefix"] = flt.postal_prefix.strip()
    if flt.population_min is not None:
        params["populationMin"] = str(flt.population_min)
    if flt.population_max is not None:
        params["populationMax"] = str(flt.population_max)
    if flt.latitude_min is not None:
        params["latitudeMin"] = str(flt.latitude_min)
    if flt.latitude_max is not None:
        params["latitudeMax"] = str(flt.latitude_max)
    if flt.longitude_min is not None:
        params["longitudeMin"] = str(flt.longitude_min)
    if flt.longitude_max is not None:
        params["longitudeMax"] = str(flt.longitude_max)
    if flt.metro_area:
        params["metroArea"] = flt.metro_area.strip()
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
    return params
