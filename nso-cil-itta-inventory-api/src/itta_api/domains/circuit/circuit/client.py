"""HTTP client for the Circuit endpoints of inventory-circuit-service."""
from __future__ import annotations

from uuid import UUID

from itta_core.http import UpstreamClient
from itta_core.models import LifecycleStatus
from itta_core.pagination import Page
from itta_api.domains.circuit.circuit.filters import CircuitFilter, to_query
from itta_api.domains.circuit.circuit.schemas import (
    CircuitBulkResult,
    CircuitCreate,
    CircuitRead,
    CircuitSummary,
    CircuitUpdate,
)


class CircuitClient:
    BASE = "/api/v1/circuits"

    def __init__(self, upstream: UpstreamClient) -> None:
        self._up = upstream

    async def search(
        self, flt: CircuitFilter, page: int = 0, size: int = 20, sort: str | None = None
    ) -> Page[CircuitRead]:
        params: dict[str, str | int] = {**to_query(flt), "page": page, "size": size}
        if sort:
            params["sort"] = sort
        return Page[CircuitRead].model_validate(await self._up.get_json(self.BASE, params=params))

    async def get(self, id: UUID) -> CircuitRead:
        return CircuitRead.model_validate(await self._up.get_json(f"{self.BASE}/{id}"))

    async def get_by_code(self, code: str) -> CircuitRead:
        return CircuitRead.model_validate(await self._up.get_json(f"{self.BASE}/by-code/{code}"))

    async def exists(self, code: str) -> bool:
        return await self._up.head(f"{self.BASE}/by-code/{code}")

    async def summaries(self, status: LifecycleStatus | None = None) -> list[CircuitSummary]:
        params = {"status": status.value} if status else None
        data = await self._up.get_json(f"{self.BASE}/summaries", params=params)
        return [CircuitSummary.model_validate(row) for row in data]

    async def create(self, payload: CircuitCreate) -> CircuitRead:
        body = payload.model_dump(mode="json", by_alias=True, exclude_none=True)
        return CircuitRead.model_validate(await self._up.post_json(self.BASE, body))

    async def update(self, id: UUID, payload: CircuitUpdate) -> CircuitRead:
        body = payload.model_dump(mode="json", by_alias=True, exclude_unset=True)
        return CircuitRead.model_validate(await self._up.put_json(f"{self.BASE}/{id}", body))

    async def change_status(self, id: UUID, status: LifecycleStatus) -> CircuitRead:
        data = await self._up.patch_json(f"{self.BASE}/{id}/status", params={"status": status.value})
        return CircuitRead.model_validate(data)

    async def delete(self, id: UUID) -> None:
        await self._up.delete(f"{self.BASE}/{id}")

    async def bulk_create(self, payloads: list[CircuitCreate]) -> CircuitBulkResult:
        body = [p.model_dump(mode="json", by_alias=True, exclude_none=True) for p in payloads]
        return CircuitBulkResult.model_validate(await self._up.post_json(f"{self.BASE}/bulk", body))

    async def status_stats(self) -> dict[str, int]:
        return await self._up.get_json(f"{self.BASE}/stats/status")

    async def export_csv(self, flt: CircuitFilter) -> bytes:
        return await self._up.get_bytes(f"{self.BASE}/export", params=to_query(flt))

    async def list_by_provider_id(self, provider_id: UUID, page: int = 0, size: int = 20) -> Page[CircuitRead]:
        data = await self._up.get_json(
            f"{self.BASE}/by-provider-id/{provider_id}", params={"page": page, "size": size}
        )
        return Page[CircuitRead].model_validate(data)

    async def list_by_a_site_id(self, a_site_id: UUID, page: int = 0, size: int = 20) -> Page[CircuitRead]:
        data = await self._up.get_json(
            f"{self.BASE}/by-a-site-id/{a_site_id}", params={"page": page, "size": size}
        )
        return Page[CircuitRead].model_validate(data)

    async def list_by_z_site_id(self, z_site_id: UUID, page: int = 0, size: int = 20) -> Page[CircuitRead]:
        data = await self._up.get_json(
            f"{self.BASE}/by-z-site-id/{z_site_id}", params={"page": page, "size": size}
        )
        return Page[CircuitRead].model_validate(data)
