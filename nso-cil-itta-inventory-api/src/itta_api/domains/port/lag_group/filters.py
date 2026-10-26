"""Search filter for Lag Group and its translation to upstream query parameters."""
from __future__ import annotations

from datetime import date, datetime
from decimal import Decimal
from uuid import UUID

from pydantic import Field

from itta_core.models import ApiModel, LifecycleStatus
from itta_api.domains.port.lag_group.schemas import *  # noqa: F401,F403


class LagGroupFilter(ApiModel):
    """Query-string filter for Lag Group search."""

    q: str | None = Field(None, description="Free text over code, name and description")
    status: list[LifecycleStatus] | None = None
    code: str | None = Field(None, description="Code contains")
    name: str | None = Field(None, description="Name contains")
    external_ref: str | None = Field(None, description="External Ref contains")
    device_id: UUID | None = None
    lag_name: str | None = Field(None, description="Lag Name contains")
    member_count_min: int | None = None
    member_count_max: int | None = None
    lacp_mode: list[LagGroupLacpMode] | None = None
    min_links_min: int | None = None
    min_links_max: int | None = None
    effective_from_from: date | None = None
    effective_from_to: date | None = None
    effective_to_from: date | None = None
    effective_to_to: date | None = None
    labels: str | None = Field(None, description="Labels contains")
    lifecycle_stage: list[LagGroupLifecycleStage] | None = None
    source_system: str | None = Field(None, description="Source System contains")


def to_query(flt: LagGroupFilter) -> dict[str, str]:
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
    if flt.lag_name:
        params["lagName"] = flt.lag_name.strip()
    if flt.member_count_min is not None:
        params["memberCountMin"] = str(flt.member_count_min)
    if flt.member_count_max is not None:
        params["memberCountMax"] = str(flt.member_count_max)
    if flt.lacp_mode:
        params["lacpMode"] = ",".join(v.value for v in flt.lacp_mode)
    if flt.min_links_min is not None:
        params["minLinksMin"] = str(flt.min_links_min)
    if flt.min_links_max is not None:
        params["minLinksMax"] = str(flt.min_links_max)
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
