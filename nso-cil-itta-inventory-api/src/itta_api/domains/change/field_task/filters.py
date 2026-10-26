"""Search filter for Field Task and its translation to upstream query parameters."""
from __future__ import annotations

from datetime import date, datetime
from decimal import Decimal
from uuid import UUID

from pydantic import Field

from itta_core.models import ApiModel, LifecycleStatus
from itta_api.domains.change.field_task.schemas import *  # noqa: F401,F403


class FieldTaskFilter(ApiModel):
    """Query-string filter for Field Task search."""

    q: str | None = Field(None, description="Free text over code, name and description")
    status: list[LifecycleStatus] | None = None
    code: str | None = Field(None, description="Code contains")
    name: str | None = Field(None, description="Name contains")
    external_ref: str | None = Field(None, description="External Ref contains")
    work_order_id: UUID | None = None
    task_name: str | None = Field(None, description="Task Name contains")
    sequence_min: int | None = None
    sequence_max: int | None = None
    duration_minutes_min: int | None = None
    duration_minutes_max: int | None = None
    completed_on_from: datetime | None = None
    completed_on_to: datetime | None = None
    region: str | None = Field(None, description="Region contains")
    owner: str | None = Field(None, description="Owner contains")
    criticality: list[FieldTaskCriticality] | None = None
    cost_center: str | None = Field(None, description="Cost Center contains")


def to_query(flt: FieldTaskFilter) -> dict[str, str]:
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
    if flt.work_order_id is not None:
        params["workOrderId"] = str(flt.work_order_id)
    if flt.task_name:
        params["taskName"] = flt.task_name.strip()
    if flt.sequence_min is not None:
        params["sequenceMin"] = str(flt.sequence_min)
    if flt.sequence_max is not None:
        params["sequenceMax"] = str(flt.sequence_max)
    if flt.duration_minutes_min is not None:
        params["durationMinutesMin"] = str(flt.duration_minutes_min)
    if flt.duration_minutes_max is not None:
        params["durationMinutesMax"] = str(flt.duration_minutes_max)
    if flt.completed_on_from is not None:
        params["completedOnFrom"] = flt.completed_on_from.isoformat()
    if flt.completed_on_to is not None:
        params["completedOnTo"] = flt.completed_on_to.isoformat()
    if flt.region:
        params["region"] = flt.region.strip()
    if flt.owner:
        params["owner"] = flt.owner.strip()
    if flt.criticality:
        params["criticality"] = ",".join(v.value for v in flt.criticality)
    if flt.cost_center:
        params["costCenter"] = flt.cost_center.strip()
    return params
