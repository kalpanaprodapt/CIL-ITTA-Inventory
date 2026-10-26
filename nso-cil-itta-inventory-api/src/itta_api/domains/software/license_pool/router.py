"""HTTP routes for License Pool."""
from __future__ import annotations

from uuid import UUID

from fastapi import APIRouter, Depends, Query, Request, Response, status
from fastapi.responses import PlainTextResponse

from itta_core.auth import Principal, require_read, require_write
from itta_core.dependencies import service_provider
from itta_core.diffing import ReconciliationItem
from itta_core.models import EntityMeta, LifecycleStatus
from itta_core.pagination import Page
from itta_api.domains.software.license_pool.filters import LicensePoolFilter
from itta_api.domains.software.license_pool.meta import META
from itta_api.domains.software.license_pool.schemas import (
    LicensePoolBulkResult,
    LicensePoolCreate,
    LicensePoolRead,
    LicensePoolSummary,
    LicensePoolUpdate,
)
from itta_api.domains.software.license_pool.service import LicensePoolService

router = APIRouter(prefix="/api/v1/license-pools", tags=["License Pool"])
get_service = service_provider("license_pool")


@router.get("/meta", response_model=EntityMeta, summary="Field catalogue for License Pool")
async def meta() -> EntityMeta:
    return META


@router.get("", response_model=Page[LicensePoolRead], summary="Search License Pool records")
async def search(
    flt: LicensePoolFilter = Depends(),
    page: int = Query(0, ge=0),
    size: int = Query(20, ge=1, le=200),
    sort: str | None = Query(None, description="field,asc|desc"),
    service: LicensePoolService = Depends(get_service),
    _: Principal = Depends(require_read),
) -> Page[LicensePoolRead]:
    return await service.search(flt, page, size, sort)


@router.get("/summaries", response_model=list[LicensePoolSummary], summary="Pick-list of License Pool records")
async def summaries(
    status_filter: LifecycleStatus | None = Query(None, alias="status"),
    service: LicensePoolService = Depends(get_service),
    _: Principal = Depends(require_read),
) -> list[LicensePoolSummary]:
    return await service.summaries(status_filter)


@router.get("/stats/status", response_model=dict[str, int], summary="Counts per lifecycle status")
async def status_stats(
    service: LicensePoolService = Depends(get_service), _: Principal = Depends(require_read)
) -> dict[str, int]:
    return await service.stats()


@router.get("/export", response_class=PlainTextResponse, summary="Export License Pool records as CSV")
async def export(
    flt: LicensePoolFilter = Depends(),
    service: LicensePoolService = Depends(get_service),
    _: Principal = Depends(require_read),
) -> PlainTextResponse:
    body = await service.export_csv(flt)
    headers = {"Content-Disposition": 'attachment; filename="license-pools.csv"'}
    return PlainTextResponse(body, media_type="text/csv", headers=headers)


@router.get("/by-code/{code}", response_model=LicensePoolRead, summary="Get a License Pool by business code")
async def get_by_code(
    code: str, service: LicensePoolService = Depends(get_service), _: Principal = Depends(require_read)
) -> LicensePoolRead:
    return await service.get_by_code(code)


@router.head("/by-code/{code}", summary="Check whether a License Pool code exists")
async def exists(
    code: str, service: LicensePoolService = Depends(get_service), _: Principal = Depends(require_read)
) -> Response:
    found = await service.exists(code)
    return Response(status_code=status.HTTP_200_OK if found else status.HTTP_404_NOT_FOUND)


@router.get("/{id}", response_model=LicensePoolRead, summary="Get a License Pool by id")
async def get(
    id: UUID, service: LicensePoolService = Depends(get_service), _: Principal = Depends(require_read)
) -> LicensePoolRead:
    return await service.get(id)


@router.post("", response_model=LicensePoolRead, status_code=status.HTTP_201_CREATED, summary="Create a License Pool")
async def create(
    payload: LicensePoolCreate,
    request: Request,
    response: Response,
    service: LicensePoolService = Depends(get_service),
    _: Principal = Depends(require_write),
) -> LicensePoolRead:
    created = await service.create(payload)
    response.headers["Location"] = f"{request.url.path}/{created.id}"
    return created


@router.put("/{id}", response_model=LicensePoolRead, summary="Update a License Pool")
async def update(
    id: UUID,
    payload: LicensePoolUpdate,
    service: LicensePoolService = Depends(get_service),
    _: Principal = Depends(require_write),
) -> LicensePoolRead:
    return await service.update(id, payload)


@router.patch("/{id}/status", response_model=LicensePoolRead, summary="Change lifecycle status")
async def change_status(
    id: UUID,
    new_status: LifecycleStatus = Query(..., alias="status"),
    service: LicensePoolService = Depends(get_service),
    _: Principal = Depends(require_write),
) -> LicensePoolRead:
    return await service.change_status(id, new_status)


@router.delete("/{id}", status_code=status.HTTP_204_NO_CONTENT, summary="Delete a License Pool")
async def delete(
    id: UUID, service: LicensePoolService = Depends(get_service), _: Principal = Depends(require_write)
) -> Response:
    await service.delete(id)
    return Response(status_code=status.HTTP_204_NO_CONTENT)


@router.post("/bulk", response_model=LicensePoolBulkResult, summary="Create many License Pool records")
async def bulk_create(
    payloads: list[LicensePoolCreate],
    service: LicensePoolService = Depends(get_service),
    _: Principal = Depends(require_write),
) -> LicensePoolBulkResult:
    return await service.bulk_create(payloads)


@router.post("/reconcile", response_model=list[ReconciliationItem], summary="Reconcile external License Pool records")
async def reconcile(
    records: list[dict[str, object]],
    apply: bool = Query(False, description="Apply the plan instead of a dry run"),
    service: LicensePoolService = Depends(get_service),
    _: Principal = Depends(require_write),
) -> list[ReconciliationItem]:
    return await service.reconcile(records, apply)


@router.get("/by-vendor-id/{vendor_id}", response_model=Page[LicensePoolRead], summary="List License Pool records by Vendor Id")
async def list_by_vendor_id(
    vendor_id: UUID,
    page: int = Query(0, ge=0),
    size: int = Query(20, ge=1, le=200),
    service: LicensePoolService = Depends(get_service),
) -> Page[LicensePoolRead]:
    return await service.list_by_vendor_id(vendor_id, page, size)
