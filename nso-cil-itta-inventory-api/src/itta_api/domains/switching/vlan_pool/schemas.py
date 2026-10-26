"""Pydantic models for Vlan Pool (L2 and L3 Routing)."""
from __future__ import annotations

from datetime import date, datetime
from decimal import Decimal
from enum import Enum
from uuid import UUID

from pydantic import Field, field_validator, model_validator

from itta_core.models import ApiModel, LifecycleStatus, reject_control_chars


class VlanPoolLifecycleStage(str, Enum):
    """Allowed values for Vlan Pool Lifecycle Stage."""

    PLAN = "PLAN"
    BUILD = "BUILD"
    OPERATE = "OPERATE"
    RETIRE = "RETIRE"


class VlanPoolBase(ApiModel):
    """Attributes shared by create and read models."""

    code: str = Field(..., max_length=255, min_length=1, description="Code")
    name: str = Field(..., max_length=255, min_length=1, description="Name")
    description: str | None = Field(None, max_length=4000, description="Description")
    status: LifecycleStatus = Field(..., description="Status")
    external_ref: str | None = Field(None, max_length=255, description="External Ref")
    pool_name: str = Field(..., max_length=255, min_length=1, description="Pool Name")
    range_start: int = Field(..., ge=0, description="Range Start")
    range_end: int = Field(..., ge=0, description="Range End")
    allocated: int | None = Field(None, ge=0, description="Allocated")
    scope: str | None = Field(None, max_length=255, description="Scope")
    effective_to: date | None = Field(None, description="Effective To")
    labels: str | None = Field(None, max_length=255, description="Labels")
    lifecycle_stage: VlanPoolLifecycleStage | None = Field(None, description="Lifecycle Stage")
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

    @field_validator("scope")
    @classmethod
    def _check_scope(cls, value: str | None) -> str | None:
        return reject_control_chars(value, "scope")

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

    @model_validator(mode="after")
    def _check_ranges(self) -> "VlanPoolBase":
        if self.range_start is not None and self.range_end is not None and self.range_end < self.range_start:
            raise ValueError("range_end must not be earlier than range_start")
        return self


class VlanPoolCreate(VlanPoolBase):
    """Payload to create a Vlan Pool."""


class VlanPoolUpdate(ApiModel):
    """Partial update; omitted attributes are left unchanged."""

    expected_version: int | None = Field(None, ge=0, description="Optimistic lock version")
    code: str | None = Field(None, max_length=255, min_length=1, description="Code")
    name: str | None = Field(None, max_length=255, min_length=1, description="Name")
    description: str | None = Field(None, max_length=4000, description="Description")
    status: LifecycleStatus | None = Field(None, description="Status")
    external_ref: str | None = Field(None, max_length=255, description="External Ref")
    pool_name: str | None = Field(None, max_length=255, min_length=1, description="Pool Name")
    range_start: int | None = Field(None, ge=0, description="Range Start")
    range_end: int | None = Field(None, ge=0, description="Range End")
    allocated: int | None = Field(None, ge=0, description="Allocated")
    scope: str | None = Field(None, max_length=255, description="Scope")
    effective_to: date | None = Field(None, description="Effective To")
    labels: str | None = Field(None, max_length=255, description="Labels")
    lifecycle_stage: VlanPoolLifecycleStage | None = Field(None, description="Lifecycle Stage")
    source_system: str | None = Field(None, max_length=255, description="Source System")
    region: str | None = Field(None, max_length=255, description="Region")


class VlanPoolRead(VlanPoolBase):
    """Vlan Pool as returned by the system of record."""

    id: UUID
    tenant_id: str
    version: int = 0
    created_at: datetime | None = None
    updated_at: datetime | None = None
    created_by: str | None = None
    updated_by: str | None = None


class VlanPoolSummary(ApiModel):
    id: UUID
    code: str
    name: str
    status: LifecycleStatus


class VlanPoolBulkFailure(ApiModel):
    index: int
    message: str


class VlanPoolBulkResult(ApiModel):
    succeeded: list[VlanPoolRead] = Field(default_factory=list)
    failures: list[VlanPoolBulkFailure] = Field(default_factory=list)
