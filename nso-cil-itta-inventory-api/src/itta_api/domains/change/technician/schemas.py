"""Pydantic models for Technician (Change and Work Management)."""
from __future__ import annotations

from datetime import date, datetime
from decimal import Decimal
from enum import Enum
from uuid import UUID

from pydantic import Field, field_validator, model_validator

from itta_core.models import ApiModel, LifecycleStatus, reject_control_chars


class TechnicianCriticality(str, Enum):
    """Allowed values for Technician Criticality."""

    LOW = "LOW"
    MEDIUM = "MEDIUM"
    HIGH = "HIGH"
    CRITICAL = "CRITICAL"


class TechnicianBase(ApiModel):
    """Attributes shared by create and read models."""

    code: str = Field(..., max_length=255, min_length=1, description="Code")
    name: str = Field(..., max_length=255, min_length=1, description="Name")
    description: str | None = Field(None, max_length=4000, description="Description")
    status: LifecycleStatus = Field(..., description="Status")
    external_ref: str | None = Field(None, max_length=255, description="External Ref")
    employee_id: str = Field(..., max_length=255, min_length=1, description="Employee Id")
    full_name: str = Field(..., max_length=255, min_length=1, description="Full Name")
    skill_set: str | None = Field(None, max_length=255, description="Skill Set")
    home_site_id: UUID | None = Field(None, description="Home Site Id")
    certification_level: int | None = Field(None, description="Certification Level")
    criticality: TechnicianCriticality | None = Field(None, description="Criticality")
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

    @field_validator("employee_id")
    @classmethod
    def _check_employee_id(cls, value: str | None) -> str | None:
        return reject_control_chars(value, "employeeId")

    @field_validator("full_name")
    @classmethod
    def _check_full_name(cls, value: str | None) -> str | None:
        return reject_control_chars(value, "fullName")

    @field_validator("skill_set")
    @classmethod
    def _check_skill_set(cls, value: str | None) -> str | None:
        return reject_control_chars(value, "skillSet")

    @field_validator("cost_center")
    @classmethod
    def _check_cost_center(cls, value: str | None) -> str | None:
        return reject_control_chars(value, "costCenter")

    @field_validator("notes")
    @classmethod
    def _check_notes(cls, value: str | None) -> str | None:
        return reject_control_chars(value, "notes")

    @model_validator(mode="after")
    def _check_ranges(self) -> "TechnicianBase":
        if self.effective_from is not None and self.effective_to is not None and self.effective_to < self.effective_from:
            raise ValueError("effective_to must not be earlier than effective_from")
        return self


class TechnicianCreate(TechnicianBase):
    """Payload to create a Technician."""


class TechnicianUpdate(ApiModel):
    """Partial update; omitted attributes are left unchanged."""

    expected_version: int | None = Field(None, ge=0, description="Optimistic lock version")
    code: str | None = Field(None, max_length=255, min_length=1, description="Code")
    name: str | None = Field(None, max_length=255, min_length=1, description="Name")
    description: str | None = Field(None, max_length=4000, description="Description")
    status: LifecycleStatus | None = Field(None, description="Status")
    external_ref: str | None = Field(None, max_length=255, description="External Ref")
    employee_id: str | None = Field(None, max_length=255, min_length=1, description="Employee Id")
    full_name: str | None = Field(None, max_length=255, min_length=1, description="Full Name")
    skill_set: str | None = Field(None, max_length=255, description="Skill Set")
    home_site_id: UUID | None = Field(None, description="Home Site Id")
    certification_level: int | None = Field(None, description="Certification Level")
    criticality: TechnicianCriticality | None = Field(None, description="Criticality")
    cost_center: str | None = Field(None, max_length=255, description="Cost Center")
    notes: str | None = Field(None, max_length=4000, description="Notes")
    effective_from: date | None = Field(None, description="Effective From")
    effective_to: date | None = Field(None, description="Effective To")


class TechnicianRead(TechnicianBase):
    """Technician as returned by the system of record."""

    id: UUID
    tenant_id: str
    version: int = 0
    created_at: datetime | None = None
    updated_at: datetime | None = None
    created_by: str | None = None
    updated_by: str | None = None


class TechnicianSummary(ApiModel):
    id: UUID
    code: str
    name: str
    status: LifecycleStatus


class TechnicianBulkFailure(ApiModel):
    index: int
    message: str


class TechnicianBulkResult(ApiModel):
    succeeded: list[TechnicianRead] = Field(default_factory=list)
    failures: list[TechnicianBulkFailure] = Field(default_factory=list)
