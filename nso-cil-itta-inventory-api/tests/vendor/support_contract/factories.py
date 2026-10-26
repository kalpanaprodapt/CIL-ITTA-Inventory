"""Test data builders for Support Contract."""
from __future__ import annotations

from datetime import date, datetime, timezone
from decimal import Decimal
from uuid import UUID

from itta_core.models import LifecycleStatus
from itta_api.domains.vendor.support_contract.schemas import *  # noqa: F401,F403

ID = UUID("11111111-1111-1111-1111-111111111111")
TENANT = "tenant-a"


def create_payload(**overrides) -> SupportContractCreate:
    data = dict(
        code="code-1",
        name="name-1",
        description="description-1",
        status=LifecycleStatus.PLANNED,
        external_ref="external_ref-1",
        vendor_id=UUID("00000000-0000-0000-0000-000000000011"),
        contract_number="contract_number-1",
        start_date=date(2026, 2, 11),
        end_date=date(2026, 2, 11),
        coverage_level=SupportContractCoverageLevel.BRONZE,
        annual_cost=Decimal("13.50"),
        owner="owner-1",
        criticality=SupportContractCriticality.LOW,
        cost_center="cost_center-1",
        notes="notes-1",
        effective_from=date(2026, 2, 11),
    )
    data.update(code="SUPPOR-0001", name="Support Contract one", status=LifecycleStatus.ACTIVE)
    data.update(overrides)
    return SupportContractCreate(**data)


def read_model(**overrides) -> SupportContractRead:
    base = create_payload().model_dump()
    base.update(id=ID, tenant_id=TENANT, version=0)
    base.update(overrides)
    return SupportContractRead(**base)


def raw_record(**overrides) -> dict[str, object]:
    record = create_payload().model_dump(mode="json", by_alias=True)
    record.update(overrides)
    return record


def upstream_json(**overrides) -> dict[str, object]:
    record = read_model().model_dump(mode="json", by_alias=True)
    record.update(overrides)
    return record
