"""Pydantic models for Customer Contact (Customers and SLA)."""
from __future__ import annotations

from datetime import date, datetime
from decimal import Decimal
from enum import Enum
from uuid import UUID

from pydantic import Field, field_validator, model_validator

from itta_core.models import ApiModel, LifecycleStatus, reject_control_chars


class CustomerContactContactRole(str, Enum):
    """Allowed values for Customer Contact Contact Role."""

    TECHNICAL = "TECHNICAL"
    BILLING = "BILLING"
    ESCALATION = "ESCALATION"
    EXECUTIVE = "EXECUTIVE"


class CustomerContactLifecycleStage(str, Enum):
    """Allowed values for Customer Contact Lifecycle Stage."""

    PLAN = "PLAN"
    BUILD = "BUILD"
    OPERATE = "OPERATE"
    RETIRE = "RETIRE"


class CustomerContactBase(ApiModel):
    """Attributes shared by create and read models."""

    code: str = Field(..., max_length=255, min_length=1, description="Code")
    name: str = Field(..., max_length=255, min_length=1, description="Name")
    description: str | None = Field(None, max_length=4000, description="Description")
    status: LifecycleStatus = Field(..., description="Status")
    external_ref: str | None = Field(None, max_length=255, description="External Ref")
    customer_id: UUID = Field(..., description="Customer Id")
    full_name: str = Field(..., max_length=255, min_length=1, description="Full Name")
    email: str | None = Field(None, max_length=255, description="Email")
    phone: str | None = Field(None, max_length=255, description="Phone")
    contact_role: CustomerContactContactRole | None = Field(None, description="Contact Role")
    labels: str | None = Field(None, max_length=255, description="Labels")
    lifecycle_stage: CustomerContactLifecycleStage | None = Field(None, description="Lifecycle Stage")
    source_system: str | None = Field(None, max_length=255, description="Source System")
    region: str | None = Field(None, max_length=255, description="Region")
    owner: str | None = Field(None, max_length=255, description="Owner")

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

    @field_validator("full_name")
    @classmethod
    def _check_full_name(cls, value: str | None) -> str | None:
        return reject_control_chars(value, "fullName")

    @field_validator("email")
    @classmethod
    def _check_email(cls, value: str | None) -> str | None:
        return reject_control_chars(value, "email")

    @field_validator("phone")
    @classmethod
    def _check_phone(cls, value: str | None) -> str | None:
        return reject_control_chars(value, "phone")

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

    @field_validator("owner")
    @classmethod
    def _check_owner(cls, value: str | None) -> str | None:
        return reject_control_chars(value, "owner")


class CustomerContactCreate(CustomerContactBase):
    """Payload to create a Customer Contact."""


class CustomerContactUpdate(ApiModel):
    """Partial update; omitted attributes are left unchanged."""

    expected_version: int | None = Field(None, ge=0, description="Optimistic lock version")
    code: str | None = Field(None, max_length=255, min_length=1, description="Code")
    name: str | None = Field(None, max_length=255, min_length=1, description="Name")
    description: str | None = Field(None, max_length=4000, description="Description")
    status: LifecycleStatus | None = Field(None, description="Status")
    external_ref: str | None = Field(None, max_length=255, description="External Ref")
    customer_id: UUID | None = Field(None, description="Customer Id")
    full_name: str | None = Field(None, max_length=255, min_length=1, description="Full Name")
    email: str | None = Field(None, max_length=255, description="Email")
    phone: str | None = Field(None, max_length=255, description="Phone")
    contact_role: CustomerContactContactRole | None = Field(None, description="Contact Role")
    labels: str | None = Field(None, max_length=255, description="Labels")
    lifecycle_stage: CustomerContactLifecycleStage | None = Field(None, description="Lifecycle Stage")
    source_system: str | None = Field(None, max_length=255, description="Source System")
    region: str | None = Field(None, max_length=255, description="Region")
    owner: str | None = Field(None, max_length=255, description="Owner")


class CustomerContactRead(CustomerContactBase):
    """Customer Contact as returned by the system of record."""

    id: UUID
    tenant_id: str
    version: int = 0
    created_at: datetime | None = None
    updated_at: datetime | None = None
    created_by: str | None = None
    updated_by: str | None = None


class CustomerContactSummary(ApiModel):
    id: UUID
    code: str
    name: str
    status: LifecycleStatus


class CustomerContactBulkFailure(ApiModel):
    index: int
    message: str


class CustomerContactBulkResult(ApiModel):
    succeeded: list[CustomerContactRead] = Field(default_factory=list)
    failures: list[CustomerContactBulkFailure] = Field(default_factory=list)
