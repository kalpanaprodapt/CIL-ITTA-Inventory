"""Test data builders for Hardware Model."""
from __future__ import annotations

from datetime import date, datetime, timezone
from decimal import Decimal
from uuid import UUID

from itta_core.models import LifecycleStatus
from itta_api.domains.vendor.hardware_model.schemas import *  # noqa: F401,F403

ID = UUID("11111111-1111-1111-1111-111111111111")
TENANT = "tenant-a"


def create_payload(**overrides) -> HardwareModelCreate:
    data = dict(
        code="code-1",
        name="name-1",
        description="description-1",
        status=LifecycleStatus.PLANNED,
        external_ref="external_ref-1",
        vendor_id=UUID("00000000-0000-0000-0000-000000000011"),
        model_number="model_number-1",
        form_factor="form_factor-1",
        power_watts=11,
        eol_date=date(2026, 2, 11),
        eos_date=date(2026, 2, 11),
        labels="labels-1",
        lifecycle_stage=HardwareModelLifecycleStage.PLAN,
        source_system="source_system-1",
        region="region-1",
        owner="owner-1",
    )
    data.update(code="HARDWA-0001", name="Hardware Model one", status=LifecycleStatus.ACTIVE)
    data.update(overrides)
    return HardwareModelCreate(**data)


def read_model(**overrides) -> HardwareModelRead:
    base = create_payload().model_dump()
    base.update(id=ID, tenant_id=TENANT, version=0)
    base.update(overrides)
    return HardwareModelRead(**base)


def raw_record(**overrides) -> dict[str, object]:
    record = create_payload().model_dump(mode="json", by_alias=True)
    record.update(overrides)
    return record


def upstream_json(**overrides) -> dict[str, object]:
    record = read_model().model_dump(mode="json", by_alias=True)
    record.update(overrides)
    return record
