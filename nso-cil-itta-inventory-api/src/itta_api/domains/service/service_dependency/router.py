"""HTTP routes for Service Dependency."""
from __future__ import annotations

from uuid import UUID

from fastapi import APIRouter, Depends, Query, Request, Response, status
from fastapi.responses import PlainTextResponse

from itta_core.auth import Principal, require_read, require_write
from itta_core.dependencies import service_provider
from itta_core.diffing import ReconciliationItem
from itta_core.models import EntityMeta, LifecycleStatus
from itta_core.pagination import Page
from itta_api.domains.service.service_dependency.filters import ServiceDependencyFilter
from itta_api.domains.service.service_dependency.meta import META
from itta_api.domains.service.service_dependency.schemas import (
    ServiceDependencyBulkResult,
    ServiceDependencyCreate,
    ServiceDependencyRead,
    ServiceDependencySummary,
    ServiceDependencyUpdate,
)
from itta_api.domains.service.service_dependency.service import ServiceDependencyService

router = APIRouter(prefix="/api/v1/service-dependencies", tags=["Service Dependency"])
get_service = service_provider("service_dependency")


@router.get("/meta", response_model=EntityMeta, summary="Field catalogue for Service Dependency")
async def meta() -> EntityMeta:
    return META


@router.get("", response_model=Page[ServiceDependencyRead], summary="Search Service Dependency records")
async def search(
    flt: ServiceDependencyFilter = Depends(),
    page: int = Query(0, ge=0),
    size: int = Query(20, ge=1, le=200),
    sort: str | None = Query(None, description="field,asc|desc"),
    service: ServiceDependencyService = Depends(get_service),
    _: Principal = Depends(require_read),
) -> Page[ServiceDependencyRead]:
    return await service.search(flt, page, size, sort)


@router.get("/summaries", response_model=list[ServiceDependencySummary], summary="Pick-list of Service Dependency records")
async def summaries(
    status_filter: LifecycleStatus | None = Query(None, alias="status"),
    service: ServiceDependencyService = Depends(get_service),
    _: Principal = Depends(require_read),
) -> list[ServiceDependencySummary]:
    return await service.summaries(status_filter)


@router.get("/stats/status", response_model=dict[str, int], summary="Counts per lifecycle status")
async def status_stats(
    service: ServiceDependencyService = Depends(get_service), _: Principal = Depends(require_read)
) -> dict[str, int]:
    return await service.stats()


@router.get("/export", response_class=PlainTextResponse, summary="Export Service Dependency records as CSV")
async def export(
    flt: ServiceDependencyFilter = Depends(),
    service: ServiceDependencyService = Depends(get_service),
    _: Principal = Depends(require_read),
) -> PlainTextResponse:
    body = await service.export_csv(flt)
    headers = {"Content-Disposition": 'attachment; filename="service-dependencies.csv"'}
    return PlainTextResponse(body, media_type="text/csv", headers=headers)


@router.get("/by-code/{code}", response_model=ServiceDependencyRead, summary="Get a Service Dependency by business code")
async def get_by_code(
    code: str, service: ServiceDependencyService = Depends(get_service), _: Principal = Depends(require_read)
) -> ServiceDependencyRead:
    return await service.get_by_code(code)


@router.head("/by-code/{code}", summary="Check whether a Service Dependency code exists")
async def exists(
    code: str, service: ServiceDependencyService = Depends(get_service), _: Principal = Depends(require_read)
) -> Response:
    found = await service.exists(code)
    return Response(status_code=status.HTTP_200_OK if found else status.HTTP_404_NOT_FOUND)


@router.get("/{id}", response_model=ServiceDependencyRead, summary="Get a Service Dependency by id")
async def get(
    id: UUID, service: ServiceDependencyService = Depends(get_service), _: Principal = Depends(require_read)
) -> ServiceDependencyRead:
    return await service.get(id)


@router.post("", response_model=ServiceDependencyRead, status_code=status.HTTP_201_CREATED, summary="Create a Service Dependency")
async def create(
    payload: ServiceDependencyCreate,
    request: Request,
    response: Response,
    service: ServiceDependencyService = Depends(get_service),
    _: Principal = Depends(require_write),
) -> ServiceDependencyRead:
    created = await service.create(payload)
    response.headers["Location"] = f"{request.url.path}/{created.id}"
    return created


@router.put("/{id}", response_model=ServiceDependencyRead, summary="Update a Service Dependency")
async def update(
    id: UUID,
    payload: ServiceDependencyUpdate,
    service: ServiceDependencyService = Depends(get_service),
    _: Principal = Depends(require_write),
) -> ServiceDependencyRead:
    return await service.update(id, payload)


@router.patch("/{id}/status", response_model=ServiceDependencyRead, summary="Change lifecycle status")
async def change_status(
    id: UUID,
    new_status: LifecycleStatus = Query(..., alias="status"),
    service: ServiceDependencyService = Depends(get_service),
    _: Principal = Depends(require_write),
) -> ServiceDependencyRead:
    return await service.change_status(id, new_status)


@router.delete("/{id}", status_code=status.HTTP_204_NO_CONTENT, summary="Delete a Service Dependency")
async def delete(
    id: UUID, service: ServiceDependencyService = Depends(get_service), _: Principal = Depends(require_write)
) -> Response:
    await service.delete(id)
    return Response(status_code=status.HTTP_204_NO_CONTENT)


@router.post("/bulk", response_model=ServiceDependencyBulkResult, summary="Create many Service Dependency records")
async def bulk_create(
    payloads: list[ServiceDependencyCreate],
    service: ServiceDependencyService = Depends(get_service),
    _: Principal = Depends(require_write),
) -> ServiceDependencyBulkResult:
    return await service.bulk_create(payloads)


@router.post("/reconcile", response_model=list[ReconciliationItem], summary="Reconcile external Service Dependency records")
async def reconcile(
    records: list[dict[str, object]],
    apply: bool = Query(False, description="Apply the plan instead of a dry run"),
    service: ServiceDependencyService = Depends(get_service),
    _: Principal = Depends(require_write),
) -> list[ReconciliationItem]:
    return await service.reconcile(records, apply)


@router.get("/by-service-id/{service_id}", response_model=Page[ServiceDependencyRead], summary="List Service Dependency records by Service Id")
async def list_by_service_id(
    service_id: UUID,
    page: int = Query(0, ge=0),
    size: int = Query(20, ge=1, le=200),
    service: ServiceDependencyService = Depends(get_service),
) -> Page[ServiceDependencyRead]:
    return await service.list_by_service_id(service_id, page, size)

@router.get("/by-depends-on-id/{depends_on_id}", response_model=Page[ServiceDependencyRead], summary="List Service Dependency records by Depends On Id")
async def list_by_depends_on_id(
    depends_on_id: UUID,
    page: int = Query(0, ge=0),
    size: int = Query(20, ge=1, le=200),
    service: ServiceDependencyService = Depends(get_service),
) -> Page[ServiceDependencyRead]:
    return await service.list_by_depends_on_id(depends_on_id, page, size)
