"""Pydantic models for Reconciliation Rule (Discovery and Reconciliation)."""
from __future__ import annotations

from datetime import date, datetime
from decimal import Decimal
from enum import Enum
from uuid import UUID

from pydantic import Field, field_validator, model_validator

from itta_core.models import ApiModel, LifecycleStatus, reject_control_chars


class ReconciliationRuleAction(str, Enum):
    """Allowed values for Reconciliation Rule Action."""

    MERGE = "MERGE"
    CREATE = "CREATE"
    IGNORE = "IGNORE"
    REVIEW = "REVIEW"


class ReconciliationRuleLifecycleStage(str, Enum):
    """Allowed values for Reconciliation Rule Lifecycle Stage."""

    PLAN = "PLAN"
    BUILD = "BUILD"
    OPERATE = "OPERATE"
    RETIRE = "RETIRE"


class ReconciliationRuleBase(ApiModel):
    """Attributes shared by create and read models."""

    code: str = Field(..., max_length=255, min_length=1, description="Code")
    name: str = Field(..., max_length=255, min_length=1, description="Name")
    description: str | None = Field(None, max_length=4000, description="Description")
    status: LifecycleStatus = Field(..., description="Status")
    external_ref: str | None = Field(None, max_length=255, description="External Ref")
    rule_name: str = Field(..., max_length=255, min_length=1, description="Rule Name")
    match_key: str = Field(..., max_length=255, min_length=1, description="Match Key")
    action: ReconciliationRuleAction | None = Field(None, description="Action")
    priority: int = Field(..., ge=0, description="Priority")
    expression: str | None = Field(None, max_length=4000, description="Expression")
    labels: str | None = Field(None, max_length=255, description="Labels")
    lifecycle_stage: ReconciliationRuleLifecycleStage | None = Field(None, description="Lifecycle Stage")
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

    @field_validator("rule_name")
    @classmethod
    def _check_rule_name(cls, value: str | None) -> str | None:
        return reject_control_chars(value, "ruleName")

    @field_validator("match_key")
    @classmethod
    def _check_match_key(cls, value: str | None) -> str | None:
        return reject_control_chars(value, "matchKey")

    @field_validator("expression")
    @classmethod
    def _check_expression(cls, value: str | None) -> str | None:
        return reject_control_chars(value, "expression")

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


class ReconciliationRuleCreate(ReconciliationRuleBase):
    """Payload to create a Reconciliation Rule."""


class ReconciliationRuleUpdate(ApiModel):
    """Partial update; omitted attributes are left unchanged."""

    expected_version: int | None = Field(None, ge=0, description="Optimistic lock version")
    code: str | None = Field(None, max_length=255, min_length=1, description="Code")
    name: str | None = Field(None, max_length=255, min_length=1, description="Name")
    description: str | None = Field(None, max_length=4000, description="Description")
    status: LifecycleStatus | None = Field(None, description="Status")
    external_ref: str | None = Field(None, max_length=255, description="External Ref")
    rule_name: str | None = Field(None, max_length=255, min_length=1, description="Rule Name")
    match_key: str | None = Field(None, max_length=255, min_length=1, description="Match Key")
    action: ReconciliationRuleAction | None = Field(None, description="Action")
    priority: int | None = Field(None, ge=0, description="Priority")
    expression: str | None = Field(None, max_length=4000, description="Expression")
    labels: str | None = Field(None, max_length=255, description="Labels")
    lifecycle_stage: ReconciliationRuleLifecycleStage | None = Field(None, description="Lifecycle Stage")
    source_system: str | None = Field(None, max_length=255, description="Source System")
    region: str | None = Field(None, max_length=255, description="Region")
    owner: str | None = Field(None, max_length=255, description="Owner")


class ReconciliationRuleRead(ReconciliationRuleBase):
    """Reconciliation Rule as returned by the system of record."""

    id: UUID
    tenant_id: str
    version: int = 0
    created_at: datetime | None = None
    updated_at: datetime | None = None
    created_by: str | None = None
    updated_by: str | None = None


class ReconciliationRuleSummary(ApiModel):
    id: UUID
    code: str
    name: str
    status: LifecycleStatus


class ReconciliationRuleBulkFailure(ApiModel):
    index: int
    message: str


class ReconciliationRuleBulkResult(ApiModel):
    succeeded: list[ReconciliationRuleRead] = Field(default_factory=list)
    failures: list[ReconciliationRuleBulkFailure] = Field(default_factory=list)
