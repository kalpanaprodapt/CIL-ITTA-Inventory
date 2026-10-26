"""Search filter for Rack Unit and its translation to upstream query parameters."""
from __future__ import annotations

from datetime import date, datetime
from decimal import Decimal
from uuid import UUID

from pydantic import Field

from itta_core.models import ApiModel, LifecycleStatus
from itta_api.domains.facility.rack_unit.schemas import *  # noqa: F401,F403


class RackUnitFilter(ApiModel):
    """Query-string filter for Rack Unit search."""

    q: str | None = Field(None, description="Free text over code, name and description")
    status: list[LifecycleStatus] | None = None
    code: str | None = Field(None, description="Code contains")
    name: str | None = Field(None, description="Name contains")
    external_ref: str | None = Field(None, description="External Ref contains")
    rack_id: UUID | None = None
    unit_number_min: int | None = None
    unit_number_max: int | None = None
    occupied: bool | None = None
    mounted_device_id: UUID | None = None
    face: list[RackUnitFace] | None = None
    source_system: str | None = Field(None, description="Source System contains")
    region: str | None = Field(None, description="Region contains")
    owner: str | None = Field(None, description="Owner contains")
    criticality: list[RackUnitCriticality] | None = None
    cost_center: str | None = Field(None, description="Cost Center contains")


def to_query(flt: RackUnitFilter) -> dict[str, str]:
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
    if flt.rack_id is not None:
        params["rackId"] = str(flt.rack_id)
    if flt.unit_number_min is not None:
        params["unitNumberMin"] = str(flt.unit_number_min)
    if flt.unit_number_max is not None:
        params["unitNumberMax"] = str(flt.unit_number_max)
    if flt.occupied is not None:
        params["occupied"] = "true" if flt.occupied else "false"
    if flt.mounted_device_id is not None:
        params["mountedDeviceId"] = str(flt.mounted_device_id)
    if flt.face:
        params["face"] = ",".join(v.value for v in flt.face)
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
