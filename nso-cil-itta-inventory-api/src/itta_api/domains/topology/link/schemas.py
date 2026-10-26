"""Pydantic models for Link (Topology and Links)."""
from __future__ import annotations

from datetime import date, datetime
from decimal import Decimal
from enum import Enum
from uuid import UUID

from pydantic import Field, field_validator, model_validator

from itta_core.models import ApiModel, LifecycleStatus, reject_control_chars


class LinkLinkType(str, Enum):
    """Allowed values for Link Link Type."""

    PHYSICAL = "PHYSICAL"
    LOGICAL = "LOGICAL"
    VIRTUAL = "VIRTUAL"


class LinkCriticality(str, Enum):
    """Allowed values for Link Criticality."""

    LOW = "LOW"
    MEDIUM = "MEDIUM"
    HIGH = "HIGH"
    CRITICAL = "CRITICAL"


class LinkBase(ApiModel):
    """Attributes shared by create and read models."""

    code: str = Field(..., max_length=255, min_length=1, description="Code")
    name: str = Field(..., max_length=255, min_length=1, description="Name")
    description: str | None = Field(None, max_length=4000, description="Description")
    status: LifecycleStatus = Field(..., description="Status")
    external_ref: str | None = Field(None, max_length=255, description="External Ref")
    a_interface_id: UUID = Field(..., description="A Interface Id")
    z_interface_id: UUID = Field(..., description="Z Interface Id")
    link_type: LinkLinkType | None = Field(None, description="Link Type")
    speed_mbps: int | None = Field(None, ge=0, description="Speed Mbps")
    discovered_by: str | None = Field(None, max_length=255, description="Discovered By")
    region: str | None = Field(None, max_length=255, description="Region")
    owner: str | None = Field(None, max_length=255, description="Owner")
    criticality: LinkCriticality | None = Field(None, description="Criticality")
    cost_center: str | None = Field(None, max_length=255, description="Cost Center")
    notes: str | None = Field(None, max_length=4000, description="Notes")

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

    @field_validator("discovered_by")
    @classmethod
    def _check_discovered_by(cls, value: str | None) -> str | None:
        return reject_control_chars(value, "discoveredBy")

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

    @field_validator("notes")
    @classmethod
    def _check_notes(cls, value: str | None) -> str | None:
        return reject_control_chars(value, "notes")


class LinkCreate(LinkBase):
    """Payload to create a Link."""


class LinkUpdate(ApiModel):
    """Partial update; omitted attributes are left unchanged."""

    expected_version: int | None = Field(None, ge=0, description="Optimistic lock version")
    code: str | None = Field(None, max_length=255, min_length=1, description="Code")
    name: str | None = Field(None, max_length=255, min_length=1, description="Name")
    description: str | None = Field(None, max_length=4000, description="Description")
    status: LifecycleStatus | None = Field(None, description="Status")
    external_ref: str | None = Field(None, max_length=255, description="External Ref")
    a_interface_id: UUID | None = Field(None, description="A Interface Id")
    z_interface_id: UUID | None = Field(None, description="Z Interface Id")
    link_type: LinkLinkType | None = Field(None, description="Link Type")
    speed_mbps: int | None = Field(None, ge=0, description="Speed Mbps")
    discovered_by: str | None = Field(None, max_length=255, description="Discovered By")
    region: str | None = Field(None, max_length=255, description="Region")
    owner: str | None = Field(None, max_length=255, description="Owner")
    criticality: LinkCriticality | None = Field(None, description="Criticality")
    cost_center: str | None = Field(None, max_length=255, description="Cost Center")
    notes: str | None = Field(None, max_length=4000, description="Notes")


class LinkRead(LinkBase):
    """Link as returned by the system of record."""

    id: UUID
    tenant_id: str
    version: int = 0
    created_at: datetime | None = None
    updated_at: datetime | None = None
    created_by: str | None = None
    updated_by: str | None = None


class LinkSummary(ApiModel):
    id: UUID
    code: str
    name: str
    status: LifecycleStatus


class LinkBulkFailure(ApiModel):
    index: int
    message: str


class LinkBulkResult(ApiModel):
    succeeded: list[LinkRead] = Field(default_factory=list)
    failures: list[LinkBulkFailure] = Field(default_factory=list)
