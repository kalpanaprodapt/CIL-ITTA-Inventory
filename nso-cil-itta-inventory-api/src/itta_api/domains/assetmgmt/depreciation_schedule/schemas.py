"""Pydantic models for Depreciation Schedule (Asset and Finance)."""
from __future__ import annotations

from datetime import date, datetime
from decimal import Decimal
from enum import Enum
from uuid import UUID

from pydantic import Field, field_validator, model_validator

from itta_core.models import ApiModel, LifecycleStatus, reject_control_chars


class DepreciationScheduleMethod(str, Enum):
    """Allowed values for Depreciation Schedule Method."""

    STRAIGHT_LINE = "STRAIGHT_LINE"
    DECLINING = "DECLINING"
    UNITS = "UNITS"


class DepreciationScheduleLifecycleStage(str, Enum):
    """Allowed values for Depreciation Schedule Lifecycle Stage."""

    PLAN = "PLAN"
    BUILD = "BUILD"
    OPERATE = "OPERATE"
    RETIRE = "RETIRE"


class DepreciationScheduleBase(ApiModel):
    """Attributes shared by create and read models."""

    code: str = Field(..., max_length=255, min_length=1, description="Code")
    name: str = Field(..., max_length=255, min_length=1, description="Name")
    description: str | None = Field(None, max_length=4000, description="Description")
    status: LifecycleStatus = Field(..., description="Status")
    external_ref: str | None = Field(None, max_length=255, description="External Ref")
    asset_id: UUID = Field(..., description="Asset Id")
    method: DepreciationScheduleMethod | None = Field(None, description="Method")
    useful_life_months: int = Field(..., ge=0, description="Useful Life Months")
    salvage_value: Decimal | None = Field(None, ge=0, max_digits=18, decimal_places=4, description="Salvage Value")
    start_date: date | None = Field(None, description="Start Date")
    effective_from: date | None = Field(None, description="Effective From")
    effective_to: date | None = Field(None, description="Effective To")
    labels: str | None = Field(None, max_length=255, description="Labels")
    lifecycle_stage: DepreciationScheduleLifecycleStage | None = Field(None, description="Lifecycle Stage")
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

    @field_validator("labels")
    @classmethod
    def _check_labels(cls, value: str | None) -> str | None:
        return reject_control_chars(value, "labels")

    @field_validator("source_system")
    @classmethod
    def _check_source_system(cls, value: str | None) -> str | None:
        return reject_control_chars(value, "sourceSystem")

    @model_validator(mode="after")
    def _check_ranges(self) -> "DepreciationScheduleBase":
        if self.effective_from is not None and self.effective_to is not None and self.effective_to < self.effective_from:
            raise ValueError("effective_to must not be earlier than effective_from")
        return self


class DepreciationScheduleCreate(DepreciationScheduleBase):
    """Payload to create a Depreciation Schedule."""


class DepreciationScheduleUpdate(ApiModel):
    """Partial update; omitted attributes are left unchanged."""

    expected_version: int | None = Field(None, ge=0, description="Optimistic lock version")
    code: str | None = Field(None, max_length=255, min_length=1, description="Code")
    name: str | None = Field(None, max_length=255, min_length=1, description="Name")
    description: str | None = Field(None, max_length=4000, description="Description")
    status: LifecycleStatus | None = Field(None, description="Status")
    external_ref: str | None = Field(None, max_length=255, description="External Ref")
    asset_id: UUID | None = Field(None, description="Asset Id")
    method: DepreciationScheduleMethod | None = Field(None, description="Method")
    useful_life_months: int | None = Field(None, ge=0, description="Useful Life Months")
    salvage_value: Decimal | None = Field(None, ge=0, max_digits=18, decimal_places=4, description="Salvage Value")
    start_date: date | None = Field(None, description="Start Date")
    effective_from: date | None = Field(None, description="Effective From")
    effective_to: date | None = Field(None, description="Effective To")
    labels: str | None = Field(None, max_length=255, description="Labels")
    lifecycle_stage: DepreciationScheduleLifecycleStage | None = Field(None, description="Lifecycle Stage")
    source_system: str | None = Field(None, max_length=255, description="Source System")


class DepreciationScheduleRead(DepreciationScheduleBase):
    """Depreciation Schedule as returned by the system of record."""

    id: UUID
    tenant_id: str
    version: int = 0
    created_at: datetime | None = None
    updated_at: datetime | None = None
    created_by: str | None = None
    updated_by: str | None = None


class DepreciationScheduleSummary(ApiModel):
    id: UUID
    code: str
    name: str
    status: LifecycleStatus


class DepreciationScheduleBulkFailure(ApiModel):
    index: int
    message: str


class DepreciationScheduleBulkResult(ApiModel):
    succeeded: list[DepreciationScheduleRead] = Field(default_factory=list)
    failures: list[DepreciationScheduleBulkFailure] = Field(default_factory=list)
