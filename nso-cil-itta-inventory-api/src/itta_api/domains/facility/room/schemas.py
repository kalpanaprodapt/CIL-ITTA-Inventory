"""Pydantic models for Room (Facility)."""
from __future__ import annotations

from datetime import date, datetime
from decimal import Decimal
from enum import Enum
from uuid import UUID

from pydantic import Field, field_validator, model_validator

from itta_core.models import ApiModel, LifecycleStatus, reject_control_chars


class RoomRoomType(str, Enum):
    """Allowed values for Room Room Type."""

    MMR = "MMR"
    COLO = "COLO"
    POWER = "POWER"
    BATTERY = "BATTERY"
    STAGING = "STAGING"
    NOC = "NOC"


class RoomLifecycleStage(str, Enum):
    """Allowed values for Room Lifecycle Stage."""

    PLAN = "PLAN"
    BUILD = "BUILD"
    OPERATE = "OPERATE"
    RETIRE = "RETIRE"


class RoomBase(ApiModel):
    """Attributes shared by create and read models."""

    code: str = Field(..., max_length=255, min_length=1, description="Code")
    name: str = Field(..., max_length=255, min_length=1, description="Name")
    description: str | None = Field(None, max_length=4000, description="Description")
    status: LifecycleStatus = Field(..., description="Status")
    external_ref: str | None = Field(None, max_length=255, description="External Ref")
    floor_id: UUID = Field(..., description="Floor Id")
    room_type: RoomRoomType | None = Field(None, description="Room Type")
    area_sqm: Decimal | None = Field(None, ge=0, max_digits=18, decimal_places=4, description="Area Sqm")
    cooling_tons: Decimal | None = Field(None, ge=0, max_digits=18, decimal_places=4, description="Cooling Tons")
    fire_zone: str | None = Field(None, max_length=255, description="Fire Zone")
    effective_from: date | None = Field(None, description="Effective From")
    effective_to: date | None = Field(None, description="Effective To")
    labels: str | None = Field(None, max_length=255, description="Labels")
    lifecycle_stage: RoomLifecycleStage | None = Field(None, description="Lifecycle Stage")
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

    @field_validator("fire_zone")
    @classmethod
    def _check_fire_zone(cls, value: str | None) -> str | None:
        return reject_control_chars(value, "fireZone")

    @field_validator("labels")
    @classmethod
    def _check_labels(cls, value: str | None) -> str | None:
        return reject_control_chars(value, "labels")

    @field_validator("source_system")
    @classmethod
    def _check_source_system(cls, value: str | None) -> str | None:
        return reject_control_chars(value, "sourceSystem")

    @model_validator(mode="after")
    def _check_ranges(self) -> "RoomBase":
        if self.effective_from is not None and self.effective_to is not None and self.effective_to < self.effective_from:
            raise ValueError("effective_to must not be earlier than effective_from")
        return self


class RoomCreate(RoomBase):
    """Payload to create a Room."""


class RoomUpdate(ApiModel):
    """Partial update; omitted attributes are left unchanged."""

    expected_version: int | None = Field(None, ge=0, description="Optimistic lock version")
    code: str | None = Field(None, max_length=255, min_length=1, description="Code")
    name: str | None = Field(None, max_length=255, min_length=1, description="Name")
    description: str | None = Field(None, max_length=4000, description="Description")
    status: LifecycleStatus | None = Field(None, description="Status")
    external_ref: str | None = Field(None, max_length=255, description="External Ref")
    floor_id: UUID | None = Field(None, description="Floor Id")
    room_type: RoomRoomType | None = Field(None, description="Room Type")
    area_sqm: Decimal | None = Field(None, ge=0, max_digits=18, decimal_places=4, description="Area Sqm")
    cooling_tons: Decimal | None = Field(None, ge=0, max_digits=18, decimal_places=4, description="Cooling Tons")
    fire_zone: str | None = Field(None, max_length=255, description="Fire Zone")
    effective_from: date | None = Field(None, description="Effective From")
    effective_to: date | None = Field(None, description="Effective To")
    labels: str | None = Field(None, max_length=255, description="Labels")
    lifecycle_stage: RoomLifecycleStage | None = Field(None, description="Lifecycle Stage")
    source_system: str | None = Field(None, max_length=255, description="Source System")


class RoomRead(RoomBase):
    """Room as returned by the system of record."""

    id: UUID
    tenant_id: str
    version: int = 0
    created_at: datetime | None = None
    updated_at: datetime | None = None
    created_by: str | None = None
    updated_by: str | None = None


class RoomSummary(ApiModel):
    id: UUID
    code: str
    name: str
    status: LifecycleStatus


class RoomBulkFailure(ApiModel):
    index: int
    message: str


class RoomBulkResult(ApiModel):
    succeeded: list[RoomRead] = Field(default_factory=list)
    failures: list[RoomBulkFailure] = Field(default_factory=list)
