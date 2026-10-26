"""HTTP routes for Vpn Service."""
from __future__ import annotations

from uuid import UUID

from fastapi import APIRouter, Depends, Query, Request, Response, status
from fastapi.responses import PlainTextResponse

from itta_core.auth import Principal, require_read, require_write
from itta_core.dependencies import service_provider
from itta_core.diffing import ReconciliationItem
from itta_core.models import EntityMeta, LifecycleStatus
from itta_core.pagination import Page
from itta_api.domains.mpls.vpn_service.filters import VpnServiceFilter
from itta_api.domains.mpls.vpn_service.meta import META
from itta_api.domains.mpls.vpn_service.schemas import (
    VpnServiceBulkResult,
    VpnServiceCreate,
    VpnServiceRead,
    VpnServiceSummary,
    VpnServiceUpdate,
)
from itta_api.domains.mpls.vpn_service.service import VpnServiceService

router = APIRouter(prefix="/api/v1/vpn-services", tags=["Vpn Service"])
get_service = service_provider("vpn_service")


@router.get("/meta", response_model=EntityMeta, summary="Field catalogue for Vpn Service")
async def meta() -> EntityMeta:
    return META


@router.get("", response_model=Page[VpnServiceRead], summary="Search Vpn Service records")
async def search(
    flt: VpnServiceFilter = Depends(),
    page: int = Query(0, ge=0),
    size: int = Query(20, ge=1, le=200),
    sort: str | None = Query(None, description="field,asc|desc"),
    service: VpnServiceService = Depends(get_service),
    _: Principal = Depends(require_read),
) -> Page[VpnServiceRead]:
    return await service.search(flt, page, size, sort)


@router.get("/summaries", response_model=list[VpnServiceSummary], summary="Pick-list of Vpn Service records")
async def summaries(
    status_filter: LifecycleStatus | None = Query(None, alias="status"),
    service: VpnServiceService = Depends(get_service),
    _: Principal = Depends(require_read),
) -> list[VpnServiceSummary]:
    return await service.summaries(status_filter)


@router.get("/stats/status", response_model=dict[str, int], summary="Counts per lifecycle status")
async def status_stats(
    service: VpnServiceService = Depends(get_service), _: Principal = Depends(require_read)
) -> dict[str, int]:
    return await service.stats()


@router.get("/export", response_class=PlainTextResponse, summary="Export Vpn Service records as CSV")
async def export(
    flt: VpnServiceFilter = Depends(),
    service: VpnServiceService = Depends(get_service),
    _: Principal = Depends(require_read),
) -> PlainTextResponse:
    body = await service.export_csv(flt)
    headers = {"Content-Disposition": 'attachment; filename="vpn-services.csv"'}
    return PlainTextResponse(body, media_type="text/csv", headers=headers)


@router.get("/by-code/{code}", response_model=VpnServiceRead, summary="Get a Vpn Service by business code")
async def get_by_code(
    code: str, service: VpnServiceService = Depends(get_service), _: Principal = Depends(require_read)
) -> VpnServiceRead:
    return await service.get_by_code(code)


@router.head("/by-code/{code}", summary="Check whether a Vpn Service code exists")
async def exists(
    code: str, service: VpnServiceService = Depends(get_service), _: Principal = Depends(require_read)
) -> Response:
    found = await service.exists(code)
    return Response(status_code=status.HTTP_200_OK if found else status.HTTP_404_NOT_FOUND)


@router.get("/{id}", response_model=VpnServiceRead, summary="Get a Vpn Service by id")
async def get(
    id: UUID, service: VpnServiceService = Depends(get_service), _: Principal = Depends(require_read)
) -> VpnServiceRead:
    return await service.get(id)


@router.post("", response_model=VpnServiceRead, status_code=status.HTTP_201_CREATED, summary="Create a Vpn Service")
async def create(
    payload: VpnServiceCreate,
    request: Request,
    response: Response,
    service: VpnServiceService = Depends(get_service),
    _: Principal = Depends(require_write),
) -> VpnServiceRead:
    created = await service.create(payload)
    response.headers["Location"] = f"{request.url.path}/{created.id}"
    return created


@router.put("/{id}", response_model=VpnServiceRead, summary="Update a Vpn Service")
async def update(
    id: UUID,
    payload: VpnServiceUpdate,
    service: VpnServiceService = Depends(get_service),
    _: Principal = Depends(require_write),
) -> VpnServiceRead:
    return await service.update(id, payload)


@router.patch("/{id}/status", response_model=VpnServiceRead, summary="Change lifecycle status")
async def change_status(
    id: UUID,
    new_status: LifecycleStatus = Query(..., alias="status"),
    service: VpnServiceService = Depends(get_service),
    _: Principal = Depends(require_write),
) -> VpnServiceRead:
    return await service.change_status(id, new_status)


@router.delete("/{id}", status_code=status.HTTP_204_NO_CONTENT, summary="Delete a Vpn Service")
async def delete(
    id: UUID, service: VpnServiceService = Depends(get_service), _: Principal = Depends(require_write)
) -> Response:
    await service.delete(id)
    return Response(status_code=status.HTTP_204_NO_CONTENT)


@router.post("/bulk", response_model=VpnServiceBulkResult, summary="Create many Vpn Service records")
async def bulk_create(
    payloads: list[VpnServiceCreate],
    service: VpnServiceService = Depends(get_service),
    _: Principal = Depends(require_write),
) -> VpnServiceBulkResult:
    return await service.bulk_create(payloads)


@router.post("/reconcile", response_model=list[ReconciliationItem], summary="Reconcile external Vpn Service records")
async def reconcile(
    records: list[dict[str, object]],
    apply: bool = Query(False, description="Apply the plan instead of a dry run"),
    service: VpnServiceService = Depends(get_service),
    _: Principal = Depends(require_write),
) -> list[ReconciliationItem]:
    return await service.reconcile(records, apply)


@router.get("/by-vrf-id/{vrf_id}", response_model=Page[VpnServiceRead], summary="List Vpn Service records by Vrf Id")
async def list_by_vrf_id(
    vrf_id: UUID,
    page: int = Query(0, ge=0),
    size: int = Query(20, ge=1, le=200),
    service: VpnServiceService = Depends(get_service),
) -> Page[VpnServiceRead]:
    return await service.list_by_vrf_id(vrf_id, page, size)

@router.get("/by-customer-id/{customer_id}", response_model=Page[VpnServiceRead], summary="List Vpn Service records by Customer Id")
async def list_by_customer_id(
    customer_id: UUID,
    page: int = Query(0, ge=0),
    size: int = Query(20, ge=1, le=200),
    service: VpnServiceService = Depends(get_service),
) -> Page[VpnServiceRead]:
    return await service.list_by_customer_id(customer_id, page, size)
