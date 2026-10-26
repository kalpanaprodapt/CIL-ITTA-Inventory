"""Search filter for Ticket Link and its translation to upstream query parameters."""
from __future__ import annotations

from datetime import date, datetime
from decimal import Decimal
from uuid import UUID

from pydantic import Field

from itta_core.models import ApiModel, LifecycleStatus
from itta_api.domains.alarm.ticket_link.schemas import *  # noqa: F401,F403


class TicketLinkFilter(ApiModel):
    """Query-string filter for Ticket Link search."""

    q: str | None = Field(None, description="Free text over code, name and description")
    status: list[LifecycleStatus] | None = None
    code: str | None = Field(None, description="Code contains")
    name: str | None = Field(None, description="Name contains")
    external_ref: str | None = Field(None, description="External Ref contains")
    alarm_id: UUID | None = None
    ticket_system: list[TicketLinkTicketSystem] | None = None
    ticket_number: str | None = Field(None, description="Ticket Number contains")
    linked_at_from: datetime | None = None
    linked_at_to: datetime | None = None
    owner: str | None = Field(None, description="Owner contains")
    criticality: list[TicketLinkCriticality] | None = None
    cost_center: str | None = Field(None, description="Cost Center contains")
    effective_from_from: date | None = None
    effective_from_to: date | None = None


def to_query(flt: TicketLinkFilter) -> dict[str, str]:
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
    if flt.alarm_id is not None:
        params["alarmId"] = str(flt.alarm_id)
    if flt.ticket_system:
        params["ticketSystem"] = ",".join(v.value for v in flt.ticket_system)
    if flt.ticket_number:
        params["ticketNumber"] = flt.ticket_number.strip()
    if flt.linked_at_from is not None:
        params["linkedAtFrom"] = flt.linked_at_from.isoformat()
    if flt.linked_at_to is not None:
        params["linkedAtTo"] = flt.linked_at_to.isoformat()
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
