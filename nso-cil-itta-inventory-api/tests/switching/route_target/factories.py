"""Test data builders for Route Target."""
from __future__ import annotations

from datetime import date, datetime, timezone
from decimal import Decimal
from uuid import UUID

from itta_core.models import LifecycleStatus
from itta_api.domains.switching.route_target.schemas import *  # noqa: F401,F403

ID = UUID("11111111-1111-1111-1111-111111111111")
TENANT = "tenant-a"


def create_payload(**overrides) -> RouteTargetCreate:
    data = dict(
        code="code-1",
        name="name-1",
        description="description-1",
        status=LifecycleStatus.PLANNED,
        external_ref="external_ref-1",
        asn=1001,
        assigned_number=1001,
        rt_type=RouteTargetRtType.IMPORT,
        vrf_id=UUID("00000000-0000-0000-0000-000000000011"),
        lifecycle_stage=RouteTargetLifecycleStage.PLAN,
        source_system="source_system-1",
        region="region-1",
        owner="owner-1",
        criticality=RouteTargetCriticality.LOW,
    )
    data.update(code="ROUTET-0001", name="Route Target one", status=LifecycleStatus.ACTIVE)
    data.update(overrides)
    return RouteTargetCreate(**data)


def read_model(**overrides) -> RouteTargetRead:
    base = create_payload().model_dump()
    base.update(id=ID, tenant_id=TENANT, version=0)
    base.update(overrides)
    return RouteTargetRead(**base)


def raw_record(**overrides) -> dict[str, object]:
    record = create_payload().model_dump(mode="json", by_alias=True)
    record.update(overrides)
    return record


def upstream_json(**overrides) -> dict[str, object]:
    record = read_model().model_dump(mode="json", by_alias=True)
    record.update(overrides)
    return record
