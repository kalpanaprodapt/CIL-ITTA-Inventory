"""Pydantic models for Topology View (Topology and Links)."""
from __future__ import annotations

from datetime import date, datetime
from decimal import Decimal
from enum import Enum
from uuid import UUID

from pydantic import Field, field_validator, model_validator

from itta_core.models import ApiModel, LifecycleStatus, reject_control_chars


class TopologyViewViewType(str, Enum):
    """Allowed values for Topology View View Type."""

    PHYSICAL = "PHYSICAL"
    L2 = "L2"
    L3 = "L3"
    SERVICE = "SERVICE"
    OPTICAL = "OPTICAL"


class TopologyViewLifecycleStage(str, Enum):
    """Allowed values for Topology View Lifecycle Stage."""

    PLAN = "PLAN"
    BUILD = "BUILD"
    OPERATE = "OPERATE"
    RETIRE = "RETIRE"


class TopologyViewBase(ApiModel):
    """Attributes shared by create and read models."""

    code: str = Field(..., max_length=255, min_length=1, description="Code")
    name: str = Field(..., max_length=255, min_length=1, description="Name")
    description: str | None = Field(None, max_length=4000, description="Description")
    status: LifecycleStatus = Field(..., description="Status")
    external_ref: str | None = Field(None, max_length=255, description="External Ref")
    view_name: str = Field(..., max_length=255, min_length=1, description="View Name")
    view_type: TopologyViewViewType | None = Field(None, description="View Type")
    scope_filter: str | None = Field(None, max_length=255, description="Scope Filter")
    refreshed_at: datetime | None = Field(None, description="Refreshed At")
    effective_to: date | None = Field(None, description="Effective To")
    labels: str | None = Field(None, max_length=255, description="Labels")
    lifecycle_stage: TopologyViewLifecycleStage | None = Field(None, description="Lifecycle Stage")
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

    @field_validator("view_name")
    @classmethod
    def _check_view_name(cls, value: str | None) -> str | None:
        return reject_control_chars(value, "viewName")

    @field_validator("scope_filter")
    @classmethod
    def _check_scope_filter(cls, value: str | None) -> str | None:
        return reject_control_chars(value, "scopeFilter")

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


class TopologyViewCreate(TopologyViewBase):
    """Payload to create a Topology View."""


class TopologyViewUpdate(ApiModel):
    """Partial update; omitted attributes are left unchanged."""

    expected_version: int | None = Field(None, ge=0, description="Optimistic lock version")
    code: str | None = Field(None, max_length=255, min_length=1, description="Code")
    name: str | None = Field(None, max_length=255, min_length=1, description="Name")
    description: str | None = Field(None, max_length=4000, description="Description")
    status: LifecycleStatus | None = Field(None, description="Status")
    external_ref: str | None = Field(None, max_length=255, description="External Ref")
    view_name: str | None = Field(None, max_length=255, min_length=1, description="View Name")
    view_type: TopologyViewViewType | None = Field(None, description="View Type")
    scope_filter: str | None = Field(None, max_length=255, description="Scope Filter")
    refreshed_at: datetime | None = Field(None, description="Refreshed At")
    effective_to: date | None = Field(None, description="Effective To")
    labels: str | None = Field(None, max_length=255, description="Labels")
    lifecycle_stage: TopologyViewLifecycleStage | None = Field(None, description="Lifecycle Stage")
    source_system: str | None = Field(None, max_length=255, description="Source System")
    region: str | None = Field(None, max_length=255, description="Region")


class TopologyViewRead(TopologyViewBase):
    """Topology View as returned by the system of record."""

    id: UUID
    tenant_id: str
    version: int = 0
    created_at: datetime | None = None
    updated_at: datetime | None = None
    created_by: str | None = None
    updated_by: str | None = None


class TopologyViewSummary(ApiModel):
    id: UUID
    code: str
    name: str
    status: LifecycleStatus


class TopologyViewBulkFailure(ApiModel):
    index: int
    message: str


class TopologyViewBulkResult(ApiModel):
    succeeded: list[TopologyViewRead] = Field(default_factory=list)
    failures: list[TopologyViewBulkFailure] = Field(default_factory=list)
