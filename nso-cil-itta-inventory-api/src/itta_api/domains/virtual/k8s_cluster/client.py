"""HTTP client for the K8s Cluster endpoints of inventory-virtual-service."""
from __future__ import annotations

from uuid import UUID

from itta_core.http import UpstreamClient
from itta_core.models import LifecycleStatus
from itta_core.pagination import Page
from itta_api.domains.virtual.k8s_cluster.filters import K8sClusterFilter, to_query
from itta_api.domains.virtual.k8s_cluster.schemas import (
    K8sClusterBulkResult,
    K8sClusterCreate,
    K8sClusterRead,
    K8sClusterSummary,
    K8sClusterUpdate,
)


class K8sClusterClient:
    BASE = "/api/v1/k8s-clusters"

    def __init__(self, upstream: UpstreamClient) -> None:
        self._up = upstream

    async def search(
        self, flt: K8sClusterFilter, page: int = 0, size: int = 20, sort: str | None = None
    ) -> Page[K8sClusterRead]:
        params: dict[str, str | int] = {**to_query(flt), "page": page, "size": size}
        if sort:
            params["sort"] = sort
        return Page[K8sClusterRead].model_validate(await self._up.get_json(self.BASE, params=params))

    async def get(self, id: UUID) -> K8sClusterRead:
        return K8sClusterRead.model_validate(await self._up.get_json(f"{self.BASE}/{id}"))

    async def get_by_code(self, code: str) -> K8sClusterRead:
        return K8sClusterRead.model_validate(await self._up.get_json(f"{self.BASE}/by-code/{code}"))

    async def exists(self, code: str) -> bool:
        return await self._up.head(f"{self.BASE}/by-code/{code}")

    async def summaries(self, status: LifecycleStatus | None = None) -> list[K8sClusterSummary]:
        params = {"status": status.value} if status else None
        data = await self._up.get_json(f"{self.BASE}/summaries", params=params)
        return [K8sClusterSummary.model_validate(row) for row in data]

    async def create(self, payload: K8sClusterCreate) -> K8sClusterRead:
        body = payload.model_dump(mode="json", by_alias=True, exclude_none=True)
        return K8sClusterRead.model_validate(await self._up.post_json(self.BASE, body))

    async def update(self, id: UUID, payload: K8sClusterUpdate) -> K8sClusterRead:
        body = payload.model_dump(mode="json", by_alias=True, exclude_unset=True)
        return K8sClusterRead.model_validate(await self._up.put_json(f"{self.BASE}/{id}", body))

    async def change_status(self, id: UUID, status: LifecycleStatus) -> K8sClusterRead:
        data = await self._up.patch_json(f"{self.BASE}/{id}/status", params={"status": status.value})
        return K8sClusterRead.model_validate(data)

    async def delete(self, id: UUID) -> None:
        await self._up.delete(f"{self.BASE}/{id}")

    async def bulk_create(self, payloads: list[K8sClusterCreate]) -> K8sClusterBulkResult:
        body = [p.model_dump(mode="json", by_alias=True, exclude_none=True) for p in payloads]
        return K8sClusterBulkResult.model_validate(await self._up.post_json(f"{self.BASE}/bulk", body))

    async def status_stats(self) -> dict[str, int]:
        return await self._up.get_json(f"{self.BASE}/stats/status")

    async def export_csv(self, flt: K8sClusterFilter) -> bytes:
        return await self._up.get_bytes(f"{self.BASE}/export", params=to_query(flt))

    async def list_by_cloud_account_id(self, cloud_account_id: UUID, page: int = 0, size: int = 20) -> Page[K8sClusterRead]:
        data = await self._up.get_json(
            f"{self.BASE}/by-cloud-account-id/{cloud_account_id}", params={"page": page, "size": size}
        )
        return Page[K8sClusterRead].model_validate(data)
