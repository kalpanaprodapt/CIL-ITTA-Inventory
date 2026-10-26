"""HTTP routes for Patch Port."""
from __future__ import annotations

from uuid import UUID

from fastapi import APIRouter, Depends, Query, Request, Response, status
from fastapi.responses import PlainTextResponse

from itta_core.auth import Principal, require_read, require_write
from itta_core.dependencies import service_provider
from itta_core.diffing import ReconciliationItem
from itta_core.models import EntityMeta, LifecycleStatus
from itta_core.pagination import Page
from itta_api.domains.cabling.patch_port.filters import PatchPortFilter
from itta_api.domains.cabling.patch_port.meta import META
from itta_api.domains.cabling.patch_port.schemas import (
    PatchPortBulkResult,
    PatchPortCreate,
    PatchPortRead,
    PatchPortSummary,
    PatchPortUpdate,
)
from itta_api.domains.cabling.patch_port.service import PatchPortService

router = APIRouter(prefix="/api/v1/patch-ports", tags=["Patch Port"])
get_service = service_provider("patch_port")


@router.get("/meta", response_model=EntityMeta, summary="Field catalogue for Patch Port")
async def meta() -> EntityMeta:
    return META


@router.get("", response_model=Page[PatchPortRead], summary="Search Patch Port records")
async def search(
    flt: PatchPortFilter = Depends(),
    page: int = Query(0, ge=0),
    size: int = Query(20, ge=1, le=200),
    sort: str | None = Query(None, description="field,asc|desc"),
    service: PatchPortService = Depends(get_service),
    _: Principal = Depends(require_read),
) -> Page[PatchPortRead]:
    return await service.search(flt, page, size, sort)


@router.get("/summaries", response_model=list[PatchPortSummary], summary="Pick-list of Patch Port records")
async def summaries(
    status_filter: LifecycleStatus | None = Query(None, alias="status"),
    service: PatchPortService = Depends(get_service),
    _: Principal = Depends(require_read),
) -> list[PatchPortSummary]:
    return await service.summaries(status_filter)


@router.get("/stats/status", response_model=dict[str, int], summary="Counts per lifecycle status")
async def status_stats(
    service: PatchPortService = Depends(get_service), _: Principal = Depends(require_read)
) -> dict[str, int]:
    return await service.stats()


@router.get("/export", response_class=PlainTextResponse, summary="Export Patch Port records as CSV")
async def export(
    flt: PatchPortFilter = Depends(),
    service: PatchPortService = Depends(get_service),
    _: Principal = Depends(require_read),
) -> PlainTextResponse:
    body = await service.export_csv(flt)
    headers = {"Content-Disposition": 'attachment; filename="patch-ports.csv"'}
    return PlainTextResponse(body, media_type="text/csv", headers=headers)


@router.get("/by-code/{code}", response_model=PatchPortRead, summary="Get a Patch Port by business code")
async def get_by_code(
    code: str, service: PatchPortService = Depends(get_service), _: Principal = Depends(require_read)
) -> PatchPortRead:
    return await service.get_by_code(code)


@router.head("/by-code/{code}", summary="Check whether a Patch Port code exists")
async def exists(
    code: str, service: PatchPortService = Depends(get_service), _: Principal = Depends(require_read)
) -> Response:
    found = await service.exists(code)
    return Response(status_code=status.HTTP_200_OK if found else status.HTTP_404_NOT_FOUND)


@router.get("/{id}", response_model=PatchPortRead, summary="Get a Patch Port by id")
async def get(
    id: UUID, service: PatchPortService = Depends(get_service), _: Principal = Depends(require_read)
) -> PatchPortRead:
    return await service.get(id)


@router.post("", response_model=PatchPortRead, status_code=status.HTTP_201_CREATED, summary="Create a Patch Port")
async def create(
    payload: PatchPortCreate,
    request: Request,
    response: Response,
    service: PatchPortService = Depends(get_service),
    _: Principal = Depends(require_write),
) -> PatchPortRead:
    created = await service.create(payload)
    response.headers["Location"] = f"{request.url.path}/{created.id}"
    return created


@router.put("/{id}", response_model=PatchPortRead, summary="Update a Patch Port")
async def update(
    id: UUID,
    payload: PatchPortUpdate,
    service: PatchPortService = Depends(get_service),
    _: Principal = Depends(require_write),
) -> PatchPortRead:
    return await service.update(id, payload)


@router.patch("/{id}/status", response_model=PatchPortRead, summary="Change lifecycle status")
async def change_status(
    id: UUID,
    new_status: LifecycleStatus = Query(..., alias="status"),
    service: PatchPortService = Depends(get_service),
    _: Principal = Depends(require_write),
) -> PatchPortRead:
    return await service.change_status(id, new_status)


@router.delete("/{id}", status_code=status.HTTP_204_NO_CONTENT, summary="Delete a Patch Port")
async def delete(
    id: UUID, service: PatchPortService = Depends(get_service), _: Principal = Depends(require_write)
) -> Response:
    await service.delete(id)
    return Response(status_code=status.HTTP_204_NO_CONTENT)


@router.post("/bulk", response_model=PatchPortBulkResult, summary="Create many Patch Port records")
async def bulk_create(
    payloads: list[PatchPortCreate],
    service: PatchPortService = Depends(get_service),
    _: Principal = Depends(require_write),
) -> PatchPortBulkResult:
    return await service.bulk_create(payloads)


@router.post("/reconcile", response_model=list[ReconciliationItem], summary="Reconcile external Patch Port records")
async def reconcile(
    records: list[dict[str, object]],
    apply: bool = Query(False, description="Apply the plan instead of a dry run"),
    service: PatchPortService = Depends(get_service),
    _: Principal = Depends(require_write),
) -> list[ReconciliationItem]:
    return await service.reconcile(records, apply)


@router.get("/by-panel-id/{panel_id}", response_model=Page[PatchPortRead], summary="List Patch Port records by Panel Id")
async def list_by_panel_id(
    panel_id: UUID,
    page: int = Query(0, ge=0),
    size: int = Query(20, ge=1, le=200),
    service: PatchPortService = Depends(get_service),
) -> Page[PatchPortRead]:
    return await service.list_by_panel_id(panel_id, page, size)

@router.get("/by-connected-cable-id/{connected_cable_id}", response_model=Page[PatchPortRead], summary="List Patch Port records by Connected Cable Id")
async def list_by_connected_cable_id(
    connected_cable_id: UUID,
    page: int = Query(0, ge=0),
    size: int = Query(20, ge=1, le=200),
    service: PatchPortService = Depends(get_service),
) -> Page[PatchPortRead]:
    return await service.list_by_connected_cable_id(connected_cable_id, page, size)
