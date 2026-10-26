"""Test data builders for Maintenance Window."""
from __future__ import annotations

from datetime import date, datetime, timezone
from decimal import Decimal
from uuid import UUID

from itta_core.models import LifecycleStatus
from itta_api.domains.change.maintenance_window.schemas import *  # noqa: F401,F403

ID = UUID("11111111-1111-1111-1111-111111111111")
TENANT = "tenant-a"


def create_payload(**overrides) -> MaintenanceWindowCreate:
    data = dict(
        code="code-1",
        name="name-1",
        description="description-1",
        status=LifecycleStatus.PLANNED,
        external_ref="external_ref-1",
        window_name="window_name-1",
        starts_at=datetime(2026, 2, 11, 10, 15, 30, tzinfo=timezone.utc),
        ends_at=datetime(2026, 2, 11, 10, 15, 30, tzinfo=timezone.utc),
        scope="scope-1",
        impact_level=MaintenanceWindowImpactLevel.NONE,
        effective_to=date(2026, 2, 11),
        labels="labels-1",
        lifecycle_stage=MaintenanceWindowLifecycleStage.PLAN,
        source_system="source_system-1",
        region="region-1",
    )
    data.update(code="MAINTE-0001", name="Maintenance Window one", status=LifecycleStatus.ACTIVE)
    data.update(overrides)
    return MaintenanceWindowCreate(**data)


def read_model(**overrides) -> MaintenanceWindowRead:
    base = create_payload().model_dump()
    base.update(id=ID, tenant_id=TENANT, version=0)
    base.update(overrides)
    return MaintenanceWindowRead(**base)


def raw_record(**overrides) -> dict[str, object]:
    record = create_payload().model_dump(mode="json", by_alias=True)
    record.update(overrides)
    return record


def upstream_json(**overrides) -> dict[str, object]:
    record = read_model().model_dump(mode="json", by_alias=True)
    record.update(overrides)
    return record
