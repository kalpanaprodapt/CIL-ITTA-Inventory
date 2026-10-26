"""Pydantic models for Discovered Asset (Discovery and Reconciliation)."""
from __future__ import annotations

from datetime import date, datetime
from decimal import Decimal
from enum import Enum
from uuid import UUID

from pydantic import Field, field_validator, model_validator

from itta_core.models import ApiModel, LifecycleStatus, reject_control_chars


class DiscoveredAssetLifecycleStage(str, Enum):
    """Allowed values for Discovered Asset Lifecycle Stage."""

    PLAN = "PLAN"
    BUILD = "BUILD"
    OPERATE = "OPERATE"
    RETIRE = "RETIRE"


class DiscoveredAssetBase(ApiModel):
    """Attributes shared by create and read models."""

    code: str = Field(..., max_length=255, min_length=1, description="Code")
    name: str = Field(..., max_length=255, min_length=1, description="Name")
    description: str | None = Field(None, max_length=4000, description="Description")
    status: LifecycleStatus = Field(..., description="Status")
    external_ref: str | None = Field(None, max_length=255, description="External Ref")
    job_id: UUID = Field(..., description="Job Id")
    asset_type: str = Field(..., max_length=255, min_length=1, description="Asset Type")
    fingerprint: str = Field(..., max_length=255, min_length=1, description="Fingerprint")
    raw_payload: str | None = Field(None, max_length=4000, description="Raw Payload")
    matched_asset_id: UUID | None = Field(None, description="Matched Asset Id")
    effective_from: date | None = Field(None, description="Effective From")
    effective_to: date | None = Field(None, description="Effective To")
    labels: str | None = Field(None, max_length=255, description="Labels")
    lifecycle_stage: DiscoveredAssetLifecycleStage | None = Field(None, description="Lifecycle Stage")
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

    @field_validator("asset_type")
    @classmethod
    def _check_asset_type(cls, value: str | None) -> str | None:
        return reject_control_chars(value, "assetType")

    @field_validator("fingerprint")
    @classmethod
    def _check_fingerprint(cls, value: str | None) -> str | None:
        return reject_control_chars(value, "fingerprint")

    @field_validator("raw_payload")
    @classmethod
    def _check_raw_payload(cls, value: str | None) -> str | None:
        return reject_control_chars(value, "rawPayload")

    @field_validator("labels")
    @classmethod
    def _check_labels(cls, value: str | None) -> str | None:
        return reject_control_chars(value, "labels")

    @field_validator("source_system")
    @classmethod
    def _check_source_system(cls, value: str | None) -> str | None:
        return reject_control_chars(value, "sourceSystem")

    @model_validator(mode="after")
    def _check_ranges(self) -> "DiscoveredAssetBase":
        if self.effective_from is not None and self.effective_to is not None and self.effective_to < self.effective_from:
            raise ValueError("effective_to must not be earlier than effective_from")
        return self


class DiscoveredAssetCreate(DiscoveredAssetBase):
    """Payload to create a Discovered Asset."""


class DiscoveredAssetUpdate(ApiModel):
    """Partial update; omitted attributes are left unchanged."""

    expected_version: int | None = Field(None, ge=0, description="Optimistic lock version")
    code: str | None = Field(None, max_length=255, min_length=1, description="Code")
    name: str | None = Field(None, max_length=255, min_length=1, description="Name")
    description: str | None = Field(None, max_length=4000, description="Description")
    status: LifecycleStatus | None = Field(None, description="Status")
    external_ref: str | None = Field(None, max_length=255, description="External Ref")
    job_id: UUID | None = Field(None, description="Job Id")
    asset_type: str | None = Field(None, max_length=255, min_length=1, description="Asset Type")
    fingerprint: str | None = Field(None, max_length=255, min_length=1, description="Fingerprint")
    raw_payload: str | None = Field(None, max_length=4000, description="Raw Payload")
    matched_asset_id: UUID | None = Field(None, description="Matched Asset Id")
    effective_from: date | None = Field(None, description="Effective From")
    effective_to: date | None = Field(None, description="Effective To")
    labels: str | None = Field(None, max_length=255, description="Labels")
    lifecycle_stage: DiscoveredAssetLifecycleStage | None = Field(None, description="Lifecycle Stage")
    source_system: str | None = Field(None, max_length=255, description="Source System")


class DiscoveredAssetRead(DiscoveredAssetBase):
    """Discovered Asset as returned by the system of record."""

    id: UUID
    tenant_id: str
    version: int = 0
    created_at: datetime | None = None
    updated_at: datetime | None = None
    created_by: str | None = None
    updated_by: str | None = None


class DiscoveredAssetSummary(ApiModel):
    id: UUID
    code: str
    name: str
    status: LifecycleStatus


class DiscoveredAssetBulkFailure(ApiModel):
    index: int
    message: str


class DiscoveredAssetBulkResult(ApiModel):
    succeeded: list[DiscoveredAssetRead] = Field(default_factory=list)
    failures: list[DiscoveredAssetBulkFailure] = Field(default_factory=list)
