"""Filter translation tests for Contract."""
from datetime import date, datetime, timezone
from decimal import Decimal
from uuid import UUID

from itta_core.models import LifecycleStatus
from itta_api.domains.customer.contract.filters import ContractFilter, to_query
from itta_api.domains.customer.contract.schemas import *  # noqa: F401,F403


def test_empty_filter_has_no_params():
    assert to_query(ContractFilter()) == {}


def test_free_text_is_trimmed():
    assert to_query(ContractFilter(q="  core ")) == {"q": "core"}


def test_status_list_is_comma_joined():
    query = to_query(ContractFilter(status=[LifecycleStatus.ACTIVE, LifecycleStatus.PLANNED]))
    assert query["status"] == "ACTIVE,PLANNED"


def test_code_is_trimmed_into_query():
    assert to_query(ContractFilter(code="  abc ")) == {"code": "abc"}


def test_code_blank_is_omitted():
    assert "code" not in to_query(ContractFilter(code=""))


def test_name_is_trimmed_into_query():
    assert to_query(ContractFilter(name="  abc ")) == {"name": "abc"}


def test_name_blank_is_omitted():
    assert "name" not in to_query(ContractFilter(name=""))


def test_external_ref_is_trimmed_into_query():
    assert to_query(ContractFilter(external_ref="  abc ")) == {"externalRef": "abc"}


def test_external_ref_blank_is_omitted():
    assert "externalRef" not in to_query(ContractFilter(external_ref=""))


def test_customer_id_is_stringified():
    value = UUID("00000000-0000-0000-0000-000000000011")
    assert to_query(ContractFilter(customer_id=value))["customerId"] == str(value)


def test_contract_number_is_trimmed_into_query():
    assert to_query(ContractFilter(contract_number="  abc ")) == {"contractNumber": "abc"}


def test_contract_number_blank_is_omitted():
    assert "contractNumber" not in to_query(ContractFilter(contract_number=""))


def test_start_date_window_is_iso_formatted():
    query = to_query(ContractFilter(start_date_from=date(2026, 1, 1), start_date_to=date(2026, 1, 1)))
    assert query["startDateFrom"].startswith("2026-01-01")
    assert query["startDateTo"].startswith("2026-01-01")


def test_end_date_window_is_iso_formatted():
    query = to_query(ContractFilter(end_date_from=date(2026, 1, 1), end_date_to=date(2026, 1, 1)))
    assert query["endDateFrom"].startswith("2026-01-01")
    assert query["endDateTo"].startswith("2026-01-01")


def test_term_months_range_is_sent():
    query = to_query(ContractFilter(term_months_min=1, term_months_max=9))
    assert query["termMonthsMin"] == str(1)
    assert query["termMonthsMax"] == str(9)


def test_term_months_zero_is_not_dropped():
    assert to_query(ContractFilter(term_months_min=0))["termMonthsMin"] == "0"


def test_auto_renew_is_lowercase_boolean():
    assert to_query(ContractFilter(auto_renew=True))["autoRenew"] == "true"
    assert to_query(ContractFilter(auto_renew=False))["autoRenew"] == "false"


def test_source_system_is_trimmed_into_query():
    assert to_query(ContractFilter(source_system="  abc ")) == {"sourceSystem": "abc"}


def test_source_system_blank_is_omitted():
    assert "sourceSystem" not in to_query(ContractFilter(source_system=""))


def test_region_is_trimmed_into_query():
    assert to_query(ContractFilter(region="  abc ")) == {"region": "abc"}


def test_region_blank_is_omitted():
    assert "region" not in to_query(ContractFilter(region=""))


def test_owner_is_trimmed_into_query():
    assert to_query(ContractFilter(owner="  abc ")) == {"owner": "abc"}


def test_owner_blank_is_omitted():
    assert "owner" not in to_query(ContractFilter(owner=""))


def test_criticality_values_are_comma_joined():
    members = list(ContractCriticality)[:2]
    query = to_query(ContractFilter(criticality=members))
    assert query["criticality"] == ",".join(m.value for m in members)


def test_cost_center_is_trimmed_into_query():
    assert to_query(ContractFilter(cost_center="  abc ")) == {"costCenter": "abc"}


def test_cost_center_blank_is_omitted():
    assert "costCenter" not in to_query(ContractFilter(cost_center=""))

