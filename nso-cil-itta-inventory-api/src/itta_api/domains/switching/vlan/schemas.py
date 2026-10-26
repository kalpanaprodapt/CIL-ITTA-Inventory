"""Pydantic models for Vlan (L2 and L3 Routing)."""
from __future__ import annotations

from datetime import date, datetime
from decimal import Decimal
from enum import Enum
from uuid import UUID

from pydantic import Field, field_validator, model_validator

from itta_core.models import ApiModel, LifecycleStatus, reject_control_chars


class VlanVlanType(str, Enum):
    """Allowed values for Vlan Vlan Type."""

    DATA = "DATA"
    VOICE = "VOICE"
    MGMT = "MGMT"
    NATIVE = "NATIVE"


class VlanLifecycleStage(str, Enum):
    """Allowed values for Vlan Lifecycle Stage."""

    PLAN = "PLAN"
    BUILD = "BUILD"
    OPERATE = "OPERATE"
    RETIRE = "RETIRE"


class VlanBase(ApiModel):
    """Attributes shared by create and read models."""

    code: str = Field(..., max_length=255, min_length=1, description="Code")
    name: str = Field(..., max_length=255, min_length=1, description="Name")
    description: str | None = Field(None, max_length=4000, description="Description")
    status: LifecycleStatus = Field(..., description="Status")
    external_ref: str | None = Field(None, max_length=255, description="External Ref")
    vlan_id: int = Field(..., ge=0, description="Vlan Id")
    vlan_name: str = Field(..., max_length=255, min_length=1, description="Vlan Name")
    vlan_type: VlanVlanType | None = Field(None, description="Vlan Type")
    pool_id: UUID | None = Field(None, description="Pool Id")
    stp_mode: str | None = Field(None, max_length=255, description="Stp Mode")
    notes: str | None = Field(None, max_length=4000, description="Notes")
    effective_from: date | None = Field(None, description="Effective From")
    effective_to: date | None = Field(None, description="Effective To")
    labels: str | None = Field(None, max_length=255, description="Labels")
    lifecycle_stage: VlanLifecycleStage | None = Field(None, description="Lifecycle Stage")

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

    @field_validator("vlan_name")
    @classmethod
    def _check_vlan_name(cls, value: str | None) -> str | None:
        return reject_control_chars(value, "vlanName")

    @field_validator("stp_mode")
    @classmethod
    def _check_stp_mode(cls, value: str | None) -> str | None:
        return reject_control_chars(value, "stpMode")

    @field_validator("notes")
    @classmethod
    def _check_notes(cls, value: str | None) -> str | None:
        return reject_control_chars(value, "notes")

    @field_validator("labels")
    @classmethod
    def _check_labels(cls, value: str | None) -> str | None:
        return reject_control_chars(value, "labels")

    @model_validator(mode="after")
    def _check_ranges(self) -> "VlanBase":
        if self.effective_from is not None and self.effective_to is not None and self.effective_to < self.effective_from:
            raise ValueError("effective_to must not be earlier than effective_from")
        return self


class VlanCreate(VlanBase):
    """Payload to create a Vlan."""


class VlanUpdate(ApiModel):
    """Partial update; omitted attributes are left unchanged."""

    expected_version: int | None = Field(None, ge=0, description="Optimistic lock version")
    code: str | None = Field(None, max_length=255, min_length=1, description="Code")
    name: str | None = Field(None, max_length=255, min_length=1, description="Name")
    description: str | None = Field(None, max_length=4000, description="Description")
    status: LifecycleStatus | None = Field(None, description="Status")
    external_ref: str | None = Field(None, max_length=255, description="External Ref")
    vlan_id: int | None = Field(None, ge=0, description="Vlan Id")
    vlan_name: str | None = Field(None, max_length=255, min_length=1, description="Vlan Name")
    vlan_type: VlanVlanType | None = Field(None, description="Vlan Type")
    pool_id: UUID | None = Field(None, description="Pool Id")
    stp_mode: str | None = Field(None, max_length=255, description="Stp Mode")
    notes: str | None = Field(None, max_length=4000, description="Notes")
    effective_from: date | None = Field(None, description="Effective From")
    effective_to: date | None = Field(None, description="Effective To")
    labels: str | None = Field(None, max_length=255, description="Labels")
    lifecycle_stage: VlanLifecycleStage | None = Field(None, description="Lifecycle Stage")


class VlanRead(VlanBase):
    """Vlan as returned by the system of record."""

    id: UUID
    tenant_id: str
    version: int = 0
    created_at: datetime | None = None
    updated_at: datetime | None = None
    created_by: str | None = None
    updated_by: str | None = None


class VlanSummary(ApiModel):
    id: UUID
    code: str
    name: str
    status: LifecycleStatus


class VlanBulkFailure(ApiModel):
    index: int
    message: str


class VlanBulkResult(ApiModel):
    succeeded: list[VlanRead] = Field(default_factory=list)
    failures: list[VlanBulkFailure] = Field(default_factory=list)
