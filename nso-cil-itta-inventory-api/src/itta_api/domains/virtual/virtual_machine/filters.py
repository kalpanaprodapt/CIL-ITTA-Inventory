"""Search filter for Virtual Machine and its translation to upstream query parameters."""
from __future__ import annotations

from datetime import date, datetime
from decimal import Decimal
from uuid import UUID

from pydantic import Field

from itta_core.models import ApiModel, LifecycleStatus
from itta_api.domains.virtual.virtual_machine.schemas import *  # noqa: F401,F403


class VirtualMachineFilter(ApiModel):
    """Query-string filter for Virtual Machine search."""

    q: str | None = Field(None, description="Free text over code, name and description")
    status: list[LifecycleStatus] | None = None
    code: str | None = Field(None, description="Code contains")
    name: str | None = Field(None, description="Name contains")
    external_ref: str | None = Field(None, description="External Ref contains")
    hypervisor_id: UUID | None = None
    vm_name: str | None = Field(None, description="Vm Name contains")
    vcpu_min: int | None = None
    vcpu_max: int | None = None
    memory_gb_min: int | None = None
    memory_gb_max: int | None = None
    disk_gb_min: int | None = None
    disk_gb_max: int | None = None
    power_state: list[VirtualMachinePowerState] | None = None
    criticality: list[VirtualMachineCriticality] | None = None
    cost_center: str | None = Field(None, description="Cost Center contains")
    effective_from_from: date | None = None
    effective_from_to: date | None = None
    effective_to_from: date | None = None
    effective_to_to: date | None = None


def to_query(flt: VirtualMachineFilter) -> dict[str, str]:
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
    if flt.hypervisor_id is not None:
        params["hypervisorId"] = str(flt.hypervisor_id)
    if flt.vm_name:
        params["vmName"] = flt.vm_name.strip()
    if flt.vcpu_min is not None:
        params["vcpuMin"] = str(flt.vcpu_min)
    if flt.vcpu_max is not None:
        params["vcpuMax"] = str(flt.vcpu_max)
    if flt.memory_gb_min is not None:
        params["memoryGbMin"] = str(flt.memory_gb_min)
    if flt.memory_gb_max is not None:
        params["memoryGbMax"] = str(flt.memory_gb_max)
    if flt.disk_gb_min is not None:
        params["diskGbMin"] = str(flt.disk_gb_min)
    if flt.disk_gb_max is not None:
        params["diskGbMax"] = str(flt.disk_gb_max)
    if flt.power_state:
        params["powerState"] = ",".join(v.value for v in flt.power_state)
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
