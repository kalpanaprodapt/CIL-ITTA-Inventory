"""Search filter for Vrf Instance and its translation to upstream query parameters."""
from __future__ import annotations

from datetime import date, datetime
from decimal import Decimal
from uuid import UUID

from pydantic import Field

from itta_core.models import ApiModel, LifecycleStatus
from itta_api.domains.ipam.vrf_instance.schemas import *  # noqa: F401,F403


class VrfInstanceFilter(ApiModel):
    """Query-string filter for Vrf Instance search."""

    q: str | None = Field(None, description="Free text over code, name and description")
    status: list[LifecycleStatus] | None = None
    code: str | None = Field(None, description="Code contains")
    name: str | None = Field(None, description="Name contains")
    external_ref: str | None = Field(None, description="External Ref contains")
    vrf_name: str | None = Field(None, description="Vrf Name contains")
    route_distinguisher: str | None = Field(None, description="Route Distinguisher contains")
    import_targets: str | None = Field(None, description="Import Targets contains")
    export_targets: str | None = Field(None, description="Export Targets contains")
    device_id: UUID | None = None
    labels: str | None = Field(None, description="Labels contains")
    lifecycle_stage: list[VrfInstanceLifecycleStage] | None = None
    source_system: str | None = Field(None, description="Source System contains")
    region: str | None = Field(None, description="Region contains")
    owner: str | None = Field(None, description="Owner contains")


def to_query(flt: VrfInstanceFilter) -> dict[str, str]:
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
    if flt.vrf_name:
        params["vrfName"] = flt.vrf_name.strip()
    if flt.route_distinguisher:
        params["routeDistinguisher"] = flt.route_distinguisher.strip()
    if flt.import_targets:
        params["importTargets"] = flt.import_targets.strip()
    if flt.export_targets:
        params["exportTargets"] = flt.export_targets.strip()
    if flt.device_id is not None:
        params["deviceId"] = str(flt.device_id)
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
