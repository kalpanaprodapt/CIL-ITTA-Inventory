"""Pydantic models for Service Catalog Item (Services)."""
from __future__ import annotations

from datetime import date, datetime
from decimal import Decimal
from enum import Enum
from uuid import UUID

from pydantic import Field, field_validator, model_validator

from itta_core.models import ApiModel, LifecycleStatus, reject_control_chars


class ServiceCatalogItemFamily(str, Enum):
    """Allowed values for Service Catalog Item Family."""

    CONNECTIVITY = "CONNECTIVITY"
    SECURITY = "SECURITY"
    VOICE = "VOICE"
    CLOUD = "CLOUD"
    MANAGED = "MANAGED"


class ServiceCatalogItemLifecycleStage(str, Enum):
    """Allowed values for Service Catalog Item Lifecycle Stage."""

    PLAN = "PLAN"
    BUILD = "BUILD"
    OPERATE = "OPERATE"
    RETIRE = "RETIRE"


class ServiceCatalogItemBase(ApiModel):
    """Attributes shared by create and read models."""

    code: str = Field(..., max_length=255, min_length=1, description="Code")
    name: str = Field(..., max_length=255, min_length=1, description="Name")
    description: str | None = Field(None, max_length=4000, description="Description")
    status: LifecycleStatus = Field(..., description="Status")
    external_ref: str | None = Field(None, max_length=255, description="External Ref")
    item_name: str = Field(..., max_length=255, min_length=1, description="Item Name")
    family: ServiceCatalogItemFamily | None = Field(None, description="Family")
    base_price: Decimal | None = Field(None, ge=0, max_digits=18, decimal_places=4, description="Base Price")
    billing_unit: str | None = Field(None, max_length=255, description="Billing Unit")
    orderable: bool | None = Field(None, description="Orderable")
    effective_to: date | None = Field(None, description="Effective To")
    labels: str | None = Field(None, max_length=255, description="Labels")
    lifecycle_stage: ServiceCatalogItemLifecycleStage | None = Field(None, description="Lifecycle Stage")
    source_system: str | None = Field(None, max_length=255, description="Source System")
    region: str | None = Field(None, max_length=255, description="Region")

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

    @field_validator("item_name")
    @classmethod
    def _check_item_name(cls, value: str | None) -> str | None:
        return reject_control_chars(value, "itemName")

    @field_validator("billing_unit")
    @classmethod
    def _check_billing_unit(cls, value: str | None) -> str | None:
        return reject_control_chars(value, "billingUnit")

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


class ServiceCatalogItemCreate(ServiceCatalogItemBase):
    """Payload to create a Service Catalog Item."""


class ServiceCatalogItemUpdate(ApiModel):
    """Partial update; omitted attributes are left unchanged."""

    expected_version: int | None = Field(None, ge=0, description="Optimistic lock version")
    code: str | None = Field(None, max_length=255, min_length=1, description="Code")
    name: str | None = Field(None, max_length=255, min_length=1, description="Name")
    description: str | None = Field(None, max_length=4000, description="Description")
    status: LifecycleStatus | None = Field(None, description="Status")
    external_ref: str | None = Field(None, max_length=255, description="External Ref")
    item_name: str | None = Field(None, max_length=255, min_length=1, description="Item Name")
    family: ServiceCatalogItemFamily | None = Field(None, description="Family")
    base_price: Decimal | None = Field(None, ge=0, max_digits=18, decimal_places=4, description="Base Price")
    billing_unit: str | None = Field(None, max_length=255, description="Billing Unit")
    orderable: bool | None = Field(None, description="Orderable")
    effective_to: date | None = Field(None, description="Effective To")
    labels: str | None = Field(None, max_length=255, description="Labels")
    lifecycle_stage: ServiceCatalogItemLifecycleStage | None = Field(None, description="Lifecycle Stage")
    source_system: str | None = Field(None, max_length=255, description="Source System")
    region: str | None = Field(None, max_length=255, description="Region")


class ServiceCatalogItemRead(ServiceCatalogItemBase):
    """Service Catalog Item as returned by the system of record."""

    id: UUID
    tenant_id: str
    version: int = 0
    created_at: datetime | None = None
    updated_at: datetime | None = None
    created_by: str | None = None
    updated_by: str | None = None


class ServiceCatalogItemSummary(ApiModel):
    id: UUID
    code: str
    name: str
    status: LifecycleStatus


class ServiceCatalogItemBulkFailure(ApiModel):
    index: int
    message: str


class ServiceCatalogItemBulkResult(ApiModel):
    succeeded: list[ServiceCatalogItemRead] = Field(default_factory=list)
    failures: list[ServiceCatalogItemBulkFailure] = Field(default_factory=list)
