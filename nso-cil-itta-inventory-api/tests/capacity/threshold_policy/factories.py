"""Test data builders for Threshold Policy."""
from __future__ import annotations

from datetime import date, datetime, timezone
from decimal import Decimal
from uuid import UUID

from itta_core.models import LifecycleStatus
from itta_api.domains.capacity.threshold_policy.schemas import *  # noqa: F401,F403

ID = UUID("11111111-1111-1111-1111-111111111111")
TENANT = "tenant-a"


def create_payload(**overrides) -> ThresholdPolicyCreate:
    data = dict(
        code="code-1",
        name="name-1",
        description="description-1",
        status=LifecycleStatus.PLANNED,
        external_ref="external_ref-1",
        policy_name="policy_name-1",
        metric="metric-1",
        warn_pct=Decimal("13.50"),
        crit_pct=Decimal("13.50"),
        evaluation_window_minutes=11,
        region="region-1",
        owner="owner-1",
        criticality=ThresholdPolicyCriticality.LOW,
        cost_center="cost_center-1",
        notes="notes-1",
    )
    data.update(code="THRESH-0001", name="Threshold Policy one", status=LifecycleStatus.ACTIVE)
    data.update(overrides)
    return ThresholdPolicyCreate(**data)


def read_model(**overrides) -> ThresholdPolicyRead:
    base = create_payload().model_dump()
    base.update(id=ID, tenant_id=TENANT, version=0)
    base.update(overrides)
    return ThresholdPolicyRead(**base)


def raw_record(**overrides) -> dict[str, object]:
    record = create_payload().model_dump(mode="json", by_alias=True)
    record.update(overrides)
    return record


def upstream_json(**overrides) -> dict[str, object]:
    record = read_model().model_dump(mode="json", by_alias=True)
    record.update(overrides)
    return record
