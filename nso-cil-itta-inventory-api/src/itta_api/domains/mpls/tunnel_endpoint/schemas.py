"""Pydantic models for Tunnel Endpoint (MPLS and VPN)."""
from __future__ import annotations

from datetime import date, datetime
from decimal import Decimal
from enum import Enum
from uuid import UUID

from pydantic import Field, field_validator, model_validator

from itta_core.models import ApiModel, LifecycleStatus, reject_control_chars


class TunnelEndpointTunnelType(str, Enum):
    """Allowed values for Tunnel Endpoint Tunnel Type."""

    GRE = "GRE"
    IPSEC = "IPSEC"
    VXLAN = "VXLAN"
    SRV6 = "SRV6"


class TunnelEndpointLifecycleStage(str, Enum):
    """Allowed values for Tunnel Endpoint Lifecycle Stage."""

    PLAN = "PLAN"
    BUILD = "BUILD"
    OPERATE = "OPERATE"
    RETIRE = "RETIRE"


class TunnelEndpointBase(ApiModel):
    """Attributes shared by create and read models."""

    code: str = Field(..., max_length=255, min_length=1, description="Code")
    name: str = Field(..., max_length=255, min_length=1, description="Name")
    description: str | None = Field(None, max_length=4000, description="Description")
    status: LifecycleStatus = Field(..., description="Status")
    external_ref: str | None = Field(None, max_length=255, description="External Ref")
    device_id: UUID = Field(..., description="Device Id")
    tunnel_name: str = Field(..., max_length=255, min_length=1, description="Tunnel Name")
    tunnel_type: TunnelEndpointTunnelType | None = Field(None, description="Tunnel Type")
    source_ip: str | None = Field(None, max_length=255, description="Source Ip")
    destination_ip: str | None = Field(None, max_length=255, description="Destination Ip")
    effective_from: date | None = Field(None, description="Effective From")
    effective_to: date | None = Field(None, description="Effective To")
    labels: str | None = Field(None, max_length=255, description="Labels")
    lifecycle_stage: TunnelEndpointLifecycleStage | None = Field(None, description="Lifecycle Stage")
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

    @field_validator("tunnel_name")
    @classmethod
    def _check_tunnel_name(cls, value: str | None) -> str | None:
        return reject_control_chars(value, "tunnelName")

    @field_validator("source_ip")
    @classmethod
    def _check_source_ip(cls, value: str | None) -> str | None:
        return reject_control_chars(value, "sourceIp")

    @field_validator("destination_ip")
    @classmethod
    def _check_destination_ip(cls, value: str | None) -> str | None:
        return reject_control_chars(value, "destinationIp")

    @field_validator("labels")
    @classmethod
    def _check_labels(cls, value: str | None) -> str | None:
        return reject_control_chars(value, "labels")

    @field_validator("source_system")
    @classmethod
    def _check_source_system(cls, value: str | None) -> str | None:
        return reject_control_chars(value, "sourceSystem")

    @model_validator(mode="after")
    def _check_ranges(self) -> "TunnelEndpointBase":
        if self.effective_from is not None and self.effective_to is not None and self.effective_to < self.effective_from:
            raise ValueError("effective_to must not be earlier than effective_from")
        return self


class TunnelEndpointCreate(TunnelEndpointBase):
    """Payload to create a Tunnel Endpoint."""


class TunnelEndpointUpdate(ApiModel):
    """Partial update; omitted attributes are left unchanged."""

    expected_version: int | None = Field(None, ge=0, description="Optimistic lock version")
    code: str | None = Field(None, max_length=255, min_length=1, description="Code")
    name: str | None = Field(None, max_length=255, min_length=1, description="Name")
    description: str | None = Field(None, max_length=4000, description="Description")
    status: LifecycleStatus | None = Field(None, description="Status")
    external_ref: str | None = Field(None, max_length=255, description="External Ref")
    device_id: UUID | None = Field(None, description="Device Id")
    tunnel_name: str | None = Field(None, max_length=255, min_length=1, description="Tunnel Name")
    tunnel_type: TunnelEndpointTunnelType | None = Field(None, description="Tunnel Type")
    source_ip: str | None = Field(None, max_length=255, description="Source Ip")
    destination_ip: str | None = Field(None, max_length=255, description="Destination Ip")
    effective_from: date | None = Field(None, description="Effective From")
    effective_to: date | None = Field(None, description="Effective To")
    labels: str | None = Field(None, max_length=255, description="Labels")
    lifecycle_stage: TunnelEndpointLifecycleStage | None = Field(None, description="Lifecycle Stage")
    source_system: str | None = Field(None, max_length=255, description="Source System")


class TunnelEndpointRead(TunnelEndpointBase):
    """Tunnel Endpoint as returned by the system of record."""

    id: UUID
    tenant_id: str
    version: int = 0
    created_at: datetime | None = None
    updated_at: datetime | None = None
    created_by: str | None = None
    updated_by: str | None = None


class TunnelEndpointSummary(ApiModel):
    id: UUID
    code: str
    name: str
    status: LifecycleStatus


class TunnelEndpointBulkFailure(ApiModel):
    index: int
    message: str


class TunnelEndpointBulkResult(ApiModel):
    succeeded: list[TunnelEndpointRead] = Field(default_factory=list)
    failures: list[TunnelEndpointBulkFailure] = Field(default_factory=list)
