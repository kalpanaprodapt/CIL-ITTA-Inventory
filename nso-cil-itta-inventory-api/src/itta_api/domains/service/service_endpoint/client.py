"""HTTP client for the Service Endpoint endpoints of inventory-service-service."""
from __future__ import annotations

from uuid import UUID

from itta_core.http import UpstreamClient
from itta_core.models import LifecycleStatus
from itta_core.pagination import Page
from itta_api.domains.service.service_endpoint.filters import ServiceEndpointFilter, to_query
from itta_api.domains.service.service_endpoint.schemas import (
    ServiceEndpointBulkResult,
    ServiceEndpointCreate,
    ServiceEndpointRead,
    ServiceEndpointSummary,
    ServiceEndpointUpdate,
)


class ServiceEndpointClient:
    BASE = "/api/v1/service-endpoints"

    def __init__(self, upstream: UpstreamClient) -> None:
        self._up = upstream

    async def search(
        self, flt: ServiceEndpointFilter, page: int = 0, size: int = 20, sort: str | None = None
    ) -> Page[ServiceEndpointRead]:
        params: dict[str, str | int] = {**to_query(flt), "page": page, "size": size}
        if sort:
            params["sort"] = sort
        return Page[ServiceEndpointRead].model_validate(await self._up.get_json(self.BASE, params=params))

    async def get(self, id: UUID) -> ServiceEndpointRead:
        return ServiceEndpointRead.model_validate(await self._up.get_json(f"{self.BASE}/{id}"))

    async def get_by_code(self, code: str) -> ServiceEndpointRead:
        return ServiceEndpointRead.model_validate(await self._up.get_json(f"{self.BASE}/by-code/{code}"))

    async def exists(self, code: str) -> bool:
        return await self._up.head(f"{self.BASE}/by-code/{code}")

    async def summaries(self, status: LifecycleStatus | None = None) -> list[ServiceEndpointSummary]:
        params = {"status": status.value} if status else None
        data = await self._up.get_json(f"{self.BASE}/summaries", params=params)
        return [ServiceEndpointSummary.model_validate(row) for row in data]

    async def create(self, payload: ServiceEndpointCreate) -> ServiceEndpointRead:
        body = payload.model_dump(mode="json", by_alias=True, exclude_none=True)
        return ServiceEndpointRead.model_validate(await self._up.post_json(self.BASE, body))

    async def update(self, id: UUID, payload: ServiceEndpointUpdate) -> ServiceEndpointRead:
        body = payload.model_dump(mode="json", by_alias=True, exclude_unset=True)
        return ServiceEndpointRead.model_validate(await self._up.put_json(f"{self.BASE}/{id}", body))

    async def change_status(self, id: UUID, status: LifecycleStatus) -> ServiceEndpointRead:
        data = await self._up.patch_json(f"{self.BASE}/{id}/status", params={"status": status.value})
        return ServiceEndpointRead.model_validate(data)

    async def delete(self, id: UUID) -> None:
        await self._up.delete(f"{self.BASE}/{id}")

    async def bulk_create(self, payloads: list[ServiceEndpointCreate]) -> ServiceEndpointBulkResult:
        body = [p.model_dump(mode="json", by_alias=True, exclude_none=True) for p in payloads]
        return ServiceEndpointBulkResult.model_validate(await self._up.post_json(f"{self.BASE}/bulk", body))

    async def status_stats(self) -> dict[str, int]:
        return await self._up.get_json(f"{self.BASE}/stats/status")

    async def export_csv(self, flt: ServiceEndpointFilter) -> bytes:
        return await self._up.get_bytes(f"{self.BASE}/export", params=to_query(flt))

    async def list_by_service_id(self, service_id: UUID, page: int = 0, size: int = 20) -> Page[ServiceEndpointRead]:
        data = await self._up.get_json(
            f"{self.BASE}/by-service-id/{service_id}", params={"page": page, "size": size}
        )
        return Page[ServiceEndpointRead].model_validate(data)

    async def list_by_site_id(self, site_id: UUID, page: int = 0, size: int = 20) -> Page[ServiceEndpointRead]:
        data = await self._up.get_json(
            f"{self.BASE}/by-site-id/{site_id}", params={"page": page, "size": size}
        )
        return Page[ServiceEndpointRead].model_validate(data)

    async def list_by_port_id(self, port_id: UUID, page: int = 0, size: int = 20) -> Page[ServiceEndpointRead]:
        data = await self._up.get_json(
            f"{self.BASE}/by-port-id/{port_id}", params={"page": page, "size": size}
        )
        return Page[ServiceEndpointRead].model_validate(data)
