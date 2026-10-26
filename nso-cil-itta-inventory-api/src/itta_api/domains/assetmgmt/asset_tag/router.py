"""HTTP routes for Asset Tag."""
from __future__ import annotations

from uuid import UUID

from fastapi import APIRouter, Depends, Query, Request, Response, status
from fastapi.responses import PlainTextResponse

from itta_core.auth import Principal, require_read, require_write
from itta_core.dependencies import service_provider
from itta_core.diffing import ReconciliationItem
from itta_core.models import EntityMeta, LifecycleStatus
from itta_core.pagination import Page
from itta_api.domains.assetmgmt.asset_tag.filters import AssetTagFilter
from itta_api.domains.assetmgmt.asset_tag.meta import META
from itta_api.domains.assetmgmt.asset_tag.schemas import (
    AssetTagBulkResult,
    AssetTagCreate,
    AssetTagRead,
    AssetTagSummary,
    AssetTagUpdate,
)
from itta_api.domains.assetmgmt.asset_tag.service import AssetTagService

router = APIRouter(prefix="/api/v1/asset-tags", tags=["Asset Tag"])
get_service = service_provider("asset_tag")


@router.get("/meta", response_model=EntityMeta, summary="Field catalogue for Asset Tag")
async def meta() -> EntityMeta:
    return META


@router.get("", response_model=Page[AssetTagRead], summary="Search Asset Tag records")
async def search(
    flt: AssetTagFilter = Depends(),
    page: int = Query(0, ge=0),
    size: int = Query(20, ge=1, le=200),
    sort: str | None = Query(None, description="field,asc|desc"),
    service: AssetTagService = Depends(get_service),
    _: Principal = Depends(require_read),
) -> Page[AssetTagRead]:
    return await service.search(flt, page, size, sort)


@router.get("/summaries", response_model=list[AssetTagSummary], summary="Pick-list of Asset Tag records")
async def summaries(
    status_filter: LifecycleStatus | None = Query(None, alias="status"),
    service: AssetTagService = Depends(get_service),
    _: Principal = Depends(require_read),
) -> list[AssetTagSummary]:
    return await service.summaries(status_filter)


@router.get("/stats/status", response_model=dict[str, int], summary="Counts per lifecycle status")
async def status_stats(
    service: AssetTagService = Depends(get_service), _: Principal = Depends(require_read)
) -> dict[str, int]:
    return await service.stats()


@router.get("/export", response_class=PlainTextResponse, summary="Export Asset Tag records as CSV")
async def export(
    flt: AssetTagFilter = Depends(),
    service: AssetTagService = Depends(get_service),
    _: Principal = Depends(require_read),
) -> PlainTextResponse:
    body = await service.export_csv(flt)
    headers = {"Content-Disposition": 'attachment; filename="asset-tags.csv"'}
    return PlainTextResponse(body, media_type="text/csv", headers=headers)


@router.get("/by-code/{code}", response_model=AssetTagRead, summary="Get a Asset Tag by business code")
async def get_by_code(
    code: str, service: AssetTagService = Depends(get_service), _: Principal = Depends(require_read)
) -> AssetTagRead:
    return await service.get_by_code(code)


@router.head("/by-code/{code}", summary="Check whether a Asset Tag code exists")
async def exists(
    code: str, service: AssetTagService = Depends(get_service), _: Principal = Depends(require_read)
) -> Response:
    found = await service.exists(code)
    return Response(status_code=status.HTTP_200_OK if found else status.HTTP_404_NOT_FOUND)


@router.get("/{id}", response_model=AssetTagRead, summary="Get a Asset Tag by id")
async def get(
    id: UUID, service: AssetTagService = Depends(get_service), _: Principal = Depends(require_read)
) -> AssetTagRead:
    return await service.get(id)


@router.post("", response_model=AssetTagRead, status_code=status.HTTP_201_CREATED, summary="Create a Asset Tag")
async def create(
    payload: AssetTagCreate,
    request: Request,
    response: Response,
    service: AssetTagService = Depends(get_service),
    _: Principal = Depends(require_write),
) -> AssetTagRead:
    created = await service.create(payload)
    response.headers["Location"] = f"{request.url.path}/{created.id}"
    return created


@router.put("/{id}", response_model=AssetTagRead, summary="Update a Asset Tag")
async def update(
    id: UUID,
    payload: AssetTagUpdate,
    service: AssetTagService = Depends(get_service),
    _: Principal = Depends(require_write),
) -> AssetTagRead:
    return await service.update(id, payload)


@router.patch("/{id}/status", response_model=AssetTagRead, summary="Change lifecycle status")
async def change_status(
    id: UUID,
    new_status: LifecycleStatus = Query(..., alias="status"),
    service: AssetTagService = Depends(get_service),
    _: Principal = Depends(require_write),
) -> AssetTagRead:
    return await service.change_status(id, new_status)


@router.delete("/{id}", status_code=status.HTTP_204_NO_CONTENT, summary="Delete a Asset Tag")
async def delete(
    id: UUID, service: AssetTagService = Depends(get_service), _: Principal = Depends(require_write)
) -> Response:
    await service.delete(id)
    return Response(status_code=status.HTTP_204_NO_CONTENT)


@router.post("/bulk", response_model=AssetTagBulkResult, summary="Create many Asset Tag records")
async def bulk_create(
    payloads: list[AssetTagCreate],
    service: AssetTagService = Depends(get_service),
    _: Principal = Depends(require_write),
) -> AssetTagBulkResult:
    return await service.bulk_create(payloads)


@router.post("/reconcile", response_model=list[ReconciliationItem], summary="Reconcile external Asset Tag records")
async def reconcile(
    records: list[dict[str, object]],
    apply: bool = Query(False, description="Apply the plan instead of a dry run"),
    service: AssetTagService = Depends(get_service),
    _: Principal = Depends(require_write),
) -> list[ReconciliationItem]:
    return await service.reconcile(records, apply)


@router.get("/by-asset-id/{asset_id}", response_model=Page[AssetTagRead], summary="List Asset Tag records by Asset Id")
async def list_by_asset_id(
    asset_id: UUID,
    page: int = Query(0, ge=0),
    size: int = Query(20, ge=1, le=200),
    service: AssetTagService = Depends(get_service),
) -> Page[AssetTagRead]:
    return await service.list_by_asset_id(asset_id, page, size)
