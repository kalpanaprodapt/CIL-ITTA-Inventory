"""Pydantic models for Discovery Profile (Discovery and Reconciliation)."""
from __future__ import annotations

from datetime import date, datetime
from decimal import Decimal
from enum import Enum
from uuid import UUID

from pydantic import Field, field_validator, model_validator

from itta_core.models import ApiModel, LifecycleStatus, reject_control_chars


class DiscoveryProfileProtocol(str, Enum):
    """Allowed values for Discovery Profile Protocol."""

    SNMP = "SNMP"
    SSH = "SSH"
    NETCONF = "NETCONF"
    GNMI = "GNMI"
    REST = "REST"


class DiscoveryProfileBase(ApiModel):
    """Attributes shared by create and read models."""

    code: str = Field(..., max_length=255, min_length=1, description="Code")
    name: str = Field(..., max_length=255, min_length=1, description="Name")
    description: str | None = Field(None, max_length=4000, description="Description")
    status: LifecycleStatus = Field(..., description="Status")
    external_ref: str | None = Field(None, max_length=255, description="External Ref")
    profile_name: str = Field(..., max_length=255, min_length=1, description="Profile Name")
    protocol: DiscoveryProfileProtocol | None = Field(None, description="Protocol")
    credential_ref: str | None = Field(None, max_length=255, description="Credential Ref")
    scope_cidr: str | None = Field(None, max_length=255, description="Scope Cidr")
    schedule_cron: str | None = Field(None, max_length=255, description="Schedule Cron")
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

    @field_validator("profile_name")
    @classmethod
    def _check_profile_name(cls, value: str | None) -> str | None:
        return reject_control_chars(value, "profileName")

    @field_validator("credential_ref")
    @classmethod
    def _check_credential_ref(cls, value: str | None) -> str | None:
        return reject_control_chars(value, "credentialRef")

    @field_validator("scope_cidr")
    @classmethod
    def _check_scope_cidr(cls, value: str | None) -> str | None:
        return reject_control_chars(value, "scopeCidr")

    @field_validator("schedule_cron")
    @classmethod
    def _check_schedule_cron(cls, value: str | None) -> str | None:
        return reject_control_chars(value, "scheduleCron")

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
    def _check_ranges(self) -> "DiscoveryProfileBase":
        if self.effective_from is not None and self.effective_to is not None and self.effective_to < self.effective_from:
            raise ValueError("effective_to must not be earlier than effective_from")
        return self


class DiscoveryProfileCreate(DiscoveryProfileBase):
    """Payload to create a Discovery Profile."""


class DiscoveryProfileUpdate(ApiModel):
    """Partial update; omitted attributes are left unchanged."""

    expected_version: int | None = Field(None, ge=0, description="Optimistic lock version")
    code: str | None = Field(None, max_length=255, min_length=1, description="Code")
    name: str | None = Field(None, max_length=255, min_length=1, description="Name")
    description: str | None = Field(None, max_length=4000, description="Description")
    status: LifecycleStatus | None = Field(None, description="Status")
    external_ref: str | None = Field(None, max_length=255, description="External Ref")
    profile_name: str | None = Field(None, max_length=255, min_length=1, description="Profile Name")
    protocol: DiscoveryProfileProtocol | None = Field(None, description="Protocol")
    credential_ref: str | None = Field(None, max_length=255, description="Credential Ref")
    scope_cidr: str | None = Field(None, max_length=255, description="Scope Cidr")
    schedule_cron: str | None = Field(None, max_length=255, description="Schedule Cron")
    cost_center: str | None = Field(None, max_length=255, description="Cost Center")
    notes: str | None = Field(None, max_length=4000, description="Notes")
    effective_from: date | None = Field(None, description="Effective From")
    effective_to: date | None = Field(None, description="Effective To")
    labels: str | None = Field(None, max_length=255, description="Labels")


class DiscoveryProfileRead(DiscoveryProfileBase):
    """Discovery Profile as returned by the system of record."""

    id: UUID
    tenant_id: str
    version: int = 0
    created_at: datetime | None = None
    updated_at: datetime | None = None
    created_by: str | None = None
    updated_by: str | None = None


class DiscoveryProfileSummary(ApiModel):
    id: UUID
    code: str
    name: str
    status: LifecycleStatus


class DiscoveryProfileBulkFailure(ApiModel):
    index: int
    message: str


class DiscoveryProfileBulkResult(ApiModel):
    succeeded: list[DiscoveryProfileRead] = Field(default_factory=list)
    failures: list[DiscoveryProfileBulkFailure] = Field(default_factory=list)
