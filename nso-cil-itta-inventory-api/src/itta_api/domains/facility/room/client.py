"""HTTP client for the Room endpoints of inventory-facility-service."""
from __future__ import annotations

from uuid import UUID

from itta_core.http import UpstreamClient
from itta_core.models import LifecycleStatus
from itta_core.pagination import Page
from itta_api.domains.facility.room.filters import RoomFilter, to_query
from itta_api.domains.facility.room.schemas import (
    RoomBulkResult,
    RoomCreate,
    RoomRead,
    RoomSummary,
    RoomUpdate,
)


class RoomClient:
    BASE = "/api/v1/rooms"

    def __init__(self, upstream: UpstreamClient) -> None:
        self._up = upstream

    async def search(
        self, flt: RoomFilter, page: int = 0, size: int = 20, sort: str | None = None
    ) -> Page[RoomRead]:
        params: dict[str, str | int] = {**to_query(flt), "page": page, "size": size}
        if sort:
            params["sort"] = sort
        return Page[RoomRead].model_validate(await self._up.get_json(self.BASE, params=params))

    async def get(self, id: UUID) -> RoomRead:
        return RoomRead.model_validate(await self._up.get_json(f"{self.BASE}/{id}"))

    async def get_by_code(self, code: str) -> RoomRead:
        return RoomRead.model_validate(await self._up.get_json(f"{self.BASE}/by-code/{code}"))

    async def exists(self, code: str) -> bool:
        return await self._up.head(f"{self.BASE}/by-code/{code}")

    async def summaries(self, status: LifecycleStatus | None = None) -> list[RoomSummary]:
        params = {"status": status.value} if status else None
        data = await self._up.get_json(f"{self.BASE}/summaries", params=params)
        return [RoomSummary.model_validate(row) for row in data]

    async def create(self, payload: RoomCreate) -> RoomRead:
        body = payload.model_dump(mode="json", by_alias=True, exclude_none=True)
        return RoomRead.model_validate(await self._up.post_json(self.BASE, body))

    async def update(self, id: UUID, payload: RoomUpdate) -> RoomRead:
        body = payload.model_dump(mode="json", by_alias=True, exclude_unset=True)
        return RoomRead.model_validate(await self._up.put_json(f"{self.BASE}/{id}", body))

    async def change_status(self, id: UUID, status: LifecycleStatus) -> RoomRead:
        data = await self._up.patch_json(f"{self.BASE}/{id}/status", params={"status": status.value})
        return RoomRead.model_validate(data)

    async def delete(self, id: UUID) -> None:
        await self._up.delete(f"{self.BASE}/{id}")

    async def bulk_create(self, payloads: list[RoomCreate]) -> RoomBulkResult:
        body = [p.model_dump(mode="json", by_alias=True, exclude_none=True) for p in payloads]
        return RoomBulkResult.model_validate(await self._up.post_json(f"{self.BASE}/bulk", body))

    async def status_stats(self) -> dict[str, int]:
        return await self._up.get_json(f"{self.BASE}/stats/status")

    async def export_csv(self, flt: RoomFilter) -> bytes:
        return await self._up.get_bytes(f"{self.BASE}/export", params=to_query(flt))

    async def list_by_floor_id(self, floor_id: UUID, page: int = 0, size: int = 20) -> Page[RoomRead]:
        data = await self._up.get_json(
            f"{self.BASE}/by-floor-id/{floor_id}", params={"page": page, "size": size}
        )
        return Page[RoomRead].model_validate(data)
