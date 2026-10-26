"""HTTP routes for Patch."""
from __future__ import annotations

from uuid import UUID

from fastapi import APIRouter, Depends, Query, Request, Response, status
from fastapi.responses import PlainTextResponse

from itta_core.auth import Principal, require_read, require_write
from itta_core.dependencies import service_provider
from itta_core.diffing import ReconciliationItem
from itta_core.models import EntityMeta, LifecycleStatus
from itta_core.pagination import Page
from itta_api.domains.software.patch.filters import PatchFilter
from itta_api.domains.software.patch.meta import META
from itta_api.domains.software.patch.schemas import (
    PatchBulkResult,
    PatchCreate,
    PatchRead,
    PatchSummary,
    PatchUpdate,
)
from itta_api.domains.software.patch.service import PatchService

router = APIRouter(prefix="/api/v1/patchs", tags=["Patch"])
get_service = service_provider("patch")


@router.get("/meta", response_model=EntityMeta, summary="Field catalogue for Patch")
async def meta() -> EntityMeta:
    return META


@router.get("", response_model=Page[PatchRead], summary="Search Patch records")
async def search(
    flt: PatchFilter = Depends(),
    page: int = Query(0, ge=0),
    size: int = Query(20, ge=1, le=200),
    sort: str | None = Query(None, description="field,asc|desc"),
    service: PatchService = Depends(get_service),
    _: Principal = Depends(require_read),
) -> Page[PatchRead]:
    return await service.search(flt, page, size, sort)


@router.get("/summaries", response_model=list[PatchSummary], summary="Pick-list of Patch records")
async def summaries(
    status_filter: LifecycleStatus | None = Query(None, alias="status"),
    service: PatchService = Depends(get_service),
    _: Principal = Depends(require_read),
) -> list[PatchSummary]:
    return await service.summaries(status_filter)


@router.get("/stats/status", response_model=dict[str, int], summary="Counts per lifecycle status")
async def status_stats(
    service: PatchService = Depends(get_service), _: Principal = Depends(require_read)
) -> dict[str, int]:
    return await service.stats()


@router.get("/export", response_class=PlainTextResponse, summary="Export Patch records as CSV")
async def export(
    flt: PatchFilter = Depends(),
    service: PatchService = Depends(get_service),
    _: Principal = Depends(require_read),
) -> PlainTextResponse:
    body = await service.export_csv(flt)
    headers = {"Content-Disposition": 'attachment; filename="patchs.csv"'}
    return PlainTextResponse(body, media_type="text/csv", headers=headers)


@router.get("/by-code/{code}", response_model=PatchRead, summary="Get a Patch by business code")
async def get_by_code(
    code: str, service: PatchService = Depends(get_service), _: Principal = Depends(require_read)
) -> PatchRead:
    return await service.get_by_code(code)


@router.head("/by-code/{code}", summary="Check whether a Patch code exists")
async def exists(
    code: str, service: PatchService = Depends(get_service), _: Principal = Depends(require_read)
) -> Response:
    found = await service.exists(code)
    return Response(status_code=status.HTTP_200_OK if found else status.HTTP_404_NOT_FOUND)


@router.get("/{id}", response_model=PatchRead, summary="Get a Patch by id")
async def get(
    id: UUID, service: PatchService = Depends(get_service), _: Principal = Depends(require_read)
) -> PatchRead:
    return await service.get(id)


@router.post("", response_model=PatchRead, status_code=status.HTTP_201_CREATED, summary="Create a Patch")
async def create(
    payload: PatchCreate,
    request: Request,
    response: Response,
    service: PatchService = Depends(get_service),
    _: Principal = Depends(require_write),
) -> PatchRead:
    created = await service.create(payload)
    response.headers["Location"] = f"{request.url.path}/{created.id}"
    return created


@router.put("/{id}", response_model=PatchRead, summary="Update a Patch")
async def update(
    id: UUID,
    payload: PatchUpdate,
    service: PatchService = Depends(get_service),
    _: Principal = Depends(require_write),
) -> PatchRead:
    return await service.update(id, payload)


@router.patch("/{id}/status", response_model=PatchRead, summary="Change lifecycle status")
async def change_status(
    id: UUID,
    new_status: LifecycleStatus = Query(..., alias="status"),
    service: PatchService = Depends(get_service),
    _: Principal = Depends(require_write),
) -> PatchRead:
    return await service.change_status(id, new_status)


@router.delete("/{id}", status_code=status.HTTP_204_NO_CONTENT, summary="Delete a Patch")
async def delete(
    id: UUID, service: PatchService = Depends(get_service), _: Principal = Depends(require_write)
) -> Response:
    await service.delete(id)
    return Response(status_code=status.HTTP_204_NO_CONTENT)


@router.post("/bulk", response_model=PatchBulkResult, summary="Create many Patch records")
async def bulk_create(
    payloads: list[PatchCreate],
    service: PatchService = Depends(get_service),
    _: Principal = Depends(require_write),
) -> PatchBulkResult:
    return await service.bulk_create(payloads)


@router.post("/reconcile", response_model=list[ReconciliationItem], summary="Reconcile external Patch records")
async def reconcile(
    records: list[dict[str, object]],
    apply: bool = Query(False, description="Apply the plan instead of a dry run"),
    service: PatchService = Depends(get_service),
    _: Principal = Depends(require_write),
) -> list[ReconciliationItem]:
    return await service.reconcile(records, apply)


@router.get("/by-version-id/{version_id}", response_model=Page[PatchRead], summary="List Patch records by Version Id")
async def list_by_version_id(
    version_id: UUID,
    page: int = Query(0, ge=0),
    size: int = Query(20, ge=1, le=200),
    service: PatchService = Depends(get_service),
) -> Page[PatchRead]:
    return await service.list_by_version_id(version_id, page, size)
