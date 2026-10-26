"""Pydantic models for Site (Geography)."""
from __future__ import annotations

from datetime import date, datetime
from decimal import Decimal
from enum import Enum
from uuid import UUID

from pydantic import Field, field_validator, model_validator

from itta_core.models import ApiModel, LifecycleStatus, reject_control_chars


class SiteSiteType(str, Enum):
    """Allowed values for Site Site Type."""

    CO = "CO"
    POP = "POP"
    DATACENTER = "DATACENTER"
    HUT = "HUT"
    CELL_SITE = "CELL_SITE"
    CUSTOMER_PREMISE = "CUSTOMER_PREMISE"


class SiteLifecycleStage(str, Enum):
    """Allowed values for Site Lifecycle Stage."""

    PLAN = "PLAN"
    BUILD = "BUILD"
    OPERATE = "OPERATE"
    RETIRE = "RETIRE"


class SiteBase(ApiModel):
    """Attributes shared by create and read models."""

    code: str = Field(..., max_length=255, min_length=1, description="Code")
    name: str = Field(..., max_length=255, min_length=1, description="Name")
    description: str | None = Field(None, max_length=4000, description="Description")
    status: LifecycleStatus = Field(..., description="Status")
    external_ref: str | None = Field(None, max_length=255, description="External Ref")
    city_id: UUID = Field(..., description="City Id")
    site_type: SiteSiteType | None = Field(None, description="Site Type")
    address: str | None = Field(None, max_length=255, description="Address")
    latitude: Decimal | None = Field(None, max_digits=18, decimal_places=4, description="Latitude")
    longitude: Decimal | None = Field(None, max_digits=18, decimal_places=4, description="Longitude")
    power_kw: Decimal | None = Field(None, ge=0, max_digits=18, decimal_places=4, description="Power Kw")
    access_hours: str | None = Field(None, max_length=255, description="Access Hours")
    effective_to: date | None = Field(None, description="Effective To")
    labels: str | None = Field(None, max_length=255, description="Labels")
    lifecycle_stage: SiteLifecycleStage | None = Field(None, description="Lifecycle Stage")
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

    @field_validator("address")
    @classmethod
    def _check_address(cls, value: str | None) -> str | None:
        return reject_control_chars(value, "address")

    @field_validator("access_hours")
    @classmethod
    def _check_access_hours(cls, value: str | None) -> str | None:
        return reject_control_chars(value, "accessHours")

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


class SiteCreate(SiteBase):
    """Payload to create a Site."""


class SiteUpdate(ApiModel):
    """Partial update; omitted attributes are left unchanged."""

    expected_version: int | None = Field(None, ge=0, description="Optimistic lock version")
    code: str | None = Field(None, max_length=255, min_length=1, description="Code")
    name: str | None = Field(None, max_length=255, min_length=1, description="Name")
    description: str | None = Field(None, max_length=4000, description="Description")
    status: LifecycleStatus | None = Field(None, description="Status")
    external_ref: str | None = Field(None, max_length=255, description="External Ref")
    city_id: UUID | None = Field(None, description="City Id")
    site_type: SiteSiteType | None = Field(None, description="Site Type")
    address: str | None = Field(None, max_length=255, description="Address")
    latitude: Decimal | None = Field(None, max_digits=18, decimal_places=4, description="Latitude")
    longitude: Decimal | None = Field(None, max_digits=18, decimal_places=4, description="Longitude")
    power_kw: Decimal | None = Field(None, ge=0, max_digits=18, decimal_places=4, description="Power Kw")
    access_hours: str | None = Field(None, max_length=255, description="Access Hours")
    effective_to: date | None = Field(None, description="Effective To")
    labels: str | None = Field(None, max_length=255, description="Labels")
    lifecycle_stage: SiteLifecycleStage | None = Field(None, description="Lifecycle Stage")
    source_system: str | None = Field(None, max_length=255, description="Source System")
    region: str | None = Field(None, max_length=255, description="Region")


class SiteRead(SiteBase):
    """Site as returned by the system of record."""

    id: UUID
    tenant_id: str
    version: int = 0
    created_at: datetime | None = None
    updated_at: datetime | None = None
    created_by: str | None = None
    updated_by: str | None = None


class SiteSummary(ApiModel):
    id: UUID
    code: str
    name: str
    status: LifecycleStatus


class SiteBulkFailure(ApiModel):
    index: int
    message: str


class SiteBulkResult(ApiModel):
    succeeded: list[SiteRead] = Field(default_factory=list)
    failures: list[SiteBulkFailure] = Field(default_factory=list)
