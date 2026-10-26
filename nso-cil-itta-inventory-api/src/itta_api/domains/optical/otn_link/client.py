"""HTTP client for the Otn Link endpoints of inventory-optical-service."""
from __future__ import annotations

from uuid import UUID

from itta_core.http import UpstreamClient
from itta_core.models import LifecycleStatus
from itta_core.pagination import Page
from itta_api.domains.optical.otn_link.filters import OtnLinkFilter, to_query
from itta_api.domains.optical.otn_link.schemas import (
    OtnLinkBulkResult,
    OtnLinkCreate,
    OtnLinkRead,
    OtnLinkSummary,
    OtnLinkUpdate,
)


class OtnLinkClient:
    BASE = "/api/v1/otn-links"

    def __init__(self, upstream: UpstreamClient) -> None:
        self._up = upstream

    async def search(
        self, flt: OtnLinkFilter, page: int = 0, size: int = 20, sort: str | None = None
    ) -> Page[OtnLinkRead]:
        params: dict[str, str | int] = {**to_query(flt), "page": page, "size": size}
        if sort:
            params["sort"] = sort
        return Page[OtnLinkRead].model_validate(await self._up.get_json(self.BASE, params=params))

    async def get(self, id: UUID) -> OtnLinkRead:
        return OtnLinkRead.model_validate(await self._up.get_json(f"{self.BASE}/{id}"))

    async def get_by_code(self, code: str) -> OtnLinkRead:
        return OtnLinkRead.model_validate(await self._up.get_json(f"{self.BASE}/by-code/{code}"))

    async def exists(self, code: str) -> bool:
        return await self._up.head(f"{self.BASE}/by-code/{code}")

    async def summaries(self, status: LifecycleStatus | None = None) -> list[OtnLinkSummary]:
        params = {"status": status.value} if status else None
        data = await self._up.get_json(f"{self.BASE}/summaries", params=params)
        return [OtnLinkSummary.model_validate(row) for row in data]

    async def create(self, payload: OtnLinkCreate) -> OtnLinkRead:
        body = payload.model_dump(mode="json", by_alias=True, exclude_none=True)
        return OtnLinkRead.model_validate(await self._up.post_json(self.BASE, body))

    async def update(self, id: UUID, payload: OtnLinkUpdate) -> OtnLinkRead:
        body = payload.model_dump(mode="json", by_alias=True, exclude_unset=True)
        return OtnLinkRead.model_validate(await self._up.put_json(f"{self.BASE}/{id}", body))

    async def change_status(self, id: UUID, status: LifecycleStatus) -> OtnLinkRead:
        data = await self._up.patch_json(f"{self.BASE}/{id}/status", params={"status": status.value})
        return OtnLinkRead.model_validate(data)

    async def delete(self, id: UUID) -> None:
        await self._up.delete(f"{self.BASE}/{id}")

    async def bulk_create(self, payloads: list[OtnLinkCreate]) -> OtnLinkBulkResult:
        body = [p.model_dump(mode="json", by_alias=True, exclude_none=True) for p in payloads]
        return OtnLinkBulkResult.model_validate(await self._up.post_json(f"{self.BASE}/bulk", body))

    async def status_stats(self) -> dict[str, int]:
        return await self._up.get_json(f"{self.BASE}/stats/status")

    async def export_csv(self, flt: OtnLinkFilter) -> bytes:
        return await self._up.get_bytes(f"{self.BASE}/export", params=to_query(flt))

    async def list_by_a_node_id(self, a_node_id: UUID, page: int = 0, size: int = 20) -> Page[OtnLinkRead]:
        data = await self._up.get_json(
            f"{self.BASE}/by-a-node-id/{a_node_id}", params={"page": page, "size": size}
        )
        return Page[OtnLinkRead].model_validate(data)

    async def list_by_z_node_id(self, z_node_id: UUID, page: int = 0, size: int = 20) -> Page[OtnLinkRead]:
        data = await self._up.get_json(
            f"{self.BASE}/by-z-node-id/{z_node_id}", params={"page": page, "size": size}
        )
        return Page[OtnLinkRead].model_validate(data)
