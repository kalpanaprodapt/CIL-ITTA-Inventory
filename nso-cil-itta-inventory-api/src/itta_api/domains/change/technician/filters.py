"""Search filter for Technician and its translation to upstream query parameters."""
from __future__ import annotations

from datetime import date, datetime
from decimal import Decimal
from uuid import UUID

from pydantic import Field

from itta_core.models import ApiModel, LifecycleStatus
from itta_api.domains.change.technician.schemas import *  # noqa: F401,F403


class TechnicianFilter(ApiModel):
    """Query-string filter for Technician search."""

    q: str | None = Field(None, description="Free text over code, name and description")
    status: list[LifecycleStatus] | None = None
    code: str | None = Field(None, description="Code contains")
    name: str | None = Field(None, description="Name contains")
    external_ref: str | None = Field(None, description="External Ref contains")
    employee_id: str | None = Field(None, description="Employee Id contains")
    full_name: str | None = Field(None, description="Full Name contains")
    skill_set: str | None = Field(None, description="Skill Set contains")
    home_site_id: UUID | None = None
    certification_level_min: int | None = None
    certification_level_max: int | None = None
    criticality: list[TechnicianCriticality] | None = None
    cost_center: str | None = Field(None, description="Cost Center contains")
    effective_from_from: date | None = None
    effective_from_to: date | None = None
    effective_to_from: date | None = None
    effective_to_to: date | None = None


def to_query(flt: TechnicianFilter) -> dict[str, str]:
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
    if flt.employee_id:
        params["employeeId"] = flt.employee_id.strip()
    if flt.full_name:
        params["fullName"] = flt.full_name.strip()
    if flt.skill_set:
        params["skillSet"] = flt.skill_set.strip()
    if flt.home_site_id is not None:
        params["homeSiteId"] = str(flt.home_site_id)
    if flt.certification_level_min is not None:
        params["certificationLevelMin"] = str(flt.certification_level_min)
    if flt.certification_level_max is not None:
        params["certificationLevelMax"] = str(flt.certification_level_max)
    if flt.criticality:
        params["criticality"] = ",".join(v.value for v in flt.criticality)
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
    return params
