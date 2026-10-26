"""HTTP client for the Power Supply endpoints of inventory-device-service."""
from __future__ import annotations

from uuid import UUID

from itta_core.http import UpstreamClient
from itta_core.models import LifecycleStatus
from itta_core.pagination import Page
from itta_api.domains.device.power_supply.filters import PowerSupplyFilter, to_query
from itta_api.domains.device.power_supply.schemas import (
    PowerSupplyBulkResult,
    PowerSupplyCreate,
    PowerSupplyRead,
    PowerSupplySummary,
    PowerSupplyUpdate,
)


class PowerSupplyClient:
    BASE = "/api/v1/power-supplies"

    def __init__(self, upstream: UpstreamClient) -> None:
        self._up = upstream

    async def search(
        self, flt: PowerSupplyFilter, page: int = 0, size: int = 20, sort: str | None = None
    ) -> Page[PowerSupplyRead]:
        params: dict[str, str | int] = {**to_query(flt), "page": page, "size": size}
        if sort:
            params["sort"] = sort
        return Page[PowerSupplyRead].model_validate(await self._up.get_json(self.BASE, params=params))

    async def get(self, id: UUID) -> PowerSupplyRead:
        return PowerSupplyRead.model_validate(await self._up.get_json(f"{self.BASE}/{id}"))

    async def get_by_code(self, code: str) -> PowerSupplyRead:
        return PowerSupplyRead.model_validate(await self._up.get_json(f"{self.BASE}/by-code/{code}"))

    async def exists(self, code: str) -> bool:
        return await self._up.head(f"{self.BASE}/by-code/{code}")

    async def summaries(self, status: LifecycleStatus | None = None) -> list[PowerSupplySummary]:
        params = {"status": status.value} if status else None
        data = await self._up.get_json(f"{self.BASE}/summaries", params=params)
        return [PowerSupplySummary.model_validate(row) for row in data]

    async def create(self, payload: PowerSupplyCreate) -> PowerSupplyRead:
        body = payload.model_dump(mode="json", by_alias=True, exclude_none=True)
        return PowerSupplyRead.model_validate(await self._up.post_json(self.BASE, body))

    async def update(self, id: UUID, payload: PowerSupplyUpdate) -> PowerSupplyRead:
        body = payload.model_dump(mode="json", by_alias=True, exclude_unset=True)
        return PowerSupplyRead.model_validate(await self._up.put_json(f"{self.BASE}/{id}", body))

    async def change_status(self, id: UUID, status: LifecycleStatus) -> PowerSupplyRead:
        data = await self._up.patch_json(f"{self.BASE}/{id}/status", params={"status": status.value})
        return PowerSupplyRead.model_validate(data)

    async def delete(self, id: UUID) -> None:
        await self._up.delete(f"{self.BASE}/{id}")

    async def bulk_create(self, payloads: list[PowerSupplyCreate]) -> PowerSupplyBulkResult:
        body = [p.model_dump(mode="json", by_alias=True, exclude_none=True) for p in payloads]
        return PowerSupplyBulkResult.model_validate(await self._up.post_json(f"{self.BASE}/bulk", body))

    async def status_stats(self) -> dict[str, int]:
        return await self._up.get_json(f"{self.BASE}/stats/status")

    async def export_csv(self, flt: PowerSupplyFilter) -> bytes:
        return await self._up.get_bytes(f"{self.BASE}/export", params=to_query(flt))

    async def list_by_chassis_id(self, chassis_id: UUID, page: int = 0, size: int = 20) -> Page[PowerSupplyRead]:
        data = await self._up.get_json(
            f"{self.BASE}/by-chassis-id/{chassis_id}", params={"page": page, "size": size}
        )
        return Page[PowerSupplyRead].model_validate(data)

    async def list_by_feed_id(self, feed_id: UUID, page: int = 0, size: int = 20) -> Page[PowerSupplyRead]:
        data = await self._up.get_json(
            f"{self.BASE}/by-feed-id/{feed_id}", params={"page": page, "size": size}
        )
        return Page[PowerSupplyRead].model_validate(data)
