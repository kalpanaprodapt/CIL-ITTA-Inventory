"""Search filter for Contract and its translation to upstream query parameters."""
from __future__ import annotations

from datetime import date, datetime
from decimal import Decimal
from uuid import UUID

from pydantic import Field

from itta_core.models import ApiModel, LifecycleStatus
from itta_api.domains.customer.contract.schemas import *  # noqa: F401,F403


class ContractFilter(ApiModel):
    """Query-string filter for Contract search."""

    q: str | None = Field(None, description="Free text over code, name and description")
    status: list[LifecycleStatus] | None = None
    code: str | None = Field(None, description="Code contains")
    name: str | None = Field(None, description="Name contains")
    external_ref: str | None = Field(None, description="External Ref contains")
    customer_id: UUID | None = None
    contract_number: str | None = Field(None, description="Contract Number contains")
    start_date_from: date | None = None
    start_date_to: date | None = None
    end_date_from: date | None = None
    end_date_to: date | None = None
    term_months_min: int | None = None
    term_months_max: int | None = None
    auto_renew: bool | None = None
    source_system: str | None = Field(None, description="Source System contains")
    region: str | None = Field(None, description="Region contains")
    owner: str | None = Field(None, description="Owner contains")
    criticality: list[ContractCriticality] | None = None
    cost_center: str | None = Field(None, description="Cost Center contains")


def to_query(flt: ContractFilter) -> dict[str, str]:
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
    if flt.contract_number:
        params["contractNumber"] = flt.contract_number.strip()
    if flt.start_date_from is not None:
        params["startDateFrom"] = flt.start_date_from.isoformat()
    if flt.start_date_to is not None:
        params["startDateTo"] = flt.start_date_to.isoformat()
    if flt.end_date_from is not None:
        params["endDateFrom"] = flt.end_date_from.isoformat()
    if flt.end_date_to is not None:
        params["endDateTo"] = flt.end_date_to.isoformat()
    if flt.term_months_min is not None:
        params["termMonthsMin"] = str(flt.term_months_min)
    if flt.term_months_max is not None:
        params["termMonthsMax"] = str(flt.term_months_max)
    if flt.auto_renew is not None:
        params["autoRenew"] = "true" if flt.auto_renew else "false"
    if flt.source_system:
        params["sourceSystem"] = flt.source_system.strip()
    if flt.region:
        params["region"] = flt.region.strip()
    if flt.owner:
        params["owner"] = flt.owner.strip()
    if flt.criticality:
        params["criticality"] = ",".join(v.value for v in flt.criticality)
    if flt.cost_center:
        params["costCenter"] = flt.cost_center.strip()
    return params
