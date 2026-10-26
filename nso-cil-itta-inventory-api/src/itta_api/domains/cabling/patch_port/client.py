"""HTTP client for the Patch Port endpoints of inventory-cabling-service."""
from __future__ import annotations

from uuid import UUID

from itta_core.http import UpstreamClient
from itta_core.models import LifecycleStatus
from itta_core.pagination import Page
from itta_api.domains.cabling.patch_port.filters import PatchPortFilter, to_query
from itta_api.domains.cabling.patch_port.schemas import (
    PatchPortBulkResult,
    PatchPortCreate,
    PatchPortRead,
    PatchPortSummary,
    PatchPortUpdate,
)


class PatchPortClient:
    BASE = "/api/v1/patch-ports"

    def __init__(self, upstream: UpstreamClient) -> None:
        self._up = upstream

    async def search(
        self, flt: PatchPortFilter, page: int = 0, size: int = 20, sort: str | None = None
    ) -> Page[PatchPortRead]:
        params: dict[str, str | int] = {**to_query(flt), "page": page, "size": size}
        if sort:
            params["sort"] = sort
        return Page[PatchPortRead].model_validate(await self._up.get_json(self.BASE, params=params))

    async def get(self, id: UUID) -> PatchPortRead:
        return PatchPortRead.model_validate(await self._up.get_json(f"{self.BASE}/{id}"))

    async def get_by_code(self, code: str) -> PatchPortRead:
        return PatchPortRead.model_validate(await self._up.get_json(f"{self.BASE}/by-code/{code}"))

    async def exists(self, code: str) -> bool:
        return await self._up.head(f"{self.BASE}/by-code/{code}")

    async def summaries(self, status: LifecycleStatus | None = None) -> list[PatchPortSummary]:
        params = {"status": status.value} if status else None
        data = await self._up.get_json(f"{self.BASE}/summaries", params=params)
        return [PatchPortSummary.model_validate(row) for row in data]

    async def create(self, payload: PatchPortCreate) -> PatchPortRead:
        body = payload.model_dump(mode="json", by_alias=True, exclude_none=True)
        return PatchPortRead.model_validate(await self._up.post_json(self.BASE, body))

    async def update(self, id: UUID, payload: PatchPortUpdate) -> PatchPortRead:
        body = payload.model_dump(mode="json", by_alias=True, exclude_unset=True)
        return PatchPortRead.model_validate(await self._up.put_json(f"{self.BASE}/{id}", body))

    async def change_status(self, id: UUID, status: LifecycleStatus) -> PatchPortRead:
        data = await self._up.patch_json(f"{self.BASE}/{id}/status", params={"status": status.value})
        return PatchPortRead.model_validate(data)

    async def delete(self, id: UUID) -> None:
        await self._up.delete(f"{self.BASE}/{id}")

    async def bulk_create(self, payloads: list[PatchPortCreate]) -> PatchPortBulkResult:
        body = [p.model_dump(mode="json", by_alias=True, exclude_none=True) for p in payloads]
        return PatchPortBulkResult.model_validate(await self._up.post_json(f"{self.BASE}/bulk", body))

    async def status_stats(self) -> dict[str, int]:
        return await self._up.get_json(f"{self.BASE}/stats/status")

    async def export_csv(self, flt: PatchPortFilter) -> bytes:
        return await self._up.get_bytes(f"{self.BASE}/export", params=to_query(flt))

    async def list_by_panel_id(self, panel_id: UUID, page: int = 0, size: int = 20) -> Page[PatchPortRead]:
        data = await self._up.get_json(
            f"{self.BASE}/by-panel-id/{panel_id}", params={"page": page, "size": size}
        )
        return Page[PatchPortRead].model_validate(data)

    async def list_by_connected_cable_id(self, connected_cable_id: UUID, page: int = 0, size: int = 20) -> Page[PatchPortRead]:
        data = await self._up.get_json(
            f"{self.BASE}/by-connected-cable-id/{connected_cable_id}", params={"page": page, "size": size}
        )
        return Page[PatchPortRead].model_validate(data)
