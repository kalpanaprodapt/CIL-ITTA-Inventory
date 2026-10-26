"""Pydantic models for Line Card (Network Device)."""
from __future__ import annotations

from datetime import date, datetime
from decimal import Decimal
from enum import Enum
from uuid import UUID

from pydantic import Field, field_validator, model_validator

from itta_core.models import ApiModel, LifecycleStatus, reject_control_chars


class LineCardCriticality(str, Enum):
    """Allowed values for Line Card Criticality."""

    LOW = "LOW"
    MEDIUM = "MEDIUM"
    HIGH = "HIGH"
    CRITICAL = "CRITICAL"


class LineCardBase(ApiModel):
    """Attributes shared by create and read models."""

    code: str = Field(..., max_length=255, min_length=1, description="Code")
    name: str = Field(..., max_length=255, min_length=1, description="Name")
    description: str | None = Field(None, max_length=4000, description="Description")
    status: LifecycleStatus = Field(..., description="Status")
    external_ref: str | None = Field(None, max_length=255, description="External Ref")
    chassis_id: UUID = Field(..., description="Chassis Id")
    slot_number: int = Field(..., ge=0, description="Slot Number")
    card_type: str | None = Field(None, max_length=255, description="Card Type")
    port_count: int | None = Field(None, ge=0, description="Port Count")
    serial_number: str | None = Field(None, max_length=255, description="Serial Number")
    firmware: str | None = Field(None, max_length=255, description="Firmware")
    region: str | None = Field(None, max_length=255, description="Region")
    owner: str | None = Field(None, max_length=255, description="Owner")
    criticality: LineCardCriticality | None = Field(None, description="Criticality")
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

    @field_validator("card_type")
    @classmethod
    def _check_card_type(cls, value: str | None) -> str | None:
        return reject_control_chars(value, "cardType")

    @field_validator("serial_number")
    @classmethod
    def _check_serial_number(cls, value: str | None) -> str | None:
        return reject_control_chars(value, "serialNumber")

    @field_validator("firmware")
    @classmethod
    def _check_firmware(cls, value: str | None) -> str | None:
        return reject_control_chars(value, "firmware")

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


class LineCardCreate(LineCardBase):
    """Payload to create a Line Card."""


class LineCardUpdate(ApiModel):
    """Partial update; omitted attributes are left unchanged."""

    expected_version: int | None = Field(None, ge=0, description="Optimistic lock version")
    code: str | None = Field(None, max_length=255, min_length=1, description="Code")
    name: str | None = Field(None, max_length=255, min_length=1, description="Name")
    description: str | None = Field(None, max_length=4000, description="Description")
    status: LifecycleStatus | None = Field(None, description="Status")
    external_ref: str | None = Field(None, max_length=255, description="External Ref")
    chassis_id: UUID | None = Field(None, description="Chassis Id")
    slot_number: int | None = Field(None, ge=0, description="Slot Number")
    card_type: str | None = Field(None, max_length=255, description="Card Type")
    port_count: int | None = Field(None, ge=0, description="Port Count")
    serial_number: str | None = Field(None, max_length=255, description="Serial Number")
    firmware: str | None = Field(None, max_length=255, description="Firmware")
    region: str | None = Field(None, max_length=255, description="Region")
    owner: str | None = Field(None, max_length=255, description="Owner")
    criticality: LineCardCriticality | None = Field(None, description="Criticality")
    cost_center: str | None = Field(None, max_length=255, description="Cost Center")
    notes: str | None = Field(None, max_length=4000, description="Notes")


class LineCardRead(LineCardBase):
    """Line Card as returned by the system of record."""

    id: UUID
    tenant_id: str
    version: int = 0
    created_at: datetime | None = None
    updated_at: datetime | None = None
    created_by: str | None = None
    updated_by: str | None = None


class LineCardSummary(ApiModel):
    id: UUID
    code: str
    name: str
    status: LifecycleStatus


class LineCardBulkFailure(ApiModel):
    index: int
    message: str


class LineCardBulkResult(ApiModel):
    succeeded: list[LineCardRead] = Field(default_factory=list)
    failures: list[LineCardBulkFailure] = Field(default_factory=list)
