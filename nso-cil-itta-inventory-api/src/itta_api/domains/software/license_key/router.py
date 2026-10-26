"""HTTP routes for License Key."""
from __future__ import annotations

from uuid import UUID

from fastapi import APIRouter, Depends, Query, Request, Response, status
from fastapi.responses import PlainTextResponse

from itta_core.auth import Principal, require_read, require_write
from itta_core.dependencies import service_provider
from itta_core.diffing import ReconciliationItem
from itta_core.models import EntityMeta, LifecycleStatus
from itta_core.pagination import Page
from itta_api.domains.software.license_key.filters import LicenseKeyFilter
from itta_api.domains.software.license_key.meta import META
from itta_api.domains.software.license_key.schemas import (
    LicenseKeyBulkResult,
    LicenseKeyCreate,
    LicenseKeyRead,
    LicenseKeySummary,
    LicenseKeyUpdate,
)
from itta_api.domains.software.license_key.service import LicenseKeyService

router = APIRouter(prefix="/api/v1/license-keies", tags=["License Key"])
get_service = service_provider("license_key")


@router.get("/meta", response_model=EntityMeta, summary="Field catalogue for License Key")
async def meta() -> EntityMeta:
    return META


@router.get("", response_model=Page[LicenseKeyRead], summary="Search License Key records")
async def search(
    flt: LicenseKeyFilter = Depends(),
    page: int = Query(0, ge=0),
    size: int = Query(20, ge=1, le=200),
    sort: str | None = Query(None, description="field,asc|desc"),
    service: LicenseKeyService = Depends(get_service),
    _: Principal = Depends(require_read),
) -> Page[LicenseKeyRead]:
    return await service.search(flt, page, size, sort)


@router.get("/summaries", response_model=list[LicenseKeySummary], summary="Pick-list of License Key records")
async def summaries(
    status_filter: LifecycleStatus | None = Query(None, alias="status"),
    service: LicenseKeyService = Depends(get_service),
    _: Principal = Depends(require_read),
) -> list[LicenseKeySummary]:
    return await service.summaries(status_filter)


@router.get("/stats/status", response_model=dict[str, int], summary="Counts per lifecycle status")
async def status_stats(
    service: LicenseKeyService = Depends(get_service), _: Principal = Depends(require_read)
) -> dict[str, int]:
    return await service.stats()


@router.get("/export", response_class=PlainTextResponse, summary="Export License Key records as CSV")
async def export(
    flt: LicenseKeyFilter = Depends(),
    service: LicenseKeyService = Depends(get_service),
    _: Principal = Depends(require_read),
) -> PlainTextResponse:
    body = await service.export_csv(flt)
    headers = {"Content-Disposition": 'attachment; filename="license-keies.csv"'}
    return PlainTextResponse(body, media_type="text/csv", headers=headers)


@router.get("/by-code/{code}", response_model=LicenseKeyRead, summary="Get a License Key by business code")
async def get_by_code(
    code: str, service: LicenseKeyService = Depends(get_service), _: Principal = Depends(require_read)
) -> LicenseKeyRead:
    return await service.get_by_code(code)


@router.head("/by-code/{code}", summary="Check whether a License Key code exists")
async def exists(
    code: str, service: LicenseKeyService = Depends(get_service), _: Principal = Depends(require_read)
) -> Response:
    found = await service.exists(code)
    return Response(status_code=status.HTTP_200_OK if found else status.HTTP_404_NOT_FOUND)


@router.get("/{id}", response_model=LicenseKeyRead, summary="Get a License Key by id")
async def get(
    id: UUID, service: LicenseKeyService = Depends(get_service), _: Principal = Depends(require_read)
) -> LicenseKeyRead:
    return await service.get(id)


@router.post("", response_model=LicenseKeyRead, status_code=status.HTTP_201_CREATED, summary="Create a License Key")
async def create(
    payload: LicenseKeyCreate,
    request: Request,
    response: Response,
    service: LicenseKeyService = Depends(get_service),
    _: Principal = Depends(require_write),
) -> LicenseKeyRead:
    created = await service.create(payload)
    response.headers["Location"] = f"{request.url.path}/{created.id}"
    return created


@router.put("/{id}", response_model=LicenseKeyRead, summary="Update a License Key")
async def update(
    id: UUID,
    payload: LicenseKeyUpdate,
    service: LicenseKeyService = Depends(get_service),
    _: Principal = Depends(require_write),
) -> LicenseKeyRead:
    return await service.update(id, payload)


@router.patch("/{id}/status", response_model=LicenseKeyRead, summary="Change lifecycle status")
async def change_status(
    id: UUID,
    new_status: LifecycleStatus = Query(..., alias="status"),
    service: LicenseKeyService = Depends(get_service),
    _: Principal = Depends(require_write),
) -> LicenseKeyRead:
    return await service.change_status(id, new_status)


@router.delete("/{id}", status_code=status.HTTP_204_NO_CONTENT, summary="Delete a License Key")
async def delete(
    id: UUID, service: LicenseKeyService = Depends(get_service), _: Principal = Depends(require_write)
) -> Response:
    await service.delete(id)
    return Response(status_code=status.HTTP_204_NO_CONTENT)


@router.post("/bulk", response_model=LicenseKeyBulkResult, summary="Create many License Key records")
async def bulk_create(
    payloads: list[LicenseKeyCreate],
    service: LicenseKeyService = Depends(get_service),
    _: Principal = Depends(require_write),
) -> LicenseKeyBulkResult:
    return await service.bulk_create(payloads)


@router.post("/reconcile", response_model=list[ReconciliationItem], summary="Reconcile external License Key records")
async def reconcile(
    records: list[dict[str, object]],
    apply: bool = Query(False, description="Apply the plan instead of a dry run"),
    service: LicenseKeyService = Depends(get_service),
    _: Principal = Depends(require_write),
) -> list[ReconciliationItem]:
    return await service.reconcile(records, apply)


@router.get("/by-pool-id/{pool_id}", response_model=Page[LicenseKeyRead], summary="List License Key records by Pool Id")
async def list_by_pool_id(
    pool_id: UUID,
    page: int = Query(0, ge=0),
    size: int = Query(20, ge=1, le=200),
    service: LicenseKeyService = Depends(get_service),
) -> Page[LicenseKeyRead]:
    return await service.list_by_pool_id(pool_id, page, size)

@router.get("/by-assigned-device-id/{assigned_device_id}", response_model=Page[LicenseKeyRead], summary="List License Key records by Assigned Device Id")
async def list_by_assigned_device_id(
    assigned_device_id: UUID,
    page: int = Query(0, ge=0),
    size: int = Query(20, ge=1, le=200),
    service: LicenseKeyService = Depends(get_service),
) -> Page[LicenseKeyRead]:
    return await service.list_by_assigned_device_id(assigned_device_id, page, size)
