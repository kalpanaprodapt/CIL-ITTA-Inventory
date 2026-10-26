"""HTTP routes for Circuit Segment."""
from __future__ import annotations

from uuid import UUID

from fastapi import APIRouter, Depends, Query, Request, Response, status
from fastapi.responses import PlainTextResponse

from itta_core.auth import Principal, require_read, require_write
from itta_core.dependencies import service_provider
from itta_core.diffing import ReconciliationItem
from itta_core.models import EntityMeta, LifecycleStatus
from itta_core.pagination import Page
from itta_api.domains.circuit.circuit_segment.filters import CircuitSegmentFilter
from itta_api.domains.circuit.circuit_segment.meta import META
from itta_api.domains.circuit.circuit_segment.schemas import (
    CircuitSegmentBulkResult,
    CircuitSegmentCreate,
    CircuitSegmentRead,
    CircuitSegmentSummary,
    CircuitSegmentUpdate,
)
from itta_api.domains.circuit.circuit_segment.service import CircuitSegmentService

router = APIRouter(prefix="/api/v1/circuit-segments", tags=["Circuit Segment"])
get_service = service_provider("circuit_segment")


@router.get("/meta", response_model=EntityMeta, summary="Field catalogue for Circuit Segment")
async def meta() -> EntityMeta:
    return META


@router.get("", response_model=Page[CircuitSegmentRead], summary="Search Circuit Segment records")
async def search(
    flt: CircuitSegmentFilter = Depends(),
    page: int = Query(0, ge=0),
    size: int = Query(20, ge=1, le=200),
    sort: str | None = Query(None, description="field,asc|desc"),
    service: CircuitSegmentService = Depends(get_service),
    _: Principal = Depends(require_read),
) -> Page[CircuitSegmentRead]:
    return await service.search(flt, page, size, sort)


@router.get("/summaries", response_model=list[CircuitSegmentSummary], summary="Pick-list of Circuit Segment records")
async def summaries(
    status_filter: LifecycleStatus | None = Query(None, alias="status"),
    service: CircuitSegmentService = Depends(get_service),
    _: Principal = Depends(require_read),
) -> list[CircuitSegmentSummary]:
    return await service.summaries(status_filter)


@router.get("/stats/status", response_model=dict[str, int], summary="Counts per lifecycle status")
async def status_stats(
    service: CircuitSegmentService = Depends(get_service), _: Principal = Depends(require_read)
) -> dict[str, int]:
    return await service.stats()


@router.get("/export", response_class=PlainTextResponse, summary="Export Circuit Segment records as CSV")
async def export(
    flt: CircuitSegmentFilter = Depends(),
    service: CircuitSegmentService = Depends(get_service),
    _: Principal = Depends(require_read),
) -> PlainTextResponse:
    body = await service.export_csv(flt)
    headers = {"Content-Disposition": 'attachment; filename="circuit-segments.csv"'}
    return PlainTextResponse(body, media_type="text/csv", headers=headers)


@router.get("/by-code/{code}", response_model=CircuitSegmentRead, summary="Get a Circuit Segment by business code")
async def get_by_code(
    code: str, service: CircuitSegmentService = Depends(get_service), _: Principal = Depends(require_read)
) -> CircuitSegmentRead:
    return await service.get_by_code(code)


@router.head("/by-code/{code}", summary="Check whether a Circuit Segment code exists")
async def exists(
    code: str, service: CircuitSegmentService = Depends(get_service), _: Principal = Depends(require_read)
) -> Response:
    found = await service.exists(code)
    return Response(status_code=status.HTTP_200_OK if found else status.HTTP_404_NOT_FOUND)


@router.get("/{id}", response_model=CircuitSegmentRead, summary="Get a Circuit Segment by id")
async def get(
    id: UUID, service: CircuitSegmentService = Depends(get_service), _: Principal = Depends(require_read)
) -> CircuitSegmentRead:
    return await service.get(id)


@router.post("", response_model=CircuitSegmentRead, status_code=status.HTTP_201_CREATED, summary="Create a Circuit Segment")
async def create(
    payload: CircuitSegmentCreate,
    request: Request,
    response: Response,
    service: CircuitSegmentService = Depends(get_service),
    _: Principal = Depends(require_write),
) -> CircuitSegmentRead:
    created = await service.create(payload)
    response.headers["Location"] = f"{request.url.path}/{created.id}"
    return created


@router.put("/{id}", response_model=CircuitSegmentRead, summary="Update a Circuit Segment")
async def update(
    id: UUID,
    payload: CircuitSegmentUpdate,
    service: CircuitSegmentService = Depends(get_service),
    _: Principal = Depends(require_write),
) -> CircuitSegmentRead:
    return await service.update(id, payload)


@router.patch("/{id}/status", response_model=CircuitSegmentRead, summary="Change lifecycle status")
async def change_status(
    id: UUID,
    new_status: LifecycleStatus = Query(..., alias="status"),
    service: CircuitSegmentService = Depends(get_service),
    _: Principal = Depends(require_write),
) -> CircuitSegmentRead:
    return await service.change_status(id, new_status)


@router.delete("/{id}", status_code=status.HTTP_204_NO_CONTENT, summary="Delete a Circuit Segment")
async def delete(
    id: UUID, service: CircuitSegmentService = Depends(get_service), _: Principal = Depends(require_write)
) -> Response:
    await service.delete(id)
    return Response(status_code=status.HTTP_204_NO_CONTENT)


@router.post("/bulk", response_model=CircuitSegmentBulkResult, summary="Create many Circuit Segment records")
async def bulk_create(
    payloads: list[CircuitSegmentCreate],
    service: CircuitSegmentService = Depends(get_service),
    _: Principal = Depends(require_write),
) -> CircuitSegmentBulkResult:
    return await service.bulk_create(payloads)


@router.post("/reconcile", response_model=list[ReconciliationItem], summary="Reconcile external Circuit Segment records")
async def reconcile(
    records: list[dict[str, object]],
    apply: bool = Query(False, description="Apply the plan instead of a dry run"),
    service: CircuitSegmentService = Depends(get_service),
    _: Principal = Depends(require_write),
) -> list[ReconciliationItem]:
    return await service.reconcile(records, apply)


@router.get("/by-circuit-id/{circuit_id}", response_model=Page[CircuitSegmentRead], summary="List Circuit Segment records by Circuit Id")
async def list_by_circuit_id(
    circuit_id: UUID,
    page: int = Query(0, ge=0),
    size: int = Query(20, ge=1, le=200),
    service: CircuitSegmentService = Depends(get_service),
) -> Page[CircuitSegmentRead]:
    return await service.list_by_circuit_id(circuit_id, page, size)

@router.get("/by-a-termination-id/{a_termination_id}", response_model=Page[CircuitSegmentRead], summary="List Circuit Segment records by A Termination Id")
async def list_by_a_termination_id(
    a_termination_id: UUID,
    page: int = Query(0, ge=0),
    size: int = Query(20, ge=1, le=200),
    service: CircuitSegmentService = Depends(get_service),
) -> Page[CircuitSegmentRead]:
    return await service.list_by_a_termination_id(a_termination_id, page, size)

@router.get("/by-z-termination-id/{z_termination_id}", response_model=Page[CircuitSegmentRead], summary="List Circuit Segment records by Z Termination Id")
async def list_by_z_termination_id(
    z_termination_id: UUID,
    page: int = Query(0, ge=0),
    size: int = Query(20, ge=1, le=200),
    service: CircuitSegmentService = Depends(get_service),
) -> Page[CircuitSegmentRead]:
    return await service.list_by_z_termination_id(z_termination_id, page, size)
