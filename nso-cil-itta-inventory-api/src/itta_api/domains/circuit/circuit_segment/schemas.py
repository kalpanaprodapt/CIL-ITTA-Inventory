"""Pydantic models for Circuit Segment (Circuits)."""
from __future__ import annotations

from datetime import date, datetime
from decimal import Decimal
from enum import Enum
from uuid import UUID

from pydantic import Field, field_validator, model_validator

from itta_core.models import ApiModel, LifecycleStatus, reject_control_chars


class CircuitSegmentCriticality(str, Enum):
    """Allowed values for Circuit Segment Criticality."""

    LOW = "LOW"
    MEDIUM = "MEDIUM"
    HIGH = "HIGH"
    CRITICAL = "CRITICAL"


class CircuitSegmentBase(ApiModel):
    """Attributes shared by create and read models."""

    code: str = Field(..., max_length=255, min_length=1, description="Code")
    name: str = Field(..., max_length=255, min_length=1, description="Name")
    description: str | None = Field(None, max_length=4000, description="Description")
    status: LifecycleStatus = Field(..., description="Status")
    external_ref: str | None = Field(None, max_length=255, description="External Ref")
    circuit_id: UUID = Field(..., description="Circuit Id")
    segment_order: int = Field(..., ge=0, description="Segment Order")
    a_termination_id: UUID | None = Field(None, description="A Termination Id")
    z_termination_id: UUID | None = Field(None, description="Z Termination Id")
    latency_ms: Decimal | None = Field(None, ge=0, max_digits=18, decimal_places=4, description="Latency Ms")
    source_system: str | None = Field(None, max_length=255, description="Source System")
    region: str | None = Field(None, max_length=255, description="Region")
    owner: str | None = Field(None, max_length=255, description="Owner")
    criticality: CircuitSegmentCriticality | None = Field(None, description="Criticality")
    cost_center: str | None = Field(None, max_length=255, description="Cost Center")

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

    @field_validator("source_system")
    @classmethod
    def _check_source_system(cls, value: str | None) -> str | None:
        return reject_control_chars(value, "sourceSystem")

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


class CircuitSegmentCreate(CircuitSegmentBase):
    """Payload to create a Circuit Segment."""


class CircuitSegmentUpdate(ApiModel):
    """Partial update; omitted attributes are left unchanged."""

    expected_version: int | None = Field(None, ge=0, description="Optimistic lock version")
    code: str | None = Field(None, max_length=255, min_length=1, description="Code")
    name: str | None = Field(None, max_length=255, min_length=1, description="Name")
    description: str | None = Field(None, max_length=4000, description="Description")
    status: LifecycleStatus | None = Field(None, description="Status")
    external_ref: str | None = Field(None, max_length=255, description="External Ref")
    circuit_id: UUID | None = Field(None, description="Circuit Id")
    segment_order: int | None = Field(None, ge=0, description="Segment Order")
    a_termination_id: UUID | None = Field(None, description="A Termination Id")
    z_termination_id: UUID | None = Field(None, description="Z Termination Id")
    latency_ms: Decimal | None = Field(None, ge=0, max_digits=18, decimal_places=4, description="Latency Ms")
    source_system: str | None = Field(None, max_length=255, description="Source System")
    region: str | None = Field(None, max_length=255, description="Region")
    owner: str | None = Field(None, max_length=255, description="Owner")
    criticality: CircuitSegmentCriticality | None = Field(None, description="Criticality")
    cost_center: str | None = Field(None, max_length=255, description="Cost Center")


class CircuitSegmentRead(CircuitSegmentBase):
    """Circuit Segment as returned by the system of record."""

    id: UUID
    tenant_id: str
    version: int = 0
    created_at: datetime | None = None
    updated_at: datetime | None = None
    created_by: str | None = None
    updated_by: str | None = None


class CircuitSegmentSummary(ApiModel):
    id: UUID
    code: str
    name: str
    status: LifecycleStatus


class CircuitSegmentBulkFailure(ApiModel):
    index: int
    message: str


class CircuitSegmentBulkResult(ApiModel):
    succeeded: list[CircuitSegmentRead] = Field(default_factory=list)
    failures: list[CircuitSegmentBulkFailure] = Field(default_factory=list)
