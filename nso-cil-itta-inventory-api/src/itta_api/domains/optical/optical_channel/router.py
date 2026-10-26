"""HTTP routes for Optical Channel."""
from __future__ import annotations

from uuid import UUID

from fastapi import APIRouter, Depends, Query, Request, Response, status
from fastapi.responses import PlainTextResponse

from itta_core.auth import Principal, require_read, require_write
from itta_core.dependencies import service_provider
from itta_core.diffing import ReconciliationItem
from itta_core.models import EntityMeta, LifecycleStatus
from itta_core.pagination import Page
from itta_api.domains.optical.optical_channel.filters import OpticalChannelFilter
from itta_api.domains.optical.optical_channel.meta import META
from itta_api.domains.optical.optical_channel.schemas import (
    OpticalChannelBulkResult,
    OpticalChannelCreate,
    OpticalChannelRead,
    OpticalChannelSummary,
    OpticalChannelUpdate,
)
from itta_api.domains.optical.optical_channel.service import OpticalChannelService

router = APIRouter(prefix="/api/v1/optical-channels", tags=["Optical Channel"])
get_service = service_provider("optical_channel")


@router.get("/meta", response_model=EntityMeta, summary="Field catalogue for Optical Channel")
async def meta() -> EntityMeta:
    return META


@router.get("", response_model=Page[OpticalChannelRead], summary="Search Optical Channel records")
async def search(
    flt: OpticalChannelFilter = Depends(),
    page: int = Query(0, ge=0),
    size: int = Query(20, ge=1, le=200),
    sort: str | None = Query(None, description="field,asc|desc"),
    service: OpticalChannelService = Depends(get_service),
    _: Principal = Depends(require_read),
) -> Page[OpticalChannelRead]:
    return await service.search(flt, page, size, sort)


@router.get("/summaries", response_model=list[OpticalChannelSummary], summary="Pick-list of Optical Channel records")
async def summaries(
    status_filter: LifecycleStatus | None = Query(None, alias="status"),
    service: OpticalChannelService = Depends(get_service),
    _: Principal = Depends(require_read),
) -> list[OpticalChannelSummary]:
    return await service.summaries(status_filter)


@router.get("/stats/status", response_model=dict[str, int], summary="Counts per lifecycle status")
async def status_stats(
    service: OpticalChannelService = Depends(get_service), _: Principal = Depends(require_read)
) -> dict[str, int]:
    return await service.stats()


@router.get("/export", response_class=PlainTextResponse, summary="Export Optical Channel records as CSV")
async def export(
    flt: OpticalChannelFilter = Depends(),
    service: OpticalChannelService = Depends(get_service),
    _: Principal = Depends(require_read),
) -> PlainTextResponse:
    body = await service.export_csv(flt)
    headers = {"Content-Disposition": 'attachment; filename="optical-channels.csv"'}
    return PlainTextResponse(body, media_type="text/csv", headers=headers)


@router.get("/by-code/{code}", response_model=OpticalChannelRead, summary="Get a Optical Channel by business code")
async def get_by_code(
    code: str, service: OpticalChannelService = Depends(get_service), _: Principal = Depends(require_read)
) -> OpticalChannelRead:
    return await service.get_by_code(code)


@router.head("/by-code/{code}", summary="Check whether a Optical Channel code exists")
async def exists(
    code: str, service: OpticalChannelService = Depends(get_service), _: Principal = Depends(require_read)
) -> Response:
    found = await service.exists(code)
    return Response(status_code=status.HTTP_200_OK if found else status.HTTP_404_NOT_FOUND)


@router.get("/{id}", response_model=OpticalChannelRead, summary="Get a Optical Channel by id")
async def get(
    id: UUID, service: OpticalChannelService = Depends(get_service), _: Principal = Depends(require_read)
) -> OpticalChannelRead:
    return await service.get(id)


@router.post("", response_model=OpticalChannelRead, status_code=status.HTTP_201_CREATED, summary="Create a Optical Channel")
async def create(
    payload: OpticalChannelCreate,
    request: Request,
    response: Response,
    service: OpticalChannelService = Depends(get_service),
    _: Principal = Depends(require_write),
) -> OpticalChannelRead:
    created = await service.create(payload)
    response.headers["Location"] = f"{request.url.path}/{created.id}"
    return created


@router.put("/{id}", response_model=OpticalChannelRead, summary="Update a Optical Channel")
async def update(
    id: UUID,
    payload: OpticalChannelUpdate,
    service: OpticalChannelService = Depends(get_service),
    _: Principal = Depends(require_write),
) -> OpticalChannelRead:
    return await service.update(id, payload)


@router.patch("/{id}/status", response_model=OpticalChannelRead, summary="Change lifecycle status")
async def change_status(
    id: UUID,
    new_status: LifecycleStatus = Query(..., alias="status"),
    service: OpticalChannelService = Depends(get_service),
    _: Principal = Depends(require_write),
) -> OpticalChannelRead:
    return await service.change_status(id, new_status)


@router.delete("/{id}", status_code=status.HTTP_204_NO_CONTENT, summary="Delete a Optical Channel")
async def delete(
    id: UUID, service: OpticalChannelService = Depends(get_service), _: Principal = Depends(require_write)
) -> Response:
    await service.delete(id)
    return Response(status_code=status.HTTP_204_NO_CONTENT)


@router.post("/bulk", response_model=OpticalChannelBulkResult, summary="Create many Optical Channel records")
async def bulk_create(
    payloads: list[OpticalChannelCreate],
    service: OpticalChannelService = Depends(get_service),
    _: Principal = Depends(require_write),
) -> OpticalChannelBulkResult:
    return await service.bulk_create(payloads)


@router.post("/reconcile", response_model=list[ReconciliationItem], summary="Reconcile external Optical Channel records")
async def reconcile(
    records: list[dict[str, object]],
    apply: bool = Query(False, description="Apply the plan instead of a dry run"),
    service: OpticalChannelService = Depends(get_service),
    _: Principal = Depends(require_write),
) -> list[ReconciliationItem]:
    return await service.reconcile(records, apply)


@router.get("/by-system-id/{system_id}", response_model=Page[OpticalChannelRead], summary="List Optical Channel records by System Id")
async def list_by_system_id(
    system_id: UUID,
    page: int = Query(0, ge=0),
    size: int = Query(20, ge=1, le=200),
    service: OpticalChannelService = Depends(get_service),
) -> Page[OpticalChannelRead]:
    return await service.list_by_system_id(system_id, page, size)
