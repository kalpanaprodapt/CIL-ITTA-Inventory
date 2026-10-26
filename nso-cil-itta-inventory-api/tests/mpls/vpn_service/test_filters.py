"""Filter translation tests for Vpn Service."""
from datetime import date, datetime, timezone
from decimal import Decimal
from uuid import UUID

from itta_core.models import LifecycleStatus
from itta_api.domains.mpls.vpn_service.filters import VpnServiceFilter, to_query
from itta_api.domains.mpls.vpn_service.schemas import *  # noqa: F401,F403


def test_empty_filter_has_no_params():
    assert to_query(VpnServiceFilter()) == {}


def test_free_text_is_trimmed():
    assert to_query(VpnServiceFilter(q="  core ")) == {"q": "core"}


def test_status_list_is_comma_joined():
    query = to_query(VpnServiceFilter(status=[LifecycleStatus.ACTIVE, LifecycleStatus.PLANNED]))
    assert query["status"] == "ACTIVE,PLANNED"


def test_code_is_trimmed_into_query():
    assert to_query(VpnServiceFilter(code="  abc ")) == {"code": "abc"}


def test_code_blank_is_omitted():
    assert "code" not in to_query(VpnServiceFilter(code=""))


def test_name_is_trimmed_into_query():
    assert to_query(VpnServiceFilter(name="  abc ")) == {"name": "abc"}


def test_name_blank_is_omitted():
    assert "name" not in to_query(VpnServiceFilter(name=""))


def test_external_ref_is_trimmed_into_query():
    assert to_query(VpnServiceFilter(external_ref="  abc ")) == {"externalRef": "abc"}


def test_external_ref_blank_is_omitted():
    assert "externalRef" not in to_query(VpnServiceFilter(external_ref=""))


def test_vpn_name_is_trimmed_into_query():
    assert to_query(VpnServiceFilter(vpn_name="  abc ")) == {"vpnName": "abc"}


def test_vpn_name_blank_is_omitted():
    assert "vpnName" not in to_query(VpnServiceFilter(vpn_name=""))


def test_vpn_type_values_are_comma_joined():
    members = list(VpnServiceVpnType)[:2]
    query = to_query(VpnServiceFilter(vpn_type=members))
    assert query["vpnType"] == ",".join(m.value for m in members)


def test_vrf_id_is_stringified():
    value = UUID("00000000-0000-0000-0000-000000000011")
    assert to_query(VpnServiceFilter(vrf_id=value))["vrfId"] == str(value)


def test_customer_id_is_stringified():
    value = UUID("00000000-0000-0000-0000-000000000011")
    assert to_query(VpnServiceFilter(customer_id=value))["customerId"] == str(value)


def test_topology_values_are_comma_joined():
    members = list(VpnServiceTopology)[:2]
    query = to_query(VpnServiceFilter(topology=members))
    assert query["topology"] == ",".join(m.value for m in members)


def test_owner_is_trimmed_into_query():
    assert to_query(VpnServiceFilter(owner="  abc ")) == {"owner": "abc"}


def test_owner_blank_is_omitted():
    assert "owner" not in to_query(VpnServiceFilter(owner=""))


def test_criticality_values_are_comma_joined():
    members = list(VpnServiceCriticality)[:2]
    query = to_query(VpnServiceFilter(criticality=members))
    assert query["criticality"] == ",".join(m.value for m in members)


def test_cost_center_is_trimmed_into_query():
    assert to_query(VpnServiceFilter(cost_center="  abc ")) == {"costCenter": "abc"}


def test_cost_center_blank_is_omitted():
    assert "costCenter" not in to_query(VpnServiceFilter(cost_center=""))


def test_effective_from_window_is_iso_formatted():
    query = to_query(VpnServiceFilter(effective_from_from=date(2026, 1, 1), effective_from_to=date(2026, 1, 1)))
    assert query["effectiveFromFrom"].startswith("2026-01-01")
    assert query["effectiveFromTo"].startswith("2026-01-01")

