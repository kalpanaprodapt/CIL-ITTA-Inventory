"""HTTP routes for Field Task."""
from __future__ import annotations

from uuid import UUID

from fastapi import APIRouter, Depends, Query, Request, Response, status
from fastapi.responses import PlainTextResponse

from itta_core.auth import Principal, require_read, require_write
from itta_core.dependencies import service_provider
from itta_core.diffing import ReconciliationItem
from itta_core.models import EntityMeta, LifecycleStatus
from itta_core.pagination import Page
from itta_api.domains.change.field_task.filters import FieldTaskFilter
from itta_api.domains.change.field_task.meta import META
from itta_api.domains.change.field_task.schemas import (
    FieldTaskBulkResult,
    FieldTaskCreate,
    FieldTaskRead,
    FieldTaskSummary,
    FieldTaskUpdate,
)
from itta_api.domains.change.field_task.service import FieldTaskService

router = APIRouter(prefix="/api/v1/field-tasks", tags=["Field Task"])
get_service = service_provider("field_task")


@router.get("/meta", response_model=EntityMeta, summary="Field catalogue for Field Task")
async def meta() -> EntityMeta:
    return META


@router.get("", response_model=Page[FieldTaskRead], summary="Search Field Task records")
async def search(
    flt: FieldTaskFilter = Depends(),
    page: int = Query(0, ge=0),
    size: int = Query(20, ge=1, le=200),
    sort: str | None = Query(None, description="field,asc|desc"),
    service: FieldTaskService = Depends(get_service),
    _: Principal = Depends(require_read),
) -> Page[FieldTaskRead]:
    return await service.search(flt, page, size, sort)


@router.get("/summaries", response_model=list[FieldTaskSummary], summary="Pick-list of Field Task records")
async def summaries(
    status_filter: LifecycleStatus | None = Query(None, alias="status"),
    service: FieldTaskService = Depends(get_service),
    _: Principal = Depends(require_read),
) -> list[FieldTaskSummary]:
    return await service.summaries(status_filter)


@router.get("/stats/status", response_model=dict[str, int], summary="Counts per lifecycle status")
async def status_stats(
    service: FieldTaskService = Depends(get_service), _: Principal = Depends(require_read)
) -> dict[str, int]:
    return await service.stats()


@router.get("/export", response_class=PlainTextResponse, summary="Export Field Task records as CSV")
async def export(
    flt: FieldTaskFilter = Depends(),
    service: FieldTaskService = Depends(get_service),
    _: Principal = Depends(require_read),
) -> PlainTextResponse:
    body = await service.export_csv(flt)
    headers = {"Content-Disposition": 'attachment; filename="field-tasks.csv"'}
    return PlainTextResponse(body, media_type="text/csv", headers=headers)


@router.get("/by-code/{code}", response_model=FieldTaskRead, summary="Get a Field Task by business code")
async def get_by_code(
    code: str, service: FieldTaskService = Depends(get_service), _: Principal = Depends(require_read)
) -> FieldTaskRead:
    return await service.get_by_code(code)


@router.head("/by-code/{code}", summary="Check whether a Field Task code exists")
async def exists(
    code: str, service: FieldTaskService = Depends(get_service), _: Principal = Depends(require_read)
) -> Response:
    found = await service.exists(code)
    return Response(status_code=status.HTTP_200_OK if found else status.HTTP_404_NOT_FOUND)


@router.get("/{id}", response_model=FieldTaskRead, summary="Get a Field Task by id")
async def get(
    id: UUID, service: FieldTaskService = Depends(get_service), _: Principal = Depends(require_read)
) -> FieldTaskRead:
    return await service.get(id)


@router.post("", response_model=FieldTaskRead, status_code=status.HTTP_201_CREATED, summary="Create a Field Task")
async def create(
    payload: FieldTaskCreate,
    request: Request,
    response: Response,
    service: FieldTaskService = Depends(get_service),
    _: Principal = Depends(require_write),
) -> FieldTaskRead:
    created = await service.create(payload)
    response.headers["Location"] = f"{request.url.path}/{created.id}"
    return created


@router.put("/{id}", response_model=FieldTaskRead, summary="Update a Field Task")
async def update(
    id: UUID,
    payload: FieldTaskUpdate,
    service: FieldTaskService = Depends(get_service),
    _: Principal = Depends(require_write),
) -> FieldTaskRead:
    return await service.update(id, payload)


@router.patch("/{id}/status", response_model=FieldTaskRead, summary="Change lifecycle status")
async def change_status(
    id: UUID,
    new_status: LifecycleStatus = Query(..., alias="status"),
    service: FieldTaskService = Depends(get_service),
    _: Principal = Depends(require_write),
) -> FieldTaskRead:
    return await service.change_status(id, new_status)


@router.delete("/{id}", status_code=status.HTTP_204_NO_CONTENT, summary="Delete a Field Task")
async def delete(
    id: UUID, service: FieldTaskService = Depends(get_service), _: Principal = Depends(require_write)
) -> Response:
    await service.delete(id)
    return Response(status_code=status.HTTP_204_NO_CONTENT)


@router.post("/bulk", response_model=FieldTaskBulkResult, summary="Create many Field Task records")
async def bulk_create(
    payloads: list[FieldTaskCreate],
    service: FieldTaskService = Depends(get_service),
    _: Principal = Depends(require_write),
) -> FieldTaskBulkResult:
    return await service.bulk_create(payloads)


@router.post("/reconcile", response_model=list[ReconciliationItem], summary="Reconcile external Field Task records")
async def reconcile(
    records: list[dict[str, object]],
    apply: bool = Query(False, description="Apply the plan instead of a dry run"),
    service: FieldTaskService = Depends(get_service),
    _: Principal = Depends(require_write),
) -> list[ReconciliationItem]:
    return await service.reconcile(records, apply)


@router.get("/by-work-order-id/{work_order_id}", response_model=Page[FieldTaskRead], summary="List Field Task records by Work Order Id")
async def list_by_work_order_id(
    work_order_id: UUID,
    page: int = Query(0, ge=0),
    size: int = Query(20, ge=1, le=200),
    service: FieldTaskService = Depends(get_service),
) -> Page[FieldTaskRead]:
    return await service.list_by_work_order_id(work_order_id, page, size)
