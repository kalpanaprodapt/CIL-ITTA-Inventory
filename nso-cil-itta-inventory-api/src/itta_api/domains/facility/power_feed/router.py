"""HTTP routes for Power Feed."""
from __future__ import annotations

from uuid import UUID

from fastapi import APIRouter, Depends, Query, Request, Response, status
from fastapi.responses import PlainTextResponse

from itta_core.auth import Principal, require_read, require_write
from itta_core.dependencies import service_provider
from itta_core.diffing import ReconciliationItem
from itta_core.models import EntityMeta, LifecycleStatus
from itta_core.pagination import Page
from itta_api.domains.facility.power_feed.filters import PowerFeedFilter
from itta_api.domains.facility.power_feed.meta import META
from itta_api.domains.facility.power_feed.schemas import (
    PowerFeedBulkResult,
    PowerFeedCreate,
    PowerFeedRead,
    PowerFeedSummary,
    PowerFeedUpdate,
)
from itta_api.domains.facility.power_feed.service import PowerFeedService

router = APIRouter(prefix="/api/v1/power-feeds", tags=["Power Feed"])
get_service = service_provider("power_feed")


@router.get("/meta", response_model=EntityMeta, summary="Field catalogue for Power Feed")
async def meta() -> EntityMeta:
    return META


@router.get("", response_model=Page[PowerFeedRead], summary="Search Power Feed records")
async def search(
    flt: PowerFeedFilter = Depends(),
    page: int = Query(0, ge=0),
    size: int = Query(20, ge=1, le=200),
    sort: str | None = Query(None, description="field,asc|desc"),
    service: PowerFeedService = Depends(get_service),
    _: Principal = Depends(require_read),
) -> Page[PowerFeedRead]:
    return await service.search(flt, page, size, sort)


@router.get("/summaries", response_model=list[PowerFeedSummary], summary="Pick-list of Power Feed records")
async def summaries(
    status_filter: LifecycleStatus | None = Query(None, alias="status"),
    service: PowerFeedService = Depends(get_service),
    _: Principal = Depends(require_read),
) -> list[PowerFeedSummary]:
    return await service.summaries(status_filter)


@router.get("/stats/status", response_model=dict[str, int], summary="Counts per lifecycle status")
async def status_stats(
    service: PowerFeedService = Depends(get_service), _: Principal = Depends(require_read)
) -> dict[str, int]:
    return await service.stats()


@router.get("/export", response_class=PlainTextResponse, summary="Export Power Feed records as CSV")
async def export(
    flt: PowerFeedFilter = Depends(),
    service: PowerFeedService = Depends(get_service),
    _: Principal = Depends(require_read),
) -> PlainTextResponse:
    body = await service.export_csv(flt)
    headers = {"Content-Disposition": 'attachment; filename="power-feeds.csv"'}
    return PlainTextResponse(body, media_type="text/csv", headers=headers)


@router.get("/by-code/{code}", response_model=PowerFeedRead, summary="Get a Power Feed by business code")
async def get_by_code(
    code: str, service: PowerFeedService = Depends(get_service), _: Principal = Depends(require_read)
) -> PowerFeedRead:
    return await service.get_by_code(code)


@router.head("/by-code/{code}", summary="Check whether a Power Feed code exists")
async def exists(
    code: str, service: PowerFeedService = Depends(get_service), _: Principal = Depends(require_read)
) -> Response:
    found = await service.exists(code)
    return Response(status_code=status.HTTP_200_OK if found else status.HTTP_404_NOT_FOUND)


@router.get("/{id}", response_model=PowerFeedRead, summary="Get a Power Feed by id")
async def get(
    id: UUID, service: PowerFeedService = Depends(get_service), _: Principal = Depends(require_read)
) -> PowerFeedRead:
    return await service.get(id)


@router.post("", response_model=PowerFeedRead, status_code=status.HTTP_201_CREATED, summary="Create a Power Feed")
async def create(
    payload: PowerFeedCreate,
    request: Request,
    response: Response,
    service: PowerFeedService = Depends(get_service),
    _: Principal = Depends(require_write),
) -> PowerFeedRead:
    created = await service.create(payload)
    response.headers["Location"] = f"{request.url.path}/{created.id}"
    return created


@router.put("/{id}", response_model=PowerFeedRead, summary="Update a Power Feed")
async def update(
    id: UUID,
    payload: PowerFeedUpdate,
    service: PowerFeedService = Depends(get_service),
    _: Principal = Depends(require_write),
) -> PowerFeedRead:
    return await service.update(id, payload)


@router.patch("/{id}/status", response_model=PowerFeedRead, summary="Change lifecycle status")
async def change_status(
    id: UUID,
    new_status: LifecycleStatus = Query(..., alias="status"),
    service: PowerFeedService = Depends(get_service),
    _: Principal = Depends(require_write),
) -> PowerFeedRead:
    return await service.change_status(id, new_status)


@router.delete("/{id}", status_code=status.HTTP_204_NO_CONTENT, summary="Delete a Power Feed")
async def delete(
    id: UUID, service: PowerFeedService = Depends(get_service), _: Principal = Depends(require_write)
) -> Response:
    await service.delete(id)
    return Response(status_code=status.HTTP_204_NO_CONTENT)


@router.post("/bulk", response_model=PowerFeedBulkResult, summary="Create many Power Feed records")
async def bulk_create(
    payloads: list[PowerFeedCreate],
    service: PowerFeedService = Depends(get_service),
    _: Principal = Depends(require_write),
) -> PowerFeedBulkResult:
    return await service.bulk_create(payloads)


@router.post("/reconcile", response_model=list[ReconciliationItem], summary="Reconcile external Power Feed records")
async def reconcile(
    records: list[dict[str, object]],
    apply: bool = Query(False, description="Apply the plan instead of a dry run"),
    service: PowerFeedService = Depends(get_service),
    _: Principal = Depends(require_write),
) -> list[ReconciliationItem]:
    return await service.reconcile(records, apply)


@router.get("/by-room-id/{room_id}", response_model=Page[PowerFeedRead], summary="List Power Feed records by Room Id")
async def list_by_room_id(
    room_id: UUID,
    page: int = Query(0, ge=0),
    size: int = Query(20, ge=1, le=200),
    service: PowerFeedService = Depends(get_service),
) -> Page[PowerFeedRead]:
    return await service.list_by_room_id(room_id, page, size)
