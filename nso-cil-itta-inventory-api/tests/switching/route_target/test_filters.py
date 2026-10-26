"""Filter translation tests for Route Target."""
from datetime import date, datetime, timezone
from decimal import Decimal
from uuid import UUID

from itta_core.models import LifecycleStatus
from itta_api.domains.switching.route_target.filters import RouteTargetFilter, to_query
from itta_api.domains.switching.route_target.schemas import *  # noqa: F401,F403


def test_empty_filter_has_no_params():
    assert to_query(RouteTargetFilter()) == {}


def test_free_text_is_trimmed():
    assert to_query(RouteTargetFilter(q="  core ")) == {"q": "core"}


def test_status_list_is_comma_joined():
    query = to_query(RouteTargetFilter(status=[LifecycleStatus.ACTIVE, LifecycleStatus.PLANNED]))
    assert query["status"] == "ACTIVE,PLANNED"


def test_code_is_trimmed_into_query():
    assert to_query(RouteTargetFilter(code="  abc ")) == {"code": "abc"}


def test_code_blank_is_omitted():
    assert "code" not in to_query(RouteTargetFilter(code=""))


def test_name_is_trimmed_into_query():
    assert to_query(RouteTargetFilter(name="  abc ")) == {"name": "abc"}


def test_name_blank_is_omitted():
    assert "name" not in to_query(RouteTargetFilter(name=""))


def test_external_ref_is_trimmed_into_query():
    assert to_query(RouteTargetFilter(external_ref="  abc ")) == {"externalRef": "abc"}


def test_external_ref_blank_is_omitted():
    assert "externalRef" not in to_query(RouteTargetFilter(external_ref=""))


def test_asn_range_is_sent():
    query = to_query(RouteTargetFilter(asn_min=1, asn_max=9))
    assert query["asnMin"] == str(1)
    assert query["asnMax"] == str(9)


def test_asn_zero_is_not_dropped():
    assert to_query(RouteTargetFilter(asn_min=0))["asnMin"] == "0"


def test_assigned_number_range_is_sent():
    query = to_query(RouteTargetFilter(assigned_number_min=1, assigned_number_max=9))
    assert query["assignedNumberMin"] == str(1)
    assert query["assignedNumberMax"] == str(9)


def test_assigned_number_zero_is_not_dropped():
    assert to_query(RouteTargetFilter(assigned_number_min=0))["assignedNumberMin"] == "0"


def test_rt_type_values_are_comma_joined():
    members = list(RouteTargetRtType)[:2]
    query = to_query(RouteTargetFilter(rt_type=members))
    assert query["rtType"] == ",".join(m.value for m in members)


def test_vrf_id_is_stringified():
    value = UUID("00000000-0000-0000-0000-000000000011")
    assert to_query(RouteTargetFilter(vrf_id=value))["vrfId"] == str(value)


def test_lifecycle_stage_values_are_comma_joined():
    members = list(RouteTargetLifecycleStage)[:2]
    query = to_query(RouteTargetFilter(lifecycle_stage=members))
    assert query["lifecycleStage"] == ",".join(m.value for m in members)


def test_source_system_is_trimmed_into_query():
    assert to_query(RouteTargetFilter(source_system="  abc ")) == {"sourceSystem": "abc"}


def test_source_system_blank_is_omitted():
    assert "sourceSystem" not in to_query(RouteTargetFilter(source_system=""))


def test_region_is_trimmed_into_query():
    assert to_query(RouteTargetFilter(region="  abc ")) == {"region": "abc"}


def test_region_blank_is_omitted():
    assert "region" not in to_query(RouteTargetFilter(region=""))


def test_owner_is_trimmed_into_query():
    assert to_query(RouteTargetFilter(owner="  abc ")) == {"owner": "abc"}


def test_owner_blank_is_omitted():
    assert "owner" not in to_query(RouteTargetFilter(owner=""))


def test_criticality_values_are_comma_joined():
    members = list(RouteTargetCriticality)[:2]
    query = to_query(RouteTargetFilter(criticality=members))
    assert query["criticality"] == ",".join(m.value for m in members)

