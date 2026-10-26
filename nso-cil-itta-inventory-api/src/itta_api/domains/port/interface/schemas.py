"""Pydantic models for Interface (Port and Interface)."""
from __future__ import annotations

from datetime import date, datetime
from decimal import Decimal
from enum import Enum
from uuid import UUID

from pydantic import Field, field_validator, model_validator

from itta_core.models import ApiModel, LifecycleStatus, reject_control_chars


class InterfaceOperState(str, Enum):
    """Allowed values for Interface Oper State."""

    UP = "UP"
    DOWN = "DOWN"
    TESTING = "TESTING"
    UNKNOWN = "UNKNOWN"


class InterfaceCriticality(str, Enum):
    """Allowed values for Interface Criticality."""

    LOW = "LOW"
    MEDIUM = "MEDIUM"
    HIGH = "HIGH"
    CRITICAL = "CRITICAL"


class InterfaceBase(ApiModel):
    """Attributes shared by create and read models."""

    code: str = Field(..., max_length=255, min_length=1, description="Code")
    name: str = Field(..., max_length=255, min_length=1, description="Name")
    description: str | None = Field(None, max_length=4000, description="Description")
    status: LifecycleStatus = Field(..., description="Status")
    external_ref: str | None = Field(None, max_length=255, description="External Ref")
    port_id: UUID = Field(..., description="Port Id")
    if_name: str = Field(..., max_length=255, min_length=1, description="If Name")
    if_index: int | None = Field(None, ge=0, description="If Index")
    mac_address: str | None = Field(None, max_length=255, description="Mac Address")
    mtu: int | None = Field(None, ge=0, description="Mtu")
    oper_state: InterfaceOperState | None = Field(None, description="Oper State")
    owner: str | None = Field(None, max_length=255, description="Owner")
    criticality: InterfaceCriticality | None = Field(None, description="Criticality")
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

    @field_validator("if_name")
    @classmethod
    def _check_if_name(cls, value: str | None) -> str | None:
        return reject_control_chars(value, "ifName")

    @field_validator("mac_address")
    @classmethod
    def _check_mac_address(cls, value: str | None) -> str | None:
        return reject_control_chars(value, "macAddress")

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


class InterfaceCreate(InterfaceBase):
    """Payload to create a Interface."""


class InterfaceUpdate(ApiModel):
    """Partial update; omitted attributes are left unchanged."""

    expected_version: int | None = Field(None, ge=0, description="Optimistic lock version")
    code: str | None = Field(None, max_length=255, min_length=1, description="Code")
    name: str | None = Field(None, max_length=255, min_length=1, description="Name")
    description: str | None = Field(None, max_length=4000, description="Description")
    status: LifecycleStatus | None = Field(None, description="Status")
    external_ref: str | None = Field(None, max_length=255, description="External Ref")
    port_id: UUID | None = Field(None, description="Port Id")
    if_name: str | None = Field(None, max_length=255, min_length=1, description="If Name")
    if_index: int | None = Field(None, ge=0, description="If Index")
    mac_address: str | None = Field(None, max_length=255, description="Mac Address")
    mtu: int | None = Field(None, ge=0, description="Mtu")
    oper_state: InterfaceOperState | None = Field(None, description="Oper State")
    owner: str | None = Field(None, max_length=255, description="Owner")
    criticality: InterfaceCriticality | None = Field(None, description="Criticality")
    cost_center: str | None = Field(None, max_length=255, description="Cost Center")
    notes: str | None = Field(None, max_length=4000, description="Notes")
    effective_from: date | None = Field(None, description="Effective From")


class InterfaceRead(InterfaceBase):
    """Interface as returned by the system of record."""

    id: UUID
    tenant_id: str
    version: int = 0
    created_at: datetime | None = None
    updated_at: datetime | None = None
    created_by: str | None = None
    updated_by: str | None = None


class InterfaceSummary(ApiModel):
    id: UUID
    code: str
    name: str
    status: LifecycleStatus


class InterfaceBulkFailure(ApiModel):
    index: int
    message: str


class InterfaceBulkResult(ApiModel):
    succeeded: list[InterfaceRead] = Field(default_factory=list)
    failures: list[InterfaceBulkFailure] = Field(default_factory=list)
