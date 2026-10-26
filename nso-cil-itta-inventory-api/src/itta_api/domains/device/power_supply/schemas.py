"""Pydantic models for Power Supply (Network Device)."""
from __future__ import annotations

from datetime import date, datetime
from decimal import Decimal
from enum import Enum
from uuid import UUID

from pydantic import Field, field_validator, model_validator

from itta_core.models import ApiModel, LifecycleStatus, reject_control_chars


class PowerSupplyCriticality(str, Enum):
    """Allowed values for Power Supply Criticality."""

    LOW = "LOW"
    MEDIUM = "MEDIUM"
    HIGH = "HIGH"
    CRITICAL = "CRITICAL"


class PowerSupplyBase(ApiModel):
    """Attributes shared by create and read models."""

    code: str = Field(..., max_length=255, min_length=1, description="Code")
    name: str = Field(..., max_length=255, min_length=1, description="Name")
    description: str | None = Field(None, max_length=4000, description="Description")
    status: LifecycleStatus = Field(..., description="Status")
    external_ref: str | None = Field(None, max_length=255, description="External Ref")
    chassis_id: UUID = Field(..., description="Chassis Id")
    slot_number: int | None = Field(None, ge=0, description="Slot Number")
    wattage: int | None = Field(None, ge=0, description="Wattage")
    feed_id: UUID | None = Field(None, description="Feed Id")
    serial_number: str | None = Field(None, max_length=255, description="Serial Number")
    efficiency: Decimal | None = Field(None, ge=0, max_digits=18, decimal_places=4, description="Efficiency")
    criticality: PowerSupplyCriticality | None = Field(None, description="Criticality")
    cost_center: str | None = Field(None, max_length=255, description="Cost Center")
    notes: str | None = Field(None, max_length=4000, description="Notes")
    effective_from: date | None = Field(None, description="Effective From")
    effective_to: date | None = Field(None, description="Effective To")

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

    @field_validator("serial_number")
    @classmethod
    def _check_serial_number(cls, value: str | None) -> str | None:
        return reject_control_chars(value, "serialNumber")

    @field_validator("cost_center")
    @classmethod
    def _check_cost_center(cls, value: str | None) -> str | None:
        return reject_control_chars(value, "costCenter")

    @field_validator("notes")
    @classmethod
    def _check_notes(cls, value: str | None) -> str | None:
        return reject_control_chars(value, "notes")

    @model_validator(mode="after")
    def _check_ranges(self) -> "PowerSupplyBase":
        if self.effective_from is not None and self.effective_to is not None and self.effective_to < self.effective_from:
            raise ValueError("effective_to must not be earlier than effective_from")
        return self


class PowerSupplyCreate(PowerSupplyBase):
    """Payload to create a Power Supply."""


class PowerSupplyUpdate(ApiModel):
    """Partial update; omitted attributes are left unchanged."""

    expected_version: int | None = Field(None, ge=0, description="Optimistic lock version")
    code: str | None = Field(None, max_length=255, min_length=1, description="Code")
    name: str | None = Field(None, max_length=255, min_length=1, description="Name")
    description: str | None = Field(None, max_length=4000, description="Description")
    status: LifecycleStatus | None = Field(None, description="Status")
    external_ref: str | None = Field(None, max_length=255, description="External Ref")
    chassis_id: UUID | None = Field(None, description="Chassis Id")
    slot_number: int | None = Field(None, ge=0, description="Slot Number")
    wattage: int | None = Field(None, ge=0, description="Wattage")
    feed_id: UUID | None = Field(None, description="Feed Id")
    serial_number: str | None = Field(None, max_length=255, description="Serial Number")
    efficiency: Decimal | None = Field(None, ge=0, max_digits=18, decimal_places=4, description="Efficiency")
    criticality: PowerSupplyCriticality | None = Field(None, description="Criticality")
    cost_center: str | None = Field(None, max_length=255, description="Cost Center")
    notes: str | None = Field(None, max_length=4000, description="Notes")
    effective_from: date | None = Field(None, description="Effective From")
    effective_to: date | None = Field(None, description="Effective To")


class PowerSupplyRead(PowerSupplyBase):
    """Power Supply as returned by the system of record."""

    id: UUID
    tenant_id: str
    version: int = 0
    created_at: datetime | None = None
    updated_at: datetime | None = None
    created_by: str | None = None
    updated_by: str | None = None


class PowerSupplySummary(ApiModel):
    id: UUID
    code: str
    name: str
    status: LifecycleStatus


class PowerSupplyBulkFailure(ApiModel):
    index: int
    message: str


class PowerSupplyBulkResult(ApiModel):
    succeeded: list[PowerSupplyRead] = Field(default_factory=list)
    failures: list[PowerSupplyBulkFailure] = Field(default_factory=list)
