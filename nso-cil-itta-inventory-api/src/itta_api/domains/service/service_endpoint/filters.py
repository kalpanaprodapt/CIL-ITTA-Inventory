"""Search filter for Service Endpoint and its translation to upstream query parameters."""
from __future__ import annotations

from datetime import date, datetime
from decimal import Decimal
from uuid import UUID

from pydantic import Field

from itta_core.models import ApiModel, LifecycleStatus
from itta_api.domains.service.service_endpoint.schemas import *  # noqa: F401,F403


class ServiceEndpointFilter(ApiModel):
    """Query-string filter for Service Endpoint search."""

    q: str | None = Field(None, description="Free text over code, name and description")
    status: list[LifecycleStatus] | None = None
    code: str | None = Field(None, description="Code contains")
    name: str | None = Field(None, description="Name contains")
    external_ref: str | None = Field(None, description="External Ref contains")
    service_id: UUID | None = None
    site_id: UUID | None = None
    port_id: UUID | None = None
    endpoint_role: list[ServiceEndpointEndpointRole] | None = None
    handoff: str | None = Field(None, description="Handoff contains")
    effective_from_from: date | None = None
    effective_from_to: date | None = None
    effective_to_from: date | None = None
    effective_to_to: date | None = None
    labels: str | None = Field(None, description="Labels contains")
    lifecycle_stage: list[ServiceEndpointLifecycleStage] | None = None


def to_query(flt: ServiceEndpointFilter) -> dict[str, str]:
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
    if flt.service_id is not None:
        params["serviceId"] = str(flt.service_id)
    if flt.site_id is not None:
        params["siteId"] = str(flt.site_id)
    if flt.port_id is not None:
        params["portId"] = str(flt.port_id)
    if flt.endpoint_role:
        params["endpointRole"] = ",".join(v.value for v in flt.endpoint_role)
    if flt.handoff:
        params["handoff"] = flt.handoff.strip()
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
