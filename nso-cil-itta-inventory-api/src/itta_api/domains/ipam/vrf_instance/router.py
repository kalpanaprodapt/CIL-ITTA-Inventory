"""HTTP routes for Vrf Instance."""
from __future__ import annotations

from uuid import UUID

from fastapi import APIRouter, Depends, Query, Request, Response, status
from fastapi.responses import PlainTextResponse

from itta_core.auth import Principal, require_read, require_write
from itta_core.dependencies import service_provider
from itta_core.diffing import ReconciliationItem
from itta_core.models import EntityMeta, LifecycleStatus
from itta_core.pagination import Page
from itta_api.domains.ipam.vrf_instance.filters import VrfInstanceFilter
from itta_api.domains.ipam.vrf_instance.meta import META
from itta_api.domains.ipam.vrf_instance.schemas import (
    VrfInstanceBulkResult,
    VrfInstanceCreate,
    VrfInstanceRead,
    VrfInstanceSummary,
    VrfInstanceUpdate,
)
from itta_api.domains.ipam.vrf_instance.service import VrfInstanceService

router = APIRouter(prefix="/api/v1/vrf-instances", tags=["Vrf Instance"])
get_service = service_provider("vrf_instance")


@router.get("/meta", response_model=EntityMeta, summary="Field catalogue for Vrf Instance")
async def meta() -> EntityMeta:
    return META


@router.get("", response_model=Page[VrfInstanceRead], summary="Search Vrf Instance records")
async def search(
    flt: VrfInstanceFilter = Depends(),
    page: int = Query(0, ge=0),
    size: int = Query(20, ge=1, le=200),
    sort: str | None = Query(None, description="field,asc|desc"),
    service: VrfInstanceService = Depends(get_service),
    _: Principal = Depends(require_read),
) -> Page[VrfInstanceRead]:
    return await service.search(flt, page, size, sort)


@router.get("/summaries", response_model=list[VrfInstanceSummary], summary="Pick-list of Vrf Instance records")
async def summaries(
    status_filter: LifecycleStatus | None = Query(None, alias="status"),
    service: VrfInstanceService = Depends(get_service),
    _: Principal = Depends(require_read),
) -> list[VrfInstanceSummary]:
    return await service.summaries(status_filter)


@router.get("/stats/status", response_model=dict[str, int], summary="Counts per lifecycle status")
async def status_stats(
    service: VrfInstanceService = Depends(get_service), _: Principal = Depends(require_read)
) -> dict[str, int]:
    return await service.stats()


@router.get("/export", response_class=PlainTextResponse, summary="Export Vrf Instance records as CSV")
async def export(
    flt: VrfInstanceFilter = Depends(),
    service: VrfInstanceService = Depends(get_service),
    _: Principal = Depends(require_read),
) -> PlainTextResponse:
    body = await service.export_csv(flt)
    headers = {"Content-Disposition": 'attachment; filename="vrf-instances.csv"'}
    return PlainTextResponse(body, media_type="text/csv", headers=headers)


@router.get("/by-code/{code}", response_model=VrfInstanceRead, summary="Get a Vrf Instance by business code")
async def get_by_code(
    code: str, service: VrfInstanceService = Depends(get_service), _: Principal = Depends(require_read)
) -> VrfInstanceRead:
    return await service.get_by_code(code)


@router.head("/by-code/{code}", summary="Check whether a Vrf Instance code exists")
async def exists(
    code: str, service: VrfInstanceService = Depends(get_service), _: Principal = Depends(require_read)
) -> Response:
    found = await service.exists(code)
    return Response(status_code=status.HTTP_200_OK if found else status.HTTP_404_NOT_FOUND)


@router.get("/{id}", response_model=VrfInstanceRead, summary="Get a Vrf Instance by id")
async def get(
    id: UUID, service: VrfInstanceService = Depends(get_service), _: Principal = Depends(require_read)
) -> VrfInstanceRead:
    return await service.get(id)


@router.post("", response_model=VrfInstanceRead, status_code=status.HTTP_201_CREATED, summary="Create a Vrf Instance")
async def create(
    payload: VrfInstanceCreate,
    request: Request,
    response: Response,
    service: VrfInstanceService = Depends(get_service),
    _: Principal = Depends(require_write),
) -> VrfInstanceRead:
    created = await service.create(payload)
    response.headers["Location"] = f"{request.url.path}/{created.id}"
    return created


@router.put("/{id}", response_model=VrfInstanceRead, summary="Update a Vrf Instance")
async def update(
    id: UUID,
    payload: VrfInstanceUpdate,
    service: VrfInstanceService = Depends(get_service),
    _: Principal = Depends(require_write),
) -> VrfInstanceRead:
    return await service.update(id, payload)


@router.patch("/{id}/status", response_model=VrfInstanceRead, summary="Change lifecycle status")
async def change_status(
    id: UUID,
    new_status: LifecycleStatus = Query(..., alias="status"),
    service: VrfInstanceService = Depends(get_service),
    _: Principal = Depends(require_write),
) -> VrfInstanceRead:
    return await service.change_status(id, new_status)


@router.delete("/{id}", status_code=status.HTTP_204_NO_CONTENT, summary="Delete a Vrf Instance")
async def delete(
    id: UUID, service: VrfInstanceService = Depends(get_service), _: Principal = Depends(require_write)
) -> Response:
    await service.delete(id)
    return Response(status_code=status.HTTP_204_NO_CONTENT)


@router.post("/bulk", response_model=VrfInstanceBulkResult, summary="Create many Vrf Instance records")
async def bulk_create(
    payloads: list[VrfInstanceCreate],
    service: VrfInstanceService = Depends(get_service),
    _: Principal = Depends(require_write),
) -> VrfInstanceBulkResult:
    return await service.bulk_create(payloads)


@router.post("/reconcile", response_model=list[ReconciliationItem], summary="Reconcile external Vrf Instance records")
async def reconcile(
    records: list[dict[str, object]],
    apply: bool = Query(False, description="Apply the plan instead of a dry run"),
    service: VrfInstanceService = Depends(get_service),
    _: Principal = Depends(require_write),
) -> list[ReconciliationItem]:
    return await service.reconcile(records, apply)


@router.get("/by-device-id/{device_id}", response_model=Page[VrfInstanceRead], summary="List Vrf Instance records by Device Id")
async def list_by_device_id(
    device_id: UUID,
    page: int = Query(0, ge=0),
    size: int = Query(20, ge=1, le=200),
    service: VrfInstanceService = Depends(get_service),
) -> Page[VrfInstanceRead]:
    return await service.list_by_device_id(device_id, page, size)
