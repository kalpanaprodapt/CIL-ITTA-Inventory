"""Search filter for Mpls Lsp and its translation to upstream query parameters."""
from __future__ import annotations

from datetime import date, datetime
from decimal import Decimal
from uuid import UUID

from pydantic import Field

from itta_core.models import ApiModel, LifecycleStatus
from itta_api.domains.mpls.mpls_lsp.schemas import *  # noqa: F401,F403


class MplsLspFilter(ApiModel):
    """Query-string filter for Mpls Lsp search."""

    q: str | None = Field(None, description="Free text over code, name and description")
    status: list[LifecycleStatus] | None = None
    code: str | None = Field(None, description="Code contains")
    name: str | None = Field(None, description="Name contains")
    external_ref: str | None = Field(None, description="External Ref contains")
    lsp_name: str | None = Field(None, description="Lsp Name contains")
    head_end_id: UUID | None = None
    tail_end_id: UUID | None = None
    bandwidth_mbps_min: int | None = None
    bandwidth_mbps_max: int | None = None
    setup_priority_min: int | None = None
    setup_priority_max: int | None = None
    path_type: list[MplsLspPathType] | None = None
    source_system: str | None = Field(None, description="Source System contains")
    region: str | None = Field(None, description="Region contains")
    owner: str | None = Field(None, description="Owner contains")
    criticality: list[MplsLspCriticality] | None = None
    cost_center: str | None = Field(None, description="Cost Center contains")


def to_query(flt: MplsLspFilter) -> dict[str, str]:
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
    if flt.lsp_name:
        params["lspName"] = flt.lsp_name.strip()
    if flt.head_end_id is not None:
        params["headEndId"] = str(flt.head_end_id)
    if flt.tail_end_id is not None:
        params["tailEndId"] = str(flt.tail_end_id)
    if flt.bandwidth_mbps_min is not None:
        params["bandwidthMbpsMin"] = str(flt.bandwidth_mbps_min)
    if flt.bandwidth_mbps_max is not None:
        params["bandwidthMbpsMax"] = str(flt.bandwidth_mbps_max)
    if flt.setup_priority_min is not None:
        params["setupPriorityMin"] = str(flt.setup_priority_min)
    if flt.setup_priority_max is not None:
        params["setupPriorityMax"] = str(flt.setup_priority_max)
    if flt.path_type:
        params["pathType"] = ",".join(v.value for v in flt.path_type)
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
