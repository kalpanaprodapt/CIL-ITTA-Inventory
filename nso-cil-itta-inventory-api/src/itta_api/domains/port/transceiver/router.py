"""HTTP routes for Transceiver."""
from __future__ import annotations

from uuid import UUID

from fastapi import APIRouter, Depends, Query, Request, Response, status
from fastapi.responses import PlainTextResponse

from itta_core.auth import Principal, require_read, require_write
from itta_core.dependencies import service_provider
from itta_core.diffing import ReconciliationItem
from itta_core.models import EntityMeta, LifecycleStatus
from itta_core.pagination import Page
from itta_api.domains.port.transceiver.filters import TransceiverFilter
from itta_api.domains.port.transceiver.meta import META
from itta_api.domains.port.transceiver.schemas import (
    TransceiverBulkResult,
    TransceiverCreate,
    TransceiverRead,
    TransceiverSummary,
    TransceiverUpdate,
)
from itta_api.domains.port.transceiver.service import TransceiverService

router = APIRouter(prefix="/api/v1/transceivers", tags=["Transceiver"])
get_service = service_provider("transceiver")


@router.get("/meta", response_model=EntityMeta, summary="Field catalogue for Transceiver")
async def meta() -> EntityMeta:
    return META


@router.get("", response_model=Page[TransceiverRead], summary="Search Transceiver records")
async def search(
    flt: TransceiverFilter = Depends(),
    page: int = Query(0, ge=0),
    size: int = Query(20, ge=1, le=200),
    sort: str | None = Query(None, description="field,asc|desc"),
    service: TransceiverService = Depends(get_service),
    _: Principal = Depends(require_read),
) -> Page[TransceiverRead]:
    return await service.search(flt, page, size, sort)


@router.get("/summaries", response_model=list[TransceiverSummary], summary="Pick-list of Transceiver records")
async def summaries(
    status_filter: LifecycleStatus | None = Query(None, alias="status"),
    service: TransceiverService = Depends(get_service),
    _: Principal = Depends(require_read),
) -> list[TransceiverSummary]:
    return await service.summaries(status_filter)


@router.get("/stats/status", response_model=dict[str, int], summary="Counts per lifecycle status")
async def status_stats(
    service: TransceiverService = Depends(get_service), _: Principal = Depends(require_read)
) -> dict[str, int]:
    return await service.stats()


@router.get("/export", response_class=PlainTextResponse, summary="Export Transceiver records as CSV")
async def export(
    flt: TransceiverFilter = Depends(),
    service: TransceiverService = Depends(get_service),
    _: Principal = Depends(require_read),
) -> PlainTextResponse:
    body = await service.export_csv(flt)
    headers = {"Content-Disposition": 'attachment; filename="transceivers.csv"'}
    return PlainTextResponse(body, media_type="text/csv", headers=headers)


@router.get("/by-code/{code}", response_model=TransceiverRead, summary="Get a Transceiver by business code")
async def get_by_code(
    code: str, service: TransceiverService = Depends(get_service), _: Principal = Depends(require_read)
) -> TransceiverRead:
    return await service.get_by_code(code)


@router.head("/by-code/{code}", summary="Check whether a Transceiver code exists")
async def exists(
    code: str, service: TransceiverService = Depends(get_service), _: Principal = Depends(require_read)
) -> Response:
    found = await service.exists(code)
    return Response(status_code=status.HTTP_200_OK if found else status.HTTP_404_NOT_FOUND)


@router.get("/{id}", response_model=TransceiverRead, summary="Get a Transceiver by id")
async def get(
    id: UUID, service: TransceiverService = Depends(get_service), _: Principal = Depends(require_read)
) -> TransceiverRead:
    return await service.get(id)


@router.post("", response_model=TransceiverRead, status_code=status.HTTP_201_CREATED, summary="Create a Transceiver")
async def create(
    payload: TransceiverCreate,
    request: Request,
    response: Response,
    service: TransceiverService = Depends(get_service),
    _: Principal = Depends(require_write),
) -> TransceiverRead:
    created = await service.create(payload)
    response.headers["Location"] = f"{request.url.path}/{created.id}"
    return created


@router.put("/{id}", response_model=TransceiverRead, summary="Update a Transceiver")
async def update(
    id: UUID,
    payload: TransceiverUpdate,
    service: TransceiverService = Depends(get_service),
    _: Principal = Depends(require_write),
) -> TransceiverRead:
    return await service.update(id, payload)


@router.patch("/{id}/status", response_model=TransceiverRead, summary="Change lifecycle status")
async def change_status(
    id: UUID,
    new_status: LifecycleStatus = Query(..., alias="status"),
    service: TransceiverService = Depends(get_service),
    _: Principal = Depends(require_write),
) -> TransceiverRead:
    return await service.change_status(id, new_status)


@router.delete("/{id}", status_code=status.HTTP_204_NO_CONTENT, summary="Delete a Transceiver")
async def delete(
    id: UUID, service: TransceiverService = Depends(get_service), _: Principal = Depends(require_write)
) -> Response:
    await service.delete(id)
    return Response(status_code=status.HTTP_204_NO_CONTENT)


@router.post("/bulk", response_model=TransceiverBulkResult, summary="Create many Transceiver records")
async def bulk_create(
    payloads: list[TransceiverCreate],
    service: TransceiverService = Depends(get_service),
    _: Principal = Depends(require_write),
) -> TransceiverBulkResult:
    return await service.bulk_create(payloads)


@router.post("/reconcile", response_model=list[ReconciliationItem], summary="Reconcile external Transceiver records")
async def reconcile(
    records: list[dict[str, object]],
    apply: bool = Query(False, description="Apply the plan instead of a dry run"),
    service: TransceiverService = Depends(get_service),
    _: Principal = Depends(require_write),
) -> list[ReconciliationItem]:
    return await service.reconcile(records, apply)


@router.get("/by-port-id/{port_id}", response_model=Page[TransceiverRead], summary="List Transceiver records by Port Id")
async def list_by_port_id(
    port_id: UUID,
    page: int = Query(0, ge=0),
    size: int = Query(20, ge=1, le=200),
    service: TransceiverService = Depends(get_service),
) -> Page[TransceiverRead]:
    return await service.list_by_port_id(port_id, page, size)
