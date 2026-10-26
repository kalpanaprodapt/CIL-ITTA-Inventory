"""Search filter for Network Device and its translation to upstream query parameters."""
from __future__ import annotations

from datetime import date, datetime
from decimal import Decimal
from uuid import UUID

from pydantic import Field

from itta_core.models import ApiModel, LifecycleStatus
from itta_api.domains.device.network_device.schemas import *  # noqa: F401,F403


class NetworkDeviceFilter(ApiModel):
    """Query-string filter for Network Device search."""

    q: str | None = Field(None, description="Free text over code, name and description")
    status: list[LifecycleStatus] | None = None
    code: str | None = Field(None, description="Code contains")
    name: str | None = Field(None, description="Name contains")
    external_ref: str | None = Field(None, description="External Ref contains")
    hostname: str | None = Field(None, description="Hostname contains")
    device_role: list[NetworkDeviceDeviceRole] | None = None
    mgmt_ip: str | None = Field(None, description="Mgmt Ip contains")
    serial_number: str | None = Field(None, description="Serial Number contains")
    model_id: UUID | None = None
    rack_unit_id: UUID | None = None
    effective_to_from: date | None = None
    effective_to_to: date | None = None
    labels: str | None = Field(None, description="Labels contains")
    lifecycle_stage: list[NetworkDeviceLifecycleStage] | None = None
    source_system: str | None = Field(None, description="Source System contains")
    region: str | None = Field(None, description="Region contains")


def to_query(flt: NetworkDeviceFilter) -> dict[str, str]:
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
    if flt.hostname:
        params["hostname"] = flt.hostname.strip()
    if flt.device_role:
        params["deviceRole"] = ",".join(v.value for v in flt.device_role)
    if flt.mgmt_ip:
        params["mgmtIp"] = flt.mgmt_ip.strip()
    if flt.serial_number:
        params["serialNumber"] = flt.serial_number.strip()
    if flt.model_id is not None:
        params["modelId"] = str(flt.model_id)
    if flt.rack_unit_id is not None:
        params["rackUnitId"] = str(flt.rack_unit_id)
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
    if flt.region:
        params["region"] = flt.region.strip()
    return params
