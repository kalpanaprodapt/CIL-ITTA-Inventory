"""Test data builders for Purchase Order."""
from __future__ import annotations

from datetime import date, datetime, timezone
from decimal import Decimal
from uuid import UUID

from itta_core.models import LifecycleStatus
from itta_api.domains.assetmgmt.purchase_order.schemas import *  # noqa: F401,F403

ID = UUID("11111111-1111-1111-1111-111111111111")
TENANT = "tenant-a"


def create_payload(**overrides) -> PurchaseOrderCreate:
    data = dict(
        code="code-1",
        name="name-1",
        description="description-1",
        status=LifecycleStatus.PLANNED,
        external_ref="external_ref-1",
        po_number="po_number-1",
        vendor_id=UUID("00000000-0000-0000-0000-000000000011"),
        ordered_on=date(2026, 2, 11),
        total_amount=Decimal("13.50"),
        currency="currency-1",
        owner="owner-1",
        criticality=PurchaseOrderCriticality.LOW,
        cost_center="cost_center-1",
        notes="notes-1",
        effective_from=date(2026, 2, 11),
    )
    data.update(code="PURCHA-0001", name="Purchase Order one", status=LifecycleStatus.ACTIVE)
    data.update(overrides)
    return PurchaseOrderCreate(**data)


def read_model(**overrides) -> PurchaseOrderRead:
    base = create_payload().model_dump()
    base.update(id=ID, tenant_id=TENANT, version=0)
    base.update(overrides)
    return PurchaseOrderRead(**base)


def raw_record(**overrides) -> dict[str, object]:
    record = create_payload().model_dump(mode="json", by_alias=True)
    record.update(overrides)
    return record


def upstream_json(**overrides) -> dict[str, object]:
    record = read_model().model_dump(mode="json", by_alias=True)
    record.update(overrides)
    return record
