"""Search filter for Interface and its translation to upstream query parameters."""
from __future__ import annotations

from datetime import date, datetime
from decimal import Decimal
from uuid import UUID

from pydantic import Field

from itta_core.models import ApiModel, LifecycleStatus
from itta_api.domains.port.interface.schemas import *  # noqa: F401,F403


class InterfaceFilter(ApiModel):
    """Query-string filter for Interface search."""

    q: str | None = Field(None, description="Free text over code, name and description")
    status: list[LifecycleStatus] | None = None
    code: str | None = Field(None, description="Code contains")
    name: str | None = Field(None, description="Name contains")
    external_ref: str | None = Field(None, description="External Ref contains")
    port_id: UUID | None = None
    if_name: str | None = Field(None, description="If Name contains")
    if_index_min: int | None = None
    if_index_max: int | None = None
    mac_address: str | None = Field(None, description="Mac Address contains")
    mtu_min: int | None = None
    mtu_max: int | None = None
    oper_state: list[InterfaceOperState] | None = None
    owner: str | None = Field(None, description="Owner contains")
    criticality: list[InterfaceCriticality] | None = None
    cost_center: str | None = Field(None, description="Cost Center contains")
    effective_from_from: date | None = None
    effective_from_to: date | None = None


def to_query(flt: InterfaceFilter) -> dict[str, str]:
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
    if flt.port_id is not None:
        params["portId"] = str(flt.port_id)
    if flt.if_name:
        params["ifName"] = flt.if_name.strip()
    if flt.if_index_min is not None:
        params["ifIndexMin"] = str(flt.if_index_min)
    if flt.if_index_max is not None:
        params["ifIndexMax"] = str(flt.if_index_max)
    if flt.mac_address:
        params["macAddress"] = flt.mac_address.strip()
    if flt.mtu_min is not None:
        params["mtuMin"] = str(flt.mtu_min)
    if flt.mtu_max is not None:
        params["mtuMax"] = str(flt.mtu_max)
    if flt.oper_state:
        params["operState"] = ",".join(v.value for v in flt.oper_state)
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
