"""Pydantic models for Lag Group (Port and Interface)."""
from __future__ import annotations

from datetime import date, datetime
from decimal import Decimal
from enum import Enum
from uuid import UUID

from pydantic import Field, field_validator, model_validator

from itta_core.models import ApiModel, LifecycleStatus, reject_control_chars


class LagGroupLacpMode(str, Enum):
    """Allowed values for Lag Group Lacp Mode."""

    ACTIVE = "ACTIVE"
    PASSIVE = "PASSIVE"
    STATIC = "STATIC"


class LagGroupLifecycleStage(str, Enum):
    """Allowed values for Lag Group Lifecycle Stage."""

    PLAN = "PLAN"
    BUILD = "BUILD"
    OPERATE = "OPERATE"
    RETIRE = "RETIRE"


class LagGroupBase(ApiModel):
    """Attributes shared by create and read models."""

    code: str = Field(..., max_length=255, min_length=1, description="Code")
    name: str = Field(..., max_length=255, min_length=1, description="Name")
    description: str | None = Field(None, max_length=4000, description="Description")
    status: LifecycleStatus = Field(..., description="Status")
    external_ref: str | None = Field(None, max_length=255, description="External Ref")
    device_id: UUID = Field(..., description="Device Id")
    lag_name: str = Field(..., max_length=255, min_length=1, description="Lag Name")
    member_count: int | None = Field(None, ge=0, description="Member Count")
    lacp_mode: LagGroupLacpMode | None = Field(None, description="Lacp Mode")
    min_links: int | None = Field(None, ge=0, description="Min Links")
    effective_from: date | None = Field(None, description="Effective From")
    effective_to: date | None = Field(None, description="Effective To")
    labels: str | None = Field(None, max_length=255, description="Labels")
    lifecycle_stage: LagGroupLifecycleStage | None = Field(None, description="Lifecycle Stage")
    source_system: str | None = Field(None, max_length=255, description="Source System")

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

    @field_validator("lag_name")
    @classmethod
    def _check_lag_name(cls, value: str | None) -> str | None:
        return reject_control_chars(value, "lagName")

    @field_validator("labels")
    @classmethod
    def _check_labels(cls, value: str | None) -> str | None:
        return reject_control_chars(value, "labels")

    @field_validator("source_system")
    @classmethod
    def _check_source_system(cls, value: str | None) -> str | None:
        return reject_control_chars(value, "sourceSystem")

    @model_validator(mode="after")
    def _check_ranges(self) -> "LagGroupBase":
        if self.effective_from is not None and self.effective_to is not None and self.effective_to < self.effective_from:
            raise ValueError("effective_to must not be earlier than effective_from")
        return self


class LagGroupCreate(LagGroupBase):
    """Payload to create a Lag Group."""


class LagGroupUpdate(ApiModel):
    """Partial update; omitted attributes are left unchanged."""

    expected_version: int | None = Field(None, ge=0, description="Optimistic lock version")
    code: str | None = Field(None, max_length=255, min_length=1, description="Code")
    name: str | None = Field(None, max_length=255, min_length=1, description="Name")
    description: str | None = Field(None, max_length=4000, description="Description")
    status: LifecycleStatus | None = Field(None, description="Status")
    external_ref: str | None = Field(None, max_length=255, description="External Ref")
    device_id: UUID | None = Field(None, description="Device Id")
    lag_name: str | None = Field(None, max_length=255, min_length=1, description="Lag Name")
    member_count: int | None = Field(None, ge=0, description="Member Count")
    lacp_mode: LagGroupLacpMode | None = Field(None, description="Lacp Mode")
    min_links: int | None = Field(None, ge=0, description="Min Links")
    effective_from: date | None = Field(None, description="Effective From")
    effective_to: date | None = Field(None, description="Effective To")
    labels: str | None = Field(None, max_length=255, description="Labels")
    lifecycle_stage: LagGroupLifecycleStage | None = Field(None, description="Lifecycle Stage")
    source_system: str | None = Field(None, max_length=255, description="Source System")


class LagGroupRead(LagGroupBase):
    """Lag Group as returned by the system of record."""

    id: UUID
    tenant_id: str
    version: int = 0
    created_at: datetime | None = None
    updated_at: datetime | None = None
    created_by: str | None = None
    updated_by: str | None = None


class LagGroupSummary(ApiModel):
    id: UUID
    code: str
    name: str
    status: LifecycleStatus


class LagGroupBulkFailure(ApiModel):
    index: int
    message: str


class LagGroupBulkResult(ApiModel):
    succeeded: list[LagGroupRead] = Field(default_factory=list)
    failures: list[LagGroupBulkFailure] = Field(default_factory=list)
