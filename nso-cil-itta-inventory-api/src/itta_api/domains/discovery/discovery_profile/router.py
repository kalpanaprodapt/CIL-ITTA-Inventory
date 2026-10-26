"""HTTP routes for Discovery Profile."""
from __future__ import annotations

from uuid import UUID

from fastapi import APIRouter, Depends, Query, Request, Response, status
from fastapi.responses import PlainTextResponse

from itta_core.auth import Principal, require_read, require_write
from itta_core.dependencies import service_provider
from itta_core.diffing import ReconciliationItem
from itta_core.models import EntityMeta, LifecycleStatus
from itta_core.pagination import Page
from itta_api.domains.discovery.discovery_profile.filters import DiscoveryProfileFilter
from itta_api.domains.discovery.discovery_profile.meta import META
from itta_api.domains.discovery.discovery_profile.schemas import (
    DiscoveryProfileBulkResult,
    DiscoveryProfileCreate,
    DiscoveryProfileRead,
    DiscoveryProfileSummary,
    DiscoveryProfileUpdate,
)
from itta_api.domains.discovery.discovery_profile.service import DiscoveryProfileService

router = APIRouter(prefix="/api/v1/discovery-profiles", tags=["Discovery Profile"])
get_service = service_provider("discovery_profile")


@router.get("/meta", response_model=EntityMeta, summary="Field catalogue for Discovery Profile")
async def meta() -> EntityMeta:
    return META


@router.get("", response_model=Page[DiscoveryProfileRead], summary="Search Discovery Profile records")
async def search(
    flt: DiscoveryProfileFilter = Depends(),
    page: int = Query(0, ge=0),
    size: int = Query(20, ge=1, le=200),
    sort: str | None = Query(None, description="field,asc|desc"),
    service: DiscoveryProfileService = Depends(get_service),
    _: Principal = Depends(require_read),
) -> Page[DiscoveryProfileRead]:
    return await service.search(flt, page, size, sort)


@router.get("/summaries", response_model=list[DiscoveryProfileSummary], summary="Pick-list of Discovery Profile records")
async def summaries(
    status_filter: LifecycleStatus | None = Query(None, alias="status"),
    service: DiscoveryProfileService = Depends(get_service),
    _: Principal = Depends(require_read),
) -> list[DiscoveryProfileSummary]:
    return await service.summaries(status_filter)


@router.get("/stats/status", response_model=dict[str, int], summary="Counts per lifecycle status")
async def status_stats(
    service: DiscoveryProfileService = Depends(get_service), _: Principal = Depends(require_read)
) -> dict[str, int]:
    return await service.stats()


@router.get("/export", response_class=PlainTextResponse, summary="Export Discovery Profile records as CSV")
async def export(
    flt: DiscoveryProfileFilter = Depends(),
    service: DiscoveryProfileService = Depends(get_service),
    _: Principal = Depends(require_read),
) -> PlainTextResponse:
    body = await service.export_csv(flt)
    headers = {"Content-Disposition": 'attachment; filename="discovery-profiles.csv"'}
    return PlainTextResponse(body, media_type="text/csv", headers=headers)


@router.get("/by-code/{code}", response_model=DiscoveryProfileRead, summary="Get a Discovery Profile by business code")
async def get_by_code(
    code: str, service: DiscoveryProfileService = Depends(get_service), _: Principal = Depends(require_read)
) -> DiscoveryProfileRead:
    return await service.get_by_code(code)


@router.head("/by-code/{code}", summary="Check whether a Discovery Profile code exists")
async def exists(
    code: str, service: DiscoveryProfileService = Depends(get_service), _: Principal = Depends(require_read)
) -> Response:
    found = await service.exists(code)
    return Response(status_code=status.HTTP_200_OK if found else status.HTTP_404_NOT_FOUND)


@router.get("/{id}", response_model=DiscoveryProfileRead, summary="Get a Discovery Profile by id")
async def get(
    id: UUID, service: DiscoveryProfileService = Depends(get_service), _: Principal = Depends(require_read)
) -> DiscoveryProfileRead:
    return await service.get(id)


@router.post("", response_model=DiscoveryProfileRead, status_code=status.HTTP_201_CREATED, summary="Create a Discovery Profile")
async def create(
    payload: DiscoveryProfileCreate,
    request: Request,
    response: Response,
    service: DiscoveryProfileService = Depends(get_service),
    _: Principal = Depends(require_write),
) -> DiscoveryProfileRead:
    created = await service.create(payload)
    response.headers["Location"] = f"{request.url.path}/{created.id}"
    return created


@router.put("/{id}", response_model=DiscoveryProfileRead, summary="Update a Discovery Profile")
async def update(
    id: UUID,
    payload: DiscoveryProfileUpdate,
    service: DiscoveryProfileService = Depends(get_service),
    _: Principal = Depends(require_write),
) -> DiscoveryProfileRead:
    return await service.update(id, payload)


@router.patch("/{id}/status", response_model=DiscoveryProfileRead, summary="Change lifecycle status")
async def change_status(
    id: UUID,
    new_status: LifecycleStatus = Query(..., alias="status"),
    service: DiscoveryProfileService = Depends(get_service),
    _: Principal = Depends(require_write),
) -> DiscoveryProfileRead:
    return await service.change_status(id, new_status)


@router.delete("/{id}", status_code=status.HTTP_204_NO_CONTENT, summary="Delete a Discovery Profile")
async def delete(
    id: UUID, service: DiscoveryProfileService = Depends(get_service), _: Principal = Depends(require_write)
) -> Response:
    await service.delete(id)
    return Response(status_code=status.HTTP_204_NO_CONTENT)


@router.post("/bulk", response_model=DiscoveryProfileBulkResult, summary="Create many Discovery Profile records")
async def bulk_create(
    payloads: list[DiscoveryProfileCreate],
    service: DiscoveryProfileService = Depends(get_service),
    _: Principal = Depends(require_write),
) -> DiscoveryProfileBulkResult:
    return await service.bulk_create(payloads)


@router.post("/reconcile", response_model=list[ReconciliationItem], summary="Reconcile external Discovery Profile records")
async def reconcile(
    records: list[dict[str, object]],
    apply: bool = Query(False, description="Apply the plan instead of a dry run"),
    service: DiscoveryProfileService = Depends(get_service),
    _: Principal = Depends(require_write),
) -> list[ReconciliationItem]:
    return await service.reconcile(records, apply)

