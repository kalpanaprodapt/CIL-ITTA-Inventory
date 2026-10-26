"""Search filter for Vlan and its translation to upstream query parameters."""
from __future__ import annotations

from datetime import date, datetime
from decimal import Decimal
from uuid import UUID

from pydantic import Field

from itta_core.models import ApiModel, LifecycleStatus
from itta_api.domains.switching.vlan.schemas import *  # noqa: F401,F403


class VlanFilter(ApiModel):
    """Query-string filter for Vlan search."""

    q: str | None = Field(None, description="Free text over code, name and description")
    status: list[LifecycleStatus] | None = None
    code: str | None = Field(None, description="Code contains")
    name: str | None = Field(None, description="Name contains")
    external_ref: str | None = Field(None, description="External Ref contains")
    vlan_id_min: int | None = None
    vlan_id_max: int | None = None
    vlan_name: str | None = Field(None, description="Vlan Name contains")
    vlan_type: list[VlanVlanType] | None = None
    pool_id: UUID | None = None
    stp_mode: str | None = Field(None, description="Stp Mode contains")
    effective_from_from: date | None = None
    effective_from_to: date | None = None
    effective_to_from: date | None = None
    effective_to_to: date | None = None
    labels: str | None = Field(None, description="Labels contains")
    lifecycle_stage: list[VlanLifecycleStage] | None = None


def to_query(flt: VlanFilter) -> dict[str, str]:
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
    if flt.vlan_id_min is not None:
        params["vlanIdMin"] = str(flt.vlan_id_min)
    if flt.vlan_id_max is not None:
        params["vlanIdMax"] = str(flt.vlan_id_max)
    if flt.vlan_name:
        params["vlanName"] = flt.vlan_name.strip()
    if flt.vlan_type:
        params["vlanType"] = ",".join(v.value for v in flt.vlan_type)
    if flt.pool_id is not None:
        params["poolId"] = str(flt.pool_id)
    if flt.stp_mode:
        params["stpMode"] = flt.stp_mode.strip()
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
    return params
