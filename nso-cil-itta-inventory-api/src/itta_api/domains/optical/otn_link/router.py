"""HTTP routes for Otn Link."""
from __future__ import annotations

from uuid import UUID

from fastapi import APIRouter, Depends, Query, Request, Response, status
from fastapi.responses import PlainTextResponse

from itta_core.auth import Principal, require_read, require_write
from itta_core.dependencies import service_provider
from itta_core.diffing import ReconciliationItem
from itta_core.models import EntityMeta, LifecycleStatus
from itta_core.pagination import Page
from itta_api.domains.optical.otn_link.filters import OtnLinkFilter
from itta_api.domains.optical.otn_link.meta import META
from itta_api.domains.optical.otn_link.schemas import (
    OtnLinkBulkResult,
    OtnLinkCreate,
    OtnLinkRead,
    OtnLinkSummary,
    OtnLinkUpdate,
)
from itta_api.domains.optical.otn_link.service import OtnLinkService

router = APIRouter(prefix="/api/v1/otn-links", tags=["Otn Link"])
get_service = service_provider("otn_link")


@router.get("/meta", response_model=EntityMeta, summary="Field catalogue for Otn Link")
async def meta() -> EntityMeta:
    return META


@router.get("", response_model=Page[OtnLinkRead], summary="Search Otn Link records")
async def search(
    flt: OtnLinkFilter = Depends(),
    page: int = Query(0, ge=0),
    size: int = Query(20, ge=1, le=200),
    sort: str | None = Query(None, description="field,asc|desc"),
    service: OtnLinkService = Depends(get_service),
    _: Principal = Depends(require_read),
) -> Page[OtnLinkRead]:
    return await service.search(flt, page, size, sort)


@router.get("/summaries", response_model=list[OtnLinkSummary], summary="Pick-list of Otn Link records")
async def summaries(
    status_filter: LifecycleStatus | None = Query(None, alias="status"),
    service: OtnLinkService = Depends(get_service),
    _: Principal = Depends(require_read),
) -> list[OtnLinkSummary]:
    return await service.summaries(status_filter)


@router.get("/stats/status", response_model=dict[str, int], summary="Counts per lifecycle status")
async def status_stats(
    service: OtnLinkService = Depends(get_service), _: Principal = Depends(require_read)
) -> dict[str, int]:
    return await service.stats()


@router.get("/export", response_class=PlainTextResponse, summary="Export Otn Link records as CSV")
async def export(
    flt: OtnLinkFilter = Depends(),
    service: OtnLinkService = Depends(get_service),
    _: Principal = Depends(require_read),
) -> PlainTextResponse:
    body = await service.export_csv(flt)
    headers = {"Content-Disposition": 'attachment; filename="otn-links.csv"'}
    return PlainTextResponse(body, media_type="text/csv", headers=headers)


@router.get("/by-code/{code}", response_model=OtnLinkRead, summary="Get a Otn Link by business code")
async def get_by_code(
    code: str, service: OtnLinkService = Depends(get_service), _: Principal = Depends(require_read)
) -> OtnLinkRead:
    return await service.get_by_code(code)


@router.head("/by-code/{code}", summary="Check whether a Otn Link code exists")
async def exists(
    code: str, service: OtnLinkService = Depends(get_service), _: Principal = Depends(require_read)
) -> Response:
    found = await service.exists(code)
    return Response(status_code=status.HTTP_200_OK if found else status.HTTP_404_NOT_FOUND)


@router.get("/{id}", response_model=OtnLinkRead, summary="Get a Otn Link by id")
async def get(
    id: UUID, service: OtnLinkService = Depends(get_service), _: Principal = Depends(require_read)
) -> OtnLinkRead:
    return await service.get(id)


@router.post("", response_model=OtnLinkRead, status_code=status.HTTP_201_CREATED, summary="Create a Otn Link")
async def create(
    payload: OtnLinkCreate,
    request: Request,
    response: Response,
    service: OtnLinkService = Depends(get_service),
    _: Principal = Depends(require_write),
) -> OtnLinkRead:
    created = await service.create(payload)
    response.headers["Location"] = f"{request.url.path}/{created.id}"
    return created


@router.put("/{id}", response_model=OtnLinkRead, summary="Update a Otn Link")
async def update(
    id: UUID,
    payload: OtnLinkUpdate,
    service: OtnLinkService = Depends(get_service),
    _: Principal = Depends(require_write),
) -> OtnLinkRead:
    return await service.update(id, payload)


@router.patch("/{id}/status", response_model=OtnLinkRead, summary="Change lifecycle status")
async def change_status(
    id: UUID,
    new_status: LifecycleStatus = Query(..., alias="status"),
    service: OtnLinkService = Depends(get_service),
    _: Principal = Depends(require_write),
) -> OtnLinkRead:
    return await service.change_status(id, new_status)


@router.delete("/{id}", status_code=status.HTTP_204_NO_CONTENT, summary="Delete a Otn Link")
async def delete(
    id: UUID, service: OtnLinkService = Depends(get_service), _: Principal = Depends(require_write)
) -> Response:
    await service.delete(id)
    return Response(status_code=status.HTTP_204_NO_CONTENT)


@router.post("/bulk", response_model=OtnLinkBulkResult, summary="Create many Otn Link records")
async def bulk_create(
    payloads: list[OtnLinkCreate],
    service: OtnLinkService = Depends(get_service),
    _: Principal = Depends(require_write),
) -> OtnLinkBulkResult:
    return await service.bulk_create(payloads)


@router.post("/reconcile", response_model=list[ReconciliationItem], summary="Reconcile external Otn Link records")
async def reconcile(
    records: list[dict[str, object]],
    apply: bool = Query(False, description="Apply the plan instead of a dry run"),
    service: OtnLinkService = Depends(get_service),
    _: Principal = Depends(require_write),
) -> list[ReconciliationItem]:
    return await service.reconcile(records, apply)


@router.get("/by-a-node-id/{a_node_id}", response_model=Page[OtnLinkRead], summary="List Otn Link records by A Node Id")
async def list_by_a_node_id(
    a_node_id: UUID,
    page: int = Query(0, ge=0),
    size: int = Query(20, ge=1, le=200),
    service: OtnLinkService = Depends(get_service),
) -> Page[OtnLinkRead]:
    return await service.list_by_a_node_id(a_node_id, page, size)

@router.get("/by-z-node-id/{z_node_id}", response_model=Page[OtnLinkRead], summary="List Otn Link records by Z Node Id")
async def list_by_z_node_id(
    z_node_id: UUID,
    page: int = Query(0, ge=0),
    size: int = Query(20, ge=1, le=200),
    service: OtnLinkService = Depends(get_service),
) -> Page[OtnLinkRead]:
    return await service.list_by_z_node_id(z_node_id, page, size)
