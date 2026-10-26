"""Pydantic models for Contract (Customers and SLA)."""
from __future__ import annotations

from datetime import date, datetime
from decimal import Decimal
from enum import Enum
from uuid import UUID

from pydantic import Field, field_validator, model_validator

from itta_core.models import ApiModel, LifecycleStatus, reject_control_chars


class ContractCriticality(str, Enum):
    """Allowed values for Contract Criticality."""

    LOW = "LOW"
    MEDIUM = "MEDIUM"
    HIGH = "HIGH"
    CRITICAL = "CRITICAL"


class ContractBase(ApiModel):
    """Attributes shared by create and read models."""

    code: str = Field(..., max_length=255, min_length=1, description="Code")
    name: str = Field(..., max_length=255, min_length=1, description="Name")
    description: str | None = Field(None, max_length=4000, description="Description")
    status: LifecycleStatus = Field(..., description="Status")
    external_ref: str | None = Field(None, max_length=255, description="External Ref")
    customer_id: UUID = Field(..., description="Customer Id")
    contract_number: str = Field(..., max_length=255, min_length=1, description="Contract Number")
    start_date: date = Field(..., description="Start Date")
    end_date: date | None = Field(None, description="End Date")
    term_months: int | None = Field(None, ge=0, description="Term Months")
    auto_renew: bool | None = Field(None, description="Auto Renew")
    source_system: str | None = Field(None, max_length=255, description="Source System")
    region: str | None = Field(None, max_length=255, description="Region")
    owner: str | None = Field(None, max_length=255, description="Owner")
    criticality: ContractCriticality | None = Field(None, description="Criticality")
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

    @field_validator("contract_number")
    @classmethod
    def _check_contract_number(cls, value: str | None) -> str | None:
        return reject_control_chars(value, "contractNumber")

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

    @model_validator(mode="after")
    def _check_ranges(self) -> "ContractBase":
        if self.start_date is not None and self.end_date is not None and self.end_date < self.start_date:
            raise ValueError("end_date must not be earlier than start_date")
        return self


class ContractCreate(ContractBase):
    """Payload to create a Contract."""


class ContractUpdate(ApiModel):
    """Partial update; omitted attributes are left unchanged."""

    expected_version: int | None = Field(None, ge=0, description="Optimistic lock version")
    code: str | None = Field(None, max_length=255, min_length=1, description="Code")
    name: str | None = Field(None, max_length=255, min_length=1, description="Name")
    description: str | None = Field(None, max_length=4000, description="Description")
    status: LifecycleStatus | None = Field(None, description="Status")
    external_ref: str | None = Field(None, max_length=255, description="External Ref")
    customer_id: UUID | None = Field(None, description="Customer Id")
    contract_number: str | None = Field(None, max_length=255, min_length=1, description="Contract Number")
    start_date: date | None = Field(None, description="Start Date")
    end_date: date | None = Field(None, description="End Date")
    term_months: int | None = Field(None, ge=0, description="Term Months")
    auto_renew: bool | None = Field(None, description="Auto Renew")
    source_system: str | None = Field(None, max_length=255, description="Source System")
    region: str | None = Field(None, max_length=255, description="Region")
    owner: str | None = Field(None, max_length=255, description="Owner")
    criticality: ContractCriticality | None = Field(None, description="Criticality")
    cost_center: str | None = Field(None, max_length=255, description="Cost Center")


class ContractRead(ContractBase):
    """Contract as returned by the system of record."""

    id: UUID
    tenant_id: str
    version: int = 0
    created_at: datetime | None = None
    updated_at: datetime | None = None
    created_by: str | None = None
    updated_by: str | None = None


class ContractSummary(ApiModel):
    id: UUID
    code: str
    name: str
    status: LifecycleStatus


class ContractBulkFailure(ApiModel):
    index: int
    message: str


class ContractBulkResult(ApiModel):
    succeeded: list[ContractRead] = Field(default_factory=list)
    failures: list[ContractBulkFailure] = Field(default_factory=list)
