"""Search filter for Antenna and its translation to upstream query parameters."""
from __future__ import annotations

from datetime import date, datetime
from decimal import Decimal
from uuid import UUID

from pydantic import Field

from itta_core.models import ApiModel, LifecycleStatus
from itta_api.domains.radio.antenna.schemas import *  # noqa: F401,F403


class AntennaFilter(ApiModel):
    """Query-string filter for Antenna search."""

    q: str | None = Field(None, description="Free text over code, name and description")
    status: list[LifecycleStatus] | None = None
    code: str | None = Field(None, description="Code contains")
    name: str | None = Field(None, description="Name contains")
    external_ref: str | None = Field(None, description="External Ref contains")
    sector_id: UUID | None = None
    model: str | None = Field(None, description="Model contains")
    height_m_min: Decimal | None = None
    height_m_max: Decimal | None = None
    azimuth_deg_min: Decimal | None = None
    azimuth_deg_max: Decimal | None = None
    tilt_deg_min: Decimal | None = None
    tilt_deg_max: Decimal | None = None
    gain_dbi_min: Decimal | None = None
    gain_dbi_max: Decimal | None = None
    source_system: str | None = Field(None, description="Source System contains")
    region: str | None = Field(None, description="Region contains")
    owner: str | None = Field(None, description="Owner contains")
    criticality: list[AntennaCriticality] | None = None
    cost_center: str | None = Field(None, description="Cost Center contains")


def to_query(flt: AntennaFilter) -> dict[str, str]:
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
    if flt.sector_id is not None:
        params["sectorId"] = str(flt.sector_id)
    if flt.model:
        params["model"] = flt.model.strip()
    if flt.height_m_min is not None:
        params["heightMMin"] = str(flt.height_m_min)
    if flt.height_m_max is not None:
        params["heightMMax"] = str(flt.height_m_max)
    if flt.azimuth_deg_min is not None:
        params["azimuthDegMin"] = str(flt.azimuth_deg_min)
    if flt.azimuth_deg_max is not None:
        params["azimuthDegMax"] = str(flt.azimuth_deg_max)
    if flt.tilt_deg_min is not None:
        params["tiltDegMin"] = str(flt.tilt_deg_min)
    if flt.tilt_deg_max is not None:
        params["tiltDegMax"] = str(flt.tilt_deg_max)
    if flt.gain_dbi_min is not None:
        params["gainDbiMin"] = str(flt.gain_dbi_min)
    if flt.gain_dbi_max is not None:
        params["gainDbiMax"] = str(flt.gain_dbi_max)
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
