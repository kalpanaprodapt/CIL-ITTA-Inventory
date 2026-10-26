"""HTTP routes for K8s Cluster."""
from __future__ import annotations

from uuid import UUID

from fastapi import APIRouter, Depends, Query, Request, Response, status
from fastapi.responses import PlainTextResponse

from itta_core.auth import Principal, require_read, require_write
from itta_core.dependencies import service_provider
from itta_core.diffing import ReconciliationItem
from itta_core.models import EntityMeta, LifecycleStatus
from itta_core.pagination import Page
from itta_api.domains.virtual.k8s_cluster.filters import K8sClusterFilter
from itta_api.domains.virtual.k8s_cluster.meta import META
from itta_api.domains.virtual.k8s_cluster.schemas import (
    K8sClusterBulkResult,
    K8sClusterCreate,
    K8sClusterRead,
    K8sClusterSummary,
    K8sClusterUpdate,
)
from itta_api.domains.virtual.k8s_cluster.service import K8sClusterService

router = APIRouter(prefix="/api/v1/k8s-clusters", tags=["K8s Cluster"])
get_service = service_provider("k8s_cluster")


@router.get("/meta", response_model=EntityMeta, summary="Field catalogue for K8s Cluster")
async def meta() -> EntityMeta:
    return META


@router.get("", response_model=Page[K8sClusterRead], summary="Search K8s Cluster records")
async def search(
    flt: K8sClusterFilter = Depends(),
    page: int = Query(0, ge=0),
    size: int = Query(20, ge=1, le=200),
    sort: str | None = Query(None, description="field,asc|desc"),
    service: K8sClusterService = Depends(get_service),
    _: Principal = Depends(require_read),
) -> Page[K8sClusterRead]:
    return await service.search(flt, page, size, sort)


@router.get("/summaries", response_model=list[K8sClusterSummary], summary="Pick-list of K8s Cluster records")
async def summaries(
    status_filter: LifecycleStatus | None = Query(None, alias="status"),
    service: K8sClusterService = Depends(get_service),
    _: Principal = Depends(require_read),
) -> list[K8sClusterSummary]:
    return await service.summaries(status_filter)


@router.get("/stats/status", response_model=dict[str, int], summary="Counts per lifecycle status")
async def status_stats(
    service: K8sClusterService = Depends(get_service), _: Principal = Depends(require_read)
) -> dict[str, int]:
    return await service.stats()


@router.get("/export", response_class=PlainTextResponse, summary="Export K8s Cluster records as CSV")
async def export(
    flt: K8sClusterFilter = Depends(),
    service: K8sClusterService = Depends(get_service),
    _: Principal = Depends(require_read),
) -> PlainTextResponse:
    body = await service.export_csv(flt)
    headers = {"Content-Disposition": 'attachment; filename="k8s-clusters.csv"'}
    return PlainTextResponse(body, media_type="text/csv", headers=headers)


@router.get("/by-code/{code}", response_model=K8sClusterRead, summary="Get a K8s Cluster by business code")
async def get_by_code(
    code: str, service: K8sClusterService = Depends(get_service), _: Principal = Depends(require_read)
) -> K8sClusterRead:
    return await service.get_by_code(code)


@router.head("/by-code/{code}", summary="Check whether a K8s Cluster code exists")
async def exists(
    code: str, service: K8sClusterService = Depends(get_service), _: Principal = Depends(require_read)
) -> Response:
    found = await service.exists(code)
    return Response(status_code=status.HTTP_200_OK if found else status.HTTP_404_NOT_FOUND)


@router.get("/{id}", response_model=K8sClusterRead, summary="Get a K8s Cluster by id")
async def get(
    id: UUID, service: K8sClusterService = Depends(get_service), _: Principal = Depends(require_read)
) -> K8sClusterRead:
    return await service.get(id)


@router.post("", response_model=K8sClusterRead, status_code=status.HTTP_201_CREATED, summary="Create a K8s Cluster")
async def create(
    payload: K8sClusterCreate,
    request: Request,
    response: Response,
    service: K8sClusterService = Depends(get_service),
    _: Principal = Depends(require_write),
) -> K8sClusterRead:
    created = await service.create(payload)
    response.headers["Location"] = f"{request.url.path}/{created.id}"
    return created


@router.put("/{id}", response_model=K8sClusterRead, summary="Update a K8s Cluster")
async def update(
    id: UUID,
    payload: K8sClusterUpdate,
    service: K8sClusterService = Depends(get_service),
    _: Principal = Depends(require_write),
) -> K8sClusterRead:
    return await service.update(id, payload)


@router.patch("/{id}/status", response_model=K8sClusterRead, summary="Change lifecycle status")
async def change_status(
    id: UUID,
    new_status: LifecycleStatus = Query(..., alias="status"),
    service: K8sClusterService = Depends(get_service),
    _: Principal = Depends(require_write),
) -> K8sClusterRead:
    return await service.change_status(id, new_status)


@router.delete("/{id}", status_code=status.HTTP_204_NO_CONTENT, summary="Delete a K8s Cluster")
async def delete(
    id: UUID, service: K8sClusterService = Depends(get_service), _: Principal = Depends(require_write)
) -> Response:
    await service.delete(id)
    return Response(status_code=status.HTTP_204_NO_CONTENT)


@router.post("/bulk", response_model=K8sClusterBulkResult, summary="Create many K8s Cluster records")
async def bulk_create(
    payloads: list[K8sClusterCreate],
    service: K8sClusterService = Depends(get_service),
    _: Principal = Depends(require_write),
) -> K8sClusterBulkResult:
    return await service.bulk_create(payloads)


@router.post("/reconcile", response_model=list[ReconciliationItem], summary="Reconcile external K8s Cluster records")
async def reconcile(
    records: list[dict[str, object]],
    apply: bool = Query(False, description="Apply the plan instead of a dry run"),
    service: K8sClusterService = Depends(get_service),
    _: Principal = Depends(require_write),
) -> list[ReconciliationItem]:
    return await service.reconcile(records, apply)


@router.get("/by-cloud-account-id/{cloud_account_id}", response_model=Page[K8sClusterRead], summary="List K8s Cluster records by Cloud Account Id")
async def list_by_cloud_account_id(
    cloud_account_id: UUID,
    page: int = Query(0, ge=0),
    size: int = Query(20, ge=1, le=200),
    service: K8sClusterService = Depends(get_service),
) -> Page[K8sClusterRead]:
    return await service.list_by_cloud_account_id(cloud_account_id, page, size)
