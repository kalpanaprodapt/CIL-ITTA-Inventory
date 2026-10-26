"""HTTP routes for Vendor."""
from __future__ import annotations

from uuid import UUID

from fastapi import APIRouter, Depends, Query, Request, Response, status
from fastapi.responses import PlainTextResponse

from itta_core.auth import Principal, require_read, require_write
from itta_core.dependencies import service_provider
from itta_core.diffing import ReconciliationItem
from itta_core.models import EntityMeta, LifecycleStatus
from itta_core.pagination import Page
from itta_api.domains.vendor.vendor.filters import VendorFilter
from itta_api.domains.vendor.vendor.meta import META
from itta_api.domains.vendor.vendor.schemas import (
    VendorBulkResult,
    VendorCreate,
    VendorRead,
    VendorSummary,
    VendorUpdate,
)
from itta_api.domains.vendor.vendor.service import VendorService

router = APIRouter(prefix="/api/v1/vendors", tags=["Vendor"])
get_service = service_provider("vendor")


@router.get("/meta", response_model=EntityMeta, summary="Field catalogue for Vendor")
async def meta() -> EntityMeta:
    return META


@router.get("", response_model=Page[VendorRead], summary="Search Vendor records")
async def search(
    flt: VendorFilter = Depends(),
    page: int = Query(0, ge=0),
    size: int = Query(20, ge=1, le=200),
    sort: str | None = Query(None, description="field,asc|desc"),
    service: VendorService = Depends(get_service),
    _: Principal = Depends(require_read),
) -> Page[VendorRead]:
    return await service.search(flt, page, size, sort)


@router.get("/summaries", response_model=list[VendorSummary], summary="Pick-list of Vendor records")
async def summaries(
    status_filter: LifecycleStatus | None = Query(None, alias="status"),
    service: VendorService = Depends(get_service),
    _: Principal = Depends(require_read),
) -> list[VendorSummary]:
    return await service.summaries(status_filter)


@router.get("/stats/status", response_model=dict[str, int], summary="Counts per lifecycle status")
async def status_stats(
    service: VendorService = Depends(get_service), _: Principal = Depends(require_read)
) -> dict[str, int]:
    return await service.stats()


@router.get("/export", response_class=PlainTextResponse, summary="Export Vendor records as CSV")
async def export(
    flt: VendorFilter = Depends(),
    service: VendorService = Depends(get_service),
    _: Principal = Depends(require_read),
) -> PlainTextResponse:
    body = await service.export_csv(flt)
    headers = {"Content-Disposition": 'attachment; filename="vendors.csv"'}
    return PlainTextResponse(body, media_type="text/csv", headers=headers)


@router.get("/by-code/{code}", response_model=VendorRead, summary="Get a Vendor by business code")
async def get_by_code(
    code: str, service: VendorService = Depends(get_service), _: Principal = Depends(require_read)
) -> VendorRead:
    return await service.get_by_code(code)


@router.head("/by-code/{code}", summary="Check whether a Vendor code exists")
async def exists(
    code: str, service: VendorService = Depends(get_service), _: Principal = Depends(require_read)
) -> Response:
    found = await service.exists(code)
    return Response(status_code=status.HTTP_200_OK if found else status.HTTP_404_NOT_FOUND)


@router.get("/{id}", response_model=VendorRead, summary="Get a Vendor by id")
async def get(
    id: UUID, service: VendorService = Depends(get_service), _: Principal = Depends(require_read)
) -> VendorRead:
    return await service.get(id)


@router.post("", response_model=VendorRead, status_code=status.HTTP_201_CREATED, summary="Create a Vendor")
async def create(
    payload: VendorCreate,
    request: Request,
    response: Response,
    service: VendorService = Depends(get_service),
    _: Principal = Depends(require_write),
) -> VendorRead:
    created = await service.create(payload)
    response.headers["Location"] = f"{request.url.path}/{created.id}"
    return created


@router.put("/{id}", response_model=VendorRead, summary="Update a Vendor")
async def update(
    id: UUID,
    payload: VendorUpdate,
    service: VendorService = Depends(get_service),
    _: Principal = Depends(require_write),
) -> VendorRead:
    return await service.update(id, payload)


@router.patch("/{id}/status", response_model=VendorRead, summary="Change lifecycle status")
async def change_status(
    id: UUID,
    new_status: LifecycleStatus = Query(..., alias="status"),
    service: VendorService = Depends(get_service),
    _: Principal = Depends(require_write),
) -> VendorRead:
    return await service.change_status(id, new_status)


@router.delete("/{id}", status_code=status.HTTP_204_NO_CONTENT, summary="Delete a Vendor")
async def delete(
    id: UUID, service: VendorService = Depends(get_service), _: Principal = Depends(require_write)
) -> Response:
    await service.delete(id)
    return Response(status_code=status.HTTP_204_NO_CONTENT)


@router.post("/bulk", response_model=VendorBulkResult, summary="Create many Vendor records")
async def bulk_create(
    payloads: list[VendorCreate],
    service: VendorService = Depends(get_service),
    _: Principal = Depends(require_write),
) -> VendorBulkResult:
    return await service.bulk_create(payloads)


@router.post("/reconcile", response_model=list[ReconciliationItem], summary="Reconcile external Vendor records")
async def reconcile(
    records: list[dict[str, object]],
    apply: bool = Query(False, description="Apply the plan instead of a dry run"),
    service: VendorService = Depends(get_service),
    _: Principal = Depends(require_write),
) -> list[ReconciliationItem]:
    return await service.reconcile(records, apply)

