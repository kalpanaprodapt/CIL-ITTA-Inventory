"""Test data builders for Antenna."""
from __future__ import annotations

from datetime import date, datetime, timezone
from decimal import Decimal
from uuid import UUID

from itta_core.models import LifecycleStatus
from itta_api.domains.radio.antenna.schemas import *  # noqa: F401,F403

ID = UUID("11111111-1111-1111-1111-111111111111")
TENANT = "tenant-a"


def create_payload(**overrides) -> AntennaCreate:
    data = dict(
        code="code-1",
        name="name-1",
        description="description-1",
        status=LifecycleStatus.PLANNED,
        external_ref="external_ref-1",
        sector_id=UUID("00000000-0000-0000-0000-000000000011"),
        model="model-1",
        height_m=Decimal("13.50"),
        azimuth_deg=Decimal("13.50"),
        tilt_deg=Decimal("13.50"),
        gain_dbi=Decimal("13.50"),
        source_system="source_system-1",
        region="region-1",
        owner="owner-1",
        criticality=AntennaCriticality.LOW,
        cost_center="cost_center-1",
    )
    data.update(code="ANTENN-0001", name="Antenna one", status=LifecycleStatus.ACTIVE)
    data.update(overrides)
    return AntennaCreate(**data)


def read_model(**overrides) -> AntennaRead:
    base = create_payload().model_dump()
    base.update(id=ID, tenant_id=TENANT, version=0)
    base.update(overrides)
    return AntennaRead(**base)


def raw_record(**overrides) -> dict[str, object]:
    record = create_payload().model_dump(mode="json", by_alias=True)
    record.update(overrides)
    return record


def upstream_json(**overrides) -> dict[str, object]:
    record = read_model().model_dump(mode="json", by_alias=True)
    record.update(overrides)
    return record
