"""Filter translation tests for Vlan."""
from datetime import date, datetime, timezone
from decimal import Decimal
from uuid import UUID

from itta_core.models import LifecycleStatus
from itta_api.domains.switching.vlan.filters import VlanFilter, to_query
from itta_api.domains.switching.vlan.schemas import *  # noqa: F401,F403


def test_empty_filter_has_no_params():
    assert to_query(VlanFilter()) == {}


def test_free_text_is_trimmed():
    assert to_query(VlanFilter(q="  core ")) == {"q": "core"}


def test_status_list_is_comma_joined():
    query = to_query(VlanFilter(status=[LifecycleStatus.ACTIVE, LifecycleStatus.PLANNED]))
    assert query["status"] == "ACTIVE,PLANNED"


def test_code_is_trimmed_into_query():
    assert to_query(VlanFilter(code="  abc ")) == {"code": "abc"}


def test_code_blank_is_omitted():
    assert "code" not in to_query(VlanFilter(code=""))


def test_name_is_trimmed_into_query():
    assert to_query(VlanFilter(name="  abc ")) == {"name": "abc"}


def test_name_blank_is_omitted():
    assert "name" not in to_query(VlanFilter(name=""))


def test_external_ref_is_trimmed_into_query():
    assert to_query(VlanFilter(external_ref="  abc ")) == {"externalRef": "abc"}


def test_external_ref_blank_is_omitted():
    assert "externalRef" not in to_query(VlanFilter(external_ref=""))


def test_vlan_id_range_is_sent():
    query = to_query(VlanFilter(vlan_id_min=1, vlan_id_max=9))
    assert query["vlanIdMin"] == str(1)
    assert query["vlanIdMax"] == str(9)


def test_vlan_id_zero_is_not_dropped():
    assert to_query(VlanFilter(vlan_id_min=0))["vlanIdMin"] == "0"


def test_vlan_name_is_trimmed_into_query():
    assert to_query(VlanFilter(vlan_name="  abc ")) == {"vlanName": "abc"}


def test_vlan_name_blank_is_omitted():
    assert "vlanName" not in to_query(VlanFilter(vlan_name=""))


def test_vlan_type_values_are_comma_joined():
    members = list(VlanVlanType)[:2]
    query = to_query(VlanFilter(vlan_type=members))
    assert query["vlanType"] == ",".join(m.value for m in members)


def test_pool_id_is_stringified():
    value = UUID("00000000-0000-0000-0000-000000000011")
    assert to_query(VlanFilter(pool_id=value))["poolId"] == str(value)


def test_stp_mode_is_trimmed_into_query():
    assert to_query(VlanFilter(stp_mode="  abc ")) == {"stpMode": "abc"}


def test_stp_mode_blank_is_omitted():
    assert "stpMode" not in to_query(VlanFilter(stp_mode=""))


def test_effective_from_window_is_iso_formatted():
    query = to_query(VlanFilter(effective_from_from=date(2026, 1, 1), effective_from_to=date(2026, 1, 1)))
    assert query["effectiveFromFrom"].startswith("2026-01-01")
    assert query["effectiveFromTo"].startswith("2026-01-01")


def test_effective_to_window_is_iso_formatted():
    query = to_query(VlanFilter(effective_to_from=date(2026, 1, 1), effective_to_to=date(2026, 1, 1)))
    assert query["effectiveToFrom"].startswith("2026-01-01")
    assert query["effectiveToTo"].startswith("2026-01-01")


def test_labels_is_trimmed_into_query():
    assert to_query(VlanFilter(labels="  abc ")) == {"labels": "abc"}


def test_labels_blank_is_omitted():
    assert "labels" not in to_query(VlanFilter(labels=""))


def test_lifecycle_stage_values_are_comma_joined():
    members = list(VlanLifecycleStage)[:2]
    query = to_query(VlanFilter(lifecycle_stage=members))
    assert query["lifecycleStage"] == ",".join(m.value for m in members)

