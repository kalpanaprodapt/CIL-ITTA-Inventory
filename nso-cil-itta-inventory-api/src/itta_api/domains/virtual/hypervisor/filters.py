"""Search filter for Hypervisor and its translation to upstream query parameters."""
from __future__ import annotations

from datetime import date, datetime
from decimal import Decimal
from uuid import UUID

from pydantic import Field

from itta_core.models import ApiModel, LifecycleStatus
from itta_api.domains.virtual.hypervisor.schemas import *  # noqa: F401,F403


class HypervisorFilter(ApiModel):
    """Query-string filter for Hypervisor search."""

    q: str | None = Field(None, description="Free text over code, name and description")
    status: list[LifecycleStatus] | None = None
    code: str | None = Field(None, description="Code contains")
    name: str | None = Field(None, description="Name contains")
    external_ref: str | None = Field(None, description="External Ref contains")
    host_device_id: UUID | None = None
    hypervisor_type: list[HypervisorHypervisorType] | None = None
    hv_version: str | None = Field(None, description="Hv Version contains")
    vcpu_capacity_min: int | None = None
    vcpu_capacity_max: int | None = None
    memory_gb_capacity_min: int | None = None
    memory_gb_capacity_max: int | None = None
    region: str | None = Field(None, description="Region contains")
    owner: str | None = Field(None, description="Owner contains")
    criticality: list[HypervisorCriticality] | None = None
    cost_center: str | None = Field(None, description="Cost Center contains")


def to_query(flt: HypervisorFilter) -> dict[str, str]:
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
    if flt.host_device_id is not None:
        params["hostDeviceId"] = str(flt.host_device_id)
    if flt.hypervisor_type:
        params["hypervisorType"] = ",".join(v.value for v in flt.hypervisor_type)
    if flt.hv_version:
        params["hvVersion"] = flt.hv_version.strip()
    if flt.vcpu_capacity_min is not None:
        params["vcpuCapacityMin"] = str(flt.vcpu_capacity_min)
    if flt.vcpu_capacity_max is not None:
        params["vcpuCapacityMax"] = str(flt.vcpu_capacity_max)
    if flt.memory_gb_capacity_min is not None:
        params["memoryGbCapacityMin"] = str(flt.memory_gb_capacity_min)
    if flt.memory_gb_capacity_max is not None:
        params["memoryGbCapacityMax"] = str(flt.memory_gb_capacity_max)
    if flt.region:
        params["region"] = flt.region.strip()
    if flt.owner:
        params["owner"] = flt.owner.strip()
    if flt.criticality:
        params["criticality"] = ",".join(v.value for v in flt.criticality)
    if flt.cost_center:
        params["costCenter"] = flt.cost_center.strip()
    return params
