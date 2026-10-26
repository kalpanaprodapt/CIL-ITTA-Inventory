"""Pydantic models for Reconciliation Result (Discovery and Reconciliation)."""
from __future__ import annotations

from datetime import date, datetime
from decimal import Decimal
from enum import Enum
from uuid import UUID

from pydantic import Field, field_validator, model_validator

from itta_core.models import ApiModel, LifecycleStatus, reject_control_chars


class ReconciliationResultOutcome(str, Enum):
    """Allowed values for Reconciliation Result Outcome."""

    MATCHED = "MATCHED"
    CREATED = "CREATED"
    CONFLICT = "CONFLICT"
    SKIPPED = "SKIPPED"


class ReconciliationResultCriticality(str, Enum):
    """Allowed values for Reconciliation Result Criticality."""

    LOW = "LOW"
    MEDIUM = "MEDIUM"
    HIGH = "HIGH"
    CRITICAL = "CRITICAL"


class ReconciliationResultBase(ApiModel):
    """Attributes shared by create and read models."""

    code: str = Field(..., max_length=255, min_length=1, description="Code")
    name: str = Field(..., max_length=255, min_length=1, description="Name")
    description: str | None = Field(None, max_length=4000, description="Description")
    status: LifecycleStatus = Field(..., description="Status")
    external_ref: str | None = Field(None, max_length=255, description="External Ref")
    job_id: UUID = Field(..., description="Job Id")
    rule_id: UUID | None = Field(None, description="Rule Id")
    outcome: ReconciliationResultOutcome | None = Field(None, description="Outcome")
    diff_summary: str | None = Field(None, max_length=4000, description="Diff Summary")
    resolved_by: str | None = Field(None, max_length=255, description="Resolved By")
    source_system: str | None = Field(None, max_length=255, description="Source System")
    region: str | None = Field(None, max_length=255, description="Region")
    owner: str | None = Field(None, max_length=255, description="Owner")
    criticality: ReconciliationResultCriticality | None = Field(None, description="Criticality")
    cost_center: str | None = Field(None, max_length=255, description="Cost Center")

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

    @field_validator("diff_summary")
    @classmethod
    def _check_diff_summary(cls, value: str | None) -> str | None:
        return reject_control_chars(value, "diffSummary")

    @field_validator("resolved_by")
    @classmethod
    def _check_resolved_by(cls, value: str | None) -> str | None:
        return reject_control_chars(value, "resolvedBy")

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

    @field_validator("cost_center")
    @classmethod
    def _check_cost_center(cls, value: str | None) -> str | None:
        return reject_control_chars(value, "costCenter")


class ReconciliationResultCreate(ReconciliationResultBase):
    """Payload to create a Reconciliation Result."""


class ReconciliationResultUpdate(ApiModel):
    """Partial update; omitted attributes are left unchanged."""

    expected_version: int | None = Field(None, ge=0, description="Optimistic lock version")
    code: str | None = Field(None, max_length=255, min_length=1, description="Code")
    name: str | None = Field(None, max_length=255, min_length=1, description="Name")
    description: str | None = Field(None, max_length=4000, description="Description")
    status: LifecycleStatus | None = Field(None, description="Status")
    external_ref: str | None = Field(None, max_length=255, description="External Ref")
    job_id: UUID | None = Field(None, description="Job Id")
    rule_id: UUID | None = Field(None, description="Rule Id")
    outcome: ReconciliationResultOutcome | None = Field(None, description="Outcome")
    diff_summary: str | None = Field(None, max_length=4000, description="Diff Summary")
    resolved_by: str | None = Field(None, max_length=255, description="Resolved By")
    source_system: str | None = Field(None, max_length=255, description="Source System")
    region: str | None = Field(None, max_length=255, description="Region")
    owner: str | None = Field(None, max_length=255, description="Owner")
    criticality: ReconciliationResultCriticality | None = Field(None, description="Criticality")
    cost_center: str | None = Field(None, max_length=255, description="Cost Center")


class ReconciliationResultRead(ReconciliationResultBase):
    """Reconciliation Result as returned by the system of record."""

    id: UUID
    tenant_id: str
    version: int = 0
    created_at: datetime | None = None
    updated_at: datetime | None = None
    created_by: str | None = None
    updated_by: str | None = None


class ReconciliationResultSummary(ApiModel):
    id: UUID
    code: str
    name: str
    status: LifecycleStatus


class ReconciliationResultBulkFailure(ApiModel):
    index: int
    message: str


class ReconciliationResultBulkResult(ApiModel):
    succeeded: list[ReconciliationResultRead] = Field(default_factory=list)
    failures: list[ReconciliationResultBulkFailure] = Field(default_factory=list)
