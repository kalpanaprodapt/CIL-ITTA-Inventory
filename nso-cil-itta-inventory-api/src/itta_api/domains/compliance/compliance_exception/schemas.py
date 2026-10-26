"""Pydantic models for Compliance Exception (Compliance and Audit)."""
from __future__ import annotations

from datetime import date, datetime
from decimal import Decimal
from enum import Enum
from uuid import UUID

from pydantic import Field, field_validator, model_validator

from itta_core.models import ApiModel, LifecycleStatus, reject_control_chars


class ComplianceExceptionLifecycleStage(str, Enum):
    """Allowed values for Compliance Exception Lifecycle Stage."""

    PLAN = "PLAN"
    BUILD = "BUILD"
    OPERATE = "OPERATE"
    RETIRE = "RETIRE"


class ComplianceExceptionBase(ApiModel):
    """Attributes shared by create and read models."""

    code: str = Field(..., max_length=255, min_length=1, description="Code")
    name: str = Field(..., max_length=255, min_length=1, description="Name")
    description: str | None = Field(None, max_length=4000, description="Description")
    status: LifecycleStatus = Field(..., description="Status")
    external_ref: str | None = Field(None, max_length=255, description="External Ref")
    rule_id: UUID = Field(..., description="Rule Id")
    target_ref: str = Field(..., max_length=255, min_length=1, description="Target Ref")
    justification: str = Field(..., max_length=4000, min_length=1, description="Justification")
    approved_by: str | None = Field(None, max_length=255, description="Approved By")
    expires_on: date | None = Field(None, description="Expires On")
    effective_to: date | None = Field(None, description="Effective To")
    labels: str | None = Field(None, max_length=255, description="Labels")
    lifecycle_stage: ComplianceExceptionLifecycleStage | None = Field(None, description="Lifecycle Stage")
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

    @field_validator("target_ref")
    @classmethod
    def _check_target_ref(cls, value: str | None) -> str | None:
        return reject_control_chars(value, "targetRef")

    @field_validator("justification")
    @classmethod
    def _check_justification(cls, value: str | None) -> str | None:
        return reject_control_chars(value, "justification")

    @field_validator("approved_by")
    @classmethod
    def _check_approved_by(cls, value: str | None) -> str | None:
        return reject_control_chars(value, "approvedBy")

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


class ComplianceExceptionCreate(ComplianceExceptionBase):
    """Payload to create a Compliance Exception."""


class ComplianceExceptionUpdate(ApiModel):
    """Partial update; omitted attributes are left unchanged."""

    expected_version: int | None = Field(None, ge=0, description="Optimistic lock version")
    code: str | None = Field(None, max_length=255, min_length=1, description="Code")
    name: str | None = Field(None, max_length=255, min_length=1, description="Name")
    description: str | None = Field(None, max_length=4000, description="Description")
    status: LifecycleStatus | None = Field(None, description="Status")
    external_ref: str | None = Field(None, max_length=255, description="External Ref")
    rule_id: UUID | None = Field(None, description="Rule Id")
    target_ref: str | None = Field(None, max_length=255, min_length=1, description="Target Ref")
    justification: str | None = Field(None, max_length=4000, min_length=1, description="Justification")
    approved_by: str | None = Field(None, max_length=255, description="Approved By")
    expires_on: date | None = Field(None, description="Expires On")
    effective_to: date | None = Field(None, description="Effective To")
    labels: str | None = Field(None, max_length=255, description="Labels")
    lifecycle_stage: ComplianceExceptionLifecycleStage | None = Field(None, description="Lifecycle Stage")
    source_system: str | None = Field(None, max_length=255, description="Source System")
    region: str | None = Field(None, max_length=255, description="Region")


class ComplianceExceptionRead(ComplianceExceptionBase):
    """Compliance Exception as returned by the system of record."""

    id: UUID
    tenant_id: str
    version: int = 0
    created_at: datetime | None = None
    updated_at: datetime | None = None
    created_by: str | None = None
    updated_by: str | None = None


class ComplianceExceptionSummary(ApiModel):
    id: UUID
    code: str
    name: str
    status: LifecycleStatus


class ComplianceExceptionBulkFailure(ApiModel):
    index: int
    message: str


class ComplianceExceptionBulkResult(ApiModel):
    succeeded: list[ComplianceExceptionRead] = Field(default_factory=list)
    failures: list[ComplianceExceptionBulkFailure] = Field(default_factory=list)
