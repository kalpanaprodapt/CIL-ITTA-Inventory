"""HTTP routes for Circuit Termination."""
from __future__ import annotations

from uuid import UUID

from fastapi import APIRouter, Depends, Query, Request, Response, status
from fastapi.responses import PlainTextResponse

from itta_core.auth import Principal, require_read, require_write
from itta_core.dependencies import service_provider
from itta_core.diffing import ReconciliationItem
from itta_core.models import EntityMeta, LifecycleStatus
from itta_core.pagination import Page
from itta_api.domains.circuit.circuit_termination.filters import CircuitTerminationFilter
from itta_api.domains.circuit.circuit_termination.meta import META
from itta_api.domains.circuit.circuit_termination.schemas import (
    CircuitTerminationBulkResult,
    CircuitTerminationCreate,
    CircuitTerminationRead,
    CircuitTerminationSummary,
    CircuitTerminationUpdate,
)
from itta_api.domains.circuit.circuit_termination.service import CircuitTerminationService

router = APIRouter(prefix="/api/v1/circuit-terminations", tags=["Circuit Termination"])
get_service = service_provider("circuit_termination")


@router.get("/meta", response_model=EntityMeta, summary="Field catalogue for Circuit Termination")
async def meta() -> EntityMeta:
    return META


@router.get("", response_model=Page[CircuitTerminationRead], summary="Search Circuit Termination records")
async def search(
    flt: CircuitTerminationFilter = Depends(),
    page: int = Query(0, ge=0),
    size: int = Query(20, ge=1, le=200),
    sort: str | None = Query(None, description="field,asc|desc"),
    service: CircuitTerminationService = Depends(get_service),
    _: Principal = Depends(require_read),
) -> Page[CircuitTerminationRead]:
    return await service.search(flt, page, size, sort)


@router.get("/summaries", response_model=list[CircuitTerminationSummary], summary="Pick-list of Circuit Termination records")
async def summaries(
    status_filter: LifecycleStatus | None = Query(None, alias="status"),
    service: CircuitTerminationService = Depends(get_service),
    _: Principal = Depends(require_read),
) -> list[CircuitTerminationSummary]:
    return await service.summaries(status_filter)


@router.get("/stats/status", response_model=dict[str, int], summary="Counts per lifecycle status")
async def status_stats(
    service: CircuitTerminationService = Depends(get_service), _: Principal = Depends(require_read)
) -> dict[str, int]:
    return await service.stats()


@router.get("/export", response_class=PlainTextResponse, summary="Export Circuit Termination records as CSV")
async def export(
    flt: CircuitTerminationFilter = Depends(),
    service: CircuitTerminationService = Depends(get_service),
    _: Principal = Depends(require_read),
) -> PlainTextResponse:
    body = await service.export_csv(flt)
    headers = {"Content-Disposition": 'attachment; filename="circuit-terminations.csv"'}
    return PlainTextResponse(body, media_type="text/csv", headers=headers)


@router.get("/by-code/{code}", response_model=CircuitTerminationRead, summary="Get a Circuit Termination by business code")
async def get_by_code(
    code: str, service: CircuitTerminationService = Depends(get_service), _: Principal = Depends(require_read)
) -> CircuitTerminationRead:
    return await service.get_by_code(code)


@router.head("/by-code/{code}", summary="Check whether a Circuit Termination code exists")
async def exists(
    code: str, service: CircuitTerminationService = Depends(get_service), _: Principal = Depends(require_read)
) -> Response:
    found = await service.exists(code)
    return Response(status_code=status.HTTP_200_OK if found else status.HTTP_404_NOT_FOUND)


@router.get("/{id}", response_model=CircuitTerminationRead, summary="Get a Circuit Termination by id")
async def get(
    id: UUID, service: CircuitTerminationService = Depends(get_service), _: Principal = Depends(require_read)
) -> CircuitTerminationRead:
    return await service.get(id)


@router.post("", response_model=CircuitTerminationRead, status_code=status.HTTP_201_CREATED, summary="Create a Circuit Termination")
async def create(
    payload: CircuitTerminationCreate,
    request: Request,
    response: Response,
    service: CircuitTerminationService = Depends(get_service),
    _: Principal = Depends(require_write),
) -> CircuitTerminationRead:
    created = await service.create(payload)
    response.headers["Location"] = f"{request.url.path}/{created.id}"
    return created


@router.put("/{id}", response_model=CircuitTerminationRead, summary="Update a Circuit Termination")
async def update(
    id: UUID,
    payload: CircuitTerminationUpdate,
    service: CircuitTerminationService = Depends(get_service),
    _: Principal = Depends(require_write),
) -> CircuitTerminationRead:
    return await service.update(id, payload)


@router.patch("/{id}/status", response_model=CircuitTerminationRead, summary="Change lifecycle status")
async def change_status(
    id: UUID,
    new_status: LifecycleStatus = Query(..., alias="status"),
    service: CircuitTerminationService = Depends(get_service),
    _: Principal = Depends(require_write),
) -> CircuitTerminationRead:
    return await service.change_status(id, new_status)


@router.delete("/{id}", status_code=status.HTTP_204_NO_CONTENT, summary="Delete a Circuit Termination")
async def delete(
    id: UUID, service: CircuitTerminationService = Depends(get_service), _: Principal = Depends(require_write)
) -> Response:
    await service.delete(id)
    return Response(status_code=status.HTTP_204_NO_CONTENT)


@router.post("/bulk", response_model=CircuitTerminationBulkResult, summary="Create many Circuit Termination records")
async def bulk_create(
    payloads: list[CircuitTerminationCreate],
    service: CircuitTerminationService = Depends(get_service),
    _: Principal = Depends(require_write),
) -> CircuitTerminationBulkResult:
    return await service.bulk_create(payloads)


@router.post("/reconcile", response_model=list[ReconciliationItem], summary="Reconcile external Circuit Termination records")
async def reconcile(
    records: list[dict[str, object]],
    apply: bool = Query(False, description="Apply the plan instead of a dry run"),
    service: CircuitTerminationService = Depends(get_service),
    _: Principal = Depends(require_write),
) -> list[ReconciliationItem]:
    return await service.reconcile(records, apply)


@router.get("/by-circuit-id/{circuit_id}", response_model=Page[CircuitTerminationRead], summary="List Circuit Termination records by Circuit Id")
async def list_by_circuit_id(
    circuit_id: UUID,
    page: int = Query(0, ge=0),
    size: int = Query(20, ge=1, le=200),
    service: CircuitTerminationService = Depends(get_service),
) -> Page[CircuitTerminationRead]:
    return await service.list_by_circuit_id(circuit_id, page, size)

@router.get("/by-port-id/{port_id}", response_model=Page[CircuitTerminationRead], summary="List Circuit Termination records by Port Id")
async def list_by_port_id(
    port_id: UUID,
    page: int = Query(0, ge=0),
    size: int = Query(20, ge=1, le=200),
    service: CircuitTerminationService = Depends(get_service),
) -> Page[CircuitTerminationRead]:
    return await service.list_by_port_id(port_id, page, size)
