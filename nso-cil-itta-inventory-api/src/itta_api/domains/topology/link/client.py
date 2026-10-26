"""HTTP client for the Link endpoints of inventory-topology-service."""
from __future__ import annotations

from uuid import UUID

from itta_core.http import UpstreamClient
from itta_core.models import LifecycleStatus
from itta_core.pagination import Page
from itta_api.domains.topology.link.filters import LinkFilter, to_query
from itta_api.domains.topology.link.schemas import (
    LinkBulkResult,
    LinkCreate,
    LinkRead,
    LinkSummary,
    LinkUpdate,
)


class LinkClient:
    BASE = "/api/v1/links"

    def __init__(self, upstream: UpstreamClient) -> None:
        self._up = upstream

    async def search(
        self, flt: LinkFilter, page: int = 0, size: int = 20, sort: str | None = None
    ) -> Page[LinkRead]:
        params: dict[str, str | int] = {**to_query(flt), "page": page, "size": size}
        if sort:
            params["sort"] = sort
        return Page[LinkRead].model_validate(await self._up.get_json(self.BASE, params=params))

    async def get(self, id: UUID) -> LinkRead:
        return LinkRead.model_validate(await self._up.get_json(f"{self.BASE}/{id}"))

    async def get_by_code(self, code: str) -> LinkRead:
        return LinkRead.model_validate(await self._up.get_json(f"{self.BASE}/by-code/{code}"))

    async def exists(self, code: str) -> bool:
        return await self._up.head(f"{self.BASE}/by-code/{code}")

    async def summaries(self, status: LifecycleStatus | None = None) -> list[LinkSummary]:
        params = {"status": status.value} if status else None
        data = await self._up.get_json(f"{self.BASE}/summaries", params=params)
        return [LinkSummary.model_validate(row) for row in data]

    async def create(self, payload: LinkCreate) -> LinkRead:
        body = payload.model_dump(mode="json", by_alias=True, exclude_none=True)
        return LinkRead.model_validate(await self._up.post_json(self.BASE, body))

    async def update(self, id: UUID, payload: LinkUpdate) -> LinkRead:
        body = payload.model_dump(mode="json", by_alias=True, exclude_unset=True)
        return LinkRead.model_validate(await self._up.put_json(f"{self.BASE}/{id}", body))

    async def change_status(self, id: UUID, status: LifecycleStatus) -> LinkRead:
        data = await self._up.patch_json(f"{self.BASE}/{id}/status", params={"status": status.value})
        return LinkRead.model_validate(data)

    async def delete(self, id: UUID) -> None:
        await self._up.delete(f"{self.BASE}/{id}")

    async def bulk_create(self, payloads: list[LinkCreate]) -> LinkBulkResult:
        body = [p.model_dump(mode="json", by_alias=True, exclude_none=True) for p in payloads]
        return LinkBulkResult.model_validate(await self._up.post_json(f"{self.BASE}/bulk", body))

    async def status_stats(self) -> dict[str, int]:
        return await self._up.get_json(f"{self.BASE}/stats/status")

    async def export_csv(self, flt: LinkFilter) -> bytes:
        return await self._up.get_bytes(f"{self.BASE}/export", params=to_query(flt))

    async def list_by_a_interface_id(self, a_interface_id: UUID, page: int = 0, size: int = 20) -> Page[LinkRead]:
        data = await self._up.get_json(
            f"{self.BASE}/by-a-interface-id/{a_interface_id}", params={"page": page, "size": size}
        )
        return Page[LinkRead].model_validate(data)

    async def list_by_z_interface_id(self, z_interface_id: UUID, page: int = 0, size: int = 20) -> Page[LinkRead]:
        data = await self._up.get_json(
            f"{self.BASE}/by-z-interface-id/{z_interface_id}", params={"page": page, "size": size}
        )
        return Page[LinkRead].model_validate(data)
