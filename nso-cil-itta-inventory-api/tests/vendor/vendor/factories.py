"""Test data builders for Vendor."""
from __future__ import annotations

from datetime import date, datetime, timezone
from decimal import Decimal
from uuid import UUID

from itta_core.models import LifecycleStatus
from itta_api.domains.vendor.vendor.schemas import *  # noqa: F401,F403

ID = UUID("11111111-1111-1111-1111-111111111111")
TENANT = "tenant-a"


def create_payload(**overrides) -> VendorCreate:
    data = dict(
        code="code-1",
        name="name-1",
        description="description-1",
        status=LifecycleStatus.PLANNED,
        external_ref="external_ref-1",
        legal_name="legal_name-1",
        support_email="support_email-1",
        support_phone="support_phone-1",
        website="website-1",
        tier=VendorTier.STRATEGIC,
        effective_from=date(2026, 2, 11),
        effective_to=date(2026, 2, 11),
        labels="labels-1",
        lifecycle_stage=VendorLifecycleStage.PLAN,
        source_system="source_system-1",
    )
    data.update(code="VENDOR-0001", name="Vendor one", status=LifecycleStatus.ACTIVE)
    data.update(overrides)
    return VendorCreate(**data)


def read_model(**overrides) -> VendorRead:
    base = create_payload().model_dump()
    base.update(id=ID, tenant_id=TENANT, version=0)
    base.update(overrides)
    return VendorRead(**base)


def raw_record(**overrides) -> dict[str, object]:
    record = create_payload().model_dump(mode="json", by_alias=True)
    record.update(overrides)
    return record


def upstream_json(**overrides) -> dict[str, object]:
    record = read_model().model_dump(mode="json", by_alias=True)
    record.update(overrides)
    return record
