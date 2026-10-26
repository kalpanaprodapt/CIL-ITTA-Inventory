"""Search filter for Compliance Check and its translation to upstream query parameters."""
from __future__ import annotations

from datetime import date, datetime
from decimal import Decimal
from uuid import UUID

from pydantic import Field

from itta_core.models import ApiModel, LifecycleStatus
from itta_api.domains.compliance.compliance_check.schemas import *  # noqa: F401,F403


class ComplianceCheckFilter(ApiModel):
    """Query-string filter for Compliance Check search."""

    q: str | None = Field(None, description="Free text over code, name and description")
    status: list[LifecycleStatus] | None = None
    code: str | None = Field(None, description="Code contains")
    name: str | None = Field(None, description="Name contains")
    external_ref: str | None = Field(None, description="External Ref contains")
    rule_id: UUID | None = None
    target_ref: str | None = Field(None, description="Target Ref contains")
    checked_at_from: datetime | None = None
    checked_at_to: datetime | None = None
    result: list[ComplianceCheckResult] | None = None
    criticality: list[ComplianceCheckCriticality] | None = None
    cost_center: str | None = Field(None, description="Cost Center contains")
    effective_from_from: date | None = None
    effective_from_to: date | None = None
    effective_to_from: date | None = None
    effective_to_to: date | None = None


def to_query(flt: ComplianceCheckFilter) -> dict[str, str]:
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
    if flt.rule_id is not None:
        params["ruleId"] = str(flt.rule_id)
    if flt.target_ref:
        params["targetRef"] = flt.target_ref.strip()
    if flt.checked_at_from is not None:
        params["checkedAtFrom"] = flt.checked_at_from.isoformat()
    if flt.checked_at_to is not None:
        params["checkedAtTo"] = flt.checked_at_to.isoformat()
    if flt.result:
        params["result"] = ",".join(v.value for v in flt.result)
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
