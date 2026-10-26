"""Filter translation tests for Cloud Account."""
from datetime import date, datetime, timezone
from decimal import Decimal
from uuid import UUID

from itta_core.models import LifecycleStatus
from itta_api.domains.virtual.cloud_account.filters import CloudAccountFilter, to_query
from itta_api.domains.virtual.cloud_account.schemas import *  # noqa: F401,F403


def test_empty_filter_has_no_params():
    assert to_query(CloudAccountFilter()) == {}


def test_free_text_is_trimmed():
    assert to_query(CloudAccountFilter(q="  core ")) == {"q": "core"}


def test_status_list_is_comma_joined():
    query = to_query(CloudAccountFilter(status=[LifecycleStatus.ACTIVE, LifecycleStatus.PLANNED]))
    assert query["status"] == "ACTIVE,PLANNED"


def test_code_is_trimmed_into_query():
    assert to_query(CloudAccountFilter(code="  abc ")) == {"code": "abc"}


def test_code_blank_is_omitted():
    assert "code" not in to_query(CloudAccountFilter(code=""))


def test_name_is_trimmed_into_query():
    assert to_query(CloudAccountFilter(name="  abc ")) == {"name": "abc"}


def test_name_blank_is_omitted():
    assert "name" not in to_query(CloudAccountFilter(name=""))


def test_external_ref_is_trimmed_into_query():
    assert to_query(CloudAccountFilter(external_ref="  abc ")) == {"externalRef": "abc"}


def test_external_ref_blank_is_omitted():
    assert "externalRef" not in to_query(CloudAccountFilter(external_ref=""))


def test_provider_values_are_comma_joined():
    members = list(CloudAccountProvider)[:2]
    query = to_query(CloudAccountFilter(provider=members))
    assert query["provider"] == ",".join(m.value for m in members)


def test_account_ref_is_trimmed_into_query():
    assert to_query(CloudAccountFilter(account_ref="  abc ")) == {"accountRef": "abc"}


def test_account_ref_blank_is_omitted():
    assert "accountRef" not in to_query(CloudAccountFilter(account_ref=""))


def test_subscription_name_is_trimmed_into_query():
    assert to_query(CloudAccountFilter(subscription_name="  abc ")) == {"subscriptionName": "abc"}


def test_subscription_name_blank_is_omitted():
    assert "subscriptionName" not in to_query(CloudAccountFilter(subscription_name=""))


def test_monthly_budget_range_is_sent():
    query = to_query(CloudAccountFilter(monthly_budget_min=Decimal("1.5"), monthly_budget_max=Decimal("9.5")))
    assert query["monthlyBudgetMin"] == str(Decimal("1.5"))
    assert query["monthlyBudgetMax"] == str(Decimal("9.5"))


def test_monthly_budget_zero_is_not_dropped():
    assert to_query(CloudAccountFilter(monthly_budget_min=0))["monthlyBudgetMin"] == "0"


def test_environment_is_trimmed_into_query():
    assert to_query(CloudAccountFilter(environment="  abc ")) == {"environment": "abc"}


def test_environment_blank_is_omitted():
    assert "environment" not in to_query(CloudAccountFilter(environment=""))


def test_effective_from_window_is_iso_formatted():
    query = to_query(CloudAccountFilter(effective_from_from=date(2026, 1, 1), effective_from_to=date(2026, 1, 1)))
    assert query["effectiveFromFrom"].startswith("2026-01-01")
    assert query["effectiveFromTo"].startswith("2026-01-01")


def test_effective_to_window_is_iso_formatted():
    query = to_query(CloudAccountFilter(effective_to_from=date(2026, 1, 1), effective_to_to=date(2026, 1, 1)))
    assert query["effectiveToFrom"].startswith("2026-01-01")
    assert query["effectiveToTo"].startswith("2026-01-01")


def test_labels_is_trimmed_into_query():
    assert to_query(CloudAccountFilter(labels="  abc ")) == {"labels": "abc"}


def test_labels_blank_is_omitted():
    assert "labels" not in to_query(CloudAccountFilter(labels=""))


def test_lifecycle_stage_values_are_comma_joined():
    members = list(CloudAccountLifecycleStage)[:2]
    query = to_query(CloudAccountFilter(lifecycle_stage=members))
    assert query["lifecycleStage"] == ",".join(m.value for m in members)

