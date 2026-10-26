"""Search filter for Asset Tag and its translation to upstream query parameters."""
from __future__ import annotations

from datetime import date, datetime
from decimal import Decimal
from uuid import UUID

from pydantic import Field

from itta_core.models import ApiModel, LifecycleStatus
from itta_api.domains.assetmgmt.asset_tag.schemas import *  # noqa: F401,F403


class AssetTagFilter(ApiModel):
    """Query-string filter for Asset Tag search."""

    q: str | None = Field(None, description="Free text over code, name and description")
    status: list[LifecycleStatus] | None = None
    code: str | None = Field(None, description="Code contains")
    name: str | None = Field(None, description="Name contains")
    external_ref: str | None = Field(None, description="External Ref contains")
    asset_id: UUID | None = None
    tag_value: str | None = Field(None, description="Tag Value contains")
    tag_type: list[AssetTagTagType] | None = None
    printed_on_from: date | None = None
    printed_on_to: date | None = None
    source_system: str | None = Field(None, description="Source System contains")
    region: str | None = Field(None, description="Region contains")
    owner: str | None = Field(None, description="Owner contains")
    criticality: list[AssetTagCriticality] | None = None
    cost_center: str | None = Field(None, description="Cost Center contains")


def to_query(flt: AssetTagFilter) -> dict[str, str]:
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
    if flt.asset_id is not None:
        params["assetId"] = str(flt.asset_id)
    if flt.tag_value:
        params["tagValue"] = flt.tag_value.strip()
    if flt.tag_type:
        params["tagType"] = ",".join(v.value for v in flt.tag_type)
    if flt.printed_on_from is not None:
        params["printedOnFrom"] = flt.printed_on_from.isoformat()
    if flt.printed_on_to is not None:
        params["printedOnTo"] = flt.printed_on_to.isoformat()
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
