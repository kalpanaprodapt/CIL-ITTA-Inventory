"""HTTP routes for Pseudo Wire."""
from __future__ import annotations

from uuid import UUID

from fastapi import APIRouter, Depends, Query, Request, Response, status
from fastapi.responses import PlainTextResponse

from itta_core.auth import Principal, require_read, require_write
from itta_core.dependencies import service_provider
from itta_core.diffing import ReconciliationItem
from itta_core.models import EntityMeta, LifecycleStatus
from itta_core.pagination import Page
from itta_api.domains.mpls.pseudo_wire.filters import PseudoWireFilter
from itta_api.domains.mpls.pseudo_wire.meta import META
from itta_api.domains.mpls.pseudo_wire.schemas import (
    PseudoWireBulkResult,
    PseudoWireCreate,
    PseudoWireRead,
    PseudoWireSummary,
    PseudoWireUpdate,
)
from itta_api.domains.mpls.pseudo_wire.service import PseudoWireService

router = APIRouter(prefix="/api/v1/pseudo-wires", tags=["Pseudo Wire"])
get_service = service_provider("pseudo_wire")


@router.get("/meta", response_model=EntityMeta, summary="Field catalogue for Pseudo Wire")
async def meta() -> EntityMeta:
    return META


@router.get("", response_model=Page[PseudoWireRead], summary="Search Pseudo Wire records")
async def search(
    flt: PseudoWireFilter = Depends(),
    page: int = Query(0, ge=0),
    size: int = Query(20, ge=1, le=200),
    sort: str | None = Query(None, description="field,asc|desc"),
    service: PseudoWireService = Depends(get_service),
    _: Principal = Depends(require_read),
) -> Page[PseudoWireRead]:
    return await service.search(flt, page, size, sort)


@router.get("/summaries", response_model=list[PseudoWireSummary], summary="Pick-list of Pseudo Wire records")
async def summaries(
    status_filter: LifecycleStatus | None = Query(None, alias="status"),
    service: PseudoWireService = Depends(get_service),
    _: Principal = Depends(require_read),
) -> list[PseudoWireSummary]:
    return await service.summaries(status_filter)


@router.get("/stats/status", response_model=dict[str, int], summary="Counts per lifecycle status")
async def status_stats(
    service: PseudoWireService = Depends(get_service), _: Principal = Depends(require_read)
) -> dict[str, int]:
    return await service.stats()


@router.get("/export", response_class=PlainTextResponse, summary="Export Pseudo Wire records as CSV")
async def export(
    flt: PseudoWireFilter = Depends(),
    service: PseudoWireService = Depends(get_service),
    _: Principal = Depends(require_read),
) -> PlainTextResponse:
    body = await service.export_csv(flt)
    headers = {"Content-Disposition": 'attachment; filename="pseudo-wires.csv"'}
    return PlainTextResponse(body, media_type="text/csv", headers=headers)


@router.get("/by-code/{code}", response_model=PseudoWireRead, summary="Get a Pseudo Wire by business code")
async def get_by_code(
    code: str, service: PseudoWireService = Depends(get_service), _: Principal = Depends(require_read)
) -> PseudoWireRead:
    return await service.get_by_code(code)


@router.head("/by-code/{code}", summary="Check whether a Pseudo Wire code exists")
async def exists(
    code: str, service: PseudoWireService = Depends(get_service), _: Principal = Depends(require_read)
) -> Response:
    found = await service.exists(code)
    return Response(status_code=status.HTTP_200_OK if found else status.HTTP_404_NOT_FOUND)


@router.get("/{id}", response_model=PseudoWireRead, summary="Get a Pseudo Wire by id")
async def get(
    id: UUID, service: PseudoWireService = Depends(get_service), _: Principal = Depends(require_read)
) -> PseudoWireRead:
    return await service.get(id)


@router.post("", response_model=PseudoWireRead, status_code=status.HTTP_201_CREATED, summary="Create a Pseudo Wire")
async def create(
    payload: PseudoWireCreate,
    request: Request,
    response: Response,
    service: PseudoWireService = Depends(get_service),
    _: Principal = Depends(require_write),
) -> PseudoWireRead:
    created = await service.create(payload)
    response.headers["Location"] = f"{request.url.path}/{created.id}"
    return created


@router.put("/{id}", response_model=PseudoWireRead, summary="Update a Pseudo Wire")
async def update(
    id: UUID,
    payload: PseudoWireUpdate,
    service: PseudoWireService = Depends(get_service),
    _: Principal = Depends(require_write),
) -> PseudoWireRead:
    return await service.update(id, payload)


@router.patch("/{id}/status", response_model=PseudoWireRead, summary="Change lifecycle status")
async def change_status(
    id: UUID,
    new_status: LifecycleStatus = Query(..., alias="status"),
    service: PseudoWireService = Depends(get_service),
    _: Principal = Depends(require_write),
) -> PseudoWireRead:
    return await service.change_status(id, new_status)


@router.delete("/{id}", status_code=status.HTTP_204_NO_CONTENT, summary="Delete a Pseudo Wire")
async def delete(
    id: UUID, service: PseudoWireService = Depends(get_service), _: Principal = Depends(require_write)
) -> Response:
    await service.delete(id)
    return Response(status_code=status.HTTP_204_NO_CONTENT)


@router.post("/bulk", response_model=PseudoWireBulkResult, summary="Create many Pseudo Wire records")
async def bulk_create(
    payloads: list[PseudoWireCreate],
    service: PseudoWireService = Depends(get_service),
    _: Principal = Depends(require_write),
) -> PseudoWireBulkResult:
    return await service.bulk_create(payloads)


@router.post("/reconcile", response_model=list[ReconciliationItem], summary="Reconcile external Pseudo Wire records")
async def reconcile(
    records: list[dict[str, object]],
    apply: bool = Query(False, description="Apply the plan instead of a dry run"),
    service: PseudoWireService = Depends(get_service),
    _: Principal = Depends(require_write),
) -> list[ReconciliationItem]:
    return await service.reconcile(records, apply)


@router.get("/by-a-end-id/{a_end_id}", response_model=Page[PseudoWireRead], summary="List Pseudo Wire records by A End Id")
async def list_by_a_end_id(
    a_end_id: UUID,
    page: int = Query(0, ge=0),
    size: int = Query(20, ge=1, le=200),
    service: PseudoWireService = Depends(get_service),
) -> Page[PseudoWireRead]:
    return await service.list_by_a_end_id(a_end_id, page, size)

@router.get("/by-z-end-id/{z_end_id}", response_model=Page[PseudoWireRead], summary="List Pseudo Wire records by Z End Id")
async def list_by_z_end_id(
    z_end_id: UUID,
    page: int = Query(0, ge=0),
    size: int = Query(20, ge=1, le=200),
    service: PseudoWireService = Depends(get_service),
) -> Page[PseudoWireRead]:
    return await service.list_by_z_end_id(z_end_id, page, size)
