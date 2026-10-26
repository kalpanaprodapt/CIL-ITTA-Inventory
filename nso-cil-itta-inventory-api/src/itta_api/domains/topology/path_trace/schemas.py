"""Pydantic models for Path Trace (Topology and Links)."""
from __future__ import annotations

from datetime import date, datetime
from decimal import Decimal
from enum import Enum
from uuid import UUID

from pydantic import Field, field_validator, model_validator

from itta_core.models import ApiModel, LifecycleStatus, reject_control_chars


class PathTraceLifecycleStage(str, Enum):
    """Allowed values for Path Trace Lifecycle Stage."""

    PLAN = "PLAN"
    BUILD = "BUILD"
    OPERATE = "OPERATE"
    RETIRE = "RETIRE"


class PathTraceCriticality(str, Enum):
    """Allowed values for Path Trace Criticality."""

    LOW = "LOW"
    MEDIUM = "MEDIUM"
    HIGH = "HIGH"
    CRITICAL = "CRITICAL"


class PathTraceBase(ApiModel):
    """Attributes shared by create and read models."""

    code: str = Field(..., max_length=255, min_length=1, description="Code")
    name: str = Field(..., max_length=255, min_length=1, description="Name")
    description: str | None = Field(None, max_length=4000, description="Description")
    status: LifecycleStatus = Field(..., description="Status")
    external_ref: str | None = Field(None, max_length=255, description="External Ref")
    source_ref: str = Field(..., max_length=255, min_length=1, description="Source Ref")
    target_ref: str = Field(..., max_length=255, min_length=1, description="Target Ref")
    hop_count: int | None = Field(None, ge=0, description="Hop Count")
    total_latency_ms: Decimal | None = Field(None, ge=0, max_digits=18, decimal_places=4, description="Total Latency Ms")
    trace_method: str | None = Field(None, max_length=255, description="Trace Method")
    traced_at: datetime | None = Field(None, description="Traced At")
    lifecycle_stage: PathTraceLifecycleStage | None = Field(None, description="Lifecycle Stage")
    source_system: str | None = Field(None, max_length=255, description="Source System")
    region: str | None = Field(None, max_length=255, description="Region")
    owner: str | None = Field(None, max_length=255, description="Owner")
    criticality: PathTraceCriticality | None = Field(None, description="Criticality")

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

    @field_validator("source_ref")
    @classmethod
    def _check_source_ref(cls, value: str | None) -> str | None:
        return reject_control_chars(value, "sourceRef")

    @field_validator("target_ref")
    @classmethod
    def _check_target_ref(cls, value: str | None) -> str | None:
        return reject_control_chars(value, "targetRef")

    @field_validator("trace_method")
    @classmethod
    def _check_trace_method(cls, value: str | None) -> str | None:
        return reject_control_chars(value, "traceMethod")

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


class PathTraceCreate(PathTraceBase):
    """Payload to create a Path Trace."""


class PathTraceUpdate(ApiModel):
    """Partial update; omitted attributes are left unchanged."""

    expected_version: int | None = Field(None, ge=0, description="Optimistic lock version")
    code: str | None = Field(None, max_length=255, min_length=1, description="Code")
    name: str | None = Field(None, max_length=255, min_length=1, description="Name")
    description: str | None = Field(None, max_length=4000, description="Description")
    status: LifecycleStatus | None = Field(None, description="Status")
    external_ref: str | None = Field(None, max_length=255, description="External Ref")
    source_ref: str | None = Field(None, max_length=255, min_length=1, description="Source Ref")
    target_ref: str | None = Field(None, max_length=255, min_length=1, description="Target Ref")
    hop_count: int | None = Field(None, ge=0, description="Hop Count")
    total_latency_ms: Decimal | None = Field(None, ge=0, max_digits=18, decimal_places=4, description="Total Latency Ms")
    trace_method: str | None = Field(None, max_length=255, description="Trace Method")
    traced_at: datetime | None = Field(None, description="Traced At")
    lifecycle_stage: PathTraceLifecycleStage | None = Field(None, description="Lifecycle Stage")
    source_system: str | None = Field(None, max_length=255, description="Source System")
    region: str | None = Field(None, max_length=255, description="Region")
    owner: str | None = Field(None, max_length=255, description="Owner")
    criticality: PathTraceCriticality | None = Field(None, description="Criticality")


class PathTraceRead(PathTraceBase):
    """Path Trace as returned by the system of record."""

    id: UUID
    tenant_id: str
    version: int = 0
    created_at: datetime | None = None
    updated_at: datetime | None = None
    created_by: str | None = None
    updated_by: str | None = None


class PathTraceSummary(ApiModel):
    id: UUID
    code: str
    name: str
    status: LifecycleStatus


class PathTraceBulkFailure(ApiModel):
    index: int
    message: str


class PathTraceBulkResult(ApiModel):
    succeeded: list[PathTraceRead] = Field(default_factory=list)
    failures: list[PathTraceBulkFailure] = Field(default_factory=list)
