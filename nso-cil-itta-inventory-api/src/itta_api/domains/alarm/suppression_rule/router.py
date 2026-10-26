"""HTTP routes for Suppression Rule."""
from __future__ import annotations

from uuid import UUID

from fastapi import APIRouter, Depends, Query, Request, Response, status
from fastapi.responses import PlainTextResponse

from itta_core.auth import Principal, require_read, require_write
from itta_core.dependencies import service_provider
from itta_core.diffing import ReconciliationItem
from itta_core.models import EntityMeta, LifecycleStatus
from itta_core.pagination import Page
from itta_api.domains.alarm.suppression_rule.filters import SuppressionRuleFilter
from itta_api.domains.alarm.suppression_rule.meta import META
from itta_api.domains.alarm.suppression_rule.schemas import (
    SuppressionRuleBulkResult,
    SuppressionRuleCreate,
    SuppressionRuleRead,
    SuppressionRuleSummary,
    SuppressionRuleUpdate,
)
from itta_api.domains.alarm.suppression_rule.service import SuppressionRuleService

router = APIRouter(prefix="/api/v1/suppression-rules", tags=["Suppression Rule"])
get_service = service_provider("suppression_rule")


@router.get("/meta", response_model=EntityMeta, summary="Field catalogue for Suppression Rule")
async def meta() -> EntityMeta:
    return META


@router.get("", response_model=Page[SuppressionRuleRead], summary="Search Suppression Rule records")
async def search(
    flt: SuppressionRuleFilter = Depends(),
    page: int = Query(0, ge=0),
    size: int = Query(20, ge=1, le=200),
    sort: str | None = Query(None, description="field,asc|desc"),
    service: SuppressionRuleService = Depends(get_service),
    _: Principal = Depends(require_read),
) -> Page[SuppressionRuleRead]:
    return await service.search(flt, page, size, sort)


@router.get("/summaries", response_model=list[SuppressionRuleSummary], summary="Pick-list of Suppression Rule records")
async def summaries(
    status_filter: LifecycleStatus | None = Query(None, alias="status"),
    service: SuppressionRuleService = Depends(get_service),
    _: Principal = Depends(require_read),
) -> list[SuppressionRuleSummary]:
    return await service.summaries(status_filter)


@router.get("/stats/status", response_model=dict[str, int], summary="Counts per lifecycle status")
async def status_stats(
    service: SuppressionRuleService = Depends(get_service), _: Principal = Depends(require_read)
) -> dict[str, int]:
    return await service.stats()


@router.get("/export", response_class=PlainTextResponse, summary="Export Suppression Rule records as CSV")
async def export(
    flt: SuppressionRuleFilter = Depends(),
    service: SuppressionRuleService = Depends(get_service),
    _: Principal = Depends(require_read),
) -> PlainTextResponse:
    body = await service.export_csv(flt)
    headers = {"Content-Disposition": 'attachment; filename="suppression-rules.csv"'}
    return PlainTextResponse(body, media_type="text/csv", headers=headers)


@router.get("/by-code/{code}", response_model=SuppressionRuleRead, summary="Get a Suppression Rule by business code")
async def get_by_code(
    code: str, service: SuppressionRuleService = Depends(get_service), _: Principal = Depends(require_read)
) -> SuppressionRuleRead:
    return await service.get_by_code(code)


@router.head("/by-code/{code}", summary="Check whether a Suppression Rule code exists")
async def exists(
    code: str, service: SuppressionRuleService = Depends(get_service), _: Principal = Depends(require_read)
) -> Response:
    found = await service.exists(code)
    return Response(status_code=status.HTTP_200_OK if found else status.HTTP_404_NOT_FOUND)


@router.get("/{id}", response_model=SuppressionRuleRead, summary="Get a Suppression Rule by id")
async def get(
    id: UUID, service: SuppressionRuleService = Depends(get_service), _: Principal = Depends(require_read)
) -> SuppressionRuleRead:
    return await service.get(id)


@router.post("", response_model=SuppressionRuleRead, status_code=status.HTTP_201_CREATED, summary="Create a Suppression Rule")
async def create(
    payload: SuppressionRuleCreate,
    request: Request,
    response: Response,
    service: SuppressionRuleService = Depends(get_service),
    _: Principal = Depends(require_write),
) -> SuppressionRuleRead:
    created = await service.create(payload)
    response.headers["Location"] = f"{request.url.path}/{created.id}"
    return created


@router.put("/{id}", response_model=SuppressionRuleRead, summary="Update a Suppression Rule")
async def update(
    id: UUID,
    payload: SuppressionRuleUpdate,
    service: SuppressionRuleService = Depends(get_service),
    _: Principal = Depends(require_write),
) -> SuppressionRuleRead:
    return await service.update(id, payload)


@router.patch("/{id}/status", response_model=SuppressionRuleRead, summary="Change lifecycle status")
async def change_status(
    id: UUID,
    new_status: LifecycleStatus = Query(..., alias="status"),
    service: SuppressionRuleService = Depends(get_service),
    _: Principal = Depends(require_write),
) -> SuppressionRuleRead:
    return await service.change_status(id, new_status)


@router.delete("/{id}", status_code=status.HTTP_204_NO_CONTENT, summary="Delete a Suppression Rule")
async def delete(
    id: UUID, service: SuppressionRuleService = Depends(get_service), _: Principal = Depends(require_write)
) -> Response:
    await service.delete(id)
    return Response(status_code=status.HTTP_204_NO_CONTENT)


@router.post("/bulk", response_model=SuppressionRuleBulkResult, summary="Create many Suppression Rule records")
async def bulk_create(
    payloads: list[SuppressionRuleCreate],
    service: SuppressionRuleService = Depends(get_service),
    _: Principal = Depends(require_write),
) -> SuppressionRuleBulkResult:
    return await service.bulk_create(payloads)


@router.post("/reconcile", response_model=list[ReconciliationItem], summary="Reconcile external Suppression Rule records")
async def reconcile(
    records: list[dict[str, object]],
    apply: bool = Query(False, description="Apply the plan instead of a dry run"),
    service: SuppressionRuleService = Depends(get_service),
    _: Principal = Depends(require_write),
) -> list[ReconciliationItem]:
    return await service.reconcile(records, apply)

