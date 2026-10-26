"""Test data builders for Contract."""
from __future__ import annotations

from datetime import date, datetime, timezone
from decimal import Decimal
from uuid import UUID

from itta_core.models import LifecycleStatus
from itta_api.domains.customer.contract.schemas import *  # noqa: F401,F403

ID = UUID("11111111-1111-1111-1111-111111111111")
TENANT = "tenant-a"


def create_payload(**overrides) -> ContractCreate:
    data = dict(
        code="code-1",
        name="name-1",
        description="description-1",
        status=LifecycleStatus.PLANNED,
        external_ref="external_ref-1",
        customer_id=UUID("00000000-0000-0000-0000-000000000011"),
        contract_number="contract_number-1",
        start_date=date(2026, 2, 11),
        end_date=date(2026, 2, 11),
        term_months=11,
        auto_renew=True,
        source_system="source_system-1",
        region="region-1",
        owner="owner-1",
        criticality=ContractCriticality.LOW,
        cost_center="cost_center-1",
    )
    data.update(code="CONTRA-0001", name="Contract one", status=LifecycleStatus.ACTIVE)
    data.update(overrides)
    return ContractCreate(**data)


def read_model(**overrides) -> ContractRead:
    base = create_payload().model_dump()
    base.update(id=ID, tenant_id=TENANT, version=0)
    base.update(overrides)
    return ContractRead(**base)


def raw_record(**overrides) -> dict[str, object]:
    record = create_payload().model_dump(mode="json", by_alias=True)
    record.update(overrides)
    return record


def upstream_json(**overrides) -> dict[str, object]:
    record = read_model().model_dump(mode="json", by_alias=True)
    record.update(overrides)
    return record
