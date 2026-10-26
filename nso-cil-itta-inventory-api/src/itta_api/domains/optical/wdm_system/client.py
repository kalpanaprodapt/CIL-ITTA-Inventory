"""HTTP client for the Wdm System endpoints of inventory-optical-service."""
from __future__ import annotations

from uuid import UUID

from itta_core.http import UpstreamClient
from itta_core.models import LifecycleStatus
from itta_core.pagination import Page
from itta_api.domains.optical.wdm_system.filters import WdmSystemFilter, to_query
from itta_api.domains.optical.wdm_system.schemas import (
    WdmSystemBulkResult,
    WdmSystemCreate,
    WdmSystemRead,
    WdmSystemSummary,
    WdmSystemUpdate,
)


class WdmSystemClient:
    BASE = "/api/v1/wdm-systems"

    def __init__(self, upstream: UpstreamClient) -> None:
        self._up = upstream

    async def search(
        self, flt: WdmSystemFilter, page: int = 0, size: int = 20, sort: str | None = None
    ) -> Page[WdmSystemRead]:
        params: dict[str, str | int] = {**to_query(flt), "page": page, "size": size}
        if sort:
            params["sort"] = sort
        return Page[WdmSystemRead].model_validate(await self._up.get_json(self.BASE, params=params))

    async def get(self, id: UUID) -> WdmSystemRead:
        return WdmSystemRead.model_validate(await self._up.get_json(f"{self.BASE}/{id}"))

    async def get_by_code(self, code: str) -> WdmSystemRead:
        return WdmSystemRead.model_validate(await self._up.get_json(f"{self.BASE}/by-code/{code}"))

    async def exists(self, code: str) -> bool:
        return await self._up.head(f"{self.BASE}/by-code/{code}")

    async def summaries(self, status: LifecycleStatus | None = None) -> list[WdmSystemSummary]:
        params = {"status": status.value} if status else None
        data = await self._up.get_json(f"{self.BASE}/summaries", params=params)
        return [WdmSystemSummary.model_validate(row) for row in data]

    async def create(self, payload: WdmSystemCreate) -> WdmSystemRead:
        body = payload.model_dump(mode="json", by_alias=True, exclude_none=True)
        return WdmSystemRead.model_validate(await self._up.post_json(self.BASE, body))

    async def update(self, id: UUID, payload: WdmSystemUpdate) -> WdmSystemRead:
        body = payload.model_dump(mode="json", by_alias=True, exclude_unset=True)
        return WdmSystemRead.model_validate(await self._up.put_json(f"{self.BASE}/{id}", body))

    async def change_status(self, id: UUID, status: LifecycleStatus) -> WdmSystemRead:
        data = await self._up.patch_json(f"{self.BASE}/{id}/status", params={"status": status.value})
        return WdmSystemRead.model_validate(data)

    async def delete(self, id: UUID) -> None:
        await self._up.delete(f"{self.BASE}/{id}")

    async def bulk_create(self, payloads: list[WdmSystemCreate]) -> WdmSystemBulkResult:
        body = [p.model_dump(mode="json", by_alias=True, exclude_none=True) for p in payloads]
        return WdmSystemBulkResult.model_validate(await self._up.post_json(f"{self.BASE}/bulk", body))

    async def status_stats(self) -> dict[str, int]:
        return await self._up.get_json(f"{self.BASE}/stats/status")

    async def export_csv(self, flt: WdmSystemFilter) -> bytes:
        return await self._up.get_bytes(f"{self.BASE}/export", params=to_query(flt))

    async def list_by_vendor_id(self, vendor_id: UUID, page: int = 0, size: int = 20) -> Page[WdmSystemRead]:
        data = await self._up.get_json(
            f"{self.BASE}/by-vendor-id/{vendor_id}", params={"page": page, "size": size}
        )
        return Page[WdmSystemRead].model_validate(data)
