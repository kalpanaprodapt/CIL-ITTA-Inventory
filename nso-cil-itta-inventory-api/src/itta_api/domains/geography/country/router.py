"""HTTP routes for Country."""
from __future__ import annotations

from uuid import UUID

from fastapi import APIRouter, Depends, Query, Request, Response, status
from fastapi.responses import PlainTextResponse

from itta_core.auth import Principal, require_read, require_write
from itta_core.dependencies import service_provider
from itta_core.diffing import ReconciliationItem
from itta_core.models import EntityMeta, LifecycleStatus
from itta_core.pagination import Page
from itta_api.domains.geography.country.filters import CountryFilter
from itta_api.domains.geography.country.meta import META
from itta_api.domains.geography.country.schemas import (
    CountryBulkResult,
    CountryCreate,
    CountryRead,
    CountrySummary,
    CountryUpdate,
)
from itta_api.domains.geography.country.service import CountryService

router = APIRouter(prefix="/api/v1/countries", tags=["Country"])
get_service = service_provider("country")


@router.get("/meta", response_model=EntityMeta, summary="Field catalogue for Country")
async def meta() -> EntityMeta:
    return META


@router.get("", response_model=Page[CountryRead], summary="Search Country records")
async def search(
    flt: CountryFilter = Depends(),
    page: int = Query(0, ge=0),
    size: int = Query(20, ge=1, le=200),
    sort: str | None = Query(None, description="field,asc|desc"),
    service: CountryService = Depends(get_service),
    _: Principal = Depends(require_read),
) -> Page[CountryRead]:
    return await service.search(flt, page, size, sort)


@router.get("/summaries", response_model=list[CountrySummary], summary="Pick-list of Country records")
async def summaries(
    status_filter: LifecycleStatus | None = Query(None, alias="status"),
    service: CountryService = Depends(get_service),
    _: Principal = Depends(require_read),
) -> list[CountrySummary]:
    return await service.summaries(status_filter)


@router.get("/stats/status", response_model=dict[str, int], summary="Counts per lifecycle status")
async def status_stats(
    service: CountryService = Depends(get_service), _: Principal = Depends(require_read)
) -> dict[str, int]:
    return await service.stats()


@router.get("/export", response_class=PlainTextResponse, summary="Export Country records as CSV")
async def export(
    flt: CountryFilter = Depends(),
    service: CountryService = Depends(get_service),
    _: Principal = Depends(require_read),
) -> PlainTextResponse:
    body = await service.export_csv(flt)
    headers = {"Content-Disposition": 'attachment; filename="countries.csv"'}
    return PlainTextResponse(body, media_type="text/csv", headers=headers)


@router.get("/by-code/{code}", response_model=CountryRead, summary="Get a Country by business code")
async def get_by_code(
    code: str, service: CountryService = Depends(get_service), _: Principal = Depends(require_read)
) -> CountryRead:
    return await service.get_by_code(code)


@router.head("/by-code/{code}", summary="Check whether a Country code exists")
async def exists(
    code: str, service: CountryService = Depends(get_service), _: Principal = Depends(require_read)
) -> Response:
    found = await service.exists(code)
    return Response(status_code=status.HTTP_200_OK if found else status.HTTP_404_NOT_FOUND)


@router.get("/{id}", response_model=CountryRead, summary="Get a Country by id")
async def get(
    id: UUID, service: CountryService = Depends(get_service), _: Principal = Depends(require_read)
) -> CountryRead:
    return await service.get(id)


@router.post("", response_model=CountryRead, status_code=status.HTTP_201_CREATED, summary="Create a Country")
async def create(
    payload: CountryCreate,
    request: Request,
    response: Response,
    service: CountryService = Depends(get_service),
    _: Principal = Depends(require_write),
) -> CountryRead:
    created = await service.create(payload)
    response.headers["Location"] = f"{request.url.path}/{created.id}"
    return created


@router.put("/{id}", response_model=CountryRead, summary="Update a Country")
async def update(
    id: UUID,
    payload: CountryUpdate,
    service: CountryService = Depends(get_service),
    _: Principal = Depends(require_write),
) -> CountryRead:
    return await service.update(id, payload)


@router.patch("/{id}/status", response_model=CountryRead, summary="Change lifecycle status")
async def change_status(
    id: UUID,
    new_status: LifecycleStatus = Query(..., alias="status"),
    service: CountryService = Depends(get_service),
    _: Principal = Depends(require_write),
) -> CountryRead:
    return await service.change_status(id, new_status)


@router.delete("/{id}", status_code=status.HTTP_204_NO_CONTENT, summary="Delete a Country")
async def delete(
    id: UUID, service: CountryService = Depends(get_service), _: Principal = Depends(require_write)
) -> Response:
    await service.delete(id)
    return Response(status_code=status.HTTP_204_NO_CONTENT)


@router.post("/bulk", response_model=CountryBulkResult, summary="Create many Country records")
async def bulk_create(
    payloads: list[CountryCreate],
    service: CountryService = Depends(get_service),
    _: Principal = Depends(require_write),
) -> CountryBulkResult:
    return await service.bulk_create(payloads)


@router.post("/reconcile", response_model=list[ReconciliationItem], summary="Reconcile external Country records")
async def reconcile(
    records: list[dict[str, object]],
    apply: bool = Query(False, description="Apply the plan instead of a dry run"),
    service: CountryService = Depends(get_service),
    _: Principal = Depends(require_write),
) -> list[ReconciliationItem]:
    return await service.reconcile(records, apply)

