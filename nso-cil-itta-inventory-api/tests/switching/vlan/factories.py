"""Test data builders for Vlan."""
from __future__ import annotations

from datetime import date, datetime, timezone
from decimal import Decimal
from uuid import UUID

from itta_core.models import LifecycleStatus
from itta_api.domains.switching.vlan.schemas import *  # noqa: F401,F403

ID = UUID("11111111-1111-1111-1111-111111111111")
TENANT = "tenant-a"


def create_payload(**overrides) -> VlanCreate:
    data = dict(
        code="code-1",
        name="name-1",
        description="description-1",
        status=LifecycleStatus.PLANNED,
        external_ref="external_ref-1",
        vlan_id=11,
        vlan_name="vlan_name-1",
        vlan_type=VlanVlanType.DATA,
        pool_id=UUID("00000000-0000-0000-0000-000000000011"),
        stp_mode="stp_mode-1",
        notes="notes-1",
        effective_from=date(2026, 2, 11),
        effective_to=date(2026, 2, 11),
        labels="labels-1",
        lifecycle_stage=VlanLifecycleStage.PLAN,
    )
    data.update(code="VLAN-0001", name="Vlan one", status=LifecycleStatus.ACTIVE)
    data.update(overrides)
    return VlanCreate(**data)


def read_model(**overrides) -> VlanRead:
    base = create_payload().model_dump()
    base.update(id=ID, tenant_id=TENANT, version=0)
    base.update(overrides)
    return VlanRead(**base)


def raw_record(**overrides) -> dict[str, object]:
    record = create_payload().model_dump(mode="json", by_alias=True)
    record.update(overrides)
    return record


def upstream_json(**overrides) -> dict[str, object]:
    record = read_model().model_dump(mode="json", by_alias=True)
    record.update(overrides)
    return record
