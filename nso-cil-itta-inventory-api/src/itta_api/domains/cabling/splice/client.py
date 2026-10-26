"""HTTP client for the Splice endpoints of inventory-cabling-service."""
from __future__ import annotations

from uuid import UUID

from itta_core.http import UpstreamClient
from itta_core.models import LifecycleStatus
from itta_core.pagination import Page
from itta_api.domains.cabling.splice.filters import SpliceFilter, to_query
from itta_api.domains.cabling.splice.schemas import (
    SpliceBulkResult,
    SpliceCreate,
    SpliceRead,
    SpliceSummary,
    SpliceUpdate,
)


class SpliceClient:
    BASE = "/api/v1/splices"

    def __init__(self, upstream: UpstreamClient) -> None:
        self._up = upstream

    async def search(
        self, flt: SpliceFilter, page: int = 0, size: int = 20, sort: str | None = None
    ) -> Page[SpliceRead]:
        params: dict[str, str | int] = {**to_query(flt), "page": page, "size": size}
        if sort:
            params["sort"] = sort
        return Page[SpliceRead].model_validate(await self._up.get_json(self.BASE, params=params))

    async def get(self, id: UUID) -> SpliceRead:
        return SpliceRead.model_validate(await self._up.get_json(f"{self.BASE}/{id}"))

    async def get_by_code(self, code: str) -> SpliceRead:
        return SpliceRead.model_validate(await self._up.get_json(f"{self.BASE}/by-code/{code}"))

    async def exists(self, code: str) -> bool:
        return await self._up.head(f"{self.BASE}/by-code/{code}")

    async def summaries(self, status: LifecycleStatus | None = None) -> list[SpliceSummary]:
        params = {"status": status.value} if status else None
        data = await self._up.get_json(f"{self.BASE}/summaries", params=params)
        return [SpliceSummary.model_validate(row) for row in data]

    async def create(self, payload: SpliceCreate) -> SpliceRead:
        body = payload.model_dump(mode="json", by_alias=True, exclude_none=True)
        return SpliceRead.model_validate(await self._up.post_json(self.BASE, body))

    async def update(self, id: UUID, payload: SpliceUpdate) -> SpliceRead:
        body = payload.model_dump(mode="json", by_alias=True, exclude_unset=True)
        return SpliceRead.model_validate(await self._up.put_json(f"{self.BASE}/{id}", body))

    async def change_status(self, id: UUID, status: LifecycleStatus) -> SpliceRead:
        data = await self._up.patch_json(f"{self.BASE}/{id}/status", params={"status": status.value})
        return SpliceRead.model_validate(data)

    async def delete(self, id: UUID) -> None:
        await self._up.delete(f"{self.BASE}/{id}")

    async def bulk_create(self, payloads: list[SpliceCreate]) -> SpliceBulkResult:
        body = [p.model_dump(mode="json", by_alias=True, exclude_none=True) for p in payloads]
        return SpliceBulkResult.model_validate(await self._up.post_json(f"{self.BASE}/bulk", body))

    async def status_stats(self) -> dict[str, int]:
        return await self._up.get_json(f"{self.BASE}/stats/status")

    async def export_csv(self, flt: SpliceFilter) -> bytes:
        return await self._up.get_bytes(f"{self.BASE}/export", params=to_query(flt))

    async def list_by_a_strand_id(self, a_strand_id: UUID, page: int = 0, size: int = 20) -> Page[SpliceRead]:
        data = await self._up.get_json(
            f"{self.BASE}/by-a-strand-id/{a_strand_id}", params={"page": page, "size": size}
        )
        return Page[SpliceRead].model_validate(data)

    async def list_by_z_strand_id(self, z_strand_id: UUID, page: int = 0, size: int = 20) -> Page[SpliceRead]:
        data = await self._up.get_json(
            f"{self.BASE}/by-z-strand-id/{z_strand_id}", params={"page": page, "size": size}
        )
        return Page[SpliceRead].model_validate(data)
