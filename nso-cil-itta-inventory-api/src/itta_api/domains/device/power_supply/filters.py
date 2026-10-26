"""Search filter for Power Supply and its translation to upstream query parameters."""
from __future__ import annotations

from datetime import date, datetime
from decimal import Decimal
from uuid import UUID

from pydantic import Field

from itta_core.models import ApiModel, LifecycleStatus
from itta_api.domains.device.power_supply.schemas import *  # noqa: F401,F403


class PowerSupplyFilter(ApiModel):
    """Query-string filter for Power Supply search."""

    q: str | None = Field(None, description="Free text over code, name and description")
    status: list[LifecycleStatus] | None = None
    code: str | None = Field(None, description="Code contains")
    name: str | None = Field(None, description="Name contains")
    external_ref: str | None = Field(None, description="External Ref contains")
    chassis_id: UUID | None = None
    slot_number_min: int | None = None
    slot_number_max: int | None = None
    wattage_min: int | None = None
    wattage_max: int | None = None
    feed_id: UUID | None = None
    serial_number: str | None = Field(None, description="Serial Number contains")
    efficiency_min: Decimal | None = None
    efficiency_max: Decimal | None = None
    criticality: list[PowerSupplyCriticality] | None = None
    cost_center: str | None = Field(None, description="Cost Center contains")
    effective_from_from: date | None = None
    effective_from_to: date | None = None
    effective_to_from: date | None = None
    effective_to_to: date | None = None


def to_query(flt: PowerSupplyFilter) -> dict[str, str]:
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
    if flt.chassis_id is not None:
        params["chassisId"] = str(flt.chassis_id)
    if flt.slot_number_min is not None:
        params["slotNumberMin"] = str(flt.slot_number_min)
    if flt.slot_number_max is not None:
        params["slotNumberMax"] = str(flt.slot_number_max)
    if flt.wattage_min is not None:
        params["wattageMin"] = str(flt.wattage_min)
    if flt.wattage_max is not None:
        params["wattageMax"] = str(flt.wattage_max)
    if flt.feed_id is not None:
        params["feedId"] = str(flt.feed_id)
    if flt.serial_number:
        params["serialNumber"] = flt.serial_number.strip()
    if flt.efficiency_min is not None:
        params["efficiencyMin"] = str(flt.efficiency_min)
    if flt.efficiency_max is not None:
        params["efficiencyMax"] = str(flt.efficiency_max)
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
