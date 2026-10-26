"""Test data builders for Import Batch."""
from __future__ import annotations

from datetime import date, datetime, timezone
from decimal import Decimal
from uuid import UUID

from itta_core.models import LifecycleStatus
from itta_api.domains.integration.import_batch.schemas import *  # noqa: F401,F403

ID = UUID("11111111-1111-1111-1111-111111111111")
TENANT = "tenant-a"


def create_payload(**overrides) -> ImportBatchCreate:
    data = dict(
        code="code-1",
        name="name-1",
        description="description-1",
        status=LifecycleStatus.PLANNED,
        external_ref="external_ref-1",
        file_name="file_name-1",
        entity_type="entity_type-1",
        total_rows=11,
        processed_rows=11,
        failed_rows=11,
        batch_state=ImportBatchBatchState.UPLOADED,
        effective_from=date(2026, 2, 11),
        effective_to=date(2026, 2, 11),
        labels="labels-1",
        lifecycle_stage=ImportBatchLifecycleStage.PLAN,
        source_system="source_system-1",
    )
    data.update(code="IMPORT-0001", name="Import Batch one", status=LifecycleStatus.ACTIVE)
    data.update(overrides)
    return ImportBatchCreate(**data)


def read_model(**overrides) -> ImportBatchRead:
    base = create_payload().model_dump()
    base.update(id=ID, tenant_id=TENANT, version=0)
    base.update(overrides)
    return ImportBatchRead(**base)


def raw_record(**overrides) -> dict[str, object]:
    record = create_payload().model_dump(mode="json", by_alias=True)
    record.update(overrides)
    return record


def upstream_json(**overrides) -> dict[str, object]:
    record = read_model().model_dump(mode="json", by_alias=True)
    record.update(overrides)
    return record
