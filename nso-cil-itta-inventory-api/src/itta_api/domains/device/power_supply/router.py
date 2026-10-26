"""HTTP routes for Power Supply."""
from __future__ import annotations

from uuid import UUID

from fastapi import APIRouter, Depends, Query, Request, Response, status
from fastapi.responses import PlainTextResponse

from itta_core.auth import Principal, require_read, require_write
from itta_core.dependencies import service_provider
from itta_core.diffing import ReconciliationItem
from itta_core.models import EntityMeta, LifecycleStatus
from itta_core.pagination import Page
from itta_api.domains.device.power_supply.filters import PowerSupplyFilter
from itta_api.domains.device.power_supply.meta import META
from itta_api.domains.device.power_supply.schemas import (
    PowerSupplyBulkResult,
    PowerSupplyCreate,
    PowerSupplyRead,
    PowerSupplySummary,
    PowerSupplyUpdate,
)
from itta_api.domains.device.power_supply.service import PowerSupplyService

router = APIRouter(prefix="/api/v1/power-supplies", tags=["Power Supply"])
get_service = service_provider("power_supply")


@router.get("/meta", response_model=EntityMeta, summary="Field catalogue for Power Supply")
async def meta() -> EntityMeta:
    return META


@router.get("", response_model=Page[PowerSupplyRead], summary="Search Power Supply records")
async def search(
    flt: PowerSupplyFilter = Depends(),
    page: int = Query(0, ge=0),
    size: int = Query(20, ge=1, le=200),
    sort: str | None = Query(None, description="field,asc|desc"),
    service: PowerSupplyService = Depends(get_service),
    _: Principal = Depends(require_read),
) -> Page[PowerSupplyRead]:
    return await service.search(flt, page, size, sort)


@router.get("/summaries", response_model=list[PowerSupplySummary], summary="Pick-list of Power Supply records")
async def summaries(
    status_filter: LifecycleStatus | None = Query(None, alias="status"),
    service: PowerSupplyService = Depends(get_service),
    _: Principal = Depends(require_read),
) -> list[PowerSupplySummary]:
    return await service.summaries(status_filter)


@router.get("/stats/status", response_model=dict[str, int], summary="Counts per lifecycle status")
async def status_stats(
    service: PowerSupplyService = Depends(get_service), _: Principal = Depends(require_read)
) -> dict[str, int]:
    return await service.stats()


@router.get("/export", response_class=PlainTextResponse, summary="Export Power Supply records as CSV")
async def export(
    flt: PowerSupplyFilter = Depends(),
    service: PowerSupplyService = Depends(get_service),
    _: Principal = Depends(require_read),
) -> PlainTextResponse:
    body = await service.export_csv(flt)
    headers = {"Content-Disposition": 'attachment; filename="power-supplies.csv"'}
    return PlainTextResponse(body, media_type="text/csv", headers=headers)


@router.get("/by-code/{code}", response_model=PowerSupplyRead, summary="Get a Power Supply by business code")
async def get_by_code(
    code: str, service: PowerSupplyService = Depends(get_service), _: Principal = Depends(require_read)
) -> PowerSupplyRead:
    return await service.get_by_code(code)


@router.head("/by-code/{code}", summary="Check whether a Power Supply code exists")
async def exists(
    code: str, service: PowerSupplyService = Depends(get_service), _: Principal = Depends(require_read)
) -> Response:
    found = await service.exists(code)
    return Response(status_code=status.HTTP_200_OK if found else status.HTTP_404_NOT_FOUND)


@router.get("/{id}", response_model=PowerSupplyRead, summary="Get a Power Supply by id")
async def get(
    id: UUID, service: PowerSupplyService = Depends(get_service), _: Principal = Depends(require_read)
) -> PowerSupplyRead:
    return await service.get(id)


@router.post("", response_model=PowerSupplyRead, status_code=status.HTTP_201_CREATED, summary="Create a Power Supply")
async def create(
    payload: PowerSupplyCreate,
    request: Request,
    response: Response,
    service: PowerSupplyService = Depends(get_service),
    _: Principal = Depends(require_write),
) -> PowerSupplyRead:
    created = await service.create(payload)
    response.headers["Location"] = f"{request.url.path}/{created.id}"
    return created


@router.put("/{id}", response_model=PowerSupplyRead, summary="Update a Power Supply")
async def update(
    id: UUID,
    payload: PowerSupplyUpdate,
    service: PowerSupplyService = Depends(get_service),
    _: Principal = Depends(require_write),
) -> PowerSupplyRead:
    return await service.update(id, payload)


@router.patch("/{id}/status", response_model=PowerSupplyRead, summary="Change lifecycle status")
async def change_status(
    id: UUID,
    new_status: LifecycleStatus = Query(..., alias="status"),
    service: PowerSupplyService = Depends(get_service),
    _: Principal = Depends(require_write),
) -> PowerSupplyRead:
    return await service.change_status(id, new_status)


@router.delete("/{id}", status_code=status.HTTP_204_NO_CONTENT, summary="Delete a Power Supply")
async def delete(
    id: UUID, service: PowerSupplyService = Depends(get_service), _: Principal = Depends(require_write)
) -> Response:
    await service.delete(id)
    return Response(status_code=status.HTTP_204_NO_CONTENT)


@router.post("/bulk", response_model=PowerSupplyBulkResult, summary="Create many Power Supply records")
async def bulk_create(
    payloads: list[PowerSupplyCreate],
    service: PowerSupplyService = Depends(get_service),
    _: Principal = Depends(require_write),
) -> PowerSupplyBulkResult:
    return await service.bulk_create(payloads)


@router.post("/reconcile", response_model=list[ReconciliationItem], summary="Reconcile external Power Supply records")
async def reconcile(
    records: list[dict[str, object]],
    apply: bool = Query(False, description="Apply the plan instead of a dry run"),
    service: PowerSupplyService = Depends(get_service),
    _: Principal = Depends(require_write),
) -> list[ReconciliationItem]:
    return await service.reconcile(records, apply)


@router.get("/by-chassis-id/{chassis_id}", response_model=Page[PowerSupplyRead], summary="List Power Supply records by Chassis Id")
async def list_by_chassis_id(
    chassis_id: UUID,
    page: int = Query(0, ge=0),
    size: int = Query(20, ge=1, le=200),
    service: PowerSupplyService = Depends(get_service),
) -> Page[PowerSupplyRead]:
    return await service.list_by_chassis_id(chassis_id, page, size)

@router.get("/by-feed-id/{feed_id}", response_model=Page[PowerSupplyRead], summary="List Power Supply records by Feed Id")
async def list_by_feed_id(
    feed_id: UUID,
    page: int = Query(0, ge=0),
    size: int = Query(20, ge=1, le=200),
    service: PowerSupplyService = Depends(get_service),
) -> Page[PowerSupplyRead]:
    return await service.list_by_feed_id(feed_id, page, size)
