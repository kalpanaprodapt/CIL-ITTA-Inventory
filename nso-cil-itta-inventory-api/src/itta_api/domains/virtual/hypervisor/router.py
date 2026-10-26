"""HTTP routes for Hypervisor."""
from __future__ import annotations

from uuid import UUID

from fastapi import APIRouter, Depends, Query, Request, Response, status
from fastapi.responses import PlainTextResponse

from itta_core.auth import Principal, require_read, require_write
from itta_core.dependencies import service_provider
from itta_core.diffing import ReconciliationItem
from itta_core.models import EntityMeta, LifecycleStatus
from itta_core.pagination import Page
from itta_api.domains.virtual.hypervisor.filters import HypervisorFilter
from itta_api.domains.virtual.hypervisor.meta import META
from itta_api.domains.virtual.hypervisor.schemas import (
    HypervisorBulkResult,
    HypervisorCreate,
    HypervisorRead,
    HypervisorSummary,
    HypervisorUpdate,
)
from itta_api.domains.virtual.hypervisor.service import HypervisorService

router = APIRouter(prefix="/api/v1/hypervisors", tags=["Hypervisor"])
get_service = service_provider("hypervisor")


@router.get("/meta", response_model=EntityMeta, summary="Field catalogue for Hypervisor")
async def meta() -> EntityMeta:
    return META


@router.get("", response_model=Page[HypervisorRead], summary="Search Hypervisor records")
async def search(
    flt: HypervisorFilter = Depends(),
    page: int = Query(0, ge=0),
    size: int = Query(20, ge=1, le=200),
    sort: str | None = Query(None, description="field,asc|desc"),
    service: HypervisorService = Depends(get_service),
    _: Principal = Depends(require_read),
) -> Page[HypervisorRead]:
    return await service.search(flt, page, size, sort)


@router.get("/summaries", response_model=list[HypervisorSummary], summary="Pick-list of Hypervisor records")
async def summaries(
    status_filter: LifecycleStatus | None = Query(None, alias="status"),
    service: HypervisorService = Depends(get_service),
    _: Principal = Depends(require_read),
) -> list[HypervisorSummary]:
    return await service.summaries(status_filter)


@router.get("/stats/status", response_model=dict[str, int], summary="Counts per lifecycle status")
async def status_stats(
    service: HypervisorService = Depends(get_service), _: Principal = Depends(require_read)
) -> dict[str, int]:
    return await service.stats()


@router.get("/export", response_class=PlainTextResponse, summary="Export Hypervisor records as CSV")
async def export(
    flt: HypervisorFilter = Depends(),
    service: HypervisorService = Depends(get_service),
    _: Principal = Depends(require_read),
) -> PlainTextResponse:
    body = await service.export_csv(flt)
    headers = {"Content-Disposition": 'attachment; filename="hypervisors.csv"'}
    return PlainTextResponse(body, media_type="text/csv", headers=headers)


@router.get("/by-code/{code}", response_model=HypervisorRead, summary="Get a Hypervisor by business code")
async def get_by_code(
    code: str, service: HypervisorService = Depends(get_service), _: Principal = Depends(require_read)
) -> HypervisorRead:
    return await service.get_by_code(code)


@router.head("/by-code/{code}", summary="Check whether a Hypervisor code exists")
async def exists(
    code: str, service: HypervisorService = Depends(get_service), _: Principal = Depends(require_read)
) -> Response:
    found = await service.exists(code)
    return Response(status_code=status.HTTP_200_OK if found else status.HTTP_404_NOT_FOUND)


@router.get("/{id}", response_model=HypervisorRead, summary="Get a Hypervisor by id")
async def get(
    id: UUID, service: HypervisorService = Depends(get_service), _: Principal = Depends(require_read)
) -> HypervisorRead:
    return await service.get(id)


@router.post("", response_model=HypervisorRead, status_code=status.HTTP_201_CREATED, summary="Create a Hypervisor")
async def create(
    payload: HypervisorCreate,
    request: Request,
    response: Response,
    service: HypervisorService = Depends(get_service),
    _: Principal = Depends(require_write),
) -> HypervisorRead:
    created = await service.create(payload)
    response.headers["Location"] = f"{request.url.path}/{created.id}"
    return created


@router.put("/{id}", response_model=HypervisorRead, summary="Update a Hypervisor")
async def update(
    id: UUID,
    payload: HypervisorUpdate,
    service: HypervisorService = Depends(get_service),
    _: Principal = Depends(require_write),
) -> HypervisorRead:
    return await service.update(id, payload)


@router.patch("/{id}/status", response_model=HypervisorRead, summary="Change lifecycle status")
async def change_status(
    id: UUID,
    new_status: LifecycleStatus = Query(..., alias="status"),
    service: HypervisorService = Depends(get_service),
    _: Principal = Depends(require_write),
) -> HypervisorRead:
    return await service.change_status(id, new_status)


@router.delete("/{id}", status_code=status.HTTP_204_NO_CONTENT, summary="Delete a Hypervisor")
async def delete(
    id: UUID, service: HypervisorService = Depends(get_service), _: Principal = Depends(require_write)
) -> Response:
    await service.delete(id)
    return Response(status_code=status.HTTP_204_NO_CONTENT)


@router.post("/bulk", response_model=HypervisorBulkResult, summary="Create many Hypervisor records")
async def bulk_create(
    payloads: list[HypervisorCreate],
    service: HypervisorService = Depends(get_service),
    _: Principal = Depends(require_write),
) -> HypervisorBulkResult:
    return await service.bulk_create(payloads)


@router.post("/reconcile", response_model=list[ReconciliationItem], summary="Reconcile external Hypervisor records")
async def reconcile(
    records: list[dict[str, object]],
    apply: bool = Query(False, description="Apply the plan instead of a dry run"),
    service: HypervisorService = Depends(get_service),
    _: Principal = Depends(require_write),
) -> list[ReconciliationItem]:
    return await service.reconcile(records, apply)


@router.get("/by-host-device-id/{host_device_id}", response_model=Page[HypervisorRead], summary="List Hypervisor records by Host Device Id")
async def list_by_host_device_id(
    host_device_id: UUID,
    page: int = Query(0, ge=0),
    size: int = Query(20, ge=1, le=200),
    service: HypervisorService = Depends(get_service),
) -> Page[HypervisorRead]:
    return await service.list_by_host_device_id(host_device_id, page, size)
