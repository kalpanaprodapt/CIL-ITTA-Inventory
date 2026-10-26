"""HTTP client for the Country endpoints of inventory-geography-service."""
from __future__ import annotations

from uuid import UUID

from itta_core.http import UpstreamClient
from itta_core.models import LifecycleStatus
from itta_core.pagination import Page
from itta_api.domains.geography.country.filters import CountryFilter, to_query
from itta_api.domains.geography.country.schemas import (
    CountryBulkResult,
    CountryCreate,
    CountryRead,
    CountrySummary,
    CountryUpdate,
)


class CountryClient:
    BASE = "/api/v1/countries"

    def __init__(self, upstream: UpstreamClient) -> None:
        self._up = upstream

    async def search(
        self, flt: CountryFilter, page: int = 0, size: int = 20, sort: str | None = None
    ) -> Page[CountryRead]:
        params: dict[str, str | int] = {**to_query(flt), "page": page, "size": size}
        if sort:
            params["sort"] = sort
        return Page[CountryRead].model_validate(await self._up.get_json(self.BASE, params=params))

    async def get(self, id: UUID) -> CountryRead:
        return CountryRead.model_validate(await self._up.get_json(f"{self.BASE}/{id}"))

    async def get_by_code(self, code: str) -> CountryRead:
        return CountryRead.model_validate(await self._up.get_json(f"{self.BASE}/by-code/{code}"))

    async def exists(self, code: str) -> bool:
        return await self._up.head(f"{self.BASE}/by-code/{code}")

    async def summaries(self, status: LifecycleStatus | None = None) -> list[CountrySummary]:
        params = {"status": status.value} if status else None
        data = await self._up.get_json(f"{self.BASE}/summaries", params=params)
        return [CountrySummary.model_validate(row) for row in data]

    async def create(self, payload: CountryCreate) -> CountryRead:
        body = payload.model_dump(mode="json", by_alias=True, exclude_none=True)
        return CountryRead.model_validate(await self._up.post_json(self.BASE, body))

    async def update(self, id: UUID, payload: CountryUpdate) -> CountryRead:
        body = payload.model_dump(mode="json", by_alias=True, exclude_unset=True)
        return CountryRead.model_validate(await self._up.put_json(f"{self.BASE}/{id}", body))

    async def change_status(self, id: UUID, status: LifecycleStatus) -> CountryRead:
        data = await self._up.patch_json(f"{self.BASE}/{id}/status", params={"status": status.value})
        return CountryRead.model_validate(data)

    async def delete(self, id: UUID) -> None:
        await self._up.delete(f"{self.BASE}/{id}")

    async def bulk_create(self, payloads: list[CountryCreate]) -> CountryBulkResult:
        body = [p.model_dump(mode="json", by_alias=True, exclude_none=True) for p in payloads]
        return CountryBulkResult.model_validate(await self._up.post_json(f"{self.BASE}/bulk", body))

    async def status_stats(self) -> dict[str, int]:
        return await self._up.get_json(f"{self.BASE}/stats/status")

    async def export_csv(self, flt: CountryFilter) -> bytes:
        return await self._up.get_bytes(f"{self.BASE}/export", params=to_query(flt))
