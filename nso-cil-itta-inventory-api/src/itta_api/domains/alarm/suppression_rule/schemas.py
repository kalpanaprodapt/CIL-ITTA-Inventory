"""Pydantic models for Suppression Rule (Alarms and Events)."""
from __future__ import annotations

from datetime import date, datetime
from decimal import Decimal
from enum import Enum
from uuid import UUID

from pydantic import Field, field_validator, model_validator

from itta_core.models import ApiModel, LifecycleStatus, reject_control_chars


class SuppressionRuleCriticality(str, Enum):
    """Allowed values for Suppression Rule Criticality."""

    LOW = "LOW"
    MEDIUM = "MEDIUM"
    HIGH = "HIGH"
    CRITICAL = "CRITICAL"


class SuppressionRuleBase(ApiModel):
    """Attributes shared by create and read models."""

    code: str = Field(..., max_length=255, min_length=1, description="Code")
    name: str = Field(..., max_length=255, min_length=1, description="Name")
    description: str | None = Field(None, max_length=4000, description="Description")
    status: LifecycleStatus = Field(..., description="Status")
    external_ref: str | None = Field(None, max_length=255, description="External Ref")
    rule_name: str = Field(..., max_length=255, min_length=1, description="Rule Name")
    match_expression: str = Field(..., max_length=4000, min_length=1, description="Match Expression")
    starts_at: datetime | None = Field(None, description="Starts At")
    ends_at: datetime | None = Field(None, description="Ends At")
    reason: str | None = Field(None, max_length=255, description="Reason")
    source_system: str | None = Field(None, max_length=255, description="Source System")
    region: str | None = Field(None, max_length=255, description="Region")
    owner: str | None = Field(None, max_length=255, description="Owner")
    criticality: SuppressionRuleCriticality | None = Field(None, description="Criticality")
    cost_center: str | None = Field(None, max_length=255, description="Cost Center")

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

    @field_validator("match_expression")
    @classmethod
    def _check_match_expression(cls, value: str | None) -> str | None:
        return reject_control_chars(value, "matchExpression")

    @field_validator("reason")
    @classmethod
    def _check_reason(cls, value: str | None) -> str | None:
        return reject_control_chars(value, "reason")

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

    @field_validator("cost_center")
    @classmethod
    def _check_cost_center(cls, value: str | None) -> str | None:
        return reject_control_chars(value, "costCenter")

    @model_validator(mode="after")
    def _check_ranges(self) -> "SuppressionRuleBase":
        if self.starts_at is not None and self.ends_at is not None and self.ends_at < self.starts_at:
            raise ValueError("ends_at must not be earlier than starts_at")
        return self


class SuppressionRuleCreate(SuppressionRuleBase):
    """Payload to create a Suppression Rule."""


class SuppressionRuleUpdate(ApiModel):
    """Partial update; omitted attributes are left unchanged."""

    expected_version: int | None = Field(None, ge=0, description="Optimistic lock version")
    code: str | None = Field(None, max_length=255, min_length=1, description="Code")
    name: str | None = Field(None, max_length=255, min_length=1, description="Name")
    description: str | None = Field(None, max_length=4000, description="Description")
    status: LifecycleStatus | None = Field(None, description="Status")
    external_ref: str | None = Field(None, max_length=255, description="External Ref")
    rule_name: str | None = Field(None, max_length=255, min_length=1, description="Rule Name")
    match_expression: str | None = Field(None, max_length=4000, min_length=1, description="Match Expression")
    starts_at: datetime | None = Field(None, description="Starts At")
    ends_at: datetime | None = Field(None, description="Ends At")
    reason: str | None = Field(None, max_length=255, description="Reason")
    source_system: str | None = Field(None, max_length=255, description="Source System")
    region: str | None = Field(None, max_length=255, description="Region")
    owner: str | None = Field(None, max_length=255, description="Owner")
    criticality: SuppressionRuleCriticality | None = Field(None, description="Criticality")
    cost_center: str | None = Field(None, max_length=255, description="Cost Center")


class SuppressionRuleRead(SuppressionRuleBase):
    """Suppression Rule as returned by the system of record."""

    id: UUID
    tenant_id: str
    version: int = 0
    created_at: datetime | None = None
    updated_at: datetime | None = None
    created_by: str | None = None
    updated_by: str | None = None


class SuppressionRuleSummary(ApiModel):
    id: UUID
    code: str
    name: str
    status: LifecycleStatus


class SuppressionRuleBulkFailure(ApiModel):
    index: int
    message: str


class SuppressionRuleBulkResult(ApiModel):
    succeeded: list[SuppressionRuleRead] = Field(default_factory=list)
    failures: list[SuppressionRuleBulkFailure] = Field(default_factory=list)
