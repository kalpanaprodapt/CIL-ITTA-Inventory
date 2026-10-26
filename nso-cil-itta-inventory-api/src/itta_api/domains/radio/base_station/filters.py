"""Search filter for Base Station and its translation to upstream query parameters."""
from __future__ import annotations

from datetime import date, datetime
from decimal import Decimal
from uuid import UUID

from pydantic import Field

from itta_core.models import ApiModel, LifecycleStatus
from itta_api.domains.radio.base_station.schemas import *  # noqa: F401,F403


class BaseStationFilter(ApiModel):
    """Query-string filter for Base Station search."""

    q: str | None = Field(None, description="Free text over code, name and description")
    status: list[LifecycleStatus] | None = None
    code: str | None = Field(None, description="Code contains")
    name: str | None = Field(None, description="Name contains")
    external_ref: str | None = Field(None, description="External Ref contains")
    site_id: UUID | None = None
    enb_id_min: int | None = None
    enb_id_max: int | None = None
    technology: list[BaseStationTechnology] | None = None
    sector_count_min: int | None = None
    sector_count_max: int | None = None
    cell_count_min: int | None = None
    cell_count_max: int | None = None
    labels: str | None = Field(None, description="Labels contains")
    lifecycle_stage: list[BaseStationLifecycleStage] | None = None
    source_system: str | None = Field(None, description="Source System contains")
    region: str | None = Field(None, description="Region contains")
    owner: str | None = Field(None, description="Owner contains")


def to_query(flt: BaseStationFilter) -> dict[str, str]:
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
    if flt.site_id is not None:
        params["siteId"] = str(flt.site_id)
    if flt.enb_id_min is not None:
        params["enbIdMin"] = str(flt.enb_id_min)
    if flt.enb_id_max is not None:
        params["enbIdMax"] = str(flt.enb_id_max)
    if flt.technology:
        params["technology"] = ",".join(v.value for v in flt.technology)
    if flt.sector_count_min is not None:
        params["sectorCountMin"] = str(flt.sector_count_min)
    if flt.sector_count_max is not None:
        params["sectorCountMax"] = str(flt.sector_count_max)
    if flt.cell_count_min is not None:
        params["cellCountMin"] = str(flt.cell_count_min)
    if flt.cell_count_max is not None:
        params["cellCountMax"] = str(flt.cell_count_max)
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
