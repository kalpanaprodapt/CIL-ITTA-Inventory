"""Search filter for Ip Pool and its translation to upstream query parameters."""
from __future__ import annotations

from datetime import date, datetime
from decimal import Decimal
from uuid import UUID

from pydantic import Field

from itta_core.models import ApiModel, LifecycleStatus
from itta_api.domains.ipam.ip_pool.schemas import *  # noqa: F401,F403


class IpPoolFilter(ApiModel):
    """Query-string filter for Ip Pool search."""

    q: str | None = Field(None, description="Free text over code, name and description")
    status: list[LifecycleStatus] | None = None
    code: str | None = Field(None, description="Code contains")
    name: str | None = Field(None, description="Name contains")
    external_ref: str | None = Field(None, description="External Ref contains")
    subnet_id: UUID | None = None
    range_start: str | None = Field(None, description="Range Start contains")
    range_end: str | None = Field(None, description="Range End contains")
    pool_type: list[IpPoolPoolType] | None = None
    lease_seconds_min: int | None = None
    lease_seconds_max: int | None = None
    effective_from_from: date | None = None
    effective_from_to: date | None = None
    effective_to_from: date | None = None
    effective_to_to: date | None = None
    labels: str | None = Field(None, description="Labels contains")
    lifecycle_stage: list[IpPoolLifecycleStage] | None = None
    source_system: str | None = Field(None, description="Source System contains")


def to_query(flt: IpPoolFilter) -> dict[str, str]:
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
    if flt.subnet_id is not None:
        params["subnetId"] = str(flt.subnet_id)
    if flt.range_start:
        params["rangeStart"] = flt.range_start.strip()
    if flt.range_end:
        params["rangeEnd"] = flt.range_end.strip()
    if flt.pool_type:
        params["poolType"] = ",".join(v.value for v in flt.pool_type)
    if flt.lease_seconds_min is not None:
        params["leaseSecondsMin"] = str(flt.lease_seconds_min)
    if flt.lease_seconds_max is not None:
        params["leaseSecondsMax"] = str(flt.lease_seconds_max)
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
    if flt.source_system:
        params["sourceSystem"] = flt.source_system.strip()
    return params
