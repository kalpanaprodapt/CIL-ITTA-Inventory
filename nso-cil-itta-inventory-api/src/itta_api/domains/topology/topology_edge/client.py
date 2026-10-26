"""HTTP client for the Topology Edge endpoints of inventory-topology-service."""
from __future__ import annotations

from uuid import UUID

from itta_core.http import UpstreamClient
from itta_core.models import LifecycleStatus
from itta_core.pagination import Page
from itta_api.domains.topology.topology_edge.filters import TopologyEdgeFilter, to_query
from itta_api.domains.topology.topology_edge.schemas import (
    TopologyEdgeBulkResult,
    TopologyEdgeCreate,
    TopologyEdgeRead,
    TopologyEdgeSummary,
    TopologyEdgeUpdate,
)


class TopologyEdgeClient:
    BASE = "/api/v1/topology-edges"

    def __init__(self, upstream: UpstreamClient) -> None:
        self._up = upstream

    async def search(
        self, flt: TopologyEdgeFilter, page: int = 0, size: int = 20, sort: str | None = None
    ) -> Page[TopologyEdgeRead]:
        params: dict[str, str | int] = {**to_query(flt), "page": page, "size": size}
        if sort:
            params["sort"] = sort
        return Page[TopologyEdgeRead].model_validate(await self._up.get_json(self.BASE, params=params))

    async def get(self, id: UUID) -> TopologyEdgeRead:
        return TopologyEdgeRead.model_validate(await self._up.get_json(f"{self.BASE}/{id}"))

    async def get_by_code(self, code: str) -> TopologyEdgeRead:
        return TopologyEdgeRead.model_validate(await self._up.get_json(f"{self.BASE}/by-code/{code}"))

    async def exists(self, code: str) -> bool:
        return await self._up.head(f"{self.BASE}/by-code/{code}")

    async def summaries(self, status: LifecycleStatus | None = None) -> list[TopologyEdgeSummary]:
        params = {"status": status.value} if status else None
        data = await self._up.get_json(f"{self.BASE}/summaries", params=params)
        return [TopologyEdgeSummary.model_validate(row) for row in data]

    async def create(self, payload: TopologyEdgeCreate) -> TopologyEdgeRead:
        body = payload.model_dump(mode="json", by_alias=True, exclude_none=True)
        return TopologyEdgeRead.model_validate(await self._up.post_json(self.BASE, body))

    async def update(self, id: UUID, payload: TopologyEdgeUpdate) -> TopologyEdgeRead:
        body = payload.model_dump(mode="json", by_alias=True, exclude_unset=True)
        return TopologyEdgeRead.model_validate(await self._up.put_json(f"{self.BASE}/{id}", body))

    async def change_status(self, id: UUID, status: LifecycleStatus) -> TopologyEdgeRead:
        data = await self._up.patch_json(f"{self.BASE}/{id}/status", params={"status": status.value})
        return TopologyEdgeRead.model_validate(data)

    async def delete(self, id: UUID) -> None:
        await self._up.delete(f"{self.BASE}/{id}")

    async def bulk_create(self, payloads: list[TopologyEdgeCreate]) -> TopologyEdgeBulkResult:
        body = [p.model_dump(mode="json", by_alias=True, exclude_none=True) for p in payloads]
        return TopologyEdgeBulkResult.model_validate(await self._up.post_json(f"{self.BASE}/bulk", body))

    async def status_stats(self) -> dict[str, int]:
        return await self._up.get_json(f"{self.BASE}/stats/status")

    async def export_csv(self, flt: TopologyEdgeFilter) -> bytes:
        return await self._up.get_bytes(f"{self.BASE}/export", params=to_query(flt))

    async def list_by_view_id(self, view_id: UUID, page: int = 0, size: int = 20) -> Page[TopologyEdgeRead]:
        data = await self._up.get_json(
            f"{self.BASE}/by-view-id/{view_id}", params={"page": page, "size": size}
        )
        return Page[TopologyEdgeRead].model_validate(data)

    async def list_by_source_node_id(self, source_node_id: UUID, page: int = 0, size: int = 20) -> Page[TopologyEdgeRead]:
        data = await self._up.get_json(
            f"{self.BASE}/by-source-node-id/{source_node_id}", params={"page": page, "size": size}
        )
        return Page[TopologyEdgeRead].model_validate(data)

    async def list_by_target_node_id(self, target_node_id: UUID, page: int = 0, size: int = 20) -> Page[TopologyEdgeRead]:
        data = await self._up.get_json(
            f"{self.BASE}/by-target-node-id/{target_node_id}", params={"page": page, "size": size}
        )
        return Page[TopologyEdgeRead].model_validate(data)
