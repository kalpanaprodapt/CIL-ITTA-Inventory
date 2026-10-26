"""Pydantic models for Audit Record (Compliance and Audit)."""
from __future__ import annotations

from datetime import date, datetime
from decimal import Decimal
from enum import Enum
from uuid import UUID

from pydantic import Field, field_validator, model_validator

from itta_core.models import ApiModel, LifecycleStatus, reject_control_chars


class AuditRecordLifecycleStage(str, Enum):
    """Allowed values for Audit Record Lifecycle Stage."""

    PLAN = "PLAN"
    BUILD = "BUILD"
    OPERATE = "OPERATE"
    RETIRE = "RETIRE"


class AuditRecordBase(ApiModel):
    """Attributes shared by create and read models."""

    code: str = Field(..., max_length=255, min_length=1, description="Code")
    name: str = Field(..., max_length=255, min_length=1, description="Name")
    description: str | None = Field(None, max_length=4000, description="Description")
    status: LifecycleStatus = Field(..., description="Status")
    external_ref: str | None = Field(None, max_length=255, description="External Ref")
    actor: str = Field(..., max_length=255, min_length=1, description="Actor")
    action: str = Field(..., max_length=255, min_length=1, description="Action")
    entity_ref: str = Field(..., max_length=255, min_length=1, description="Entity Ref")
    occurred_at: datetime = Field(..., description="Occurred At")
    before_state: str | None = Field(None, max_length=4000, description="Before State")
    after_state: str | None = Field(None, max_length=4000, description="After State")
    notes: str | None = Field(None, max_length=4000, description="Notes")
    effective_from: date | None = Field(None, description="Effective From")
    effective_to: date | None = Field(None, description="Effective To")
    labels: str | None = Field(None, max_length=255, description="Labels")
    lifecycle_stage: AuditRecordLifecycleStage | None = Field(None, description="Lifecycle Stage")

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

    @field_validator("actor")
    @classmethod
    def _check_actor(cls, value: str | None) -> str | None:
        return reject_control_chars(value, "actor")

    @field_validator("action")
    @classmethod
    def _check_action(cls, value: str | None) -> str | None:
        return reject_control_chars(value, "action")

    @field_validator("entity_ref")
    @classmethod
    def _check_entity_ref(cls, value: str | None) -> str | None:
        return reject_control_chars(value, "entityRef")

    @field_validator("before_state")
    @classmethod
    def _check_before_state(cls, value: str | None) -> str | None:
        return reject_control_chars(value, "beforeState")

    @field_validator("after_state")
    @classmethod
    def _check_after_state(cls, value: str | None) -> str | None:
        return reject_control_chars(value, "afterState")

    @field_validator("notes")
    @classmethod
    def _check_notes(cls, value: str | None) -> str | None:
        return reject_control_chars(value, "notes")

    @field_validator("labels")
    @classmethod
    def _check_labels(cls, value: str | None) -> str | None:
        return reject_control_chars(value, "labels")

    @model_validator(mode="after")
    def _check_ranges(self) -> "AuditRecordBase":
        if self.effective_from is not None and self.effective_to is not None and self.effective_to < self.effective_from:
            raise ValueError("effective_to must not be earlier than effective_from")
        return self


class AuditRecordCreate(AuditRecordBase):
    """Payload to create a Audit Record."""


class AuditRecordUpdate(ApiModel):
    """Partial update; omitted attributes are left unchanged."""

    expected_version: int | None = Field(None, ge=0, description="Optimistic lock version")
    code: str | None = Field(None, max_length=255, min_length=1, description="Code")
    name: str | None = Field(None, max_length=255, min_length=1, description="Name")
    description: str | None = Field(None, max_length=4000, description="Description")
    status: LifecycleStatus | None = Field(None, description="Status")
    external_ref: str | None = Field(None, max_length=255, description="External Ref")
    actor: str | None = Field(None, max_length=255, min_length=1, description="Actor")
    action: str | None = Field(None, max_length=255, min_length=1, description="Action")
    entity_ref: str | None = Field(None, max_length=255, min_length=1, description="Entity Ref")
    occurred_at: datetime | None = Field(None, description="Occurred At")
    before_state: str | None = Field(None, max_length=4000, description="Before State")
    after_state: str | None = Field(None, max_length=4000, description="After State")
    notes: str | None = Field(None, max_length=4000, description="Notes")
    effective_from: date | None = Field(None, description="Effective From")
    effective_to: date | None = Field(None, description="Effective To")
    labels: str | None = Field(None, max_length=255, description="Labels")
    lifecycle_stage: AuditRecordLifecycleStage | None = Field(None, description="Lifecycle Stage")


class AuditRecordRead(AuditRecordBase):
    """Audit Record as returned by the system of record."""

    id: UUID
    tenant_id: str
    version: int = 0
    created_at: datetime | None = None
    updated_at: datetime | None = None
    created_by: str | None = None
    updated_by: str | None = None


class AuditRecordSummary(ApiModel):
    id: UUID
    code: str
    name: str
    status: LifecycleStatus


class AuditRecordBulkFailure(ApiModel):
    index: int
    message: str


class AuditRecordBulkResult(ApiModel):
    succeeded: list[AuditRecordRead] = Field(default_factory=list)
    failures: list[AuditRecordBulkFailure] = Field(default_factory=list)
