"""HTTP routes for Link."""
from __future__ import annotations

from uuid import UUID

from fastapi import APIRouter, Depends, Query, Request, Response, status
from fastapi.responses import PlainTextResponse

from itta_core.auth import Principal, require_read, require_write
from itta_core.dependencies import service_provider
from itta_core.diffing import ReconciliationItem
from itta_core.models import EntityMeta, LifecycleStatus
from itta_core.pagination import Page
from itta_api.domains.topology.link.filters import LinkFilter
from itta_api.domains.topology.link.meta import META
from itta_api.domains.topology.link.schemas import (
    LinkBulkResult,
    LinkCreate,
    LinkRead,
    LinkSummary,
    LinkUpdate,
)
from itta_api.domains.topology.link.service import LinkService

router = APIRouter(prefix="/api/v1/links", tags=["Link"])
get_service = service_provider("link")


@router.get("/meta", response_model=EntityMeta, summary="Field catalogue for Link")
async def meta() -> EntityMeta:
    return META


@router.get("", response_model=Page[LinkRead], summary="Search Link records")
async def search(
    flt: LinkFilter = Depends(),
    page: int = Query(0, ge=0),
    size: int = Query(20, ge=1, le=200),
    sort: str | None = Query(None, description="field,asc|desc"),
    service: LinkService = Depends(get_service),
    _: Principal = Depends(require_read),
) -> Page[LinkRead]:
    return await service.search(flt, page, size, sort)


@router.get("/summaries", response_model=list[LinkSummary], summary="Pick-list of Link records")
async def summaries(
    status_filter: LifecycleStatus | None = Query(None, alias="status"),
    service: LinkService = Depends(get_service),
    _: Principal = Depends(require_read),
) -> list[LinkSummary]:
    return await service.summaries(status_filter)


@router.get("/stats/status", response_model=dict[str, int], summary="Counts per lifecycle status")
async def status_stats(
    service: LinkService = Depends(get_service), _: Principal = Depends(require_read)
) -> dict[str, int]:
    return await service.stats()


@router.get("/export", response_class=PlainTextResponse, summary="Export Link records as CSV")
async def export(
    flt: LinkFilter = Depends(),
    service: LinkService = Depends(get_service),
    _: Principal = Depends(require_read),
) -> PlainTextResponse:
    body = await service.export_csv(flt)
    headers = {"Content-Disposition": 'attachment; filename="links.csv"'}
    return PlainTextResponse(body, media_type="text/csv", headers=headers)


@router.get("/by-code/{code}", response_model=LinkRead, summary="Get a Link by business code")
async def get_by_code(
    code: str, service: LinkService = Depends(get_service), _: Principal = Depends(require_read)
) -> LinkRead:
    return await service.get_by_code(code)


@router.head("/by-code/{code}", summary="Check whether a Link code exists")
async def exists(
    code: str, service: LinkService = Depends(get_service), _: Principal = Depends(require_read)
) -> Response:
    found = await service.exists(code)
    return Response(status_code=status.HTTP_200_OK if found else status.HTTP_404_NOT_FOUND)


@router.get("/{id}", response_model=LinkRead, summary="Get a Link by id")
async def get(
    id: UUID, service: LinkService = Depends(get_service), _: Principal = Depends(require_read)
) -> LinkRead:
    return await service.get(id)


@router.post("", response_model=LinkRead, status_code=status.HTTP_201_CREATED, summary="Create a Link")
async def create(
    payload: LinkCreate,
    request: Request,
    response: Response,
    service: LinkService = Depends(get_service),
    _: Principal = Depends(require_write),
) -> LinkRead:
    created = await service.create(payload)
    response.headers["Location"] = f"{request.url.path}/{created.id}"
    return created


@router.put("/{id}", response_model=LinkRead, summary="Update a Link")
async def update(
    id: UUID,
    payload: LinkUpdate,
    service: LinkService = Depends(get_service),
    _: Principal = Depends(require_write),
) -> LinkRead:
    return await service.update(id, payload)


@router.patch("/{id}/status", response_model=LinkRead, summary="Change lifecycle status")
async def change_status(
    id: UUID,
    new_status: LifecycleStatus = Query(..., alias="status"),
    service: LinkService = Depends(get_service),
    _: Principal = Depends(require_write),
) -> LinkRead:
    return await service.change_status(id, new_status)


@router.delete("/{id}", status_code=status.HTTP_204_NO_CONTENT, summary="Delete a Link")
async def delete(
    id: UUID, service: LinkService = Depends(get_service), _: Principal = Depends(require_write)
) -> Response:
    await service.delete(id)
    return Response(status_code=status.HTTP_204_NO_CONTENT)


@router.post("/bulk", response_model=LinkBulkResult, summary="Create many Link records")
async def bulk_create(
    payloads: list[LinkCreate],
    service: LinkService = Depends(get_service),
    _: Principal = Depends(require_write),
) -> LinkBulkResult:
    return await service.bulk_create(payloads)


@router.post("/reconcile", response_model=list[ReconciliationItem], summary="Reconcile external Link records")
async def reconcile(
    records: list[dict[str, object]],
    apply: bool = Query(False, description="Apply the plan instead of a dry run"),
    service: LinkService = Depends(get_service),
    _: Principal = Depends(require_write),
) -> list[ReconciliationItem]:
    return await service.reconcile(records, apply)


@router.get("/by-a-interface-id/{a_interface_id}", response_model=Page[LinkRead], summary="List Link records by A Interface Id")
async def list_by_a_interface_id(
    a_interface_id: UUID,
    page: int = Query(0, ge=0),
    size: int = Query(20, ge=1, le=200),
    service: LinkService = Depends(get_service),
) -> Page[LinkRead]:
    return await service.list_by_a_interface_id(a_interface_id, page, size)

@router.get("/by-z-interface-id/{z_interface_id}", response_model=Page[LinkRead], summary="List Link records by Z Interface Id")
async def list_by_z_interface_id(
    z_interface_id: UUID,
    page: int = Query(0, ge=0),
    size: int = Query(20, ge=1, le=200),
    service: LinkService = Depends(get_service),
) -> Page[LinkRead]:
    return await service.list_by_z_interface_id(z_interface_id, page, size)
