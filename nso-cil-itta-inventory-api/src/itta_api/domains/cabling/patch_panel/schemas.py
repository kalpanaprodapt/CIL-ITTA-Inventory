"""Pydantic models for Patch Panel (Cabling)."""
from __future__ import annotations

from datetime import date, datetime
from decimal import Decimal
from enum import Enum
from uuid import UUID

from pydantic import Field, field_validator, model_validator

from itta_core.models import ApiModel, LifecycleStatus, reject_control_chars


class PatchPanelPanelType(str, Enum):
    """Allowed values for Patch Panel Panel Type."""

    COPPER = "COPPER"
    FIBER = "FIBER"


class PatchPanelLifecycleStage(str, Enum):
    """Allowed values for Patch Panel Lifecycle Stage."""

    PLAN = "PLAN"
    BUILD = "BUILD"
    OPERATE = "OPERATE"
    RETIRE = "RETIRE"


class PatchPanelBase(ApiModel):
    """Attributes shared by create and read models."""

    code: str = Field(..., max_length=255, min_length=1, description="Code")
    name: str = Field(..., max_length=255, min_length=1, description="Name")
    description: str | None = Field(None, max_length=4000, description="Description")
    status: LifecycleStatus = Field(..., description="Status")
    external_ref: str | None = Field(None, max_length=255, description="External Ref")
    room_id: UUID = Field(..., description="Room Id")
    rack_id: UUID | None = Field(None, description="Rack Id")
    panel_type: PatchPanelPanelType | None = Field(None, description="Panel Type")
    port_count: int = Field(..., ge=0, description="Port Count")
    label: str | None = Field(None, max_length=255, description="Label")
    notes: str | None = Field(None, max_length=4000, description="Notes")
    effective_from: date | None = Field(None, description="Effective From")
    effective_to: date | None = Field(None, description="Effective To")
    labels: str | None = Field(None, max_length=255, description="Labels")
    lifecycle_stage: PatchPanelLifecycleStage | None = Field(None, description="Lifecycle Stage")

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

    @field_validator("label")
    @classmethod
    def _check_label(cls, value: str | None) -> str | None:
        return reject_control_chars(value, "label")

    @field_validator("notes")
    @classmethod
    def _check_notes(cls, value: str | None) -> str | None:
        return reject_control_chars(value, "notes")

    @field_validator("labels")
    @classmethod
    def _check_labels(cls, value: str | None) -> str | None:
        return reject_control_chars(value, "labels")

    @model_validator(mode="after")
    def _check_ranges(self) -> "PatchPanelBase":
        if self.effective_from is not None and self.effective_to is not None and self.effective_to < self.effective_from:
            raise ValueError("effective_to must not be earlier than effective_from")
        return self


class PatchPanelCreate(PatchPanelBase):
    """Payload to create a Patch Panel."""


class PatchPanelUpdate(ApiModel):
    """Partial update; omitted attributes are left unchanged."""

    expected_version: int | None = Field(None, ge=0, description="Optimistic lock version")
    code: str | None = Field(None, max_length=255, min_length=1, description="Code")
    name: str | None = Field(None, max_length=255, min_length=1, description="Name")
    description: str | None = Field(None, max_length=4000, description="Description")
    status: LifecycleStatus | None = Field(None, description="Status")
    external_ref: str | None = Field(None, max_length=255, description="External Ref")
    room_id: UUID | None = Field(None, description="Room Id")
    rack_id: UUID | None = Field(None, description="Rack Id")
    panel_type: PatchPanelPanelType | None = Field(None, description="Panel Type")
    port_count: int | None = Field(None, ge=0, description="Port Count")
    label: str | None = Field(None, max_length=255, description="Label")
    notes: str | None = Field(None, max_length=4000, description="Notes")
    effective_from: date | None = Field(None, description="Effective From")
    effective_to: date | None = Field(None, description="Effective To")
    labels: str | None = Field(None, max_length=255, description="Labels")
    lifecycle_stage: PatchPanelLifecycleStage | None = Field(None, description="Lifecycle Stage")


class PatchPanelRead(PatchPanelBase):
    """Patch Panel as returned by the system of record."""

    id: UUID
    tenant_id: str
    version: int = 0
    created_at: datetime | None = None
    updated_at: datetime | None = None
    created_by: str | None = None
    updated_by: str | None = None


class PatchPanelSummary(ApiModel):
    id: UUID
    code: str
    name: str
    status: LifecycleStatus


class PatchPanelBulkFailure(ApiModel):
    index: int
    message: str


class PatchPanelBulkResult(ApiModel):
    succeeded: list[PatchPanelRead] = Field(default_factory=list)
    failures: list[PatchPanelBulkFailure] = Field(default_factory=list)
