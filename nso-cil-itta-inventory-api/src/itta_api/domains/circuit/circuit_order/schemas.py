"""Pydantic models for Circuit Order (Circuits)."""
from __future__ import annotations

from datetime import date, datetime
from decimal import Decimal
from enum import Enum
from uuid import UUID

from pydantic import Field, field_validator, model_validator

from itta_core.models import ApiModel, LifecycleStatus, reject_control_chars


class CircuitOrderOrderStatus(str, Enum):
    """Allowed values for Circuit Order Order Status."""

    DRAFT = "DRAFT"
    SUBMITTED = "SUBMITTED"
    CONFIRMED = "CONFIRMED"
    DELIVERED = "DELIVERED"
    CANCELLED = "CANCELLED"


class CircuitOrderLifecycleStage(str, Enum):
    """Allowed values for Circuit Order Lifecycle Stage."""

    PLAN = "PLAN"
    BUILD = "BUILD"
    OPERATE = "OPERATE"
    RETIRE = "RETIRE"


class CircuitOrderBase(ApiModel):
    """Attributes shared by create and read models."""

    code: str = Field(..., max_length=255, min_length=1, description="Code")
    name: str = Field(..., max_length=255, min_length=1, description="Name")
    description: str | None = Field(None, max_length=4000, description="Description")
    status: LifecycleStatus = Field(..., description="Status")
    external_ref: str | None = Field(None, max_length=255, description="External Ref")
    circuit_id: UUID | None = Field(None, description="Circuit Id")
    order_number: str = Field(..., max_length=255, min_length=1, description="Order Number")
    ordered_on: date = Field(..., description="Ordered On")
    requested_date: date | None = Field(None, description="Requested Date")
    committed_date: date | None = Field(None, description="Committed Date")
    order_status: CircuitOrderOrderStatus | None = Field(None, description="Order Status")
    effective_from: date | None = Field(None, description="Effective From")
    effective_to: date | None = Field(None, description="Effective To")
    labels: str | None = Field(None, max_length=255, description="Labels")
    lifecycle_stage: CircuitOrderLifecycleStage | None = Field(None, description="Lifecycle Stage")
    source_system: str | None = Field(None, max_length=255, description="Source System")

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

    @field_validator("order_number")
    @classmethod
    def _check_order_number(cls, value: str | None) -> str | None:
        return reject_control_chars(value, "orderNumber")

    @field_validator("labels")
    @classmethod
    def _check_labels(cls, value: str | None) -> str | None:
        return reject_control_chars(value, "labels")

    @field_validator("source_system")
    @classmethod
    def _check_source_system(cls, value: str | None) -> str | None:
        return reject_control_chars(value, "sourceSystem")

    @model_validator(mode="after")
    def _check_ranges(self) -> "CircuitOrderBase":
        if self.effective_from is not None and self.effective_to is not None and self.effective_to < self.effective_from:
            raise ValueError("effective_to must not be earlier than effective_from")
        return self


class CircuitOrderCreate(CircuitOrderBase):
    """Payload to create a Circuit Order."""


class CircuitOrderUpdate(ApiModel):
    """Partial update; omitted attributes are left unchanged."""

    expected_version: int | None = Field(None, ge=0, description="Optimistic lock version")
    code: str | None = Field(None, max_length=255, min_length=1, description="Code")
    name: str | None = Field(None, max_length=255, min_length=1, description="Name")
    description: str | None = Field(None, max_length=4000, description="Description")
    status: LifecycleStatus | None = Field(None, description="Status")
    external_ref: str | None = Field(None, max_length=255, description="External Ref")
    circuit_id: UUID | None = Field(None, description="Circuit Id")
    order_number: str | None = Field(None, max_length=255, min_length=1, description="Order Number")
    ordered_on: date | None = Field(None, description="Ordered On")
    requested_date: date | None = Field(None, description="Requested Date")
    committed_date: date | None = Field(None, description="Committed Date")
    order_status: CircuitOrderOrderStatus | None = Field(None, description="Order Status")
    effective_from: date | None = Field(None, description="Effective From")
    effective_to: date | None = Field(None, description="Effective To")
    labels: str | None = Field(None, max_length=255, description="Labels")
    lifecycle_stage: CircuitOrderLifecycleStage | None = Field(None, description="Lifecycle Stage")
    source_system: str | None = Field(None, max_length=255, description="Source System")


class CircuitOrderRead(CircuitOrderBase):
    """Circuit Order as returned by the system of record."""

    id: UUID
    tenant_id: str
    version: int = 0
    created_at: datetime | None = None
    updated_at: datetime | None = None
    created_by: str | None = None
    updated_by: str | None = None


class CircuitOrderSummary(ApiModel):
    id: UUID
    code: str
    name: str
    status: LifecycleStatus


class CircuitOrderBulkFailure(ApiModel):
    index: int
    message: str


class CircuitOrderBulkResult(ApiModel):
    succeeded: list[CircuitOrderRead] = Field(default_factory=list)
    failures: list[CircuitOrderBulkFailure] = Field(default_factory=list)
