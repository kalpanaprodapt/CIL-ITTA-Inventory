"""Pydantic models for Alarm Definition (Alarms and Events)."""
from __future__ import annotations

from datetime import date, datetime
from decimal import Decimal
from enum import Enum
from uuid import UUID

from pydantic import Field, field_validator, model_validator

from itta_core.models import ApiModel, LifecycleStatus, reject_control_chars


class AlarmDefinitionSeverity(str, Enum):
    """Allowed values for Alarm Definition Severity."""

    INDETERMINATE = "INDETERMINATE"
    WARNING = "WARNING"
    MINOR = "MINOR"
    MAJOR = "MAJOR"
    CRITICAL = "CRITICAL"


class AlarmDefinitionBase(ApiModel):
    """Attributes shared by create and read models."""

    code: str = Field(..., max_length=255, min_length=1, description="Code")
    name: str = Field(..., max_length=255, min_length=1, description="Name")
    description: str | None = Field(None, max_length=4000, description="Description")
    status: LifecycleStatus = Field(..., description="Status")
    external_ref: str | None = Field(None, max_length=255, description="External Ref")
    alarm_code: str = Field(..., max_length=255, min_length=1, description="Alarm Code")
    severity: AlarmDefinitionSeverity | None = Field(None, description="Severity")
    probable_cause: str | None = Field(None, max_length=255, description="Probable Cause")
    auto_clear: bool | None = Field(None, description="Auto Clear")
    cost_center: str | None = Field(None, max_length=255, description="Cost Center")
    notes: str | None = Field(None, max_length=4000, description="Notes")
    effective_from: date | None = Field(None, description="Effective From")
    effective_to: date | None = Field(None, description="Effective To")
    labels: str | None = Field(None, max_length=255, description="Labels")

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

    @field_validator("alarm_code")
    @classmethod
    def _check_alarm_code(cls, value: str | None) -> str | None:
        return reject_control_chars(value, "alarmCode")

    @field_validator("probable_cause")
    @classmethod
    def _check_probable_cause(cls, value: str | None) -> str | None:
        return reject_control_chars(value, "probableCause")

    @field_validator("cost_center")
    @classmethod
    def _check_cost_center(cls, value: str | None) -> str | None:
        return reject_control_chars(value, "costCenter")

    @field_validator("notes")
    @classmethod
    def _check_notes(cls, value: str | None) -> str | None:
        return reject_control_chars(value, "notes")

    @field_validator("labels")
    @classmethod
    def _check_labels(cls, value: str | None) -> str | None:
        return reject_control_chars(value, "labels")

    @model_validator(mode="after")
    def _check_ranges(self) -> "AlarmDefinitionBase":
        if self.effective_from is not None and self.effective_to is not None and self.effective_to < self.effective_from:
            raise ValueError("effective_to must not be earlier than effective_from")
        return self


class AlarmDefinitionCreate(AlarmDefinitionBase):
    """Payload to create a Alarm Definition."""


class AlarmDefinitionUpdate(ApiModel):
    """Partial update; omitted attributes are left unchanged."""

    expected_version: int | None = Field(None, ge=0, description="Optimistic lock version")
    code: str | None = Field(None, max_length=255, min_length=1, description="Code")
    name: str | None = Field(None, max_length=255, min_length=1, description="Name")
    description: str | None = Field(None, max_length=4000, description="Description")
    status: LifecycleStatus | None = Field(None, description="Status")
    external_ref: str | None = Field(None, max_length=255, description="External Ref")
    alarm_code: str | None = Field(None, max_length=255, min_length=1, description="Alarm Code")
    severity: AlarmDefinitionSeverity | None = Field(None, description="Severity")
    probable_cause: str | None = Field(None, max_length=255, description="Probable Cause")
    auto_clear: bool | None = Field(None, description="Auto Clear")
    cost_center: str | None = Field(None, max_length=255, description="Cost Center")
    notes: str | None = Field(None, max_length=4000, description="Notes")
    effective_from: date | None = Field(None, description="Effective From")
    effective_to: date | None = Field(None, description="Effective To")
    labels: str | None = Field(None, max_length=255, description="Labels")


class AlarmDefinitionRead(AlarmDefinitionBase):
    """Alarm Definition as returned by the system of record."""

    id: UUID
    tenant_id: str
    version: int = 0
    created_at: datetime | None = None
    updated_at: datetime | None = None
    created_by: str | None = None
    updated_by: str | None = None


class AlarmDefinitionSummary(ApiModel):
    id: UUID
    code: str
    name: str
    status: LifecycleStatus


class AlarmDefinitionBulkFailure(ApiModel):
    index: int
    message: str


class AlarmDefinitionBulkResult(ApiModel):
    succeeded: list[AlarmDefinitionRead] = Field(default_factory=list)
    failures: list[AlarmDefinitionBulkFailure] = Field(default_factory=list)
