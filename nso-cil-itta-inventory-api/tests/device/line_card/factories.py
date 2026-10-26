"""Test data builders for Line Card."""
from __future__ import annotations

from datetime import date, datetime, timezone
from decimal import Decimal
from uuid import UUID

from itta_core.models import LifecycleStatus
from itta_api.domains.device.line_card.schemas import *  # noqa: F401,F403

ID = UUID("11111111-1111-1111-1111-111111111111")
TENANT = "tenant-a"


def create_payload(**overrides) -> LineCardCreate:
    data = dict(
        code="code-1",
        name="name-1",
        description="description-1",
        status=LifecycleStatus.PLANNED,
        external_ref="external_ref-1",
        chassis_id=UUID("00000000-0000-0000-0000-000000000011"),
        slot_number=11,
        card_type="card_type-1",
        port_count=11,
        serial_number="serial_number-1",
        firmware="firmware-1",
        region="region-1",
        owner="owner-1",
        criticality=LineCardCriticality.LOW,
        cost_center="cost_center-1",
        notes="notes-1",
    )
    data.update(code="LINECA-0001", name="Line Card one", status=LifecycleStatus.ACTIVE)
    data.update(overrides)
    return LineCardCreate(**data)


def read_model(**overrides) -> LineCardRead:
    base = create_payload().model_dump()
    base.update(id=ID, tenant_id=TENANT, version=0)
    base.update(overrides)
    return LineCardRead(**base)


def raw_record(**overrides) -> dict[str, object]:
    record = create_payload().model_dump(mode="json", by_alias=True)
    record.update(overrides)
    return record


def upstream_json(**overrides) -> dict[str, object]:
    record = read_model().model_dump(mode="json", by_alias=True)
    record.update(overrides)
    return record
