"""HTTP routes for Sku."""
from __future__ import annotations

from uuid import UUID

from fastapi import APIRouter, Depends, Query, Request, Response, status
from fastapi.responses import PlainTextResponse

from itta_core.auth import Principal, require_read, require_write
from itta_core.dependencies import service_provider
from itta_core.diffing import ReconciliationItem
from itta_core.models import EntityMeta, LifecycleStatus
from itta_core.pagination import Page
from itta_api.domains.vendor.sku.filters import SkuFilter
from itta_api.domains.vendor.sku.meta import META
from itta_api.domains.vendor.sku.schemas import (
    SkuBulkResult,
    SkuCreate,
    SkuRead,
    SkuSummary,
    SkuUpdate,
)
from itta_api.domains.vendor.sku.service import SkuService

router = APIRouter(prefix="/api/v1/skus", tags=["Sku"])
get_service = service_provider("sku")


@router.get("/meta", response_model=EntityMeta, summary="Field catalogue for Sku")
async def meta() -> EntityMeta:
    return META


@router.get("", response_model=Page[SkuRead], summary="Search Sku records")
async def search(
    flt: SkuFilter = Depends(),
    page: int = Query(0, ge=0),
    size: int = Query(20, ge=1, le=200),
    sort: str | None = Query(None, description="field,asc|desc"),
    service: SkuService = Depends(get_service),
    _: Principal = Depends(require_read),
) -> Page[SkuRead]:
    return await service.search(flt, page, size, sort)


@router.get("/summaries", response_model=list[SkuSummary], summary="Pick-list of Sku records")
async def summaries(
    status_filter: LifecycleStatus | None = Query(None, alias="status"),
    service: SkuService = Depends(get_service),
    _: Principal = Depends(require_read),
) -> list[SkuSummary]:
    return await service.summaries(status_filter)


@router.get("/stats/status", response_model=dict[str, int], summary="Counts per lifecycle status")
async def status_stats(
    service: SkuService = Depends(get_service), _: Principal = Depends(require_read)
) -> dict[str, int]:
    return await service.stats()


@router.get("/export", response_class=PlainTextResponse, summary="Export Sku records as CSV")
async def export(
    flt: SkuFilter = Depends(),
    service: SkuService = Depends(get_service),
    _: Principal = Depends(require_read),
) -> PlainTextResponse:
    body = await service.export_csv(flt)
    headers = {"Content-Disposition": 'attachment; filename="skus.csv"'}
    return PlainTextResponse(body, media_type="text/csv", headers=headers)


@router.get("/by-code/{code}", response_model=SkuRead, summary="Get a Sku by business code")
async def get_by_code(
    code: str, service: SkuService = Depends(get_service), _: Principal = Depends(require_read)
) -> SkuRead:
    return await service.get_by_code(code)


@router.head("/by-code/{code}", summary="Check whether a Sku code exists")
async def exists(
    code: str, service: SkuService = Depends(get_service), _: Principal = Depends(require_read)
) -> Response:
    found = await service.exists(code)
    return Response(status_code=status.HTTP_200_OK if found else status.HTTP_404_NOT_FOUND)


@router.get("/{id}", response_model=SkuRead, summary="Get a Sku by id")
async def get(
    id: UUID, service: SkuService = Depends(get_service), _: Principal = Depends(require_read)
) -> SkuRead:
    return await service.get(id)


@router.post("", response_model=SkuRead, status_code=status.HTTP_201_CREATED, summary="Create a Sku")
async def create(
    payload: SkuCreate,
    request: Request,
    response: Response,
    service: SkuService = Depends(get_service),
    _: Principal = Depends(require_write),
) -> SkuRead:
    created = await service.create(payload)
    response.headers["Location"] = f"{request.url.path}/{created.id}"
    return created


@router.put("/{id}", response_model=SkuRead, summary="Update a Sku")
async def update(
    id: UUID,
    payload: SkuUpdate,
    service: SkuService = Depends(get_service),
    _: Principal = Depends(require_write),
) -> SkuRead:
    return await service.update(id, payload)


@router.patch("/{id}/status", response_model=SkuRead, summary="Change lifecycle status")
async def change_status(
    id: UUID,
    new_status: LifecycleStatus = Query(..., alias="status"),
    service: SkuService = Depends(get_service),
    _: Principal = Depends(require_write),
) -> SkuRead:
    return await service.change_status(id, new_status)


@router.delete("/{id}", status_code=status.HTTP_204_NO_CONTENT, summary="Delete a Sku")
async def delete(
    id: UUID, service: SkuService = Depends(get_service), _: Principal = Depends(require_write)
) -> Response:
    await service.delete(id)
    return Response(status_code=status.HTTP_204_NO_CONTENT)


@router.post("/bulk", response_model=SkuBulkResult, summary="Create many Sku records")
async def bulk_create(
    payloads: list[SkuCreate],
    service: SkuService = Depends(get_service),
    _: Principal = Depends(require_write),
) -> SkuBulkResult:
    return await service.bulk_create(payloads)


@router.post("/reconcile", response_model=list[ReconciliationItem], summary="Reconcile external Sku records")
async def reconcile(
    records: list[dict[str, object]],
    apply: bool = Query(False, description="Apply the plan instead of a dry run"),
    service: SkuService = Depends(get_service),
    _: Principal = Depends(require_write),
) -> list[ReconciliationItem]:
    return await service.reconcile(records, apply)


@router.get("/by-model-id/{model_id}", response_model=Page[SkuRead], summary="List Sku records by Model Id")
async def list_by_model_id(
    model_id: UUID,
    page: int = Query(0, ge=0),
    size: int = Query(20, ge=1, le=200),
    service: SkuService = Depends(get_service),
) -> Page[SkuRead]:
    return await service.list_by_model_id(model_id, page, size)
