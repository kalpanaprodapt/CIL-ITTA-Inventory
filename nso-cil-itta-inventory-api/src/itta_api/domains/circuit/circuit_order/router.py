"""HTTP routes for Circuit Order."""
from __future__ import annotations

from uuid import UUID

from fastapi import APIRouter, Depends, Query, Request, Response, status
from fastapi.responses import PlainTextResponse

from itta_core.auth import Principal, require_read, require_write
from itta_core.dependencies import service_provider
from itta_core.diffing import ReconciliationItem
from itta_core.models import EntityMeta, LifecycleStatus
from itta_core.pagination import Page
from itta_api.domains.circuit.circuit_order.filters import CircuitOrderFilter
from itta_api.domains.circuit.circuit_order.meta import META
from itta_api.domains.circuit.circuit_order.schemas import (
    CircuitOrderBulkResult,
    CircuitOrderCreate,
    CircuitOrderRead,
    CircuitOrderSummary,
    CircuitOrderUpdate,
)
from itta_api.domains.circuit.circuit_order.service import CircuitOrderService

router = APIRouter(prefix="/api/v1/circuit-orders", tags=["Circuit Order"])
get_service = service_provider("circuit_order")


@router.get("/meta", response_model=EntityMeta, summary="Field catalogue for Circuit Order")
async def meta() -> EntityMeta:
    return META


@router.get("", response_model=Page[CircuitOrderRead], summary="Search Circuit Order records")
async def search(
    flt: CircuitOrderFilter = Depends(),
    page: int = Query(0, ge=0),
    size: int = Query(20, ge=1, le=200),
    sort: str | None = Query(None, description="field,asc|desc"),
    service: CircuitOrderService = Depends(get_service),
    _: Principal = Depends(require_read),
) -> Page[CircuitOrderRead]:
    return await service.search(flt, page, size, sort)


@router.get("/summaries", response_model=list[CircuitOrderSummary], summary="Pick-list of Circuit Order records")
async def summaries(
    status_filter: LifecycleStatus | None = Query(None, alias="status"),
    service: CircuitOrderService = Depends(get_service),
    _: Principal = Depends(require_read),
) -> list[CircuitOrderSummary]:
    return await service.summaries(status_filter)


@router.get("/stats/status", response_model=dict[str, int], summary="Counts per lifecycle status")
async def status_stats(
    service: CircuitOrderService = Depends(get_service), _: Principal = Depends(require_read)
) -> dict[str, int]:
    return await service.stats()


@router.get("/export", response_class=PlainTextResponse, summary="Export Circuit Order records as CSV")
async def export(
    flt: CircuitOrderFilter = Depends(),
    service: CircuitOrderService = Depends(get_service),
    _: Principal = Depends(require_read),
) -> PlainTextResponse:
    body = await service.export_csv(flt)
    headers = {"Content-Disposition": 'attachment; filename="circuit-orders.csv"'}
    return PlainTextResponse(body, media_type="text/csv", headers=headers)


@router.get("/by-code/{code}", response_model=CircuitOrderRead, summary="Get a Circuit Order by business code")
async def get_by_code(
    code: str, service: CircuitOrderService = Depends(get_service), _: Principal = Depends(require_read)
) -> CircuitOrderRead:
    return await service.get_by_code(code)


@router.head("/by-code/{code}", summary="Check whether a Circuit Order code exists")
async def exists(
    code: str, service: CircuitOrderService = Depends(get_service), _: Principal = Depends(require_read)
) -> Response:
    found = await service.exists(code)
    return Response(status_code=status.HTTP_200_OK if found else status.HTTP_404_NOT_FOUND)


@router.get("/{id}", response_model=CircuitOrderRead, summary="Get a Circuit Order by id")
async def get(
    id: UUID, service: CircuitOrderService = Depends(get_service), _: Principal = Depends(require_read)
) -> CircuitOrderRead:
    return await service.get(id)


@router.post("", response_model=CircuitOrderRead, status_code=status.HTTP_201_CREATED, summary="Create a Circuit Order")
async def create(
    payload: CircuitOrderCreate,
    request: Request,
    response: Response,
    service: CircuitOrderService = Depends(get_service),
    _: Principal = Depends(require_write),
) -> CircuitOrderRead:
    created = await service.create(payload)
    response.headers["Location"] = f"{request.url.path}/{created.id}"
    return created


@router.put("/{id}", response_model=CircuitOrderRead, summary="Update a Circuit Order")
async def update(
    id: UUID,
    payload: CircuitOrderUpdate,
    service: CircuitOrderService = Depends(get_service),
    _: Principal = Depends(require_write),
) -> CircuitOrderRead:
    return await service.update(id, payload)


@router.patch("/{id}/status", response_model=CircuitOrderRead, summary="Change lifecycle status")
async def change_status(
    id: UUID,
    new_status: LifecycleStatus = Query(..., alias="status"),
    service: CircuitOrderService = Depends(get_service),
    _: Principal = Depends(require_write),
) -> CircuitOrderRead:
    return await service.change_status(id, new_status)


@router.delete("/{id}", status_code=status.HTTP_204_NO_CONTENT, summary="Delete a Circuit Order")
async def delete(
    id: UUID, service: CircuitOrderService = Depends(get_service), _: Principal = Depends(require_write)
) -> Response:
    await service.delete(id)
    return Response(status_code=status.HTTP_204_NO_CONTENT)


@router.post("/bulk", response_model=CircuitOrderBulkResult, summary="Create many Circuit Order records")
async def bulk_create(
    payloads: list[CircuitOrderCreate],
    service: CircuitOrderService = Depends(get_service),
    _: Principal = Depends(require_write),
) -> CircuitOrderBulkResult:
    return await service.bulk_create(payloads)


@router.post("/reconcile", response_model=list[ReconciliationItem], summary="Reconcile external Circuit Order records")
async def reconcile(
    records: list[dict[str, object]],
    apply: bool = Query(False, description="Apply the plan instead of a dry run"),
    service: CircuitOrderService = Depends(get_service),
    _: Principal = Depends(require_write),
) -> list[ReconciliationItem]:
    return await service.reconcile(records, apply)


@router.get("/by-circuit-id/{circuit_id}", response_model=Page[CircuitOrderRead], summary="List Circuit Order records by Circuit Id")
async def list_by_circuit_id(
    circuit_id: UUID,
    page: int = Query(0, ge=0),
    size: int = Query(20, ge=1, le=200),
    service: CircuitOrderService = Depends(get_service),
) -> Page[CircuitOrderRead]:
    return await service.list_by_circuit_id(circuit_id, page, size)
