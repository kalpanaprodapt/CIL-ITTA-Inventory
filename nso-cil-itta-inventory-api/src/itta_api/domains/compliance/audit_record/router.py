"""HTTP routes for Audit Record."""
from __future__ import annotations

from uuid import UUID

from fastapi import APIRouter, Depends, Query, Request, Response, status
from fastapi.responses import PlainTextResponse

from itta_core.auth import Principal, require_read, require_write
from itta_core.dependencies import service_provider
from itta_core.diffing import ReconciliationItem
from itta_core.models import EntityMeta, LifecycleStatus
from itta_core.pagination import Page
from itta_api.domains.compliance.audit_record.filters import AuditRecordFilter
from itta_api.domains.compliance.audit_record.meta import META
from itta_api.domains.compliance.audit_record.schemas import (
    AuditRecordBulkResult,
    AuditRecordCreate,
    AuditRecordRead,
    AuditRecordSummary,
    AuditRecordUpdate,
)
from itta_api.domains.compliance.audit_record.service import AuditRecordService

router = APIRouter(prefix="/api/v1/audit-records", tags=["Audit Record"])
get_service = service_provider("audit_record")


@router.get("/meta", response_model=EntityMeta, summary="Field catalogue for Audit Record")
async def meta() -> EntityMeta:
    return META


@router.get("", response_model=Page[AuditRecordRead], summary="Search Audit Record records")
async def search(
    flt: AuditRecordFilter = Depends(),
    page: int = Query(0, ge=0),
    size: int = Query(20, ge=1, le=200),
    sort: str | None = Query(None, description="field,asc|desc"),
    service: AuditRecordService = Depends(get_service),
    _: Principal = Depends(require_read),
) -> Page[AuditRecordRead]:
    return await service.search(flt, page, size, sort)


@router.get("/summaries", response_model=list[AuditRecordSummary], summary="Pick-list of Audit Record records")
async def summaries(
    status_filter: LifecycleStatus | None = Query(None, alias="status"),
    service: AuditRecordService = Depends(get_service),
    _: Principal = Depends(require_read),
) -> list[AuditRecordSummary]:
    return await service.summaries(status_filter)


@router.get("/stats/status", response_model=dict[str, int], summary="Counts per lifecycle status")
async def status_stats(
    service: AuditRecordService = Depends(get_service), _: Principal = Depends(require_read)
) -> dict[str, int]:
    return await service.stats()


@router.get("/export", response_class=PlainTextResponse, summary="Export Audit Record records as CSV")
async def export(
    flt: AuditRecordFilter = Depends(),
    service: AuditRecordService = Depends(get_service),
    _: Principal = Depends(require_read),
) -> PlainTextResponse:
    body = await service.export_csv(flt)
    headers = {"Content-Disposition": 'attachment; filename="audit-records.csv"'}
    return PlainTextResponse(body, media_type="text/csv", headers=headers)


@router.get("/by-code/{code}", response_model=AuditRecordRead, summary="Get a Audit Record by business code")
async def get_by_code(
    code: str, service: AuditRecordService = Depends(get_service), _: Principal = Depends(require_read)
) -> AuditRecordRead:
    return await service.get_by_code(code)


@router.head("/by-code/{code}", summary="Check whether a Audit Record code exists")
async def exists(
    code: str, service: AuditRecordService = Depends(get_service), _: Principal = Depends(require_read)
) -> Response:
    found = await service.exists(code)
    return Response(status_code=status.HTTP_200_OK if found else status.HTTP_404_NOT_FOUND)


@router.get("/{id}", response_model=AuditRecordRead, summary="Get a Audit Record by id")
async def get(
    id: UUID, service: AuditRecordService = Depends(get_service), _: Principal = Depends(require_read)
) -> AuditRecordRead:
    return await service.get(id)


@router.post("", response_model=AuditRecordRead, status_code=status.HTTP_201_CREATED, summary="Create a Audit Record")
async def create(
    payload: AuditRecordCreate,
    request: Request,
    response: Response,
    service: AuditRecordService = Depends(get_service),
    _: Principal = Depends(require_write),
) -> AuditRecordRead:
    created = await service.create(payload)
    response.headers["Location"] = f"{request.url.path}/{created.id}"
    return created


@router.put("/{id}", response_model=AuditRecordRead, summary="Update a Audit Record")
async def update(
    id: UUID,
    payload: AuditRecordUpdate,
    service: AuditRecordService = Depends(get_service),
    _: Principal = Depends(require_write),
) -> AuditRecordRead:
    return await service.update(id, payload)


@router.patch("/{id}/status", response_model=AuditRecordRead, summary="Change lifecycle status")
async def change_status(
    id: UUID,
    new_status: LifecycleStatus = Query(..., alias="status"),
    service: AuditRecordService = Depends(get_service),
    _: Principal = Depends(require_write),
) -> AuditRecordRead:
    return await service.change_status(id, new_status)


@router.delete("/{id}", status_code=status.HTTP_204_NO_CONTENT, summary="Delete a Audit Record")
async def delete(
    id: UUID, service: AuditRecordService = Depends(get_service), _: Principal = Depends(require_write)
) -> Response:
    await service.delete(id)
    return Response(status_code=status.HTTP_204_NO_CONTENT)


@router.post("/bulk", response_model=AuditRecordBulkResult, summary="Create many Audit Record records")
async def bulk_create(
    payloads: list[AuditRecordCreate],
    service: AuditRecordService = Depends(get_service),
    _: Principal = Depends(require_write),
) -> AuditRecordBulkResult:
    return await service.bulk_create(payloads)


@router.post("/reconcile", response_model=list[ReconciliationItem], summary="Reconcile external Audit Record records")
async def reconcile(
    records: list[dict[str, object]],
    apply: bool = Query(False, description="Apply the plan instead of a dry run"),
    service: AuditRecordService = Depends(get_service),
    _: Principal = Depends(require_write),
) -> list[ReconciliationItem]:
    return await service.reconcile(records, apply)

