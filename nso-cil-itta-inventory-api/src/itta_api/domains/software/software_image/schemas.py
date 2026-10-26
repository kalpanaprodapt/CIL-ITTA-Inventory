"""Pydantic models for Software Image (Software and Licensing)."""
from __future__ import annotations

from datetime import date, datetime
from decimal import Decimal
from enum import Enum
from uuid import UUID

from pydantic import Field, field_validator, model_validator

from itta_core.models import ApiModel, LifecycleStatus, reject_control_chars


class SoftwareImageLifecycleStage(str, Enum):
    """Allowed values for Software Image Lifecycle Stage."""

    PLAN = "PLAN"
    BUILD = "BUILD"
    OPERATE = "OPERATE"
    RETIRE = "RETIRE"


class SoftwareImageCriticality(str, Enum):
    """Allowed values for Software Image Criticality."""

    LOW = "LOW"
    MEDIUM = "MEDIUM"
    HIGH = "HIGH"
    CRITICAL = "CRITICAL"


class SoftwareImageBase(ApiModel):
    """Attributes shared by create and read models."""

    code: str = Field(..., max_length=255, min_length=1, description="Code")
    name: str = Field(..., max_length=255, min_length=1, description="Name")
    description: str | None = Field(None, max_length=4000, description="Description")
    status: LifecycleStatus = Field(..., description="Status")
    external_ref: str | None = Field(None, max_length=255, description="External Ref")
    vendor_id: UUID = Field(..., description="Vendor Id")
    image_name: str = Field(..., max_length=255, min_length=1, description="Image Name")
    size_mb: int | None = Field(None, ge=0, description="Size Mb")
    checksum: str | None = Field(None, max_length=255, description="Checksum")
    platform: str | None = Field(None, max_length=255, description="Platform")
    storage_path: str | None = Field(None, max_length=255, description="Storage Path")
    lifecycle_stage: SoftwareImageLifecycleStage | None = Field(None, description="Lifecycle Stage")
    source_system: str | None = Field(None, max_length=255, description="Source System")
    region: str | None = Field(None, max_length=255, description="Region")
    owner: str | None = Field(None, max_length=255, description="Owner")
    criticality: SoftwareImageCriticality | None = Field(None, description="Criticality")

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

    @field_validator("image_name")
    @classmethod
    def _check_image_name(cls, value: str | None) -> str | None:
        return reject_control_chars(value, "imageName")

    @field_validator("checksum")
    @classmethod
    def _check_checksum(cls, value: str | None) -> str | None:
        return reject_control_chars(value, "checksum")

    @field_validator("platform")
    @classmethod
    def _check_platform(cls, value: str | None) -> str | None:
        return reject_control_chars(value, "platform")

    @field_validator("storage_path")
    @classmethod
    def _check_storage_path(cls, value: str | None) -> str | None:
        return reject_control_chars(value, "storagePath")

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


class SoftwareImageCreate(SoftwareImageBase):
    """Payload to create a Software Image."""


class SoftwareImageUpdate(ApiModel):
    """Partial update; omitted attributes are left unchanged."""

    expected_version: int | None = Field(None, ge=0, description="Optimistic lock version")
    code: str | None = Field(None, max_length=255, min_length=1, description="Code")
    name: str | None = Field(None, max_length=255, min_length=1, description="Name")
    description: str | None = Field(None, max_length=4000, description="Description")
    status: LifecycleStatus | None = Field(None, description="Status")
    external_ref: str | None = Field(None, max_length=255, description="External Ref")
    vendor_id: UUID | None = Field(None, description="Vendor Id")
    image_name: str | None = Field(None, max_length=255, min_length=1, description="Image Name")
    size_mb: int | None = Field(None, ge=0, description="Size Mb")
    checksum: str | None = Field(None, max_length=255, description="Checksum")
    platform: str | None = Field(None, max_length=255, description="Platform")
    storage_path: str | None = Field(None, max_length=255, description="Storage Path")
    lifecycle_stage: SoftwareImageLifecycleStage | None = Field(None, description="Lifecycle Stage")
    source_system: str | None = Field(None, max_length=255, description="Source System")
    region: str | None = Field(None, max_length=255, description="Region")
    owner: str | None = Field(None, max_length=255, description="Owner")
    criticality: SoftwareImageCriticality | None = Field(None, description="Criticality")


class SoftwareImageRead(SoftwareImageBase):
    """Software Image as returned by the system of record."""

    id: UUID
    tenant_id: str
    version: int = 0
    created_at: datetime | None = None
    updated_at: datetime | None = None
    created_by: str | None = None
    updated_by: str | None = None


class SoftwareImageSummary(ApiModel):
    id: UUID
    code: str
    name: str
    status: LifecycleStatus


class SoftwareImageBulkFailure(ApiModel):
    index: int
    message: str


class SoftwareImageBulkResult(ApiModel):
    succeeded: list[SoftwareImageRead] = Field(default_factory=list)
    failures: list[SoftwareImageBulkFailure] = Field(default_factory=list)
