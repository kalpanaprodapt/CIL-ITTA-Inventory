"""Filter translation tests for Sub Interface."""
from datetime import date, datetime, timezone
from decimal import Decimal
from uuid import UUID

from itta_core.models import LifecycleStatus
from itta_api.domains.port.sub_interface.filters import SubInterfaceFilter, to_query
from itta_api.domains.port.sub_interface.schemas import *  # noqa: F401,F403


def test_empty_filter_has_no_params():
    assert to_query(SubInterfaceFilter()) == {}


def test_free_text_is_trimmed():
    assert to_query(SubInterfaceFilter(q="  core ")) == {"q": "core"}


def test_status_list_is_comma_joined():
    query = to_query(SubInterfaceFilter(status=[LifecycleStatus.ACTIVE, LifecycleStatus.PLANNED]))
    assert query["status"] == "ACTIVE,PLANNED"


def test_code_is_trimmed_into_query():
    assert to_query(SubInterfaceFilter(code="  abc ")) == {"code": "abc"}


def test_code_blank_is_omitted():
    assert "code" not in to_query(SubInterfaceFilter(code=""))


def test_name_is_trimmed_into_query():
    assert to_query(SubInterfaceFilter(name="  abc ")) == {"name": "abc"}


def test_name_blank_is_omitted():
    assert "name" not in to_query(SubInterfaceFilter(name=""))


def test_external_ref_is_trimmed_into_query():
    assert to_query(SubInterfaceFilter(external_ref="  abc ")) == {"externalRef": "abc"}


def test_external_ref_blank_is_omitted():
    assert "externalRef" not in to_query(SubInterfaceFilter(external_ref=""))


def test_interface_id_is_stringified():
    value = UUID("00000000-0000-0000-0000-000000000011")
    assert to_query(SubInterfaceFilter(interface_id=value))["interfaceId"] == str(value)


def test_vlan_tag_range_is_sent():
    query = to_query(SubInterfaceFilter(vlan_tag_min=1, vlan_tag_max=9))
    assert query["vlanTagMin"] == str(1)
    assert query["vlanTagMax"] == str(9)


def test_vlan_tag_zero_is_not_dropped():
    assert to_query(SubInterfaceFilter(vlan_tag_min=0))["vlanTagMin"] == "0"


def test_encapsulation_is_trimmed_into_query():
    assert to_query(SubInterfaceFilter(encapsulation="  abc ")) == {"encapsulation": "abc"}


def test_encapsulation_blank_is_omitted():
    assert "encapsulation" not in to_query(SubInterfaceFilter(encapsulation=""))


def test_ipv4_address_is_trimmed_into_query():
    assert to_query(SubInterfaceFilter(ipv4_address="  abc ")) == {"ipv4Address": "abc"}


def test_ipv4_address_blank_is_omitted():
    assert "ipv4Address" not in to_query(SubInterfaceFilter(ipv4_address=""))


def test_ipv6_address_is_trimmed_into_query():
    assert to_query(SubInterfaceFilter(ipv6_address="  abc ")) == {"ipv6Address": "abc"}


def test_ipv6_address_blank_is_omitted():
    assert "ipv6Address" not in to_query(SubInterfaceFilter(ipv6_address=""))


def test_cost_center_is_trimmed_into_query():
    assert to_query(SubInterfaceFilter(cost_center="  abc ")) == {"costCenter": "abc"}


def test_cost_center_blank_is_omitted():
    assert "costCenter" not in to_query(SubInterfaceFilter(cost_center=""))


def test_effective_from_window_is_iso_formatted():
    query = to_query(SubInterfaceFilter(effective_from_from=date(2026, 1, 1), effective_from_to=date(2026, 1, 1)))
    assert query["effectiveFromFrom"].startswith("2026-01-01")
    assert query["effectiveFromTo"].startswith("2026-01-01")


def test_effective_to_window_is_iso_formatted():
    query = to_query(SubInterfaceFilter(effective_to_from=date(2026, 1, 1), effective_to_to=date(2026, 1, 1)))
    assert query["effectiveToFrom"].startswith("2026-01-01")
    assert query["effectiveToTo"].startswith("2026-01-01")


def test_labels_is_trimmed_into_query():
    assert to_query(SubInterfaceFilter(labels="  abc ")) == {"labels": "abc"}


def test_labels_blank_is_omitted():
    assert "labels" not in to_query(SubInterfaceFilter(labels=""))

