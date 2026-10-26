"""Search filter for Chassis and its translation to upstream query parameters."""
from __future__ import annotations

from datetime import date, datetime
from decimal import Decimal
from uuid import UUID

from pydantic import Field

from itta_core.models import ApiModel, LifecycleStatus
from itta_api.domains.device.chassis.schemas import *  # noqa: F401,F403


class ChassisFilter(ApiModel):
    """Query-string filter for Chassis search."""

    q: str | None = Field(None, description="Free text over code, name and description")
    status: list[LifecycleStatus] | None = None
    code: str | None = Field(None, description="Code contains")
    name: str | None = Field(None, description="Name contains")
    external_ref: str | None = Field(None, description="External Ref contains")
    device_id: UUID | None = None
    slot_count_min: int | None = None
    slot_count_max: int | None = None
    serial_number: str | None = Field(None, description="Serial Number contains")
    backplane_gbps_min: Decimal | None = None
    backplane_gbps_max: Decimal | None = None
    redundancy_mode: str | None = Field(None, description="Redundancy Mode contains")
    lifecycle_stage: list[ChassisLifecycleStage] | None = None
    source_system: str | None = Field(None, description="Source System contains")
    region: str | None = Field(None, description="Region contains")
    owner: str | None = Field(None, description="Owner contains")
    criticality: list[ChassisCriticality] | None = None


def to_query(flt: ChassisFilter) -> dict[str, str]:
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
    if flt.device_id is not None:
        params["deviceId"] = str(flt.device_id)
    if flt.slot_count_min is not None:
        params["slotCountMin"] = str(flt.slot_count_min)
    if flt.slot_count_max is not None:
        params["slotCountMax"] = str(flt.slot_count_max)
    if flt.serial_number:
        params["serialNumber"] = flt.serial_number.strip()
    if flt.backplane_gbps_min is not None:
        params["backplaneGbpsMin"] = str(flt.backplane_gbps_min)
    if flt.backplane_gbps_max is not None:
        params["backplaneGbpsMax"] = str(flt.backplane_gbps_max)
    if flt.redundancy_mode:
        params["redundancyMode"] = flt.redundancy_mode.strip()
    if flt.lifecycle_stage:
        params["lifecycleStage"] = ",".join(v.value for v in flt.lifecycle_stage)
    if flt.source_system:
        params["sourceSystem"] = flt.source_system.strip()
    if flt.region:
        params["region"] = flt.region.strip()
    if flt.owner:
        params["owner"] = flt.owner.strip()
    if flt.criticality:
        params["criticality"] = ",".join(v.value for v in flt.criticality)
    return params
