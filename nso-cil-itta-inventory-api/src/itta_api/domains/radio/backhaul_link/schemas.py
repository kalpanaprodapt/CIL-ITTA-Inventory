"""Pydantic models for Backhaul Link (Radio and Mobile)."""
from __future__ import annotations

from datetime import date, datetime
from decimal import Decimal
from enum import Enum
from uuid import UUID

from pydantic import Field, field_validator, model_validator

from itta_core.models import ApiModel, LifecycleStatus, reject_control_chars


class BackhaulLinkLinkMedium(str, Enum):
    """Allowed values for Backhaul Link Link Medium."""

    FIBER = "FIBER"
    MICROWAVE = "MICROWAVE"
    SATELLITE = "SATELLITE"


class BackhaulLinkBase(ApiModel):
    """Attributes shared by create and read models."""

    code: str = Field(..., max_length=255, min_length=1, description="Code")
    name: str = Field(..., max_length=255, min_length=1, description="Name")
    description: str | None = Field(None, max_length=4000, description="Description")
    status: LifecycleStatus = Field(..., description="Status")
    external_ref: str | None = Field(None, max_length=255, description="External Ref")
    base_station_id: UUID = Field(..., description="Base Station Id")
    link_medium: BackhaulLinkLinkMedium | None = Field(None, description="Link Medium")
    capacity_mbps: int = Field(..., ge=0, description="Capacity Mbps")
    latency_ms: Decimal | None = Field(None, ge=0, max_digits=18, decimal_places=4, description="Latency Ms")
    circuit_ref: str | None = Field(None, max_length=255, description="Circuit Ref")
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

    @field_validator("circuit_ref")
    @classmethod
    def _check_circuit_ref(cls, value: str | None) -> str | None:
        return reject_control_chars(value, "circuitRef")

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
    def _check_ranges(self) -> "BackhaulLinkBase":
        if self.effective_from is not None and self.effective_to is not None and self.effective_to < self.effective_from:
            raise ValueError("effective_to must not be earlier than effective_from")
        return self


class BackhaulLinkCreate(BackhaulLinkBase):
    """Payload to create a Backhaul Link."""


class BackhaulLinkUpdate(ApiModel):
    """Partial update; omitted attributes are left unchanged."""

    expected_version: int | None = Field(None, ge=0, description="Optimistic lock version")
    code: str | None = Field(None, max_length=255, min_length=1, description="Code")
    name: str | None = Field(None, max_length=255, min_length=1, description="Name")
    description: str | None = Field(None, max_length=4000, description="Description")
    status: LifecycleStatus | None = Field(None, description="Status")
    external_ref: str | None = Field(None, max_length=255, description="External Ref")
    base_station_id: UUID | None = Field(None, description="Base Station Id")
    link_medium: BackhaulLinkLinkMedium | None = Field(None, description="Link Medium")
    capacity_mbps: int | None = Field(None, ge=0, description="Capacity Mbps")
    latency_ms: Decimal | None = Field(None, ge=0, max_digits=18, decimal_places=4, description="Latency Ms")
    circuit_ref: str | None = Field(None, max_length=255, description="Circuit Ref")
    cost_center: str | None = Field(None, max_length=255, description="Cost Center")
    notes: str | None = Field(None, max_length=4000, description="Notes")
    effective_from: date | None = Field(None, description="Effective From")
    effective_to: date | None = Field(None, description="Effective To")
    labels: str | None = Field(None, max_length=255, description="Labels")


class BackhaulLinkRead(BackhaulLinkBase):
    """Backhaul Link as returned by the system of record."""

    id: UUID
    tenant_id: str
    version: int = 0
    created_at: datetime | None = None
    updated_at: datetime | None = None
    created_by: str | None = None
    updated_by: str | None = None


class BackhaulLinkSummary(ApiModel):
    id: UUID
    code: str
    name: str
    status: LifecycleStatus


class BackhaulLinkBulkFailure(ApiModel):
    index: int
    message: str


class BackhaulLinkBulkResult(ApiModel):
    succeeded: list[BackhaulLinkRead] = Field(default_factory=list)
    failures: list[BackhaulLinkBulkFailure] = Field(default_factory=list)
