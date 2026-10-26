"""Pydantic models for Import Batch (Integration Layer)."""
from __future__ import annotations

from datetime import date, datetime
from decimal import Decimal
from enum import Enum
from uuid import UUID

from pydantic import Field, field_validator, model_validator

from itta_core.models import ApiModel, LifecycleStatus, reject_control_chars


class ImportBatchBatchState(str, Enum):
    """Allowed values for Import Batch Batch State."""

    UPLOADED = "UPLOADED"
    VALIDATING = "VALIDATING"
    LOADING = "LOADING"
    DONE = "DONE"
    FAILED = "FAILED"


class ImportBatchLifecycleStage(str, Enum):
    """Allowed values for Import Batch Lifecycle Stage."""

    PLAN = "PLAN"
    BUILD = "BUILD"
    OPERATE = "OPERATE"
    RETIRE = "RETIRE"


class ImportBatchBase(ApiModel):
    """Attributes shared by create and read models."""

    code: str = Field(..., max_length=255, min_length=1, description="Code")
    name: str = Field(..., max_length=255, min_length=1, description="Name")
    description: str | None = Field(None, max_length=4000, description="Description")
    status: LifecycleStatus = Field(..., description="Status")
    external_ref: str | None = Field(None, max_length=255, description="External Ref")
    file_name: str = Field(..., max_length=255, min_length=1, description="File Name")
    entity_type: str = Field(..., max_length=255, min_length=1, description="Entity Type")
    total_rows: int | None = Field(None, ge=0, description="Total Rows")
    processed_rows: int | None = Field(None, ge=0, description="Processed Rows")
    failed_rows: int | None = Field(None, ge=0, description="Failed Rows")
    batch_state: ImportBatchBatchState | None = Field(None, description="Batch State")
    effective_from: date | None = Field(None, description="Effective From")
    effective_to: date | None = Field(None, description="Effective To")
    labels: str | None = Field(None, max_length=255, description="Labels")
    lifecycle_stage: ImportBatchLifecycleStage | None = Field(None, description="Lifecycle Stage")
    source_system: str | None = Field(None, max_length=255, description="Source System")

    @field_validator("code")
    @classmethod
    def _check_code(cls, value: str | None) -> str | None:
        return reject_control_chars(value, "code")

    @field_validator("name")
    @classmethod
    def _check_name(cls, value: str | None) -> str | None:
        return reject_control_chars(value, "name")

    @field_validator("description")
    @classmethod
    def _check_description(cls, value: str | None) -> str | None:
        return reject_control_chars(value, "description")

    @field_validator("external_ref")
    @classmethod
    def _check_external_ref(cls, value: str | None) -> str | None:
        return reject_control_chars(value, "externalRef")

    @field_validator("file_name")
    @classmethod
    def _check_file_name(cls, value: str | None) -> str | None:
        return reject_control_chars(value, "fileName")

    @field_validator("entity_type")
    @classmethod
    def _check_entity_type(cls, value: str | None) -> str | None:
        return reject_control_chars(value, "entityType")

    @field_validator("labels")
    @classmethod
    def _check_labels(cls, value: str | None) -> str | None:
        return reject_control_chars(value, "labels")

    @field_validator("source_system")
    @classmethod
    def _check_source_system(cls, value: str | None) -> str | None:
        return reject_control_chars(value, "sourceSystem")

    @model_validator(mode="after")
    def _check_ranges(self) -> "ImportBatchBase":
        if self.effective_from is not None and self.effective_to is not None and self.effective_to < self.effective_from:
            raise ValueError("effective_to must not be earlier than effective_from")
        return self


class ImportBatchCreate(ImportBatchBase):
    """Payload to create a Import Batch."""


class ImportBatchUpdate(ApiModel):
    """Partial update; omitted attributes are left unchanged."""

    expected_version: int | None = Field(None, ge=0, description="Optimistic lock version")
    code: str | None = Field(None, max_length=255, min_length=1, description="Code")
    name: str | None = Field(None, max_length=255, min_length=1, description="Name")
    description: str | None = Field(None, max_length=4000, description="Description")
    status: LifecycleStatus | None = Field(None, description="Status")
    external_ref: str | None = Field(None, max_length=255, description="External Ref")
    file_name: str | None = Field(None, max_length=255, min_length=1, description="File Name")
    entity_type: str | None = Field(None, max_length=255, min_length=1, description="Entity Type")
    total_rows: int | None = Field(None, ge=0, description="Total Rows")
    processed_rows: int | None = Field(None, ge=0, description="Processed Rows")
    failed_rows: int | None = Field(None, ge=0, description="Failed Rows")
    batch_state: ImportBatchBatchState | None = Field(None, description="Batch State")
    effective_from: date | None = Field(None, description="Effective From")
    effective_to: date | None = Field(None, description="Effective To")
    labels: str | None = Field(None, max_length=255, description="Labels")
    lifecycle_stage: ImportBatchLifecycleStage | None = Field(None, description="Lifecycle Stage")
    source_system: str | None = Field(None, max_length=255, description="Source System")


class ImportBatchRead(ImportBatchBase):
    """Import Batch as returned by the system of record."""

    id: UUID
    tenant_id: str
    version: int = 0
    created_at: datetime | None = None
    updated_at: datetime | None = None
    created_by: str | None = None
    updated_by: str | None = None


class ImportBatchSummary(ApiModel):
    id: UUID
    code: str
    name: str
    status: LifecycleStatus


class ImportBatchBulkFailure(ApiModel):
    index: int
    message: str


class ImportBatchBulkResult(ApiModel):
    succeeded: list[ImportBatchRead] = Field(default_factory=list)
    failures: list[ImportBatchBulkFailure] = Field(default_factory=list)
