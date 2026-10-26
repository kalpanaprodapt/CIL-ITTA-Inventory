"""Search filter for Vnf and its translation to upstream query parameters."""
from __future__ import annotations

from datetime import date, datetime
from decimal import Decimal
from uuid import UUID

from pydantic import Field

from itta_core.models import ApiModel, LifecycleStatus
from itta_api.domains.virtual.vnf.schemas import *  # noqa: F401,F403


class VnfFilter(ApiModel):
    """Query-string filter for Vnf search."""

    q: str | None = Field(None, description="Free text over code, name and description")
    status: list[LifecycleStatus] | None = None
    code: str | None = Field(None, description="Code contains")
    name: str | None = Field(None, description="Name contains")
    external_ref: str | None = Field(None, description="External Ref contains")
    vnf_name: str | None = Field(None, description="Vnf Name contains")
    vnf_type: str | None = Field(None, description="Vnf Type contains")
    flavor: str | None = Field(None, description="Flavor contains")
    vcpu_min: int | None = None
    vcpu_max: int | None = None
    memory_gb_min: int | None = None
    memory_gb_max: int | None = None
    instance_count_min: int | None = None
    instance_count_max: int | None = None
    lifecycle_stage: list[VnfLifecycleStage] | None = None
    source_system: str | None = Field(None, description="Source System contains")
    region: str | None = Field(None, description="Region contains")
    owner: str | None = Field(None, description="Owner contains")
    criticality: list[VnfCriticality] | None = None


def to_query(flt: VnfFilter) -> dict[str, str]:
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
    if flt.vnf_name:
        params["vnfName"] = flt.vnf_name.strip()
    if flt.vnf_type:
        params["vnfType"] = flt.vnf_type.strip()
    if flt.flavor:
        params["flavor"] = flt.flavor.strip()
    if flt.vcpu_min is not None:
        params["vcpuMin"] = str(flt.vcpu_min)
    if flt.vcpu_max is not None:
        params["vcpuMax"] = str(flt.vcpu_max)
    if flt.memory_gb_min is not None:
        params["memoryGbMin"] = str(flt.memory_gb_min)
    if flt.memory_gb_max is not None:
        params["memoryGbMax"] = str(flt.memory_gb_max)
    if flt.instance_count_min is not None:
        params["instanceCountMin"] = str(flt.instance_count_min)
    if flt.instance_count_max is not None:
        params["instanceCountMax"] = str(flt.instance_count_max)
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
