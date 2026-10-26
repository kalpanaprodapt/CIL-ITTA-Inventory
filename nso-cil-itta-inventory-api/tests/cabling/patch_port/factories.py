"""Test data builders for Patch Port."""
from __future__ import annotations

from datetime import date, datetime, timezone
from decimal import Decimal
from uuid import UUID

from itta_core.models import LifecycleStatus
from itta_api.domains.cabling.patch_port.schemas import *  # noqa: F401,F403

ID = UUID("11111111-1111-1111-1111-111111111111")
TENANT = "tenant-a"


def create_payload(**overrides) -> PatchPortCreate:
    data = dict(
        code="code-1",
        name="name-1",
        description="description-1",
        status=LifecycleStatus.PLANNED,
        external_ref="external_ref-1",
        panel_id=UUID("00000000-0000-0000-0000-000000000011"),
        port_number=11,
        connected_cable_id=UUID("00000000-0000-0000-0000-000000000011"),
        polarity="polarity-1",
        effective_to=date(2026, 2, 11),
        labels="labels-1",
        lifecycle_stage=PatchPortLifecycleStage.PLAN,
        source_system="source_system-1",
        region="region-1",
    )
    data.update(code="PATCHP-0001", name="Patch Port one", status=LifecycleStatus.ACTIVE)
    data.update(overrides)
    return PatchPortCreate(**data)


def read_model(**overrides) -> PatchPortRead:
    base = create_payload().model_dump()
    base.update(id=ID, tenant_id=TENANT, version=0)
    base.update(overrides)
    return PatchPortRead(**base)


def raw_record(**overrides) -> dict[str, object]:
    record = create_payload().model_dump(mode="json", by_alias=True)
    record.update(overrides)
    return record


def upstream_json(**overrides) -> dict[str, object]:
    record = read_model().model_dump(mode="json", by_alias=True)
    record.update(overrides)
    return record
