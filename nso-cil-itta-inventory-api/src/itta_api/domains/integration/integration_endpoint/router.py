"""HTTP routes for Integration Endpoint."""
from __future__ import annotations

from uuid import UUID

from fastapi import APIRouter, Depends, Query, Request, Response, status
from fastapi.responses import PlainTextResponse

from itta_core.auth import Principal, require_read, require_write
from itta_core.dependencies import service_provider
from itta_core.diffing import ReconciliationItem
from itta_core.models import EntityMeta, LifecycleStatus
from itta_core.pagination import Page
from itta_api.domains.integration.integration_endpoint.filters import IntegrationEndpointFilter
from itta_api.domains.integration.integration_endpoint.meta import META
from itta_api.domains.integration.integration_endpoint.schemas import (
    IntegrationEndpointBulkResult,
    IntegrationEndpointCreate,
    IntegrationEndpointRead,
    IntegrationEndpointSummary,
    IntegrationEndpointUpdate,
)
from itta_api.domains.integration.integration_endpoint.service import IntegrationEndpointService

router = APIRouter(prefix="/api/v1/integration-endpoints", tags=["Integration Endpoint"])
get_service = service_provider("integration_endpoint")


@router.get("/meta", response_model=EntityMeta, summary="Field catalogue for Integration Endpoint")
async def meta() -> EntityMeta:
    return META


@router.get("", response_model=Page[IntegrationEndpointRead], summary="Search Integration Endpoint records")
async def search(
    flt: IntegrationEndpointFilter = Depends(),
    page: int = Query(0, ge=0),
    size: int = Query(20, ge=1, le=200),
    sort: str | None = Query(None, description="field,asc|desc"),
    service: IntegrationEndpointService = Depends(get_service),
    _: Principal = Depends(require_read),
) -> Page[IntegrationEndpointRead]:
    return await service.search(flt, page, size, sort)


@router.get("/summaries", response_model=list[IntegrationEndpointSummary], summary="Pick-list of Integration Endpoint records")
async def summaries(
    status_filter: LifecycleStatus | None = Query(None, alias="status"),
    service: IntegrationEndpointService = Depends(get_service),
    _: Principal = Depends(require_read),
) -> list[IntegrationEndpointSummary]:
    return await service.summaries(status_filter)


@router.get("/stats/status", response_model=dict[str, int], summary="Counts per lifecycle status")
async def status_stats(
    service: IntegrationEndpointService = Depends(get_service), _: Principal = Depends(require_read)
) -> dict[str, int]:
    return await service.stats()


@router.get("/export", response_class=PlainTextResponse, summary="Export Integration Endpoint records as CSV")
async def export(
    flt: IntegrationEndpointFilter = Depends(),
    service: IntegrationEndpointService = Depends(get_service),
    _: Principal = Depends(require_read),
) -> PlainTextResponse:
    body = await service.export_csv(flt)
    headers = {"Content-Disposition": 'attachment; filename="integration-endpoints.csv"'}
    return PlainTextResponse(body, media_type="text/csv", headers=headers)


@router.get("/by-code/{code}", response_model=IntegrationEndpointRead, summary="Get a Integration Endpoint by business code")
async def get_by_code(
    code: str, service: IntegrationEndpointService = Depends(get_service), _: Principal = Depends(require_read)
) -> IntegrationEndpointRead:
    return await service.get_by_code(code)


@router.head("/by-code/{code}", summary="Check whether a Integration Endpoint code exists")
async def exists(
    code: str, service: IntegrationEndpointService = Depends(get_service), _: Principal = Depends(require_read)
) -> Response:
    found = await service.exists(code)
    return Response(status_code=status.HTTP_200_OK if found else status.HTTP_404_NOT_FOUND)


@router.get("/{id}", response_model=IntegrationEndpointRead, summary="Get a Integration Endpoint by id")
async def get(
    id: UUID, service: IntegrationEndpointService = Depends(get_service), _: Principal = Depends(require_read)
) -> IntegrationEndpointRead:
    return await service.get(id)


@router.post("", response_model=IntegrationEndpointRead, status_code=status.HTTP_201_CREATED, summary="Create a Integration Endpoint")
async def create(
    payload: IntegrationEndpointCreate,
    request: Request,
    response: Response,
    service: IntegrationEndpointService = Depends(get_service),
    _: Principal = Depends(require_write),
) -> IntegrationEndpointRead:
    created = await service.create(payload)
    response.headers["Location"] = f"{request.url.path}/{created.id}"
    return created


@router.put("/{id}", response_model=IntegrationEndpointRead, summary="Update a Integration Endpoint")
async def update(
    id: UUID,
    payload: IntegrationEndpointUpdate,
    service: IntegrationEndpointService = Depends(get_service),
    _: Principal = Depends(require_write),
) -> IntegrationEndpointRead:
    return await service.update(id, payload)


@router.patch("/{id}/status", response_model=IntegrationEndpointRead, summary="Change lifecycle status")
async def change_status(
    id: UUID,
    new_status: LifecycleStatus = Query(..., alias="status"),
    service: IntegrationEndpointService = Depends(get_service),
    _: Principal = Depends(require_write),
) -> IntegrationEndpointRead:
    return await service.change_status(id, new_status)


@router.delete("/{id}", status_code=status.HTTP_204_NO_CONTENT, summary="Delete a Integration Endpoint")
async def delete(
    id: UUID, service: IntegrationEndpointService = Depends(get_service), _: Principal = Depends(require_write)
) -> Response:
    await service.delete(id)
    return Response(status_code=status.HTTP_204_NO_CONTENT)


@router.post("/bulk", response_model=IntegrationEndpointBulkResult, summary="Create many Integration Endpoint records")
async def bulk_create(
    payloads: list[IntegrationEndpointCreate],
    service: IntegrationEndpointService = Depends(get_service),
    _: Principal = Depends(require_write),
) -> IntegrationEndpointBulkResult:
    return await service.bulk_create(payloads)


@router.post("/reconcile", response_model=list[ReconciliationItem], summary="Reconcile external Integration Endpoint records")
async def reconcile(
    records: list[dict[str, object]],
    apply: bool = Query(False, description="Apply the plan instead of a dry run"),
    service: IntegrationEndpointService = Depends(get_service),
    _: Principal = Depends(require_write),
) -> list[ReconciliationItem]:
    return await service.reconcile(records, apply)

