"""HTTP client for the Audit Record endpoints of inventory-compliance-service."""
from __future__ import annotations

from uuid import UUID

from itta_core.http import UpstreamClient
from itta_core.models import LifecycleStatus
from itta_core.pagination import Page
from itta_api.domains.compliance.audit_record.filters import AuditRecordFilter, to_query
from itta_api.domains.compliance.audit_record.schemas import (
    AuditRecordBulkResult,
    AuditRecordCreate,
    AuditRecordRead,
    AuditRecordSummary,
    AuditRecordUpdate,
)


class AuditRecordClient:
    BASE = "/api/v1/audit-records"

    def __init__(self, upstream: UpstreamClient) -> None:
        self._up = upstream

    async def search(
        self, flt: AuditRecordFilter, page: int = 0, size: int = 20, sort: str | None = None
    ) -> Page[AuditRecordRead]:
        params: dict[str, str | int] = {**to_query(flt), "page": page, "size": size}
        if sort:
            params["sort"] = sort
        return Page[AuditRecordRead].model_validate(await self._up.get_json(self.BASE, params=params))

    async def get(self, id: UUID) -> AuditRecordRead:
        return AuditRecordRead.model_validate(await self._up.get_json(f"{self.BASE}/{id}"))

    async def get_by_code(self, code: str) -> AuditRecordRead:
        return AuditRecordRead.model_validate(await self._up.get_json(f"{self.BASE}/by-code/{code}"))

    async def exists(self, code: str) -> bool:
        return await self._up.head(f"{self.BASE}/by-code/{code}")

    async def summaries(self, status: LifecycleStatus | None = None) -> list[AuditRecordSummary]:
        params = {"status": status.value} if status else None
        data = await self._up.get_json(f"{self.BASE}/summaries", params=params)
        return [AuditRecordSummary.model_validate(row) for row in data]

    async def create(self, payload: AuditRecordCreate) -> AuditRecordRead:
        body = payload.model_dump(mode="json", by_alias=True, exclude_none=True)
        return AuditRecordRead.model_validate(await self._up.post_json(self.BASE, body))

    async def update(self, id: UUID, payload: AuditRecordUpdate) -> AuditRecordRead:
        body = payload.model_dump(mode="json", by_alias=True, exclude_unset=True)
        return AuditRecordRead.model_validate(await self._up.put_json(f"{self.BASE}/{id}", body))

    async def change_status(self, id: UUID, status: LifecycleStatus) -> AuditRecordRead:
        data = await self._up.patch_json(f"{self.BASE}/{id}/status", params={"status": status.value})
        return AuditRecordRead.model_validate(data)

    async def delete(self, id: UUID) -> None:
        await self._up.delete(f"{self.BASE}/{id}")

    async def bulk_create(self, payloads: list[AuditRecordCreate]) -> AuditRecordBulkResult:
        body = [p.model_dump(mode="json", by_alias=True, exclude_none=True) for p in payloads]
        return AuditRecordBulkResult.model_validate(await self._up.post_json(f"{self.BASE}/bulk", body))

    async def status_stats(self) -> dict[str, int]:
        return await self._up.get_json(f"{self.BASE}/stats/status")

    async def export_csv(self, flt: AuditRecordFilter) -> bytes:
        return await self._up.get_bytes(f"{self.BASE}/export", params=to_query(flt))
