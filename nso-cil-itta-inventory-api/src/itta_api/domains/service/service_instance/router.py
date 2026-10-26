"""HTTP routes for Service Instance."""
from __future__ import annotations

from uuid import UUID

from fastapi import APIRouter, Depends, Query, Request, Response, status
from fastapi.responses import PlainTextResponse

from itta_core.auth import Principal, require_read, require_write
from itta_core.dependencies import service_provider
from itta_core.diffing import ReconciliationItem
from itta_core.models import EntityMeta, LifecycleStatus
from itta_core.pagination import Page
from itta_api.domains.service.service_instance.filters import ServiceInstanceFilter
from itta_api.domains.service.service_instance.meta import META
from itta_api.domains.service.service_instance.schemas import (
    ServiceInstanceBulkResult,
    ServiceInstanceCreate,
    ServiceInstanceRead,
    ServiceInstanceSummary,
    ServiceInstanceUpdate,
)
from itta_api.domains.service.service_instance.service import ServiceInstanceService

router = APIRouter(prefix="/api/v1/service-instances", tags=["Service Instance"])
get_service = service_provider("service_instance")


@router.get("/meta", response_model=EntityMeta, summary="Field catalogue for Service Instance")
async def meta() -> EntityMeta:
    return META


@router.get("", response_model=Page[ServiceInstanceRead], summary="Search Service Instance records")
async def search(
    flt: ServiceInstanceFilter = Depends(),
    page: int = Query(0, ge=0),
    size: int = Query(20, ge=1, le=200),
    sort: str | None = Query(None, description="field,asc|desc"),
    service: ServiceInstanceService = Depends(get_service),
    _: Principal = Depends(require_read),
) -> Page[ServiceInstanceRead]:
    return await service.search(flt, page, size, sort)


@router.get("/summaries", response_model=list[ServiceInstanceSummary], summary="Pick-list of Service Instance records")
async def summaries(
    status_filter: LifecycleStatus | None = Query(None, alias="status"),
    service: ServiceInstanceService = Depends(get_service),
    _: Principal = Depends(require_read),
) -> list[ServiceInstanceSummary]:
    return await service.summaries(status_filter)


@router.get("/stats/status", response_model=dict[str, int], summary="Counts per lifecycle status")
async def status_stats(
    service: ServiceInstanceService = Depends(get_service), _: Principal = Depends(require_read)
) -> dict[str, int]:
    return await service.stats()


@router.get("/export", response_class=PlainTextResponse, summary="Export Service Instance records as CSV")
async def export(
    flt: ServiceInstanceFilter = Depends(),
    service: ServiceInstanceService = Depends(get_service),
    _: Principal = Depends(require_read),
) -> PlainTextResponse:
    body = await service.export_csv(flt)
    headers = {"Content-Disposition": 'attachment; filename="service-instances.csv"'}
    return PlainTextResponse(body, media_type="text/csv", headers=headers)


@router.get("/by-code/{code}", response_model=ServiceInstanceRead, summary="Get a Service Instance by business code")
async def get_by_code(
    code: str, service: ServiceInstanceService = Depends(get_service), _: Principal = Depends(require_read)
) -> ServiceInstanceRead:
    return await service.get_by_code(code)


@router.head("/by-code/{code}", summary="Check whether a Service Instance code exists")
async def exists(
    code: str, service: ServiceInstanceService = Depends(get_service), _: Principal = Depends(require_read)
) -> Response:
    found = await service.exists(code)
    return Response(status_code=status.HTTP_200_OK if found else status.HTTP_404_NOT_FOUND)


@router.get("/{id}", response_model=ServiceInstanceRead, summary="Get a Service Instance by id")
async def get(
    id: UUID, service: ServiceInstanceService = Depends(get_service), _: Principal = Depends(require_read)
) -> ServiceInstanceRead:
    return await service.get(id)


@router.post("", response_model=ServiceInstanceRead, status_code=status.HTTP_201_CREATED, summary="Create a Service Instance")
async def create(
    payload: ServiceInstanceCreate,
    request: Request,
    response: Response,
    service: ServiceInstanceService = Depends(get_service),
    _: Principal = Depends(require_write),
) -> ServiceInstanceRead:
    created = await service.create(payload)
    response.headers["Location"] = f"{request.url.path}/{created.id}"
    return created


@router.put("/{id}", response_model=ServiceInstanceRead, summary="Update a Service Instance")
async def update(
    id: UUID,
    payload: ServiceInstanceUpdate,
    service: ServiceInstanceService = Depends(get_service),
    _: Principal = Depends(require_write),
) -> ServiceInstanceRead:
    return await service.update(id, payload)


@router.patch("/{id}/status", response_model=ServiceInstanceRead, summary="Change lifecycle status")
async def change_status(
    id: UUID,
    new_status: LifecycleStatus = Query(..., alias="status"),
    service: ServiceInstanceService = Depends(get_service),
    _: Principal = Depends(require_write),
) -> ServiceInstanceRead:
    return await service.change_status(id, new_status)


@router.delete("/{id}", status_code=status.HTTP_204_NO_CONTENT, summary="Delete a Service Instance")
async def delete(
    id: UUID, service: ServiceInstanceService = Depends(get_service), _: Principal = Depends(require_write)
) -> Response:
    await service.delete(id)
    return Response(status_code=status.HTTP_204_NO_CONTENT)


@router.post("/bulk", response_model=ServiceInstanceBulkResult, summary="Create many Service Instance records")
async def bulk_create(
    payloads: list[ServiceInstanceCreate],
    service: ServiceInstanceService = Depends(get_service),
    _: Principal = Depends(require_write),
) -> ServiceInstanceBulkResult:
    return await service.bulk_create(payloads)


@router.post("/reconcile", response_model=list[ReconciliationItem], summary="Reconcile external Service Instance records")
async def reconcile(
    records: list[dict[str, object]],
    apply: bool = Query(False, description="Apply the plan instead of a dry run"),
    service: ServiceInstanceService = Depends(get_service),
    _: Principal = Depends(require_write),
) -> list[ReconciliationItem]:
    return await service.reconcile(records, apply)


@router.get("/by-catalog-item-id/{catalog_item_id}", response_model=Page[ServiceInstanceRead], summary="List Service Instance records by Catalog Item Id")
async def list_by_catalog_item_id(
    catalog_item_id: UUID,
    page: int = Query(0, ge=0),
    size: int = Query(20, ge=1, le=200),
    service: ServiceInstanceService = Depends(get_service),
) -> Page[ServiceInstanceRead]:
    return await service.list_by_catalog_item_id(catalog_item_id, page, size)

@router.get("/by-customer-id/{customer_id}", response_model=Page[ServiceInstanceRead], summary="List Service Instance records by Customer Id")
async def list_by_customer_id(
    customer_id: UUID,
    page: int = Query(0, ge=0),
    size: int = Query(20, ge=1, le=200),
    service: ServiceInstanceService = Depends(get_service),
) -> Page[ServiceInstanceRead]:
    return await service.list_by_customer_id(customer_id, page, size)
