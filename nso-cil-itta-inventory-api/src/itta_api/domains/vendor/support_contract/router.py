"""HTTP routes for Support Contract."""
from __future__ import annotations

from uuid import UUID

from fastapi import APIRouter, Depends, Query, Request, Response, status
from fastapi.responses import PlainTextResponse

from itta_core.auth import Principal, require_read, require_write
from itta_core.dependencies import service_provider
from itta_core.diffing import ReconciliationItem
from itta_core.models import EntityMeta, LifecycleStatus
from itta_core.pagination import Page
from itta_api.domains.vendor.support_contract.filters import SupportContractFilter
from itta_api.domains.vendor.support_contract.meta import META
from itta_api.domains.vendor.support_contract.schemas import (
    SupportContractBulkResult,
    SupportContractCreate,
    SupportContractRead,
    SupportContractSummary,
    SupportContractUpdate,
)
from itta_api.domains.vendor.support_contract.service import SupportContractService

router = APIRouter(prefix="/api/v1/support-contracts", tags=["Support Contract"])
get_service = service_provider("support_contract")


@router.get("/meta", response_model=EntityMeta, summary="Field catalogue for Support Contract")
async def meta() -> EntityMeta:
    return META


@router.get("", response_model=Page[SupportContractRead], summary="Search Support Contract records")
async def search(
    flt: SupportContractFilter = Depends(),
    page: int = Query(0, ge=0),
    size: int = Query(20, ge=1, le=200),
    sort: str | None = Query(None, description="field,asc|desc"),
    service: SupportContractService = Depends(get_service),
    _: Principal = Depends(require_read),
) -> Page[SupportContractRead]:
    return await service.search(flt, page, size, sort)


@router.get("/summaries", response_model=list[SupportContractSummary], summary="Pick-list of Support Contract records")
async def summaries(
    status_filter: LifecycleStatus | None = Query(None, alias="status"),
    service: SupportContractService = Depends(get_service),
    _: Principal = Depends(require_read),
) -> list[SupportContractSummary]:
    return await service.summaries(status_filter)


@router.get("/stats/status", response_model=dict[str, int], summary="Counts per lifecycle status")
async def status_stats(
    service: SupportContractService = Depends(get_service), _: Principal = Depends(require_read)
) -> dict[str, int]:
    return await service.stats()


@router.get("/export", response_class=PlainTextResponse, summary="Export Support Contract records as CSV")
async def export(
    flt: SupportContractFilter = Depends(),
    service: SupportContractService = Depends(get_service),
    _: Principal = Depends(require_read),
) -> PlainTextResponse:
    body = await service.export_csv(flt)
    headers = {"Content-Disposition": 'attachment; filename="support-contracts.csv"'}
    return PlainTextResponse(body, media_type="text/csv", headers=headers)


@router.get("/by-code/{code}", response_model=SupportContractRead, summary="Get a Support Contract by business code")
async def get_by_code(
    code: str, service: SupportContractService = Depends(get_service), _: Principal = Depends(require_read)
) -> SupportContractRead:
    return await service.get_by_code(code)


@router.head("/by-code/{code}", summary="Check whether a Support Contract code exists")
async def exists(
    code: str, service: SupportContractService = Depends(get_service), _: Principal = Depends(require_read)
) -> Response:
    found = await service.exists(code)
    return Response(status_code=status.HTTP_200_OK if found else status.HTTP_404_NOT_FOUND)


@router.get("/{id}", response_model=SupportContractRead, summary="Get a Support Contract by id")
async def get(
    id: UUID, service: SupportContractService = Depends(get_service), _: Principal = Depends(require_read)
) -> SupportContractRead:
    return await service.get(id)


@router.post("", response_model=SupportContractRead, status_code=status.HTTP_201_CREATED, summary="Create a Support Contract")
async def create(
    payload: SupportContractCreate,
    request: Request,
    response: Response,
    service: SupportContractService = Depends(get_service),
    _: Principal = Depends(require_write),
) -> SupportContractRead:
    created = await service.create(payload)
    response.headers["Location"] = f"{request.url.path}/{created.id}"
    return created


@router.put("/{id}", response_model=SupportContractRead, summary="Update a Support Contract")
async def update(
    id: UUID,
    payload: SupportContractUpdate,
    service: SupportContractService = Depends(get_service),
    _: Principal = Depends(require_write),
) -> SupportContractRead:
    return await service.update(id, payload)


@router.patch("/{id}/status", response_model=SupportContractRead, summary="Change lifecycle status")
async def change_status(
    id: UUID,
    new_status: LifecycleStatus = Query(..., alias="status"),
    service: SupportContractService = Depends(get_service),
    _: Principal = Depends(require_write),
) -> SupportContractRead:
    return await service.change_status(id, new_status)


@router.delete("/{id}", status_code=status.HTTP_204_NO_CONTENT, summary="Delete a Support Contract")
async def delete(
    id: UUID, service: SupportContractService = Depends(get_service), _: Principal = Depends(require_write)
) -> Response:
    await service.delete(id)
    return Response(status_code=status.HTTP_204_NO_CONTENT)


@router.post("/bulk", response_model=SupportContractBulkResult, summary="Create many Support Contract records")
async def bulk_create(
    payloads: list[SupportContractCreate],
    service: SupportContractService = Depends(get_service),
    _: Principal = Depends(require_write),
) -> SupportContractBulkResult:
    return await service.bulk_create(payloads)


@router.post("/reconcile", response_model=list[ReconciliationItem], summary="Reconcile external Support Contract records")
async def reconcile(
    records: list[dict[str, object]],
    apply: bool = Query(False, description="Apply the plan instead of a dry run"),
    service: SupportContractService = Depends(get_service),
    _: Principal = Depends(require_write),
) -> list[ReconciliationItem]:
    return await service.reconcile(records, apply)


@router.get("/by-vendor-id/{vendor_id}", response_model=Page[SupportContractRead], summary="List Support Contract records by Vendor Id")
async def list_by_vendor_id(
    vendor_id: UUID,
    page: int = Query(0, ge=0),
    size: int = Query(20, ge=1, le=200),
    service: SupportContractService = Depends(get_service),
) -> Page[SupportContractRead]:
    return await service.list_by_vendor_id(vendor_id, page, size)
