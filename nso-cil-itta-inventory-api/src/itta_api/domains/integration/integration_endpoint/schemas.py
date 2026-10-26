"""Pydantic models for Integration Endpoint (Integration Layer)."""
from __future__ import annotations

from datetime import date, datetime
from decimal import Decimal
from enum import Enum
from uuid import UUID

from pydantic import Field, field_validator, model_validator

from itta_core.models import ApiModel, LifecycleStatus, reject_control_chars


class IntegrationEndpointAuthType(str, Enum):
    """Allowed values for Integration Endpoint Auth Type."""

    BASIC = "BASIC"
    OAUTH2 = "OAUTH2"
    MTLS = "MTLS"
    API_KEY = "API_KEY"


class IntegrationEndpointCriticality(str, Enum):
    """Allowed values for Integration Endpoint Criticality."""

    LOW = "LOW"
    MEDIUM = "MEDIUM"
    HIGH = "HIGH"
    CRITICAL = "CRITICAL"


class IntegrationEndpointBase(ApiModel):
    """Attributes shared by create and read models."""

    code: str = Field(..., max_length=255, min_length=1, description="Code")
    name: str = Field(..., max_length=255, min_length=1, description="Name")
    description: str | None = Field(None, max_length=4000, description="Description")
    status: LifecycleStatus = Field(..., description="Status")
    external_ref: str | None = Field(None, max_length=255, description="External Ref")
    endpoint_name: str = Field(..., max_length=255, min_length=1, description="Endpoint Name")
    target_system: str = Field(..., max_length=255, min_length=1, description="Target System")
    base_url: str = Field(..., max_length=255, min_length=1, description="Base Url")
    auth_type: IntegrationEndpointAuthType | None = Field(None, description="Auth Type")
    timeout_ms: int | None = Field(None, ge=0, description="Timeout Ms")
    source_system: str | None = Field(None, max_length=255, description="Source System")
    region: str | None = Field(None, max_length=255, description="Region")
    owner: str | None = Field(None, max_length=255, description="Owner")
    criticality: IntegrationEndpointCriticality | None = Field(None, description="Criticality")
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

    @field_validator("endpoint_name")
    @classmethod
    def _check_endpoint_name(cls, value: str | None) -> str | None:
        return reject_control_chars(value, "endpointName")

    @field_validator("target_system")
    @classmethod
    def _check_target_system(cls, value: str | None) -> str | None:
        return reject_control_chars(value, "targetSystem")

    @field_validator("base_url")
    @classmethod
    def _check_base_url(cls, value: str | None) -> str | None:
        return reject_control_chars(value, "baseUrl")

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


class IntegrationEndpointCreate(IntegrationEndpointBase):
    """Payload to create a Integration Endpoint."""


class IntegrationEndpointUpdate(ApiModel):
    """Partial update; omitted attributes are left unchanged."""

    expected_version: int | None = Field(None, ge=0, description="Optimistic lock version")
    code: str | None = Field(None, max_length=255, min_length=1, description="Code")
    name: str | None = Field(None, max_length=255, min_length=1, description="Name")
    description: str | None = Field(None, max_length=4000, description="Description")
    status: LifecycleStatus | None = Field(None, description="Status")
    external_ref: str | None = Field(None, max_length=255, description="External Ref")
    endpoint_name: str | None = Field(None, max_length=255, min_length=1, description="Endpoint Name")
    target_system: str | None = Field(None, max_length=255, min_length=1, description="Target System")
    base_url: str | None = Field(None, max_length=255, min_length=1, description="Base Url")
    auth_type: IntegrationEndpointAuthType | None = Field(None, description="Auth Type")
    timeout_ms: int | None = Field(None, ge=0, description="Timeout Ms")
    source_system: str | None = Field(None, max_length=255, description="Source System")
    region: str | None = Field(None, max_length=255, description="Region")
    owner: str | None = Field(None, max_length=255, description="Owner")
    criticality: IntegrationEndpointCriticality | None = Field(None, description="Criticality")
    cost_center: str | None = Field(None, max_length=255, description="Cost Center")


class IntegrationEndpointRead(IntegrationEndpointBase):
    """Integration Endpoint as returned by the system of record."""

    id: UUID
    tenant_id: str
    version: int = 0
    created_at: datetime | None = None
    updated_at: datetime | None = None
    created_by: str | None = None
    updated_by: str | None = None


class IntegrationEndpointSummary(ApiModel):
    id: UUID
    code: str
    name: str
    status: LifecycleStatus


class IntegrationEndpointBulkFailure(ApiModel):
    index: int
    message: str


class IntegrationEndpointBulkResult(ApiModel):
    succeeded: list[IntegrationEndpointRead] = Field(default_factory=list)
    failures: list[IntegrationEndpointBulkFailure] = Field(default_factory=list)
