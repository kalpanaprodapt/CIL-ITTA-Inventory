"""HTTP routes for Policy Document."""
from __future__ import annotations

from uuid import UUID

from fastapi import APIRouter, Depends, Query, Request, Response, status
from fastapi.responses import PlainTextResponse

from itta_core.auth import Principal, require_read, require_write
from itta_core.dependencies import service_provider
from itta_core.diffing import ReconciliationItem
from itta_core.models import EntityMeta, LifecycleStatus
from itta_core.pagination import Page
from itta_api.domains.compliance.policy_document.filters import PolicyDocumentFilter
from itta_api.domains.compliance.policy_document.meta import META
from itta_api.domains.compliance.policy_document.schemas import (
    PolicyDocumentBulkResult,
    PolicyDocumentCreate,
    PolicyDocumentRead,
    PolicyDocumentSummary,
    PolicyDocumentUpdate,
)
from itta_api.domains.compliance.policy_document.service import PolicyDocumentService

router = APIRouter(prefix="/api/v1/policy-documents", tags=["Policy Document"])
get_service = service_provider("policy_document")


@router.get("/meta", response_model=EntityMeta, summary="Field catalogue for Policy Document")
async def meta() -> EntityMeta:
    return META


@router.get("", response_model=Page[PolicyDocumentRead], summary="Search Policy Document records")
async def search(
    flt: PolicyDocumentFilter = Depends(),
    page: int = Query(0, ge=0),
    size: int = Query(20, ge=1, le=200),
    sort: str | None = Query(None, description="field,asc|desc"),
    service: PolicyDocumentService = Depends(get_service),
    _: Principal = Depends(require_read),
) -> Page[PolicyDocumentRead]:
    return await service.search(flt, page, size, sort)


@router.get("/summaries", response_model=list[PolicyDocumentSummary], summary="Pick-list of Policy Document records")
async def summaries(
    status_filter: LifecycleStatus | None = Query(None, alias="status"),
    service: PolicyDocumentService = Depends(get_service),
    _: Principal = Depends(require_read),
) -> list[PolicyDocumentSummary]:
    return await service.summaries(status_filter)


@router.get("/stats/status", response_model=dict[str, int], summary="Counts per lifecycle status")
async def status_stats(
    service: PolicyDocumentService = Depends(get_service), _: Principal = Depends(require_read)
) -> dict[str, int]:
    return await service.stats()


@router.get("/export", response_class=PlainTextResponse, summary="Export Policy Document records as CSV")
async def export(
    flt: PolicyDocumentFilter = Depends(),
    service: PolicyDocumentService = Depends(get_service),
    _: Principal = Depends(require_read),
) -> PlainTextResponse:
    body = await service.export_csv(flt)
    headers = {"Content-Disposition": 'attachment; filename="policy-documents.csv"'}
    return PlainTextResponse(body, media_type="text/csv", headers=headers)


@router.get("/by-code/{code}", response_model=PolicyDocumentRead, summary="Get a Policy Document by business code")
async def get_by_code(
    code: str, service: PolicyDocumentService = Depends(get_service), _: Principal = Depends(require_read)
) -> PolicyDocumentRead:
    return await service.get_by_code(code)


@router.head("/by-code/{code}", summary="Check whether a Policy Document code exists")
async def exists(
    code: str, service: PolicyDocumentService = Depends(get_service), _: Principal = Depends(require_read)
) -> Response:
    found = await service.exists(code)
    return Response(status_code=status.HTTP_200_OK if found else status.HTTP_404_NOT_FOUND)


@router.get("/{id}", response_model=PolicyDocumentRead, summary="Get a Policy Document by id")
async def get(
    id: UUID, service: PolicyDocumentService = Depends(get_service), _: Principal = Depends(require_read)
) -> PolicyDocumentRead:
    return await service.get(id)


@router.post("", response_model=PolicyDocumentRead, status_code=status.HTTP_201_CREATED, summary="Create a Policy Document")
async def create(
    payload: PolicyDocumentCreate,
    request: Request,
    response: Response,
    service: PolicyDocumentService = Depends(get_service),
    _: Principal = Depends(require_write),
) -> PolicyDocumentRead:
    created = await service.create(payload)
    response.headers["Location"] = f"{request.url.path}/{created.id}"
    return created


@router.put("/{id}", response_model=PolicyDocumentRead, summary="Update a Policy Document")
async def update(
    id: UUID,
    payload: PolicyDocumentUpdate,
    service: PolicyDocumentService = Depends(get_service),
    _: Principal = Depends(require_write),
) -> PolicyDocumentRead:
    return await service.update(id, payload)


@router.patch("/{id}/status", response_model=PolicyDocumentRead, summary="Change lifecycle status")
async def change_status(
    id: UUID,
    new_status: LifecycleStatus = Query(..., alias="status"),
    service: PolicyDocumentService = Depends(get_service),
    _: Principal = Depends(require_write),
) -> PolicyDocumentRead:
    return await service.change_status(id, new_status)


@router.delete("/{id}", status_code=status.HTTP_204_NO_CONTENT, summary="Delete a Policy Document")
async def delete(
    id: UUID, service: PolicyDocumentService = Depends(get_service), _: Principal = Depends(require_write)
) -> Response:
    await service.delete(id)
    return Response(status_code=status.HTTP_204_NO_CONTENT)


@router.post("/bulk", response_model=PolicyDocumentBulkResult, summary="Create many Policy Document records")
async def bulk_create(
    payloads: list[PolicyDocumentCreate],
    service: PolicyDocumentService = Depends(get_service),
    _: Principal = Depends(require_write),
) -> PolicyDocumentBulkResult:
    return await service.bulk_create(payloads)


@router.post("/reconcile", response_model=list[ReconciliationItem], summary="Reconcile external Policy Document records")
async def reconcile(
    records: list[dict[str, object]],
    apply: bool = Query(False, description="Apply the plan instead of a dry run"),
    service: PolicyDocumentService = Depends(get_service),
    _: Principal = Depends(require_write),
) -> list[ReconciliationItem]:
    return await service.reconcile(records, apply)

