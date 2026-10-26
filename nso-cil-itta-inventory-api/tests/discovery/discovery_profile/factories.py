"""Test data builders for Discovery Profile."""
from __future__ import annotations

from datetime import date, datetime, timezone
from decimal import Decimal
from uuid import UUID

from itta_core.models import LifecycleStatus
from itta_api.domains.discovery.discovery_profile.schemas import *  # noqa: F401,F403

ID = UUID("11111111-1111-1111-1111-111111111111")
TENANT = "tenant-a"


def create_payload(**overrides) -> DiscoveryProfileCreate:
    data = dict(
        code="code-1",
        name="name-1",
        description="description-1",
        status=LifecycleStatus.PLANNED,
        external_ref="external_ref-1",
        profile_name="profile_name-1",
        protocol=DiscoveryProfileProtocol.SNMP,
        credential_ref="credential_ref-1",
        scope_cidr="scope_cidr-1",
        schedule_cron="schedule_cron-1",
        cost_center="cost_center-1",
        notes="notes-1",
        effective_from=date(2026, 2, 11),
        effective_to=date(2026, 2, 11),
        labels="labels-1",
    )
    data.update(code="DISCOV-0001", name="Discovery Profile one", status=LifecycleStatus.ACTIVE)
    data.update(overrides)
    return DiscoveryProfileCreate(**data)


def read_model(**overrides) -> DiscoveryProfileRead:
    base = create_payload().model_dump()
    base.update(id=ID, tenant_id=TENANT, version=0)
    base.update(overrides)
    return DiscoveryProfileRead(**base)


def raw_record(**overrides) -> dict[str, object]:
    record = create_payload().model_dump(mode="json", by_alias=True)
    record.update(overrides)
    return record


def upstream_json(**overrides) -> dict[str, object]:
    record = read_model().model_dump(mode="json", by_alias=True)
    record.update(overrides)
    return record
