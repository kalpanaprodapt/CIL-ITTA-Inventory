"""Filter translation tests for Floor."""
from datetime import date, datetime, timezone
from decimal import Decimal
from uuid import UUID

from itta_core.models import LifecycleStatus
from itta_api.domains.facility.floor.filters import FloorFilter, to_query
from itta_api.domains.facility.floor.schemas import *  # noqa: F401,F403


def test_empty_filter_has_no_params():
    assert to_query(FloorFilter()) == {}


def test_free_text_is_trimmed():
    assert to_query(FloorFilter(q="  core ")) == {"q": "core"}


def test_status_list_is_comma_joined():
    query = to_query(FloorFilter(status=[LifecycleStatus.ACTIVE, LifecycleStatus.PLANNED]))
    assert query["status"] == "ACTIVE,PLANNED"


def test_code_is_trimmed_into_query():
    assert to_query(FloorFilter(code="  abc ")) == {"code": "abc"}


def test_code_blank_is_omitted():
    assert "code" not in to_query(FloorFilter(code=""))


def test_name_is_trimmed_into_query():
    assert to_query(FloorFilter(name="  abc ")) == {"name": "abc"}


def test_name_blank_is_omitted():
    assert "name" not in to_query(FloorFilter(name=""))


def test_external_ref_is_trimmed_into_query():
    assert to_query(FloorFilter(external_ref="  abc ")) == {"externalRef": "abc"}


def test_external_ref_blank_is_omitted():
    assert "externalRef" not in to_query(FloorFilter(external_ref=""))


def test_building_id_is_stringified():
    value = UUID("00000000-0000-0000-0000-000000000011")
    assert to_query(FloorFilter(building_id=value))["buildingId"] == str(value)


def test_level_range_is_sent():
    query = to_query(FloorFilter(level_min=1, level_max=9))
    assert query["levelMin"] == str(1)
    assert query["levelMax"] == str(9)


def test_level_zero_is_not_dropped():
    assert to_query(FloorFilter(level_min=0))["levelMin"] == "0"


def test_area_sqm_range_is_sent():
    query = to_query(FloorFilter(area_sqm_min=Decimal("1.5"), area_sqm_max=Decimal("9.5")))
    assert query["areaSqmMin"] == str(Decimal("1.5"))
    assert query["areaSqmMax"] == str(Decimal("9.5"))


def test_area_sqm_zero_is_not_dropped():
    assert to_query(FloorFilter(area_sqm_min=0))["areaSqmMin"] == "0"


def test_load_capacity_kg_range_is_sent():
    query = to_query(FloorFilter(load_capacity_kg_min=Decimal("1.5"), load_capacity_kg_max=Decimal("9.5")))
    assert query["loadCapacityKgMin"] == str(Decimal("1.5"))
    assert query["loadCapacityKgMax"] == str(Decimal("9.5"))


def test_load_capacity_kg_zero_is_not_dropped():
    assert to_query(FloorFilter(load_capacity_kg_min=0))["loadCapacityKgMin"] == "0"


def test_has_raised_floor_is_lowercase_boolean():
    assert to_query(FloorFilter(has_raised_floor=True))["hasRaisedFloor"] == "true"
    assert to_query(FloorFilter(has_raised_floor=False))["hasRaisedFloor"] == "false"


def test_cost_center_is_trimmed_into_query():
    assert to_query(FloorFilter(cost_center="  abc ")) == {"costCenter": "abc"}


def test_cost_center_blank_is_omitted():
    assert "costCenter" not in to_query(FloorFilter(cost_center=""))


def test_effective_from_window_is_iso_formatted():
    query = to_query(FloorFilter(effective_from_from=date(2026, 1, 1), effective_from_to=date(2026, 1, 1)))
    assert query["effectiveFromFrom"].startswith("2026-01-01")
    assert query["effectiveFromTo"].startswith("2026-01-01")


def test_effective_to_window_is_iso_formatted():
    query = to_query(FloorFilter(effective_to_from=date(2026, 1, 1), effective_to_to=date(2026, 1, 1)))
    assert query["effectiveToFrom"].startswith("2026-01-01")
    assert query["effectiveToTo"].startswith("2026-01-01")


def test_labels_is_trimmed_into_query():
    assert to_query(FloorFilter(labels="  abc ")) == {"labels": "abc"}


def test_labels_blank_is_omitted():
    assert "labels" not in to_query(FloorFilter(labels=""))

