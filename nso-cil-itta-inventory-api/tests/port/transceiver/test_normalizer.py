"""Normaliser tests for Transceiver."""
from datetime import date
from decimal import Decimal
from uuid import UUID

from itta_core.models import LifecycleStatus
from itta_api.domains.port.transceiver import normalizer
from itta_api.domains.port.transceiver.schemas import *  # noqa: F401,F403


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

def test_normalize_port_id_accepts_hex_without_dashes():
    assert normalizer.normalize_port_id("00000000000000000000000000000011") == UUID(int=0x11)

def test_normalize_form_factor_is_case_insensitive():
    assert normalizer.normalize_form_factor("sfp") == TransceiverFormFactor.SFP

def test_normalize_wavelength_nm_parses_text():
    assert normalizer.normalize_wavelength_nm(" 42 ") == 42

def test_normalize_reach_km_rounds_to_four_places():
    assert normalizer.normalize_reach_km("1.234567") == Decimal("1.2346")

def test_normalize_serial_number_trims_and_collapses():
    assert normalizer.normalize_serial_number("  a   b ") in ("a b", "A B")


def test_normalize_serial_number_blank_is_none():
    assert normalizer.normalize_serial_number("   ") is None

def test_normalize_vendor_part_trims_and_collapses():
    assert normalizer.normalize_vendor_part("  a   b ") in ("a b", "A B")


def test_normalize_vendor_part_blank_is_none():
    assert normalizer.normalize_vendor_part("   ") is None

def test_normalize_labels_trims_and_collapses():
    assert normalizer.normalize_labels("  a   b ") in ("a b", "A B")


def test_normalize_labels_blank_is_none():
    assert normalizer.normalize_labels("   ") is None

def test_normalize_lifecycle_stage_is_case_insensitive():
    assert normalizer.normalize_lifecycle_stage("plan") == TransceiverLifecycleStage.PLAN

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
