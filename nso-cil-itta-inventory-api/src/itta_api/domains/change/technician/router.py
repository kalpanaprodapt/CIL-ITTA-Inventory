"""HTTP routes for Technician."""
from __future__ import annotations

from uuid import UUID

from fastapi import APIRouter, Depends, Query, Request, Response, status
from fastapi.responses import PlainTextResponse

from itta_core.auth import Principal, require_read, require_write
from itta_core.dependencies import service_provider
from itta_core.diffing import ReconciliationItem
from itta_core.models import EntityMeta, LifecycleStatus
from itta_core.pagination import Page
from itta_api.domains.change.technician.filters import TechnicianFilter
from itta_api.domains.change.technician.meta import META
from itta_api.domains.change.technician.schemas import (
    TechnicianBulkResult,
    TechnicianCreate,
    TechnicianRead,
    TechnicianSummary,
    TechnicianUpdate,
)
from itta_api.domains.change.technician.service import TechnicianService

router = APIRouter(prefix="/api/v1/technicians", tags=["Technician"])
get_service = service_provider("technician")


@router.get("/meta", response_model=EntityMeta, summary="Field catalogue for Technician")
async def meta() -> EntityMeta:
    return META


@router.get("", response_model=Page[TechnicianRead], summary="Search Technician records")
async def search(
    flt: TechnicianFilter = Depends(),
    page: int = Query(0, ge=0),
    size: int = Query(20, ge=1, le=200),
    sort: str | None = Query(None, description="field,asc|desc"),
    service: TechnicianService = Depends(get_service),
    _: Principal = Depends(require_read),
) -> Page[TechnicianRead]:
    return await service.search(flt, page, size, sort)


@router.get("/summaries", response_model=list[TechnicianSummary], summary="Pick-list of Technician records")
async def summaries(
    status_filter: LifecycleStatus | None = Query(None, alias="status"),
    service: TechnicianService = Depends(get_service),
    _: Principal = Depends(require_read),
) -> list[TechnicianSummary]:
    return await service.summaries(status_filter)


@router.get("/stats/status", response_model=dict[str, int], summary="Counts per lifecycle status")
async def status_stats(
    service: TechnicianService = Depends(get_service), _: Principal = Depends(require_read)
) -> dict[str, int]:
    return await service.stats()


@router.get("/export", response_class=PlainTextResponse, summary="Export Technician records as CSV")
async def export(
    flt: TechnicianFilter = Depends(),
    service: TechnicianService = Depends(get_service),
    _: Principal = Depends(require_read),
) -> PlainTextResponse:
    body = await service.export_csv(flt)
    headers = {"Content-Disposition": 'attachment; filename="technicians.csv"'}
    return PlainTextResponse(body, media_type="text/csv", headers=headers)


@router.get("/by-code/{code}", response_model=TechnicianRead, summary="Get a Technician by business code")
async def get_by_code(
    code: str, service: TechnicianService = Depends(get_service), _: Principal = Depends(require_read)
) -> TechnicianRead:
    return await service.get_by_code(code)


@router.head("/by-code/{code}", summary="Check whether a Technician code exists")
async def exists(
    code: str, service: TechnicianService = Depends(get_service), _: Principal = Depends(require_read)
) -> Response:
    found = await service.exists(code)
    return Response(status_code=status.HTTP_200_OK if found else status.HTTP_404_NOT_FOUND)


@router.get("/{id}", response_model=TechnicianRead, summary="Get a Technician by id")
async def get(
    id: UUID, service: TechnicianService = Depends(get_service), _: Principal = Depends(require_read)
) -> TechnicianRead:
    return await service.get(id)


@router.post("", response_model=TechnicianRead, status_code=status.HTTP_201_CREATED, summary="Create a Technician")
async def create(
    payload: TechnicianCreate,
    request: Request,
    response: Response,
    service: TechnicianService = Depends(get_service),
    _: Principal = Depends(require_write),
) -> TechnicianRead:
    created = await service.create(payload)
    response.headers["Location"] = f"{request.url.path}/{created.id}"
    return created


@router.put("/{id}", response_model=TechnicianRead, summary="Update a Technician")
async def update(
    id: UUID,
    payload: TechnicianUpdate,
    service: TechnicianService = Depends(get_service),
    _: Principal = Depends(require_write),
) -> TechnicianRead:
    return await service.update(id, payload)


@router.patch("/{id}/status", response_model=TechnicianRead, summary="Change lifecycle status")
async def change_status(
    id: UUID,
    new_status: LifecycleStatus = Query(..., alias="status"),
    service: TechnicianService = Depends(get_service),
    _: Principal = Depends(require_write),
) -> TechnicianRead:
    return await service.change_status(id, new_status)


@router.delete("/{id}", status_code=status.HTTP_204_NO_CONTENT, summary="Delete a Technician")
async def delete(
    id: UUID, service: TechnicianService = Depends(get_service), _: Principal = Depends(require_write)
) -> Response:
    await service.delete(id)
    return Response(status_code=status.HTTP_204_NO_CONTENT)


@router.post("/bulk", response_model=TechnicianBulkResult, summary="Create many Technician records")
async def bulk_create(
    payloads: list[TechnicianCreate],
    service: TechnicianService = Depends(get_service),
    _: Principal = Depends(require_write),
) -> TechnicianBulkResult:
    return await service.bulk_create(payloads)


@router.post("/reconcile", response_model=list[ReconciliationItem], summary="Reconcile external Technician records")
async def reconcile(
    records: list[dict[str, object]],
    apply: bool = Query(False, description="Apply the plan instead of a dry run"),
    service: TechnicianService = Depends(get_service),
    _: Principal = Depends(require_write),
) -> list[ReconciliationItem]:
    return await service.reconcile(records, apply)


@router.get("/by-home-site-id/{home_site_id}", response_model=Page[TechnicianRead], summary="List Technician records by Home Site Id")
async def list_by_home_site_id(
    home_site_id: UUID,
    page: int = Query(0, ge=0),
    size: int = Query(20, ge=1, le=200),
    service: TechnicianService = Depends(get_service),
) -> Page[TechnicianRead]:
    return await service.list_by_home_site_id(home_site_id, page, size)
