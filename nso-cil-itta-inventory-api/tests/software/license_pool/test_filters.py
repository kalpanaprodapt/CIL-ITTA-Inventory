"""Filter translation tests for License Pool."""
from datetime import date, datetime, timezone
from decimal import Decimal
from uuid import UUID

from itta_core.models import LifecycleStatus
from itta_api.domains.software.license_pool.filters import LicensePoolFilter, to_query
from itta_api.domains.software.license_pool.schemas import *  # noqa: F401,F403


def test_empty_filter_has_no_params():
    assert to_query(LicensePoolFilter()) == {}


def test_free_text_is_trimmed():
    assert to_query(LicensePoolFilter(q="  core ")) == {"q": "core"}


def test_status_list_is_comma_joined():
    query = to_query(LicensePoolFilter(status=[LifecycleStatus.ACTIVE, LifecycleStatus.PLANNED]))
    assert query["status"] == "ACTIVE,PLANNED"


def test_code_is_trimmed_into_query():
    assert to_query(LicensePoolFilter(code="  abc ")) == {"code": "abc"}


def test_code_blank_is_omitted():
    assert "code" not in to_query(LicensePoolFilter(code=""))


def test_name_is_trimmed_into_query():
    assert to_query(LicensePoolFilter(name="  abc ")) == {"name": "abc"}


def test_name_blank_is_omitted():
    assert "name" not in to_query(LicensePoolFilter(name=""))


def test_external_ref_is_trimmed_into_query():
    assert to_query(LicensePoolFilter(external_ref="  abc ")) == {"externalRef": "abc"}


def test_external_ref_blank_is_omitted():
    assert "externalRef" not in to_query(LicensePoolFilter(external_ref=""))


def test_vendor_id_is_stringified():
    value = UUID("00000000-0000-0000-0000-000000000011")
    assert to_query(LicensePoolFilter(vendor_id=value))["vendorId"] == str(value)


def test_pool_name_is_trimmed_into_query():
    assert to_query(LicensePoolFilter(pool_name="  abc ")) == {"poolName": "abc"}


def test_pool_name_blank_is_omitted():
    assert "poolName" not in to_query(LicensePoolFilter(pool_name=""))


def test_total_seats_range_is_sent():
    query = to_query(LicensePoolFilter(total_seats_min=1, total_seats_max=9))
    assert query["totalSeatsMin"] == str(1)
    assert query["totalSeatsMax"] == str(9)


def test_total_seats_zero_is_not_dropped():
    assert to_query(LicensePoolFilter(total_seats_min=0))["totalSeatsMin"] == "0"


def test_used_seats_range_is_sent():
    query = to_query(LicensePoolFilter(used_seats_min=1, used_seats_max=9))
    assert query["usedSeatsMin"] == str(1)
    assert query["usedSeatsMax"] == str(9)


def test_used_seats_zero_is_not_dropped():
    assert to_query(LicensePoolFilter(used_seats_min=0))["usedSeatsMin"] == "0"


def test_renewal_date_window_is_iso_formatted():
    query = to_query(LicensePoolFilter(renewal_date_from=date(2026, 1, 1), renewal_date_to=date(2026, 1, 1)))
    assert query["renewalDateFrom"].startswith("2026-01-01")
    assert query["renewalDateTo"].startswith("2026-01-01")


def test_effective_to_window_is_iso_formatted():
    query = to_query(LicensePoolFilter(effective_to_from=date(2026, 1, 1), effective_to_to=date(2026, 1, 1)))
    assert query["effectiveToFrom"].startswith("2026-01-01")
    assert query["effectiveToTo"].startswith("2026-01-01")


def test_labels_is_trimmed_into_query():
    assert to_query(LicensePoolFilter(labels="  abc ")) == {"labels": "abc"}


def test_labels_blank_is_omitted():
    assert "labels" not in to_query(LicensePoolFilter(labels=""))


def test_lifecycle_stage_values_are_comma_joined():
    members = list(LicensePoolLifecycleStage)[:2]
    query = to_query(LicensePoolFilter(lifecycle_stage=members))
    assert query["lifecycleStage"] == ",".join(m.value for m in members)


def test_source_system_is_trimmed_into_query():
    assert to_query(LicensePoolFilter(source_system="  abc ")) == {"sourceSystem": "abc"}


def test_source_system_blank_is_omitted():
    assert "sourceSystem" not in to_query(LicensePoolFilter(source_system=""))


def test_region_is_trimmed_into_query():
    assert to_query(LicensePoolFilter(region="  abc ")) == {"region": "abc"}


def test_region_blank_is_omitted():
    assert "region" not in to_query(LicensePoolFilter(region=""))

