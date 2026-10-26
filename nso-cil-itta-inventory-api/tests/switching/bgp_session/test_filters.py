"""Filter translation tests for Bgp Session."""
from datetime import date, datetime, timezone
from decimal import Decimal
from uuid import UUID

from itta_core.models import LifecycleStatus
from itta_api.domains.switching.bgp_session.filters import BgpSessionFilter, to_query
from itta_api.domains.switching.bgp_session.schemas import *  # noqa: F401,F403


def test_empty_filter_has_no_params():
    assert to_query(BgpSessionFilter()) == {}


def test_free_text_is_trimmed():
    assert to_query(BgpSessionFilter(q="  core ")) == {"q": "core"}


def test_status_list_is_comma_joined():
    query = to_query(BgpSessionFilter(status=[LifecycleStatus.ACTIVE, LifecycleStatus.PLANNED]))
    assert query["status"] == "ACTIVE,PLANNED"


def test_code_is_trimmed_into_query():
    assert to_query(BgpSessionFilter(code="  abc ")) == {"code": "abc"}


def test_code_blank_is_omitted():
    assert "code" not in to_query(BgpSessionFilter(code=""))


def test_name_is_trimmed_into_query():
    assert to_query(BgpSessionFilter(name="  abc ")) == {"name": "abc"}


def test_name_blank_is_omitted():
    assert "name" not in to_query(BgpSessionFilter(name=""))


def test_external_ref_is_trimmed_into_query():
    assert to_query(BgpSessionFilter(external_ref="  abc ")) == {"externalRef": "abc"}


def test_external_ref_blank_is_omitted():
    assert "externalRef" not in to_query(BgpSessionFilter(external_ref=""))


def test_local_device_id_is_stringified():
    value = UUID("00000000-0000-0000-0000-000000000011")
    assert to_query(BgpSessionFilter(local_device_id=value))["localDeviceId"] == str(value)


def test_peer_address_is_trimmed_into_query():
    assert to_query(BgpSessionFilter(peer_address="  abc ")) == {"peerAddress": "abc"}


def test_peer_address_blank_is_omitted():
    assert "peerAddress" not in to_query(BgpSessionFilter(peer_address=""))


def test_local_asn_range_is_sent():
    query = to_query(BgpSessionFilter(local_asn_min=1, local_asn_max=9))
    assert query["localAsnMin"] == str(1)
    assert query["localAsnMax"] == str(9)


def test_local_asn_zero_is_not_dropped():
    assert to_query(BgpSessionFilter(local_asn_min=0))["localAsnMin"] == "0"


def test_remote_asn_range_is_sent():
    query = to_query(BgpSessionFilter(remote_asn_min=1, remote_asn_max=9))
    assert query["remoteAsnMin"] == str(1)
    assert query["remoteAsnMax"] == str(9)


def test_remote_asn_zero_is_not_dropped():
    assert to_query(BgpSessionFilter(remote_asn_min=0))["remoteAsnMin"] == "0"


def test_session_state_values_are_comma_joined():
    members = list(BgpSessionSessionState)[:2]
    query = to_query(BgpSessionFilter(session_state=members))
    assert query["sessionState"] == ",".join(m.value for m in members)


def test_prefix_limit_range_is_sent():
    query = to_query(BgpSessionFilter(prefix_limit_min=1, prefix_limit_max=9))
    assert query["prefixLimitMin"] == str(1)
    assert query["prefixLimitMax"] == str(9)


def test_prefix_limit_zero_is_not_dropped():
    assert to_query(BgpSessionFilter(prefix_limit_min=0))["prefixLimitMin"] == "0"


def test_region_is_trimmed_into_query():
    assert to_query(BgpSessionFilter(region="  abc ")) == {"region": "abc"}


def test_region_blank_is_omitted():
    assert "region" not in to_query(BgpSessionFilter(region=""))


def test_owner_is_trimmed_into_query():
    assert to_query(BgpSessionFilter(owner="  abc ")) == {"owner": "abc"}


def test_owner_blank_is_omitted():
    assert "owner" not in to_query(BgpSessionFilter(owner=""))


def test_criticality_values_are_comma_joined():
    members = list(BgpSessionCriticality)[:2]
    query = to_query(BgpSessionFilter(criticality=members))
    assert query["criticality"] == ",".join(m.value for m in members)


def test_cost_center_is_trimmed_into_query():
    assert to_query(BgpSessionFilter(cost_center="  abc ")) == {"costCenter": "abc"}


def test_cost_center_blank_is_omitted():
    assert "costCenter" not in to_query(BgpSessionFilter(cost_center=""))

