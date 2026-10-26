"""HTTP routes for Amplifier."""
from __future__ import annotations

from uuid import UUID

from fastapi import APIRouter, Depends, Query, Request, Response, status
from fastapi.responses import PlainTextResponse

from itta_core.auth import Principal, require_read, require_write
from itta_core.dependencies import service_provider
from itta_core.diffing import ReconciliationItem
from itta_core.models import EntityMeta, LifecycleStatus
from itta_core.pagination import Page
from itta_api.domains.optical.amplifier.filters import AmplifierFilter
from itta_api.domains.optical.amplifier.meta import META
from itta_api.domains.optical.amplifier.schemas import (
    AmplifierBulkResult,
    AmplifierCreate,
    AmplifierRead,
    AmplifierSummary,
    AmplifierUpdate,
)
from itta_api.domains.optical.amplifier.service import AmplifierService

router = APIRouter(prefix="/api/v1/amplifiers", tags=["Amplifier"])
get_service = service_provider("amplifier")


@router.get("/meta", response_model=EntityMeta, summary="Field catalogue for Amplifier")
async def meta() -> EntityMeta:
    return META


@router.get("", response_model=Page[AmplifierRead], summary="Search Amplifier records")
async def search(
    flt: AmplifierFilter = Depends(),
    page: int = Query(0, ge=0),
    size: int = Query(20, ge=1, le=200),
    sort: str | None = Query(None, description="field,asc|desc"),
    service: AmplifierService = Depends(get_service),
    _: Principal = Depends(require_read),
) -> Page[AmplifierRead]:
    return await service.search(flt, page, size, sort)


@router.get("/summaries", response_model=list[AmplifierSummary], summary="Pick-list of Amplifier records")
async def summaries(
    status_filter: LifecycleStatus | None = Query(None, alias="status"),
    service: AmplifierService = Depends(get_service),
    _: Principal = Depends(require_read),
) -> list[AmplifierSummary]:
    return await service.summaries(status_filter)


@router.get("/stats/status", response_model=dict[str, int], summary="Counts per lifecycle status")
async def status_stats(
    service: AmplifierService = Depends(get_service), _: Principal = Depends(require_read)
) -> dict[str, int]:
    return await service.stats()


@router.get("/export", response_class=PlainTextResponse, summary="Export Amplifier records as CSV")
async def export(
    flt: AmplifierFilter = Depends(),
    service: AmplifierService = Depends(get_service),
    _: Principal = Depends(require_read),
) -> PlainTextResponse:
    body = await service.export_csv(flt)
    headers = {"Content-Disposition": 'attachment; filename="amplifiers.csv"'}
    return PlainTextResponse(body, media_type="text/csv", headers=headers)


@router.get("/by-code/{code}", response_model=AmplifierRead, summary="Get a Amplifier by business code")
async def get_by_code(
    code: str, service: AmplifierService = Depends(get_service), _: Principal = Depends(require_read)
) -> AmplifierRead:
    return await service.get_by_code(code)


@router.head("/by-code/{code}", summary="Check whether a Amplifier code exists")
async def exists(
    code: str, service: AmplifierService = Depends(get_service), _: Principal = Depends(require_read)
) -> Response:
    found = await service.exists(code)
    return Response(status_code=status.HTTP_200_OK if found else status.HTTP_404_NOT_FOUND)


@router.get("/{id}", response_model=AmplifierRead, summary="Get a Amplifier by id")
async def get(
    id: UUID, service: AmplifierService = Depends(get_service), _: Principal = Depends(require_read)
) -> AmplifierRead:
    return await service.get(id)


@router.post("", response_model=AmplifierRead, status_code=status.HTTP_201_CREATED, summary="Create a Amplifier")
async def create(
    payload: AmplifierCreate,
    request: Request,
    response: Response,
    service: AmplifierService = Depends(get_service),
    _: Principal = Depends(require_write),
) -> AmplifierRead:
    created = await service.create(payload)
    response.headers["Location"] = f"{request.url.path}/{created.id}"
    return created


@router.put("/{id}", response_model=AmplifierRead, summary="Update a Amplifier")
async def update(
    id: UUID,
    payload: AmplifierUpdate,
    service: AmplifierService = Depends(get_service),
    _: Principal = Depends(require_write),
) -> AmplifierRead:
    return await service.update(id, payload)


@router.patch("/{id}/status", response_model=AmplifierRead, summary="Change lifecycle status")
async def change_status(
    id: UUID,
    new_status: LifecycleStatus = Query(..., alias="status"),
    service: AmplifierService = Depends(get_service),
    _: Principal = Depends(require_write),
) -> AmplifierRead:
    return await service.change_status(id, new_status)


@router.delete("/{id}", status_code=status.HTTP_204_NO_CONTENT, summary="Delete a Amplifier")
async def delete(
    id: UUID, service: AmplifierService = Depends(get_service), _: Principal = Depends(require_write)
) -> Response:
    await service.delete(id)
    return Response(status_code=status.HTTP_204_NO_CONTENT)


@router.post("/bulk", response_model=AmplifierBulkResult, summary="Create many Amplifier records")
async def bulk_create(
    payloads: list[AmplifierCreate],
    service: AmplifierService = Depends(get_service),
    _: Principal = Depends(require_write),
) -> AmplifierBulkResult:
    return await service.bulk_create(payloads)


@router.post("/reconcile", response_model=list[ReconciliationItem], summary="Reconcile external Amplifier records")
async def reconcile(
    records: list[dict[str, object]],
    apply: bool = Query(False, description="Apply the plan instead of a dry run"),
    service: AmplifierService = Depends(get_service),
    _: Principal = Depends(require_write),
) -> list[ReconciliationItem]:
    return await service.reconcile(records, apply)


@router.get("/by-system-id/{system_id}", response_model=Page[AmplifierRead], summary="List Amplifier records by System Id")
async def list_by_system_id(
    system_id: UUID,
    page: int = Query(0, ge=0),
    size: int = Query(20, ge=1, le=200),
    service: AmplifierService = Depends(get_service),
) -> Page[AmplifierRead]:
    return await service.list_by_system_id(system_id, page, size)
