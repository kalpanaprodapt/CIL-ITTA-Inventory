"""HTTP routes for Room."""
from __future__ import annotations

from uuid import UUID

from fastapi import APIRouter, Depends, Query, Request, Response, status
from fastapi.responses import PlainTextResponse

from itta_core.auth import Principal, require_read, require_write
from itta_core.dependencies import service_provider
from itta_core.diffing import ReconciliationItem
from itta_core.models import EntityMeta, LifecycleStatus
from itta_core.pagination import Page
from itta_api.domains.facility.room.filters import RoomFilter
from itta_api.domains.facility.room.meta import META
from itta_api.domains.facility.room.schemas import (
    RoomBulkResult,
    RoomCreate,
    RoomRead,
    RoomSummary,
    RoomUpdate,
)
from itta_api.domains.facility.room.service import RoomService

router = APIRouter(prefix="/api/v1/rooms", tags=["Room"])
get_service = service_provider("room")


@router.get("/meta", response_model=EntityMeta, summary="Field catalogue for Room")
async def meta() -> EntityMeta:
    return META


@router.get("", response_model=Page[RoomRead], summary="Search Room records")
async def search(
    flt: RoomFilter = Depends(),
    page: int = Query(0, ge=0),
    size: int = Query(20, ge=1, le=200),
    sort: str | None = Query(None, description="field,asc|desc"),
    service: RoomService = Depends(get_service),
    _: Principal = Depends(require_read),
) -> Page[RoomRead]:
    return await service.search(flt, page, size, sort)


@router.get("/summaries", response_model=list[RoomSummary], summary="Pick-list of Room records")
async def summaries(
    status_filter: LifecycleStatus | None = Query(None, alias="status"),
    service: RoomService = Depends(get_service),
    _: Principal = Depends(require_read),
) -> list[RoomSummary]:
    return await service.summaries(status_filter)


@router.get("/stats/status", response_model=dict[str, int], summary="Counts per lifecycle status")
async def status_stats(
    service: RoomService = Depends(get_service), _: Principal = Depends(require_read)
) -> dict[str, int]:
    return await service.stats()


@router.get("/export", response_class=PlainTextResponse, summary="Export Room records as CSV")
async def export(
    flt: RoomFilter = Depends(),
    service: RoomService = Depends(get_service),
    _: Principal = Depends(require_read),
) -> PlainTextResponse:
    body = await service.export_csv(flt)
    headers = {"Content-Disposition": 'attachment; filename="rooms.csv"'}
    return PlainTextResponse(body, media_type="text/csv", headers=headers)


@router.get("/by-code/{code}", response_model=RoomRead, summary="Get a Room by business code")
async def get_by_code(
    code: str, service: RoomService = Depends(get_service), _: Principal = Depends(require_read)
) -> RoomRead:
    return await service.get_by_code(code)


@router.head("/by-code/{code}", summary="Check whether a Room code exists")
async def exists(
    code: str, service: RoomService = Depends(get_service), _: Principal = Depends(require_read)
) -> Response:
    found = await service.exists(code)
    return Response(status_code=status.HTTP_200_OK if found else status.HTTP_404_NOT_FOUND)


@router.get("/{id}", response_model=RoomRead, summary="Get a Room by id")
async def get(
    id: UUID, service: RoomService = Depends(get_service), _: Principal = Depends(require_read)
) -> RoomRead:
    return await service.get(id)


@router.post("", response_model=RoomRead, status_code=status.HTTP_201_CREATED, summary="Create a Room")
async def create(
    payload: RoomCreate,
    request: Request,
    response: Response,
    service: RoomService = Depends(get_service),
    _: Principal = Depends(require_write),
) -> RoomRead:
    created = await service.create(payload)
    response.headers["Location"] = f"{request.url.path}/{created.id}"
    return created


@router.put("/{id}", response_model=RoomRead, summary="Update a Room")
async def update(
    id: UUID,
    payload: RoomUpdate,
    service: RoomService = Depends(get_service),
    _: Principal = Depends(require_write),
) -> RoomRead:
    return await service.update(id, payload)


@router.patch("/{id}/status", response_model=RoomRead, summary="Change lifecycle status")
async def change_status(
    id: UUID,
    new_status: LifecycleStatus = Query(..., alias="status"),
    service: RoomService = Depends(get_service),
    _: Principal = Depends(require_write),
) -> RoomRead:
    return await service.change_status(id, new_status)


@router.delete("/{id}", status_code=status.HTTP_204_NO_CONTENT, summary="Delete a Room")
async def delete(
    id: UUID, service: RoomService = Depends(get_service), _: Principal = Depends(require_write)
) -> Response:
    await service.delete(id)
    return Response(status_code=status.HTTP_204_NO_CONTENT)


@router.post("/bulk", response_model=RoomBulkResult, summary="Create many Room records")
async def bulk_create(
    payloads: list[RoomCreate],
    service: RoomService = Depends(get_service),
    _: Principal = Depends(require_write),
) -> RoomBulkResult:
    return await service.bulk_create(payloads)


@router.post("/reconcile", response_model=list[ReconciliationItem], summary="Reconcile external Room records")
async def reconcile(
    records: list[dict[str, object]],
    apply: bool = Query(False, description="Apply the plan instead of a dry run"),
    service: RoomService = Depends(get_service),
    _: Principal = Depends(require_write),
) -> list[ReconciliationItem]:
    return await service.reconcile(records, apply)


@router.get("/by-floor-id/{floor_id}", response_model=Page[RoomRead], summary="List Room records by Floor Id")
async def list_by_floor_id(
    floor_id: UUID,
    page: int = Query(0, ge=0),
    size: int = Query(20, ge=1, le=200),
    service: RoomService = Depends(get_service),
) -> Page[RoomRead]:
    return await service.list_by_floor_id(floor_id, page, size)
