"""HTTP routes for Utilization Sample."""
from __future__ import annotations

from uuid import UUID

from fastapi import APIRouter, Depends, Query, Request, Response, status
from fastapi.responses import PlainTextResponse

from itta_core.auth import Principal, require_read, require_write
from itta_core.dependencies import service_provider
from itta_core.diffing import ReconciliationItem
from itta_core.models import EntityMeta, LifecycleStatus
from itta_core.pagination import Page
from itta_api.domains.capacity.utilization_sample.filters import UtilizationSampleFilter
from itta_api.domains.capacity.utilization_sample.meta import META
from itta_api.domains.capacity.utilization_sample.schemas import (
    UtilizationSampleBulkResult,
    UtilizationSampleCreate,
    UtilizationSampleRead,
    UtilizationSampleSummary,
    UtilizationSampleUpdate,
)
from itta_api.domains.capacity.utilization_sample.service import UtilizationSampleService

router = APIRouter(prefix="/api/v1/utilization-samples", tags=["Utilization Sample"])
get_service = service_provider("utilization_sample")


@router.get("/meta", response_model=EntityMeta, summary="Field catalogue for Utilization Sample")
async def meta() -> EntityMeta:
    return META


@router.get("", response_model=Page[UtilizationSampleRead], summary="Search Utilization Sample records")
async def search(
    flt: UtilizationSampleFilter = Depends(),
    page: int = Query(0, ge=0),
    size: int = Query(20, ge=1, le=200),
    sort: str | None = Query(None, description="field,asc|desc"),
    service: UtilizationSampleService = Depends(get_service),
    _: Principal = Depends(require_read),
) -> Page[UtilizationSampleRead]:
    return await service.search(flt, page, size, sort)


@router.get("/summaries", response_model=list[UtilizationSampleSummary], summary="Pick-list of Utilization Sample records")
async def summaries(
    status_filter: LifecycleStatus | None = Query(None, alias="status"),
    service: UtilizationSampleService = Depends(get_service),
    _: Principal = Depends(require_read),
) -> list[UtilizationSampleSummary]:
    return await service.summaries(status_filter)


@router.get("/stats/status", response_model=dict[str, int], summary="Counts per lifecycle status")
async def status_stats(
    service: UtilizationSampleService = Depends(get_service), _: Principal = Depends(require_read)
) -> dict[str, int]:
    return await service.stats()


@router.get("/export", response_class=PlainTextResponse, summary="Export Utilization Sample records as CSV")
async def export(
    flt: UtilizationSampleFilter = Depends(),
    service: UtilizationSampleService = Depends(get_service),
    _: Principal = Depends(require_read),
) -> PlainTextResponse:
    body = await service.export_csv(flt)
    headers = {"Content-Disposition": 'attachment; filename="utilization-samples.csv"'}
    return PlainTextResponse(body, media_type="text/csv", headers=headers)


@router.get("/by-code/{code}", response_model=UtilizationSampleRead, summary="Get a Utilization Sample by business code")
async def get_by_code(
    code: str, service: UtilizationSampleService = Depends(get_service), _: Principal = Depends(require_read)
) -> UtilizationSampleRead:
    return await service.get_by_code(code)


@router.head("/by-code/{code}", summary="Check whether a Utilization Sample code exists")
async def exists(
    code: str, service: UtilizationSampleService = Depends(get_service), _: Principal = Depends(require_read)
) -> Response:
    found = await service.exists(code)
    return Response(status_code=status.HTTP_200_OK if found else status.HTTP_404_NOT_FOUND)


@router.get("/{id}", response_model=UtilizationSampleRead, summary="Get a Utilization Sample by id")
async def get(
    id: UUID, service: UtilizationSampleService = Depends(get_service), _: Principal = Depends(require_read)
) -> UtilizationSampleRead:
    return await service.get(id)


@router.post("", response_model=UtilizationSampleRead, status_code=status.HTTP_201_CREATED, summary="Create a Utilization Sample")
async def create(
    payload: UtilizationSampleCreate,
    request: Request,
    response: Response,
    service: UtilizationSampleService = Depends(get_service),
    _: Principal = Depends(require_write),
) -> UtilizationSampleRead:
    created = await service.create(payload)
    response.headers["Location"] = f"{request.url.path}/{created.id}"
    return created


@router.put("/{id}", response_model=UtilizationSampleRead, summary="Update a Utilization Sample")
async def update(
    id: UUID,
    payload: UtilizationSampleUpdate,
    service: UtilizationSampleService = Depends(get_service),
    _: Principal = Depends(require_write),
) -> UtilizationSampleRead:
    return await service.update(id, payload)


@router.patch("/{id}/status", response_model=UtilizationSampleRead, summary="Change lifecycle status")
async def change_status(
    id: UUID,
    new_status: LifecycleStatus = Query(..., alias="status"),
    service: UtilizationSampleService = Depends(get_service),
    _: Principal = Depends(require_write),
) -> UtilizationSampleRead:
    return await service.change_status(id, new_status)


@router.delete("/{id}", status_code=status.HTTP_204_NO_CONTENT, summary="Delete a Utilization Sample")
async def delete(
    id: UUID, service: UtilizationSampleService = Depends(get_service), _: Principal = Depends(require_write)
) -> Response:
    await service.delete(id)
    return Response(status_code=status.HTTP_204_NO_CONTENT)


@router.post("/bulk", response_model=UtilizationSampleBulkResult, summary="Create many Utilization Sample records")
async def bulk_create(
    payloads: list[UtilizationSampleCreate],
    service: UtilizationSampleService = Depends(get_service),
    _: Principal = Depends(require_write),
) -> UtilizationSampleBulkResult:
    return await service.bulk_create(payloads)


@router.post("/reconcile", response_model=list[ReconciliationItem], summary="Reconcile external Utilization Sample records")
async def reconcile(
    records: list[dict[str, object]],
    apply: bool = Query(False, description="Apply the plan instead of a dry run"),
    service: UtilizationSampleService = Depends(get_service),
    _: Principal = Depends(require_write),
) -> list[ReconciliationItem]:
    return await service.reconcile(records, apply)

