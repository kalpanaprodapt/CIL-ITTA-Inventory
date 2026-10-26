"""Search filter for Vendor and its translation to upstream query parameters."""
from __future__ import annotations

from datetime import date, datetime
from decimal import Decimal
from uuid import UUID

from pydantic import Field

from itta_core.models import ApiModel, LifecycleStatus
from itta_api.domains.vendor.vendor.schemas import *  # noqa: F401,F403


class VendorFilter(ApiModel):
    """Query-string filter for Vendor search."""

    q: str | None = Field(None, description="Free text over code, name and description")
    status: list[LifecycleStatus] | None = None
    code: str | None = Field(None, description="Code contains")
    name: str | None = Field(None, description="Name contains")
    external_ref: str | None = Field(None, description="External Ref contains")
    legal_name: str | None = Field(None, description="Legal Name contains")
    support_email: str | None = Field(None, description="Support Email contains")
    support_phone: str | None = Field(None, description="Support Phone contains")
    website: str | None = Field(None, description="Website contains")
    tier: list[VendorTier] | None = None
    effective_from_from: date | None = None
    effective_from_to: date | None = None
    effective_to_from: date | None = None
    effective_to_to: date | None = None
    labels: str | None = Field(None, description="Labels contains")
    lifecycle_stage: list[VendorLifecycleStage] | None = None
    source_system: str | None = Field(None, description="Source System contains")


def to_query(flt: VendorFilter) -> dict[str, str]:
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
    if flt.legal_name:
        params["legalName"] = flt.legal_name.strip()
    if flt.support_email:
        params["supportEmail"] = flt.support_email.strip()
    if flt.support_phone:
        params["supportPhone"] = flt.support_phone.strip()
    if flt.website:
        params["website"] = flt.website.strip()
    if flt.tier:
        params["tier"] = ",".join(v.value for v in flt.tier)
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
    if flt.source_system:
        params["sourceSystem"] = flt.source_system.strip()
    return params
