"""Pydantic models for Bgp Session (L2 and L3 Routing)."""
from __future__ import annotations

from datetime import date, datetime
from decimal import Decimal
from enum import Enum
from uuid import UUID

from pydantic import Field, field_validator, model_validator

from itta_core.models import ApiModel, LifecycleStatus, reject_control_chars


class BgpSessionSessionState(str, Enum):
    """Allowed values for Bgp Session Session State."""

    IDLE = "IDLE"
    CONNECT = "CONNECT"
    ACTIVE = "ACTIVE"
    OPENSENT = "OPENSENT"
    ESTABLISHED = "ESTABLISHED"


class BgpSessionCriticality(str, Enum):
    """Allowed values for Bgp Session Criticality."""

    LOW = "LOW"
    MEDIUM = "MEDIUM"
    HIGH = "HIGH"
    CRITICAL = "CRITICAL"


class BgpSessionBase(ApiModel):
    """Attributes shared by create and read models."""

    code: str = Field(..., max_length=255, min_length=1, description="Code")
    name: str = Field(..., max_length=255, min_length=1, description="Name")
    description: str | None = Field(None, max_length=4000, description="Description")
    status: LifecycleStatus = Field(..., description="Status")
    external_ref: str | None = Field(None, max_length=255, description="External Ref")
    local_device_id: UUID = Field(..., description="Local Device Id")
    peer_address: str = Field(..., max_length=255, min_length=1, description="Peer Address")
    local_asn: int = Field(..., ge=0, description="Local Asn")
    remote_asn: int = Field(..., ge=0, description="Remote Asn")
    session_state: BgpSessionSessionState | None = Field(None, description="Session State")
    prefix_limit: int | None = Field(None, ge=0, description="Prefix Limit")
    region: str | None = Field(None, max_length=255, description="Region")
    owner: str | None = Field(None, max_length=255, description="Owner")
    criticality: BgpSessionCriticality | None = Field(None, description="Criticality")
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

    @field_validator("peer_address")
    @classmethod
    def _check_peer_address(cls, value: str | None) -> str | None:
        return reject_control_chars(value, "peerAddress")

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


class BgpSessionCreate(BgpSessionBase):
    """Payload to create a Bgp Session."""


class BgpSessionUpdate(ApiModel):
    """Partial update; omitted attributes are left unchanged."""

    expected_version: int | None = Field(None, ge=0, description="Optimistic lock version")
    code: str | None = Field(None, max_length=255, min_length=1, description="Code")
    name: str | None = Field(None, max_length=255, min_length=1, description="Name")
    description: str | None = Field(None, max_length=4000, description="Description")
    status: LifecycleStatus | None = Field(None, description="Status")
    external_ref: str | None = Field(None, max_length=255, description="External Ref")
    local_device_id: UUID | None = Field(None, description="Local Device Id")
    peer_address: str | None = Field(None, max_length=255, min_length=1, description="Peer Address")
    local_asn: int | None = Field(None, ge=0, description="Local Asn")
    remote_asn: int | None = Field(None, ge=0, description="Remote Asn")
    session_state: BgpSessionSessionState | None = Field(None, description="Session State")
    prefix_limit: int | None = Field(None, ge=0, description="Prefix Limit")
    region: str | None = Field(None, max_length=255, description="Region")
    owner: str | None = Field(None, max_length=255, description="Owner")
    criticality: BgpSessionCriticality | None = Field(None, description="Criticality")
    cost_center: str | None = Field(None, max_length=255, description="Cost Center")
    notes: str | None = Field(None, max_length=4000, description="Notes")


class BgpSessionRead(BgpSessionBase):
    """Bgp Session as returned by the system of record."""

    id: UUID
    tenant_id: str
    version: int = 0
    created_at: datetime | None = None
    updated_at: datetime | None = None
    created_by: str | None = None
    updated_by: str | None = None


class BgpSessionSummary(ApiModel):
    id: UUID
    code: str
    name: str
    status: LifecycleStatus


class BgpSessionBulkFailure(ApiModel):
    index: int
    message: str


class BgpSessionBulkResult(ApiModel):
    succeeded: list[BgpSessionRead] = Field(default_factory=list)
    failures: list[BgpSessionBulkFailure] = Field(default_factory=list)
