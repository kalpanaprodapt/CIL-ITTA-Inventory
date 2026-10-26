"""HTTP routes for Qos Policy."""
from __future__ import annotations

from uuid import UUID

from fastapi import APIRouter, Depends, Query, Request, Response, status
from fastapi.responses import PlainTextResponse

from itta_core.auth import Principal, require_read, require_write
from itta_core.dependencies import service_provider
from itta_core.diffing import ReconciliationItem
from itta_core.models import EntityMeta, LifecycleStatus
from itta_core.pagination import Page
from itta_api.domains.mpls.qos_policy.filters import QosPolicyFilter
from itta_api.domains.mpls.qos_policy.meta import META
from itta_api.domains.mpls.qos_policy.schemas import (
    QosPolicyBulkResult,
    QosPolicyCreate,
    QosPolicyRead,
    QosPolicySummary,
    QosPolicyUpdate,
)
from itta_api.domains.mpls.qos_policy.service import QosPolicyService

router = APIRouter(prefix="/api/v1/qos-policies", tags=["Qos Policy"])
get_service = service_provider("qos_policy")


@router.get("/meta", response_model=EntityMeta, summary="Field catalogue for Qos Policy")
async def meta() -> EntityMeta:
    return META


@router.get("", response_model=Page[QosPolicyRead], summary="Search Qos Policy records")
async def search(
    flt: QosPolicyFilter = Depends(),
    page: int = Query(0, ge=0),
    size: int = Query(20, ge=1, le=200),
    sort: str | None = Query(None, description="field,asc|desc"),
    service: QosPolicyService = Depends(get_service),
    _: Principal = Depends(require_read),
) -> Page[QosPolicyRead]:
    return await service.search(flt, page, size, sort)


@router.get("/summaries", response_model=list[QosPolicySummary], summary="Pick-list of Qos Policy records")
async def summaries(
    status_filter: LifecycleStatus | None = Query(None, alias="status"),
    service: QosPolicyService = Depends(get_service),
    _: Principal = Depends(require_read),
) -> list[QosPolicySummary]:
    return await service.summaries(status_filter)


@router.get("/stats/status", response_model=dict[str, int], summary="Counts per lifecycle status")
async def status_stats(
    service: QosPolicyService = Depends(get_service), _: Principal = Depends(require_read)
) -> dict[str, int]:
    return await service.stats()


@router.get("/export", response_class=PlainTextResponse, summary="Export Qos Policy records as CSV")
async def export(
    flt: QosPolicyFilter = Depends(),
    service: QosPolicyService = Depends(get_service),
    _: Principal = Depends(require_read),
) -> PlainTextResponse:
    body = await service.export_csv(flt)
    headers = {"Content-Disposition": 'attachment; filename="qos-policies.csv"'}
    return PlainTextResponse(body, media_type="text/csv", headers=headers)


@router.get("/by-code/{code}", response_model=QosPolicyRead, summary="Get a Qos Policy by business code")
async def get_by_code(
    code: str, service: QosPolicyService = Depends(get_service), _: Principal = Depends(require_read)
) -> QosPolicyRead:
    return await service.get_by_code(code)


@router.head("/by-code/{code}", summary="Check whether a Qos Policy code exists")
async def exists(
    code: str, service: QosPolicyService = Depends(get_service), _: Principal = Depends(require_read)
) -> Response:
    found = await service.exists(code)
    return Response(status_code=status.HTTP_200_OK if found else status.HTTP_404_NOT_FOUND)


@router.get("/{id}", response_model=QosPolicyRead, summary="Get a Qos Policy by id")
async def get(
    id: UUID, service: QosPolicyService = Depends(get_service), _: Principal = Depends(require_read)
) -> QosPolicyRead:
    return await service.get(id)


@router.post("", response_model=QosPolicyRead, status_code=status.HTTP_201_CREATED, summary="Create a Qos Policy")
async def create(
    payload: QosPolicyCreate,
    request: Request,
    response: Response,
    service: QosPolicyService = Depends(get_service),
    _: Principal = Depends(require_write),
) -> QosPolicyRead:
    created = await service.create(payload)
    response.headers["Location"] = f"{request.url.path}/{created.id}"
    return created


@router.put("/{id}", response_model=QosPolicyRead, summary="Update a Qos Policy")
async def update(
    id: UUID,
    payload: QosPolicyUpdate,
    service: QosPolicyService = Depends(get_service),
    _: Principal = Depends(require_write),
) -> QosPolicyRead:
    return await service.update(id, payload)


@router.patch("/{id}/status", response_model=QosPolicyRead, summary="Change lifecycle status")
async def change_status(
    id: UUID,
    new_status: LifecycleStatus = Query(..., alias="status"),
    service: QosPolicyService = Depends(get_service),
    _: Principal = Depends(require_write),
) -> QosPolicyRead:
    return await service.change_status(id, new_status)


@router.delete("/{id}", status_code=status.HTTP_204_NO_CONTENT, summary="Delete a Qos Policy")
async def delete(
    id: UUID, service: QosPolicyService = Depends(get_service), _: Principal = Depends(require_write)
) -> Response:
    await service.delete(id)
    return Response(status_code=status.HTTP_204_NO_CONTENT)


@router.post("/bulk", response_model=QosPolicyBulkResult, summary="Create many Qos Policy records")
async def bulk_create(
    payloads: list[QosPolicyCreate],
    service: QosPolicyService = Depends(get_service),
    _: Principal = Depends(require_write),
) -> QosPolicyBulkResult:
    return await service.bulk_create(payloads)


@router.post("/reconcile", response_model=list[ReconciliationItem], summary="Reconcile external Qos Policy records")
async def reconcile(
    records: list[dict[str, object]],
    apply: bool = Query(False, description="Apply the plan instead of a dry run"),
    service: QosPolicyService = Depends(get_service),
    _: Principal = Depends(require_write),
) -> list[ReconciliationItem]:
    return await service.reconcile(records, apply)

