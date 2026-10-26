"""HTTP client for the Circuit Termination endpoints of inventory-circuit-service."""
from __future__ import annotations

from uuid import UUID

from itta_core.http import UpstreamClient
from itta_core.models import LifecycleStatus
from itta_core.pagination import Page
from itta_api.domains.circuit.circuit_termination.filters import CircuitTerminationFilter, to_query
from itta_api.domains.circuit.circuit_termination.schemas import (
    CircuitTerminationBulkResult,
    CircuitTerminationCreate,
    CircuitTerminationRead,
    CircuitTerminationSummary,
    CircuitTerminationUpdate,
)


class CircuitTerminationClient:
    BASE = "/api/v1/circuit-terminations"

    def __init__(self, upstream: UpstreamClient) -> None:
        self._up = upstream

    async def search(
        self, flt: CircuitTerminationFilter, page: int = 0, size: int = 20, sort: str | None = None
    ) -> Page[CircuitTerminationRead]:
        params: dict[str, str | int] = {**to_query(flt), "page": page, "size": size}
        if sort:
            params["sort"] = sort
        return Page[CircuitTerminationRead].model_validate(await self._up.get_json(self.BASE, params=params))

    async def get(self, id: UUID) -> CircuitTerminationRead:
        return CircuitTerminationRead.model_validate(await self._up.get_json(f"{self.BASE}/{id}"))

    async def get_by_code(self, code: str) -> CircuitTerminationRead:
        return CircuitTerminationRead.model_validate(await self._up.get_json(f"{self.BASE}/by-code/{code}"))

    async def exists(self, code: str) -> bool:
        return await self._up.head(f"{self.BASE}/by-code/{code}")

    async def summaries(self, status: LifecycleStatus | None = None) -> list[CircuitTerminationSummary]:
        params = {"status": status.value} if status else None
        data = await self._up.get_json(f"{self.BASE}/summaries", params=params)
        return [CircuitTerminationSummary.model_validate(row) for row in data]

    async def create(self, payload: CircuitTerminationCreate) -> CircuitTerminationRead:
        body = payload.model_dump(mode="json", by_alias=True, exclude_none=True)
        return CircuitTerminationRead.model_validate(await self._up.post_json(self.BASE, body))

    async def update(self, id: UUID, payload: CircuitTerminationUpdate) -> CircuitTerminationRead:
        body = payload.model_dump(mode="json", by_alias=True, exclude_unset=True)
        return CircuitTerminationRead.model_validate(await self._up.put_json(f"{self.BASE}/{id}", body))

    async def change_status(self, id: UUID, status: LifecycleStatus) -> CircuitTerminationRead:
        data = await self._up.patch_json(f"{self.BASE}/{id}/status", params={"status": status.value})
        return CircuitTerminationRead.model_validate(data)

    async def delete(self, id: UUID) -> None:
        await self._up.delete(f"{self.BASE}/{id}")

    async def bulk_create(self, payloads: list[CircuitTerminationCreate]) -> CircuitTerminationBulkResult:
        body = [p.model_dump(mode="json", by_alias=True, exclude_none=True) for p in payloads]
        return CircuitTerminationBulkResult.model_validate(await self._up.post_json(f"{self.BASE}/bulk", body))

    async def status_stats(self) -> dict[str, int]:
        return await self._up.get_json(f"{self.BASE}/stats/status")

    async def export_csv(self, flt: CircuitTerminationFilter) -> bytes:
        return await self._up.get_bytes(f"{self.BASE}/export", params=to_query(flt))

    async def list_by_circuit_id(self, circuit_id: UUID, page: int = 0, size: int = 20) -> Page[CircuitTerminationRead]:
        data = await self._up.get_json(
            f"{self.BASE}/by-circuit-id/{circuit_id}", params={"page": page, "size": size}
        )
        return Page[CircuitTerminationRead].model_validate(data)

    async def list_by_port_id(self, port_id: UUID, page: int = 0, size: int = 20) -> Page[CircuitTerminationRead]:
        data = await self._up.get_json(
            f"{self.BASE}/by-port-id/{port_id}", params={"page": page, "size": size}
        )
        return Page[CircuitTerminationRead].model_validate(data)
