"""HTTP routes for Route Target."""
from __future__ import annotations

from uuid import UUID

from fastapi import APIRouter, Depends, Query, Request, Response, status
from fastapi.responses import PlainTextResponse

from itta_core.auth import Principal, require_read, require_write
from itta_core.dependencies import service_provider
from itta_core.diffing import ReconciliationItem
from itta_core.models import EntityMeta, LifecycleStatus
from itta_core.pagination import Page
from itta_api.domains.switching.route_target.filters import RouteTargetFilter
from itta_api.domains.switching.route_target.meta import META
from itta_api.domains.switching.route_target.schemas import (
    RouteTargetBulkResult,
    RouteTargetCreate,
    RouteTargetRead,
    RouteTargetSummary,
    RouteTargetUpdate,
)
from itta_api.domains.switching.route_target.service import RouteTargetService

router = APIRouter(prefix="/api/v1/route-targets", tags=["Route Target"])
get_service = service_provider("route_target")


@router.get("/meta", response_model=EntityMeta, summary="Field catalogue for Route Target")
async def meta() -> EntityMeta:
    return META


@router.get("", response_model=Page[RouteTargetRead], summary="Search Route Target records")
async def search(
    flt: RouteTargetFilter = Depends(),
    page: int = Query(0, ge=0),
    size: int = Query(20, ge=1, le=200),
    sort: str | None = Query(None, description="field,asc|desc"),
    service: RouteTargetService = Depends(get_service),
    _: Principal = Depends(require_read),
) -> Page[RouteTargetRead]:
    return await service.search(flt, page, size, sort)


@router.get("/summaries", response_model=list[RouteTargetSummary], summary="Pick-list of Route Target records")
async def summaries(
    status_filter: LifecycleStatus | None = Query(None, alias="status"),
    service: RouteTargetService = Depends(get_service),
    _: Principal = Depends(require_read),
) -> list[RouteTargetSummary]:
    return await service.summaries(status_filter)


@router.get("/stats/status", response_model=dict[str, int], summary="Counts per lifecycle status")
async def status_stats(
    service: RouteTargetService = Depends(get_service), _: Principal = Depends(require_read)
) -> dict[str, int]:
    return await service.stats()


@router.get("/export", response_class=PlainTextResponse, summary="Export Route Target records as CSV")
async def export(
    flt: RouteTargetFilter = Depends(),
    service: RouteTargetService = Depends(get_service),
    _: Principal = Depends(require_read),
) -> PlainTextResponse:
    body = await service.export_csv(flt)
    headers = {"Content-Disposition": 'attachment; filename="route-targets.csv"'}
    return PlainTextResponse(body, media_type="text/csv", headers=headers)


@router.get("/by-code/{code}", response_model=RouteTargetRead, summary="Get a Route Target by business code")
async def get_by_code(
    code: str, service: RouteTargetService = Depends(get_service), _: Principal = Depends(require_read)
) -> RouteTargetRead:
    return await service.get_by_code(code)


@router.head("/by-code/{code}", summary="Check whether a Route Target code exists")
async def exists(
    code: str, service: RouteTargetService = Depends(get_service), _: Principal = Depends(require_read)
) -> Response:
    found = await service.exists(code)
    return Response(status_code=status.HTTP_200_OK if found else status.HTTP_404_NOT_FOUND)


@router.get("/{id}", response_model=RouteTargetRead, summary="Get a Route Target by id")
async def get(
    id: UUID, service: RouteTargetService = Depends(get_service), _: Principal = Depends(require_read)
) -> RouteTargetRead:
    return await service.get(id)


@router.post("", response_model=RouteTargetRead, status_code=status.HTTP_201_CREATED, summary="Create a Route Target")
async def create(
    payload: RouteTargetCreate,
    request: Request,
    response: Response,
    service: RouteTargetService = Depends(get_service),
    _: Principal = Depends(require_write),
) -> RouteTargetRead:
    created = await service.create(payload)
    response.headers["Location"] = f"{request.url.path}/{created.id}"
    return created


@router.put("/{id}", response_model=RouteTargetRead, summary="Update a Route Target")
async def update(
    id: UUID,
    payload: RouteTargetUpdate,
    service: RouteTargetService = Depends(get_service),
    _: Principal = Depends(require_write),
) -> RouteTargetRead:
    return await service.update(id, payload)


@router.patch("/{id}/status", response_model=RouteTargetRead, summary="Change lifecycle status")
async def change_status(
    id: UUID,
    new_status: LifecycleStatus = Query(..., alias="status"),
    service: RouteTargetService = Depends(get_service),
    _: Principal = Depends(require_write),
) -> RouteTargetRead:
    return await service.change_status(id, new_status)


@router.delete("/{id}", status_code=status.HTTP_204_NO_CONTENT, summary="Delete a Route Target")
async def delete(
    id: UUID, service: RouteTargetService = Depends(get_service), _: Principal = Depends(require_write)
) -> Response:
    await service.delete(id)
    return Response(status_code=status.HTTP_204_NO_CONTENT)


@router.post("/bulk", response_model=RouteTargetBulkResult, summary="Create many Route Target records")
async def bulk_create(
    payloads: list[RouteTargetCreate],
    service: RouteTargetService = Depends(get_service),
    _: Principal = Depends(require_write),
) -> RouteTargetBulkResult:
    return await service.bulk_create(payloads)


@router.post("/reconcile", response_model=list[ReconciliationItem], summary="Reconcile external Route Target records")
async def reconcile(
    records: list[dict[str, object]],
    apply: bool = Query(False, description="Apply the plan instead of a dry run"),
    service: RouteTargetService = Depends(get_service),
    _: Principal = Depends(require_write),
) -> list[ReconciliationItem]:
    return await service.reconcile(records, apply)


@router.get("/by-vrf-id/{vrf_id}", response_model=Page[RouteTargetRead], summary="List Route Target records by Vrf Id")
async def list_by_vrf_id(
    vrf_id: UUID,
    page: int = Query(0, ge=0),
    size: int = Query(20, ge=1, le=200),
    service: RouteTargetService = Depends(get_service),
) -> Page[RouteTargetRead]:
    return await service.list_by_vrf_id(vrf_id, page, size)
