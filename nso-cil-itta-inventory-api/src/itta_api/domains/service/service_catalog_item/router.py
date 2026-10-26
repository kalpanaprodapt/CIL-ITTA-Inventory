"""HTTP routes for Service Catalog Item."""
from __future__ import annotations

from uuid import UUID

from fastapi import APIRouter, Depends, Query, Request, Response, status
from fastapi.responses import PlainTextResponse

from itta_core.auth import Principal, require_read, require_write
from itta_core.dependencies import service_provider
from itta_core.diffing import ReconciliationItem
from itta_core.models import EntityMeta, LifecycleStatus
from itta_core.pagination import Page
from itta_api.domains.service.service_catalog_item.filters import ServiceCatalogItemFilter
from itta_api.domains.service.service_catalog_item.meta import META
from itta_api.domains.service.service_catalog_item.schemas import (
    ServiceCatalogItemBulkResult,
    ServiceCatalogItemCreate,
    ServiceCatalogItemRead,
    ServiceCatalogItemSummary,
    ServiceCatalogItemUpdate,
)
from itta_api.domains.service.service_catalog_item.service import ServiceCatalogItemService

router = APIRouter(prefix="/api/v1/service-catalog-items", tags=["Service Catalog Item"])
get_service = service_provider("service_catalog_item")


@router.get("/meta", response_model=EntityMeta, summary="Field catalogue for Service Catalog Item")
async def meta() -> EntityMeta:
    return META


@router.get("", response_model=Page[ServiceCatalogItemRead], summary="Search Service Catalog Item records")
async def search(
    flt: ServiceCatalogItemFilter = Depends(),
    page: int = Query(0, ge=0),
    size: int = Query(20, ge=1, le=200),
    sort: str | None = Query(None, description="field,asc|desc"),
    service: ServiceCatalogItemService = Depends(get_service),
    _: Principal = Depends(require_read),
) -> Page[ServiceCatalogItemRead]:
    return await service.search(flt, page, size, sort)


@router.get("/summaries", response_model=list[ServiceCatalogItemSummary], summary="Pick-list of Service Catalog Item records")
async def summaries(
    status_filter: LifecycleStatus | None = Query(None, alias="status"),
    service: ServiceCatalogItemService = Depends(get_service),
    _: Principal = Depends(require_read),
) -> list[ServiceCatalogItemSummary]:
    return await service.summaries(status_filter)


@router.get("/stats/status", response_model=dict[str, int], summary="Counts per lifecycle status")
async def status_stats(
    service: ServiceCatalogItemService = Depends(get_service), _: Principal = Depends(require_read)
) -> dict[str, int]:
    return await service.stats()


@router.get("/export", response_class=PlainTextResponse, summary="Export Service Catalog Item records as CSV")
async def export(
    flt: ServiceCatalogItemFilter = Depends(),
    service: ServiceCatalogItemService = Depends(get_service),
    _: Principal = Depends(require_read),
) -> PlainTextResponse:
    body = await service.export_csv(flt)
    headers = {"Content-Disposition": 'attachment; filename="service-catalog-items.csv"'}
    return PlainTextResponse(body, media_type="text/csv", headers=headers)


@router.get("/by-code/{code}", response_model=ServiceCatalogItemRead, summary="Get a Service Catalog Item by business code")
async def get_by_code(
    code: str, service: ServiceCatalogItemService = Depends(get_service), _: Principal = Depends(require_read)
) -> ServiceCatalogItemRead:
    return await service.get_by_code(code)


@router.head("/by-code/{code}", summary="Check whether a Service Catalog Item code exists")
async def exists(
    code: str, service: ServiceCatalogItemService = Depends(get_service), _: Principal = Depends(require_read)
) -> Response:
    found = await service.exists(code)
    return Response(status_code=status.HTTP_200_OK if found else status.HTTP_404_NOT_FOUND)


@router.get("/{id}", response_model=ServiceCatalogItemRead, summary="Get a Service Catalog Item by id")
async def get(
    id: UUID, service: ServiceCatalogItemService = Depends(get_service), _: Principal = Depends(require_read)
) -> ServiceCatalogItemRead:
    return await service.get(id)


@router.post("", response_model=ServiceCatalogItemRead, status_code=status.HTTP_201_CREATED, summary="Create a Service Catalog Item")
async def create(
    payload: ServiceCatalogItemCreate,
    request: Request,
    response: Response,
    service: ServiceCatalogItemService = Depends(get_service),
    _: Principal = Depends(require_write),
) -> ServiceCatalogItemRead:
    created = await service.create(payload)
    response.headers["Location"] = f"{request.url.path}/{created.id}"
    return created


@router.put("/{id}", response_model=ServiceCatalogItemRead, summary="Update a Service Catalog Item")
async def update(
    id: UUID,
    payload: ServiceCatalogItemUpdate,
    service: ServiceCatalogItemService = Depends(get_service),
    _: Principal = Depends(require_write),
) -> ServiceCatalogItemRead:
    return await service.update(id, payload)


@router.patch("/{id}/status", response_model=ServiceCatalogItemRead, summary="Change lifecycle status")
async def change_status(
    id: UUID,
    new_status: LifecycleStatus = Query(..., alias="status"),
    service: ServiceCatalogItemService = Depends(get_service),
    _: Principal = Depends(require_write),
) -> ServiceCatalogItemRead:
    return await service.change_status(id, new_status)


@router.delete("/{id}", status_code=status.HTTP_204_NO_CONTENT, summary="Delete a Service Catalog Item")
async def delete(
    id: UUID, service: ServiceCatalogItemService = Depends(get_service), _: Principal = Depends(require_write)
) -> Response:
    await service.delete(id)
    return Response(status_code=status.HTTP_204_NO_CONTENT)


@router.post("/bulk", response_model=ServiceCatalogItemBulkResult, summary="Create many Service Catalog Item records")
async def bulk_create(
    payloads: list[ServiceCatalogItemCreate],
    service: ServiceCatalogItemService = Depends(get_service),
    _: Principal = Depends(require_write),
) -> ServiceCatalogItemBulkResult:
    return await service.bulk_create(payloads)


@router.post("/reconcile", response_model=list[ReconciliationItem], summary="Reconcile external Service Catalog Item records")
async def reconcile(
    records: list[dict[str, object]],
    apply: bool = Query(False, description="Apply the plan instead of a dry run"),
    service: ServiceCatalogItemService = Depends(get_service),
    _: Principal = Depends(require_write),
) -> list[ReconciliationItem]:
    return await service.reconcile(records, apply)

