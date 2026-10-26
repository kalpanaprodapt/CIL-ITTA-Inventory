"""Pydantic models for Building (Geography)."""
from __future__ import annotations

from datetime import date, datetime
from decimal import Decimal
from enum import Enum
from uuid import UUID

from pydantic import Field, field_validator, model_validator

from itta_core.models import ApiModel, LifecycleStatus, reject_control_chars


class BuildingOwnership(str, Enum):
    """Allowed values for Building Ownership."""

    OWNED = "OWNED"
    LEASED = "LEASED"
    COLO = "COLO"


class BuildingLifecycleStage(str, Enum):
    """Allowed values for Building Lifecycle Stage."""

    PLAN = "PLAN"
    BUILD = "BUILD"
    OPERATE = "OPERATE"
    RETIRE = "RETIRE"


class BuildingCriticality(str, Enum):
    """Allowed values for Building Criticality."""

    LOW = "LOW"
    MEDIUM = "MEDIUM"
    HIGH = "HIGH"
    CRITICAL = "CRITICAL"


class BuildingBase(ApiModel):
    """Attributes shared by create and read models."""

    code: str = Field(..., max_length=255, min_length=1, description="Code")
    name: str = Field(..., max_length=255, min_length=1, description="Name")
    description: str | None = Field(None, max_length=4000, description="Description")
    status: LifecycleStatus = Field(..., description="Status")
    external_ref: str | None = Field(None, max_length=255, description="External Ref")
    site_id: UUID = Field(..., description="Site Id")
    floors: int | None = Field(None, ge=0, description="Floors")
    year_built: int | None = Field(None, ge=0, description="Year Built")
    ownership: BuildingOwnership | None = Field(None, description="Ownership")
    lease_expiry: date | None = Field(None, description="Lease Expiry")
    security_level: int | None = Field(None, description="Security Level")
    lifecycle_stage: BuildingLifecycleStage | None = Field(None, description="Lifecycle Stage")
    source_system: str | None = Field(None, max_length=255, description="Source System")
    region: str | None = Field(None, max_length=255, description="Region")
    owner: str | None = Field(None, max_length=255, description="Owner")
    criticality: BuildingCriticality | None = Field(None, description="Criticality")

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


class BuildingCreate(BuildingBase):
    """Payload to create a Building."""


class BuildingUpdate(ApiModel):
    """Partial update; omitted attributes are left unchanged."""

    expected_version: int | None = Field(None, ge=0, description="Optimistic lock version")
    code: str | None = Field(None, max_length=255, min_length=1, description="Code")
    name: str | None = Field(None, max_length=255, min_length=1, description="Name")
    description: str | None = Field(None, max_length=4000, description="Description")
    status: LifecycleStatus | None = Field(None, description="Status")
    external_ref: str | None = Field(None, max_length=255, description="External Ref")
    site_id: UUID | None = Field(None, description="Site Id")
    floors: int | None = Field(None, ge=0, description="Floors")
    year_built: int | None = Field(None, ge=0, description="Year Built")
    ownership: BuildingOwnership | None = Field(None, description="Ownership")
    lease_expiry: date | None = Field(None, description="Lease Expiry")
    security_level: int | None = Field(None, description="Security Level")
    lifecycle_stage: BuildingLifecycleStage | None = Field(None, description="Lifecycle Stage")
    source_system: str | None = Field(None, max_length=255, description="Source System")
    region: str | None = Field(None, max_length=255, description="Region")
    owner: str | None = Field(None, max_length=255, description="Owner")
    criticality: BuildingCriticality | None = Field(None, description="Criticality")


class BuildingRead(BuildingBase):
    """Building as returned by the system of record."""

    id: UUID
    tenant_id: str
    version: int = 0
    created_at: datetime | None = None
    updated_at: datetime | None = None
    created_by: str | None = None
    updated_by: str | None = None


class BuildingSummary(ApiModel):
    id: UUID
    code: str
    name: str
    status: LifecycleStatus


class BuildingBulkFailure(ApiModel):
    index: int
    message: str


class BuildingBulkResult(ApiModel):
    succeeded: list[BuildingRead] = Field(default_factory=list)
    failures: list[BuildingBulkFailure] = Field(default_factory=list)
