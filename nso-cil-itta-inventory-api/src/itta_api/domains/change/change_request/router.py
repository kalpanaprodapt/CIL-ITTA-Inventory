"""HTTP routes for Change Request."""
from __future__ import annotations

from uuid import UUID

from fastapi import APIRouter, Depends, Query, Request, Response, status
from fastapi.responses import PlainTextResponse

from itta_core.auth import Principal, require_read, require_write
from itta_core.dependencies import service_provider
from itta_core.diffing import ReconciliationItem
from itta_core.models import EntityMeta, LifecycleStatus
from itta_core.pagination import Page
from itta_api.domains.change.change_request.filters import ChangeRequestFilter
from itta_api.domains.change.change_request.meta import META
from itta_api.domains.change.change_request.schemas import (
    ChangeRequestBulkResult,
    ChangeRequestCreate,
    ChangeRequestRead,
    ChangeRequestSummary,
    ChangeRequestUpdate,
)
from itta_api.domains.change.change_request.service import ChangeRequestService

router = APIRouter(prefix="/api/v1/change-requests", tags=["Change Request"])
get_service = service_provider("change_request")


@router.get("/meta", response_model=EntityMeta, summary="Field catalogue for Change Request")
async def meta() -> EntityMeta:
    return META


@router.get("", response_model=Page[ChangeRequestRead], summary="Search Change Request records")
async def search(
    flt: ChangeRequestFilter = Depends(),
    page: int = Query(0, ge=0),
    size: int = Query(20, ge=1, le=200),
    sort: str | None = Query(None, description="field,asc|desc"),
    service: ChangeRequestService = Depends(get_service),
    _: Principal = Depends(require_read),
) -> Page[ChangeRequestRead]:
    return await service.search(flt, page, size, sort)


@router.get("/summaries", response_model=list[ChangeRequestSummary], summary="Pick-list of Change Request records")
async def summaries(
    status_filter: LifecycleStatus | None = Query(None, alias="status"),
    service: ChangeRequestService = Depends(get_service),
    _: Principal = Depends(require_read),
) -> list[ChangeRequestSummary]:
    return await service.summaries(status_filter)


@router.get("/stats/status", response_model=dict[str, int], summary="Counts per lifecycle status")
async def status_stats(
    service: ChangeRequestService = Depends(get_service), _: Principal = Depends(require_read)
) -> dict[str, int]:
    return await service.stats()


@router.get("/export", response_class=PlainTextResponse, summary="Export Change Request records as CSV")
async def export(
    flt: ChangeRequestFilter = Depends(),
    service: ChangeRequestService = Depends(get_service),
    _: Principal = Depends(require_read),
) -> PlainTextResponse:
    body = await service.export_csv(flt)
    headers = {"Content-Disposition": 'attachment; filename="change-requests.csv"'}
    return PlainTextResponse(body, media_type="text/csv", headers=headers)


@router.get("/by-code/{code}", response_model=ChangeRequestRead, summary="Get a Change Request by business code")
async def get_by_code(
    code: str, service: ChangeRequestService = Depends(get_service), _: Principal = Depends(require_read)
) -> ChangeRequestRead:
    return await service.get_by_code(code)


@router.head("/by-code/{code}", summary="Check whether a Change Request code exists")
async def exists(
    code: str, service: ChangeRequestService = Depends(get_service), _: Principal = Depends(require_read)
) -> Response:
    found = await service.exists(code)
    return Response(status_code=status.HTTP_200_OK if found else status.HTTP_404_NOT_FOUND)


@router.get("/{id}", response_model=ChangeRequestRead, summary="Get a Change Request by id")
async def get(
    id: UUID, service: ChangeRequestService = Depends(get_service), _: Principal = Depends(require_read)
) -> ChangeRequestRead:
    return await service.get(id)


@router.post("", response_model=ChangeRequestRead, status_code=status.HTTP_201_CREATED, summary="Create a Change Request")
async def create(
    payload: ChangeRequestCreate,
    request: Request,
    response: Response,
    service: ChangeRequestService = Depends(get_service),
    _: Principal = Depends(require_write),
) -> ChangeRequestRead:
    created = await service.create(payload)
    response.headers["Location"] = f"{request.url.path}/{created.id}"
    return created


@router.put("/{id}", response_model=ChangeRequestRead, summary="Update a Change Request")
async def update(
    id: UUID,
    payload: ChangeRequestUpdate,
    service: ChangeRequestService = Depends(get_service),
    _: Principal = Depends(require_write),
) -> ChangeRequestRead:
    return await service.update(id, payload)


@router.patch("/{id}/status", response_model=ChangeRequestRead, summary="Change lifecycle status")
async def change_status(
    id: UUID,
    new_status: LifecycleStatus = Query(..., alias="status"),
    service: ChangeRequestService = Depends(get_service),
    _: Principal = Depends(require_write),
) -> ChangeRequestRead:
    return await service.change_status(id, new_status)


@router.delete("/{id}", status_code=status.HTTP_204_NO_CONTENT, summary="Delete a Change Request")
async def delete(
    id: UUID, service: ChangeRequestService = Depends(get_service), _: Principal = Depends(require_write)
) -> Response:
    await service.delete(id)
    return Response(status_code=status.HTTP_204_NO_CONTENT)


@router.post("/bulk", response_model=ChangeRequestBulkResult, summary="Create many Change Request records")
async def bulk_create(
    payloads: list[ChangeRequestCreate],
    service: ChangeRequestService = Depends(get_service),
    _: Principal = Depends(require_write),
) -> ChangeRequestBulkResult:
    return await service.bulk_create(payloads)


@router.post("/reconcile", response_model=list[ReconciliationItem], summary="Reconcile external Change Request records")
async def reconcile(
    records: list[dict[str, object]],
    apply: bool = Query(False, description="Apply the plan instead of a dry run"),
    service: ChangeRequestService = Depends(get_service),
    _: Principal = Depends(require_write),
) -> list[ReconciliationItem]:
    return await service.reconcile(records, apply)

