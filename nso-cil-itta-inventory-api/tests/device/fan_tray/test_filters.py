"""Filter translation tests for Fan Tray."""
from datetime import date, datetime, timezone
from decimal import Decimal
from uuid import UUID

from itta_core.models import LifecycleStatus
from itta_api.domains.device.fan_tray.filters import FanTrayFilter, to_query
from itta_api.domains.device.fan_tray.schemas import *  # noqa: F401,F403


def test_empty_filter_has_no_params():
    assert to_query(FanTrayFilter()) == {}


def test_free_text_is_trimmed():
    assert to_query(FanTrayFilter(q="  core ")) == {"q": "core"}


def test_status_list_is_comma_joined():
    query = to_query(FanTrayFilter(status=[LifecycleStatus.ACTIVE, LifecycleStatus.PLANNED]))
    assert query["status"] == "ACTIVE,PLANNED"


def test_code_is_trimmed_into_query():
    assert to_query(FanTrayFilter(code="  abc ")) == {"code": "abc"}


def test_code_blank_is_omitted():
    assert "code" not in to_query(FanTrayFilter(code=""))


def test_name_is_trimmed_into_query():
    assert to_query(FanTrayFilter(name="  abc ")) == {"name": "abc"}


def test_name_blank_is_omitted():
    assert "name" not in to_query(FanTrayFilter(name=""))


def test_external_ref_is_trimmed_into_query():
    assert to_query(FanTrayFilter(external_ref="  abc ")) == {"externalRef": "abc"}


def test_external_ref_blank_is_omitted():
    assert "externalRef" not in to_query(FanTrayFilter(external_ref=""))


def test_chassis_id_is_stringified():
    value = UUID("00000000-0000-0000-0000-000000000011")
    assert to_query(FanTrayFilter(chassis_id=value))["chassisId"] == str(value)


def test_slot_number_range_is_sent():
    query = to_query(FanTrayFilter(slot_number_min=1, slot_number_max=9))
    assert query["slotNumberMin"] == str(1)
    assert query["slotNumberMax"] == str(9)


def test_slot_number_zero_is_not_dropped():
    assert to_query(FanTrayFilter(slot_number_min=0))["slotNumberMin"] == "0"


def test_fan_count_range_is_sent():
    query = to_query(FanTrayFilter(fan_count_min=1, fan_count_max=9))
    assert query["fanCountMin"] == str(1)
    assert query["fanCountMax"] == str(9)


def test_fan_count_zero_is_not_dropped():
    assert to_query(FanTrayFilter(fan_count_min=0))["fanCountMin"] == "0"


def test_airflow_values_are_comma_joined():
    members = list(FanTrayAirflow)[:2]
    query = to_query(FanTrayFilter(airflow=members))
    assert query["airflow"] == ",".join(m.value for m in members)


def test_serial_number_is_trimmed_into_query():
    assert to_query(FanTrayFilter(serial_number="  abc ")) == {"serialNumber": "abc"}


def test_serial_number_blank_is_omitted():
    assert "serialNumber" not in to_query(FanTrayFilter(serial_number=""))


def test_effective_from_window_is_iso_formatted():
    query = to_query(FanTrayFilter(effective_from_from=date(2026, 1, 1), effective_from_to=date(2026, 1, 1)))
    assert query["effectiveFromFrom"].startswith("2026-01-01")
    assert query["effectiveFromTo"].startswith("2026-01-01")


def test_effective_to_window_is_iso_formatted():
    query = to_query(FanTrayFilter(effective_to_from=date(2026, 1, 1), effective_to_to=date(2026, 1, 1)))
    assert query["effectiveToFrom"].startswith("2026-01-01")
    assert query["effectiveToTo"].startswith("2026-01-01")


def test_labels_is_trimmed_into_query():
    assert to_query(FanTrayFilter(labels="  abc ")) == {"labels": "abc"}


def test_labels_blank_is_omitted():
    assert "labels" not in to_query(FanTrayFilter(labels=""))


def test_lifecycle_stage_values_are_comma_joined():
    members = list(FanTrayLifecycleStage)[:2]
    query = to_query(FanTrayFilter(lifecycle_stage=members))
    assert query["lifecycleStage"] == ",".join(m.value for m in members)

