"""HTTP routes for Service Order."""
from __future__ import annotations

from uuid import UUID

from fastapi import APIRouter, Depends, Query, Request, Response, status
from fastapi.responses import PlainTextResponse

from itta_core.auth import Principal, require_read, require_write
from itta_core.dependencies import service_provider
from itta_core.diffing import ReconciliationItem
from itta_core.models import EntityMeta, LifecycleStatus
from itta_core.pagination import Page
from itta_api.domains.service.service_order.filters import ServiceOrderFilter
from itta_api.domains.service.service_order.meta import META
from itta_api.domains.service.service_order.schemas import (
    ServiceOrderBulkResult,
    ServiceOrderCreate,
    ServiceOrderRead,
    ServiceOrderSummary,
    ServiceOrderUpdate,
)
from itta_api.domains.service.service_order.service import ServiceOrderService

router = APIRouter(prefix="/api/v1/service-orders", tags=["Service Order"])
get_service = service_provider("service_order")


@router.get("/meta", response_model=EntityMeta, summary="Field catalogue for Service Order")
async def meta() -> EntityMeta:
    return META


@router.get("", response_model=Page[ServiceOrderRead], summary="Search Service Order records")
async def search(
    flt: ServiceOrderFilter = Depends(),
    page: int = Query(0, ge=0),
    size: int = Query(20, ge=1, le=200),
    sort: str | None = Query(None, description="field,asc|desc"),
    service: ServiceOrderService = Depends(get_service),
    _: Principal = Depends(require_read),
) -> Page[ServiceOrderRead]:
    return await service.search(flt, page, size, sort)


@router.get("/summaries", response_model=list[ServiceOrderSummary], summary="Pick-list of Service Order records")
async def summaries(
    status_filter: LifecycleStatus | None = Query(None, alias="status"),
    service: ServiceOrderService = Depends(get_service),
    _: Principal = Depends(require_read),
) -> list[ServiceOrderSummary]:
    return await service.summaries(status_filter)


@router.get("/stats/status", response_model=dict[str, int], summary="Counts per lifecycle status")
async def status_stats(
    service: ServiceOrderService = Depends(get_service), _: Principal = Depends(require_read)
) -> dict[str, int]:
    return await service.stats()


@router.get("/export", response_class=PlainTextResponse, summary="Export Service Order records as CSV")
async def export(
    flt: ServiceOrderFilter = Depends(),
    service: ServiceOrderService = Depends(get_service),
    _: Principal = Depends(require_read),
) -> PlainTextResponse:
    body = await service.export_csv(flt)
    headers = {"Content-Disposition": 'attachment; filename="service-orders.csv"'}
    return PlainTextResponse(body, media_type="text/csv", headers=headers)


@router.get("/by-code/{code}", response_model=ServiceOrderRead, summary="Get a Service Order by business code")
async def get_by_code(
    code: str, service: ServiceOrderService = Depends(get_service), _: Principal = Depends(require_read)
) -> ServiceOrderRead:
    return await service.get_by_code(code)


@router.head("/by-code/{code}", summary="Check whether a Service Order code exists")
async def exists(
    code: str, service: ServiceOrderService = Depends(get_service), _: Principal = Depends(require_read)
) -> Response:
    found = await service.exists(code)
    return Response(status_code=status.HTTP_200_OK if found else status.HTTP_404_NOT_FOUND)


@router.get("/{id}", response_model=ServiceOrderRead, summary="Get a Service Order by id")
async def get(
    id: UUID, service: ServiceOrderService = Depends(get_service), _: Principal = Depends(require_read)
) -> ServiceOrderRead:
    return await service.get(id)


@router.post("", response_model=ServiceOrderRead, status_code=status.HTTP_201_CREATED, summary="Create a Service Order")
async def create(
    payload: ServiceOrderCreate,
    request: Request,
    response: Response,
    service: ServiceOrderService = Depends(get_service),
    _: Principal = Depends(require_write),
) -> ServiceOrderRead:
    created = await service.create(payload)
    response.headers["Location"] = f"{request.url.path}/{created.id}"
    return created


@router.put("/{id}", response_model=ServiceOrderRead, summary="Update a Service Order")
async def update(
    id: UUID,
    payload: ServiceOrderUpdate,
    service: ServiceOrderService = Depends(get_service),
    _: Principal = Depends(require_write),
) -> ServiceOrderRead:
    return await service.update(id, payload)


@router.patch("/{id}/status", response_model=ServiceOrderRead, summary="Change lifecycle status")
async def change_status(
    id: UUID,
    new_status: LifecycleStatus = Query(..., alias="status"),
    service: ServiceOrderService = Depends(get_service),
    _: Principal = Depends(require_write),
) -> ServiceOrderRead:
    return await service.change_status(id, new_status)


@router.delete("/{id}", status_code=status.HTTP_204_NO_CONTENT, summary="Delete a Service Order")
async def delete(
    id: UUID, service: ServiceOrderService = Depends(get_service), _: Principal = Depends(require_write)
) -> Response:
    await service.delete(id)
    return Response(status_code=status.HTTP_204_NO_CONTENT)


@router.post("/bulk", response_model=ServiceOrderBulkResult, summary="Create many Service Order records")
async def bulk_create(
    payloads: list[ServiceOrderCreate],
    service: ServiceOrderService = Depends(get_service),
    _: Principal = Depends(require_write),
) -> ServiceOrderBulkResult:
    return await service.bulk_create(payloads)


@router.post("/reconcile", response_model=list[ReconciliationItem], summary="Reconcile external Service Order records")
async def reconcile(
    records: list[dict[str, object]],
    apply: bool = Query(False, description="Apply the plan instead of a dry run"),
    service: ServiceOrderService = Depends(get_service),
    _: Principal = Depends(require_write),
) -> list[ReconciliationItem]:
    return await service.reconcile(records, apply)


@router.get("/by-customer-id/{customer_id}", response_model=Page[ServiceOrderRead], summary="List Service Order records by Customer Id")
async def list_by_customer_id(
    customer_id: UUID,
    page: int = Query(0, ge=0),
    size: int = Query(20, ge=1, le=200),
    service: ServiceOrderService = Depends(get_service),
) -> Page[ServiceOrderRead]:
    return await service.list_by_customer_id(customer_id, page, size)
