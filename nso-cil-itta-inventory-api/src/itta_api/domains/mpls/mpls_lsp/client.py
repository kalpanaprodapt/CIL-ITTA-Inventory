"""HTTP client for the Mpls Lsp endpoints of inventory-mpls-service."""
from __future__ import annotations

from uuid import UUID

from itta_core.http import UpstreamClient
from itta_core.models import LifecycleStatus
from itta_core.pagination import Page
from itta_api.domains.mpls.mpls_lsp.filters import MplsLspFilter, to_query
from itta_api.domains.mpls.mpls_lsp.schemas import (
    MplsLspBulkResult,
    MplsLspCreate,
    MplsLspRead,
    MplsLspSummary,
    MplsLspUpdate,
)


class MplsLspClient:
    BASE = "/api/v1/mpls-lsps"

    def __init__(self, upstream: UpstreamClient) -> None:
        self._up = upstream

    async def search(
        self, flt: MplsLspFilter, page: int = 0, size: int = 20, sort: str | None = None
    ) -> Page[MplsLspRead]:
        params: dict[str, str | int] = {**to_query(flt), "page": page, "size": size}
        if sort:
            params["sort"] = sort
        return Page[MplsLspRead].model_validate(await self._up.get_json(self.BASE, params=params))

    async def get(self, id: UUID) -> MplsLspRead:
        return MplsLspRead.model_validate(await self._up.get_json(f"{self.BASE}/{id}"))

    async def get_by_code(self, code: str) -> MplsLspRead:
        return MplsLspRead.model_validate(await self._up.get_json(f"{self.BASE}/by-code/{code}"))

    async def exists(self, code: str) -> bool:
        return await self._up.head(f"{self.BASE}/by-code/{code}")

    async def summaries(self, status: LifecycleStatus | None = None) -> list[MplsLspSummary]:
        params = {"status": status.value} if status else None
        data = await self._up.get_json(f"{self.BASE}/summaries", params=params)
        return [MplsLspSummary.model_validate(row) for row in data]

    async def create(self, payload: MplsLspCreate) -> MplsLspRead:
        body = payload.model_dump(mode="json", by_alias=True, exclude_none=True)
        return MplsLspRead.model_validate(await self._up.post_json(self.BASE, body))

    async def update(self, id: UUID, payload: MplsLspUpdate) -> MplsLspRead:
        body = payload.model_dump(mode="json", by_alias=True, exclude_unset=True)
        return MplsLspRead.model_validate(await self._up.put_json(f"{self.BASE}/{id}", body))

    async def change_status(self, id: UUID, status: LifecycleStatus) -> MplsLspRead:
        data = await self._up.patch_json(f"{self.BASE}/{id}/status", params={"status": status.value})
        return MplsLspRead.model_validate(data)

    async def delete(self, id: UUID) -> None:
        await self._up.delete(f"{self.BASE}/{id}")

    async def bulk_create(self, payloads: list[MplsLspCreate]) -> MplsLspBulkResult:
        body = [p.model_dump(mode="json", by_alias=True, exclude_none=True) for p in payloads]
        return MplsLspBulkResult.model_validate(await self._up.post_json(f"{self.BASE}/bulk", body))

    async def status_stats(self) -> dict[str, int]:
        return await self._up.get_json(f"{self.BASE}/stats/status")

    async def export_csv(self, flt: MplsLspFilter) -> bytes:
        return await self._up.get_bytes(f"{self.BASE}/export", params=to_query(flt))

    async def list_by_head_end_id(self, head_end_id: UUID, page: int = 0, size: int = 20) -> Page[MplsLspRead]:
        data = await self._up.get_json(
            f"{self.BASE}/by-head-end-id/{head_end_id}", params={"page": page, "size": size}
        )
        return Page[MplsLspRead].model_validate(data)

    async def list_by_tail_end_id(self, tail_end_id: UUID, page: int = 0, size: int = 20) -> Page[MplsLspRead]:
        data = await self._up.get_json(
            f"{self.BASE}/by-tail-end-id/{tail_end_id}", params={"page": page, "size": size}
        )
        return Page[MplsLspRead].model_validate(data)
