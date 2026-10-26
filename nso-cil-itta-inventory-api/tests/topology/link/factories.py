"""Test data builders for Link."""
from __future__ import annotations

from datetime import date, datetime, timezone
from decimal import Decimal
from uuid import UUID

from itta_core.models import LifecycleStatus
from itta_api.domains.topology.link.schemas import *  # noqa: F401,F403

ID = UUID("11111111-1111-1111-1111-111111111111")
TENANT = "tenant-a"


def create_payload(**overrides) -> LinkCreate:
    data = dict(
        code="code-1",
        name="name-1",
        description="description-1",
        status=LifecycleStatus.PLANNED,
        external_ref="external_ref-1",
        a_interface_id=UUID("00000000-0000-0000-0000-000000000011"),
        z_interface_id=UUID("00000000-0000-0000-0000-000000000011"),
        link_type=LinkLinkType.PHYSICAL,
        speed_mbps=1001,
        discovered_by="discovered_by-1",
        region="region-1",
        owner="owner-1",
        criticality=LinkCriticality.LOW,
        cost_center="cost_center-1",
        notes="notes-1",
    )
    data.update(code="LINK-0001", name="Link one", status=LifecycleStatus.ACTIVE)
    data.update(overrides)
    return LinkCreate(**data)


def read_model(**overrides) -> LinkRead:
    base = create_payload().model_dump()
    base.update(id=ID, tenant_id=TENANT, version=0)
    base.update(overrides)
    return LinkRead(**base)


def raw_record(**overrides) -> dict[str, object]:
    record = create_payload().model_dump(mode="json", by_alias=True)
    record.update(overrides)
    return record


def upstream_json(**overrides) -> dict[str, object]:
    record = read_model().model_dump(mode="json", by_alias=True)
    record.update(overrides)
    return record
