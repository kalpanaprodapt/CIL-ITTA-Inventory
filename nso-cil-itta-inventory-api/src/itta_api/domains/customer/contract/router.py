"""HTTP routes for Contract."""
from __future__ import annotations

from uuid import UUID

from fastapi import APIRouter, Depends, Query, Request, Response, status
from fastapi.responses import PlainTextResponse

from itta_core.auth import Principal, require_read, require_write
from itta_core.dependencies import service_provider
from itta_core.diffing import ReconciliationItem
from itta_core.models import EntityMeta, LifecycleStatus
from itta_core.pagination import Page
from itta_api.domains.customer.contract.filters import ContractFilter
from itta_api.domains.customer.contract.meta import META
from itta_api.domains.customer.contract.schemas import (
    ContractBulkResult,
    ContractCreate,
    ContractRead,
    ContractSummary,
    ContractUpdate,
)
from itta_api.domains.customer.contract.service import ContractService

router = APIRouter(prefix="/api/v1/contracts", tags=["Contract"])
get_service = service_provider("contract")


@router.get("/meta", response_model=EntityMeta, summary="Field catalogue for Contract")
async def meta() -> EntityMeta:
    return META


@router.get("", response_model=Page[ContractRead], summary="Search Contract records")
async def search(
    flt: ContractFilter = Depends(),
    page: int = Query(0, ge=0),
    size: int = Query(20, ge=1, le=200),
    sort: str | None = Query(None, description="field,asc|desc"),
    service: ContractService = Depends(get_service),
    _: Principal = Depends(require_read),
) -> Page[ContractRead]:
    return await service.search(flt, page, size, sort)


@router.get("/summaries", response_model=list[ContractSummary], summary="Pick-list of Contract records")
async def summaries(
    status_filter: LifecycleStatus | None = Query(None, alias="status"),
    service: ContractService = Depends(get_service),
    _: Principal = Depends(require_read),
) -> list[ContractSummary]:
    return await service.summaries(status_filter)


@router.get("/stats/status", response_model=dict[str, int], summary="Counts per lifecycle status")
async def status_stats(
    service: ContractService = Depends(get_service), _: Principal = Depends(require_read)
) -> dict[str, int]:
    return await service.stats()


@router.get("/export", response_class=PlainTextResponse, summary="Export Contract records as CSV")
async def export(
    flt: ContractFilter = Depends(),
    service: ContractService = Depends(get_service),
    _: Principal = Depends(require_read),
) -> PlainTextResponse:
    body = await service.export_csv(flt)
    headers = {"Content-Disposition": 'attachment; filename="contracts.csv"'}
    return PlainTextResponse(body, media_type="text/csv", headers=headers)


@router.get("/by-code/{code}", response_model=ContractRead, summary="Get a Contract by business code")
async def get_by_code(
    code: str, service: ContractService = Depends(get_service), _: Principal = Depends(require_read)
) -> ContractRead:
    return await service.get_by_code(code)


@router.head("/by-code/{code}", summary="Check whether a Contract code exists")
async def exists(
    code: str, service: ContractService = Depends(get_service), _: Principal = Depends(require_read)
) -> Response:
    found = await service.exists(code)
    return Response(status_code=status.HTTP_200_OK if found else status.HTTP_404_NOT_FOUND)


@router.get("/{id}", response_model=ContractRead, summary="Get a Contract by id")
async def get(
    id: UUID, service: ContractService = Depends(get_service), _: Principal = Depends(require_read)
) -> ContractRead:
    return await service.get(id)


@router.post("", response_model=ContractRead, status_code=status.HTTP_201_CREATED, summary="Create a Contract")
async def create(
    payload: ContractCreate,
    request: Request,
    response: Response,
    service: ContractService = Depends(get_service),
    _: Principal = Depends(require_write),
) -> ContractRead:
    created = await service.create(payload)
    response.headers["Location"] = f"{request.url.path}/{created.id}"
    return created


@router.put("/{id}", response_model=ContractRead, summary="Update a Contract")
async def update(
    id: UUID,
    payload: ContractUpdate,
    service: ContractService = Depends(get_service),
    _: Principal = Depends(require_write),
) -> ContractRead:
    return await service.update(id, payload)


@router.patch("/{id}/status", response_model=ContractRead, summary="Change lifecycle status")
async def change_status(
    id: UUID,
    new_status: LifecycleStatus = Query(..., alias="status"),
    service: ContractService = Depends(get_service),
    _: Principal = Depends(require_write),
) -> ContractRead:
    return await service.change_status(id, new_status)


@router.delete("/{id}", status_code=status.HTTP_204_NO_CONTENT, summary="Delete a Contract")
async def delete(
    id: UUID, service: ContractService = Depends(get_service), _: Principal = Depends(require_write)
) -> Response:
    await service.delete(id)
    return Response(status_code=status.HTTP_204_NO_CONTENT)


@router.post("/bulk", response_model=ContractBulkResult, summary="Create many Contract records")
async def bulk_create(
    payloads: list[ContractCreate],
    service: ContractService = Depends(get_service),
    _: Principal = Depends(require_write),
) -> ContractBulkResult:
    return await service.bulk_create(payloads)


@router.post("/reconcile", response_model=list[ReconciliationItem], summary="Reconcile external Contract records")
async def reconcile(
    records: list[dict[str, object]],
    apply: bool = Query(False, description="Apply the plan instead of a dry run"),
    service: ContractService = Depends(get_service),
    _: Principal = Depends(require_write),
) -> list[ReconciliationItem]:
    return await service.reconcile(records, apply)


@router.get("/by-customer-id/{customer_id}", response_model=Page[ContractRead], summary="List Contract records by Customer Id")
async def list_by_customer_id(
    customer_id: UUID,
    page: int = Query(0, ge=0),
    size: int = Query(20, ge=1, le=200),
    service: ContractService = Depends(get_service),
) -> Page[ContractRead]:
    return await service.list_by_customer_id(customer_id, page, size)
