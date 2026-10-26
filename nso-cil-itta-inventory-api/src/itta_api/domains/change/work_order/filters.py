"""Search filter for Work Order and its translation to upstream query parameters."""
from __future__ import annotations

from datetime import date, datetime
from decimal import Decimal
from uuid import UUID

from pydantic import Field

from itta_core.models import ApiModel, LifecycleStatus
from itta_api.domains.change.work_order.schemas import *  # noqa: F401,F403


class WorkOrderFilter(ApiModel):
    """Query-string filter for Work Order search."""

    q: str | None = Field(None, description="Free text over code, name and description")
    status: list[LifecycleStatus] | None = None
    code: str | None = Field(None, description="Code contains")
    name: str | None = Field(None, description="Name contains")
    external_ref: str | None = Field(None, description="External Ref contains")
    work_order_number: str | None = Field(None, description="Work Order Number contains")
    work_type: str | None = Field(None, description="Work Type contains")
    assigned_technician_id: UUID | None = None
    due_date_from: date | None = None
    due_date_to: date | None = None
    work_state: list[WorkOrderWorkState] | None = None
    lifecycle_stage: list[WorkOrderLifecycleStage] | None = None
    source_system: str | None = Field(None, description="Source System contains")
    region: str | None = Field(None, description="Region contains")
    owner: str | None = Field(None, description="Owner contains")
    criticality: list[WorkOrderCriticality] | None = None


def to_query(flt: WorkOrderFilter) -> dict[str, str]:
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
    if flt.work_order_number:
        params["workOrderNumber"] = flt.work_order_number.strip()
    if flt.work_type:
        params["workType"] = flt.work_type.strip()
    if flt.assigned_technician_id is not None:
        params["assignedTechnicianId"] = str(flt.assigned_technician_id)
    if flt.due_date_from is not None:
        params["dueDateFrom"] = flt.due_date_from.isoformat()
    if flt.due_date_to is not None:
        params["dueDateTo"] = flt.due_date_to.isoformat()
    if flt.work_state:
        params["workState"] = ",".join(v.value for v in flt.work_state)
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
