"""Normaliser tests for Sync Mapping."""
from datetime import date
from decimal import Decimal
from uuid import UUID

from itta_core.models import LifecycleStatus
from itta_api.domains.integration.sync_mapping import normalizer
from itta_api.domains.integration.sync_mapping.schemas import *  # noqa: F401,F403


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

def test_normalize_job_id_accepts_hex_without_dashes():
    assert normalizer.normalize_job_id("00000000000000000000000000000011") == UUID(int=0x11)

def test_normalize_source_field_trims_and_collapses():
    assert normalizer.normalize_source_field("  a   b ") in ("a b", "A B")


def test_normalize_source_field_blank_is_none():
    assert normalizer.normalize_source_field("   ") is None

def test_normalize_target_field_trims_and_collapses():
    assert normalizer.normalize_target_field("  a   b ") in ("a b", "A B")


def test_normalize_target_field_blank_is_none():
    assert normalizer.normalize_target_field("   ") is None

def test_normalize_transform_trims_and_collapses():
    assert normalizer.normalize_transform("  a   b ") in ("a b", "A B")


def test_normalize_transform_blank_is_none():
    assert normalizer.normalize_transform("   ") is None

def test_normalize_required_accepts_yes():
    assert normalizer.normalize_required("Yes") is True

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

def test_normalize_labels_trims_and_collapses():
    assert normalizer.normalize_labels("  a   b ") in ("a b", "A B")


def test_normalize_labels_blank_is_none():
    assert normalizer.normalize_labels("   ") is None
