"""HTTP routes for Discovered Asset."""
from __future__ import annotations

from uuid import UUID

from fastapi import APIRouter, Depends, Query, Request, Response, status
from fastapi.responses import PlainTextResponse

from itta_core.auth import Principal, require_read, require_write
from itta_core.dependencies import service_provider
from itta_core.diffing import ReconciliationItem
from itta_core.models import EntityMeta, LifecycleStatus
from itta_core.pagination import Page
from itta_api.domains.discovery.discovered_asset.filters import DiscoveredAssetFilter
from itta_api.domains.discovery.discovered_asset.meta import META
from itta_api.domains.discovery.discovered_asset.schemas import (
    DiscoveredAssetBulkResult,
    DiscoveredAssetCreate,
    DiscoveredAssetRead,
    DiscoveredAssetSummary,
    DiscoveredAssetUpdate,
)
from itta_api.domains.discovery.discovered_asset.service import DiscoveredAssetService

router = APIRouter(prefix="/api/v1/discovered-assets", tags=["Discovered Asset"])
get_service = service_provider("discovered_asset")


@router.get("/meta", response_model=EntityMeta, summary="Field catalogue for Discovered Asset")
async def meta() -> EntityMeta:
    return META


@router.get("", response_model=Page[DiscoveredAssetRead], summary="Search Discovered Asset records")
async def search(
    flt: DiscoveredAssetFilter = Depends(),
    page: int = Query(0, ge=0),
    size: int = Query(20, ge=1, le=200),
    sort: str | None = Query(None, description="field,asc|desc"),
    service: DiscoveredAssetService = Depends(get_service),
    _: Principal = Depends(require_read),
) -> Page[DiscoveredAssetRead]:
    return await service.search(flt, page, size, sort)


@router.get("/summaries", response_model=list[DiscoveredAssetSummary], summary="Pick-list of Discovered Asset records")
async def summaries(
    status_filter: LifecycleStatus | None = Query(None, alias="status"),
    service: DiscoveredAssetService = Depends(get_service),
    _: Principal = Depends(require_read),
) -> list[DiscoveredAssetSummary]:
    return await service.summaries(status_filter)


@router.get("/stats/status", response_model=dict[str, int], summary="Counts per lifecycle status")
async def status_stats(
    service: DiscoveredAssetService = Depends(get_service), _: Principal = Depends(require_read)
) -> dict[str, int]:
    return await service.stats()


@router.get("/export", response_class=PlainTextResponse, summary="Export Discovered Asset records as CSV")
async def export(
    flt: DiscoveredAssetFilter = Depends(),
    service: DiscoveredAssetService = Depends(get_service),
    _: Principal = Depends(require_read),
) -> PlainTextResponse:
    body = await service.export_csv(flt)
    headers = {"Content-Disposition": 'attachment; filename="discovered-assets.csv"'}
    return PlainTextResponse(body, media_type="text/csv", headers=headers)


@router.get("/by-code/{code}", response_model=DiscoveredAssetRead, summary="Get a Discovered Asset by business code")
async def get_by_code(
    code: str, service: DiscoveredAssetService = Depends(get_service), _: Principal = Depends(require_read)
) -> DiscoveredAssetRead:
    return await service.get_by_code(code)


@router.head("/by-code/{code}", summary="Check whether a Discovered Asset code exists")
async def exists(
    code: str, service: DiscoveredAssetService = Depends(get_service), _: Principal = Depends(require_read)
) -> Response:
    found = await service.exists(code)
    return Response(status_code=status.HTTP_200_OK if found else status.HTTP_404_NOT_FOUND)


@router.get("/{id}", response_model=DiscoveredAssetRead, summary="Get a Discovered Asset by id")
async def get(
    id: UUID, service: DiscoveredAssetService = Depends(get_service), _: Principal = Depends(require_read)
) -> DiscoveredAssetRead:
    return await service.get(id)


@router.post("", response_model=DiscoveredAssetRead, status_code=status.HTTP_201_CREATED, summary="Create a Discovered Asset")
async def create(
    payload: DiscoveredAssetCreate,
    request: Request,
    response: Response,
    service: DiscoveredAssetService = Depends(get_service),
    _: Principal = Depends(require_write),
) -> DiscoveredAssetRead:
    created = await service.create(payload)
    response.headers["Location"] = f"{request.url.path}/{created.id}"
    return created


@router.put("/{id}", response_model=DiscoveredAssetRead, summary="Update a Discovered Asset")
async def update(
    id: UUID,
    payload: DiscoveredAssetUpdate,
    service: DiscoveredAssetService = Depends(get_service),
    _: Principal = Depends(require_write),
) -> DiscoveredAssetRead:
    return await service.update(id, payload)


@router.patch("/{id}/status", response_model=DiscoveredAssetRead, summary="Change lifecycle status")
async def change_status(
    id: UUID,
    new_status: LifecycleStatus = Query(..., alias="status"),
    service: DiscoveredAssetService = Depends(get_service),
    _: Principal = Depends(require_write),
) -> DiscoveredAssetRead:
    return await service.change_status(id, new_status)


@router.delete("/{id}", status_code=status.HTTP_204_NO_CONTENT, summary="Delete a Discovered Asset")
async def delete(
    id: UUID, service: DiscoveredAssetService = Depends(get_service), _: Principal = Depends(require_write)
) -> Response:
    await service.delete(id)
    return Response(status_code=status.HTTP_204_NO_CONTENT)


@router.post("/bulk", response_model=DiscoveredAssetBulkResult, summary="Create many Discovered Asset records")
async def bulk_create(
    payloads: list[DiscoveredAssetCreate],
    service: DiscoveredAssetService = Depends(get_service),
    _: Principal = Depends(require_write),
) -> DiscoveredAssetBulkResult:
    return await service.bulk_create(payloads)


@router.post("/reconcile", response_model=list[ReconciliationItem], summary="Reconcile external Discovered Asset records")
async def reconcile(
    records: list[dict[str, object]],
    apply: bool = Query(False, description="Apply the plan instead of a dry run"),
    service: DiscoveredAssetService = Depends(get_service),
    _: Principal = Depends(require_write),
) -> list[ReconciliationItem]:
    return await service.reconcile(records, apply)


@router.get("/by-job-id/{job_id}", response_model=Page[DiscoveredAssetRead], summary="List Discovered Asset records by Job Id")
async def list_by_job_id(
    job_id: UUID,
    page: int = Query(0, ge=0),
    size: int = Query(20, ge=1, le=200),
    service: DiscoveredAssetService = Depends(get_service),
) -> Page[DiscoveredAssetRead]:
    return await service.list_by_job_id(job_id, page, size)

@router.get("/by-matched-asset-id/{matched_asset_id}", response_model=Page[DiscoveredAssetRead], summary="List Discovered Asset records by Matched Asset Id")
async def list_by_matched_asset_id(
    matched_asset_id: UUID,
    page: int = Query(0, ge=0),
    size: int = Query(20, ge=1, le=200),
    service: DiscoveredAssetService = Depends(get_service),
) -> Page[DiscoveredAssetRead]:
    return await service.list_by_matched_asset_id(matched_asset_id, page, size)
