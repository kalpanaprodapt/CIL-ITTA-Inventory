"""Search filter for Change Request and its translation to upstream query parameters."""
from __future__ import annotations

from datetime import date, datetime
from decimal import Decimal
from uuid import UUID

from pydantic import Field

from itta_core.models import ApiModel, LifecycleStatus
from itta_api.domains.change.change_request.schemas import *  # noqa: F401,F403


class ChangeRequestFilter(ApiModel):
    """Query-string filter for Change Request search."""

    q: str | None = Field(None, description="Free text over code, name and description")
    status: list[LifecycleStatus] | None = None
    code: str | None = Field(None, description="Code contains")
    name: str | None = Field(None, description="Name contains")
    external_ref: str | None = Field(None, description="External Ref contains")
    change_number: str | None = Field(None, description="Change Number contains")
    change_type: list[ChangeRequestChangeType] | None = None
    risk_level: list[ChangeRequestRiskLevel] | None = None
    planned_start_from: datetime | None = None
    planned_start_to: datetime | None = None
    planned_end_from: datetime | None = None
    planned_end_to: datetime | None = None
    effective_from_from: date | None = None
    effective_from_to: date | None = None
    effective_to_from: date | None = None
    effective_to_to: date | None = None
    labels: str | None = Field(None, description="Labels contains")
    lifecycle_stage: list[ChangeRequestLifecycleStage] | None = None


def to_query(flt: ChangeRequestFilter) -> dict[str, str]:
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
    if flt.change_number:
        params["changeNumber"] = flt.change_number.strip()
    if flt.change_type:
        params["changeType"] = ",".join(v.value for v in flt.change_type)
    if flt.risk_level:
        params["riskLevel"] = ",".join(v.value for v in flt.risk_level)
    if flt.planned_start_from is not None:
        params["plannedStartFrom"] = flt.planned_start_from.isoformat()
    if flt.planned_start_to is not None:
        params["plannedStartTo"] = flt.planned_start_to.isoformat()
    if flt.planned_end_from is not None:
        params["plannedEndFrom"] = flt.planned_end_from.isoformat()
    if flt.planned_end_to is not None:
        params["plannedEndTo"] = flt.planned_end_to.isoformat()
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
