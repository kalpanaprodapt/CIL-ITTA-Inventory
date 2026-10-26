"""HTTP routes for Purchase Order."""
from __future__ import annotations

from uuid import UUID

from fastapi import APIRouter, Depends, Query, Request, Response, status
from fastapi.responses import PlainTextResponse

from itta_core.auth import Principal, require_read, require_write
from itta_core.dependencies import service_provider
from itta_core.diffing import ReconciliationItem
from itta_core.models import EntityMeta, LifecycleStatus
from itta_core.pagination import Page
from itta_api.domains.assetmgmt.purchase_order.filters import PurchaseOrderFilter
from itta_api.domains.assetmgmt.purchase_order.meta import META
from itta_api.domains.assetmgmt.purchase_order.schemas import (
    PurchaseOrderBulkResult,
    PurchaseOrderCreate,
    PurchaseOrderRead,
    PurchaseOrderSummary,
    PurchaseOrderUpdate,
)
from itta_api.domains.assetmgmt.purchase_order.service import PurchaseOrderService

router = APIRouter(prefix="/api/v1/purchase-orders", tags=["Purchase Order"])
get_service = service_provider("purchase_order")


@router.get("/meta", response_model=EntityMeta, summary="Field catalogue for Purchase Order")
async def meta() -> EntityMeta:
    return META


@router.get("", response_model=Page[PurchaseOrderRead], summary="Search Purchase Order records")
async def search(
    flt: PurchaseOrderFilter = Depends(),
    page: int = Query(0, ge=0),
    size: int = Query(20, ge=1, le=200),
    sort: str | None = Query(None, description="field,asc|desc"),
    service: PurchaseOrderService = Depends(get_service),
    _: Principal = Depends(require_read),
) -> Page[PurchaseOrderRead]:
    return await service.search(flt, page, size, sort)


@router.get("/summaries", response_model=list[PurchaseOrderSummary], summary="Pick-list of Purchase Order records")
async def summaries(
    status_filter: LifecycleStatus | None = Query(None, alias="status"),
    service: PurchaseOrderService = Depends(get_service),
    _: Principal = Depends(require_read),
) -> list[PurchaseOrderSummary]:
    return await service.summaries(status_filter)


@router.get("/stats/status", response_model=dict[str, int], summary="Counts per lifecycle status")
async def status_stats(
    service: PurchaseOrderService = Depends(get_service), _: Principal = Depends(require_read)
) -> dict[str, int]:
    return await service.stats()


@router.get("/export", response_class=PlainTextResponse, summary="Export Purchase Order records as CSV")
async def export(
    flt: PurchaseOrderFilter = Depends(),
    service: PurchaseOrderService = Depends(get_service),
    _: Principal = Depends(require_read),
) -> PlainTextResponse:
    body = await service.export_csv(flt)
    headers = {"Content-Disposition": 'attachment; filename="purchase-orders.csv"'}
    return PlainTextResponse(body, media_type="text/csv", headers=headers)


@router.get("/by-code/{code}", response_model=PurchaseOrderRead, summary="Get a Purchase Order by business code")
async def get_by_code(
    code: str, service: PurchaseOrderService = Depends(get_service), _: Principal = Depends(require_read)
) -> PurchaseOrderRead:
    return await service.get_by_code(code)


@router.head("/by-code/{code}", summary="Check whether a Purchase Order code exists")
async def exists(
    code: str, service: PurchaseOrderService = Depends(get_service), _: Principal = Depends(require_read)
) -> Response:
    found = await service.exists(code)
    return Response(status_code=status.HTTP_200_OK if found else status.HTTP_404_NOT_FOUND)


@router.get("/{id}", response_model=PurchaseOrderRead, summary="Get a Purchase Order by id")
async def get(
    id: UUID, service: PurchaseOrderService = Depends(get_service), _: Principal = Depends(require_read)
) -> PurchaseOrderRead:
    return await service.get(id)


@router.post("", response_model=PurchaseOrderRead, status_code=status.HTTP_201_CREATED, summary="Create a Purchase Order")
async def create(
    payload: PurchaseOrderCreate,
    request: Request,
    response: Response,
    service: PurchaseOrderService = Depends(get_service),
    _: Principal = Depends(require_write),
) -> PurchaseOrderRead:
    created = await service.create(payload)
    response.headers["Location"] = f"{request.url.path}/{created.id}"
    return created


@router.put("/{id}", response_model=PurchaseOrderRead, summary="Update a Purchase Order")
async def update(
    id: UUID,
    payload: PurchaseOrderUpdate,
    service: PurchaseOrderService = Depends(get_service),
    _: Principal = Depends(require_write),
) -> PurchaseOrderRead:
    return await service.update(id, payload)


@router.patch("/{id}/status", response_model=PurchaseOrderRead, summary="Change lifecycle status")
async def change_status(
    id: UUID,
    new_status: LifecycleStatus = Query(..., alias="status"),
    service: PurchaseOrderService = Depends(get_service),
    _: Principal = Depends(require_write),
) -> PurchaseOrderRead:
    return await service.change_status(id, new_status)


@router.delete("/{id}", status_code=status.HTTP_204_NO_CONTENT, summary="Delete a Purchase Order")
async def delete(
    id: UUID, service: PurchaseOrderService = Depends(get_service), _: Principal = Depends(require_write)
) -> Response:
    await service.delete(id)
    return Response(status_code=status.HTTP_204_NO_CONTENT)


@router.post("/bulk", response_model=PurchaseOrderBulkResult, summary="Create many Purchase Order records")
async def bulk_create(
    payloads: list[PurchaseOrderCreate],
    service: PurchaseOrderService = Depends(get_service),
    _: Principal = Depends(require_write),
) -> PurchaseOrderBulkResult:
    return await service.bulk_create(payloads)


@router.post("/reconcile", response_model=list[ReconciliationItem], summary="Reconcile external Purchase Order records")
async def reconcile(
    records: list[dict[str, object]],
    apply: bool = Query(False, description="Apply the plan instead of a dry run"),
    service: PurchaseOrderService = Depends(get_service),
    _: Principal = Depends(require_write),
) -> list[ReconciliationItem]:
    return await service.reconcile(records, apply)


@router.get("/by-vendor-id/{vendor_id}", response_model=Page[PurchaseOrderRead], summary="List Purchase Order records by Vendor Id")
async def list_by_vendor_id(
    vendor_id: UUID,
    page: int = Query(0, ge=0),
    size: int = Query(20, ge=1, le=200),
    service: PurchaseOrderService = Depends(get_service),
) -> Page[PurchaseOrderRead]:
    return await service.list_by_vendor_id(vendor_id, page, size)
