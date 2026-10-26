"""Filter translation tests for Power Feed."""
from datetime import date, datetime, timezone
from decimal import Decimal
from uuid import UUID

from itta_core.models import LifecycleStatus
from itta_api.domains.facility.power_feed.filters import PowerFeedFilter, to_query
from itta_api.domains.facility.power_feed.schemas import *  # noqa: F401,F403


def test_empty_filter_has_no_params():
    assert to_query(PowerFeedFilter()) == {}


def test_free_text_is_trimmed():
    assert to_query(PowerFeedFilter(q="  core ")) == {"q": "core"}


def test_status_list_is_comma_joined():
    query = to_query(PowerFeedFilter(status=[LifecycleStatus.ACTIVE, LifecycleStatus.PLANNED]))
    assert query["status"] == "ACTIVE,PLANNED"


def test_code_is_trimmed_into_query():
    assert to_query(PowerFeedFilter(code="  abc ")) == {"code": "abc"}


def test_code_blank_is_omitted():
    assert "code" not in to_query(PowerFeedFilter(code=""))


def test_name_is_trimmed_into_query():
    assert to_query(PowerFeedFilter(name="  abc ")) == {"name": "abc"}


def test_name_blank_is_omitted():
    assert "name" not in to_query(PowerFeedFilter(name=""))


def test_external_ref_is_trimmed_into_query():
    assert to_query(PowerFeedFilter(external_ref="  abc ")) == {"externalRef": "abc"}


def test_external_ref_blank_is_omitted():
    assert "externalRef" not in to_query(PowerFeedFilter(external_ref=""))


def test_room_id_is_stringified():
    value = UUID("00000000-0000-0000-0000-000000000011")
    assert to_query(PowerFeedFilter(room_id=value))["roomId"] == str(value)


def test_feed_type_values_are_comma_joined():
    members = list(PowerFeedFeedType)[:2]
    query = to_query(PowerFeedFilter(feed_type=members))
    assert query["feedType"] == ",".join(m.value for m in members)


def test_voltage_range_is_sent():
    query = to_query(PowerFeedFilter(voltage_min=Decimal("1.5"), voltage_max=Decimal("9.5")))
    assert query["voltageMin"] == str(Decimal("1.5"))
    assert query["voltageMax"] == str(Decimal("9.5"))


def test_voltage_zero_is_not_dropped():
    assert to_query(PowerFeedFilter(voltage_min=0))["voltageMin"] == "0"


def test_amperage_range_is_sent():
    query = to_query(PowerFeedFilter(amperage_min=Decimal("1.5"), amperage_max=Decimal("9.5")))
    assert query["amperageMin"] == str(Decimal("1.5"))
    assert query["amperageMax"] == str(Decimal("9.5"))


def test_amperage_zero_is_not_dropped():
    assert to_query(PowerFeedFilter(amperage_min=0))["amperageMin"] == "0"


def test_phase_is_trimmed_into_query():
    assert to_query(PowerFeedFilter(phase="  abc ")) == {"phase": "abc"}


def test_phase_blank_is_omitted():
    assert "phase" not in to_query(PowerFeedFilter(phase=""))


def test_breaker_label_is_trimmed_into_query():
    assert to_query(PowerFeedFilter(breaker_label="  abc ")) == {"breakerLabel": "abc"}


def test_breaker_label_blank_is_omitted():
    assert "breakerLabel" not in to_query(PowerFeedFilter(breaker_label=""))


def test_redundancy_group_is_trimmed_into_query():
    assert to_query(PowerFeedFilter(redundancy_group="  abc ")) == {"redundancyGroup": "abc"}


def test_redundancy_group_blank_is_omitted():
    assert "redundancyGroup" not in to_query(PowerFeedFilter(redundancy_group=""))


def test_owner_is_trimmed_into_query():
    assert to_query(PowerFeedFilter(owner="  abc ")) == {"owner": "abc"}


def test_owner_blank_is_omitted():
    assert "owner" not in to_query(PowerFeedFilter(owner=""))


def test_criticality_values_are_comma_joined():
    members = list(PowerFeedCriticality)[:2]
    query = to_query(PowerFeedFilter(criticality=members))
    assert query["criticality"] == ",".join(m.value for m in members)


def test_cost_center_is_trimmed_into_query():
    assert to_query(PowerFeedFilter(cost_center="  abc ")) == {"costCenter": "abc"}


def test_cost_center_blank_is_omitted():
    assert "costCenter" not in to_query(PowerFeedFilter(cost_center=""))


def test_effective_from_window_is_iso_formatted():
    query = to_query(PowerFeedFilter(effective_from_from=date(2026, 1, 1), effective_from_to=date(2026, 1, 1)))
    assert query["effectiveFromFrom"].startswith("2026-01-01")
    assert query["effectiveFromTo"].startswith("2026-01-01")

