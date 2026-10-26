"""Search filter for Vlan Pool and its translation to upstream query parameters."""
from __future__ import annotations

from datetime import date, datetime
from decimal import Decimal
from uuid import UUID

from pydantic import Field

from itta_core.models import ApiModel, LifecycleStatus
from itta_api.domains.switching.vlan_pool.schemas import *  # noqa: F401,F403


class VlanPoolFilter(ApiModel):
    """Query-string filter for Vlan Pool search."""

    q: str | None = Field(None, description="Free text over code, name and description")
    status: list[LifecycleStatus] | None = None
    code: str | None = Field(None, description="Code contains")
    name: str | None = Field(None, description="Name contains")
    external_ref: str | None = Field(None, description="External Ref contains")
    pool_name: str | None = Field(None, description="Pool Name contains")
    range_start_min: int | None = None
    range_start_max: int | None = None
    range_end_min: int | None = None
    range_end_max: int | None = None
    allocated_min: int | None = None
    allocated_max: int | None = None
    scope: str | None = Field(None, description="Scope contains")
    effective_to_from: date | None = None
    effective_to_to: date | None = None
    labels: str | None = Field(None, description="Labels contains")
    lifecycle_stage: list[VlanPoolLifecycleStage] | None = None
    source_system: str | None = Field(None, description="Source System contains")
    region: str | None = Field(None, description="Region contains")


def to_query(flt: VlanPoolFilter) -> dict[str, str]:
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
    if flt.pool_name:
        params["poolName"] = flt.pool_name.strip()
    if flt.range_start_min is not None:
        params["rangeStartMin"] = str(flt.range_start_min)
    if flt.range_start_max is not None:
        params["rangeStartMax"] = str(flt.range_start_max)
    if flt.range_end_min is not None:
        params["rangeEndMin"] = str(flt.range_end_min)
    if flt.range_end_max is not None:
        params["rangeEndMax"] = str(flt.range_end_max)
    if flt.allocated_min is not None:
        params["allocatedMin"] = str(flt.allocated_min)
    if flt.allocated_max is not None:
        params["allocatedMax"] = str(flt.allocated_max)
    if flt.scope:
        params["scope"] = flt.scope.strip()
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
    if flt.region:
        params["region"] = flt.region.strip()
    return params
