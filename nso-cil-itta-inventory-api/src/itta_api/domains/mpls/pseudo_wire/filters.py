"""Search filter for Pseudo Wire and its translation to upstream query parameters."""
from __future__ import annotations

from datetime import date, datetime
from decimal import Decimal
from uuid import UUID

from pydantic import Field

from itta_core.models import ApiModel, LifecycleStatus
from itta_api.domains.mpls.pseudo_wire.schemas import *  # noqa: F401,F403


class PseudoWireFilter(ApiModel):
    """Query-string filter for Pseudo Wire search."""

    q: str | None = Field(None, description="Free text over code, name and description")
    status: list[LifecycleStatus] | None = None
    code: str | None = Field(None, description="Code contains")
    name: str | None = Field(None, description="Name contains")
    external_ref: str | None = Field(None, description="External Ref contains")
    vc_id_min: int | None = None
    vc_id_max: int | None = None
    a_end_id: UUID | None = None
    z_end_id: UUID | None = None
    encapsulation: str | None = Field(None, description="Encapsulation contains")
    control_word: bool | None = None
    cost_center: str | None = Field(None, description="Cost Center contains")
    effective_from_from: date | None = None
    effective_from_to: date | None = None
    effective_to_from: date | None = None
    effective_to_to: date | None = None
    labels: str | None = Field(None, description="Labels contains")


def to_query(flt: PseudoWireFilter) -> dict[str, str]:
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
    if flt.vc_id_min is not None:
        params["vcIdMin"] = str(flt.vc_id_min)
    if flt.vc_id_max is not None:
        params["vcIdMax"] = str(flt.vc_id_max)
    if flt.a_end_id is not None:
        params["aEndId"] = str(flt.a_end_id)
    if flt.z_end_id is not None:
        params["zEndId"] = str(flt.z_end_id)
    if flt.encapsulation:
        params["encapsulation"] = flt.encapsulation.strip()
    if flt.control_word is not None:
        params["controlWord"] = "true" if flt.control_word else "false"
    if flt.cost_center:
        params["costCenter"] = flt.cost_center.strip()
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
    return params
