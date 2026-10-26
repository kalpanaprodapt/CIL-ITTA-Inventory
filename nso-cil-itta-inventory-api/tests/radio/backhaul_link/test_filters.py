"""Filter translation tests for Backhaul Link."""
from datetime import date, datetime, timezone
from decimal import Decimal
from uuid import UUID

from itta_core.models import LifecycleStatus
from itta_api.domains.radio.backhaul_link.filters import BackhaulLinkFilter, to_query
from itta_api.domains.radio.backhaul_link.schemas import *  # noqa: F401,F403


def test_empty_filter_has_no_params():
    assert to_query(BackhaulLinkFilter()) == {}


def test_free_text_is_trimmed():
    assert to_query(BackhaulLinkFilter(q="  core ")) == {"q": "core"}


def test_status_list_is_comma_joined():
    query = to_query(BackhaulLinkFilter(status=[LifecycleStatus.ACTIVE, LifecycleStatus.PLANNED]))
    assert query["status"] == "ACTIVE,PLANNED"


def test_code_is_trimmed_into_query():
    assert to_query(BackhaulLinkFilter(code="  abc ")) == {"code": "abc"}


def test_code_blank_is_omitted():
    assert "code" not in to_query(BackhaulLinkFilter(code=""))


def test_name_is_trimmed_into_query():
    assert to_query(BackhaulLinkFilter(name="  abc ")) == {"name": "abc"}


def test_name_blank_is_omitted():
    assert "name" not in to_query(BackhaulLinkFilter(name=""))


def test_external_ref_is_trimmed_into_query():
    assert to_query(BackhaulLinkFilter(external_ref="  abc ")) == {"externalRef": "abc"}


def test_external_ref_blank_is_omitted():
    assert "externalRef" not in to_query(BackhaulLinkFilter(external_ref=""))


def test_base_station_id_is_stringified():
    value = UUID("00000000-0000-0000-0000-000000000011")
    assert to_query(BackhaulLinkFilter(base_station_id=value))["baseStationId"] == str(value)


def test_link_medium_values_are_comma_joined():
    members = list(BackhaulLinkLinkMedium)[:2]
    query = to_query(BackhaulLinkFilter(link_medium=members))
    assert query["linkMedium"] == ",".join(m.value for m in members)


def test_capacity_mbps_range_is_sent():
    query = to_query(BackhaulLinkFilter(capacity_mbps_min=1, capacity_mbps_max=9))
    assert query["capacityMbpsMin"] == str(1)
    assert query["capacityMbpsMax"] == str(9)


def test_capacity_mbps_zero_is_not_dropped():
    assert to_query(BackhaulLinkFilter(capacity_mbps_min=0))["capacityMbpsMin"] == "0"


def test_latency_ms_range_is_sent():
    query = to_query(BackhaulLinkFilter(latency_ms_min=Decimal("1.5"), latency_ms_max=Decimal("9.5")))
    assert query["latencyMsMin"] == str(Decimal("1.5"))
    assert query["latencyMsMax"] == str(Decimal("9.5"))


def test_latency_ms_zero_is_not_dropped():
    assert to_query(BackhaulLinkFilter(latency_ms_min=0))["latencyMsMin"] == "0"


def test_circuit_ref_is_trimmed_into_query():
    assert to_query(BackhaulLinkFilter(circuit_ref="  abc ")) == {"circuitRef": "abc"}


def test_circuit_ref_blank_is_omitted():
    assert "circuitRef" not in to_query(BackhaulLinkFilter(circuit_ref=""))


def test_cost_center_is_trimmed_into_query():
    assert to_query(BackhaulLinkFilter(cost_center="  abc ")) == {"costCenter": "abc"}


def test_cost_center_blank_is_omitted():
    assert "costCenter" not in to_query(BackhaulLinkFilter(cost_center=""))


def test_effective_from_window_is_iso_formatted():
    query = to_query(BackhaulLinkFilter(effective_from_from=date(2026, 1, 1), effective_from_to=date(2026, 1, 1)))
    assert query["effectiveFromFrom"].startswith("2026-01-01")
    assert query["effectiveFromTo"].startswith("2026-01-01")


def test_effective_to_window_is_iso_formatted():
    query = to_query(BackhaulLinkFilter(effective_to_from=date(2026, 1, 1), effective_to_to=date(2026, 1, 1)))
    assert query["effectiveToFrom"].startswith("2026-01-01")
    assert query["effectiveToTo"].startswith("2026-01-01")


def test_labels_is_trimmed_into_query():
    assert to_query(BackhaulLinkFilter(labels="  abc ")) == {"labels": "abc"}


def test_labels_blank_is_omitted():
    assert "labels" not in to_query(BackhaulLinkFilter(labels=""))

