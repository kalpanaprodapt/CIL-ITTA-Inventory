"""Test data builders for Otn Link."""
from __future__ import annotations

from datetime import date, datetime, timezone
from decimal import Decimal
from uuid import UUID

from itta_core.models import LifecycleStatus
from itta_api.domains.optical.otn_link.schemas import *  # noqa: F401,F403

ID = UUID("11111111-1111-1111-1111-111111111111")
TENANT = "tenant-a"


def create_payload(**overrides) -> OtnLinkCreate:
    data = dict(
        code="code-1",
        name="name-1",
        description="description-1",
        status=LifecycleStatus.PLANNED,
        external_ref="external_ref-1",
        a_node_id=UUID("00000000-0000-0000-0000-000000000011"),
        z_node_id=UUID("00000000-0000-0000-0000-000000000011"),
        odu=OtnLinkOdu.ODU0,
        latency_us=Decimal("13.50"),
        protection="protection-1",
        effective_to=date(2026, 2, 11),
        labels="labels-1",
        lifecycle_stage=OtnLinkLifecycleStage.PLAN,
        source_system="source_system-1",
        region="region-1",
    )
    data.update(code="OTNLIN-0001", name="Otn Link one", status=LifecycleStatus.ACTIVE)
    data.update(overrides)
    return OtnLinkCreate(**data)


def read_model(**overrides) -> OtnLinkRead:
    base = create_payload().model_dump()
    base.update(id=ID, tenant_id=TENANT, version=0)
    base.update(overrides)
    return OtnLinkRead(**base)


def raw_record(**overrides) -> dict[str, object]:
    record = create_payload().model_dump(mode="json", by_alias=True)
    record.update(overrides)
    return record


def upstream_json(**overrides) -> dict[str, object]:
    record = read_model().model_dump(mode="json", by_alias=True)
    record.update(overrides)
    return record
