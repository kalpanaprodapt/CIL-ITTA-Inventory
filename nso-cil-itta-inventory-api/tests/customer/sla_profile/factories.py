"""Test data builders for Sla Profile."""
from __future__ import annotations

from datetime import date, datetime, timezone
from decimal import Decimal
from uuid import UUID

from itta_core.models import LifecycleStatus
from itta_api.domains.customer.sla_profile.schemas import *  # noqa: F401,F403

ID = UUID("11111111-1111-1111-1111-111111111111")
TENANT = "tenant-a"


def create_payload(**overrides) -> SlaProfileCreate:
    data = dict(
        code="code-1",
        name="name-1",
        description="description-1",
        status=LifecycleStatus.PLANNED,
        external_ref="external_ref-1",
        profile_name="profile_name-1",
        availability_pct=Decimal("13.50"),
        mttr_minutes=11,
        latency_ms=Decimal("13.50"),
        jitter_ms=Decimal("13.50"),
        credit_policy="credit_policy-1",
        owner="owner-1",
        criticality=SlaProfileCriticality.LOW,
        cost_center="cost_center-1",
        notes="notes-1",
        effective_from=date(2026, 2, 11),
    )
    data.update(code="SLAPRO-0001", name="Sla Profile one", status=LifecycleStatus.ACTIVE)
    data.update(overrides)
    return SlaProfileCreate(**data)


def read_model(**overrides) -> SlaProfileRead:
    base = create_payload().model_dump()
    base.update(id=ID, tenant_id=TENANT, version=0)
    base.update(overrides)
    return SlaProfileRead(**base)


def raw_record(**overrides) -> dict[str, object]:
    record = create_payload().model_dump(mode="json", by_alias=True)
    record.update(overrides)
    return record


def upstream_json(**overrides) -> dict[str, object]:
    record = read_model().model_dump(mode="json", by_alias=True)
    record.update(overrides)
    return record
