"""Search filter for Link and its translation to upstream query parameters."""
from __future__ import annotations

from datetime import date, datetime
from decimal import Decimal
from uuid import UUID

from pydantic import Field

from itta_core.models import ApiModel, LifecycleStatus
from itta_api.domains.topology.link.schemas import *  # noqa: F401,F403


class LinkFilter(ApiModel):
    """Query-string filter for Link search."""

    q: str | None = Field(None, description="Free text over code, name and description")
    status: list[LifecycleStatus] | None = None
    code: str | None = Field(None, description="Code contains")
    name: str | None = Field(None, description="Name contains")
    external_ref: str | None = Field(None, description="External Ref contains")
    a_interface_id: UUID | None = None
    z_interface_id: UUID | None = None
    link_type: list[LinkLinkType] | None = None
    speed_mbps_min: int | None = None
    speed_mbps_max: int | None = None
    discovered_by: str | None = Field(None, description="Discovered By contains")
    region: str | None = Field(None, description="Region contains")
    owner: str | None = Field(None, description="Owner contains")
    criticality: list[LinkCriticality] | None = None
    cost_center: str | None = Field(None, description="Cost Center contains")


def to_query(flt: LinkFilter) -> dict[str, str]:
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
    if flt.a_interface_id is not None:
        params["aInterfaceId"] = str(flt.a_interface_id)
    if flt.z_interface_id is not None:
        params["zInterfaceId"] = str(flt.z_interface_id)
    if flt.link_type:
        params["linkType"] = ",".join(v.value for v in flt.link_type)
    if flt.speed_mbps_min is not None:
        params["speedMbpsMin"] = str(flt.speed_mbps_min)
    if flt.speed_mbps_max is not None:
        params["speedMbpsMax"] = str(flt.speed_mbps_max)
    if flt.discovered_by:
        params["discoveredBy"] = flt.discovered_by.strip()
    if flt.region:
        params["region"] = flt.region.strip()
    if flt.owner:
        params["owner"] = flt.owner.strip()
    if flt.criticality:
        params["criticality"] = ",".join(v.value for v in flt.criticality)
    if flt.cost_center:
        params["costCenter"] = flt.cost_center.strip()
    return params
