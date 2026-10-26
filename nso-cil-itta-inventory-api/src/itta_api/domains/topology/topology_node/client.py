"""HTTP client for the Topology Node endpoints of inventory-topology-service."""
from __future__ import annotations

from uuid import UUID

from itta_core.http import UpstreamClient
from itta_core.models import LifecycleStatus
from itta_core.pagination import Page
from itta_api.domains.topology.topology_node.filters import TopologyNodeFilter, to_query
from itta_api.domains.topology.topology_node.schemas import (
    TopologyNodeBulkResult,
    TopologyNodeCreate,
    TopologyNodeRead,
    TopologyNodeSummary,
    TopologyNodeUpdate,
)


class TopologyNodeClient:
    BASE = "/api/v1/topology-nodes"

    def __init__(self, upstream: UpstreamClient) -> None:
        self._up = upstream

    async def search(
        self, flt: TopologyNodeFilter, page: int = 0, size: int = 20, sort: str | None = None
    ) -> Page[TopologyNodeRead]:
        params: dict[str, str | int] = {**to_query(flt), "page": page, "size": size}
        if sort:
            params["sort"] = sort
        return Page[TopologyNodeRead].model_validate(await self._up.get_json(self.BASE, params=params))

    async def get(self, id: UUID) -> TopologyNodeRead:
        return TopologyNodeRead.model_validate(await self._up.get_json(f"{self.BASE}/{id}"))

    async def get_by_code(self, code: str) -> TopologyNodeRead:
        return TopologyNodeRead.model_validate(await self._up.get_json(f"{self.BASE}/by-code/{code}"))

    async def exists(self, code: str) -> bool:
        return await self._up.head(f"{self.BASE}/by-code/{code}")

    async def summaries(self, status: LifecycleStatus | None = None) -> list[TopologyNodeSummary]:
        params = {"status": status.value} if status else None
        data = await self._up.get_json(f"{self.BASE}/summaries", params=params)
        return [TopologyNodeSummary.model_validate(row) for row in data]

    async def create(self, payload: TopologyNodeCreate) -> TopologyNodeRead:
        body = payload.model_dump(mode="json", by_alias=True, exclude_none=True)
        return TopologyNodeRead.model_validate(await self._up.post_json(self.BASE, body))

    async def update(self, id: UUID, payload: TopologyNodeUpdate) -> TopologyNodeRead:
        body = payload.model_dump(mode="json", by_alias=True, exclude_unset=True)
        return TopologyNodeRead.model_validate(await self._up.put_json(f"{self.BASE}/{id}", body))

    async def change_status(self, id: UUID, status: LifecycleStatus) -> TopologyNodeRead:
        data = await self._up.patch_json(f"{self.BASE}/{id}/status", params={"status": status.value})
        return TopologyNodeRead.model_validate(data)

    async def delete(self, id: UUID) -> None:
        await self._up.delete(f"{self.BASE}/{id}")

    async def bulk_create(self, payloads: list[TopologyNodeCreate]) -> TopologyNodeBulkResult:
        body = [p.model_dump(mode="json", by_alias=True, exclude_none=True) for p in payloads]
        return TopologyNodeBulkResult.model_validate(await self._up.post_json(f"{self.BASE}/bulk", body))

    async def status_stats(self) -> dict[str, int]:
        return await self._up.get_json(f"{self.BASE}/stats/status")

    async def export_csv(self, flt: TopologyNodeFilter) -> bytes:
        return await self._up.get_bytes(f"{self.BASE}/export", params=to_query(flt))

    async def list_by_view_id(self, view_id: UUID, page: int = 0, size: int = 20) -> Page[TopologyNodeRead]:
        data = await self._up.get_json(
            f"{self.BASE}/by-view-id/{view_id}", params={"page": page, "size": size}
        )
        return Page[TopologyNodeRead].model_validate(data)
