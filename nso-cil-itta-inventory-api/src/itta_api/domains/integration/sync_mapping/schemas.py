"""Pydantic models for Sync Mapping (Integration Layer)."""
from __future__ import annotations

from datetime import date, datetime
from decimal import Decimal
from enum import Enum
from uuid import UUID

from pydantic import Field, field_validator, model_validator

from itta_core.models import ApiModel, LifecycleStatus, reject_control_chars



class SyncMappingBase(ApiModel):
    """Attributes shared by create and read models."""

    code: str = Field(..., max_length=255, min_length=1, description="Code")
    name: str = Field(..., max_length=255, min_length=1, description="Name")
    description: str | None = Field(None, max_length=4000, description="Description")
    status: LifecycleStatus = Field(..., description="Status")
    external_ref: str | None = Field(None, max_length=255, description="External Ref")
    job_id: UUID = Field(..., description="Job Id")
    source_field: str = Field(..., max_length=255, min_length=1, description="Source Field")
    target_field: str = Field(..., max_length=255, min_length=1, description="Target Field")
    transform: str | None = Field(None, max_length=255, description="Transform")
    required: bool | None = Field(None, description="Required")
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

    @field_validator("source_field")
    @classmethod
    def _check_source_field(cls, value: str | None) -> str | None:
        return reject_control_chars(value, "sourceField")

    @field_validator("target_field")
    @classmethod
    def _check_target_field(cls, value: str | None) -> str | None:
        return reject_control_chars(value, "targetField")

    @field_validator("transform")
    @classmethod
    def _check_transform(cls, value: str | None) -> str | None:
        return reject_control_chars(value, "transform")

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
    def _check_ranges(self) -> "SyncMappingBase":
        if self.effective_from is not None and self.effective_to is not None and self.effective_to < self.effective_from:
            raise ValueError("effective_to must not be earlier than effective_from")
        return self


class SyncMappingCreate(SyncMappingBase):
    """Payload to create a Sync Mapping."""


class SyncMappingUpdate(ApiModel):
    """Partial update; omitted attributes are left unchanged."""

    expected_version: int | None = Field(None, ge=0, description="Optimistic lock version")
    code: str | None = Field(None, max_length=255, min_length=1, description="Code")
    name: str | None = Field(None, max_length=255, min_length=1, description="Name")
    description: str | None = Field(None, max_length=4000, description="Description")
    status: LifecycleStatus | None = Field(None, description="Status")
    external_ref: str | None = Field(None, max_length=255, description="External Ref")
    job_id: UUID | None = Field(None, description="Job Id")
    source_field: str | None = Field(None, max_length=255, min_length=1, description="Source Field")
    target_field: str | None = Field(None, max_length=255, min_length=1, description="Target Field")
    transform: str | None = Field(None, max_length=255, description="Transform")
    required: bool | None = Field(None, description="Required")
    cost_center: str | None = Field(None, max_length=255, description="Cost Center")
    notes: str | None = Field(None, max_length=4000, description="Notes")
    effective_from: date | None = Field(None, description="Effective From")
    effective_to: date | None = Field(None, description="Effective To")
    labels: str | None = Field(None, max_length=255, description="Labels")


class SyncMappingRead(SyncMappingBase):
    """Sync Mapping as returned by the system of record."""

    id: UUID
    tenant_id: str
    version: int = 0
    created_at: datetime | None = None
    updated_at: datetime | None = None
    created_by: str | None = None
    updated_by: str | None = None


class SyncMappingSummary(ApiModel):
    id: UUID
    code: str
    name: str
    status: LifecycleStatus


class SyncMappingBulkFailure(ApiModel):
    index: int
    message: str


class SyncMappingBulkResult(ApiModel):
    succeeded: list[SyncMappingRead] = Field(default_factory=list)
    failures: list[SyncMappingBulkFailure] = Field(default_factory=list)
