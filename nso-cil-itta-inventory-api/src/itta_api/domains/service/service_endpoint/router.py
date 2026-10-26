"""HTTP routes for Service Endpoint."""
from __future__ import annotations

from uuid import UUID

from fastapi import APIRouter, Depends, Query, Request, Response, status
from fastapi.responses import PlainTextResponse

from itta_core.auth import Principal, require_read, require_write
from itta_core.dependencies import service_provider
from itta_core.diffing import ReconciliationItem
from itta_core.models import EntityMeta, LifecycleStatus
from itta_core.pagination import Page
from itta_api.domains.service.service_endpoint.filters import ServiceEndpointFilter
from itta_api.domains.service.service_endpoint.meta import META
from itta_api.domains.service.service_endpoint.schemas import (
    ServiceEndpointBulkResult,
    ServiceEndpointCreate,
    ServiceEndpointRead,
    ServiceEndpointSummary,
    ServiceEndpointUpdate,
)
from itta_api.domains.service.service_endpoint.service import ServiceEndpointService

router = APIRouter(prefix="/api/v1/service-endpoints", tags=["Service Endpoint"])
get_service = service_provider("service_endpoint")


@router.get("/meta", response_model=EntityMeta, summary="Field catalogue for Service Endpoint")
async def meta() -> EntityMeta:
    return META


@router.get("", response_model=Page[ServiceEndpointRead], summary="Search Service Endpoint records")
async def search(
    flt: ServiceEndpointFilter = Depends(),
    page: int = Query(0, ge=0),
    size: int = Query(20, ge=1, le=200),
    sort: str | None = Query(None, description="field,asc|desc"),
    service: ServiceEndpointService = Depends(get_service),
    _: Principal = Depends(require_read),
) -> Page[ServiceEndpointRead]:
    return await service.search(flt, page, size, sort)


@router.get("/summaries", response_model=list[ServiceEndpointSummary], summary="Pick-list of Service Endpoint records")
async def summaries(
    status_filter: LifecycleStatus | None = Query(None, alias="status"),
    service: ServiceEndpointService = Depends(get_service),
    _: Principal = Depends(require_read),
) -> list[ServiceEndpointSummary]:
    return await service.summaries(status_filter)


@router.get("/stats/status", response_model=dict[str, int], summary="Counts per lifecycle status")
async def status_stats(
    service: ServiceEndpointService = Depends(get_service), _: Principal = Depends(require_read)
) -> dict[str, int]:
    return await service.stats()


@router.get("/export", response_class=PlainTextResponse, summary="Export Service Endpoint records as CSV")
async def export(
    flt: ServiceEndpointFilter = Depends(),
    service: ServiceEndpointService = Depends(get_service),
    _: Principal = Depends(require_read),
) -> PlainTextResponse:
    body = await service.export_csv(flt)
    headers = {"Content-Disposition": 'attachment; filename="service-endpoints.csv"'}
    return PlainTextResponse(body, media_type="text/csv", headers=headers)


@router.get("/by-code/{code}", response_model=ServiceEndpointRead, summary="Get a Service Endpoint by business code")
async def get_by_code(
    code: str, service: ServiceEndpointService = Depends(get_service), _: Principal = Depends(require_read)
) -> ServiceEndpointRead:
    return await service.get_by_code(code)


@router.head("/by-code/{code}", summary="Check whether a Service Endpoint code exists")
async def exists(
    code: str, service: ServiceEndpointService = Depends(get_service), _: Principal = Depends(require_read)
) -> Response:
    found = await service.exists(code)
    return Response(status_code=status.HTTP_200_OK if found else status.HTTP_404_NOT_FOUND)


@router.get("/{id}", response_model=ServiceEndpointRead, summary="Get a Service Endpoint by id")
async def get(
    id: UUID, service: ServiceEndpointService = Depends(get_service), _: Principal = Depends(require_read)
) -> ServiceEndpointRead:
    return await service.get(id)


@router.post("", response_model=ServiceEndpointRead, status_code=status.HTTP_201_CREATED, summary="Create a Service Endpoint")
async def create(
    payload: ServiceEndpointCreate,
    request: Request,
    response: Response,
    service: ServiceEndpointService = Depends(get_service),
    _: Principal = Depends(require_write),
) -> ServiceEndpointRead:
    created = await service.create(payload)
    response.headers["Location"] = f"{request.url.path}/{created.id}"
    return created


@router.put("/{id}", response_model=ServiceEndpointRead, summary="Update a Service Endpoint")
async def update(
    id: UUID,
    payload: ServiceEndpointUpdate,
    service: ServiceEndpointService = Depends(get_service),
    _: Principal = Depends(require_write),
) -> ServiceEndpointRead:
    return await service.update(id, payload)


@router.patch("/{id}/status", response_model=ServiceEndpointRead, summary="Change lifecycle status")
async def change_status(
    id: UUID,
    new_status: LifecycleStatus = Query(..., alias="status"),
    service: ServiceEndpointService = Depends(get_service),
    _: Principal = Depends(require_write),
) -> ServiceEndpointRead:
    return await service.change_status(id, new_status)


@router.delete("/{id}", status_code=status.HTTP_204_NO_CONTENT, summary="Delete a Service Endpoint")
async def delete(
    id: UUID, service: ServiceEndpointService = Depends(get_service), _: Principal = Depends(require_write)
) -> Response:
    await service.delete(id)
    return Response(status_code=status.HTTP_204_NO_CONTENT)


@router.post("/bulk", response_model=ServiceEndpointBulkResult, summary="Create many Service Endpoint records")
async def bulk_create(
    payloads: list[ServiceEndpointCreate],
    service: ServiceEndpointService = Depends(get_service),
    _: Principal = Depends(require_write),
) -> ServiceEndpointBulkResult:
    return await service.bulk_create(payloads)


@router.post("/reconcile", response_model=list[ReconciliationItem], summary="Reconcile external Service Endpoint records")
async def reconcile(
    records: list[dict[str, object]],
    apply: bool = Query(False, description="Apply the plan instead of a dry run"),
    service: ServiceEndpointService = Depends(get_service),
    _: Principal = Depends(require_write),
) -> list[ReconciliationItem]:
    return await service.reconcile(records, apply)


@router.get("/by-service-id/{service_id}", response_model=Page[ServiceEndpointRead], summary="List Service Endpoint records by Service Id")
async def list_by_service_id(
    service_id: UUID,
    page: int = Query(0, ge=0),
    size: int = Query(20, ge=1, le=200),
    service: ServiceEndpointService = Depends(get_service),
) -> Page[ServiceEndpointRead]:
    return await service.list_by_service_id(service_id, page, size)

@router.get("/by-site-id/{site_id}", response_model=Page[ServiceEndpointRead], summary="List Service Endpoint records by Site Id")
async def list_by_site_id(
    site_id: UUID,
    page: int = Query(0, ge=0),
    size: int = Query(20, ge=1, le=200),
    service: ServiceEndpointService = Depends(get_service),
) -> Page[ServiceEndpointRead]:
    return await service.list_by_site_id(site_id, page, size)

@router.get("/by-port-id/{port_id}", response_model=Page[ServiceEndpointRead], summary="List Service Endpoint records by Port Id")
async def list_by_port_id(
    port_id: UUID,
    page: int = Query(0, ge=0),
    size: int = Query(20, ge=1, le=200),
    service: ServiceEndpointService = Depends(get_service),
) -> Page[ServiceEndpointRead]:
    return await service.list_by_port_id(port_id, page, size)
