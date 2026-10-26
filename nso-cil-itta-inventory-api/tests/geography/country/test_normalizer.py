"""Normaliser tests for Country."""
from datetime import date
from decimal import Decimal
from uuid import UUID

from itta_core.models import LifecycleStatus
from itta_api.domains.geography.country import normalizer
from itta_api.domains.geography.country.schemas import *  # noqa: F401,F403


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

def test_normalize_iso_code_trims_and_collapses():
    assert normalizer.normalize_iso_code("  a   b ") in ("a b", "A B")


def test_normalize_iso_code_blank_is_none():
    assert normalizer.normalize_iso_code("   ") is None

def test_normalize_iso_numeric_parses_text():
    assert normalizer.normalize_iso_numeric(" 42 ") == 42

def test_normalize_dial_code_trims_and_collapses():
    assert normalizer.normalize_dial_code("  a   b ") in ("a b", "A B")


def test_normalize_dial_code_blank_is_none():
    assert normalizer.normalize_dial_code("   ") is None

def test_normalize_currency_trims_and_collapses():
    assert normalizer.normalize_currency("  a   b ") in ("a b", "A B")


def test_normalize_currency_blank_is_none():
    assert normalizer.normalize_currency("   ") is None

def test_normalize_regulatory_body_trims_and_collapses():
    assert normalizer.normalize_regulatory_body("  a   b ") in ("a b", "A B")


def test_normalize_regulatory_body_blank_is_none():
    assert normalizer.normalize_regulatory_body("   ") is None

def test_normalize_timezone_trims_and_collapses():
    assert normalizer.normalize_timezone("  a   b ") in ("a b", "A B")


def test_normalize_timezone_blank_is_none():
    assert normalizer.normalize_timezone("   ") is None

def test_normalize_region_trims_and_collapses():
    assert normalizer.normalize_region("  a   b ") in ("a b", "A B")


def test_normalize_region_blank_is_none():
    assert normalizer.normalize_region("   ") is None

def test_normalize_owner_trims_and_collapses():
    assert normalizer.normalize_owner("  a   b ") in ("a b", "A B")


def test_normalize_owner_blank_is_none():
    assert normalizer.normalize_owner("   ") is None

def test_normalize_criticality_is_case_insensitive():
    assert normalizer.normalize_criticality("low") == CountryCriticality.LOW

def test_normalize_cost_center_trims_and_collapses():
    assert normalizer.normalize_cost_center("  a   b ") in ("a b", "A B")


def test_normalize_cost_center_blank_is_none():
    assert normalizer.normalize_cost_center("   ") is None

def test_normalize_notes_trims_and_collapses():
    assert normalizer.normalize_notes("  a   b ") in ("a b", "A B")


def test_normalize_notes_blank_is_none():
    assert normalizer.normalize_notes("   ") is None
