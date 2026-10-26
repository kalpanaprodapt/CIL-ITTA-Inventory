"""HTTP client for the Bgp Session endpoints of inventory-switching-service."""
from __future__ import annotations

from uuid import UUID

from itta_core.http import UpstreamClient
from itta_core.models import LifecycleStatus
from itta_core.pagination import Page
from itta_api.domains.switching.bgp_session.filters import BgpSessionFilter, to_query
from itta_api.domains.switching.bgp_session.schemas import (
    BgpSessionBulkResult,
    BgpSessionCreate,
    BgpSessionRead,
    BgpSessionSummary,
    BgpSessionUpdate,
)


class BgpSessionClient:
    BASE = "/api/v1/bgp-sessions"

    def __init__(self, upstream: UpstreamClient) -> None:
        self._up = upstream

    async def search(
        self, flt: BgpSessionFilter, page: int = 0, size: int = 20, sort: str | None = None
    ) -> Page[BgpSessionRead]:
        params: dict[str, str | int] = {**to_query(flt), "page": page, "size": size}
        if sort:
            params["sort"] = sort
        return Page[BgpSessionRead].model_validate(await self._up.get_json(self.BASE, params=params))

    async def get(self, id: UUID) -> BgpSessionRead:
        return BgpSessionRead.model_validate(await self._up.get_json(f"{self.BASE}/{id}"))

    async def get_by_code(self, code: str) -> BgpSessionRead:
        return BgpSessionRead.model_validate(await self._up.get_json(f"{self.BASE}/by-code/{code}"))

    async def exists(self, code: str) -> bool:
        return await self._up.head(f"{self.BASE}/by-code/{code}")

    async def summaries(self, status: LifecycleStatus | None = None) -> list[BgpSessionSummary]:
        params = {"status": status.value} if status else None
        data = await self._up.get_json(f"{self.BASE}/summaries", params=params)
        return [BgpSessionSummary.model_validate(row) for row in data]

    async def create(self, payload: BgpSessionCreate) -> BgpSessionRead:
        body = payload.model_dump(mode="json", by_alias=True, exclude_none=True)
        return BgpSessionRead.model_validate(await self._up.post_json(self.BASE, body))

    async def update(self, id: UUID, payload: BgpSessionUpdate) -> BgpSessionRead:
        body = payload.model_dump(mode="json", by_alias=True, exclude_unset=True)
        return BgpSessionRead.model_validate(await self._up.put_json(f"{self.BASE}/{id}", body))

    async def change_status(self, id: UUID, status: LifecycleStatus) -> BgpSessionRead:
        data = await self._up.patch_json(f"{self.BASE}/{id}/status", params={"status": status.value})
        return BgpSessionRead.model_validate(data)

    async def delete(self, id: UUID) -> None:
        await self._up.delete(f"{self.BASE}/{id}")

    async def bulk_create(self, payloads: list[BgpSessionCreate]) -> BgpSessionBulkResult:
        body = [p.model_dump(mode="json", by_alias=True, exclude_none=True) for p in payloads]
        return BgpSessionBulkResult.model_validate(await self._up.post_json(f"{self.BASE}/bulk", body))

    async def status_stats(self) -> dict[str, int]:
        return await self._up.get_json(f"{self.BASE}/stats/status")

    async def export_csv(self, flt: BgpSessionFilter) -> bytes:
        return await self._up.get_bytes(f"{self.BASE}/export", params=to_query(flt))

    async def list_by_local_device_id(self, local_device_id: UUID, page: int = 0, size: int = 20) -> Page[BgpSessionRead]:
        data = await self._up.get_json(
            f"{self.BASE}/by-local-device-id/{local_device_id}", params={"page": page, "size": size}
        )
        return Page[BgpSessionRead].model_validate(data)
