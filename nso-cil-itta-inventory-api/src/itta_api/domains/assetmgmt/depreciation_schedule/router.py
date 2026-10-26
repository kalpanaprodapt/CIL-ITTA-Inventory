"""HTTP routes for Depreciation Schedule."""
from __future__ import annotations

from uuid import UUID

from fastapi import APIRouter, Depends, Query, Request, Response, status
from fastapi.responses import PlainTextResponse

from itta_core.auth import Principal, require_read, require_write
from itta_core.dependencies import service_provider
from itta_core.diffing import ReconciliationItem
from itta_core.models import EntityMeta, LifecycleStatus
from itta_core.pagination import Page
from itta_api.domains.assetmgmt.depreciation_schedule.filters import DepreciationScheduleFilter
from itta_api.domains.assetmgmt.depreciation_schedule.meta import META
from itta_api.domains.assetmgmt.depreciation_schedule.schemas import (
    DepreciationScheduleBulkResult,
    DepreciationScheduleCreate,
    DepreciationScheduleRead,
    DepreciationScheduleSummary,
    DepreciationScheduleUpdate,
)
from itta_api.domains.assetmgmt.depreciation_schedule.service import DepreciationScheduleService

router = APIRouter(prefix="/api/v1/depreciation-schedules", tags=["Depreciation Schedule"])
get_service = service_provider("depreciation_schedule")


@router.get("/meta", response_model=EntityMeta, summary="Field catalogue for Depreciation Schedule")
async def meta() -> EntityMeta:
    return META


@router.get("", response_model=Page[DepreciationScheduleRead], summary="Search Depreciation Schedule records")
async def search(
    flt: DepreciationScheduleFilter = Depends(),
    page: int = Query(0, ge=0),
    size: int = Query(20, ge=1, le=200),
    sort: str | None = Query(None, description="field,asc|desc"),
    service: DepreciationScheduleService = Depends(get_service),
    _: Principal = Depends(require_read),
) -> Page[DepreciationScheduleRead]:
    return await service.search(flt, page, size, sort)


@router.get("/summaries", response_model=list[DepreciationScheduleSummary], summary="Pick-list of Depreciation Schedule records")
async def summaries(
    status_filter: LifecycleStatus | None = Query(None, alias="status"),
    service: DepreciationScheduleService = Depends(get_service),
    _: Principal = Depends(require_read),
) -> list[DepreciationScheduleSummary]:
    return await service.summaries(status_filter)


@router.get("/stats/status", response_model=dict[str, int], summary="Counts per lifecycle status")
async def status_stats(
    service: DepreciationScheduleService = Depends(get_service), _: Principal = Depends(require_read)
) -> dict[str, int]:
    return await service.stats()


@router.get("/export", response_class=PlainTextResponse, summary="Export Depreciation Schedule records as CSV")
async def export(
    flt: DepreciationScheduleFilter = Depends(),
    service: DepreciationScheduleService = Depends(get_service),
    _: Principal = Depends(require_read),
) -> PlainTextResponse:
    body = await service.export_csv(flt)
    headers = {"Content-Disposition": 'attachment; filename="depreciation-schedules.csv"'}
    return PlainTextResponse(body, media_type="text/csv", headers=headers)


@router.get("/by-code/{code}", response_model=DepreciationScheduleRead, summary="Get a Depreciation Schedule by business code")
async def get_by_code(
    code: str, service: DepreciationScheduleService = Depends(get_service), _: Principal = Depends(require_read)
) -> DepreciationScheduleRead:
    return await service.get_by_code(code)


@router.head("/by-code/{code}", summary="Check whether a Depreciation Schedule code exists")
async def exists(
    code: str, service: DepreciationScheduleService = Depends(get_service), _: Principal = Depends(require_read)
) -> Response:
    found = await service.exists(code)
    return Response(status_code=status.HTTP_200_OK if found else status.HTTP_404_NOT_FOUND)


@router.get("/{id}", response_model=DepreciationScheduleRead, summary="Get a Depreciation Schedule by id")
async def get(
    id: UUID, service: DepreciationScheduleService = Depends(get_service), _: Principal = Depends(require_read)
) -> DepreciationScheduleRead:
    return await service.get(id)


@router.post("", response_model=DepreciationScheduleRead, status_code=status.HTTP_201_CREATED, summary="Create a Depreciation Schedule")
async def create(
    payload: DepreciationScheduleCreate,
    request: Request,
    response: Response,
    service: DepreciationScheduleService = Depends(get_service),
    _: Principal = Depends(require_write),
) -> DepreciationScheduleRead:
    created = await service.create(payload)
    response.headers["Location"] = f"{request.url.path}/{created.id}"
    return created


@router.put("/{id}", response_model=DepreciationScheduleRead, summary="Update a Depreciation Schedule")
async def update(
    id: UUID,
    payload: DepreciationScheduleUpdate,
    service: DepreciationScheduleService = Depends(get_service),
    _: Principal = Depends(require_write),
) -> DepreciationScheduleRead:
    return await service.update(id, payload)


@router.patch("/{id}/status", response_model=DepreciationScheduleRead, summary="Change lifecycle status")
async def change_status(
    id: UUID,
    new_status: LifecycleStatus = Query(..., alias="status"),
    service: DepreciationScheduleService = Depends(get_service),
    _: Principal = Depends(require_write),
) -> DepreciationScheduleRead:
    return await service.change_status(id, new_status)


@router.delete("/{id}", status_code=status.HTTP_204_NO_CONTENT, summary="Delete a Depreciation Schedule")
async def delete(
    id: UUID, service: DepreciationScheduleService = Depends(get_service), _: Principal = Depends(require_write)
) -> Response:
    await service.delete(id)
    return Response(status_code=status.HTTP_204_NO_CONTENT)


@router.post("/bulk", response_model=DepreciationScheduleBulkResult, summary="Create many Depreciation Schedule records")
async def bulk_create(
    payloads: list[DepreciationScheduleCreate],
    service: DepreciationScheduleService = Depends(get_service),
    _: Principal = Depends(require_write),
) -> DepreciationScheduleBulkResult:
    return await service.bulk_create(payloads)


@router.post("/reconcile", response_model=list[ReconciliationItem], summary="Reconcile external Depreciation Schedule records")
async def reconcile(
    records: list[dict[str, object]],
    apply: bool = Query(False, description="Apply the plan instead of a dry run"),
    service: DepreciationScheduleService = Depends(get_service),
    _: Principal = Depends(require_write),
) -> list[ReconciliationItem]:
    return await service.reconcile(records, apply)


@router.get("/by-asset-id/{asset_id}", response_model=Page[DepreciationScheduleRead], summary="List Depreciation Schedule records by Asset Id")
async def list_by_asset_id(
    asset_id: UUID,
    page: int = Query(0, ge=0),
    size: int = Query(20, ge=1, le=200),
    service: DepreciationScheduleService = Depends(get_service),
) -> Page[DepreciationScheduleRead]:
    return await service.list_by_asset_id(asset_id, page, size)
