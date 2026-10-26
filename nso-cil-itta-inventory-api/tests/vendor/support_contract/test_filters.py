"""Filter translation tests for Support Contract."""
from datetime import date, datetime, timezone
from decimal import Decimal
from uuid import UUID

from itta_core.models import LifecycleStatus
from itta_api.domains.vendor.support_contract.filters import SupportContractFilter, to_query
from itta_api.domains.vendor.support_contract.schemas import *  # noqa: F401,F403


def test_empty_filter_has_no_params():
    assert to_query(SupportContractFilter()) == {}


def test_free_text_is_trimmed():
    assert to_query(SupportContractFilter(q="  core ")) == {"q": "core"}


def test_status_list_is_comma_joined():
    query = to_query(SupportContractFilter(status=[LifecycleStatus.ACTIVE, LifecycleStatus.PLANNED]))
    assert query["status"] == "ACTIVE,PLANNED"


def test_code_is_trimmed_into_query():
    assert to_query(SupportContractFilter(code="  abc ")) == {"code": "abc"}


def test_code_blank_is_omitted():
    assert "code" not in to_query(SupportContractFilter(code=""))


def test_name_is_trimmed_into_query():
    assert to_query(SupportContractFilter(name="  abc ")) == {"name": "abc"}


def test_name_blank_is_omitted():
    assert "name" not in to_query(SupportContractFilter(name=""))


def test_external_ref_is_trimmed_into_query():
    assert to_query(SupportContractFilter(external_ref="  abc ")) == {"externalRef": "abc"}


def test_external_ref_blank_is_omitted():
    assert "externalRef" not in to_query(SupportContractFilter(external_ref=""))


def test_vendor_id_is_stringified():
    value = UUID("00000000-0000-0000-0000-000000000011")
    assert to_query(SupportContractFilter(vendor_id=value))["vendorId"] == str(value)


def test_contract_number_is_trimmed_into_query():
    assert to_query(SupportContractFilter(contract_number="  abc ")) == {"contractNumber": "abc"}


def test_contract_number_blank_is_omitted():
    assert "contractNumber" not in to_query(SupportContractFilter(contract_number=""))


def test_start_date_window_is_iso_formatted():
    query = to_query(SupportContractFilter(start_date_from=date(2026, 1, 1), start_date_to=date(2026, 1, 1)))
    assert query["startDateFrom"].startswith("2026-01-01")
    assert query["startDateTo"].startswith("2026-01-01")


def test_end_date_window_is_iso_formatted():
    query = to_query(SupportContractFilter(end_date_from=date(2026, 1, 1), end_date_to=date(2026, 1, 1)))
    assert query["endDateFrom"].startswith("2026-01-01")
    assert query["endDateTo"].startswith("2026-01-01")


def test_coverage_level_values_are_comma_joined():
    members = list(SupportContractCoverageLevel)[:2]
    query = to_query(SupportContractFilter(coverage_level=members))
    assert query["coverageLevel"] == ",".join(m.value for m in members)


def test_annual_cost_range_is_sent():
    query = to_query(SupportContractFilter(annual_cost_min=Decimal("1.5"), annual_cost_max=Decimal("9.5")))
    assert query["annualCostMin"] == str(Decimal("1.5"))
    assert query["annualCostMax"] == str(Decimal("9.5"))


def test_annual_cost_zero_is_not_dropped():
    assert to_query(SupportContractFilter(annual_cost_min=0))["annualCostMin"] == "0"


def test_owner_is_trimmed_into_query():
    assert to_query(SupportContractFilter(owner="  abc ")) == {"owner": "abc"}


def test_owner_blank_is_omitted():
    assert "owner" not in to_query(SupportContractFilter(owner=""))


def test_criticality_values_are_comma_joined():
    members = list(SupportContractCriticality)[:2]
    query = to_query(SupportContractFilter(criticality=members))
    assert query["criticality"] == ",".join(m.value for m in members)


def test_cost_center_is_trimmed_into_query():
    assert to_query(SupportContractFilter(cost_center="  abc ")) == {"costCenter": "abc"}


def test_cost_center_blank_is_omitted():
    assert "costCenter" not in to_query(SupportContractFilter(cost_center=""))


def test_effective_from_window_is_iso_formatted():
    query = to_query(SupportContractFilter(effective_from_from=date(2026, 1, 1), effective_from_to=date(2026, 1, 1)))
    assert query["effectiveFromFrom"].startswith("2026-01-01")
    assert query["effectiveFromTo"].startswith("2026-01-01")

