"""Pydantic models for Discovery Job (Discovery and Reconciliation)."""
from __future__ import annotations

from datetime import date, datetime
from decimal import Decimal
from enum import Enum
from uuid import UUID

from pydantic import Field, field_validator, model_validator

from itta_core.models import ApiModel, LifecycleStatus, reject_control_chars


class DiscoveryJobJobState(str, Enum):
    """Allowed values for Discovery Job Job State."""

    QUEUED = "QUEUED"
    RUNNING = "RUNNING"
    SUCCEEDED = "SUCCEEDED"
    FAILED = "FAILED"
    CANCELLED = "CANCELLED"


class DiscoveryJobCriticality(str, Enum):
    """Allowed values for Discovery Job Criticality."""

    LOW = "LOW"
    MEDIUM = "MEDIUM"
    HIGH = "HIGH"
    CRITICAL = "CRITICAL"


class DiscoveryJobBase(ApiModel):
    """Attributes shared by create and read models."""

    code: str = Field(..., max_length=255, min_length=1, description="Code")
    name: str = Field(..., max_length=255, min_length=1, description="Name")
    description: str | None = Field(None, max_length=4000, description="Description")
    status: LifecycleStatus = Field(..., description="Status")
    external_ref: str | None = Field(None, max_length=255, description="External Ref")
    profile_id: UUID = Field(..., description="Profile Id")
    started_at: datetime | None = Field(None, description="Started At")
    finished_at: datetime | None = Field(None, description="Finished At")
    job_state: DiscoveryJobJobState | None = Field(None, description="Job State")
    assets_found: int | None = Field(None, ge=0, description="Assets Found")
    owner: str | None = Field(None, max_length=255, description="Owner")
    criticality: DiscoveryJobCriticality | None = Field(None, description="Criticality")
    cost_center: str | None = Field(None, max_length=255, description="Cost Center")
    notes: str | None = Field(None, max_length=4000, description="Notes")
    effective_from: date | None = Field(None, description="Effective From")

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

    @field_validator("owner")
    @classmethod
    def _check_owner(cls, value: str | None) -> str | None:
        return reject_control_chars(value, "owner")

    @field_validator("cost_center")
    @classmethod
    def _check_cost_center(cls, value: str | None) -> str | None:
        return reject_control_chars(value, "costCenter")

    @field_validator("notes")
    @classmethod
    def _check_notes(cls, value: str | None) -> str | None:
        return reject_control_chars(value, "notes")

    @model_validator(mode="after")
    def _check_ranges(self) -> "DiscoveryJobBase":
        if self.started_at is not None and self.finished_at is not None and self.finished_at < self.started_at:
            raise ValueError("finished_at must not be earlier than started_at")
        return self


class DiscoveryJobCreate(DiscoveryJobBase):
    """Payload to create a Discovery Job."""


class DiscoveryJobUpdate(ApiModel):
    """Partial update; omitted attributes are left unchanged."""

    expected_version: int | None = Field(None, ge=0, description="Optimistic lock version")
    code: str | None = Field(None, max_length=255, min_length=1, description="Code")
    name: str | None = Field(None, max_length=255, min_length=1, description="Name")
    description: str | None = Field(None, max_length=4000, description="Description")
    status: LifecycleStatus | None = Field(None, description="Status")
    external_ref: str | None = Field(None, max_length=255, description="External Ref")
    profile_id: UUID | None = Field(None, description="Profile Id")
    started_at: datetime | None = Field(None, description="Started At")
    finished_at: datetime | None = Field(None, description="Finished At")
    job_state: DiscoveryJobJobState | None = Field(None, description="Job State")
    assets_found: int | None = Field(None, ge=0, description="Assets Found")
    owner: str | None = Field(None, max_length=255, description="Owner")
    criticality: DiscoveryJobCriticality | None = Field(None, description="Criticality")
    cost_center: str | None = Field(None, max_length=255, description="Cost Center")
    notes: str | None = Field(None, max_length=4000, description="Notes")
    effective_from: date | None = Field(None, description="Effective From")


class DiscoveryJobRead(DiscoveryJobBase):
    """Discovery Job as returned by the system of record."""

    id: UUID
    tenant_id: str
    version: int = 0
    created_at: datetime | None = None
    updated_at: datetime | None = None
    created_by: str | None = None
    updated_by: str | None = None


class DiscoveryJobSummary(ApiModel):
    id: UUID
    code: str
    name: str
    status: LifecycleStatus


class DiscoveryJobBulkFailure(ApiModel):
    index: int
    message: str


class DiscoveryJobBulkResult(ApiModel):
    succeeded: list[DiscoveryJobRead] = Field(default_factory=list)
    failures: list[DiscoveryJobBulkFailure] = Field(default_factory=list)
