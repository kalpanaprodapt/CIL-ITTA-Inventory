"""Pydantic models for Change Request (Change and Work Management)."""
from __future__ import annotations

from datetime import date, datetime
from decimal import Decimal
from enum import Enum
from uuid import UUID

from pydantic import Field, field_validator, model_validator

from itta_core.models import ApiModel, LifecycleStatus, reject_control_chars


class ChangeRequestChangeType(str, Enum):
    """Allowed values for Change Request Change Type."""

    STANDARD = "STANDARD"
    NORMAL = "NORMAL"
    EMERGENCY = "EMERGENCY"


class ChangeRequestRiskLevel(str, Enum):
    """Allowed values for Change Request Risk Level."""

    LOW = "LOW"
    MEDIUM = "MEDIUM"
    HIGH = "HIGH"


class ChangeRequestLifecycleStage(str, Enum):
    """Allowed values for Change Request Lifecycle Stage."""

    PLAN = "PLAN"
    BUILD = "BUILD"
    OPERATE = "OPERATE"
    RETIRE = "RETIRE"


class ChangeRequestBase(ApiModel):
    """Attributes shared by create and read models."""

    code: str = Field(..., max_length=255, min_length=1, description="Code")
    name: str = Field(..., max_length=255, min_length=1, description="Name")
    description: str | None = Field(None, max_length=4000, description="Description")
    status: LifecycleStatus = Field(..., description="Status")
    external_ref: str | None = Field(None, max_length=255, description="External Ref")
    change_number: str = Field(..., max_length=255, min_length=1, description="Change Number")
    change_type: ChangeRequestChangeType | None = Field(None, description="Change Type")
    risk_level: ChangeRequestRiskLevel | None = Field(None, description="Risk Level")
    planned_start: datetime | None = Field(None, description="Planned Start")
    planned_end: datetime | None = Field(None, description="Planned End")
    notes: str | None = Field(None, max_length=4000, description="Notes")
    effective_from: date | None = Field(None, description="Effective From")
    effective_to: date | None = Field(None, description="Effective To")
    labels: str | None = Field(None, max_length=255, description="Labels")
    lifecycle_stage: ChangeRequestLifecycleStage | None = Field(None, description="Lifecycle Stage")

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

    @field_validator("change_number")
    @classmethod
    def _check_change_number(cls, value: str | None) -> str | None:
        return reject_control_chars(value, "changeNumber")

    @field_validator("notes")
    @classmethod
    def _check_notes(cls, value: str | None) -> str | None:
        return reject_control_chars(value, "notes")

    @field_validator("labels")
    @classmethod
    def _check_labels(cls, value: str | None) -> str | None:
        return reject_control_chars(value, "labels")

    @model_validator(mode="after")
    def _check_ranges(self) -> "ChangeRequestBase":
        if self.effective_from is not None and self.effective_to is not None and self.effective_to < self.effective_from:
            raise ValueError("effective_to must not be earlier than effective_from")
        if self.planned_start is not None and self.planned_end is not None and self.planned_end < self.planned_start:
            raise ValueError("planned_end must not be earlier than planned_start")
        return self


class ChangeRequestCreate(ChangeRequestBase):
    """Payload to create a Change Request."""


class ChangeRequestUpdate(ApiModel):
    """Partial update; omitted attributes are left unchanged."""

    expected_version: int | None = Field(None, ge=0, description="Optimistic lock version")
    code: str | None = Field(None, max_length=255, min_length=1, description="Code")
    name: str | None = Field(None, max_length=255, min_length=1, description="Name")
    description: str | None = Field(None, max_length=4000, description="Description")
    status: LifecycleStatus | None = Field(None, description="Status")
    external_ref: str | None = Field(None, max_length=255, description="External Ref")
    change_number: str | None = Field(None, max_length=255, min_length=1, description="Change Number")
    change_type: ChangeRequestChangeType | None = Field(None, description="Change Type")
    risk_level: ChangeRequestRiskLevel | None = Field(None, description="Risk Level")
    planned_start: datetime | None = Field(None, description="Planned Start")
    planned_end: datetime | None = Field(None, description="Planned End")
    notes: str | None = Field(None, max_length=4000, description="Notes")
    effective_from: date | None = Field(None, description="Effective From")
    effective_to: date | None = Field(None, description="Effective To")
    labels: str | None = Field(None, max_length=255, description="Labels")
    lifecycle_stage: ChangeRequestLifecycleStage | None = Field(None, description="Lifecycle Stage")


class ChangeRequestRead(ChangeRequestBase):
    """Change Request as returned by the system of record."""

    id: UUID
    tenant_id: str
    version: int = 0
    created_at: datetime | None = None
    updated_at: datetime | None = None
    created_by: str | None = None
    updated_by: str | None = None


class ChangeRequestSummary(ApiModel):
    id: UUID
    code: str
    name: str
    status: LifecycleStatus


class ChangeRequestBulkFailure(ApiModel):
    index: int
    message: str


class ChangeRequestBulkResult(ApiModel):
    succeeded: list[ChangeRequestRead] = Field(default_factory=list)
    failures: list[ChangeRequestBulkFailure] = Field(default_factory=list)
