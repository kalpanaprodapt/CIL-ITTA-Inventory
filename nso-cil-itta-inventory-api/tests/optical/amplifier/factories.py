"""Test data builders for Amplifier."""
from __future__ import annotations

from datetime import date, datetime, timezone
from decimal import Decimal
from uuid import UUID

from itta_core.models import LifecycleStatus
from itta_api.domains.optical.amplifier.schemas import *  # noqa: F401,F403

ID = UUID("11111111-1111-1111-1111-111111111111")
TENANT = "tenant-a"


def create_payload(**overrides) -> AmplifierCreate:
    data = dict(
        code="code-1",
        name="name-1",
        description="description-1",
        status=LifecycleStatus.PLANNED,
        external_ref="external_ref-1",
        system_id=UUID("00000000-0000-0000-0000-000000000011"),
        amp_type=AmplifierAmpType.EDFA,
        gain_db=Decimal("13.50"),
        output_power_dbm=Decimal("13.50"),
        noise_figure_db=Decimal("13.50"),
        lifecycle_stage=AmplifierLifecycleStage.PLAN,
        source_system="source_system-1",
        region="region-1",
        owner="owner-1",
        criticality=AmplifierCriticality.LOW,
    )
    data.update(code="AMPLIF-0001", name="Amplifier one", status=LifecycleStatus.ACTIVE)
    data.update(overrides)
    return AmplifierCreate(**data)


def read_model(**overrides) -> AmplifierRead:
    base = create_payload().model_dump()
    base.update(id=ID, tenant_id=TENANT, version=0)
    base.update(overrides)
    return AmplifierRead(**base)


def raw_record(**overrides) -> dict[str, object]:
    record = create_payload().model_dump(mode="json", by_alias=True)
    record.update(overrides)
    return record


def upstream_json(**overrides) -> dict[str, object]:
    record = read_model().model_dump(mode="json", by_alias=True)
    record.update(overrides)
    return record
