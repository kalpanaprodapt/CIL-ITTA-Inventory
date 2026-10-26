"""HTTP routes for Bgp Session."""
from __future__ import annotations

from uuid import UUID

from fastapi import APIRouter, Depends, Query, Request, Response, status
from fastapi.responses import PlainTextResponse

from itta_core.auth import Principal, require_read, require_write
from itta_core.dependencies import service_provider
from itta_core.diffing import ReconciliationItem
from itta_core.models import EntityMeta, LifecycleStatus
from itta_core.pagination import Page
from itta_api.domains.switching.bgp_session.filters import BgpSessionFilter
from itta_api.domains.switching.bgp_session.meta import META
from itta_api.domains.switching.bgp_session.schemas import (
    BgpSessionBulkResult,
    BgpSessionCreate,
    BgpSessionRead,
    BgpSessionSummary,
    BgpSessionUpdate,
)
from itta_api.domains.switching.bgp_session.service import BgpSessionService

router = APIRouter(prefix="/api/v1/bgp-sessions", tags=["Bgp Session"])
get_service = service_provider("bgp_session")


@router.get("/meta", response_model=EntityMeta, summary="Field catalogue for Bgp Session")
async def meta() -> EntityMeta:
    return META


@router.get("", response_model=Page[BgpSessionRead], summary="Search Bgp Session records")
async def search(
    flt: BgpSessionFilter = Depends(),
    page: int = Query(0, ge=0),
    size: int = Query(20, ge=1, le=200),
    sort: str | None = Query(None, description="field,asc|desc"),
    service: BgpSessionService = Depends(get_service),
    _: Principal = Depends(require_read),
) -> Page[BgpSessionRead]:
    return await service.search(flt, page, size, sort)


@router.get("/summaries", response_model=list[BgpSessionSummary], summary="Pick-list of Bgp Session records")
async def summaries(
    status_filter: LifecycleStatus | None = Query(None, alias="status"),
    service: BgpSessionService = Depends(get_service),
    _: Principal = Depends(require_read),
) -> list[BgpSessionSummary]:
    return await service.summaries(status_filter)


@router.get("/stats/status", response_model=dict[str, int], summary="Counts per lifecycle status")
async def status_stats(
    service: BgpSessionService = Depends(get_service), _: Principal = Depends(require_read)
) -> dict[str, int]:
    return await service.stats()


@router.get("/export", response_class=PlainTextResponse, summary="Export Bgp Session records as CSV")
async def export(
    flt: BgpSessionFilter = Depends(),
    service: BgpSessionService = Depends(get_service),
    _: Principal = Depends(require_read),
) -> PlainTextResponse:
    body = await service.export_csv(flt)
    headers = {"Content-Disposition": 'attachment; filename="bgp-sessions.csv"'}
    return PlainTextResponse(body, media_type="text/csv", headers=headers)


@router.get("/by-code/{code}", response_model=BgpSessionRead, summary="Get a Bgp Session by business code")
async def get_by_code(
    code: str, service: BgpSessionService = Depends(get_service), _: Principal = Depends(require_read)
) -> BgpSessionRead:
    return await service.get_by_code(code)


@router.head("/by-code/{code}", summary="Check whether a Bgp Session code exists")
async def exists(
    code: str, service: BgpSessionService = Depends(get_service), _: Principal = Depends(require_read)
) -> Response:
    found = await service.exists(code)
    return Response(status_code=status.HTTP_200_OK if found else status.HTTP_404_NOT_FOUND)


@router.get("/{id}", response_model=BgpSessionRead, summary="Get a Bgp Session by id")
async def get(
    id: UUID, service: BgpSessionService = Depends(get_service), _: Principal = Depends(require_read)
) -> BgpSessionRead:
    return await service.get(id)


@router.post("", response_model=BgpSessionRead, status_code=status.HTTP_201_CREATED, summary="Create a Bgp Session")
async def create(
    payload: BgpSessionCreate,
    request: Request,
    response: Response,
    service: BgpSessionService = Depends(get_service),
    _: Principal = Depends(require_write),
) -> BgpSessionRead:
    created = await service.create(payload)
    response.headers["Location"] = f"{request.url.path}/{created.id}"
    return created


@router.put("/{id}", response_model=BgpSessionRead, summary="Update a Bgp Session")
async def update(
    id: UUID,
    payload: BgpSessionUpdate,
    service: BgpSessionService = Depends(get_service),
    _: Principal = Depends(require_write),
) -> BgpSessionRead:
    return await service.update(id, payload)


@router.patch("/{id}/status", response_model=BgpSessionRead, summary="Change lifecycle status")
async def change_status(
    id: UUID,
    new_status: LifecycleStatus = Query(..., alias="status"),
    service: BgpSessionService = Depends(get_service),
    _: Principal = Depends(require_write),
) -> BgpSessionRead:
    return await service.change_status(id, new_status)


@router.delete("/{id}", status_code=status.HTTP_204_NO_CONTENT, summary="Delete a Bgp Session")
async def delete(
    id: UUID, service: BgpSessionService = Depends(get_service), _: Principal = Depends(require_write)
) -> Response:
    await service.delete(id)
    return Response(status_code=status.HTTP_204_NO_CONTENT)


@router.post("/bulk", response_model=BgpSessionBulkResult, summary="Create many Bgp Session records")
async def bulk_create(
    payloads: list[BgpSessionCreate],
    service: BgpSessionService = Depends(get_service),
    _: Principal = Depends(require_write),
) -> BgpSessionBulkResult:
    return await service.bulk_create(payloads)


@router.post("/reconcile", response_model=list[ReconciliationItem], summary="Reconcile external Bgp Session records")
async def reconcile(
    records: list[dict[str, object]],
    apply: bool = Query(False, description="Apply the plan instead of a dry run"),
    service: BgpSessionService = Depends(get_service),
    _: Principal = Depends(require_write),
) -> list[ReconciliationItem]:
    return await service.reconcile(records, apply)


@router.get("/by-local-device-id/{local_device_id}", response_model=Page[BgpSessionRead], summary="List Bgp Session records by Local Device Id")
async def list_by_local_device_id(
    local_device_id: UUID,
    page: int = Query(0, ge=0),
    size: int = Query(20, ge=1, le=200),
    service: BgpSessionService = Depends(get_service),
) -> Page[BgpSessionRead]:
    return await service.list_by_local_device_id(local_device_id, page, size)
