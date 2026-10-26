"""Normaliser tests for Site."""
from datetime import date
from decimal import Decimal
from uuid import UUID

from itta_core.models import LifecycleStatus
from itta_api.domains.geography.site import normalizer
from itta_api.domains.geography.site.schemas import *  # noqa: F401,F403


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

def test_normalize_city_id_accepts_hex_without_dashes():
    assert normalizer.normalize_city_id("00000000000000000000000000000011") == UUID(int=0x11)

def test_normalize_site_type_is_case_insensitive():
    assert normalizer.normalize_site_type("co") == SiteSiteType.CO

def test_normalize_address_trims_and_collapses():
    assert normalizer.normalize_address("  a   b ") in ("a b", "A B")


def test_normalize_address_blank_is_none():
    assert normalizer.normalize_address("   ") is None

def test_normalize_latitude_rounds_to_four_places():
    assert normalizer.normalize_latitude("1.234567") == Decimal("1.2346")

def test_normalize_longitude_rounds_to_four_places():
    assert normalizer.normalize_longitude("1.234567") == Decimal("1.2346")

def test_normalize_power_kw_rounds_to_four_places():
    assert normalizer.normalize_power_kw("1.234567") == Decimal("1.2346")

def test_normalize_access_hours_trims_and_collapses():
    assert normalizer.normalize_access_hours("  a   b ") in ("a b", "A B")


def test_normalize_access_hours_blank_is_none():
    assert normalizer.normalize_access_hours("   ") is None

def test_normalize_effective_to_parses_iso_date():
    assert normalizer.normalize_effective_to("2026-03-04") == date(2026, 3, 4)

def test_normalize_labels_trims_and_collapses():
    assert normalizer.normalize_labels("  a   b ") in ("a b", "A B")


def test_normalize_labels_blank_is_none():
    assert normalizer.normalize_labels("   ") is None

def test_normalize_lifecycle_stage_is_case_insensitive():
    assert normalizer.normalize_lifecycle_stage("plan") == SiteLifecycleStage.PLAN

def test_normalize_source_system_trims_and_collapses():
    assert normalizer.normalize_source_system("  a   b ") in ("a b", "A B")


def test_normalize_source_system_blank_is_none():
    assert normalizer.normalize_source_system("   ") is None

def test_normalize_region_trims_and_collapses():
    assert normalizer.normalize_region("  a   b ") in ("a b", "A B")


def test_normalize_region_blank_is_none():
    assert normalizer.normalize_region("   ") is None
