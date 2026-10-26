"""Search filter for K8s Cluster and its translation to upstream query parameters."""
from __future__ import annotations

from datetime import date, datetime
from decimal import Decimal
from uuid import UUID

from pydantic import Field

from itta_core.models import ApiModel, LifecycleStatus
from itta_api.domains.virtual.k8s_cluster.schemas import *  # noqa: F401,F403


class K8sClusterFilter(ApiModel):
    """Query-string filter for K8s Cluster search."""

    q: str | None = Field(None, description="Free text over code, name and description")
    status: list[LifecycleStatus] | None = None
    code: str | None = Field(None, description="Code contains")
    name: str | None = Field(None, description="Name contains")
    external_ref: str | None = Field(None, description="External Ref contains")
    cluster_name: str | None = Field(None, description="Cluster Name contains")
    cluster_version: str | None = Field(None, description="Cluster Version contains")
    node_count_min: int | None = None
    node_count_max: int | None = None
    cloud_account_id: UUID | None = None
    region: str | None = Field(None, description="Region contains")
    api_endpoint: str | None = Field(None, description="Api Endpoint contains")
    effective_to_from: date | None = None
    effective_to_to: date | None = None
    labels: str | None = Field(None, description="Labels contains")
    lifecycle_stage: list[K8sClusterLifecycleStage] | None = None
    source_system: str | None = Field(None, description="Source System contains")
    owner: str | None = Field(None, description="Owner contains")


def to_query(flt: K8sClusterFilter) -> dict[str, str]:
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
    if flt.cluster_name:
        params["clusterName"] = flt.cluster_name.strip()
    if flt.cluster_version:
        params["clusterVersion"] = flt.cluster_version.strip()
    if flt.node_count_min is not None:
        params["nodeCountMin"] = str(flt.node_count_min)
    if flt.node_count_max is not None:
        params["nodeCountMax"] = str(flt.node_count_max)
    if flt.cloud_account_id is not None:
        params["cloudAccountId"] = str(flt.cloud_account_id)
    if flt.region:
        params["region"] = flt.region.strip()
    if flt.api_endpoint:
        params["apiEndpoint"] = flt.api_endpoint.strip()
    if flt.effective_to_from is not None:
        params["effectiveToFrom"] = flt.effective_to_from.isoformat()
    if flt.effective_to_to is not None:
        params["effectiveToTo"] = flt.effective_to_to.isoformat()
    if flt.labels:
        params["labels"] = flt.labels.strip()
    if flt.lifecycle_stage:
        params["lifecycleStage"] = ",".join(v.value for v in flt.lifecycle_stage)
    if flt.source_system:
        params["sourceSystem"] = flt.source_system.strip()
    if flt.owner:
        params["owner"] = flt.owner.strip()
    return params
