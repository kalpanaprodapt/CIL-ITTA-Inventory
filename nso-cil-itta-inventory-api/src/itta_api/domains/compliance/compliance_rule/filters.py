"""Search filter for Compliance Rule and its translation to upstream query parameters."""
from __future__ import annotations

from datetime import date, datetime
from decimal import Decimal
from uuid import UUID

from pydantic import Field

from itta_core.models import ApiModel, LifecycleStatus
from itta_api.domains.compliance.compliance_rule.schemas import *  # noqa: F401,F403


class ComplianceRuleFilter(ApiModel):
    """Query-string filter for Compliance Rule search."""

    q: str | None = Field(None, description="Free text over code, name and description")
    status: list[LifecycleStatus] | None = None
    code: str | None = Field(None, description="Code contains")
    name: str | None = Field(None, description="Name contains")
    external_ref: str | None = Field(None, description="External Ref contains")
    rule_code: str | None = Field(None, description="Rule Code contains")
    framework: list[ComplianceRuleFramework] | None = None
    severity: list[ComplianceRuleSeverity] | None = None
    region: str | None = Field(None, description="Region contains")
    owner: str | None = Field(None, description="Owner contains")
    criticality: list[ComplianceRuleCriticality] | None = None
    cost_center: str | None = Field(None, description="Cost Center contains")


def to_query(flt: ComplianceRuleFilter) -> dict[str, str]:
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
    if flt.rule_code:
        params["ruleCode"] = flt.rule_code.strip()
    if flt.framework:
        params["framework"] = ",".join(v.value for v in flt.framework)
    if flt.severity:
        params["severity"] = ",".join(v.value for v in flt.severity)
    if flt.region:
        params["region"] = flt.region.strip()
    if flt.owner:
        params["owner"] = flt.owner.strip()
    if flt.criticality:
        params["criticality"] = ",".join(v.value for v in flt.criticality)
    if flt.cost_center:
        params["costCenter"] = flt.cost_center.strip()
    return params
