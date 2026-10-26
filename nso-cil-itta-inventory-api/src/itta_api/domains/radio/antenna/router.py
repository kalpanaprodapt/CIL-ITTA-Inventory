"""HTTP routes for Antenna."""
from __future__ import annotations

from uuid import UUID

from fastapi import APIRouter, Depends, Query, Request, Response, status
from fastapi.responses import PlainTextResponse

from itta_core.auth import Principal, require_read, require_write
from itta_core.dependencies import service_provider
from itta_core.diffing import ReconciliationItem
from itta_core.models import EntityMeta, LifecycleStatus
from itta_core.pagination import Page
from itta_api.domains.radio.antenna.filters import AntennaFilter
from itta_api.domains.radio.antenna.meta import META
from itta_api.domains.radio.antenna.schemas import (
    AntennaBulkResult,
    AntennaCreate,
    AntennaRead,
    AntennaSummary,
    AntennaUpdate,
)
from itta_api.domains.radio.antenna.service import AntennaService

router = APIRouter(prefix="/api/v1/antennas", tags=["Antenna"])
get_service = service_provider("antenna")


@router.get("/meta", response_model=EntityMeta, summary="Field catalogue for Antenna")
async def meta() -> EntityMeta:
    return META


@router.get("", response_model=Page[AntennaRead], summary="Search Antenna records")
async def search(
    flt: AntennaFilter = Depends(),
    page: int = Query(0, ge=0),
    size: int = Query(20, ge=1, le=200),
    sort: str | None = Query(None, description="field,asc|desc"),
    service: AntennaService = Depends(get_service),
    _: Principal = Depends(require_read),
) -> Page[AntennaRead]:
    return await service.search(flt, page, size, sort)


@router.get("/summaries", response_model=list[AntennaSummary], summary="Pick-list of Antenna records")
async def summaries(
    status_filter: LifecycleStatus | None = Query(None, alias="status"),
    service: AntennaService = Depends(get_service),
    _: Principal = Depends(require_read),
) -> list[AntennaSummary]:
    return await service.summaries(status_filter)


@router.get("/stats/status", response_model=dict[str, int], summary="Counts per lifecycle status")
async def status_stats(
    service: AntennaService = Depends(get_service), _: Principal = Depends(require_read)
) -> dict[str, int]:
    return await service.stats()


@router.get("/export", response_class=PlainTextResponse, summary="Export Antenna records as CSV")
async def export(
    flt: AntennaFilter = Depends(),
    service: AntennaService = Depends(get_service),
    _: Principal = Depends(require_read),
) -> PlainTextResponse:
    body = await service.export_csv(flt)
    headers = {"Content-Disposition": 'attachment; filename="antennas.csv"'}
    return PlainTextResponse(body, media_type="text/csv", headers=headers)


@router.get("/by-code/{code}", response_model=AntennaRead, summary="Get a Antenna by business code")
async def get_by_code(
    code: str, service: AntennaService = Depends(get_service), _: Principal = Depends(require_read)
) -> AntennaRead:
    return await service.get_by_code(code)


@router.head("/by-code/{code}", summary="Check whether a Antenna code exists")
async def exists(
    code: str, service: AntennaService = Depends(get_service), _: Principal = Depends(require_read)
) -> Response:
    found = await service.exists(code)
    return Response(status_code=status.HTTP_200_OK if found else status.HTTP_404_NOT_FOUND)


@router.get("/{id}", response_model=AntennaRead, summary="Get a Antenna by id")
async def get(
    id: UUID, service: AntennaService = Depends(get_service), _: Principal = Depends(require_read)
) -> AntennaRead:
    return await service.get(id)


@router.post("", response_model=AntennaRead, status_code=status.HTTP_201_CREATED, summary="Create a Antenna")
async def create(
    payload: AntennaCreate,
    request: Request,
    response: Response,
    service: AntennaService = Depends(get_service),
    _: Principal = Depends(require_write),
) -> AntennaRead:
    created = await service.create(payload)
    response.headers["Location"] = f"{request.url.path}/{created.id}"
    return created


@router.put("/{id}", response_model=AntennaRead, summary="Update a Antenna")
async def update(
    id: UUID,
    payload: AntennaUpdate,
    service: AntennaService = Depends(get_service),
    _: Principal = Depends(require_write),
) -> AntennaRead:
    return await service.update(id, payload)


@router.patch("/{id}/status", response_model=AntennaRead, summary="Change lifecycle status")
async def change_status(
    id: UUID,
    new_status: LifecycleStatus = Query(..., alias="status"),
    service: AntennaService = Depends(get_service),
    _: Principal = Depends(require_write),
) -> AntennaRead:
    return await service.change_status(id, new_status)


@router.delete("/{id}", status_code=status.HTTP_204_NO_CONTENT, summary="Delete a Antenna")
async def delete(
    id: UUID, service: AntennaService = Depends(get_service), _: Principal = Depends(require_write)
) -> Response:
    await service.delete(id)
    return Response(status_code=status.HTTP_204_NO_CONTENT)


@router.post("/bulk", response_model=AntennaBulkResult, summary="Create many Antenna records")
async def bulk_create(
    payloads: list[AntennaCreate],
    service: AntennaService = Depends(get_service),
    _: Principal = Depends(require_write),
) -> AntennaBulkResult:
    return await service.bulk_create(payloads)


@router.post("/reconcile", response_model=list[ReconciliationItem], summary="Reconcile external Antenna records")
async def reconcile(
    records: list[dict[str, object]],
    apply: bool = Query(False, description="Apply the plan instead of a dry run"),
    service: AntennaService = Depends(get_service),
    _: Principal = Depends(require_write),
) -> list[ReconciliationItem]:
    return await service.reconcile(records, apply)


@router.get("/by-sector-id/{sector_id}", response_model=Page[AntennaRead], summary="List Antenna records by Sector Id")
async def list_by_sector_id(
    sector_id: UUID,
    page: int = Query(0, ge=0),
    size: int = Query(20, ge=1, le=200),
    service: AntennaService = Depends(get_service),
) -> Page[AntennaRead]:
    return await service.list_by_sector_id(sector_id, page, size)
