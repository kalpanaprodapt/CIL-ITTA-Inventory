"""Pydantic models for Circuit Termination (Circuits)."""
from __future__ import annotations

from datetime import date, datetime
from decimal import Decimal
from enum import Enum
from uuid import UUID

from pydantic import Field, field_validator, model_validator

from itta_core.models import ApiModel, LifecycleStatus, reject_control_chars


class CircuitTerminationSide(str, Enum):
    """Allowed values for Circuit Termination Side."""

    A = "A"
    Z = "Z"


class CircuitTerminationBase(ApiModel):
    """Attributes shared by create and read models."""

    code: str = Field(..., max_length=255, min_length=1, description="Code")
    name: str = Field(..., max_length=255, min_length=1, description="Name")
    description: str | None = Field(None, max_length=4000, description="Description")
    status: LifecycleStatus = Field(..., description="Status")
    external_ref: str | None = Field(None, max_length=255, description="External Ref")
    circuit_id: UUID = Field(..., description="Circuit Id")
    side: CircuitTerminationSide | None = Field(None, description="Side")
    port_id: UUID = Field(..., description="Port Id")
    xconnect_id: str | None = Field(None, max_length=255, description="Xconnect Id")
    patch_info: str | None = Field(None, max_length=255, description="Patch Info")
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

    @field_validator("xconnect_id")
    @classmethod
    def _check_xconnect_id(cls, value: str | None) -> str | None:
        return reject_control_chars(value, "xconnectId")

    @field_validator("patch_info")
    @classmethod
    def _check_patch_info(cls, value: str | None) -> str | None:
        return reject_control_chars(value, "patchInfo")

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
    def _check_ranges(self) -> "CircuitTerminationBase":
        if self.effective_from is not None and self.effective_to is not None and self.effective_to < self.effective_from:
            raise ValueError("effective_to must not be earlier than effective_from")
        return self


class CircuitTerminationCreate(CircuitTerminationBase):
    """Payload to create a Circuit Termination."""


class CircuitTerminationUpdate(ApiModel):
    """Partial update; omitted attributes are left unchanged."""

    expected_version: int | None = Field(None, ge=0, description="Optimistic lock version")
    code: str | None = Field(None, max_length=255, min_length=1, description="Code")
    name: str | None = Field(None, max_length=255, min_length=1, description="Name")
    description: str | None = Field(None, max_length=4000, description="Description")
    status: LifecycleStatus | None = Field(None, description="Status")
    external_ref: str | None = Field(None, max_length=255, description="External Ref")
    circuit_id: UUID | None = Field(None, description="Circuit Id")
    side: CircuitTerminationSide | None = Field(None, description="Side")
    port_id: UUID | None = Field(None, description="Port Id")
    xconnect_id: str | None = Field(None, max_length=255, description="Xconnect Id")
    patch_info: str | None = Field(None, max_length=255, description="Patch Info")
    cost_center: str | None = Field(None, max_length=255, description="Cost Center")
    notes: str | None = Field(None, max_length=4000, description="Notes")
    effective_from: date | None = Field(None, description="Effective From")
    effective_to: date | None = Field(None, description="Effective To")
    labels: str | None = Field(None, max_length=255, description="Labels")


class CircuitTerminationRead(CircuitTerminationBase):
    """Circuit Termination as returned by the system of record."""

    id: UUID
    tenant_id: str
    version: int = 0
    created_at: datetime | None = None
    updated_at: datetime | None = None
    created_by: str | None = None
    updated_by: str | None = None


class CircuitTerminationSummary(ApiModel):
    id: UUID
    code: str
    name: str
    status: LifecycleStatus


class CircuitTerminationBulkFailure(ApiModel):
    index: int
    message: str


class CircuitTerminationBulkResult(ApiModel):
    succeeded: list[CircuitTerminationRead] = Field(default_factory=list)
    failures: list[CircuitTerminationBulkFailure] = Field(default_factory=list)
