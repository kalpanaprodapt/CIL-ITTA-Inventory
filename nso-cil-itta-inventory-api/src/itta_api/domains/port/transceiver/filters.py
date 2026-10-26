"""Search filter for Transceiver and its translation to upstream query parameters."""
from __future__ import annotations

from datetime import date, datetime
from decimal import Decimal
from uuid import UUID

from pydantic import Field

from itta_core.models import ApiModel, LifecycleStatus
from itta_api.domains.port.transceiver.schemas import *  # noqa: F401,F403


class TransceiverFilter(ApiModel):
    """Query-string filter for Transceiver search."""

    q: str | None = Field(None, description="Free text over code, name and description")
    status: list[LifecycleStatus] | None = None
    code: str | None = Field(None, description="Code contains")
    name: str | None = Field(None, description="Name contains")
    external_ref: str | None = Field(None, description="External Ref contains")
    port_id: UUID | None = None
    form_factor: list[TransceiverFormFactor] | None = None
    wavelength_nm_min: int | None = None
    wavelength_nm_max: int | None = None
    reach_km_min: Decimal | None = None
    reach_km_max: Decimal | None = None
    serial_number: str | None = Field(None, description="Serial Number contains")
    vendor_part: str | None = Field(None, description="Vendor Part contains")
    labels: str | None = Field(None, description="Labels contains")
    lifecycle_stage: list[TransceiverLifecycleStage] | None = None
    source_system: str | None = Field(None, description="Source System contains")
    region: str | None = Field(None, description="Region contains")
    owner: str | None = Field(None, description="Owner contains")


def to_query(flt: TransceiverFilter) -> dict[str, str]:
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
    if flt.form_factor:
        params["formFactor"] = ",".join(v.value for v in flt.form_factor)
    if flt.wavelength_nm_min is not None:
        params["wavelengthNmMin"] = str(flt.wavelength_nm_min)
    if flt.wavelength_nm_max is not None:
        params["wavelengthNmMax"] = str(flt.wavelength_nm_max)
    if flt.reach_km_min is not None:
        params["reachKmMin"] = str(flt.reach_km_min)
    if flt.reach_km_max is not None:
        params["reachKmMax"] = str(flt.reach_km_max)
    if flt.serial_number:
        params["serialNumber"] = flt.serial_number.strip()
    if flt.vendor_part:
        params["vendorPart"] = flt.vendor_part.strip()
    if flt.labels:
        params["labels"] = flt.labels.strip()
    if flt.lifecycle_stage:
        params["lifecycleStage"] = ",".join(v.value for v in flt.lifecycle_stage)
    if flt.source_system:
        params["sourceSystem"] = flt.source_system.strip()
    if flt.region:
        params["region"] = flt.region.strip()
    if flt.owner:
        params["owner"] = flt.owner.strip()
    return params
