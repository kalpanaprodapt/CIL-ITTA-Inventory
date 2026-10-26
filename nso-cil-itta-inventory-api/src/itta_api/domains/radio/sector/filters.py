"""Search filter for Sector and its translation to upstream query parameters."""
from __future__ import annotations

from datetime import date, datetime
from decimal import Decimal
from uuid import UUID

from pydantic import Field

from itta_core.models import ApiModel, LifecycleStatus
from itta_api.domains.radio.sector.schemas import *  # noqa: F401,F403


class SectorFilter(ApiModel):
    """Query-string filter for Sector search."""

    q: str | None = Field(None, description="Free text over code, name and description")
    status: list[LifecycleStatus] | None = None
    code: str | None = Field(None, description="Code contains")
    name: str | None = Field(None, description="Name contains")
    external_ref: str | None = Field(None, description="External Ref contains")
    base_station_id: UUID | None = None
    sector_number_min: int | None = None
    sector_number_max: int | None = None
    pci_min: int | None = None
    pci_max: int | None = None
    frequency_mhz_min: int | None = None
    frequency_mhz_max: int | None = None
    bandwidth_mhz_min: int | None = None
    bandwidth_mhz_max: int | None = None
    owner: str | None = Field(None, description="Owner contains")
    criticality: list[SectorCriticality] | None = None
    cost_center: str | None = Field(None, description="Cost Center contains")
    effective_from_from: date | None = None
    effective_from_to: date | None = None


def to_query(flt: SectorFilter) -> dict[str, str]:
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
    if flt.base_station_id is not None:
        params["baseStationId"] = str(flt.base_station_id)
    if flt.sector_number_min is not None:
        params["sectorNumberMin"] = str(flt.sector_number_min)
    if flt.sector_number_max is not None:
        params["sectorNumberMax"] = str(flt.sector_number_max)
    if flt.pci_min is not None:
        params["pciMin"] = str(flt.pci_min)
    if flt.pci_max is not None:
        params["pciMax"] = str(flt.pci_max)
    if flt.frequency_mhz_min is not None:
        params["frequencyMhzMin"] = str(flt.frequency_mhz_min)
    if flt.frequency_mhz_max is not None:
        params["frequencyMhzMax"] = str(flt.frequency_mhz_max)
    if flt.bandwidth_mhz_min is not None:
        params["bandwidthMhzMin"] = str(flt.bandwidth_mhz_min)
    if flt.bandwidth_mhz_max is not None:
        params["bandwidthMhzMax"] = str(flt.bandwidth_mhz_max)
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
