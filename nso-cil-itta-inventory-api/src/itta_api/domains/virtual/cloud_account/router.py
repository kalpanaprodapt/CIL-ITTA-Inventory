"""HTTP routes for Cloud Account."""
from __future__ import annotations

from uuid import UUID

from fastapi import APIRouter, Depends, Query, Request, Response, status
from fastapi.responses import PlainTextResponse

from itta_core.auth import Principal, require_read, require_write
from itta_core.dependencies import service_provider
from itta_core.diffing import ReconciliationItem
from itta_core.models import EntityMeta, LifecycleStatus
from itta_core.pagination import Page
from itta_api.domains.virtual.cloud_account.filters import CloudAccountFilter
from itta_api.domains.virtual.cloud_account.meta import META
from itta_api.domains.virtual.cloud_account.schemas import (
    CloudAccountBulkResult,
    CloudAccountCreate,
    CloudAccountRead,
    CloudAccountSummary,
    CloudAccountUpdate,
)
from itta_api.domains.virtual.cloud_account.service import CloudAccountService

router = APIRouter(prefix="/api/v1/cloud-accounts", tags=["Cloud Account"])
get_service = service_provider("cloud_account")


@router.get("/meta", response_model=EntityMeta, summary="Field catalogue for Cloud Account")
async def meta() -> EntityMeta:
    return META


@router.get("", response_model=Page[CloudAccountRead], summary="Search Cloud Account records")
async def search(
    flt: CloudAccountFilter = Depends(),
    page: int = Query(0, ge=0),
    size: int = Query(20, ge=1, le=200),
    sort: str | None = Query(None, description="field,asc|desc"),
    service: CloudAccountService = Depends(get_service),
    _: Principal = Depends(require_read),
) -> Page[CloudAccountRead]:
    return await service.search(flt, page, size, sort)


@router.get("/summaries", response_model=list[CloudAccountSummary], summary="Pick-list of Cloud Account records")
async def summaries(
    status_filter: LifecycleStatus | None = Query(None, alias="status"),
    service: CloudAccountService = Depends(get_service),
    _: Principal = Depends(require_read),
) -> list[CloudAccountSummary]:
    return await service.summaries(status_filter)


@router.get("/stats/status", response_model=dict[str, int], summary="Counts per lifecycle status")
async def status_stats(
    service: CloudAccountService = Depends(get_service), _: Principal = Depends(require_read)
) -> dict[str, int]:
    return await service.stats()


@router.get("/export", response_class=PlainTextResponse, summary="Export Cloud Account records as CSV")
async def export(
    flt: CloudAccountFilter = Depends(),
    service: CloudAccountService = Depends(get_service),
    _: Principal = Depends(require_read),
) -> PlainTextResponse:
    body = await service.export_csv(flt)
    headers = {"Content-Disposition": 'attachment; filename="cloud-accounts.csv"'}
    return PlainTextResponse(body, media_type="text/csv", headers=headers)


@router.get("/by-code/{code}", response_model=CloudAccountRead, summary="Get a Cloud Account by business code")
async def get_by_code(
    code: str, service: CloudAccountService = Depends(get_service), _: Principal = Depends(require_read)
) -> CloudAccountRead:
    return await service.get_by_code(code)


@router.head("/by-code/{code}", summary="Check whether a Cloud Account code exists")
async def exists(
    code: str, service: CloudAccountService = Depends(get_service), _: Principal = Depends(require_read)
) -> Response:
    found = await service.exists(code)
    return Response(status_code=status.HTTP_200_OK if found else status.HTTP_404_NOT_FOUND)


@router.get("/{id}", response_model=CloudAccountRead, summary="Get a Cloud Account by id")
async def get(
    id: UUID, service: CloudAccountService = Depends(get_service), _: Principal = Depends(require_read)
) -> CloudAccountRead:
    return await service.get(id)


@router.post("", response_model=CloudAccountRead, status_code=status.HTTP_201_CREATED, summary="Create a Cloud Account")
async def create(
    payload: CloudAccountCreate,
    request: Request,
    response: Response,
    service: CloudAccountService = Depends(get_service),
    _: Principal = Depends(require_write),
) -> CloudAccountRead:
    created = await service.create(payload)
    response.headers["Location"] = f"{request.url.path}/{created.id}"
    return created


@router.put("/{id}", response_model=CloudAccountRead, summary="Update a Cloud Account")
async def update(
    id: UUID,
    payload: CloudAccountUpdate,
    service: CloudAccountService = Depends(get_service),
    _: Principal = Depends(require_write),
) -> CloudAccountRead:
    return await service.update(id, payload)


@router.patch("/{id}/status", response_model=CloudAccountRead, summary="Change lifecycle status")
async def change_status(
    id: UUID,
    new_status: LifecycleStatus = Query(..., alias="status"),
    service: CloudAccountService = Depends(get_service),
    _: Principal = Depends(require_write),
) -> CloudAccountRead:
    return await service.change_status(id, new_status)


@router.delete("/{id}", status_code=status.HTTP_204_NO_CONTENT, summary="Delete a Cloud Account")
async def delete(
    id: UUID, service: CloudAccountService = Depends(get_service), _: Principal = Depends(require_write)
) -> Response:
    await service.delete(id)
    return Response(status_code=status.HTTP_204_NO_CONTENT)


@router.post("/bulk", response_model=CloudAccountBulkResult, summary="Create many Cloud Account records")
async def bulk_create(
    payloads: list[CloudAccountCreate],
    service: CloudAccountService = Depends(get_service),
    _: Principal = Depends(require_write),
) -> CloudAccountBulkResult:
    return await service.bulk_create(payloads)


@router.post("/reconcile", response_model=list[ReconciliationItem], summary="Reconcile external Cloud Account records")
async def reconcile(
    records: list[dict[str, object]],
    apply: bool = Query(False, description="Apply the plan instead of a dry run"),
    service: CloudAccountService = Depends(get_service),
    _: Principal = Depends(require_write),
) -> list[ReconciliationItem]:
    return await service.reconcile(records, apply)

