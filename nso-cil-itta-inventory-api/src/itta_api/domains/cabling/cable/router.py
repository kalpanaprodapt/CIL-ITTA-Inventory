"""HTTP routes for Cable."""
from __future__ import annotations

from uuid import UUID

from fastapi import APIRouter, Depends, Query, Request, Response, status
from fastapi.responses import PlainTextResponse

from itta_core.auth import Principal, require_read, require_write
from itta_core.dependencies import service_provider
from itta_core.diffing import ReconciliationItem
from itta_core.models import EntityMeta, LifecycleStatus
from itta_core.pagination import Page
from itta_api.domains.cabling.cable.filters import CableFilter
from itta_api.domains.cabling.cable.meta import META
from itta_api.domains.cabling.cable.schemas import (
    CableBulkResult,
    CableCreate,
    CableRead,
    CableSummary,
    CableUpdate,
)
from itta_api.domains.cabling.cable.service import CableService

router = APIRouter(prefix="/api/v1/cables", tags=["Cable"])
get_service = service_provider("cable")


@router.get("/meta", response_model=EntityMeta, summary="Field catalogue for Cable")
async def meta() -> EntityMeta:
    return META


@router.get("", response_model=Page[CableRead], summary="Search Cable records")
async def search(
    flt: CableFilter = Depends(),
    page: int = Query(0, ge=0),
    size: int = Query(20, ge=1, le=200),
    sort: str | None = Query(None, description="field,asc|desc"),
    service: CableService = Depends(get_service),
    _: Principal = Depends(require_read),
) -> Page[CableRead]:
    return await service.search(flt, page, size, sort)


@router.get("/summaries", response_model=list[CableSummary], summary="Pick-list of Cable records")
async def summaries(
    status_filter: LifecycleStatus | None = Query(None, alias="status"),
    service: CableService = Depends(get_service),
    _: Principal = Depends(require_read),
) -> list[CableSummary]:
    return await service.summaries(status_filter)


@router.get("/stats/status", response_model=dict[str, int], summary="Counts per lifecycle status")
async def status_stats(
    service: CableService = Depends(get_service), _: Principal = Depends(require_read)
) -> dict[str, int]:
    return await service.stats()


@router.get("/export", response_class=PlainTextResponse, summary="Export Cable records as CSV")
async def export(
    flt: CableFilter = Depends(),
    service: CableService = Depends(get_service),
    _: Principal = Depends(require_read),
) -> PlainTextResponse:
    body = await service.export_csv(flt)
    headers = {"Content-Disposition": 'attachment; filename="cables.csv"'}
    return PlainTextResponse(body, media_type="text/csv", headers=headers)


@router.get("/by-code/{code}", response_model=CableRead, summary="Get a Cable by business code")
async def get_by_code(
    code: str, service: CableService = Depends(get_service), _: Principal = Depends(require_read)
) -> CableRead:
    return await service.get_by_code(code)


@router.head("/by-code/{code}", summary="Check whether a Cable code exists")
async def exists(
    code: str, service: CableService = Depends(get_service), _: Principal = Depends(require_read)
) -> Response:
    found = await service.exists(code)
    return Response(status_code=status.HTTP_200_OK if found else status.HTTP_404_NOT_FOUND)


@router.get("/{id}", response_model=CableRead, summary="Get a Cable by id")
async def get(
    id: UUID, service: CableService = Depends(get_service), _: Principal = Depends(require_read)
) -> CableRead:
    return await service.get(id)


@router.post("", response_model=CableRead, status_code=status.HTTP_201_CREATED, summary="Create a Cable")
async def create(
    payload: CableCreate,
    request: Request,
    response: Response,
    service: CableService = Depends(get_service),
    _: Principal = Depends(require_write),
) -> CableRead:
    created = await service.create(payload)
    response.headers["Location"] = f"{request.url.path}/{created.id}"
    return created


@router.put("/{id}", response_model=CableRead, summary="Update a Cable")
async def update(
    id: UUID,
    payload: CableUpdate,
    service: CableService = Depends(get_service),
    _: Principal = Depends(require_write),
) -> CableRead:
    return await service.update(id, payload)


@router.patch("/{id}/status", response_model=CableRead, summary="Change lifecycle status")
async def change_status(
    id: UUID,
    new_status: LifecycleStatus = Query(..., alias="status"),
    service: CableService = Depends(get_service),
    _: Principal = Depends(require_write),
) -> CableRead:
    return await service.change_status(id, new_status)


@router.delete("/{id}", status_code=status.HTTP_204_NO_CONTENT, summary="Delete a Cable")
async def delete(
    id: UUID, service: CableService = Depends(get_service), _: Principal = Depends(require_write)
) -> Response:
    await service.delete(id)
    return Response(status_code=status.HTTP_204_NO_CONTENT)


@router.post("/bulk", response_model=CableBulkResult, summary="Create many Cable records")
async def bulk_create(
    payloads: list[CableCreate],
    service: CableService = Depends(get_service),
    _: Principal = Depends(require_write),
) -> CableBulkResult:
    return await service.bulk_create(payloads)


@router.post("/reconcile", response_model=list[ReconciliationItem], summary="Reconcile external Cable records")
async def reconcile(
    records: list[dict[str, object]],
    apply: bool = Query(False, description="Apply the plan instead of a dry run"),
    service: CableService = Depends(get_service),
    _: Principal = Depends(require_write),
) -> list[ReconciliationItem]:
    return await service.reconcile(records, apply)


@router.get("/by-a-end-port-id/{a_end_port_id}", response_model=Page[CableRead], summary="List Cable records by A End Port Id")
async def list_by_a_end_port_id(
    a_end_port_id: UUID,
    page: int = Query(0, ge=0),
    size: int = Query(20, ge=1, le=200),
    service: CableService = Depends(get_service),
) -> Page[CableRead]:
    return await service.list_by_a_end_port_id(a_end_port_id, page, size)

@router.get("/by-z-end-port-id/{z_end_port_id}", response_model=Page[CableRead], summary="List Cable records by Z End Port Id")
async def list_by_z_end_port_id(
    z_end_port_id: UUID,
    page: int = Query(0, ge=0),
    size: int = Query(20, ge=1, le=200),
    service: CableService = Depends(get_service),
) -> Page[CableRead]:
    return await service.list_by_z_end_port_id(z_end_port_id, page, size)
