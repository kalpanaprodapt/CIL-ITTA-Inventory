"""Search filter for Audit Record and its translation to upstream query parameters."""
from __future__ import annotations

from datetime import date, datetime
from decimal import Decimal
from uuid import UUID

from pydantic import Field

from itta_core.models import ApiModel, LifecycleStatus
from itta_api.domains.compliance.audit_record.schemas import *  # noqa: F401,F403


class AuditRecordFilter(ApiModel):
    """Query-string filter for Audit Record search."""

    q: str | None = Field(None, description="Free text over code, name and description")
    status: list[LifecycleStatus] | None = None
    code: str | None = Field(None, description="Code contains")
    name: str | None = Field(None, description="Name contains")
    external_ref: str | None = Field(None, description="External Ref contains")
    actor: str | None = Field(None, description="Actor contains")
    action: str | None = Field(None, description="Action contains")
    entity_ref: str | None = Field(None, description="Entity Ref contains")
    occurred_at_from: datetime | None = None
    occurred_at_to: datetime | None = None
    effective_from_from: date | None = None
    effective_from_to: date | None = None
    effective_to_from: date | None = None
    effective_to_to: date | None = None
    labels: str | None = Field(None, description="Labels contains")
    lifecycle_stage: list[AuditRecordLifecycleStage] | None = None


def to_query(flt: AuditRecordFilter) -> dict[str, str]:
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
    if flt.actor:
        params["actor"] = flt.actor.strip()
    if flt.action:
        params["action"] = flt.action.strip()
    if flt.entity_ref:
        params["entityRef"] = flt.entity_ref.strip()
    if flt.occurred_at_from is not None:
        params["occurredAtFrom"] = flt.occurred_at_from.isoformat()
    if flt.occurred_at_to is not None:
        params["occurredAtTo"] = flt.occurred_at_to.isoformat()
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
