"""Search filter for Vpn Service and its translation to upstream query parameters."""
from __future__ import annotations

from datetime import date, datetime
from decimal import Decimal
from uuid import UUID

from pydantic import Field

from itta_core.models import ApiModel, LifecycleStatus
from itta_api.domains.mpls.vpn_service.schemas import *  # noqa: F401,F403


class VpnServiceFilter(ApiModel):
    """Query-string filter for Vpn Service search."""

    q: str | None = Field(None, description="Free text over code, name and description")
    status: list[LifecycleStatus] | None = None
    code: str | None = Field(None, description="Code contains")
    name: str | None = Field(None, description="Name contains")
    external_ref: str | None = Field(None, description="External Ref contains")
    vpn_name: str | None = Field(None, description="Vpn Name contains")
    vpn_type: list[VpnServiceVpnType] | None = None
    vrf_id: UUID | None = None
    customer_id: UUID | None = None
    topology: list[VpnServiceTopology] | None = None
    owner: str | None = Field(None, description="Owner contains")
    criticality: list[VpnServiceCriticality] | None = None
    cost_center: str | None = Field(None, description="Cost Center contains")
    effective_from_from: date | None = None
    effective_from_to: date | None = None


def to_query(flt: VpnServiceFilter) -> dict[str, str]:
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
    if flt.vpn_name:
        params["vpnName"] = flt.vpn_name.strip()
    if flt.vpn_type:
        params["vpnType"] = ",".join(v.value for v in flt.vpn_type)
    if flt.vrf_id is not None:
        params["vrfId"] = str(flt.vrf_id)
    if flt.customer_id is not None:
        params["customerId"] = str(flt.customer_id)
    if flt.topology:
        params["topology"] = ",".join(v.value for v in flt.topology)
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
