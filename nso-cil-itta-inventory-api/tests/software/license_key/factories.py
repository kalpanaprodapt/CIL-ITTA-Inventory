"""Test data builders for License Key."""
from __future__ import annotations

from datetime import date, datetime, timezone
from decimal import Decimal
from uuid import UUID

from itta_core.models import LifecycleStatus
from itta_api.domains.software.license_key.schemas import *  # noqa: F401,F403

ID = UUID("11111111-1111-1111-1111-111111111111")
TENANT = "tenant-a"


def create_payload(**overrides) -> LicenseKeyCreate:
    data = dict(
        code="code-1",
        name="name-1",
        description="description-1",
        status=LifecycleStatus.PLANNED,
        external_ref="external_ref-1",
        pool_id=UUID("00000000-0000-0000-0000-000000000011"),
        key_value="key_value-1",
        feature="feature-1",
        expires_on=date(2026, 2, 11),
        assigned_device_id=UUID("00000000-0000-0000-0000-000000000011"),
        notes="notes-1",
        effective_from=date(2026, 2, 11),
        effective_to=date(2026, 2, 11),
        labels="labels-1",
        lifecycle_stage=LicenseKeyLifecycleStage.PLAN,
    )
    data.update(code="LICENS-0001", name="License Key one", status=LifecycleStatus.ACTIVE)
    data.update(overrides)
    return LicenseKeyCreate(**data)


def read_model(**overrides) -> LicenseKeyRead:
    base = create_payload().model_dump()
    base.update(id=ID, tenant_id=TENANT, version=0)
    base.update(overrides)
    return LicenseKeyRead(**base)


def raw_record(**overrides) -> dict[str, object]:
    record = create_payload().model_dump(mode="json", by_alias=True)
    record.update(overrides)
    return record


def upstream_json(**overrides) -> dict[str, object]:
    record = read_model().model_dump(mode="json", by_alias=True)
    record.update(overrides)
    return record
