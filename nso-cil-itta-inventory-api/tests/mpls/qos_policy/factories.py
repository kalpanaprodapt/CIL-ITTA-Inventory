"""Test data builders for Qos Policy."""
from __future__ import annotations

from datetime import date, datetime, timezone
from decimal import Decimal
from uuid import UUID

from itta_core.models import LifecycleStatus
from itta_api.domains.mpls.qos_policy.schemas import *  # noqa: F401,F403

ID = UUID("11111111-1111-1111-1111-111111111111")
TENANT = "tenant-a"


def create_payload(**overrides) -> QosPolicyCreate:
    data = dict(
        code="code-1",
        name="name-1",
        description="description-1",
        status=LifecycleStatus.PLANNED,
        external_ref="external_ref-1",
        policy_name="policy_name-1",
        class_count=11,
        cir_mbps=1001,
        pir_mbps=1001,
        marking_scheme="marking_scheme-1",
        labels="labels-1",
        lifecycle_stage=QosPolicyLifecycleStage.PLAN,
        source_system="source_system-1",
        region="region-1",
        owner="owner-1",
    )
    data.update(code="QOSPOL-0001", name="Qos Policy one", status=LifecycleStatus.ACTIVE)
    data.update(overrides)
    return QosPolicyCreate(**data)


def read_model(**overrides) -> QosPolicyRead:
    base = create_payload().model_dump()
    base.update(id=ID, tenant_id=TENANT, version=0)
    base.update(overrides)
    return QosPolicyRead(**base)


def raw_record(**overrides) -> dict[str, object]:
    record = create_payload().model_dump(mode="json", by_alias=True)
    record.update(overrides)
    return record


def upstream_json(**overrides) -> dict[str, object]:
    record = read_model().model_dump(mode="json", by_alias=True)
    record.update(overrides)
    return record
