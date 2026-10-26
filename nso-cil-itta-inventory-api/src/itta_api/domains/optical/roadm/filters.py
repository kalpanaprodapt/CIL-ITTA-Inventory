"""Search filter for Roadm and its translation to upstream query parameters."""
from __future__ import annotations

from datetime import date, datetime
from decimal import Decimal
from uuid import UUID

from pydantic import Field

from itta_core.models import ApiModel, LifecycleStatus
from itta_api.domains.optical.roadm.schemas import *  # noqa: F401,F403


class RoadmFilter(ApiModel):
    """Query-string filter for Roadm search."""

    q: str | None = Field(None, description="Free text over code, name and description")
    status: list[LifecycleStatus] | None = None
    code: str | None = Field(None, description="Code contains")
    name: str | None = Field(None, description="Name contains")
    external_ref: str | None = Field(None, description="External Ref contains")
    system_id: UUID | None = None
    degrees_min: int | None = None
    degrees_max: int | None = None
    colorless: bool | None = None
    directionless: bool | None = None
    contentionless: bool | None = None
    region: str | None = Field(None, description="Region contains")
    owner: str | None = Field(None, description="Owner contains")
    criticality: list[RoadmCriticality] | None = None
    cost_center: str | None = Field(None, description="Cost Center contains")


def to_query(flt: RoadmFilter) -> dict[str, str]:
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
    if flt.system_id is not None:
        params["systemId"] = str(flt.system_id)
    if flt.degrees_min is not None:
        params["degreesMin"] = str(flt.degrees_min)
    if flt.degrees_max is not None:
        params["degreesMax"] = str(flt.degrees_max)
    if flt.colorless is not None:
        params["colorless"] = "true" if flt.colorless else "false"
    if flt.directionless is not None:
        params["directionless"] = "true" if flt.directionless else "false"
    if flt.contentionless is not None:
        params["contentionless"] = "true" if flt.contentionless else "false"
    if flt.region:
        params["region"] = flt.region.strip()
    if flt.owner:
        params["owner"] = flt.owner.strip()
    if flt.criticality:
        params["criticality"] = ",".join(v.value for v in flt.criticality)
    if flt.cost_center:
        params["costCenter"] = flt.cost_center.strip()
    return params
