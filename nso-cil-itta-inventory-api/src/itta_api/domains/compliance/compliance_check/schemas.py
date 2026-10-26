"""Pydantic models for Compliance Check (Compliance and Audit)."""
from __future__ import annotations

from datetime import date, datetime
from decimal import Decimal
from enum import Enum
from uuid import UUID

from pydantic import Field, field_validator, model_validator

from itta_core.models import ApiModel, LifecycleStatus, reject_control_chars


class ComplianceCheckResult(str, Enum):
    """Allowed values for Compliance Check Result."""

    PASS = "PASS"
    FAIL = "FAIL"
    WARN = "WARN"
    NA = "NA"


class ComplianceCheckCriticality(str, Enum):
    """Allowed values for Compliance Check Criticality."""

    LOW = "LOW"
    MEDIUM = "MEDIUM"
    HIGH = "HIGH"
    CRITICAL = "CRITICAL"


class ComplianceCheckBase(ApiModel):
    """Attributes shared by create and read models."""

    code: str = Field(..., max_length=255, min_length=1, description="Code")
    name: str = Field(..., max_length=255, min_length=1, description="Name")
    description: str | None = Field(None, max_length=4000, description="Description")
    status: LifecycleStatus = Field(..., description="Status")
    external_ref: str | None = Field(None, max_length=255, description="External Ref")
    rule_id: UUID = Field(..., description="Rule Id")
    target_ref: str = Field(..., max_length=255, min_length=1, description="Target Ref")
    checked_at: datetime = Field(..., description="Checked At")
    result: ComplianceCheckResult | None = Field(None, description="Result")
    evidence: str | None = Field(None, max_length=4000, description="Evidence")
    criticality: ComplianceCheckCriticality | None = Field(None, description="Criticality")
    cost_center: str | None = Field(None, max_length=255, description="Cost Center")
    notes: str | None = Field(None, max_length=4000, description="Notes")
    effective_from: date | None = Field(None, description="Effective From")
    effective_to: date | None = Field(None, description="Effective To")

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

    @field_validator("target_ref")
    @classmethod
    def _check_target_ref(cls, value: str | None) -> str | None:
        return reject_control_chars(value, "targetRef")

    @field_validator("evidence")
    @classmethod
    def _check_evidence(cls, value: str | None) -> str | None:
        return reject_control_chars(value, "evidence")

    @field_validator("cost_center")
    @classmethod
    def _check_cost_center(cls, value: str | None) -> str | None:
        return reject_control_chars(value, "costCenter")

    @field_validator("notes")
    @classmethod
    def _check_notes(cls, value: str | None) -> str | None:
        return reject_control_chars(value, "notes")

    @model_validator(mode="after")
    def _check_ranges(self) -> "ComplianceCheckBase":
        if self.effective_from is not None and self.effective_to is not None and self.effective_to < self.effective_from:
            raise ValueError("effective_to must not be earlier than effective_from")
        return self


class ComplianceCheckCreate(ComplianceCheckBase):
    """Payload to create a Compliance Check."""


class ComplianceCheckUpdate(ApiModel):
    """Partial update; omitted attributes are left unchanged."""

    expected_version: int | None = Field(None, ge=0, description="Optimistic lock version")
    code: str | None = Field(None, max_length=255, min_length=1, description="Code")
    name: str | None = Field(None, max_length=255, min_length=1, description="Name")
    description: str | None = Field(None, max_length=4000, description="Description")
    status: LifecycleStatus | None = Field(None, description="Status")
    external_ref: str | None = Field(None, max_length=255, description="External Ref")
    rule_id: UUID | None = Field(None, description="Rule Id")
    target_ref: str | None = Field(None, max_length=255, min_length=1, description="Target Ref")
    checked_at: datetime | None = Field(None, description="Checked At")
    result: ComplianceCheckResult | None = Field(None, description="Result")
    evidence: str | None = Field(None, max_length=4000, description="Evidence")
    criticality: ComplianceCheckCriticality | None = Field(None, description="Criticality")
    cost_center: str | None = Field(None, max_length=255, description="Cost Center")
    notes: str | None = Field(None, max_length=4000, description="Notes")
    effective_from: date | None = Field(None, description="Effective From")
    effective_to: date | None = Field(None, description="Effective To")


class ComplianceCheckRead(ComplianceCheckBase):
    """Compliance Check as returned by the system of record."""

    id: UUID
    tenant_id: str
    version: int = 0
    created_at: datetime | None = None
    updated_at: datetime | None = None
    created_by: str | None = None
    updated_by: str | None = None


class ComplianceCheckSummary(ApiModel):
    id: UUID
    code: str
    name: str
    status: LifecycleStatus


class ComplianceCheckBulkFailure(ApiModel):
    index: int
    message: str


class ComplianceCheckBulkResult(ApiModel):
    succeeded: list[ComplianceCheckRead] = Field(default_factory=list)
    failures: list[ComplianceCheckBulkFailure] = Field(default_factory=list)
