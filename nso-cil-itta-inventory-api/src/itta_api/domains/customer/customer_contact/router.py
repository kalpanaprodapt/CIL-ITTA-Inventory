"""HTTP routes for Customer Contact."""
from __future__ import annotations

from uuid import UUID

from fastapi import APIRouter, Depends, Query, Request, Response, status
from fastapi.responses import PlainTextResponse

from itta_core.auth import Principal, require_read, require_write
from itta_core.dependencies import service_provider
from itta_core.diffing import ReconciliationItem
from itta_core.models import EntityMeta, LifecycleStatus
from itta_core.pagination import Page
from itta_api.domains.customer.customer_contact.filters import CustomerContactFilter
from itta_api.domains.customer.customer_contact.meta import META
from itta_api.domains.customer.customer_contact.schemas import (
    CustomerContactBulkResult,
    CustomerContactCreate,
    CustomerContactRead,
    CustomerContactSummary,
    CustomerContactUpdate,
)
from itta_api.domains.customer.customer_contact.service import CustomerContactService

router = APIRouter(prefix="/api/v1/customer-contacts", tags=["Customer Contact"])
get_service = service_provider("customer_contact")


@router.get("/meta", response_model=EntityMeta, summary="Field catalogue for Customer Contact")
async def meta() -> EntityMeta:
    return META


@router.get("", response_model=Page[CustomerContactRead], summary="Search Customer Contact records")
async def search(
    flt: CustomerContactFilter = Depends(),
    page: int = Query(0, ge=0),
    size: int = Query(20, ge=1, le=200),
    sort: str | None = Query(None, description="field,asc|desc"),
    service: CustomerContactService = Depends(get_service),
    _: Principal = Depends(require_read),
) -> Page[CustomerContactRead]:
    return await service.search(flt, page, size, sort)


@router.get("/summaries", response_model=list[CustomerContactSummary], summary="Pick-list of Customer Contact records")
async def summaries(
    status_filter: LifecycleStatus | None = Query(None, alias="status"),
    service: CustomerContactService = Depends(get_service),
    _: Principal = Depends(require_read),
) -> list[CustomerContactSummary]:
    return await service.summaries(status_filter)


@router.get("/stats/status", response_model=dict[str, int], summary="Counts per lifecycle status")
async def status_stats(
    service: CustomerContactService = Depends(get_service), _: Principal = Depends(require_read)
) -> dict[str, int]:
    return await service.stats()


@router.get("/export", response_class=PlainTextResponse, summary="Export Customer Contact records as CSV")
async def export(
    flt: CustomerContactFilter = Depends(),
    service: CustomerContactService = Depends(get_service),
    _: Principal = Depends(require_read),
) -> PlainTextResponse:
    body = await service.export_csv(flt)
    headers = {"Content-Disposition": 'attachment; filename="customer-contacts.csv"'}
    return PlainTextResponse(body, media_type="text/csv", headers=headers)


@router.get("/by-code/{code}", response_model=CustomerContactRead, summary="Get a Customer Contact by business code")
async def get_by_code(
    code: str, service: CustomerContactService = Depends(get_service), _: Principal = Depends(require_read)
) -> CustomerContactRead:
    return await service.get_by_code(code)


@router.head("/by-code/{code}", summary="Check whether a Customer Contact code exists")
async def exists(
    code: str, service: CustomerContactService = Depends(get_service), _: Principal = Depends(require_read)
) -> Response:
    found = await service.exists(code)
    return Response(status_code=status.HTTP_200_OK if found else status.HTTP_404_NOT_FOUND)


@router.get("/{id}", response_model=CustomerContactRead, summary="Get a Customer Contact by id")
async def get(
    id: UUID, service: CustomerContactService = Depends(get_service), _: Principal = Depends(require_read)
) -> CustomerContactRead:
    return await service.get(id)


@router.post("", response_model=CustomerContactRead, status_code=status.HTTP_201_CREATED, summary="Create a Customer Contact")
async def create(
    payload: CustomerContactCreate,
    request: Request,
    response: Response,
    service: CustomerContactService = Depends(get_service),
    _: Principal = Depends(require_write),
) -> CustomerContactRead:
    created = await service.create(payload)
    response.headers["Location"] = f"{request.url.path}/{created.id}"
    return created


@router.put("/{id}", response_model=CustomerContactRead, summary="Update a Customer Contact")
async def update(
    id: UUID,
    payload: CustomerContactUpdate,
    service: CustomerContactService = Depends(get_service),
    _: Principal = Depends(require_write),
) -> CustomerContactRead:
    return await service.update(id, payload)


@router.patch("/{id}/status", response_model=CustomerContactRead, summary="Change lifecycle status")
async def change_status(
    id: UUID,
    new_status: LifecycleStatus = Query(..., alias="status"),
    service: CustomerContactService = Depends(get_service),
    _: Principal = Depends(require_write),
) -> CustomerContactRead:
    return await service.change_status(id, new_status)


@router.delete("/{id}", status_code=status.HTTP_204_NO_CONTENT, summary="Delete a Customer Contact")
async def delete(
    id: UUID, service: CustomerContactService = Depends(get_service), _: Principal = Depends(require_write)
) -> Response:
    await service.delete(id)
    return Response(status_code=status.HTTP_204_NO_CONTENT)


@router.post("/bulk", response_model=CustomerContactBulkResult, summary="Create many Customer Contact records")
async def bulk_create(
    payloads: list[CustomerContactCreate],
    service: CustomerContactService = Depends(get_service),
    _: Principal = Depends(require_write),
) -> CustomerContactBulkResult:
    return await service.bulk_create(payloads)


@router.post("/reconcile", response_model=list[ReconciliationItem], summary="Reconcile external Customer Contact records")
async def reconcile(
    records: list[dict[str, object]],
    apply: bool = Query(False, description="Apply the plan instead of a dry run"),
    service: CustomerContactService = Depends(get_service),
    _: Principal = Depends(require_write),
) -> list[ReconciliationItem]:
    return await service.reconcile(records, apply)


@router.get("/by-customer-id/{customer_id}", response_model=Page[CustomerContactRead], summary="List Customer Contact records by Customer Id")
async def list_by_customer_id(
    customer_id: UUID,
    page: int = Query(0, ge=0),
    size: int = Query(20, ge=1, le=200),
    service: CustomerContactService = Depends(get_service),
) -> Page[CustomerContactRead]:
    return await service.list_by_customer_id(customer_id, page, size)
