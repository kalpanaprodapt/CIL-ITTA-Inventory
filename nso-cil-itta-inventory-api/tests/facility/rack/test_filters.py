"""Filter translation tests for Rack."""
from datetime import date, datetime, timezone
from decimal import Decimal
from uuid import UUID

from itta_core.models import LifecycleStatus
from itta_api.domains.facility.rack.filters import RackFilter, to_query
from itta_api.domains.facility.rack.schemas import *  # noqa: F401,F403


def test_empty_filter_has_no_params():
    assert to_query(RackFilter()) == {}


def test_free_text_is_trimmed():
    assert to_query(RackFilter(q="  core ")) == {"q": "core"}


def test_status_list_is_comma_joined():
    query = to_query(RackFilter(status=[LifecycleStatus.ACTIVE, LifecycleStatus.PLANNED]))
    assert query["status"] == "ACTIVE,PLANNED"


def test_code_is_trimmed_into_query():
    assert to_query(RackFilter(code="  abc ")) == {"code": "abc"}


def test_code_blank_is_omitted():
    assert "code" not in to_query(RackFilter(code=""))


def test_name_is_trimmed_into_query():
    assert to_query(RackFilter(name="  abc ")) == {"name": "abc"}


def test_name_blank_is_omitted():
    assert "name" not in to_query(RackFilter(name=""))


def test_external_ref_is_trimmed_into_query():
    assert to_query(RackFilter(external_ref="  abc ")) == {"externalRef": "abc"}


def test_external_ref_blank_is_omitted():
    assert "externalRef" not in to_query(RackFilter(external_ref=""))


def test_room_id_is_stringified():
    value = UUID("00000000-0000-0000-0000-000000000011")
    assert to_query(RackFilter(room_id=value))["roomId"] == str(value)


def test_rack_units_range_is_sent():
    query = to_query(RackFilter(rack_units_min=1, rack_units_max=9))
    assert query["rackUnitsMin"] == str(1)
    assert query["rackUnitsMax"] == str(9)


def test_rack_units_zero_is_not_dropped():
    assert to_query(RackFilter(rack_units_min=0))["rackUnitsMin"] == "0"


def test_width_mm_range_is_sent():
    query = to_query(RackFilter(width_mm_min=1, width_mm_max=9))
    assert query["widthMmMin"] == str(1)
    assert query["widthMmMax"] == str(9)


def test_width_mm_zero_is_not_dropped():
    assert to_query(RackFilter(width_mm_min=0))["widthMmMin"] == "0"


def test_depth_mm_range_is_sent():
    query = to_query(RackFilter(depth_mm_min=1, depth_mm_max=9))
    assert query["depthMmMin"] == str(1)
    assert query["depthMmMax"] == str(9)


def test_depth_mm_zero_is_not_dropped():
    assert to_query(RackFilter(depth_mm_min=0))["depthMmMin"] == "0"


def test_max_weight_kg_range_is_sent():
    query = to_query(RackFilter(max_weight_kg_min=Decimal("1.5"), max_weight_kg_max=Decimal("9.5")))
    assert query["maxWeightKgMin"] == str(Decimal("1.5"))
    assert query["maxWeightKgMax"] == str(Decimal("9.5"))


def test_max_weight_kg_zero_is_not_dropped():
    assert to_query(RackFilter(max_weight_kg_min=0))["maxWeightKgMin"] == "0"


def test_row_label_is_trimmed_into_query():
    assert to_query(RackFilter(row_label="  abc ")) == {"rowLabel": "abc"}


def test_row_label_blank_is_omitted():
    assert "rowLabel" not in to_query(RackFilter(row_label=""))


def test_position_is_trimmed_into_query():
    assert to_query(RackFilter(position="  abc ")) == {"position": "abc"}


def test_position_blank_is_omitted():
    assert "position" not in to_query(RackFilter(position=""))


def test_labels_is_trimmed_into_query():
    assert to_query(RackFilter(labels="  abc ")) == {"labels": "abc"}


def test_labels_blank_is_omitted():
    assert "labels" not in to_query(RackFilter(labels=""))


def test_lifecycle_stage_values_are_comma_joined():
    members = list(RackLifecycleStage)[:2]
    query = to_query(RackFilter(lifecycle_stage=members))
    assert query["lifecycleStage"] == ",".join(m.value for m in members)


def test_source_system_is_trimmed_into_query():
    assert to_query(RackFilter(source_system="  abc ")) == {"sourceSystem": "abc"}


def test_source_system_blank_is_omitted():
    assert "sourceSystem" not in to_query(RackFilter(source_system=""))


def test_region_is_trimmed_into_query():
    assert to_query(RackFilter(region="  abc ")) == {"region": "abc"}


def test_region_blank_is_omitted():
    assert "region" not in to_query(RackFilter(region=""))


def test_owner_is_trimmed_into_query():
    assert to_query(RackFilter(owner="  abc ")) == {"owner": "abc"}


def test_owner_blank_is_omitted():
    assert "owner" not in to_query(RackFilter(owner=""))

