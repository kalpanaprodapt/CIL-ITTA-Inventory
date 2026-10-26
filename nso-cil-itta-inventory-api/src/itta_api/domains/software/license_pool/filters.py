"""Search filter for License Pool and its translation to upstream query parameters."""
from __future__ import annotations

from datetime import date, datetime
from decimal import Decimal
from uuid import UUID

from pydantic import Field

from itta_core.models import ApiModel, LifecycleStatus
from itta_api.domains.software.license_pool.schemas import *  # noqa: F401,F403


class LicensePoolFilter(ApiModel):
    """Query-string filter for License Pool search."""

    q: str | None = Field(None, description="Free text over code, name and description")
    status: list[LifecycleStatus] | None = None
    code: str | None = Field(None, description="Code contains")
    name: str | None = Field(None, description="Name contains")
    external_ref: str | None = Field(None, description="External Ref contains")
    vendor_id: UUID | None = None
    pool_name: str | None = Field(None, description="Pool Name contains")
    total_seats_min: int | None = None
    total_seats_max: int | None = None
    used_seats_min: int | None = None
    used_seats_max: int | None = None
    renewal_date_from: date | None = None
    renewal_date_to: date | None = None
    effective_to_from: date | None = None
    effective_to_to: date | None = None
    labels: str | None = Field(None, description="Labels contains")
    lifecycle_stage: list[LicensePoolLifecycleStage] | None = None
    source_system: str | None = Field(None, description="Source System contains")
    region: str | None = Field(None, description="Region contains")


def to_query(flt: LicensePoolFilter) -> dict[str, str]:
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
    if flt.vendor_id is not None:
        params["vendorId"] = str(flt.vendor_id)
    if flt.pool_name:
        params["poolName"] = flt.pool_name.strip()
    if flt.total_seats_min is not None:
        params["totalSeatsMin"] = str(flt.total_seats_min)
    if flt.total_seats_max is not None:
        params["totalSeatsMax"] = str(flt.total_seats_max)
    if flt.used_seats_min is not None:
        params["usedSeatsMin"] = str(flt.used_seats_min)
    if flt.used_seats_max is not None:
        params["usedSeatsMax"] = str(flt.used_seats_max)
    if flt.renewal_date_from is not None:
        params["renewalDateFrom"] = flt.renewal_date_from.isoformat()
    if flt.renewal_date_to is not None:
        params["renewalDateTo"] = flt.renewal_date_to.isoformat()
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
