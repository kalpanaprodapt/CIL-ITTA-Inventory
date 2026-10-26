"""Test data builders for Dns Record."""
from __future__ import annotations

from datetime import date, datetime, timezone
from decimal import Decimal
from uuid import UUID

from itta_core.models import LifecycleStatus
from itta_api.domains.ipam.dns_record.schemas import *  # noqa: F401,F403

ID = UUID("11111111-1111-1111-1111-111111111111")
TENANT = "tenant-a"


def create_payload(**overrides) -> DnsRecordCreate:
    data = dict(
        code="code-1",
        name="name-1",
        description="description-1",
        status=LifecycleStatus.PLANNED,
        external_ref="external_ref-1",
        zone_name="zone_name-1",
        record_type=DnsRecordRecordType.A,
        record_value="record_value-1",
        ttl=11,
        source_system="source_system-1",
        region="region-1",
        owner="owner-1",
        criticality=DnsRecordCriticality.LOW,
        cost_center="cost_center-1",
    )
    data.update(code="DNSREC-0001", name="Dns Record one", status=LifecycleStatus.ACTIVE)
    data.update(overrides)
    return DnsRecordCreate(**data)


def read_model(**overrides) -> DnsRecordRead:
    base = create_payload().model_dump()
    base.update(id=ID, tenant_id=TENANT, version=0)
    base.update(overrides)
    return DnsRecordRead(**base)


def raw_record(**overrides) -> dict[str, object]:
    record = create_payload().model_dump(mode="json", by_alias=True)
    record.update(overrides)
    return record


def upstream_json(**overrides) -> dict[str, object]:
    record = read_model().model_dump(mode="json", by_alias=True)
    record.update(overrides)
    return record
