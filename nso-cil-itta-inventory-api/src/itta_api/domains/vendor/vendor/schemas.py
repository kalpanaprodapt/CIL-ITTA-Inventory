"""Pydantic models for Vendor (Vendor and Model)."""
from __future__ import annotations

from datetime import date, datetime
from decimal import Decimal
from enum import Enum
from uuid import UUID

from pydantic import Field, field_validator, model_validator

from itta_core.models import ApiModel, LifecycleStatus, reject_control_chars


class VendorTier(str, Enum):
    """Allowed values for Vendor Tier."""

    STRATEGIC = "STRATEGIC"
    PREFERRED = "PREFERRED"
    APPROVED = "APPROVED"
    BLOCKED = "BLOCKED"


class VendorLifecycleStage(str, Enum):
    """Allowed values for Vendor Lifecycle Stage."""

    PLAN = "PLAN"
    BUILD = "BUILD"
    OPERATE = "OPERATE"
    RETIRE = "RETIRE"


class VendorBase(ApiModel):
    """Attributes shared by create and read models."""

    code: str = Field(..., max_length=255, min_length=1, description="Code")
    name: str = Field(..., max_length=255, min_length=1, description="Name")
    description: str | None = Field(None, max_length=4000, description="Description")
    status: LifecycleStatus = Field(..., description="Status")
    external_ref: str | None = Field(None, max_length=255, description="External Ref")
    legal_name: str = Field(..., max_length=255, min_length=1, description="Legal Name")
    support_email: str | None = Field(None, max_length=255, description="Support Email")
    support_phone: str | None = Field(None, max_length=255, description="Support Phone")
    website: str | None = Field(None, max_length=255, description="Website")
    tier: VendorTier | None = Field(None, description="Tier")
    effective_from: date | None = Field(None, description="Effective From")
    effective_to: date | None = Field(None, description="Effective To")
    labels: str | None = Field(None, max_length=255, description="Labels")
    lifecycle_stage: VendorLifecycleStage | None = Field(None, description="Lifecycle Stage")
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

    @field_validator("legal_name")
    @classmethod
    def _check_legal_name(cls, value: str | None) -> str | None:
        return reject_control_chars(value, "legalName")

    @field_validator("support_email")
    @classmethod
    def _check_support_email(cls, value: str | None) -> str | None:
        return reject_control_chars(value, "supportEmail")

    @field_validator("support_phone")
    @classmethod
    def _check_support_phone(cls, value: str | None) -> str | None:
        return reject_control_chars(value, "supportPhone")

    @field_validator("website")
    @classmethod
    def _check_website(cls, value: str | None) -> str | None:
        return reject_control_chars(value, "website")

    @field_validator("labels")
    @classmethod
    def _check_labels(cls, value: str | None) -> str | None:
        return reject_control_chars(value, "labels")

    @field_validator("source_system")
    @classmethod
    def _check_source_system(cls, value: str | None) -> str | None:
        return reject_control_chars(value, "sourceSystem")

    @model_validator(mode="after")
    def _check_ranges(self) -> "VendorBase":
        if self.effective_from is not None and self.effective_to is not None and self.effective_to < self.effective_from:
            raise ValueError("effective_to must not be earlier than effective_from")
        return self


class VendorCreate(VendorBase):
    """Payload to create a Vendor."""


class VendorUpdate(ApiModel):
    """Partial update; omitted attributes are left unchanged."""

    expected_version: int | None = Field(None, ge=0, description="Optimistic lock version")
    code: str | None = Field(None, max_length=255, min_length=1, description="Code")
    name: str | None = Field(None, max_length=255, min_length=1, description="Name")
    description: str | None = Field(None, max_length=4000, description="Description")
    status: LifecycleStatus | None = Field(None, description="Status")
    external_ref: str | None = Field(None, max_length=255, description="External Ref")
    legal_name: str | None = Field(None, max_length=255, min_length=1, description="Legal Name")
    support_email: str | None = Field(None, max_length=255, description="Support Email")
    support_phone: str | None = Field(None, max_length=255, description="Support Phone")
    website: str | None = Field(None, max_length=255, description="Website")
    tier: VendorTier | None = Field(None, description="Tier")
    effective_from: date | None = Field(None, description="Effective From")
    effective_to: date | None = Field(None, description="Effective To")
    labels: str | None = Field(None, max_length=255, description="Labels")
    lifecycle_stage: VendorLifecycleStage | None = Field(None, description="Lifecycle Stage")
    source_system: str | None = Field(None, max_length=255, description="Source System")


class VendorRead(VendorBase):
    """Vendor as returned by the system of record."""

    id: UUID
    tenant_id: str
    version: int = 0
    created_at: datetime | None = None
    updated_at: datetime | None = None
    created_by: str | None = None
    updated_by: str | None = None


class VendorSummary(ApiModel):
    id: UUID
    code: str
    name: str
    status: LifecycleStatus


class VendorBulkFailure(ApiModel):
    index: int
    message: str


class VendorBulkResult(ApiModel):
    succeeded: list[VendorRead] = Field(default_factory=list)
    failures: list[VendorBulkFailure] = Field(default_factory=list)
