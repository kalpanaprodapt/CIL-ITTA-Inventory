"""HTTP routes for Circuit."""
from __future__ import annotations

from uuid import UUID

from fastapi import APIRouter, Depends, Query, Request, Response, status
from fastapi.responses import PlainTextResponse

from itta_core.auth import Principal, require_read, require_write
from itta_core.dependencies import service_provider
from itta_core.diffing import ReconciliationItem
from itta_core.models import EntityMeta, LifecycleStatus
from itta_core.pagination import Page
from itta_api.domains.circuit.circuit.filters import CircuitFilter
from itta_api.domains.circuit.circuit.meta import META
from itta_api.domains.circuit.circuit.schemas import (
    CircuitBulkResult,
    CircuitCreate,
    CircuitRead,
    CircuitSummary,
    CircuitUpdate,
)
from itta_api.domains.circuit.circuit.service import CircuitService

router = APIRouter(prefix="/api/v1/circuits", tags=["Circuit"])
get_service = service_provider("circuit")


@router.get("/meta", response_model=EntityMeta, summary="Field catalogue for Circuit")
async def meta() -> EntityMeta:
    return META


@router.get("", response_model=Page[CircuitRead], summary="Search Circuit records")
async def search(
    flt: CircuitFilter = Depends(),
    page: int = Query(0, ge=0),
    size: int = Query(20, ge=1, le=200),
    sort: str | None = Query(None, description="field,asc|desc"),
    service: CircuitService = Depends(get_service),
    _: Principal = Depends(require_read),
) -> Page[CircuitRead]:
    return await service.search(flt, page, size, sort)


@router.get("/summaries", response_model=list[CircuitSummary], summary="Pick-list of Circuit records")
async def summaries(
    status_filter: LifecycleStatus | None = Query(None, alias="status"),
    service: CircuitService = Depends(get_service),
    _: Principal = Depends(require_read),
) -> list[CircuitSummary]:
    return await service.summaries(status_filter)


@router.get("/stats/status", response_model=dict[str, int], summary="Counts per lifecycle status")
async def status_stats(
    service: CircuitService = Depends(get_service), _: Principal = Depends(require_read)
) -> dict[str, int]:
    return await service.stats()


@router.get("/export", response_class=PlainTextResponse, summary="Export Circuit records as CSV")
async def export(
    flt: CircuitFilter = Depends(),
    service: CircuitService = Depends(get_service),
    _: Principal = Depends(require_read),
) -> PlainTextResponse:
    body = await service.export_csv(flt)
    headers = {"Content-Disposition": 'attachment; filename="circuits.csv"'}
    return PlainTextResponse(body, media_type="text/csv", headers=headers)


@router.get("/by-code/{code}", response_model=CircuitRead, summary="Get a Circuit by business code")
async def get_by_code(
    code: str, service: CircuitService = Depends(get_service), _: Principal = Depends(require_read)
) -> CircuitRead:
    return await service.get_by_code(code)


@router.head("/by-code/{code}", summary="Check whether a Circuit code exists")
async def exists(
    code: str, service: CircuitService = Depends(get_service), _: Principal = Depends(require_read)
) -> Response:
    found = await service.exists(code)
    return Response(status_code=status.HTTP_200_OK if found else status.HTTP_404_NOT_FOUND)


@router.get("/{id}", response_model=CircuitRead, summary="Get a Circuit by id")
async def get(
    id: UUID, service: CircuitService = Depends(get_service), _: Principal = Depends(require_read)
) -> CircuitRead:
    return await service.get(id)


@router.post("", response_model=CircuitRead, status_code=status.HTTP_201_CREATED, summary="Create a Circuit")
async def create(
    payload: CircuitCreate,
    request: Request,
    response: Response,
    service: CircuitService = Depends(get_service),
    _: Principal = Depends(require_write),
) -> CircuitRead:
    created = await service.create(payload)
    response.headers["Location"] = f"{request.url.path}/{created.id}"
    return created


@router.put("/{id}", response_model=CircuitRead, summary="Update a Circuit")
async def update(
    id: UUID,
    payload: CircuitUpdate,
    service: CircuitService = Depends(get_service),
    _: Principal = Depends(require_write),
) -> CircuitRead:
    return await service.update(id, payload)


@router.patch("/{id}/status", response_model=CircuitRead, summary="Change lifecycle status")
async def change_status(
    id: UUID,
    new_status: LifecycleStatus = Query(..., alias="status"),
    service: CircuitService = Depends(get_service),
    _: Principal = Depends(require_write),
) -> CircuitRead:
    return await service.change_status(id, new_status)


@router.delete("/{id}", status_code=status.HTTP_204_NO_CONTENT, summary="Delete a Circuit")
async def delete(
    id: UUID, service: CircuitService = Depends(get_service), _: Principal = Depends(require_write)
) -> Response:
    await service.delete(id)
    return Response(status_code=status.HTTP_204_NO_CONTENT)


@router.post("/bulk", response_model=CircuitBulkResult, summary="Create many Circuit records")
async def bulk_create(
    payloads: list[CircuitCreate],
    service: CircuitService = Depends(get_service),
    _: Principal = Depends(require_write),
) -> CircuitBulkResult:
    return await service.bulk_create(payloads)


@router.post("/reconcile", response_model=list[ReconciliationItem], summary="Reconcile external Circuit records")
async def reconcile(
    records: list[dict[str, object]],
    apply: bool = Query(False, description="Apply the plan instead of a dry run"),
    service: CircuitService = Depends(get_service),
    _: Principal = Depends(require_write),
) -> list[ReconciliationItem]:
    return await service.reconcile(records, apply)


@router.get("/by-provider-id/{provider_id}", response_model=Page[CircuitRead], summary="List Circuit records by Provider Id")
async def list_by_provider_id(
    provider_id: UUID,
    page: int = Query(0, ge=0),
    size: int = Query(20, ge=1, le=200),
    service: CircuitService = Depends(get_service),
) -> Page[CircuitRead]:
    return await service.list_by_provider_id(provider_id, page, size)

@router.get("/by-a-site-id/{a_site_id}", response_model=Page[CircuitRead], summary="List Circuit records by A Site Id")
async def list_by_a_site_id(
    a_site_id: UUID,
    page: int = Query(0, ge=0),
    size: int = Query(20, ge=1, le=200),
    service: CircuitService = Depends(get_service),
) -> Page[CircuitRead]:
    return await service.list_by_a_site_id(a_site_id, page, size)

@router.get("/by-z-site-id/{z_site_id}", response_model=Page[CircuitRead], summary="List Circuit records by Z Site Id")
async def list_by_z_site_id(
    z_site_id: UUID,
    page: int = Query(0, ge=0),
    size: int = Query(20, ge=1, le=200),
    service: CircuitService = Depends(get_service),
) -> Page[CircuitRead]:
    return await service.list_by_z_site_id(z_site_id, page, size)
