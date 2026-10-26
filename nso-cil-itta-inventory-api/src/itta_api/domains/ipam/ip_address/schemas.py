"""Pydantic models for Ip Address (IP Management)."""
from __future__ import annotations

from datetime import date, datetime
from decimal import Decimal
from enum import Enum
from uuid import UUID

from pydantic import Field, field_validator, model_validator

from itta_core.models import ApiModel, LifecycleStatus, reject_control_chars


class IpAddressAssignmentType(str, Enum):
    """Allowed values for Ip Address Assignment Type."""

    STATIC = "STATIC"
    DHCP = "DHCP"
    RESERVED = "RESERVED"
    ANYCAST = "ANYCAST"


class IpAddressBase(ApiModel):
    """Attributes shared by create and read models."""

    code: str = Field(..., max_length=255, min_length=1, description="Code")
    name: str = Field(..., max_length=255, min_length=1, description="Name")
    description: str | None = Field(None, max_length=4000, description="Description")
    status: LifecycleStatus = Field(..., description="Status")
    external_ref: str | None = Field(None, max_length=255, description="External Ref")
    subnet_id: UUID = Field(..., description="Subnet Id")
    address: str = Field(..., max_length=255, min_length=1, description="Address")
    assigned_to_id: UUID | None = Field(None, description="Assigned To Id")
    assignment_type: IpAddressAssignmentType | None = Field(None, description="Assignment Type")
    dns_name: str | None = Field(None, max_length=255, description="Dns Name")
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

    @field_validator("address")
    @classmethod
    def _check_address(cls, value: str | None) -> str | None:
        return reject_control_chars(value, "address")

    @field_validator("dns_name")
    @classmethod
    def _check_dns_name(cls, value: str | None) -> str | None:
        return reject_control_chars(value, "dnsName")

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
    def _check_ranges(self) -> "IpAddressBase":
        if self.effective_from is not None and self.effective_to is not None and self.effective_to < self.effective_from:
            raise ValueError("effective_to must not be earlier than effective_from")
        return self


class IpAddressCreate(IpAddressBase):
    """Payload to create a Ip Address."""


class IpAddressUpdate(ApiModel):
    """Partial update; omitted attributes are left unchanged."""

    expected_version: int | None = Field(None, ge=0, description="Optimistic lock version")
    code: str | None = Field(None, max_length=255, min_length=1, description="Code")
    name: str | None = Field(None, max_length=255, min_length=1, description="Name")
    description: str | None = Field(None, max_length=4000, description="Description")
    status: LifecycleStatus | None = Field(None, description="Status")
    external_ref: str | None = Field(None, max_length=255, description="External Ref")
    subnet_id: UUID | None = Field(None, description="Subnet Id")
    address: str | None = Field(None, max_length=255, min_length=1, description="Address")
    assigned_to_id: UUID | None = Field(None, description="Assigned To Id")
    assignment_type: IpAddressAssignmentType | None = Field(None, description="Assignment Type")
    dns_name: str | None = Field(None, max_length=255, description="Dns Name")
    cost_center: str | None = Field(None, max_length=255, description="Cost Center")
    notes: str | None = Field(None, max_length=4000, description="Notes")
    effective_from: date | None = Field(None, description="Effective From")
    effective_to: date | None = Field(None, description="Effective To")
    labels: str | None = Field(None, max_length=255, description="Labels")


class IpAddressRead(IpAddressBase):
    """Ip Address as returned by the system of record."""

    id: UUID
    tenant_id: str
    version: int = 0
    created_at: datetime | None = None
    updated_at: datetime | None = None
    created_by: str | None = None
    updated_by: str | None = None


class IpAddressSummary(ApiModel):
    id: UUID
    code: str
    name: str
    status: LifecycleStatus


class IpAddressBulkFailure(ApiModel):
    index: int
    message: str


class IpAddressBulkResult(ApiModel):
    succeeded: list[IpAddressRead] = Field(default_factory=list)
    failures: list[IpAddressBulkFailure] = Field(default_factory=list)
