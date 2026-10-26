"""Pydantic models for Sync Job (Integration Layer)."""
from __future__ import annotations

from datetime import date, datetime
from decimal import Decimal
from enum import Enum
from uuid import UUID

from pydantic import Field, field_validator, model_validator

from itta_core.models import ApiModel, LifecycleStatus, reject_control_chars


class SyncJobDirection(str, Enum):
    """Allowed values for Sync Job Direction."""

    INBOUND = "INBOUND"
    OUTBOUND = "OUTBOUND"
    BIDIRECTIONAL = "BIDIRECTIONAL"


class SyncJobLastStatus(str, Enum):
    """Allowed values for Sync Job Last Status."""

    OK = "OK"
    PARTIAL = "PARTIAL"
    FAILED = "FAILED"


class SyncJobCriticality(str, Enum):
    """Allowed values for Sync Job Criticality."""

    LOW = "LOW"
    MEDIUM = "MEDIUM"
    HIGH = "HIGH"
    CRITICAL = "CRITICAL"


class SyncJobBase(ApiModel):
    """Attributes shared by create and read models."""

    code: str = Field(..., max_length=255, min_length=1, description="Code")
    name: str = Field(..., max_length=255, min_length=1, description="Name")
    description: str | None = Field(None, max_length=4000, description="Description")
    status: LifecycleStatus = Field(..., description="Status")
    external_ref: str | None = Field(None, max_length=255, description="External Ref")
    endpoint_id: UUID = Field(..., description="Endpoint Id")
    direction: SyncJobDirection | None = Field(None, description="Direction")
    schedule_cron: str | None = Field(None, max_length=255, description="Schedule Cron")
    last_run_at: datetime | None = Field(None, description="Last Run At")
    last_status: SyncJobLastStatus | None = Field(None, description="Last Status")
    owner: str | None = Field(None, max_length=255, description="Owner")
    criticality: SyncJobCriticality | None = Field(None, description="Criticality")
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

    @field_validator("schedule_cron")
    @classmethod
    def _check_schedule_cron(cls, value: str | None) -> str | None:
        return reject_control_chars(value, "scheduleCron")

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


class SyncJobCreate(SyncJobBase):
    """Payload to create a Sync Job."""


class SyncJobUpdate(ApiModel):
    """Partial update; omitted attributes are left unchanged."""

    expected_version: int | None = Field(None, ge=0, description="Optimistic lock version")
    code: str | None = Field(None, max_length=255, min_length=1, description="Code")
    name: str | None = Field(None, max_length=255, min_length=1, description="Name")
    description: str | None = Field(None, max_length=4000, description="Description")
    status: LifecycleStatus | None = Field(None, description="Status")
    external_ref: str | None = Field(None, max_length=255, description="External Ref")
    endpoint_id: UUID | None = Field(None, description="Endpoint Id")
    direction: SyncJobDirection | None = Field(None, description="Direction")
    schedule_cron: str | None = Field(None, max_length=255, description="Schedule Cron")
    last_run_at: datetime | None = Field(None, description="Last Run At")
    last_status: SyncJobLastStatus | None = Field(None, description="Last Status")
    owner: str | None = Field(None, max_length=255, description="Owner")
    criticality: SyncJobCriticality | None = Field(None, description="Criticality")
    cost_center: str | None = Field(None, max_length=255, description="Cost Center")
    notes: str | None = Field(None, max_length=4000, description="Notes")
    effective_from: date | None = Field(None, description="Effective From")


class SyncJobRead(SyncJobBase):
    """Sync Job as returned by the system of record."""

    id: UUID
    tenant_id: str
    version: int = 0
    created_at: datetime | None = None
    updated_at: datetime | None = None
    created_by: str | None = None
    updated_by: str | None = None


class SyncJobSummary(ApiModel):
    id: UUID
    code: str
    name: str
    status: LifecycleStatus


class SyncJobBulkFailure(ApiModel):
    index: int
    message: str


class SyncJobBulkResult(ApiModel):
    succeeded: list[SyncJobRead] = Field(default_factory=list)
    failures: list[SyncJobBulkFailure] = Field(default_factory=list)
