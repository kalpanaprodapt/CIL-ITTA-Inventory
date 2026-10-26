"""Normaliser tests for Line Card."""
from datetime import date
from decimal import Decimal
from uuid import UUID

from itta_core.models import LifecycleStatus
from itta_api.domains.device.line_card import normalizer
from itta_api.domains.device.line_card.schemas import *  # noqa: F401,F403


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

def test_normalize_chassis_id_accepts_hex_without_dashes():
    assert normalizer.normalize_chassis_id("00000000000000000000000000000011") == UUID(int=0x11)

def test_normalize_slot_number_parses_text():
    assert normalizer.normalize_slot_number(" 42 ") == 42

def test_normalize_card_type_trims_and_collapses():
    assert normalizer.normalize_card_type("  a   b ") in ("a b", "A B")


def test_normalize_card_type_blank_is_none():
    assert normalizer.normalize_card_type("   ") is None

def test_normalize_port_count_parses_text():
    assert normalizer.normalize_port_count(" 42 ") == 42

def test_normalize_serial_number_trims_and_collapses():
    assert normalizer.normalize_serial_number("  a   b ") in ("a b", "A B")


def test_normalize_serial_number_blank_is_none():
    assert normalizer.normalize_serial_number("   ") is None

def test_normalize_firmware_trims_and_collapses():
    assert normalizer.normalize_firmware("  a   b ") in ("a b", "A B")


def test_normalize_firmware_blank_is_none():
    assert normalizer.normalize_firmware("   ") is None

def test_normalize_region_trims_and_collapses():
    assert normalizer.normalize_region("  a   b ") in ("a b", "A B")


def test_normalize_region_blank_is_none():
    assert normalizer.normalize_region("   ") is None

def test_normalize_owner_trims_and_collapses():
    assert normalizer.normalize_owner("  a   b ") in ("a b", "A B")


def test_normalize_owner_blank_is_none():
    assert normalizer.normalize_owner("   ") is None

def test_normalize_criticality_is_case_insensitive():
    assert normalizer.normalize_criticality("low") == LineCardCriticality.LOW

def test_normalize_cost_center_trims_and_collapses():
    assert normalizer.normalize_cost_center("  a   b ") in ("a b", "A B")


def test_normalize_cost_center_blank_is_none():
    assert normalizer.normalize_cost_center("   ") is None

def test_normalize_notes_trims_and_collapses():
    assert normalizer.normalize_notes("  a   b ") in ("a b", "A B")


def test_normalize_notes_blank_is_none():
    assert normalizer.normalize_notes("   ") is None
