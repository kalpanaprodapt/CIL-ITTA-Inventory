"""Pydantic models for Event Source (Alarms and Events)."""
from __future__ import annotations

from datetime import date, datetime
from decimal import Decimal
from enum import Enum
from uuid import UUID

from pydantic import Field, field_validator, model_validator

from itta_core.models import ApiModel, LifecycleStatus, reject_control_chars


class EventSourceProtocol(str, Enum):
    """Allowed values for Event Source Protocol."""

    SNMP_TRAP = "SNMP_TRAP"
    SYSLOG = "SYSLOG"
    KAFKA = "KAFKA"
    WEBHOOK = "WEBHOOK"


class EventSourceLifecycleStage(str, Enum):
    """Allowed values for Event Source Lifecycle Stage."""

    PLAN = "PLAN"
    BUILD = "BUILD"
    OPERATE = "OPERATE"
    RETIRE = "RETIRE"


class EventSourceBase(ApiModel):
    """Attributes shared by create and read models."""

    code: str = Field(..., max_length=255, min_length=1, description="Code")
    name: str = Field(..., max_length=255, min_length=1, description="Name")
    description: str | None = Field(None, max_length=4000, description="Description")
    status: LifecycleStatus = Field(..., description="Status")
    external_ref: str | None = Field(None, max_length=255, description="External Ref")
    source_name: str = Field(..., max_length=255, min_length=1, description="Source Name")
    protocol: EventSourceProtocol | None = Field(None, description="Protocol")
    endpoint: str | None = Field(None, max_length=255, description="Endpoint")
    heartbeat_seconds: int | None = Field(None, ge=0, description="Heartbeat Seconds")
    labels: str | None = Field(None, max_length=255, description="Labels")
    lifecycle_stage: EventSourceLifecycleStage | None = Field(None, description="Lifecycle Stage")
    source_system: str | None = Field(None, max_length=255, description="Source System")
    region: str | None = Field(None, max_length=255, description="Region")
    owner: str | None = Field(None, max_length=255, description="Owner")

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

    @field_validator("source_name")
    @classmethod
    def _check_source_name(cls, value: str | None) -> str | None:
        return reject_control_chars(value, "sourceName")

    @field_validator("endpoint")
    @classmethod
    def _check_endpoint(cls, value: str | None) -> str | None:
        return reject_control_chars(value, "endpoint")

    @field_validator("labels")
    @classmethod
    def _check_labels(cls, value: str | None) -> str | None:
        return reject_control_chars(value, "labels")

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


class EventSourceCreate(EventSourceBase):
    """Payload to create a Event Source."""


class EventSourceUpdate(ApiModel):
    """Partial update; omitted attributes are left unchanged."""

    expected_version: int | None = Field(None, ge=0, description="Optimistic lock version")
    code: str | None = Field(None, max_length=255, min_length=1, description="Code")
    name: str | None = Field(None, max_length=255, min_length=1, description="Name")
    description: str | None = Field(None, max_length=4000, description="Description")
    status: LifecycleStatus | None = Field(None, description="Status")
    external_ref: str | None = Field(None, max_length=255, description="External Ref")
    source_name: str | None = Field(None, max_length=255, min_length=1, description="Source Name")
    protocol: EventSourceProtocol | None = Field(None, description="Protocol")
    endpoint: str | None = Field(None, max_length=255, description="Endpoint")
    heartbeat_seconds: int | None = Field(None, ge=0, description="Heartbeat Seconds")
    labels: str | None = Field(None, max_length=255, description="Labels")
    lifecycle_stage: EventSourceLifecycleStage | None = Field(None, description="Lifecycle Stage")
    source_system: str | None = Field(None, max_length=255, description="Source System")
    region: str | None = Field(None, max_length=255, description="Region")
    owner: str | None = Field(None, max_length=255, description="Owner")


class EventSourceRead(EventSourceBase):
    """Event Source as returned by the system of record."""

    id: UUID
    tenant_id: str
    version: int = 0
    created_at: datetime | None = None
    updated_at: datetime | None = None
    created_by: str | None = None
    updated_by: str | None = None


class EventSourceSummary(ApiModel):
    id: UUID
    code: str
    name: str
    status: LifecycleStatus


class EventSourceBulkFailure(ApiModel):
    index: int
    message: str


class EventSourceBulkResult(ApiModel):
    succeeded: list[EventSourceRead] = Field(default_factory=list)
    failures: list[EventSourceBulkFailure] = Field(default_factory=list)
