"""Pydantic models for Vrf Instance (IP Management)."""
from __future__ import annotations

from datetime import date, datetime
from decimal import Decimal
from enum import Enum
from uuid import UUID

from pydantic import Field, field_validator, model_validator

from itta_core.models import ApiModel, LifecycleStatus, reject_control_chars


class VrfInstanceLifecycleStage(str, Enum):
    """Allowed values for Vrf Instance Lifecycle Stage."""

    PLAN = "PLAN"
    BUILD = "BUILD"
    OPERATE = "OPERATE"
    RETIRE = "RETIRE"


class VrfInstanceBase(ApiModel):
    """Attributes shared by create and read models."""

    code: str = Field(..., max_length=255, min_length=1, description="Code")
    name: str = Field(..., max_length=255, min_length=1, description="Name")
    description: str | None = Field(None, max_length=4000, description="Description")
    status: LifecycleStatus = Field(..., description="Status")
    external_ref: str | None = Field(None, max_length=255, description="External Ref")
    vrf_name: str = Field(..., max_length=255, min_length=1, description="Vrf Name")
    route_distinguisher: str = Field(..., max_length=255, min_length=1, description="Route Distinguisher")
    import_targets: str | None = Field(None, max_length=255, description="Import Targets")
    export_targets: str | None = Field(None, max_length=255, description="Export Targets")
    device_id: UUID | None = Field(None, description="Device Id")
    labels: str | None = Field(None, max_length=255, description="Labels")
    lifecycle_stage: VrfInstanceLifecycleStage | None = Field(None, description="Lifecycle Stage")
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

    @field_validator("vrf_name")
    @classmethod
    def _check_vrf_name(cls, value: str | None) -> str | None:
        return reject_control_chars(value, "vrfName")

    @field_validator("route_distinguisher")
    @classmethod
    def _check_route_distinguisher(cls, value: str | None) -> str | None:
        return reject_control_chars(value, "routeDistinguisher")

    @field_validator("import_targets")
    @classmethod
    def _check_import_targets(cls, value: str | None) -> str | None:
        return reject_control_chars(value, "importTargets")

    @field_validator("export_targets")
    @classmethod
    def _check_export_targets(cls, value: str | None) -> str | None:
        return reject_control_chars(value, "exportTargets")

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


class VrfInstanceCreate(VrfInstanceBase):
    """Payload to create a Vrf Instance."""


class VrfInstanceUpdate(ApiModel):
    """Partial update; omitted attributes are left unchanged."""

    expected_version: int | None = Field(None, ge=0, description="Optimistic lock version")
    code: str | None = Field(None, max_length=255, min_length=1, description="Code")
    name: str | None = Field(None, max_length=255, min_length=1, description="Name")
    description: str | None = Field(None, max_length=4000, description="Description")
    status: LifecycleStatus | None = Field(None, description="Status")
    external_ref: str | None = Field(None, max_length=255, description="External Ref")
    vrf_name: str | None = Field(None, max_length=255, min_length=1, description="Vrf Name")
    route_distinguisher: str | None = Field(None, max_length=255, min_length=1, description="Route Distinguisher")
    import_targets: str | None = Field(None, max_length=255, description="Import Targets")
    export_targets: str | None = Field(None, max_length=255, description="Export Targets")
    device_id: UUID | None = Field(None, description="Device Id")
    labels: str | None = Field(None, max_length=255, description="Labels")
    lifecycle_stage: VrfInstanceLifecycleStage | None = Field(None, description="Lifecycle Stage")
    source_system: str | None = Field(None, max_length=255, description="Source System")
    region: str | None = Field(None, max_length=255, description="Region")
    owner: str | None = Field(None, max_length=255, description="Owner")


class VrfInstanceRead(VrfInstanceBase):
    """Vrf Instance as returned by the system of record."""

    id: UUID
    tenant_id: str
    version: int = 0
    created_at: datetime | None = None
    updated_at: datetime | None = None
    created_by: str | None = None
    updated_by: str | None = None


class VrfInstanceSummary(ApiModel):
    id: UUID
    code: str
    name: str
    status: LifecycleStatus


class VrfInstanceBulkFailure(ApiModel):
    index: int
    message: str


class VrfInstanceBulkResult(ApiModel):
    succeeded: list[VrfInstanceRead] = Field(default_factory=list)
    failures: list[VrfInstanceBulkFailure] = Field(default_factory=list)
