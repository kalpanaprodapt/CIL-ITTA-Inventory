"""Search filter for Patch Panel and its translation to upstream query parameters."""
from __future__ import annotations

from datetime import date, datetime
from decimal import Decimal
from uuid import UUID

from pydantic import Field

from itta_core.models import ApiModel, LifecycleStatus
from itta_api.domains.cabling.patch_panel.schemas import *  # noqa: F401,F403


class PatchPanelFilter(ApiModel):
    """Query-string filter for Patch Panel search."""

    q: str | None = Field(None, description="Free text over code, name and description")
    status: list[LifecycleStatus] | None = None
    code: str | None = Field(None, description="Code contains")
    name: str | None = Field(None, description="Name contains")
    external_ref: str | None = Field(None, description="External Ref contains")
    room_id: UUID | None = None
    rack_id: UUID | None = None
    panel_type: list[PatchPanelPanelType] | None = None
    port_count_min: int | None = None
    port_count_max: int | None = None
    label: str | None = Field(None, description="Label contains")
    effective_from_from: date | None = None
    effective_from_to: date | None = None
    effective_to_from: date | None = None
    effective_to_to: date | None = None
    labels: str | None = Field(None, description="Labels contains")
    lifecycle_stage: list[PatchPanelLifecycleStage] | None = None


def to_query(flt: PatchPanelFilter) -> dict[str, str]:
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
    if flt.rack_id is not None:
        params["rackId"] = str(flt.rack_id)
    if flt.panel_type:
        params["panelType"] = ",".join(v.value for v in flt.panel_type)
    if flt.port_count_min is not None:
        params["portCountMin"] = str(flt.port_count_min)
    if flt.port_count_max is not None:
        params["portCountMax"] = str(flt.port_count_max)
    if flt.label:
        params["label"] = flt.label.strip()
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
