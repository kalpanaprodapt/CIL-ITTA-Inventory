"""HTTP routes for Discovery Job."""
from __future__ import annotations

from uuid import UUID

from fastapi import APIRouter, Depends, Query, Request, Response, status
from fastapi.responses import PlainTextResponse

from itta_core.auth import Principal, require_read, require_write
from itta_core.dependencies import service_provider
from itta_core.diffing import ReconciliationItem
from itta_core.models import EntityMeta, LifecycleStatus
from itta_core.pagination import Page
from itta_api.domains.discovery.discovery_job.filters import DiscoveryJobFilter
from itta_api.domains.discovery.discovery_job.meta import META
from itta_api.domains.discovery.discovery_job.schemas import (
    DiscoveryJobBulkResult,
    DiscoveryJobCreate,
    DiscoveryJobRead,
    DiscoveryJobSummary,
    DiscoveryJobUpdate,
)
from itta_api.domains.discovery.discovery_job.service import DiscoveryJobService

router = APIRouter(prefix="/api/v1/discovery-jobs", tags=["Discovery Job"])
get_service = service_provider("discovery_job")


@router.get("/meta", response_model=EntityMeta, summary="Field catalogue for Discovery Job")
async def meta() -> EntityMeta:
    return META


@router.get("", response_model=Page[DiscoveryJobRead], summary="Search Discovery Job records")
async def search(
    flt: DiscoveryJobFilter = Depends(),
    page: int = Query(0, ge=0),
    size: int = Query(20, ge=1, le=200),
    sort: str | None = Query(None, description="field,asc|desc"),
    service: DiscoveryJobService = Depends(get_service),
    _: Principal = Depends(require_read),
) -> Page[DiscoveryJobRead]:
    return await service.search(flt, page, size, sort)


@router.get("/summaries", response_model=list[DiscoveryJobSummary], summary="Pick-list of Discovery Job records")
async def summaries(
    status_filter: LifecycleStatus | None = Query(None, alias="status"),
    service: DiscoveryJobService = Depends(get_service),
    _: Principal = Depends(require_read),
) -> list[DiscoveryJobSummary]:
    return await service.summaries(status_filter)


@router.get("/stats/status", response_model=dict[str, int], summary="Counts per lifecycle status")
async def status_stats(
    service: DiscoveryJobService = Depends(get_service), _: Principal = Depends(require_read)
) -> dict[str, int]:
    return await service.stats()


@router.get("/export", response_class=PlainTextResponse, summary="Export Discovery Job records as CSV")
async def export(
    flt: DiscoveryJobFilter = Depends(),
    service: DiscoveryJobService = Depends(get_service),
    _: Principal = Depends(require_read),
) -> PlainTextResponse:
    body = await service.export_csv(flt)
    headers = {"Content-Disposition": 'attachment; filename="discovery-jobs.csv"'}
    return PlainTextResponse(body, media_type="text/csv", headers=headers)


@router.get("/by-code/{code}", response_model=DiscoveryJobRead, summary="Get a Discovery Job by business code")
async def get_by_code(
    code: str, service: DiscoveryJobService = Depends(get_service), _: Principal = Depends(require_read)
) -> DiscoveryJobRead:
    return await service.get_by_code(code)


@router.head("/by-code/{code}", summary="Check whether a Discovery Job code exists")
async def exists(
    code: str, service: DiscoveryJobService = Depends(get_service), _: Principal = Depends(require_read)
) -> Response:
    found = await service.exists(code)
    return Response(status_code=status.HTTP_200_OK if found else status.HTTP_404_NOT_FOUND)


@router.get("/{id}", response_model=DiscoveryJobRead, summary="Get a Discovery Job by id")
async def get(
    id: UUID, service: DiscoveryJobService = Depends(get_service), _: Principal = Depends(require_read)
) -> DiscoveryJobRead:
    return await service.get(id)


@router.post("", response_model=DiscoveryJobRead, status_code=status.HTTP_201_CREATED, summary="Create a Discovery Job")
async def create(
    payload: DiscoveryJobCreate,
    request: Request,
    response: Response,
    service: DiscoveryJobService = Depends(get_service),
    _: Principal = Depends(require_write),
) -> DiscoveryJobRead:
    created = await service.create(payload)
    response.headers["Location"] = f"{request.url.path}/{created.id}"
    return created


@router.put("/{id}", response_model=DiscoveryJobRead, summary="Update a Discovery Job")
async def update(
    id: UUID,
    payload: DiscoveryJobUpdate,
    service: DiscoveryJobService = Depends(get_service),
    _: Principal = Depends(require_write),
) -> DiscoveryJobRead:
    return await service.update(id, payload)


@router.patch("/{id}/status", response_model=DiscoveryJobRead, summary="Change lifecycle status")
async def change_status(
    id: UUID,
    new_status: LifecycleStatus = Query(..., alias="status"),
    service: DiscoveryJobService = Depends(get_service),
    _: Principal = Depends(require_write),
) -> DiscoveryJobRead:
    return await service.change_status(id, new_status)


@router.delete("/{id}", status_code=status.HTTP_204_NO_CONTENT, summary="Delete a Discovery Job")
async def delete(
    id: UUID, service: DiscoveryJobService = Depends(get_service), _: Principal = Depends(require_write)
) -> Response:
    await service.delete(id)
    return Response(status_code=status.HTTP_204_NO_CONTENT)


@router.post("/bulk", response_model=DiscoveryJobBulkResult, summary="Create many Discovery Job records")
async def bulk_create(
    payloads: list[DiscoveryJobCreate],
    service: DiscoveryJobService = Depends(get_service),
    _: Principal = Depends(require_write),
) -> DiscoveryJobBulkResult:
    return await service.bulk_create(payloads)


@router.post("/reconcile", response_model=list[ReconciliationItem], summary="Reconcile external Discovery Job records")
async def reconcile(
    records: list[dict[str, object]],
    apply: bool = Query(False, description="Apply the plan instead of a dry run"),
    service: DiscoveryJobService = Depends(get_service),
    _: Principal = Depends(require_write),
) -> list[ReconciliationItem]:
    return await service.reconcile(records, apply)


@router.get("/by-profile-id/{profile_id}", response_model=Page[DiscoveryJobRead], summary="List Discovery Job records by Profile Id")
async def list_by_profile_id(
    profile_id: UUID,
    page: int = Query(0, ge=0),
    size: int = Query(20, ge=1, le=200),
    service: DiscoveryJobService = Depends(get_service),
) -> Page[DiscoveryJobRead]:
    return await service.list_by_profile_id(profile_id, page, size)
