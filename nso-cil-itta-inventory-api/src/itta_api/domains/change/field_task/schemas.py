"""Pydantic models for Field Task (Change and Work Management)."""
from __future__ import annotations

from datetime import date, datetime
from decimal import Decimal
from enum import Enum
from uuid import UUID

from pydantic import Field, field_validator, model_validator

from itta_core.models import ApiModel, LifecycleStatus, reject_control_chars


class FieldTaskCriticality(str, Enum):
    """Allowed values for Field Task Criticality."""

    LOW = "LOW"
    MEDIUM = "MEDIUM"
    HIGH = "HIGH"
    CRITICAL = "CRITICAL"


class FieldTaskBase(ApiModel):
    """Attributes shared by create and read models."""

    code: str = Field(..., max_length=255, min_length=1, description="Code")
    name: str = Field(..., max_length=255, min_length=1, description="Name")
    description: str | None = Field(None, max_length=4000, description="Description")
    status: LifecycleStatus = Field(..., description="Status")
    external_ref: str | None = Field(None, max_length=255, description="External Ref")
    work_order_id: UUID = Field(..., description="Work Order Id")
    task_name: str = Field(..., max_length=255, min_length=1, description="Task Name")
    sequence: int = Field(..., ge=0, description="Sequence")
    duration_minutes: int | None = Field(None, ge=0, description="Duration Minutes")
    completed_on: datetime | None = Field(None, description="Completed On")
    region: str | None = Field(None, max_length=255, description="Region")
    owner: str | None = Field(None, max_length=255, description="Owner")
    criticality: FieldTaskCriticality | None = Field(None, description="Criticality")
    cost_center: str | None = Field(None, max_length=255, description="Cost Center")
    notes: str | None = Field(None, max_length=4000, description="Notes")

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

    @field_validator("task_name")
    @classmethod
    def _check_task_name(cls, value: str | None) -> str | None:
        return reject_control_chars(value, "taskName")

    @field_validator("region")
    @classmethod
    def _check_region(cls, value: str | None) -> str | None:
        return reject_control_chars(value, "region")

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


class FieldTaskCreate(FieldTaskBase):
    """Payload to create a Field Task."""


class FieldTaskUpdate(ApiModel):
    """Partial update; omitted attributes are left unchanged."""

    expected_version: int | None = Field(None, ge=0, description="Optimistic lock version")
    code: str | None = Field(None, max_length=255, min_length=1, description="Code")
    name: str | None = Field(None, max_length=255, min_length=1, description="Name")
    description: str | None = Field(None, max_length=4000, description="Description")
    status: LifecycleStatus | None = Field(None, description="Status")
    external_ref: str | None = Field(None, max_length=255, description="External Ref")
    work_order_id: UUID | None = Field(None, description="Work Order Id")
    task_name: str | None = Field(None, max_length=255, min_length=1, description="Task Name")
    sequence: int | None = Field(None, ge=0, description="Sequence")
    duration_minutes: int | None = Field(None, ge=0, description="Duration Minutes")
    completed_on: datetime | None = Field(None, description="Completed On")
    region: str | None = Field(None, max_length=255, description="Region")
    owner: str | None = Field(None, max_length=255, description="Owner")
    criticality: FieldTaskCriticality | None = Field(None, description="Criticality")
    cost_center: str | None = Field(None, max_length=255, description="Cost Center")
    notes: str | None = Field(None, max_length=4000, description="Notes")


class FieldTaskRead(FieldTaskBase):
    """Field Task as returned by the system of record."""

    id: UUID
    tenant_id: str
    version: int = 0
    created_at: datetime | None = None
    updated_at: datetime | None = None
    created_by: str | None = None
    updated_by: str | None = None


class FieldTaskSummary(ApiModel):
    id: UUID
    code: str
    name: str
    status: LifecycleStatus


class FieldTaskBulkFailure(ApiModel):
    index: int
    message: str


class FieldTaskBulkResult(ApiModel):
    succeeded: list[FieldTaskRead] = Field(default_factory=list)
    failures: list[FieldTaskBulkFailure] = Field(default_factory=list)
