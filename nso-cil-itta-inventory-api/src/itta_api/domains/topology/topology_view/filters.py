"""Search filter for Topology View and its translation to upstream query parameters."""
from __future__ import annotations

from datetime import date, datetime
from decimal import Decimal
from uuid import UUID

from pydantic import Field

from itta_core.models import ApiModel, LifecycleStatus
from itta_api.domains.topology.topology_view.schemas import *  # noqa: F401,F403


class TopologyViewFilter(ApiModel):
    """Query-string filter for Topology View search."""

    q: str | None = Field(None, description="Free text over code, name and description")
    status: list[LifecycleStatus] | None = None
    code: str | None = Field(None, description="Code contains")
    name: str | None = Field(None, description="Name contains")
    external_ref: str | None = Field(None, description="External Ref contains")
    view_name: str | None = Field(None, description="View Name contains")
    view_type: list[TopologyViewViewType] | None = None
    scope_filter: str | None = Field(None, description="Scope Filter contains")
    refreshed_at_from: datetime | None = None
    refreshed_at_to: datetime | None = None
    effective_to_from: date | None = None
    effective_to_to: date | None = None
    labels: str | None = Field(None, description="Labels contains")
    lifecycle_stage: list[TopologyViewLifecycleStage] | None = None
    source_system: str | None = Field(None, description="Source System contains")
    region: str | None = Field(None, description="Region contains")


def to_query(flt: TopologyViewFilter) -> dict[str, str]:
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
    if flt.view_name:
        params["viewName"] = flt.view_name.strip()
    if flt.view_type:
        params["viewType"] = ",".join(v.value for v in flt.view_type)
    if flt.scope_filter:
        params["scopeFilter"] = flt.scope_filter.strip()
    if flt.refreshed_at_from is not None:
        params["refreshedAtFrom"] = flt.refreshed_at_from.isoformat()
    if flt.refreshed_at_to is not None:
        params["refreshedAtTo"] = flt.refreshed_at_to.isoformat()
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
