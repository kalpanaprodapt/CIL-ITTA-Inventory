"""Pydantic models for Asset (Asset and Finance)."""
from __future__ import annotations

from datetime import date, datetime
from decimal import Decimal
from enum import Enum
from uuid import UUID

from pydantic import Field, field_validator, model_validator

from itta_core.models import ApiModel, LifecycleStatus, reject_control_chars


class AssetLifecycleStage(str, Enum):
    """Allowed values for Asset Lifecycle Stage."""

    PLAN = "PLAN"
    BUILD = "BUILD"
    OPERATE = "OPERATE"
    RETIRE = "RETIRE"


class AssetBase(ApiModel):
    """Attributes shared by create and read models."""

    code: str = Field(..., max_length=255, min_length=1, description="Code")
    name: str = Field(..., max_length=255, min_length=1, description="Name")
    description: str | None = Field(None, max_length=4000, description="Description")
    status: LifecycleStatus = Field(..., description="Status")
    external_ref: str | None = Field(None, max_length=255, description="External Ref")
    asset_tag: str = Field(..., max_length=255, min_length=1, description="Asset Tag")
    asset_class: str = Field(..., max_length=255, min_length=1, description="Asset Class")
    acquired_on: date | None = Field(None, description="Acquired On")
    purchase_price: Decimal | None = Field(None, ge=0, max_digits=18, decimal_places=4, description="Purchase Price")
    current_value: Decimal | None = Field(None, ge=0, max_digits=18, decimal_places=4, description="Current Value")
    custodian: str | None = Field(None, max_length=255, description="Custodian")
    labels: str | None = Field(None, max_length=255, description="Labels")
    lifecycle_stage: AssetLifecycleStage | None = Field(None, description="Lifecycle Stage")
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

    @field_validator("asset_tag")
    @classmethod
    def _check_asset_tag(cls, value: str | None) -> str | None:
        return reject_control_chars(value, "assetTag")

    @field_validator("asset_class")
    @classmethod
    def _check_asset_class(cls, value: str | None) -> str | None:
        return reject_control_chars(value, "assetClass")

    @field_validator("custodian")
    @classmethod
    def _check_custodian(cls, value: str | None) -> str | None:
        return reject_control_chars(value, "custodian")

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


class AssetCreate(AssetBase):
    """Payload to create a Asset."""


class AssetUpdate(ApiModel):
    """Partial update; omitted attributes are left unchanged."""

    expected_version: int | None = Field(None, ge=0, description="Optimistic lock version")
    code: str | None = Field(None, max_length=255, min_length=1, description="Code")
    name: str | None = Field(None, max_length=255, min_length=1, description="Name")
    description: str | None = Field(None, max_length=4000, description="Description")
    status: LifecycleStatus | None = Field(None, description="Status")
    external_ref: str | None = Field(None, max_length=255, description="External Ref")
    asset_tag: str | None = Field(None, max_length=255, min_length=1, description="Asset Tag")
    asset_class: str | None = Field(None, max_length=255, min_length=1, description="Asset Class")
    acquired_on: date | None = Field(None, description="Acquired On")
    purchase_price: Decimal | None = Field(None, ge=0, max_digits=18, decimal_places=4, description="Purchase Price")
    current_value: Decimal | None = Field(None, ge=0, max_digits=18, decimal_places=4, description="Current Value")
    custodian: str | None = Field(None, max_length=255, description="Custodian")
    labels: str | None = Field(None, max_length=255, description="Labels")
    lifecycle_stage: AssetLifecycleStage | None = Field(None, description="Lifecycle Stage")
    source_system: str | None = Field(None, max_length=255, description="Source System")
    region: str | None = Field(None, max_length=255, description="Region")
    owner: str | None = Field(None, max_length=255, description="Owner")


class AssetRead(AssetBase):
    """Asset as returned by the system of record."""

    id: UUID
    tenant_id: str
    version: int = 0
    created_at: datetime | None = None
    updated_at: datetime | None = None
    created_by: str | None = None
    updated_by: str | None = None


class AssetSummary(ApiModel):
    id: UUID
    code: str
    name: str
    status: LifecycleStatus


class AssetBulkFailure(ApiModel):
    index: int
    message: str


class AssetBulkResult(ApiModel):
    succeeded: list[AssetRead] = Field(default_factory=list)
    failures: list[AssetBulkFailure] = Field(default_factory=list)
