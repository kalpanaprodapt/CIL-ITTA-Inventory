"""Search filter for Support Contract and its translation to upstream query parameters."""
from __future__ import annotations

from datetime import date, datetime
from decimal import Decimal
from uuid import UUID

from pydantic import Field

from itta_core.models import ApiModel, LifecycleStatus
from itta_api.domains.vendor.support_contract.schemas import *  # noqa: F401,F403


class SupportContractFilter(ApiModel):
    """Query-string filter for Support Contract search."""

    q: str | None = Field(None, description="Free text over code, name and description")
    status: list[LifecycleStatus] | None = None
    code: str | None = Field(None, description="Code contains")
    name: str | None = Field(None, description="Name contains")
    external_ref: str | None = Field(None, description="External Ref contains")
    vendor_id: UUID | None = None
    contract_number: str | None = Field(None, description="Contract Number contains")
    start_date_from: date | None = None
    start_date_to: date | None = None
    end_date_from: date | None = None
    end_date_to: date | None = None
    coverage_level: list[SupportContractCoverageLevel] | None = None
    annual_cost_min: Decimal | None = None
    annual_cost_max: Decimal | None = None
    owner: str | None = Field(None, description="Owner contains")
    criticality: list[SupportContractCriticality] | None = None
    cost_center: str | None = Field(None, description="Cost Center contains")
    effective_from_from: date | None = None
    effective_from_to: date | None = None


def to_query(flt: SupportContractFilter) -> dict[str, str]:
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
    if flt.vendor_id is not None:
        params["vendorId"] = str(flt.vendor_id)
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
    if flt.coverage_level:
        params["coverageLevel"] = ",".join(v.value for v in flt.coverage_level)
    if flt.annual_cost_min is not None:
        params["annualCostMin"] = str(flt.annual_cost_min)
    if flt.annual_cost_max is not None:
        params["annualCostMax"] = str(flt.annual_cost_max)
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
