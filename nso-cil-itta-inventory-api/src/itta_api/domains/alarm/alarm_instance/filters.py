"""Search filter for Alarm Instance and its translation to upstream query parameters."""
from __future__ import annotations

from datetime import date, datetime
from decimal import Decimal
from uuid import UUID

from pydantic import Field

from itta_core.models import ApiModel, LifecycleStatus
from itta_api.domains.alarm.alarm_instance.schemas import *  # noqa: F401,F403


class AlarmInstanceFilter(ApiModel):
    """Query-string filter for Alarm Instance search."""

    q: str | None = Field(None, description="Free text over code, name and description")
    status: list[LifecycleStatus] | None = None
    code: str | None = Field(None, description="Code contains")
    name: str | None = Field(None, description="Name contains")
    external_ref: str | None = Field(None, description="External Ref contains")
    definition_id: UUID | None = None
    source_ref: str | None = Field(None, description="Source Ref contains")
    raised_at_from: datetime | None = None
    raised_at_to: datetime | None = None
    cleared_at_from: datetime | None = None
    cleared_at_to: datetime | None = None
    acked_by: str | None = Field(None, description="Acked By contains")
    alarm_state: list[AlarmInstanceAlarmState] | None = None
    effective_from_from: date | None = None
    effective_from_to: date | None = None
    effective_to_from: date | None = None
    effective_to_to: date | None = None
    labels: str | None = Field(None, description="Labels contains")
    lifecycle_stage: list[AlarmInstanceLifecycleStage] | None = None
    source_system: str | None = Field(None, description="Source System contains")


def to_query(flt: AlarmInstanceFilter) -> dict[str, str]:
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
    if flt.definition_id is not None:
        params["definitionId"] = str(flt.definition_id)
    if flt.source_ref:
        params["sourceRef"] = flt.source_ref.strip()
    if flt.raised_at_from is not None:
        params["raisedAtFrom"] = flt.raised_at_from.isoformat()
    if flt.raised_at_to is not None:
        params["raisedAtTo"] = flt.raised_at_to.isoformat()
    if flt.cleared_at_from is not None:
        params["clearedAtFrom"] = flt.cleared_at_from.isoformat()
    if flt.cleared_at_to is not None:
        params["clearedAtTo"] = flt.cleared_at_to.isoformat()
    if flt.acked_by:
        params["ackedBy"] = flt.acked_by.strip()
    if flt.alarm_state:
        params["alarmState"] = ",".join(v.value for v in flt.alarm_state)
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
    if flt.source_system:
        params["sourceSystem"] = flt.source_system.strip()
    return params
