"""Test data builders for K8s Cluster."""
from __future__ import annotations

from datetime import date, datetime, timezone
from decimal import Decimal
from uuid import UUID

from itta_core.models import LifecycleStatus
from itta_api.domains.virtual.k8s_cluster.schemas import *  # noqa: F401,F403

ID = UUID("11111111-1111-1111-1111-111111111111")
TENANT = "tenant-a"


def create_payload(**overrides) -> K8sClusterCreate:
    data = dict(
        code="code-1",
        name="name-1",
        description="description-1",
        status=LifecycleStatus.PLANNED,
        external_ref="external_ref-1",
        cluster_name="cluster_name-1",
        cluster_version="cluster_version-1",
        node_count=11,
        cloud_account_id=UUID("00000000-0000-0000-0000-000000000011"),
        region="region-1",
        api_endpoint="api_endpoint-1",
        effective_to=date(2026, 2, 11),
        labels="labels-1",
        lifecycle_stage=K8sClusterLifecycleStage.PLAN,
        source_system="source_system-1",
        owner="owner-1",
    )
    data.update(code="K8SCLU-0001", name="K8s Cluster one", status=LifecycleStatus.ACTIVE)
    data.update(overrides)
    return K8sClusterCreate(**data)


def read_model(**overrides) -> K8sClusterRead:
    base = create_payload().model_dump()
    base.update(id=ID, tenant_id=TENANT, version=0)
    base.update(overrides)
    return K8sClusterRead(**base)


def raw_record(**overrides) -> dict[str, object]:
    record = create_payload().model_dump(mode="json", by_alias=True)
    record.update(overrides)
    return record


def upstream_json(**overrides) -> dict[str, object]:
    record = read_model().model_dump(mode="json", by_alias=True)
    record.update(overrides)
    return record
