"""HTTP routes for Lag Group."""
from __future__ import annotations

from uuid import UUID

from fastapi import APIRouter, Depends, Query, Request, Response, status
from fastapi.responses import PlainTextResponse

from itta_core.auth import Principal, require_read, require_write
from itta_core.dependencies import service_provider
from itta_core.diffing import ReconciliationItem
from itta_core.models import EntityMeta, LifecycleStatus
from itta_core.pagination import Page
from itta_api.domains.port.lag_group.filters import LagGroupFilter
from itta_api.domains.port.lag_group.meta import META
from itta_api.domains.port.lag_group.schemas import (
    LagGroupBulkResult,
    LagGroupCreate,
    LagGroupRead,
    LagGroupSummary,
    LagGroupUpdate,
)
from itta_api.domains.port.lag_group.service import LagGroupService

router = APIRouter(prefix="/api/v1/lag-groups", tags=["Lag Group"])
get_service = service_provider("lag_group")


@router.get("/meta", response_model=EntityMeta, summary="Field catalogue for Lag Group")
async def meta() -> EntityMeta:
    return META


@router.get("", response_model=Page[LagGroupRead], summary="Search Lag Group records")
async def search(
    flt: LagGroupFilter = Depends(),
    page: int = Query(0, ge=0),
    size: int = Query(20, ge=1, le=200),
    sort: str | None = Query(None, description="field,asc|desc"),
    service: LagGroupService = Depends(get_service),
    _: Principal = Depends(require_read),
) -> Page[LagGroupRead]:
    return await service.search(flt, page, size, sort)


@router.get("/summaries", response_model=list[LagGroupSummary], summary="Pick-list of Lag Group records")
async def summaries(
    status_filter: LifecycleStatus | None = Query(None, alias="status"),
    service: LagGroupService = Depends(get_service),
    _: Principal = Depends(require_read),
) -> list[LagGroupSummary]:
    return await service.summaries(status_filter)


@router.get("/stats/status", response_model=dict[str, int], summary="Counts per lifecycle status")
async def status_stats(
    service: LagGroupService = Depends(get_service), _: Principal = Depends(require_read)
) -> dict[str, int]:
    return await service.stats()


@router.get("/export", response_class=PlainTextResponse, summary="Export Lag Group records as CSV")
async def export(
    flt: LagGroupFilter = Depends(),
    service: LagGroupService = Depends(get_service),
    _: Principal = Depends(require_read),
) -> PlainTextResponse:
    body = await service.export_csv(flt)
    headers = {"Content-Disposition": 'attachment; filename="lag-groups.csv"'}
    return PlainTextResponse(body, media_type="text/csv", headers=headers)


@router.get("/by-code/{code}", response_model=LagGroupRead, summary="Get a Lag Group by business code")
async def get_by_code(
    code: str, service: LagGroupService = Depends(get_service), _: Principal = Depends(require_read)
) -> LagGroupRead:
    return await service.get_by_code(code)


@router.head("/by-code/{code}", summary="Check whether a Lag Group code exists")
async def exists(
    code: str, service: LagGroupService = Depends(get_service), _: Principal = Depends(require_read)
) -> Response:
    found = await service.exists(code)
    return Response(status_code=status.HTTP_200_OK if found else status.HTTP_404_NOT_FOUND)


@router.get("/{id}", response_model=LagGroupRead, summary="Get a Lag Group by id")
async def get(
    id: UUID, service: LagGroupService = Depends(get_service), _: Principal = Depends(require_read)
) -> LagGroupRead:
    return await service.get(id)


@router.post("", response_model=LagGroupRead, status_code=status.HTTP_201_CREATED, summary="Create a Lag Group")
async def create(
    payload: LagGroupCreate,
    request: Request,
    response: Response,
    service: LagGroupService = Depends(get_service),
    _: Principal = Depends(require_write),
) -> LagGroupRead:
    created = await service.create(payload)
    response.headers["Location"] = f"{request.url.path}/{created.id}"
    return created


@router.put("/{id}", response_model=LagGroupRead, summary="Update a Lag Group")
async def update(
    id: UUID,
    payload: LagGroupUpdate,
    service: LagGroupService = Depends(get_service),
    _: Principal = Depends(require_write),
) -> LagGroupRead:
    return await service.update(id, payload)


@router.patch("/{id}/status", response_model=LagGroupRead, summary="Change lifecycle status")
async def change_status(
    id: UUID,
    new_status: LifecycleStatus = Query(..., alias="status"),
    service: LagGroupService = Depends(get_service),
    _: Principal = Depends(require_write),
) -> LagGroupRead:
    return await service.change_status(id, new_status)


@router.delete("/{id}", status_code=status.HTTP_204_NO_CONTENT, summary="Delete a Lag Group")
async def delete(
    id: UUID, service: LagGroupService = Depends(get_service), _: Principal = Depends(require_write)
) -> Response:
    await service.delete(id)
    return Response(status_code=status.HTTP_204_NO_CONTENT)


@router.post("/bulk", response_model=LagGroupBulkResult, summary="Create many Lag Group records")
async def bulk_create(
    payloads: list[LagGroupCreate],
    service: LagGroupService = Depends(get_service),
    _: Principal = Depends(require_write),
) -> LagGroupBulkResult:
    return await service.bulk_create(payloads)


@router.post("/reconcile", response_model=list[ReconciliationItem], summary="Reconcile external Lag Group records")
async def reconcile(
    records: list[dict[str, object]],
    apply: bool = Query(False, description="Apply the plan instead of a dry run"),
    service: LagGroupService = Depends(get_service),
    _: Principal = Depends(require_write),
) -> list[ReconciliationItem]:
    return await service.reconcile(records, apply)


@router.get("/by-device-id/{device_id}", response_model=Page[LagGroupRead], summary="List Lag Group records by Device Id")
async def list_by_device_id(
    device_id: UUID,
    page: int = Query(0, ge=0),
    size: int = Query(20, ge=1, le=200),
    service: LagGroupService = Depends(get_service),
) -> Page[LagGroupRead]:
    return await service.list_by_device_id(device_id, page, size)
