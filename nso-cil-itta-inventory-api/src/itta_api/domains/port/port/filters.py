"""Search filter for Port and its translation to upstream query parameters."""
from __future__ import annotations

from datetime import date, datetime
from decimal import Decimal
from uuid import UUID

from pydantic import Field

from itta_core.models import ApiModel, LifecycleStatus
from itta_api.domains.port.port.schemas import *  # noqa: F401,F403


class PortFilter(ApiModel):
    """Query-string filter for Port search."""

    q: str | None = Field(None, description="Free text over code, name and description")
    status: list[LifecycleStatus] | None = None
    code: str | None = Field(None, description="Code contains")
    name: str | None = Field(None, description="Name contains")
    external_ref: str | None = Field(None, description="External Ref contains")
    device_id: UUID | None = None
    card_id: UUID | None = None
    port_number: str | None = Field(None, description="Port Number contains")
    speed_mbps_min: int | None = None
    speed_mbps_max: int | None = None
    media_type: list[PortMediaType] | None = None
    admin_state: list[PortAdminState] | None = None
    source_system: str | None = Field(None, description="Source System contains")
    region: str | None = Field(None, description="Region contains")
    owner: str | None = Field(None, description="Owner contains")
    criticality: list[PortCriticality] | None = None
    cost_center: str | None = Field(None, description="Cost Center contains")


def to_query(flt: PortFilter) -> dict[str, str]:
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
    if flt.device_id is not None:
        params["deviceId"] = str(flt.device_id)
    if flt.card_id is not None:
        params["cardId"] = str(flt.card_id)
    if flt.port_number:
        params["portNumber"] = flt.port_number.strip()
    if flt.speed_mbps_min is not None:
        params["speedMbpsMin"] = str(flt.speed_mbps_min)
    if flt.speed_mbps_max is not None:
        params["speedMbpsMax"] = str(flt.speed_mbps_max)
    if flt.media_type:
        params["mediaType"] = ",".join(v.value for v in flt.media_type)
    if flt.admin_state:
        params["adminState"] = ",".join(v.value for v in flt.admin_state)
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
