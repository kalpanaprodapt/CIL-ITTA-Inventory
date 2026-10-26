"""Pydantic models for Topology Edge (Topology and Links)."""
from __future__ import annotations

from datetime import date, datetime
from decimal import Decimal
from enum import Enum
from uuid import UUID

from pydantic import Field, field_validator, model_validator

from itta_core.models import ApiModel, LifecycleStatus, reject_control_chars


class TopologyEdgeLifecycleStage(str, Enum):
    """Allowed values for Topology Edge Lifecycle Stage."""

    PLAN = "PLAN"
    BUILD = "BUILD"
    OPERATE = "OPERATE"
    RETIRE = "RETIRE"


class TopologyEdgeBase(ApiModel):
    """Attributes shared by create and read models."""

    code: str = Field(..., max_length=255, min_length=1, description="Code")
    name: str = Field(..., max_length=255, min_length=1, description="Name")
    description: str | None = Field(None, max_length=4000, description="Description")
    status: LifecycleStatus = Field(..., description="Status")
    external_ref: str | None = Field(None, max_length=255, description="External Ref")
    view_id: UUID = Field(..., description="View Id")
    source_node_id: UUID = Field(..., description="Source Node Id")
    target_node_id: UUID = Field(..., description="Target Node Id")
    weight: Decimal | None = Field(None, ge=0, max_digits=18, decimal_places=4, description="Weight")
    directed: bool | None = Field(None, description="Directed")
    notes: str | None = Field(None, max_length=4000, description="Notes")
    effective_from: date | None = Field(None, description="Effective From")
    effective_to: date | None = Field(None, description="Effective To")
    labels: str | None = Field(None, max_length=255, description="Labels")
    lifecycle_stage: TopologyEdgeLifecycleStage | None = Field(None, description="Lifecycle Stage")

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

    @field_validator("notes")
    @classmethod
    def _check_notes(cls, value: str | None) -> str | None:
        return reject_control_chars(value, "notes")

    @field_validator("labels")
    @classmethod
    def _check_labels(cls, value: str | None) -> str | None:
        return reject_control_chars(value, "labels")

    @model_validator(mode="after")
    def _check_ranges(self) -> "TopologyEdgeBase":
        if self.effective_from is not None and self.effective_to is not None and self.effective_to < self.effective_from:
            raise ValueError("effective_to must not be earlier than effective_from")
        return self


class TopologyEdgeCreate(TopologyEdgeBase):
    """Payload to create a Topology Edge."""


class TopologyEdgeUpdate(ApiModel):
    """Partial update; omitted attributes are left unchanged."""

    expected_version: int | None = Field(None, ge=0, description="Optimistic lock version")
    code: str | None = Field(None, max_length=255, min_length=1, description="Code")
    name: str | None = Field(None, max_length=255, min_length=1, description="Name")
    description: str | None = Field(None, max_length=4000, description="Description")
    status: LifecycleStatus | None = Field(None, description="Status")
    external_ref: str | None = Field(None, max_length=255, description="External Ref")
    view_id: UUID | None = Field(None, description="View Id")
    source_node_id: UUID | None = Field(None, description="Source Node Id")
    target_node_id: UUID | None = Field(None, description="Target Node Id")
    weight: Decimal | None = Field(None, ge=0, max_digits=18, decimal_places=4, description="Weight")
    directed: bool | None = Field(None, description="Directed")
    notes: str | None = Field(None, max_length=4000, description="Notes")
    effective_from: date | None = Field(None, description="Effective From")
    effective_to: date | None = Field(None, description="Effective To")
    labels: str | None = Field(None, max_length=255, description="Labels")
    lifecycle_stage: TopologyEdgeLifecycleStage | None = Field(None, description="Lifecycle Stage")


class TopologyEdgeRead(TopologyEdgeBase):
    """Topology Edge as returned by the system of record."""

    id: UUID
    tenant_id: str
    version: int = 0
    created_at: datetime | None = None
    updated_at: datetime | None = None
    created_by: str | None = None
    updated_by: str | None = None


class TopologyEdgeSummary(ApiModel):
    id: UUID
    code: str
    name: str
    status: LifecycleStatus


class TopologyEdgeBulkFailure(ApiModel):
    index: int
    message: str


class TopologyEdgeBulkResult(ApiModel):
    succeeded: list[TopologyEdgeRead] = Field(default_factory=list)
    failures: list[TopologyEdgeBulkFailure] = Field(default_factory=list)
