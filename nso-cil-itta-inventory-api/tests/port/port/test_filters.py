"""Filter translation tests for Port."""
from datetime import date, datetime, timezone
from decimal import Decimal
from uuid import UUID

from itta_core.models import LifecycleStatus
from itta_api.domains.port.port.filters import PortFilter, to_query
from itta_api.domains.port.port.schemas import *  # noqa: F401,F403


def test_empty_filter_has_no_params():
    assert to_query(PortFilter()) == {}


def test_free_text_is_trimmed():
    assert to_query(PortFilter(q="  core ")) == {"q": "core"}


def test_status_list_is_comma_joined():
    query = to_query(PortFilter(status=[LifecycleStatus.ACTIVE, LifecycleStatus.PLANNED]))
    assert query["status"] == "ACTIVE,PLANNED"


def test_code_is_trimmed_into_query():
    assert to_query(PortFilter(code="  abc ")) == {"code": "abc"}


def test_code_blank_is_omitted():
    assert "code" not in to_query(PortFilter(code=""))


def test_name_is_trimmed_into_query():
    assert to_query(PortFilter(name="  abc ")) == {"name": "abc"}


def test_name_blank_is_omitted():
    assert "name" not in to_query(PortFilter(name=""))


def test_external_ref_is_trimmed_into_query():
    assert to_query(PortFilter(external_ref="  abc ")) == {"externalRef": "abc"}


def test_external_ref_blank_is_omitted():
    assert "externalRef" not in to_query(PortFilter(external_ref=""))


def test_device_id_is_stringified():
    value = UUID("00000000-0000-0000-0000-000000000011")
    assert to_query(PortFilter(device_id=value))["deviceId"] == str(value)


def test_card_id_is_stringified():
    value = UUID("00000000-0000-0000-0000-000000000011")
    assert to_query(PortFilter(card_id=value))["cardId"] == str(value)


def test_port_number_is_trimmed_into_query():
    assert to_query(PortFilter(port_number="  abc ")) == {"portNumber": "abc"}


def test_port_number_blank_is_omitted():
    assert "portNumber" not in to_query(PortFilter(port_number=""))


def test_speed_mbps_range_is_sent():
    query = to_query(PortFilter(speed_mbps_min=1, speed_mbps_max=9))
    assert query["speedMbpsMin"] == str(1)
    assert query["speedMbpsMax"] == str(9)


def test_speed_mbps_zero_is_not_dropped():
    assert to_query(PortFilter(speed_mbps_min=0))["speedMbpsMin"] == "0"


def test_media_type_values_are_comma_joined():
    members = list(PortMediaType)[:2]
    query = to_query(PortFilter(media_type=members))
    assert query["mediaType"] == ",".join(m.value for m in members)


def test_admin_state_values_are_comma_joined():
    members = list(PortAdminState)[:2]
    query = to_query(PortFilter(admin_state=members))
    assert query["adminState"] == ",".join(m.value for m in members)


def test_source_system_is_trimmed_into_query():
    assert to_query(PortFilter(source_system="  abc ")) == {"sourceSystem": "abc"}


def test_source_system_blank_is_omitted():
    assert "sourceSystem" not in to_query(PortFilter(source_system=""))


def test_region_is_trimmed_into_query():
    assert to_query(PortFilter(region="  abc ")) == {"region": "abc"}


def test_region_blank_is_omitted():
    assert "region" not in to_query(PortFilter(region=""))


def test_owner_is_trimmed_into_query():
    assert to_query(PortFilter(owner="  abc ")) == {"owner": "abc"}


def test_owner_blank_is_omitted():
    assert "owner" not in to_query(PortFilter(owner=""))


def test_criticality_values_are_comma_joined():
    members = list(PortCriticality)[:2]
    query = to_query(PortFilter(criticality=members))
    assert query["criticality"] == ",".join(m.value for m in members)


def test_cost_center_is_trimmed_into_query():
    assert to_query(PortFilter(cost_center="  abc ")) == {"costCenter": "abc"}


def test_cost_center_blank_is_omitted():
    assert "costCenter" not in to_query(PortFilter(cost_center=""))

