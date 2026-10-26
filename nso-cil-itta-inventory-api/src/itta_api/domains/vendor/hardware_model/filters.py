"""Search filter for Hardware Model and its translation to upstream query parameters."""
from __future__ import annotations

from datetime import date, datetime
from decimal import Decimal
from uuid import UUID

from pydantic import Field

from itta_core.models import ApiModel, LifecycleStatus
from itta_api.domains.vendor.hardware_model.schemas import *  # noqa: F401,F403


class HardwareModelFilter(ApiModel):
    """Query-string filter for Hardware Model search."""

    q: str | None = Field(None, description="Free text over code, name and description")
    status: list[LifecycleStatus] | None = None
    code: str | None = Field(None, description="Code contains")
    name: str | None = Field(None, description="Name contains")
    external_ref: str | None = Field(None, description="External Ref contains")
    vendor_id: UUID | None = None
    model_number: str | None = Field(None, description="Model Number contains")
    form_factor: str | None = Field(None, description="Form Factor contains")
    power_watts_min: int | None = None
    power_watts_max: int | None = None
    eol_date_from: date | None = None
    eol_date_to: date | None = None
    eos_date_from: date | None = None
    eos_date_to: date | None = None
    labels: str | None = Field(None, description="Labels contains")
    lifecycle_stage: list[HardwareModelLifecycleStage] | None = None
    source_system: str | None = Field(None, description="Source System contains")
    region: str | None = Field(None, description="Region contains")
    owner: str | None = Field(None, description="Owner contains")


def to_query(flt: HardwareModelFilter) -> dict[str, str]:
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
    if flt.vendor_id is not None:
        params["vendorId"] = str(flt.vendor_id)
    if flt.model_number:
        params["modelNumber"] = flt.model_number.strip()
    if flt.form_factor:
        params["formFactor"] = flt.form_factor.strip()
    if flt.power_watts_min is not None:
        params["powerWattsMin"] = str(flt.power_watts_min)
    if flt.power_watts_max is not None:
        params["powerWattsMax"] = str(flt.power_watts_max)
    if flt.eol_date_from is not None:
        params["eolDateFrom"] = flt.eol_date_from.isoformat()
    if flt.eol_date_to is not None:
        params["eolDateTo"] = flt.eol_date_to.isoformat()
    if flt.eos_date_from is not None:
        params["eosDateFrom"] = flt.eos_date_from.isoformat()
    if flt.eos_date_to is not None:
        params["eosDateTo"] = flt.eos_date_to.isoformat()
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
