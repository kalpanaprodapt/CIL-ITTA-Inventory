"""HTTP routes for Compliance Exception."""
from __future__ import annotations

from uuid import UUID

from fastapi import APIRouter, Depends, Query, Request, Response, status
from fastapi.responses import PlainTextResponse

from itta_core.auth import Principal, require_read, require_write
from itta_core.dependencies import service_provider
from itta_core.diffing import ReconciliationItem
from itta_core.models import EntityMeta, LifecycleStatus
from itta_core.pagination import Page
from itta_api.domains.compliance.compliance_exception.filters import ComplianceExceptionFilter
from itta_api.domains.compliance.compliance_exception.meta import META
from itta_api.domains.compliance.compliance_exception.schemas import (
    ComplianceExceptionBulkResult,
    ComplianceExceptionCreate,
    ComplianceExceptionRead,
    ComplianceExceptionSummary,
    ComplianceExceptionUpdate,
)
from itta_api.domains.compliance.compliance_exception.service import ComplianceExceptionService

router = APIRouter(prefix="/api/v1/compliance-exceptions", tags=["Compliance Exception"])
get_service = service_provider("compliance_exception")


@router.get("/meta", response_model=EntityMeta, summary="Field catalogue for Compliance Exception")
async def meta() -> EntityMeta:
    return META


@router.get("", response_model=Page[ComplianceExceptionRead], summary="Search Compliance Exception records")
async def search(
    flt: ComplianceExceptionFilter = Depends(),
    page: int = Query(0, ge=0),
    size: int = Query(20, ge=1, le=200),
    sort: str | None = Query(None, description="field,asc|desc"),
    service: ComplianceExceptionService = Depends(get_service),
    _: Principal = Depends(require_read),
) -> Page[ComplianceExceptionRead]:
    return await service.search(flt, page, size, sort)


@router.get("/summaries", response_model=list[ComplianceExceptionSummary], summary="Pick-list of Compliance Exception records")
async def summaries(
    status_filter: LifecycleStatus | None = Query(None, alias="status"),
    service: ComplianceExceptionService = Depends(get_service),
    _: Principal = Depends(require_read),
) -> list[ComplianceExceptionSummary]:
    return await service.summaries(status_filter)


@router.get("/stats/status", response_model=dict[str, int], summary="Counts per lifecycle status")
async def status_stats(
    service: ComplianceExceptionService = Depends(get_service), _: Principal = Depends(require_read)
) -> dict[str, int]:
    return await service.stats()


@router.get("/export", response_class=PlainTextResponse, summary="Export Compliance Exception records as CSV")
async def export(
    flt: ComplianceExceptionFilter = Depends(),
    service: ComplianceExceptionService = Depends(get_service),
    _: Principal = Depends(require_read),
) -> PlainTextResponse:
    body = await service.export_csv(flt)
    headers = {"Content-Disposition": 'attachment; filename="compliance-exceptions.csv"'}
    return PlainTextResponse(body, media_type="text/csv", headers=headers)


@router.get("/by-code/{code}", response_model=ComplianceExceptionRead, summary="Get a Compliance Exception by business code")
async def get_by_code(
    code: str, service: ComplianceExceptionService = Depends(get_service), _: Principal = Depends(require_read)
) -> ComplianceExceptionRead:
    return await service.get_by_code(code)


@router.head("/by-code/{code}", summary="Check whether a Compliance Exception code exists")
async def exists(
    code: str, service: ComplianceExceptionService = Depends(get_service), _: Principal = Depends(require_read)
) -> Response:
    found = await service.exists(code)
    return Response(status_code=status.HTTP_200_OK if found else status.HTTP_404_NOT_FOUND)


@router.get("/{id}", response_model=ComplianceExceptionRead, summary="Get a Compliance Exception by id")
async def get(
    id: UUID, service: ComplianceExceptionService = Depends(get_service), _: Principal = Depends(require_read)
) -> ComplianceExceptionRead:
    return await service.get(id)


@router.post("", response_model=ComplianceExceptionRead, status_code=status.HTTP_201_CREATED, summary="Create a Compliance Exception")
async def create(
    payload: ComplianceExceptionCreate,
    request: Request,
    response: Response,
    service: ComplianceExceptionService = Depends(get_service),
    _: Principal = Depends(require_write),
) -> ComplianceExceptionRead:
    created = await service.create(payload)
    response.headers["Location"] = f"{request.url.path}/{created.id}"
    return created


@router.put("/{id}", response_model=ComplianceExceptionRead, summary="Update a Compliance Exception")
async def update(
    id: UUID,
    payload: ComplianceExceptionUpdate,
    service: ComplianceExceptionService = Depends(get_service),
    _: Principal = Depends(require_write),
) -> ComplianceExceptionRead:
    return await service.update(id, payload)


@router.patch("/{id}/status", response_model=ComplianceExceptionRead, summary="Change lifecycle status")
async def change_status(
    id: UUID,
    new_status: LifecycleStatus = Query(..., alias="status"),
    service: ComplianceExceptionService = Depends(get_service),
    _: Principal = Depends(require_write),
) -> ComplianceExceptionRead:
    return await service.change_status(id, new_status)


@router.delete("/{id}", status_code=status.HTTP_204_NO_CONTENT, summary="Delete a Compliance Exception")
async def delete(
    id: UUID, service: ComplianceExceptionService = Depends(get_service), _: Principal = Depends(require_write)
) -> Response:
    await service.delete(id)
    return Response(status_code=status.HTTP_204_NO_CONTENT)


@router.post("/bulk", response_model=ComplianceExceptionBulkResult, summary="Create many Compliance Exception records")
async def bulk_create(
    payloads: list[ComplianceExceptionCreate],
    service: ComplianceExceptionService = Depends(get_service),
    _: Principal = Depends(require_write),
) -> ComplianceExceptionBulkResult:
    return await service.bulk_create(payloads)


@router.post("/reconcile", response_model=list[ReconciliationItem], summary="Reconcile external Compliance Exception records")
async def reconcile(
    records: list[dict[str, object]],
    apply: bool = Query(False, description="Apply the plan instead of a dry run"),
    service: ComplianceExceptionService = Depends(get_service),
    _: Principal = Depends(require_write),
) -> list[ReconciliationItem]:
    return await service.reconcile(records, apply)


@router.get("/by-rule-id/{rule_id}", response_model=Page[ComplianceExceptionRead], summary="List Compliance Exception records by Rule Id")
async def list_by_rule_id(
    rule_id: UUID,
    page: int = Query(0, ge=0),
    size: int = Query(20, ge=1, le=200),
    service: ComplianceExceptionService = Depends(get_service),
) -> Page[ComplianceExceptionRead]:
    return await service.list_by_rule_id(rule_id, page, size)
