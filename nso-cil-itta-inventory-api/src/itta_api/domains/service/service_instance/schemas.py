"""Pydantic models for Service Instance (Services)."""
from __future__ import annotations

from datetime import date, datetime
from decimal import Decimal
from enum import Enum
from uuid import UUID

from pydantic import Field, field_validator, model_validator

from itta_core.models import ApiModel, LifecycleStatus, reject_control_chars


class ServiceInstanceServiceState(str, Enum):
    """Allowed values for Service Instance Service State."""

    PLANNED = "PLANNED"
    PROVISIONING = "PROVISIONING"
    ACTIVE = "ACTIVE"
    SUSPENDED = "SUSPENDED"
    TERMINATED = "TERMINATED"


class ServiceInstanceLifecycleStage(str, Enum):
    """Allowed values for Service Instance Lifecycle Stage."""

    PLAN = "PLAN"
    BUILD = "BUILD"
    OPERATE = "OPERATE"
    RETIRE = "RETIRE"


class ServiceInstanceCriticality(str, Enum):
    """Allowed values for Service Instance Criticality."""

    LOW = "LOW"
    MEDIUM = "MEDIUM"
    HIGH = "HIGH"
    CRITICAL = "CRITICAL"


class ServiceInstanceBase(ApiModel):
    """Attributes shared by create and read models."""

    code: str = Field(..., max_length=255, min_length=1, description="Code")
    name: str = Field(..., max_length=255, min_length=1, description="Name")
    description: str | None = Field(None, max_length=4000, description="Description")
    status: LifecycleStatus = Field(..., description="Status")
    external_ref: str | None = Field(None, max_length=255, description="External Ref")
    catalog_item_id: UUID = Field(..., description="Catalog Item Id")
    customer_id: UUID = Field(..., description="Customer Id")
    activated_on: date | None = Field(None, description="Activated On")
    bandwidth_mbps: int | None = Field(None, ge=0, description="Bandwidth Mbps")
    service_state: ServiceInstanceServiceState | None = Field(None, description="Service State")
    lifecycle_stage: ServiceInstanceLifecycleStage | None = Field(None, description="Lifecycle Stage")
    source_system: str | None = Field(None, max_length=255, description="Source System")
    region: str | None = Field(None, max_length=255, description="Region")
    owner: str | None = Field(None, max_length=255, description="Owner")
    criticality: ServiceInstanceCriticality | None = Field(None, description="Criticality")

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


class ServiceInstanceCreate(ServiceInstanceBase):
    """Payload to create a Service Instance."""


class ServiceInstanceUpdate(ApiModel):
    """Partial update; omitted attributes are left unchanged."""

    expected_version: int | None = Field(None, ge=0, description="Optimistic lock version")
    code: str | None = Field(None, max_length=255, min_length=1, description="Code")
    name: str | None = Field(None, max_length=255, min_length=1, description="Name")
    description: str | None = Field(None, max_length=4000, description="Description")
    status: LifecycleStatus | None = Field(None, description="Status")
    external_ref: str | None = Field(None, max_length=255, description="External Ref")
    catalog_item_id: UUID | None = Field(None, description="Catalog Item Id")
    customer_id: UUID | None = Field(None, description="Customer Id")
    activated_on: date | None = Field(None, description="Activated On")
    bandwidth_mbps: int | None = Field(None, ge=0, description="Bandwidth Mbps")
    service_state: ServiceInstanceServiceState | None = Field(None, description="Service State")
    lifecycle_stage: ServiceInstanceLifecycleStage | None = Field(None, description="Lifecycle Stage")
    source_system: str | None = Field(None, max_length=255, description="Source System")
    region: str | None = Field(None, max_length=255, description="Region")
    owner: str | None = Field(None, max_length=255, description="Owner")
    criticality: ServiceInstanceCriticality | None = Field(None, description="Criticality")


class ServiceInstanceRead(ServiceInstanceBase):
    """Service Instance as returned by the system of record."""

    id: UUID
    tenant_id: str
    version: int = 0
    created_at: datetime | None = None
    updated_at: datetime | None = None
    created_by: str | None = None
    updated_by: str | None = None


class ServiceInstanceSummary(ApiModel):
    id: UUID
    code: str
    name: str
    status: LifecycleStatus


class ServiceInstanceBulkFailure(ApiModel):
    index: int
    message: str


class ServiceInstanceBulkResult(ApiModel):
    succeeded: list[ServiceInstanceRead] = Field(default_factory=list)
    failures: list[ServiceInstanceBulkFailure] = Field(default_factory=list)
