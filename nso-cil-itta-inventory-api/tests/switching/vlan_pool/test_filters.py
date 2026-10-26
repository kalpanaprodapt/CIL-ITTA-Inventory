"""Filter translation tests for Vlan Pool."""
from datetime import date, datetime, timezone
from decimal import Decimal
from uuid import UUID

from itta_core.models import LifecycleStatus
from itta_api.domains.switching.vlan_pool.filters import VlanPoolFilter, to_query
from itta_api.domains.switching.vlan_pool.schemas import *  # noqa: F401,F403


def test_empty_filter_has_no_params():
    assert to_query(VlanPoolFilter()) == {}


def test_free_text_is_trimmed():
    assert to_query(VlanPoolFilter(q="  core ")) == {"q": "core"}


def test_status_list_is_comma_joined():
    query = to_query(VlanPoolFilter(status=[LifecycleStatus.ACTIVE, LifecycleStatus.PLANNED]))
    assert query["status"] == "ACTIVE,PLANNED"


def test_code_is_trimmed_into_query():
    assert to_query(VlanPoolFilter(code="  abc ")) == {"code": "abc"}


def test_code_blank_is_omitted():
    assert "code" not in to_query(VlanPoolFilter(code=""))


def test_name_is_trimmed_into_query():
    assert to_query(VlanPoolFilter(name="  abc ")) == {"name": "abc"}


def test_name_blank_is_omitted():
    assert "name" not in to_query(VlanPoolFilter(name=""))


def test_external_ref_is_trimmed_into_query():
    assert to_query(VlanPoolFilter(external_ref="  abc ")) == {"externalRef": "abc"}


def test_external_ref_blank_is_omitted():
    assert "externalRef" not in to_query(VlanPoolFilter(external_ref=""))


def test_pool_name_is_trimmed_into_query():
    assert to_query(VlanPoolFilter(pool_name="  abc ")) == {"poolName": "abc"}


def test_pool_name_blank_is_omitted():
    assert "poolName" not in to_query(VlanPoolFilter(pool_name=""))


def test_range_start_range_is_sent():
    query = to_query(VlanPoolFilter(range_start_min=1, range_start_max=9))
    assert query["rangeStartMin"] == str(1)
    assert query["rangeStartMax"] == str(9)


def test_range_start_zero_is_not_dropped():
    assert to_query(VlanPoolFilter(range_start_min=0))["rangeStartMin"] == "0"


def test_range_end_range_is_sent():
    query = to_query(VlanPoolFilter(range_end_min=1, range_end_max=9))
    assert query["rangeEndMin"] == str(1)
    assert query["rangeEndMax"] == str(9)


def test_range_end_zero_is_not_dropped():
    assert to_query(VlanPoolFilter(range_end_min=0))["rangeEndMin"] == "0"


def test_allocated_range_is_sent():
    query = to_query(VlanPoolFilter(allocated_min=1, allocated_max=9))
    assert query["allocatedMin"] == str(1)
    assert query["allocatedMax"] == str(9)


def test_allocated_zero_is_not_dropped():
    assert to_query(VlanPoolFilter(allocated_min=0))["allocatedMin"] == "0"


def test_scope_is_trimmed_into_query():
    assert to_query(VlanPoolFilter(scope="  abc ")) == {"scope": "abc"}


def test_scope_blank_is_omitted():
    assert "scope" not in to_query(VlanPoolFilter(scope=""))


def test_effective_to_window_is_iso_formatted():
    query = to_query(VlanPoolFilter(effective_to_from=date(2026, 1, 1), effective_to_to=date(2026, 1, 1)))
    assert query["effectiveToFrom"].startswith("2026-01-01")
    assert query["effectiveToTo"].startswith("2026-01-01")


def test_labels_is_trimmed_into_query():
    assert to_query(VlanPoolFilter(labels="  abc ")) == {"labels": "abc"}


def test_labels_blank_is_omitted():
    assert "labels" not in to_query(VlanPoolFilter(labels=""))


def test_lifecycle_stage_values_are_comma_joined():
    members = list(VlanPoolLifecycleStage)[:2]
    query = to_query(VlanPoolFilter(lifecycle_stage=members))
    assert query["lifecycleStage"] == ",".join(m.value for m in members)


def test_source_system_is_trimmed_into_query():
    assert to_query(VlanPoolFilter(source_system="  abc ")) == {"sourceSystem": "abc"}


def test_source_system_blank_is_omitted():
    assert "sourceSystem" not in to_query(VlanPoolFilter(source_system=""))


def test_region_is_trimmed_into_query():
    assert to_query(VlanPoolFilter(region="  abc ")) == {"region": "abc"}


def test_region_blank_is_omitted():
    assert "region" not in to_query(VlanPoolFilter(region=""))

