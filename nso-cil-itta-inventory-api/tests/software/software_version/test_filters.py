"""Filter translation tests for Software Version."""
from datetime import date, datetime, timezone
from decimal import Decimal
from uuid import UUID

from itta_core.models import LifecycleStatus
from itta_api.domains.software.software_version.filters import SoftwareVersionFilter, to_query
from itta_api.domains.software.software_version.schemas import *  # noqa: F401,F403


def test_empty_filter_has_no_params():
    assert to_query(SoftwareVersionFilter()) == {}


def test_free_text_is_trimmed():
    assert to_query(SoftwareVersionFilter(q="  core ")) == {"q": "core"}


def test_status_list_is_comma_joined():
    query = to_query(SoftwareVersionFilter(status=[LifecycleStatus.ACTIVE, LifecycleStatus.PLANNED]))
    assert query["status"] == "ACTIVE,PLANNED"


def test_code_is_trimmed_into_query():
    assert to_query(SoftwareVersionFilter(code="  abc ")) == {"code": "abc"}


def test_code_blank_is_omitted():
    assert "code" not in to_query(SoftwareVersionFilter(code=""))


def test_name_is_trimmed_into_query():
    assert to_query(SoftwareVersionFilter(name="  abc ")) == {"name": "abc"}


def test_name_blank_is_omitted():
    assert "name" not in to_query(SoftwareVersionFilter(name=""))


def test_external_ref_is_trimmed_into_query():
    assert to_query(SoftwareVersionFilter(external_ref="  abc ")) == {"externalRef": "abc"}


def test_external_ref_blank_is_omitted():
    assert "externalRef" not in to_query(SoftwareVersionFilter(external_ref=""))


def test_image_id_is_stringified():
    value = UUID("00000000-0000-0000-0000-000000000011")
    assert to_query(SoftwareVersionFilter(image_id=value))["imageId"] == str(value)


def test_version_string_is_trimmed_into_query():
    assert to_query(SoftwareVersionFilter(version_string="  abc ")) == {"versionString": "abc"}


def test_version_string_blank_is_omitted():
    assert "versionString" not in to_query(SoftwareVersionFilter(version_string=""))


def test_release_date_window_is_iso_formatted():
    query = to_query(SoftwareVersionFilter(release_date_from=date(2026, 1, 1), release_date_to=date(2026, 1, 1)))
    assert query["releaseDateFrom"].startswith("2026-01-01")
    assert query["releaseDateTo"].startswith("2026-01-01")


def test_end_of_support_window_is_iso_formatted():
    query = to_query(SoftwareVersionFilter(end_of_support_from=date(2026, 1, 1), end_of_support_to=date(2026, 1, 1)))
    assert query["endOfSupportFrom"].startswith("2026-01-01")
    assert query["endOfSupportTo"].startswith("2026-01-01")


def test_release_train_values_are_comma_joined():
    members = list(SoftwareVersionReleaseTrain)[:2]
    query = to_query(SoftwareVersionFilter(release_train=members))
    assert query["releaseTrain"] == ",".join(m.value for m in members)


def test_cve_count_range_is_sent():
    query = to_query(SoftwareVersionFilter(cve_count_min=1, cve_count_max=9))
    assert query["cveCountMin"] == str(1)
    assert query["cveCountMax"] == str(9)


def test_cve_count_zero_is_not_dropped():
    assert to_query(SoftwareVersionFilter(cve_count_min=0))["cveCountMin"] == "0"


def test_region_is_trimmed_into_query():
    assert to_query(SoftwareVersionFilter(region="  abc ")) == {"region": "abc"}


def test_region_blank_is_omitted():
    assert "region" not in to_query(SoftwareVersionFilter(region=""))


def test_owner_is_trimmed_into_query():
    assert to_query(SoftwareVersionFilter(owner="  abc ")) == {"owner": "abc"}


def test_owner_blank_is_omitted():
    assert "owner" not in to_query(SoftwareVersionFilter(owner=""))


def test_criticality_values_are_comma_joined():
    members = list(SoftwareVersionCriticality)[:2]
    query = to_query(SoftwareVersionFilter(criticality=members))
    assert query["criticality"] == ",".join(m.value for m in members)


def test_cost_center_is_trimmed_into_query():
    assert to_query(SoftwareVersionFilter(cost_center="  abc ")) == {"costCenter": "abc"}


def test_cost_center_blank_is_omitted():
    assert "costCenter" not in to_query(SoftwareVersionFilter(cost_center=""))

