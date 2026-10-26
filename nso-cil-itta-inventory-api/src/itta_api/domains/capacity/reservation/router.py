"""HTTP routes for Reservation."""
from __future__ import annotations

from uuid import UUID

from fastapi import APIRouter, Depends, Query, Request, Response, status
from fastapi.responses import PlainTextResponse

from itta_core.auth import Principal, require_read, require_write
from itta_core.dependencies import service_provider
from itta_core.diffing import ReconciliationItem
from itta_core.models import EntityMeta, LifecycleStatus
from itta_core.pagination import Page
from itta_api.domains.capacity.reservation.filters import ReservationFilter
from itta_api.domains.capacity.reservation.meta import META
from itta_api.domains.capacity.reservation.schemas import (
    ReservationBulkResult,
    ReservationCreate,
    ReservationRead,
    ReservationSummary,
    ReservationUpdate,
)
from itta_api.domains.capacity.reservation.service import ReservationService

router = APIRouter(prefix="/api/v1/reservations", tags=["Reservation"])
get_service = service_provider("reservation")


@router.get("/meta", response_model=EntityMeta, summary="Field catalogue for Reservation")
async def meta() -> EntityMeta:
    return META


@router.get("", response_model=Page[ReservationRead], summary="Search Reservation records")
async def search(
    flt: ReservationFilter = Depends(),
    page: int = Query(0, ge=0),
    size: int = Query(20, ge=1, le=200),
    sort: str | None = Query(None, description="field,asc|desc"),
    service: ReservationService = Depends(get_service),
    _: Principal = Depends(require_read),
) -> Page[ReservationRead]:
    return await service.search(flt, page, size, sort)


@router.get("/summaries", response_model=list[ReservationSummary], summary="Pick-list of Reservation records")
async def summaries(
    status_filter: LifecycleStatus | None = Query(None, alias="status"),
    service: ReservationService = Depends(get_service),
    _: Principal = Depends(require_read),
) -> list[ReservationSummary]:
    return await service.summaries(status_filter)


@router.get("/stats/status", response_model=dict[str, int], summary="Counts per lifecycle status")
async def status_stats(
    service: ReservationService = Depends(get_service), _: Principal = Depends(require_read)
) -> dict[str, int]:
    return await service.stats()


@router.get("/export", response_class=PlainTextResponse, summary="Export Reservation records as CSV")
async def export(
    flt: ReservationFilter = Depends(),
    service: ReservationService = Depends(get_service),
    _: Principal = Depends(require_read),
) -> PlainTextResponse:
    body = await service.export_csv(flt)
    headers = {"Content-Disposition": 'attachment; filename="reservations.csv"'}
    return PlainTextResponse(body, media_type="text/csv", headers=headers)


@router.get("/by-code/{code}", response_model=ReservationRead, summary="Get a Reservation by business code")
async def get_by_code(
    code: str, service: ReservationService = Depends(get_service), _: Principal = Depends(require_read)
) -> ReservationRead:
    return await service.get_by_code(code)


@router.head("/by-code/{code}", summary="Check whether a Reservation code exists")
async def exists(
    code: str, service: ReservationService = Depends(get_service), _: Principal = Depends(require_read)
) -> Response:
    found = await service.exists(code)
    return Response(status_code=status.HTTP_200_OK if found else status.HTTP_404_NOT_FOUND)


@router.get("/{id}", response_model=ReservationRead, summary="Get a Reservation by id")
async def get(
    id: UUID, service: ReservationService = Depends(get_service), _: Principal = Depends(require_read)
) -> ReservationRead:
    return await service.get(id)


@router.post("", response_model=ReservationRead, status_code=status.HTTP_201_CREATED, summary="Create a Reservation")
async def create(
    payload: ReservationCreate,
    request: Request,
    response: Response,
    service: ReservationService = Depends(get_service),
    _: Principal = Depends(require_write),
) -> ReservationRead:
    created = await service.create(payload)
    response.headers["Location"] = f"{request.url.path}/{created.id}"
    return created


@router.put("/{id}", response_model=ReservationRead, summary="Update a Reservation")
async def update(
    id: UUID,
    payload: ReservationUpdate,
    service: ReservationService = Depends(get_service),
    _: Principal = Depends(require_write),
) -> ReservationRead:
    return await service.update(id, payload)


@router.patch("/{id}/status", response_model=ReservationRead, summary="Change lifecycle status")
async def change_status(
    id: UUID,
    new_status: LifecycleStatus = Query(..., alias="status"),
    service: ReservationService = Depends(get_service),
    _: Principal = Depends(require_write),
) -> ReservationRead:
    return await service.change_status(id, new_status)


@router.delete("/{id}", status_code=status.HTTP_204_NO_CONTENT, summary="Delete a Reservation")
async def delete(
    id: UUID, service: ReservationService = Depends(get_service), _: Principal = Depends(require_write)
) -> Response:
    await service.delete(id)
    return Response(status_code=status.HTTP_204_NO_CONTENT)


@router.post("/bulk", response_model=ReservationBulkResult, summary="Create many Reservation records")
async def bulk_create(
    payloads: list[ReservationCreate],
    service: ReservationService = Depends(get_service),
    _: Principal = Depends(require_write),
) -> ReservationBulkResult:
    return await service.bulk_create(payloads)


@router.post("/reconcile", response_model=list[ReconciliationItem], summary="Reconcile external Reservation records")
async def reconcile(
    records: list[dict[str, object]],
    apply: bool = Query(False, description="Apply the plan instead of a dry run"),
    service: ReservationService = Depends(get_service),
    _: Principal = Depends(require_write),
) -> list[ReconciliationItem]:
    return await service.reconcile(records, apply)

