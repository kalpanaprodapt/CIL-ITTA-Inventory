"""Test data builders for Audit Record."""
from __future__ import annotations

from datetime import date, datetime, timezone
from decimal import Decimal
from uuid import UUID

from itta_core.models import LifecycleStatus
from itta_api.domains.compliance.audit_record.schemas import *  # noqa: F401,F403

ID = UUID("11111111-1111-1111-1111-111111111111")
TENANT = "tenant-a"


def create_payload(**overrides) -> AuditRecordCreate:
    data = dict(
        code="code-1",
        name="name-1",
        description="description-1",
        status=LifecycleStatus.PLANNED,
        external_ref="external_ref-1",
        actor="actor-1",
        action="action-1",
        entity_ref="entity_ref-1",
        occurred_at=datetime(2026, 2, 11, 10, 15, 30, tzinfo=timezone.utc),
        before_state="before_state-1",
        after_state="after_state-1",
        notes="notes-1",
        effective_from=date(2026, 2, 11),
        effective_to=date(2026, 2, 11),
        labels="labels-1",
        lifecycle_stage=AuditRecordLifecycleStage.PLAN,
    )
    data.update(code="AUDITR-0001", name="Audit Record one", status=LifecycleStatus.ACTIVE)
    data.update(overrides)
    return AuditRecordCreate(**data)


def read_model(**overrides) -> AuditRecordRead:
    base = create_payload().model_dump()
    base.update(id=ID, tenant_id=TENANT, version=0)
    base.update(overrides)
    return AuditRecordRead(**base)


def raw_record(**overrides) -> dict[str, object]:
    record = create_payload().model_dump(mode="json", by_alias=True)
    record.update(overrides)
    return record


def upstream_json(**overrides) -> dict[str, object]:
    record = read_model().model_dump(mode="json", by_alias=True)
    record.update(overrides)
    return record
