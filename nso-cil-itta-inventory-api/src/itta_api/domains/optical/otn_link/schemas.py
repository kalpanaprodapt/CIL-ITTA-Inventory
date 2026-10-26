"""Pydantic models for Otn Link (Optical)."""
from __future__ import annotations

from datetime import date, datetime
from decimal import Decimal
from enum import Enum
from uuid import UUID

from pydantic import Field, field_validator, model_validator

from itta_core.models import ApiModel, LifecycleStatus, reject_control_chars


class OtnLinkOdu(str, Enum):
    """Allowed values for Otn Link Odu."""

    ODU0 = "ODU0"
    ODU1 = "ODU1"
    ODU2 = "ODU2"
    ODU4 = "ODU4"
    ODUFLEX = "ODUFLEX"


class OtnLinkLifecycleStage(str, Enum):
    """Allowed values for Otn Link Lifecycle Stage."""

    PLAN = "PLAN"
    BUILD = "BUILD"
    OPERATE = "OPERATE"
    RETIRE = "RETIRE"


class OtnLinkBase(ApiModel):
    """Attributes shared by create and read models."""

    code: str = Field(..., max_length=255, min_length=1, description="Code")
    name: str = Field(..., max_length=255, min_length=1, description="Name")
    description: str | None = Field(None, max_length=4000, description="Description")
    status: LifecycleStatus = Field(..., description="Status")
    external_ref: str | None = Field(None, max_length=255, description="External Ref")
    a_node_id: UUID = Field(..., description="A Node Id")
    z_node_id: UUID = Field(..., description="Z Node Id")
    odu: OtnLinkOdu | None = Field(None, description="Odu")
    latency_us: Decimal | None = Field(None, ge=0, max_digits=18, decimal_places=4, description="Latency Us")
    protection: str | None = Field(None, max_length=255, description="Protection")
    effective_to: date | None = Field(None, description="Effective To")
    labels: str | None = Field(None, max_length=255, description="Labels")
    lifecycle_stage: OtnLinkLifecycleStage | None = Field(None, description="Lifecycle Stage")
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

    @field_validator("protection")
    @classmethod
    def _check_protection(cls, value: str | None) -> str | None:
        return reject_control_chars(value, "protection")

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


class OtnLinkCreate(OtnLinkBase):
    """Payload to create a Otn Link."""


class OtnLinkUpdate(ApiModel):
    """Partial update; omitted attributes are left unchanged."""

    expected_version: int | None = Field(None, ge=0, description="Optimistic lock version")
    code: str | None = Field(None, max_length=255, min_length=1, description="Code")
    name: str | None = Field(None, max_length=255, min_length=1, description="Name")
    description: str | None = Field(None, max_length=4000, description="Description")
    status: LifecycleStatus | None = Field(None, description="Status")
    external_ref: str | None = Field(None, max_length=255, description="External Ref")
    a_node_id: UUID | None = Field(None, description="A Node Id")
    z_node_id: UUID | None = Field(None, description="Z Node Id")
    odu: OtnLinkOdu | None = Field(None, description="Odu")
    latency_us: Decimal | None = Field(None, ge=0, max_digits=18, decimal_places=4, description="Latency Us")
    protection: str | None = Field(None, max_length=255, description="Protection")
    effective_to: date | None = Field(None, description="Effective To")
    labels: str | None = Field(None, max_length=255, description="Labels")
    lifecycle_stage: OtnLinkLifecycleStage | None = Field(None, description="Lifecycle Stage")
    source_system: str | None = Field(None, max_length=255, description="Source System")
    region: str | None = Field(None, max_length=255, description="Region")


class OtnLinkRead(OtnLinkBase):
    """Otn Link as returned by the system of record."""

    id: UUID
    tenant_id: str
    version: int = 0
    created_at: datetime | None = None
    updated_at: datetime | None = None
    created_by: str | None = None
    updated_by: str | None = None


class OtnLinkSummary(ApiModel):
    id: UUID
    code: str
    name: str
    status: LifecycleStatus


class OtnLinkBulkFailure(ApiModel):
    index: int
    message: str


class OtnLinkBulkResult(ApiModel):
    succeeded: list[OtnLinkRead] = Field(default_factory=list)
    failures: list[OtnLinkBulkFailure] = Field(default_factory=list)
