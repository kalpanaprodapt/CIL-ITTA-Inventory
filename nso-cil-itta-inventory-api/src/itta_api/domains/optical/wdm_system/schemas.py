"""Pydantic models for Wdm System (Optical)."""
from __future__ import annotations

from datetime import date, datetime
from decimal import Decimal
from enum import Enum
from uuid import UUID

from pydantic import Field, field_validator, model_validator

from itta_core.models import ApiModel, LifecycleStatus, reject_control_chars


class WdmSystemBand(str, Enum):
    """Allowed values for Wdm System Band."""

    C = "C"
    L = "L"
    C_L = "C_L"


class WdmSystemLifecycleStage(str, Enum):
    """Allowed values for Wdm System Lifecycle Stage."""

    PLAN = "PLAN"
    BUILD = "BUILD"
    OPERATE = "OPERATE"
    RETIRE = "RETIRE"


class WdmSystemBase(ApiModel):
    """Attributes shared by create and read models."""

    code: str = Field(..., max_length=255, min_length=1, description="Code")
    name: str = Field(..., max_length=255, min_length=1, description="Name")
    description: str | None = Field(None, max_length=4000, description="Description")
    status: LifecycleStatus = Field(..., description="Status")
    external_ref: str | None = Field(None, max_length=255, description="External Ref")
    system_name: str = Field(..., max_length=255, min_length=1, description="System Name")
    channel_capacity: int = Field(..., ge=0, description="Channel Capacity")
    band: WdmSystemBand | None = Field(None, description="Band")
    span_count: int | None = Field(None, ge=0, description="Span Count")
    vendor_id: UUID | None = Field(None, description="Vendor Id")
    notes: str | None = Field(None, max_length=4000, description="Notes")
    effective_from: date | None = Field(None, description="Effective From")
    effective_to: date | None = Field(None, description="Effective To")
    labels: str | None = Field(None, max_length=255, description="Labels")
    lifecycle_stage: WdmSystemLifecycleStage | None = Field(None, description="Lifecycle Stage")

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

    @field_validator("system_name")
    @classmethod
    def _check_system_name(cls, value: str | None) -> str | None:
        return reject_control_chars(value, "systemName")

    @field_validator("notes")
    @classmethod
    def _check_notes(cls, value: str | None) -> str | None:
        return reject_control_chars(value, "notes")

    @field_validator("labels")
    @classmethod
    def _check_labels(cls, value: str | None) -> str | None:
        return reject_control_chars(value, "labels")

    @model_validator(mode="after")
    def _check_ranges(self) -> "WdmSystemBase":
        if self.effective_from is not None and self.effective_to is not None and self.effective_to < self.effective_from:
            raise ValueError("effective_to must not be earlier than effective_from")
        return self


class WdmSystemCreate(WdmSystemBase):
    """Payload to create a Wdm System."""


class WdmSystemUpdate(ApiModel):
    """Partial update; omitted attributes are left unchanged."""

    expected_version: int | None = Field(None, ge=0, description="Optimistic lock version")
    code: str | None = Field(None, max_length=255, min_length=1, description="Code")
    name: str | None = Field(None, max_length=255, min_length=1, description="Name")
    description: str | None = Field(None, max_length=4000, description="Description")
    status: LifecycleStatus | None = Field(None, description="Status")
    external_ref: str | None = Field(None, max_length=255, description="External Ref")
    system_name: str | None = Field(None, max_length=255, min_length=1, description="System Name")
    channel_capacity: int | None = Field(None, ge=0, description="Channel Capacity")
    band: WdmSystemBand | None = Field(None, description="Band")
    span_count: int | None = Field(None, ge=0, description="Span Count")
    vendor_id: UUID | None = Field(None, description="Vendor Id")
    notes: str | None = Field(None, max_length=4000, description="Notes")
    effective_from: date | None = Field(None, description="Effective From")
    effective_to: date | None = Field(None, description="Effective To")
    labels: str | None = Field(None, max_length=255, description="Labels")
    lifecycle_stage: WdmSystemLifecycleStage | None = Field(None, description="Lifecycle Stage")


class WdmSystemRead(WdmSystemBase):
    """Wdm System as returned by the system of record."""

    id: UUID
    tenant_id: str
    version: int = 0
    created_at: datetime | None = None
    updated_at: datetime | None = None
    created_by: str | None = None
    updated_by: str | None = None


class WdmSystemSummary(ApiModel):
    id: UUID
    code: str
    name: str
    status: LifecycleStatus


class WdmSystemBulkFailure(ApiModel):
    index: int
    message: str


class WdmSystemBulkResult(ApiModel):
    succeeded: list[WdmSystemRead] = Field(default_factory=list)
    failures: list[WdmSystemBulkFailure] = Field(default_factory=list)
