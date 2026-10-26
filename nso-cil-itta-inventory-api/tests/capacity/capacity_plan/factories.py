"""Test data builders for Capacity Plan."""
from __future__ import annotations

from datetime import date, datetime, timezone
from decimal import Decimal
from uuid import UUID

from itta_core.models import LifecycleStatus
from itta_api.domains.capacity.capacity_plan.schemas import *  # noqa: F401,F403

ID = UUID("11111111-1111-1111-1111-111111111111")
TENANT = "tenant-a"


def create_payload(**overrides) -> CapacityPlanCreate:
    data = dict(
        code="code-1",
        name="name-1",
        description="description-1",
        status=LifecycleStatus.PLANNED,
        external_ref="external_ref-1",
        plan_name="plan_name-1",
        horizon_months=11,
        target_utilization_pct=Decimal("13.50"),
        owner="owner-1",
        approved_on=date(2026, 2, 11),
        effective_to=date(2026, 2, 11),
        labels="labels-1",
        lifecycle_stage=CapacityPlanLifecycleStage.PLAN,
        source_system="source_system-1",
        region="region-1",
    )
    data.update(code="CAPACI-0001", name="Capacity Plan one", status=LifecycleStatus.ACTIVE)
    data.update(overrides)
    return CapacityPlanCreate(**data)


def read_model(**overrides) -> CapacityPlanRead:
    base = create_payload().model_dump()
    base.update(id=ID, tenant_id=TENANT, version=0)
    base.update(overrides)
    return CapacityPlanRead(**base)


def raw_record(**overrides) -> dict[str, object]:
    record = create_payload().model_dump(mode="json", by_alias=True)
    record.update(overrides)
    return record


def upstream_json(**overrides) -> dict[str, object]:
    record = read_model().model_dump(mode="json", by_alias=True)
    record.update(overrides)
    return record
