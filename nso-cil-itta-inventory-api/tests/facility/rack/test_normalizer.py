"""Normaliser tests for Rack."""
from datetime import date
from decimal import Decimal
from uuid import UUID

from itta_core.models import LifecycleStatus
from itta_api.domains.facility.rack import normalizer
from itta_api.domains.facility.rack.schemas import *  # noqa: F401,F403


def test_normalize_record_drops_unknown_keys():
    out = normalizer.normalize_record({"code": " a-1 ", "bogus": 1})
    assert "bogus" not in out
    assert out["code"] == "A-1" or out["code"] == "a-1"


def test_normalize_record_accepts_snake_case_keys():
    out = normalizer.normalize_record({"name": " x "})
    assert out["name"] == "x"


def test_normalize_code_trims_and_collapses():
    assert normalizer.normalize_code("  a   b ") in ("a b", "A B")


def test_normalize_code_blank_is_none():
    assert normalizer.normalize_code("   ") is None

def test_normalize_name_trims_and_collapses():
    assert normalizer.normalize_name("  a   b ") in ("a b", "A B")


def test_normalize_name_blank_is_none():
    assert normalizer.normalize_name("   ") is None

def test_normalize_description_trims_and_collapses():
    assert normalizer.normalize_description("  a   b ") in ("a b", "A B")


def test_normalize_description_blank_is_none():
    assert normalizer.normalize_description("   ") is None

def test_normalize_status_is_case_insensitive():
    assert normalizer.normalize_status("planned") == LifecycleStatus.PLANNED

def test_normalize_external_ref_trims_and_collapses():
    assert normalizer.normalize_external_ref("  a   b ") in ("a b", "A B")


def test_normalize_external_ref_blank_is_none():
    assert normalizer.normalize_external_ref("   ") is None

def test_normalize_room_id_accepts_hex_without_dashes():
    assert normalizer.normalize_room_id("00000000000000000000000000000011") == UUID(int=0x11)

def test_normalize_rack_units_parses_text():
    assert normalizer.normalize_rack_units(" 42 ") == 42

def test_normalize_width_mm_parses_text():
    assert normalizer.normalize_width_mm(" 42 ") == 42

def test_normalize_depth_mm_parses_text():
    assert normalizer.normalize_depth_mm(" 42 ") == 42

def test_normalize_max_weight_kg_rounds_to_four_places():
    assert normalizer.normalize_max_weight_kg("1.234567") == Decimal("1.2346")

def test_normalize_row_label_trims_and_collapses():
    assert normalizer.normalize_row_label("  a   b ") in ("a b", "A B")


def test_normalize_row_label_blank_is_none():
    assert normalizer.normalize_row_label("   ") is None

def test_normalize_position_trims_and_collapses():
    assert normalizer.normalize_position("  a   b ") in ("a b", "A B")


def test_normalize_position_blank_is_none():
    assert normalizer.normalize_position("   ") is None

def test_normalize_labels_trims_and_collapses():
    assert normalizer.normalize_labels("  a   b ") in ("a b", "A B")


def test_normalize_labels_blank_is_none():
    assert normalizer.normalize_labels("   ") is None

def test_normalize_lifecycle_stage_is_case_insensitive():
    assert normalizer.normalize_lifecycle_stage("plan") == RackLifecycleStage.PLAN

def test_normalize_source_system_trims_and_collapses():
    assert normalizer.normalize_source_system("  a   b ") in ("a b", "A B")


def test_normalize_source_system_blank_is_none():
    assert normalizer.normalize_source_system("   ") is None

def test_normalize_region_trims_and_collapses():
    assert normalizer.normalize_region("  a   b ") in ("a b", "A B")


def test_normalize_region_blank_is_none():
    assert normalizer.normalize_region("   ") is None

def test_normalize_owner_trims_and_collapses():
    assert normalizer.normalize_owner("  a   b ") in ("a b", "A B")


def test_normalize_owner_blank_is_none():
    assert normalizer.normalize_owner("   ") is None
