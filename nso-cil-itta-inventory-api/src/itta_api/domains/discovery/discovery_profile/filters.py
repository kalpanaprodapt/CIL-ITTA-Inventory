"""Search filter for Discovery Profile and its translation to upstream query parameters."""
from __future__ import annotations

from datetime import date, datetime
from decimal import Decimal
from uuid import UUID

from pydantic import Field

from itta_core.models import ApiModel, LifecycleStatus
from itta_api.domains.discovery.discovery_profile.schemas import *  # noqa: F401,F403


class DiscoveryProfileFilter(ApiModel):
    """Query-string filter for Discovery Profile search."""

    q: str | None = Field(None, description="Free text over code, name and description")
    status: list[LifecycleStatus] | None = None
    code: str | None = Field(None, description="Code contains")
    name: str | None = Field(None, description="Name contains")
    external_ref: str | None = Field(None, description="External Ref contains")
    profile_name: str | None = Field(None, description="Profile Name contains")
    protocol: list[DiscoveryProfileProtocol] | None = None
    credential_ref: str | None = Field(None, description="Credential Ref contains")
    scope_cidr: str | None = Field(None, description="Scope Cidr contains")
    schedule_cron: str | None = Field(None, description="Schedule Cron contains")
    cost_center: str | None = Field(None, description="Cost Center contains")
    effective_from_from: date | None = None
    effective_from_to: date | None = None
    effective_to_from: date | None = None
    effective_to_to: date | None = None
    labels: str | None = Field(None, description="Labels contains")


def to_query(flt: DiscoveryProfileFilter) -> dict[str, str]:
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
    if flt.profile_name:
        params["profileName"] = flt.profile_name.strip()
    if flt.protocol:
        params["protocol"] = ",".join(v.value for v in flt.protocol)
    if flt.credential_ref:
        params["credentialRef"] = flt.credential_ref.strip()
    if flt.scope_cidr:
        params["scopeCidr"] = flt.scope_cidr.strip()
    if flt.schedule_cron:
        params["scheduleCron"] = flt.schedule_cron.strip()
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
