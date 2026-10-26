"""HTTP client for the Vpn Service endpoints of inventory-mpls-service."""
from __future__ import annotations

from uuid import UUID

from itta_core.http import UpstreamClient
from itta_core.models import LifecycleStatus
from itta_core.pagination import Page
from itta_api.domains.mpls.vpn_service.filters import VpnServiceFilter, to_query
from itta_api.domains.mpls.vpn_service.schemas import (
    VpnServiceBulkResult,
    VpnServiceCreate,
    VpnServiceRead,
    VpnServiceSummary,
    VpnServiceUpdate,
)


class VpnServiceClient:
    BASE = "/api/v1/vpn-services"

    def __init__(self, upstream: UpstreamClient) -> None:
        self._up = upstream

    async def search(
        self, flt: VpnServiceFilter, page: int = 0, size: int = 20, sort: str | None = None
    ) -> Page[VpnServiceRead]:
        params: dict[str, str | int] = {**to_query(flt), "page": page, "size": size}
        if sort:
            params["sort"] = sort
        return Page[VpnServiceRead].model_validate(await self._up.get_json(self.BASE, params=params))

    async def get(self, id: UUID) -> VpnServiceRead:
        return VpnServiceRead.model_validate(await self._up.get_json(f"{self.BASE}/{id}"))

    async def get_by_code(self, code: str) -> VpnServiceRead:
        return VpnServiceRead.model_validate(await self._up.get_json(f"{self.BASE}/by-code/{code}"))

    async def exists(self, code: str) -> bool:
        return await self._up.head(f"{self.BASE}/by-code/{code}")

    async def summaries(self, status: LifecycleStatus | None = None) -> list[VpnServiceSummary]:
        params = {"status": status.value} if status else None
        data = await self._up.get_json(f"{self.BASE}/summaries", params=params)
        return [VpnServiceSummary.model_validate(row) for row in data]

    async def create(self, payload: VpnServiceCreate) -> VpnServiceRead:
        body = payload.model_dump(mode="json", by_alias=True, exclude_none=True)
        return VpnServiceRead.model_validate(await self._up.post_json(self.BASE, body))

    async def update(self, id: UUID, payload: VpnServiceUpdate) -> VpnServiceRead:
        body = payload.model_dump(mode="json", by_alias=True, exclude_unset=True)
        return VpnServiceRead.model_validate(await self._up.put_json(f"{self.BASE}/{id}", body))

    async def change_status(self, id: UUID, status: LifecycleStatus) -> VpnServiceRead:
        data = await self._up.patch_json(f"{self.BASE}/{id}/status", params={"status": status.value})
        return VpnServiceRead.model_validate(data)

    async def delete(self, id: UUID) -> None:
        await self._up.delete(f"{self.BASE}/{id}")

    async def bulk_create(self, payloads: list[VpnServiceCreate]) -> VpnServiceBulkResult:
        body = [p.model_dump(mode="json", by_alias=True, exclude_none=True) for p in payloads]
        return VpnServiceBulkResult.model_validate(await self._up.post_json(f"{self.BASE}/bulk", body))

    async def status_stats(self) -> dict[str, int]:
        return await self._up.get_json(f"{self.BASE}/stats/status")

    async def export_csv(self, flt: VpnServiceFilter) -> bytes:
        return await self._up.get_bytes(f"{self.BASE}/export", params=to_query(flt))

    async def list_by_vrf_id(self, vrf_id: UUID, page: int = 0, size: int = 20) -> Page[VpnServiceRead]:
        data = await self._up.get_json(
            f"{self.BASE}/by-vrf-id/{vrf_id}", params={"page": page, "size": size}
        )
        return Page[VpnServiceRead].model_validate(data)

    async def list_by_customer_id(self, customer_id: UUID, page: int = 0, size: int = 20) -> Page[VpnServiceRead]:
        data = await self._up.get_json(
            f"{self.BASE}/by-customer-id/{customer_id}", params={"page": page, "size": size}
        )
        return Page[VpnServiceRead].model_validate(data)
