"""HTTP routes for Interface."""
from __future__ import annotations

from uuid import UUID

from fastapi import APIRouter, Depends, Query, Request, Response, status
from fastapi.responses import PlainTextResponse

from itta_core.auth import Principal, require_read, require_write
from itta_core.dependencies import service_provider
from itta_core.diffing import ReconciliationItem
from itta_core.models import EntityMeta, LifecycleStatus
from itta_core.pagination import Page
from itta_api.domains.port.interface.filters import InterfaceFilter
from itta_api.domains.port.interface.meta import META
from itta_api.domains.port.interface.schemas import (
    InterfaceBulkResult,
    InterfaceCreate,
    InterfaceRead,
    InterfaceSummary,
    InterfaceUpdate,
)
from itta_api.domains.port.interface.service import InterfaceService

router = APIRouter(prefix="/api/v1/interfaces", tags=["Interface"])
get_service = service_provider("interface")


@router.get("/meta", response_model=EntityMeta, summary="Field catalogue for Interface")
async def meta() -> EntityMeta:
    return META


@router.get("", response_model=Page[InterfaceRead], summary="Search Interface records")
async def search(
    flt: InterfaceFilter = Depends(),
    page: int = Query(0, ge=0),
    size: int = Query(20, ge=1, le=200),
    sort: str | None = Query(None, description="field,asc|desc"),
    service: InterfaceService = Depends(get_service),
    _: Principal = Depends(require_read),
) -> Page[InterfaceRead]:
    return await service.search(flt, page, size, sort)


@router.get("/summaries", response_model=list[InterfaceSummary], summary="Pick-list of Interface records")
async def summaries(
    status_filter: LifecycleStatus | None = Query(None, alias="status"),
    service: InterfaceService = Depends(get_service),
    _: Principal = Depends(require_read),
) -> list[InterfaceSummary]:
    return await service.summaries(status_filter)


@router.get("/stats/status", response_model=dict[str, int], summary="Counts per lifecycle status")
async def status_stats(
    service: InterfaceService = Depends(get_service), _: Principal = Depends(require_read)
) -> dict[str, int]:
    return await service.stats()


@router.get("/export", response_class=PlainTextResponse, summary="Export Interface records as CSV")
async def export(
    flt: InterfaceFilter = Depends(),
    service: InterfaceService = Depends(get_service),
    _: Principal = Depends(require_read),
) -> PlainTextResponse:
    body = await service.export_csv(flt)
    headers = {"Content-Disposition": 'attachment; filename="interfaces.csv"'}
    return PlainTextResponse(body, media_type="text/csv", headers=headers)


@router.get("/by-code/{code}", response_model=InterfaceRead, summary="Get a Interface by business code")
async def get_by_code(
    code: str, service: InterfaceService = Depends(get_service), _: Principal = Depends(require_read)
) -> InterfaceRead:
    return await service.get_by_code(code)


@router.head("/by-code/{code}", summary="Check whether a Interface code exists")
async def exists(
    code: str, service: InterfaceService = Depends(get_service), _: Principal = Depends(require_read)
) -> Response:
    found = await service.exists(code)
    return Response(status_code=status.HTTP_200_OK if found else status.HTTP_404_NOT_FOUND)


@router.get("/{id}", response_model=InterfaceRead, summary="Get a Interface by id")
async def get(
    id: UUID, service: InterfaceService = Depends(get_service), _: Principal = Depends(require_read)
) -> InterfaceRead:
    return await service.get(id)


@router.post("", response_model=InterfaceRead, status_code=status.HTTP_201_CREATED, summary="Create a Interface")
async def create(
    payload: InterfaceCreate,
    request: Request,
    response: Response,
    service: InterfaceService = Depends(get_service),
    _: Principal = Depends(require_write),
) -> InterfaceRead:
    created = await service.create(payload)
    response.headers["Location"] = f"{request.url.path}/{created.id}"
    return created


@router.put("/{id}", response_model=InterfaceRead, summary="Update a Interface")
async def update(
    id: UUID,
    payload: InterfaceUpdate,
    service: InterfaceService = Depends(get_service),
    _: Principal = Depends(require_write),
) -> InterfaceRead:
    return await service.update(id, payload)


@router.patch("/{id}/status", response_model=InterfaceRead, summary="Change lifecycle status")
async def change_status(
    id: UUID,
    new_status: LifecycleStatus = Query(..., alias="status"),
    service: InterfaceService = Depends(get_service),
    _: Principal = Depends(require_write),
) -> InterfaceRead:
    return await service.change_status(id, new_status)


@router.delete("/{id}", status_code=status.HTTP_204_NO_CONTENT, summary="Delete a Interface")
async def delete(
    id: UUID, service: InterfaceService = Depends(get_service), _: Principal = Depends(require_write)
) -> Response:
    await service.delete(id)
    return Response(status_code=status.HTTP_204_NO_CONTENT)


@router.post("/bulk", response_model=InterfaceBulkResult, summary="Create many Interface records")
async def bulk_create(
    payloads: list[InterfaceCreate],
    service: InterfaceService = Depends(get_service),
    _: Principal = Depends(require_write),
) -> InterfaceBulkResult:
    return await service.bulk_create(payloads)


@router.post("/reconcile", response_model=list[ReconciliationItem], summary="Reconcile external Interface records")
async def reconcile(
    records: list[dict[str, object]],
    apply: bool = Query(False, description="Apply the plan instead of a dry run"),
    service: InterfaceService = Depends(get_service),
    _: Principal = Depends(require_write),
) -> list[ReconciliationItem]:
    return await service.reconcile(records, apply)


@router.get("/by-port-id/{port_id}", response_model=Page[InterfaceRead], summary="List Interface records by Port Id")
async def list_by_port_id(
    port_id: UUID,
    page: int = Query(0, ge=0),
    size: int = Query(20, ge=1, le=200),
    service: InterfaceService = Depends(get_service),
) -> Page[InterfaceRead]:
    return await service.list_by_port_id(port_id, page, size)
