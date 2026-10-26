"""HTTP routes for Webhook Subscription."""
from __future__ import annotations

from uuid import UUID

from fastapi import APIRouter, Depends, Query, Request, Response, status
from fastapi.responses import PlainTextResponse

from itta_core.auth import Principal, require_read, require_write
from itta_core.dependencies import service_provider
from itta_core.diffing import ReconciliationItem
from itta_core.models import EntityMeta, LifecycleStatus
from itta_core.pagination import Page
from itta_api.domains.integration.webhook_subscription.filters import WebhookSubscriptionFilter
from itta_api.domains.integration.webhook_subscription.meta import META
from itta_api.domains.integration.webhook_subscription.schemas import (
    WebhookSubscriptionBulkResult,
    WebhookSubscriptionCreate,
    WebhookSubscriptionRead,
    WebhookSubscriptionSummary,
    WebhookSubscriptionUpdate,
)
from itta_api.domains.integration.webhook_subscription.service import WebhookSubscriptionService

router = APIRouter(prefix="/api/v1/webhook-subscriptions", tags=["Webhook Subscription"])
get_service = service_provider("webhook_subscription")


@router.get("/meta", response_model=EntityMeta, summary="Field catalogue for Webhook Subscription")
async def meta() -> EntityMeta:
    return META


@router.get("", response_model=Page[WebhookSubscriptionRead], summary="Search Webhook Subscription records")
async def search(
    flt: WebhookSubscriptionFilter = Depends(),
    page: int = Query(0, ge=0),
    size: int = Query(20, ge=1, le=200),
    sort: str | None = Query(None, description="field,asc|desc"),
    service: WebhookSubscriptionService = Depends(get_service),
    _: Principal = Depends(require_read),
) -> Page[WebhookSubscriptionRead]:
    return await service.search(flt, page, size, sort)


@router.get("/summaries", response_model=list[WebhookSubscriptionSummary], summary="Pick-list of Webhook Subscription records")
async def summaries(
    status_filter: LifecycleStatus | None = Query(None, alias="status"),
    service: WebhookSubscriptionService = Depends(get_service),
    _: Principal = Depends(require_read),
) -> list[WebhookSubscriptionSummary]:
    return await service.summaries(status_filter)


@router.get("/stats/status", response_model=dict[str, int], summary="Counts per lifecycle status")
async def status_stats(
    service: WebhookSubscriptionService = Depends(get_service), _: Principal = Depends(require_read)
) -> dict[str, int]:
    return await service.stats()


@router.get("/export", response_class=PlainTextResponse, summary="Export Webhook Subscription records as CSV")
async def export(
    flt: WebhookSubscriptionFilter = Depends(),
    service: WebhookSubscriptionService = Depends(get_service),
    _: Principal = Depends(require_read),
) -> PlainTextResponse:
    body = await service.export_csv(flt)
    headers = {"Content-Disposition": 'attachment; filename="webhook-subscriptions.csv"'}
    return PlainTextResponse(body, media_type="text/csv", headers=headers)


@router.get("/by-code/{code}", response_model=WebhookSubscriptionRead, summary="Get a Webhook Subscription by business code")
async def get_by_code(
    code: str, service: WebhookSubscriptionService = Depends(get_service), _: Principal = Depends(require_read)
) -> WebhookSubscriptionRead:
    return await service.get_by_code(code)


@router.head("/by-code/{code}", summary="Check whether a Webhook Subscription code exists")
async def exists(
    code: str, service: WebhookSubscriptionService = Depends(get_service), _: Principal = Depends(require_read)
) -> Response:
    found = await service.exists(code)
    return Response(status_code=status.HTTP_200_OK if found else status.HTTP_404_NOT_FOUND)


@router.get("/{id}", response_model=WebhookSubscriptionRead, summary="Get a Webhook Subscription by id")
async def get(
    id: UUID, service: WebhookSubscriptionService = Depends(get_service), _: Principal = Depends(require_read)
) -> WebhookSubscriptionRead:
    return await service.get(id)


@router.post("", response_model=WebhookSubscriptionRead, status_code=status.HTTP_201_CREATED, summary="Create a Webhook Subscription")
async def create(
    payload: WebhookSubscriptionCreate,
    request: Request,
    response: Response,
    service: WebhookSubscriptionService = Depends(get_service),
    _: Principal = Depends(require_write),
) -> WebhookSubscriptionRead:
    created = await service.create(payload)
    response.headers["Location"] = f"{request.url.path}/{created.id}"
    return created


@router.put("/{id}", response_model=WebhookSubscriptionRead, summary="Update a Webhook Subscription")
async def update(
    id: UUID,
    payload: WebhookSubscriptionUpdate,
    service: WebhookSubscriptionService = Depends(get_service),
    _: Principal = Depends(require_write),
) -> WebhookSubscriptionRead:
    return await service.update(id, payload)


@router.patch("/{id}/status", response_model=WebhookSubscriptionRead, summary="Change lifecycle status")
async def change_status(
    id: UUID,
    new_status: LifecycleStatus = Query(..., alias="status"),
    service: WebhookSubscriptionService = Depends(get_service),
    _: Principal = Depends(require_write),
) -> WebhookSubscriptionRead:
    return await service.change_status(id, new_status)


@router.delete("/{id}", status_code=status.HTTP_204_NO_CONTENT, summary="Delete a Webhook Subscription")
async def delete(
    id: UUID, service: WebhookSubscriptionService = Depends(get_service), _: Principal = Depends(require_write)
) -> Response:
    await service.delete(id)
    return Response(status_code=status.HTTP_204_NO_CONTENT)


@router.post("/bulk", response_model=WebhookSubscriptionBulkResult, summary="Create many Webhook Subscription records")
async def bulk_create(
    payloads: list[WebhookSubscriptionCreate],
    service: WebhookSubscriptionService = Depends(get_service),
    _: Principal = Depends(require_write),
) -> WebhookSubscriptionBulkResult:
    return await service.bulk_create(payloads)


@router.post("/reconcile", response_model=list[ReconciliationItem], summary="Reconcile external Webhook Subscription records")
async def reconcile(
    records: list[dict[str, object]],
    apply: bool = Query(False, description="Apply the plan instead of a dry run"),
    service: WebhookSubscriptionService = Depends(get_service),
    _: Principal = Depends(require_write),
) -> list[ReconciliationItem]:
    return await service.reconcile(records, apply)

