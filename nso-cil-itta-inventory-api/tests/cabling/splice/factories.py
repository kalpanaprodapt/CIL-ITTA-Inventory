"""Test data builders for Splice."""
from __future__ import annotations

from datetime import date, datetime, timezone
from decimal import Decimal
from uuid import UUID

from itta_core.models import LifecycleStatus
from itta_api.domains.cabling.splice.schemas import *  # noqa: F401,F403

ID = UUID("11111111-1111-1111-1111-111111111111")
TENANT = "tenant-a"


def create_payload(**overrides) -> SpliceCreate:
    data = dict(
        code="code-1",
        name="name-1",
        description="description-1",
        status=LifecycleStatus.PLANNED,
        external_ref="external_ref-1",
        enclosure_id="enclosure_id-1",
        a_strand_id=UUID("00000000-0000-0000-0000-000000000011"),
        z_strand_id=UUID("00000000-0000-0000-0000-000000000011"),
        loss_db=Decimal("13.50"),
        spliced_on=date(2026, 2, 11),
        lifecycle_stage=SpliceLifecycleStage.PLAN,
        source_system="source_system-1",
        region="region-1",
        owner="owner-1",
        criticality=SpliceCriticality.LOW,
    )
    data.update(code="SPLICE-0001", name="Splice one", status=LifecycleStatus.ACTIVE)
    data.update(overrides)
    return SpliceCreate(**data)


def read_model(**overrides) -> SpliceRead:
    base = create_payload().model_dump()
    base.update(id=ID, tenant_id=TENANT, version=0)
    base.update(overrides)
    return SpliceRead(**base)


def raw_record(**overrides) -> dict[str, object]:
    record = create_payload().model_dump(mode="json", by_alias=True)
    record.update(overrides)
    return record


def upstream_json(**overrides) -> dict[str, object]:
    record = read_model().model_dump(mode="json", by_alias=True)
    record.update(overrides)
    return record
