"""Pydantic models for License Pool (Software and Licensing)."""
from __future__ import annotations

from datetime import date, datetime
from decimal import Decimal
from enum import Enum
from uuid import UUID

from pydantic import Field, field_validator, model_validator

from itta_core.models import ApiModel, LifecycleStatus, reject_control_chars


class LicensePoolLifecycleStage(str, Enum):
    """Allowed values for License Pool Lifecycle Stage."""

    PLAN = "PLAN"
    BUILD = "BUILD"
    OPERATE = "OPERATE"
    RETIRE = "RETIRE"


class LicensePoolBase(ApiModel):
    """Attributes shared by create and read models."""

    code: str = Field(..., max_length=255, min_length=1, description="Code")
    name: str = Field(..., max_length=255, min_length=1, description="Name")
    description: str | None = Field(None, max_length=4000, description="Description")
    status: LifecycleStatus = Field(..., description="Status")
    external_ref: str | None = Field(None, max_length=255, description="External Ref")
    vendor_id: UUID = Field(..., description="Vendor Id")
    pool_name: str = Field(..., max_length=255, min_length=1, description="Pool Name")
    total_seats: int = Field(..., ge=0, description="Total Seats")
    used_seats: int | None = Field(None, ge=0, description="Used Seats")
    renewal_date: date | None = Field(None, description="Renewal Date")
    effective_to: date | None = Field(None, description="Effective To")
    labels: str | None = Field(None, max_length=255, description="Labels")
    lifecycle_stage: LicensePoolLifecycleStage | None = Field(None, description="Lifecycle Stage")
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

    @field_validator("pool_name")
    @classmethod
    def _check_pool_name(cls, value: str | None) -> str | None:
        return reject_control_chars(value, "poolName")

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


class LicensePoolCreate(LicensePoolBase):
    """Payload to create a License Pool."""


class LicensePoolUpdate(ApiModel):
    """Partial update; omitted attributes are left unchanged."""

    expected_version: int | None = Field(None, ge=0, description="Optimistic lock version")
    code: str | None = Field(None, max_length=255, min_length=1, description="Code")
    name: str | None = Field(None, max_length=255, min_length=1, description="Name")
    description: str | None = Field(None, max_length=4000, description="Description")
    status: LifecycleStatus | None = Field(None, description="Status")
    external_ref: str | None = Field(None, max_length=255, description="External Ref")
    vendor_id: UUID | None = Field(None, description="Vendor Id")
    pool_name: str | None = Field(None, max_length=255, min_length=1, description="Pool Name")
    total_seats: int | None = Field(None, ge=0, description="Total Seats")
    used_seats: int | None = Field(None, ge=0, description="Used Seats")
    renewal_date: date | None = Field(None, description="Renewal Date")
    effective_to: date | None = Field(None, description="Effective To")
    labels: str | None = Field(None, max_length=255, description="Labels")
    lifecycle_stage: LicensePoolLifecycleStage | None = Field(None, description="Lifecycle Stage")
    source_system: str | None = Field(None, max_length=255, description="Source System")
    region: str | None = Field(None, max_length=255, description="Region")


class LicensePoolRead(LicensePoolBase):
    """License Pool as returned by the system of record."""

    id: UUID
    tenant_id: str
    version: int = 0
    created_at: datetime | None = None
    updated_at: datetime | None = None
    created_by: str | None = None
    updated_by: str | None = None


class LicensePoolSummary(ApiModel):
    id: UUID
    code: str
    name: str
    status: LifecycleStatus


class LicensePoolBulkFailure(ApiModel):
    index: int
    message: str


class LicensePoolBulkResult(ApiModel):
    succeeded: list[LicensePoolRead] = Field(default_factory=list)
    failures: list[LicensePoolBulkFailure] = Field(default_factory=list)
