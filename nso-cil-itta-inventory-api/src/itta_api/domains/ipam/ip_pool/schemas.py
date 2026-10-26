"""Pydantic models for Ip Pool (IP Management)."""
from __future__ import annotations

from datetime import date, datetime
from decimal import Decimal
from enum import Enum
from uuid import UUID

from pydantic import Field, field_validator, model_validator

from itta_core.models import ApiModel, LifecycleStatus, reject_control_chars


class IpPoolPoolType(str, Enum):
    """Allowed values for Ip Pool Pool Type."""

    DYNAMIC = "DYNAMIC"
    STATIC = "STATIC"
    NAT = "NAT"


class IpPoolLifecycleStage(str, Enum):
    """Allowed values for Ip Pool Lifecycle Stage."""

    PLAN = "PLAN"
    BUILD = "BUILD"
    OPERATE = "OPERATE"
    RETIRE = "RETIRE"


class IpPoolBase(ApiModel):
    """Attributes shared by create and read models."""

    code: str = Field(..., max_length=255, min_length=1, description="Code")
    name: str = Field(..., max_length=255, min_length=1, description="Name")
    description: str | None = Field(None, max_length=4000, description="Description")
    status: LifecycleStatus = Field(..., description="Status")
    external_ref: str | None = Field(None, max_length=255, description="External Ref")
    subnet_id: UUID = Field(..., description="Subnet Id")
    range_start: str = Field(..., max_length=255, min_length=1, description="Range Start")
    range_end: str = Field(..., max_length=255, min_length=1, description="Range End")
    pool_type: IpPoolPoolType | None = Field(None, description="Pool Type")
    lease_seconds: int | None = Field(None, ge=0, description="Lease Seconds")
    effective_from: date | None = Field(None, description="Effective From")
    effective_to: date | None = Field(None, description="Effective To")
    labels: str | None = Field(None, max_length=255, description="Labels")
    lifecycle_stage: IpPoolLifecycleStage | None = Field(None, description="Lifecycle Stage")
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

    @field_validator("range_start")
    @classmethod
    def _check_range_start(cls, value: str | None) -> str | None:
        return reject_control_chars(value, "rangeStart")

    @field_validator("range_end")
    @classmethod
    def _check_range_end(cls, value: str | None) -> str | None:
        return reject_control_chars(value, "rangeEnd")

    @field_validator("labels")
    @classmethod
    def _check_labels(cls, value: str | None) -> str | None:
        return reject_control_chars(value, "labels")

    @field_validator("source_system")
    @classmethod
    def _check_source_system(cls, value: str | None) -> str | None:
        return reject_control_chars(value, "sourceSystem")

    @model_validator(mode="after")
    def _check_ranges(self) -> "IpPoolBase":
        if self.effective_from is not None and self.effective_to is not None and self.effective_to < self.effective_from:
            raise ValueError("effective_to must not be earlier than effective_from")
        return self


class IpPoolCreate(IpPoolBase):
    """Payload to create a Ip Pool."""


class IpPoolUpdate(ApiModel):
    """Partial update; omitted attributes are left unchanged."""

    expected_version: int | None = Field(None, ge=0, description="Optimistic lock version")
    code: str | None = Field(None, max_length=255, min_length=1, description="Code")
    name: str | None = Field(None, max_length=255, min_length=1, description="Name")
    description: str | None = Field(None, max_length=4000, description="Description")
    status: LifecycleStatus | None = Field(None, description="Status")
    external_ref: str | None = Field(None, max_length=255, description="External Ref")
    subnet_id: UUID | None = Field(None, description="Subnet Id")
    range_start: str | None = Field(None, max_length=255, min_length=1, description="Range Start")
    range_end: str | None = Field(None, max_length=255, min_length=1, description="Range End")
    pool_type: IpPoolPoolType | None = Field(None, description="Pool Type")
    lease_seconds: int | None = Field(None, ge=0, description="Lease Seconds")
    effective_from: date | None = Field(None, description="Effective From")
    effective_to: date | None = Field(None, description="Effective To")
    labels: str | None = Field(None, max_length=255, description="Labels")
    lifecycle_stage: IpPoolLifecycleStage | None = Field(None, description="Lifecycle Stage")
    source_system: str | None = Field(None, max_length=255, description="Source System")


class IpPoolRead(IpPoolBase):
    """Ip Pool as returned by the system of record."""

    id: UUID
    tenant_id: str
    version: int = 0
    created_at: datetime | None = None
    updated_at: datetime | None = None
    created_by: str | None = None
    updated_by: str | None = None


class IpPoolSummary(ApiModel):
    id: UUID
    code: str
    name: str
    status: LifecycleStatus


class IpPoolBulkFailure(ApiModel):
    index: int
    message: str


class IpPoolBulkResult(ApiModel):
    succeeded: list[IpPoolRead] = Field(default_factory=list)
    failures: list[IpPoolBulkFailure] = Field(default_factory=list)
