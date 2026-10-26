"""Test data builders for Sub Interface."""
from __future__ import annotations

from datetime import date, datetime, timezone
from decimal import Decimal
from uuid import UUID

from itta_core.models import LifecycleStatus
from itta_api.domains.port.sub_interface.schemas import *  # noqa: F401,F403

ID = UUID("11111111-1111-1111-1111-111111111111")
TENANT = "tenant-a"


def create_payload(**overrides) -> SubInterfaceCreate:
    data = dict(
        code="code-1",
        name="name-1",
        description="description-1",
        status=LifecycleStatus.PLANNED,
        external_ref="external_ref-1",
        interface_id=UUID("00000000-0000-0000-0000-000000000011"),
        vlan_tag=11,
        encapsulation="encapsulation-1",
        ipv4_address="ipv4_address-1",
        ipv6_address="ipv6_address-1",
        cost_center="cost_center-1",
        notes="notes-1",
        effective_from=date(2026, 2, 11),
        effective_to=date(2026, 2, 11),
        labels="labels-1",
    )
    data.update(code="SUBINT-0001", name="Sub Interface one", status=LifecycleStatus.ACTIVE)
    data.update(overrides)
    return SubInterfaceCreate(**data)


def read_model(**overrides) -> SubInterfaceRead:
    base = create_payload().model_dump()
    base.update(id=ID, tenant_id=TENANT, version=0)
    base.update(overrides)
    return SubInterfaceRead(**base)


def raw_record(**overrides) -> dict[str, object]:
    record = create_payload().model_dump(mode="json", by_alias=True)
    record.update(overrides)
    return record


def upstream_json(**overrides) -> dict[str, object]:
    record = read_model().model_dump(mode="json", by_alias=True)
    record.update(overrides)
    return record
