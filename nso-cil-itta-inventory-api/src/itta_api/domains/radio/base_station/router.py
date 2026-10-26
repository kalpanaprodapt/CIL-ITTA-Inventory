"""HTTP routes for Base Station."""
from __future__ import annotations

from uuid import UUID

from fastapi import APIRouter, Depends, Query, Request, Response, status
from fastapi.responses import PlainTextResponse

from itta_core.auth import Principal, require_read, require_write
from itta_core.dependencies import service_provider
from itta_core.diffing import ReconciliationItem
from itta_core.models import EntityMeta, LifecycleStatus
from itta_core.pagination import Page
from itta_api.domains.radio.base_station.filters import BaseStationFilter
from itta_api.domains.radio.base_station.meta import META
from itta_api.domains.radio.base_station.schemas import (
    BaseStationBulkResult,
    BaseStationCreate,
    BaseStationRead,
    BaseStationSummary,
    BaseStationUpdate,
)
from itta_api.domains.radio.base_station.service import BaseStationService

router = APIRouter(prefix="/api/v1/base-stations", tags=["Base Station"])
get_service = service_provider("base_station")


@router.get("/meta", response_model=EntityMeta, summary="Field catalogue for Base Station")
async def meta() -> EntityMeta:
    return META


@router.get("", response_model=Page[BaseStationRead], summary="Search Base Station records")
async def search(
    flt: BaseStationFilter = Depends(),
    page: int = Query(0, ge=0),
    size: int = Query(20, ge=1, le=200),
    sort: str | None = Query(None, description="field,asc|desc"),
    service: BaseStationService = Depends(get_service),
    _: Principal = Depends(require_read),
) -> Page[BaseStationRead]:
    return await service.search(flt, page, size, sort)


@router.get("/summaries", response_model=list[BaseStationSummary], summary="Pick-list of Base Station records")
async def summaries(
    status_filter: LifecycleStatus | None = Query(None, alias="status"),
    service: BaseStationService = Depends(get_service),
    _: Principal = Depends(require_read),
) -> list[BaseStationSummary]:
    return await service.summaries(status_filter)


@router.get("/stats/status", response_model=dict[str, int], summary="Counts per lifecycle status")
async def status_stats(
    service: BaseStationService = Depends(get_service), _: Principal = Depends(require_read)
) -> dict[str, int]:
    return await service.stats()


@router.get("/export", response_class=PlainTextResponse, summary="Export Base Station records as CSV")
async def export(
    flt: BaseStationFilter = Depends(),
    service: BaseStationService = Depends(get_service),
    _: Principal = Depends(require_read),
) -> PlainTextResponse:
    body = await service.export_csv(flt)
    headers = {"Content-Disposition": 'attachment; filename="base-stations.csv"'}
    return PlainTextResponse(body, media_type="text/csv", headers=headers)


@router.get("/by-code/{code}", response_model=BaseStationRead, summary="Get a Base Station by business code")
async def get_by_code(
    code: str, service: BaseStationService = Depends(get_service), _: Principal = Depends(require_read)
) -> BaseStationRead:
    return await service.get_by_code(code)


@router.head("/by-code/{code}", summary="Check whether a Base Station code exists")
async def exists(
    code: str, service: BaseStationService = Depends(get_service), _: Principal = Depends(require_read)
) -> Response:
    found = await service.exists(code)
    return Response(status_code=status.HTTP_200_OK if found else status.HTTP_404_NOT_FOUND)


@router.get("/{id}", response_model=BaseStationRead, summary="Get a Base Station by id")
async def get(
    id: UUID, service: BaseStationService = Depends(get_service), _: Principal = Depends(require_read)
) -> BaseStationRead:
    return await service.get(id)


@router.post("", response_model=BaseStationRead, status_code=status.HTTP_201_CREATED, summary="Create a Base Station")
async def create(
    payload: BaseStationCreate,
    request: Request,
    response: Response,
    service: BaseStationService = Depends(get_service),
    _: Principal = Depends(require_write),
) -> BaseStationRead:
    created = await service.create(payload)
    response.headers["Location"] = f"{request.url.path}/{created.id}"
    return created


@router.put("/{id}", response_model=BaseStationRead, summary="Update a Base Station")
async def update(
    id: UUID,
    payload: BaseStationUpdate,
    service: BaseStationService = Depends(get_service),
    _: Principal = Depends(require_write),
) -> BaseStationRead:
    return await service.update(id, payload)


@router.patch("/{id}/status", response_model=BaseStationRead, summary="Change lifecycle status")
async def change_status(
    id: UUID,
    new_status: LifecycleStatus = Query(..., alias="status"),
    service: BaseStationService = Depends(get_service),
    _: Principal = Depends(require_write),
) -> BaseStationRead:
    return await service.change_status(id, new_status)


@router.delete("/{id}", status_code=status.HTTP_204_NO_CONTENT, summary="Delete a Base Station")
async def delete(
    id: UUID, service: BaseStationService = Depends(get_service), _: Principal = Depends(require_write)
) -> Response:
    await service.delete(id)
    return Response(status_code=status.HTTP_204_NO_CONTENT)


@router.post("/bulk", response_model=BaseStationBulkResult, summary="Create many Base Station records")
async def bulk_create(
    payloads: list[BaseStationCreate],
    service: BaseStationService = Depends(get_service),
    _: Principal = Depends(require_write),
) -> BaseStationBulkResult:
    return await service.bulk_create(payloads)


@router.post("/reconcile", response_model=list[ReconciliationItem], summary="Reconcile external Base Station records")
async def reconcile(
    records: list[dict[str, object]],
    apply: bool = Query(False, description="Apply the plan instead of a dry run"),
    service: BaseStationService = Depends(get_service),
    _: Principal = Depends(require_write),
) -> list[ReconciliationItem]:
    return await service.reconcile(records, apply)


@router.get("/by-site-id/{site_id}", response_model=Page[BaseStationRead], summary="List Base Station records by Site Id")
async def list_by_site_id(
    site_id: UUID,
    page: int = Query(0, ge=0),
    size: int = Query(20, ge=1, le=200),
    service: BaseStationService = Depends(get_service),
) -> Page[BaseStationRead]:
    return await service.list_by_site_id(site_id, page, size)
