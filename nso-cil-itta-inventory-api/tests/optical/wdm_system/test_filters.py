"""Filter translation tests for Wdm System."""
from datetime import date, datetime, timezone
from decimal import Decimal
from uuid import UUID

from itta_core.models import LifecycleStatus
from itta_api.domains.optical.wdm_system.filters import WdmSystemFilter, to_query
from itta_api.domains.optical.wdm_system.schemas import *  # noqa: F401,F403


def test_empty_filter_has_no_params():
    assert to_query(WdmSystemFilter()) == {}


def test_free_text_is_trimmed():
    assert to_query(WdmSystemFilter(q="  core ")) == {"q": "core"}


def test_status_list_is_comma_joined():
    query = to_query(WdmSystemFilter(status=[LifecycleStatus.ACTIVE, LifecycleStatus.PLANNED]))
    assert query["status"] == "ACTIVE,PLANNED"


def test_code_is_trimmed_into_query():
    assert to_query(WdmSystemFilter(code="  abc ")) == {"code": "abc"}


def test_code_blank_is_omitted():
    assert "code" not in to_query(WdmSystemFilter(code=""))


def test_name_is_trimmed_into_query():
    assert to_query(WdmSystemFilter(name="  abc ")) == {"name": "abc"}


def test_name_blank_is_omitted():
    assert "name" not in to_query(WdmSystemFilter(name=""))


def test_external_ref_is_trimmed_into_query():
    assert to_query(WdmSystemFilter(external_ref="  abc ")) == {"externalRef": "abc"}


def test_external_ref_blank_is_omitted():
    assert "externalRef" not in to_query(WdmSystemFilter(external_ref=""))


def test_system_name_is_trimmed_into_query():
    assert to_query(WdmSystemFilter(system_name="  abc ")) == {"systemName": "abc"}


def test_system_name_blank_is_omitted():
    assert "systemName" not in to_query(WdmSystemFilter(system_name=""))


def test_channel_capacity_range_is_sent():
    query = to_query(WdmSystemFilter(channel_capacity_min=1, channel_capacity_max=9))
    assert query["channelCapacityMin"] == str(1)
    assert query["channelCapacityMax"] == str(9)


def test_channel_capacity_zero_is_not_dropped():
    assert to_query(WdmSystemFilter(channel_capacity_min=0))["channelCapacityMin"] == "0"


def test_band_values_are_comma_joined():
    members = list(WdmSystemBand)[:2]
    query = to_query(WdmSystemFilter(band=members))
    assert query["band"] == ",".join(m.value for m in members)


def test_span_count_range_is_sent():
    query = to_query(WdmSystemFilter(span_count_min=1, span_count_max=9))
    assert query["spanCountMin"] == str(1)
    assert query["spanCountMax"] == str(9)


def test_span_count_zero_is_not_dropped():
    assert to_query(WdmSystemFilter(span_count_min=0))["spanCountMin"] == "0"


def test_vendor_id_is_stringified():
    value = UUID("00000000-0000-0000-0000-000000000011")
    assert to_query(WdmSystemFilter(vendor_id=value))["vendorId"] == str(value)


def test_effective_from_window_is_iso_formatted():
    query = to_query(WdmSystemFilter(effective_from_from=date(2026, 1, 1), effective_from_to=date(2026, 1, 1)))
    assert query["effectiveFromFrom"].startswith("2026-01-01")
    assert query["effectiveFromTo"].startswith("2026-01-01")


def test_effective_to_window_is_iso_formatted():
    query = to_query(WdmSystemFilter(effective_to_from=date(2026, 1, 1), effective_to_to=date(2026, 1, 1)))
    assert query["effectiveToFrom"].startswith("2026-01-01")
    assert query["effectiveToTo"].startswith("2026-01-01")


def test_labels_is_trimmed_into_query():
    assert to_query(WdmSystemFilter(labels="  abc ")) == {"labels": "abc"}


def test_labels_blank_is_omitted():
    assert "labels" not in to_query(WdmSystemFilter(labels=""))


def test_lifecycle_stage_values_are_comma_joined():
    members = list(WdmSystemLifecycleStage)[:2]
    query = to_query(WdmSystemFilter(lifecycle_stage=members))
    assert query["lifecycleStage"] == ",".join(m.value for m in members)

