"""Filter translation tests for Country."""
from datetime import date, datetime, timezone
from decimal import Decimal
from uuid import UUID

from itta_core.models import LifecycleStatus
from itta_api.domains.geography.country.filters import CountryFilter, to_query
from itta_api.domains.geography.country.schemas import *  # noqa: F401,F403


def test_empty_filter_has_no_params():
    assert to_query(CountryFilter()) == {}


def test_free_text_is_trimmed():
    assert to_query(CountryFilter(q="  core ")) == {"q": "core"}


def test_status_list_is_comma_joined():
    query = to_query(CountryFilter(status=[LifecycleStatus.ACTIVE, LifecycleStatus.PLANNED]))
    assert query["status"] == "ACTIVE,PLANNED"


def test_code_is_trimmed_into_query():
    assert to_query(CountryFilter(code="  abc ")) == {"code": "abc"}


def test_code_blank_is_omitted():
    assert "code" not in to_query(CountryFilter(code=""))


def test_name_is_trimmed_into_query():
    assert to_query(CountryFilter(name="  abc ")) == {"name": "abc"}


def test_name_blank_is_omitted():
    assert "name" not in to_query(CountryFilter(name=""))


def test_external_ref_is_trimmed_into_query():
    assert to_query(CountryFilter(external_ref="  abc ")) == {"externalRef": "abc"}


def test_external_ref_blank_is_omitted():
    assert "externalRef" not in to_query(CountryFilter(external_ref=""))


def test_iso_code_is_trimmed_into_query():
    assert to_query(CountryFilter(iso_code="  abc ")) == {"isoCode": "abc"}


def test_iso_code_blank_is_omitted():
    assert "isoCode" not in to_query(CountryFilter(iso_code=""))


def test_iso_numeric_range_is_sent():
    query = to_query(CountryFilter(iso_numeric_min=1, iso_numeric_max=9))
    assert query["isoNumericMin"] == str(1)
    assert query["isoNumericMax"] == str(9)


def test_iso_numeric_zero_is_not_dropped():
    assert to_query(CountryFilter(iso_numeric_min=0))["isoNumericMin"] == "0"


def test_dial_code_is_trimmed_into_query():
    assert to_query(CountryFilter(dial_code="  abc ")) == {"dialCode": "abc"}


def test_dial_code_blank_is_omitted():
    assert "dialCode" not in to_query(CountryFilter(dial_code=""))


def test_currency_is_trimmed_into_query():
    assert to_query(CountryFilter(currency="  abc ")) == {"currency": "abc"}


def test_currency_blank_is_omitted():
    assert "currency" not in to_query(CountryFilter(currency=""))


def test_regulatory_body_is_trimmed_into_query():
    assert to_query(CountryFilter(regulatory_body="  abc ")) == {"regulatoryBody": "abc"}


def test_regulatory_body_blank_is_omitted():
    assert "regulatoryBody" not in to_query(CountryFilter(regulatory_body=""))


def test_timezone_is_trimmed_into_query():
    assert to_query(CountryFilter(timezone="  abc ")) == {"timezone": "abc"}


def test_timezone_blank_is_omitted():
    assert "timezone" not in to_query(CountryFilter(timezone=""))


def test_region_is_trimmed_into_query():
    assert to_query(CountryFilter(region="  abc ")) == {"region": "abc"}


def test_region_blank_is_omitted():
    assert "region" not in to_query(CountryFilter(region=""))


def test_owner_is_trimmed_into_query():
    assert to_query(CountryFilter(owner="  abc ")) == {"owner": "abc"}


def test_owner_blank_is_omitted():
    assert "owner" not in to_query(CountryFilter(owner=""))


def test_criticality_values_are_comma_joined():
    members = list(CountryCriticality)[:2]
    query = to_query(CountryFilter(criticality=members))
    assert query["criticality"] == ",".join(m.value for m in members)


def test_cost_center_is_trimmed_into_query():
    assert to_query(CountryFilter(cost_center="  abc ")) == {"costCenter": "abc"}


def test_cost_center_blank_is_omitted():
    assert "costCenter" not in to_query(CountryFilter(cost_center=""))

