"""HTTP routes for Topology Node."""
from __future__ import annotations

from uuid import UUID

from fastapi import APIRouter, Depends, Query, Request, Response, status
from fastapi.responses import PlainTextResponse

from itta_core.auth import Principal, require_read, require_write
from itta_core.dependencies import service_provider
from itta_core.diffing import ReconciliationItem
from itta_core.models import EntityMeta, LifecycleStatus
from itta_core.pagination import Page
from itta_api.domains.topology.topology_node.filters import TopologyNodeFilter
from itta_api.domains.topology.topology_node.meta import META
from itta_api.domains.topology.topology_node.schemas import (
    TopologyNodeBulkResult,
    TopologyNodeCreate,
    TopologyNodeRead,
    TopologyNodeSummary,
    TopologyNodeUpdate,
)
from itta_api.domains.topology.topology_node.service import TopologyNodeService

router = APIRouter(prefix="/api/v1/topology-nodes", tags=["Topology Node"])
get_service = service_provider("topology_node")


@router.get("/meta", response_model=EntityMeta, summary="Field catalogue for Topology Node")
async def meta() -> EntityMeta:
    return META


@router.get("", response_model=Page[TopologyNodeRead], summary="Search Topology Node records")
async def search(
    flt: TopologyNodeFilter = Depends(),
    page: int = Query(0, ge=0),
    size: int = Query(20, ge=1, le=200),
    sort: str | None = Query(None, description="field,asc|desc"),
    service: TopologyNodeService = Depends(get_service),
    _: Principal = Depends(require_read),
) -> Page[TopologyNodeRead]:
    return await service.search(flt, page, size, sort)


@router.get("/summaries", response_model=list[TopologyNodeSummary], summary="Pick-list of Topology Node records")
async def summaries(
    status_filter: LifecycleStatus | None = Query(None, alias="status"),
    service: TopologyNodeService = Depends(get_service),
    _: Principal = Depends(require_read),
) -> list[TopologyNodeSummary]:
    return await service.summaries(status_filter)


@router.get("/stats/status", response_model=dict[str, int], summary="Counts per lifecycle status")
async def status_stats(
    service: TopologyNodeService = Depends(get_service), _: Principal = Depends(require_read)
) -> dict[str, int]:
    return await service.stats()


@router.get("/export", response_class=PlainTextResponse, summary="Export Topology Node records as CSV")
async def export(
    flt: TopologyNodeFilter = Depends(),
    service: TopologyNodeService = Depends(get_service),
    _: Principal = Depends(require_read),
) -> PlainTextResponse:
    body = await service.export_csv(flt)
    headers = {"Content-Disposition": 'attachment; filename="topology-nodes.csv"'}
    return PlainTextResponse(body, media_type="text/csv", headers=headers)


@router.get("/by-code/{code}", response_model=TopologyNodeRead, summary="Get a Topology Node by business code")
async def get_by_code(
    code: str, service: TopologyNodeService = Depends(get_service), _: Principal = Depends(require_read)
) -> TopologyNodeRead:
    return await service.get_by_code(code)


@router.head("/by-code/{code}", summary="Check whether a Topology Node code exists")
async def exists(
    code: str, service: TopologyNodeService = Depends(get_service), _: Principal = Depends(require_read)
) -> Response:
    found = await service.exists(code)
    return Response(status_code=status.HTTP_200_OK if found else status.HTTP_404_NOT_FOUND)


@router.get("/{id}", response_model=TopologyNodeRead, summary="Get a Topology Node by id")
async def get(
    id: UUID, service: TopologyNodeService = Depends(get_service), _: Principal = Depends(require_read)
) -> TopologyNodeRead:
    return await service.get(id)


@router.post("", response_model=TopologyNodeRead, status_code=status.HTTP_201_CREATED, summary="Create a Topology Node")
async def create(
    payload: TopologyNodeCreate,
    request: Request,
    response: Response,
    service: TopologyNodeService = Depends(get_service),
    _: Principal = Depends(require_write),
) -> TopologyNodeRead:
    created = await service.create(payload)
    response.headers["Location"] = f"{request.url.path}/{created.id}"
    return created


@router.put("/{id}", response_model=TopologyNodeRead, summary="Update a Topology Node")
async def update(
    id: UUID,
    payload: TopologyNodeUpdate,
    service: TopologyNodeService = Depends(get_service),
    _: Principal = Depends(require_write),
) -> TopologyNodeRead:
    return await service.update(id, payload)


@router.patch("/{id}/status", response_model=TopologyNodeRead, summary="Change lifecycle status")
async def change_status(
    id: UUID,
    new_status: LifecycleStatus = Query(..., alias="status"),
    service: TopologyNodeService = Depends(get_service),
    _: Principal = Depends(require_write),
) -> TopologyNodeRead:
    return await service.change_status(id, new_status)


@router.delete("/{id}", status_code=status.HTTP_204_NO_CONTENT, summary="Delete a Topology Node")
async def delete(
    id: UUID, service: TopologyNodeService = Depends(get_service), _: Principal = Depends(require_write)
) -> Response:
    await service.delete(id)
    return Response(status_code=status.HTTP_204_NO_CONTENT)


@router.post("/bulk", response_model=TopologyNodeBulkResult, summary="Create many Topology Node records")
async def bulk_create(
    payloads: list[TopologyNodeCreate],
    service: TopologyNodeService = Depends(get_service),
    _: Principal = Depends(require_write),
) -> TopologyNodeBulkResult:
    return await service.bulk_create(payloads)


@router.post("/reconcile", response_model=list[ReconciliationItem], summary="Reconcile external Topology Node records")
async def reconcile(
    records: list[dict[str, object]],
    apply: bool = Query(False, description="Apply the plan instead of a dry run"),
    service: TopologyNodeService = Depends(get_service),
    _: Principal = Depends(require_write),
) -> list[ReconciliationItem]:
    return await service.reconcile(records, apply)


@router.get("/by-view-id/{view_id}", response_model=Page[TopologyNodeRead], summary="List Topology Node records by View Id")
async def list_by_view_id(
    view_id: UUID,
    page: int = Query(0, ge=0),
    size: int = Query(20, ge=1, le=200),
    service: TopologyNodeService = Depends(get_service),
) -> Page[TopologyNodeRead]:
    return await service.list_by_view_id(view_id, page, size)
