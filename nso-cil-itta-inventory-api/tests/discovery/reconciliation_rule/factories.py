"""Test data builders for Reconciliation Rule."""
from __future__ import annotations

from datetime import date, datetime, timezone
from decimal import Decimal
from uuid import UUID

from itta_core.models import LifecycleStatus
from itta_api.domains.discovery.reconciliation_rule.schemas import *  # noqa: F401,F403

ID = UUID("11111111-1111-1111-1111-111111111111")
TENANT = "tenant-a"


def create_payload(**overrides) -> ReconciliationRuleCreate:
    data = dict(
        code="code-1",
        name="name-1",
        description="description-1",
        status=LifecycleStatus.PLANNED,
        external_ref="external_ref-1",
        rule_name="rule_name-1",
        match_key="match_key-1",
        action=ReconciliationRuleAction.MERGE,
        priority=11,
        expression="expression-1",
        labels="labels-1",
        lifecycle_stage=ReconciliationRuleLifecycleStage.PLAN,
        source_system="source_system-1",
        region="region-1",
        owner="owner-1",
    )
    data.update(code="RECONC-0001", name="Reconciliation Rule one", status=LifecycleStatus.ACTIVE)
    data.update(overrides)
    return ReconciliationRuleCreate(**data)


def read_model(**overrides) -> ReconciliationRuleRead:
    base = create_payload().model_dump()
    base.update(id=ID, tenant_id=TENANT, version=0)
    base.update(overrides)
    return ReconciliationRuleRead(**base)


def raw_record(**overrides) -> dict[str, object]:
    record = create_payload().model_dump(mode="json", by_alias=True)
    record.update(overrides)
    return record


def upstream_json(**overrides) -> dict[str, object]:
    record = read_model().model_dump(mode="json", by_alias=True)
    record.update(overrides)
    return record
