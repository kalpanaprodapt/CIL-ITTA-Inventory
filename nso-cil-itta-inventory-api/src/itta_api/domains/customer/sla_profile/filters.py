"""Search filter for Sla Profile and its translation to upstream query parameters."""
from __future__ import annotations

from datetime import date, datetime
from decimal import Decimal
from uuid import UUID

from pydantic import Field

from itta_core.models import ApiModel, LifecycleStatus
from itta_api.domains.customer.sla_profile.schemas import *  # noqa: F401,F403


class SlaProfileFilter(ApiModel):
    """Query-string filter for Sla Profile search."""

    q: str | None = Field(None, description="Free text over code, name and description")
    status: list[LifecycleStatus] | None = None
    code: str | None = Field(None, description="Code contains")
    name: str | None = Field(None, description="Name contains")
    external_ref: str | None = Field(None, description="External Ref contains")
    profile_name: str | None = Field(None, description="Profile Name contains")
    availability_pct_min: Decimal | None = None
    availability_pct_max: Decimal | None = None
    mttr_minutes_min: int | None = None
    mttr_minutes_max: int | None = None
    latency_ms_min: Decimal | None = None
    latency_ms_max: Decimal | None = None
    jitter_ms_min: Decimal | None = None
    jitter_ms_max: Decimal | None = None
    owner: str | None = Field(None, description="Owner contains")
    criticality: list[SlaProfileCriticality] | None = None
    cost_center: str | None = Field(None, description="Cost Center contains")
    effective_from_from: date | None = None
    effective_from_to: date | None = None


def to_query(flt: SlaProfileFilter) -> dict[str, str]:
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
    if flt.profile_name:
        params["profileName"] = flt.profile_name.strip()
    if flt.availability_pct_min is not None:
        params["availabilityPctMin"] = str(flt.availability_pct_min)
    if flt.availability_pct_max is not None:
        params["availabilityPctMax"] = str(flt.availability_pct_max)
    if flt.mttr_minutes_min is not None:
        params["mttrMinutesMin"] = str(flt.mttr_minutes_min)
    if flt.mttr_minutes_max is not None:
        params["mttrMinutesMax"] = str(flt.mttr_minutes_max)
    if flt.latency_ms_min is not None:
        params["latencyMsMin"] = str(flt.latency_ms_min)
    if flt.latency_ms_max is not None:
        params["latencyMsMax"] = str(flt.latency_ms_max)
    if flt.jitter_ms_min is not None:
        params["jitterMsMin"] = str(flt.jitter_ms_min)
    if flt.jitter_ms_max is not None:
        params["jitterMsMax"] = str(flt.jitter_ms_max)
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
