"""Search filter for Reconciliation Result and its translation to upstream query parameters."""
from __future__ import annotations

from datetime import date, datetime
from decimal import Decimal
from uuid import UUID

from pydantic import Field

from itta_core.models import ApiModel, LifecycleStatus
from itta_api.domains.discovery.reconciliation_result.schemas import *  # noqa: F401,F403


class ReconciliationResultFilter(ApiModel):
    """Query-string filter for Reconciliation Result search."""

    q: str | None = Field(None, description="Free text over code, name and description")
    status: list[LifecycleStatus] | None = None
    code: str | None = Field(None, description="Code contains")
    name: str | None = Field(None, description="Name contains")
    external_ref: str | None = Field(None, description="External Ref contains")
    job_id: UUID | None = None
    rule_id: UUID | None = None
    outcome: list[ReconciliationResultOutcome] | None = None
    resolved_by: str | None = Field(None, description="Resolved By contains")
    source_system: str | None = Field(None, description="Source System contains")
    region: str | None = Field(None, description="Region contains")
    owner: str | None = Field(None, description="Owner contains")
    criticality: list[ReconciliationResultCriticality] | None = None
    cost_center: str | None = Field(None, description="Cost Center contains")


def to_query(flt: ReconciliationResultFilter) -> dict[str, str]:
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
    if flt.job_id is not None:
        params["jobId"] = str(flt.job_id)
    if flt.rule_id is not None:
        params["ruleId"] = str(flt.rule_id)
    if flt.outcome:
        params["outcome"] = ",".join(v.value for v in flt.outcome)
    if flt.resolved_by:
        params["resolvedBy"] = flt.resolved_by.strip()
    if flt.source_system:
        params["sourceSystem"] = flt.source_system.strip()
    if flt.region:
        params["region"] = flt.region.strip()
    if flt.owner:
        params["owner"] = flt.owner.strip()
    if flt.criticality:
        params["criticality"] = ",".join(v.value for v in flt.criticality)
    if flt.cost_center:
        params["costCenter"] = flt.cost_center.strip()
    return params
