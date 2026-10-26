"""Test data builders for Discovery Job."""
from __future__ import annotations

from datetime import date, datetime, timezone
from decimal import Decimal
from uuid import UUID

from itta_core.models import LifecycleStatus
from itta_api.domains.discovery.discovery_job.schemas import *  # noqa: F401,F403

ID = UUID("11111111-1111-1111-1111-111111111111")
TENANT = "tenant-a"


def create_payload(**overrides) -> DiscoveryJobCreate:
    data = dict(
        code="code-1",
        name="name-1",
        description="description-1",
        status=LifecycleStatus.PLANNED,
        external_ref="external_ref-1",
        profile_id=UUID("00000000-0000-0000-0000-000000000011"),
        started_at=datetime(2026, 2, 11, 10, 15, 30, tzinfo=timezone.utc),
        finished_at=datetime(2026, 2, 11, 10, 15, 30, tzinfo=timezone.utc),
        job_state=DiscoveryJobJobState.QUEUED,
        assets_found=11,
        owner="owner-1",
        criticality=DiscoveryJobCriticality.LOW,
        cost_center="cost_center-1",
        notes="notes-1",
        effective_from=date(2026, 2, 11),
    )
    data.update(code="DISCOV-0001", name="Discovery Job one", status=LifecycleStatus.ACTIVE)
    data.update(overrides)
    return DiscoveryJobCreate(**data)


def read_model(**overrides) -> DiscoveryJobRead:
    base = create_payload().model_dump()
    base.update(id=ID, tenant_id=TENANT, version=0)
    base.update(overrides)
    return DiscoveryJobRead(**base)


def raw_record(**overrides) -> dict[str, object]:
    record = create_payload().model_dump(mode="json", by_alias=True)
    record.update(overrides)
    return record


def upstream_json(**overrides) -> dict[str, object]:
    record = read_model().model_dump(mode="json", by_alias=True)
    record.update(overrides)
    return record
