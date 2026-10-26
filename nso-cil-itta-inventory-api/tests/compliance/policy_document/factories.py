"""Test data builders for Policy Document."""
from __future__ import annotations

from datetime import date, datetime, timezone
from decimal import Decimal
from uuid import UUID

from itta_core.models import LifecycleStatus
from itta_api.domains.compliance.policy_document.schemas import *  # noqa: F401,F403

ID = UUID("11111111-1111-1111-1111-111111111111")
TENANT = "tenant-a"


def create_payload(**overrides) -> PolicyDocumentCreate:
    data = dict(
        code="code-1",
        name="name-1",
        description="description-1",
        status=LifecycleStatus.PLANNED,
        external_ref="external_ref-1",
        title="title-1",
        doc_version="doc_version-1",
        effective_date=date(2026, 2, 11),
        owner="owner-1",
        document_url="document_url-1",
        lifecycle_stage=PolicyDocumentLifecycleStage.PLAN,
        source_system="source_system-1",
        region="region-1",
        criticality=PolicyDocumentCriticality.LOW,
        cost_center="cost_center-1",
    )
    data.update(code="POLICY-0001", name="Policy Document one", status=LifecycleStatus.ACTIVE)
    data.update(overrides)
    return PolicyDocumentCreate(**data)


def read_model(**overrides) -> PolicyDocumentRead:
    base = create_payload().model_dump()
    base.update(id=ID, tenant_id=TENANT, version=0)
    base.update(overrides)
    return PolicyDocumentRead(**base)


def raw_record(**overrides) -> dict[str, object]:
    record = create_payload().model_dump(mode="json", by_alias=True)
    record.update(overrides)
    return record


def upstream_json(**overrides) -> dict[str, object]:
    record = read_model().model_dump(mode="json", by_alias=True)
    record.update(overrides)
    return record
