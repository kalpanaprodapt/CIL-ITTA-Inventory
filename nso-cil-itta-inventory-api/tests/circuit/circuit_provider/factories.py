"""Test data builders for Circuit Provider."""
from __future__ import annotations

from datetime import date, datetime, timezone
from decimal import Decimal
from uuid import UUID

from itta_core.models import LifecycleStatus
from itta_api.domains.circuit.circuit_provider.schemas import *  # noqa: F401,F403

ID = UUID("11111111-1111-1111-1111-111111111111")
TENANT = "tenant-a"


def create_payload(**overrides) -> CircuitProviderCreate:
    data = dict(
        code="code-1",
        name="name-1",
        description="description-1",
        status=LifecycleStatus.PLANNED,
        external_ref="external_ref-1",
        provider_name="provider_name-1",
        account_number="account_number-1",
        noc="noc-1",
        escalation_email="escalation_email-1",
        provider_type=CircuitProviderProviderType.TIER1,
        owner="owner-1",
        criticality=CircuitProviderCriticality.LOW,
        cost_center="cost_center-1",
        notes="notes-1",
        effective_from=date(2026, 2, 11),
    )
    data.update(code="CIRCUI-0001", name="Circuit Provider one", status=LifecycleStatus.ACTIVE)
    data.update(overrides)
    return CircuitProviderCreate(**data)


def read_model(**overrides) -> CircuitProviderRead:
    base = create_payload().model_dump()
    base.update(id=ID, tenant_id=TENANT, version=0)
    base.update(overrides)
    return CircuitProviderRead(**base)


def raw_record(**overrides) -> dict[str, object]:
    record = create_payload().model_dump(mode="json", by_alias=True)
    record.update(overrides)
    return record


def upstream_json(**overrides) -> dict[str, object]:
    record = read_model().model_dump(mode="json", by_alias=True)
    record.update(overrides)
    return record
