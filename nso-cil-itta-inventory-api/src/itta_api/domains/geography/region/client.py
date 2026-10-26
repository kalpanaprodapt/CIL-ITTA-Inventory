"""HTTP client for the Region endpoints of inventory-geography-service."""
from __future__ import annotations

from uuid import UUID

from itta_core.http import UpstreamClient
from itta_core.models import LifecycleStatus
from itta_core.pagination import Page
from itta_api.domains.geography.region.filters import RegionFilter, to_query
from itta_api.domains.geography.region.schemas import (
    RegionBulkResult,
    RegionCreate,
    RegionRead,
    RegionSummary,
    RegionUpdate,
)


class RegionClient:
    BASE = "/api/v1/regions"

    def __init__(self, upstream: UpstreamClient) -> None:
        self._up = upstream

    async def search(
        self, flt: RegionFilter, page: int = 0, size: int = 20, sort: str | None = None
    ) -> Page[RegionRead]:
        params: dict[str, str | int] = {**to_query(flt), "page": page, "size": size}
        if sort:
            params["sort"] = sort
        return Page[RegionRead].model_validate(await self._up.get_json(self.BASE, params=params))

    async def get(self, id: UUID) -> RegionRead:
        return RegionRead.model_validate(await self._up.get_json(f"{self.BASE}/{id}"))

    async def get_by_code(self, code: str) -> RegionRead:
        return RegionRead.model_validate(await self._up.get_json(f"{self.BASE}/by-code/{code}"))

    async def exists(self, code: str) -> bool:
        return await self._up.head(f"{self.BASE}/by-code/{code}")

    async def summaries(self, status: LifecycleStatus | None = None) -> list[RegionSummary]:
        params = {"status": status.value} if status else None
        data = await self._up.get_json(f"{self.BASE}/summaries", params=params)
        return [RegionSummary.model_validate(row) for row in data]

    async def create(self, payload: RegionCreate) -> RegionRead:
        body = payload.model_dump(mode="json", by_alias=True, exclude_none=True)
        return RegionRead.model_validate(await self._up.post_json(self.BASE, body))

    async def update(self, id: UUID, payload: RegionUpdate) -> RegionRead:
        body = payload.model_dump(mode="json", by_alias=True, exclude_unset=True)
        return RegionRead.model_validate(await self._up.put_json(f"{self.BASE}/{id}", body))

    async def change_status(self, id: UUID, status: LifecycleStatus) -> RegionRead:
        data = await self._up.patch_json(f"{self.BASE}/{id}/status", params={"status": status.value})
        return RegionRead.model_validate(data)

    async def delete(self, id: UUID) -> None:
        await self._up.delete(f"{self.BASE}/{id}")

    async def bulk_create(self, payloads: list[RegionCreate]) -> RegionBulkResult:
        body = [p.model_dump(mode="json", by_alias=True, exclude_none=True) for p in payloads]
        return RegionBulkResult.model_validate(await self._up.post_json(f"{self.BASE}/bulk", body))

    async def status_stats(self) -> dict[str, int]:
        return await self._up.get_json(f"{self.BASE}/stats/status")

    async def export_csv(self, flt: RegionFilter) -> bytes:
        return await self._up.get_bytes(f"{self.BASE}/export", params=to_query(flt))

    async def list_by_country_id(self, country_id: UUID, page: int = 0, size: int = 20) -> Page[RegionRead]:
        data = await self._up.get_json(
            f"{self.BASE}/by-country-id/{country_id}", params={"page": page, "size": size}
        )
        return Page[RegionRead].model_validate(data)
