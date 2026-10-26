"""HTTP routes for Event Source."""
from __future__ import annotations

from uuid import UUID

from fastapi import APIRouter, Depends, Query, Request, Response, status
from fastapi.responses import PlainTextResponse

from itta_core.auth import Principal, require_read, require_write
from itta_core.dependencies import service_provider
from itta_core.diffing import ReconciliationItem
from itta_core.models import EntityMeta, LifecycleStatus
from itta_core.pagination import Page
from itta_api.domains.alarm.event_source.filters import EventSourceFilter
from itta_api.domains.alarm.event_source.meta import META
from itta_api.domains.alarm.event_source.schemas import (
    EventSourceBulkResult,
    EventSourceCreate,
    EventSourceRead,
    EventSourceSummary,
    EventSourceUpdate,
)
from itta_api.domains.alarm.event_source.service import EventSourceService

router = APIRouter(prefix="/api/v1/event-sources", tags=["Event Source"])
get_service = service_provider("event_source")


@router.get("/meta", response_model=EntityMeta, summary="Field catalogue for Event Source")
async def meta() -> EntityMeta:
    return META


@router.get("", response_model=Page[EventSourceRead], summary="Search Event Source records")
async def search(
    flt: EventSourceFilter = Depends(),
    page: int = Query(0, ge=0),
    size: int = Query(20, ge=1, le=200),
    sort: str | None = Query(None, description="field,asc|desc"),
    service: EventSourceService = Depends(get_service),
    _: Principal = Depends(require_read),
) -> Page[EventSourceRead]:
    return await service.search(flt, page, size, sort)


@router.get("/summaries", response_model=list[EventSourceSummary], summary="Pick-list of Event Source records")
async def summaries(
    status_filter: LifecycleStatus | None = Query(None, alias="status"),
    service: EventSourceService = Depends(get_service),
    _: Principal = Depends(require_read),
) -> list[EventSourceSummary]:
    return await service.summaries(status_filter)


@router.get("/stats/status", response_model=dict[str, int], summary="Counts per lifecycle status")
async def status_stats(
    service: EventSourceService = Depends(get_service), _: Principal = Depends(require_read)
) -> dict[str, int]:
    return await service.stats()


@router.get("/export", response_class=PlainTextResponse, summary="Export Event Source records as CSV")
async def export(
    flt: EventSourceFilter = Depends(),
    service: EventSourceService = Depends(get_service),
    _: Principal = Depends(require_read),
) -> PlainTextResponse:
    body = await service.export_csv(flt)
    headers = {"Content-Disposition": 'attachment; filename="event-sources.csv"'}
    return PlainTextResponse(body, media_type="text/csv", headers=headers)


@router.get("/by-code/{code}", response_model=EventSourceRead, summary="Get a Event Source by business code")
async def get_by_code(
    code: str, service: EventSourceService = Depends(get_service), _: Principal = Depends(require_read)
) -> EventSourceRead:
    return await service.get_by_code(code)


@router.head("/by-code/{code}", summary="Check whether a Event Source code exists")
async def exists(
    code: str, service: EventSourceService = Depends(get_service), _: Principal = Depends(require_read)
) -> Response:
    found = await service.exists(code)
    return Response(status_code=status.HTTP_200_OK if found else status.HTTP_404_NOT_FOUND)


@router.get("/{id}", response_model=EventSourceRead, summary="Get a Event Source by id")
async def get(
    id: UUID, service: EventSourceService = Depends(get_service), _: Principal = Depends(require_read)
) -> EventSourceRead:
    return await service.get(id)


@router.post("", response_model=EventSourceRead, status_code=status.HTTP_201_CREATED, summary="Create a Event Source")
async def create(
    payload: EventSourceCreate,
    request: Request,
    response: Response,
    service: EventSourceService = Depends(get_service),
    _: Principal = Depends(require_write),
) -> EventSourceRead:
    created = await service.create(payload)
    response.headers["Location"] = f"{request.url.path}/{created.id}"
    return created


@router.put("/{id}", response_model=EventSourceRead, summary="Update a Event Source")
async def update(
    id: UUID,
    payload: EventSourceUpdate,
    service: EventSourceService = Depends(get_service),
    _: Principal = Depends(require_write),
) -> EventSourceRead:
    return await service.update(id, payload)


@router.patch("/{id}/status", response_model=EventSourceRead, summary="Change lifecycle status")
async def change_status(
    id: UUID,
    new_status: LifecycleStatus = Query(..., alias="status"),
    service: EventSourceService = Depends(get_service),
    _: Principal = Depends(require_write),
) -> EventSourceRead:
    return await service.change_status(id, new_status)


@router.delete("/{id}", status_code=status.HTTP_204_NO_CONTENT, summary="Delete a Event Source")
async def delete(
    id: UUID, service: EventSourceService = Depends(get_service), _: Principal = Depends(require_write)
) -> Response:
    await service.delete(id)
    return Response(status_code=status.HTTP_204_NO_CONTENT)


@router.post("/bulk", response_model=EventSourceBulkResult, summary="Create many Event Source records")
async def bulk_create(
    payloads: list[EventSourceCreate],
    service: EventSourceService = Depends(get_service),
    _: Principal = Depends(require_write),
) -> EventSourceBulkResult:
    return await service.bulk_create(payloads)


@router.post("/reconcile", response_model=list[ReconciliationItem], summary="Reconcile external Event Source records")
async def reconcile(
    records: list[dict[str, object]],
    apply: bool = Query(False, description="Apply the plan instead of a dry run"),
    service: EventSourceService = Depends(get_service),
    _: Principal = Depends(require_write),
) -> list[ReconciliationItem]:
    return await service.reconcile(records, apply)

