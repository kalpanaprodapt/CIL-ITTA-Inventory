"""Application service for Service Endpoint: caching, validation and bulk orchestration."""
from __future__ import annotations

import logging
from uuid import UUID

from itta_core.cache import AsyncTTLCache
from itta_core.errors import ConflictError, NotFoundError
from itta_core.models import LifecycleStatus
from itta_core.pagination import Page
from itta_api.domains.service.service_endpoint.client import ServiceEndpointClient
from itta_api.domains.service.service_endpoint.exporter import to_csv
from itta_api.domains.service.service_endpoint.filters import ServiceEndpointFilter
from itta_api.domains.service.service_endpoint.normalizer import normalize_record
from itta_api.domains.service.service_endpoint.reconciler import reconcile_many, to_create, to_update
from itta_api.domains.service.service_endpoint.schemas import (
    ServiceEndpointBulkResult,
    ServiceEndpointCreate,
    ServiceEndpointRead,
    ServiceEndpointSummary,
    ServiceEndpointUpdate,
)
from itta_core.diffing import Action, ReconciliationItem

log = logging.getLogger(__name__)
SUMMARY_TTL = 60.0
ITEM_TTL = 15.0


class ServiceEndpointService:
    def __init__(self, client: ServiceEndpointClient, cache: AsyncTTLCache) -> None:
        self._client = client
        self._cache = cache

    @staticmethod
    def _key(kind: str, *parts: object) -> str:
        return ":".join(["service_endpoint", kind, *[str(p) for p in parts]])

    async def search(
        self, flt: ServiceEndpointFilter, page: int = 0, size: int = 20, sort: str | None = None
    ) -> Page[ServiceEndpointRead]:
        return await self._client.search(flt, page, size, sort)

    async def get(self, id: UUID) -> ServiceEndpointRead:
        key = self._key("id", id)
        cached = await self._cache.get(key)
        if cached is not None:
            return cached
        item = await self._client.get(id)
        await self._cache.set(key, item, ITEM_TTL)
        return item

    async def get_by_code(self, code: str) -> ServiceEndpointRead:
        return await self._client.get_by_code(code)

    async def exists(self, code: str) -> bool:
        return await self._client.exists(code)

    async def summaries(self, status: LifecycleStatus | None = None) -> list[ServiceEndpointSummary]:
        key = self._key("summaries", status.value if status else "default")
        cached = await self._cache.get(key)
        if cached is not None:
            return cached
        rows = await self._client.summaries(status)
        await self._cache.set(key, rows, SUMMARY_TTL)
        return rows

    async def create(self, payload: ServiceEndpointCreate) -> ServiceEndpointRead:
        if await self._client.exists(payload.code):
            raise ConflictError(f"ServiceEndpoint with code {payload.code} already exists")
        created = await self._client.create(payload)
        await self._invalidate(created.id)
        return created

    async def update(self, id: UUID, payload: ServiceEndpointUpdate) -> ServiceEndpointRead:
        updated = await self._client.update(id, payload)
        await self._invalidate(id)
        return updated

    async def change_status(self, id: UUID, status: LifecycleStatus) -> ServiceEndpointRead:
        updated = await self._client.change_status(id, status)
        await self._invalidate(id)
        return updated

    async def delete(self, id: UUID) -> None:
        await self._client.delete(id)
        await self._invalidate(id)

    async def bulk_create(self, payloads: list[ServiceEndpointCreate]) -> ServiceEndpointBulkResult:
        seen: set[str] = set()
        unique: list[ServiceEndpointCreate] = []
        for payload in payloads:
            if payload.code in seen:
                raise ConflictError(f"duplicate code {payload.code} in bulk request")
            seen.add(payload.code)
            unique.append(payload)
        result = await self._client.bulk_create(unique)
        await self._cache.invalidate_prefix(self._key("summaries"))
        return result

    async def stats(self) -> dict[str, int]:
        return await self._client.status_stats()

    async def export_csv(self, flt: ServiceEndpointFilter, page_size: int = 500) -> str:
        items: list[ServiceEndpointRead] = []
        page = 0
        while True:
            chunk = await self._client.search(flt, page, page_size, "code")
            items.extend(chunk.content)
            page += 1
            if page >= chunk.total_pages:
                break
        return to_csv(items)

    async def reconcile(self, records: list[dict[str, object]], apply: bool = False) -> list[ReconciliationItem]:
        """Compare external records against stored ones; optionally apply the resulting plan."""
        codes = [str(normalize_record(r).get("code")) for r in records if normalize_record(r).get("code")]
        existing: dict[str, ServiceEndpointRead] = {}
        for code in dict.fromkeys(codes):
            try:
                existing[code] = await self._client.get_by_code(code)
            except NotFoundError:
                continue
        items = reconcile_many(existing, records)
        if apply:
            for item in items:
                if item.action == Action.CREATE:
                    created = await self._client.create(to_create(item))
                    item.result_id = created.id
                elif item.action == Action.UPDATE and item.target_id is not None:
                    current = existing[item.key]
                    updated = await self._client.update(item.target_id, to_update(item, current.version))
                    item.result_id = updated.id
            await self._cache.invalidate_prefix(self._key(""))
        return items

    async def _invalidate(self, id: UUID) -> None:
        await self._cache.delete(self._key("id", id))
        await self._cache.invalidate_prefix(self._key("summaries"))

    async def list_by_service_id(self, service_id: UUID, page: int = 0, size: int = 20) -> Page[ServiceEndpointRead]:
        return await self._client.list_by_service_id(service_id, page, size)

    async def list_by_site_id(self, site_id: UUID, page: int = 0, size: int = 20) -> Page[ServiceEndpointRead]:
        return await self._client.list_by_site_id(site_id, page, size)

    async def list_by_port_id(self, port_id: UUID, page: int = 0, size: int = 20) -> Page[ServiceEndpointRead]:
        return await self._client.list_by_port_id(port_id, page, size)
