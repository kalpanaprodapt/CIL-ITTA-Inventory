"""HTTP client for the Event Source endpoints of inventory-alarm-service."""
from __future__ import annotations

from uuid import UUID

from itta_core.http import UpstreamClient
from itta_core.models import LifecycleStatus
from itta_core.pagination import Page
from itta_api.domains.alarm.event_source.filters import EventSourceFilter, to_query
from itta_api.domains.alarm.event_source.schemas import (
    EventSourceBulkResult,
    EventSourceCreate,
    EventSourceRead,
    EventSourceSummary,
    EventSourceUpdate,
)


class EventSourceClient:
    BASE = "/api/v1/event-sources"

    def __init__(self, upstream: UpstreamClient) -> None:
        self._up = upstream

    async def search(
        self, flt: EventSourceFilter, page: int = 0, size: int = 20, sort: str | None = None
    ) -> Page[EventSourceRead]:
        params: dict[str, str | int] = {**to_query(flt), "page": page, "size": size}
        if sort:
            params["sort"] = sort
        return Page[EventSourceRead].model_validate(await self._up.get_json(self.BASE, params=params))

    async def get(self, id: UUID) -> EventSourceRead:
        return EventSourceRead.model_validate(await self._up.get_json(f"{self.BASE}/{id}"))

    async def get_by_code(self, code: str) -> EventSourceRead:
        return EventSourceRead.model_validate(await self._up.get_json(f"{self.BASE}/by-code/{code}"))

    async def exists(self, code: str) -> bool:
        return await self._up.head(f"{self.BASE}/by-code/{code}")

    async def summaries(self, status: LifecycleStatus | None = None) -> list[EventSourceSummary]:
        params = {"status": status.value} if status else None
        data = await self._up.get_json(f"{self.BASE}/summaries", params=params)
        return [EventSourceSummary.model_validate(row) for row in data]

    async def create(self, payload: EventSourceCreate) -> EventSourceRead:
        body = payload.model_dump(mode="json", by_alias=True, exclude_none=True)
        return EventSourceRead.model_validate(await self._up.post_json(self.BASE, body))

    async def update(self, id: UUID, payload: EventSourceUpdate) -> EventSourceRead:
        body = payload.model_dump(mode="json", by_alias=True, exclude_unset=True)
        return EventSourceRead.model_validate(await self._up.put_json(f"{self.BASE}/{id}", body))

    async def change_status(self, id: UUID, status: LifecycleStatus) -> EventSourceRead:
        data = await self._up.patch_json(f"{self.BASE}/{id}/status", params={"status": status.value})
        return EventSourceRead.model_validate(data)

    async def delete(self, id: UUID) -> None:
        await self._up.delete(f"{self.BASE}/{id}")

    async def bulk_create(self, payloads: list[EventSourceCreate]) -> EventSourceBulkResult:
        body = [p.model_dump(mode="json", by_alias=True, exclude_none=True) for p in payloads]
        return EventSourceBulkResult.model_validate(await self._up.post_json(f"{self.BASE}/bulk", body))

    async def status_stats(self) -> dict[str, int]:
        return await self._up.get_json(f"{self.BASE}/stats/status")

    async def export_csv(self, flt: EventSourceFilter) -> bytes:
        return await self._up.get_bytes(f"{self.BASE}/export", params=to_query(flt))
