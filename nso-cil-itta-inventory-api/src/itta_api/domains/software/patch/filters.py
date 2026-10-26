"""Search filter for Patch and its translation to upstream query parameters."""
from __future__ import annotations

from datetime import date, datetime
from decimal import Decimal
from uuid import UUID

from pydantic import Field

from itta_core.models import ApiModel, LifecycleStatus
from itta_api.domains.software.patch.schemas import *  # noqa: F401,F403


class PatchFilter(ApiModel):
    """Query-string filter for Patch search."""

    q: str | None = Field(None, description="Free text over code, name and description")
    status: list[LifecycleStatus] | None = None
    code: str | None = Field(None, description="Code contains")
    name: str | None = Field(None, description="Name contains")
    external_ref: str | None = Field(None, description="External Ref contains")
    version_id: UUID | None = None
    patch_id: str | None = Field(None, description="Patch Id contains")
    severity: list[PatchSeverity] | None = None
    released_on_from: date | None = None
    released_on_to: date | None = None
    reboot_required: bool | None = None
    criticality: list[PatchCriticality] | None = None
    cost_center: str | None = Field(None, description="Cost Center contains")
    effective_from_from: date | None = None
    effective_from_to: date | None = None
    effective_to_from: date | None = None
    effective_to_to: date | None = None


def to_query(flt: PatchFilter) -> dict[str, str]:
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
    if flt.version_id is not None:
        params["versionId"] = str(flt.version_id)
    if flt.patch_id:
        params["patchId"] = flt.patch_id.strip()
    if flt.severity:
        params["severity"] = ",".join(v.value for v in flt.severity)
    if flt.released_on_from is not None:
        params["releasedOnFrom"] = flt.released_on_from.isoformat()
    if flt.released_on_to is not None:
        params["releasedOnTo"] = flt.released_on_to.isoformat()
    if flt.reboot_required is not None:
        params["rebootRequired"] = "true" if flt.reboot_required else "false"
    if flt.criticality:
        params["criticality"] = ",".join(v.value for v in flt.criticality)
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
    return params
