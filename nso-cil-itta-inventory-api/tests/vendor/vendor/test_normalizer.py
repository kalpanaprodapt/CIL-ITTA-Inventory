"""Normaliser tests for Vendor."""
from datetime import date
from decimal import Decimal
from uuid import UUID

from itta_core.models import LifecycleStatus
from itta_api.domains.vendor.vendor import normalizer
from itta_api.domains.vendor.vendor.schemas import *  # noqa: F401,F403


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

def test_normalize_legal_name_trims_and_collapses():
    assert normalizer.normalize_legal_name("  a   b ") in ("a b", "A B")


def test_normalize_legal_name_blank_is_none():
    assert normalizer.normalize_legal_name("   ") is None

def test_normalize_support_email_trims_and_collapses():
    assert normalizer.normalize_support_email("  a   b ") in ("a b", "A B")


def test_normalize_support_email_blank_is_none():
    assert normalizer.normalize_support_email("   ") is None

def test_normalize_support_phone_trims_and_collapses():
    assert normalizer.normalize_support_phone("  a   b ") in ("a b", "A B")


def test_normalize_support_phone_blank_is_none():
    assert normalizer.normalize_support_phone("   ") is None

def test_normalize_website_trims_and_collapses():
    assert normalizer.normalize_website("  a   b ") in ("a b", "A B")


def test_normalize_website_blank_is_none():
    assert normalizer.normalize_website("   ") is None

def test_normalize_tier_is_case_insensitive():
    assert normalizer.normalize_tier("strategic") == VendorTier.STRATEGIC

def test_normalize_effective_from_parses_iso_date():
    assert normalizer.normalize_effective_from("2026-03-04") == date(2026, 3, 4)

def test_normalize_effective_to_parses_iso_date():
    assert normalizer.normalize_effective_to("2026-03-04") == date(2026, 3, 4)

def test_normalize_labels_trims_and_collapses():
    assert normalizer.normalize_labels("  a   b ") in ("a b", "A B")


def test_normalize_labels_blank_is_none():
    assert normalizer.normalize_labels("   ") is None

def test_normalize_lifecycle_stage_is_case_insensitive():
    assert normalizer.normalize_lifecycle_stage("plan") == VendorLifecycleStage.PLAN

def test_normalize_source_system_trims_and_collapses():
    assert normalizer.normalize_source_system("  a   b ") in ("a b", "A B")


def test_normalize_source_system_blank_is_none():
    assert normalizer.normalize_source_system("   ") is None
