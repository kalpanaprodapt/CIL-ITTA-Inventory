"""Pydantic models for Splice (Cabling)."""
from __future__ import annotations

from datetime import date, datetime
from decimal import Decimal
from enum import Enum
from uuid import UUID

from pydantic import Field, field_validator, model_validator

from itta_core.models import ApiModel, LifecycleStatus, reject_control_chars


class SpliceLifecycleStage(str, Enum):
    """Allowed values for Splice Lifecycle Stage."""

    PLAN = "PLAN"
    BUILD = "BUILD"
    OPERATE = "OPERATE"
    RETIRE = "RETIRE"


class SpliceCriticality(str, Enum):
    """Allowed values for Splice Criticality."""

    LOW = "LOW"
    MEDIUM = "MEDIUM"
    HIGH = "HIGH"
    CRITICAL = "CRITICAL"


class SpliceBase(ApiModel):
    """Attributes shared by create and read models."""

    code: str = Field(..., max_length=255, min_length=1, description="Code")
    name: str = Field(..., max_length=255, min_length=1, description="Name")
    description: str | None = Field(None, max_length=4000, description="Description")
    status: LifecycleStatus = Field(..., description="Status")
    external_ref: str | None = Field(None, max_length=255, description="External Ref")
    enclosure_id: str = Field(..., max_length=255, min_length=1, description="Enclosure Id")
    a_strand_id: UUID | None = Field(None, description="A Strand Id")
    z_strand_id: UUID | None = Field(None, description="Z Strand Id")
    loss_db: Decimal | None = Field(None, max_digits=18, decimal_places=4, description="Loss Db")
    spliced_on: date | None = Field(None, description="Spliced On")
    lifecycle_stage: SpliceLifecycleStage | None = Field(None, description="Lifecycle Stage")
    source_system: str | None = Field(None, max_length=255, description="Source System")
    region: str | None = Field(None, max_length=255, description="Region")
    owner: str | None = Field(None, max_length=255, description="Owner")
    criticality: SpliceCriticality | None = Field(None, description="Criticality")

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

    @field_validator("enclosure_id")
    @classmethod
    def _check_enclosure_id(cls, value: str | None) -> str | None:
        return reject_control_chars(value, "enclosureId")

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


class SpliceCreate(SpliceBase):
    """Payload to create a Splice."""


class SpliceUpdate(ApiModel):
    """Partial update; omitted attributes are left unchanged."""

    expected_version: int | None = Field(None, ge=0, description="Optimistic lock version")
    code: str | None = Field(None, max_length=255, min_length=1, description="Code")
    name: str | None = Field(None, max_length=255, min_length=1, description="Name")
    description: str | None = Field(None, max_length=4000, description="Description")
    status: LifecycleStatus | None = Field(None, description="Status")
    external_ref: str | None = Field(None, max_length=255, description="External Ref")
    enclosure_id: str | None = Field(None, max_length=255, min_length=1, description="Enclosure Id")
    a_strand_id: UUID | None = Field(None, description="A Strand Id")
    z_strand_id: UUID | None = Field(None, description="Z Strand Id")
    loss_db: Decimal | None = Field(None, max_digits=18, decimal_places=4, description="Loss Db")
    spliced_on: date | None = Field(None, description="Spliced On")
    lifecycle_stage: SpliceLifecycleStage | None = Field(None, description="Lifecycle Stage")
    source_system: str | None = Field(None, max_length=255, description="Source System")
    region: str | None = Field(None, max_length=255, description="Region")
    owner: str | None = Field(None, max_length=255, description="Owner")
    criticality: SpliceCriticality | None = Field(None, description="Criticality")


class SpliceRead(SpliceBase):
    """Splice as returned by the system of record."""

    id: UUID
    tenant_id: str
    version: int = 0
    created_at: datetime | None = None
    updated_at: datetime | None = None
    created_by: str | None = None
    updated_by: str | None = None


class SpliceSummary(ApiModel):
    id: UUID
    code: str
    name: str
    status: LifecycleStatus


class SpliceBulkFailure(ApiModel):
    index: int
    message: str


class SpliceBulkResult(ApiModel):
    succeeded: list[SpliceRead] = Field(default_factory=list)
    failures: list[SpliceBulkFailure] = Field(default_factory=list)
