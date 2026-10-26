"""Pydantic models for Customer Account (Customers and SLA)."""
from __future__ import annotations

from datetime import date, datetime
from decimal import Decimal
from enum import Enum
from uuid import UUID

from pydantic import Field, field_validator, model_validator

from itta_core.models import ApiModel, LifecycleStatus, reject_control_chars


class CustomerAccountBillingCycle(str, Enum):
    """Allowed values for Customer Account Billing Cycle."""

    MONTHLY = "MONTHLY"
    QUARTERLY = "QUARTERLY"
    ANNUAL = "ANNUAL"


class CustomerAccountLifecycleStage(str, Enum):
    """Allowed values for Customer Account Lifecycle Stage."""

    PLAN = "PLAN"
    BUILD = "BUILD"
    OPERATE = "OPERATE"
    RETIRE = "RETIRE"


class CustomerAccountBase(ApiModel):
    """Attributes shared by create and read models."""

    code: str = Field(..., max_length=255, min_length=1, description="Code")
    name: str = Field(..., max_length=255, min_length=1, description="Name")
    description: str | None = Field(None, max_length=4000, description="Description")
    status: LifecycleStatus = Field(..., description="Status")
    external_ref: str | None = Field(None, max_length=255, description="External Ref")
    customer_id: UUID = Field(..., description="Customer Id")
    account_number: str = Field(..., max_length=255, min_length=1, description="Account Number")
    billing_cycle: CustomerAccountBillingCycle | None = Field(None, description="Billing Cycle")
    currency: str | None = Field(None, max_length=255, description="Currency")
    credit_limit: Decimal | None = Field(None, ge=0, max_digits=18, decimal_places=4, description="Credit Limit")
    effective_from: date | None = Field(None, description="Effective From")
    effective_to: date | None = Field(None, description="Effective To")
    labels: str | None = Field(None, max_length=255, description="Labels")
    lifecycle_stage: CustomerAccountLifecycleStage | None = Field(None, description="Lifecycle Stage")
    source_system: str | None = Field(None, max_length=255, description="Source System")

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

    @field_validator("account_number")
    @classmethod
    def _check_account_number(cls, value: str | None) -> str | None:
        return reject_control_chars(value, "accountNumber")

    @field_validator("currency")
    @classmethod
    def _check_currency(cls, value: str | None) -> str | None:
        return reject_control_chars(value, "currency")

    @field_validator("labels")
    @classmethod
    def _check_labels(cls, value: str | None) -> str | None:
        return reject_control_chars(value, "labels")

    @field_validator("source_system")
    @classmethod
    def _check_source_system(cls, value: str | None) -> str | None:
        return reject_control_chars(value, "sourceSystem")

    @model_validator(mode="after")
    def _check_ranges(self) -> "CustomerAccountBase":
        if self.effective_from is not None and self.effective_to is not None and self.effective_to < self.effective_from:
            raise ValueError("effective_to must not be earlier than effective_from")
        return self


class CustomerAccountCreate(CustomerAccountBase):
    """Payload to create a Customer Account."""


class CustomerAccountUpdate(ApiModel):
    """Partial update; omitted attributes are left unchanged."""

    expected_version: int | None = Field(None, ge=0, description="Optimistic lock version")
    code: str | None = Field(None, max_length=255, min_length=1, description="Code")
    name: str | None = Field(None, max_length=255, min_length=1, description="Name")
    description: str | None = Field(None, max_length=4000, description="Description")
    status: LifecycleStatus | None = Field(None, description="Status")
    external_ref: str | None = Field(None, max_length=255, description="External Ref")
    customer_id: UUID | None = Field(None, description="Customer Id")
    account_number: str | None = Field(None, max_length=255, min_length=1, description="Account Number")
    billing_cycle: CustomerAccountBillingCycle | None = Field(None, description="Billing Cycle")
    currency: str | None = Field(None, max_length=255, description="Currency")
    credit_limit: Decimal | None = Field(None, ge=0, max_digits=18, decimal_places=4, description="Credit Limit")
    effective_from: date | None = Field(None, description="Effective From")
    effective_to: date | None = Field(None, description="Effective To")
    labels: str | None = Field(None, max_length=255, description="Labels")
    lifecycle_stage: CustomerAccountLifecycleStage | None = Field(None, description="Lifecycle Stage")
    source_system: str | None = Field(None, max_length=255, description="Source System")


class CustomerAccountRead(CustomerAccountBase):
    """Customer Account as returned by the system of record."""

    id: UUID
    tenant_id: str
    version: int = 0
    created_at: datetime | None = None
    updated_at: datetime | None = None
    created_by: str | None = None
    updated_by: str | None = None


class CustomerAccountSummary(ApiModel):
    id: UUID
    code: str
    name: str
    status: LifecycleStatus


class CustomerAccountBulkFailure(ApiModel):
    index: int
    message: str


class CustomerAccountBulkResult(ApiModel):
    succeeded: list[CustomerAccountRead] = Field(default_factory=list)
    failures: list[CustomerAccountBulkFailure] = Field(default_factory=list)
