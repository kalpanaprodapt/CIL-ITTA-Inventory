"""Test data builders for Rack."""
from __future__ import annotations

from datetime import date, datetime, timezone
from decimal import Decimal
from uuid import UUID

from itta_core.models import LifecycleStatus
from itta_api.domains.facility.rack.schemas import *  # noqa: F401,F403

ID = UUID("11111111-1111-1111-1111-111111111111")
TENANT = "tenant-a"


def create_payload(**overrides) -> RackCreate:
    data = dict(
        code="code-1",
        name="name-1",
        description="description-1",
        status=LifecycleStatus.PLANNED,
        external_ref="external_ref-1",
        room_id=UUID("00000000-0000-0000-0000-000000000011"),
        rack_units=11,
        width_mm=11,
        depth_mm=11,
        max_weight_kg=Decimal("13.50"),
        row_label="row_label-1",
        position="position-1",
        labels="labels-1",
        lifecycle_stage=RackLifecycleStage.PLAN,
        source_system="source_system-1",
        region="region-1",
        owner="owner-1",
    )
    data.update(code="RACK-0001", name="Rack one", status=LifecycleStatus.ACTIVE)
    data.update(overrides)
    return RackCreate(**data)


def read_model(**overrides) -> RackRead:
    base = create_payload().model_dump()
    base.update(id=ID, tenant_id=TENANT, version=0)
    base.update(overrides)
    return RackRead(**base)


def raw_record(**overrides) -> dict[str, object]:
    record = create_payload().model_dump(mode="json", by_alias=True)
    record.update(overrides)
    return record


def upstream_json(**overrides) -> dict[str, object]:
    record = read_model().model_dump(mode="json", by_alias=True)
    record.update(overrides)
    return record
