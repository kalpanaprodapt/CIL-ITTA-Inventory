"""HTTP routes for Alarm Instance."""
from __future__ import annotations

from uuid import UUID

from fastapi import APIRouter, Depends, Query, Request, Response, status
from fastapi.responses import PlainTextResponse

from itta_core.auth import Principal, require_read, require_write
from itta_core.dependencies import service_provider
from itta_core.diffing import ReconciliationItem
from itta_core.models import EntityMeta, LifecycleStatus
from itta_core.pagination import Page
from itta_api.domains.alarm.alarm_instance.filters import AlarmInstanceFilter
from itta_api.domains.alarm.alarm_instance.meta import META
from itta_api.domains.alarm.alarm_instance.schemas import (
    AlarmInstanceBulkResult,
    AlarmInstanceCreate,
    AlarmInstanceRead,
    AlarmInstanceSummary,
    AlarmInstanceUpdate,
)
from itta_api.domains.alarm.alarm_instance.service import AlarmInstanceService

router = APIRouter(prefix="/api/v1/alarm-instances", tags=["Alarm Instance"])
get_service = service_provider("alarm_instance")


@router.get("/meta", response_model=EntityMeta, summary="Field catalogue for Alarm Instance")
async def meta() -> EntityMeta:
    return META


@router.get("", response_model=Page[AlarmInstanceRead], summary="Search Alarm Instance records")
async def search(
    flt: AlarmInstanceFilter = Depends(),
    page: int = Query(0, ge=0),
    size: int = Query(20, ge=1, le=200),
    sort: str | None = Query(None, description="field,asc|desc"),
    service: AlarmInstanceService = Depends(get_service),
    _: Principal = Depends(require_read),
) -> Page[AlarmInstanceRead]:
    return await service.search(flt, page, size, sort)


@router.get("/summaries", response_model=list[AlarmInstanceSummary], summary="Pick-list of Alarm Instance records")
async def summaries(
    status_filter: LifecycleStatus | None = Query(None, alias="status"),
    service: AlarmInstanceService = Depends(get_service),
    _: Principal = Depends(require_read),
) -> list[AlarmInstanceSummary]:
    return await service.summaries(status_filter)


@router.get("/stats/status", response_model=dict[str, int], summary="Counts per lifecycle status")
async def status_stats(
    service: AlarmInstanceService = Depends(get_service), _: Principal = Depends(require_read)
) -> dict[str, int]:
    return await service.stats()


@router.get("/export", response_class=PlainTextResponse, summary="Export Alarm Instance records as CSV")
async def export(
    flt: AlarmInstanceFilter = Depends(),
    service: AlarmInstanceService = Depends(get_service),
    _: Principal = Depends(require_read),
) -> PlainTextResponse:
    body = await service.export_csv(flt)
    headers = {"Content-Disposition": 'attachment; filename="alarm-instances.csv"'}
    return PlainTextResponse(body, media_type="text/csv", headers=headers)


@router.get("/by-code/{code}", response_model=AlarmInstanceRead, summary="Get a Alarm Instance by business code")
async def get_by_code(
    code: str, service: AlarmInstanceService = Depends(get_service), _: Principal = Depends(require_read)
) -> AlarmInstanceRead:
    return await service.get_by_code(code)


@router.head("/by-code/{code}", summary="Check whether a Alarm Instance code exists")
async def exists(
    code: str, service: AlarmInstanceService = Depends(get_service), _: Principal = Depends(require_read)
) -> Response:
    found = await service.exists(code)
    return Response(status_code=status.HTTP_200_OK if found else status.HTTP_404_NOT_FOUND)


@router.get("/{id}", response_model=AlarmInstanceRead, summary="Get a Alarm Instance by id")
async def get(
    id: UUID, service: AlarmInstanceService = Depends(get_service), _: Principal = Depends(require_read)
) -> AlarmInstanceRead:
    return await service.get(id)


@router.post("", response_model=AlarmInstanceRead, status_code=status.HTTP_201_CREATED, summary="Create a Alarm Instance")
async def create(
    payload: AlarmInstanceCreate,
    request: Request,
    response: Response,
    service: AlarmInstanceService = Depends(get_service),
    _: Principal = Depends(require_write),
) -> AlarmInstanceRead:
    created = await service.create(payload)
    response.headers["Location"] = f"{request.url.path}/{created.id}"
    return created


@router.put("/{id}", response_model=AlarmInstanceRead, summary="Update a Alarm Instance")
async def update(
    id: UUID,
    payload: AlarmInstanceUpdate,
    service: AlarmInstanceService = Depends(get_service),
    _: Principal = Depends(require_write),
) -> AlarmInstanceRead:
    return await service.update(id, payload)


@router.patch("/{id}/status", response_model=AlarmInstanceRead, summary="Change lifecycle status")
async def change_status(
    id: UUID,
    new_status: LifecycleStatus = Query(..., alias="status"),
    service: AlarmInstanceService = Depends(get_service),
    _: Principal = Depends(require_write),
) -> AlarmInstanceRead:
    return await service.change_status(id, new_status)


@router.delete("/{id}", status_code=status.HTTP_204_NO_CONTENT, summary="Delete a Alarm Instance")
async def delete(
    id: UUID, service: AlarmInstanceService = Depends(get_service), _: Principal = Depends(require_write)
) -> Response:
    await service.delete(id)
    return Response(status_code=status.HTTP_204_NO_CONTENT)


@router.post("/bulk", response_model=AlarmInstanceBulkResult, summary="Create many Alarm Instance records")
async def bulk_create(
    payloads: list[AlarmInstanceCreate],
    service: AlarmInstanceService = Depends(get_service),
    _: Principal = Depends(require_write),
) -> AlarmInstanceBulkResult:
    return await service.bulk_create(payloads)


@router.post("/reconcile", response_model=list[ReconciliationItem], summary="Reconcile external Alarm Instance records")
async def reconcile(
    records: list[dict[str, object]],
    apply: bool = Query(False, description="Apply the plan instead of a dry run"),
    service: AlarmInstanceService = Depends(get_service),
    _: Principal = Depends(require_write),
) -> list[ReconciliationItem]:
    return await service.reconcile(records, apply)


@router.get("/by-definition-id/{definition_id}", response_model=Page[AlarmInstanceRead], summary="List Alarm Instance records by Definition Id")
async def list_by_definition_id(
    definition_id: UUID,
    page: int = Query(0, ge=0),
    size: int = Query(20, ge=1, le=200),
    service: AlarmInstanceService = Depends(get_service),
) -> Page[AlarmInstanceRead]:
    return await service.list_by_definition_id(definition_id, page, size)
