"""Filter translation tests for Customer."""
from datetime import date, datetime, timezone
from decimal import Decimal
from uuid import UUID

from itta_core.models import LifecycleStatus
from itta_api.domains.customer.customer.filters import CustomerFilter, to_query
from itta_api.domains.customer.customer.schemas import *  # noqa: F401,F403


def test_empty_filter_has_no_params():
    assert to_query(CustomerFilter()) == {}


def test_free_text_is_trimmed():
    assert to_query(CustomerFilter(q="  core ")) == {"q": "core"}


def test_status_list_is_comma_joined():
    query = to_query(CustomerFilter(status=[LifecycleStatus.ACTIVE, LifecycleStatus.PLANNED]))
    assert query["status"] == "ACTIVE,PLANNED"


def test_code_is_trimmed_into_query():
    assert to_query(CustomerFilter(code="  abc ")) == {"code": "abc"}


def test_code_blank_is_omitted():
    assert "code" not in to_query(CustomerFilter(code=""))


def test_name_is_trimmed_into_query():
    assert to_query(CustomerFilter(name="  abc ")) == {"name": "abc"}


def test_name_blank_is_omitted():
    assert "name" not in to_query(CustomerFilter(name=""))


def test_external_ref_is_trimmed_into_query():
    assert to_query(CustomerFilter(external_ref="  abc ")) == {"externalRef": "abc"}


def test_external_ref_blank_is_omitted():
    assert "externalRef" not in to_query(CustomerFilter(external_ref=""))


def test_legal_name_is_trimmed_into_query():
    assert to_query(CustomerFilter(legal_name="  abc ")) == {"legalName": "abc"}


def test_legal_name_blank_is_omitted():
    assert "legalName" not in to_query(CustomerFilter(legal_name=""))


def test_segment_values_are_comma_joined():
    members = list(CustomerSegment)[:2]
    query = to_query(CustomerFilter(segment=members))
    assert query["segment"] == ",".join(m.value for m in members)


def test_tax_id_is_trimmed_into_query():
    assert to_query(CustomerFilter(tax_id="  abc ")) == {"taxId": "abc"}


def test_tax_id_blank_is_omitted():
    assert "taxId" not in to_query(CustomerFilter(tax_id=""))


def test_industry_is_trimmed_into_query():
    assert to_query(CustomerFilter(industry="  abc ")) == {"industry": "abc"}


def test_industry_blank_is_omitted():
    assert "industry" not in to_query(CustomerFilter(industry=""))


def test_account_manager_is_trimmed_into_query():
    assert to_query(CustomerFilter(account_manager="  abc ")) == {"accountManager": "abc"}


def test_account_manager_blank_is_omitted():
    assert "accountManager" not in to_query(CustomerFilter(account_manager=""))


def test_cost_center_is_trimmed_into_query():
    assert to_query(CustomerFilter(cost_center="  abc ")) == {"costCenter": "abc"}


def test_cost_center_blank_is_omitted():
    assert "costCenter" not in to_query(CustomerFilter(cost_center=""))


def test_effective_from_window_is_iso_formatted():
    query = to_query(CustomerFilter(effective_from_from=date(2026, 1, 1), effective_from_to=date(2026, 1, 1)))
    assert query["effectiveFromFrom"].startswith("2026-01-01")
    assert query["effectiveFromTo"].startswith("2026-01-01")


def test_effective_to_window_is_iso_formatted():
    query = to_query(CustomerFilter(effective_to_from=date(2026, 1, 1), effective_to_to=date(2026, 1, 1)))
    assert query["effectiveToFrom"].startswith("2026-01-01")
    assert query["effectiveToTo"].startswith("2026-01-01")


def test_labels_is_trimmed_into_query():
    assert to_query(CustomerFilter(labels="  abc ")) == {"labels": "abc"}


def test_labels_blank_is_omitted():
    assert "labels" not in to_query(CustomerFilter(labels=""))

