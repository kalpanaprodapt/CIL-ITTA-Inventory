"""Test data builders for Power Feed."""
from __future__ import annotations

from datetime import date, datetime, timezone
from decimal import Decimal
from uuid import UUID

from itta_core.models import LifecycleStatus
from itta_api.domains.facility.power_feed.schemas import *  # noqa: F401,F403

ID = UUID("11111111-1111-1111-1111-111111111111")
TENANT = "tenant-a"


def create_payload(**overrides) -> PowerFeedCreate:
    data = dict(
        code="code-1",
        name="name-1",
        description="description-1",
        status=LifecycleStatus.PLANNED,
        external_ref="external_ref-1",
        room_id=UUID("00000000-0000-0000-0000-000000000011"),
        feed_type=PowerFeedFeedType.AC,
        voltage=Decimal("13.50"),
        amperage=Decimal("13.50"),
        phase="phase-1",
        breaker_label="breaker_label-1",
        redundancy_group="redundancy_group-1",
        owner="owner-1",
        criticality=PowerFeedCriticality.LOW,
        cost_center="cost_center-1",
        notes="notes-1",
        effective_from=date(2026, 2, 11),
    )
    data.update(code="POWERF-0001", name="Power Feed one", status=LifecycleStatus.ACTIVE)
    data.update(overrides)
    return PowerFeedCreate(**data)


def read_model(**overrides) -> PowerFeedRead:
    base = create_payload().model_dump()
    base.update(id=ID, tenant_id=TENANT, version=0)
    base.update(overrides)
    return PowerFeedRead(**base)


def raw_record(**overrides) -> dict[str, object]:
    record = create_payload().model_dump(mode="json", by_alias=True)
    record.update(overrides)
    return record


def upstream_json(**overrides) -> dict[str, object]:
    record = read_model().model_dump(mode="json", by_alias=True)
    record.update(overrides)
    return record
