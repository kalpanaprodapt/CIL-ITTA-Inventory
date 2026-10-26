"""HTTP routes for Vlan Pool."""
from __future__ import annotations

from uuid import UUID

from fastapi import APIRouter, Depends, Query, Request, Response, status
from fastapi.responses import PlainTextResponse

from itta_core.auth import Principal, require_read, require_write
from itta_core.dependencies import service_provider
from itta_core.diffing import ReconciliationItem
from itta_core.models import EntityMeta, LifecycleStatus
from itta_core.pagination import Page
from itta_api.domains.switching.vlan_pool.filters import VlanPoolFilter
from itta_api.domains.switching.vlan_pool.meta import META
from itta_api.domains.switching.vlan_pool.schemas import (
    VlanPoolBulkResult,
    VlanPoolCreate,
    VlanPoolRead,
    VlanPoolSummary,
    VlanPoolUpdate,
)
from itta_api.domains.switching.vlan_pool.service import VlanPoolService

router = APIRouter(prefix="/api/v1/vlan-pools", tags=["Vlan Pool"])
get_service = service_provider("vlan_pool")


@router.get("/meta", response_model=EntityMeta, summary="Field catalogue for Vlan Pool")
async def meta() -> EntityMeta:
    return META


@router.get("", response_model=Page[VlanPoolRead], summary="Search Vlan Pool records")
async def search(
    flt: VlanPoolFilter = Depends(),
    page: int = Query(0, ge=0),
    size: int = Query(20, ge=1, le=200),
    sort: str | None = Query(None, description="field,asc|desc"),
    service: VlanPoolService = Depends(get_service),
    _: Principal = Depends(require_read),
) -> Page[VlanPoolRead]:
    return await service.search(flt, page, size, sort)


@router.get("/summaries", response_model=list[VlanPoolSummary], summary="Pick-list of Vlan Pool records")
async def summaries(
    status_filter: LifecycleStatus | None = Query(None, alias="status"),
    service: VlanPoolService = Depends(get_service),
    _: Principal = Depends(require_read),
) -> list[VlanPoolSummary]:
    return await service.summaries(status_filter)


@router.get("/stats/status", response_model=dict[str, int], summary="Counts per lifecycle status")
async def status_stats(
    service: VlanPoolService = Depends(get_service), _: Principal = Depends(require_read)
) -> dict[str, int]:
    return await service.stats()


@router.get("/export", response_class=PlainTextResponse, summary="Export Vlan Pool records as CSV")
async def export(
    flt: VlanPoolFilter = Depends(),
    service: VlanPoolService = Depends(get_service),
    _: Principal = Depends(require_read),
) -> PlainTextResponse:
    body = await service.export_csv(flt)
    headers = {"Content-Disposition": 'attachment; filename="vlan-pools.csv"'}
    return PlainTextResponse(body, media_type="text/csv", headers=headers)


@router.get("/by-code/{code}", response_model=VlanPoolRead, summary="Get a Vlan Pool by business code")
async def get_by_code(
    code: str, service: VlanPoolService = Depends(get_service), _: Principal = Depends(require_read)
) -> VlanPoolRead:
    return await service.get_by_code(code)


@router.head("/by-code/{code}", summary="Check whether a Vlan Pool code exists")
async def exists(
    code: str, service: VlanPoolService = Depends(get_service), _: Principal = Depends(require_read)
) -> Response:
    found = await service.exists(code)
    return Response(status_code=status.HTTP_200_OK if found else status.HTTP_404_NOT_FOUND)


@router.get("/{id}", response_model=VlanPoolRead, summary="Get a Vlan Pool by id")
async def get(
    id: UUID, service: VlanPoolService = Depends(get_service), _: Principal = Depends(require_read)
) -> VlanPoolRead:
    return await service.get(id)


@router.post("", response_model=VlanPoolRead, status_code=status.HTTP_201_CREATED, summary="Create a Vlan Pool")
async def create(
    payload: VlanPoolCreate,
    request: Request,
    response: Response,
    service: VlanPoolService = Depends(get_service),
    _: Principal = Depends(require_write),
) -> VlanPoolRead:
    created = await service.create(payload)
    response.headers["Location"] = f"{request.url.path}/{created.id}"
    return created


@router.put("/{id}", response_model=VlanPoolRead, summary="Update a Vlan Pool")
async def update(
    id: UUID,
    payload: VlanPoolUpdate,
    service: VlanPoolService = Depends(get_service),
    _: Principal = Depends(require_write),
) -> VlanPoolRead:
    return await service.update(id, payload)


@router.patch("/{id}/status", response_model=VlanPoolRead, summary="Change lifecycle status")
async def change_status(
    id: UUID,
    new_status: LifecycleStatus = Query(..., alias="status"),
    service: VlanPoolService = Depends(get_service),
    _: Principal = Depends(require_write),
) -> VlanPoolRead:
    return await service.change_status(id, new_status)


@router.delete("/{id}", status_code=status.HTTP_204_NO_CONTENT, summary="Delete a Vlan Pool")
async def delete(
    id: UUID, service: VlanPoolService = Depends(get_service), _: Principal = Depends(require_write)
) -> Response:
    await service.delete(id)
    return Response(status_code=status.HTTP_204_NO_CONTENT)


@router.post("/bulk", response_model=VlanPoolBulkResult, summary="Create many Vlan Pool records")
async def bulk_create(
    payloads: list[VlanPoolCreate],
    service: VlanPoolService = Depends(get_service),
    _: Principal = Depends(require_write),
) -> VlanPoolBulkResult:
    return await service.bulk_create(payloads)


@router.post("/reconcile", response_model=list[ReconciliationItem], summary="Reconcile external Vlan Pool records")
async def reconcile(
    records: list[dict[str, object]],
    apply: bool = Query(False, description="Apply the plan instead of a dry run"),
    service: VlanPoolService = Depends(get_service),
    _: Principal = Depends(require_write),
) -> list[ReconciliationItem]:
    return await service.reconcile(records, apply)

