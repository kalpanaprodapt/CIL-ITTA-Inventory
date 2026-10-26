"""HTTP routes for Sub Interface."""
from __future__ import annotations

from uuid import UUID

from fastapi import APIRouter, Depends, Query, Request, Response, status
from fastapi.responses import PlainTextResponse

from itta_core.auth import Principal, require_read, require_write
from itta_core.dependencies import service_provider
from itta_core.diffing import ReconciliationItem
from itta_core.models import EntityMeta, LifecycleStatus
from itta_core.pagination import Page
from itta_api.domains.port.sub_interface.filters import SubInterfaceFilter
from itta_api.domains.port.sub_interface.meta import META
from itta_api.domains.port.sub_interface.schemas import (
    SubInterfaceBulkResult,
    SubInterfaceCreate,
    SubInterfaceRead,
    SubInterfaceSummary,
    SubInterfaceUpdate,
)
from itta_api.domains.port.sub_interface.service import SubInterfaceService

router = APIRouter(prefix="/api/v1/sub-interfaces", tags=["Sub Interface"])
get_service = service_provider("sub_interface")


@router.get("/meta", response_model=EntityMeta, summary="Field catalogue for Sub Interface")
async def meta() -> EntityMeta:
    return META


@router.get("", response_model=Page[SubInterfaceRead], summary="Search Sub Interface records")
async def search(
    flt: SubInterfaceFilter = Depends(),
    page: int = Query(0, ge=0),
    size: int = Query(20, ge=1, le=200),
    sort: str | None = Query(None, description="field,asc|desc"),
    service: SubInterfaceService = Depends(get_service),
    _: Principal = Depends(require_read),
) -> Page[SubInterfaceRead]:
    return await service.search(flt, page, size, sort)


@router.get("/summaries", response_model=list[SubInterfaceSummary], summary="Pick-list of Sub Interface records")
async def summaries(
    status_filter: LifecycleStatus | None = Query(None, alias="status"),
    service: SubInterfaceService = Depends(get_service),
    _: Principal = Depends(require_read),
) -> list[SubInterfaceSummary]:
    return await service.summaries(status_filter)


@router.get("/stats/status", response_model=dict[str, int], summary="Counts per lifecycle status")
async def status_stats(
    service: SubInterfaceService = Depends(get_service), _: Principal = Depends(require_read)
) -> dict[str, int]:
    return await service.stats()


@router.get("/export", response_class=PlainTextResponse, summary="Export Sub Interface records as CSV")
async def export(
    flt: SubInterfaceFilter = Depends(),
    service: SubInterfaceService = Depends(get_service),
    _: Principal = Depends(require_read),
) -> PlainTextResponse:
    body = await service.export_csv(flt)
    headers = {"Content-Disposition": 'attachment; filename="sub-interfaces.csv"'}
    return PlainTextResponse(body, media_type="text/csv", headers=headers)


@router.get("/by-code/{code}", response_model=SubInterfaceRead, summary="Get a Sub Interface by business code")
async def get_by_code(
    code: str, service: SubInterfaceService = Depends(get_service), _: Principal = Depends(require_read)
) -> SubInterfaceRead:
    return await service.get_by_code(code)


@router.head("/by-code/{code}", summary="Check whether a Sub Interface code exists")
async def exists(
    code: str, service: SubInterfaceService = Depends(get_service), _: Principal = Depends(require_read)
) -> Response:
    found = await service.exists(code)
    return Response(status_code=status.HTTP_200_OK if found else status.HTTP_404_NOT_FOUND)


@router.get("/{id}", response_model=SubInterfaceRead, summary="Get a Sub Interface by id")
async def get(
    id: UUID, service: SubInterfaceService = Depends(get_service), _: Principal = Depends(require_read)
) -> SubInterfaceRead:
    return await service.get(id)


@router.post("", response_model=SubInterfaceRead, status_code=status.HTTP_201_CREATED, summary="Create a Sub Interface")
async def create(
    payload: SubInterfaceCreate,
    request: Request,
    response: Response,
    service: SubInterfaceService = Depends(get_service),
    _: Principal = Depends(require_write),
) -> SubInterfaceRead:
    created = await service.create(payload)
    response.headers["Location"] = f"{request.url.path}/{created.id}"
    return created


@router.put("/{id}", response_model=SubInterfaceRead, summary="Update a Sub Interface")
async def update(
    id: UUID,
    payload: SubInterfaceUpdate,
    service: SubInterfaceService = Depends(get_service),
    _: Principal = Depends(require_write),
) -> SubInterfaceRead:
    return await service.update(id, payload)


@router.patch("/{id}/status", response_model=SubInterfaceRead, summary="Change lifecycle status")
async def change_status(
    id: UUID,
    new_status: LifecycleStatus = Query(..., alias="status"),
    service: SubInterfaceService = Depends(get_service),
    _: Principal = Depends(require_write),
) -> SubInterfaceRead:
    return await service.change_status(id, new_status)


@router.delete("/{id}", status_code=status.HTTP_204_NO_CONTENT, summary="Delete a Sub Interface")
async def delete(
    id: UUID, service: SubInterfaceService = Depends(get_service), _: Principal = Depends(require_write)
) -> Response:
    await service.delete(id)
    return Response(status_code=status.HTTP_204_NO_CONTENT)


@router.post("/bulk", response_model=SubInterfaceBulkResult, summary="Create many Sub Interface records")
async def bulk_create(
    payloads: list[SubInterfaceCreate],
    service: SubInterfaceService = Depends(get_service),
    _: Principal = Depends(require_write),
) -> SubInterfaceBulkResult:
    return await service.bulk_create(payloads)


@router.post("/reconcile", response_model=list[ReconciliationItem], summary="Reconcile external Sub Interface records")
async def reconcile(
    records: list[dict[str, object]],
    apply: bool = Query(False, description="Apply the plan instead of a dry run"),
    service: SubInterfaceService = Depends(get_service),
    _: Principal = Depends(require_write),
) -> list[ReconciliationItem]:
    return await service.reconcile(records, apply)


@router.get("/by-interface-id/{interface_id}", response_model=Page[SubInterfaceRead], summary="List Sub Interface records by Interface Id")
async def list_by_interface_id(
    interface_id: UUID,
    page: int = Query(0, ge=0),
    size: int = Query(20, ge=1, le=200),
    service: SubInterfaceService = Depends(get_service),
) -> Page[SubInterfaceRead]:
    return await service.list_by_interface_id(interface_id, page, size)
