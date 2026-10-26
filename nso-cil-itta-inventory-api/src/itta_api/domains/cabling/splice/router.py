"""HTTP routes for Splice."""
from __future__ import annotations

from uuid import UUID

from fastapi import APIRouter, Depends, Query, Request, Response, status
from fastapi.responses import PlainTextResponse

from itta_core.auth import Principal, require_read, require_write
from itta_core.dependencies import service_provider
from itta_core.diffing import ReconciliationItem
from itta_core.models import EntityMeta, LifecycleStatus
from itta_core.pagination import Page
from itta_api.domains.cabling.splice.filters import SpliceFilter
from itta_api.domains.cabling.splice.meta import META
from itta_api.domains.cabling.splice.schemas import (
    SpliceBulkResult,
    SpliceCreate,
    SpliceRead,
    SpliceSummary,
    SpliceUpdate,
)
from itta_api.domains.cabling.splice.service import SpliceService

router = APIRouter(prefix="/api/v1/splices", tags=["Splice"])
get_service = service_provider("splice")


@router.get("/meta", response_model=EntityMeta, summary="Field catalogue for Splice")
async def meta() -> EntityMeta:
    return META


@router.get("", response_model=Page[SpliceRead], summary="Search Splice records")
async def search(
    flt: SpliceFilter = Depends(),
    page: int = Query(0, ge=0),
    size: int = Query(20, ge=1, le=200),
    sort: str | None = Query(None, description="field,asc|desc"),
    service: SpliceService = Depends(get_service),
    _: Principal = Depends(require_read),
) -> Page[SpliceRead]:
    return await service.search(flt, page, size, sort)


@router.get("/summaries", response_model=list[SpliceSummary], summary="Pick-list of Splice records")
async def summaries(
    status_filter: LifecycleStatus | None = Query(None, alias="status"),
    service: SpliceService = Depends(get_service),
    _: Principal = Depends(require_read),
) -> list[SpliceSummary]:
    return await service.summaries(status_filter)


@router.get("/stats/status", response_model=dict[str, int], summary="Counts per lifecycle status")
async def status_stats(
    service: SpliceService = Depends(get_service), _: Principal = Depends(require_read)
) -> dict[str, int]:
    return await service.stats()


@router.get("/export", response_class=PlainTextResponse, summary="Export Splice records as CSV")
async def export(
    flt: SpliceFilter = Depends(),
    service: SpliceService = Depends(get_service),
    _: Principal = Depends(require_read),
) -> PlainTextResponse:
    body = await service.export_csv(flt)
    headers = {"Content-Disposition": 'attachment; filename="splices.csv"'}
    return PlainTextResponse(body, media_type="text/csv", headers=headers)


@router.get("/by-code/{code}", response_model=SpliceRead, summary="Get a Splice by business code")
async def get_by_code(
    code: str, service: SpliceService = Depends(get_service), _: Principal = Depends(require_read)
) -> SpliceRead:
    return await service.get_by_code(code)


@router.head("/by-code/{code}", summary="Check whether a Splice code exists")
async def exists(
    code: str, service: SpliceService = Depends(get_service), _: Principal = Depends(require_read)
) -> Response:
    found = await service.exists(code)
    return Response(status_code=status.HTTP_200_OK if found else status.HTTP_404_NOT_FOUND)


@router.get("/{id}", response_model=SpliceRead, summary="Get a Splice by id")
async def get(
    id: UUID, service: SpliceService = Depends(get_service), _: Principal = Depends(require_read)
) -> SpliceRead:
    return await service.get(id)


@router.post("", response_model=SpliceRead, status_code=status.HTTP_201_CREATED, summary="Create a Splice")
async def create(
    payload: SpliceCreate,
    request: Request,
    response: Response,
    service: SpliceService = Depends(get_service),
    _: Principal = Depends(require_write),
) -> SpliceRead:
    created = await service.create(payload)
    response.headers["Location"] = f"{request.url.path}/{created.id}"
    return created


@router.put("/{id}", response_model=SpliceRead, summary="Update a Splice")
async def update(
    id: UUID,
    payload: SpliceUpdate,
    service: SpliceService = Depends(get_service),
    _: Principal = Depends(require_write),
) -> SpliceRead:
    return await service.update(id, payload)


@router.patch("/{id}/status", response_model=SpliceRead, summary="Change lifecycle status")
async def change_status(
    id: UUID,
    new_status: LifecycleStatus = Query(..., alias="status"),
    service: SpliceService = Depends(get_service),
    _: Principal = Depends(require_write),
) -> SpliceRead:
    return await service.change_status(id, new_status)


@router.delete("/{id}", status_code=status.HTTP_204_NO_CONTENT, summary="Delete a Splice")
async def delete(
    id: UUID, service: SpliceService = Depends(get_service), _: Principal = Depends(require_write)
) -> Response:
    await service.delete(id)
    return Response(status_code=status.HTTP_204_NO_CONTENT)


@router.post("/bulk", response_model=SpliceBulkResult, summary="Create many Splice records")
async def bulk_create(
    payloads: list[SpliceCreate],
    service: SpliceService = Depends(get_service),
    _: Principal = Depends(require_write),
) -> SpliceBulkResult:
    return await service.bulk_create(payloads)


@router.post("/reconcile", response_model=list[ReconciliationItem], summary="Reconcile external Splice records")
async def reconcile(
    records: list[dict[str, object]],
    apply: bool = Query(False, description="Apply the plan instead of a dry run"),
    service: SpliceService = Depends(get_service),
    _: Principal = Depends(require_write),
) -> list[ReconciliationItem]:
    return await service.reconcile(records, apply)


@router.get("/by-a-strand-id/{a_strand_id}", response_model=Page[SpliceRead], summary="List Splice records by A Strand Id")
async def list_by_a_strand_id(
    a_strand_id: UUID,
    page: int = Query(0, ge=0),
    size: int = Query(20, ge=1, le=200),
    service: SpliceService = Depends(get_service),
) -> Page[SpliceRead]:
    return await service.list_by_a_strand_id(a_strand_id, page, size)

@router.get("/by-z-strand-id/{z_strand_id}", response_model=Page[SpliceRead], summary="List Splice records by Z Strand Id")
async def list_by_z_strand_id(
    z_strand_id: UUID,
    page: int = Query(0, ge=0),
    size: int = Query(20, ge=1, le=200),
    service: SpliceService = Depends(get_service),
) -> Page[SpliceRead]:
    return await service.list_by_z_strand_id(z_strand_id, page, size)
