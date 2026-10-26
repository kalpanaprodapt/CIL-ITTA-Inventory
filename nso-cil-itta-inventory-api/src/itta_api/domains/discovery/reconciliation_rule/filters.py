"""Search filter for Reconciliation Rule and its translation to upstream query parameters."""
from __future__ import annotations

from datetime import date, datetime
from decimal import Decimal
from uuid import UUID

from pydantic import Field

from itta_core.models import ApiModel, LifecycleStatus
from itta_api.domains.discovery.reconciliation_rule.schemas import *  # noqa: F401,F403


class ReconciliationRuleFilter(ApiModel):
    """Query-string filter for Reconciliation Rule search."""

    q: str | None = Field(None, description="Free text over code, name and description")
    status: list[LifecycleStatus] | None = None
    code: str | None = Field(None, description="Code contains")
    name: str | None = Field(None, description="Name contains")
    external_ref: str | None = Field(None, description="External Ref contains")
    rule_name: str | None = Field(None, description="Rule Name contains")
    match_key: str | None = Field(None, description="Match Key contains")
    action: list[ReconciliationRuleAction] | None = None
    priority_min: int | None = None
    priority_max: int | None = None
    labels: str | None = Field(None, description="Labels contains")
    lifecycle_stage: list[ReconciliationRuleLifecycleStage] | None = None
    source_system: str | None = Field(None, description="Source System contains")
    region: str | None = Field(None, description="Region contains")
    owner: str | None = Field(None, description="Owner contains")


def to_query(flt: ReconciliationRuleFilter) -> dict[str, str]:
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
    if flt.match_key:
        params["matchKey"] = flt.match_key.strip()
    if flt.action:
        params["action"] = ",".join(v.value for v in flt.action)
    if flt.priority_min is not None:
        params["priorityMin"] = str(flt.priority_min)
    if flt.priority_max is not None:
        params["priorityMax"] = str(flt.priority_max)
    if flt.labels:
        params["labels"] = flt.labels.strip()
    if flt.lifecycle_stage:
        params["lifecycleStage"] = ",".join(v.value for v in flt.lifecycle_stage)
    if flt.source_system:
        params["sourceSystem"] = flt.source_system.strip()
    if flt.region:
        params["region"] = flt.region.strip()
    if flt.owner:
        params["owner"] = flt.owner.strip()
    return params
