"""Pydantic models for Antenna (Radio and Mobile)."""
from __future__ import annotations

from datetime import date, datetime
from decimal import Decimal
from enum import Enum
from uuid import UUID

from pydantic import Field, field_validator, model_validator

from itta_core.models import ApiModel, LifecycleStatus, reject_control_chars


class AntennaCriticality(str, Enum):
    """Allowed values for Antenna Criticality."""

    LOW = "LOW"
    MEDIUM = "MEDIUM"
    HIGH = "HIGH"
    CRITICAL = "CRITICAL"


class AntennaBase(ApiModel):
    """Attributes shared by create and read models."""

    code: str = Field(..., max_length=255, min_length=1, description="Code")
    name: str = Field(..., max_length=255, min_length=1, description="Name")
    description: str | None = Field(None, max_length=4000, description="Description")
    status: LifecycleStatus = Field(..., description="Status")
    external_ref: str | None = Field(None, max_length=255, description="External Ref")
    sector_id: UUID = Field(..., description="Sector Id")
    model: str | None = Field(None, max_length=255, description="Model")
    height_m: Decimal | None = Field(None, ge=0, max_digits=18, decimal_places=4, description="Height M")
    azimuth_deg: Decimal | None = Field(None, max_digits=18, decimal_places=4, description="Azimuth Deg")
    tilt_deg: Decimal | None = Field(None, max_digits=18, decimal_places=4, description="Tilt Deg")
    gain_dbi: Decimal | None = Field(None, max_digits=18, decimal_places=4, description="Gain Dbi")
    source_system: str | None = Field(None, max_length=255, description="Source System")
    region: str | None = Field(None, max_length=255, description="Region")
    owner: str | None = Field(None, max_length=255, description="Owner")
    criticality: AntennaCriticality | None = Field(None, description="Criticality")
    cost_center: str | None = Field(None, max_length=255, description="Cost Center")

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

    @field_validator("model")
    @classmethod
    def _check_model(cls, value: str | None) -> str | None:
        return reject_control_chars(value, "model")

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

    @field_validator("cost_center")
    @classmethod
    def _check_cost_center(cls, value: str | None) -> str | None:
        return reject_control_chars(value, "costCenter")


class AntennaCreate(AntennaBase):
    """Payload to create a Antenna."""


class AntennaUpdate(ApiModel):
    """Partial update; omitted attributes are left unchanged."""

    expected_version: int | None = Field(None, ge=0, description="Optimistic lock version")
    code: str | None = Field(None, max_length=255, min_length=1, description="Code")
    name: str | None = Field(None, max_length=255, min_length=1, description="Name")
    description: str | None = Field(None, max_length=4000, description="Description")
    status: LifecycleStatus | None = Field(None, description="Status")
    external_ref: str | None = Field(None, max_length=255, description="External Ref")
    sector_id: UUID | None = Field(None, description="Sector Id")
    model: str | None = Field(None, max_length=255, description="Model")
    height_m: Decimal | None = Field(None, ge=0, max_digits=18, decimal_places=4, description="Height M")
    azimuth_deg: Decimal | None = Field(None, max_digits=18, decimal_places=4, description="Azimuth Deg")
    tilt_deg: Decimal | None = Field(None, max_digits=18, decimal_places=4, description="Tilt Deg")
    gain_dbi: Decimal | None = Field(None, max_digits=18, decimal_places=4, description="Gain Dbi")
    source_system: str | None = Field(None, max_length=255, description="Source System")
    region: str | None = Field(None, max_length=255, description="Region")
    owner: str | None = Field(None, max_length=255, description="Owner")
    criticality: AntennaCriticality | None = Field(None, description="Criticality")
    cost_center: str | None = Field(None, max_length=255, description="Cost Center")


class AntennaRead(AntennaBase):
    """Antenna as returned by the system of record."""

    id: UUID
    tenant_id: str
    version: int = 0
    created_at: datetime | None = None
    updated_at: datetime | None = None
    created_by: str | None = None
    updated_by: str | None = None


class AntennaSummary(ApiModel):
    id: UUID
    code: str
    name: str
    status: LifecycleStatus


class AntennaBulkFailure(ApiModel):
    index: int
    message: str


class AntennaBulkResult(ApiModel):
    succeeded: list[AntennaRead] = Field(default_factory=list)
    failures: list[AntennaBulkFailure] = Field(default_factory=list)
