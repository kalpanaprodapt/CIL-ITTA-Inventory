"""Test data builders for Service Order."""
from __future__ import annotations

from datetime import date, datetime, timezone
from decimal import Decimal
from uuid import UUID

from itta_core.models import LifecycleStatus
from itta_api.domains.service.service_order.schemas import *  # noqa: F401,F403

ID = UUID("11111111-1111-1111-1111-111111111111")
TENANT = "tenant-a"


def create_payload(**overrides) -> ServiceOrderCreate:
    data = dict(
        code="code-1",
        name="name-1",
        description="description-1",
        status=LifecycleStatus.PLANNED,
        external_ref="external_ref-1",
        customer_id=UUID("00000000-0000-0000-0000-000000000011"),
        order_type=ServiceOrderOrderType.NEW,
        requested_on=date(2026, 2, 11),
        target_date=date(2026, 2, 11),
        priority=11,
        region="region-1",
        owner="owner-1",
        criticality=ServiceOrderCriticality.LOW,
        cost_center="cost_center-1",
        notes="notes-1",
    )
    data.update(code="SERVIC-0001", name="Service Order one", status=LifecycleStatus.ACTIVE)
    data.update(overrides)
    return ServiceOrderCreate(**data)


def read_model(**overrides) -> ServiceOrderRead:
    base = create_payload().model_dump()
    base.update(id=ID, tenant_id=TENANT, version=0)
    base.update(overrides)
    return ServiceOrderRead(**base)


def raw_record(**overrides) -> dict[str, object]:
    record = create_payload().model_dump(mode="json", by_alias=True)
    record.update(overrides)
    return record


def upstream_json(**overrides) -> dict[str, object]:
    record = read_model().model_dump(mode="json", by_alias=True)
    record.update(overrides)
    return record
