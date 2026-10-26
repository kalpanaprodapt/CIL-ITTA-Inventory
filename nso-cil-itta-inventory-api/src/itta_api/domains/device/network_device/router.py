"""HTTP routes for Network Device."""
from __future__ import annotations

from uuid import UUID

from fastapi import APIRouter, Depends, Query, Request, Response, status
from fastapi.responses import PlainTextResponse

from itta_core.auth import Principal, require_read, require_write
from itta_core.dependencies import service_provider
from itta_core.diffing import ReconciliationItem
from itta_core.models import EntityMeta, LifecycleStatus
from itta_core.pagination import Page
from itta_api.domains.device.network_device.filters import NetworkDeviceFilter
from itta_api.domains.device.network_device.meta import META
from itta_api.domains.device.network_device.schemas import (
    NetworkDeviceBulkResult,
    NetworkDeviceCreate,
    NetworkDeviceRead,
    NetworkDeviceSummary,
    NetworkDeviceUpdate,
)
from itta_api.domains.device.network_device.service import NetworkDeviceService

router = APIRouter(prefix="/api/v1/network-devices", tags=["Network Device"])
get_service = service_provider("network_device")


@router.get("/meta", response_model=EntityMeta, summary="Field catalogue for Network Device")
async def meta() -> EntityMeta:
    return META


@router.get("", response_model=Page[NetworkDeviceRead], summary="Search Network Device records")
async def search(
    flt: NetworkDeviceFilter = Depends(),
    page: int = Query(0, ge=0),
    size: int = Query(20, ge=1, le=200),
    sort: str | None = Query(None, description="field,asc|desc"),
    service: NetworkDeviceService = Depends(get_service),
    _: Principal = Depends(require_read),
) -> Page[NetworkDeviceRead]:
    return await service.search(flt, page, size, sort)


@router.get("/summaries", response_model=list[NetworkDeviceSummary], summary="Pick-list of Network Device records")
async def summaries(
    status_filter: LifecycleStatus | None = Query(None, alias="status"),
    service: NetworkDeviceService = Depends(get_service),
    _: Principal = Depends(require_read),
) -> list[NetworkDeviceSummary]:
    return await service.summaries(status_filter)


@router.get("/stats/status", response_model=dict[str, int], summary="Counts per lifecycle status")
async def status_stats(
    service: NetworkDeviceService = Depends(get_service), _: Principal = Depends(require_read)
) -> dict[str, int]:
    return await service.stats()


@router.get("/export", response_class=PlainTextResponse, summary="Export Network Device records as CSV")
async def export(
    flt: NetworkDeviceFilter = Depends(),
    service: NetworkDeviceService = Depends(get_service),
    _: Principal = Depends(require_read),
) -> PlainTextResponse:
    body = await service.export_csv(flt)
    headers = {"Content-Disposition": 'attachment; filename="network-devices.csv"'}
    return PlainTextResponse(body, media_type="text/csv", headers=headers)


@router.get("/by-code/{code}", response_model=NetworkDeviceRead, summary="Get a Network Device by business code")
async def get_by_code(
    code: str, service: NetworkDeviceService = Depends(get_service), _: Principal = Depends(require_read)
) -> NetworkDeviceRead:
    return await service.get_by_code(code)


@router.head("/by-code/{code}", summary="Check whether a Network Device code exists")
async def exists(
    code: str, service: NetworkDeviceService = Depends(get_service), _: Principal = Depends(require_read)
) -> Response:
    found = await service.exists(code)
    return Response(status_code=status.HTTP_200_OK if found else status.HTTP_404_NOT_FOUND)


@router.get("/{id}", response_model=NetworkDeviceRead, summary="Get a Network Device by id")
async def get(
    id: UUID, service: NetworkDeviceService = Depends(get_service), _: Principal = Depends(require_read)
) -> NetworkDeviceRead:
    return await service.get(id)


@router.post("", response_model=NetworkDeviceRead, status_code=status.HTTP_201_CREATED, summary="Create a Network Device")
async def create(
    payload: NetworkDeviceCreate,
    request: Request,
    response: Response,
    service: NetworkDeviceService = Depends(get_service),
    _: Principal = Depends(require_write),
) -> NetworkDeviceRead:
    created = await service.create(payload)
    response.headers["Location"] = f"{request.url.path}/{created.id}"
    return created


@router.put("/{id}", response_model=NetworkDeviceRead, summary="Update a Network Device")
async def update(
    id: UUID,
    payload: NetworkDeviceUpdate,
    service: NetworkDeviceService = Depends(get_service),
    _: Principal = Depends(require_write),
) -> NetworkDeviceRead:
    return await service.update(id, payload)


@router.patch("/{id}/status", response_model=NetworkDeviceRead, summary="Change lifecycle status")
async def change_status(
    id: UUID,
    new_status: LifecycleStatus = Query(..., alias="status"),
    service: NetworkDeviceService = Depends(get_service),
    _: Principal = Depends(require_write),
) -> NetworkDeviceRead:
    return await service.change_status(id, new_status)


@router.delete("/{id}", status_code=status.HTTP_204_NO_CONTENT, summary="Delete a Network Device")
async def delete(
    id: UUID, service: NetworkDeviceService = Depends(get_service), _: Principal = Depends(require_write)
) -> Response:
    await service.delete(id)
    return Response(status_code=status.HTTP_204_NO_CONTENT)


@router.post("/bulk", response_model=NetworkDeviceBulkResult, summary="Create many Network Device records")
async def bulk_create(
    payloads: list[NetworkDeviceCreate],
    service: NetworkDeviceService = Depends(get_service),
    _: Principal = Depends(require_write),
) -> NetworkDeviceBulkResult:
    return await service.bulk_create(payloads)


@router.post("/reconcile", response_model=list[ReconciliationItem], summary="Reconcile external Network Device records")
async def reconcile(
    records: list[dict[str, object]],
    apply: bool = Query(False, description="Apply the plan instead of a dry run"),
    service: NetworkDeviceService = Depends(get_service),
    _: Principal = Depends(require_write),
) -> list[ReconciliationItem]:
    return await service.reconcile(records, apply)


@router.get("/by-model-id/{model_id}", response_model=Page[NetworkDeviceRead], summary="List Network Device records by Model Id")
async def list_by_model_id(
    model_id: UUID,
    page: int = Query(0, ge=0),
    size: int = Query(20, ge=1, le=200),
    service: NetworkDeviceService = Depends(get_service),
) -> Page[NetworkDeviceRead]:
    return await service.list_by_model_id(model_id, page, size)

@router.get("/by-rack-unit-id/{rack_unit_id}", response_model=Page[NetworkDeviceRead], summary="List Network Device records by Rack Unit Id")
async def list_by_rack_unit_id(
    rack_unit_id: UUID,
    page: int = Query(0, ge=0),
    size: int = Query(20, ge=1, le=200),
    service: NetworkDeviceService = Depends(get_service),
) -> Page[NetworkDeviceRead]:
    return await service.list_by_rack_unit_id(rack_unit_id, page, size)
