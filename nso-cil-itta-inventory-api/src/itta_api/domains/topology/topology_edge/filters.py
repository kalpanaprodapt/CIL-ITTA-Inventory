"""Search filter for Topology Edge and its translation to upstream query parameters."""
from __future__ import annotations

from datetime import date, datetime
from decimal import Decimal
from uuid import UUID

from pydantic import Field

from itta_core.models import ApiModel, LifecycleStatus
from itta_api.domains.topology.topology_edge.schemas import *  # noqa: F401,F403


class TopologyEdgeFilter(ApiModel):
    """Query-string filter for Topology Edge search."""

    q: str | None = Field(None, description="Free text over code, name and description")
    status: list[LifecycleStatus] | None = None
    code: str | None = Field(None, description="Code contains")
    name: str | None = Field(None, description="Name contains")
    external_ref: str | None = Field(None, description="External Ref contains")
    view_id: UUID | None = None
    source_node_id: UUID | None = None
    target_node_id: UUID | None = None
    weight_min: Decimal | None = None
    weight_max: Decimal | None = None
    directed: bool | None = None
    effective_from_from: date | None = None
    effective_from_to: date | None = None
    effective_to_from: date | None = None
    effective_to_to: date | None = None
    labels: str | None = Field(None, description="Labels contains")
    lifecycle_stage: list[TopologyEdgeLifecycleStage] | None = None


def to_query(flt: TopologyEdgeFilter) -> dict[str, str]:
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
    if flt.view_id is not None:
        params["viewId"] = str(flt.view_id)
    if flt.source_node_id is not None:
        params["sourceNodeId"] = str(flt.source_node_id)
    if flt.target_node_id is not None:
        params["targetNodeId"] = str(flt.target_node_id)
    if flt.weight_min is not None:
        params["weightMin"] = str(flt.weight_min)
    if flt.weight_max is not None:
        params["weightMax"] = str(flt.weight_max)
    if flt.directed is not None:
        params["directed"] = "true" if flt.directed else "false"
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
