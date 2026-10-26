"""Normaliser tests for Cable."""
from datetime import date
from decimal import Decimal
from uuid import UUID

from itta_core.models import LifecycleStatus
from itta_api.domains.cabling.cable import normalizer
from itta_api.domains.cabling.cable.schemas import *  # noqa: F401,F403


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

def test_normalize_cable_type_is_case_insensitive():
    assert normalizer.normalize_cable_type("cat6") == CableCableType.CAT6

def test_normalize_length_m_rounds_to_four_places():
    assert normalizer.normalize_length_m("1.234567") == Decimal("1.2346")

def test_normalize_a_end_port_id_accepts_hex_without_dashes():
    assert normalizer.normalize_a_end_port_id("00000000000000000000000000000011") == UUID(int=0x11)

def test_normalize_z_end_port_id_accepts_hex_without_dashes():
    assert normalizer.normalize_z_end_port_id("00000000000000000000000000000011") == UUID(int=0x11)

def test_normalize_color_trims_and_collapses():
    assert normalizer.normalize_color("  a   b ") in ("a b", "A B")


def test_normalize_color_blank_is_none():
    assert normalizer.normalize_color("   ") is None

def test_normalize_label_trims_and_collapses():
    assert normalizer.normalize_label("  a   b ") in ("a b", "A B")


def test_normalize_label_blank_is_none():
    assert normalizer.normalize_label("   ") is None

def test_normalize_criticality_is_case_insensitive():
    assert normalizer.normalize_criticality("low") == CableCriticality.LOW

def test_normalize_cost_center_trims_and_collapses():
    assert normalizer.normalize_cost_center("  a   b ") in ("a b", "A B")


def test_normalize_cost_center_blank_is_none():
    assert normalizer.normalize_cost_center("   ") is None

def test_normalize_notes_trims_and_collapses():
    assert normalizer.normalize_notes("  a   b ") in ("a b", "A B")


def test_normalize_notes_blank_is_none():
    assert normalizer.normalize_notes("   ") is None

def test_normalize_effective_from_parses_iso_date():
    assert normalizer.normalize_effective_from("2026-03-04") == date(2026, 3, 4)

def test_normalize_effective_to_parses_iso_date():
    assert normalizer.normalize_effective_to("2026-03-04") == date(2026, 3, 4)
