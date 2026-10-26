"""Search filter for Reservation and its translation to upstream query parameters."""
from __future__ import annotations

from datetime import date, datetime
from decimal import Decimal
from uuid import UUID

from pydantic import Field

from itta_core.models import ApiModel, LifecycleStatus
from itta_api.domains.capacity.reservation.schemas import *  # noqa: F401,F403


class ReservationFilter(ApiModel):
    """Query-string filter for Reservation search."""

    q: str | None = Field(None, description="Free text over code, name and description")
    status: list[LifecycleStatus] | None = None
    code: str | None = Field(None, description="Code contains")
    name: str | None = Field(None, description="Name contains")
    external_ref: str | None = Field(None, description="External Ref contains")
    resource_ref: str | None = Field(None, description="Resource Ref contains")
    reserved_for: str | None = Field(None, description="Reserved For contains")
    quantity_min: Decimal | None = None
    quantity_max: Decimal | None = None
    start_date_from: date | None = None
    start_date_to: date | None = None
    end_date_from: date | None = None
    end_date_to: date | None = None
    effective_from_from: date | None = None
    effective_from_to: date | None = None
    effective_to_from: date | None = None
    effective_to_to: date | None = None
    labels: str | None = Field(None, description="Labels contains")
    lifecycle_stage: list[ReservationLifecycleStage] | None = None


def to_query(flt: ReservationFilter) -> dict[str, str]:
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
    if flt.resource_ref:
        params["resourceRef"] = flt.resource_ref.strip()
    if flt.reserved_for:
        params["reservedFor"] = flt.reserved_for.strip()
    if flt.quantity_min is not None:
        params["quantityMin"] = str(flt.quantity_min)
    if flt.quantity_max is not None:
        params["quantityMax"] = str(flt.quantity_max)
    if flt.start_date_from is not None:
        params["startDateFrom"] = flt.start_date_from.isoformat()
    if flt.start_date_to is not None:
        params["startDateTo"] = flt.start_date_to.isoformat()
    if flt.end_date_from is not None:
        params["endDateFrom"] = flt.end_date_from.isoformat()
    if flt.end_date_to is not None:
        params["endDateTo"] = flt.end_date_to.isoformat()
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
