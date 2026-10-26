"""Test data builders for Port."""
from __future__ import annotations

from datetime import date, datetime, timezone
from decimal import Decimal
from uuid import UUID

from itta_core.models import LifecycleStatus
from itta_api.domains.port.port.schemas import *  # noqa: F401,F403

ID = UUID("11111111-1111-1111-1111-111111111111")
TENANT = "tenant-a"


def create_payload(**overrides) -> PortCreate:
    data = dict(
        code="code-1",
        name="name-1",
        description="description-1",
        status=LifecycleStatus.PLANNED,
        external_ref="external_ref-1",
        device_id=UUID("00000000-0000-0000-0000-000000000011"),
        card_id=UUID("00000000-0000-0000-0000-000000000011"),
        port_number="port_number-1",
        speed_mbps=1001,
        media_type=PortMediaType.COPPER,
        admin_state=PortAdminState.UP,
        source_system="source_system-1",
        region="region-1",
        owner="owner-1",
        criticality=PortCriticality.LOW,
        cost_center="cost_center-1",
    )
    data.update(code="PORT-0001", name="Port one", status=LifecycleStatus.ACTIVE)
    data.update(overrides)
    return PortCreate(**data)


def read_model(**overrides) -> PortRead:
    base = create_payload().model_dump()
    base.update(id=ID, tenant_id=TENANT, version=0)
    base.update(overrides)
    return PortRead(**base)


def raw_record(**overrides) -> dict[str, object]:
    record = create_payload().model_dump(mode="json", by_alias=True)
    record.update(overrides)
    return record


def upstream_json(**overrides) -> dict[str, object]:
    record = read_model().model_dump(mode="json", by_alias=True)
    record.update(overrides)
    return record
