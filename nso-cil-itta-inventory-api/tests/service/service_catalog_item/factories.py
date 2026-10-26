"""Test data builders for Service Catalog Item."""
from __future__ import annotations

from datetime import date, datetime, timezone
from decimal import Decimal
from uuid import UUID

from itta_core.models import LifecycleStatus
from itta_api.domains.service.service_catalog_item.schemas import *  # noqa: F401,F403

ID = UUID("11111111-1111-1111-1111-111111111111")
TENANT = "tenant-a"


def create_payload(**overrides) -> ServiceCatalogItemCreate:
    data = dict(
        code="code-1",
        name="name-1",
        description="description-1",
        status=LifecycleStatus.PLANNED,
        external_ref="external_ref-1",
        item_name="item_name-1",
        family=ServiceCatalogItemFamily.CONNECTIVITY,
        base_price=Decimal("13.50"),
        billing_unit="billing_unit-1",
        orderable=True,
        effective_to=date(2026, 2, 11),
        labels="labels-1",
        lifecycle_stage=ServiceCatalogItemLifecycleStage.PLAN,
        source_system="source_system-1",
        region="region-1",
    )
    data.update(code="SERVIC-0001", name="Service Catalog Item one", status=LifecycleStatus.ACTIVE)
    data.update(overrides)
    return ServiceCatalogItemCreate(**data)


def read_model(**overrides) -> ServiceCatalogItemRead:
    base = create_payload().model_dump()
    base.update(id=ID, tenant_id=TENANT, version=0)
    base.update(overrides)
    return ServiceCatalogItemRead(**base)


def raw_record(**overrides) -> dict[str, object]:
    record = create_payload().model_dump(mode="json", by_alias=True)
    record.update(overrides)
    return record


def upstream_json(**overrides) -> dict[str, object]:
    record = read_model().model_dump(mode="json", by_alias=True)
    record.update(overrides)
    return record
