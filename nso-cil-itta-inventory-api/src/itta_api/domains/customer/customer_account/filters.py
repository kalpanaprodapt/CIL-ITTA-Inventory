"""Search filter for Customer Account and its translation to upstream query parameters."""
from __future__ import annotations

from datetime import date, datetime
from decimal import Decimal
from uuid import UUID

from pydantic import Field

from itta_core.models import ApiModel, LifecycleStatus
from itta_api.domains.customer.customer_account.schemas import *  # noqa: F401,F403


class CustomerAccountFilter(ApiModel):
    """Query-string filter for Customer Account search."""

    q: str | None = Field(None, description="Free text over code, name and description")
    status: list[LifecycleStatus] | None = None
    code: str | None = Field(None, description="Code contains")
    name: str | None = Field(None, description="Name contains")
    external_ref: str | None = Field(None, description="External Ref contains")
    customer_id: UUID | None = None
    account_number: str | None = Field(None, description="Account Number contains")
    billing_cycle: list[CustomerAccountBillingCycle] | None = None
    currency: str | None = Field(None, description="Currency contains")
    credit_limit_min: Decimal | None = None
    credit_limit_max: Decimal | None = None
    effective_from_from: date | None = None
    effective_from_to: date | None = None
    effective_to_from: date | None = None
    effective_to_to: date | None = None
    labels: str | None = Field(None, description="Labels contains")
    lifecycle_stage: list[CustomerAccountLifecycleStage] | None = None
    source_system: str | None = Field(None, description="Source System contains")


def to_query(flt: CustomerAccountFilter) -> dict[str, str]:
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
    if flt.customer_id is not None:
        params["customerId"] = str(flt.customer_id)
    if flt.account_number:
        params["accountNumber"] = flt.account_number.strip()
    if flt.billing_cycle:
        params["billingCycle"] = ",".join(v.value for v in flt.billing_cycle)
    if flt.currency:
        params["currency"] = flt.currency.strip()
    if flt.credit_limit_min is not None:
        params["creditLimitMin"] = str(flt.credit_limit_min)
    if flt.credit_limit_max is not None:
        params["creditLimitMax"] = str(flt.credit_limit_max)
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
    if flt.source_system:
        params["sourceSystem"] = flt.source_system.strip()
    return params
