"""Search filter for Discovery Job and its translation to upstream query parameters."""
from __future__ import annotations

from datetime import date, datetime
from decimal import Decimal
from uuid import UUID

from pydantic import Field

from itta_core.models import ApiModel, LifecycleStatus
from itta_api.domains.discovery.discovery_job.schemas import *  # noqa: F401,F403


class DiscoveryJobFilter(ApiModel):
    """Query-string filter for Discovery Job search."""

    q: str | None = Field(None, description="Free text over code, name and description")
    status: list[LifecycleStatus] | None = None
    code: str | None = Field(None, description="Code contains")
    name: str | None = Field(None, description="Name contains")
    external_ref: str | None = Field(None, description="External Ref contains")
    profile_id: UUID | None = None
    started_at_from: datetime | None = None
    started_at_to: datetime | None = None
    finished_at_from: datetime | None = None
    finished_at_to: datetime | None = None
    job_state: list[DiscoveryJobJobState] | None = None
    assets_found_min: int | None = None
    assets_found_max: int | None = None
    owner: str | None = Field(None, description="Owner contains")
    criticality: list[DiscoveryJobCriticality] | None = None
    cost_center: str | None = Field(None, description="Cost Center contains")
    effective_from_from: date | None = None
    effective_from_to: date | None = None


def to_query(flt: DiscoveryJobFilter) -> dict[str, str]:
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
    if flt.profile_id is not None:
        params["profileId"] = str(flt.profile_id)
    if flt.started_at_from is not None:
        params["startedAtFrom"] = flt.started_at_from.isoformat()
    if flt.started_at_to is not None:
        params["startedAtTo"] = flt.started_at_to.isoformat()
    if flt.finished_at_from is not None:
        params["finishedAtFrom"] = flt.finished_at_from.isoformat()
    if flt.finished_at_to is not None:
        params["finishedAtTo"] = flt.finished_at_to.isoformat()
    if flt.job_state:
        params["jobState"] = ",".join(v.value for v in flt.job_state)
    if flt.assets_found_min is not None:
        params["assetsFoundMin"] = str(flt.assets_found_min)
    if flt.assets_found_max is not None:
        params["assetsFoundMax"] = str(flt.assets_found_max)
    if flt.owner:
        params["owner"] = flt.owner.strip()
    if flt.criticality:
        params["criticality"] = ",".join(v.value for v in flt.criticality)
    if flt.cost_center:
        params["costCenter"] = flt.cost_center.strip()
    if flt.effective_from_from is not None:
        params["effectiveFromFrom"] = flt.effective_from_from.isoformat()
    if flt.effective_from_to is not None:
        params["effectiveFromTo"] = flt.effective_from_to.isoformat()
    return params
