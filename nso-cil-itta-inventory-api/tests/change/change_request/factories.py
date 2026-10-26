"""Test data builders for Change Request."""
from __future__ import annotations

from datetime import date, datetime, timezone
from decimal import Decimal
from uuid import UUID

from itta_core.models import LifecycleStatus
from itta_api.domains.change.change_request.schemas import *  # noqa: F401,F403

ID = UUID("11111111-1111-1111-1111-111111111111")
TENANT = "tenant-a"


def create_payload(**overrides) -> ChangeRequestCreate:
    data = dict(
        code="code-1",
        name="name-1",
        description="description-1",
        status=LifecycleStatus.PLANNED,
        external_ref="external_ref-1",
        change_number="change_number-1",
        change_type=ChangeRequestChangeType.STANDARD,
        risk_level=ChangeRequestRiskLevel.LOW,
        planned_start=datetime(2026, 2, 11, 10, 15, 30, tzinfo=timezone.utc),
        planned_end=datetime(2026, 2, 11, 10, 15, 30, tzinfo=timezone.utc),
        notes="notes-1",
        effective_from=date(2026, 2, 11),
        effective_to=date(2026, 2, 11),
        labels="labels-1",
        lifecycle_stage=ChangeRequestLifecycleStage.PLAN,
    )
    data.update(code="CHANGE-0001", name="Change Request one", status=LifecycleStatus.ACTIVE)
    data.update(overrides)
    return ChangeRequestCreate(**data)


def read_model(**overrides) -> ChangeRequestRead:
    base = create_payload().model_dump()
    base.update(id=ID, tenant_id=TENANT, version=0)
    base.update(overrides)
    return ChangeRequestRead(**base)


def raw_record(**overrides) -> dict[str, object]:
    record = create_payload().model_dump(mode="json", by_alias=True)
    record.update(overrides)
    return record


def upstream_json(**overrides) -> dict[str, object]:
    record = read_model().model_dump(mode="json", by_alias=True)
    record.update(overrides)
    return record
