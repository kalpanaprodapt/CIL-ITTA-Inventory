"""Filter translation tests for Interface."""
from datetime import date, datetime, timezone
from decimal import Decimal
from uuid import UUID

from itta_core.models import LifecycleStatus
from itta_api.domains.port.interface.filters import InterfaceFilter, to_query
from itta_api.domains.port.interface.schemas import *  # noqa: F401,F403


def test_empty_filter_has_no_params():
    assert to_query(InterfaceFilter()) == {}


def test_free_text_is_trimmed():
    assert to_query(InterfaceFilter(q="  core ")) == {"q": "core"}


def test_status_list_is_comma_joined():
    query = to_query(InterfaceFilter(status=[LifecycleStatus.ACTIVE, LifecycleStatus.PLANNED]))
    assert query["status"] == "ACTIVE,PLANNED"


def test_code_is_trimmed_into_query():
    assert to_query(InterfaceFilter(code="  abc ")) == {"code": "abc"}


def test_code_blank_is_omitted():
    assert "code" not in to_query(InterfaceFilter(code=""))


def test_name_is_trimmed_into_query():
    assert to_query(InterfaceFilter(name="  abc ")) == {"name": "abc"}


def test_name_blank_is_omitted():
    assert "name" not in to_query(InterfaceFilter(name=""))


def test_external_ref_is_trimmed_into_query():
    assert to_query(InterfaceFilter(external_ref="  abc ")) == {"externalRef": "abc"}


def test_external_ref_blank_is_omitted():
    assert "externalRef" not in to_query(InterfaceFilter(external_ref=""))


def test_port_id_is_stringified():
    value = UUID("00000000-0000-0000-0000-000000000011")
    assert to_query(InterfaceFilter(port_id=value))["portId"] == str(value)


def test_if_name_is_trimmed_into_query():
    assert to_query(InterfaceFilter(if_name="  abc ")) == {"ifName": "abc"}


def test_if_name_blank_is_omitted():
    assert "ifName" not in to_query(InterfaceFilter(if_name=""))


def test_if_index_range_is_sent():
    query = to_query(InterfaceFilter(if_index_min=1, if_index_max=9))
    assert query["ifIndexMin"] == str(1)
    assert query["ifIndexMax"] == str(9)


def test_if_index_zero_is_not_dropped():
    assert to_query(InterfaceFilter(if_index_min=0))["ifIndexMin"] == "0"


def test_mac_address_is_trimmed_into_query():
    assert to_query(InterfaceFilter(mac_address="  abc ")) == {"macAddress": "abc"}


def test_mac_address_blank_is_omitted():
    assert "macAddress" not in to_query(InterfaceFilter(mac_address=""))


def test_mtu_range_is_sent():
    query = to_query(InterfaceFilter(mtu_min=1, mtu_max=9))
    assert query["mtuMin"] == str(1)
    assert query["mtuMax"] == str(9)


def test_mtu_zero_is_not_dropped():
    assert to_query(InterfaceFilter(mtu_min=0))["mtuMin"] == "0"


def test_oper_state_values_are_comma_joined():
    members = list(InterfaceOperState)[:2]
    query = to_query(InterfaceFilter(oper_state=members))
    assert query["operState"] == ",".join(m.value for m in members)


def test_owner_is_trimmed_into_query():
    assert to_query(InterfaceFilter(owner="  abc ")) == {"owner": "abc"}


def test_owner_blank_is_omitted():
    assert "owner" not in to_query(InterfaceFilter(owner=""))


def test_criticality_values_are_comma_joined():
    members = list(InterfaceCriticality)[:2]
    query = to_query(InterfaceFilter(criticality=members))
    assert query["criticality"] == ",".join(m.value for m in members)


def test_cost_center_is_trimmed_into_query():
    assert to_query(InterfaceFilter(cost_center="  abc ")) == {"costCenter": "abc"}


def test_cost_center_blank_is_omitted():
    assert "costCenter" not in to_query(InterfaceFilter(cost_center=""))


def test_effective_from_window_is_iso_formatted():
    query = to_query(InterfaceFilter(effective_from_from=date(2026, 1, 1), effective_from_to=date(2026, 1, 1)))
    assert query["effectiveFromFrom"].startswith("2026-01-01")
    assert query["effectiveFromTo"].startswith("2026-01-01")

