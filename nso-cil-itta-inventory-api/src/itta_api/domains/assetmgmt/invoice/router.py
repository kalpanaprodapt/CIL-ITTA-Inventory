"""HTTP routes for Invoice."""
from __future__ import annotations

from uuid import UUID

from fastapi import APIRouter, Depends, Query, Request, Response, status
from fastapi.responses import PlainTextResponse

from itta_core.auth import Principal, require_read, require_write
from itta_core.dependencies import service_provider
from itta_core.diffing import ReconciliationItem
from itta_core.models import EntityMeta, LifecycleStatus
from itta_core.pagination import Page
from itta_api.domains.assetmgmt.invoice.filters import InvoiceFilter
from itta_api.domains.assetmgmt.invoice.meta import META
from itta_api.domains.assetmgmt.invoice.schemas import (
    InvoiceBulkResult,
    InvoiceCreate,
    InvoiceRead,
    InvoiceSummary,
    InvoiceUpdate,
)
from itta_api.domains.assetmgmt.invoice.service import InvoiceService

router = APIRouter(prefix="/api/v1/invoices", tags=["Invoice"])
get_service = service_provider("invoice")


@router.get("/meta", response_model=EntityMeta, summary="Field catalogue for Invoice")
async def meta() -> EntityMeta:
    return META


@router.get("", response_model=Page[InvoiceRead], summary="Search Invoice records")
async def search(
    flt: InvoiceFilter = Depends(),
    page: int = Query(0, ge=0),
    size: int = Query(20, ge=1, le=200),
    sort: str | None = Query(None, description="field,asc|desc"),
    service: InvoiceService = Depends(get_service),
    _: Principal = Depends(require_read),
) -> Page[InvoiceRead]:
    return await service.search(flt, page, size, sort)


@router.get("/summaries", response_model=list[InvoiceSummary], summary="Pick-list of Invoice records")
async def summaries(
    status_filter: LifecycleStatus | None = Query(None, alias="status"),
    service: InvoiceService = Depends(get_service),
    _: Principal = Depends(require_read),
) -> list[InvoiceSummary]:
    return await service.summaries(status_filter)


@router.get("/stats/status", response_model=dict[str, int], summary="Counts per lifecycle status")
async def status_stats(
    service: InvoiceService = Depends(get_service), _: Principal = Depends(require_read)
) -> dict[str, int]:
    return await service.stats()


@router.get("/export", response_class=PlainTextResponse, summary="Export Invoice records as CSV")
async def export(
    flt: InvoiceFilter = Depends(),
    service: InvoiceService = Depends(get_service),
    _: Principal = Depends(require_read),
) -> PlainTextResponse:
    body = await service.export_csv(flt)
    headers = {"Content-Disposition": 'attachment; filename="invoices.csv"'}
    return PlainTextResponse(body, media_type="text/csv", headers=headers)


@router.get("/by-code/{code}", response_model=InvoiceRead, summary="Get a Invoice by business code")
async def get_by_code(
    code: str, service: InvoiceService = Depends(get_service), _: Principal = Depends(require_read)
) -> InvoiceRead:
    return await service.get_by_code(code)


@router.head("/by-code/{code}", summary="Check whether a Invoice code exists")
async def exists(
    code: str, service: InvoiceService = Depends(get_service), _: Principal = Depends(require_read)
) -> Response:
    found = await service.exists(code)
    return Response(status_code=status.HTTP_200_OK if found else status.HTTP_404_NOT_FOUND)


@router.get("/{id}", response_model=InvoiceRead, summary="Get a Invoice by id")
async def get(
    id: UUID, service: InvoiceService = Depends(get_service), _: Principal = Depends(require_read)
) -> InvoiceRead:
    return await service.get(id)


@router.post("", response_model=InvoiceRead, status_code=status.HTTP_201_CREATED, summary="Create a Invoice")
async def create(
    payload: InvoiceCreate,
    request: Request,
    response: Response,
    service: InvoiceService = Depends(get_service),
    _: Principal = Depends(require_write),
) -> InvoiceRead:
    created = await service.create(payload)
    response.headers["Location"] = f"{request.url.path}/{created.id}"
    return created


@router.put("/{id}", response_model=InvoiceRead, summary="Update a Invoice")
async def update(
    id: UUID,
    payload: InvoiceUpdate,
    service: InvoiceService = Depends(get_service),
    _: Principal = Depends(require_write),
) -> InvoiceRead:
    return await service.update(id, payload)


@router.patch("/{id}/status", response_model=InvoiceRead, summary="Change lifecycle status")
async def change_status(
    id: UUID,
    new_status: LifecycleStatus = Query(..., alias="status"),
    service: InvoiceService = Depends(get_service),
    _: Principal = Depends(require_write),
) -> InvoiceRead:
    return await service.change_status(id, new_status)


@router.delete("/{id}", status_code=status.HTTP_204_NO_CONTENT, summary="Delete a Invoice")
async def delete(
    id: UUID, service: InvoiceService = Depends(get_service), _: Principal = Depends(require_write)
) -> Response:
    await service.delete(id)
    return Response(status_code=status.HTTP_204_NO_CONTENT)


@router.post("/bulk", response_model=InvoiceBulkResult, summary="Create many Invoice records")
async def bulk_create(
    payloads: list[InvoiceCreate],
    service: InvoiceService = Depends(get_service),
    _: Principal = Depends(require_write),
) -> InvoiceBulkResult:
    return await service.bulk_create(payloads)


@router.post("/reconcile", response_model=list[ReconciliationItem], summary="Reconcile external Invoice records")
async def reconcile(
    records: list[dict[str, object]],
    apply: bool = Query(False, description="Apply the plan instead of a dry run"),
    service: InvoiceService = Depends(get_service),
    _: Principal = Depends(require_write),
) -> list[ReconciliationItem]:
    return await service.reconcile(records, apply)


@router.get("/by-po-id/{po_id}", response_model=Page[InvoiceRead], summary="List Invoice records by Po Id")
async def list_by_po_id(
    po_id: UUID,
    page: int = Query(0, ge=0),
    size: int = Query(20, ge=1, le=200),
    service: InvoiceService = Depends(get_service),
) -> Page[InvoiceRead]:
    return await service.list_by_po_id(po_id, page, size)
