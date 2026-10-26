"""HTTP routes for Work Order."""
from __future__ import annotations

from uuid import UUID

from fastapi import APIRouter, Depends, Query, Request, Response, status
from fastapi.responses import PlainTextResponse

from itta_core.auth import Principal, require_read, require_write
from itta_core.dependencies import service_provider
from itta_core.diffing import ReconciliationItem
from itta_core.models import EntityMeta, LifecycleStatus
from itta_core.pagination import Page
from itta_api.domains.change.work_order.filters import WorkOrderFilter
from itta_api.domains.change.work_order.meta import META
from itta_api.domains.change.work_order.schemas import (
    WorkOrderBulkResult,
    WorkOrderCreate,
    WorkOrderRead,
    WorkOrderSummary,
    WorkOrderUpdate,
)
from itta_api.domains.change.work_order.service import WorkOrderService

router = APIRouter(prefix="/api/v1/work-orders", tags=["Work Order"])
get_service = service_provider("work_order")


@router.get("/meta", response_model=EntityMeta, summary="Field catalogue for Work Order")
async def meta() -> EntityMeta:
    return META


@router.get("", response_model=Page[WorkOrderRead], summary="Search Work Order records")
async def search(
    flt: WorkOrderFilter = Depends(),
    page: int = Query(0, ge=0),
    size: int = Query(20, ge=1, le=200),
    sort: str | None = Query(None, description="field,asc|desc"),
    service: WorkOrderService = Depends(get_service),
    _: Principal = Depends(require_read),
) -> Page[WorkOrderRead]:
    return await service.search(flt, page, size, sort)


@router.get("/summaries", response_model=list[WorkOrderSummary], summary="Pick-list of Work Order records")
async def summaries(
    status_filter: LifecycleStatus | None = Query(None, alias="status"),
    service: WorkOrderService = Depends(get_service),
    _: Principal = Depends(require_read),
) -> list[WorkOrderSummary]:
    return await service.summaries(status_filter)


@router.get("/stats/status", response_model=dict[str, int], summary="Counts per lifecycle status")
async def status_stats(
    service: WorkOrderService = Depends(get_service), _: Principal = Depends(require_read)
) -> dict[str, int]:
    return await service.stats()


@router.get("/export", response_class=PlainTextResponse, summary="Export Work Order records as CSV")
async def export(
    flt: WorkOrderFilter = Depends(),
    service: WorkOrderService = Depends(get_service),
    _: Principal = Depends(require_read),
) -> PlainTextResponse:
    body = await service.export_csv(flt)
    headers = {"Content-Disposition": 'attachment; filename="work-orders.csv"'}
    return PlainTextResponse(body, media_type="text/csv", headers=headers)


@router.get("/by-code/{code}", response_model=WorkOrderRead, summary="Get a Work Order by business code")
async def get_by_code(
    code: str, service: WorkOrderService = Depends(get_service), _: Principal = Depends(require_read)
) -> WorkOrderRead:
    return await service.get_by_code(code)


@router.head("/by-code/{code}", summary="Check whether a Work Order code exists")
async def exists(
    code: str, service: WorkOrderService = Depends(get_service), _: Principal = Depends(require_read)
) -> Response:
    found = await service.exists(code)
    return Response(status_code=status.HTTP_200_OK if found else status.HTTP_404_NOT_FOUND)


@router.get("/{id}", response_model=WorkOrderRead, summary="Get a Work Order by id")
async def get(
    id: UUID, service: WorkOrderService = Depends(get_service), _: Principal = Depends(require_read)
) -> WorkOrderRead:
    return await service.get(id)


@router.post("", response_model=WorkOrderRead, status_code=status.HTTP_201_CREATED, summary="Create a Work Order")
async def create(
    payload: WorkOrderCreate,
    request: Request,
    response: Response,
    service: WorkOrderService = Depends(get_service),
    _: Principal = Depends(require_write),
) -> WorkOrderRead:
    created = await service.create(payload)
    response.headers["Location"] = f"{request.url.path}/{created.id}"
    return created


@router.put("/{id}", response_model=WorkOrderRead, summary="Update a Work Order")
async def update(
    id: UUID,
    payload: WorkOrderUpdate,
    service: WorkOrderService = Depends(get_service),
    _: Principal = Depends(require_write),
) -> WorkOrderRead:
    return await service.update(id, payload)


@router.patch("/{id}/status", response_model=WorkOrderRead, summary="Change lifecycle status")
async def change_status(
    id: UUID,
    new_status: LifecycleStatus = Query(..., alias="status"),
    service: WorkOrderService = Depends(get_service),
    _: Principal = Depends(require_write),
) -> WorkOrderRead:
    return await service.change_status(id, new_status)


@router.delete("/{id}", status_code=status.HTTP_204_NO_CONTENT, summary="Delete a Work Order")
async def delete(
    id: UUID, service: WorkOrderService = Depends(get_service), _: Principal = Depends(require_write)
) -> Response:
    await service.delete(id)
    return Response(status_code=status.HTTP_204_NO_CONTENT)


@router.post("/bulk", response_model=WorkOrderBulkResult, summary="Create many Work Order records")
async def bulk_create(
    payloads: list[WorkOrderCreate],
    service: WorkOrderService = Depends(get_service),
    _: Principal = Depends(require_write),
) -> WorkOrderBulkResult:
    return await service.bulk_create(payloads)


@router.post("/reconcile", response_model=list[ReconciliationItem], summary="Reconcile external Work Order records")
async def reconcile(
    records: list[dict[str, object]],
    apply: bool = Query(False, description="Apply the plan instead of a dry run"),
    service: WorkOrderService = Depends(get_service),
    _: Principal = Depends(require_write),
) -> list[ReconciliationItem]:
    return await service.reconcile(records, apply)


@router.get("/by-assigned-technician-id/{assigned_technician_id}", response_model=Page[WorkOrderRead], summary="List Work Order records by Assigned Technician Id")
async def list_by_assigned_technician_id(
    assigned_technician_id: UUID,
    page: int = Query(0, ge=0),
    size: int = Query(20, ge=1, le=200),
    service: WorkOrderService = Depends(get_service),
) -> Page[WorkOrderRead]:
    return await service.list_by_assigned_technician_id(assigned_technician_id, page, size)
