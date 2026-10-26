"""Search filter for Route Target and its translation to upstream query parameters."""
from __future__ import annotations

from datetime import date, datetime
from decimal import Decimal
from uuid import UUID

from pydantic import Field

from itta_core.models import ApiModel, LifecycleStatus
from itta_api.domains.switching.route_target.schemas import *  # noqa: F401,F403


class RouteTargetFilter(ApiModel):
    """Query-string filter for Route Target search."""

    q: str | None = Field(None, description="Free text over code, name and description")
    status: list[LifecycleStatus] | None = None
    code: str | None = Field(None, description="Code contains")
    name: str | None = Field(None, description="Name contains")
    external_ref: str | None = Field(None, description="External Ref contains")
    asn_min: int | None = None
    asn_max: int | None = None
    assigned_number_min: int | None = None
    assigned_number_max: int | None = None
    rt_type: list[RouteTargetRtType] | None = None
    vrf_id: UUID | None = None
    lifecycle_stage: list[RouteTargetLifecycleStage] | None = None
    source_system: str | None = Field(None, description="Source System contains")
    region: str | None = Field(None, description="Region contains")
    owner: str | None = Field(None, description="Owner contains")
    criticality: list[RouteTargetCriticality] | None = None


def to_query(flt: RouteTargetFilter) -> dict[str, str]:
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
    if flt.asn_min is not None:
        params["asnMin"] = str(flt.asn_min)
    if flt.asn_max is not None:
        params["asnMax"] = str(flt.asn_max)
    if flt.assigned_number_min is not None:
        params["assignedNumberMin"] = str(flt.assigned_number_min)
    if flt.assigned_number_max is not None:
        params["assignedNumberMax"] = str(flt.assigned_number_max)
    if flt.rt_type:
        params["rtType"] = ",".join(v.value for v in flt.rt_type)
    if flt.vrf_id is not None:
        params["vrfId"] = str(flt.vrf_id)
    if flt.lifecycle_stage:
        params["lifecycleStage"] = ",".join(v.value for v in flt.lifecycle_stage)
    if flt.source_system:
        params["sourceSystem"] = flt.source_system.strip()
    if flt.region:
        params["region"] = flt.region.strip()
    if flt.owner:
        params["owner"] = flt.owner.strip()
    if flt.criticality:
        params["criticality"] = ",".join(v.value for v in flt.criticality)
    return params
