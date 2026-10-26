"""Search filter for Radio Unit and its translation to upstream query parameters."""
from __future__ import annotations

from datetime import date, datetime
from decimal import Decimal
from uuid import UUID

from pydantic import Field

from itta_core.models import ApiModel, LifecycleStatus
from itta_api.domains.radio.radio_unit.schemas import *  # noqa: F401,F403


class RadioUnitFilter(ApiModel):
    """Query-string filter for Radio Unit search."""

    q: str | None = Field(None, description="Free text over code, name and description")
    status: list[LifecycleStatus] | None = None
    code: str | None = Field(None, description="Code contains")
    name: str | None = Field(None, description="Name contains")
    external_ref: str | None = Field(None, description="External Ref contains")
    site_id: UUID | None = None
    unit_type: list[RadioUnitUnitType] | None = None
    band_mhz_min: int | None = None
    band_mhz_max: int | None = None
    tx_power_dbm_min: Decimal | None = None
    tx_power_dbm_max: Decimal | None = None
    serial_number: str | None = Field(None, description="Serial Number contains")
    effective_from_from: date | None = None
    effective_from_to: date | None = None
    effective_to_from: date | None = None
    effective_to_to: date | None = None
    labels: str | None = Field(None, description="Labels contains")
    lifecycle_stage: list[RadioUnitLifecycleStage] | None = None
    source_system: str | None = Field(None, description="Source System contains")


def to_query(flt: RadioUnitFilter) -> dict[str, str]:
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
    if flt.site_id is not None:
        params["siteId"] = str(flt.site_id)
    if flt.unit_type:
        params["unitType"] = ",".join(v.value for v in flt.unit_type)
    if flt.band_mhz_min is not None:
        params["bandMhzMin"] = str(flt.band_mhz_min)
    if flt.band_mhz_max is not None:
        params["bandMhzMax"] = str(flt.band_mhz_max)
    if flt.tx_power_dbm_min is not None:
        params["txPowerDbmMin"] = str(flt.tx_power_dbm_min)
    if flt.tx_power_dbm_max is not None:
        params["txPowerDbmMax"] = str(flt.tx_power_dbm_max)
    if flt.serial_number:
        params["serialNumber"] = flt.serial_number.strip()
    if flt.effective_from_from is not None:
        params["effectiveFromFrom"] = flt.effective_from_from.isoformat()
    if flt.effective_from_to is not None:
        params["effectiveFromTo"] = flt.effective_from_to.isoformat()
    if flt.effective_to_from is not None:
        params["effectiveToFrom"] = flt.effective_to_from.isoformat()
    if flt.effective_to_to is not None:
        params["effectiveToTo"] = flt.effective_to_to.isoformat()
    if flt.labels:
        params["labels"] = flt.labels.strip()
    if flt.lifecycle_stage:
        params["lifecycleStage"] = ",".join(v.value for v in flt.lifecycle_stage)
    if flt.source_system:
        params["sourceSystem"] = flt.source_system.strip()
    return params
