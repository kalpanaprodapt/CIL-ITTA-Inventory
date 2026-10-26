"""Filter translation tests for Room."""
from datetime import date, datetime, timezone
from decimal import Decimal
from uuid import UUID

from itta_core.models import LifecycleStatus
from itta_api.domains.facility.room.filters import RoomFilter, to_query
from itta_api.domains.facility.room.schemas import *  # noqa: F401,F403


def test_empty_filter_has_no_params():
    assert to_query(RoomFilter()) == {}


def test_free_text_is_trimmed():
    assert to_query(RoomFilter(q="  core ")) == {"q": "core"}


def test_status_list_is_comma_joined():
    query = to_query(RoomFilter(status=[LifecycleStatus.ACTIVE, LifecycleStatus.PLANNED]))
    assert query["status"] == "ACTIVE,PLANNED"


def test_code_is_trimmed_into_query():
    assert to_query(RoomFilter(code="  abc ")) == {"code": "abc"}


def test_code_blank_is_omitted():
    assert "code" not in to_query(RoomFilter(code=""))


def test_name_is_trimmed_into_query():
    assert to_query(RoomFilter(name="  abc ")) == {"name": "abc"}


def test_name_blank_is_omitted():
    assert "name" not in to_query(RoomFilter(name=""))


def test_external_ref_is_trimmed_into_query():
    assert to_query(RoomFilter(external_ref="  abc ")) == {"externalRef": "abc"}


def test_external_ref_blank_is_omitted():
    assert "externalRef" not in to_query(RoomFilter(external_ref=""))


def test_floor_id_is_stringified():
    value = UUID("00000000-0000-0000-0000-000000000011")
    assert to_query(RoomFilter(floor_id=value))["floorId"] == str(value)


def test_room_type_values_are_comma_joined():
    members = list(RoomRoomType)[:2]
    query = to_query(RoomFilter(room_type=members))
    assert query["roomType"] == ",".join(m.value for m in members)


def test_area_sqm_range_is_sent():
    query = to_query(RoomFilter(area_sqm_min=Decimal("1.5"), area_sqm_max=Decimal("9.5")))
    assert query["areaSqmMin"] == str(Decimal("1.5"))
    assert query["areaSqmMax"] == str(Decimal("9.5"))


def test_area_sqm_zero_is_not_dropped():
    assert to_query(RoomFilter(area_sqm_min=0))["areaSqmMin"] == "0"


def test_cooling_tons_range_is_sent():
    query = to_query(RoomFilter(cooling_tons_min=Decimal("1.5"), cooling_tons_max=Decimal("9.5")))
    assert query["coolingTonsMin"] == str(Decimal("1.5"))
    assert query["coolingTonsMax"] == str(Decimal("9.5"))


def test_cooling_tons_zero_is_not_dropped():
    assert to_query(RoomFilter(cooling_tons_min=0))["coolingTonsMin"] == "0"


def test_fire_zone_is_trimmed_into_query():
    assert to_query(RoomFilter(fire_zone="  abc ")) == {"fireZone": "abc"}


def test_fire_zone_blank_is_omitted():
    assert "fireZone" not in to_query(RoomFilter(fire_zone=""))


def test_effective_from_window_is_iso_formatted():
    query = to_query(RoomFilter(effective_from_from=date(2026, 1, 1), effective_from_to=date(2026, 1, 1)))
    assert query["effectiveFromFrom"].startswith("2026-01-01")
    assert query["effectiveFromTo"].startswith("2026-01-01")


def test_effective_to_window_is_iso_formatted():
    query = to_query(RoomFilter(effective_to_from=date(2026, 1, 1), effective_to_to=date(2026, 1, 1)))
    assert query["effectiveToFrom"].startswith("2026-01-01")
    assert query["effectiveToTo"].startswith("2026-01-01")


def test_labels_is_trimmed_into_query():
    assert to_query(RoomFilter(labels="  abc ")) == {"labels": "abc"}


def test_labels_blank_is_omitted():
    assert "labels" not in to_query(RoomFilter(labels=""))


def test_lifecycle_stage_values_are_comma_joined():
    members = list(RoomLifecycleStage)[:2]
    query = to_query(RoomFilter(lifecycle_stage=members))
    assert query["lifecycleStage"] == ",".join(m.value for m in members)


def test_source_system_is_trimmed_into_query():
    assert to_query(RoomFilter(source_system="  abc ")) == {"sourceSystem": "abc"}


def test_source_system_blank_is_omitted():
    assert "sourceSystem" not in to_query(RoomFilter(source_system=""))

