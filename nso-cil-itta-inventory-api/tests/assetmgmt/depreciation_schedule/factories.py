"""Test data builders for Depreciation Schedule."""
from __future__ import annotations

from datetime import date, datetime, timezone
from decimal import Decimal
from uuid import UUID

from itta_core.models import LifecycleStatus
from itta_api.domains.assetmgmt.depreciation_schedule.schemas import *  # noqa: F401,F403

ID = UUID("11111111-1111-1111-1111-111111111111")
TENANT = "tenant-a"


def create_payload(**overrides) -> DepreciationScheduleCreate:
    data = dict(
        code="code-1",
        name="name-1",
        description="description-1",
        status=LifecycleStatus.PLANNED,
        external_ref="external_ref-1",
        asset_id=UUID("00000000-0000-0000-0000-000000000011"),
        method=DepreciationScheduleMethod.STRAIGHT_LINE,
        useful_life_months=11,
        salvage_value=Decimal("13.50"),
        start_date=date(2026, 2, 11),
        effective_from=date(2026, 2, 11),
        effective_to=date(2026, 2, 11),
        labels="labels-1",
        lifecycle_stage=DepreciationScheduleLifecycleStage.PLAN,
        source_system="source_system-1",
    )
    data.update(code="DEPREC-0001", name="Depreciation Schedule one", status=LifecycleStatus.ACTIVE)
    data.update(overrides)
    return DepreciationScheduleCreate(**data)


def read_model(**overrides) -> DepreciationScheduleRead:
    base = create_payload().model_dump()
    base.update(id=ID, tenant_id=TENANT, version=0)
    base.update(overrides)
    return DepreciationScheduleRead(**base)


def raw_record(**overrides) -> dict[str, object]:
    record = create_payload().model_dump(mode="json", by_alias=True)
    record.update(overrides)
    return record


def upstream_json(**overrides) -> dict[str, object]:
    record = read_model().model_dump(mode="json", by_alias=True)
    record.update(overrides)
    return record
