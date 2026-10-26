"""HTTP routes for Threshold Policy."""
from __future__ import annotations

from uuid import UUID

from fastapi import APIRouter, Depends, Query, Request, Response, status
from fastapi.responses import PlainTextResponse

from itta_core.auth import Principal, require_read, require_write
from itta_core.dependencies import service_provider
from itta_core.diffing import ReconciliationItem
from itta_core.models import EntityMeta, LifecycleStatus
from itta_core.pagination import Page
from itta_api.domains.capacity.threshold_policy.filters import ThresholdPolicyFilter
from itta_api.domains.capacity.threshold_policy.meta import META
from itta_api.domains.capacity.threshold_policy.schemas import (
    ThresholdPolicyBulkResult,
    ThresholdPolicyCreate,
    ThresholdPolicyRead,
    ThresholdPolicySummary,
    ThresholdPolicyUpdate,
)
from itta_api.domains.capacity.threshold_policy.service import ThresholdPolicyService

router = APIRouter(prefix="/api/v1/threshold-policies", tags=["Threshold Policy"])
get_service = service_provider("threshold_policy")


@router.get("/meta", response_model=EntityMeta, summary="Field catalogue for Threshold Policy")
async def meta() -> EntityMeta:
    return META


@router.get("", response_model=Page[ThresholdPolicyRead], summary="Search Threshold Policy records")
async def search(
    flt: ThresholdPolicyFilter = Depends(),
    page: int = Query(0, ge=0),
    size: int = Query(20, ge=1, le=200),
    sort: str | None = Query(None, description="field,asc|desc"),
    service: ThresholdPolicyService = Depends(get_service),
    _: Principal = Depends(require_read),
) -> Page[ThresholdPolicyRead]:
    return await service.search(flt, page, size, sort)


@router.get("/summaries", response_model=list[ThresholdPolicySummary], summary="Pick-list of Threshold Policy records")
async def summaries(
    status_filter: LifecycleStatus | None = Query(None, alias="status"),
    service: ThresholdPolicyService = Depends(get_service),
    _: Principal = Depends(require_read),
) -> list[ThresholdPolicySummary]:
    return await service.summaries(status_filter)


@router.get("/stats/status", response_model=dict[str, int], summary="Counts per lifecycle status")
async def status_stats(
    service: ThresholdPolicyService = Depends(get_service), _: Principal = Depends(require_read)
) -> dict[str, int]:
    return await service.stats()


@router.get("/export", response_class=PlainTextResponse, summary="Export Threshold Policy records as CSV")
async def export(
    flt: ThresholdPolicyFilter = Depends(),
    service: ThresholdPolicyService = Depends(get_service),
    _: Principal = Depends(require_read),
) -> PlainTextResponse:
    body = await service.export_csv(flt)
    headers = {"Content-Disposition": 'attachment; filename="threshold-policies.csv"'}
    return PlainTextResponse(body, media_type="text/csv", headers=headers)


@router.get("/by-code/{code}", response_model=ThresholdPolicyRead, summary="Get a Threshold Policy by business code")
async def get_by_code(
    code: str, service: ThresholdPolicyService = Depends(get_service), _: Principal = Depends(require_read)
) -> ThresholdPolicyRead:
    return await service.get_by_code(code)


@router.head("/by-code/{code}", summary="Check whether a Threshold Policy code exists")
async def exists(
    code: str, service: ThresholdPolicyService = Depends(get_service), _: Principal = Depends(require_read)
) -> Response:
    found = await service.exists(code)
    return Response(status_code=status.HTTP_200_OK if found else status.HTTP_404_NOT_FOUND)


@router.get("/{id}", response_model=ThresholdPolicyRead, summary="Get a Threshold Policy by id")
async def get(
    id: UUID, service: ThresholdPolicyService = Depends(get_service), _: Principal = Depends(require_read)
) -> ThresholdPolicyRead:
    return await service.get(id)


@router.post("", response_model=ThresholdPolicyRead, status_code=status.HTTP_201_CREATED, summary="Create a Threshold Policy")
async def create(
    payload: ThresholdPolicyCreate,
    request: Request,
    response: Response,
    service: ThresholdPolicyService = Depends(get_service),
    _: Principal = Depends(require_write),
) -> ThresholdPolicyRead:
    created = await service.create(payload)
    response.headers["Location"] = f"{request.url.path}/{created.id}"
    return created


@router.put("/{id}", response_model=ThresholdPolicyRead, summary="Update a Threshold Policy")
async def update(
    id: UUID,
    payload: ThresholdPolicyUpdate,
    service: ThresholdPolicyService = Depends(get_service),
    _: Principal = Depends(require_write),
) -> ThresholdPolicyRead:
    return await service.update(id, payload)


@router.patch("/{id}/status", response_model=ThresholdPolicyRead, summary="Change lifecycle status")
async def change_status(
    id: UUID,
    new_status: LifecycleStatus = Query(..., alias="status"),
    service: ThresholdPolicyService = Depends(get_service),
    _: Principal = Depends(require_write),
) -> ThresholdPolicyRead:
    return await service.change_status(id, new_status)


@router.delete("/{id}", status_code=status.HTTP_204_NO_CONTENT, summary="Delete a Threshold Policy")
async def delete(
    id: UUID, service: ThresholdPolicyService = Depends(get_service), _: Principal = Depends(require_write)
) -> Response:
    await service.delete(id)
    return Response(status_code=status.HTTP_204_NO_CONTENT)


@router.post("/bulk", response_model=ThresholdPolicyBulkResult, summary="Create many Threshold Policy records")
async def bulk_create(
    payloads: list[ThresholdPolicyCreate],
    service: ThresholdPolicyService = Depends(get_service),
    _: Principal = Depends(require_write),
) -> ThresholdPolicyBulkResult:
    return await service.bulk_create(payloads)


@router.post("/reconcile", response_model=list[ReconciliationItem], summary="Reconcile external Threshold Policy records")
async def reconcile(
    records: list[dict[str, object]],
    apply: bool = Query(False, description="Apply the plan instead of a dry run"),
    service: ThresholdPolicyService = Depends(get_service),
    _: Principal = Depends(require_write),
) -> list[ReconciliationItem]:
    return await service.reconcile(records, apply)

