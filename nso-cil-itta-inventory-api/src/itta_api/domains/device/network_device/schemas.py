"""Pydantic models for Network Device (Network Device)."""
from __future__ import annotations

from datetime import date, datetime
from decimal import Decimal
from enum import Enum
from uuid import UUID

from pydantic import Field, field_validator, model_validator

from itta_core.models import ApiModel, LifecycleStatus, reject_control_chars


class NetworkDeviceDeviceRole(str, Enum):
    """Allowed values for Network Device Device Role."""

    CORE = "CORE"
    EDGE = "EDGE"
    AGG = "AGG"
    ACCESS = "ACCESS"
    CPE = "CPE"
    FIREWALL = "FIREWALL"
    LB = "LB"


class NetworkDeviceLifecycleStage(str, Enum):
    """Allowed values for Network Device Lifecycle Stage."""

    PLAN = "PLAN"
    BUILD = "BUILD"
    OPERATE = "OPERATE"
    RETIRE = "RETIRE"


class NetworkDeviceBase(ApiModel):
    """Attributes shared by create and read models."""

    code: str = Field(..., max_length=255, min_length=1, description="Code")
    name: str = Field(..., max_length=255, min_length=1, description="Name")
    description: str | None = Field(None, max_length=4000, description="Description")
    status: LifecycleStatus = Field(..., description="Status")
    external_ref: str | None = Field(None, max_length=255, description="External Ref")
    hostname: str = Field(..., max_length=255, min_length=1, description="Hostname")
    device_role: NetworkDeviceDeviceRole | None = Field(None, description="Device Role")
    mgmt_ip: str | None = Field(None, max_length=255, description="Mgmt Ip")
    serial_number: str | None = Field(None, max_length=255, description="Serial Number")
    model_id: UUID | None = Field(None, description="Model Id")
    rack_unit_id: UUID | None = Field(None, description="Rack Unit Id")
    effective_to: date | None = Field(None, description="Effective To")
    labels: str | None = Field(None, max_length=255, description="Labels")
    lifecycle_stage: NetworkDeviceLifecycleStage | None = Field(None, description="Lifecycle Stage")
    source_system: str | None = Field(None, max_length=255, description="Source System")
    region: str | None = Field(None, max_length=255, description="Region")

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

    @field_validator("hostname")
    @classmethod
    def _check_hostname(cls, value: str | None) -> str | None:
        return reject_control_chars(value, "hostname")

    @field_validator("mgmt_ip")
    @classmethod
    def _check_mgmt_ip(cls, value: str | None) -> str | None:
        return reject_control_chars(value, "mgmtIp")

    @field_validator("serial_number")
    @classmethod
    def _check_serial_number(cls, value: str | None) -> str | None:
        return reject_control_chars(value, "serialNumber")

    @field_validator("labels")
    @classmethod
    def _check_labels(cls, value: str | None) -> str | None:
        return reject_control_chars(value, "labels")

    @field_validator("source_system")
    @classmethod
    def _check_source_system(cls, value: str | None) -> str | None:
        return reject_control_chars(value, "sourceSystem")

    @field_validator("region")
    @classmethod
    def _check_region(cls, value: str | None) -> str | None:
        return reject_control_chars(value, "region")


class NetworkDeviceCreate(NetworkDeviceBase):
    """Payload to create a Network Device."""


class NetworkDeviceUpdate(ApiModel):
    """Partial update; omitted attributes are left unchanged."""

    expected_version: int | None = Field(None, ge=0, description="Optimistic lock version")
    code: str | None = Field(None, max_length=255, min_length=1, description="Code")
    name: str | None = Field(None, max_length=255, min_length=1, description="Name")
    description: str | None = Field(None, max_length=4000, description="Description")
    status: LifecycleStatus | None = Field(None, description="Status")
    external_ref: str | None = Field(None, max_length=255, description="External Ref")
    hostname: str | None = Field(None, max_length=255, min_length=1, description="Hostname")
    device_role: NetworkDeviceDeviceRole | None = Field(None, description="Device Role")
    mgmt_ip: str | None = Field(None, max_length=255, description="Mgmt Ip")
    serial_number: str | None = Field(None, max_length=255, description="Serial Number")
    model_id: UUID | None = Field(None, description="Model Id")
    rack_unit_id: UUID | None = Field(None, description="Rack Unit Id")
    effective_to: date | None = Field(None, description="Effective To")
    labels: str | None = Field(None, max_length=255, description="Labels")
    lifecycle_stage: NetworkDeviceLifecycleStage | None = Field(None, description="Lifecycle Stage")
    source_system: str | None = Field(None, max_length=255, description="Source System")
    region: str | None = Field(None, max_length=255, description="Region")


class NetworkDeviceRead(NetworkDeviceBase):
    """Network Device as returned by the system of record."""

    id: UUID
    tenant_id: str
    version: int = 0
    created_at: datetime | None = None
    updated_at: datetime | None = None
    created_by: str | None = None
    updated_by: str | None = None


class NetworkDeviceSummary(ApiModel):
    id: UUID
    code: str
    name: str
    status: LifecycleStatus


class NetworkDeviceBulkFailure(ApiModel):
    index: int
    message: str


class NetworkDeviceBulkResult(ApiModel):
    succeeded: list[NetworkDeviceRead] = Field(default_factory=list)
    failures: list[NetworkDeviceBulkFailure] = Field(default_factory=list)
