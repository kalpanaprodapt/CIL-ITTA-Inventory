"""Test data builders for Field Task."""
from __future__ import annotations

from datetime import date, datetime, timezone
from decimal import Decimal
from uuid import UUID

from itta_core.models import LifecycleStatus
from itta_api.domains.change.field_task.schemas import *  # noqa: F401,F403

ID = UUID("11111111-1111-1111-1111-111111111111")
TENANT = "tenant-a"


def create_payload(**overrides) -> FieldTaskCreate:
    data = dict(
        code="code-1",
        name="name-1",
        description="description-1",
        status=LifecycleStatus.PLANNED,
        external_ref="external_ref-1",
        work_order_id=UUID("00000000-0000-0000-0000-000000000011"),
        task_name="task_name-1",
        sequence=11,
        duration_minutes=11,
        completed_on=datetime(2026, 2, 11, 10, 15, 30, tzinfo=timezone.utc),
        region="region-1",
        owner="owner-1",
        criticality=FieldTaskCriticality.LOW,
        cost_center="cost_center-1",
        notes="notes-1",
    )
    data.update(code="FIELDT-0001", name="Field Task one", status=LifecycleStatus.ACTIVE)
    data.update(overrides)
    return FieldTaskCreate(**data)


def read_model(**overrides) -> FieldTaskRead:
    base = create_payload().model_dump()
    base.update(id=ID, tenant_id=TENANT, version=0)
    base.update(overrides)
    return FieldTaskRead(**base)


def raw_record(**overrides) -> dict[str, object]:
    record = create_payload().model_dump(mode="json", by_alias=True)
    record.update(overrides)
    return record


def upstream_json(**overrides) -> dict[str, object]:
    record = read_model().model_dump(mode="json", by_alias=True)
    record.update(overrides)
    return record
