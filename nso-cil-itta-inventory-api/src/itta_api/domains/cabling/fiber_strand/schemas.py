"""Pydantic models for Fiber Strand (Cabling)."""
from __future__ import annotations

from datetime import date, datetime
from decimal import Decimal
from enum import Enum
from uuid import UUID

from pydantic import Field, field_validator, model_validator

from itta_core.models import ApiModel, LifecycleStatus, reject_control_chars


class FiberStrandCriticality(str, Enum):
    """Allowed values for Fiber Strand Criticality."""

    LOW = "LOW"
    MEDIUM = "MEDIUM"
    HIGH = "HIGH"
    CRITICAL = "CRITICAL"


class FiberStrandBase(ApiModel):
    """Attributes shared by create and read models."""

    code: str = Field(..., max_length=255, min_length=1, description="Code")
    name: str = Field(..., max_length=255, min_length=1, description="Name")
    description: str | None = Field(None, max_length=4000, description="Description")
    status: LifecycleStatus = Field(..., description="Status")
    external_ref: str | None = Field(None, max_length=255, description="External Ref")
    cable_id: UUID = Field(..., description="Cable Id")
    strand_number: int = Field(..., ge=0, description="Strand Number")
    buffer_tube: str | None = Field(None, max_length=255, description="Buffer Tube")
    tube_color: str | None = Field(None, max_length=255, description="Tube Color")
    attenuation_db: Decimal | None = Field(None, max_digits=18, decimal_places=4, description="Attenuation Db")
    in_use: bool | None = Field(None, description="In Use")
    region: str | None = Field(None, max_length=255, description="Region")
    owner: str | None = Field(None, max_length=255, description="Owner")
    criticality: FiberStrandCriticality | None = Field(None, description="Criticality")
    cost_center: str | None = Field(None, max_length=255, description="Cost Center")
    notes: str | None = Field(None, max_length=4000, description="Notes")

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

    @field_validator("buffer_tube")
    @classmethod
    def _check_buffer_tube(cls, value: str | None) -> str | None:
        return reject_control_chars(value, "bufferTube")

    @field_validator("tube_color")
    @classmethod
    def _check_tube_color(cls, value: str | None) -> str | None:
        return reject_control_chars(value, "tubeColor")

    @field_validator("region")
    @classmethod
    def _check_region(cls, value: str | None) -> str | None:
        return reject_control_chars(value, "region")

    @field_validator("owner")
    @classmethod
    def _check_owner(cls, value: str | None) -> str | None:
        return reject_control_chars(value, "owner")

    @field_validator("cost_center")
    @classmethod
    def _check_cost_center(cls, value: str | None) -> str | None:
        return reject_control_chars(value, "costCenter")

    @field_validator("notes")
    @classmethod
    def _check_notes(cls, value: str | None) -> str | None:
        return reject_control_chars(value, "notes")


class FiberStrandCreate(FiberStrandBase):
    """Payload to create a Fiber Strand."""


class FiberStrandUpdate(ApiModel):
    """Partial update; omitted attributes are left unchanged."""

    expected_version: int | None = Field(None, ge=0, description="Optimistic lock version")
    code: str | None = Field(None, max_length=255, min_length=1, description="Code")
    name: str | None = Field(None, max_length=255, min_length=1, description="Name")
    description: str | None = Field(None, max_length=4000, description="Description")
    status: LifecycleStatus | None = Field(None, description="Status")
    external_ref: str | None = Field(None, max_length=255, description="External Ref")
    cable_id: UUID | None = Field(None, description="Cable Id")
    strand_number: int | None = Field(None, ge=0, description="Strand Number")
    buffer_tube: str | None = Field(None, max_length=255, description="Buffer Tube")
    tube_color: str | None = Field(None, max_length=255, description="Tube Color")
    attenuation_db: Decimal | None = Field(None, max_digits=18, decimal_places=4, description="Attenuation Db")
    in_use: bool | None = Field(None, description="In Use")
    region: str | None = Field(None, max_length=255, description="Region")
    owner: str | None = Field(None, max_length=255, description="Owner")
    criticality: FiberStrandCriticality | None = Field(None, description="Criticality")
    cost_center: str | None = Field(None, max_length=255, description="Cost Center")
    notes: str | None = Field(None, max_length=4000, description="Notes")


class FiberStrandRead(FiberStrandBase):
    """Fiber Strand as returned by the system of record."""

    id: UUID
    tenant_id: str
    version: int = 0
    created_at: datetime | None = None
    updated_at: datetime | None = None
    created_by: str | None = None
    updated_by: str | None = None


class FiberStrandSummary(ApiModel):
    id: UUID
    code: str
    name: str
    status: LifecycleStatus


class FiberStrandBulkFailure(ApiModel):
    index: int
    message: str


class FiberStrandBulkResult(ApiModel):
    succeeded: list[FiberStrandRead] = Field(default_factory=list)
    failures: list[FiberStrandBulkFailure] = Field(default_factory=list)
