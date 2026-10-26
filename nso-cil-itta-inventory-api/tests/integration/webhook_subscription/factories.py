"""Test data builders for Webhook Subscription."""
from __future__ import annotations

from datetime import date, datetime, timezone
from decimal import Decimal
from uuid import UUID

from itta_core.models import LifecycleStatus
from itta_api.domains.integration.webhook_subscription.schemas import *  # noqa: F401,F403

ID = UUID("11111111-1111-1111-1111-111111111111")
TENANT = "tenant-a"


def create_payload(**overrides) -> WebhookSubscriptionCreate:
    data = dict(
        code="code-1",
        name="name-1",
        description="description-1",
        status=LifecycleStatus.PLANNED,
        external_ref="external_ref-1",
        callback_url="callback_url-1",
        event_types="event_types-1",
        secret_ref="secret_ref-1",
        active=True,
        retry_limit=11,
        labels="labels-1",
        lifecycle_stage=WebhookSubscriptionLifecycleStage.PLAN,
        source_system="source_system-1",
        region="region-1",
        owner="owner-1",
    )
    data.update(code="WEBHOO-0001", name="Webhook Subscription one", status=LifecycleStatus.ACTIVE)
    data.update(overrides)
    return WebhookSubscriptionCreate(**data)


def read_model(**overrides) -> WebhookSubscriptionRead:
    base = create_payload().model_dump()
    base.update(id=ID, tenant_id=TENANT, version=0)
    base.update(overrides)
    return WebhookSubscriptionRead(**base)


def raw_record(**overrides) -> dict[str, object]:
    record = create_payload().model_dump(mode="json", by_alias=True)
    record.update(overrides)
    return record


def upstream_json(**overrides) -> dict[str, object]:
    record = read_model().model_dump(mode="json", by_alias=True)
    record.update(overrides)
    return record
