"""Test data builders for Discovered Asset."""
from __future__ import annotations

from datetime import date, datetime, timezone
from decimal import Decimal
from uuid import UUID

from itta_core.models import LifecycleStatus
from itta_api.domains.discovery.discovered_asset.schemas import *  # noqa: F401,F403

ID = UUID("11111111-1111-1111-1111-111111111111")
TENANT = "tenant-a"


def create_payload(**overrides) -> DiscoveredAssetCreate:
    data = dict(
        code="code-1",
        name="name-1",
        description="description-1",
        status=LifecycleStatus.PLANNED,
        external_ref="external_ref-1",
        job_id=UUID("00000000-0000-0000-0000-000000000011"),
        asset_type="asset_type-1",
        fingerprint="fingerprint-1",
        raw_payload="raw_payload-1",
        matched_asset_id=UUID("00000000-0000-0000-0000-000000000011"),
        effective_from=date(2026, 2, 11),
        effective_to=date(2026, 2, 11),
        labels="labels-1",
        lifecycle_stage=DiscoveredAssetLifecycleStage.PLAN,
        source_system="source_system-1",
    )
    data.update(code="DISCOV-0001", name="Discovered Asset one", status=LifecycleStatus.ACTIVE)
    data.update(overrides)
    return DiscoveredAssetCreate(**data)


def read_model(**overrides) -> DiscoveredAssetRead:
    base = create_payload().model_dump()
    base.update(id=ID, tenant_id=TENANT, version=0)
    base.update(overrides)
    return DiscoveredAssetRead(**base)


def raw_record(**overrides) -> dict[str, object]:
    record = create_payload().model_dump(mode="json", by_alias=True)
    record.update(overrides)
    return record


def upstream_json(**overrides) -> dict[str, object]:
    record = read_model().model_dump(mode="json", by_alias=True)
    record.update(overrides)
    return record
