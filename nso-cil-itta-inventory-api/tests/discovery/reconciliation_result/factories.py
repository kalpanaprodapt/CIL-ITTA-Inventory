"""Test data builders for Reconciliation Result."""
from __future__ import annotations

from datetime import date, datetime, timezone
from decimal import Decimal
from uuid import UUID

from itta_core.models import LifecycleStatus
from itta_api.domains.discovery.reconciliation_result.schemas import *  # noqa: F401,F403

ID = UUID("11111111-1111-1111-1111-111111111111")
TENANT = "tenant-a"


def create_payload(**overrides) -> ReconciliationResultCreate:
    data = dict(
        code="code-1",
        name="name-1",
        description="description-1",
        status=LifecycleStatus.PLANNED,
        external_ref="external_ref-1",
        job_id=UUID("00000000-0000-0000-0000-000000000011"),
        rule_id=UUID("00000000-0000-0000-0000-000000000011"),
        outcome=ReconciliationResultOutcome.MATCHED,
        diff_summary="diff_summary-1",
        resolved_by="resolved_by-1",
        source_system="source_system-1",
        region="region-1",
        owner="owner-1",
        criticality=ReconciliationResultCriticality.LOW,
        cost_center="cost_center-1",
    )
    data.update(code="RECONC-0001", name="Reconciliation Result one", status=LifecycleStatus.ACTIVE)
    data.update(overrides)
    return ReconciliationResultCreate(**data)


def read_model(**overrides) -> ReconciliationResultRead:
    base = create_payload().model_dump()
    base.update(id=ID, tenant_id=TENANT, version=0)
    base.update(overrides)
    return ReconciliationResultRead(**base)


def raw_record(**overrides) -> dict[str, object]:
    record = create_payload().model_dump(mode="json", by_alias=True)
    record.update(overrides)
    return record


def upstream_json(**overrides) -> dict[str, object]:
    record = read_model().model_dump(mode="json", by_alias=True)
    record.update(overrides)
    return record
