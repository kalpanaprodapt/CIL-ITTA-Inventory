"""HTTP routes for Building."""
from __future__ import annotations

from uuid import UUID

from fastapi import APIRouter, Depends, Query, Request, Response, status
from fastapi.responses import PlainTextResponse

from itta_core.auth import Principal, require_read, require_write
from itta_core.dependencies import service_provider
from itta_core.diffing import ReconciliationItem
from itta_core.models import EntityMeta, LifecycleStatus
from itta_core.pagination import Page
from itta_api.domains.geography.building.filters import BuildingFilter
from itta_api.domains.geography.building.meta import META
from itta_api.domains.geography.building.schemas import (
    BuildingBulkResult,
    BuildingCreate,
    BuildingRead,
    BuildingSummary,
    BuildingUpdate,
)
from itta_api.domains.geography.building.service import BuildingService

router = APIRouter(prefix="/api/v1/buildings", tags=["Building"])
get_service = service_provider("building")


@router.get("/meta", response_model=EntityMeta, summary="Field catalogue for Building")
async def meta() -> EntityMeta:
    return META


@router.get("", response_model=Page[BuildingRead], summary="Search Building records")
async def search(
    flt: BuildingFilter = Depends(),
    page: int = Query(0, ge=0),
    size: int = Query(20, ge=1, le=200),
    sort: str | None = Query(None, description="field,asc|desc"),
    service: BuildingService = Depends(get_service),
    _: Principal = Depends(require_read),
) -> Page[BuildingRead]:
    return await service.search(flt, page, size, sort)


@router.get("/summaries", response_model=list[BuildingSummary], summary="Pick-list of Building records")
async def summaries(
    status_filter: LifecycleStatus | None = Query(None, alias="status"),
    service: BuildingService = Depends(get_service),
    _: Principal = Depends(require_read),
) -> list[BuildingSummary]:
    return await service.summaries(status_filter)


@router.get("/stats/status", response_model=dict[str, int], summary="Counts per lifecycle status")
async def status_stats(
    service: BuildingService = Depends(get_service), _: Principal = Depends(require_read)
) -> dict[str, int]:
    return await service.stats()


@router.get("/export", response_class=PlainTextResponse, summary="Export Building records as CSV")
async def export(
    flt: BuildingFilter = Depends(),
    service: BuildingService = Depends(get_service),
    _: Principal = Depends(require_read),
) -> PlainTextResponse:
    body = await service.export_csv(flt)
    headers = {"Content-Disposition": 'attachment; filename="buildings.csv"'}
    return PlainTextResponse(body, media_type="text/csv", headers=headers)


@router.get("/by-code/{code}", response_model=BuildingRead, summary="Get a Building by business code")
async def get_by_code(
    code: str, service: BuildingService = Depends(get_service), _: Principal = Depends(require_read)
) -> BuildingRead:
    return await service.get_by_code(code)


@router.head("/by-code/{code}", summary="Check whether a Building code exists")
async def exists(
    code: str, service: BuildingService = Depends(get_service), _: Principal = Depends(require_read)
) -> Response:
    found = await service.exists(code)
    return Response(status_code=status.HTTP_200_OK if found else status.HTTP_404_NOT_FOUND)


@router.get("/{id}", response_model=BuildingRead, summary="Get a Building by id")
async def get(
    id: UUID, service: BuildingService = Depends(get_service), _: Principal = Depends(require_read)
) -> BuildingRead:
    return await service.get(id)


@router.post("", response_model=BuildingRead, status_code=status.HTTP_201_CREATED, summary="Create a Building")
async def create(
    payload: BuildingCreate,
    request: Request,
    response: Response,
    service: BuildingService = Depends(get_service),
    _: Principal = Depends(require_write),
) -> BuildingRead:
    created = await service.create(payload)
    response.headers["Location"] = f"{request.url.path}/{created.id}"
    return created


@router.put("/{id}", response_model=BuildingRead, summary="Update a Building")
async def update(
    id: UUID,
    payload: BuildingUpdate,
    service: BuildingService = Depends(get_service),
    _: Principal = Depends(require_write),
) -> BuildingRead:
    return await service.update(id, payload)


@router.patch("/{id}/status", response_model=BuildingRead, summary="Change lifecycle status")
async def change_status(
    id: UUID,
    new_status: LifecycleStatus = Query(..., alias="status"),
    service: BuildingService = Depends(get_service),
    _: Principal = Depends(require_write),
) -> BuildingRead:
    return await service.change_status(id, new_status)


@router.delete("/{id}", status_code=status.HTTP_204_NO_CONTENT, summary="Delete a Building")
async def delete(
    id: UUID, service: BuildingService = Depends(get_service), _: Principal = Depends(require_write)
) -> Response:
    await service.delete(id)
    return Response(status_code=status.HTTP_204_NO_CONTENT)


@router.post("/bulk", response_model=BuildingBulkResult, summary="Create many Building records")
async def bulk_create(
    payloads: list[BuildingCreate],
    service: BuildingService = Depends(get_service),
    _: Principal = Depends(require_write),
) -> BuildingBulkResult:
    return await service.bulk_create(payloads)


@router.post("/reconcile", response_model=list[ReconciliationItem], summary="Reconcile external Building records")
async def reconcile(
    records: list[dict[str, object]],
    apply: bool = Query(False, description="Apply the plan instead of a dry run"),
    service: BuildingService = Depends(get_service),
    _: Principal = Depends(require_write),
) -> list[ReconciliationItem]:
    return await service.reconcile(records, apply)


@router.get("/by-site-id/{site_id}", response_model=Page[BuildingRead], summary="List Building records by Site Id")
async def list_by_site_id(
    site_id: UUID,
    page: int = Query(0, ge=0),
    size: int = Query(20, ge=1, le=200),
    service: BuildingService = Depends(get_service),
) -> Page[BuildingRead]:
    return await service.list_by_site_id(site_id, page, size)
