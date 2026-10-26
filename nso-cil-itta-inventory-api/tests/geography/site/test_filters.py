"""Filter translation tests for Site."""
from datetime import date, datetime, timezone
from decimal import Decimal
from uuid import UUID

from itta_core.models import LifecycleStatus
from itta_api.domains.geography.site.filters import SiteFilter, to_query
from itta_api.domains.geography.site.schemas import *  # noqa: F401,F403


def test_empty_filter_has_no_params():
    assert to_query(SiteFilter()) == {}


def test_free_text_is_trimmed():
    assert to_query(SiteFilter(q="  core ")) == {"q": "core"}


def test_status_list_is_comma_joined():
    query = to_query(SiteFilter(status=[LifecycleStatus.ACTIVE, LifecycleStatus.PLANNED]))
    assert query["status"] == "ACTIVE,PLANNED"


def test_code_is_trimmed_into_query():
    assert to_query(SiteFilter(code="  abc ")) == {"code": "abc"}


def test_code_blank_is_omitted():
    assert "code" not in to_query(SiteFilter(code=""))


def test_name_is_trimmed_into_query():
    assert to_query(SiteFilter(name="  abc ")) == {"name": "abc"}


def test_name_blank_is_omitted():
    assert "name" not in to_query(SiteFilter(name=""))


def test_external_ref_is_trimmed_into_query():
    assert to_query(SiteFilter(external_ref="  abc ")) == {"externalRef": "abc"}


def test_external_ref_blank_is_omitted():
    assert "externalRef" not in to_query(SiteFilter(external_ref=""))


def test_city_id_is_stringified():
    value = UUID("00000000-0000-0000-0000-000000000011")
    assert to_query(SiteFilter(city_id=value))["cityId"] == str(value)


def test_site_type_values_are_comma_joined():
    members = list(SiteSiteType)[:2]
    query = to_query(SiteFilter(site_type=members))
    assert query["siteType"] == ",".join(m.value for m in members)


def test_address_is_trimmed_into_query():
    assert to_query(SiteFilter(address="  abc ")) == {"address": "abc"}


def test_address_blank_is_omitted():
    assert "address" not in to_query(SiteFilter(address=""))


def test_latitude_range_is_sent():
    query = to_query(SiteFilter(latitude_min=Decimal("1.5"), latitude_max=Decimal("9.5")))
    assert query["latitudeMin"] == str(Decimal("1.5"))
    assert query["latitudeMax"] == str(Decimal("9.5"))


def test_latitude_zero_is_not_dropped():
    assert to_query(SiteFilter(latitude_min=0))["latitudeMin"] == "0"


def test_longitude_range_is_sent():
    query = to_query(SiteFilter(longitude_min=Decimal("1.5"), longitude_max=Decimal("9.5")))
    assert query["longitudeMin"] == str(Decimal("1.5"))
    assert query["longitudeMax"] == str(Decimal("9.5"))


def test_longitude_zero_is_not_dropped():
    assert to_query(SiteFilter(longitude_min=0))["longitudeMin"] == "0"


def test_power_kw_range_is_sent():
    query = to_query(SiteFilter(power_kw_min=Decimal("1.5"), power_kw_max=Decimal("9.5")))
    assert query["powerKwMin"] == str(Decimal("1.5"))
    assert query["powerKwMax"] == str(Decimal("9.5"))


def test_power_kw_zero_is_not_dropped():
    assert to_query(SiteFilter(power_kw_min=0))["powerKwMin"] == "0"


def test_access_hours_is_trimmed_into_query():
    assert to_query(SiteFilter(access_hours="  abc ")) == {"accessHours": "abc"}


def test_access_hours_blank_is_omitted():
    assert "accessHours" not in to_query(SiteFilter(access_hours=""))


def test_effective_to_window_is_iso_formatted():
    query = to_query(SiteFilter(effective_to_from=date(2026, 1, 1), effective_to_to=date(2026, 1, 1)))
    assert query["effectiveToFrom"].startswith("2026-01-01")
    assert query["effectiveToTo"].startswith("2026-01-01")


def test_labels_is_trimmed_into_query():
    assert to_query(SiteFilter(labels="  abc ")) == {"labels": "abc"}


def test_labels_blank_is_omitted():
    assert "labels" not in to_query(SiteFilter(labels=""))


def test_lifecycle_stage_values_are_comma_joined():
    members = list(SiteLifecycleStage)[:2]
    query = to_query(SiteFilter(lifecycle_stage=members))
    assert query["lifecycleStage"] == ",".join(m.value for m in members)


def test_source_system_is_trimmed_into_query():
    assert to_query(SiteFilter(source_system="  abc ")) == {"sourceSystem": "abc"}


def test_source_system_blank_is_omitted():
    assert "sourceSystem" not in to_query(SiteFilter(source_system=""))


def test_region_is_trimmed_into_query():
    assert to_query(SiteFilter(region="  abc ")) == {"region": "abc"}


def test_region_blank_is_omitted():
    assert "region" not in to_query(SiteFilter(region=""))

