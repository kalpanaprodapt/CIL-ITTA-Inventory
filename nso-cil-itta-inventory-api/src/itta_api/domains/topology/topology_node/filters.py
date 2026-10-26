"""Search filter for Topology Node and its translation to upstream query parameters."""
from __future__ import annotations

from datetime import date, datetime
from decimal import Decimal
from uuid import UUID

from pydantic import Field

from itta_core.models import ApiModel, LifecycleStatus
from itta_api.domains.topology.topology_node.schemas import *  # noqa: F401,F403


class TopologyNodeFilter(ApiModel):
    """Query-string filter for Topology Node search."""

    q: str | None = Field(None, description="Free text over code, name and description")
    status: list[LifecycleStatus] | None = None
    code: str | None = Field(None, description="Code contains")
    name: str | None = Field(None, description="Name contains")
    external_ref: str | None = Field(None, description="External Ref contains")
    view_id: UUID | None = None
    entity_ref: str | None = Field(None, description="Entity Ref contains")
    node_type: str | None = Field(None, description="Node Type contains")
    x_pos_min: Decimal | None = None
    x_pos_max: Decimal | None = None
    y_pos_min: Decimal | None = None
    y_pos_max: Decimal | None = None
    layer_min: int | None = None
    layer_max: int | None = None
    criticality: list[TopologyNodeCriticality] | None = None
    cost_center: str | None = Field(None, description="Cost Center contains")
    effective_from_from: date | None = None
    effective_from_to: date | None = None
    effective_to_from: date | None = None
    effective_to_to: date | None = None


def to_query(flt: TopologyNodeFilter) -> dict[str, str]:
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
    if flt.entity_ref:
        params["entityRef"] = flt.entity_ref.strip()
    if flt.node_type:
        params["nodeType"] = flt.node_type.strip()
    if flt.x_pos_min is not None:
        params["xPosMin"] = str(flt.x_pos_min)
    if flt.x_pos_max is not None:
        params["xPosMax"] = str(flt.x_pos_max)
    if flt.y_pos_min is not None:
        params["yPosMin"] = str(flt.y_pos_min)
    if flt.y_pos_max is not None:
        params["yPosMax"] = str(flt.y_pos_max)
    if flt.layer_min is not None:
        params["layerMin"] = str(flt.layer_min)
    if flt.layer_max is not None:
        params["layerMax"] = str(flt.layer_max)
    if flt.criticality:
        params["criticality"] = ",".join(v.value for v in flt.criticality)
    if flt.cost_center:
        params["costCenter"] = flt.cost_center.strip()
    if flt.effective_from_from is not None:
        params["effectiveFromFrom"] = flt.effective_from_from.isoformat()
    if flt.effective_from_to is not None:
        params["effectiveFromTo"] = flt.effective_from_to.isoformat()
    if flt.effective_to_from is not None:
        params["effectiveToFrom"] = flt.effective_to_from.isoformat()
    if flt.effective_to_to is not None:
        params["effectiveToTo"] = flt.effective_to_to.isoformat()
    return params
