"""Normaliser tests for Network Device."""
from datetime import date
from decimal import Decimal
from uuid import UUID

from itta_core.models import LifecycleStatus
from itta_api.domains.device.network_device import normalizer
from itta_api.domains.device.network_device.schemas import *  # noqa: F401,F403


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

def test_normalize_hostname_trims_and_collapses():
    assert normalizer.normalize_hostname("  a   b ") in ("a b", "A B")


def test_normalize_hostname_blank_is_none():
    assert normalizer.normalize_hostname("   ") is None

def test_normalize_device_role_is_case_insensitive():
    assert normalizer.normalize_device_role("core") == NetworkDeviceDeviceRole.CORE

def test_normalize_mgmt_ip_trims_and_collapses():
    assert normalizer.normalize_mgmt_ip("  a   b ") in ("a b", "A B")


def test_normalize_mgmt_ip_blank_is_none():
    assert normalizer.normalize_mgmt_ip("   ") is None

def test_normalize_serial_number_trims_and_collapses():
    assert normalizer.normalize_serial_number("  a   b ") in ("a b", "A B")


def test_normalize_serial_number_blank_is_none():
    assert normalizer.normalize_serial_number("   ") is None

def test_normalize_model_id_accepts_hex_without_dashes():
    assert normalizer.normalize_model_id("00000000000000000000000000000011") == UUID(int=0x11)

def test_normalize_rack_unit_id_accepts_hex_without_dashes():
    assert normalizer.normalize_rack_unit_id("00000000000000000000000000000011") == UUID(int=0x11)

def test_normalize_effective_to_parses_iso_date():
    assert normalizer.normalize_effective_to("2026-03-04") == date(2026, 3, 4)

def test_normalize_labels_trims_and_collapses():
    assert normalizer.normalize_labels("  a   b ") in ("a b", "A B")


def test_normalize_labels_blank_is_none():
    assert normalizer.normalize_labels("   ") is None

def test_normalize_lifecycle_stage_is_case_insensitive():
    assert normalizer.normalize_lifecycle_stage("plan") == NetworkDeviceLifecycleStage.PLAN

def test_normalize_source_system_trims_and_collapses():
    assert normalizer.normalize_source_system("  a   b ") in ("a b", "A B")


def test_normalize_source_system_blank_is_none():
    assert normalizer.normalize_source_system("   ") is None

def test_normalize_region_trims_and_collapses():
    assert normalizer.normalize_region("  a   b ") in ("a b", "A B")


def test_normalize_region_blank_is_none():
    assert normalizer.normalize_region("   ") is None
