"""Search filter for Fiber Strand and its translation to upstream query parameters."""
from __future__ import annotations

from datetime import date, datetime
from decimal import Decimal
from uuid import UUID

from pydantic import Field

from itta_core.models import ApiModel, LifecycleStatus
from itta_api.domains.cabling.fiber_strand.schemas import *  # noqa: F401,F403


class FiberStrandFilter(ApiModel):
    """Query-string filter for Fiber Strand search."""

    q: str | None = Field(None, description="Free text over code, name and description")
    status: list[LifecycleStatus] | None = None
    code: str | None = Field(None, description="Code contains")
    name: str | None = Field(None, description="Name contains")
    external_ref: str | None = Field(None, description="External Ref contains")
    cable_id: UUID | None = None
    strand_number_min: int | None = None
    strand_number_max: int | None = None
    buffer_tube: str | None = Field(None, description="Buffer Tube contains")
    tube_color: str | None = Field(None, description="Tube Color contains")
    attenuation_db_min: Decimal | None = None
    attenuation_db_max: Decimal | None = None
    in_use: bool | None = None
    region: str | None = Field(None, description="Region contains")
    owner: str | None = Field(None, description="Owner contains")
    criticality: list[FiberStrandCriticality] | None = None
    cost_center: str | None = Field(None, description="Cost Center contains")


def to_query(flt: FiberStrandFilter) -> dict[str, str]:
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
    if flt.cable_id is not None:
        params["cableId"] = str(flt.cable_id)
    if flt.strand_number_min is not None:
        params["strandNumberMin"] = str(flt.strand_number_min)
    if flt.strand_number_max is not None:
        params["strandNumberMax"] = str(flt.strand_number_max)
    if flt.buffer_tube:
        params["bufferTube"] = flt.buffer_tube.strip()
    if flt.tube_color:
        params["tubeColor"] = flt.tube_color.strip()
    if flt.attenuation_db_min is not None:
        params["attenuationDbMin"] = str(flt.attenuation_db_min)
    if flt.attenuation_db_max is not None:
        params["attenuationDbMax"] = str(flt.attenuation_db_max)
    if flt.in_use is not None:
        params["inUse"] = "true" if flt.in_use else "false"
    if flt.region:
        params["region"] = flt.region.strip()
    if flt.owner:
        params["owner"] = flt.owner.strip()
    if flt.criticality:
        params["criticality"] = ",".join(v.value for v in flt.criticality)
    if flt.cost_center:
        params["costCenter"] = flt.cost_center.strip()
    return params
