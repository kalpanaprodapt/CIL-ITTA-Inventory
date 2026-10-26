"""HTTP client for the Discovered Asset endpoints of inventory-discovery-service."""
from __future__ import annotations

from uuid import UUID

from itta_core.http import UpstreamClient
from itta_core.models import LifecycleStatus
from itta_core.pagination import Page
from itta_api.domains.discovery.discovered_asset.filters import DiscoveredAssetFilter, to_query
from itta_api.domains.discovery.discovered_asset.schemas import (
    DiscoveredAssetBulkResult,
    DiscoveredAssetCreate,
    DiscoveredAssetRead,
    DiscoveredAssetSummary,
    DiscoveredAssetUpdate,
)


class DiscoveredAssetClient:
    BASE = "/api/v1/discovered-assets"

    def __init__(self, upstream: UpstreamClient) -> None:
        self._up = upstream

    async def search(
        self, flt: DiscoveredAssetFilter, page: int = 0, size: int = 20, sort: str | None = None
    ) -> Page[DiscoveredAssetRead]:
        params: dict[str, str | int] = {**to_query(flt), "page": page, "size": size}
        if sort:
            params["sort"] = sort
        return Page[DiscoveredAssetRead].model_validate(await self._up.get_json(self.BASE, params=params))

    async def get(self, id: UUID) -> DiscoveredAssetRead:
        return DiscoveredAssetRead.model_validate(await self._up.get_json(f"{self.BASE}/{id}"))

    async def get_by_code(self, code: str) -> DiscoveredAssetRead:
        return DiscoveredAssetRead.model_validate(await self._up.get_json(f"{self.BASE}/by-code/{code}"))

    async def exists(self, code: str) -> bool:
        return await self._up.head(f"{self.BASE}/by-code/{code}")

    async def summaries(self, status: LifecycleStatus | None = None) -> list[DiscoveredAssetSummary]:
        params = {"status": status.value} if status else None
        data = await self._up.get_json(f"{self.BASE}/summaries", params=params)
        return [DiscoveredAssetSummary.model_validate(row) for row in data]

    async def create(self, payload: DiscoveredAssetCreate) -> DiscoveredAssetRead:
        body = payload.model_dump(mode="json", by_alias=True, exclude_none=True)
        return DiscoveredAssetRead.model_validate(await self._up.post_json(self.BASE, body))

    async def update(self, id: UUID, payload: DiscoveredAssetUpdate) -> DiscoveredAssetRead:
        body = payload.model_dump(mode="json", by_alias=True, exclude_unset=True)
        return DiscoveredAssetRead.model_validate(await self._up.put_json(f"{self.BASE}/{id}", body))

    async def change_status(self, id: UUID, status: LifecycleStatus) -> DiscoveredAssetRead:
        data = await self._up.patch_json(f"{self.BASE}/{id}/status", params={"status": status.value})
        return DiscoveredAssetRead.model_validate(data)

    async def delete(self, id: UUID) -> None:
        await self._up.delete(f"{self.BASE}/{id}")

    async def bulk_create(self, payloads: list[DiscoveredAssetCreate]) -> DiscoveredAssetBulkResult:
        body = [p.model_dump(mode="json", by_alias=True, exclude_none=True) for p in payloads]
        return DiscoveredAssetBulkResult.model_validate(await self._up.post_json(f"{self.BASE}/bulk", body))

    async def status_stats(self) -> dict[str, int]:
        return await self._up.get_json(f"{self.BASE}/stats/status")

    async def export_csv(self, flt: DiscoveredAssetFilter) -> bytes:
        return await self._up.get_bytes(f"{self.BASE}/export", params=to_query(flt))

    async def list_by_job_id(self, job_id: UUID, page: int = 0, size: int = 20) -> Page[DiscoveredAssetRead]:
        data = await self._up.get_json(
            f"{self.BASE}/by-job-id/{job_id}", params={"page": page, "size": size}
        )
        return Page[DiscoveredAssetRead].model_validate(data)

    async def list_by_matched_asset_id(self, matched_asset_id: UUID, page: int = 0, size: int = 20) -> Page[DiscoveredAssetRead]:
        data = await self._up.get_json(
            f"{self.BASE}/by-matched-asset-id/{matched_asset_id}", params={"page": page, "size": size}
        )
        return Page[DiscoveredAssetRead].model_validate(data)
