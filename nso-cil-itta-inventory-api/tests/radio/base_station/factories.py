"""Test data builders for Base Station."""
from __future__ import annotations

from datetime import date, datetime, timezone
from decimal import Decimal
from uuid import UUID

from itta_core.models import LifecycleStatus
from itta_api.domains.radio.base_station.schemas import *  # noqa: F401,F403

ID = UUID("11111111-1111-1111-1111-111111111111")
TENANT = "tenant-a"


def create_payload(**overrides) -> BaseStationCreate:
    data = dict(
        code="code-1",
        name="name-1",
        description="description-1",
        status=LifecycleStatus.PLANNED,
        external_ref="external_ref-1",
        site_id=UUID("00000000-0000-0000-0000-000000000011"),
        enb_id=1001,
        technology=BaseStationTechnology.LTE,
        sector_count=11,
        cell_count=11,
        labels="labels-1",
        lifecycle_stage=BaseStationLifecycleStage.PLAN,
        source_system="source_system-1",
        region="region-1",
        owner="owner-1",
    )
    data.update(code="BASEST-0001", name="Base Station one", status=LifecycleStatus.ACTIVE)
    data.update(overrides)
    return BaseStationCreate(**data)


def read_model(**overrides) -> BaseStationRead:
    base = create_payload().model_dump()
    base.update(id=ID, tenant_id=TENANT, version=0)
    base.update(overrides)
    return BaseStationRead(**base)


def raw_record(**overrides) -> dict[str, object]:
    record = create_payload().model_dump(mode="json", by_alias=True)
    record.update(overrides)
    return record


def upstream_json(**overrides) -> dict[str, object]:
    record = read_model().model_dump(mode="json", by_alias=True)
    record.update(overrides)
    return record
