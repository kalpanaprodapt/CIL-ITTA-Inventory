"""Search filter for Sync Job and its translation to upstream query parameters."""
from __future__ import annotations

from datetime import date, datetime
from decimal import Decimal
from uuid import UUID

from pydantic import Field

from itta_core.models import ApiModel, LifecycleStatus
from itta_api.domains.integration.sync_job.schemas import *  # noqa: F401,F403


class SyncJobFilter(ApiModel):
    """Query-string filter for Sync Job search."""

    q: str | None = Field(None, description="Free text over code, name and description")
    status: list[LifecycleStatus] | None = None
    code: str | None = Field(None, description="Code contains")
    name: str | None = Field(None, description="Name contains")
    external_ref: str | None = Field(None, description="External Ref contains")
    endpoint_id: UUID | None = None
    direction: list[SyncJobDirection] | None = None
    schedule_cron: str | None = Field(None, description="Schedule Cron contains")
    last_run_at_from: datetime | None = None
    last_run_at_to: datetime | None = None
    last_status: list[SyncJobLastStatus] | None = None
    owner: str | None = Field(None, description="Owner contains")
    criticality: list[SyncJobCriticality] | None = None
    cost_center: str | None = Field(None, description="Cost Center contains")
    effective_from_from: date | None = None
    effective_from_to: date | None = None


def to_query(flt: SyncJobFilter) -> dict[str, str]:
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
    if flt.endpoint_id is not None:
        params["endpointId"] = str(flt.endpoint_id)
    if flt.direction:
        params["direction"] = ",".join(v.value for v in flt.direction)
    if flt.schedule_cron:
        params["scheduleCron"] = flt.schedule_cron.strip()
    if flt.last_run_at_from is not None:
        params["lastRunAtFrom"] = flt.last_run_at_from.isoformat()
    if flt.last_run_at_to is not None:
        params["lastRunAtTo"] = flt.last_run_at_to.isoformat()
    if flt.last_status:
        params["lastStatus"] = ",".join(v.value for v in flt.last_status)
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
