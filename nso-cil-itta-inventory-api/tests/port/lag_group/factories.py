"""Test data builders for Lag Group."""
from __future__ import annotations

from datetime import date, datetime, timezone
from decimal import Decimal
from uuid import UUID

from itta_core.models import LifecycleStatus
from itta_api.domains.port.lag_group.schemas import *  # noqa: F401,F403

ID = UUID("11111111-1111-1111-1111-111111111111")
TENANT = "tenant-a"


def create_payload(**overrides) -> LagGroupCreate:
    data = dict(
        code="code-1",
        name="name-1",
        description="description-1",
        status=LifecycleStatus.PLANNED,
        external_ref="external_ref-1",
        device_id=UUID("00000000-0000-0000-0000-000000000011"),
        lag_name="lag_name-1",
        member_count=11,
        lacp_mode=LagGroupLacpMode.ACTIVE,
        min_links=11,
        effective_from=date(2026, 2, 11),
        effective_to=date(2026, 2, 11),
        labels="labels-1",
        lifecycle_stage=LagGroupLifecycleStage.PLAN,
        source_system="source_system-1",
    )
    data.update(code="LAGGRO-0001", name="Lag Group one", status=LifecycleStatus.ACTIVE)
    data.update(overrides)
    return LagGroupCreate(**data)


def read_model(**overrides) -> LagGroupRead:
    base = create_payload().model_dump()
    base.update(id=ID, tenant_id=TENANT, version=0)
    base.update(overrides)
    return LagGroupRead(**base)


def raw_record(**overrides) -> dict[str, object]:
    record = create_payload().model_dump(mode="json", by_alias=True)
    record.update(overrides)
    return record


def upstream_json(**overrides) -> dict[str, object]:
    record = read_model().model_dump(mode="json", by_alias=True)
    record.update(overrides)
    return record
