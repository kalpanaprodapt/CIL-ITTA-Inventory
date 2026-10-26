"""Search filter for Import Batch and its translation to upstream query parameters."""
from __future__ import annotations

from datetime import date, datetime
from decimal import Decimal
from uuid import UUID

from pydantic import Field

from itta_core.models import ApiModel, LifecycleStatus
from itta_api.domains.integration.import_batch.schemas import *  # noqa: F401,F403


class ImportBatchFilter(ApiModel):
    """Query-string filter for Import Batch search."""

    q: str | None = Field(None, description="Free text over code, name and description")
    status: list[LifecycleStatus] | None = None
    code: str | None = Field(None, description="Code contains")
    name: str | None = Field(None, description="Name contains")
    external_ref: str | None = Field(None, description="External Ref contains")
    file_name: str | None = Field(None, description="File Name contains")
    entity_type: str | None = Field(None, description="Entity Type contains")
    total_rows_min: int | None = None
    total_rows_max: int | None = None
    processed_rows_min: int | None = None
    processed_rows_max: int | None = None
    failed_rows_min: int | None = None
    failed_rows_max: int | None = None
    batch_state: list[ImportBatchBatchState] | None = None
    effective_from_from: date | None = None
    effective_from_to: date | None = None
    effective_to_from: date | None = None
    effective_to_to: date | None = None
    labels: str | None = Field(None, description="Labels contains")
    lifecycle_stage: list[ImportBatchLifecycleStage] | None = None
    source_system: str | None = Field(None, description="Source System contains")


def to_query(flt: ImportBatchFilter) -> dict[str, str]:
    """Translate a filter into the query parameters understood by the inventory service."""
    params: dict[str, str] = {}
    if flt.q and flt.q.strip():
        params["q"] = flt.q.strip()
    if flt.status:
        params["status"] = ",".join(v.value for v in flt.status)
    if flt.code:
        params["code"] = flt.code.strip()
    if flt.name:
        params["name"] = flt.name.strip()
    if flt.external_ref:
        params["externalRef"] = flt.external_ref.strip()
    if flt.file_name:
        params["fileName"] = flt.file_name.strip()
    if flt.entity_type:
        params["entityType"] = flt.entity_type.strip()
    if flt.total_rows_min is not None:
        params["totalRowsMin"] = str(flt.total_rows_min)
    if flt.total_rows_max is not None:
        params["totalRowsMax"] = str(flt.total_rows_max)
    if flt.processed_rows_min is not None:
        params["processedRowsMin"] = str(flt.processed_rows_min)
    if flt.processed_rows_max is not None:
        params["processedRowsMax"] = str(flt.processed_rows_max)
    if flt.failed_rows_min is not None:
        params["failedRowsMin"] = str(flt.failed_rows_min)
    if flt.failed_rows_max is not None:
        params["failedRowsMax"] = str(flt.failed_rows_max)
    if flt.batch_state:
        params["batchState"] = ",".join(v.value for v in flt.batch_state)
    if flt.effective_from_from is not None:
        params["effectiveFromFrom"] = flt.effective_from_from.isoformat()
    if flt.effective_from_to is not None:
        params["effectiveFromTo"] = flt.effective_from_to.isoformat()
    if flt.effective_to_from is not None:
        params["effectiveToFrom"] = flt.effective_to_from.isoformat()
    if flt.effective_to_to is not None:
        params["effectiveToTo"] = flt.effective_to_to.isoformat()
    if flt.labels:
        params["labels"] = flt.labels.strip()
    if flt.lifecycle_stage:
        params["lifecycleStage"] = ",".join(v.value for v in flt.lifecycle_stage)
    if flt.source_system:
        params["sourceSystem"] = flt.source_system.strip()
    return params
