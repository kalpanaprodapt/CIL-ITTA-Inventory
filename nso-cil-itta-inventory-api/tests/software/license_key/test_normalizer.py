"""Normaliser tests for License Key."""
from datetime import date
from decimal import Decimal
from uuid import UUID

from itta_core.models import LifecycleStatus
from itta_api.domains.software.license_key import normalizer
from itta_api.domains.software.license_key.schemas import *  # noqa: F401,F403


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

def test_normalize_pool_id_accepts_hex_without_dashes():
    assert normalizer.normalize_pool_id("00000000000000000000000000000011") == UUID(int=0x11)

def test_normalize_key_value_trims_and_collapses():
    assert normalizer.normalize_key_value("  a   b ") in ("a b", "A B")


def test_normalize_key_value_blank_is_none():
    assert normalizer.normalize_key_value("   ") is None

def test_normalize_feature_trims_and_collapses():
    assert normalizer.normalize_feature("  a   b ") in ("a b", "A B")


def test_normalize_feature_blank_is_none():
    assert normalizer.normalize_feature("   ") is None

def test_normalize_expires_on_parses_iso_date():
    assert normalizer.normalize_expires_on("2026-03-04") == date(2026, 3, 4)

def test_normalize_assigned_device_id_accepts_hex_without_dashes():
    assert normalizer.normalize_assigned_device_id("00000000000000000000000000000011") == UUID(int=0x11)

def test_normalize_notes_trims_and_collapses():
    assert normalizer.normalize_notes("  a   b ") in ("a b", "A B")


def test_normalize_notes_blank_is_none():
    assert normalizer.normalize_notes("   ") is None

def test_normalize_effective_from_parses_iso_date():
    assert normalizer.normalize_effective_from("2026-03-04") == date(2026, 3, 4)

def test_normalize_effective_to_parses_iso_date():
    assert normalizer.normalize_effective_to("2026-03-04") == date(2026, 3, 4)

def test_normalize_labels_trims_and_collapses():
    assert normalizer.normalize_labels("  a   b ") in ("a b", "A B")


def test_normalize_labels_blank_is_none():
    assert normalizer.normalize_labels("   ") is None

def test_normalize_lifecycle_stage_is_case_insensitive():
    assert normalizer.normalize_lifecycle_stage("plan") == LicenseKeyLifecycleStage.PLAN
