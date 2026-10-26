"""HTTP client for the Rack Unit endpoints of inventory-facility-service."""
from __future__ import annotations

from uuid import UUID

from itta_core.http import UpstreamClient
from itta_core.models import LifecycleStatus
from itta_core.pagination import Page
from itta_api.domains.facility.rack_unit.filters import RackUnitFilter, to_query
from itta_api.domains.facility.rack_unit.schemas import (
    RackUnitBulkResult,
    RackUnitCreate,
    RackUnitRead,
    RackUnitSummary,
    RackUnitUpdate,
)


class RackUnitClient:
    BASE = "/api/v1/rack-units"

    def __init__(self, upstream: UpstreamClient) -> None:
        self._up = upstream

    async def search(
        self, flt: RackUnitFilter, page: int = 0, size: int = 20, sort: str | None = None
    ) -> Page[RackUnitRead]:
        params: dict[str, str | int] = {**to_query(flt), "page": page, "size": size}
        if sort:
            params["sort"] = sort
        return Page[RackUnitRead].model_validate(await self._up.get_json(self.BASE, params=params))

    async def get(self, id: UUID) -> RackUnitRead:
        return RackUnitRead.model_validate(await self._up.get_json(f"{self.BASE}/{id}"))

    async def get_by_code(self, code: str) -> RackUnitRead:
        return RackUnitRead.model_validate(await self._up.get_json(f"{self.BASE}/by-code/{code}"))

    async def exists(self, code: str) -> bool:
        return await self._up.head(f"{self.BASE}/by-code/{code}")

    async def summaries(self, status: LifecycleStatus | None = None) -> list[RackUnitSummary]:
        params = {"status": status.value} if status else None
        data = await self._up.get_json(f"{self.BASE}/summaries", params=params)
        return [RackUnitSummary.model_validate(row) for row in data]

    async def create(self, payload: RackUnitCreate) -> RackUnitRead:
        body = payload.model_dump(mode="json", by_alias=True, exclude_none=True)
        return RackUnitRead.model_validate(await self._up.post_json(self.BASE, body))

    async def update(self, id: UUID, payload: RackUnitUpdate) -> RackUnitRead:
        body = payload.model_dump(mode="json", by_alias=True, exclude_unset=True)
        return RackUnitRead.model_validate(await self._up.put_json(f"{self.BASE}/{id}", body))

    async def change_status(self, id: UUID, status: LifecycleStatus) -> RackUnitRead:
        data = await self._up.patch_json(f"{self.BASE}/{id}/status", params={"status": status.value})
        return RackUnitRead.model_validate(data)

    async def delete(self, id: UUID) -> None:
        await self._up.delete(f"{self.BASE}/{id}")

    async def bulk_create(self, payloads: list[RackUnitCreate]) -> RackUnitBulkResult:
        body = [p.model_dump(mode="json", by_alias=True, exclude_none=True) for p in payloads]
        return RackUnitBulkResult.model_validate(await self._up.post_json(f"{self.BASE}/bulk", body))

    async def status_stats(self) -> dict[str, int]:
        return await self._up.get_json(f"{self.BASE}/stats/status")

    async def export_csv(self, flt: RackUnitFilter) -> bytes:
        return await self._up.get_bytes(f"{self.BASE}/export", params=to_query(flt))

    async def list_by_rack_id(self, rack_id: UUID, page: int = 0, size: int = 20) -> Page[RackUnitRead]:
        data = await self._up.get_json(
            f"{self.BASE}/by-rack-id/{rack_id}", params={"page": page, "size": size}
        )
        return Page[RackUnitRead].model_validate(data)

    async def list_by_mounted_device_id(self, mounted_device_id: UUID, page: int = 0, size: int = 20) -> Page[RackUnitRead]:
        data = await self._up.get_json(
            f"{self.BASE}/by-mounted-device-id/{mounted_device_id}", params={"page": page, "size": size}
        )
        return Page[RackUnitRead].model_validate(data)
