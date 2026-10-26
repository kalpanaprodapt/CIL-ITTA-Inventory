"""HTTP routes for Fiber Strand."""
from __future__ import annotations

from uuid import UUID

from fastapi import APIRouter, Depends, Query, Request, Response, status
from fastapi.responses import PlainTextResponse

from itta_core.auth import Principal, require_read, require_write
from itta_core.dependencies import service_provider
from itta_core.diffing import ReconciliationItem
from itta_core.models import EntityMeta, LifecycleStatus
from itta_core.pagination import Page
from itta_api.domains.cabling.fiber_strand.filters import FiberStrandFilter
from itta_api.domains.cabling.fiber_strand.meta import META
from itta_api.domains.cabling.fiber_strand.schemas import (
    FiberStrandBulkResult,
    FiberStrandCreate,
    FiberStrandRead,
    FiberStrandSummary,
    FiberStrandUpdate,
)
from itta_api.domains.cabling.fiber_strand.service import FiberStrandService

router = APIRouter(prefix="/api/v1/fiber-strands", tags=["Fiber Strand"])
get_service = service_provider("fiber_strand")


@router.get("/meta", response_model=EntityMeta, summary="Field catalogue for Fiber Strand")
async def meta() -> EntityMeta:
    return META


@router.get("", response_model=Page[FiberStrandRead], summary="Search Fiber Strand records")
async def search(
    flt: FiberStrandFilter = Depends(),
    page: int = Query(0, ge=0),
    size: int = Query(20, ge=1, le=200),
    sort: str | None = Query(None, description="field,asc|desc"),
    service: FiberStrandService = Depends(get_service),
    _: Principal = Depends(require_read),
) -> Page[FiberStrandRead]:
    return await service.search(flt, page, size, sort)


@router.get("/summaries", response_model=list[FiberStrandSummary], summary="Pick-list of Fiber Strand records")
async def summaries(
    status_filter: LifecycleStatus | None = Query(None, alias="status"),
    service: FiberStrandService = Depends(get_service),
    _: Principal = Depends(require_read),
) -> list[FiberStrandSummary]:
    return await service.summaries(status_filter)


@router.get("/stats/status", response_model=dict[str, int], summary="Counts per lifecycle status")
async def status_stats(
    service: FiberStrandService = Depends(get_service), _: Principal = Depends(require_read)
) -> dict[str, int]:
    return await service.stats()


@router.get("/export", response_class=PlainTextResponse, summary="Export Fiber Strand records as CSV")
async def export(
    flt: FiberStrandFilter = Depends(),
    service: FiberStrandService = Depends(get_service),
    _: Principal = Depends(require_read),
) -> PlainTextResponse:
    body = await service.export_csv(flt)
    headers = {"Content-Disposition": 'attachment; filename="fiber-strands.csv"'}
    return PlainTextResponse(body, media_type="text/csv", headers=headers)


@router.get("/by-code/{code}", response_model=FiberStrandRead, summary="Get a Fiber Strand by business code")
async def get_by_code(
    code: str, service: FiberStrandService = Depends(get_service), _: Principal = Depends(require_read)
) -> FiberStrandRead:
    return await service.get_by_code(code)


@router.head("/by-code/{code}", summary="Check whether a Fiber Strand code exists")
async def exists(
    code: str, service: FiberStrandService = Depends(get_service), _: Principal = Depends(require_read)
) -> Response:
    found = await service.exists(code)
    return Response(status_code=status.HTTP_200_OK if found else status.HTTP_404_NOT_FOUND)


@router.get("/{id}", response_model=FiberStrandRead, summary="Get a Fiber Strand by id")
async def get(
    id: UUID, service: FiberStrandService = Depends(get_service), _: Principal = Depends(require_read)
) -> FiberStrandRead:
    return await service.get(id)


@router.post("", response_model=FiberStrandRead, status_code=status.HTTP_201_CREATED, summary="Create a Fiber Strand")
async def create(
    payload: FiberStrandCreate,
    request: Request,
    response: Response,
    service: FiberStrandService = Depends(get_service),
    _: Principal = Depends(require_write),
) -> FiberStrandRead:
    created = await service.create(payload)
    response.headers["Location"] = f"{request.url.path}/{created.id}"
    return created


@router.put("/{id}", response_model=FiberStrandRead, summary="Update a Fiber Strand")
async def update(
    id: UUID,
    payload: FiberStrandUpdate,
    service: FiberStrandService = Depends(get_service),
    _: Principal = Depends(require_write),
) -> FiberStrandRead:
    return await service.update(id, payload)


@router.patch("/{id}/status", response_model=FiberStrandRead, summary="Change lifecycle status")
async def change_status(
    id: UUID,
    new_status: LifecycleStatus = Query(..., alias="status"),
    service: FiberStrandService = Depends(get_service),
    _: Principal = Depends(require_write),
) -> FiberStrandRead:
    return await service.change_status(id, new_status)


@router.delete("/{id}", status_code=status.HTTP_204_NO_CONTENT, summary="Delete a Fiber Strand")
async def delete(
    id: UUID, service: FiberStrandService = Depends(get_service), _: Principal = Depends(require_write)
) -> Response:
    await service.delete(id)
    return Response(status_code=status.HTTP_204_NO_CONTENT)


@router.post("/bulk", response_model=FiberStrandBulkResult, summary="Create many Fiber Strand records")
async def bulk_create(
    payloads: list[FiberStrandCreate],
    service: FiberStrandService = Depends(get_service),
    _: Principal = Depends(require_write),
) -> FiberStrandBulkResult:
    return await service.bulk_create(payloads)


@router.post("/reconcile", response_model=list[ReconciliationItem], summary="Reconcile external Fiber Strand records")
async def reconcile(
    records: list[dict[str, object]],
    apply: bool = Query(False, description="Apply the plan instead of a dry run"),
    service: FiberStrandService = Depends(get_service),
    _: Principal = Depends(require_write),
) -> list[ReconciliationItem]:
    return await service.reconcile(records, apply)


@router.get("/by-cable-id/{cable_id}", response_model=Page[FiberStrandRead], summary="List Fiber Strand records by Cable Id")
async def list_by_cable_id(
    cable_id: UUID,
    page: int = Query(0, ge=0),
    size: int = Query(20, ge=1, le=200),
    service: FiberStrandService = Depends(get_service),
) -> Page[FiberStrandRead]:
    return await service.list_by_cable_id(cable_id, page, size)
