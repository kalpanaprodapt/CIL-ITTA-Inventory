"""Pydantic models for Alarm Instance (Alarms and Events)."""
from __future__ import annotations

from datetime import date, datetime
from decimal import Decimal
from enum import Enum
from uuid import UUID

from pydantic import Field, field_validator, model_validator

from itta_core.models import ApiModel, LifecycleStatus, reject_control_chars


class AlarmInstanceAlarmState(str, Enum):
    """Allowed values for Alarm Instance Alarm State."""

    ACTIVE = "ACTIVE"
    ACKED = "ACKED"
    CLEARED = "CLEARED"


class AlarmInstanceLifecycleStage(str, Enum):
    """Allowed values for Alarm Instance Lifecycle Stage."""

    PLAN = "PLAN"
    BUILD = "BUILD"
    OPERATE = "OPERATE"
    RETIRE = "RETIRE"


class AlarmInstanceBase(ApiModel):
    """Attributes shared by create and read models."""

    code: str = Field(..., max_length=255, min_length=1, description="Code")
    name: str = Field(..., max_length=255, min_length=1, description="Name")
    description: str | None = Field(None, max_length=4000, description="Description")
    status: LifecycleStatus = Field(..., description="Status")
    external_ref: str | None = Field(None, max_length=255, description="External Ref")
    definition_id: UUID = Field(..., description="Definition Id")
    source_ref: str = Field(..., max_length=255, min_length=1, description="Source Ref")
    raised_at: datetime = Field(..., description="Raised At")
    cleared_at: datetime | None = Field(None, description="Cleared At")
    acked_by: str | None = Field(None, max_length=255, description="Acked By")
    alarm_state: AlarmInstanceAlarmState | None = Field(None, description="Alarm State")
    effective_from: date | None = Field(None, description="Effective From")
    effective_to: date | None = Field(None, description="Effective To")
    labels: str | None = Field(None, max_length=255, description="Labels")
    lifecycle_stage: AlarmInstanceLifecycleStage | None = Field(None, description="Lifecycle Stage")
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

    @field_validator("source_ref")
    @classmethod
    def _check_source_ref(cls, value: str | None) -> str | None:
        return reject_control_chars(value, "sourceRef")

    @field_validator("acked_by")
    @classmethod
    def _check_acked_by(cls, value: str | None) -> str | None:
        return reject_control_chars(value, "ackedBy")

    @field_validator("labels")
    @classmethod
    def _check_labels(cls, value: str | None) -> str | None:
        return reject_control_chars(value, "labels")

    @field_validator("source_system")
    @classmethod
    def _check_source_system(cls, value: str | None) -> str | None:
        return reject_control_chars(value, "sourceSystem")

    @model_validator(mode="after")
    def _check_ranges(self) -> "AlarmInstanceBase":
        if self.effective_from is not None and self.effective_to is not None and self.effective_to < self.effective_from:
            raise ValueError("effective_to must not be earlier than effective_from")
        if self.raised_at is not None and self.cleared_at is not None and self.cleared_at < self.raised_at:
            raise ValueError("cleared_at must not be earlier than raised_at")
        return self


class AlarmInstanceCreate(AlarmInstanceBase):
    """Payload to create a Alarm Instance."""


class AlarmInstanceUpdate(ApiModel):
    """Partial update; omitted attributes are left unchanged."""

    expected_version: int | None = Field(None, ge=0, description="Optimistic lock version")
    code: str | None = Field(None, max_length=255, min_length=1, description="Code")
    name: str | None = Field(None, max_length=255, min_length=1, description="Name")
    description: str | None = Field(None, max_length=4000, description="Description")
    status: LifecycleStatus | None = Field(None, description="Status")
    external_ref: str | None = Field(None, max_length=255, description="External Ref")
    definition_id: UUID | None = Field(None, description="Definition Id")
    source_ref: str | None = Field(None, max_length=255, min_length=1, description="Source Ref")
    raised_at: datetime | None = Field(None, description="Raised At")
    cleared_at: datetime | None = Field(None, description="Cleared At")
    acked_by: str | None = Field(None, max_length=255, description="Acked By")
    alarm_state: AlarmInstanceAlarmState | None = Field(None, description="Alarm State")
    effective_from: date | None = Field(None, description="Effective From")
    effective_to: date | None = Field(None, description="Effective To")
    labels: str | None = Field(None, max_length=255, description="Labels")
    lifecycle_stage: AlarmInstanceLifecycleStage | None = Field(None, description="Lifecycle Stage")
    source_system: str | None = Field(None, max_length=255, description="Source System")


class AlarmInstanceRead(AlarmInstanceBase):
    """Alarm Instance as returned by the system of record."""

    id: UUID
    tenant_id: str
    version: int = 0
    created_at: datetime | None = None
    updated_at: datetime | None = None
    created_by: str | None = None
    updated_by: str | None = None


class AlarmInstanceSummary(ApiModel):
    id: UUID
    code: str
    name: str
    status: LifecycleStatus


class AlarmInstanceBulkFailure(ApiModel):
    index: int
    message: str


class AlarmInstanceBulkResult(ApiModel):
    succeeded: list[AlarmInstanceRead] = Field(default_factory=list)
    failures: list[AlarmInstanceBulkFailure] = Field(default_factory=list)
