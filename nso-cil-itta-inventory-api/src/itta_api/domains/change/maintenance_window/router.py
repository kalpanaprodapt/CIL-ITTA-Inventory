"""HTTP routes for Maintenance Window."""
from __future__ import annotations

from uuid import UUID

from fastapi import APIRouter, Depends, Query, Request, Response, status
from fastapi.responses import PlainTextResponse

from itta_core.auth import Principal, require_read, require_write
from itta_core.dependencies import service_provider
from itta_core.diffing import ReconciliationItem
from itta_core.models import EntityMeta, LifecycleStatus
from itta_core.pagination import Page
from itta_api.domains.change.maintenance_window.filters import MaintenanceWindowFilter
from itta_api.domains.change.maintenance_window.meta import META
from itta_api.domains.change.maintenance_window.schemas import (
    MaintenanceWindowBulkResult,
    MaintenanceWindowCreate,
    MaintenanceWindowRead,
    MaintenanceWindowSummary,
    MaintenanceWindowUpdate,
)
from itta_api.domains.change.maintenance_window.service import MaintenanceWindowService

router = APIRouter(prefix="/api/v1/maintenance-windows", tags=["Maintenance Window"])
get_service = service_provider("maintenance_window")


@router.get("/meta", response_model=EntityMeta, summary="Field catalogue for Maintenance Window")
async def meta() -> EntityMeta:
    return META


@router.get("", response_model=Page[MaintenanceWindowRead], summary="Search Maintenance Window records")
async def search(
    flt: MaintenanceWindowFilter = Depends(),
    page: int = Query(0, ge=0),
    size: int = Query(20, ge=1, le=200),
    sort: str | None = Query(None, description="field,asc|desc"),
    service: MaintenanceWindowService = Depends(get_service),
    _: Principal = Depends(require_read),
) -> Page[MaintenanceWindowRead]:
    return await service.search(flt, page, size, sort)


@router.get("/summaries", response_model=list[MaintenanceWindowSummary], summary="Pick-list of Maintenance Window records")
async def summaries(
    status_filter: LifecycleStatus | None = Query(None, alias="status"),
    service: MaintenanceWindowService = Depends(get_service),
    _: Principal = Depends(require_read),
) -> list[MaintenanceWindowSummary]:
    return await service.summaries(status_filter)


@router.get("/stats/status", response_model=dict[str, int], summary="Counts per lifecycle status")
async def status_stats(
    service: MaintenanceWindowService = Depends(get_service), _: Principal = Depends(require_read)
) -> dict[str, int]:
    return await service.stats()


@router.get("/export", response_class=PlainTextResponse, summary="Export Maintenance Window records as CSV")
async def export(
    flt: MaintenanceWindowFilter = Depends(),
    service: MaintenanceWindowService = Depends(get_service),
    _: Principal = Depends(require_read),
) -> PlainTextResponse:
    body = await service.export_csv(flt)
    headers = {"Content-Disposition": 'attachment; filename="maintenance-windows.csv"'}
    return PlainTextResponse(body, media_type="text/csv", headers=headers)


@router.get("/by-code/{code}", response_model=MaintenanceWindowRead, summary="Get a Maintenance Window by business code")
async def get_by_code(
    code: str, service: MaintenanceWindowService = Depends(get_service), _: Principal = Depends(require_read)
) -> MaintenanceWindowRead:
    return await service.get_by_code(code)


@router.head("/by-code/{code}", summary="Check whether a Maintenance Window code exists")
async def exists(
    code: str, service: MaintenanceWindowService = Depends(get_service), _: Principal = Depends(require_read)
) -> Response:
    found = await service.exists(code)
    return Response(status_code=status.HTTP_200_OK if found else status.HTTP_404_NOT_FOUND)


@router.get("/{id}", response_model=MaintenanceWindowRead, summary="Get a Maintenance Window by id")
async def get(
    id: UUID, service: MaintenanceWindowService = Depends(get_service), _: Principal = Depends(require_read)
) -> MaintenanceWindowRead:
    return await service.get(id)


@router.post("", response_model=MaintenanceWindowRead, status_code=status.HTTP_201_CREATED, summary="Create a Maintenance Window")
async def create(
    payload: MaintenanceWindowCreate,
    request: Request,
    response: Response,
    service: MaintenanceWindowService = Depends(get_service),
    _: Principal = Depends(require_write),
) -> MaintenanceWindowRead:
    created = await service.create(payload)
    response.headers["Location"] = f"{request.url.path}/{created.id}"
    return created


@router.put("/{id}", response_model=MaintenanceWindowRead, summary="Update a Maintenance Window")
async def update(
    id: UUID,
    payload: MaintenanceWindowUpdate,
    service: MaintenanceWindowService = Depends(get_service),
    _: Principal = Depends(require_write),
) -> MaintenanceWindowRead:
    return await service.update(id, payload)


@router.patch("/{id}/status", response_model=MaintenanceWindowRead, summary="Change lifecycle status")
async def change_status(
    id: UUID,
    new_status: LifecycleStatus = Query(..., alias="status"),
    service: MaintenanceWindowService = Depends(get_service),
    _: Principal = Depends(require_write),
) -> MaintenanceWindowRead:
    return await service.change_status(id, new_status)


@router.delete("/{id}", status_code=status.HTTP_204_NO_CONTENT, summary="Delete a Maintenance Window")
async def delete(
    id: UUID, service: MaintenanceWindowService = Depends(get_service), _: Principal = Depends(require_write)
) -> Response:
    await service.delete(id)
    return Response(status_code=status.HTTP_204_NO_CONTENT)


@router.post("/bulk", response_model=MaintenanceWindowBulkResult, summary="Create many Maintenance Window records")
async def bulk_create(
    payloads: list[MaintenanceWindowCreate],
    service: MaintenanceWindowService = Depends(get_service),
    _: Principal = Depends(require_write),
) -> MaintenanceWindowBulkResult:
    return await service.bulk_create(payloads)


@router.post("/reconcile", response_model=list[ReconciliationItem], summary="Reconcile external Maintenance Window records")
async def reconcile(
    records: list[dict[str, object]],
    apply: bool = Query(False, description="Apply the plan instead of a dry run"),
    service: MaintenanceWindowService = Depends(get_service),
    _: Principal = Depends(require_write),
) -> list[ReconciliationItem]:
    return await service.reconcile(records, apply)

