"""Pydantic models for Policy Document (Compliance and Audit)."""
from __future__ import annotations

from datetime import date, datetime
from decimal import Decimal
from enum import Enum
from uuid import UUID

from pydantic import Field, field_validator, model_validator

from itta_core.models import ApiModel, LifecycleStatus, reject_control_chars


class PolicyDocumentLifecycleStage(str, Enum):
    """Allowed values for Policy Document Lifecycle Stage."""

    PLAN = "PLAN"
    BUILD = "BUILD"
    OPERATE = "OPERATE"
    RETIRE = "RETIRE"


class PolicyDocumentCriticality(str, Enum):
    """Allowed values for Policy Document Criticality."""

    LOW = "LOW"
    MEDIUM = "MEDIUM"
    HIGH = "HIGH"
    CRITICAL = "CRITICAL"


class PolicyDocumentBase(ApiModel):
    """Attributes shared by create and read models."""

    code: str = Field(..., max_length=255, min_length=1, description="Code")
    name: str = Field(..., max_length=255, min_length=1, description="Name")
    description: str | None = Field(None, max_length=4000, description="Description")
    status: LifecycleStatus = Field(..., description="Status")
    external_ref: str | None = Field(None, max_length=255, description="External Ref")
    title: str = Field(..., max_length=255, min_length=1, description="Title")
    doc_version: str = Field(..., max_length=255, min_length=1, description="Doc Version")
    effective_date: date = Field(..., description="Effective Date")
    owner: str | None = Field(None, max_length=255, description="Owner")
    document_url: str | None = Field(None, max_length=255, description="Document Url")
    lifecycle_stage: PolicyDocumentLifecycleStage | None = Field(None, description="Lifecycle Stage")
    source_system: str | None = Field(None, max_length=255, description="Source System")
    region: str | None = Field(None, max_length=255, description="Region")
    criticality: PolicyDocumentCriticality | None = Field(None, description="Criticality")
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

    @field_validator("title")
    @classmethod
    def _check_title(cls, value: str | None) -> str | None:
        return reject_control_chars(value, "title")

    @field_validator("doc_version")
    @classmethod
    def _check_doc_version(cls, value: str | None) -> str | None:
        return reject_control_chars(value, "docVersion")

    @field_validator("owner")
    @classmethod
    def _check_owner(cls, value: str | None) -> str | None:
        return reject_control_chars(value, "owner")

    @field_validator("document_url")
    @classmethod
    def _check_document_url(cls, value: str | None) -> str | None:
        return reject_control_chars(value, "documentUrl")

    @field_validator("source_system")
    @classmethod
    def _check_source_system(cls, value: str | None) -> str | None:
        return reject_control_chars(value, "sourceSystem")

    @field_validator("region")
    @classmethod
    def _check_region(cls, value: str | None) -> str | None:
        return reject_control_chars(value, "region")

    @field_validator("cost_center")
    @classmethod
    def _check_cost_center(cls, value: str | None) -> str | None:
        return reject_control_chars(value, "costCenter")


class PolicyDocumentCreate(PolicyDocumentBase):
    """Payload to create a Policy Document."""


class PolicyDocumentUpdate(ApiModel):
    """Partial update; omitted attributes are left unchanged."""

    expected_version: int | None = Field(None, ge=0, description="Optimistic lock version")
    code: str | None = Field(None, max_length=255, min_length=1, description="Code")
    name: str | None = Field(None, max_length=255, min_length=1, description="Name")
    description: str | None = Field(None, max_length=4000, description="Description")
    status: LifecycleStatus | None = Field(None, description="Status")
    external_ref: str | None = Field(None, max_length=255, description="External Ref")
    title: str | None = Field(None, max_length=255, min_length=1, description="Title")
    doc_version: str | None = Field(None, max_length=255, min_length=1, description="Doc Version")
    effective_date: date | None = Field(None, description="Effective Date")
    owner: str | None = Field(None, max_length=255, description="Owner")
    document_url: str | None = Field(None, max_length=255, description="Document Url")
    lifecycle_stage: PolicyDocumentLifecycleStage | None = Field(None, description="Lifecycle Stage")
    source_system: str | None = Field(None, max_length=255, description="Source System")
    region: str | None = Field(None, max_length=255, description="Region")
    criticality: PolicyDocumentCriticality | None = Field(None, description="Criticality")
    cost_center: str | None = Field(None, max_length=255, description="Cost Center")


class PolicyDocumentRead(PolicyDocumentBase):
    """Policy Document as returned by the system of record."""

    id: UUID
    tenant_id: str
    version: int = 0
    created_at: datetime | None = None
    updated_at: datetime | None = None
    created_by: str | None = None
    updated_by: str | None = None


class PolicyDocumentSummary(ApiModel):
    id: UUID
    code: str
    name: str
    status: LifecycleStatus


class PolicyDocumentBulkFailure(ApiModel):
    index: int
    message: str


class PolicyDocumentBulkResult(ApiModel):
    succeeded: list[PolicyDocumentRead] = Field(default_factory=list)
    failures: list[PolicyDocumentBulkFailure] = Field(default_factory=list)
