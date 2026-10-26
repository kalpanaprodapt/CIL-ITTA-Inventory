"""Search filter for Circuit Order and its translation to upstream query parameters."""
from __future__ import annotations

from datetime import date, datetime
from decimal import Decimal
from uuid import UUID

from pydantic import Field

from itta_core.models import ApiModel, LifecycleStatus
from itta_api.domains.circuit.circuit_order.schemas import *  # noqa: F401,F403


class CircuitOrderFilter(ApiModel):
    """Query-string filter for Circuit Order search."""

    q: str | None = Field(None, description="Free text over code, name and description")
    status: list[LifecycleStatus] | None = None
    code: str | None = Field(None, description="Code contains")
    name: str | None = Field(None, description="Name contains")
    external_ref: str | None = Field(None, description="External Ref contains")
    circuit_id: UUID | None = None
    order_number: str | None = Field(None, description="Order Number contains")
    ordered_on_from: date | None = None
    ordered_on_to: date | None = None
    requested_date_from: date | None = None
    requested_date_to: date | None = None
    committed_date_from: date | None = None
    committed_date_to: date | None = None
    order_status: list[CircuitOrderOrderStatus] | None = None
    effective_from_from: date | None = None
    effective_from_to: date | None = None
    effective_to_from: date | None = None
    effective_to_to: date | None = None
    labels: str | None = Field(None, description="Labels contains")
    lifecycle_stage: list[CircuitOrderLifecycleStage] | None = None
    source_system: str | None = Field(None, description="Source System contains")


def to_query(flt: CircuitOrderFilter) -> dict[str, str]:
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
    if flt.circuit_id is not None:
        params["circuitId"] = str(flt.circuit_id)
    if flt.order_number:
        params["orderNumber"] = flt.order_number.strip()
    if flt.ordered_on_from is not None:
        params["orderedOnFrom"] = flt.ordered_on_from.isoformat()
    if flt.ordered_on_to is not None:
        params["orderedOnTo"] = flt.ordered_on_to.isoformat()
    if flt.requested_date_from is not None:
        params["requestedDateFrom"] = flt.requested_date_from.isoformat()
    if flt.requested_date_to is not None:
        params["requestedDateTo"] = flt.requested_date_to.isoformat()
    if flt.committed_date_from is not None:
        params["committedDateFrom"] = flt.committed_date_from.isoformat()
    if flt.committed_date_to is not None:
        params["committedDateTo"] = flt.committed_date_to.isoformat()
    if flt.order_status:
        params["orderStatus"] = ",".join(v.value for v in flt.order_status)
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
