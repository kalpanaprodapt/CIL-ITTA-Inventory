"""HTTP routes for Line Card."""
from __future__ import annotations

from uuid import UUID

from fastapi import APIRouter, Depends, Query, Request, Response, status
from fastapi.responses import PlainTextResponse

from itta_core.auth import Principal, require_read, require_write
from itta_core.dependencies import service_provider
from itta_core.diffing import ReconciliationItem
from itta_core.models import EntityMeta, LifecycleStatus
from itta_core.pagination import Page
from itta_api.domains.device.line_card.filters import LineCardFilter
from itta_api.domains.device.line_card.meta import META
from itta_api.domains.device.line_card.schemas import (
    LineCardBulkResult,
    LineCardCreate,
    LineCardRead,
    LineCardSummary,
    LineCardUpdate,
)
from itta_api.domains.device.line_card.service import LineCardService

router = APIRouter(prefix="/api/v1/line-cards", tags=["Line Card"])
get_service = service_provider("line_card")


@router.get("/meta", response_model=EntityMeta, summary="Field catalogue for Line Card")
async def meta() -> EntityMeta:
    return META


@router.get("", response_model=Page[LineCardRead], summary="Search Line Card records")
async def search(
    flt: LineCardFilter = Depends(),
    page: int = Query(0, ge=0),
    size: int = Query(20, ge=1, le=200),
    sort: str | None = Query(None, description="field,asc|desc"),
    service: LineCardService = Depends(get_service),
    _: Principal = Depends(require_read),
) -> Page[LineCardRead]:
    return await service.search(flt, page, size, sort)


@router.get("/summaries", response_model=list[LineCardSummary], summary="Pick-list of Line Card records")
async def summaries(
    status_filter: LifecycleStatus | None = Query(None, alias="status"),
    service: LineCardService = Depends(get_service),
    _: Principal = Depends(require_read),
) -> list[LineCardSummary]:
    return await service.summaries(status_filter)


@router.get("/stats/status", response_model=dict[str, int], summary="Counts per lifecycle status")
async def status_stats(
    service: LineCardService = Depends(get_service), _: Principal = Depends(require_read)
) -> dict[str, int]:
    return await service.stats()


@router.get("/export", response_class=PlainTextResponse, summary="Export Line Card records as CSV")
async def export(
    flt: LineCardFilter = Depends(),
    service: LineCardService = Depends(get_service),
    _: Principal = Depends(require_read),
) -> PlainTextResponse:
    body = await service.export_csv(flt)
    headers = {"Content-Disposition": 'attachment; filename="line-cards.csv"'}
    return PlainTextResponse(body, media_type="text/csv", headers=headers)


@router.get("/by-code/{code}", response_model=LineCardRead, summary="Get a Line Card by business code")
async def get_by_code(
    code: str, service: LineCardService = Depends(get_service), _: Principal = Depends(require_read)
) -> LineCardRead:
    return await service.get_by_code(code)


@router.head("/by-code/{code}", summary="Check whether a Line Card code exists")
async def exists(
    code: str, service: LineCardService = Depends(get_service), _: Principal = Depends(require_read)
) -> Response:
    found = await service.exists(code)
    return Response(status_code=status.HTTP_200_OK if found else status.HTTP_404_NOT_FOUND)


@router.get("/{id}", response_model=LineCardRead, summary="Get a Line Card by id")
async def get(
    id: UUID, service: LineCardService = Depends(get_service), _: Principal = Depends(require_read)
) -> LineCardRead:
    return await service.get(id)


@router.post("", response_model=LineCardRead, status_code=status.HTTP_201_CREATED, summary="Create a Line Card")
async def create(
    payload: LineCardCreate,
    request: Request,
    response: Response,
    service: LineCardService = Depends(get_service),
    _: Principal = Depends(require_write),
) -> LineCardRead:
    created = await service.create(payload)
    response.headers["Location"] = f"{request.url.path}/{created.id}"
    return created


@router.put("/{id}", response_model=LineCardRead, summary="Update a Line Card")
async def update(
    id: UUID,
    payload: LineCardUpdate,
    service: LineCardService = Depends(get_service),
    _: Principal = Depends(require_write),
) -> LineCardRead:
    return await service.update(id, payload)


@router.patch("/{id}/status", response_model=LineCardRead, summary="Change lifecycle status")
async def change_status(
    id: UUID,
    new_status: LifecycleStatus = Query(..., alias="status"),
    service: LineCardService = Depends(get_service),
    _: Principal = Depends(require_write),
) -> LineCardRead:
    return await service.change_status(id, new_status)


@router.delete("/{id}", status_code=status.HTTP_204_NO_CONTENT, summary="Delete a Line Card")
async def delete(
    id: UUID, service: LineCardService = Depends(get_service), _: Principal = Depends(require_write)
) -> Response:
    await service.delete(id)
    return Response(status_code=status.HTTP_204_NO_CONTENT)


@router.post("/bulk", response_model=LineCardBulkResult, summary="Create many Line Card records")
async def bulk_create(
    payloads: list[LineCardCreate],
    service: LineCardService = Depends(get_service),
    _: Principal = Depends(require_write),
) -> LineCardBulkResult:
    return await service.bulk_create(payloads)


@router.post("/reconcile", response_model=list[ReconciliationItem], summary="Reconcile external Line Card records")
async def reconcile(
    records: list[dict[str, object]],
    apply: bool = Query(False, description="Apply the plan instead of a dry run"),
    service: LineCardService = Depends(get_service),
    _: Principal = Depends(require_write),
) -> list[ReconciliationItem]:
    return await service.reconcile(records, apply)


@router.get("/by-chassis-id/{chassis_id}", response_model=Page[LineCardRead], summary="List Line Card records by Chassis Id")
async def list_by_chassis_id(
    chassis_id: UUID,
    page: int = Query(0, ge=0),
    size: int = Query(20, ge=1, le=200),
    service: LineCardService = Depends(get_service),
) -> Page[LineCardRead]:
    return await service.list_by_chassis_id(chassis_id, page, size)
