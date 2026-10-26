"""Search filter for Line Card and its translation to upstream query parameters."""
from __future__ import annotations

from datetime import date, datetime
from decimal import Decimal
from uuid import UUID

from pydantic import Field

from itta_core.models import ApiModel, LifecycleStatus
from itta_api.domains.device.line_card.schemas import *  # noqa: F401,F403


class LineCardFilter(ApiModel):
    """Query-string filter for Line Card search."""

    q: str | None = Field(None, description="Free text over code, name and description")
    status: list[LifecycleStatus] | None = None
    code: str | None = Field(None, description="Code contains")
    name: str | None = Field(None, description="Name contains")
    external_ref: str | None = Field(None, description="External Ref contains")
    chassis_id: UUID | None = None
    slot_number_min: int | None = None
    slot_number_max: int | None = None
    card_type: str | None = Field(None, description="Card Type contains")
    port_count_min: int | None = None
    port_count_max: int | None = None
    serial_number: str | None = Field(None, description="Serial Number contains")
    firmware: str | None = Field(None, description="Firmware contains")
    region: str | None = Field(None, description="Region contains")
    owner: str | None = Field(None, description="Owner contains")
    criticality: list[LineCardCriticality] | None = None
    cost_center: str | None = Field(None, description="Cost Center contains")


def to_query(flt: LineCardFilter) -> dict[str, str]:
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
    if flt.card_type:
        params["cardType"] = flt.card_type.strip()
    if flt.port_count_min is not None:
        params["portCountMin"] = str(flt.port_count_min)
    if flt.port_count_max is not None:
        params["portCountMax"] = str(flt.port_count_max)
    if flt.serial_number:
        params["serialNumber"] = flt.serial_number.strip()
    if flt.firmware:
        params["firmware"] = flt.firmware.strip()
    if flt.region:
        params["region"] = flt.region.strip()
    if flt.owner:
        params["owner"] = flt.owner.strip()
    if flt.criticality:
        params["criticality"] = ",".join(v.value for v in flt.criticality)
    if flt.cost_center:
        params["costCenter"] = flt.cost_center.strip()
    return params
