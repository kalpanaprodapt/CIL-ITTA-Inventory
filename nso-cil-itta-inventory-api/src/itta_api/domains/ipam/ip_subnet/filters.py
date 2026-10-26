"""Search filter for Ip Subnet and its translation to upstream query parameters."""
from __future__ import annotations

from datetime import date, datetime
from decimal import Decimal
from uuid import UUID

from pydantic import Field

from itta_core.models import ApiModel, LifecycleStatus
from itta_api.domains.ipam.ip_subnet.schemas import *  # noqa: F401,F403


class IpSubnetFilter(ApiModel):
    """Query-string filter for Ip Subnet search."""

    q: str | None = Field(None, description="Free text over code, name and description")
    status: list[LifecycleStatus] | None = None
    code: str | None = Field(None, description="Code contains")
    name: str | None = Field(None, description="Name contains")
    external_ref: str | None = Field(None, description="External Ref contains")
    cidr: str | None = Field(None, description="Cidr contains")
    ip_version: list[IpSubnetIpVersion] | None = None
    vrf_id: UUID | None = None
    gateway: str | None = Field(None, description="Gateway contains")
    vlan_id: UUID | None = None
    utilization_pct_min: Decimal | None = None
    utilization_pct_max: Decimal | None = None
    owner: str | None = Field(None, description="Owner contains")
    criticality: list[IpSubnetCriticality] | None = None
    cost_center: str | None = Field(None, description="Cost Center contains")
    effective_from_from: date | None = None
    effective_from_to: date | None = None


def to_query(flt: IpSubnetFilter) -> dict[str, str]:
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
    if flt.cidr:
        params["cidr"] = flt.cidr.strip()
    if flt.ip_version:
        params["ipVersion"] = ",".join(v.value for v in flt.ip_version)
    if flt.vrf_id is not None:
        params["vrfId"] = str(flt.vrf_id)
    if flt.gateway:
        params["gateway"] = flt.gateway.strip()
    if flt.vlan_id is not None:
        params["vlanId"] = str(flt.vlan_id)
    if flt.utilization_pct_min is not None:
        params["utilizationPctMin"] = str(flt.utilization_pct_min)
    if flt.utilization_pct_max is not None:
        params["utilizationPctMax"] = str(flt.utilization_pct_max)
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
