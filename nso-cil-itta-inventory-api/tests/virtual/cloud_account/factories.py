"""Test data builders for Cloud Account."""
from __future__ import annotations

from datetime import date, datetime, timezone
from decimal import Decimal
from uuid import UUID

from itta_core.models import LifecycleStatus
from itta_api.domains.virtual.cloud_account.schemas import *  # noqa: F401,F403

ID = UUID("11111111-1111-1111-1111-111111111111")
TENANT = "tenant-a"


def create_payload(**overrides) -> CloudAccountCreate:
    data = dict(
        code="code-1",
        name="name-1",
        description="description-1",
        status=LifecycleStatus.PLANNED,
        external_ref="external_ref-1",
        provider=CloudAccountProvider.AZURE,
        account_ref="account_ref-1",
        subscription_name="subscription_name-1",
        monthly_budget=Decimal("13.50"),
        environment="environment-1",
        notes="notes-1",
        effective_from=date(2026, 2, 11),
        effective_to=date(2026, 2, 11),
        labels="labels-1",
        lifecycle_stage=CloudAccountLifecycleStage.PLAN,
    )
    data.update(code="CLOUDA-0001", name="Cloud Account one", status=LifecycleStatus.ACTIVE)
    data.update(overrides)
    return CloudAccountCreate(**data)


def read_model(**overrides) -> CloudAccountRead:
    base = create_payload().model_dump()
    base.update(id=ID, tenant_id=TENANT, version=0)
    base.update(overrides)
    return CloudAccountRead(**base)


def raw_record(**overrides) -> dict[str, object]:
    record = create_payload().model_dump(mode="json", by_alias=True)
    record.update(overrides)
    return record


def upstream_json(**overrides) -> dict[str, object]:
    record = read_model().model_dump(mode="json", by_alias=True)
    record.update(overrides)
    return record
