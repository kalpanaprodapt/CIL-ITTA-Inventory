"""HTTP routes for Topology View."""
from __future__ import annotations

from uuid import UUID

from fastapi import APIRouter, Depends, Query, Request, Response, status
from fastapi.responses import PlainTextResponse

from itta_core.auth import Principal, require_read, require_write
from itta_core.dependencies import service_provider
from itta_core.diffing import ReconciliationItem
from itta_core.models import EntityMeta, LifecycleStatus
from itta_core.pagination import Page
from itta_api.domains.topology.topology_view.filters import TopologyViewFilter
from itta_api.domains.topology.topology_view.meta import META
from itta_api.domains.topology.topology_view.schemas import (
    TopologyViewBulkResult,
    TopologyViewCreate,
    TopologyViewRead,
    TopologyViewSummary,
    TopologyViewUpdate,
)
from itta_api.domains.topology.topology_view.service import TopologyViewService

router = APIRouter(prefix="/api/v1/topology-views", tags=["Topology View"])
get_service = service_provider("topology_view")


@router.get("/meta", response_model=EntityMeta, summary="Field catalogue for Topology View")
async def meta() -> EntityMeta:
    return META


@router.get("", response_model=Page[TopologyViewRead], summary="Search Topology View records")
async def search(
    flt: TopologyViewFilter = Depends(),
    page: int = Query(0, ge=0),
    size: int = Query(20, ge=1, le=200),
    sort: str | None = Query(None, description="field,asc|desc"),
    service: TopologyViewService = Depends(get_service),
    _: Principal = Depends(require_read),
) -> Page[TopologyViewRead]:
    return await service.search(flt, page, size, sort)


@router.get("/summaries", response_model=list[TopologyViewSummary], summary="Pick-list of Topology View records")
async def summaries(
    status_filter: LifecycleStatus | None = Query(None, alias="status"),
    service: TopologyViewService = Depends(get_service),
    _: Principal = Depends(require_read),
) -> list[TopologyViewSummary]:
    return await service.summaries(status_filter)


@router.get("/stats/status", response_model=dict[str, int], summary="Counts per lifecycle status")
async def status_stats(
    service: TopologyViewService = Depends(get_service), _: Principal = Depends(require_read)
) -> dict[str, int]:
    return await service.stats()


@router.get("/export", response_class=PlainTextResponse, summary="Export Topology View records as CSV")
async def export(
    flt: TopologyViewFilter = Depends(),
    service: TopologyViewService = Depends(get_service),
    _: Principal = Depends(require_read),
) -> PlainTextResponse:
    body = await service.export_csv(flt)
    headers = {"Content-Disposition": 'attachment; filename="topology-views.csv"'}
    return PlainTextResponse(body, media_type="text/csv", headers=headers)


@router.get("/by-code/{code}", response_model=TopologyViewRead, summary="Get a Topology View by business code")
async def get_by_code(
    code: str, service: TopologyViewService = Depends(get_service), _: Principal = Depends(require_read)
) -> TopologyViewRead:
    return await service.get_by_code(code)


@router.head("/by-code/{code}", summary="Check whether a Topology View code exists")
async def exists(
    code: str, service: TopologyViewService = Depends(get_service), _: Principal = Depends(require_read)
) -> Response:
    found = await service.exists(code)
    return Response(status_code=status.HTTP_200_OK if found else status.HTTP_404_NOT_FOUND)


@router.get("/{id}", response_model=TopologyViewRead, summary="Get a Topology View by id")
async def get(
    id: UUID, service: TopologyViewService = Depends(get_service), _: Principal = Depends(require_read)
) -> TopologyViewRead:
    return await service.get(id)


@router.post("", response_model=TopologyViewRead, status_code=status.HTTP_201_CREATED, summary="Create a Topology View")
async def create(
    payload: TopologyViewCreate,
    request: Request,
    response: Response,
    service: TopologyViewService = Depends(get_service),
    _: Principal = Depends(require_write),
) -> TopologyViewRead:
    created = await service.create(payload)
    response.headers["Location"] = f"{request.url.path}/{created.id}"
    return created


@router.put("/{id}", response_model=TopologyViewRead, summary="Update a Topology View")
async def update(
    id: UUID,
    payload: TopologyViewUpdate,
    service: TopologyViewService = Depends(get_service),
    _: Principal = Depends(require_write),
) -> TopologyViewRead:
    return await service.update(id, payload)


@router.patch("/{id}/status", response_model=TopologyViewRead, summary="Change lifecycle status")
async def change_status(
    id: UUID,
    new_status: LifecycleStatus = Query(..., alias="status"),
    service: TopologyViewService = Depends(get_service),
    _: Principal = Depends(require_write),
) -> TopologyViewRead:
    return await service.change_status(id, new_status)


@router.delete("/{id}", status_code=status.HTTP_204_NO_CONTENT, summary="Delete a Topology View")
async def delete(
    id: UUID, service: TopologyViewService = Depends(get_service), _: Principal = Depends(require_write)
) -> Response:
    await service.delete(id)
    return Response(status_code=status.HTTP_204_NO_CONTENT)


@router.post("/bulk", response_model=TopologyViewBulkResult, summary="Create many Topology View records")
async def bulk_create(
    payloads: list[TopologyViewCreate],
    service: TopologyViewService = Depends(get_service),
    _: Principal = Depends(require_write),
) -> TopologyViewBulkResult:
    return await service.bulk_create(payloads)


@router.post("/reconcile", response_model=list[ReconciliationItem], summary="Reconcile external Topology View records")
async def reconcile(
    records: list[dict[str, object]],
    apply: bool = Query(False, description="Apply the plan instead of a dry run"),
    service: TopologyViewService = Depends(get_service),
    _: Principal = Depends(require_write),
) -> list[ReconciliationItem]:
    return await service.reconcile(records, apply)

