"""Filter translation tests for Topology Edge."""
from datetime import date, datetime, timezone
from decimal import Decimal
from uuid import UUID

from itta_core.models import LifecycleStatus
from itta_api.domains.topology.topology_edge.filters import TopologyEdgeFilter, to_query
from itta_api.domains.topology.topology_edge.schemas import *  # noqa: F401,F403


def test_empty_filter_has_no_params():
    assert to_query(TopologyEdgeFilter()) == {}


def test_free_text_is_trimmed():
    assert to_query(TopologyEdgeFilter(q="  core ")) == {"q": "core"}


def test_status_list_is_comma_joined():
    query = to_query(TopologyEdgeFilter(status=[LifecycleStatus.ACTIVE, LifecycleStatus.PLANNED]))
    assert query["status"] == "ACTIVE,PLANNED"


def test_code_is_trimmed_into_query():
    assert to_query(TopologyEdgeFilter(code="  abc ")) == {"code": "abc"}


def test_code_blank_is_omitted():
    assert "code" not in to_query(TopologyEdgeFilter(code=""))


def test_name_is_trimmed_into_query():
    assert to_query(TopologyEdgeFilter(name="  abc ")) == {"name": "abc"}


def test_name_blank_is_omitted():
    assert "name" not in to_query(TopologyEdgeFilter(name=""))


def test_external_ref_is_trimmed_into_query():
    assert to_query(TopologyEdgeFilter(external_ref="  abc ")) == {"externalRef": "abc"}


def test_external_ref_blank_is_omitted():
    assert "externalRef" not in to_query(TopologyEdgeFilter(external_ref=""))


def test_view_id_is_stringified():
    value = UUID("00000000-0000-0000-0000-000000000011")
    assert to_query(TopologyEdgeFilter(view_id=value))["viewId"] == str(value)


def test_source_node_id_is_stringified():
    value = UUID("00000000-0000-0000-0000-000000000011")
    assert to_query(TopologyEdgeFilter(source_node_id=value))["sourceNodeId"] == str(value)


def test_target_node_id_is_stringified():
    value = UUID("00000000-0000-0000-0000-000000000011")
    assert to_query(TopologyEdgeFilter(target_node_id=value))["targetNodeId"] == str(value)


def test_weight_range_is_sent():
    query = to_query(TopologyEdgeFilter(weight_min=Decimal("1.5"), weight_max=Decimal("9.5")))
    assert query["weightMin"] == str(Decimal("1.5"))
    assert query["weightMax"] == str(Decimal("9.5"))


def test_weight_zero_is_not_dropped():
    assert to_query(TopologyEdgeFilter(weight_min=0))["weightMin"] == "0"


def test_directed_is_lowercase_boolean():
    assert to_query(TopologyEdgeFilter(directed=True))["directed"] == "true"
    assert to_query(TopologyEdgeFilter(directed=False))["directed"] == "false"


def test_effective_from_window_is_iso_formatted():
    query = to_query(TopologyEdgeFilter(effective_from_from=date(2026, 1, 1), effective_from_to=date(2026, 1, 1)))
    assert query["effectiveFromFrom"].startswith("2026-01-01")
    assert query["effectiveFromTo"].startswith("2026-01-01")


def test_effective_to_window_is_iso_formatted():
    query = to_query(TopologyEdgeFilter(effective_to_from=date(2026, 1, 1), effective_to_to=date(2026, 1, 1)))
    assert query["effectiveToFrom"].startswith("2026-01-01")
    assert query["effectiveToTo"].startswith("2026-01-01")


def test_labels_is_trimmed_into_query():
    assert to_query(TopologyEdgeFilter(labels="  abc ")) == {"labels": "abc"}


def test_labels_blank_is_omitted():
    assert "labels" not in to_query(TopologyEdgeFilter(labels=""))


def test_lifecycle_stage_values_are_comma_joined():
    members = list(TopologyEdgeLifecycleStage)[:2]
    query = to_query(TopologyEdgeFilter(lifecycle_stage=members))
    assert query["lifecycleStage"] == ",".join(m.value for m in members)

