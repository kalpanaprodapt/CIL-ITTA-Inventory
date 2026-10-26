"""HTTP routes for Compliance Check."""
from __future__ import annotations

from uuid import UUID

from fastapi import APIRouter, Depends, Query, Request, Response, status
from fastapi.responses import PlainTextResponse

from itta_core.auth import Principal, require_read, require_write
from itta_core.dependencies import service_provider
from itta_core.diffing import ReconciliationItem
from itta_core.models import EntityMeta, LifecycleStatus
from itta_core.pagination import Page
from itta_api.domains.compliance.compliance_check.filters import ComplianceCheckFilter
from itta_api.domains.compliance.compliance_check.meta import META
from itta_api.domains.compliance.compliance_check.schemas import (
    ComplianceCheckBulkResult,
    ComplianceCheckCreate,
    ComplianceCheckRead,
    ComplianceCheckSummary,
    ComplianceCheckUpdate,
)
from itta_api.domains.compliance.compliance_check.service import ComplianceCheckService

router = APIRouter(prefix="/api/v1/compliance-checks", tags=["Compliance Check"])
get_service = service_provider("compliance_check")


@router.get("/meta", response_model=EntityMeta, summary="Field catalogue for Compliance Check")
async def meta() -> EntityMeta:
    return META


@router.get("", response_model=Page[ComplianceCheckRead], summary="Search Compliance Check records")
async def search(
    flt: ComplianceCheckFilter = Depends(),
    page: int = Query(0, ge=0),
    size: int = Query(20, ge=1, le=200),
    sort: str | None = Query(None, description="field,asc|desc"),
    service: ComplianceCheckService = Depends(get_service),
    _: Principal = Depends(require_read),
) -> Page[ComplianceCheckRead]:
    return await service.search(flt, page, size, sort)


@router.get("/summaries", response_model=list[ComplianceCheckSummary], summary="Pick-list of Compliance Check records")
async def summaries(
    status_filter: LifecycleStatus | None = Query(None, alias="status"),
    service: ComplianceCheckService = Depends(get_service),
    _: Principal = Depends(require_read),
) -> list[ComplianceCheckSummary]:
    return await service.summaries(status_filter)


@router.get("/stats/status", response_model=dict[str, int], summary="Counts per lifecycle status")
async def status_stats(
    service: ComplianceCheckService = Depends(get_service), _: Principal = Depends(require_read)
) -> dict[str, int]:
    return await service.stats()


@router.get("/export", response_class=PlainTextResponse, summary="Export Compliance Check records as CSV")
async def export(
    flt: ComplianceCheckFilter = Depends(),
    service: ComplianceCheckService = Depends(get_service),
    _: Principal = Depends(require_read),
) -> PlainTextResponse:
    body = await service.export_csv(flt)
    headers = {"Content-Disposition": 'attachment; filename="compliance-checks.csv"'}
    return PlainTextResponse(body, media_type="text/csv", headers=headers)


@router.get("/by-code/{code}", response_model=ComplianceCheckRead, summary="Get a Compliance Check by business code")
async def get_by_code(
    code: str, service: ComplianceCheckService = Depends(get_service), _: Principal = Depends(require_read)
) -> ComplianceCheckRead:
    return await service.get_by_code(code)


@router.head("/by-code/{code}", summary="Check whether a Compliance Check code exists")
async def exists(
    code: str, service: ComplianceCheckService = Depends(get_service), _: Principal = Depends(require_read)
) -> Response:
    found = await service.exists(code)
    return Response(status_code=status.HTTP_200_OK if found else status.HTTP_404_NOT_FOUND)


@router.get("/{id}", response_model=ComplianceCheckRead, summary="Get a Compliance Check by id")
async def get(
    id: UUID, service: ComplianceCheckService = Depends(get_service), _: Principal = Depends(require_read)
) -> ComplianceCheckRead:
    return await service.get(id)


@router.post("", response_model=ComplianceCheckRead, status_code=status.HTTP_201_CREATED, summary="Create a Compliance Check")
async def create(
    payload: ComplianceCheckCreate,
    request: Request,
    response: Response,
    service: ComplianceCheckService = Depends(get_service),
    _: Principal = Depends(require_write),
) -> ComplianceCheckRead:
    created = await service.create(payload)
    response.headers["Location"] = f"{request.url.path}/{created.id}"
    return created


@router.put("/{id}", response_model=ComplianceCheckRead, summary="Update a Compliance Check")
async def update(
    id: UUID,
    payload: ComplianceCheckUpdate,
    service: ComplianceCheckService = Depends(get_service),
    _: Principal = Depends(require_write),
) -> ComplianceCheckRead:
    return await service.update(id, payload)


@router.patch("/{id}/status", response_model=ComplianceCheckRead, summary="Change lifecycle status")
async def change_status(
    id: UUID,
    new_status: LifecycleStatus = Query(..., alias="status"),
    service: ComplianceCheckService = Depends(get_service),
    _: Principal = Depends(require_write),
) -> ComplianceCheckRead:
    return await service.change_status(id, new_status)


@router.delete("/{id}", status_code=status.HTTP_204_NO_CONTENT, summary="Delete a Compliance Check")
async def delete(
    id: UUID, service: ComplianceCheckService = Depends(get_service), _: Principal = Depends(require_write)
) -> Response:
    await service.delete(id)
    return Response(status_code=status.HTTP_204_NO_CONTENT)


@router.post("/bulk", response_model=ComplianceCheckBulkResult, summary="Create many Compliance Check records")
async def bulk_create(
    payloads: list[ComplianceCheckCreate],
    service: ComplianceCheckService = Depends(get_service),
    _: Principal = Depends(require_write),
) -> ComplianceCheckBulkResult:
    return await service.bulk_create(payloads)


@router.post("/reconcile", response_model=list[ReconciliationItem], summary="Reconcile external Compliance Check records")
async def reconcile(
    records: list[dict[str, object]],
    apply: bool = Query(False, description="Apply the plan instead of a dry run"),
    service: ComplianceCheckService = Depends(get_service),
    _: Principal = Depends(require_write),
) -> list[ReconciliationItem]:
    return await service.reconcile(records, apply)


@router.get("/by-rule-id/{rule_id}", response_model=Page[ComplianceCheckRead], summary="List Compliance Check records by Rule Id")
async def list_by_rule_id(
    rule_id: UUID,
    page: int = Query(0, ge=0),
    size: int = Query(20, ge=1, le=200),
    service: ComplianceCheckService = Depends(get_service),
) -> Page[ComplianceCheckRead]:
    return await service.list_by_rule_id(rule_id, page, size)
