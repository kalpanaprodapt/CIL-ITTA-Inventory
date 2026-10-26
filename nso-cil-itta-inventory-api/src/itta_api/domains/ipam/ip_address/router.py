"""HTTP routes for Ip Address."""
from __future__ import annotations

from uuid import UUID

from fastapi import APIRouter, Depends, Query, Request, Response, status
from fastapi.responses import PlainTextResponse

from itta_core.auth import Principal, require_read, require_write
from itta_core.dependencies import service_provider
from itta_core.diffing import ReconciliationItem
from itta_core.models import EntityMeta, LifecycleStatus
from itta_core.pagination import Page
from itta_api.domains.ipam.ip_address.filters import IpAddressFilter
from itta_api.domains.ipam.ip_address.meta import META
from itta_api.domains.ipam.ip_address.schemas import (
    IpAddressBulkResult,
    IpAddressCreate,
    IpAddressRead,
    IpAddressSummary,
    IpAddressUpdate,
)
from itta_api.domains.ipam.ip_address.service import IpAddressService

router = APIRouter(prefix="/api/v1/ip-addresses", tags=["Ip Address"])
get_service = service_provider("ip_address")


@router.get("/meta", response_model=EntityMeta, summary="Field catalogue for Ip Address")
async def meta() -> EntityMeta:
    return META


@router.get("", response_model=Page[IpAddressRead], summary="Search Ip Address records")
async def search(
    flt: IpAddressFilter = Depends(),
    page: int = Query(0, ge=0),
    size: int = Query(20, ge=1, le=200),
    sort: str | None = Query(None, description="field,asc|desc"),
    service: IpAddressService = Depends(get_service),
    _: Principal = Depends(require_read),
) -> Page[IpAddressRead]:
    return await service.search(flt, page, size, sort)


@router.get("/summaries", response_model=list[IpAddressSummary], summary="Pick-list of Ip Address records")
async def summaries(
    status_filter: LifecycleStatus | None = Query(None, alias="status"),
    service: IpAddressService = Depends(get_service),
    _: Principal = Depends(require_read),
) -> list[IpAddressSummary]:
    return await service.summaries(status_filter)


@router.get("/stats/status", response_model=dict[str, int], summary="Counts per lifecycle status")
async def status_stats(
    service: IpAddressService = Depends(get_service), _: Principal = Depends(require_read)
) -> dict[str, int]:
    return await service.stats()


@router.get("/export", response_class=PlainTextResponse, summary="Export Ip Address records as CSV")
async def export(
    flt: IpAddressFilter = Depends(),
    service: IpAddressService = Depends(get_service),
    _: Principal = Depends(require_read),
) -> PlainTextResponse:
    body = await service.export_csv(flt)
    headers = {"Content-Disposition": 'attachment; filename="ip-addresses.csv"'}
    return PlainTextResponse(body, media_type="text/csv", headers=headers)


@router.get("/by-code/{code}", response_model=IpAddressRead, summary="Get a Ip Address by business code")
async def get_by_code(
    code: str, service: IpAddressService = Depends(get_service), _: Principal = Depends(require_read)
) -> IpAddressRead:
    return await service.get_by_code(code)


@router.head("/by-code/{code}", summary="Check whether a Ip Address code exists")
async def exists(
    code: str, service: IpAddressService = Depends(get_service), _: Principal = Depends(require_read)
) -> Response:
    found = await service.exists(code)
    return Response(status_code=status.HTTP_200_OK if found else status.HTTP_404_NOT_FOUND)


@router.get("/{id}", response_model=IpAddressRead, summary="Get a Ip Address by id")
async def get(
    id: UUID, service: IpAddressService = Depends(get_service), _: Principal = Depends(require_read)
) -> IpAddressRead:
    return await service.get(id)


@router.post("", response_model=IpAddressRead, status_code=status.HTTP_201_CREATED, summary="Create a Ip Address")
async def create(
    payload: IpAddressCreate,
    request: Request,
    response: Response,
    service: IpAddressService = Depends(get_service),
    _: Principal = Depends(require_write),
) -> IpAddressRead:
    created = await service.create(payload)
    response.headers["Location"] = f"{request.url.path}/{created.id}"
    return created


@router.put("/{id}", response_model=IpAddressRead, summary="Update a Ip Address")
async def update(
    id: UUID,
    payload: IpAddressUpdate,
    service: IpAddressService = Depends(get_service),
    _: Principal = Depends(require_write),
) -> IpAddressRead:
    return await service.update(id, payload)


@router.patch("/{id}/status", response_model=IpAddressRead, summary="Change lifecycle status")
async def change_status(
    id: UUID,
    new_status: LifecycleStatus = Query(..., alias="status"),
    service: IpAddressService = Depends(get_service),
    _: Principal = Depends(require_write),
) -> IpAddressRead:
    return await service.change_status(id, new_status)


@router.delete("/{id}", status_code=status.HTTP_204_NO_CONTENT, summary="Delete a Ip Address")
async def delete(
    id: UUID, service: IpAddressService = Depends(get_service), _: Principal = Depends(require_write)
) -> Response:
    await service.delete(id)
    return Response(status_code=status.HTTP_204_NO_CONTENT)


@router.post("/bulk", response_model=IpAddressBulkResult, summary="Create many Ip Address records")
async def bulk_create(
    payloads: list[IpAddressCreate],
    service: IpAddressService = Depends(get_service),
    _: Principal = Depends(require_write),
) -> IpAddressBulkResult:
    return await service.bulk_create(payloads)


@router.post("/reconcile", response_model=list[ReconciliationItem], summary="Reconcile external Ip Address records")
async def reconcile(
    records: list[dict[str, object]],
    apply: bool = Query(False, description="Apply the plan instead of a dry run"),
    service: IpAddressService = Depends(get_service),
    _: Principal = Depends(require_write),
) -> list[ReconciliationItem]:
    return await service.reconcile(records, apply)


@router.get("/by-subnet-id/{subnet_id}", response_model=Page[IpAddressRead], summary="List Ip Address records by Subnet Id")
async def list_by_subnet_id(
    subnet_id: UUID,
    page: int = Query(0, ge=0),
    size: int = Query(20, ge=1, le=200),
    service: IpAddressService = Depends(get_service),
) -> Page[IpAddressRead]:
    return await service.list_by_subnet_id(subnet_id, page, size)

@router.get("/by-assigned-to-id/{assigned_to_id}", response_model=Page[IpAddressRead], summary="List Ip Address records by Assigned To Id")
async def list_by_assigned_to_id(
    assigned_to_id: UUID,
    page: int = Query(0, ge=0),
    size: int = Query(20, ge=1, le=200),
    service: IpAddressService = Depends(get_service),
) -> Page[IpAddressRead]:
    return await service.list_by_assigned_to_id(assigned_to_id, page, size)
