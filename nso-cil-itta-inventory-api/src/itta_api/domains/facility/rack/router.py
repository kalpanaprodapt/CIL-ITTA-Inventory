"""HTTP routes for Rack."""
from __future__ import annotations

from uuid import UUID

from fastapi import APIRouter, Depends, Query, Request, Response, status
from fastapi.responses import PlainTextResponse

from itta_core.auth import Principal, require_read, require_write
from itta_core.dependencies import service_provider
from itta_core.diffing import ReconciliationItem
from itta_core.models import EntityMeta, LifecycleStatus
from itta_core.pagination import Page
from itta_api.domains.facility.rack.filters import RackFilter
from itta_api.domains.facility.rack.meta import META
from itta_api.domains.facility.rack.schemas import (
    RackBulkResult,
    RackCreate,
    RackRead,
    RackSummary,
    RackUpdate,
)
from itta_api.domains.facility.rack.service import RackService

router = APIRouter(prefix="/api/v1/racks", tags=["Rack"])
get_service = service_provider("rack")


@router.get("/meta", response_model=EntityMeta, summary="Field catalogue for Rack")
async def meta() -> EntityMeta:
    return META


@router.get("", response_model=Page[RackRead], summary="Search Rack records")
async def search(
    flt: RackFilter = Depends(),
    page: int = Query(0, ge=0),
    size: int = Query(20, ge=1, le=200),
    sort: str | None = Query(None, description="field,asc|desc"),
    service: RackService = Depends(get_service),
    _: Principal = Depends(require_read),
) -> Page[RackRead]:
    return await service.search(flt, page, size, sort)


@router.get("/summaries", response_model=list[RackSummary], summary="Pick-list of Rack records")
async def summaries(
    status_filter: LifecycleStatus | None = Query(None, alias="status"),
    service: RackService = Depends(get_service),
    _: Principal = Depends(require_read),
) -> list[RackSummary]:
    return await service.summaries(status_filter)


@router.get("/stats/status", response_model=dict[str, int], summary="Counts per lifecycle status")
async def status_stats(
    service: RackService = Depends(get_service), _: Principal = Depends(require_read)
) -> dict[str, int]:
    return await service.stats()


@router.get("/export", response_class=PlainTextResponse, summary="Export Rack records as CSV")
async def export(
    flt: RackFilter = Depends(),
    service: RackService = Depends(get_service),
    _: Principal = Depends(require_read),
) -> PlainTextResponse:
    body = await service.export_csv(flt)
    headers = {"Content-Disposition": 'attachment; filename="racks.csv"'}
    return PlainTextResponse(body, media_type="text/csv", headers=headers)


@router.get("/by-code/{code}", response_model=RackRead, summary="Get a Rack by business code")
async def get_by_code(
    code: str, service: RackService = Depends(get_service), _: Principal = Depends(require_read)
) -> RackRead:
    return await service.get_by_code(code)


@router.head("/by-code/{code}", summary="Check whether a Rack code exists")
async def exists(
    code: str, service: RackService = Depends(get_service), _: Principal = Depends(require_read)
) -> Response:
    found = await service.exists(code)
    return Response(status_code=status.HTTP_200_OK if found else status.HTTP_404_NOT_FOUND)


@router.get("/{id}", response_model=RackRead, summary="Get a Rack by id")
async def get(
    id: UUID, service: RackService = Depends(get_service), _: Principal = Depends(require_read)
) -> RackRead:
    return await service.get(id)


@router.post("", response_model=RackRead, status_code=status.HTTP_201_CREATED, summary="Create a Rack")
async def create(
    payload: RackCreate,
    request: Request,
    response: Response,
    service: RackService = Depends(get_service),
    _: Principal = Depends(require_write),
) -> RackRead:
    created = await service.create(payload)
    response.headers["Location"] = f"{request.url.path}/{created.id}"
    return created


@router.put("/{id}", response_model=RackRead, summary="Update a Rack")
async def update(
    id: UUID,
    payload: RackUpdate,
    service: RackService = Depends(get_service),
    _: Principal = Depends(require_write),
) -> RackRead:
    return await service.update(id, payload)


@router.patch("/{id}/status", response_model=RackRead, summary="Change lifecycle status")
async def change_status(
    id: UUID,
    new_status: LifecycleStatus = Query(..., alias="status"),
    service: RackService = Depends(get_service),
    _: Principal = Depends(require_write),
) -> RackRead:
    return await service.change_status(id, new_status)


@router.delete("/{id}", status_code=status.HTTP_204_NO_CONTENT, summary="Delete a Rack")
async def delete(
    id: UUID, service: RackService = Depends(get_service), _: Principal = Depends(require_write)
) -> Response:
    await service.delete(id)
    return Response(status_code=status.HTTP_204_NO_CONTENT)


@router.post("/bulk", response_model=RackBulkResult, summary="Create many Rack records")
async def bulk_create(
    payloads: list[RackCreate],
    service: RackService = Depends(get_service),
    _: Principal = Depends(require_write),
) -> RackBulkResult:
    return await service.bulk_create(payloads)


@router.post("/reconcile", response_model=list[ReconciliationItem], summary="Reconcile external Rack records")
async def reconcile(
    records: list[dict[str, object]],
    apply: bool = Query(False, description="Apply the plan instead of a dry run"),
    service: RackService = Depends(get_service),
    _: Principal = Depends(require_write),
) -> list[ReconciliationItem]:
    return await service.reconcile(records, apply)


@router.get("/by-room-id/{room_id}", response_model=Page[RackRead], summary="List Rack records by Room Id")
async def list_by_room_id(
    room_id: UUID,
    page: int = Query(0, ge=0),
    size: int = Query(20, ge=1, le=200),
    service: RackService = Depends(get_service),
) -> Page[RackRead]:
    return await service.list_by_room_id(room_id, page, size)
