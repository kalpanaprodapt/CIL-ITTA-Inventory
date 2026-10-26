"""Filter translation tests for Lag Group."""
from datetime import date, datetime, timezone
from decimal import Decimal
from uuid import UUID

from itta_core.models import LifecycleStatus
from itta_api.domains.port.lag_group.filters import LagGroupFilter, to_query
from itta_api.domains.port.lag_group.schemas import *  # noqa: F401,F403


def test_empty_filter_has_no_params():
    assert to_query(LagGroupFilter()) == {}


def test_free_text_is_trimmed():
    assert to_query(LagGroupFilter(q="  core ")) == {"q": "core"}


def test_status_list_is_comma_joined():
    query = to_query(LagGroupFilter(status=[LifecycleStatus.ACTIVE, LifecycleStatus.PLANNED]))
    assert query["status"] == "ACTIVE,PLANNED"


def test_code_is_trimmed_into_query():
    assert to_query(LagGroupFilter(code="  abc ")) == {"code": "abc"}


def test_code_blank_is_omitted():
    assert "code" not in to_query(LagGroupFilter(code=""))


def test_name_is_trimmed_into_query():
    assert to_query(LagGroupFilter(name="  abc ")) == {"name": "abc"}


def test_name_blank_is_omitted():
    assert "name" not in to_query(LagGroupFilter(name=""))


def test_external_ref_is_trimmed_into_query():
    assert to_query(LagGroupFilter(external_ref="  abc ")) == {"externalRef": "abc"}


def test_external_ref_blank_is_omitted():
    assert "externalRef" not in to_query(LagGroupFilter(external_ref=""))


def test_device_id_is_stringified():
    value = UUID("00000000-0000-0000-0000-000000000011")
    assert to_query(LagGroupFilter(device_id=value))["deviceId"] == str(value)


def test_lag_name_is_trimmed_into_query():
    assert to_query(LagGroupFilter(lag_name="  abc ")) == {"lagName": "abc"}


def test_lag_name_blank_is_omitted():
    assert "lagName" not in to_query(LagGroupFilter(lag_name=""))


def test_member_count_range_is_sent():
    query = to_query(LagGroupFilter(member_count_min=1, member_count_max=9))
    assert query["memberCountMin"] == str(1)
    assert query["memberCountMax"] == str(9)


def test_member_count_zero_is_not_dropped():
    assert to_query(LagGroupFilter(member_count_min=0))["memberCountMin"] == "0"


def test_lacp_mode_values_are_comma_joined():
    members = list(LagGroupLacpMode)[:2]
    query = to_query(LagGroupFilter(lacp_mode=members))
    assert query["lacpMode"] == ",".join(m.value for m in members)


def test_min_links_range_is_sent():
    query = to_query(LagGroupFilter(min_links_min=1, min_links_max=9))
    assert query["minLinksMin"] == str(1)
    assert query["minLinksMax"] == str(9)


def test_min_links_zero_is_not_dropped():
    assert to_query(LagGroupFilter(min_links_min=0))["minLinksMin"] == "0"


def test_effective_from_window_is_iso_formatted():
    query = to_query(LagGroupFilter(effective_from_from=date(2026, 1, 1), effective_from_to=date(2026, 1, 1)))
    assert query["effectiveFromFrom"].startswith("2026-01-01")
    assert query["effectiveFromTo"].startswith("2026-01-01")


def test_effective_to_window_is_iso_formatted():
    query = to_query(LagGroupFilter(effective_to_from=date(2026, 1, 1), effective_to_to=date(2026, 1, 1)))
    assert query["effectiveToFrom"].startswith("2026-01-01")
    assert query["effectiveToTo"].startswith("2026-01-01")


def test_labels_is_trimmed_into_query():
    assert to_query(LagGroupFilter(labels="  abc ")) == {"labels": "abc"}


def test_labels_blank_is_omitted():
    assert "labels" not in to_query(LagGroupFilter(labels=""))


def test_lifecycle_stage_values_are_comma_joined():
    members = list(LagGroupLifecycleStage)[:2]
    query = to_query(LagGroupFilter(lifecycle_stage=members))
    assert query["lifecycleStage"] == ",".join(m.value for m in members)


def test_source_system_is_trimmed_into_query():
    assert to_query(LagGroupFilter(source_system="  abc ")) == {"sourceSystem": "abc"}


def test_source_system_blank_is_omitted():
    assert "sourceSystem" not in to_query(LagGroupFilter(source_system=""))

