"""Search filter for Maintenance Window and its translation to upstream query parameters."""
from __future__ import annotations

from datetime import date, datetime
from decimal import Decimal
from uuid import UUID

from pydantic import Field

from itta_core.models import ApiModel, LifecycleStatus
from itta_api.domains.change.maintenance_window.schemas import *  # noqa: F401,F403


class MaintenanceWindowFilter(ApiModel):
    """Query-string filter for Maintenance Window search."""

    q: str | None = Field(None, description="Free text over code, name and description")
    status: list[LifecycleStatus] | None = None
    code: str | None = Field(None, description="Code contains")
    name: str | None = Field(None, description="Name contains")
    external_ref: str | None = Field(None, description="External Ref contains")
    window_name: str | None = Field(None, description="Window Name contains")
    starts_at_from: datetime | None = None
    starts_at_to: datetime | None = None
    ends_at_from: datetime | None = None
    ends_at_to: datetime | None = None
    scope: str | None = Field(None, description="Scope contains")
    impact_level: list[MaintenanceWindowImpactLevel] | None = None
    effective_to_from: date | None = None
    effective_to_to: date | None = None
    labels: str | None = Field(None, description="Labels contains")
    lifecycle_stage: list[MaintenanceWindowLifecycleStage] | None = None
    source_system: str | None = Field(None, description="Source System contains")
    region: str | None = Field(None, description="Region contains")


def to_query(flt: MaintenanceWindowFilter) -> dict[str, str]:
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
    if flt.window_name:
        params["windowName"] = flt.window_name.strip()
    if flt.starts_at_from is not None:
        params["startsAtFrom"] = flt.starts_at_from.isoformat()
    if flt.starts_at_to is not None:
        params["startsAtTo"] = flt.starts_at_to.isoformat()
    if flt.ends_at_from is not None:
        params["endsAtFrom"] = flt.ends_at_from.isoformat()
    if flt.ends_at_to is not None:
        params["endsAtTo"] = flt.ends_at_to.isoformat()
    if flt.scope:
        params["scope"] = flt.scope.strip()
    if flt.impact_level:
        params["impactLevel"] = ",".join(v.value for v in flt.impact_level)
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
