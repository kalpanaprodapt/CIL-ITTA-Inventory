"""Test data builders for Vrf Instance."""
from __future__ import annotations

from datetime import date, datetime, timezone
from decimal import Decimal
from uuid import UUID

from itta_core.models import LifecycleStatus
from itta_api.domains.ipam.vrf_instance.schemas import *  # noqa: F401,F403

ID = UUID("11111111-1111-1111-1111-111111111111")
TENANT = "tenant-a"


def create_payload(**overrides) -> VrfInstanceCreate:
    data = dict(
        code="code-1",
        name="name-1",
        description="description-1",
        status=LifecycleStatus.PLANNED,
        external_ref="external_ref-1",
        vrf_name="vrf_name-1",
        route_distinguisher="route_distinguisher-1",
        import_targets="import_targets-1",
        export_targets="export_targets-1",
        device_id=UUID("00000000-0000-0000-0000-000000000011"),
        labels="labels-1",
        lifecycle_stage=VrfInstanceLifecycleStage.PLAN,
        source_system="source_system-1",
        region="region-1",
        owner="owner-1",
    )
    data.update(code="VRFINS-0001", name="Vrf Instance one", status=LifecycleStatus.ACTIVE)
    data.update(overrides)
    return VrfInstanceCreate(**data)


def read_model(**overrides) -> VrfInstanceRead:
    base = create_payload().model_dump()
    base.update(id=ID, tenant_id=TENANT, version=0)
    base.update(overrides)
    return VrfInstanceRead(**base)


def raw_record(**overrides) -> dict[str, object]:
    record = create_payload().model_dump(mode="json", by_alias=True)
    record.update(overrides)
    return record


def upstream_json(**overrides) -> dict[str, object]:
    record = read_model().model_dump(mode="json", by_alias=True)
    record.update(overrides)
    return record
