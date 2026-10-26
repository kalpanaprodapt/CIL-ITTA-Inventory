"""Pydantic models for Warranty (Vendor and Model)."""
from __future__ import annotations

from datetime import date, datetime
from decimal import Decimal
from enum import Enum
from uuid import UUID

from pydantic import Field, field_validator, model_validator

from itta_core.models import ApiModel, LifecycleStatus, reject_control_chars


class WarrantyWarrantyType(str, Enum):
    """Allowed values for Warranty Warranty Type."""

    STANDARD = "STANDARD"
    EXTENDED = "EXTENDED"
    ADVANCED_REPLACEMENT = "ADVANCED_REPLACEMENT"


class WarrantyBase(ApiModel):
    """Attributes shared by create and read models."""

    code: str = Field(..., max_length=255, min_length=1, description="Code")
    name: str = Field(..., max_length=255, min_length=1, description="Name")
    description: str | None = Field(None, max_length=4000, description="Description")
    status: LifecycleStatus = Field(..., description="Status")
    external_ref: str | None = Field(None, max_length=255, description="External Ref")
    asset_id: UUID = Field(..., description="Asset Id")
    start_date: date = Field(..., description="Start Date")
    end_date: date = Field(..., description="End Date")
    warranty_type: WarrantyWarrantyType | None = Field(None, description="Warranty Type")
    terms: str | None = Field(None, max_length=4000, description="Terms")
    cost_center: str | None = Field(None, max_length=255, description="Cost Center")
    notes: str | None = Field(None, max_length=4000, description="Notes")
    effective_from: date | None = Field(None, description="Effective From")
    effective_to: date | None = Field(None, description="Effective To")
    labels: str | None = Field(None, max_length=255, description="Labels")

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

    @field_validator("terms")
    @classmethod
    def _check_terms(cls, value: str | None) -> str | None:
        return reject_control_chars(value, "terms")

    @field_validator("cost_center")
    @classmethod
    def _check_cost_center(cls, value: str | None) -> str | None:
        return reject_control_chars(value, "costCenter")

    @field_validator("notes")
    @classmethod
    def _check_notes(cls, value: str | None) -> str | None:
        return reject_control_chars(value, "notes")

    @field_validator("labels")
    @classmethod
    def _check_labels(cls, value: str | None) -> str | None:
        return reject_control_chars(value, "labels")

    @model_validator(mode="after")
    def _check_ranges(self) -> "WarrantyBase":
        if self.start_date is not None and self.end_date is not None and self.end_date < self.start_date:
            raise ValueError("end_date must not be earlier than start_date")
        if self.effective_from is not None and self.effective_to is not None and self.effective_to < self.effective_from:
            raise ValueError("effective_to must not be earlier than effective_from")
        return self


class WarrantyCreate(WarrantyBase):
    """Payload to create a Warranty."""


class WarrantyUpdate(ApiModel):
    """Partial update; omitted attributes are left unchanged."""

    expected_version: int | None = Field(None, ge=0, description="Optimistic lock version")
    code: str | None = Field(None, max_length=255, min_length=1, description="Code")
    name: str | None = Field(None, max_length=255, min_length=1, description="Name")
    description: str | None = Field(None, max_length=4000, description="Description")
    status: LifecycleStatus | None = Field(None, description="Status")
    external_ref: str | None = Field(None, max_length=255, description="External Ref")
    asset_id: UUID | None = Field(None, description="Asset Id")
    start_date: date | None = Field(None, description="Start Date")
    end_date: date | None = Field(None, description="End Date")
    warranty_type: WarrantyWarrantyType | None = Field(None, description="Warranty Type")
    terms: str | None = Field(None, max_length=4000, description="Terms")
    cost_center: str | None = Field(None, max_length=255, description="Cost Center")
    notes: str | None = Field(None, max_length=4000, description="Notes")
    effective_from: date | None = Field(None, description="Effective From")
    effective_to: date | None = Field(None, description="Effective To")
    labels: str | None = Field(None, max_length=255, description="Labels")


class WarrantyRead(WarrantyBase):
    """Warranty as returned by the system of record."""

    id: UUID
    tenant_id: str
    version: int = 0
    created_at: datetime | None = None
    updated_at: datetime | None = None
    created_by: str | None = None
    updated_by: str | None = None


class WarrantySummary(ApiModel):
    id: UUID
    code: str
    name: str
    status: LifecycleStatus


class WarrantyBulkFailure(ApiModel):
    index: int
    message: str


class WarrantyBulkResult(ApiModel):
    succeeded: list[WarrantyRead] = Field(default_factory=list)
    failures: list[WarrantyBulkFailure] = Field(default_factory=list)
