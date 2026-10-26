"""Search filter for Circuit Provider and its translation to upstream query parameters."""
from __future__ import annotations

from datetime import date, datetime
from decimal import Decimal
from uuid import UUID

from pydantic import Field

from itta_core.models import ApiModel, LifecycleStatus
from itta_api.domains.circuit.circuit_provider.schemas import *  # noqa: F401,F403


class CircuitProviderFilter(ApiModel):
    """Query-string filter for Circuit Provider search."""

    q: str | None = Field(None, description="Free text over code, name and description")
    status: list[LifecycleStatus] | None = None
    code: str | None = Field(None, description="Code contains")
    name: str | None = Field(None, description="Name contains")
    external_ref: str | None = Field(None, description="External Ref contains")
    provider_name: str | None = Field(None, description="Provider Name contains")
    account_number: str | None = Field(None, description="Account Number contains")
    noc: str | None = Field(None, description="Noc contains")
    escalation_email: str | None = Field(None, description="Escalation Email contains")
    provider_type: list[CircuitProviderProviderType] | None = None
    owner: str | None = Field(None, description="Owner contains")
    criticality: list[CircuitProviderCriticality] | None = None
    cost_center: str | None = Field(None, description="Cost Center contains")
    effective_from_from: date | None = None
    effective_from_to: date | None = None


def to_query(flt: CircuitProviderFilter) -> dict[str, str]:
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
    if flt.provider_name:
        params["providerName"] = flt.provider_name.strip()
    if flt.account_number:
        params["accountNumber"] = flt.account_number.strip()
    if flt.noc:
        params["noc"] = flt.noc.strip()
    if flt.escalation_email:
        params["escalationEmail"] = flt.escalation_email.strip()
    if flt.provider_type:
        params["providerType"] = ",".join(v.value for v in flt.provider_type)
    if flt.owner:
        params["owner"] = flt.owner.strip()
    if flt.criticality:
        params["criticality"] = ",".join(v.value for v in flt.criticality)
    if flt.cost_center:
        params["costCenter"] = flt.cost_center.strip()
    if flt.effective_from_from is not None:
        params["effectiveFromFrom"] = flt.effective_from_from.isoformat()
    if flt.effective_from_to is not None:
        params["effectiveFromTo"] = flt.effective_from_to.isoformat()
    return params
