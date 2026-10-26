"""Pydantic models for Patch Port (Cabling)."""
from __future__ import annotations

from datetime import date, datetime
from decimal import Decimal
from enum import Enum
from uuid import UUID

from pydantic import Field, field_validator, model_validator

from itta_core.models import ApiModel, LifecycleStatus, reject_control_chars


class PatchPortLifecycleStage(str, Enum):
    """Allowed values for Patch Port Lifecycle Stage."""

    PLAN = "PLAN"
    BUILD = "BUILD"
    OPERATE = "OPERATE"
    RETIRE = "RETIRE"


class PatchPortBase(ApiModel):
    """Attributes shared by create and read models."""

    code: str = Field(..., max_length=255, min_length=1, description="Code")
    name: str = Field(..., max_length=255, min_length=1, description="Name")
    description: str | None = Field(None, max_length=4000, description="Description")
    status: LifecycleStatus = Field(..., description="Status")
    external_ref: str | None = Field(None, max_length=255, description="External Ref")
    panel_id: UUID = Field(..., description="Panel Id")
    port_number: int = Field(..., ge=0, description="Port Number")
    connected_cable_id: UUID | None = Field(None, description="Connected Cable Id")
    polarity: str | None = Field(None, max_length=255, description="Polarity")
    effective_to: date | None = Field(None, description="Effective To")
    labels: str | None = Field(None, max_length=255, description="Labels")
    lifecycle_stage: PatchPortLifecycleStage | None = Field(None, description="Lifecycle Stage")
    source_system: str | None = Field(None, max_length=255, description="Source System")
    region: str | None = Field(None, max_length=255, description="Region")

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

    @field_validator("polarity")
    @classmethod
    def _check_polarity(cls, value: str | None) -> str | None:
        return reject_control_chars(value, "polarity")

    @field_validator("labels")
    @classmethod
    def _check_labels(cls, value: str | None) -> str | None:
        return reject_control_chars(value, "labels")

    @field_validator("source_system")
    @classmethod
    def _check_source_system(cls, value: str | None) -> str | None:
        return reject_control_chars(value, "sourceSystem")

    @field_validator("region")
    @classmethod
    def _check_region(cls, value: str | None) -> str | None:
        return reject_control_chars(value, "region")


class PatchPortCreate(PatchPortBase):
    """Payload to create a Patch Port."""


class PatchPortUpdate(ApiModel):
    """Partial update; omitted attributes are left unchanged."""

    expected_version: int | None = Field(None, ge=0, description="Optimistic lock version")
    code: str | None = Field(None, max_length=255, min_length=1, description="Code")
    name: str | None = Field(None, max_length=255, min_length=1, description="Name")
    description: str | None = Field(None, max_length=4000, description="Description")
    status: LifecycleStatus | None = Field(None, description="Status")
    external_ref: str | None = Field(None, max_length=255, description="External Ref")
    panel_id: UUID | None = Field(None, description="Panel Id")
    port_number: int | None = Field(None, ge=0, description="Port Number")
    connected_cable_id: UUID | None = Field(None, description="Connected Cable Id")
    polarity: str | None = Field(None, max_length=255, description="Polarity")
    effective_to: date | None = Field(None, description="Effective To")
    labels: str | None = Field(None, max_length=255, description="Labels")
    lifecycle_stage: PatchPortLifecycleStage | None = Field(None, description="Lifecycle Stage")
    source_system: str | None = Field(None, max_length=255, description="Source System")
    region: str | None = Field(None, max_length=255, description="Region")


class PatchPortRead(PatchPortBase):
    """Patch Port as returned by the system of record."""

    id: UUID
    tenant_id: str
    version: int = 0
    created_at: datetime | None = None
    updated_at: datetime | None = None
    created_by: str | None = None
    updated_by: str | None = None


class PatchPortSummary(ApiModel):
    id: UUID
    code: str
    name: str
    status: LifecycleStatus


class PatchPortBulkFailure(ApiModel):
    index: int
    message: str


class PatchPortBulkResult(ApiModel):
    succeeded: list[PatchPortRead] = Field(default_factory=list)
    failures: list[PatchPortBulkFailure] = Field(default_factory=list)
