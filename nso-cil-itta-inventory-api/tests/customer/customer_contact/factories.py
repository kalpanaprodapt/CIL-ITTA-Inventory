"""Test data builders for Customer Contact."""
from __future__ import annotations

from datetime import date, datetime, timezone
from decimal import Decimal
from uuid import UUID

from itta_core.models import LifecycleStatus
from itta_api.domains.customer.customer_contact.schemas import *  # noqa: F401,F403

ID = UUID("11111111-1111-1111-1111-111111111111")
TENANT = "tenant-a"


def create_payload(**overrides) -> CustomerContactCreate:
    data = dict(
        code="code-1",
        name="name-1",
        description="description-1",
        status=LifecycleStatus.PLANNED,
        external_ref="external_ref-1",
        customer_id=UUID("00000000-0000-0000-0000-000000000011"),
        full_name="full_name-1",
        email="email-1",
        phone="phone-1",
        contact_role=CustomerContactContactRole.TECHNICAL,
        labels="labels-1",
        lifecycle_stage=CustomerContactLifecycleStage.PLAN,
        source_system="source_system-1",
        region="region-1",
        owner="owner-1",
    )
    data.update(code="CUSTOM-0001", name="Customer Contact one", status=LifecycleStatus.ACTIVE)
    data.update(overrides)
    return CustomerContactCreate(**data)


def read_model(**overrides) -> CustomerContactRead:
    base = create_payload().model_dump()
    base.update(id=ID, tenant_id=TENANT, version=0)
    base.update(overrides)
    return CustomerContactRead(**base)


def raw_record(**overrides) -> dict[str, object]:
    record = create_payload().model_dump(mode="json", by_alias=True)
    record.update(overrides)
    return record


def upstream_json(**overrides) -> dict[str, object]:
    record = read_model().model_dump(mode="json", by_alias=True)
    record.update(overrides)
    return record
