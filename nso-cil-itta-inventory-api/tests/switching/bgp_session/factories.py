"""Test data builders for Bgp Session."""
from __future__ import annotations

from datetime import date, datetime, timezone
from decimal import Decimal
from uuid import UUID

from itta_core.models import LifecycleStatus
from itta_api.domains.switching.bgp_session.schemas import *  # noqa: F401,F403

ID = UUID("11111111-1111-1111-1111-111111111111")
TENANT = "tenant-a"


def create_payload(**overrides) -> BgpSessionCreate:
    data = dict(
        code="code-1",
        name="name-1",
        description="description-1",
        status=LifecycleStatus.PLANNED,
        external_ref="external_ref-1",
        local_device_id=UUID("00000000-0000-0000-0000-000000000011"),
        peer_address="peer_address-1",
        local_asn=1001,
        remote_asn=1001,
        session_state=BgpSessionSessionState.IDLE,
        prefix_limit=11,
        region="region-1",
        owner="owner-1",
        criticality=BgpSessionCriticality.LOW,
        cost_center="cost_center-1",
        notes="notes-1",
    )
    data.update(code="BGPSES-0001", name="Bgp Session one", status=LifecycleStatus.ACTIVE)
    data.update(overrides)
    return BgpSessionCreate(**data)


def read_model(**overrides) -> BgpSessionRead:
    base = create_payload().model_dump()
    base.update(id=ID, tenant_id=TENANT, version=0)
    base.update(overrides)
    return BgpSessionRead(**base)


def raw_record(**overrides) -> dict[str, object]:
    record = create_payload().model_dump(mode="json", by_alias=True)
    record.update(overrides)
    return record


def upstream_json(**overrides) -> dict[str, object]:
    record = read_model().model_dump(mode="json", by_alias=True)
    record.update(overrides)
    return record
