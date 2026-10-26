"""HTTP routes for Dns Record."""
from __future__ import annotations

from uuid import UUID

from fastapi import APIRouter, Depends, Query, Request, Response, status
from fastapi.responses import PlainTextResponse

from itta_core.auth import Principal, require_read, require_write
from itta_core.dependencies import service_provider
from itta_core.diffing import ReconciliationItem
from itta_core.models import EntityMeta, LifecycleStatus
from itta_core.pagination import Page
from itta_api.domains.ipam.dns_record.filters import DnsRecordFilter
from itta_api.domains.ipam.dns_record.meta import META
from itta_api.domains.ipam.dns_record.schemas import (
    DnsRecordBulkResult,
    DnsRecordCreate,
    DnsRecordRead,
    DnsRecordSummary,
    DnsRecordUpdate,
)
from itta_api.domains.ipam.dns_record.service import DnsRecordService

router = APIRouter(prefix="/api/v1/dns-records", tags=["Dns Record"])
get_service = service_provider("dns_record")


@router.get("/meta", response_model=EntityMeta, summary="Field catalogue for Dns Record")
async def meta() -> EntityMeta:
    return META


@router.get("", response_model=Page[DnsRecordRead], summary="Search Dns Record records")
async def search(
    flt: DnsRecordFilter = Depends(),
    page: int = Query(0, ge=0),
    size: int = Query(20, ge=1, le=200),
    sort: str | None = Query(None, description="field,asc|desc"),
    service: DnsRecordService = Depends(get_service),
    _: Principal = Depends(require_read),
) -> Page[DnsRecordRead]:
    return await service.search(flt, page, size, sort)


@router.get("/summaries", response_model=list[DnsRecordSummary], summary="Pick-list of Dns Record records")
async def summaries(
    status_filter: LifecycleStatus | None = Query(None, alias="status"),
    service: DnsRecordService = Depends(get_service),
    _: Principal = Depends(require_read),
) -> list[DnsRecordSummary]:
    return await service.summaries(status_filter)


@router.get("/stats/status", response_model=dict[str, int], summary="Counts per lifecycle status")
async def status_stats(
    service: DnsRecordService = Depends(get_service), _: Principal = Depends(require_read)
) -> dict[str, int]:
    return await service.stats()


@router.get("/export", response_class=PlainTextResponse, summary="Export Dns Record records as CSV")
async def export(
    flt: DnsRecordFilter = Depends(),
    service: DnsRecordService = Depends(get_service),
    _: Principal = Depends(require_read),
) -> PlainTextResponse:
    body = await service.export_csv(flt)
    headers = {"Content-Disposition": 'attachment; filename="dns-records.csv"'}
    return PlainTextResponse(body, media_type="text/csv", headers=headers)


@router.get("/by-code/{code}", response_model=DnsRecordRead, summary="Get a Dns Record by business code")
async def get_by_code(
    code: str, service: DnsRecordService = Depends(get_service), _: Principal = Depends(require_read)
) -> DnsRecordRead:
    return await service.get_by_code(code)


@router.head("/by-code/{code}", summary="Check whether a Dns Record code exists")
async def exists(
    code: str, service: DnsRecordService = Depends(get_service), _: Principal = Depends(require_read)
) -> Response:
    found = await service.exists(code)
    return Response(status_code=status.HTTP_200_OK if found else status.HTTP_404_NOT_FOUND)


@router.get("/{id}", response_model=DnsRecordRead, summary="Get a Dns Record by id")
async def get(
    id: UUID, service: DnsRecordService = Depends(get_service), _: Principal = Depends(require_read)
) -> DnsRecordRead:
    return await service.get(id)


@router.post("", response_model=DnsRecordRead, status_code=status.HTTP_201_CREATED, summary="Create a Dns Record")
async def create(
    payload: DnsRecordCreate,
    request: Request,
    response: Response,
    service: DnsRecordService = Depends(get_service),
    _: Principal = Depends(require_write),
) -> DnsRecordRead:
    created = await service.create(payload)
    response.headers["Location"] = f"{request.url.path}/{created.id}"
    return created


@router.put("/{id}", response_model=DnsRecordRead, summary="Update a Dns Record")
async def update(
    id: UUID,
    payload: DnsRecordUpdate,
    service: DnsRecordService = Depends(get_service),
    _: Principal = Depends(require_write),
) -> DnsRecordRead:
    return await service.update(id, payload)


@router.patch("/{id}/status", response_model=DnsRecordRead, summary="Change lifecycle status")
async def change_status(
    id: UUID,
    new_status: LifecycleStatus = Query(..., alias="status"),
    service: DnsRecordService = Depends(get_service),
    _: Principal = Depends(require_write),
) -> DnsRecordRead:
    return await service.change_status(id, new_status)


@router.delete("/{id}", status_code=status.HTTP_204_NO_CONTENT, summary="Delete a Dns Record")
async def delete(
    id: UUID, service: DnsRecordService = Depends(get_service), _: Principal = Depends(require_write)
) -> Response:
    await service.delete(id)
    return Response(status_code=status.HTTP_204_NO_CONTENT)


@router.post("/bulk", response_model=DnsRecordBulkResult, summary="Create many Dns Record records")
async def bulk_create(
    payloads: list[DnsRecordCreate],
    service: DnsRecordService = Depends(get_service),
    _: Principal = Depends(require_write),
) -> DnsRecordBulkResult:
    return await service.bulk_create(payloads)


@router.post("/reconcile", response_model=list[ReconciliationItem], summary="Reconcile external Dns Record records")
async def reconcile(
    records: list[dict[str, object]],
    apply: bool = Query(False, description="Apply the plan instead of a dry run"),
    service: DnsRecordService = Depends(get_service),
    _: Principal = Depends(require_write),
) -> list[ReconciliationItem]:
    return await service.reconcile(records, apply)

