"""Test data builders for Hypervisor."""
from __future__ import annotations

from datetime import date, datetime, timezone
from decimal import Decimal
from uuid import UUID

from itta_core.models import LifecycleStatus
from itta_api.domains.virtual.hypervisor.schemas import *  # noqa: F401,F403

ID = UUID("11111111-1111-1111-1111-111111111111")
TENANT = "tenant-a"


def create_payload(**overrides) -> HypervisorCreate:
    data = dict(
        code="code-1",
        name="name-1",
        description="description-1",
        status=LifecycleStatus.PLANNED,
        external_ref="external_ref-1",
        host_device_id=UUID("00000000-0000-0000-0000-000000000011"),
        hypervisor_type=HypervisorHypervisorType.KVM,
        hv_version="hv_version-1",
        vcpu_capacity=11,
        memory_gb_capacity=11,
        region="region-1",
        owner="owner-1",
        criticality=HypervisorCriticality.LOW,
        cost_center="cost_center-1",
        notes="notes-1",
    )
    data.update(code="HYPERV-0001", name="Hypervisor one", status=LifecycleStatus.ACTIVE)
    data.update(overrides)
    return HypervisorCreate(**data)


def read_model(**overrides) -> HypervisorRead:
    base = create_payload().model_dump()
    base.update(id=ID, tenant_id=TENANT, version=0)
    base.update(overrides)
    return HypervisorRead(**base)


def raw_record(**overrides) -> dict[str, object]:
    record = create_payload().model_dump(mode="json", by_alias=True)
    record.update(overrides)
    return record


def upstream_json(**overrides) -> dict[str, object]:
    record = read_model().model_dump(mode="json", by_alias=True)
    record.update(overrides)
    return record
