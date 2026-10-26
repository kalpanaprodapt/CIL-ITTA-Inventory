"""HTTP routes for Tunnel Endpoint."""
from __future__ import annotations

from uuid import UUID

from fastapi import APIRouter, Depends, Query, Request, Response, status
from fastapi.responses import PlainTextResponse

from itta_core.auth import Principal, require_read, require_write
from itta_core.dependencies import service_provider
from itta_core.diffing import ReconciliationItem
from itta_core.models import EntityMeta, LifecycleStatus
from itta_core.pagination import Page
from itta_api.domains.mpls.tunnel_endpoint.filters import TunnelEndpointFilter
from itta_api.domains.mpls.tunnel_endpoint.meta import META
from itta_api.domains.mpls.tunnel_endpoint.schemas import (
    TunnelEndpointBulkResult,
    TunnelEndpointCreate,
    TunnelEndpointRead,
    TunnelEndpointSummary,
    TunnelEndpointUpdate,
)
from itta_api.domains.mpls.tunnel_endpoint.service import TunnelEndpointService

router = APIRouter(prefix="/api/v1/tunnel-endpoints", tags=["Tunnel Endpoint"])
get_service = service_provider("tunnel_endpoint")


@router.get("/meta", response_model=EntityMeta, summary="Field catalogue for Tunnel Endpoint")
async def meta() -> EntityMeta:
    return META


@router.get("", response_model=Page[TunnelEndpointRead], summary="Search Tunnel Endpoint records")
async def search(
    flt: TunnelEndpointFilter = Depends(),
    page: int = Query(0, ge=0),
    size: int = Query(20, ge=1, le=200),
    sort: str | None = Query(None, description="field,asc|desc"),
    service: TunnelEndpointService = Depends(get_service),
    _: Principal = Depends(require_read),
) -> Page[TunnelEndpointRead]:
    return await service.search(flt, page, size, sort)


@router.get("/summaries", response_model=list[TunnelEndpointSummary], summary="Pick-list of Tunnel Endpoint records")
async def summaries(
    status_filter: LifecycleStatus | None = Query(None, alias="status"),
    service: TunnelEndpointService = Depends(get_service),
    _: Principal = Depends(require_read),
) -> list[TunnelEndpointSummary]:
    return await service.summaries(status_filter)


@router.get("/stats/status", response_model=dict[str, int], summary="Counts per lifecycle status")
async def status_stats(
    service: TunnelEndpointService = Depends(get_service), _: Principal = Depends(require_read)
) -> dict[str, int]:
    return await service.stats()


@router.get("/export", response_class=PlainTextResponse, summary="Export Tunnel Endpoint records as CSV")
async def export(
    flt: TunnelEndpointFilter = Depends(),
    service: TunnelEndpointService = Depends(get_service),
    _: Principal = Depends(require_read),
) -> PlainTextResponse:
    body = await service.export_csv(flt)
    headers = {"Content-Disposition": 'attachment; filename="tunnel-endpoints.csv"'}
    return PlainTextResponse(body, media_type="text/csv", headers=headers)


@router.get("/by-code/{code}", response_model=TunnelEndpointRead, summary="Get a Tunnel Endpoint by business code")
async def get_by_code(
    code: str, service: TunnelEndpointService = Depends(get_service), _: Principal = Depends(require_read)
) -> TunnelEndpointRead:
    return await service.get_by_code(code)


@router.head("/by-code/{code}", summary="Check whether a Tunnel Endpoint code exists")
async def exists(
    code: str, service: TunnelEndpointService = Depends(get_service), _: Principal = Depends(require_read)
) -> Response:
    found = await service.exists(code)
    return Response(status_code=status.HTTP_200_OK if found else status.HTTP_404_NOT_FOUND)


@router.get("/{id}", response_model=TunnelEndpointRead, summary="Get a Tunnel Endpoint by id")
async def get(
    id: UUID, service: TunnelEndpointService = Depends(get_service), _: Principal = Depends(require_read)
) -> TunnelEndpointRead:
    return await service.get(id)


@router.post("", response_model=TunnelEndpointRead, status_code=status.HTTP_201_CREATED, summary="Create a Tunnel Endpoint")
async def create(
    payload: TunnelEndpointCreate,
    request: Request,
    response: Response,
    service: TunnelEndpointService = Depends(get_service),
    _: Principal = Depends(require_write),
) -> TunnelEndpointRead:
    created = await service.create(payload)
    response.headers["Location"] = f"{request.url.path}/{created.id}"
    return created


@router.put("/{id}", response_model=TunnelEndpointRead, summary="Update a Tunnel Endpoint")
async def update(
    id: UUID,
    payload: TunnelEndpointUpdate,
    service: TunnelEndpointService = Depends(get_service),
    _: Principal = Depends(require_write),
) -> TunnelEndpointRead:
    return await service.update(id, payload)


@router.patch("/{id}/status", response_model=TunnelEndpointRead, summary="Change lifecycle status")
async def change_status(
    id: UUID,
    new_status: LifecycleStatus = Query(..., alias="status"),
    service: TunnelEndpointService = Depends(get_service),
    _: Principal = Depends(require_write),
) -> TunnelEndpointRead:
    return await service.change_status(id, new_status)


@router.delete("/{id}", status_code=status.HTTP_204_NO_CONTENT, summary="Delete a Tunnel Endpoint")
async def delete(
    id: UUID, service: TunnelEndpointService = Depends(get_service), _: Principal = Depends(require_write)
) -> Response:
    await service.delete(id)
    return Response(status_code=status.HTTP_204_NO_CONTENT)


@router.post("/bulk", response_model=TunnelEndpointBulkResult, summary="Create many Tunnel Endpoint records")
async def bulk_create(
    payloads: list[TunnelEndpointCreate],
    service: TunnelEndpointService = Depends(get_service),
    _: Principal = Depends(require_write),
) -> TunnelEndpointBulkResult:
    return await service.bulk_create(payloads)


@router.post("/reconcile", response_model=list[ReconciliationItem], summary="Reconcile external Tunnel Endpoint records")
async def reconcile(
    records: list[dict[str, object]],
    apply: bool = Query(False, description="Apply the plan instead of a dry run"),
    service: TunnelEndpointService = Depends(get_service),
    _: Principal = Depends(require_write),
) -> list[ReconciliationItem]:
    return await service.reconcile(records, apply)


@router.get("/by-device-id/{device_id}", response_model=Page[TunnelEndpointRead], summary="List Tunnel Endpoint records by Device Id")
async def list_by_device_id(
    device_id: UUID,
    page: int = Query(0, ge=0),
    size: int = Query(20, ge=1, le=200),
    service: TunnelEndpointService = Depends(get_service),
) -> Page[TunnelEndpointRead]:
    return await service.list_by_device_id(device_id, page, size)
