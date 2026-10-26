"""Search filter for Customer Contact and its translation to upstream query parameters."""
from __future__ import annotations

from datetime import date, datetime
from decimal import Decimal
from uuid import UUID

from pydantic import Field

from itta_core.models import ApiModel, LifecycleStatus
from itta_api.domains.customer.customer_contact.schemas import *  # noqa: F401,F403


class CustomerContactFilter(ApiModel):
    """Query-string filter for Customer Contact search."""

    q: str | None = Field(None, description="Free text over code, name and description")
    status: list[LifecycleStatus] | None = None
    code: str | None = Field(None, description="Code contains")
    name: str | None = Field(None, description="Name contains")
    external_ref: str | None = Field(None, description="External Ref contains")
    customer_id: UUID | None = None
    full_name: str | None = Field(None, description="Full Name contains")
    email: str | None = Field(None, description="Email contains")
    phone: str | None = Field(None, description="Phone contains")
    contact_role: list[CustomerContactContactRole] | None = None
    labels: str | None = Field(None, description="Labels contains")
    lifecycle_stage: list[CustomerContactLifecycleStage] | None = None
    source_system: str | None = Field(None, description="Source System contains")
    region: str | None = Field(None, description="Region contains")
    owner: str | None = Field(None, description="Owner contains")


def to_query(flt: CustomerContactFilter) -> dict[str, str]:
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
    if flt.customer_id is not None:
        params["customerId"] = str(flt.customer_id)
    if flt.full_name:
        params["fullName"] = flt.full_name.strip()
    if flt.email:
        params["email"] = flt.email.strip()
    if flt.phone:
        params["phone"] = flt.phone.strip()
    if flt.contact_role:
        params["contactRole"] = ",".join(v.value for v in flt.contact_role)
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
