"""HTTP routes for Warranty."""
from __future__ import annotations

from uuid import UUID

from fastapi import APIRouter, Depends, Query, Request, Response, status
from fastapi.responses import PlainTextResponse

from itta_core.auth import Principal, require_read, require_write
from itta_core.dependencies import service_provider
from itta_core.diffing import ReconciliationItem
from itta_core.models import EntityMeta, LifecycleStatus
from itta_core.pagination import Page
from itta_api.domains.vendor.warranty.filters import WarrantyFilter
from itta_api.domains.vendor.warranty.meta import META
from itta_api.domains.vendor.warranty.schemas import (
    WarrantyBulkResult,
    WarrantyCreate,
    WarrantyRead,
    WarrantySummary,
    WarrantyUpdate,
)
from itta_api.domains.vendor.warranty.service import WarrantyService

router = APIRouter(prefix="/api/v1/warranties", tags=["Warranty"])
get_service = service_provider("warranty")


@router.get("/meta", response_model=EntityMeta, summary="Field catalogue for Warranty")
async def meta() -> EntityMeta:
    return META


@router.get("", response_model=Page[WarrantyRead], summary="Search Warranty records")
async def search(
    flt: WarrantyFilter = Depends(),
    page: int = Query(0, ge=0),
    size: int = Query(20, ge=1, le=200),
    sort: str | None = Query(None, description="field,asc|desc"),
    service: WarrantyService = Depends(get_service),
    _: Principal = Depends(require_read),
) -> Page[WarrantyRead]:
    return await service.search(flt, page, size, sort)


@router.get("/summaries", response_model=list[WarrantySummary], summary="Pick-list of Warranty records")
async def summaries(
    status_filter: LifecycleStatus | None = Query(None, alias="status"),
    service: WarrantyService = Depends(get_service),
    _: Principal = Depends(require_read),
) -> list[WarrantySummary]:
    return await service.summaries(status_filter)


@router.get("/stats/status", response_model=dict[str, int], summary="Counts per lifecycle status")
async def status_stats(
    service: WarrantyService = Depends(get_service), _: Principal = Depends(require_read)
) -> dict[str, int]:
    return await service.stats()


@router.get("/export", response_class=PlainTextResponse, summary="Export Warranty records as CSV")
async def export(
    flt: WarrantyFilter = Depends(),
    service: WarrantyService = Depends(get_service),
    _: Principal = Depends(require_read),
) -> PlainTextResponse:
    body = await service.export_csv(flt)
    headers = {"Content-Disposition": 'attachment; filename="warranties.csv"'}
    return PlainTextResponse(body, media_type="text/csv", headers=headers)


@router.get("/by-code/{code}", response_model=WarrantyRead, summary="Get a Warranty by business code")
async def get_by_code(
    code: str, service: WarrantyService = Depends(get_service), _: Principal = Depends(require_read)
) -> WarrantyRead:
    return await service.get_by_code(code)


@router.head("/by-code/{code}", summary="Check whether a Warranty code exists")
async def exists(
    code: str, service: WarrantyService = Depends(get_service), _: Principal = Depends(require_read)
) -> Response:
    found = await service.exists(code)
    return Response(status_code=status.HTTP_200_OK if found else status.HTTP_404_NOT_FOUND)


@router.get("/{id}", response_model=WarrantyRead, summary="Get a Warranty by id")
async def get(
    id: UUID, service: WarrantyService = Depends(get_service), _: Principal = Depends(require_read)
) -> WarrantyRead:
    return await service.get(id)


@router.post("", response_model=WarrantyRead, status_code=status.HTTP_201_CREATED, summary="Create a Warranty")
async def create(
    payload: WarrantyCreate,
    request: Request,
    response: Response,
    service: WarrantyService = Depends(get_service),
    _: Principal = Depends(require_write),
) -> WarrantyRead:
    created = await service.create(payload)
    response.headers["Location"] = f"{request.url.path}/{created.id}"
    return created


@router.put("/{id}", response_model=WarrantyRead, summary="Update a Warranty")
async def update(
    id: UUID,
    payload: WarrantyUpdate,
    service: WarrantyService = Depends(get_service),
    _: Principal = Depends(require_write),
) -> WarrantyRead:
    return await service.update(id, payload)


@router.patch("/{id}/status", response_model=WarrantyRead, summary="Change lifecycle status")
async def change_status(
    id: UUID,
    new_status: LifecycleStatus = Query(..., alias="status"),
    service: WarrantyService = Depends(get_service),
    _: Principal = Depends(require_write),
) -> WarrantyRead:
    return await service.change_status(id, new_status)


@router.delete("/{id}", status_code=status.HTTP_204_NO_CONTENT, summary="Delete a Warranty")
async def delete(
    id: UUID, service: WarrantyService = Depends(get_service), _: Principal = Depends(require_write)
) -> Response:
    await service.delete(id)
    return Response(status_code=status.HTTP_204_NO_CONTENT)


@router.post("/bulk", response_model=WarrantyBulkResult, summary="Create many Warranty records")
async def bulk_create(
    payloads: list[WarrantyCreate],
    service: WarrantyService = Depends(get_service),
    _: Principal = Depends(require_write),
) -> WarrantyBulkResult:
    return await service.bulk_create(payloads)


@router.post("/reconcile", response_model=list[ReconciliationItem], summary="Reconcile external Warranty records")
async def reconcile(
    records: list[dict[str, object]],
    apply: bool = Query(False, description="Apply the plan instead of a dry run"),
    service: WarrantyService = Depends(get_service),
    _: Principal = Depends(require_write),
) -> list[ReconciliationItem]:
    return await service.reconcile(records, apply)


@router.get("/by-asset-id/{asset_id}", response_model=Page[WarrantyRead], summary="List Warranty records by Asset Id")
async def list_by_asset_id(
    asset_id: UUID,
    page: int = Query(0, ge=0),
    size: int = Query(20, ge=1, le=200),
    service: WarrantyService = Depends(get_service),
) -> Page[WarrantyRead]:
    return await service.list_by_asset_id(asset_id, page, size)
