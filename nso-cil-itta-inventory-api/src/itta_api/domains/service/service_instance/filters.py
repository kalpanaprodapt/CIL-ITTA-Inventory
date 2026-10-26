"""Search filter for Service Instance and its translation to upstream query parameters."""
from __future__ import annotations

from datetime import date, datetime
from decimal import Decimal
from uuid import UUID

from pydantic import Field

from itta_core.models import ApiModel, LifecycleStatus
from itta_api.domains.service.service_instance.schemas import *  # noqa: F401,F403


class ServiceInstanceFilter(ApiModel):
    """Query-string filter for Service Instance search."""

    q: str | None = Field(None, description="Free text over code, name and description")
    status: list[LifecycleStatus] | None = None
    code: str | None = Field(None, description="Code contains")
    name: str | None = Field(None, description="Name contains")
    external_ref: str | None = Field(None, description="External Ref contains")
    catalog_item_id: UUID | None = None
    customer_id: UUID | None = None
    activated_on_from: date | None = None
    activated_on_to: date | None = None
    bandwidth_mbps_min: int | None = None
    bandwidth_mbps_max: int | None = None
    service_state: list[ServiceInstanceServiceState] | None = None
    lifecycle_stage: list[ServiceInstanceLifecycleStage] | None = None
    source_system: str | None = Field(None, description="Source System contains")
    region: str | None = Field(None, description="Region contains")
    owner: str | None = Field(None, description="Owner contains")
    criticality: list[ServiceInstanceCriticality] | None = None


def to_query(flt: ServiceInstanceFilter) -> dict[str, str]:
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
    if flt.catalog_item_id is not None:
        params["catalogItemId"] = str(flt.catalog_item_id)
    if flt.customer_id is not None:
        params["customerId"] = str(flt.customer_id)
    if flt.activated_on_from is not None:
        params["activatedOnFrom"] = flt.activated_on_from.isoformat()
    if flt.activated_on_to is not None:
        params["activatedOnTo"] = flt.activated_on_to.isoformat()
    if flt.bandwidth_mbps_min is not None:
        params["bandwidthMbpsMin"] = str(flt.bandwidth_mbps_min)
    if flt.bandwidth_mbps_max is not None:
        params["bandwidthMbpsMax"] = str(flt.bandwidth_mbps_max)
    if flt.service_state:
        params["serviceState"] = ",".join(v.value for v in flt.service_state)
    if flt.lifecycle_stage:
        params["lifecycleStage"] = ",".join(v.value for v in flt.lifecycle_stage)
    if flt.source_system:
        params["sourceSystem"] = flt.source_system.strip()
    if flt.region:
        params["region"] = flt.region.strip()
    if flt.owner:
        params["owner"] = flt.owner.strip()
    if flt.criticality:
        params["criticality"] = ",".join(v.value for v in flt.criticality)
    return params
