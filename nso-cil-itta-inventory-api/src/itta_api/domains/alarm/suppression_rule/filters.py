"""Search filter for Suppression Rule and its translation to upstream query parameters."""
from __future__ import annotations

from datetime import date, datetime
from decimal import Decimal
from uuid import UUID

from pydantic import Field

from itta_core.models import ApiModel, LifecycleStatus
from itta_api.domains.alarm.suppression_rule.schemas import *  # noqa: F401,F403


class SuppressionRuleFilter(ApiModel):
    """Query-string filter for Suppression Rule search."""

    q: str | None = Field(None, description="Free text over code, name and description")
    status: list[LifecycleStatus] | None = None
    code: str | None = Field(None, description="Code contains")
    name: str | None = Field(None, description="Name contains")
    external_ref: str | None = Field(None, description="External Ref contains")
    rule_name: str | None = Field(None, description="Rule Name contains")
    starts_at_from: datetime | None = None
    starts_at_to: datetime | None = None
    ends_at_from: datetime | None = None
    ends_at_to: datetime | None = None
    reason: str | None = Field(None, description="Reason contains")
    source_system: str | None = Field(None, description="Source System contains")
    region: str | None = Field(None, description="Region contains")
    owner: str | None = Field(None, description="Owner contains")
    criticality: list[SuppressionRuleCriticality] | None = None
    cost_center: str | None = Field(None, description="Cost Center contains")


def to_query(flt: SuppressionRuleFilter) -> dict[str, str]:
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
    if flt.rule_name:
        params["ruleName"] = flt.rule_name.strip()
    if flt.starts_at_from is not None:
        params["startsAtFrom"] = flt.starts_at_from.isoformat()
    if flt.starts_at_to is not None:
        params["startsAtTo"] = flt.starts_at_to.isoformat()
    if flt.ends_at_from is not None:
        params["endsAtFrom"] = flt.ends_at_from.isoformat()
    if flt.ends_at_to is not None:
        params["endsAtTo"] = flt.ends_at_to.isoformat()
    if flt.reason:
        params["reason"] = flt.reason.strip()
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
