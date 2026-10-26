"""HTTP routes for Chassis."""
from __future__ import annotations

from uuid import UUID

from fastapi import APIRouter, Depends, Query, Request, Response, status
from fastapi.responses import PlainTextResponse

from itta_core.auth import Principal, require_read, require_write
from itta_core.dependencies import service_provider
from itta_core.diffing import ReconciliationItem
from itta_core.models import EntityMeta, LifecycleStatus
from itta_core.pagination import Page
from itta_api.domains.device.chassis.filters import ChassisFilter
from itta_api.domains.device.chassis.meta import META
from itta_api.domains.device.chassis.schemas import (
    ChassisBulkResult,
    ChassisCreate,
    ChassisRead,
    ChassisSummary,
    ChassisUpdate,
)
from itta_api.domains.device.chassis.service import ChassisService

router = APIRouter(prefix="/api/v1/chassises", tags=["Chassis"])
get_service = service_provider("chassis")


@router.get("/meta", response_model=EntityMeta, summary="Field catalogue for Chassis")
async def meta() -> EntityMeta:
    return META


@router.get("", response_model=Page[ChassisRead], summary="Search Chassis records")
async def search(
    flt: ChassisFilter = Depends(),
    page: int = Query(0, ge=0),
    size: int = Query(20, ge=1, le=200),
    sort: str | None = Query(None, description="field,asc|desc"),
    service: ChassisService = Depends(get_service),
    _: Principal = Depends(require_read),
) -> Page[ChassisRead]:
    return await service.search(flt, page, size, sort)


@router.get("/summaries", response_model=list[ChassisSummary], summary="Pick-list of Chassis records")
async def summaries(
    status_filter: LifecycleStatus | None = Query(None, alias="status"),
    service: ChassisService = Depends(get_service),
    _: Principal = Depends(require_read),
) -> list[ChassisSummary]:
    return await service.summaries(status_filter)


@router.get("/stats/status", response_model=dict[str, int], summary="Counts per lifecycle status")
async def status_stats(
    service: ChassisService = Depends(get_service), _: Principal = Depends(require_read)
) -> dict[str, int]:
    return await service.stats()


@router.get("/export", response_class=PlainTextResponse, summary="Export Chassis records as CSV")
async def export(
    flt: ChassisFilter = Depends(),
    service: ChassisService = Depends(get_service),
    _: Principal = Depends(require_read),
) -> PlainTextResponse:
    body = await service.export_csv(flt)
    headers = {"Content-Disposition": 'attachment; filename="chassises.csv"'}
    return PlainTextResponse(body, media_type="text/csv", headers=headers)


@router.get("/by-code/{code}", response_model=ChassisRead, summary="Get a Chassis by business code")
async def get_by_code(
    code: str, service: ChassisService = Depends(get_service), _: Principal = Depends(require_read)
) -> ChassisRead:
    return await service.get_by_code(code)


@router.head("/by-code/{code}", summary="Check whether a Chassis code exists")
async def exists(
    code: str, service: ChassisService = Depends(get_service), _: Principal = Depends(require_read)
) -> Response:
    found = await service.exists(code)
    return Response(status_code=status.HTTP_200_OK if found else status.HTTP_404_NOT_FOUND)


@router.get("/{id}", response_model=ChassisRead, summary="Get a Chassis by id")
async def get(
    id: UUID, service: ChassisService = Depends(get_service), _: Principal = Depends(require_read)
) -> ChassisRead:
    return await service.get(id)


@router.post("", response_model=ChassisRead, status_code=status.HTTP_201_CREATED, summary="Create a Chassis")
async def create(
    payload: ChassisCreate,
    request: Request,
    response: Response,
    service: ChassisService = Depends(get_service),
    _: Principal = Depends(require_write),
) -> ChassisRead:
    created = await service.create(payload)
    response.headers["Location"] = f"{request.url.path}/{created.id}"
    return created


@router.put("/{id}", response_model=ChassisRead, summary="Update a Chassis")
async def update(
    id: UUID,
    payload: ChassisUpdate,
    service: ChassisService = Depends(get_service),
    _: Principal = Depends(require_write),
) -> ChassisRead:
    return await service.update(id, payload)


@router.patch("/{id}/status", response_model=ChassisRead, summary="Change lifecycle status")
async def change_status(
    id: UUID,
    new_status: LifecycleStatus = Query(..., alias="status"),
    service: ChassisService = Depends(get_service),
    _: Principal = Depends(require_write),
) -> ChassisRead:
    return await service.change_status(id, new_status)


@router.delete("/{id}", status_code=status.HTTP_204_NO_CONTENT, summary="Delete a Chassis")
async def delete(
    id: UUID, service: ChassisService = Depends(get_service), _: Principal = Depends(require_write)
) -> Response:
    await service.delete(id)
    return Response(status_code=status.HTTP_204_NO_CONTENT)


@router.post("/bulk", response_model=ChassisBulkResult, summary="Create many Chassis records")
async def bulk_create(
    payloads: list[ChassisCreate],
    service: ChassisService = Depends(get_service),
    _: Principal = Depends(require_write),
) -> ChassisBulkResult:
    return await service.bulk_create(payloads)


@router.post("/reconcile", response_model=list[ReconciliationItem], summary="Reconcile external Chassis records")
async def reconcile(
    records: list[dict[str, object]],
    apply: bool = Query(False, description="Apply the plan instead of a dry run"),
    service: ChassisService = Depends(get_service),
    _: Principal = Depends(require_write),
) -> list[ReconciliationItem]:
    return await service.reconcile(records, apply)


@router.get("/by-device-id/{device_id}", response_model=Page[ChassisRead], summary="List Chassis records by Device Id")
async def list_by_device_id(
    device_id: UUID,
    page: int = Query(0, ge=0),
    size: int = Query(20, ge=1, le=200),
    service: ChassisService = Depends(get_service),
) -> Page[ChassisRead]:
    return await service.list_by_device_id(device_id, page, size)
