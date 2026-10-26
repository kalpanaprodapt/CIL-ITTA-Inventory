"""HTTP routes for Hardware Model."""
from __future__ import annotations

from uuid import UUID

from fastapi import APIRouter, Depends, Query, Request, Response, status
from fastapi.responses import PlainTextResponse

from itta_core.auth import Principal, require_read, require_write
from itta_core.dependencies import service_provider
from itta_core.diffing import ReconciliationItem
from itta_core.models import EntityMeta, LifecycleStatus
from itta_core.pagination import Page
from itta_api.domains.vendor.hardware_model.filters import HardwareModelFilter
from itta_api.domains.vendor.hardware_model.meta import META
from itta_api.domains.vendor.hardware_model.schemas import (
    HardwareModelBulkResult,
    HardwareModelCreate,
    HardwareModelRead,
    HardwareModelSummary,
    HardwareModelUpdate,
)
from itta_api.domains.vendor.hardware_model.service import HardwareModelService

router = APIRouter(prefix="/api/v1/hardware-models", tags=["Hardware Model"])
get_service = service_provider("hardware_model")


@router.get("/meta", response_model=EntityMeta, summary="Field catalogue for Hardware Model")
async def meta() -> EntityMeta:
    return META


@router.get("", response_model=Page[HardwareModelRead], summary="Search Hardware Model records")
async def search(
    flt: HardwareModelFilter = Depends(),
    page: int = Query(0, ge=0),
    size: int = Query(20, ge=1, le=200),
    sort: str | None = Query(None, description="field,asc|desc"),
    service: HardwareModelService = Depends(get_service),
    _: Principal = Depends(require_read),
) -> Page[HardwareModelRead]:
    return await service.search(flt, page, size, sort)


@router.get("/summaries", response_model=list[HardwareModelSummary], summary="Pick-list of Hardware Model records")
async def summaries(
    status_filter: LifecycleStatus | None = Query(None, alias="status"),
    service: HardwareModelService = Depends(get_service),
    _: Principal = Depends(require_read),
) -> list[HardwareModelSummary]:
    return await service.summaries(status_filter)


@router.get("/stats/status", response_model=dict[str, int], summary="Counts per lifecycle status")
async def status_stats(
    service: HardwareModelService = Depends(get_service), _: Principal = Depends(require_read)
) -> dict[str, int]:
    return await service.stats()


@router.get("/export", response_class=PlainTextResponse, summary="Export Hardware Model records as CSV")
async def export(
    flt: HardwareModelFilter = Depends(),
    service: HardwareModelService = Depends(get_service),
    _: Principal = Depends(require_read),
) -> PlainTextResponse:
    body = await service.export_csv(flt)
    headers = {"Content-Disposition": 'attachment; filename="hardware-models.csv"'}
    return PlainTextResponse(body, media_type="text/csv", headers=headers)


@router.get("/by-code/{code}", response_model=HardwareModelRead, summary="Get a Hardware Model by business code")
async def get_by_code(
    code: str, service: HardwareModelService = Depends(get_service), _: Principal = Depends(require_read)
) -> HardwareModelRead:
    return await service.get_by_code(code)


@router.head("/by-code/{code}", summary="Check whether a Hardware Model code exists")
async def exists(
    code: str, service: HardwareModelService = Depends(get_service), _: Principal = Depends(require_read)
) -> Response:
    found = await service.exists(code)
    return Response(status_code=status.HTTP_200_OK if found else status.HTTP_404_NOT_FOUND)


@router.get("/{id}", response_model=HardwareModelRead, summary="Get a Hardware Model by id")
async def get(
    id: UUID, service: HardwareModelService = Depends(get_service), _: Principal = Depends(require_read)
) -> HardwareModelRead:
    return await service.get(id)


@router.post("", response_model=HardwareModelRead, status_code=status.HTTP_201_CREATED, summary="Create a Hardware Model")
async def create(
    payload: HardwareModelCreate,
    request: Request,
    response: Response,
    service: HardwareModelService = Depends(get_service),
    _: Principal = Depends(require_write),
) -> HardwareModelRead:
    created = await service.create(payload)
    response.headers["Location"] = f"{request.url.path}/{created.id}"
    return created


@router.put("/{id}", response_model=HardwareModelRead, summary="Update a Hardware Model")
async def update(
    id: UUID,
    payload: HardwareModelUpdate,
    service: HardwareModelService = Depends(get_service),
    _: Principal = Depends(require_write),
) -> HardwareModelRead:
    return await service.update(id, payload)


@router.patch("/{id}/status", response_model=HardwareModelRead, summary="Change lifecycle status")
async def change_status(
    id: UUID,
    new_status: LifecycleStatus = Query(..., alias="status"),
    service: HardwareModelService = Depends(get_service),
    _: Principal = Depends(require_write),
) -> HardwareModelRead:
    return await service.change_status(id, new_status)


@router.delete("/{id}", status_code=status.HTTP_204_NO_CONTENT, summary="Delete a Hardware Model")
async def delete(
    id: UUID, service: HardwareModelService = Depends(get_service), _: Principal = Depends(require_write)
) -> Response:
    await service.delete(id)
    return Response(status_code=status.HTTP_204_NO_CONTENT)


@router.post("/bulk", response_model=HardwareModelBulkResult, summary="Create many Hardware Model records")
async def bulk_create(
    payloads: list[HardwareModelCreate],
    service: HardwareModelService = Depends(get_service),
    _: Principal = Depends(require_write),
) -> HardwareModelBulkResult:
    return await service.bulk_create(payloads)


@router.post("/reconcile", response_model=list[ReconciliationItem], summary="Reconcile external Hardware Model records")
async def reconcile(
    records: list[dict[str, object]],
    apply: bool = Query(False, description="Apply the plan instead of a dry run"),
    service: HardwareModelService = Depends(get_service),
    _: Principal = Depends(require_write),
) -> list[ReconciliationItem]:
    return await service.reconcile(records, apply)


@router.get("/by-vendor-id/{vendor_id}", response_model=Page[HardwareModelRead], summary="List Hardware Model records by Vendor Id")
async def list_by_vendor_id(
    vendor_id: UUID,
    page: int = Query(0, ge=0),
    size: int = Query(20, ge=1, le=200),
    service: HardwareModelService = Depends(get_service),
) -> Page[HardwareModelRead]:
    return await service.list_by_vendor_id(vendor_id, page, size)
