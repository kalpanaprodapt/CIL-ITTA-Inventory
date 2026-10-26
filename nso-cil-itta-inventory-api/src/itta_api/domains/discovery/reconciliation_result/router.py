"""HTTP routes for Reconciliation Result."""
from __future__ import annotations

from uuid import UUID

from fastapi import APIRouter, Depends, Query, Request, Response, status
from fastapi.responses import PlainTextResponse

from itta_core.auth import Principal, require_read, require_write
from itta_core.dependencies import service_provider
from itta_core.diffing import ReconciliationItem
from itta_core.models import EntityMeta, LifecycleStatus
from itta_core.pagination import Page
from itta_api.domains.discovery.reconciliation_result.filters import ReconciliationResultFilter
from itta_api.domains.discovery.reconciliation_result.meta import META
from itta_api.domains.discovery.reconciliation_result.schemas import (
    ReconciliationResultBulkResult,
    ReconciliationResultCreate,
    ReconciliationResultRead,
    ReconciliationResultSummary,
    ReconciliationResultUpdate,
)
from itta_api.domains.discovery.reconciliation_result.service import ReconciliationResultService

router = APIRouter(prefix="/api/v1/reconciliation-results", tags=["Reconciliation Result"])
get_service = service_provider("reconciliation_result")


@router.get("/meta", response_model=EntityMeta, summary="Field catalogue for Reconciliation Result")
async def meta() -> EntityMeta:
    return META


@router.get("", response_model=Page[ReconciliationResultRead], summary="Search Reconciliation Result records")
async def search(
    flt: ReconciliationResultFilter = Depends(),
    page: int = Query(0, ge=0),
    size: int = Query(20, ge=1, le=200),
    sort: str | None = Query(None, description="field,asc|desc"),
    service: ReconciliationResultService = Depends(get_service),
    _: Principal = Depends(require_read),
) -> Page[ReconciliationResultRead]:
    return await service.search(flt, page, size, sort)


@router.get("/summaries", response_model=list[ReconciliationResultSummary], summary="Pick-list of Reconciliation Result records")
async def summaries(
    status_filter: LifecycleStatus | None = Query(None, alias="status"),
    service: ReconciliationResultService = Depends(get_service),
    _: Principal = Depends(require_read),
) -> list[ReconciliationResultSummary]:
    return await service.summaries(status_filter)


@router.get("/stats/status", response_model=dict[str, int], summary="Counts per lifecycle status")
async def status_stats(
    service: ReconciliationResultService = Depends(get_service), _: Principal = Depends(require_read)
) -> dict[str, int]:
    return await service.stats()


@router.get("/export", response_class=PlainTextResponse, summary="Export Reconciliation Result records as CSV")
async def export(
    flt: ReconciliationResultFilter = Depends(),
    service: ReconciliationResultService = Depends(get_service),
    _: Principal = Depends(require_read),
) -> PlainTextResponse:
    body = await service.export_csv(flt)
    headers = {"Content-Disposition": 'attachment; filename="reconciliation-results.csv"'}
    return PlainTextResponse(body, media_type="text/csv", headers=headers)


@router.get("/by-code/{code}", response_model=ReconciliationResultRead, summary="Get a Reconciliation Result by business code")
async def get_by_code(
    code: str, service: ReconciliationResultService = Depends(get_service), _: Principal = Depends(require_read)
) -> ReconciliationResultRead:
    return await service.get_by_code(code)


@router.head("/by-code/{code}", summary="Check whether a Reconciliation Result code exists")
async def exists(
    code: str, service: ReconciliationResultService = Depends(get_service), _: Principal = Depends(require_read)
) -> Response:
    found = await service.exists(code)
    return Response(status_code=status.HTTP_200_OK if found else status.HTTP_404_NOT_FOUND)


@router.get("/{id}", response_model=ReconciliationResultRead, summary="Get a Reconciliation Result by id")
async def get(
    id: UUID, service: ReconciliationResultService = Depends(get_service), _: Principal = Depends(require_read)
) -> ReconciliationResultRead:
    return await service.get(id)


@router.post("", response_model=ReconciliationResultRead, status_code=status.HTTP_201_CREATED, summary="Create a Reconciliation Result")
async def create(
    payload: ReconciliationResultCreate,
    request: Request,
    response: Response,
    service: ReconciliationResultService = Depends(get_service),
    _: Principal = Depends(require_write),
) -> ReconciliationResultRead:
    created = await service.create(payload)
    response.headers["Location"] = f"{request.url.path}/{created.id}"
    return created


@router.put("/{id}", response_model=ReconciliationResultRead, summary="Update a Reconciliation Result")
async def update(
    id: UUID,
    payload: ReconciliationResultUpdate,
    service: ReconciliationResultService = Depends(get_service),
    _: Principal = Depends(require_write),
) -> ReconciliationResultRead:
    return await service.update(id, payload)


@router.patch("/{id}/status", response_model=ReconciliationResultRead, summary="Change lifecycle status")
async def change_status(
    id: UUID,
    new_status: LifecycleStatus = Query(..., alias="status"),
    service: ReconciliationResultService = Depends(get_service),
    _: Principal = Depends(require_write),
) -> ReconciliationResultRead:
    return await service.change_status(id, new_status)


@router.delete("/{id}", status_code=status.HTTP_204_NO_CONTENT, summary="Delete a Reconciliation Result")
async def delete(
    id: UUID, service: ReconciliationResultService = Depends(get_service), _: Principal = Depends(require_write)
) -> Response:
    await service.delete(id)
    return Response(status_code=status.HTTP_204_NO_CONTENT)


@router.post("/bulk", response_model=ReconciliationResultBulkResult, summary="Create many Reconciliation Result records")
async def bulk_create(
    payloads: list[ReconciliationResultCreate],
    service: ReconciliationResultService = Depends(get_service),
    _: Principal = Depends(require_write),
) -> ReconciliationResultBulkResult:
    return await service.bulk_create(payloads)


@router.post("/reconcile", response_model=list[ReconciliationItem], summary="Reconcile external Reconciliation Result records")
async def reconcile(
    records: list[dict[str, object]],
    apply: bool = Query(False, description="Apply the plan instead of a dry run"),
    service: ReconciliationResultService = Depends(get_service),
    _: Principal = Depends(require_write),
) -> list[ReconciliationItem]:
    return await service.reconcile(records, apply)


@router.get("/by-job-id/{job_id}", response_model=Page[ReconciliationResultRead], summary="List Reconciliation Result records by Job Id")
async def list_by_job_id(
    job_id: UUID,
    page: int = Query(0, ge=0),
    size: int = Query(20, ge=1, le=200),
    service: ReconciliationResultService = Depends(get_service),
) -> Page[ReconciliationResultRead]:
    return await service.list_by_job_id(job_id, page, size)

@router.get("/by-rule-id/{rule_id}", response_model=Page[ReconciliationResultRead], summary="List Reconciliation Result records by Rule Id")
async def list_by_rule_id(
    rule_id: UUID,
    page: int = Query(0, ge=0),
    size: int = Query(20, ge=1, le=200),
    service: ReconciliationResultService = Depends(get_service),
) -> Page[ReconciliationResultRead]:
    return await service.list_by_rule_id(rule_id, page, size)
