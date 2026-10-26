"""Test data builders for Circuit Termination."""
from __future__ import annotations

from datetime import date, datetime, timezone
from decimal import Decimal
from uuid import UUID

from itta_core.models import LifecycleStatus
from itta_api.domains.circuit.circuit_termination.schemas import *  # noqa: F401,F403

ID = UUID("11111111-1111-1111-1111-111111111111")
TENANT = "tenant-a"


def create_payload(**overrides) -> CircuitTerminationCreate:
    data = dict(
        code="code-1",
        name="name-1",
        description="description-1",
        status=LifecycleStatus.PLANNED,
        external_ref="external_ref-1",
        circuit_id=UUID("00000000-0000-0000-0000-000000000011"),
        side=CircuitTerminationSide.A,
        port_id=UUID("00000000-0000-0000-0000-000000000011"),
        xconnect_id="xconnect_id-1",
        patch_info="patch_info-1",
        cost_center="cost_center-1",
        notes="notes-1",
        effective_from=date(2026, 2, 11),
        effective_to=date(2026, 2, 11),
        labels="labels-1",
    )
    data.update(code="CIRCUI-0001", name="Circuit Termination one", status=LifecycleStatus.ACTIVE)
    data.update(overrides)
    return CircuitTerminationCreate(**data)


def read_model(**overrides) -> CircuitTerminationRead:
    base = create_payload().model_dump()
    base.update(id=ID, tenant_id=TENANT, version=0)
    base.update(overrides)
    return CircuitTerminationRead(**base)


def raw_record(**overrides) -> dict[str, object]:
    record = create_payload().model_dump(mode="json", by_alias=True)
    record.update(overrides)
    return record


def upstream_json(**overrides) -> dict[str, object]:
    record = read_model().model_dump(mode="json", by_alias=True)
    record.update(overrides)
    return record
