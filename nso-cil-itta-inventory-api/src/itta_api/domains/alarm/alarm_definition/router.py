"""HTTP routes for Alarm Definition."""
from __future__ import annotations

from uuid import UUID

from fastapi import APIRouter, Depends, Query, Request, Response, status
from fastapi.responses import PlainTextResponse

from itta_core.auth import Principal, require_read, require_write
from itta_core.dependencies import service_provider
from itta_core.diffing import ReconciliationItem
from itta_core.models import EntityMeta, LifecycleStatus
from itta_core.pagination import Page
from itta_api.domains.alarm.alarm_definition.filters import AlarmDefinitionFilter
from itta_api.domains.alarm.alarm_definition.meta import META
from itta_api.domains.alarm.alarm_definition.schemas import (
    AlarmDefinitionBulkResult,
    AlarmDefinitionCreate,
    AlarmDefinitionRead,
    AlarmDefinitionSummary,
    AlarmDefinitionUpdate,
)
from itta_api.domains.alarm.alarm_definition.service import AlarmDefinitionService

router = APIRouter(prefix="/api/v1/alarm-definitions", tags=["Alarm Definition"])
get_service = service_provider("alarm_definition")


@router.get("/meta", response_model=EntityMeta, summary="Field catalogue for Alarm Definition")
async def meta() -> EntityMeta:
    return META


@router.get("", response_model=Page[AlarmDefinitionRead], summary="Search Alarm Definition records")
async def search(
    flt: AlarmDefinitionFilter = Depends(),
    page: int = Query(0, ge=0),
    size: int = Query(20, ge=1, le=200),
    sort: str | None = Query(None, description="field,asc|desc"),
    service: AlarmDefinitionService = Depends(get_service),
    _: Principal = Depends(require_read),
) -> Page[AlarmDefinitionRead]:
    return await service.search(flt, page, size, sort)


@router.get("/summaries", response_model=list[AlarmDefinitionSummary], summary="Pick-list of Alarm Definition records")
async def summaries(
    status_filter: LifecycleStatus | None = Query(None, alias="status"),
    service: AlarmDefinitionService = Depends(get_service),
    _: Principal = Depends(require_read),
) -> list[AlarmDefinitionSummary]:
    return await service.summaries(status_filter)


@router.get("/stats/status", response_model=dict[str, int], summary="Counts per lifecycle status")
async def status_stats(
    service: AlarmDefinitionService = Depends(get_service), _: Principal = Depends(require_read)
) -> dict[str, int]:
    return await service.stats()


@router.get("/export", response_class=PlainTextResponse, summary="Export Alarm Definition records as CSV")
async def export(
    flt: AlarmDefinitionFilter = Depends(),
    service: AlarmDefinitionService = Depends(get_service),
    _: Principal = Depends(require_read),
) -> PlainTextResponse:
    body = await service.export_csv(flt)
    headers = {"Content-Disposition": 'attachment; filename="alarm-definitions.csv"'}
    return PlainTextResponse(body, media_type="text/csv", headers=headers)


@router.get("/by-code/{code}", response_model=AlarmDefinitionRead, summary="Get a Alarm Definition by business code")
async def get_by_code(
    code: str, service: AlarmDefinitionService = Depends(get_service), _: Principal = Depends(require_read)
) -> AlarmDefinitionRead:
    return await service.get_by_code(code)


@router.head("/by-code/{code}", summary="Check whether a Alarm Definition code exists")
async def exists(
    code: str, service: AlarmDefinitionService = Depends(get_service), _: Principal = Depends(require_read)
) -> Response:
    found = await service.exists(code)
    return Response(status_code=status.HTTP_200_OK if found else status.HTTP_404_NOT_FOUND)


@router.get("/{id}", response_model=AlarmDefinitionRead, summary="Get a Alarm Definition by id")
async def get(
    id: UUID, service: AlarmDefinitionService = Depends(get_service), _: Principal = Depends(require_read)
) -> AlarmDefinitionRead:
    return await service.get(id)


@router.post("", response_model=AlarmDefinitionRead, status_code=status.HTTP_201_CREATED, summary="Create a Alarm Definition")
async def create(
    payload: AlarmDefinitionCreate,
    request: Request,
    response: Response,
    service: AlarmDefinitionService = Depends(get_service),
    _: Principal = Depends(require_write),
) -> AlarmDefinitionRead:
    created = await service.create(payload)
    response.headers["Location"] = f"{request.url.path}/{created.id}"
    return created


@router.put("/{id}", response_model=AlarmDefinitionRead, summary="Update a Alarm Definition")
async def update(
    id: UUID,
    payload: AlarmDefinitionUpdate,
    service: AlarmDefinitionService = Depends(get_service),
    _: Principal = Depends(require_write),
) -> AlarmDefinitionRead:
    return await service.update(id, payload)


@router.patch("/{id}/status", response_model=AlarmDefinitionRead, summary="Change lifecycle status")
async def change_status(
    id: UUID,
    new_status: LifecycleStatus = Query(..., alias="status"),
    service: AlarmDefinitionService = Depends(get_service),
    _: Principal = Depends(require_write),
) -> AlarmDefinitionRead:
    return await service.change_status(id, new_status)


@router.delete("/{id}", status_code=status.HTTP_204_NO_CONTENT, summary="Delete a Alarm Definition")
async def delete(
    id: UUID, service: AlarmDefinitionService = Depends(get_service), _: Principal = Depends(require_write)
) -> Response:
    await service.delete(id)
    return Response(status_code=status.HTTP_204_NO_CONTENT)


@router.post("/bulk", response_model=AlarmDefinitionBulkResult, summary="Create many Alarm Definition records")
async def bulk_create(
    payloads: list[AlarmDefinitionCreate],
    service: AlarmDefinitionService = Depends(get_service),
    _: Principal = Depends(require_write),
) -> AlarmDefinitionBulkResult:
    return await service.bulk_create(payloads)


@router.post("/reconcile", response_model=list[ReconciliationItem], summary="Reconcile external Alarm Definition records")
async def reconcile(
    records: list[dict[str, object]],
    apply: bool = Query(False, description="Apply the plan instead of a dry run"),
    service: AlarmDefinitionService = Depends(get_service),
    _: Principal = Depends(require_write),
) -> list[ReconciliationItem]:
    return await service.reconcile(records, apply)

