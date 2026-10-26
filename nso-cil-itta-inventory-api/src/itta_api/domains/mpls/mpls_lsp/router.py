"""HTTP routes for Mpls Lsp."""
from __future__ import annotations

from uuid import UUID

from fastapi import APIRouter, Depends, Query, Request, Response, status
from fastapi.responses import PlainTextResponse

from itta_core.auth import Principal, require_read, require_write
from itta_core.dependencies import service_provider
from itta_core.diffing import ReconciliationItem
from itta_core.models import EntityMeta, LifecycleStatus
from itta_core.pagination import Page
from itta_api.domains.mpls.mpls_lsp.filters import MplsLspFilter
from itta_api.domains.mpls.mpls_lsp.meta import META
from itta_api.domains.mpls.mpls_lsp.schemas import (
    MplsLspBulkResult,
    MplsLspCreate,
    MplsLspRead,
    MplsLspSummary,
    MplsLspUpdate,
)
from itta_api.domains.mpls.mpls_lsp.service import MplsLspService

router = APIRouter(prefix="/api/v1/mpls-lsps", tags=["Mpls Lsp"])
get_service = service_provider("mpls_lsp")


@router.get("/meta", response_model=EntityMeta, summary="Field catalogue for Mpls Lsp")
async def meta() -> EntityMeta:
    return META


@router.get("", response_model=Page[MplsLspRead], summary="Search Mpls Lsp records")
async def search(
    flt: MplsLspFilter = Depends(),
    page: int = Query(0, ge=0),
    size: int = Query(20, ge=1, le=200),
    sort: str | None = Query(None, description="field,asc|desc"),
    service: MplsLspService = Depends(get_service),
    _: Principal = Depends(require_read),
) -> Page[MplsLspRead]:
    return await service.search(flt, page, size, sort)


@router.get("/summaries", response_model=list[MplsLspSummary], summary="Pick-list of Mpls Lsp records")
async def summaries(
    status_filter: LifecycleStatus | None = Query(None, alias="status"),
    service: MplsLspService = Depends(get_service),
    _: Principal = Depends(require_read),
) -> list[MplsLspSummary]:
    return await service.summaries(status_filter)


@router.get("/stats/status", response_model=dict[str, int], summary="Counts per lifecycle status")
async def status_stats(
    service: MplsLspService = Depends(get_service), _: Principal = Depends(require_read)
) -> dict[str, int]:
    return await service.stats()


@router.get("/export", response_class=PlainTextResponse, summary="Export Mpls Lsp records as CSV")
async def export(
    flt: MplsLspFilter = Depends(),
    service: MplsLspService = Depends(get_service),
    _: Principal = Depends(require_read),
) -> PlainTextResponse:
    body = await service.export_csv(flt)
    headers = {"Content-Disposition": 'attachment; filename="mpls-lsps.csv"'}
    return PlainTextResponse(body, media_type="text/csv", headers=headers)


@router.get("/by-code/{code}", response_model=MplsLspRead, summary="Get a Mpls Lsp by business code")
async def get_by_code(
    code: str, service: MplsLspService = Depends(get_service), _: Principal = Depends(require_read)
) -> MplsLspRead:
    return await service.get_by_code(code)


@router.head("/by-code/{code}", summary="Check whether a Mpls Lsp code exists")
async def exists(
    code: str, service: MplsLspService = Depends(get_service), _: Principal = Depends(require_read)
) -> Response:
    found = await service.exists(code)
    return Response(status_code=status.HTTP_200_OK if found else status.HTTP_404_NOT_FOUND)


@router.get("/{id}", response_model=MplsLspRead, summary="Get a Mpls Lsp by id")
async def get(
    id: UUID, service: MplsLspService = Depends(get_service), _: Principal = Depends(require_read)
) -> MplsLspRead:
    return await service.get(id)


@router.post("", response_model=MplsLspRead, status_code=status.HTTP_201_CREATED, summary="Create a Mpls Lsp")
async def create(
    payload: MplsLspCreate,
    request: Request,
    response: Response,
    service: MplsLspService = Depends(get_service),
    _: Principal = Depends(require_write),
) -> MplsLspRead:
    created = await service.create(payload)
    response.headers["Location"] = f"{request.url.path}/{created.id}"
    return created


@router.put("/{id}", response_model=MplsLspRead, summary="Update a Mpls Lsp")
async def update(
    id: UUID,
    payload: MplsLspUpdate,
    service: MplsLspService = Depends(get_service),
    _: Principal = Depends(require_write),
) -> MplsLspRead:
    return await service.update(id, payload)


@router.patch("/{id}/status", response_model=MplsLspRead, summary="Change lifecycle status")
async def change_status(
    id: UUID,
    new_status: LifecycleStatus = Query(..., alias="status"),
    service: MplsLspService = Depends(get_service),
    _: Principal = Depends(require_write),
) -> MplsLspRead:
    return await service.change_status(id, new_status)


@router.delete("/{id}", status_code=status.HTTP_204_NO_CONTENT, summary="Delete a Mpls Lsp")
async def delete(
    id: UUID, service: MplsLspService = Depends(get_service), _: Principal = Depends(require_write)
) -> Response:
    await service.delete(id)
    return Response(status_code=status.HTTP_204_NO_CONTENT)


@router.post("/bulk", response_model=MplsLspBulkResult, summary="Create many Mpls Lsp records")
async def bulk_create(
    payloads: list[MplsLspCreate],
    service: MplsLspService = Depends(get_service),
    _: Principal = Depends(require_write),
) -> MplsLspBulkResult:
    return await service.bulk_create(payloads)


@router.post("/reconcile", response_model=list[ReconciliationItem], summary="Reconcile external Mpls Lsp records")
async def reconcile(
    records: list[dict[str, object]],
    apply: bool = Query(False, description="Apply the plan instead of a dry run"),
    service: MplsLspService = Depends(get_service),
    _: Principal = Depends(require_write),
) -> list[ReconciliationItem]:
    return await service.reconcile(records, apply)


@router.get("/by-head-end-id/{head_end_id}", response_model=Page[MplsLspRead], summary="List Mpls Lsp records by Head End Id")
async def list_by_head_end_id(
    head_end_id: UUID,
    page: int = Query(0, ge=0),
    size: int = Query(20, ge=1, le=200),
    service: MplsLspService = Depends(get_service),
) -> Page[MplsLspRead]:
    return await service.list_by_head_end_id(head_end_id, page, size)

@router.get("/by-tail-end-id/{tail_end_id}", response_model=Page[MplsLspRead], summary="List Mpls Lsp records by Tail End Id")
async def list_by_tail_end_id(
    tail_end_id: UUID,
    page: int = Query(0, ge=0),
    size: int = Query(20, ge=1, le=200),
    service: MplsLspService = Depends(get_service),
) -> Page[MplsLspRead]:
    return await service.list_by_tail_end_id(tail_end_id, page, size)
