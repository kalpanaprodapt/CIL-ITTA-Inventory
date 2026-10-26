"""Test data builders for Chassis."""
from __future__ import annotations

from datetime import date, datetime, timezone
from decimal import Decimal
from uuid import UUID

from itta_core.models import LifecycleStatus
from itta_api.domains.device.chassis.schemas import *  # noqa: F401,F403

ID = UUID("11111111-1111-1111-1111-111111111111")
TENANT = "tenant-a"


def create_payload(**overrides) -> ChassisCreate:
    data = dict(
        code="code-1",
        name="name-1",
        description="description-1",
        status=LifecycleStatus.PLANNED,
        external_ref="external_ref-1",
        device_id=UUID("00000000-0000-0000-0000-000000000011"),
        slot_count=11,
        serial_number="serial_number-1",
        backplane_gbps=Decimal("13.50"),
        redundancy_mode="redundancy_mode-1",
        lifecycle_stage=ChassisLifecycleStage.PLAN,
        source_system="source_system-1",
        region="region-1",
        owner="owner-1",
        criticality=ChassisCriticality.LOW,
    )
    data.update(code="CHASSI-0001", name="Chassis one", status=LifecycleStatus.ACTIVE)
    data.update(overrides)
    return ChassisCreate(**data)


def read_model(**overrides) -> ChassisRead:
    base = create_payload().model_dump()
    base.update(id=ID, tenant_id=TENANT, version=0)
    base.update(overrides)
    return ChassisRead(**base)


def raw_record(**overrides) -> dict[str, object]:
    record = create_payload().model_dump(mode="json", by_alias=True)
    record.update(overrides)
    return record


def upstream_json(**overrides) -> dict[str, object]:
    record = read_model().model_dump(mode="json", by_alias=True)
    record.update(overrides)
    return record
