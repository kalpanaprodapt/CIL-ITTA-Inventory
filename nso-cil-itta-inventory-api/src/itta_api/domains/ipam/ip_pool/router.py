"""HTTP routes for Ip Pool."""
from __future__ import annotations

from uuid import UUID

from fastapi import APIRouter, Depends, Query, Request, Response, status
from fastapi.responses import PlainTextResponse

from itta_core.auth import Principal, require_read, require_write
from itta_core.dependencies import service_provider
from itta_core.diffing import ReconciliationItem
from itta_core.models import EntityMeta, LifecycleStatus
from itta_core.pagination import Page
from itta_api.domains.ipam.ip_pool.filters import IpPoolFilter
from itta_api.domains.ipam.ip_pool.meta import META
from itta_api.domains.ipam.ip_pool.schemas import (
    IpPoolBulkResult,
    IpPoolCreate,
    IpPoolRead,
    IpPoolSummary,
    IpPoolUpdate,
)
from itta_api.domains.ipam.ip_pool.service import IpPoolService

router = APIRouter(prefix="/api/v1/ip-pools", tags=["Ip Pool"])
get_service = service_provider("ip_pool")


@router.get("/meta", response_model=EntityMeta, summary="Field catalogue for Ip Pool")
async def meta() -> EntityMeta:
    return META


@router.get("", response_model=Page[IpPoolRead], summary="Search Ip Pool records")
async def search(
    flt: IpPoolFilter = Depends(),
    page: int = Query(0, ge=0),
    size: int = Query(20, ge=1, le=200),
    sort: str | None = Query(None, description="field,asc|desc"),
    service: IpPoolService = Depends(get_service),
    _: Principal = Depends(require_read),
) -> Page[IpPoolRead]:
    return await service.search(flt, page, size, sort)


@router.get("/summaries", response_model=list[IpPoolSummary], summary="Pick-list of Ip Pool records")
async def summaries(
    status_filter: LifecycleStatus | None = Query(None, alias="status"),
    service: IpPoolService = Depends(get_service),
    _: Principal = Depends(require_read),
) -> list[IpPoolSummary]:
    return await service.summaries(status_filter)


@router.get("/stats/status", response_model=dict[str, int], summary="Counts per lifecycle status")
async def status_stats(
    service: IpPoolService = Depends(get_service), _: Principal = Depends(require_read)
) -> dict[str, int]:
    return await service.stats()


@router.get("/export", response_class=PlainTextResponse, summary="Export Ip Pool records as CSV")
async def export(
    flt: IpPoolFilter = Depends(),
    service: IpPoolService = Depends(get_service),
    _: Principal = Depends(require_read),
) -> PlainTextResponse:
    body = await service.export_csv(flt)
    headers = {"Content-Disposition": 'attachment; filename="ip-pools.csv"'}
    return PlainTextResponse(body, media_type="text/csv", headers=headers)


@router.get("/by-code/{code}", response_model=IpPoolRead, summary="Get a Ip Pool by business code")
async def get_by_code(
    code: str, service: IpPoolService = Depends(get_service), _: Principal = Depends(require_read)
) -> IpPoolRead:
    return await service.get_by_code(code)


@router.head("/by-code/{code}", summary="Check whether a Ip Pool code exists")
async def exists(
    code: str, service: IpPoolService = Depends(get_service), _: Principal = Depends(require_read)
) -> Response:
    found = await service.exists(code)
    return Response(status_code=status.HTTP_200_OK if found else status.HTTP_404_NOT_FOUND)


@router.get("/{id}", response_model=IpPoolRead, summary="Get a Ip Pool by id")
async def get(
    id: UUID, service: IpPoolService = Depends(get_service), _: Principal = Depends(require_read)
) -> IpPoolRead:
    return await service.get(id)


@router.post("", response_model=IpPoolRead, status_code=status.HTTP_201_CREATED, summary="Create a Ip Pool")
async def create(
    payload: IpPoolCreate,
    request: Request,
    response: Response,
    service: IpPoolService = Depends(get_service),
    _: Principal = Depends(require_write),
) -> IpPoolRead:
    created = await service.create(payload)
    response.headers["Location"] = f"{request.url.path}/{created.id}"
    return created


@router.put("/{id}", response_model=IpPoolRead, summary="Update a Ip Pool")
async def update(
    id: UUID,
    payload: IpPoolUpdate,
    service: IpPoolService = Depends(get_service),
    _: Principal = Depends(require_write),
) -> IpPoolRead:
    return await service.update(id, payload)


@router.patch("/{id}/status", response_model=IpPoolRead, summary="Change lifecycle status")
async def change_status(
    id: UUID,
    new_status: LifecycleStatus = Query(..., alias="status"),
    service: IpPoolService = Depends(get_service),
    _: Principal = Depends(require_write),
) -> IpPoolRead:
    return await service.change_status(id, new_status)


@router.delete("/{id}", status_code=status.HTTP_204_NO_CONTENT, summary="Delete a Ip Pool")
async def delete(
    id: UUID, service: IpPoolService = Depends(get_service), _: Principal = Depends(require_write)
) -> Response:
    await service.delete(id)
    return Response(status_code=status.HTTP_204_NO_CONTENT)


@router.post("/bulk", response_model=IpPoolBulkResult, summary="Create many Ip Pool records")
async def bulk_create(
    payloads: list[IpPoolCreate],
    service: IpPoolService = Depends(get_service),
    _: Principal = Depends(require_write),
) -> IpPoolBulkResult:
    return await service.bulk_create(payloads)


@router.post("/reconcile", response_model=list[ReconciliationItem], summary="Reconcile external Ip Pool records")
async def reconcile(
    records: list[dict[str, object]],
    apply: bool = Query(False, description="Apply the plan instead of a dry run"),
    service: IpPoolService = Depends(get_service),
    _: Principal = Depends(require_write),
) -> list[ReconciliationItem]:
    return await service.reconcile(records, apply)


@router.get("/by-subnet-id/{subnet_id}", response_model=Page[IpPoolRead], summary="List Ip Pool records by Subnet Id")
async def list_by_subnet_id(
    subnet_id: UUID,
    page: int = Query(0, ge=0),
    size: int = Query(20, ge=1, le=200),
    service: IpPoolService = Depends(get_service),
) -> Page[IpPoolRead]:
    return await service.list_by_subnet_id(subnet_id, page, size)
