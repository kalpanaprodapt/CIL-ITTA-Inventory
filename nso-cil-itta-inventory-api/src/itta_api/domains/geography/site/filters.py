"""Search filter for Site and its translation to upstream query parameters."""
from __future__ import annotations

from datetime import date, datetime
from decimal import Decimal
from uuid import UUID

from pydantic import Field

from itta_core.models import ApiModel, LifecycleStatus
from itta_api.domains.geography.site.schemas import *  # noqa: F401,F403


class SiteFilter(ApiModel):
    """Query-string filter for Site search."""

    q: str | None = Field(None, description="Free text over code, name and description")
    status: list[LifecycleStatus] | None = None
    code: str | None = Field(None, description="Code contains")
    name: str | None = Field(None, description="Name contains")
    external_ref: str | None = Field(None, description="External Ref contains")
    city_id: UUID | None = None
    site_type: list[SiteSiteType] | None = None
    address: str | None = Field(None, description="Address contains")
    latitude_min: Decimal | None = None
    latitude_max: Decimal | None = None
    longitude_min: Decimal | None = None
    longitude_max: Decimal | None = None
    power_kw_min: Decimal | None = None
    power_kw_max: Decimal | None = None
    access_hours: str | None = Field(None, description="Access Hours contains")
    effective_to_from: date | None = None
    effective_to_to: date | None = None
    labels: str | None = Field(None, description="Labels contains")
    lifecycle_stage: list[SiteLifecycleStage] | None = None
    source_system: str | None = Field(None, description="Source System contains")
    region: str | None = Field(None, description="Region contains")


def to_query(flt: SiteFilter) -> dict[str, str]:
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
    if flt.city_id is not None:
        params["cityId"] = str(flt.city_id)
    if flt.site_type:
        params["siteType"] = ",".join(v.value for v in flt.site_type)
    if flt.address:
        params["address"] = flt.address.strip()
    if flt.latitude_min is not None:
        params["latitudeMin"] = str(flt.latitude_min)
    if flt.latitude_max is not None:
        params["latitudeMax"] = str(flt.latitude_max)
    if flt.longitude_min is not None:
        params["longitudeMin"] = str(flt.longitude_min)
    if flt.longitude_max is not None:
        params["longitudeMax"] = str(flt.longitude_max)
    if flt.power_kw_min is not None:
        params["powerKwMin"] = str(flt.power_kw_min)
    if flt.power_kw_max is not None:
        params["powerKwMax"] = str(flt.power_kw_max)
    if flt.access_hours:
        params["accessHours"] = flt.access_hours.strip()
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
