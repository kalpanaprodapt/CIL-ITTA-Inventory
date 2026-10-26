"""Test data builders for Asset."""
from __future__ import annotations

from datetime import date, datetime, timezone
from decimal import Decimal
from uuid import UUID

from itta_core.models import LifecycleStatus
from itta_api.domains.assetmgmt.asset.schemas import *  # noqa: F401,F403

ID = UUID("11111111-1111-1111-1111-111111111111")
TENANT = "tenant-a"


def create_payload(**overrides) -> AssetCreate:
    data = dict(
        code="code-1",
        name="name-1",
        description="description-1",
        status=LifecycleStatus.PLANNED,
        external_ref="external_ref-1",
        asset_tag="asset_tag-1",
        asset_class="asset_class-1",
        acquired_on=date(2026, 2, 11),
        purchase_price=Decimal("13.50"),
        current_value=Decimal("13.50"),
        custodian="custodian-1",
        labels="labels-1",
        lifecycle_stage=AssetLifecycleStage.PLAN,
        source_system="source_system-1",
        region="region-1",
        owner="owner-1",
    )
    data.update(code="ASSET-0001", name="Asset one", status=LifecycleStatus.ACTIVE)
    data.update(overrides)
    return AssetCreate(**data)


def read_model(**overrides) -> AssetRead:
    base = create_payload().model_dump()
    base.update(id=ID, tenant_id=TENANT, version=0)
    base.update(overrides)
    return AssetRead(**base)


def raw_record(**overrides) -> dict[str, object]:
    record = create_payload().model_dump(mode="json", by_alias=True)
    record.update(overrides)
    return record


def upstream_json(**overrides) -> dict[str, object]:
    record = read_model().model_dump(mode="json", by_alias=True)
    record.update(overrides)
    return record
