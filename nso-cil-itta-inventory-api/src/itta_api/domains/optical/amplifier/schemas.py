"""Pydantic models for Amplifier (Optical)."""
from __future__ import annotations

from datetime import date, datetime
from decimal import Decimal
from enum import Enum
from uuid import UUID

from pydantic import Field, field_validator, model_validator

from itta_core.models import ApiModel, LifecycleStatus, reject_control_chars


class AmplifierAmpType(str, Enum):
    """Allowed values for Amplifier Amp Type."""

    EDFA = "EDFA"
    RAMAN = "RAMAN"
    HYBRID = "HYBRID"


class AmplifierLifecycleStage(str, Enum):
    """Allowed values for Amplifier Lifecycle Stage."""

    PLAN = "PLAN"
    BUILD = "BUILD"
    OPERATE = "OPERATE"
    RETIRE = "RETIRE"


class AmplifierCriticality(str, Enum):
    """Allowed values for Amplifier Criticality."""

    LOW = "LOW"
    MEDIUM = "MEDIUM"
    HIGH = "HIGH"
    CRITICAL = "CRITICAL"


class AmplifierBase(ApiModel):
    """Attributes shared by create and read models."""

    code: str = Field(..., max_length=255, min_length=1, description="Code")
    name: str = Field(..., max_length=255, min_length=1, description="Name")
    description: str | None = Field(None, max_length=4000, description="Description")
    status: LifecycleStatus = Field(..., description="Status")
    external_ref: str | None = Field(None, max_length=255, description="External Ref")
    system_id: UUID = Field(..., description="System Id")
    amp_type: AmplifierAmpType | None = Field(None, description="Amp Type")
    gain_db: Decimal = Field(..., max_digits=18, decimal_places=4, description="Gain Db")
    output_power_dbm: Decimal | None = Field(None, max_digits=18, decimal_places=4, description="Output Power Dbm")
    noise_figure_db: Decimal | None = Field(None, max_digits=18, decimal_places=4, description="Noise Figure Db")
    lifecycle_stage: AmplifierLifecycleStage | None = Field(None, description="Lifecycle Stage")
    source_system: str | None = Field(None, max_length=255, description="Source System")
    region: str | None = Field(None, max_length=255, description="Region")
    owner: str | None = Field(None, max_length=255, description="Owner")
    criticality: AmplifierCriticality | None = Field(None, description="Criticality")

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


class AmplifierCreate(AmplifierBase):
    """Payload to create a Amplifier."""


class AmplifierUpdate(ApiModel):
    """Partial update; omitted attributes are left unchanged."""

    expected_version: int | None = Field(None, ge=0, description="Optimistic lock version")
    code: str | None = Field(None, max_length=255, min_length=1, description="Code")
    name: str | None = Field(None, max_length=255, min_length=1, description="Name")
    description: str | None = Field(None, max_length=4000, description="Description")
    status: LifecycleStatus | None = Field(None, description="Status")
    external_ref: str | None = Field(None, max_length=255, description="External Ref")
    system_id: UUID | None = Field(None, description="System Id")
    amp_type: AmplifierAmpType | None = Field(None, description="Amp Type")
    gain_db: Decimal | None = Field(None, max_digits=18, decimal_places=4, description="Gain Db")
    output_power_dbm: Decimal | None = Field(None, max_digits=18, decimal_places=4, description="Output Power Dbm")
    noise_figure_db: Decimal | None = Field(None, max_digits=18, decimal_places=4, description="Noise Figure Db")
    lifecycle_stage: AmplifierLifecycleStage | None = Field(None, description="Lifecycle Stage")
    source_system: str | None = Field(None, max_length=255, description="Source System")
    region: str | None = Field(None, max_length=255, description="Region")
    owner: str | None = Field(None, max_length=255, description="Owner")
    criticality: AmplifierCriticality | None = Field(None, description="Criticality")


class AmplifierRead(AmplifierBase):
    """Amplifier as returned by the system of record."""

    id: UUID
    tenant_id: str
    version: int = 0
    created_at: datetime | None = None
    updated_at: datetime | None = None
    created_by: str | None = None
    updated_by: str | None = None


class AmplifierSummary(ApiModel):
    id: UUID
    code: str
    name: str
    status: LifecycleStatus


class AmplifierBulkFailure(ApiModel):
    index: int
    message: str


class AmplifierBulkResult(ApiModel):
    succeeded: list[AmplifierRead] = Field(default_factory=list)
    failures: list[AmplifierBulkFailure] = Field(default_factory=list)
