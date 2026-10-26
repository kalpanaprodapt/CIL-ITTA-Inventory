"""Search filter for Dns Record and its translation to upstream query parameters."""
from __future__ import annotations

from datetime import date, datetime
from decimal import Decimal
from uuid import UUID

from pydantic import Field

from itta_core.models import ApiModel, LifecycleStatus
from itta_api.domains.ipam.dns_record.schemas import *  # noqa: F401,F403


class DnsRecordFilter(ApiModel):
    """Query-string filter for Dns Record search."""

    q: str | None = Field(None, description="Free text over code, name and description")
    status: list[LifecycleStatus] | None = None
    code: str | None = Field(None, description="Code contains")
    name: str | None = Field(None, description="Name contains")
    external_ref: str | None = Field(None, description="External Ref contains")
    zone_name: str | None = Field(None, description="Zone Name contains")
    record_type: list[DnsRecordRecordType] | None = None
    record_value: str | None = Field(None, description="Record Value contains")
    ttl_min: int | None = None
    ttl_max: int | None = None
    source_system: str | None = Field(None, description="Source System contains")
    region: str | None = Field(None, description="Region contains")
    owner: str | None = Field(None, description="Owner contains")
    criticality: list[DnsRecordCriticality] | None = None
    cost_center: str | None = Field(None, description="Cost Center contains")


def to_query(flt: DnsRecordFilter) -> dict[str, str]:
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
    if flt.zone_name:
        params["zoneName"] = flt.zone_name.strip()
    if flt.record_type:
        params["recordType"] = ",".join(v.value for v in flt.record_type)
    if flt.record_value:
        params["recordValue"] = flt.record_value.strip()
    if flt.ttl_min is not None:
        params["ttlMin"] = str(flt.ttl_min)
    if flt.ttl_max is not None:
        params["ttlMax"] = str(flt.ttl_max)
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
