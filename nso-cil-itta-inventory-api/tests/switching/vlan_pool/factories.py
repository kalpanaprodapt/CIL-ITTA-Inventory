"""Test data builders for Vlan Pool."""
from __future__ import annotations

from datetime import date, datetime, timezone
from decimal import Decimal
from uuid import UUID

from itta_core.models import LifecycleStatus
from itta_api.domains.switching.vlan_pool.schemas import *  # noqa: F401,F403

ID = UUID("11111111-1111-1111-1111-111111111111")
TENANT = "tenant-a"


def create_payload(**overrides) -> VlanPoolCreate:
    data = dict(
        code="code-1",
        name="name-1",
        description="description-1",
        status=LifecycleStatus.PLANNED,
        external_ref="external_ref-1",
        pool_name="pool_name-1",
        range_start=11,
        range_end=11,
        allocated=11,
        scope="scope-1",
        effective_to=date(2026, 2, 11),
        labels="labels-1",
        lifecycle_stage=VlanPoolLifecycleStage.PLAN,
        source_system="source_system-1",
        region="region-1",
    )
    data.update(code="VLANPO-0001", name="Vlan Pool one", status=LifecycleStatus.ACTIVE)
    data.update(overrides)
    return VlanPoolCreate(**data)


def read_model(**overrides) -> VlanPoolRead:
    base = create_payload().model_dump()
    base.update(id=ID, tenant_id=TENANT, version=0)
    base.update(overrides)
    return VlanPoolRead(**base)


def raw_record(**overrides) -> dict[str, object]:
    record = create_payload().model_dump(mode="json", by_alias=True)
    record.update(overrides)
    return record


def upstream_json(**overrides) -> dict[str, object]:
    record = read_model().model_dump(mode="json", by_alias=True)
    record.update(overrides)
    return record
