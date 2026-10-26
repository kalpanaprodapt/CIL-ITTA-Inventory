"""Pydantic models for Ip Subnet (IP Management)."""
from __future__ import annotations

from datetime import date, datetime
from decimal import Decimal
from enum import Enum
from uuid import UUID

from pydantic import Field, field_validator, model_validator

from itta_core.models import ApiModel, LifecycleStatus, reject_control_chars


class IpSubnetIpVersion(str, Enum):
    """Allowed values for Ip Subnet Ip Version."""

    V4 = "V4"
    V6 = "V6"


class IpSubnetCriticality(str, Enum):
    """Allowed values for Ip Subnet Criticality."""

    LOW = "LOW"
    MEDIUM = "MEDIUM"
    HIGH = "HIGH"
    CRITICAL = "CRITICAL"


class IpSubnetBase(ApiModel):
    """Attributes shared by create and read models."""

    code: str = Field(..., max_length=255, min_length=1, description="Code")
    name: str = Field(..., max_length=255, min_length=1, description="Name")
    description: str | None = Field(None, max_length=4000, description="Description")
    status: LifecycleStatus = Field(..., description="Status")
    external_ref: str | None = Field(None, max_length=255, description="External Ref")
    cidr: str = Field(..., max_length=255, min_length=1, description="Cidr")
    ip_version: IpSubnetIpVersion | None = Field(None, description="Ip Version")
    vrf_id: UUID | None = Field(None, description="Vrf Id")
    gateway: str | None = Field(None, max_length=255, description="Gateway")
    vlan_id: UUID | None = Field(None, description="Vlan Id")
    utilization_pct: Decimal | None = Field(None, ge=0, max_digits=18, decimal_places=4, description="Utilization Pct")
    owner: str | None = Field(None, max_length=255, description="Owner")
    criticality: IpSubnetCriticality | None = Field(None, description="Criticality")
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

    @field_validator("cidr")
    @classmethod
    def _check_cidr(cls, value: str | None) -> str | None:
        return reject_control_chars(value, "cidr")

    @field_validator("gateway")
    @classmethod
    def _check_gateway(cls, value: str | None) -> str | None:
        return reject_control_chars(value, "gateway")

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


class IpSubnetCreate(IpSubnetBase):
    """Payload to create a Ip Subnet."""


class IpSubnetUpdate(ApiModel):
    """Partial update; omitted attributes are left unchanged."""

    expected_version: int | None = Field(None, ge=0, description="Optimistic lock version")
    code: str | None = Field(None, max_length=255, min_length=1, description="Code")
    name: str | None = Field(None, max_length=255, min_length=1, description="Name")
    description: str | None = Field(None, max_length=4000, description="Description")
    status: LifecycleStatus | None = Field(None, description="Status")
    external_ref: str | None = Field(None, max_length=255, description="External Ref")
    cidr: str | None = Field(None, max_length=255, min_length=1, description="Cidr")
    ip_version: IpSubnetIpVersion | None = Field(None, description="Ip Version")
    vrf_id: UUID | None = Field(None, description="Vrf Id")
    gateway: str | None = Field(None, max_length=255, description="Gateway")
    vlan_id: UUID | None = Field(None, description="Vlan Id")
    utilization_pct: Decimal | None = Field(None, ge=0, max_digits=18, decimal_places=4, description="Utilization Pct")
    owner: str | None = Field(None, max_length=255, description="Owner")
    criticality: IpSubnetCriticality | None = Field(None, description="Criticality")
    cost_center: str | None = Field(None, max_length=255, description="Cost Center")
    notes: str | None = Field(None, max_length=4000, description="Notes")
    effective_from: date | None = Field(None, description="Effective From")


class IpSubnetRead(IpSubnetBase):
    """Ip Subnet as returned by the system of record."""

    id: UUID
    tenant_id: str
    version: int = 0
    created_at: datetime | None = None
    updated_at: datetime | None = None
    created_by: str | None = None
    updated_by: str | None = None


class IpSubnetSummary(ApiModel):
    id: UUID
    code: str
    name: str
    status: LifecycleStatus


class IpSubnetBulkFailure(ApiModel):
    index: int
    message: str


class IpSubnetBulkResult(ApiModel):
    succeeded: list[IpSubnetRead] = Field(default_factory=list)
    failures: list[IpSubnetBulkFailure] = Field(default_factory=list)
