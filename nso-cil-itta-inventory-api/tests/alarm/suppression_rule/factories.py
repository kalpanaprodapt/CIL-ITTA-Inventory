"""Test data builders for Suppression Rule."""
from __future__ import annotations

from datetime import date, datetime, timezone
from decimal import Decimal
from uuid import UUID

from itta_core.models import LifecycleStatus
from itta_api.domains.alarm.suppression_rule.schemas import *  # noqa: F401,F403

ID = UUID("11111111-1111-1111-1111-111111111111")
TENANT = "tenant-a"


def create_payload(**overrides) -> SuppressionRuleCreate:
    data = dict(
        code="code-1",
        name="name-1",
        description="description-1",
        status=LifecycleStatus.PLANNED,
        external_ref="external_ref-1",
        rule_name="rule_name-1",
        match_expression="match_expression-1",
        starts_at=datetime(2026, 2, 11, 10, 15, 30, tzinfo=timezone.utc),
        ends_at=datetime(2026, 2, 11, 10, 15, 30, tzinfo=timezone.utc),
        reason="reason-1",
        source_system="source_system-1",
        region="region-1",
        owner="owner-1",
        criticality=SuppressionRuleCriticality.LOW,
        cost_center="cost_center-1",
    )
    data.update(code="SUPPRE-0001", name="Suppression Rule one", status=LifecycleStatus.ACTIVE)
    data.update(overrides)
    return SuppressionRuleCreate(**data)


def read_model(**overrides) -> SuppressionRuleRead:
    base = create_payload().model_dump()
    base.update(id=ID, tenant_id=TENANT, version=0)
    base.update(overrides)
    return SuppressionRuleRead(**base)


def raw_record(**overrides) -> dict[str, object]:
    record = create_payload().model_dump(mode="json", by_alias=True)
    record.update(overrides)
    return record


def upstream_json(**overrides) -> dict[str, object]:
    record = read_model().model_dump(mode="json", by_alias=True)
    record.update(overrides)
    return record
