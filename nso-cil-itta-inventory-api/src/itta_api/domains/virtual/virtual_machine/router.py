"""HTTP routes for Virtual Machine."""
from __future__ import annotations

from uuid import UUID

from fastapi import APIRouter, Depends, Query, Request, Response, status
from fastapi.responses import PlainTextResponse

from itta_core.auth import Principal, require_read, require_write
from itta_core.dependencies import service_provider
from itta_core.diffing import ReconciliationItem
from itta_core.models import EntityMeta, LifecycleStatus
from itta_core.pagination import Page
from itta_api.domains.virtual.virtual_machine.filters import VirtualMachineFilter
from itta_api.domains.virtual.virtual_machine.meta import META
from itta_api.domains.virtual.virtual_machine.schemas import (
    VirtualMachineBulkResult,
    VirtualMachineCreate,
    VirtualMachineRead,
    VirtualMachineSummary,
    VirtualMachineUpdate,
)
from itta_api.domains.virtual.virtual_machine.service import VirtualMachineService

router = APIRouter(prefix="/api/v1/virtual-machines", tags=["Virtual Machine"])
get_service = service_provider("virtual_machine")


@router.get("/meta", response_model=EntityMeta, summary="Field catalogue for Virtual Machine")
async def meta() -> EntityMeta:
    return META


@router.get("", response_model=Page[VirtualMachineRead], summary="Search Virtual Machine records")
async def search(
    flt: VirtualMachineFilter = Depends(),
    page: int = Query(0, ge=0),
    size: int = Query(20, ge=1, le=200),
    sort: str | None = Query(None, description="field,asc|desc"),
    service: VirtualMachineService = Depends(get_service),
    _: Principal = Depends(require_read),
) -> Page[VirtualMachineRead]:
    return await service.search(flt, page, size, sort)


@router.get("/summaries", response_model=list[VirtualMachineSummary], summary="Pick-list of Virtual Machine records")
async def summaries(
    status_filter: LifecycleStatus | None = Query(None, alias="status"),
    service: VirtualMachineService = Depends(get_service),
    _: Principal = Depends(require_read),
) -> list[VirtualMachineSummary]:
    return await service.summaries(status_filter)


@router.get("/stats/status", response_model=dict[str, int], summary="Counts per lifecycle status")
async def status_stats(
    service: VirtualMachineService = Depends(get_service), _: Principal = Depends(require_read)
) -> dict[str, int]:
    return await service.stats()


@router.get("/export", response_class=PlainTextResponse, summary="Export Virtual Machine records as CSV")
async def export(
    flt: VirtualMachineFilter = Depends(),
    service: VirtualMachineService = Depends(get_service),
    _: Principal = Depends(require_read),
) -> PlainTextResponse:
    body = await service.export_csv(flt)
    headers = {"Content-Disposition": 'attachment; filename="virtual-machines.csv"'}
    return PlainTextResponse(body, media_type="text/csv", headers=headers)


@router.get("/by-code/{code}", response_model=VirtualMachineRead, summary="Get a Virtual Machine by business code")
async def get_by_code(
    code: str, service: VirtualMachineService = Depends(get_service), _: Principal = Depends(require_read)
) -> VirtualMachineRead:
    return await service.get_by_code(code)


@router.head("/by-code/{code}", summary="Check whether a Virtual Machine code exists")
async def exists(
    code: str, service: VirtualMachineService = Depends(get_service), _: Principal = Depends(require_read)
) -> Response:
    found = await service.exists(code)
    return Response(status_code=status.HTTP_200_OK if found else status.HTTP_404_NOT_FOUND)


@router.get("/{id}", response_model=VirtualMachineRead, summary="Get a Virtual Machine by id")
async def get(
    id: UUID, service: VirtualMachineService = Depends(get_service), _: Principal = Depends(require_read)
) -> VirtualMachineRead:
    return await service.get(id)


@router.post("", response_model=VirtualMachineRead, status_code=status.HTTP_201_CREATED, summary="Create a Virtual Machine")
async def create(
    payload: VirtualMachineCreate,
    request: Request,
    response: Response,
    service: VirtualMachineService = Depends(get_service),
    _: Principal = Depends(require_write),
) -> VirtualMachineRead:
    created = await service.create(payload)
    response.headers["Location"] = f"{request.url.path}/{created.id}"
    return created


@router.put("/{id}", response_model=VirtualMachineRead, summary="Update a Virtual Machine")
async def update(
    id: UUID,
    payload: VirtualMachineUpdate,
    service: VirtualMachineService = Depends(get_service),
    _: Principal = Depends(require_write),
) -> VirtualMachineRead:
    return await service.update(id, payload)


@router.patch("/{id}/status", response_model=VirtualMachineRead, summary="Change lifecycle status")
async def change_status(
    id: UUID,
    new_status: LifecycleStatus = Query(..., alias="status"),
    service: VirtualMachineService = Depends(get_service),
    _: Principal = Depends(require_write),
) -> VirtualMachineRead:
    return await service.change_status(id, new_status)


@router.delete("/{id}", status_code=status.HTTP_204_NO_CONTENT, summary="Delete a Virtual Machine")
async def delete(
    id: UUID, service: VirtualMachineService = Depends(get_service), _: Principal = Depends(require_write)
) -> Response:
    await service.delete(id)
    return Response(status_code=status.HTTP_204_NO_CONTENT)


@router.post("/bulk", response_model=VirtualMachineBulkResult, summary="Create many Virtual Machine records")
async def bulk_create(
    payloads: list[VirtualMachineCreate],
    service: VirtualMachineService = Depends(get_service),
    _: Principal = Depends(require_write),
) -> VirtualMachineBulkResult:
    return await service.bulk_create(payloads)


@router.post("/reconcile", response_model=list[ReconciliationItem], summary="Reconcile external Virtual Machine records")
async def reconcile(
    records: list[dict[str, object]],
    apply: bool = Query(False, description="Apply the plan instead of a dry run"),
    service: VirtualMachineService = Depends(get_service),
    _: Principal = Depends(require_write),
) -> list[ReconciliationItem]:
    return await service.reconcile(records, apply)


@router.get("/by-hypervisor-id/{hypervisor_id}", response_model=Page[VirtualMachineRead], summary="List Virtual Machine records by Hypervisor Id")
async def list_by_hypervisor_id(
    hypervisor_id: UUID,
    page: int = Query(0, ge=0),
    size: int = Query(20, ge=1, le=200),
    service: VirtualMachineService = Depends(get_service),
) -> Page[VirtualMachineRead]:
    return await service.list_by_hypervisor_id(hypervisor_id, page, size)
