"""HTTP client for the Discovery Job endpoints of inventory-discovery-service."""
from __future__ import annotations

from uuid import UUID

from itta_core.http import UpstreamClient
from itta_core.models import LifecycleStatus
from itta_core.pagination import Page
from itta_api.domains.discovery.discovery_job.filters import DiscoveryJobFilter, to_query
from itta_api.domains.discovery.discovery_job.schemas import (
    DiscoveryJobBulkResult,
    DiscoveryJobCreate,
    DiscoveryJobRead,
    DiscoveryJobSummary,
    DiscoveryJobUpdate,
)


class DiscoveryJobClient:
    BASE = "/api/v1/discovery-jobs"

    def __init__(self, upstream: UpstreamClient) -> None:
        self._up = upstream

    async def search(
        self, flt: DiscoveryJobFilter, page: int = 0, size: int = 20, sort: str | None = None
    ) -> Page[DiscoveryJobRead]:
        params: dict[str, str | int] = {**to_query(flt), "page": page, "size": size}
        if sort:
            params["sort"] = sort
        return Page[DiscoveryJobRead].model_validate(await self._up.get_json(self.BASE, params=params))

    async def get(self, id: UUID) -> DiscoveryJobRead:
        return DiscoveryJobRead.model_validate(await self._up.get_json(f"{self.BASE}/{id}"))

    async def get_by_code(self, code: str) -> DiscoveryJobRead:
        return DiscoveryJobRead.model_validate(await self._up.get_json(f"{self.BASE}/by-code/{code}"))

    async def exists(self, code: str) -> bool:
        return await self._up.head(f"{self.BASE}/by-code/{code}")

    async def summaries(self, status: LifecycleStatus | None = None) -> list[DiscoveryJobSummary]:
        params = {"status": status.value} if status else None
        data = await self._up.get_json(f"{self.BASE}/summaries", params=params)
        return [DiscoveryJobSummary.model_validate(row) for row in data]

    async def create(self, payload: DiscoveryJobCreate) -> DiscoveryJobRead:
        body = payload.model_dump(mode="json", by_alias=True, exclude_none=True)
        return DiscoveryJobRead.model_validate(await self._up.post_json(self.BASE, body))

    async def update(self, id: UUID, payload: DiscoveryJobUpdate) -> DiscoveryJobRead:
        body = payload.model_dump(mode="json", by_alias=True, exclude_unset=True)
        return DiscoveryJobRead.model_validate(await self._up.put_json(f"{self.BASE}/{id}", body))

    async def change_status(self, id: UUID, status: LifecycleStatus) -> DiscoveryJobRead:
        data = await self._up.patch_json(f"{self.BASE}/{id}/status", params={"status": status.value})
        return DiscoveryJobRead.model_validate(data)

    async def delete(self, id: UUID) -> None:
        await self._up.delete(f"{self.BASE}/{id}")

    async def bulk_create(self, payloads: list[DiscoveryJobCreate]) -> DiscoveryJobBulkResult:
        body = [p.model_dump(mode="json", by_alias=True, exclude_none=True) for p in payloads]
        return DiscoveryJobBulkResult.model_validate(await self._up.post_json(f"{self.BASE}/bulk", body))

    async def status_stats(self) -> dict[str, int]:
        return await self._up.get_json(f"{self.BASE}/stats/status")

    async def export_csv(self, flt: DiscoveryJobFilter) -> bytes:
        return await self._up.get_bytes(f"{self.BASE}/export", params=to_query(flt))

    async def list_by_profile_id(self, profile_id: UUID, page: int = 0, size: int = 20) -> Page[DiscoveryJobRead]:
        data = await self._up.get_json(
            f"{self.BASE}/by-profile-id/{profile_id}", params={"page": page, "size": size}
        )
        return Page[DiscoveryJobRead].model_validate(data)
