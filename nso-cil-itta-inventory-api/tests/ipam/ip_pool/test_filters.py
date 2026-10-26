"""Filter translation tests for Ip Pool."""
from datetime import date, datetime, timezone
from decimal import Decimal
from uuid import UUID

from itta_core.models import LifecycleStatus
from itta_api.domains.ipam.ip_pool.filters import IpPoolFilter, to_query
from itta_api.domains.ipam.ip_pool.schemas import *  # noqa: F401,F403


def test_empty_filter_has_no_params():
    assert to_query(IpPoolFilter()) == {}


def test_free_text_is_trimmed():
    assert to_query(IpPoolFilter(q="  core ")) == {"q": "core"}


def test_status_list_is_comma_joined():
    query = to_query(IpPoolFilter(status=[LifecycleStatus.ACTIVE, LifecycleStatus.PLANNED]))
    assert query["status"] == "ACTIVE,PLANNED"


def test_code_is_trimmed_into_query():
    assert to_query(IpPoolFilter(code="  abc ")) == {"code": "abc"}


def test_code_blank_is_omitted():
    assert "code" not in to_query(IpPoolFilter(code=""))


def test_name_is_trimmed_into_query():
    assert to_query(IpPoolFilter(name="  abc ")) == {"name": "abc"}


def test_name_blank_is_omitted():
    assert "name" not in to_query(IpPoolFilter(name=""))


def test_external_ref_is_trimmed_into_query():
    assert to_query(IpPoolFilter(external_ref="  abc ")) == {"externalRef": "abc"}


def test_external_ref_blank_is_omitted():
    assert "externalRef" not in to_query(IpPoolFilter(external_ref=""))


def test_subnet_id_is_stringified():
    value = UUID("00000000-0000-0000-0000-000000000011")
    assert to_query(IpPoolFilter(subnet_id=value))["subnetId"] == str(value)


def test_range_start_is_trimmed_into_query():
    assert to_query(IpPoolFilter(range_start="  abc ")) == {"rangeStart": "abc"}


def test_range_start_blank_is_omitted():
    assert "rangeStart" not in to_query(IpPoolFilter(range_start=""))


def test_range_end_is_trimmed_into_query():
    assert to_query(IpPoolFilter(range_end="  abc ")) == {"rangeEnd": "abc"}


def test_range_end_blank_is_omitted():
    assert "rangeEnd" not in to_query(IpPoolFilter(range_end=""))


def test_pool_type_values_are_comma_joined():
    members = list(IpPoolPoolType)[:2]
    query = to_query(IpPoolFilter(pool_type=members))
    assert query["poolType"] == ",".join(m.value for m in members)


def test_lease_seconds_range_is_sent():
    query = to_query(IpPoolFilter(lease_seconds_min=1, lease_seconds_max=9))
    assert query["leaseSecondsMin"] == str(1)
    assert query["leaseSecondsMax"] == str(9)


def test_lease_seconds_zero_is_not_dropped():
    assert to_query(IpPoolFilter(lease_seconds_min=0))["leaseSecondsMin"] == "0"


def test_effective_from_window_is_iso_formatted():
    query = to_query(IpPoolFilter(effective_from_from=date(2026, 1, 1), effective_from_to=date(2026, 1, 1)))
    assert query["effectiveFromFrom"].startswith("2026-01-01")
    assert query["effectiveFromTo"].startswith("2026-01-01")


def test_effective_to_window_is_iso_formatted():
    query = to_query(IpPoolFilter(effective_to_from=date(2026, 1, 1), effective_to_to=date(2026, 1, 1)))
    assert query["effectiveToFrom"].startswith("2026-01-01")
    assert query["effectiveToTo"].startswith("2026-01-01")


def test_labels_is_trimmed_into_query():
    assert to_query(IpPoolFilter(labels="  abc ")) == {"labels": "abc"}


def test_labels_blank_is_omitted():
    assert "labels" not in to_query(IpPoolFilter(labels=""))


def test_lifecycle_stage_values_are_comma_joined():
    members = list(IpPoolLifecycleStage)[:2]
    query = to_query(IpPoolFilter(lifecycle_stage=members))
    assert query["lifecycleStage"] == ",".join(m.value for m in members)


def test_source_system_is_trimmed_into_query():
    assert to_query(IpPoolFilter(source_system="  abc ")) == {"sourceSystem": "abc"}


def test_source_system_blank_is_omitted():
    assert "sourceSystem" not in to_query(IpPoolFilter(source_system=""))

