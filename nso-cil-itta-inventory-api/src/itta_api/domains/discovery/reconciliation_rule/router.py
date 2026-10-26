"""HTTP routes for Reconciliation Rule."""
from __future__ import annotations

from uuid import UUID

from fastapi import APIRouter, Depends, Query, Request, Response, status
from fastapi.responses import PlainTextResponse

from itta_core.auth import Principal, require_read, require_write
from itta_core.dependencies import service_provider
from itta_core.diffing import ReconciliationItem
from itta_core.models import EntityMeta, LifecycleStatus
from itta_core.pagination import Page
from itta_api.domains.discovery.reconciliation_rule.filters import ReconciliationRuleFilter
from itta_api.domains.discovery.reconciliation_rule.meta import META
from itta_api.domains.discovery.reconciliation_rule.schemas import (
    ReconciliationRuleBulkResult,
    ReconciliationRuleCreate,
    ReconciliationRuleRead,
    ReconciliationRuleSummary,
    ReconciliationRuleUpdate,
)
from itta_api.domains.discovery.reconciliation_rule.service import ReconciliationRuleService

router = APIRouter(prefix="/api/v1/reconciliation-rules", tags=["Reconciliation Rule"])
get_service = service_provider("reconciliation_rule")


@router.get("/meta", response_model=EntityMeta, summary="Field catalogue for Reconciliation Rule")
async def meta() -> EntityMeta:
    return META


@router.get("", response_model=Page[ReconciliationRuleRead], summary="Search Reconciliation Rule records")
async def search(
    flt: ReconciliationRuleFilter = Depends(),
    page: int = Query(0, ge=0),
    size: int = Query(20, ge=1, le=200),
    sort: str | None = Query(None, description="field,asc|desc"),
    service: ReconciliationRuleService = Depends(get_service),
    _: Principal = Depends(require_read),
) -> Page[ReconciliationRuleRead]:
    return await service.search(flt, page, size, sort)


@router.get("/summaries", response_model=list[ReconciliationRuleSummary], summary="Pick-list of Reconciliation Rule records")
async def summaries(
    status_filter: LifecycleStatus | None = Query(None, alias="status"),
    service: ReconciliationRuleService = Depends(get_service),
    _: Principal = Depends(require_read),
) -> list[ReconciliationRuleSummary]:
    return await service.summaries(status_filter)


@router.get("/stats/status", response_model=dict[str, int], summary="Counts per lifecycle status")
async def status_stats(
    service: ReconciliationRuleService = Depends(get_service), _: Principal = Depends(require_read)
) -> dict[str, int]:
    return await service.stats()


@router.get("/export", response_class=PlainTextResponse, summary="Export Reconciliation Rule records as CSV")
async def export(
    flt: ReconciliationRuleFilter = Depends(),
    service: ReconciliationRuleService = Depends(get_service),
    _: Principal = Depends(require_read),
) -> PlainTextResponse:
    body = await service.export_csv(flt)
    headers = {"Content-Disposition": 'attachment; filename="reconciliation-rules.csv"'}
    return PlainTextResponse(body, media_type="text/csv", headers=headers)


@router.get("/by-code/{code}", response_model=ReconciliationRuleRead, summary="Get a Reconciliation Rule by business code")
async def get_by_code(
    code: str, service: ReconciliationRuleService = Depends(get_service), _: Principal = Depends(require_read)
) -> ReconciliationRuleRead:
    return await service.get_by_code(code)


@router.head("/by-code/{code}", summary="Check whether a Reconciliation Rule code exists")
async def exists(
    code: str, service: ReconciliationRuleService = Depends(get_service), _: Principal = Depends(require_read)
) -> Response:
    found = await service.exists(code)
    return Response(status_code=status.HTTP_200_OK if found else status.HTTP_404_NOT_FOUND)


@router.get("/{id}", response_model=ReconciliationRuleRead, summary="Get a Reconciliation Rule by id")
async def get(
    id: UUID, service: ReconciliationRuleService = Depends(get_service), _: Principal = Depends(require_read)
) -> ReconciliationRuleRead:
    return await service.get(id)


@router.post("", response_model=ReconciliationRuleRead, status_code=status.HTTP_201_CREATED, summary="Create a Reconciliation Rule")
async def create(
    payload: ReconciliationRuleCreate,
    request: Request,
    response: Response,
    service: ReconciliationRuleService = Depends(get_service),
    _: Principal = Depends(require_write),
) -> ReconciliationRuleRead:
    created = await service.create(payload)
    response.headers["Location"] = f"{request.url.path}/{created.id}"
    return created


@router.put("/{id}", response_model=ReconciliationRuleRead, summary="Update a Reconciliation Rule")
async def update(
    id: UUID,
    payload: ReconciliationRuleUpdate,
    service: ReconciliationRuleService = Depends(get_service),
    _: Principal = Depends(require_write),
) -> ReconciliationRuleRead:
    return await service.update(id, payload)


@router.patch("/{id}/status", response_model=ReconciliationRuleRead, summary="Change lifecycle status")
async def change_status(
    id: UUID,
    new_status: LifecycleStatus = Query(..., alias="status"),
    service: ReconciliationRuleService = Depends(get_service),
    _: Principal = Depends(require_write),
) -> ReconciliationRuleRead:
    return await service.change_status(id, new_status)


@router.delete("/{id}", status_code=status.HTTP_204_NO_CONTENT, summary="Delete a Reconciliation Rule")
async def delete(
    id: UUID, service: ReconciliationRuleService = Depends(get_service), _: Principal = Depends(require_write)
) -> Response:
    await service.delete(id)
    return Response(status_code=status.HTTP_204_NO_CONTENT)


@router.post("/bulk", response_model=ReconciliationRuleBulkResult, summary="Create many Reconciliation Rule records")
async def bulk_create(
    payloads: list[ReconciliationRuleCreate],
    service: ReconciliationRuleService = Depends(get_service),
    _: Principal = Depends(require_write),
) -> ReconciliationRuleBulkResult:
    return await service.bulk_create(payloads)


@router.post("/reconcile", response_model=list[ReconciliationItem], summary="Reconcile external Reconciliation Rule records")
async def reconcile(
    records: list[dict[str, object]],
    apply: bool = Query(False, description="Apply the plan instead of a dry run"),
    service: ReconciliationRuleService = Depends(get_service),
    _: Principal = Depends(require_write),
) -> list[ReconciliationItem]:
    return await service.reconcile(records, apply)

