"""Search filter for License Key and its translation to upstream query parameters."""
from __future__ import annotations

from datetime import date, datetime
from decimal import Decimal
from uuid import UUID

from pydantic import Field

from itta_core.models import ApiModel, LifecycleStatus
from itta_api.domains.software.license_key.schemas import *  # noqa: F401,F403


class LicenseKeyFilter(ApiModel):
    """Query-string filter for License Key search."""

    q: str | None = Field(None, description="Free text over code, name and description")
    status: list[LifecycleStatus] | None = None
    code: str | None = Field(None, description="Code contains")
    name: str | None = Field(None, description="Name contains")
    external_ref: str | None = Field(None, description="External Ref contains")
    pool_id: UUID | None = None
    key_value: str | None = Field(None, description="Key Value contains")
    feature: str | None = Field(None, description="Feature contains")
    expires_on_from: date | None = None
    expires_on_to: date | None = None
    assigned_device_id: UUID | None = None
    effective_from_from: date | None = None
    effective_from_to: date | None = None
    effective_to_from: date | None = None
    effective_to_to: date | None = None
    labels: str | None = Field(None, description="Labels contains")
    lifecycle_stage: list[LicenseKeyLifecycleStage] | None = None


def to_query(flt: LicenseKeyFilter) -> dict[str, str]:
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
    if flt.pool_id is not None:
        params["poolId"] = str(flt.pool_id)
    if flt.key_value:
        params["keyValue"] = flt.key_value.strip()
    if flt.feature:
        params["feature"] = flt.feature.strip()
    if flt.expires_on_from is not None:
        params["expiresOnFrom"] = flt.expires_on_from.isoformat()
    if flt.expires_on_to is not None:
        params["expiresOnTo"] = flt.expires_on_to.isoformat()
    if flt.assigned_device_id is not None:
        params["assignedDeviceId"] = str(flt.assigned_device_id)
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
    return params
