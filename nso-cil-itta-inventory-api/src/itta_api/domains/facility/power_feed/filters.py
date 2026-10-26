"""Search filter for Power Feed and its translation to upstream query parameters."""
from __future__ import annotations

from datetime import date, datetime
from decimal import Decimal
from uuid import UUID

from pydantic import Field

from itta_core.models import ApiModel, LifecycleStatus
from itta_api.domains.facility.power_feed.schemas import *  # noqa: F401,F403


class PowerFeedFilter(ApiModel):
    """Query-string filter for Power Feed search."""

    q: str | None = Field(None, description="Free text over code, name and description")
    status: list[LifecycleStatus] | None = None
    code: str | None = Field(None, description="Code contains")
    name: str | None = Field(None, description="Name contains")
    external_ref: str | None = Field(None, description="External Ref contains")
    room_id: UUID | None = None
    feed_type: list[PowerFeedFeedType] | None = None
    voltage_min: Decimal | None = None
    voltage_max: Decimal | None = None
    amperage_min: Decimal | None = None
    amperage_max: Decimal | None = None
    phase: str | None = Field(None, description="Phase contains")
    breaker_label: str | None = Field(None, description="Breaker Label contains")
    redundancy_group: str | None = Field(None, description="Redundancy Group contains")
    owner: str | None = Field(None, description="Owner contains")
    criticality: list[PowerFeedCriticality] | None = None
    cost_center: str | None = Field(None, description="Cost Center contains")
    effective_from_from: date | None = None
    effective_from_to: date | None = None


def to_query(flt: PowerFeedFilter) -> dict[str, str]:
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
    if flt.room_id is not None:
        params["roomId"] = str(flt.room_id)
    if flt.feed_type:
        params["feedType"] = ",".join(v.value for v in flt.feed_type)
    if flt.voltage_min is not None:
        params["voltageMin"] = str(flt.voltage_min)
    if flt.voltage_max is not None:
        params["voltageMax"] = str(flt.voltage_max)
    if flt.amperage_min is not None:
        params["amperageMin"] = str(flt.amperage_min)
    if flt.amperage_max is not None:
        params["amperageMax"] = str(flt.amperage_max)
    if flt.phase:
        params["phase"] = flt.phase.strip()
    if flt.breaker_label:
        params["breakerLabel"] = flt.breaker_label.strip()
    if flt.redundancy_group:
        params["redundancyGroup"] = flt.redundancy_group.strip()
    if flt.owner:
        params["owner"] = flt.owner.strip()
    if flt.criticality:
        params["criticality"] = ",".join(v.value for v in flt.criticality)
    if flt.cost_center:
        params["costCenter"] = flt.cost_center.strip()
    if flt.effective_from_from is not None:
        params["effectiveFromFrom"] = flt.effective_from_from.isoformat()
    if flt.effective_from_to is not None:
        params["effectiveFromTo"] = flt.effective_from_to.isoformat()
    return params
