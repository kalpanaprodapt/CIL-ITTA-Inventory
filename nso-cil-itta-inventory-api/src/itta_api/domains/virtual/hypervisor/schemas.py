"""Pydantic models for Hypervisor (Virtual and Cloud)."""
from __future__ import annotations

from datetime import date, datetime
from decimal import Decimal
from enum import Enum
from uuid import UUID

from pydantic import Field, field_validator, model_validator

from itta_core.models import ApiModel, LifecycleStatus, reject_control_chars


class HypervisorHypervisorType(str, Enum):
    """Allowed values for Hypervisor Hypervisor Type."""

    KVM = "KVM"
    VMWARE = "VMWARE"
    HYPERV = "HYPERV"
    XEN = "XEN"


class HypervisorCriticality(str, Enum):
    """Allowed values for Hypervisor Criticality."""

    LOW = "LOW"
    MEDIUM = "MEDIUM"
    HIGH = "HIGH"
    CRITICAL = "CRITICAL"


class HypervisorBase(ApiModel):
    """Attributes shared by create and read models."""

    code: str = Field(..., max_length=255, min_length=1, description="Code")
    name: str = Field(..., max_length=255, min_length=1, description="Name")
    description: str | None = Field(None, max_length=4000, description="Description")
    status: LifecycleStatus = Field(..., description="Status")
    external_ref: str | None = Field(None, max_length=255, description="External Ref")
    host_device_id: UUID = Field(..., description="Host Device Id")
    hypervisor_type: HypervisorHypervisorType | None = Field(None, description="Hypervisor Type")
    hv_version: str | None = Field(None, max_length=255, description="Hv Version")
    vcpu_capacity: int | None = Field(None, ge=0, description="Vcpu Capacity")
    memory_gb_capacity: int | None = Field(None, ge=0, description="Memory Gb Capacity")
    region: str | None = Field(None, max_length=255, description="Region")
    owner: str | None = Field(None, max_length=255, description="Owner")
    criticality: HypervisorCriticality | None = Field(None, description="Criticality")
    cost_center: str | None = Field(None, max_length=255, description="Cost Center")
    notes: str | None = Field(None, max_length=4000, description="Notes")

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

    @field_validator("hv_version")
    @classmethod
    def _check_hv_version(cls, value: str | None) -> str | None:
        return reject_control_chars(value, "hvVersion")

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

    @field_validator("notes")
    @classmethod
    def _check_notes(cls, value: str | None) -> str | None:
        return reject_control_chars(value, "notes")


class HypervisorCreate(HypervisorBase):
    """Payload to create a Hypervisor."""


class HypervisorUpdate(ApiModel):
    """Partial update; omitted attributes are left unchanged."""

    expected_version: int | None = Field(None, ge=0, description="Optimistic lock version")
    code: str | None = Field(None, max_length=255, min_length=1, description="Code")
    name: str | None = Field(None, max_length=255, min_length=1, description="Name")
    description: str | None = Field(None, max_length=4000, description="Description")
    status: LifecycleStatus | None = Field(None, description="Status")
    external_ref: str | None = Field(None, max_length=255, description="External Ref")
    host_device_id: UUID | None = Field(None, description="Host Device Id")
    hypervisor_type: HypervisorHypervisorType | None = Field(None, description="Hypervisor Type")
    hv_version: str | None = Field(None, max_length=255, description="Hv Version")
    vcpu_capacity: int | None = Field(None, ge=0, description="Vcpu Capacity")
    memory_gb_capacity: int | None = Field(None, ge=0, description="Memory Gb Capacity")
    region: str | None = Field(None, max_length=255, description="Region")
    owner: str | None = Field(None, max_length=255, description="Owner")
    criticality: HypervisorCriticality | None = Field(None, description="Criticality")
    cost_center: str | None = Field(None, max_length=255, description="Cost Center")
    notes: str | None = Field(None, max_length=4000, description="Notes")


class HypervisorRead(HypervisorBase):
    """Hypervisor as returned by the system of record."""

    id: UUID
    tenant_id: str
    version: int = 0
    created_at: datetime | None = None
    updated_at: datetime | None = None
    created_by: str | None = None
    updated_by: str | None = None


class HypervisorSummary(ApiModel):
    id: UUID
    code: str
    name: str
    status: LifecycleStatus


class HypervisorBulkFailure(ApiModel):
    index: int
    message: str


class HypervisorBulkResult(ApiModel):
    succeeded: list[HypervisorRead] = Field(default_factory=list)
    failures: list[HypervisorBulkFailure] = Field(default_factory=list)
