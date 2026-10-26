"""HTTP client for the Hypervisor endpoints of inventory-virtual-service."""
from __future__ import annotations

from uuid import UUID

from itta_core.http import UpstreamClient
from itta_core.models import LifecycleStatus
from itta_core.pagination import Page
from itta_api.domains.virtual.hypervisor.filters import HypervisorFilter, to_query
from itta_api.domains.virtual.hypervisor.schemas import (
    HypervisorBulkResult,
    HypervisorCreate,
    HypervisorRead,
    HypervisorSummary,
    HypervisorUpdate,
)


class HypervisorClient:
    BASE = "/api/v1/hypervisors"

    def __init__(self, upstream: UpstreamClient) -> None:
        self._up = upstream

    async def search(
        self, flt: HypervisorFilter, page: int = 0, size: int = 20, sort: str | None = None
    ) -> Page[HypervisorRead]:
        params: dict[str, str | int] = {**to_query(flt), "page": page, "size": size}
        if sort:
            params["sort"] = sort
        return Page[HypervisorRead].model_validate(await self._up.get_json(self.BASE, params=params))

    async def get(self, id: UUID) -> HypervisorRead:
        return HypervisorRead.model_validate(await self._up.get_json(f"{self.BASE}/{id}"))

    async def get_by_code(self, code: str) -> HypervisorRead:
        return HypervisorRead.model_validate(await self._up.get_json(f"{self.BASE}/by-code/{code}"))

    async def exists(self, code: str) -> bool:
        return await self._up.head(f"{self.BASE}/by-code/{code}")

    async def summaries(self, status: LifecycleStatus | None = None) -> list[HypervisorSummary]:
        params = {"status": status.value} if status else None
        data = await self._up.get_json(f"{self.BASE}/summaries", params=params)
        return [HypervisorSummary.model_validate(row) for row in data]

    async def create(self, payload: HypervisorCreate) -> HypervisorRead:
        body = payload.model_dump(mode="json", by_alias=True, exclude_none=True)
        return HypervisorRead.model_validate(await self._up.post_json(self.BASE, body))

    async def update(self, id: UUID, payload: HypervisorUpdate) -> HypervisorRead:
        body = payload.model_dump(mode="json", by_alias=True, exclude_unset=True)
        return HypervisorRead.model_validate(await self._up.put_json(f"{self.BASE}/{id}", body))

    async def change_status(self, id: UUID, status: LifecycleStatus) -> HypervisorRead:
        data = await self._up.patch_json(f"{self.BASE}/{id}/status", params={"status": status.value})
        return HypervisorRead.model_validate(data)

    async def delete(self, id: UUID) -> None:
        await self._up.delete(f"{self.BASE}/{id}")

    async def bulk_create(self, payloads: list[HypervisorCreate]) -> HypervisorBulkResult:
        body = [p.model_dump(mode="json", by_alias=True, exclude_none=True) for p in payloads]
        return HypervisorBulkResult.model_validate(await self._up.post_json(f"{self.BASE}/bulk", body))

    async def status_stats(self) -> dict[str, int]:
        return await self._up.get_json(f"{self.BASE}/stats/status")

    async def export_csv(self, flt: HypervisorFilter) -> bytes:
        return await self._up.get_bytes(f"{self.BASE}/export", params=to_query(flt))

    async def list_by_host_device_id(self, host_device_id: UUID, page: int = 0, size: int = 20) -> Page[HypervisorRead]:
        data = await self._up.get_json(
            f"{self.BASE}/by-host-device-id/{host_device_id}", params={"page": page, "size": size}
        )
        return Page[HypervisorRead].model_validate(data)
