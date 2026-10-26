"""HTTP routes for Software Image."""
from __future__ import annotations

from uuid import UUID

from fastapi import APIRouter, Depends, Query, Request, Response, status
from fastapi.responses import PlainTextResponse

from itta_core.auth import Principal, require_read, require_write
from itta_core.dependencies import service_provider
from itta_core.diffing import ReconciliationItem
from itta_core.models import EntityMeta, LifecycleStatus
from itta_core.pagination import Page
from itta_api.domains.software.software_image.filters import SoftwareImageFilter
from itta_api.domains.software.software_image.meta import META
from itta_api.domains.software.software_image.schemas import (
    SoftwareImageBulkResult,
    SoftwareImageCreate,
    SoftwareImageRead,
    SoftwareImageSummary,
    SoftwareImageUpdate,
)
from itta_api.domains.software.software_image.service import SoftwareImageService

router = APIRouter(prefix="/api/v1/software-images", tags=["Software Image"])
get_service = service_provider("software_image")


@router.get("/meta", response_model=EntityMeta, summary="Field catalogue for Software Image")
async def meta() -> EntityMeta:
    return META


@router.get("", response_model=Page[SoftwareImageRead], summary="Search Software Image records")
async def search(
    flt: SoftwareImageFilter = Depends(),
    page: int = Query(0, ge=0),
    size: int = Query(20, ge=1, le=200),
    sort: str | None = Query(None, description="field,asc|desc"),
    service: SoftwareImageService = Depends(get_service),
    _: Principal = Depends(require_read),
) -> Page[SoftwareImageRead]:
    return await service.search(flt, page, size, sort)


@router.get("/summaries", response_model=list[SoftwareImageSummary], summary="Pick-list of Software Image records")
async def summaries(
    status_filter: LifecycleStatus | None = Query(None, alias="status"),
    service: SoftwareImageService = Depends(get_service),
    _: Principal = Depends(require_read),
) -> list[SoftwareImageSummary]:
    return await service.summaries(status_filter)


@router.get("/stats/status", response_model=dict[str, int], summary="Counts per lifecycle status")
async def status_stats(
    service: SoftwareImageService = Depends(get_service), _: Principal = Depends(require_read)
) -> dict[str, int]:
    return await service.stats()


@router.get("/export", response_class=PlainTextResponse, summary="Export Software Image records as CSV")
async def export(
    flt: SoftwareImageFilter = Depends(),
    service: SoftwareImageService = Depends(get_service),
    _: Principal = Depends(require_read),
) -> PlainTextResponse:
    body = await service.export_csv(flt)
    headers = {"Content-Disposition": 'attachment; filename="software-images.csv"'}
    return PlainTextResponse(body, media_type="text/csv", headers=headers)


@router.get("/by-code/{code}", response_model=SoftwareImageRead, summary="Get a Software Image by business code")
async def get_by_code(
    code: str, service: SoftwareImageService = Depends(get_service), _: Principal = Depends(require_read)
) -> SoftwareImageRead:
    return await service.get_by_code(code)


@router.head("/by-code/{code}", summary="Check whether a Software Image code exists")
async def exists(
    code: str, service: SoftwareImageService = Depends(get_service), _: Principal = Depends(require_read)
) -> Response:
    found = await service.exists(code)
    return Response(status_code=status.HTTP_200_OK if found else status.HTTP_404_NOT_FOUND)


@router.get("/{id}", response_model=SoftwareImageRead, summary="Get a Software Image by id")
async def get(
    id: UUID, service: SoftwareImageService = Depends(get_service), _: Principal = Depends(require_read)
) -> SoftwareImageRead:
    return await service.get(id)


@router.post("", response_model=SoftwareImageRead, status_code=status.HTTP_201_CREATED, summary="Create a Software Image")
async def create(
    payload: SoftwareImageCreate,
    request: Request,
    response: Response,
    service: SoftwareImageService = Depends(get_service),
    _: Principal = Depends(require_write),
) -> SoftwareImageRead:
    created = await service.create(payload)
    response.headers["Location"] = f"{request.url.path}/{created.id}"
    return created


@router.put("/{id}", response_model=SoftwareImageRead, summary="Update a Software Image")
async def update(
    id: UUID,
    payload: SoftwareImageUpdate,
    service: SoftwareImageService = Depends(get_service),
    _: Principal = Depends(require_write),
) -> SoftwareImageRead:
    return await service.update(id, payload)


@router.patch("/{id}/status", response_model=SoftwareImageRead, summary="Change lifecycle status")
async def change_status(
    id: UUID,
    new_status: LifecycleStatus = Query(..., alias="status"),
    service: SoftwareImageService = Depends(get_service),
    _: Principal = Depends(require_write),
) -> SoftwareImageRead:
    return await service.change_status(id, new_status)


@router.delete("/{id}", status_code=status.HTTP_204_NO_CONTENT, summary="Delete a Software Image")
async def delete(
    id: UUID, service: SoftwareImageService = Depends(get_service), _: Principal = Depends(require_write)
) -> Response:
    await service.delete(id)
    return Response(status_code=status.HTTP_204_NO_CONTENT)


@router.post("/bulk", response_model=SoftwareImageBulkResult, summary="Create many Software Image records")
async def bulk_create(
    payloads: list[SoftwareImageCreate],
    service: SoftwareImageService = Depends(get_service),
    _: Principal = Depends(require_write),
) -> SoftwareImageBulkResult:
    return await service.bulk_create(payloads)


@router.post("/reconcile", response_model=list[ReconciliationItem], summary="Reconcile external Software Image records")
async def reconcile(
    records: list[dict[str, object]],
    apply: bool = Query(False, description="Apply the plan instead of a dry run"),
    service: SoftwareImageService = Depends(get_service),
    _: Principal = Depends(require_write),
) -> list[ReconciliationItem]:
    return await service.reconcile(records, apply)


@router.get("/by-vendor-id/{vendor_id}", response_model=Page[SoftwareImageRead], summary="List Software Image records by Vendor Id")
async def list_by_vendor_id(
    vendor_id: UUID,
    page: int = Query(0, ge=0),
    size: int = Query(20, ge=1, le=200),
    service: SoftwareImageService = Depends(get_service),
) -> Page[SoftwareImageRead]:
    return await service.list_by_vendor_id(vendor_id, page, size)
