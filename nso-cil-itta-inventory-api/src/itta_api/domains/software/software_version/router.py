"""HTTP routes for Software Version."""
from __future__ import annotations

from uuid import UUID

from fastapi import APIRouter, Depends, Query, Request, Response, status
from fastapi.responses import PlainTextResponse

from itta_core.auth import Principal, require_read, require_write
from itta_core.dependencies import service_provider
from itta_core.diffing import ReconciliationItem
from itta_core.models import EntityMeta, LifecycleStatus
from itta_core.pagination import Page
from itta_api.domains.software.software_version.filters import SoftwareVersionFilter
from itta_api.domains.software.software_version.meta import META
from itta_api.domains.software.software_version.schemas import (
    SoftwareVersionBulkResult,
    SoftwareVersionCreate,
    SoftwareVersionRead,
    SoftwareVersionSummary,
    SoftwareVersionUpdate,
)
from itta_api.domains.software.software_version.service import SoftwareVersionService

router = APIRouter(prefix="/api/v1/software-versions", tags=["Software Version"])
get_service = service_provider("software_version")


@router.get("/meta", response_model=EntityMeta, summary="Field catalogue for Software Version")
async def meta() -> EntityMeta:
    return META


@router.get("", response_model=Page[SoftwareVersionRead], summary="Search Software Version records")
async def search(
    flt: SoftwareVersionFilter = Depends(),
    page: int = Query(0, ge=0),
    size: int = Query(20, ge=1, le=200),
    sort: str | None = Query(None, description="field,asc|desc"),
    service: SoftwareVersionService = Depends(get_service),
    _: Principal = Depends(require_read),
) -> Page[SoftwareVersionRead]:
    return await service.search(flt, page, size, sort)


@router.get("/summaries", response_model=list[SoftwareVersionSummary], summary="Pick-list of Software Version records")
async def summaries(
    status_filter: LifecycleStatus | None = Query(None, alias="status"),
    service: SoftwareVersionService = Depends(get_service),
    _: Principal = Depends(require_read),
) -> list[SoftwareVersionSummary]:
    return await service.summaries(status_filter)


@router.get("/stats/status", response_model=dict[str, int], summary="Counts per lifecycle status")
async def status_stats(
    service: SoftwareVersionService = Depends(get_service), _: Principal = Depends(require_read)
) -> dict[str, int]:
    return await service.stats()


@router.get("/export", response_class=PlainTextResponse, summary="Export Software Version records as CSV")
async def export(
    flt: SoftwareVersionFilter = Depends(),
    service: SoftwareVersionService = Depends(get_service),
    _: Principal = Depends(require_read),
) -> PlainTextResponse:
    body = await service.export_csv(flt)
    headers = {"Content-Disposition": 'attachment; filename="software-versions.csv"'}
    return PlainTextResponse(body, media_type="text/csv", headers=headers)


@router.get("/by-code/{code}", response_model=SoftwareVersionRead, summary="Get a Software Version by business code")
async def get_by_code(
    code: str, service: SoftwareVersionService = Depends(get_service), _: Principal = Depends(require_read)
) -> SoftwareVersionRead:
    return await service.get_by_code(code)


@router.head("/by-code/{code}", summary="Check whether a Software Version code exists")
async def exists(
    code: str, service: SoftwareVersionService = Depends(get_service), _: Principal = Depends(require_read)
) -> Response:
    found = await service.exists(code)
    return Response(status_code=status.HTTP_200_OK if found else status.HTTP_404_NOT_FOUND)


@router.get("/{id}", response_model=SoftwareVersionRead, summary="Get a Software Version by id")
async def get(
    id: UUID, service: SoftwareVersionService = Depends(get_service), _: Principal = Depends(require_read)
) -> SoftwareVersionRead:
    return await service.get(id)


@router.post("", response_model=SoftwareVersionRead, status_code=status.HTTP_201_CREATED, summary="Create a Software Version")
async def create(
    payload: SoftwareVersionCreate,
    request: Request,
    response: Response,
    service: SoftwareVersionService = Depends(get_service),
    _: Principal = Depends(require_write),
) -> SoftwareVersionRead:
    created = await service.create(payload)
    response.headers["Location"] = f"{request.url.path}/{created.id}"
    return created


@router.put("/{id}", response_model=SoftwareVersionRead, summary="Update a Software Version")
async def update(
    id: UUID,
    payload: SoftwareVersionUpdate,
    service: SoftwareVersionService = Depends(get_service),
    _: Principal = Depends(require_write),
) -> SoftwareVersionRead:
    return await service.update(id, payload)


@router.patch("/{id}/status", response_model=SoftwareVersionRead, summary="Change lifecycle status")
async def change_status(
    id: UUID,
    new_status: LifecycleStatus = Query(..., alias="status"),
    service: SoftwareVersionService = Depends(get_service),
    _: Principal = Depends(require_write),
) -> SoftwareVersionRead:
    return await service.change_status(id, new_status)


@router.delete("/{id}", status_code=status.HTTP_204_NO_CONTENT, summary="Delete a Software Version")
async def delete(
    id: UUID, service: SoftwareVersionService = Depends(get_service), _: Principal = Depends(require_write)
) -> Response:
    await service.delete(id)
    return Response(status_code=status.HTTP_204_NO_CONTENT)


@router.post("/bulk", response_model=SoftwareVersionBulkResult, summary="Create many Software Version records")
async def bulk_create(
    payloads: list[SoftwareVersionCreate],
    service: SoftwareVersionService = Depends(get_service),
    _: Principal = Depends(require_write),
) -> SoftwareVersionBulkResult:
    return await service.bulk_create(payloads)


@router.post("/reconcile", response_model=list[ReconciliationItem], summary="Reconcile external Software Version records")
async def reconcile(
    records: list[dict[str, object]],
    apply: bool = Query(False, description="Apply the plan instead of a dry run"),
    service: SoftwareVersionService = Depends(get_service),
    _: Principal = Depends(require_write),
) -> list[ReconciliationItem]:
    return await service.reconcile(records, apply)


@router.get("/by-image-id/{image_id}", response_model=Page[SoftwareVersionRead], summary="List Software Version records by Image Id")
async def list_by_image_id(
    image_id: UUID,
    page: int = Query(0, ge=0),
    size: int = Query(20, ge=1, le=200),
    service: SoftwareVersionService = Depends(get_service),
) -> Page[SoftwareVersionRead]:
    return await service.list_by_image_id(image_id, page, size)
