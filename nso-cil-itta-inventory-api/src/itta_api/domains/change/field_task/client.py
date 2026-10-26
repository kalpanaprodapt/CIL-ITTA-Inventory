"""HTTP client for the Field Task endpoints of inventory-change-service."""
from __future__ import annotations

from uuid import UUID

from itta_core.http import UpstreamClient
from itta_core.models import LifecycleStatus
from itta_core.pagination import Page
from itta_api.domains.change.field_task.filters import FieldTaskFilter, to_query
from itta_api.domains.change.field_task.schemas import (
    FieldTaskBulkResult,
    FieldTaskCreate,
    FieldTaskRead,
    FieldTaskSummary,
    FieldTaskUpdate,
)


class FieldTaskClient:
    BASE = "/api/v1/field-tasks"

    def __init__(self, upstream: UpstreamClient) -> None:
        self._up = upstream

    async def search(
        self, flt: FieldTaskFilter, page: int = 0, size: int = 20, sort: str | None = None
    ) -> Page[FieldTaskRead]:
        params: dict[str, str | int] = {**to_query(flt), "page": page, "size": size}
        if sort:
            params["sort"] = sort
        return Page[FieldTaskRead].model_validate(await self._up.get_json(self.BASE, params=params))

    async def get(self, id: UUID) -> FieldTaskRead:
        return FieldTaskRead.model_validate(await self._up.get_json(f"{self.BASE}/{id}"))

    async def get_by_code(self, code: str) -> FieldTaskRead:
        return FieldTaskRead.model_validate(await self._up.get_json(f"{self.BASE}/by-code/{code}"))

    async def exists(self, code: str) -> bool:
        return await self._up.head(f"{self.BASE}/by-code/{code}")

    async def summaries(self, status: LifecycleStatus | None = None) -> list[FieldTaskSummary]:
        params = {"status": status.value} if status else None
        data = await self._up.get_json(f"{self.BASE}/summaries", params=params)
        return [FieldTaskSummary.model_validate(row) for row in data]

    async def create(self, payload: FieldTaskCreate) -> FieldTaskRead:
        body = payload.model_dump(mode="json", by_alias=True, exclude_none=True)
        return FieldTaskRead.model_validate(await self._up.post_json(self.BASE, body))

    async def update(self, id: UUID, payload: FieldTaskUpdate) -> FieldTaskRead:
        body = payload.model_dump(mode="json", by_alias=True, exclude_unset=True)
        return FieldTaskRead.model_validate(await self._up.put_json(f"{self.BASE}/{id}", body))

    async def change_status(self, id: UUID, status: LifecycleStatus) -> FieldTaskRead:
        data = await self._up.patch_json(f"{self.BASE}/{id}/status", params={"status": status.value})
        return FieldTaskRead.model_validate(data)

    async def delete(self, id: UUID) -> None:
        await self._up.delete(f"{self.BASE}/{id}")

    async def bulk_create(self, payloads: list[FieldTaskCreate]) -> FieldTaskBulkResult:
        body = [p.model_dump(mode="json", by_alias=True, exclude_none=True) for p in payloads]
        return FieldTaskBulkResult.model_validate(await self._up.post_json(f"{self.BASE}/bulk", body))

    async def status_stats(self) -> dict[str, int]:
        return await self._up.get_json(f"{self.BASE}/stats/status")

    async def export_csv(self, flt: FieldTaskFilter) -> bytes:
        return await self._up.get_bytes(f"{self.BASE}/export", params=to_query(flt))

    async def list_by_work_order_id(self, work_order_id: UUID, page: int = 0, size: int = 20) -> Page[FieldTaskRead]:
        data = await self._up.get_json(
            f"{self.BASE}/by-work-order-id/{work_order_id}", params={"page": page, "size": size}
        )
        return Page[FieldTaskRead].model_validate(data)
