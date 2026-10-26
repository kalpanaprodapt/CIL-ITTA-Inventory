"""Pydantic models for Sub Interface (Port and Interface)."""
from __future__ import annotations

from datetime import date, datetime
from decimal import Decimal
from enum import Enum
from uuid import UUID

from pydantic import Field, field_validator, model_validator

from itta_core.models import ApiModel, LifecycleStatus, reject_control_chars



class SubInterfaceBase(ApiModel):
    """Attributes shared by create and read models."""

    code: str = Field(..., max_length=255, min_length=1, description="Code")
    name: str = Field(..., max_length=255, min_length=1, description="Name")
    description: str | None = Field(None, max_length=4000, description="Description")
    status: LifecycleStatus = Field(..., description="Status")
    external_ref: str | None = Field(None, max_length=255, description="External Ref")
    interface_id: UUID = Field(..., description="Interface Id")
    vlan_tag: int = Field(..., ge=0, description="Vlan Tag")
    encapsulation: str | None = Field(None, max_length=255, description="Encapsulation")
    ipv4_address: str | None = Field(None, max_length=255, description="Ipv4 Address")
    ipv6_address: str | None = Field(None, max_length=255, description="Ipv6 Address")
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

    @field_validator("encapsulation")
    @classmethod
    def _check_encapsulation(cls, value: str | None) -> str | None:
        return reject_control_chars(value, "encapsulation")

    @field_validator("ipv4_address")
    @classmethod
    def _check_ipv4_address(cls, value: str | None) -> str | None:
        return reject_control_chars(value, "ipv4Address")

    @field_validator("ipv6_address")
    @classmethod
    def _check_ipv6_address(cls, value: str | None) -> str | None:
        return reject_control_chars(value, "ipv6Address")

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
    def _check_ranges(self) -> "SubInterfaceBase":
        if self.effective_from is not None and self.effective_to is not None and self.effective_to < self.effective_from:
            raise ValueError("effective_to must not be earlier than effective_from")
        return self


class SubInterfaceCreate(SubInterfaceBase):
    """Payload to create a Sub Interface."""


class SubInterfaceUpdate(ApiModel):
    """Partial update; omitted attributes are left unchanged."""

    expected_version: int | None = Field(None, ge=0, description="Optimistic lock version")
    code: str | None = Field(None, max_length=255, min_length=1, description="Code")
    name: str | None = Field(None, max_length=255, min_length=1, description="Name")
    description: str | None = Field(None, max_length=4000, description="Description")
    status: LifecycleStatus | None = Field(None, description="Status")
    external_ref: str | None = Field(None, max_length=255, description="External Ref")
    interface_id: UUID | None = Field(None, description="Interface Id")
    vlan_tag: int | None = Field(None, ge=0, description="Vlan Tag")
    encapsulation: str | None = Field(None, max_length=255, description="Encapsulation")
    ipv4_address: str | None = Field(None, max_length=255, description="Ipv4 Address")
    ipv6_address: str | None = Field(None, max_length=255, description="Ipv6 Address")
    cost_center: str | None = Field(None, max_length=255, description="Cost Center")
    notes: str | None = Field(None, max_length=4000, description="Notes")
    effective_from: date | None = Field(None, description="Effective From")
    effective_to: date | None = Field(None, description="Effective To")
    labels: str | None = Field(None, max_length=255, description="Labels")


class SubInterfaceRead(SubInterfaceBase):
    """Sub Interface as returned by the system of record."""

    id: UUID
    tenant_id: str
    version: int = 0
    created_at: datetime | None = None
    updated_at: datetime | None = None
    created_by: str | None = None
    updated_by: str | None = None


class SubInterfaceSummary(ApiModel):
    id: UUID
    code: str
    name: str
    status: LifecycleStatus


class SubInterfaceBulkFailure(ApiModel):
    index: int
    message: str


class SubInterfaceBulkResult(ApiModel):
    succeeded: list[SubInterfaceRead] = Field(default_factory=list)
    failures: list[SubInterfaceBulkFailure] = Field(default_factory=list)
