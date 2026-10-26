"""HTTP client for the Service Instance endpoints of inventory-service-service."""
from __future__ import annotations

from uuid import UUID

from itta_core.http import UpstreamClient
from itta_core.models import LifecycleStatus
from itta_core.pagination import Page
from itta_api.domains.service.service_instance.filters import ServiceInstanceFilter, to_query
from itta_api.domains.service.service_instance.schemas import (
    ServiceInstanceBulkResult,
    ServiceInstanceCreate,
    ServiceInstanceRead,
    ServiceInstanceSummary,
    ServiceInstanceUpdate,
)


class ServiceInstanceClient:
    BASE = "/api/v1/service-instances"

    def __init__(self, upstream: UpstreamClient) -> None:
        self._up = upstream

    async def search(
        self, flt: ServiceInstanceFilter, page: int = 0, size: int = 20, sort: str | None = None
    ) -> Page[ServiceInstanceRead]:
        params: dict[str, str | int] = {**to_query(flt), "page": page, "size": size}
        if sort:
            params["sort"] = sort
        return Page[ServiceInstanceRead].model_validate(await self._up.get_json(self.BASE, params=params))

    async def get(self, id: UUID) -> ServiceInstanceRead:
        return ServiceInstanceRead.model_validate(await self._up.get_json(f"{self.BASE}/{id}"))

    async def get_by_code(self, code: str) -> ServiceInstanceRead:
        return ServiceInstanceRead.model_validate(await self._up.get_json(f"{self.BASE}/by-code/{code}"))

    async def exists(self, code: str) -> bool:
        return await self._up.head(f"{self.BASE}/by-code/{code}")

    async def summaries(self, status: LifecycleStatus | None = None) -> list[ServiceInstanceSummary]:
        params = {"status": status.value} if status else None
        data = await self._up.get_json(f"{self.BASE}/summaries", params=params)
        return [ServiceInstanceSummary.model_validate(row) for row in data]

    async def create(self, payload: ServiceInstanceCreate) -> ServiceInstanceRead:
        body = payload.model_dump(mode="json", by_alias=True, exclude_none=True)
        return ServiceInstanceRead.model_validate(await self._up.post_json(self.BASE, body))

    async def update(self, id: UUID, payload: ServiceInstanceUpdate) -> ServiceInstanceRead:
        body = payload.model_dump(mode="json", by_alias=True, exclude_unset=True)
        return ServiceInstanceRead.model_validate(await self._up.put_json(f"{self.BASE}/{id}", body))

    async def change_status(self, id: UUID, status: LifecycleStatus) -> ServiceInstanceRead:
        data = await self._up.patch_json(f"{self.BASE}/{id}/status", params={"status": status.value})
        return ServiceInstanceRead.model_validate(data)

    async def delete(self, id: UUID) -> None:
        await self._up.delete(f"{self.BASE}/{id}")

    async def bulk_create(self, payloads: list[ServiceInstanceCreate]) -> ServiceInstanceBulkResult:
        body = [p.model_dump(mode="json", by_alias=True, exclude_none=True) for p in payloads]
        return ServiceInstanceBulkResult.model_validate(await self._up.post_json(f"{self.BASE}/bulk", body))

    async def status_stats(self) -> dict[str, int]:
        return await self._up.get_json(f"{self.BASE}/stats/status")

    async def export_csv(self, flt: ServiceInstanceFilter) -> bytes:
        return await self._up.get_bytes(f"{self.BASE}/export", params=to_query(flt))

    async def list_by_catalog_item_id(self, catalog_item_id: UUID, page: int = 0, size: int = 20) -> Page[ServiceInstanceRead]:
        data = await self._up.get_json(
            f"{self.BASE}/by-catalog-item-id/{catalog_item_id}", params={"page": page, "size": size}
        )
        return Page[ServiceInstanceRead].model_validate(data)

    async def list_by_customer_id(self, customer_id: UUID, page: int = 0, size: int = 20) -> Page[ServiceInstanceRead]:
        data = await self._up.get_json(
            f"{self.BASE}/by-customer-id/{customer_id}", params={"page": page, "size": size}
        )
        return Page[ServiceInstanceRead].model_validate(data)
