"""Test data builders for Software Image."""
from __future__ import annotations

from datetime import date, datetime, timezone
from decimal import Decimal
from uuid import UUID

from itta_core.models import LifecycleStatus
from itta_api.domains.software.software_image.schemas import *  # noqa: F401,F403

ID = UUID("11111111-1111-1111-1111-111111111111")
TENANT = "tenant-a"


def create_payload(**overrides) -> SoftwareImageCreate:
    data = dict(
        code="code-1",
        name="name-1",
        description="description-1",
        status=LifecycleStatus.PLANNED,
        external_ref="external_ref-1",
        vendor_id=UUID("00000000-0000-0000-0000-000000000011"),
        image_name="image_name-1",
        size_mb=1001,
        checksum="checksum-1",
        platform="platform-1",
        storage_path="storage_path-1",
        lifecycle_stage=SoftwareImageLifecycleStage.PLAN,
        source_system="source_system-1",
        region="region-1",
        owner="owner-1",
        criticality=SoftwareImageCriticality.LOW,
    )
    data.update(code="SOFTWA-0001", name="Software Image one", status=LifecycleStatus.ACTIVE)
    data.update(overrides)
    return SoftwareImageCreate(**data)


def read_model(**overrides) -> SoftwareImageRead:
    base = create_payload().model_dump()
    base.update(id=ID, tenant_id=TENANT, version=0)
    base.update(overrides)
    return SoftwareImageRead(**base)


def raw_record(**overrides) -> dict[str, object]:
    record = create_payload().model_dump(mode="json", by_alias=True)
    record.update(overrides)
    return record


def upstream_json(**overrides) -> dict[str, object]:
    record = read_model().model_dump(mode="json", by_alias=True)
    record.update(overrides)
    return record
