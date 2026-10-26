"""HTTP routes for Path Trace."""
from __future__ import annotations

from uuid import UUID

from fastapi import APIRouter, Depends, Query, Request, Response, status
from fastapi.responses import PlainTextResponse

from itta_core.auth import Principal, require_read, require_write
from itta_core.dependencies import service_provider
from itta_core.diffing import ReconciliationItem
from itta_core.models import EntityMeta, LifecycleStatus
from itta_core.pagination import Page
from itta_api.domains.topology.path_trace.filters import PathTraceFilter
from itta_api.domains.topology.path_trace.meta import META
from itta_api.domains.topology.path_trace.schemas import (
    PathTraceBulkResult,
    PathTraceCreate,
    PathTraceRead,
    PathTraceSummary,
    PathTraceUpdate,
)
from itta_api.domains.topology.path_trace.service import PathTraceService

router = APIRouter(prefix="/api/v1/path-traces", tags=["Path Trace"])
get_service = service_provider("path_trace")


@router.get("/meta", response_model=EntityMeta, summary="Field catalogue for Path Trace")
async def meta() -> EntityMeta:
    return META


@router.get("", response_model=Page[PathTraceRead], summary="Search Path Trace records")
async def search(
    flt: PathTraceFilter = Depends(),
    page: int = Query(0, ge=0),
    size: int = Query(20, ge=1, le=200),
    sort: str | None = Query(None, description="field,asc|desc"),
    service: PathTraceService = Depends(get_service),
    _: Principal = Depends(require_read),
) -> Page[PathTraceRead]:
    return await service.search(flt, page, size, sort)


@router.get("/summaries", response_model=list[PathTraceSummary], summary="Pick-list of Path Trace records")
async def summaries(
    status_filter: LifecycleStatus | None = Query(None, alias="status"),
    service: PathTraceService = Depends(get_service),
    _: Principal = Depends(require_read),
) -> list[PathTraceSummary]:
    return await service.summaries(status_filter)


@router.get("/stats/status", response_model=dict[str, int], summary="Counts per lifecycle status")
async def status_stats(
    service: PathTraceService = Depends(get_service), _: Principal = Depends(require_read)
) -> dict[str, int]:
    return await service.stats()


@router.get("/export", response_class=PlainTextResponse, summary="Export Path Trace records as CSV")
async def export(
    flt: PathTraceFilter = Depends(),
    service: PathTraceService = Depends(get_service),
    _: Principal = Depends(require_read),
) -> PlainTextResponse:
    body = await service.export_csv(flt)
    headers = {"Content-Disposition": 'attachment; filename="path-traces.csv"'}
    return PlainTextResponse(body, media_type="text/csv", headers=headers)


@router.get("/by-code/{code}", response_model=PathTraceRead, summary="Get a Path Trace by business code")
async def get_by_code(
    code: str, service: PathTraceService = Depends(get_service), _: Principal = Depends(require_read)
) -> PathTraceRead:
    return await service.get_by_code(code)


@router.head("/by-code/{code}", summary="Check whether a Path Trace code exists")
async def exists(
    code: str, service: PathTraceService = Depends(get_service), _: Principal = Depends(require_read)
) -> Response:
    found = await service.exists(code)
    return Response(status_code=status.HTTP_200_OK if found else status.HTTP_404_NOT_FOUND)


@router.get("/{id}", response_model=PathTraceRead, summary="Get a Path Trace by id")
async def get(
    id: UUID, service: PathTraceService = Depends(get_service), _: Principal = Depends(require_read)
) -> PathTraceRead:
    return await service.get(id)


@router.post("", response_model=PathTraceRead, status_code=status.HTTP_201_CREATED, summary="Create a Path Trace")
async def create(
    payload: PathTraceCreate,
    request: Request,
    response: Response,
    service: PathTraceService = Depends(get_service),
    _: Principal = Depends(require_write),
) -> PathTraceRead:
    created = await service.create(payload)
    response.headers["Location"] = f"{request.url.path}/{created.id}"
    return created


@router.put("/{id}", response_model=PathTraceRead, summary="Update a Path Trace")
async def update(
    id: UUID,
    payload: PathTraceUpdate,
    service: PathTraceService = Depends(get_service),
    _: Principal = Depends(require_write),
) -> PathTraceRead:
    return await service.update(id, payload)


@router.patch("/{id}/status", response_model=PathTraceRead, summary="Change lifecycle status")
async def change_status(
    id: UUID,
    new_status: LifecycleStatus = Query(..., alias="status"),
    service: PathTraceService = Depends(get_service),
    _: Principal = Depends(require_write),
) -> PathTraceRead:
    return await service.change_status(id, new_status)


@router.delete("/{id}", status_code=status.HTTP_204_NO_CONTENT, summary="Delete a Path Trace")
async def delete(
    id: UUID, service: PathTraceService = Depends(get_service), _: Principal = Depends(require_write)
) -> Response:
    await service.delete(id)
    return Response(status_code=status.HTTP_204_NO_CONTENT)


@router.post("/bulk", response_model=PathTraceBulkResult, summary="Create many Path Trace records")
async def bulk_create(
    payloads: list[PathTraceCreate],
    service: PathTraceService = Depends(get_service),
    _: Principal = Depends(require_write),
) -> PathTraceBulkResult:
    return await service.bulk_create(payloads)


@router.post("/reconcile", response_model=list[ReconciliationItem], summary="Reconcile external Path Trace records")
async def reconcile(
    records: list[dict[str, object]],
    apply: bool = Query(False, description="Apply the plan instead of a dry run"),
    service: PathTraceService = Depends(get_service),
    _: Principal = Depends(require_write),
) -> list[ReconciliationItem]:
    return await service.reconcile(records, apply)

