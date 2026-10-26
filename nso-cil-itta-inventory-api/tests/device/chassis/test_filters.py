"""Filter translation tests for Chassis."""
from datetime import date, datetime, timezone
from decimal import Decimal
from uuid import UUID

from itta_core.models import LifecycleStatus
from itta_api.domains.device.chassis.filters import ChassisFilter, to_query
from itta_api.domains.device.chassis.schemas import *  # noqa: F401,F403


def test_empty_filter_has_no_params():
    assert to_query(ChassisFilter()) == {}


def test_free_text_is_trimmed():
    assert to_query(ChassisFilter(q="  core ")) == {"q": "core"}


def test_status_list_is_comma_joined():
    query = to_query(ChassisFilter(status=[LifecycleStatus.ACTIVE, LifecycleStatus.PLANNED]))
    assert query["status"] == "ACTIVE,PLANNED"


def test_code_is_trimmed_into_query():
    assert to_query(ChassisFilter(code="  abc ")) == {"code": "abc"}


def test_code_blank_is_omitted():
    assert "code" not in to_query(ChassisFilter(code=""))


def test_name_is_trimmed_into_query():
    assert to_query(ChassisFilter(name="  abc ")) == {"name": "abc"}


def test_name_blank_is_omitted():
    assert "name" not in to_query(ChassisFilter(name=""))


def test_external_ref_is_trimmed_into_query():
    assert to_query(ChassisFilter(external_ref="  abc ")) == {"externalRef": "abc"}


def test_external_ref_blank_is_omitted():
    assert "externalRef" not in to_query(ChassisFilter(external_ref=""))


def test_device_id_is_stringified():
    value = UUID("00000000-0000-0000-0000-000000000011")
    assert to_query(ChassisFilter(device_id=value))["deviceId"] == str(value)


def test_slot_count_range_is_sent():
    query = to_query(ChassisFilter(slot_count_min=1, slot_count_max=9))
    assert query["slotCountMin"] == str(1)
    assert query["slotCountMax"] == str(9)


def test_slot_count_zero_is_not_dropped():
    assert to_query(ChassisFilter(slot_count_min=0))["slotCountMin"] == "0"


def test_serial_number_is_trimmed_into_query():
    assert to_query(ChassisFilter(serial_number="  abc ")) == {"serialNumber": "abc"}


def test_serial_number_blank_is_omitted():
    assert "serialNumber" not in to_query(ChassisFilter(serial_number=""))


def test_backplane_gbps_range_is_sent():
    query = to_query(ChassisFilter(backplane_gbps_min=Decimal("1.5"), backplane_gbps_max=Decimal("9.5")))
    assert query["backplaneGbpsMin"] == str(Decimal("1.5"))
    assert query["backplaneGbpsMax"] == str(Decimal("9.5"))


def test_backplane_gbps_zero_is_not_dropped():
    assert to_query(ChassisFilter(backplane_gbps_min=0))["backplaneGbpsMin"] == "0"


def test_redundancy_mode_is_trimmed_into_query():
    assert to_query(ChassisFilter(redundancy_mode="  abc ")) == {"redundancyMode": "abc"}


def test_redundancy_mode_blank_is_omitted():
    assert "redundancyMode" not in to_query(ChassisFilter(redundancy_mode=""))


def test_lifecycle_stage_values_are_comma_joined():
    members = list(ChassisLifecycleStage)[:2]
    query = to_query(ChassisFilter(lifecycle_stage=members))
    assert query["lifecycleStage"] == ",".join(m.value for m in members)


def test_source_system_is_trimmed_into_query():
    assert to_query(ChassisFilter(source_system="  abc ")) == {"sourceSystem": "abc"}


def test_source_system_blank_is_omitted():
    assert "sourceSystem" not in to_query(ChassisFilter(source_system=""))


def test_region_is_trimmed_into_query():
    assert to_query(ChassisFilter(region="  abc ")) == {"region": "abc"}


def test_region_blank_is_omitted():
    assert "region" not in to_query(ChassisFilter(region=""))


def test_owner_is_trimmed_into_query():
    assert to_query(ChassisFilter(owner="  abc ")) == {"owner": "abc"}


def test_owner_blank_is_omitted():
    assert "owner" not in to_query(ChassisFilter(owner=""))


def test_criticality_values_are_comma_joined():
    members = list(ChassisCriticality)[:2]
    query = to_query(ChassisFilter(criticality=members))
    assert query["criticality"] == ",".join(m.value for m in members)

