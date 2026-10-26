"""HTTP routes for Topology Edge."""
from __future__ import annotations

from uuid import UUID

from fastapi import APIRouter, Depends, Query, Request, Response, status
from fastapi.responses import PlainTextResponse

from itta_core.auth import Principal, require_read, require_write
from itta_core.dependencies import service_provider
from itta_core.diffing import ReconciliationItem
from itta_core.models import EntityMeta, LifecycleStatus
from itta_core.pagination import Page
from itta_api.domains.topology.topology_edge.filters import TopologyEdgeFilter
from itta_api.domains.topology.topology_edge.meta import META
from itta_api.domains.topology.topology_edge.schemas import (
    TopologyEdgeBulkResult,
    TopologyEdgeCreate,
    TopologyEdgeRead,
    TopologyEdgeSummary,
    TopologyEdgeUpdate,
)
from itta_api.domains.topology.topology_edge.service import TopologyEdgeService

router = APIRouter(prefix="/api/v1/topology-edges", tags=["Topology Edge"])
get_service = service_provider("topology_edge")


@router.get("/meta", response_model=EntityMeta, summary="Field catalogue for Topology Edge")
async def meta() -> EntityMeta:
    return META


@router.get("", response_model=Page[TopologyEdgeRead], summary="Search Topology Edge records")
async def search(
    flt: TopologyEdgeFilter = Depends(),
    page: int = Query(0, ge=0),
    size: int = Query(20, ge=1, le=200),
    sort: str | None = Query(None, description="field,asc|desc"),
    service: TopologyEdgeService = Depends(get_service),
    _: Principal = Depends(require_read),
) -> Page[TopologyEdgeRead]:
    return await service.search(flt, page, size, sort)


@router.get("/summaries", response_model=list[TopologyEdgeSummary], summary="Pick-list of Topology Edge records")
async def summaries(
    status_filter: LifecycleStatus | None = Query(None, alias="status"),
    service: TopologyEdgeService = Depends(get_service),
    _: Principal = Depends(require_read),
) -> list[TopologyEdgeSummary]:
    return await service.summaries(status_filter)


@router.get("/stats/status", response_model=dict[str, int], summary="Counts per lifecycle status")
async def status_stats(
    service: TopologyEdgeService = Depends(get_service), _: Principal = Depends(require_read)
) -> dict[str, int]:
    return await service.stats()


@router.get("/export", response_class=PlainTextResponse, summary="Export Topology Edge records as CSV")
async def export(
    flt: TopologyEdgeFilter = Depends(),
    service: TopologyEdgeService = Depends(get_service),
    _: Principal = Depends(require_read),
) -> PlainTextResponse:
    body = await service.export_csv(flt)
    headers = {"Content-Disposition": 'attachment; filename="topology-edges.csv"'}
    return PlainTextResponse(body, media_type="text/csv", headers=headers)


@router.get("/by-code/{code}", response_model=TopologyEdgeRead, summary="Get a Topology Edge by business code")
async def get_by_code(
    code: str, service: TopologyEdgeService = Depends(get_service), _: Principal = Depends(require_read)
) -> TopologyEdgeRead:
    return await service.get_by_code(code)


@router.head("/by-code/{code}", summary="Check whether a Topology Edge code exists")
async def exists(
    code: str, service: TopologyEdgeService = Depends(get_service), _: Principal = Depends(require_read)
) -> Response:
    found = await service.exists(code)
    return Response(status_code=status.HTTP_200_OK if found else status.HTTP_404_NOT_FOUND)


@router.get("/{id}", response_model=TopologyEdgeRead, summary="Get a Topology Edge by id")
async def get(
    id: UUID, service: TopologyEdgeService = Depends(get_service), _: Principal = Depends(require_read)
) -> TopologyEdgeRead:
    return await service.get(id)


@router.post("", response_model=TopologyEdgeRead, status_code=status.HTTP_201_CREATED, summary="Create a Topology Edge")
async def create(
    payload: TopologyEdgeCreate,
    request: Request,
    response: Response,
    service: TopologyEdgeService = Depends(get_service),
    _: Principal = Depends(require_write),
) -> TopologyEdgeRead:
    created = await service.create(payload)
    response.headers["Location"] = f"{request.url.path}/{created.id}"
    return created


@router.put("/{id}", response_model=TopologyEdgeRead, summary="Update a Topology Edge")
async def update(
    id: UUID,
    payload: TopologyEdgeUpdate,
    service: TopologyEdgeService = Depends(get_service),
    _: Principal = Depends(require_write),
) -> TopologyEdgeRead:
    return await service.update(id, payload)


@router.patch("/{id}/status", response_model=TopologyEdgeRead, summary="Change lifecycle status")
async def change_status(
    id: UUID,
    new_status: LifecycleStatus = Query(..., alias="status"),
    service: TopologyEdgeService = Depends(get_service),
    _: Principal = Depends(require_write),
) -> TopologyEdgeRead:
    return await service.change_status(id, new_status)


@router.delete("/{id}", status_code=status.HTTP_204_NO_CONTENT, summary="Delete a Topology Edge")
async def delete(
    id: UUID, service: TopologyEdgeService = Depends(get_service), _: Principal = Depends(require_write)
) -> Response:
    await service.delete(id)
    return Response(status_code=status.HTTP_204_NO_CONTENT)


@router.post("/bulk", response_model=TopologyEdgeBulkResult, summary="Create many Topology Edge records")
async def bulk_create(
    payloads: list[TopologyEdgeCreate],
    service: TopologyEdgeService = Depends(get_service),
    _: Principal = Depends(require_write),
) -> TopologyEdgeBulkResult:
    return await service.bulk_create(payloads)


@router.post("/reconcile", response_model=list[ReconciliationItem], summary="Reconcile external Topology Edge records")
async def reconcile(
    records: list[dict[str, object]],
    apply: bool = Query(False, description="Apply the plan instead of a dry run"),
    service: TopologyEdgeService = Depends(get_service),
    _: Principal = Depends(require_write),
) -> list[ReconciliationItem]:
    return await service.reconcile(records, apply)


@router.get("/by-view-id/{view_id}", response_model=Page[TopologyEdgeRead], summary="List Topology Edge records by View Id")
async def list_by_view_id(
    view_id: UUID,
    page: int = Query(0, ge=0),
    size: int = Query(20, ge=1, le=200),
    service: TopologyEdgeService = Depends(get_service),
) -> Page[TopologyEdgeRead]:
    return await service.list_by_view_id(view_id, page, size)

@router.get("/by-source-node-id/{source_node_id}", response_model=Page[TopologyEdgeRead], summary="List Topology Edge records by Source Node Id")
async def list_by_source_node_id(
    source_node_id: UUID,
    page: int = Query(0, ge=0),
    size: int = Query(20, ge=1, le=200),
    service: TopologyEdgeService = Depends(get_service),
) -> Page[TopologyEdgeRead]:
    return await service.list_by_source_node_id(source_node_id, page, size)

@router.get("/by-target-node-id/{target_node_id}", response_model=Page[TopologyEdgeRead], summary="List Topology Edge records by Target Node Id")
async def list_by_target_node_id(
    target_node_id: UUID,
    page: int = Query(0, ge=0),
    size: int = Query(20, ge=1, le=200),
    service: TopologyEdgeService = Depends(get_service),
) -> Page[TopologyEdgeRead]:
    return await service.list_by_target_node_id(target_node_id, page, size)
