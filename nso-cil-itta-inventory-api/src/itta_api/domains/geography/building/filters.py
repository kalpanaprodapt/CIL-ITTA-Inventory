"""Search filter for Building and its translation to upstream query parameters."""
from __future__ import annotations

from datetime import date, datetime
from decimal import Decimal
from uuid import UUID

from pydantic import Field

from itta_core.models import ApiModel, LifecycleStatus
from itta_api.domains.geography.building.schemas import *  # noqa: F401,F403


class BuildingFilter(ApiModel):
    """Query-string filter for Building search."""

    q: str | None = Field(None, description="Free text over code, name and description")
    status: list[LifecycleStatus] | None = None
    code: str | None = Field(None, description="Code contains")
    name: str | None = Field(None, description="Name contains")
    external_ref: str | None = Field(None, description="External Ref contains")
    site_id: UUID | None = None
    floors_min: int | None = None
    floors_max: int | None = None
    year_built_min: int | None = None
    year_built_max: int | None = None
    ownership: list[BuildingOwnership] | None = None
    lease_expiry_from: date | None = None
    lease_expiry_to: date | None = None
    security_level_min: int | None = None
    security_level_max: int | None = None
    lifecycle_stage: list[BuildingLifecycleStage] | None = None
    source_system: str | None = Field(None, description="Source System contains")
    region: str | None = Field(None, description="Region contains")
    owner: str | None = Field(None, description="Owner contains")
    criticality: list[BuildingCriticality] | None = None


def to_query(flt: BuildingFilter) -> dict[str, str]:
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
    if flt.site_id is not None:
        params["siteId"] = str(flt.site_id)
    if flt.floors_min is not None:
        params["floorsMin"] = str(flt.floors_min)
    if flt.floors_max is not None:
        params["floorsMax"] = str(flt.floors_max)
    if flt.year_built_min is not None:
        params["yearBuiltMin"] = str(flt.year_built_min)
    if flt.year_built_max is not None:
        params["yearBuiltMax"] = str(flt.year_built_max)
    if flt.ownership:
        params["ownership"] = ",".join(v.value for v in flt.ownership)
    if flt.lease_expiry_from is not None:
        params["leaseExpiryFrom"] = flt.lease_expiry_from.isoformat()
    if flt.lease_expiry_to is not None:
        params["leaseExpiryTo"] = flt.lease_expiry_to.isoformat()
    if flt.security_level_min is not None:
        params["securityLevelMin"] = str(flt.security_level_min)
    if flt.security_level_max is not None:
        params["securityLevelMax"] = str(flt.security_level_max)
    if flt.lifecycle_stage:
        params["lifecycleStage"] = ",".join(v.value for v in flt.lifecycle_stage)
    if flt.source_system:
        params["sourceSystem"] = flt.source_system.strip()
    if flt.region:
        params["region"] = flt.region.strip()
    if flt.owner:
        params["owner"] = flt.owner.strip()
    if flt.criticality:
        params["criticality"] = ",".join(v.value for v in flt.criticality)
    return params
