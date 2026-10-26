"""HTTP routes for Ip Subnet."""
from __future__ import annotations

from uuid import UUID

from fastapi import APIRouter, Depends, Query, Request, Response, status
from fastapi.responses import PlainTextResponse

from itta_core.auth import Principal, require_read, require_write
from itta_core.dependencies import service_provider
from itta_core.diffing import ReconciliationItem
from itta_core.models import EntityMeta, LifecycleStatus
from itta_core.pagination import Page
from itta_api.domains.ipam.ip_subnet.filters import IpSubnetFilter
from itta_api.domains.ipam.ip_subnet.meta import META
from itta_api.domains.ipam.ip_subnet.schemas import (
    IpSubnetBulkResult,
    IpSubnetCreate,
    IpSubnetRead,
    IpSubnetSummary,
    IpSubnetUpdate,
)
from itta_api.domains.ipam.ip_subnet.service import IpSubnetService

router = APIRouter(prefix="/api/v1/ip-subnets", tags=["Ip Subnet"])
get_service = service_provider("ip_subnet")


@router.get("/meta", response_model=EntityMeta, summary="Field catalogue for Ip Subnet")
async def meta() -> EntityMeta:
    return META


@router.get("", response_model=Page[IpSubnetRead], summary="Search Ip Subnet records")
async def search(
    flt: IpSubnetFilter = Depends(),
    page: int = Query(0, ge=0),
    size: int = Query(20, ge=1, le=200),
    sort: str | None = Query(None, description="field,asc|desc"),
    service: IpSubnetService = Depends(get_service),
    _: Principal = Depends(require_read),
) -> Page[IpSubnetRead]:
    return await service.search(flt, page, size, sort)


@router.get("/summaries", response_model=list[IpSubnetSummary], summary="Pick-list of Ip Subnet records")
async def summaries(
    status_filter: LifecycleStatus | None = Query(None, alias="status"),
    service: IpSubnetService = Depends(get_service),
    _: Principal = Depends(require_read),
) -> list[IpSubnetSummary]:
    return await service.summaries(status_filter)


@router.get("/stats/status", response_model=dict[str, int], summary="Counts per lifecycle status")
async def status_stats(
    service: IpSubnetService = Depends(get_service), _: Principal = Depends(require_read)
) -> dict[str, int]:
    return await service.stats()


@router.get("/export", response_class=PlainTextResponse, summary="Export Ip Subnet records as CSV")
async def export(
    flt: IpSubnetFilter = Depends(),
    service: IpSubnetService = Depends(get_service),
    _: Principal = Depends(require_read),
) -> PlainTextResponse:
    body = await service.export_csv(flt)
    headers = {"Content-Disposition": 'attachment; filename="ip-subnets.csv"'}
    return PlainTextResponse(body, media_type="text/csv", headers=headers)


@router.get("/by-code/{code}", response_model=IpSubnetRead, summary="Get a Ip Subnet by business code")
async def get_by_code(
    code: str, service: IpSubnetService = Depends(get_service), _: Principal = Depends(require_read)
) -> IpSubnetRead:
    return await service.get_by_code(code)


@router.head("/by-code/{code}", summary="Check whether a Ip Subnet code exists")
async def exists(
    code: str, service: IpSubnetService = Depends(get_service), _: Principal = Depends(require_read)
) -> Response:
    found = await service.exists(code)
    return Response(status_code=status.HTTP_200_OK if found else status.HTTP_404_NOT_FOUND)


@router.get("/{id}", response_model=IpSubnetRead, summary="Get a Ip Subnet by id")
async def get(
    id: UUID, service: IpSubnetService = Depends(get_service), _: Principal = Depends(require_read)
) -> IpSubnetRead:
    return await service.get(id)


@router.post("", response_model=IpSubnetRead, status_code=status.HTTP_201_CREATED, summary="Create a Ip Subnet")
async def create(
    payload: IpSubnetCreate,
    request: Request,
    response: Response,
    service: IpSubnetService = Depends(get_service),
    _: Principal = Depends(require_write),
) -> IpSubnetRead:
    created = await service.create(payload)
    response.headers["Location"] = f"{request.url.path}/{created.id}"
    return created


@router.put("/{id}", response_model=IpSubnetRead, summary="Update a Ip Subnet")
async def update(
    id: UUID,
    payload: IpSubnetUpdate,
    service: IpSubnetService = Depends(get_service),
    _: Principal = Depends(require_write),
) -> IpSubnetRead:
    return await service.update(id, payload)


@router.patch("/{id}/status", response_model=IpSubnetRead, summary="Change lifecycle status")
async def change_status(
    id: UUID,
    new_status: LifecycleStatus = Query(..., alias="status"),
    service: IpSubnetService = Depends(get_service),
    _: Principal = Depends(require_write),
) -> IpSubnetRead:
    return await service.change_status(id, new_status)


@router.delete("/{id}", status_code=status.HTTP_204_NO_CONTENT, summary="Delete a Ip Subnet")
async def delete(
    id: UUID, service: IpSubnetService = Depends(get_service), _: Principal = Depends(require_write)
) -> Response:
    await service.delete(id)
    return Response(status_code=status.HTTP_204_NO_CONTENT)


@router.post("/bulk", response_model=IpSubnetBulkResult, summary="Create many Ip Subnet records")
async def bulk_create(
    payloads: list[IpSubnetCreate],
    service: IpSubnetService = Depends(get_service),
    _: Principal = Depends(require_write),
) -> IpSubnetBulkResult:
    return await service.bulk_create(payloads)


@router.post("/reconcile", response_model=list[ReconciliationItem], summary="Reconcile external Ip Subnet records")
async def reconcile(
    records: list[dict[str, object]],
    apply: bool = Query(False, description="Apply the plan instead of a dry run"),
    service: IpSubnetService = Depends(get_service),
    _: Principal = Depends(require_write),
) -> list[ReconciliationItem]:
    return await service.reconcile(records, apply)


@router.get("/by-vrf-id/{vrf_id}", response_model=Page[IpSubnetRead], summary="List Ip Subnet records by Vrf Id")
async def list_by_vrf_id(
    vrf_id: UUID,
    page: int = Query(0, ge=0),
    size: int = Query(20, ge=1, le=200),
    service: IpSubnetService = Depends(get_service),
) -> Page[IpSubnetRead]:
    return await service.list_by_vrf_id(vrf_id, page, size)

@router.get("/by-vlan-id/{vlan_id}", response_model=Page[IpSubnetRead], summary="List Ip Subnet records by Vlan Id")
async def list_by_vlan_id(
    vlan_id: UUID,
    page: int = Query(0, ge=0),
    size: int = Query(20, ge=1, le=200),
    service: IpSubnetService = Depends(get_service),
) -> Page[IpSubnetRead]:
    return await service.list_by_vlan_id(vlan_id, page, size)
