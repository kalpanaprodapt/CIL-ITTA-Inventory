"""HTTP routes for Customer Account."""
from __future__ import annotations

from uuid import UUID

from fastapi import APIRouter, Depends, Query, Request, Response, status
from fastapi.responses import PlainTextResponse

from itta_core.auth import Principal, require_read, require_write
from itta_core.dependencies import service_provider
from itta_core.diffing import ReconciliationItem
from itta_core.models import EntityMeta, LifecycleStatus
from itta_core.pagination import Page
from itta_api.domains.customer.customer_account.filters import CustomerAccountFilter
from itta_api.domains.customer.customer_account.meta import META
from itta_api.domains.customer.customer_account.schemas import (
    CustomerAccountBulkResult,
    CustomerAccountCreate,
    CustomerAccountRead,
    CustomerAccountSummary,
    CustomerAccountUpdate,
)
from itta_api.domains.customer.customer_account.service import CustomerAccountService

router = APIRouter(prefix="/api/v1/customer-accounts", tags=["Customer Account"])
get_service = service_provider("customer_account")


@router.get("/meta", response_model=EntityMeta, summary="Field catalogue for Customer Account")
async def meta() -> EntityMeta:
    return META


@router.get("", response_model=Page[CustomerAccountRead], summary="Search Customer Account records")
async def search(
    flt: CustomerAccountFilter = Depends(),
    page: int = Query(0, ge=0),
    size: int = Query(20, ge=1, le=200),
    sort: str | None = Query(None, description="field,asc|desc"),
    service: CustomerAccountService = Depends(get_service),
    _: Principal = Depends(require_read),
) -> Page[CustomerAccountRead]:
    return await service.search(flt, page, size, sort)


@router.get("/summaries", response_model=list[CustomerAccountSummary], summary="Pick-list of Customer Account records")
async def summaries(
    status_filter: LifecycleStatus | None = Query(None, alias="status"),
    service: CustomerAccountService = Depends(get_service),
    _: Principal = Depends(require_read),
) -> list[CustomerAccountSummary]:
    return await service.summaries(status_filter)


@router.get("/stats/status", response_model=dict[str, int], summary="Counts per lifecycle status")
async def status_stats(
    service: CustomerAccountService = Depends(get_service), _: Principal = Depends(require_read)
) -> dict[str, int]:
    return await service.stats()


@router.get("/export", response_class=PlainTextResponse, summary="Export Customer Account records as CSV")
async def export(
    flt: CustomerAccountFilter = Depends(),
    service: CustomerAccountService = Depends(get_service),
    _: Principal = Depends(require_read),
) -> PlainTextResponse:
    body = await service.export_csv(flt)
    headers = {"Content-Disposition": 'attachment; filename="customer-accounts.csv"'}
    return PlainTextResponse(body, media_type="text/csv", headers=headers)


@router.get("/by-code/{code}", response_model=CustomerAccountRead, summary="Get a Customer Account by business code")
async def get_by_code(
    code: str, service: CustomerAccountService = Depends(get_service), _: Principal = Depends(require_read)
) -> CustomerAccountRead:
    return await service.get_by_code(code)


@router.head("/by-code/{code}", summary="Check whether a Customer Account code exists")
async def exists(
    code: str, service: CustomerAccountService = Depends(get_service), _: Principal = Depends(require_read)
) -> Response:
    found = await service.exists(code)
    return Response(status_code=status.HTTP_200_OK if found else status.HTTP_404_NOT_FOUND)


@router.get("/{id}", response_model=CustomerAccountRead, summary="Get a Customer Account by id")
async def get(
    id: UUID, service: CustomerAccountService = Depends(get_service), _: Principal = Depends(require_read)
) -> CustomerAccountRead:
    return await service.get(id)


@router.post("", response_model=CustomerAccountRead, status_code=status.HTTP_201_CREATED, summary="Create a Customer Account")
async def create(
    payload: CustomerAccountCreate,
    request: Request,
    response: Response,
    service: CustomerAccountService = Depends(get_service),
    _: Principal = Depends(require_write),
) -> CustomerAccountRead:
    created = await service.create(payload)
    response.headers["Location"] = f"{request.url.path}/{created.id}"
    return created


@router.put("/{id}", response_model=CustomerAccountRead, summary="Update a Customer Account")
async def update(
    id: UUID,
    payload: CustomerAccountUpdate,
    service: CustomerAccountService = Depends(get_service),
    _: Principal = Depends(require_write),
) -> CustomerAccountRead:
    return await service.update(id, payload)


@router.patch("/{id}/status", response_model=CustomerAccountRead, summary="Change lifecycle status")
async def change_status(
    id: UUID,
    new_status: LifecycleStatus = Query(..., alias="status"),
    service: CustomerAccountService = Depends(get_service),
    _: Principal = Depends(require_write),
) -> CustomerAccountRead:
    return await service.change_status(id, new_status)


@router.delete("/{id}", status_code=status.HTTP_204_NO_CONTENT, summary="Delete a Customer Account")
async def delete(
    id: UUID, service: CustomerAccountService = Depends(get_service), _: Principal = Depends(require_write)
) -> Response:
    await service.delete(id)
    return Response(status_code=status.HTTP_204_NO_CONTENT)


@router.post("/bulk", response_model=CustomerAccountBulkResult, summary="Create many Customer Account records")
async def bulk_create(
    payloads: list[CustomerAccountCreate],
    service: CustomerAccountService = Depends(get_service),
    _: Principal = Depends(require_write),
) -> CustomerAccountBulkResult:
    return await service.bulk_create(payloads)


@router.post("/reconcile", response_model=list[ReconciliationItem], summary="Reconcile external Customer Account records")
async def reconcile(
    records: list[dict[str, object]],
    apply: bool = Query(False, description="Apply the plan instead of a dry run"),
    service: CustomerAccountService = Depends(get_service),
    _: Principal = Depends(require_write),
) -> list[ReconciliationItem]:
    return await service.reconcile(records, apply)


@router.get("/by-customer-id/{customer_id}", response_model=Page[CustomerAccountRead], summary="List Customer Account records by Customer Id")
async def list_by_customer_id(
    customer_id: UUID,
    page: int = Query(0, ge=0),
    size: int = Query(20, ge=1, le=200),
    service: CustomerAccountService = Depends(get_service),
) -> Page[CustomerAccountRead]:
    return await service.list_by_customer_id(customer_id, page, size)
