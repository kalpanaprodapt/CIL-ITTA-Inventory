"""Search filter for Splice and its translation to upstream query parameters."""
from __future__ import annotations

from datetime import date, datetime
from decimal import Decimal
from uuid import UUID

from pydantic import Field

from itta_core.models import ApiModel, LifecycleStatus
from itta_api.domains.cabling.splice.schemas import *  # noqa: F401,F403


class SpliceFilter(ApiModel):
    """Query-string filter for Splice search."""

    q: str | None = Field(None, description="Free text over code, name and description")
    status: list[LifecycleStatus] | None = None
    code: str | None = Field(None, description="Code contains")
    name: str | None = Field(None, description="Name contains")
    external_ref: str | None = Field(None, description="External Ref contains")
    enclosure_id: str | None = Field(None, description="Enclosure Id contains")
    a_strand_id: UUID | None = None
    z_strand_id: UUID | None = None
    loss_db_min: Decimal | None = None
    loss_db_max: Decimal | None = None
    spliced_on_from: date | None = None
    spliced_on_to: date | None = None
    lifecycle_stage: list[SpliceLifecycleStage] | None = None
    source_system: str | None = Field(None, description="Source System contains")
    region: str | None = Field(None, description="Region contains")
    owner: str | None = Field(None, description="Owner contains")
    criticality: list[SpliceCriticality] | None = None


def to_query(flt: SpliceFilter) -> dict[str, str]:
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
    if flt.enclosure_id:
        params["enclosureId"] = flt.enclosure_id.strip()
    if flt.a_strand_id is not None:
        params["aStrandId"] = str(flt.a_strand_id)
    if flt.z_strand_id is not None:
        params["zStrandId"] = str(flt.z_strand_id)
    if flt.loss_db_min is not None:
        params["lossDbMin"] = str(flt.loss_db_min)
    if flt.loss_db_max is not None:
        params["lossDbMax"] = str(flt.loss_db_max)
    if flt.spliced_on_from is not None:
        params["splicedOnFrom"] = flt.spliced_on_from.isoformat()
    if flt.spliced_on_to is not None:
        params["splicedOnTo"] = flt.spliced_on_to.isoformat()
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
