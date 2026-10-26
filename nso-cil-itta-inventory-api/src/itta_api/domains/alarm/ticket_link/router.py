"""HTTP routes for Ticket Link."""
from __future__ import annotations

from uuid import UUID

from fastapi import APIRouter, Depends, Query, Request, Response, status
from fastapi.responses import PlainTextResponse

from itta_core.auth import Principal, require_read, require_write
from itta_core.dependencies import service_provider
from itta_core.diffing import ReconciliationItem
from itta_core.models import EntityMeta, LifecycleStatus
from itta_core.pagination import Page
from itta_api.domains.alarm.ticket_link.filters import TicketLinkFilter
from itta_api.domains.alarm.ticket_link.meta import META
from itta_api.domains.alarm.ticket_link.schemas import (
    TicketLinkBulkResult,
    TicketLinkCreate,
    TicketLinkRead,
    TicketLinkSummary,
    TicketLinkUpdate,
)
from itta_api.domains.alarm.ticket_link.service import TicketLinkService

router = APIRouter(prefix="/api/v1/ticket-links", tags=["Ticket Link"])
get_service = service_provider("ticket_link")


@router.get("/meta", response_model=EntityMeta, summary="Field catalogue for Ticket Link")
async def meta() -> EntityMeta:
    return META


@router.get("", response_model=Page[TicketLinkRead], summary="Search Ticket Link records")
async def search(
    flt: TicketLinkFilter = Depends(),
    page: int = Query(0, ge=0),
    size: int = Query(20, ge=1, le=200),
    sort: str | None = Query(None, description="field,asc|desc"),
    service: TicketLinkService = Depends(get_service),
    _: Principal = Depends(require_read),
) -> Page[TicketLinkRead]:
    return await service.search(flt, page, size, sort)


@router.get("/summaries", response_model=list[TicketLinkSummary], summary="Pick-list of Ticket Link records")
async def summaries(
    status_filter: LifecycleStatus | None = Query(None, alias="status"),
    service: TicketLinkService = Depends(get_service),
    _: Principal = Depends(require_read),
) -> list[TicketLinkSummary]:
    return await service.summaries(status_filter)


@router.get("/stats/status", response_model=dict[str, int], summary="Counts per lifecycle status")
async def status_stats(
    service: TicketLinkService = Depends(get_service), _: Principal = Depends(require_read)
) -> dict[str, int]:
    return await service.stats()


@router.get("/export", response_class=PlainTextResponse, summary="Export Ticket Link records as CSV")
async def export(
    flt: TicketLinkFilter = Depends(),
    service: TicketLinkService = Depends(get_service),
    _: Principal = Depends(require_read),
) -> PlainTextResponse:
    body = await service.export_csv(flt)
    headers = {"Content-Disposition": 'attachment; filename="ticket-links.csv"'}
    return PlainTextResponse(body, media_type="text/csv", headers=headers)


@router.get("/by-code/{code}", response_model=TicketLinkRead, summary="Get a Ticket Link by business code")
async def get_by_code(
    code: str, service: TicketLinkService = Depends(get_service), _: Principal = Depends(require_read)
) -> TicketLinkRead:
    return await service.get_by_code(code)


@router.head("/by-code/{code}", summary="Check whether a Ticket Link code exists")
async def exists(
    code: str, service: TicketLinkService = Depends(get_service), _: Principal = Depends(require_read)
) -> Response:
    found = await service.exists(code)
    return Response(status_code=status.HTTP_200_OK if found else status.HTTP_404_NOT_FOUND)


@router.get("/{id}", response_model=TicketLinkRead, summary="Get a Ticket Link by id")
async def get(
    id: UUID, service: TicketLinkService = Depends(get_service), _: Principal = Depends(require_read)
) -> TicketLinkRead:
    return await service.get(id)


@router.post("", response_model=TicketLinkRead, status_code=status.HTTP_201_CREATED, summary="Create a Ticket Link")
async def create(
    payload: TicketLinkCreate,
    request: Request,
    response: Response,
    service: TicketLinkService = Depends(get_service),
    _: Principal = Depends(require_write),
) -> TicketLinkRead:
    created = await service.create(payload)
    response.headers["Location"] = f"{request.url.path}/{created.id}"
    return created


@router.put("/{id}", response_model=TicketLinkRead, summary="Update a Ticket Link")
async def update(
    id: UUID,
    payload: TicketLinkUpdate,
    service: TicketLinkService = Depends(get_service),
    _: Principal = Depends(require_write),
) -> TicketLinkRead:
    return await service.update(id, payload)


@router.patch("/{id}/status", response_model=TicketLinkRead, summary="Change lifecycle status")
async def change_status(
    id: UUID,
    new_status: LifecycleStatus = Query(..., alias="status"),
    service: TicketLinkService = Depends(get_service),
    _: Principal = Depends(require_write),
) -> TicketLinkRead:
    return await service.change_status(id, new_status)


@router.delete("/{id}", status_code=status.HTTP_204_NO_CONTENT, summary="Delete a Ticket Link")
async def delete(
    id: UUID, service: TicketLinkService = Depends(get_service), _: Principal = Depends(require_write)
) -> Response:
    await service.delete(id)
    return Response(status_code=status.HTTP_204_NO_CONTENT)


@router.post("/bulk", response_model=TicketLinkBulkResult, summary="Create many Ticket Link records")
async def bulk_create(
    payloads: list[TicketLinkCreate],
    service: TicketLinkService = Depends(get_service),
    _: Principal = Depends(require_write),
) -> TicketLinkBulkResult:
    return await service.bulk_create(payloads)


@router.post("/reconcile", response_model=list[ReconciliationItem], summary="Reconcile external Ticket Link records")
async def reconcile(
    records: list[dict[str, object]],
    apply: bool = Query(False, description="Apply the plan instead of a dry run"),
    service: TicketLinkService = Depends(get_service),
    _: Principal = Depends(require_write),
) -> list[ReconciliationItem]:
    return await service.reconcile(records, apply)


@router.get("/by-alarm-id/{alarm_id}", response_model=Page[TicketLinkRead], summary="List Ticket Link records by Alarm Id")
async def list_by_alarm_id(
    alarm_id: UUID,
    page: int = Query(0, ge=0),
    size: int = Query(20, ge=1, le=200),
    service: TicketLinkService = Depends(get_service),
) -> Page[TicketLinkRead]:
    return await service.list_by_alarm_id(alarm_id, page, size)
