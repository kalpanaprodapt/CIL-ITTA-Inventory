"""Test data builders for Vnf."""
from __future__ import annotations

from datetime import date, datetime, timezone
from decimal import Decimal
from uuid import UUID

from itta_core.models import LifecycleStatus
from itta_api.domains.virtual.vnf.schemas import *  # noqa: F401,F403

ID = UUID("11111111-1111-1111-1111-111111111111")
TENANT = "tenant-a"


def create_payload(**overrides) -> VnfCreate:
    data = dict(
        code="code-1",
        name="name-1",
        description="description-1",
        status=LifecycleStatus.PLANNED,
        external_ref="external_ref-1",
        vnf_name="vnf_name-1",
        vnf_type="vnf_type-1",
        flavor="flavor-1",
        vcpu=11,
        memory_gb=11,
        instance_count=11,
        lifecycle_stage=VnfLifecycleStage.PLAN,
        source_system="source_system-1",
        region="region-1",
        owner="owner-1",
        criticality=VnfCriticality.LOW,
    )
    data.update(code="VNF-0001", name="Vnf one", status=LifecycleStatus.ACTIVE)
    data.update(overrides)
    return VnfCreate(**data)


def read_model(**overrides) -> VnfRead:
    base = create_payload().model_dump()
    base.update(id=ID, tenant_id=TENANT, version=0)
    base.update(overrides)
    return VnfRead(**base)


def raw_record(**overrides) -> dict[str, object]:
    record = create_payload().model_dump(mode="json", by_alias=True)
    record.update(overrides)
    return record


def upstream_json(**overrides) -> dict[str, object]:
    record = read_model().model_dump(mode="json", by_alias=True)
    record.update(overrides)
    return record
