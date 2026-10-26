"""Test data builders for Path Trace."""
from __future__ import annotations

from datetime import date, datetime, timezone
from decimal import Decimal
from uuid import UUID

from itta_core.models import LifecycleStatus
from itta_api.domains.topology.path_trace.schemas import *  # noqa: F401,F403

ID = UUID("11111111-1111-1111-1111-111111111111")
TENANT = "tenant-a"


def create_payload(**overrides) -> PathTraceCreate:
    data = dict(
        code="code-1",
        name="name-1",
        description="description-1",
        status=LifecycleStatus.PLANNED,
        external_ref="external_ref-1",
        source_ref="source_ref-1",
        target_ref="target_ref-1",
        hop_count=11,
        total_latency_ms=Decimal("13.50"),
        trace_method="trace_method-1",
        traced_at=datetime(2026, 2, 11, 10, 15, 30, tzinfo=timezone.utc),
        lifecycle_stage=PathTraceLifecycleStage.PLAN,
        source_system="source_system-1",
        region="region-1",
        owner="owner-1",
        criticality=PathTraceCriticality.LOW,
    )
    data.update(code="PATHTR-0001", name="Path Trace one", status=LifecycleStatus.ACTIVE)
    data.update(overrides)
    return PathTraceCreate(**data)


def read_model(**overrides) -> PathTraceRead:
    base = create_payload().model_dump()
    base.update(id=ID, tenant_id=TENANT, version=0)
    base.update(overrides)
    return PathTraceRead(**base)


def raw_record(**overrides) -> dict[str, object]:
    record = create_payload().model_dump(mode="json", by_alias=True)
    record.update(overrides)
    return record


def upstream_json(**overrides) -> dict[str, object]:
    record = read_model().model_dump(mode="json", by_alias=True)
    record.update(overrides)
    return record
