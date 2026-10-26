"""Pydantic models for Virtual Machine (Virtual and Cloud)."""
from __future__ import annotations

from datetime import date, datetime
from decimal import Decimal
from enum import Enum
from uuid import UUID

from pydantic import Field, field_validator, model_validator

from itta_core.models import ApiModel, LifecycleStatus, reject_control_chars


class VirtualMachinePowerState(str, Enum):
    """Allowed values for Virtual Machine Power State."""

    ON = "ON"
    OFF = "OFF"
    SUSPENDED = "SUSPENDED"


class VirtualMachineCriticality(str, Enum):
    """Allowed values for Virtual Machine Criticality."""

    LOW = "LOW"
    MEDIUM = "MEDIUM"
    HIGH = "HIGH"
    CRITICAL = "CRITICAL"


class VirtualMachineBase(ApiModel):
    """Attributes shared by create and read models."""

    code: str = Field(..., max_length=255, min_length=1, description="Code")
    name: str = Field(..., max_length=255, min_length=1, description="Name")
    description: str | None = Field(None, max_length=4000, description="Description")
    status: LifecycleStatus = Field(..., description="Status")
    external_ref: str | None = Field(None, max_length=255, description="External Ref")
    hypervisor_id: UUID = Field(..., description="Hypervisor Id")
    vm_name: str = Field(..., max_length=255, min_length=1, description="Vm Name")
    vcpu: int = Field(..., ge=0, description="Vcpu")
    memory_gb: int = Field(..., ge=0, description="Memory Gb")
    disk_gb: int | None = Field(None, ge=0, description="Disk Gb")
    power_state: VirtualMachinePowerState | None = Field(None, description="Power State")
    criticality: VirtualMachineCriticality | None = Field(None, description="Criticality")
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

    @field_validator("vm_name")
    @classmethod
    def _check_vm_name(cls, value: str | None) -> str | None:
        return reject_control_chars(value, "vmName")

    @field_validator("cost_center")
    @classmethod
    def _check_cost_center(cls, value: str | None) -> str | None:
        return reject_control_chars(value, "costCenter")

    @field_validator("notes")
    @classmethod
    def _check_notes(cls, value: str | None) -> str | None:
        return reject_control_chars(value, "notes")

    @model_validator(mode="after")
    def _check_ranges(self) -> "VirtualMachineBase":
        if self.effective_from is not None and self.effective_to is not None and self.effective_to < self.effective_from:
            raise ValueError("effective_to must not be earlier than effective_from")
        return self


class VirtualMachineCreate(VirtualMachineBase):
    """Payload to create a Virtual Machine."""


class VirtualMachineUpdate(ApiModel):
    """Partial update; omitted attributes are left unchanged."""

    expected_version: int | None = Field(None, ge=0, description="Optimistic lock version")
    code: str | None = Field(None, max_length=255, min_length=1, description="Code")
    name: str | None = Field(None, max_length=255, min_length=1, description="Name")
    description: str | None = Field(None, max_length=4000, description="Description")
    status: LifecycleStatus | None = Field(None, description="Status")
    external_ref: str | None = Field(None, max_length=255, description="External Ref")
    hypervisor_id: UUID | None = Field(None, description="Hypervisor Id")
    vm_name: str | None = Field(None, max_length=255, min_length=1, description="Vm Name")
    vcpu: int | None = Field(None, ge=0, description="Vcpu")
    memory_gb: int | None = Field(None, ge=0, description="Memory Gb")
    disk_gb: int | None = Field(None, ge=0, description="Disk Gb")
    power_state: VirtualMachinePowerState | None = Field(None, description="Power State")
    criticality: VirtualMachineCriticality | None = Field(None, description="Criticality")
    cost_center: str | None = Field(None, max_length=255, description="Cost Center")
    notes: str | None = Field(None, max_length=4000, description="Notes")
    effective_from: date | None = Field(None, description="Effective From")
    effective_to: date | None = Field(None, description="Effective To")


class VirtualMachineRead(VirtualMachineBase):
    """Virtual Machine as returned by the system of record."""

    id: UUID
    tenant_id: str
    version: int = 0
    created_at: datetime | None = None
    updated_at: datetime | None = None
    created_by: str | None = None
    updated_by: str | None = None


class VirtualMachineSummary(ApiModel):
    id: UUID
    code: str
    name: str
    status: LifecycleStatus


class VirtualMachineBulkFailure(ApiModel):
    index: int
    message: str


class VirtualMachineBulkResult(ApiModel):
    succeeded: list[VirtualMachineRead] = Field(default_factory=list)
    failures: list[VirtualMachineBulkFailure] = Field(default_factory=list)
