"""HTTP client for the Patch endpoints of inventory-software-service."""
from __future__ import annotations

from uuid import UUID

from itta_core.http import UpstreamClient
from itta_core.models import LifecycleStatus
from itta_core.pagination import Page
from itta_api.domains.software.patch.filters import PatchFilter, to_query
from itta_api.domains.software.patch.schemas import (
    PatchBulkResult,
    PatchCreate,
    PatchRead,
    PatchSummary,
    PatchUpdate,
)


class PatchClient:
    BASE = "/api/v1/patchs"

    def __init__(self, upstream: UpstreamClient) -> None:
        self._up = upstream

    async def search(
        self, flt: PatchFilter, page: int = 0, size: int = 20, sort: str | None = None
    ) -> Page[PatchRead]:
        params: dict[str, str | int] = {**to_query(flt), "page": page, "size": size}
        if sort:
            params["sort"] = sort
        return Page[PatchRead].model_validate(await self._up.get_json(self.BASE, params=params))

    async def get(self, id: UUID) -> PatchRead:
        return PatchRead.model_validate(await self._up.get_json(f"{self.BASE}/{id}"))

    async def get_by_code(self, code: str) -> PatchRead:
        return PatchRead.model_validate(await self._up.get_json(f"{self.BASE}/by-code/{code}"))

    async def exists(self, code: str) -> bool:
        return await self._up.head(f"{self.BASE}/by-code/{code}")

    async def summaries(self, status: LifecycleStatus | None = None) -> list[PatchSummary]:
        params = {"status": status.value} if status else None
        data = await self._up.get_json(f"{self.BASE}/summaries", params=params)
        return [PatchSummary.model_validate(row) for row in data]

    async def create(self, payload: PatchCreate) -> PatchRead:
        body = payload.model_dump(mode="json", by_alias=True, exclude_none=True)
        return PatchRead.model_validate(await self._up.post_json(self.BASE, body))

    async def update(self, id: UUID, payload: PatchUpdate) -> PatchRead:
        body = payload.model_dump(mode="json", by_alias=True, exclude_unset=True)
        return PatchRead.model_validate(await self._up.put_json(f"{self.BASE}/{id}", body))

    async def change_status(self, id: UUID, status: LifecycleStatus) -> PatchRead:
        data = await self._up.patch_json(f"{self.BASE}/{id}/status", params={"status": status.value})
        return PatchRead.model_validate(data)

    async def delete(self, id: UUID) -> None:
        await self._up.delete(f"{self.BASE}/{id}")

    async def bulk_create(self, payloads: list[PatchCreate]) -> PatchBulkResult:
        body = [p.model_dump(mode="json", by_alias=True, exclude_none=True) for p in payloads]
        return PatchBulkResult.model_validate(await self._up.post_json(f"{self.BASE}/bulk", body))

    async def status_stats(self) -> dict[str, int]:
        return await self._up.get_json(f"{self.BASE}/stats/status")

    async def export_csv(self, flt: PatchFilter) -> bytes:
        return await self._up.get_bytes(f"{self.BASE}/export", params=to_query(flt))

    async def list_by_version_id(self, version_id: UUID, page: int = 0, size: int = 20) -> Page[PatchRead]:
        data = await self._up.get_json(
            f"{self.BASE}/by-version-id/{version_id}", params={"page": page, "size": size}
        )
        return Page[PatchRead].model_validate(data)
