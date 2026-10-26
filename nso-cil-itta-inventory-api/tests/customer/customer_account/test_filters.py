"""Filter translation tests for Customer Account."""
from datetime import date, datetime, timezone
from decimal import Decimal
from uuid import UUID

from itta_core.models import LifecycleStatus
from itta_api.domains.customer.customer_account.filters import CustomerAccountFilter, to_query
from itta_api.domains.customer.customer_account.schemas import *  # noqa: F401,F403


def test_empty_filter_has_no_params():
    assert to_query(CustomerAccountFilter()) == {}


def test_free_text_is_trimmed():
    assert to_query(CustomerAccountFilter(q="  core ")) == {"q": "core"}


def test_status_list_is_comma_joined():
    query = to_query(CustomerAccountFilter(status=[LifecycleStatus.ACTIVE, LifecycleStatus.PLANNED]))
    assert query["status"] == "ACTIVE,PLANNED"


def test_code_is_trimmed_into_query():
    assert to_query(CustomerAccountFilter(code="  abc ")) == {"code": "abc"}


def test_code_blank_is_omitted():
    assert "code" not in to_query(CustomerAccountFilter(code=""))


def test_name_is_trimmed_into_query():
    assert to_query(CustomerAccountFilter(name="  abc ")) == {"name": "abc"}


def test_name_blank_is_omitted():
    assert "name" not in to_query(CustomerAccountFilter(name=""))


def test_external_ref_is_trimmed_into_query():
    assert to_query(CustomerAccountFilter(external_ref="  abc ")) == {"externalRef": "abc"}


def test_external_ref_blank_is_omitted():
    assert "externalRef" not in to_query(CustomerAccountFilter(external_ref=""))


def test_customer_id_is_stringified():
    value = UUID("00000000-0000-0000-0000-000000000011")
    assert to_query(CustomerAccountFilter(customer_id=value))["customerId"] == str(value)


def test_account_number_is_trimmed_into_query():
    assert to_query(CustomerAccountFilter(account_number="  abc ")) == {"accountNumber": "abc"}


def test_account_number_blank_is_omitted():
    assert "accountNumber" not in to_query(CustomerAccountFilter(account_number=""))


def test_billing_cycle_values_are_comma_joined():
    members = list(CustomerAccountBillingCycle)[:2]
    query = to_query(CustomerAccountFilter(billing_cycle=members))
    assert query["billingCycle"] == ",".join(m.value for m in members)


def test_currency_is_trimmed_into_query():
    assert to_query(CustomerAccountFilter(currency="  abc ")) == {"currency": "abc"}


def test_currency_blank_is_omitted():
    assert "currency" not in to_query(CustomerAccountFilter(currency=""))


def test_credit_limit_range_is_sent():
    query = to_query(CustomerAccountFilter(credit_limit_min=Decimal("1.5"), credit_limit_max=Decimal("9.5")))
    assert query["creditLimitMin"] == str(Decimal("1.5"))
    assert query["creditLimitMax"] == str(Decimal("9.5"))


def test_credit_limit_zero_is_not_dropped():
    assert to_query(CustomerAccountFilter(credit_limit_min=0))["creditLimitMin"] == "0"


def test_effective_from_window_is_iso_formatted():
    query = to_query(CustomerAccountFilter(effective_from_from=date(2026, 1, 1), effective_from_to=date(2026, 1, 1)))
    assert query["effectiveFromFrom"].startswith("2026-01-01")
    assert query["effectiveFromTo"].startswith("2026-01-01")


def test_effective_to_window_is_iso_formatted():
    query = to_query(CustomerAccountFilter(effective_to_from=date(2026, 1, 1), effective_to_to=date(2026, 1, 1)))
    assert query["effectiveToFrom"].startswith("2026-01-01")
    assert query["effectiveToTo"].startswith("2026-01-01")


def test_labels_is_trimmed_into_query():
    assert to_query(CustomerAccountFilter(labels="  abc ")) == {"labels": "abc"}


def test_labels_blank_is_omitted():
    assert "labels" not in to_query(CustomerAccountFilter(labels=""))


def test_lifecycle_stage_values_are_comma_joined():
    members = list(CustomerAccountLifecycleStage)[:2]
    query = to_query(CustomerAccountFilter(lifecycle_stage=members))
    assert query["lifecycleStage"] == ",".join(m.value for m in members)


def test_source_system_is_trimmed_into_query():
    assert to_query(CustomerAccountFilter(source_system="  abc ")) == {"sourceSystem": "abc"}


def test_source_system_blank_is_omitted():
    assert "sourceSystem" not in to_query(CustomerAccountFilter(source_system=""))

