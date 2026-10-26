"""HTTP routes for Customer."""
from __future__ import annotations

from uuid import UUID

from fastapi import APIRouter, Depends, Query, Request, Response, status
from fastapi.responses import PlainTextResponse

from itta_core.auth import Principal, require_read, require_write
from itta_core.dependencies import service_provider
from itta_core.diffing import ReconciliationItem
from itta_core.models import EntityMeta, LifecycleStatus
from itta_core.pagination import Page
from itta_api.domains.customer.customer.filters import CustomerFilter
from itta_api.domains.customer.customer.meta import META
from itta_api.domains.customer.customer.schemas import (
    CustomerBulkResult,
    CustomerCreate,
    CustomerRead,
    CustomerSummary,
    CustomerUpdate,
)
from itta_api.domains.customer.customer.service import CustomerService

router = APIRouter(prefix="/api/v1/customers", tags=["Customer"])
get_service = service_provider("customer")


@router.get("/meta", response_model=EntityMeta, summary="Field catalogue for Customer")
async def meta() -> EntityMeta:
    return META


@router.get("", response_model=Page[CustomerRead], summary="Search Customer records")
async def search(
    flt: CustomerFilter = Depends(),
    page: int = Query(0, ge=0),
    size: int = Query(20, ge=1, le=200),
    sort: str | None = Query(None, description="field,asc|desc"),
    service: CustomerService = Depends(get_service),
    _: Principal = Depends(require_read),
) -> Page[CustomerRead]:
    return await service.search(flt, page, size, sort)


@router.get("/summaries", response_model=list[CustomerSummary], summary="Pick-list of Customer records")
async def summaries(
    status_filter: LifecycleStatus | None = Query(None, alias="status"),
    service: CustomerService = Depends(get_service),
    _: Principal = Depends(require_read),
) -> list[CustomerSummary]:
    return await service.summaries(status_filter)


@router.get("/stats/status", response_model=dict[str, int], summary="Counts per lifecycle status")
async def status_stats(
    service: CustomerService = Depends(get_service), _: Principal = Depends(require_read)
) -> dict[str, int]:
    return await service.stats()


@router.get("/export", response_class=PlainTextResponse, summary="Export Customer records as CSV")
async def export(
    flt: CustomerFilter = Depends(),
    service: CustomerService = Depends(get_service),
    _: Principal = Depends(require_read),
) -> PlainTextResponse:
    body = await service.export_csv(flt)
    headers = {"Content-Disposition": 'attachment; filename="customers.csv"'}
    return PlainTextResponse(body, media_type="text/csv", headers=headers)


@router.get("/by-code/{code}", response_model=CustomerRead, summary="Get a Customer by business code")
async def get_by_code(
    code: str, service: CustomerService = Depends(get_service), _: Principal = Depends(require_read)
) -> CustomerRead:
    return await service.get_by_code(code)


@router.head("/by-code/{code}", summary="Check whether a Customer code exists")
async def exists(
    code: str, service: CustomerService = Depends(get_service), _: Principal = Depends(require_read)
) -> Response:
    found = await service.exists(code)
    return Response(status_code=status.HTTP_200_OK if found else status.HTTP_404_NOT_FOUND)


@router.get("/{id}", response_model=CustomerRead, summary="Get a Customer by id")
async def get(
    id: UUID, service: CustomerService = Depends(get_service), _: Principal = Depends(require_read)
) -> CustomerRead:
    return await service.get(id)


@router.post("", response_model=CustomerRead, status_code=status.HTTP_201_CREATED, summary="Create a Customer")
async def create(
    payload: CustomerCreate,
    request: Request,
    response: Response,
    service: CustomerService = Depends(get_service),
    _: Principal = Depends(require_write),
) -> CustomerRead:
    created = await service.create(payload)
    response.headers["Location"] = f"{request.url.path}/{created.id}"
    return created


@router.put("/{id}", response_model=CustomerRead, summary="Update a Customer")
async def update(
    id: UUID,
    payload: CustomerUpdate,
    service: CustomerService = Depends(get_service),
    _: Principal = Depends(require_write),
) -> CustomerRead:
    return await service.update(id, payload)


@router.patch("/{id}/status", response_model=CustomerRead, summary="Change lifecycle status")
async def change_status(
    id: UUID,
    new_status: LifecycleStatus = Query(..., alias="status"),
    service: CustomerService = Depends(get_service),
    _: Principal = Depends(require_write),
) -> CustomerRead:
    return await service.change_status(id, new_status)


@router.delete("/{id}", status_code=status.HTTP_204_NO_CONTENT, summary="Delete a Customer")
async def delete(
    id: UUID, service: CustomerService = Depends(get_service), _: Principal = Depends(require_write)
) -> Response:
    await service.delete(id)
    return Response(status_code=status.HTTP_204_NO_CONTENT)


@router.post("/bulk", response_model=CustomerBulkResult, summary="Create many Customer records")
async def bulk_create(
    payloads: list[CustomerCreate],
    service: CustomerService = Depends(get_service),
    _: Principal = Depends(require_write),
) -> CustomerBulkResult:
    return await service.bulk_create(payloads)


@router.post("/reconcile", response_model=list[ReconciliationItem], summary="Reconcile external Customer records")
async def reconcile(
    records: list[dict[str, object]],
    apply: bool = Query(False, description="Apply the plan instead of a dry run"),
    service: CustomerService = Depends(get_service),
    _: Principal = Depends(require_write),
) -> list[ReconciliationItem]:
    return await service.reconcile(records, apply)

