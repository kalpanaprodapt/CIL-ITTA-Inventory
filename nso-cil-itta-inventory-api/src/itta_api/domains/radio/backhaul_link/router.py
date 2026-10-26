"""HTTP routes for Backhaul Link."""
from __future__ import annotations

from uuid import UUID

from fastapi import APIRouter, Depends, Query, Request, Response, status
from fastapi.responses import PlainTextResponse

from itta_core.auth import Principal, require_read, require_write
from itta_core.dependencies import service_provider
from itta_core.diffing import ReconciliationItem
from itta_core.models import EntityMeta, LifecycleStatus
from itta_core.pagination import Page
from itta_api.domains.radio.backhaul_link.filters import BackhaulLinkFilter
from itta_api.domains.radio.backhaul_link.meta import META
from itta_api.domains.radio.backhaul_link.schemas import (
    BackhaulLinkBulkResult,
    BackhaulLinkCreate,
    BackhaulLinkRead,
    BackhaulLinkSummary,
    BackhaulLinkUpdate,
)
from itta_api.domains.radio.backhaul_link.service import BackhaulLinkService

router = APIRouter(prefix="/api/v1/backhaul-links", tags=["Backhaul Link"])
get_service = service_provider("backhaul_link")


@router.get("/meta", response_model=EntityMeta, summary="Field catalogue for Backhaul Link")
async def meta() -> EntityMeta:
    return META


@router.get("", response_model=Page[BackhaulLinkRead], summary="Search Backhaul Link records")
async def search(
    flt: BackhaulLinkFilter = Depends(),
    page: int = Query(0, ge=0),
    size: int = Query(20, ge=1, le=200),
    sort: str | None = Query(None, description="field,asc|desc"),
    service: BackhaulLinkService = Depends(get_service),
    _: Principal = Depends(require_read),
) -> Page[BackhaulLinkRead]:
    return await service.search(flt, page, size, sort)


@router.get("/summaries", response_model=list[BackhaulLinkSummary], summary="Pick-list of Backhaul Link records")
async def summaries(
    status_filter: LifecycleStatus | None = Query(None, alias="status"),
    service: BackhaulLinkService = Depends(get_service),
    _: Principal = Depends(require_read),
) -> list[BackhaulLinkSummary]:
    return await service.summaries(status_filter)


@router.get("/stats/status", response_model=dict[str, int], summary="Counts per lifecycle status")
async def status_stats(
    service: BackhaulLinkService = Depends(get_service), _: Principal = Depends(require_read)
) -> dict[str, int]:
    return await service.stats()


@router.get("/export", response_class=PlainTextResponse, summary="Export Backhaul Link records as CSV")
async def export(
    flt: BackhaulLinkFilter = Depends(),
    service: BackhaulLinkService = Depends(get_service),
    _: Principal = Depends(require_read),
) -> PlainTextResponse:
    body = await service.export_csv(flt)
    headers = {"Content-Disposition": 'attachment; filename="backhaul-links.csv"'}
    return PlainTextResponse(body, media_type="text/csv", headers=headers)


@router.get("/by-code/{code}", response_model=BackhaulLinkRead, summary="Get a Backhaul Link by business code")
async def get_by_code(
    code: str, service: BackhaulLinkService = Depends(get_service), _: Principal = Depends(require_read)
) -> BackhaulLinkRead:
    return await service.get_by_code(code)


@router.head("/by-code/{code}", summary="Check whether a Backhaul Link code exists")
async def exists(
    code: str, service: BackhaulLinkService = Depends(get_service), _: Principal = Depends(require_read)
) -> Response:
    found = await service.exists(code)
    return Response(status_code=status.HTTP_200_OK if found else status.HTTP_404_NOT_FOUND)


@router.get("/{id}", response_model=BackhaulLinkRead, summary="Get a Backhaul Link by id")
async def get(
    id: UUID, service: BackhaulLinkService = Depends(get_service), _: Principal = Depends(require_read)
) -> BackhaulLinkRead:
    return await service.get(id)


@router.post("", response_model=BackhaulLinkRead, status_code=status.HTTP_201_CREATED, summary="Create a Backhaul Link")
async def create(
    payload: BackhaulLinkCreate,
    request: Request,
    response: Response,
    service: BackhaulLinkService = Depends(get_service),
    _: Principal = Depends(require_write),
) -> BackhaulLinkRead:
    created = await service.create(payload)
    response.headers["Location"] = f"{request.url.path}/{created.id}"
    return created


@router.put("/{id}", response_model=BackhaulLinkRead, summary="Update a Backhaul Link")
async def update(
    id: UUID,
    payload: BackhaulLinkUpdate,
    service: BackhaulLinkService = Depends(get_service),
    _: Principal = Depends(require_write),
) -> BackhaulLinkRead:
    return await service.update(id, payload)


@router.patch("/{id}/status", response_model=BackhaulLinkRead, summary="Change lifecycle status")
async def change_status(
    id: UUID,
    new_status: LifecycleStatus = Query(..., alias="status"),
    service: BackhaulLinkService = Depends(get_service),
    _: Principal = Depends(require_write),
) -> BackhaulLinkRead:
    return await service.change_status(id, new_status)


@router.delete("/{id}", status_code=status.HTTP_204_NO_CONTENT, summary="Delete a Backhaul Link")
async def delete(
    id: UUID, service: BackhaulLinkService = Depends(get_service), _: Principal = Depends(require_write)
) -> Response:
    await service.delete(id)
    return Response(status_code=status.HTTP_204_NO_CONTENT)


@router.post("/bulk", response_model=BackhaulLinkBulkResult, summary="Create many Backhaul Link records")
async def bulk_create(
    payloads: list[BackhaulLinkCreate],
    service: BackhaulLinkService = Depends(get_service),
    _: Principal = Depends(require_write),
) -> BackhaulLinkBulkResult:
    return await service.bulk_create(payloads)


@router.post("/reconcile", response_model=list[ReconciliationItem], summary="Reconcile external Backhaul Link records")
async def reconcile(
    records: list[dict[str, object]],
    apply: bool = Query(False, description="Apply the plan instead of a dry run"),
    service: BackhaulLinkService = Depends(get_service),
    _: Principal = Depends(require_write),
) -> list[ReconciliationItem]:
    return await service.reconcile(records, apply)


@router.get("/by-base-station-id/{base_station_id}", response_model=Page[BackhaulLinkRead], summary="List Backhaul Link records by Base Station Id")
async def list_by_base_station_id(
    base_station_id: UUID,
    page: int = Query(0, ge=0),
    size: int = Query(20, ge=1, le=200),
    service: BackhaulLinkService = Depends(get_service),
) -> Page[BackhaulLinkRead]:
    return await service.list_by_base_station_id(base_station_id, page, size)
