"""Filter translation tests for Link."""
from datetime import date, datetime, timezone
from decimal import Decimal
from uuid import UUID

from itta_core.models import LifecycleStatus
from itta_api.domains.topology.link.filters import LinkFilter, to_query
from itta_api.domains.topology.link.schemas import *  # noqa: F401,F403


def test_empty_filter_has_no_params():
    assert to_query(LinkFilter()) == {}


def test_free_text_is_trimmed():
    assert to_query(LinkFilter(q="  core ")) == {"q": "core"}


def test_status_list_is_comma_joined():
    query = to_query(LinkFilter(status=[LifecycleStatus.ACTIVE, LifecycleStatus.PLANNED]))
    assert query["status"] == "ACTIVE,PLANNED"


def test_code_is_trimmed_into_query():
    assert to_query(LinkFilter(code="  abc ")) == {"code": "abc"}


def test_code_blank_is_omitted():
    assert "code" not in to_query(LinkFilter(code=""))


def test_name_is_trimmed_into_query():
    assert to_query(LinkFilter(name="  abc ")) == {"name": "abc"}


def test_name_blank_is_omitted():
    assert "name" not in to_query(LinkFilter(name=""))


def test_external_ref_is_trimmed_into_query():
    assert to_query(LinkFilter(external_ref="  abc ")) == {"externalRef": "abc"}


def test_external_ref_blank_is_omitted():
    assert "externalRef" not in to_query(LinkFilter(external_ref=""))


def test_a_interface_id_is_stringified():
    value = UUID("00000000-0000-0000-0000-000000000011")
    assert to_query(LinkFilter(a_interface_id=value))["aInterfaceId"] == str(value)


def test_z_interface_id_is_stringified():
    value = UUID("00000000-0000-0000-0000-000000000011")
    assert to_query(LinkFilter(z_interface_id=value))["zInterfaceId"] == str(value)


def test_link_type_values_are_comma_joined():
    members = list(LinkLinkType)[:2]
    query = to_query(LinkFilter(link_type=members))
    assert query["linkType"] == ",".join(m.value for m in members)


def test_speed_mbps_range_is_sent():
    query = to_query(LinkFilter(speed_mbps_min=1, speed_mbps_max=9))
    assert query["speedMbpsMin"] == str(1)
    assert query["speedMbpsMax"] == str(9)


def test_speed_mbps_zero_is_not_dropped():
    assert to_query(LinkFilter(speed_mbps_min=0))["speedMbpsMin"] == "0"


def test_discovered_by_is_trimmed_into_query():
    assert to_query(LinkFilter(discovered_by="  abc ")) == {"discoveredBy": "abc"}


def test_discovered_by_blank_is_omitted():
    assert "discoveredBy" not in to_query(LinkFilter(discovered_by=""))


def test_region_is_trimmed_into_query():
    assert to_query(LinkFilter(region="  abc ")) == {"region": "abc"}


def test_region_blank_is_omitted():
    assert "region" not in to_query(LinkFilter(region=""))


def test_owner_is_trimmed_into_query():
    assert to_query(LinkFilter(owner="  abc ")) == {"owner": "abc"}


def test_owner_blank_is_omitted():
    assert "owner" not in to_query(LinkFilter(owner=""))


def test_criticality_values_are_comma_joined():
    members = list(LinkCriticality)[:2]
    query = to_query(LinkFilter(criticality=members))
    assert query["criticality"] == ",".join(m.value for m in members)


def test_cost_center_is_trimmed_into_query():
    assert to_query(LinkFilter(cost_center="  abc ")) == {"costCenter": "abc"}


def test_cost_center_blank_is_omitted():
    assert "costCenter" not in to_query(LinkFilter(cost_center=""))

