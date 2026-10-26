"""Search filter for Bgp Session and its translation to upstream query parameters."""
from __future__ import annotations

from datetime import date, datetime
from decimal import Decimal
from uuid import UUID

from pydantic import Field

from itta_core.models import ApiModel, LifecycleStatus
from itta_api.domains.switching.bgp_session.schemas import *  # noqa: F401,F403


class BgpSessionFilter(ApiModel):
    """Query-string filter for Bgp Session search."""

    q: str | None = Field(None, description="Free text over code, name and description")
    status: list[LifecycleStatus] | None = None
    code: str | None = Field(None, description="Code contains")
    name: str | None = Field(None, description="Name contains")
    external_ref: str | None = Field(None, description="External Ref contains")
    local_device_id: UUID | None = None
    peer_address: str | None = Field(None, description="Peer Address contains")
    local_asn_min: int | None = None
    local_asn_max: int | None = None
    remote_asn_min: int | None = None
    remote_asn_max: int | None = None
    session_state: list[BgpSessionSessionState] | None = None
    prefix_limit_min: int | None = None
    prefix_limit_max: int | None = None
    region: str | None = Field(None, description="Region contains")
    owner: str | None = Field(None, description="Owner contains")
    criticality: list[BgpSessionCriticality] | None = None
    cost_center: str | None = Field(None, description="Cost Center contains")


def to_query(flt: BgpSessionFilter) -> dict[str, str]:
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
    if flt.local_device_id is not None:
        params["localDeviceId"] = str(flt.local_device_id)
    if flt.peer_address:
        params["peerAddress"] = flt.peer_address.strip()
    if flt.local_asn_min is not None:
        params["localAsnMin"] = str(flt.local_asn_min)
    if flt.local_asn_max is not None:
        params["localAsnMax"] = str(flt.local_asn_max)
    if flt.remote_asn_min is not None:
        params["remoteAsnMin"] = str(flt.remote_asn_min)
    if flt.remote_asn_max is not None:
        params["remoteAsnMax"] = str(flt.remote_asn_max)
    if flt.session_state:
        params["sessionState"] = ",".join(v.value for v in flt.session_state)
    if flt.prefix_limit_min is not None:
        params["prefixLimitMin"] = str(flt.prefix_limit_min)
    if flt.prefix_limit_max is not None:
        params["prefixLimitMax"] = str(flt.prefix_limit_max)
    if flt.region:
        params["region"] = flt.region.strip()
    if flt.owner:
        params["owner"] = flt.owner.strip()
    if flt.criticality:
        params["criticality"] = ",".join(v.value for v in flt.criticality)
    if flt.cost_center:
        params["costCenter"] = flt.cost_center.strip()
    return params
