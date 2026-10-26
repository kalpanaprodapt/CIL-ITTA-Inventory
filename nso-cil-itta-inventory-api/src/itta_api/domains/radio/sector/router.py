"""HTTP routes for Sector."""
from __future__ import annotations

from uuid import UUID

from fastapi import APIRouter, Depends, Query, Request, Response, status
from fastapi.responses import PlainTextResponse

from itta_core.auth import Principal, require_read, require_write
from itta_core.dependencies import service_provider
from itta_core.diffing import ReconciliationItem
from itta_core.models import EntityMeta, LifecycleStatus
from itta_core.pagination import Page
from itta_api.domains.radio.sector.filters import SectorFilter
from itta_api.domains.radio.sector.meta import META
from itta_api.domains.radio.sector.schemas import (
    SectorBulkResult,
    SectorCreate,
    SectorRead,
    SectorSummary,
    SectorUpdate,
)
from itta_api.domains.radio.sector.service import SectorService

router = APIRouter(prefix="/api/v1/sectors", tags=["Sector"])
get_service = service_provider("sector")


@router.get("/meta", response_model=EntityMeta, summary="Field catalogue for Sector")
async def meta() -> EntityMeta:
    return META


@router.get("", response_model=Page[SectorRead], summary="Search Sector records")
async def search(
    flt: SectorFilter = Depends(),
    page: int = Query(0, ge=0),
    size: int = Query(20, ge=1, le=200),
    sort: str | None = Query(None, description="field,asc|desc"),
    service: SectorService = Depends(get_service),
    _: Principal = Depends(require_read),
) -> Page[SectorRead]:
    return await service.search(flt, page, size, sort)


@router.get("/summaries", response_model=list[SectorSummary], summary="Pick-list of Sector records")
async def summaries(
    status_filter: LifecycleStatus | None = Query(None, alias="status"),
    service: SectorService = Depends(get_service),
    _: Principal = Depends(require_read),
) -> list[SectorSummary]:
    return await service.summaries(status_filter)


@router.get("/stats/status", response_model=dict[str, int], summary="Counts per lifecycle status")
async def status_stats(
    service: SectorService = Depends(get_service), _: Principal = Depends(require_read)
) -> dict[str, int]:
    return await service.stats()


@router.get("/export", response_class=PlainTextResponse, summary="Export Sector records as CSV")
async def export(
    flt: SectorFilter = Depends(),
    service: SectorService = Depends(get_service),
    _: Principal = Depends(require_read),
) -> PlainTextResponse:
    body = await service.export_csv(flt)
    headers = {"Content-Disposition": 'attachment; filename="sectors.csv"'}
    return PlainTextResponse(body, media_type="text/csv", headers=headers)


@router.get("/by-code/{code}", response_model=SectorRead, summary="Get a Sector by business code")
async def get_by_code(
    code: str, service: SectorService = Depends(get_service), _: Principal = Depends(require_read)
) -> SectorRead:
    return await service.get_by_code(code)


@router.head("/by-code/{code}", summary="Check whether a Sector code exists")
async def exists(
    code: str, service: SectorService = Depends(get_service), _: Principal = Depends(require_read)
) -> Response:
    found = await service.exists(code)
    return Response(status_code=status.HTTP_200_OK if found else status.HTTP_404_NOT_FOUND)


@router.get("/{id}", response_model=SectorRead, summary="Get a Sector by id")
async def get(
    id: UUID, service: SectorService = Depends(get_service), _: Principal = Depends(require_read)
) -> SectorRead:
    return await service.get(id)


@router.post("", response_model=SectorRead, status_code=status.HTTP_201_CREATED, summary="Create a Sector")
async def create(
    payload: SectorCreate,
    request: Request,
    response: Response,
    service: SectorService = Depends(get_service),
    _: Principal = Depends(require_write),
) -> SectorRead:
    created = await service.create(payload)
    response.headers["Location"] = f"{request.url.path}/{created.id}"
    return created


@router.put("/{id}", response_model=SectorRead, summary="Update a Sector")
async def update(
    id: UUID,
    payload: SectorUpdate,
    service: SectorService = Depends(get_service),
    _: Principal = Depends(require_write),
) -> SectorRead:
    return await service.update(id, payload)


@router.patch("/{id}/status", response_model=SectorRead, summary="Change lifecycle status")
async def change_status(
    id: UUID,
    new_status: LifecycleStatus = Query(..., alias="status"),
    service: SectorService = Depends(get_service),
    _: Principal = Depends(require_write),
) -> SectorRead:
    return await service.change_status(id, new_status)


@router.delete("/{id}", status_code=status.HTTP_204_NO_CONTENT, summary="Delete a Sector")
async def delete(
    id: UUID, service: SectorService = Depends(get_service), _: Principal = Depends(require_write)
) -> Response:
    await service.delete(id)
    return Response(status_code=status.HTTP_204_NO_CONTENT)


@router.post("/bulk", response_model=SectorBulkResult, summary="Create many Sector records")
async def bulk_create(
    payloads: list[SectorCreate],
    service: SectorService = Depends(get_service),
    _: Principal = Depends(require_write),
) -> SectorBulkResult:
    return await service.bulk_create(payloads)


@router.post("/reconcile", response_model=list[ReconciliationItem], summary="Reconcile external Sector records")
async def reconcile(
    records: list[dict[str, object]],
    apply: bool = Query(False, description="Apply the plan instead of a dry run"),
    service: SectorService = Depends(get_service),
    _: Principal = Depends(require_write),
) -> list[ReconciliationItem]:
    return await service.reconcile(records, apply)


@router.get("/by-base-station-id/{base_station_id}", response_model=Page[SectorRead], summary="List Sector records by Base Station Id")
async def list_by_base_station_id(
    base_station_id: UUID,
    page: int = Query(0, ge=0),
    size: int = Query(20, ge=1, le=200),
    service: SectorService = Depends(get_service),
) -> Page[SectorRead]:
    return await service.list_by_base_station_id(base_station_id, page, size)
