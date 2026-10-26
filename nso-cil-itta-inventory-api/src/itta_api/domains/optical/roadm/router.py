"""HTTP routes for Roadm."""
from __future__ import annotations

from uuid import UUID

from fastapi import APIRouter, Depends, Query, Request, Response, status
from fastapi.responses import PlainTextResponse

from itta_core.auth import Principal, require_read, require_write
from itta_core.dependencies import service_provider
from itta_core.diffing import ReconciliationItem
from itta_core.models import EntityMeta, LifecycleStatus
from itta_core.pagination import Page
from itta_api.domains.optical.roadm.filters import RoadmFilter
from itta_api.domains.optical.roadm.meta import META
from itta_api.domains.optical.roadm.schemas import (
    RoadmBulkResult,
    RoadmCreate,
    RoadmRead,
    RoadmSummary,
    RoadmUpdate,
)
from itta_api.domains.optical.roadm.service import RoadmService

router = APIRouter(prefix="/api/v1/roadms", tags=["Roadm"])
get_service = service_provider("roadm")


@router.get("/meta", response_model=EntityMeta, summary="Field catalogue for Roadm")
async def meta() -> EntityMeta:
    return META


@router.get("", response_model=Page[RoadmRead], summary="Search Roadm records")
async def search(
    flt: RoadmFilter = Depends(),
    page: int = Query(0, ge=0),
    size: int = Query(20, ge=1, le=200),
    sort: str | None = Query(None, description="field,asc|desc"),
    service: RoadmService = Depends(get_service),
    _: Principal = Depends(require_read),
) -> Page[RoadmRead]:
    return await service.search(flt, page, size, sort)


@router.get("/summaries", response_model=list[RoadmSummary], summary="Pick-list of Roadm records")
async def summaries(
    status_filter: LifecycleStatus | None = Query(None, alias="status"),
    service: RoadmService = Depends(get_service),
    _: Principal = Depends(require_read),
) -> list[RoadmSummary]:
    return await service.summaries(status_filter)


@router.get("/stats/status", response_model=dict[str, int], summary="Counts per lifecycle status")
async def status_stats(
    service: RoadmService = Depends(get_service), _: Principal = Depends(require_read)
) -> dict[str, int]:
    return await service.stats()


@router.get("/export", response_class=PlainTextResponse, summary="Export Roadm records as CSV")
async def export(
    flt: RoadmFilter = Depends(),
    service: RoadmService = Depends(get_service),
    _: Principal = Depends(require_read),
) -> PlainTextResponse:
    body = await service.export_csv(flt)
    headers = {"Content-Disposition": 'attachment; filename="roadms.csv"'}
    return PlainTextResponse(body, media_type="text/csv", headers=headers)


@router.get("/by-code/{code}", response_model=RoadmRead, summary="Get a Roadm by business code")
async def get_by_code(
    code: str, service: RoadmService = Depends(get_service), _: Principal = Depends(require_read)
) -> RoadmRead:
    return await service.get_by_code(code)


@router.head("/by-code/{code}", summary="Check whether a Roadm code exists")
async def exists(
    code: str, service: RoadmService = Depends(get_service), _: Principal = Depends(require_read)
) -> Response:
    found = await service.exists(code)
    return Response(status_code=status.HTTP_200_OK if found else status.HTTP_404_NOT_FOUND)


@router.get("/{id}", response_model=RoadmRead, summary="Get a Roadm by id")
async def get(
    id: UUID, service: RoadmService = Depends(get_service), _: Principal = Depends(require_read)
) -> RoadmRead:
    return await service.get(id)


@router.post("", response_model=RoadmRead, status_code=status.HTTP_201_CREATED, summary="Create a Roadm")
async def create(
    payload: RoadmCreate,
    request: Request,
    response: Response,
    service: RoadmService = Depends(get_service),
    _: Principal = Depends(require_write),
) -> RoadmRead:
    created = await service.create(payload)
    response.headers["Location"] = f"{request.url.path}/{created.id}"
    return created


@router.put("/{id}", response_model=RoadmRead, summary="Update a Roadm")
async def update(
    id: UUID,
    payload: RoadmUpdate,
    service: RoadmService = Depends(get_service),
    _: Principal = Depends(require_write),
) -> RoadmRead:
    return await service.update(id, payload)


@router.patch("/{id}/status", response_model=RoadmRead, summary="Change lifecycle status")
async def change_status(
    id: UUID,
    new_status: LifecycleStatus = Query(..., alias="status"),
    service: RoadmService = Depends(get_service),
    _: Principal = Depends(require_write),
) -> RoadmRead:
    return await service.change_status(id, new_status)


@router.delete("/{id}", status_code=status.HTTP_204_NO_CONTENT, summary="Delete a Roadm")
async def delete(
    id: UUID, service: RoadmService = Depends(get_service), _: Principal = Depends(require_write)
) -> Response:
    await service.delete(id)
    return Response(status_code=status.HTTP_204_NO_CONTENT)


@router.post("/bulk", response_model=RoadmBulkResult, summary="Create many Roadm records")
async def bulk_create(
    payloads: list[RoadmCreate],
    service: RoadmService = Depends(get_service),
    _: Principal = Depends(require_write),
) -> RoadmBulkResult:
    return await service.bulk_create(payloads)


@router.post("/reconcile", response_model=list[ReconciliationItem], summary="Reconcile external Roadm records")
async def reconcile(
    records: list[dict[str, object]],
    apply: bool = Query(False, description="Apply the plan instead of a dry run"),
    service: RoadmService = Depends(get_service),
    _: Principal = Depends(require_write),
) -> list[ReconciliationItem]:
    return await service.reconcile(records, apply)


@router.get("/by-system-id/{system_id}", response_model=Page[RoadmRead], summary="List Roadm records by System Id")
async def list_by_system_id(
    system_id: UUID,
    page: int = Query(0, ge=0),
    size: int = Query(20, ge=1, le=200),
    service: RoadmService = Depends(get_service),
) -> Page[RoadmRead]:
    return await service.list_by_system_id(system_id, page, size)
