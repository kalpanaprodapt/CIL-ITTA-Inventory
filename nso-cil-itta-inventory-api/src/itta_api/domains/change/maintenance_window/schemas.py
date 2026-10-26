"""Pydantic models for Maintenance Window (Change and Work Management)."""
from __future__ import annotations

from datetime import date, datetime
from decimal import Decimal
from enum import Enum
from uuid import UUID

from pydantic import Field, field_validator, model_validator

from itta_core.models import ApiModel, LifecycleStatus, reject_control_chars


class MaintenanceWindowImpactLevel(str, Enum):
    """Allowed values for Maintenance Window Impact Level."""

    NONE = "NONE"
    DEGRADED = "DEGRADED"
    OUTAGE = "OUTAGE"


class MaintenanceWindowLifecycleStage(str, Enum):
    """Allowed values for Maintenance Window Lifecycle Stage."""

    PLAN = "PLAN"
    BUILD = "BUILD"
    OPERATE = "OPERATE"
    RETIRE = "RETIRE"


class MaintenanceWindowBase(ApiModel):
    """Attributes shared by create and read models."""

    code: str = Field(..., max_length=255, min_length=1, description="Code")
    name: str = Field(..., max_length=255, min_length=1, description="Name")
    description: str | None = Field(None, max_length=4000, description="Description")
    status: LifecycleStatus = Field(..., description="Status")
    external_ref: str | None = Field(None, max_length=255, description="External Ref")
    window_name: str = Field(..., max_length=255, min_length=1, description="Window Name")
    starts_at: datetime = Field(..., description="Starts At")
    ends_at: datetime = Field(..., description="Ends At")
    scope: str | None = Field(None, max_length=255, description="Scope")
    impact_level: MaintenanceWindowImpactLevel | None = Field(None, description="Impact Level")
    effective_to: date | None = Field(None, description="Effective To")
    labels: str | None = Field(None, max_length=255, description="Labels")
    lifecycle_stage: MaintenanceWindowLifecycleStage | None = Field(None, description="Lifecycle Stage")
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

    @field_validator("window_name")
    @classmethod
    def _check_window_name(cls, value: str | None) -> str | None:
        return reject_control_chars(value, "windowName")

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
    def _check_ranges(self) -> "MaintenanceWindowBase":
        if self.starts_at is not None and self.ends_at is not None and self.ends_at < self.starts_at:
            raise ValueError("ends_at must not be earlier than starts_at")
        return self


class MaintenanceWindowCreate(MaintenanceWindowBase):
    """Payload to create a Maintenance Window."""


class MaintenanceWindowUpdate(ApiModel):
    """Partial update; omitted attributes are left unchanged."""

    expected_version: int | None = Field(None, ge=0, description="Optimistic lock version")
    code: str | None = Field(None, max_length=255, min_length=1, description="Code")
    name: str | None = Field(None, max_length=255, min_length=1, description="Name")
    description: str | None = Field(None, max_length=4000, description="Description")
    status: LifecycleStatus | None = Field(None, description="Status")
    external_ref: str | None = Field(None, max_length=255, description="External Ref")
    window_name: str | None = Field(None, max_length=255, min_length=1, description="Window Name")
    starts_at: datetime | None = Field(None, description="Starts At")
    ends_at: datetime | None = Field(None, description="Ends At")
    scope: str | None = Field(None, max_length=255, description="Scope")
    impact_level: MaintenanceWindowImpactLevel | None = Field(None, description="Impact Level")
    effective_to: date | None = Field(None, description="Effective To")
    labels: str | None = Field(None, max_length=255, description="Labels")
    lifecycle_stage: MaintenanceWindowLifecycleStage | None = Field(None, description="Lifecycle Stage")
    source_system: str | None = Field(None, max_length=255, description="Source System")
    region: str | None = Field(None, max_length=255, description="Region")


class MaintenanceWindowRead(MaintenanceWindowBase):
    """Maintenance Window as returned by the system of record."""

    id: UUID
    tenant_id: str
    version: int = 0
    created_at: datetime | None = None
    updated_at: datetime | None = None
    created_by: str | None = None
    updated_by: str | None = None


class MaintenanceWindowSummary(ApiModel):
    id: UUID
    code: str
    name: str
    status: LifecycleStatus


class MaintenanceWindowBulkFailure(ApiModel):
    index: int
    message: str


class MaintenanceWindowBulkResult(ApiModel):
    succeeded: list[MaintenanceWindowRead] = Field(default_factory=list)
    failures: list[MaintenanceWindowBulkFailure] = Field(default_factory=list)
