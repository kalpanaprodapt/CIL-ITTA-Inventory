"""HTTP routes for Asset."""
from __future__ import annotations

from uuid import UUID

from fastapi import APIRouter, Depends, Query, Request, Response, status
from fastapi.responses import PlainTextResponse

from itta_core.auth import Principal, require_read, require_write
from itta_core.dependencies import service_provider
from itta_core.diffing import ReconciliationItem
from itta_core.models import EntityMeta, LifecycleStatus
from itta_core.pagination import Page
from itta_api.domains.assetmgmt.asset.filters import AssetFilter
from itta_api.domains.assetmgmt.asset.meta import META
from itta_api.domains.assetmgmt.asset.schemas import (
    AssetBulkResult,
    AssetCreate,
    AssetRead,
    AssetSummary,
    AssetUpdate,
)
from itta_api.domains.assetmgmt.asset.service import AssetService

router = APIRouter(prefix="/api/v1/assets", tags=["Asset"])
get_service = service_provider("asset")


@router.get("/meta", response_model=EntityMeta, summary="Field catalogue for Asset")
async def meta() -> EntityMeta:
    return META


@router.get("", response_model=Page[AssetRead], summary="Search Asset records")
async def search(
    flt: AssetFilter = Depends(),
    page: int = Query(0, ge=0),
    size: int = Query(20, ge=1, le=200),
    sort: str | None = Query(None, description="field,asc|desc"),
    service: AssetService = Depends(get_service),
    _: Principal = Depends(require_read),
) -> Page[AssetRead]:
    return await service.search(flt, page, size, sort)


@router.get("/summaries", response_model=list[AssetSummary], summary="Pick-list of Asset records")
async def summaries(
    status_filter: LifecycleStatus | None = Query(None, alias="status"),
    service: AssetService = Depends(get_service),
    _: Principal = Depends(require_read),
) -> list[AssetSummary]:
    return await service.summaries(status_filter)


@router.get("/stats/status", response_model=dict[str, int], summary="Counts per lifecycle status")
async def status_stats(
    service: AssetService = Depends(get_service), _: Principal = Depends(require_read)
) -> dict[str, int]:
    return await service.stats()


@router.get("/export", response_class=PlainTextResponse, summary="Export Asset records as CSV")
async def export(
    flt: AssetFilter = Depends(),
    service: AssetService = Depends(get_service),
    _: Principal = Depends(require_read),
) -> PlainTextResponse:
    body = await service.export_csv(flt)
    headers = {"Content-Disposition": 'attachment; filename="assets.csv"'}
    return PlainTextResponse(body, media_type="text/csv", headers=headers)


@router.get("/by-code/{code}", response_model=AssetRead, summary="Get a Asset by business code")
async def get_by_code(
    code: str, service: AssetService = Depends(get_service), _: Principal = Depends(require_read)
) -> AssetRead:
    return await service.get_by_code(code)


@router.head("/by-code/{code}", summary="Check whether a Asset code exists")
async def exists(
    code: str, service: AssetService = Depends(get_service), _: Principal = Depends(require_read)
) -> Response:
    found = await service.exists(code)
    return Response(status_code=status.HTTP_200_OK if found else status.HTTP_404_NOT_FOUND)


@router.get("/{id}", response_model=AssetRead, summary="Get a Asset by id")
async def get(
    id: UUID, service: AssetService = Depends(get_service), _: Principal = Depends(require_read)
) -> AssetRead:
    return await service.get(id)


@router.post("", response_model=AssetRead, status_code=status.HTTP_201_CREATED, summary="Create a Asset")
async def create(
    payload: AssetCreate,
    request: Request,
    response: Response,
    service: AssetService = Depends(get_service),
    _: Principal = Depends(require_write),
) -> AssetRead:
    created = await service.create(payload)
    response.headers["Location"] = f"{request.url.path}/{created.id}"
    return created


@router.put("/{id}", response_model=AssetRead, summary="Update a Asset")
async def update(
    id: UUID,
    payload: AssetUpdate,
    service: AssetService = Depends(get_service),
    _: Principal = Depends(require_write),
) -> AssetRead:
    return await service.update(id, payload)


@router.patch("/{id}/status", response_model=AssetRead, summary="Change lifecycle status")
async def change_status(
    id: UUID,
    new_status: LifecycleStatus = Query(..., alias="status"),
    service: AssetService = Depends(get_service),
    _: Principal = Depends(require_write),
) -> AssetRead:
    return await service.change_status(id, new_status)


@router.delete("/{id}", status_code=status.HTTP_204_NO_CONTENT, summary="Delete a Asset")
async def delete(
    id: UUID, service: AssetService = Depends(get_service), _: Principal = Depends(require_write)
) -> Response:
    await service.delete(id)
    return Response(status_code=status.HTTP_204_NO_CONTENT)


@router.post("/bulk", response_model=AssetBulkResult, summary="Create many Asset records")
async def bulk_create(
    payloads: list[AssetCreate],
    service: AssetService = Depends(get_service),
    _: Principal = Depends(require_write),
) -> AssetBulkResult:
    return await service.bulk_create(payloads)


@router.post("/reconcile", response_model=list[ReconciliationItem], summary="Reconcile external Asset records")
async def reconcile(
    records: list[dict[str, object]],
    apply: bool = Query(False, description="Apply the plan instead of a dry run"),
    service: AssetService = Depends(get_service),
    _: Principal = Depends(require_write),
) -> list[ReconciliationItem]:
    return await service.reconcile(records, apply)

