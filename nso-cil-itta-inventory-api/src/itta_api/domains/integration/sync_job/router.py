"""HTTP routes for Sync Job."""
from __future__ import annotations

from uuid import UUID

from fastapi import APIRouter, Depends, Query, Request, Response, status
from fastapi.responses import PlainTextResponse

from itta_core.auth import Principal, require_read, require_write
from itta_core.dependencies import service_provider
from itta_core.diffing import ReconciliationItem
from itta_core.models import EntityMeta, LifecycleStatus
from itta_core.pagination import Page
from itta_api.domains.integration.sync_job.filters import SyncJobFilter
from itta_api.domains.integration.sync_job.meta import META
from itta_api.domains.integration.sync_job.schemas import (
    SyncJobBulkResult,
    SyncJobCreate,
    SyncJobRead,
    SyncJobSummary,
    SyncJobUpdate,
)
from itta_api.domains.integration.sync_job.service import SyncJobService

router = APIRouter(prefix="/api/v1/sync-jobs", tags=["Sync Job"])
get_service = service_provider("sync_job")


@router.get("/meta", response_model=EntityMeta, summary="Field catalogue for Sync Job")
async def meta() -> EntityMeta:
    return META


@router.get("", response_model=Page[SyncJobRead], summary="Search Sync Job records")
async def search(
    flt: SyncJobFilter = Depends(),
    page: int = Query(0, ge=0),
    size: int = Query(20, ge=1, le=200),
    sort: str | None = Query(None, description="field,asc|desc"),
    service: SyncJobService = Depends(get_service),
    _: Principal = Depends(require_read),
) -> Page[SyncJobRead]:
    return await service.search(flt, page, size, sort)


@router.get("/summaries", response_model=list[SyncJobSummary], summary="Pick-list of Sync Job records")
async def summaries(
    status_filter: LifecycleStatus | None = Query(None, alias="status"),
    service: SyncJobService = Depends(get_service),
    _: Principal = Depends(require_read),
) -> list[SyncJobSummary]:
    return await service.summaries(status_filter)


@router.get("/stats/status", response_model=dict[str, int], summary="Counts per lifecycle status")
async def status_stats(
    service: SyncJobService = Depends(get_service), _: Principal = Depends(require_read)
) -> dict[str, int]:
    return await service.stats()


@router.get("/export", response_class=PlainTextResponse, summary="Export Sync Job records as CSV")
async def export(
    flt: SyncJobFilter = Depends(),
    service: SyncJobService = Depends(get_service),
    _: Principal = Depends(require_read),
) -> PlainTextResponse:
    body = await service.export_csv(flt)
    headers = {"Content-Disposition": 'attachment; filename="sync-jobs.csv"'}
    return PlainTextResponse(body, media_type="text/csv", headers=headers)


@router.get("/by-code/{code}", response_model=SyncJobRead, summary="Get a Sync Job by business code")
async def get_by_code(
    code: str, service: SyncJobService = Depends(get_service), _: Principal = Depends(require_read)
) -> SyncJobRead:
    return await service.get_by_code(code)


@router.head("/by-code/{code}", summary="Check whether a Sync Job code exists")
async def exists(
    code: str, service: SyncJobService = Depends(get_service), _: Principal = Depends(require_read)
) -> Response:
    found = await service.exists(code)
    return Response(status_code=status.HTTP_200_OK if found else status.HTTP_404_NOT_FOUND)


@router.get("/{id}", response_model=SyncJobRead, summary="Get a Sync Job by id")
async def get(
    id: UUID, service: SyncJobService = Depends(get_service), _: Principal = Depends(require_read)
) -> SyncJobRead:
    return await service.get(id)


@router.post("", response_model=SyncJobRead, status_code=status.HTTP_201_CREATED, summary="Create a Sync Job")
async def create(
    payload: SyncJobCreate,
    request: Request,
    response: Response,
    service: SyncJobService = Depends(get_service),
    _: Principal = Depends(require_write),
) -> SyncJobRead:
    created = await service.create(payload)
    response.headers["Location"] = f"{request.url.path}/{created.id}"
    return created


@router.put("/{id}", response_model=SyncJobRead, summary="Update a Sync Job")
async def update(
    id: UUID,
    payload: SyncJobUpdate,
    service: SyncJobService = Depends(get_service),
    _: Principal = Depends(require_write),
) -> SyncJobRead:
    return await service.update(id, payload)


@router.patch("/{id}/status", response_model=SyncJobRead, summary="Change lifecycle status")
async def change_status(
    id: UUID,
    new_status: LifecycleStatus = Query(..., alias="status"),
    service: SyncJobService = Depends(get_service),
    _: Principal = Depends(require_write),
) -> SyncJobRead:
    return await service.change_status(id, new_status)


@router.delete("/{id}", status_code=status.HTTP_204_NO_CONTENT, summary="Delete a Sync Job")
async def delete(
    id: UUID, service: SyncJobService = Depends(get_service), _: Principal = Depends(require_write)
) -> Response:
    await service.delete(id)
    return Response(status_code=status.HTTP_204_NO_CONTENT)


@router.post("/bulk", response_model=SyncJobBulkResult, summary="Create many Sync Job records")
async def bulk_create(
    payloads: list[SyncJobCreate],
    service: SyncJobService = Depends(get_service),
    _: Principal = Depends(require_write),
) -> SyncJobBulkResult:
    return await service.bulk_create(payloads)


@router.post("/reconcile", response_model=list[ReconciliationItem], summary="Reconcile external Sync Job records")
async def reconcile(
    records: list[dict[str, object]],
    apply: bool = Query(False, description="Apply the plan instead of a dry run"),
    service: SyncJobService = Depends(get_service),
    _: Principal = Depends(require_write),
) -> list[ReconciliationItem]:
    return await service.reconcile(records, apply)


@router.get("/by-endpoint-id/{endpoint_id}", response_model=Page[SyncJobRead], summary="List Sync Job records by Endpoint Id")
async def list_by_endpoint_id(
    endpoint_id: UUID,
    page: int = Query(0, ge=0),
    size: int = Query(20, ge=1, le=200),
    service: SyncJobService = Depends(get_service),
) -> Page[SyncJobRead]:
    return await service.list_by_endpoint_id(endpoint_id, page, size)
