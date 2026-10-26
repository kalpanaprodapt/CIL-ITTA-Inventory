"""HTTP routes for Port."""
from __future__ import annotations

from uuid import UUID

from fastapi import APIRouter, Depends, Query, Request, Response, status
from fastapi.responses import PlainTextResponse

from itta_core.auth import Principal, require_read, require_write
from itta_core.dependencies import service_provider
from itta_core.diffing import ReconciliationItem
from itta_core.models import EntityMeta, LifecycleStatus
from itta_core.pagination import Page
from itta_api.domains.port.port.filters import PortFilter
from itta_api.domains.port.port.meta import META
from itta_api.domains.port.port.schemas import (
    PortBulkResult,
    PortCreate,
    PortRead,
    PortSummary,
    PortUpdate,
)
from itta_api.domains.port.port.service import PortService

router = APIRouter(prefix="/api/v1/ports", tags=["Port"])
get_service = service_provider("port")


@router.get("/meta", response_model=EntityMeta, summary="Field catalogue for Port")
async def meta() -> EntityMeta:
    return META


@router.get("", response_model=Page[PortRead], summary="Search Port records")
async def search(
    flt: PortFilter = Depends(),
    page: int = Query(0, ge=0),
    size: int = Query(20, ge=1, le=200),
    sort: str | None = Query(None, description="field,asc|desc"),
    service: PortService = Depends(get_service),
    _: Principal = Depends(require_read),
) -> Page[PortRead]:
    return await service.search(flt, page, size, sort)


@router.get("/summaries", response_model=list[PortSummary], summary="Pick-list of Port records")
async def summaries(
    status_filter: LifecycleStatus | None = Query(None, alias="status"),
    service: PortService = Depends(get_service),
    _: Principal = Depends(require_read),
) -> list[PortSummary]:
    return await service.summaries(status_filter)


@router.get("/stats/status", response_model=dict[str, int], summary="Counts per lifecycle status")
async def status_stats(
    service: PortService = Depends(get_service), _: Principal = Depends(require_read)
) -> dict[str, int]:
    return await service.stats()


@router.get("/export", response_class=PlainTextResponse, summary="Export Port records as CSV")
async def export(
    flt: PortFilter = Depends(),
    service: PortService = Depends(get_service),
    _: Principal = Depends(require_read),
) -> PlainTextResponse:
    body = await service.export_csv(flt)
    headers = {"Content-Disposition": 'attachment; filename="ports.csv"'}
    return PlainTextResponse(body, media_type="text/csv", headers=headers)


@router.get("/by-code/{code}", response_model=PortRead, summary="Get a Port by business code")
async def get_by_code(
    code: str, service: PortService = Depends(get_service), _: Principal = Depends(require_read)
) -> PortRead:
    return await service.get_by_code(code)


@router.head("/by-code/{code}", summary="Check whether a Port code exists")
async def exists(
    code: str, service: PortService = Depends(get_service), _: Principal = Depends(require_read)
) -> Response:
    found = await service.exists(code)
    return Response(status_code=status.HTTP_200_OK if found else status.HTTP_404_NOT_FOUND)


@router.get("/{id}", response_model=PortRead, summary="Get a Port by id")
async def get(
    id: UUID, service: PortService = Depends(get_service), _: Principal = Depends(require_read)
) -> PortRead:
    return await service.get(id)


@router.post("", response_model=PortRead, status_code=status.HTTP_201_CREATED, summary="Create a Port")
async def create(
    payload: PortCreate,
    request: Request,
    response: Response,
    service: PortService = Depends(get_service),
    _: Principal = Depends(require_write),
) -> PortRead:
    created = await service.create(payload)
    response.headers["Location"] = f"{request.url.path}/{created.id}"
    return created


@router.put("/{id}", response_model=PortRead, summary="Update a Port")
async def update(
    id: UUID,
    payload: PortUpdate,
    service: PortService = Depends(get_service),
    _: Principal = Depends(require_write),
) -> PortRead:
    return await service.update(id, payload)


@router.patch("/{id}/status", response_model=PortRead, summary="Change lifecycle status")
async def change_status(
    id: UUID,
    new_status: LifecycleStatus = Query(..., alias="status"),
    service: PortService = Depends(get_service),
    _: Principal = Depends(require_write),
) -> PortRead:
    return await service.change_status(id, new_status)


@router.delete("/{id}", status_code=status.HTTP_204_NO_CONTENT, summary="Delete a Port")
async def delete(
    id: UUID, service: PortService = Depends(get_service), _: Principal = Depends(require_write)
) -> Response:
    await service.delete(id)
    return Response(status_code=status.HTTP_204_NO_CONTENT)


@router.post("/bulk", response_model=PortBulkResult, summary="Create many Port records")
async def bulk_create(
    payloads: list[PortCreate],
    service: PortService = Depends(get_service),
    _: Principal = Depends(require_write),
) -> PortBulkResult:
    return await service.bulk_create(payloads)


@router.post("/reconcile", response_model=list[ReconciliationItem], summary="Reconcile external Port records")
async def reconcile(
    records: list[dict[str, object]],
    apply: bool = Query(False, description="Apply the plan instead of a dry run"),
    service: PortService = Depends(get_service),
    _: Principal = Depends(require_write),
) -> list[ReconciliationItem]:
    return await service.reconcile(records, apply)


@router.get("/by-device-id/{device_id}", response_model=Page[PortRead], summary="List Port records by Device Id")
async def list_by_device_id(
    device_id: UUID,
    page: int = Query(0, ge=0),
    size: int = Query(20, ge=1, le=200),
    service: PortService = Depends(get_service),
) -> Page[PortRead]:
    return await service.list_by_device_id(device_id, page, size)

@router.get("/by-card-id/{card_id}", response_model=Page[PortRead], summary="List Port records by Card Id")
async def list_by_card_id(
    card_id: UUID,
    page: int = Query(0, ge=0),
    size: int = Query(20, ge=1, le=200),
    service: PortService = Depends(get_service),
) -> Page[PortRead]:
    return await service.list_by_card_id(card_id, page, size)
