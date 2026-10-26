"""Pydantic models for Fan Tray (Network Device)."""
from __future__ import annotations

from datetime import date, datetime
from decimal import Decimal
from enum import Enum
from uuid import UUID

from pydantic import Field, field_validator, model_validator

from itta_core.models import ApiModel, LifecycleStatus, reject_control_chars


class FanTrayAirflow(str, Enum):
    """Allowed values for Fan Tray Airflow."""

    FRONT_TO_BACK = "FRONT_TO_BACK"
    BACK_TO_FRONT = "BACK_TO_FRONT"


class FanTrayLifecycleStage(str, Enum):
    """Allowed values for Fan Tray Lifecycle Stage."""

    PLAN = "PLAN"
    BUILD = "BUILD"
    OPERATE = "OPERATE"
    RETIRE = "RETIRE"


class FanTrayBase(ApiModel):
    """Attributes shared by create and read models."""

    code: str = Field(..., max_length=255, min_length=1, description="Code")
    name: str = Field(..., max_length=255, min_length=1, description="Name")
    description: str | None = Field(None, max_length=4000, description="Description")
    status: LifecycleStatus = Field(..., description="Status")
    external_ref: str | None = Field(None, max_length=255, description="External Ref")
    chassis_id: UUID = Field(..., description="Chassis Id")
    slot_number: int | None = Field(None, ge=0, description="Slot Number")
    fan_count: int | None = Field(None, ge=0, description="Fan Count")
    airflow: FanTrayAirflow | None = Field(None, description="Airflow")
    serial_number: str | None = Field(None, max_length=255, description="Serial Number")
    notes: str | None = Field(None, max_length=4000, description="Notes")
    effective_from: date | None = Field(None, description="Effective From")
    effective_to: date | None = Field(None, description="Effective To")
    labels: str | None = Field(None, max_length=255, description="Labels")
    lifecycle_stage: FanTrayLifecycleStage | None = Field(None, description="Lifecycle Stage")

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

    @field_validator("serial_number")
    @classmethod
    def _check_serial_number(cls, value: str | None) -> str | None:
        return reject_control_chars(value, "serialNumber")

    @field_validator("notes")
    @classmethod
    def _check_notes(cls, value: str | None) -> str | None:
        return reject_control_chars(value, "notes")

    @field_validator("labels")
    @classmethod
    def _check_labels(cls, value: str | None) -> str | None:
        return reject_control_chars(value, "labels")

    @model_validator(mode="after")
    def _check_ranges(self) -> "FanTrayBase":
        if self.effective_from is not None and self.effective_to is not None and self.effective_to < self.effective_from:
            raise ValueError("effective_to must not be earlier than effective_from")
        return self


class FanTrayCreate(FanTrayBase):
    """Payload to create a Fan Tray."""


class FanTrayUpdate(ApiModel):
    """Partial update; omitted attributes are left unchanged."""

    expected_version: int | None = Field(None, ge=0, description="Optimistic lock version")
    code: str | None = Field(None, max_length=255, min_length=1, description="Code")
    name: str | None = Field(None, max_length=255, min_length=1, description="Name")
    description: str | None = Field(None, max_length=4000, description="Description")
    status: LifecycleStatus | None = Field(None, description="Status")
    external_ref: str | None = Field(None, max_length=255, description="External Ref")
    chassis_id: UUID | None = Field(None, description="Chassis Id")
    slot_number: int | None = Field(None, ge=0, description="Slot Number")
    fan_count: int | None = Field(None, ge=0, description="Fan Count")
    airflow: FanTrayAirflow | None = Field(None, description="Airflow")
    serial_number: str | None = Field(None, max_length=255, description="Serial Number")
    notes: str | None = Field(None, max_length=4000, description="Notes")
    effective_from: date | None = Field(None, description="Effective From")
    effective_to: date | None = Field(None, description="Effective To")
    labels: str | None = Field(None, max_length=255, description="Labels")
    lifecycle_stage: FanTrayLifecycleStage | None = Field(None, description="Lifecycle Stage")


class FanTrayRead(FanTrayBase):
    """Fan Tray as returned by the system of record."""

    id: UUID
    tenant_id: str
    version: int = 0
    created_at: datetime | None = None
    updated_at: datetime | None = None
    created_by: str | None = None
    updated_by: str | None = None


class FanTraySummary(ApiModel):
    id: UUID
    code: str
    name: str
    status: LifecycleStatus


class FanTrayBulkFailure(ApiModel):
    index: int
    message: str


class FanTrayBulkResult(ApiModel):
    succeeded: list[FanTrayRead] = Field(default_factory=list)
    failures: list[FanTrayBulkFailure] = Field(default_factory=list)
