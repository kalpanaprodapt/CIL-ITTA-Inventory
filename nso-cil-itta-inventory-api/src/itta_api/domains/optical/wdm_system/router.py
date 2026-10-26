"""HTTP routes for Wdm System."""
from __future__ import annotations

from uuid import UUID

from fastapi import APIRouter, Depends, Query, Request, Response, status
from fastapi.responses import PlainTextResponse

from itta_core.auth import Principal, require_read, require_write
from itta_core.dependencies import service_provider
from itta_core.diffing import ReconciliationItem
from itta_core.models import EntityMeta, LifecycleStatus
from itta_core.pagination import Page
from itta_api.domains.optical.wdm_system.filters import WdmSystemFilter
from itta_api.domains.optical.wdm_system.meta import META
from itta_api.domains.optical.wdm_system.schemas import (
    WdmSystemBulkResult,
    WdmSystemCreate,
    WdmSystemRead,
    WdmSystemSummary,
    WdmSystemUpdate,
)
from itta_api.domains.optical.wdm_system.service import WdmSystemService

router = APIRouter(prefix="/api/v1/wdm-systems", tags=["Wdm System"])
get_service = service_provider("wdm_system")


@router.get("/meta", response_model=EntityMeta, summary="Field catalogue for Wdm System")
async def meta() -> EntityMeta:
    return META


@router.get("", response_model=Page[WdmSystemRead], summary="Search Wdm System records")
async def search(
    flt: WdmSystemFilter = Depends(),
    page: int = Query(0, ge=0),
    size: int = Query(20, ge=1, le=200),
    sort: str | None = Query(None, description="field,asc|desc"),
    service: WdmSystemService = Depends(get_service),
    _: Principal = Depends(require_read),
) -> Page[WdmSystemRead]:
    return await service.search(flt, page, size, sort)


@router.get("/summaries", response_model=list[WdmSystemSummary], summary="Pick-list of Wdm System records")
async def summaries(
    status_filter: LifecycleStatus | None = Query(None, alias="status"),
    service: WdmSystemService = Depends(get_service),
    _: Principal = Depends(require_read),
) -> list[WdmSystemSummary]:
    return await service.summaries(status_filter)


@router.get("/stats/status", response_model=dict[str, int], summary="Counts per lifecycle status")
async def status_stats(
    service: WdmSystemService = Depends(get_service), _: Principal = Depends(require_read)
) -> dict[str, int]:
    return await service.stats()


@router.get("/export", response_class=PlainTextResponse, summary="Export Wdm System records as CSV")
async def export(
    flt: WdmSystemFilter = Depends(),
    service: WdmSystemService = Depends(get_service),
    _: Principal = Depends(require_read),
) -> PlainTextResponse:
    body = await service.export_csv(flt)
    headers = {"Content-Disposition": 'attachment; filename="wdm-systems.csv"'}
    return PlainTextResponse(body, media_type="text/csv", headers=headers)


@router.get("/by-code/{code}", response_model=WdmSystemRead, summary="Get a Wdm System by business code")
async def get_by_code(
    code: str, service: WdmSystemService = Depends(get_service), _: Principal = Depends(require_read)
) -> WdmSystemRead:
    return await service.get_by_code(code)


@router.head("/by-code/{code}", summary="Check whether a Wdm System code exists")
async def exists(
    code: str, service: WdmSystemService = Depends(get_service), _: Principal = Depends(require_read)
) -> Response:
    found = await service.exists(code)
    return Response(status_code=status.HTTP_200_OK if found else status.HTTP_404_NOT_FOUND)


@router.get("/{id}", response_model=WdmSystemRead, summary="Get a Wdm System by id")
async def get(
    id: UUID, service: WdmSystemService = Depends(get_service), _: Principal = Depends(require_read)
) -> WdmSystemRead:
    return await service.get(id)


@router.post("", response_model=WdmSystemRead, status_code=status.HTTP_201_CREATED, summary="Create a Wdm System")
async def create(
    payload: WdmSystemCreate,
    request: Request,
    response: Response,
    service: WdmSystemService = Depends(get_service),
    _: Principal = Depends(require_write),
) -> WdmSystemRead:
    created = await service.create(payload)
    response.headers["Location"] = f"{request.url.path}/{created.id}"
    return created


@router.put("/{id}", response_model=WdmSystemRead, summary="Update a Wdm System")
async def update(
    id: UUID,
    payload: WdmSystemUpdate,
    service: WdmSystemService = Depends(get_service),
    _: Principal = Depends(require_write),
) -> WdmSystemRead:
    return await service.update(id, payload)


@router.patch("/{id}/status", response_model=WdmSystemRead, summary="Change lifecycle status")
async def change_status(
    id: UUID,
    new_status: LifecycleStatus = Query(..., alias="status"),
    service: WdmSystemService = Depends(get_service),
    _: Principal = Depends(require_write),
) -> WdmSystemRead:
    return await service.change_status(id, new_status)


@router.delete("/{id}", status_code=status.HTTP_204_NO_CONTENT, summary="Delete a Wdm System")
async def delete(
    id: UUID, service: WdmSystemService = Depends(get_service), _: Principal = Depends(require_write)
) -> Response:
    await service.delete(id)
    return Response(status_code=status.HTTP_204_NO_CONTENT)


@router.post("/bulk", response_model=WdmSystemBulkResult, summary="Create many Wdm System records")
async def bulk_create(
    payloads: list[WdmSystemCreate],
    service: WdmSystemService = Depends(get_service),
    _: Principal = Depends(require_write),
) -> WdmSystemBulkResult:
    return await service.bulk_create(payloads)


@router.post("/reconcile", response_model=list[ReconciliationItem], summary="Reconcile external Wdm System records")
async def reconcile(
    records: list[dict[str, object]],
    apply: bool = Query(False, description="Apply the plan instead of a dry run"),
    service: WdmSystemService = Depends(get_service),
    _: Principal = Depends(require_write),
) -> list[ReconciliationItem]:
    return await service.reconcile(records, apply)


@router.get("/by-vendor-id/{vendor_id}", response_model=Page[WdmSystemRead], summary="List Wdm System records by Vendor Id")
async def list_by_vendor_id(
    vendor_id: UUID,
    page: int = Query(0, ge=0),
    size: int = Query(20, ge=1, le=200),
    service: WdmSystemService = Depends(get_service),
) -> Page[WdmSystemRead]:
    return await service.list_by_vendor_id(vendor_id, page, size)
