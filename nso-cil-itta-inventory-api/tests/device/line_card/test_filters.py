"""Filter translation tests for Line Card."""
from datetime import date, datetime, timezone
from decimal import Decimal
from uuid import UUID

from itta_core.models import LifecycleStatus
from itta_api.domains.device.line_card.filters import LineCardFilter, to_query
from itta_api.domains.device.line_card.schemas import *  # noqa: F401,F403


def test_empty_filter_has_no_params():
    assert to_query(LineCardFilter()) == {}


def test_free_text_is_trimmed():
    assert to_query(LineCardFilter(q="  core ")) == {"q": "core"}


def test_status_list_is_comma_joined():
    query = to_query(LineCardFilter(status=[LifecycleStatus.ACTIVE, LifecycleStatus.PLANNED]))
    assert query["status"] == "ACTIVE,PLANNED"


def test_code_is_trimmed_into_query():
    assert to_query(LineCardFilter(code="  abc ")) == {"code": "abc"}


def test_code_blank_is_omitted():
    assert "code" not in to_query(LineCardFilter(code=""))


def test_name_is_trimmed_into_query():
    assert to_query(LineCardFilter(name="  abc ")) == {"name": "abc"}


def test_name_blank_is_omitted():
    assert "name" not in to_query(LineCardFilter(name=""))


def test_external_ref_is_trimmed_into_query():
    assert to_query(LineCardFilter(external_ref="  abc ")) == {"externalRef": "abc"}


def test_external_ref_blank_is_omitted():
    assert "externalRef" not in to_query(LineCardFilter(external_ref=""))


def test_chassis_id_is_stringified():
    value = UUID("00000000-0000-0000-0000-000000000011")
    assert to_query(LineCardFilter(chassis_id=value))["chassisId"] == str(value)


def test_slot_number_range_is_sent():
    query = to_query(LineCardFilter(slot_number_min=1, slot_number_max=9))
    assert query["slotNumberMin"] == str(1)
    assert query["slotNumberMax"] == str(9)


def test_slot_number_zero_is_not_dropped():
    assert to_query(LineCardFilter(slot_number_min=0))["slotNumberMin"] == "0"


def test_card_type_is_trimmed_into_query():
    assert to_query(LineCardFilter(card_type="  abc ")) == {"cardType": "abc"}


def test_card_type_blank_is_omitted():
    assert "cardType" not in to_query(LineCardFilter(card_type=""))


def test_port_count_range_is_sent():
    query = to_query(LineCardFilter(port_count_min=1, port_count_max=9))
    assert query["portCountMin"] == str(1)
    assert query["portCountMax"] == str(9)


def test_port_count_zero_is_not_dropped():
    assert to_query(LineCardFilter(port_count_min=0))["portCountMin"] == "0"


def test_serial_number_is_trimmed_into_query():
    assert to_query(LineCardFilter(serial_number="  abc ")) == {"serialNumber": "abc"}


def test_serial_number_blank_is_omitted():
    assert "serialNumber" not in to_query(LineCardFilter(serial_number=""))


def test_firmware_is_trimmed_into_query():
    assert to_query(LineCardFilter(firmware="  abc ")) == {"firmware": "abc"}


def test_firmware_blank_is_omitted():
    assert "firmware" not in to_query(LineCardFilter(firmware=""))


def test_region_is_trimmed_into_query():
    assert to_query(LineCardFilter(region="  abc ")) == {"region": "abc"}


def test_region_blank_is_omitted():
    assert "region" not in to_query(LineCardFilter(region=""))


def test_owner_is_trimmed_into_query():
    assert to_query(LineCardFilter(owner="  abc ")) == {"owner": "abc"}


def test_owner_blank_is_omitted():
    assert "owner" not in to_query(LineCardFilter(owner=""))


def test_criticality_values_are_comma_joined():
    members = list(LineCardCriticality)[:2]
    query = to_query(LineCardFilter(criticality=members))
    assert query["criticality"] == ",".join(m.value for m in members)


def test_cost_center_is_trimmed_into_query():
    assert to_query(LineCardFilter(cost_center="  abc ")) == {"costCenter": "abc"}


def test_cost_center_blank_is_omitted():
    assert "costCenter" not in to_query(LineCardFilter(cost_center=""))

