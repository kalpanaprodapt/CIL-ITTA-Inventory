"""HTTP routes for Patch Panel."""
from __future__ import annotations

from uuid import UUID

from fastapi import APIRouter, Depends, Query, Request, Response, status
from fastapi.responses import PlainTextResponse

from itta_core.auth import Principal, require_read, require_write
from itta_core.dependencies import service_provider
from itta_core.diffing import ReconciliationItem
from itta_core.models import EntityMeta, LifecycleStatus
from itta_core.pagination import Page
from itta_api.domains.cabling.patch_panel.filters import PatchPanelFilter
from itta_api.domains.cabling.patch_panel.meta import META
from itta_api.domains.cabling.patch_panel.schemas import (
    PatchPanelBulkResult,
    PatchPanelCreate,
    PatchPanelRead,
    PatchPanelSummary,
    PatchPanelUpdate,
)
from itta_api.domains.cabling.patch_panel.service import PatchPanelService

router = APIRouter(prefix="/api/v1/patch-panels", tags=["Patch Panel"])
get_service = service_provider("patch_panel")


@router.get("/meta", response_model=EntityMeta, summary="Field catalogue for Patch Panel")
async def meta() -> EntityMeta:
    return META


@router.get("", response_model=Page[PatchPanelRead], summary="Search Patch Panel records")
async def search(
    flt: PatchPanelFilter = Depends(),
    page: int = Query(0, ge=0),
    size: int = Query(20, ge=1, le=200),
    sort: str | None = Query(None, description="field,asc|desc"),
    service: PatchPanelService = Depends(get_service),
    _: Principal = Depends(require_read),
) -> Page[PatchPanelRead]:
    return await service.search(flt, page, size, sort)


@router.get("/summaries", response_model=list[PatchPanelSummary], summary="Pick-list of Patch Panel records")
async def summaries(
    status_filter: LifecycleStatus | None = Query(None, alias="status"),
    service: PatchPanelService = Depends(get_service),
    _: Principal = Depends(require_read),
) -> list[PatchPanelSummary]:
    return await service.summaries(status_filter)


@router.get("/stats/status", response_model=dict[str, int], summary="Counts per lifecycle status")
async def status_stats(
    service: PatchPanelService = Depends(get_service), _: Principal = Depends(require_read)
) -> dict[str, int]:
    return await service.stats()


@router.get("/export", response_class=PlainTextResponse, summary="Export Patch Panel records as CSV")
async def export(
    flt: PatchPanelFilter = Depends(),
    service: PatchPanelService = Depends(get_service),
    _: Principal = Depends(require_read),
) -> PlainTextResponse:
    body = await service.export_csv(flt)
    headers = {"Content-Disposition": 'attachment; filename="patch-panels.csv"'}
    return PlainTextResponse(body, media_type="text/csv", headers=headers)


@router.get("/by-code/{code}", response_model=PatchPanelRead, summary="Get a Patch Panel by business code")
async def get_by_code(
    code: str, service: PatchPanelService = Depends(get_service), _: Principal = Depends(require_read)
) -> PatchPanelRead:
    return await service.get_by_code(code)


@router.head("/by-code/{code}", summary="Check whether a Patch Panel code exists")
async def exists(
    code: str, service: PatchPanelService = Depends(get_service), _: Principal = Depends(require_read)
) -> Response:
    found = await service.exists(code)
    return Response(status_code=status.HTTP_200_OK if found else status.HTTP_404_NOT_FOUND)


@router.get("/{id}", response_model=PatchPanelRead, summary="Get a Patch Panel by id")
async def get(
    id: UUID, service: PatchPanelService = Depends(get_service), _: Principal = Depends(require_read)
) -> PatchPanelRead:
    return await service.get(id)


@router.post("", response_model=PatchPanelRead, status_code=status.HTTP_201_CREATED, summary="Create a Patch Panel")
async def create(
    payload: PatchPanelCreate,
    request: Request,
    response: Response,
    service: PatchPanelService = Depends(get_service),
    _: Principal = Depends(require_write),
) -> PatchPanelRead:
    created = await service.create(payload)
    response.headers["Location"] = f"{request.url.path}/{created.id}"
    return created


@router.put("/{id}", response_model=PatchPanelRead, summary="Update a Patch Panel")
async def update(
    id: UUID,
    payload: PatchPanelUpdate,
    service: PatchPanelService = Depends(get_service),
    _: Principal = Depends(require_write),
) -> PatchPanelRead:
    return await service.update(id, payload)


@router.patch("/{id}/status", response_model=PatchPanelRead, summary="Change lifecycle status")
async def change_status(
    id: UUID,
    new_status: LifecycleStatus = Query(..., alias="status"),
    service: PatchPanelService = Depends(get_service),
    _: Principal = Depends(require_write),
) -> PatchPanelRead:
    return await service.change_status(id, new_status)


@router.delete("/{id}", status_code=status.HTTP_204_NO_CONTENT, summary="Delete a Patch Panel")
async def delete(
    id: UUID, service: PatchPanelService = Depends(get_service), _: Principal = Depends(require_write)
) -> Response:
    await service.delete(id)
    return Response(status_code=status.HTTP_204_NO_CONTENT)


@router.post("/bulk", response_model=PatchPanelBulkResult, summary="Create many Patch Panel records")
async def bulk_create(
    payloads: list[PatchPanelCreate],
    service: PatchPanelService = Depends(get_service),
    _: Principal = Depends(require_write),
) -> PatchPanelBulkResult:
    return await service.bulk_create(payloads)


@router.post("/reconcile", response_model=list[ReconciliationItem], summary="Reconcile external Patch Panel records")
async def reconcile(
    records: list[dict[str, object]],
    apply: bool = Query(False, description="Apply the plan instead of a dry run"),
    service: PatchPanelService = Depends(get_service),
    _: Principal = Depends(require_write),
) -> list[ReconciliationItem]:
    return await service.reconcile(records, apply)


@router.get("/by-room-id/{room_id}", response_model=Page[PatchPanelRead], summary="List Patch Panel records by Room Id")
async def list_by_room_id(
    room_id: UUID,
    page: int = Query(0, ge=0),
    size: int = Query(20, ge=1, le=200),
    service: PatchPanelService = Depends(get_service),
) -> Page[PatchPanelRead]:
    return await service.list_by_room_id(room_id, page, size)

@router.get("/by-rack-id/{rack_id}", response_model=Page[PatchPanelRead], summary="List Patch Panel records by Rack Id")
async def list_by_rack_id(
    rack_id: UUID,
    page: int = Query(0, ge=0),
    size: int = Query(20, ge=1, le=200),
    service: PatchPanelService = Depends(get_service),
) -> Page[PatchPanelRead]:
    return await service.list_by_rack_id(rack_id, page, size)
