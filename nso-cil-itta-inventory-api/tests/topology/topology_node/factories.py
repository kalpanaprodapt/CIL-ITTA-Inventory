"""Test data builders for Topology Node."""
from __future__ import annotations

from datetime import date, datetime, timezone
from decimal import Decimal
from uuid import UUID

from itta_core.models import LifecycleStatus
from itta_api.domains.topology.topology_node.schemas import *  # noqa: F401,F403

ID = UUID("11111111-1111-1111-1111-111111111111")
TENANT = "tenant-a"


def create_payload(**overrides) -> TopologyNodeCreate:
    data = dict(
        code="code-1",
        name="name-1",
        description="description-1",
        status=LifecycleStatus.PLANNED,
        external_ref="external_ref-1",
        view_id=UUID("00000000-0000-0000-0000-000000000011"),
        entity_ref="entity_ref-1",
        node_type="node_type-1",
        x_pos=Decimal("13.50"),
        y_pos=Decimal("13.50"),
        layer=11,
        criticality=TopologyNodeCriticality.LOW,
        cost_center="cost_center-1",
        notes="notes-1",
        effective_from=date(2026, 2, 11),
        effective_to=date(2026, 2, 11),
    )
    data.update(code="TOPOLO-0001", name="Topology Node one", status=LifecycleStatus.ACTIVE)
    data.update(overrides)
    return TopologyNodeCreate(**data)


def read_model(**overrides) -> TopologyNodeRead:
    base = create_payload().model_dump()
    base.update(id=ID, tenant_id=TENANT, version=0)
    base.update(overrides)
    return TopologyNodeRead(**base)


def raw_record(**overrides) -> dict[str, object]:
    record = create_payload().model_dump(mode="json", by_alias=True)
    record.update(overrides)
    return record


def upstream_json(**overrides) -> dict[str, object]:
    record = read_model().model_dump(mode="json", by_alias=True)
    record.update(overrides)
    return record
