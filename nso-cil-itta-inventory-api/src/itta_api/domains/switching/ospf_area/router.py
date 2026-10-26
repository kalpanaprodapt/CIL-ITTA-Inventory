"""HTTP routes for Ospf Area."""
from __future__ import annotations

from uuid import UUID

from fastapi import APIRouter, Depends, Query, Request, Response, status
from fastapi.responses import PlainTextResponse

from itta_core.auth import Principal, require_read, require_write
from itta_core.dependencies import service_provider
from itta_core.diffing import ReconciliationItem
from itta_core.models import EntityMeta, LifecycleStatus
from itta_core.pagination import Page
from itta_api.domains.switching.ospf_area.filters import OspfAreaFilter
from itta_api.domains.switching.ospf_area.meta import META
from itta_api.domains.switching.ospf_area.schemas import (
    OspfAreaBulkResult,
    OspfAreaCreate,
    OspfAreaRead,
    OspfAreaSummary,
    OspfAreaUpdate,
)
from itta_api.domains.switching.ospf_area.service import OspfAreaService

router = APIRouter(prefix="/api/v1/ospf-areas", tags=["Ospf Area"])
get_service = service_provider("ospf_area")


@router.get("/meta", response_model=EntityMeta, summary="Field catalogue for Ospf Area")
async def meta() -> EntityMeta:
    return META


@router.get("", response_model=Page[OspfAreaRead], summary="Search Ospf Area records")
async def search(
    flt: OspfAreaFilter = Depends(),
    page: int = Query(0, ge=0),
    size: int = Query(20, ge=1, le=200),
    sort: str | None = Query(None, description="field,asc|desc"),
    service: OspfAreaService = Depends(get_service),
    _: Principal = Depends(require_read),
) -> Page[OspfAreaRead]:
    return await service.search(flt, page, size, sort)


@router.get("/summaries", response_model=list[OspfAreaSummary], summary="Pick-list of Ospf Area records")
async def summaries(
    status_filter: LifecycleStatus | None = Query(None, alias="status"),
    service: OspfAreaService = Depends(get_service),
    _: Principal = Depends(require_read),
) -> list[OspfAreaSummary]:
    return await service.summaries(status_filter)


@router.get("/stats/status", response_model=dict[str, int], summary="Counts per lifecycle status")
async def status_stats(
    service: OspfAreaService = Depends(get_service), _: Principal = Depends(require_read)
) -> dict[str, int]:
    return await service.stats()


@router.get("/export", response_class=PlainTextResponse, summary="Export Ospf Area records as CSV")
async def export(
    flt: OspfAreaFilter = Depends(),
    service: OspfAreaService = Depends(get_service),
    _: Principal = Depends(require_read),
) -> PlainTextResponse:
    body = await service.export_csv(flt)
    headers = {"Content-Disposition": 'attachment; filename="ospf-areas.csv"'}
    return PlainTextResponse(body, media_type="text/csv", headers=headers)


@router.get("/by-code/{code}", response_model=OspfAreaRead, summary="Get a Ospf Area by business code")
async def get_by_code(
    code: str, service: OspfAreaService = Depends(get_service), _: Principal = Depends(require_read)
) -> OspfAreaRead:
    return await service.get_by_code(code)


@router.head("/by-code/{code}", summary="Check whether a Ospf Area code exists")
async def exists(
    code: str, service: OspfAreaService = Depends(get_service), _: Principal = Depends(require_read)
) -> Response:
    found = await service.exists(code)
    return Response(status_code=status.HTTP_200_OK if found else status.HTTP_404_NOT_FOUND)


@router.get("/{id}", response_model=OspfAreaRead, summary="Get a Ospf Area by id")
async def get(
    id: UUID, service: OspfAreaService = Depends(get_service), _: Principal = Depends(require_read)
) -> OspfAreaRead:
    return await service.get(id)


@router.post("", response_model=OspfAreaRead, status_code=status.HTTP_201_CREATED, summary="Create a Ospf Area")
async def create(
    payload: OspfAreaCreate,
    request: Request,
    response: Response,
    service: OspfAreaService = Depends(get_service),
    _: Principal = Depends(require_write),
) -> OspfAreaRead:
    created = await service.create(payload)
    response.headers["Location"] = f"{request.url.path}/{created.id}"
    return created


@router.put("/{id}", response_model=OspfAreaRead, summary="Update a Ospf Area")
async def update(
    id: UUID,
    payload: OspfAreaUpdate,
    service: OspfAreaService = Depends(get_service),
    _: Principal = Depends(require_write),
) -> OspfAreaRead:
    return await service.update(id, payload)


@router.patch("/{id}/status", response_model=OspfAreaRead, summary="Change lifecycle status")
async def change_status(
    id: UUID,
    new_status: LifecycleStatus = Query(..., alias="status"),
    service: OspfAreaService = Depends(get_service),
    _: Principal = Depends(require_write),
) -> OspfAreaRead:
    return await service.change_status(id, new_status)


@router.delete("/{id}", status_code=status.HTTP_204_NO_CONTENT, summary="Delete a Ospf Area")
async def delete(
    id: UUID, service: OspfAreaService = Depends(get_service), _: Principal = Depends(require_write)
) -> Response:
    await service.delete(id)
    return Response(status_code=status.HTTP_204_NO_CONTENT)


@router.post("/bulk", response_model=OspfAreaBulkResult, summary="Create many Ospf Area records")
async def bulk_create(
    payloads: list[OspfAreaCreate],
    service: OspfAreaService = Depends(get_service),
    _: Principal = Depends(require_write),
) -> OspfAreaBulkResult:
    return await service.bulk_create(payloads)


@router.post("/reconcile", response_model=list[ReconciliationItem], summary="Reconcile external Ospf Area records")
async def reconcile(
    records: list[dict[str, object]],
    apply: bool = Query(False, description="Apply the plan instead of a dry run"),
    service: OspfAreaService = Depends(get_service),
    _: Principal = Depends(require_write),
) -> list[ReconciliationItem]:
    return await service.reconcile(records, apply)

