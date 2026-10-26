"""Search filter for Optical Channel and its translation to upstream query parameters."""
from __future__ import annotations

from datetime import date, datetime
from decimal import Decimal
from uuid import UUID

from pydantic import Field

from itta_core.models import ApiModel, LifecycleStatus
from itta_api.domains.optical.optical_channel.schemas import *  # noqa: F401,F403


class OpticalChannelFilter(ApiModel):
    """Query-string filter for Optical Channel search."""

    q: str | None = Field(None, description="Free text over code, name and description")
    status: list[LifecycleStatus] | None = None
    code: str | None = Field(None, description="Code contains")
    name: str | None = Field(None, description="Name contains")
    external_ref: str | None = Field(None, description="External Ref contains")
    wavelength_nm_min: Decimal | None = None
    wavelength_nm_max: Decimal | None = None
    channel_number_min: int | None = None
    channel_number_max: int | None = None
    grid_type: list[OpticalChannelGridType] | None = None
    bitrate_gbps_min: Decimal | None = None
    bitrate_gbps_max: Decimal | None = None
    system_id: UUID | None = None
    criticality: list[OpticalChannelCriticality] | None = None
    cost_center: str | None = Field(None, description="Cost Center contains")
    effective_from_from: date | None = None
    effective_from_to: date | None = None
    effective_to_from: date | None = None
    effective_to_to: date | None = None


def to_query(flt: OpticalChannelFilter) -> dict[str, str]:
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
    if flt.wavelength_nm_min is not None:
        params["wavelengthNmMin"] = str(flt.wavelength_nm_min)
    if flt.wavelength_nm_max is not None:
        params["wavelengthNmMax"] = str(flt.wavelength_nm_max)
    if flt.channel_number_min is not None:
        params["channelNumberMin"] = str(flt.channel_number_min)
    if flt.channel_number_max is not None:
        params["channelNumberMax"] = str(flt.channel_number_max)
    if flt.grid_type:
        params["gridType"] = ",".join(v.value for v in flt.grid_type)
    if flt.bitrate_gbps_min is not None:
        params["bitrateGbpsMin"] = str(flt.bitrate_gbps_min)
    if flt.bitrate_gbps_max is not None:
        params["bitrateGbpsMax"] = str(flt.bitrate_gbps_max)
    if flt.system_id is not None:
        params["systemId"] = str(flt.system_id)
    if flt.criticality:
        params["criticality"] = ",".join(v.value for v in flt.criticality)
    if flt.cost_center:
        params["costCenter"] = flt.cost_center.strip()
    if flt.effective_from_from is not None:
        params["effectiveFromFrom"] = flt.effective_from_from.isoformat()
    if flt.effective_from_to is not None:
        params["effectiveFromTo"] = flt.effective_from_to.isoformat()
    if flt.effective_to_from is not None:
        params["effectiveToFrom"] = flt.effective_to_from.isoformat()
    if flt.effective_to_to is not None:
        params["effectiveToTo"] = flt.effective_to_to.isoformat()
    return params
