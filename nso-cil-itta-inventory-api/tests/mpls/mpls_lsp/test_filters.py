"""Filter translation tests for Mpls Lsp."""
from datetime import date, datetime, timezone
from decimal import Decimal
from uuid import UUID

from itta_core.models import LifecycleStatus
from itta_api.domains.mpls.mpls_lsp.filters import MplsLspFilter, to_query
from itta_api.domains.mpls.mpls_lsp.schemas import *  # noqa: F401,F403


def test_empty_filter_has_no_params():
    assert to_query(MplsLspFilter()) == {}


def test_free_text_is_trimmed():
    assert to_query(MplsLspFilter(q="  core ")) == {"q": "core"}


def test_status_list_is_comma_joined():
    query = to_query(MplsLspFilter(status=[LifecycleStatus.ACTIVE, LifecycleStatus.PLANNED]))
    assert query["status"] == "ACTIVE,PLANNED"


def test_code_is_trimmed_into_query():
    assert to_query(MplsLspFilter(code="  abc ")) == {"code": "abc"}


def test_code_blank_is_omitted():
    assert "code" not in to_query(MplsLspFilter(code=""))


def test_name_is_trimmed_into_query():
    assert to_query(MplsLspFilter(name="  abc ")) == {"name": "abc"}


def test_name_blank_is_omitted():
    assert "name" not in to_query(MplsLspFilter(name=""))


def test_external_ref_is_trimmed_into_query():
    assert to_query(MplsLspFilter(external_ref="  abc ")) == {"externalRef": "abc"}


def test_external_ref_blank_is_omitted():
    assert "externalRef" not in to_query(MplsLspFilter(external_ref=""))


def test_lsp_name_is_trimmed_into_query():
    assert to_query(MplsLspFilter(lsp_name="  abc ")) == {"lspName": "abc"}


def test_lsp_name_blank_is_omitted():
    assert "lspName" not in to_query(MplsLspFilter(lsp_name=""))


def test_head_end_id_is_stringified():
    value = UUID("00000000-0000-0000-0000-000000000011")
    assert to_query(MplsLspFilter(head_end_id=value))["headEndId"] == str(value)


def test_tail_end_id_is_stringified():
    value = UUID("00000000-0000-0000-0000-000000000011")
    assert to_query(MplsLspFilter(tail_end_id=value))["tailEndId"] == str(value)


def test_bandwidth_mbps_range_is_sent():
    query = to_query(MplsLspFilter(bandwidth_mbps_min=1, bandwidth_mbps_max=9))
    assert query["bandwidthMbpsMin"] == str(1)
    assert query["bandwidthMbpsMax"] == str(9)


def test_bandwidth_mbps_zero_is_not_dropped():
    assert to_query(MplsLspFilter(bandwidth_mbps_min=0))["bandwidthMbpsMin"] == "0"


def test_setup_priority_range_is_sent():
    query = to_query(MplsLspFilter(setup_priority_min=1, setup_priority_max=9))
    assert query["setupPriorityMin"] == str(1)
    assert query["setupPriorityMax"] == str(9)


def test_setup_priority_zero_is_not_dropped():
    assert to_query(MplsLspFilter(setup_priority_min=0))["setupPriorityMin"] == "0"


def test_path_type_values_are_comma_joined():
    members = list(MplsLspPathType)[:2]
    query = to_query(MplsLspFilter(path_type=members))
    assert query["pathType"] == ",".join(m.value for m in members)


def test_source_system_is_trimmed_into_query():
    assert to_query(MplsLspFilter(source_system="  abc ")) == {"sourceSystem": "abc"}


def test_source_system_blank_is_omitted():
    assert "sourceSystem" not in to_query(MplsLspFilter(source_system=""))


def test_region_is_trimmed_into_query():
    assert to_query(MplsLspFilter(region="  abc ")) == {"region": "abc"}


def test_region_blank_is_omitted():
    assert "region" not in to_query(MplsLspFilter(region=""))


def test_owner_is_trimmed_into_query():
    assert to_query(MplsLspFilter(owner="  abc ")) == {"owner": "abc"}


def test_owner_blank_is_omitted():
    assert "owner" not in to_query(MplsLspFilter(owner=""))


def test_criticality_values_are_comma_joined():
    members = list(MplsLspCriticality)[:2]
    query = to_query(MplsLspFilter(criticality=members))
    assert query["criticality"] == ",".join(m.value for m in members)


def test_cost_center_is_trimmed_into_query():
    assert to_query(MplsLspFilter(cost_center="  abc ")) == {"costCenter": "abc"}


def test_cost_center_blank_is_omitted():
    assert "costCenter" not in to_query(MplsLspFilter(cost_center=""))

