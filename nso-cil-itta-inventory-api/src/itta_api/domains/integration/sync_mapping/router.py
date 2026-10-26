"""HTTP routes for Sync Mapping."""
from __future__ import annotations

from uuid import UUID

from fastapi import APIRouter, Depends, Query, Request, Response, status
from fastapi.responses import PlainTextResponse

from itta_core.auth import Principal, require_read, require_write
from itta_core.dependencies import service_provider
from itta_core.diffing import ReconciliationItem
from itta_core.models import EntityMeta, LifecycleStatus
from itta_core.pagination import Page
from itta_api.domains.integration.sync_mapping.filters import SyncMappingFilter
from itta_api.domains.integration.sync_mapping.meta import META
from itta_api.domains.integration.sync_mapping.schemas import (
    SyncMappingBulkResult,
    SyncMappingCreate,
    SyncMappingRead,
    SyncMappingSummary,
    SyncMappingUpdate,
)
from itta_api.domains.integration.sync_mapping.service import SyncMappingService

router = APIRouter(prefix="/api/v1/sync-mappings", tags=["Sync Mapping"])
get_service = service_provider("sync_mapping")


@router.get("/meta", response_model=EntityMeta, summary="Field catalogue for Sync Mapping")
async def meta() -> EntityMeta:
    return META


@router.get("", response_model=Page[SyncMappingRead], summary="Search Sync Mapping records")
async def search(
    flt: SyncMappingFilter = Depends(),
    page: int = Query(0, ge=0),
    size: int = Query(20, ge=1, le=200),
    sort: str | None = Query(None, description="field,asc|desc"),
    service: SyncMappingService = Depends(get_service),
    _: Principal = Depends(require_read),
) -> Page[SyncMappingRead]:
    return await service.search(flt, page, size, sort)


@router.get("/summaries", response_model=list[SyncMappingSummary], summary="Pick-list of Sync Mapping records")
async def summaries(
    status_filter: LifecycleStatus | None = Query(None, alias="status"),
    service: SyncMappingService = Depends(get_service),
    _: Principal = Depends(require_read),
) -> list[SyncMappingSummary]:
    return await service.summaries(status_filter)


@router.get("/stats/status", response_model=dict[str, int], summary="Counts per lifecycle status")
async def status_stats(
    service: SyncMappingService = Depends(get_service), _: Principal = Depends(require_read)
) -> dict[str, int]:
    return await service.stats()


@router.get("/export", response_class=PlainTextResponse, summary="Export Sync Mapping records as CSV")
async def export(
    flt: SyncMappingFilter = Depends(),
    service: SyncMappingService = Depends(get_service),
    _: Principal = Depends(require_read),
) -> PlainTextResponse:
    body = await service.export_csv(flt)
    headers = {"Content-Disposition": 'attachment; filename="sync-mappings.csv"'}
    return PlainTextResponse(body, media_type="text/csv", headers=headers)


@router.get("/by-code/{code}", response_model=SyncMappingRead, summary="Get a Sync Mapping by business code")
async def get_by_code(
    code: str, service: SyncMappingService = Depends(get_service), _: Principal = Depends(require_read)
) -> SyncMappingRead:
    return await service.get_by_code(code)


@router.head("/by-code/{code}", summary="Check whether a Sync Mapping code exists")
async def exists(
    code: str, service: SyncMappingService = Depends(get_service), _: Principal = Depends(require_read)
) -> Response:
    found = await service.exists(code)
    return Response(status_code=status.HTTP_200_OK if found else status.HTTP_404_NOT_FOUND)


@router.get("/{id}", response_model=SyncMappingRead, summary="Get a Sync Mapping by id")
async def get(
    id: UUID, service: SyncMappingService = Depends(get_service), _: Principal = Depends(require_read)
) -> SyncMappingRead:
    return await service.get(id)


@router.post("", response_model=SyncMappingRead, status_code=status.HTTP_201_CREATED, summary="Create a Sync Mapping")
async def create(
    payload: SyncMappingCreate,
    request: Request,
    response: Response,
    service: SyncMappingService = Depends(get_service),
    _: Principal = Depends(require_write),
) -> SyncMappingRead:
    created = await service.create(payload)
    response.headers["Location"] = f"{request.url.path}/{created.id}"
    return created


@router.put("/{id}", response_model=SyncMappingRead, summary="Update a Sync Mapping")
async def update(
    id: UUID,
    payload: SyncMappingUpdate,
    service: SyncMappingService = Depends(get_service),
    _: Principal = Depends(require_write),
) -> SyncMappingRead:
    return await service.update(id, payload)


@router.patch("/{id}/status", response_model=SyncMappingRead, summary="Change lifecycle status")
async def change_status(
    id: UUID,
    new_status: LifecycleStatus = Query(..., alias="status"),
    service: SyncMappingService = Depends(get_service),
    _: Principal = Depends(require_write),
) -> SyncMappingRead:
    return await service.change_status(id, new_status)


@router.delete("/{id}", status_code=status.HTTP_204_NO_CONTENT, summary="Delete a Sync Mapping")
async def delete(
    id: UUID, service: SyncMappingService = Depends(get_service), _: Principal = Depends(require_write)
) -> Response:
    await service.delete(id)
    return Response(status_code=status.HTTP_204_NO_CONTENT)


@router.post("/bulk", response_model=SyncMappingBulkResult, summary="Create many Sync Mapping records")
async def bulk_create(
    payloads: list[SyncMappingCreate],
    service: SyncMappingService = Depends(get_service),
    _: Principal = Depends(require_write),
) -> SyncMappingBulkResult:
    return await service.bulk_create(payloads)


@router.post("/reconcile", response_model=list[ReconciliationItem], summary="Reconcile external Sync Mapping records")
async def reconcile(
    records: list[dict[str, object]],
    apply: bool = Query(False, description="Apply the plan instead of a dry run"),
    service: SyncMappingService = Depends(get_service),
    _: Principal = Depends(require_write),
) -> list[ReconciliationItem]:
    return await service.reconcile(records, apply)


@router.get("/by-job-id/{job_id}", response_model=Page[SyncMappingRead], summary="List Sync Mapping records by Job Id")
async def list_by_job_id(
    job_id: UUID,
    page: int = Query(0, ge=0),
    size: int = Query(20, ge=1, le=200),
    service: SyncMappingService = Depends(get_service),
) -> Page[SyncMappingRead]:
    return await service.list_by_job_id(job_id, page, size)
