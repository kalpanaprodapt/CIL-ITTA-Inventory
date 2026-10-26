"""HTTP routes for Vnf."""
from __future__ import annotations

from uuid import UUID

from fastapi import APIRouter, Depends, Query, Request, Response, status
from fastapi.responses import PlainTextResponse

from itta_core.auth import Principal, require_read, require_write
from itta_core.dependencies import service_provider
from itta_core.diffing import ReconciliationItem
from itta_core.models import EntityMeta, LifecycleStatus
from itta_core.pagination import Page
from itta_api.domains.virtual.vnf.filters import VnfFilter
from itta_api.domains.virtual.vnf.meta import META
from itta_api.domains.virtual.vnf.schemas import (
    VnfBulkResult,
    VnfCreate,
    VnfRead,
    VnfSummary,
    VnfUpdate,
)
from itta_api.domains.virtual.vnf.service import VnfService

router = APIRouter(prefix="/api/v1/vnfs", tags=["Vnf"])
get_service = service_provider("vnf")


@router.get("/meta", response_model=EntityMeta, summary="Field catalogue for Vnf")
async def meta() -> EntityMeta:
    return META


@router.get("", response_model=Page[VnfRead], summary="Search Vnf records")
async def search(
    flt: VnfFilter = Depends(),
    page: int = Query(0, ge=0),
    size: int = Query(20, ge=1, le=200),
    sort: str | None = Query(None, description="field,asc|desc"),
    service: VnfService = Depends(get_service),
    _: Principal = Depends(require_read),
) -> Page[VnfRead]:
    return await service.search(flt, page, size, sort)


@router.get("/summaries", response_model=list[VnfSummary], summary="Pick-list of Vnf records")
async def summaries(
    status_filter: LifecycleStatus | None = Query(None, alias="status"),
    service: VnfService = Depends(get_service),
    _: Principal = Depends(require_read),
) -> list[VnfSummary]:
    return await service.summaries(status_filter)


@router.get("/stats/status", response_model=dict[str, int], summary="Counts per lifecycle status")
async def status_stats(
    service: VnfService = Depends(get_service), _: Principal = Depends(require_read)
) -> dict[str, int]:
    return await service.stats()


@router.get("/export", response_class=PlainTextResponse, summary="Export Vnf records as CSV")
async def export(
    flt: VnfFilter = Depends(),
    service: VnfService = Depends(get_service),
    _: Principal = Depends(require_read),
) -> PlainTextResponse:
    body = await service.export_csv(flt)
    headers = {"Content-Disposition": 'attachment; filename="vnfs.csv"'}
    return PlainTextResponse(body, media_type="text/csv", headers=headers)


@router.get("/by-code/{code}", response_model=VnfRead, summary="Get a Vnf by business code")
async def get_by_code(
    code: str, service: VnfService = Depends(get_service), _: Principal = Depends(require_read)
) -> VnfRead:
    return await service.get_by_code(code)


@router.head("/by-code/{code}", summary="Check whether a Vnf code exists")
async def exists(
    code: str, service: VnfService = Depends(get_service), _: Principal = Depends(require_read)
) -> Response:
    found = await service.exists(code)
    return Response(status_code=status.HTTP_200_OK if found else status.HTTP_404_NOT_FOUND)


@router.get("/{id}", response_model=VnfRead, summary="Get a Vnf by id")
async def get(
    id: UUID, service: VnfService = Depends(get_service), _: Principal = Depends(require_read)
) -> VnfRead:
    return await service.get(id)


@router.post("", response_model=VnfRead, status_code=status.HTTP_201_CREATED, summary="Create a Vnf")
async def create(
    payload: VnfCreate,
    request: Request,
    response: Response,
    service: VnfService = Depends(get_service),
    _: Principal = Depends(require_write),
) -> VnfRead:
    created = await service.create(payload)
    response.headers["Location"] = f"{request.url.path}/{created.id}"
    return created


@router.put("/{id}", response_model=VnfRead, summary="Update a Vnf")
async def update(
    id: UUID,
    payload: VnfUpdate,
    service: VnfService = Depends(get_service),
    _: Principal = Depends(require_write),
) -> VnfRead:
    return await service.update(id, payload)


@router.patch("/{id}/status", response_model=VnfRead, summary="Change lifecycle status")
async def change_status(
    id: UUID,
    new_status: LifecycleStatus = Query(..., alias="status"),
    service: VnfService = Depends(get_service),
    _: Principal = Depends(require_write),
) -> VnfRead:
    return await service.change_status(id, new_status)


@router.delete("/{id}", status_code=status.HTTP_204_NO_CONTENT, summary="Delete a Vnf")
async def delete(
    id: UUID, service: VnfService = Depends(get_service), _: Principal = Depends(require_write)
) -> Response:
    await service.delete(id)
    return Response(status_code=status.HTTP_204_NO_CONTENT)


@router.post("/bulk", response_model=VnfBulkResult, summary="Create many Vnf records")
async def bulk_create(
    payloads: list[VnfCreate],
    service: VnfService = Depends(get_service),
    _: Principal = Depends(require_write),
) -> VnfBulkResult:
    return await service.bulk_create(payloads)


@router.post("/reconcile", response_model=list[ReconciliationItem], summary="Reconcile external Vnf records")
async def reconcile(
    records: list[dict[str, object]],
    apply: bool = Query(False, description="Apply the plan instead of a dry run"),
    service: VnfService = Depends(get_service),
    _: Principal = Depends(require_write),
) -> list[ReconciliationItem]:
    return await service.reconcile(records, apply)

