"""Filter translation tests for Topology View."""
from datetime import date, datetime, timezone
from decimal import Decimal
from uuid import UUID

from itta_core.models import LifecycleStatus
from itta_api.domains.topology.topology_view.filters import TopologyViewFilter, to_query
from itta_api.domains.topology.topology_view.schemas import *  # noqa: F401,F403


def test_empty_filter_has_no_params():
    assert to_query(TopologyViewFilter()) == {}


def test_free_text_is_trimmed():
    assert to_query(TopologyViewFilter(q="  core ")) == {"q": "core"}


def test_status_list_is_comma_joined():
    query = to_query(TopologyViewFilter(status=[LifecycleStatus.ACTIVE, LifecycleStatus.PLANNED]))
    assert query["status"] == "ACTIVE,PLANNED"


def test_code_is_trimmed_into_query():
    assert to_query(TopologyViewFilter(code="  abc ")) == {"code": "abc"}


def test_code_blank_is_omitted():
    assert "code" not in to_query(TopologyViewFilter(code=""))


def test_name_is_trimmed_into_query():
    assert to_query(TopologyViewFilter(name="  abc ")) == {"name": "abc"}


def test_name_blank_is_omitted():
    assert "name" not in to_query(TopologyViewFilter(name=""))


def test_external_ref_is_trimmed_into_query():
    assert to_query(TopologyViewFilter(external_ref="  abc ")) == {"externalRef": "abc"}


def test_external_ref_blank_is_omitted():
    assert "externalRef" not in to_query(TopologyViewFilter(external_ref=""))


def test_view_name_is_trimmed_into_query():
    assert to_query(TopologyViewFilter(view_name="  abc ")) == {"viewName": "abc"}


def test_view_name_blank_is_omitted():
    assert "viewName" not in to_query(TopologyViewFilter(view_name=""))


def test_view_type_values_are_comma_joined():
    members = list(TopologyViewViewType)[:2]
    query = to_query(TopologyViewFilter(view_type=members))
    assert query["viewType"] == ",".join(m.value for m in members)


def test_scope_filter_is_trimmed_into_query():
    assert to_query(TopologyViewFilter(scope_filter="  abc ")) == {"scopeFilter": "abc"}


def test_scope_filter_blank_is_omitted():
    assert "scopeFilter" not in to_query(TopologyViewFilter(scope_filter=""))


def test_refreshed_at_window_is_iso_formatted():
    query = to_query(TopologyViewFilter(refreshed_at_from=datetime(2026, 1, 1, tzinfo=timezone.utc), refreshed_at_to=datetime(2026, 1, 1, tzinfo=timezone.utc)))
    assert query["refreshedAtFrom"].startswith("2026-01-01")
    assert query["refreshedAtTo"].startswith("2026-01-01")


def test_effective_to_window_is_iso_formatted():
    query = to_query(TopologyViewFilter(effective_to_from=date(2026, 1, 1), effective_to_to=date(2026, 1, 1)))
    assert query["effectiveToFrom"].startswith("2026-01-01")
    assert query["effectiveToTo"].startswith("2026-01-01")


def test_labels_is_trimmed_into_query():
    assert to_query(TopologyViewFilter(labels="  abc ")) == {"labels": "abc"}


def test_labels_blank_is_omitted():
    assert "labels" not in to_query(TopologyViewFilter(labels=""))


def test_lifecycle_stage_values_are_comma_joined():
    members = list(TopologyViewLifecycleStage)[:2]
    query = to_query(TopologyViewFilter(lifecycle_stage=members))
    assert query["lifecycleStage"] == ",".join(m.value for m in members)


def test_source_system_is_trimmed_into_query():
    assert to_query(TopologyViewFilter(source_system="  abc ")) == {"sourceSystem": "abc"}


def test_source_system_blank_is_omitted():
    assert "sourceSystem" not in to_query(TopologyViewFilter(source_system=""))


def test_region_is_trimmed_into_query():
    assert to_query(TopologyViewFilter(region="  abc ")) == {"region": "abc"}


def test_region_blank_is_omitted():
    assert "region" not in to_query(TopologyViewFilter(region=""))

