"""HTTP routes for Forecast Model."""
from __future__ import annotations

from uuid import UUID

from fastapi import APIRouter, Depends, Query, Request, Response, status
from fastapi.responses import PlainTextResponse

from itta_core.auth import Principal, require_read, require_write
from itta_core.dependencies import service_provider
from itta_core.diffing import ReconciliationItem
from itta_core.models import EntityMeta, LifecycleStatus
from itta_core.pagination import Page
from itta_api.domains.capacity.forecast_model.filters import ForecastModelFilter
from itta_api.domains.capacity.forecast_model.meta import META
from itta_api.domains.capacity.forecast_model.schemas import (
    ForecastModelBulkResult,
    ForecastModelCreate,
    ForecastModelRead,
    ForecastModelSummary,
    ForecastModelUpdate,
)
from itta_api.domains.capacity.forecast_model.service import ForecastModelService

router = APIRouter(prefix="/api/v1/forecast-models", tags=["Forecast Model"])
get_service = service_provider("forecast_model")


@router.get("/meta", response_model=EntityMeta, summary="Field catalogue for Forecast Model")
async def meta() -> EntityMeta:
    return META


@router.get("", response_model=Page[ForecastModelRead], summary="Search Forecast Model records")
async def search(
    flt: ForecastModelFilter = Depends(),
    page: int = Query(0, ge=0),
    size: int = Query(20, ge=1, le=200),
    sort: str | None = Query(None, description="field,asc|desc"),
    service: ForecastModelService = Depends(get_service),
    _: Principal = Depends(require_read),
) -> Page[ForecastModelRead]:
    return await service.search(flt, page, size, sort)


@router.get("/summaries", response_model=list[ForecastModelSummary], summary="Pick-list of Forecast Model records")
async def summaries(
    status_filter: LifecycleStatus | None = Query(None, alias="status"),
    service: ForecastModelService = Depends(get_service),
    _: Principal = Depends(require_read),
) -> list[ForecastModelSummary]:
    return await service.summaries(status_filter)


@router.get("/stats/status", response_model=dict[str, int], summary="Counts per lifecycle status")
async def status_stats(
    service: ForecastModelService = Depends(get_service), _: Principal = Depends(require_read)
) -> dict[str, int]:
    return await service.stats()


@router.get("/export", response_class=PlainTextResponse, summary="Export Forecast Model records as CSV")
async def export(
    flt: ForecastModelFilter = Depends(),
    service: ForecastModelService = Depends(get_service),
    _: Principal = Depends(require_read),
) -> PlainTextResponse:
    body = await service.export_csv(flt)
    headers = {"Content-Disposition": 'attachment; filename="forecast-models.csv"'}
    return PlainTextResponse(body, media_type="text/csv", headers=headers)


@router.get("/by-code/{code}", response_model=ForecastModelRead, summary="Get a Forecast Model by business code")
async def get_by_code(
    code: str, service: ForecastModelService = Depends(get_service), _: Principal = Depends(require_read)
) -> ForecastModelRead:
    return await service.get_by_code(code)


@router.head("/by-code/{code}", summary="Check whether a Forecast Model code exists")
async def exists(
    code: str, service: ForecastModelService = Depends(get_service), _: Principal = Depends(require_read)
) -> Response:
    found = await service.exists(code)
    return Response(status_code=status.HTTP_200_OK if found else status.HTTP_404_NOT_FOUND)


@router.get("/{id}", response_model=ForecastModelRead, summary="Get a Forecast Model by id")
async def get(
    id: UUID, service: ForecastModelService = Depends(get_service), _: Principal = Depends(require_read)
) -> ForecastModelRead:
    return await service.get(id)


@router.post("", response_model=ForecastModelRead, status_code=status.HTTP_201_CREATED, summary="Create a Forecast Model")
async def create(
    payload: ForecastModelCreate,
    request: Request,
    response: Response,
    service: ForecastModelService = Depends(get_service),
    _: Principal = Depends(require_write),
) -> ForecastModelRead:
    created = await service.create(payload)
    response.headers["Location"] = f"{request.url.path}/{created.id}"
    return created


@router.put("/{id}", response_model=ForecastModelRead, summary="Update a Forecast Model")
async def update(
    id: UUID,
    payload: ForecastModelUpdate,
    service: ForecastModelService = Depends(get_service),
    _: Principal = Depends(require_write),
) -> ForecastModelRead:
    return await service.update(id, payload)


@router.patch("/{id}/status", response_model=ForecastModelRead, summary="Change lifecycle status")
async def change_status(
    id: UUID,
    new_status: LifecycleStatus = Query(..., alias="status"),
    service: ForecastModelService = Depends(get_service),
    _: Principal = Depends(require_write),
) -> ForecastModelRead:
    return await service.change_status(id, new_status)


@router.delete("/{id}", status_code=status.HTTP_204_NO_CONTENT, summary="Delete a Forecast Model")
async def delete(
    id: UUID, service: ForecastModelService = Depends(get_service), _: Principal = Depends(require_write)
) -> Response:
    await service.delete(id)
    return Response(status_code=status.HTTP_204_NO_CONTENT)


@router.post("/bulk", response_model=ForecastModelBulkResult, summary="Create many Forecast Model records")
async def bulk_create(
    payloads: list[ForecastModelCreate],
    service: ForecastModelService = Depends(get_service),
    _: Principal = Depends(require_write),
) -> ForecastModelBulkResult:
    return await service.bulk_create(payloads)


@router.post("/reconcile", response_model=list[ReconciliationItem], summary="Reconcile external Forecast Model records")
async def reconcile(
    records: list[dict[str, object]],
    apply: bool = Query(False, description="Apply the plan instead of a dry run"),
    service: ForecastModelService = Depends(get_service),
    _: Principal = Depends(require_write),
) -> list[ReconciliationItem]:
    return await service.reconcile(records, apply)

