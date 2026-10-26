"""Search filter for Integration Endpoint and its translation to upstream query parameters."""
from __future__ import annotations

from datetime import date, datetime
from decimal import Decimal
from uuid import UUID

from pydantic import Field

from itta_core.models import ApiModel, LifecycleStatus
from itta_api.domains.integration.integration_endpoint.schemas import *  # noqa: F401,F403


class IntegrationEndpointFilter(ApiModel):
    """Query-string filter for Integration Endpoint search."""

    q: str | None = Field(None, description="Free text over code, name and description")
    status: list[LifecycleStatus] | None = None
    code: str | None = Field(None, description="Code contains")
    name: str | None = Field(None, description="Name contains")
    external_ref: str | None = Field(None, description="External Ref contains")
    endpoint_name: str | None = Field(None, description="Endpoint Name contains")
    target_system: str | None = Field(None, description="Target System contains")
    base_url: str | None = Field(None, description="Base Url contains")
    auth_type: list[IntegrationEndpointAuthType] | None = None
    timeout_ms_min: int | None = None
    timeout_ms_max: int | None = None
    source_system: str | None = Field(None, description="Source System contains")
    region: str | None = Field(None, description="Region contains")
    owner: str | None = Field(None, description="Owner contains")
    criticality: list[IntegrationEndpointCriticality] | None = None
    cost_center: str | None = Field(None, description="Cost Center contains")


def to_query(flt: IntegrationEndpointFilter) -> dict[str, str]:
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
    if flt.endpoint_name:
        params["endpointName"] = flt.endpoint_name.strip()
    if flt.target_system:
        params["targetSystem"] = flt.target_system.strip()
    if flt.base_url:
        params["baseUrl"] = flt.base_url.strip()
    if flt.auth_type:
        params["authType"] = ",".join(v.value for v in flt.auth_type)
    if flt.timeout_ms_min is not None:
        params["timeoutMsMin"] = str(flt.timeout_ms_min)
    if flt.timeout_ms_max is not None:
        params["timeoutMsMax"] = str(flt.timeout_ms_max)
    if flt.source_system:
        params["sourceSystem"] = flt.source_system.strip()
    if flt.region:
        params["region"] = flt.region.strip()
    if flt.owner:
        params["owner"] = flt.owner.strip()
    if flt.criticality:
        params["criticality"] = ",".join(v.value for v in flt.criticality)
    if flt.cost_center:
        params["costCenter"] = flt.cost_center.strip()
    return params
