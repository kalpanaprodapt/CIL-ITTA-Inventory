"""Filter translation tests for Topology Node."""
from datetime import date, datetime, timezone
from decimal import Decimal
from uuid import UUID

from itta_core.models import LifecycleStatus
from itta_api.domains.topology.topology_node.filters import TopologyNodeFilter, to_query
from itta_api.domains.topology.topology_node.schemas import *  # noqa: F401,F403


def test_empty_filter_has_no_params():
    assert to_query(TopologyNodeFilter()) == {}


def test_free_text_is_trimmed():
    assert to_query(TopologyNodeFilter(q="  core ")) == {"q": "core"}


def test_status_list_is_comma_joined():
    query = to_query(TopologyNodeFilter(status=[LifecycleStatus.ACTIVE, LifecycleStatus.PLANNED]))
    assert query["status"] == "ACTIVE,PLANNED"


def test_code_is_trimmed_into_query():
    assert to_query(TopologyNodeFilter(code="  abc ")) == {"code": "abc"}


def test_code_blank_is_omitted():
    assert "code" not in to_query(TopologyNodeFilter(code=""))


def test_name_is_trimmed_into_query():
    assert to_query(TopologyNodeFilter(name="  abc ")) == {"name": "abc"}


def test_name_blank_is_omitted():
    assert "name" not in to_query(TopologyNodeFilter(name=""))


def test_external_ref_is_trimmed_into_query():
    assert to_query(TopologyNodeFilter(external_ref="  abc ")) == {"externalRef": "abc"}


def test_external_ref_blank_is_omitted():
    assert "externalRef" not in to_query(TopologyNodeFilter(external_ref=""))


def test_view_id_is_stringified():
    value = UUID("00000000-0000-0000-0000-000000000011")
    assert to_query(TopologyNodeFilter(view_id=value))["viewId"] == str(value)


def test_entity_ref_is_trimmed_into_query():
    assert to_query(TopologyNodeFilter(entity_ref="  abc ")) == {"entityRef": "abc"}


def test_entity_ref_blank_is_omitted():
    assert "entityRef" not in to_query(TopologyNodeFilter(entity_ref=""))


def test_node_type_is_trimmed_into_query():
    assert to_query(TopologyNodeFilter(node_type="  abc ")) == {"nodeType": "abc"}


def test_node_type_blank_is_omitted():
    assert "nodeType" not in to_query(TopologyNodeFilter(node_type=""))


def test_x_pos_range_is_sent():
    query = to_query(TopologyNodeFilter(x_pos_min=Decimal("1.5"), x_pos_max=Decimal("9.5")))
    assert query["xPosMin"] == str(Decimal("1.5"))
    assert query["xPosMax"] == str(Decimal("9.5"))


def test_x_pos_zero_is_not_dropped():
    assert to_query(TopologyNodeFilter(x_pos_min=0))["xPosMin"] == "0"


def test_y_pos_range_is_sent():
    query = to_query(TopologyNodeFilter(y_pos_min=Decimal("1.5"), y_pos_max=Decimal("9.5")))
    assert query["yPosMin"] == str(Decimal("1.5"))
    assert query["yPosMax"] == str(Decimal("9.5"))


def test_y_pos_zero_is_not_dropped():
    assert to_query(TopologyNodeFilter(y_pos_min=0))["yPosMin"] == "0"


def test_layer_range_is_sent():
    query = to_query(TopologyNodeFilter(layer_min=1, layer_max=9))
    assert query["layerMin"] == str(1)
    assert query["layerMax"] == str(9)


def test_layer_zero_is_not_dropped():
    assert to_query(TopologyNodeFilter(layer_min=0))["layerMin"] == "0"


def test_criticality_values_are_comma_joined():
    members = list(TopologyNodeCriticality)[:2]
    query = to_query(TopologyNodeFilter(criticality=members))
    assert query["criticality"] == ",".join(m.value for m in members)


def test_cost_center_is_trimmed_into_query():
    assert to_query(TopologyNodeFilter(cost_center="  abc ")) == {"costCenter": "abc"}


def test_cost_center_blank_is_omitted():
    assert "costCenter" not in to_query(TopologyNodeFilter(cost_center=""))


def test_effective_from_window_is_iso_formatted():
    query = to_query(TopologyNodeFilter(effective_from_from=date(2026, 1, 1), effective_from_to=date(2026, 1, 1)))
    assert query["effectiveFromFrom"].startswith("2026-01-01")
    assert query["effectiveFromTo"].startswith("2026-01-01")


def test_effective_to_window_is_iso_formatted():
    query = to_query(TopologyNodeFilter(effective_to_from=date(2026, 1, 1), effective_to_to=date(2026, 1, 1)))
    assert query["effectiveToFrom"].startswith("2026-01-01")
    assert query["effectiveToTo"].startswith("2026-01-01")

