"""HTTP routes for Site."""
from __future__ import annotations

from uuid import UUID

from fastapi import APIRouter, Depends, Query, Request, Response, status
from fastapi.responses import PlainTextResponse

from itta_core.auth import Principal, require_read, require_write
from itta_core.dependencies import service_provider
from itta_core.diffing import ReconciliationItem
from itta_core.models import EntityMeta, LifecycleStatus
from itta_core.pagination import Page
from itta_api.domains.geography.site.filters import SiteFilter
from itta_api.domains.geography.site.meta import META
from itta_api.domains.geography.site.schemas import (
    SiteBulkResult,
    SiteCreate,
    SiteRead,
    SiteSummary,
    SiteUpdate,
)
from itta_api.domains.geography.site.service import SiteService

router = APIRouter(prefix="/api/v1/sites", tags=["Site"])
get_service = service_provider("site")


@router.get("/meta", response_model=EntityMeta, summary="Field catalogue for Site")
async def meta() -> EntityMeta:
    return META


@router.get("", response_model=Page[SiteRead], summary="Search Site records")
async def search(
    flt: SiteFilter = Depends(),
    page: int = Query(0, ge=0),
    size: int = Query(20, ge=1, le=200),
    sort: str | None = Query(None, description="field,asc|desc"),
    service: SiteService = Depends(get_service),
    _: Principal = Depends(require_read),
) -> Page[SiteRead]:
    return await service.search(flt, page, size, sort)


@router.get("/summaries", response_model=list[SiteSummary], summary="Pick-list of Site records")
async def summaries(
    status_filter: LifecycleStatus | None = Query(None, alias="status"),
    service: SiteService = Depends(get_service),
    _: Principal = Depends(require_read),
) -> list[SiteSummary]:
    return await service.summaries(status_filter)


@router.get("/stats/status", response_model=dict[str, int], summary="Counts per lifecycle status")
async def status_stats(
    service: SiteService = Depends(get_service), _: Principal = Depends(require_read)
) -> dict[str, int]:
    return await service.stats()


@router.get("/export", response_class=PlainTextResponse, summary="Export Site records as CSV")
async def export(
    flt: SiteFilter = Depends(),
    service: SiteService = Depends(get_service),
    _: Principal = Depends(require_read),
) -> PlainTextResponse:
    body = await service.export_csv(flt)
    headers = {"Content-Disposition": 'attachment; filename="sites.csv"'}
    return PlainTextResponse(body, media_type="text/csv", headers=headers)


@router.get("/by-code/{code}", response_model=SiteRead, summary="Get a Site by business code")
async def get_by_code(
    code: str, service: SiteService = Depends(get_service), _: Principal = Depends(require_read)
) -> SiteRead:
    return await service.get_by_code(code)


@router.head("/by-code/{code}", summary="Check whether a Site code exists")
async def exists(
    code: str, service: SiteService = Depends(get_service), _: Principal = Depends(require_read)
) -> Response:
    found = await service.exists(code)
    return Response(status_code=status.HTTP_200_OK if found else status.HTTP_404_NOT_FOUND)


@router.get("/{id}", response_model=SiteRead, summary="Get a Site by id")
async def get(
    id: UUID, service: SiteService = Depends(get_service), _: Principal = Depends(require_read)
) -> SiteRead:
    return await service.get(id)


@router.post("", response_model=SiteRead, status_code=status.HTTP_201_CREATED, summary="Create a Site")
async def create(
    payload: SiteCreate,
    request: Request,
    response: Response,
    service: SiteService = Depends(get_service),
    _: Principal = Depends(require_write),
) -> SiteRead:
    created = await service.create(payload)
    response.headers["Location"] = f"{request.url.path}/{created.id}"
    return created


@router.put("/{id}", response_model=SiteRead, summary="Update a Site")
async def update(
    id: UUID,
    payload: SiteUpdate,
    service: SiteService = Depends(get_service),
    _: Principal = Depends(require_write),
) -> SiteRead:
    return await service.update(id, payload)


@router.patch("/{id}/status", response_model=SiteRead, summary="Change lifecycle status")
async def change_status(
    id: UUID,
    new_status: LifecycleStatus = Query(..., alias="status"),
    service: SiteService = Depends(get_service),
    _: Principal = Depends(require_write),
) -> SiteRead:
    return await service.change_status(id, new_status)


@router.delete("/{id}", status_code=status.HTTP_204_NO_CONTENT, summary="Delete a Site")
async def delete(
    id: UUID, service: SiteService = Depends(get_service), _: Principal = Depends(require_write)
) -> Response:
    await service.delete(id)
    return Response(status_code=status.HTTP_204_NO_CONTENT)


@router.post("/bulk", response_model=SiteBulkResult, summary="Create many Site records")
async def bulk_create(
    payloads: list[SiteCreate],
    service: SiteService = Depends(get_service),
    _: Principal = Depends(require_write),
) -> SiteBulkResult:
    return await service.bulk_create(payloads)


@router.post("/reconcile", response_model=list[ReconciliationItem], summary="Reconcile external Site records")
async def reconcile(
    records: list[dict[str, object]],
    apply: bool = Query(False, description="Apply the plan instead of a dry run"),
    service: SiteService = Depends(get_service),
    _: Principal = Depends(require_write),
) -> list[ReconciliationItem]:
    return await service.reconcile(records, apply)


@router.get("/by-city-id/{city_id}", response_model=Page[SiteRead], summary="List Site records by City Id")
async def list_by_city_id(
    city_id: UUID,
    page: int = Query(0, ge=0),
    size: int = Query(20, ge=1, le=200),
    service: SiteService = Depends(get_service),
) -> Page[SiteRead]:
    return await service.list_by_city_id(city_id, page, size)
