"""Pydantic models for Power Feed (Facility)."""
from __future__ import annotations

from datetime import date, datetime
from decimal import Decimal
from enum import Enum
from uuid import UUID

from pydantic import Field, field_validator, model_validator

from itta_core.models import ApiModel, LifecycleStatus, reject_control_chars


class PowerFeedFeedType(str, Enum):
    """Allowed values for Power Feed Feed Type."""

    AC = "AC"
    DC = "DC"


class PowerFeedCriticality(str, Enum):
    """Allowed values for Power Feed Criticality."""

    LOW = "LOW"
    MEDIUM = "MEDIUM"
    HIGH = "HIGH"
    CRITICAL = "CRITICAL"


class PowerFeedBase(ApiModel):
    """Attributes shared by create and read models."""

    code: str = Field(..., max_length=255, min_length=1, description="Code")
    name: str = Field(..., max_length=255, min_length=1, description="Name")
    description: str | None = Field(None, max_length=4000, description="Description")
    status: LifecycleStatus = Field(..., description="Status")
    external_ref: str | None = Field(None, max_length=255, description="External Ref")
    room_id: UUID = Field(..., description="Room Id")
    feed_type: PowerFeedFeedType | None = Field(None, description="Feed Type")
    voltage: Decimal | None = Field(None, ge=0, max_digits=18, decimal_places=4, description="Voltage")
    amperage: Decimal | None = Field(None, ge=0, max_digits=18, decimal_places=4, description="Amperage")
    phase: str | None = Field(None, max_length=255, description="Phase")
    breaker_label: str | None = Field(None, max_length=255, description="Breaker Label")
    redundancy_group: str | None = Field(None, max_length=255, description="Redundancy Group")
    owner: str | None = Field(None, max_length=255, description="Owner")
    criticality: PowerFeedCriticality | None = Field(None, description="Criticality")
    cost_center: str | None = Field(None, max_length=255, description="Cost Center")
    notes: str | None = Field(None, max_length=4000, description="Notes")
    effective_from: date | None = Field(None, description="Effective From")

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

    @field_validator("phase")
    @classmethod
    def _check_phase(cls, value: str | None) -> str | None:
        return reject_control_chars(value, "phase")

    @field_validator("breaker_label")
    @classmethod
    def _check_breaker_label(cls, value: str | None) -> str | None:
        return reject_control_chars(value, "breakerLabel")

    @field_validator("redundancy_group")
    @classmethod
    def _check_redundancy_group(cls, value: str | None) -> str | None:
        return reject_control_chars(value, "redundancyGroup")

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


class PowerFeedCreate(PowerFeedBase):
    """Payload to create a Power Feed."""


class PowerFeedUpdate(ApiModel):
    """Partial update; omitted attributes are left unchanged."""

    expected_version: int | None = Field(None, ge=0, description="Optimistic lock version")
    code: str | None = Field(None, max_length=255, min_length=1, description="Code")
    name: str | None = Field(None, max_length=255, min_length=1, description="Name")
    description: str | None = Field(None, max_length=4000, description="Description")
    status: LifecycleStatus | None = Field(None, description="Status")
    external_ref: str | None = Field(None, max_length=255, description="External Ref")
    room_id: UUID | None = Field(None, description="Room Id")
    feed_type: PowerFeedFeedType | None = Field(None, description="Feed Type")
    voltage: Decimal | None = Field(None, ge=0, max_digits=18, decimal_places=4, description="Voltage")
    amperage: Decimal | None = Field(None, ge=0, max_digits=18, decimal_places=4, description="Amperage")
    phase: str | None = Field(None, max_length=255, description="Phase")
    breaker_label: str | None = Field(None, max_length=255, description="Breaker Label")
    redundancy_group: str | None = Field(None, max_length=255, description="Redundancy Group")
    owner: str | None = Field(None, max_length=255, description="Owner")
    criticality: PowerFeedCriticality | None = Field(None, description="Criticality")
    cost_center: str | None = Field(None, max_length=255, description="Cost Center")
    notes: str | None = Field(None, max_length=4000, description="Notes")
    effective_from: date | None = Field(None, description="Effective From")


class PowerFeedRead(PowerFeedBase):
    """Power Feed as returned by the system of record."""

    id: UUID
    tenant_id: str
    version: int = 0
    created_at: datetime | None = None
    updated_at: datetime | None = None
    created_by: str | None = None
    updated_by: str | None = None


class PowerFeedSummary(ApiModel):
    id: UUID
    code: str
    name: str
    status: LifecycleStatus


class PowerFeedBulkFailure(ApiModel):
    index: int
    message: str


class PowerFeedBulkResult(ApiModel):
    succeeded: list[PowerFeedRead] = Field(default_factory=list)
    failures: list[PowerFeedBulkFailure] = Field(default_factory=list)
