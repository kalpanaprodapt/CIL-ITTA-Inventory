"""Search filter for Cloud Account and its translation to upstream query parameters."""
from __future__ import annotations

from datetime import date, datetime
from decimal import Decimal
from uuid import UUID

from pydantic import Field

from itta_core.models import ApiModel, LifecycleStatus
from itta_api.domains.virtual.cloud_account.schemas import *  # noqa: F401,F403


class CloudAccountFilter(ApiModel):
    """Query-string filter for Cloud Account search."""

    q: str | None = Field(None, description="Free text over code, name and description")
    status: list[LifecycleStatus] | None = None
    code: str | None = Field(None, description="Code contains")
    name: str | None = Field(None, description="Name contains")
    external_ref: str | None = Field(None, description="External Ref contains")
    provider: list[CloudAccountProvider] | None = None
    account_ref: str | None = Field(None, description="Account Ref contains")
    subscription_name: str | None = Field(None, description="Subscription Name contains")
    monthly_budget_min: Decimal | None = None
    monthly_budget_max: Decimal | None = None
    environment: str | None = Field(None, description="Environment contains")
    effective_from_from: date | None = None
    effective_from_to: date | None = None
    effective_to_from: date | None = None
    effective_to_to: date | None = None
    labels: str | None = Field(None, description="Labels contains")
    lifecycle_stage: list[CloudAccountLifecycleStage] | None = None


def to_query(flt: CloudAccountFilter) -> dict[str, str]:
    """Translate a filter into the query parameters understood by the inventory service."""
    params: dict[str, str] = {}
    if flt.q and flt.q.strip():
        params["q"] = flt.q.strip()
    if flt.status:
        params["status"] = ",".join(v.value for v in flt.status)
    if flt.code:
        params["code"] = flt.code.strip()
    if flt.name:
        params["name"] = flt.name.strip()
    if flt.external_ref:
        params["externalRef"] = flt.external_ref.strip()
    if flt.provider:
        params["provider"] = ",".join(v.value for v in flt.provider)
    if flt.account_ref:
        params["accountRef"] = flt.account_ref.strip()
    if flt.subscription_name:
        params["subscriptionName"] = flt.subscription_name.strip()
    if flt.monthly_budget_min is not None:
        params["monthlyBudgetMin"] = str(flt.monthly_budget_min)
    if flt.monthly_budget_max is not None:
        params["monthlyBudgetMax"] = str(flt.monthly_budget_max)
    if flt.environment:
        params["environment"] = flt.environment.strip()
    if flt.effective_from_from is not None:
        params["effectiveFromFrom"] = flt.effective_from_from.isoformat()
    if flt.effective_from_to is not None:
        params["effectiveFromTo"] = flt.effective_from_to.isoformat()
    if flt.effective_to_from is not None:
        params["effectiveToFrom"] = flt.effective_to_from.isoformat()
    if flt.effective_to_to is not None:
        params["effectiveToTo"] = flt.effective_to_to.isoformat()
    if flt.labels:
        params["labels"] = flt.labels.strip()
    if flt.lifecycle_stage:
        params["lifecycleStage"] = ",".join(v.value for v in flt.lifecycle_stage)
    return params
