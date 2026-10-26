"""Test data builders for Alarm Instance."""
from __future__ import annotations

from datetime import date, datetime, timezone
from decimal import Decimal
from uuid import UUID

from itta_core.models import LifecycleStatus
from itta_api.domains.alarm.alarm_instance.schemas import *  # noqa: F401,F403

ID = UUID("11111111-1111-1111-1111-111111111111")
TENANT = "tenant-a"


def create_payload(**overrides) -> AlarmInstanceCreate:
    data = dict(
        code="code-1",
        name="name-1",
        description="description-1",
        status=LifecycleStatus.PLANNED,
        external_ref="external_ref-1",
        definition_id=UUID("00000000-0000-0000-0000-000000000011"),
        source_ref="source_ref-1",
        raised_at=datetime(2026, 2, 11, 10, 15, 30, tzinfo=timezone.utc),
        cleared_at=datetime(2026, 2, 11, 10, 15, 30, tzinfo=timezone.utc),
        acked_by="acked_by-1",
        alarm_state=AlarmInstanceAlarmState.ACTIVE,
        effective_from=date(2026, 2, 11),
        effective_to=date(2026, 2, 11),
        labels="labels-1",
        lifecycle_stage=AlarmInstanceLifecycleStage.PLAN,
        source_system="source_system-1",
    )
    data.update(code="ALARMI-0001", name="Alarm Instance one", status=LifecycleStatus.ACTIVE)
    data.update(overrides)
    return AlarmInstanceCreate(**data)


def read_model(**overrides) -> AlarmInstanceRead:
    base = create_payload().model_dump()
    base.update(id=ID, tenant_id=TENANT, version=0)
    base.update(overrides)
    return AlarmInstanceRead(**base)


def raw_record(**overrides) -> dict[str, object]:
    record = create_payload().model_dump(mode="json", by_alias=True)
    record.update(overrides)
    return record


def upstream_json(**overrides) -> dict[str, object]:
    record = read_model().model_dump(mode="json", by_alias=True)
    record.update(overrides)
    return record
