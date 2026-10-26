"""Filter translation tests for Webhook Subscription."""
from datetime import date, datetime, timezone
from decimal import Decimal
from uuid import UUID

from itta_core.models import LifecycleStatus
from itta_api.domains.integration.webhook_subscription.filters import WebhookSubscriptionFilter, to_query
from itta_api.domains.integration.webhook_subscription.schemas import *  # noqa: F401,F403


def test_empty_filter_has_no_params():
    assert to_query(WebhookSubscriptionFilter()) == {}


def test_free_text_is_trimmed():
    assert to_query(WebhookSubscriptionFilter(q="  core ")) == {"q": "core"}


def test_status_list_is_comma_joined():
    query = to_query(WebhookSubscriptionFilter(status=[LifecycleStatus.ACTIVE, LifecycleStatus.PLANNED]))
    assert query["status"] == "ACTIVE,PLANNED"


def test_code_is_trimmed_into_query():
    assert to_query(WebhookSubscriptionFilter(code="  abc ")) == {"code": "abc"}


def test_code_blank_is_omitted():
    assert "code" not in to_query(WebhookSubscriptionFilter(code=""))


def test_name_is_trimmed_into_query():
    assert to_query(WebhookSubscriptionFilter(name="  abc ")) == {"name": "abc"}


def test_name_blank_is_omitted():
    assert "name" not in to_query(WebhookSubscriptionFilter(name=""))


def test_external_ref_is_trimmed_into_query():
    assert to_query(WebhookSubscriptionFilter(external_ref="  abc ")) == {"externalRef": "abc"}


def test_external_ref_blank_is_omitted():
    assert "externalRef" not in to_query(WebhookSubscriptionFilter(external_ref=""))


def test_callback_url_is_trimmed_into_query():
    assert to_query(WebhookSubscriptionFilter(callback_url="  abc ")) == {"callbackUrl": "abc"}


def test_callback_url_blank_is_omitted():
    assert "callbackUrl" not in to_query(WebhookSubscriptionFilter(callback_url=""))


def test_event_types_is_trimmed_into_query():
    assert to_query(WebhookSubscriptionFilter(event_types="  abc ")) == {"eventTypes": "abc"}


def test_event_types_blank_is_omitted():
    assert "eventTypes" not in to_query(WebhookSubscriptionFilter(event_types=""))


def test_secret_ref_is_trimmed_into_query():
    assert to_query(WebhookSubscriptionFilter(secret_ref="  abc ")) == {"secretRef": "abc"}


def test_secret_ref_blank_is_omitted():
    assert "secretRef" not in to_query(WebhookSubscriptionFilter(secret_ref=""))


def test_active_is_lowercase_boolean():
    assert to_query(WebhookSubscriptionFilter(active=True))["active"] == "true"
    assert to_query(WebhookSubscriptionFilter(active=False))["active"] == "false"


def test_retry_limit_range_is_sent():
    query = to_query(WebhookSubscriptionFilter(retry_limit_min=1, retry_limit_max=9))
    assert query["retryLimitMin"] == str(1)
    assert query["retryLimitMax"] == str(9)


def test_retry_limit_zero_is_not_dropped():
    assert to_query(WebhookSubscriptionFilter(retry_limit_min=0))["retryLimitMin"] == "0"


def test_labels_is_trimmed_into_query():
    assert to_query(WebhookSubscriptionFilter(labels="  abc ")) == {"labels": "abc"}


def test_labels_blank_is_omitted():
    assert "labels" not in to_query(WebhookSubscriptionFilter(labels=""))


def test_lifecycle_stage_values_are_comma_joined():
    members = list(WebhookSubscriptionLifecycleStage)[:2]
    query = to_query(WebhookSubscriptionFilter(lifecycle_stage=members))
    assert query["lifecycleStage"] == ",".join(m.value for m in members)


def test_source_system_is_trimmed_into_query():
    assert to_query(WebhookSubscriptionFilter(source_system="  abc ")) == {"sourceSystem": "abc"}


def test_source_system_blank_is_omitted():
    assert "sourceSystem" not in to_query(WebhookSubscriptionFilter(source_system=""))


def test_region_is_trimmed_into_query():
    assert to_query(WebhookSubscriptionFilter(region="  abc ")) == {"region": "abc"}


def test_region_blank_is_omitted():
    assert "region" not in to_query(WebhookSubscriptionFilter(region=""))


def test_owner_is_trimmed_into_query():
    assert to_query(WebhookSubscriptionFilter(owner="  abc ")) == {"owner": "abc"}


def test_owner_blank_is_omitted():
    assert "owner" not in to_query(WebhookSubscriptionFilter(owner=""))

