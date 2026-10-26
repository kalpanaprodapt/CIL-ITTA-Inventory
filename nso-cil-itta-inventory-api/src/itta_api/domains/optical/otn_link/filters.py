"""Search filter for Otn Link and its translation to upstream query parameters."""
from __future__ import annotations

from datetime import date, datetime
from decimal import Decimal
from uuid import UUID

from pydantic import Field

from itta_core.models import ApiModel, LifecycleStatus
from itta_api.domains.optical.otn_link.schemas import *  # noqa: F401,F403


class OtnLinkFilter(ApiModel):
    """Query-string filter for Otn Link search."""

    q: str | None = Field(None, description="Free text over code, name and description")
    status: list[LifecycleStatus] | None = None
    code: str | None = Field(None, description="Code contains")
    name: str | None = Field(None, description="Name contains")
    external_ref: str | None = Field(None, description="External Ref contains")
    a_node_id: UUID | None = None
    z_node_id: UUID | None = None
    odu: list[OtnLinkOdu] | None = None
    latency_us_min: Decimal | None = None
    latency_us_max: Decimal | None = None
    protection: str | None = Field(None, description="Protection contains")
    effective_to_from: date | None = None
    effective_to_to: date | None = None
    labels: str | None = Field(None, description="Labels contains")
    lifecycle_stage: list[OtnLinkLifecycleStage] | None = None
    source_system: str | None = Field(None, description="Source System contains")
    region: str | None = Field(None, description="Region contains")


def to_query(flt: OtnLinkFilter) -> dict[str, str]:
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
    if flt.a_node_id is not None:
        params["aNodeId"] = str(flt.a_node_id)
    if flt.z_node_id is not None:
        params["zNodeId"] = str(flt.z_node_id)
    if flt.odu:
        params["odu"] = ",".join(v.value for v in flt.odu)
    if flt.latency_us_min is not None:
        params["latencyUsMin"] = str(flt.latency_us_min)
    if flt.latency_us_max is not None:
        params["latencyUsMax"] = str(flt.latency_us_max)
    if flt.protection:
        params["protection"] = flt.protection.strip()
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
