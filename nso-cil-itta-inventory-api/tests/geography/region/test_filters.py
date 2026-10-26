"""Filter translation tests for Region."""
from datetime import date, datetime, timezone
from decimal import Decimal
from uuid import UUID

from itta_core.models import LifecycleStatus
from itta_api.domains.geography.region.filters import RegionFilter, to_query
from itta_api.domains.geography.region.schemas import *  # noqa: F401,F403


def test_empty_filter_has_no_params():
    assert to_query(RegionFilter()) == {}


def test_free_text_is_trimmed():
    assert to_query(RegionFilter(q="  core ")) == {"q": "core"}


def test_status_list_is_comma_joined():
    query = to_query(RegionFilter(status=[LifecycleStatus.ACTIVE, LifecycleStatus.PLANNED]))
    assert query["status"] == "ACTIVE,PLANNED"


def test_code_is_trimmed_into_query():
    assert to_query(RegionFilter(code="  abc ")) == {"code": "abc"}


def test_code_blank_is_omitted():
    assert "code" not in to_query(RegionFilter(code=""))


def test_name_is_trimmed_into_query():
    assert to_query(RegionFilter(name="  abc ")) == {"name": "abc"}


def test_name_blank_is_omitted():
    assert "name" not in to_query(RegionFilter(name=""))


def test_external_ref_is_trimmed_into_query():
    assert to_query(RegionFilter(external_ref="  abc ")) == {"externalRef": "abc"}


def test_external_ref_blank_is_omitted():
    assert "externalRef" not in to_query(RegionFilter(external_ref=""))


def test_country_id_is_stringified():
    value = UUID("00000000-0000-0000-0000-000000000011")
    assert to_query(RegionFilter(country_id=value))["countryId"] == str(value)


def test_region_type_values_are_comma_joined():
    members = list(RegionRegionType)[:2]
    query = to_query(RegionFilter(region_type=members))
    assert query["regionType"] == ",".join(m.value for m in members)


def test_population_range_is_sent():
    query = to_query(RegionFilter(population_min=1, population_max=9))
    assert query["populationMin"] == str(1)
    assert query["populationMax"] == str(9)


def test_population_zero_is_not_dropped():
    assert to_query(RegionFilter(population_min=0))["populationMin"] == "0"


def test_latitude_range_is_sent():
    query = to_query(RegionFilter(latitude_min=Decimal("1.5"), latitude_max=Decimal("9.5")))
    assert query["latitudeMin"] == str(Decimal("1.5"))
    assert query["latitudeMax"] == str(Decimal("9.5"))


def test_latitude_zero_is_not_dropped():
    assert to_query(RegionFilter(latitude_min=0))["latitudeMin"] == "0"


def test_longitude_range_is_sent():
    query = to_query(RegionFilter(longitude_min=Decimal("1.5"), longitude_max=Decimal("9.5")))
    assert query["longitudeMin"] == str(Decimal("1.5"))
    assert query["longitudeMax"] == str(Decimal("9.5"))


def test_longitude_zero_is_not_dropped():
    assert to_query(RegionFilter(longitude_min=0))["longitudeMin"] == "0"


def test_criticality_values_are_comma_joined():
    members = list(RegionCriticality)[:2]
    query = to_query(RegionFilter(criticality=members))
    assert query["criticality"] == ",".join(m.value for m in members)


def test_cost_center_is_trimmed_into_query():
    assert to_query(RegionFilter(cost_center="  abc ")) == {"costCenter": "abc"}


def test_cost_center_blank_is_omitted():
    assert "costCenter" not in to_query(RegionFilter(cost_center=""))


def test_effective_from_window_is_iso_formatted():
    query = to_query(RegionFilter(effective_from_from=date(2026, 1, 1), effective_from_to=date(2026, 1, 1)))
    assert query["effectiveFromFrom"].startswith("2026-01-01")
    assert query["effectiveFromTo"].startswith("2026-01-01")


def test_effective_to_window_is_iso_formatted():
    query = to_query(RegionFilter(effective_to_from=date(2026, 1, 1), effective_to_to=date(2026, 1, 1)))
    assert query["effectiveToFrom"].startswith("2026-01-01")
    assert query["effectiveToTo"].startswith("2026-01-01")

