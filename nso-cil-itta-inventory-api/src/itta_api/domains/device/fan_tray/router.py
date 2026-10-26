"""HTTP routes for Fan Tray."""
from __future__ import annotations

from uuid import UUID

from fastapi import APIRouter, Depends, Query, Request, Response, status
from fastapi.responses import PlainTextResponse

from itta_core.auth import Principal, require_read, require_write
from itta_core.dependencies import service_provider
from itta_core.diffing import ReconciliationItem
from itta_core.models import EntityMeta, LifecycleStatus
from itta_core.pagination import Page
from itta_api.domains.device.fan_tray.filters import FanTrayFilter
from itta_api.domains.device.fan_tray.meta import META
from itta_api.domains.device.fan_tray.schemas import (
    FanTrayBulkResult,
    FanTrayCreate,
    FanTrayRead,
    FanTraySummary,
    FanTrayUpdate,
)
from itta_api.domains.device.fan_tray.service import FanTrayService

router = APIRouter(prefix="/api/v1/fan-traies", tags=["Fan Tray"])
get_service = service_provider("fan_tray")


@router.get("/meta", response_model=EntityMeta, summary="Field catalogue for Fan Tray")
async def meta() -> EntityMeta:
    return META


@router.get("", response_model=Page[FanTrayRead], summary="Search Fan Tray records")
async def search(
    flt: FanTrayFilter = Depends(),
    page: int = Query(0, ge=0),
    size: int = Query(20, ge=1, le=200),
    sort: str | None = Query(None, description="field,asc|desc"),
    service: FanTrayService = Depends(get_service),
    _: Principal = Depends(require_read),
) -> Page[FanTrayRead]:
    return await service.search(flt, page, size, sort)


@router.get("/summaries", response_model=list[FanTraySummary], summary="Pick-list of Fan Tray records")
async def summaries(
    status_filter: LifecycleStatus | None = Query(None, alias="status"),
    service: FanTrayService = Depends(get_service),
    _: Principal = Depends(require_read),
) -> list[FanTraySummary]:
    return await service.summaries(status_filter)


@router.get("/stats/status", response_model=dict[str, int], summary="Counts per lifecycle status")
async def status_stats(
    service: FanTrayService = Depends(get_service), _: Principal = Depends(require_read)
) -> dict[str, int]:
    return await service.stats()


@router.get("/export", response_class=PlainTextResponse, summary="Export Fan Tray records as CSV")
async def export(
    flt: FanTrayFilter = Depends(),
    service: FanTrayService = Depends(get_service),
    _: Principal = Depends(require_read),
) -> PlainTextResponse:
    body = await service.export_csv(flt)
    headers = {"Content-Disposition": 'attachment; filename="fan-traies.csv"'}
    return PlainTextResponse(body, media_type="text/csv", headers=headers)


@router.get("/by-code/{code}", response_model=FanTrayRead, summary="Get a Fan Tray by business code")
async def get_by_code(
    code: str, service: FanTrayService = Depends(get_service), _: Principal = Depends(require_read)
) -> FanTrayRead:
    return await service.get_by_code(code)


@router.head("/by-code/{code}", summary="Check whether a Fan Tray code exists")
async def exists(
    code: str, service: FanTrayService = Depends(get_service), _: Principal = Depends(require_read)
) -> Response:
    found = await service.exists(code)
    return Response(status_code=status.HTTP_200_OK if found else status.HTTP_404_NOT_FOUND)


@router.get("/{id}", response_model=FanTrayRead, summary="Get a Fan Tray by id")
async def get(
    id: UUID, service: FanTrayService = Depends(get_service), _: Principal = Depends(require_read)
) -> FanTrayRead:
    return await service.get(id)


@router.post("", response_model=FanTrayRead, status_code=status.HTTP_201_CREATED, summary="Create a Fan Tray")
async def create(
    payload: FanTrayCreate,
    request: Request,
    response: Response,
    service: FanTrayService = Depends(get_service),
    _: Principal = Depends(require_write),
) -> FanTrayRead:
    created = await service.create(payload)
    response.headers["Location"] = f"{request.url.path}/{created.id}"
    return created


@router.put("/{id}", response_model=FanTrayRead, summary="Update a Fan Tray")
async def update(
    id: UUID,
    payload: FanTrayUpdate,
    service: FanTrayService = Depends(get_service),
    _: Principal = Depends(require_write),
) -> FanTrayRead:
    return await service.update(id, payload)


@router.patch("/{id}/status", response_model=FanTrayRead, summary="Change lifecycle status")
async def change_status(
    id: UUID,
    new_status: LifecycleStatus = Query(..., alias="status"),
    service: FanTrayService = Depends(get_service),
    _: Principal = Depends(require_write),
) -> FanTrayRead:
    return await service.change_status(id, new_status)


@router.delete("/{id}", status_code=status.HTTP_204_NO_CONTENT, summary="Delete a Fan Tray")
async def delete(
    id: UUID, service: FanTrayService = Depends(get_service), _: Principal = Depends(require_write)
) -> Response:
    await service.delete(id)
    return Response(status_code=status.HTTP_204_NO_CONTENT)


@router.post("/bulk", response_model=FanTrayBulkResult, summary="Create many Fan Tray records")
async def bulk_create(
    payloads: list[FanTrayCreate],
    service: FanTrayService = Depends(get_service),
    _: Principal = Depends(require_write),
) -> FanTrayBulkResult:
    return await service.bulk_create(payloads)


@router.post("/reconcile", response_model=list[ReconciliationItem], summary="Reconcile external Fan Tray records")
async def reconcile(
    records: list[dict[str, object]],
    apply: bool = Query(False, description="Apply the plan instead of a dry run"),
    service: FanTrayService = Depends(get_service),
    _: Principal = Depends(require_write),
) -> list[ReconciliationItem]:
    return await service.reconcile(records, apply)


@router.get("/by-chassis-id/{chassis_id}", response_model=Page[FanTrayRead], summary="List Fan Tray records by Chassis Id")
async def list_by_chassis_id(
    chassis_id: UUID,
    page: int = Query(0, ge=0),
    size: int = Query(20, ge=1, le=200),
    service: FanTrayService = Depends(get_service),
) -> Page[FanTrayRead]:
    return await service.list_by_chassis_id(chassis_id, page, size)
