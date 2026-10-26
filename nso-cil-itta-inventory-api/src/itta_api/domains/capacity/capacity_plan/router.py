"""HTTP routes for Capacity Plan."""
from __future__ import annotations

from uuid import UUID

from fastapi import APIRouter, Depends, Query, Request, Response, status
from fastapi.responses import PlainTextResponse

from itta_core.auth import Principal, require_read, require_write
from itta_core.dependencies import service_provider
from itta_core.diffing import ReconciliationItem
from itta_core.models import EntityMeta, LifecycleStatus
from itta_core.pagination import Page
from itta_api.domains.capacity.capacity_plan.filters import CapacityPlanFilter
from itta_api.domains.capacity.capacity_plan.meta import META
from itta_api.domains.capacity.capacity_plan.schemas import (
    CapacityPlanBulkResult,
    CapacityPlanCreate,
    CapacityPlanRead,
    CapacityPlanSummary,
    CapacityPlanUpdate,
)
from itta_api.domains.capacity.capacity_plan.service import CapacityPlanService

router = APIRouter(prefix="/api/v1/capacity-plans", tags=["Capacity Plan"])
get_service = service_provider("capacity_plan")


@router.get("/meta", response_model=EntityMeta, summary="Field catalogue for Capacity Plan")
async def meta() -> EntityMeta:
    return META


@router.get("", response_model=Page[CapacityPlanRead], summary="Search Capacity Plan records")
async def search(
    flt: CapacityPlanFilter = Depends(),
    page: int = Query(0, ge=0),
    size: int = Query(20, ge=1, le=200),
    sort: str | None = Query(None, description="field,asc|desc"),
    service: CapacityPlanService = Depends(get_service),
    _: Principal = Depends(require_read),
) -> Page[CapacityPlanRead]:
    return await service.search(flt, page, size, sort)


@router.get("/summaries", response_model=list[CapacityPlanSummary], summary="Pick-list of Capacity Plan records")
async def summaries(
    status_filter: LifecycleStatus | None = Query(None, alias="status"),
    service: CapacityPlanService = Depends(get_service),
    _: Principal = Depends(require_read),
) -> list[CapacityPlanSummary]:
    return await service.summaries(status_filter)


@router.get("/stats/status", response_model=dict[str, int], summary="Counts per lifecycle status")
async def status_stats(
    service: CapacityPlanService = Depends(get_service), _: Principal = Depends(require_read)
) -> dict[str, int]:
    return await service.stats()


@router.get("/export", response_class=PlainTextResponse, summary="Export Capacity Plan records as CSV")
async def export(
    flt: CapacityPlanFilter = Depends(),
    service: CapacityPlanService = Depends(get_service),
    _: Principal = Depends(require_read),
) -> PlainTextResponse:
    body = await service.export_csv(flt)
    headers = {"Content-Disposition": 'attachment; filename="capacity-plans.csv"'}
    return PlainTextResponse(body, media_type="text/csv", headers=headers)


@router.get("/by-code/{code}", response_model=CapacityPlanRead, summary="Get a Capacity Plan by business code")
async def get_by_code(
    code: str, service: CapacityPlanService = Depends(get_service), _: Principal = Depends(require_read)
) -> CapacityPlanRead:
    return await service.get_by_code(code)


@router.head("/by-code/{code}", summary="Check whether a Capacity Plan code exists")
async def exists(
    code: str, service: CapacityPlanService = Depends(get_service), _: Principal = Depends(require_read)
) -> Response:
    found = await service.exists(code)
    return Response(status_code=status.HTTP_200_OK if found else status.HTTP_404_NOT_FOUND)


@router.get("/{id}", response_model=CapacityPlanRead, summary="Get a Capacity Plan by id")
async def get(
    id: UUID, service: CapacityPlanService = Depends(get_service), _: Principal = Depends(require_read)
) -> CapacityPlanRead:
    return await service.get(id)


@router.post("", response_model=CapacityPlanRead, status_code=status.HTTP_201_CREATED, summary="Create a Capacity Plan")
async def create(
    payload: CapacityPlanCreate,
    request: Request,
    response: Response,
    service: CapacityPlanService = Depends(get_service),
    _: Principal = Depends(require_write),
) -> CapacityPlanRead:
    created = await service.create(payload)
    response.headers["Location"] = f"{request.url.path}/{created.id}"
    return created


@router.put("/{id}", response_model=CapacityPlanRead, summary="Update a Capacity Plan")
async def update(
    id: UUID,
    payload: CapacityPlanUpdate,
    service: CapacityPlanService = Depends(get_service),
    _: Principal = Depends(require_write),
) -> CapacityPlanRead:
    return await service.update(id, payload)


@router.patch("/{id}/status", response_model=CapacityPlanRead, summary="Change lifecycle status")
async def change_status(
    id: UUID,
    new_status: LifecycleStatus = Query(..., alias="status"),
    service: CapacityPlanService = Depends(get_service),
    _: Principal = Depends(require_write),
) -> CapacityPlanRead:
    return await service.change_status(id, new_status)


@router.delete("/{id}", status_code=status.HTTP_204_NO_CONTENT, summary="Delete a Capacity Plan")
async def delete(
    id: UUID, service: CapacityPlanService = Depends(get_service), _: Principal = Depends(require_write)
) -> Response:
    await service.delete(id)
    return Response(status_code=status.HTTP_204_NO_CONTENT)


@router.post("/bulk", response_model=CapacityPlanBulkResult, summary="Create many Capacity Plan records")
async def bulk_create(
    payloads: list[CapacityPlanCreate],
    service: CapacityPlanService = Depends(get_service),
    _: Principal = Depends(require_write),
) -> CapacityPlanBulkResult:
    return await service.bulk_create(payloads)


@router.post("/reconcile", response_model=list[ReconciliationItem], summary="Reconcile external Capacity Plan records")
async def reconcile(
    records: list[dict[str, object]],
    apply: bool = Query(False, description="Apply the plan instead of a dry run"),
    service: CapacityPlanService = Depends(get_service),
    _: Principal = Depends(require_write),
) -> list[ReconciliationItem]:
    return await service.reconcile(records, apply)

