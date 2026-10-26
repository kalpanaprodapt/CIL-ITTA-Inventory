"""HTTP routes for Rack Unit."""
from __future__ import annotations

from uuid import UUID

from fastapi import APIRouter, Depends, Query, Request, Response, status
from fastapi.responses import PlainTextResponse

from itta_core.auth import Principal, require_read, require_write
from itta_core.dependencies import service_provider
from itta_core.diffing import ReconciliationItem
from itta_core.models import EntityMeta, LifecycleStatus
from itta_core.pagination import Page
from itta_api.domains.facility.rack_unit.filters import RackUnitFilter
from itta_api.domains.facility.rack_unit.meta import META
from itta_api.domains.facility.rack_unit.schemas import (
    RackUnitBulkResult,
    RackUnitCreate,
    RackUnitRead,
    RackUnitSummary,
    RackUnitUpdate,
)
from itta_api.domains.facility.rack_unit.service import RackUnitService

router = APIRouter(prefix="/api/v1/rack-units", tags=["Rack Unit"])
get_service = service_provider("rack_unit")


@router.get("/meta", response_model=EntityMeta, summary="Field catalogue for Rack Unit")
async def meta() -> EntityMeta:
    return META


@router.get("", response_model=Page[RackUnitRead], summary="Search Rack Unit records")
async def search(
    flt: RackUnitFilter = Depends(),
    page: int = Query(0, ge=0),
    size: int = Query(20, ge=1, le=200),
    sort: str | None = Query(None, description="field,asc|desc"),
    service: RackUnitService = Depends(get_service),
    _: Principal = Depends(require_read),
) -> Page[RackUnitRead]:
    return await service.search(flt, page, size, sort)


@router.get("/summaries", response_model=list[RackUnitSummary], summary="Pick-list of Rack Unit records")
async def summaries(
    status_filter: LifecycleStatus | None = Query(None, alias="status"),
    service: RackUnitService = Depends(get_service),
    _: Principal = Depends(require_read),
) -> list[RackUnitSummary]:
    return await service.summaries(status_filter)


@router.get("/stats/status", response_model=dict[str, int], summary="Counts per lifecycle status")
async def status_stats(
    service: RackUnitService = Depends(get_service), _: Principal = Depends(require_read)
) -> dict[str, int]:
    return await service.stats()


@router.get("/export", response_class=PlainTextResponse, summary="Export Rack Unit records as CSV")
async def export(
    flt: RackUnitFilter = Depends(),
    service: RackUnitService = Depends(get_service),
    _: Principal = Depends(require_read),
) -> PlainTextResponse:
    body = await service.export_csv(flt)
    headers = {"Content-Disposition": 'attachment; filename="rack-units.csv"'}
    return PlainTextResponse(body, media_type="text/csv", headers=headers)


@router.get("/by-code/{code}", response_model=RackUnitRead, summary="Get a Rack Unit by business code")
async def get_by_code(
    code: str, service: RackUnitService = Depends(get_service), _: Principal = Depends(require_read)
) -> RackUnitRead:
    return await service.get_by_code(code)


@router.head("/by-code/{code}", summary="Check whether a Rack Unit code exists")
async def exists(
    code: str, service: RackUnitService = Depends(get_service), _: Principal = Depends(require_read)
) -> Response:
    found = await service.exists(code)
    return Response(status_code=status.HTTP_200_OK if found else status.HTTP_404_NOT_FOUND)


@router.get("/{id}", response_model=RackUnitRead, summary="Get a Rack Unit by id")
async def get(
    id: UUID, service: RackUnitService = Depends(get_service), _: Principal = Depends(require_read)
) -> RackUnitRead:
    return await service.get(id)


@router.post("", response_model=RackUnitRead, status_code=status.HTTP_201_CREATED, summary="Create a Rack Unit")
async def create(
    payload: RackUnitCreate,
    request: Request,
    response: Response,
    service: RackUnitService = Depends(get_service),
    _: Principal = Depends(require_write),
) -> RackUnitRead:
    created = await service.create(payload)
    response.headers["Location"] = f"{request.url.path}/{created.id}"
    return created


@router.put("/{id}", response_model=RackUnitRead, summary="Update a Rack Unit")
async def update(
    id: UUID,
    payload: RackUnitUpdate,
    service: RackUnitService = Depends(get_service),
    _: Principal = Depends(require_write),
) -> RackUnitRead:
    return await service.update(id, payload)


@router.patch("/{id}/status", response_model=RackUnitRead, summary="Change lifecycle status")
async def change_status(
    id: UUID,
    new_status: LifecycleStatus = Query(..., alias="status"),
    service: RackUnitService = Depends(get_service),
    _: Principal = Depends(require_write),
) -> RackUnitRead:
    return await service.change_status(id, new_status)


@router.delete("/{id}", status_code=status.HTTP_204_NO_CONTENT, summary="Delete a Rack Unit")
async def delete(
    id: UUID, service: RackUnitService = Depends(get_service), _: Principal = Depends(require_write)
) -> Response:
    await service.delete(id)
    return Response(status_code=status.HTTP_204_NO_CONTENT)


@router.post("/bulk", response_model=RackUnitBulkResult, summary="Create many Rack Unit records")
async def bulk_create(
    payloads: list[RackUnitCreate],
    service: RackUnitService = Depends(get_service),
    _: Principal = Depends(require_write),
) -> RackUnitBulkResult:
    return await service.bulk_create(payloads)


@router.post("/reconcile", response_model=list[ReconciliationItem], summary="Reconcile external Rack Unit records")
async def reconcile(
    records: list[dict[str, object]],
    apply: bool = Query(False, description="Apply the plan instead of a dry run"),
    service: RackUnitService = Depends(get_service),
    _: Principal = Depends(require_write),
) -> list[ReconciliationItem]:
    return await service.reconcile(records, apply)


@router.get("/by-rack-id/{rack_id}", response_model=Page[RackUnitRead], summary="List Rack Unit records by Rack Id")
async def list_by_rack_id(
    rack_id: UUID,
    page: int = Query(0, ge=0),
    size: int = Query(20, ge=1, le=200),
    service: RackUnitService = Depends(get_service),
) -> Page[RackUnitRead]:
    return await service.list_by_rack_id(rack_id, page, size)

@router.get("/by-mounted-device-id/{mounted_device_id}", response_model=Page[RackUnitRead], summary="List Rack Unit records by Mounted Device Id")
async def list_by_mounted_device_id(
    mounted_device_id: UUID,
    page: int = Query(0, ge=0),
    size: int = Query(20, ge=1, le=200),
    service: RackUnitService = Depends(get_service),
) -> Page[RackUnitRead]:
    return await service.list_by_mounted_device_id(mounted_device_id, page, size)
