"""Pydantic models for K8s Cluster (Virtual and Cloud)."""
from __future__ import annotations

from datetime import date, datetime
from decimal import Decimal
from enum import Enum
from uuid import UUID

from pydantic import Field, field_validator, model_validator

from itta_core.models import ApiModel, LifecycleStatus, reject_control_chars


class K8sClusterLifecycleStage(str, Enum):
    """Allowed values for K8s Cluster Lifecycle Stage."""

    PLAN = "PLAN"
    BUILD = "BUILD"
    OPERATE = "OPERATE"
    RETIRE = "RETIRE"


class K8sClusterBase(ApiModel):
    """Attributes shared by create and read models."""

    code: str = Field(..., max_length=255, min_length=1, description="Code")
    name: str = Field(..., max_length=255, min_length=1, description="Name")
    description: str | None = Field(None, max_length=4000, description="Description")
    status: LifecycleStatus = Field(..., description="Status")
    external_ref: str | None = Field(None, max_length=255, description="External Ref")
    cluster_name: str = Field(..., max_length=255, min_length=1, description="Cluster Name")
    cluster_version: str = Field(..., max_length=255, min_length=1, description="Cluster Version")
    node_count: int = Field(..., ge=0, description="Node Count")
    cloud_account_id: UUID | None = Field(None, description="Cloud Account Id")
    region: str | None = Field(None, max_length=255, description="Region")
    api_endpoint: str | None = Field(None, max_length=255, description="Api Endpoint")
    effective_to: date | None = Field(None, description="Effective To")
    labels: str | None = Field(None, max_length=255, description="Labels")
    lifecycle_stage: K8sClusterLifecycleStage | None = Field(None, description="Lifecycle Stage")
    source_system: str | None = Field(None, max_length=255, description="Source System")
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

    @field_validator("cluster_name")
    @classmethod
    def _check_cluster_name(cls, value: str | None) -> str | None:
        return reject_control_chars(value, "clusterName")

    @field_validator("cluster_version")
    @classmethod
    def _check_cluster_version(cls, value: str | None) -> str | None:
        return reject_control_chars(value, "clusterVersion")

    @field_validator("region")
    @classmethod
    def _check_region(cls, value: str | None) -> str | None:
        return reject_control_chars(value, "region")

    @field_validator("api_endpoint")
    @classmethod
    def _check_api_endpoint(cls, value: str | None) -> str | None:
        return reject_control_chars(value, "apiEndpoint")

    @field_validator("labels")
    @classmethod
    def _check_labels(cls, value: str | None) -> str | None:
        return reject_control_chars(value, "labels")

    @field_validator("source_system")
    @classmethod
    def _check_source_system(cls, value: str | None) -> str | None:
        return reject_control_chars(value, "sourceSystem")

    @field_validator("owner")
    @classmethod
    def _check_owner(cls, value: str | None) -> str | None:
        return reject_control_chars(value, "owner")


class K8sClusterCreate(K8sClusterBase):
    """Payload to create a K8s Cluster."""


class K8sClusterUpdate(ApiModel):
    """Partial update; omitted attributes are left unchanged."""

    expected_version: int | None = Field(None, ge=0, description="Optimistic lock version")
    code: str | None = Field(None, max_length=255, min_length=1, description="Code")
    name: str | None = Field(None, max_length=255, min_length=1, description="Name")
    description: str | None = Field(None, max_length=4000, description="Description")
    status: LifecycleStatus | None = Field(None, description="Status")
    external_ref: str | None = Field(None, max_length=255, description="External Ref")
    cluster_name: str | None = Field(None, max_length=255, min_length=1, description="Cluster Name")
    cluster_version: str | None = Field(None, max_length=255, min_length=1, description="Cluster Version")
    node_count: int | None = Field(None, ge=0, description="Node Count")
    cloud_account_id: UUID | None = Field(None, description="Cloud Account Id")
    region: str | None = Field(None, max_length=255, description="Region")
    api_endpoint: str | None = Field(None, max_length=255, description="Api Endpoint")
    effective_to: date | None = Field(None, description="Effective To")
    labels: str | None = Field(None, max_length=255, description="Labels")
    lifecycle_stage: K8sClusterLifecycleStage | None = Field(None, description="Lifecycle Stage")
    source_system: str | None = Field(None, max_length=255, description="Source System")
    owner: str | None = Field(None, max_length=255, description="Owner")


class K8sClusterRead(K8sClusterBase):
    """K8s Cluster as returned by the system of record."""

    id: UUID
    tenant_id: str
    version: int = 0
    created_at: datetime | None = None
    updated_at: datetime | None = None
    created_by: str | None = None
    updated_by: str | None = None


class K8sClusterSummary(ApiModel):
    id: UUID
    code: str
    name: str
    status: LifecycleStatus


class K8sClusterBulkFailure(ApiModel):
    index: int
    message: str


class K8sClusterBulkResult(ApiModel):
    succeeded: list[K8sClusterRead] = Field(default_factory=list)
    failures: list[K8sClusterBulkFailure] = Field(default_factory=list)
