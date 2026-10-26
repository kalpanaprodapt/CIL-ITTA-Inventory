"""HTTP routes for Region."""
from __future__ import annotations

from uuid import UUID

from fastapi import APIRouter, Depends, Query, Request, Response, status
from fastapi.responses import PlainTextResponse

from itta_core.auth import Principal, require_read, require_write
from itta_core.dependencies import service_provider
from itta_core.diffing import ReconciliationItem
from itta_core.models import EntityMeta, LifecycleStatus
from itta_core.pagination import Page
from itta_api.domains.geography.region.filters import RegionFilter
from itta_api.domains.geography.region.meta import META
from itta_api.domains.geography.region.schemas import (
    RegionBulkResult,
    RegionCreate,
    RegionRead,
    RegionSummary,
    RegionUpdate,
)
from itta_api.domains.geography.region.service import RegionService

router = APIRouter(prefix="/api/v1/regions", tags=["Region"])
get_service = service_provider("region")


@router.get("/meta", response_model=EntityMeta, summary="Field catalogue for Region")
async def meta() -> EntityMeta:
    return META


@router.get("", response_model=Page[RegionRead], summary="Search Region records")
async def search(
    flt: RegionFilter = Depends(),
    page: int = Query(0, ge=0),
    size: int = Query(20, ge=1, le=200),
    sort: str | None = Query(None, description="field,asc|desc"),
    service: RegionService = Depends(get_service),
    _: Principal = Depends(require_read),
) -> Page[RegionRead]:
    return await service.search(flt, page, size, sort)


@router.get("/summaries", response_model=list[RegionSummary], summary="Pick-list of Region records")
async def summaries(
    status_filter: LifecycleStatus | None = Query(None, alias="status"),
    service: RegionService = Depends(get_service),
    _: Principal = Depends(require_read),
) -> list[RegionSummary]:
    return await service.summaries(status_filter)


@router.get("/stats/status", response_model=dict[str, int], summary="Counts per lifecycle status")
async def status_stats(
    service: RegionService = Depends(get_service), _: Principal = Depends(require_read)
) -> dict[str, int]:
    return await service.stats()


@router.get("/export", response_class=PlainTextResponse, summary="Export Region records as CSV")
async def export(
    flt: RegionFilter = Depends(),
    service: RegionService = Depends(get_service),
    _: Principal = Depends(require_read),
) -> PlainTextResponse:
    body = await service.export_csv(flt)
    headers = {"Content-Disposition": 'attachment; filename="regions.csv"'}
    return PlainTextResponse(body, media_type="text/csv", headers=headers)


@router.get("/by-code/{code}", response_model=RegionRead, summary="Get a Region by business code")
async def get_by_code(
    code: str, service: RegionService = Depends(get_service), _: Principal = Depends(require_read)
) -> RegionRead:
    return await service.get_by_code(code)


@router.head("/by-code/{code}", summary="Check whether a Region code exists")
async def exists(
    code: str, service: RegionService = Depends(get_service), _: Principal = Depends(require_read)
) -> Response:
    found = await service.exists(code)
    return Response(status_code=status.HTTP_200_OK if found else status.HTTP_404_NOT_FOUND)


@router.get("/{id}", response_model=RegionRead, summary="Get a Region by id")
async def get(
    id: UUID, service: RegionService = Depends(get_service), _: Principal = Depends(require_read)
) -> RegionRead:
    return await service.get(id)


@router.post("", response_model=RegionRead, status_code=status.HTTP_201_CREATED, summary="Create a Region")
async def create(
    payload: RegionCreate,
    request: Request,
    response: Response,
    service: RegionService = Depends(get_service),
    _: Principal = Depends(require_write),
) -> RegionRead:
    created = await service.create(payload)
    response.headers["Location"] = f"{request.url.path}/{created.id}"
    return created


@router.put("/{id}", response_model=RegionRead, summary="Update a Region")
async def update(
    id: UUID,
    payload: RegionUpdate,
    service: RegionService = Depends(get_service),
    _: Principal = Depends(require_write),
) -> RegionRead:
    return await service.update(id, payload)


@router.patch("/{id}/status", response_model=RegionRead, summary="Change lifecycle status")
async def change_status(
    id: UUID,
    new_status: LifecycleStatus = Query(..., alias="status"),
    service: RegionService = Depends(get_service),
    _: Principal = Depends(require_write),
) -> RegionRead:
    return await service.change_status(id, new_status)


@router.delete("/{id}", status_code=status.HTTP_204_NO_CONTENT, summary="Delete a Region")
async def delete(
    id: UUID, service: RegionService = Depends(get_service), _: Principal = Depends(require_write)
) -> Response:
    await service.delete(id)
    return Response(status_code=status.HTTP_204_NO_CONTENT)


@router.post("/bulk", response_model=RegionBulkResult, summary="Create many Region records")
async def bulk_create(
    payloads: list[RegionCreate],
    service: RegionService = Depends(get_service),
    _: Principal = Depends(require_write),
) -> RegionBulkResult:
    return await service.bulk_create(payloads)


@router.post("/reconcile", response_model=list[ReconciliationItem], summary="Reconcile external Region records")
async def reconcile(
    records: list[dict[str, object]],
    apply: bool = Query(False, description="Apply the plan instead of a dry run"),
    service: RegionService = Depends(get_service),
    _: Principal = Depends(require_write),
) -> list[ReconciliationItem]:
    return await service.reconcile(records, apply)


@router.get("/by-country-id/{country_id}", response_model=Page[RegionRead], summary="List Region records by Country Id")
async def list_by_country_id(
    country_id: UUID,
    page: int = Query(0, ge=0),
    size: int = Query(20, ge=1, le=200),
    service: RegionService = Depends(get_service),
) -> Page[RegionRead]:
    return await service.list_by_country_id(country_id, page, size)
