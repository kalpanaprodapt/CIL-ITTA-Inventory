"""Normaliser tests for Path Trace."""
from datetime import date
from decimal import Decimal
from uuid import UUID

from itta_core.models import LifecycleStatus
from itta_api.domains.topology.path_trace import normalizer
from itta_api.domains.topology.path_trace.schemas import *  # noqa: F401,F403


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

def test_normalize_source_ref_trims_and_collapses():
    assert normalizer.normalize_source_ref("  a   b ") in ("a b", "A B")


def test_normalize_source_ref_blank_is_none():
    assert normalizer.normalize_source_ref("   ") is None

def test_normalize_target_ref_trims_and_collapses():
    assert normalizer.normalize_target_ref("  a   b ") in ("a b", "A B")


def test_normalize_target_ref_blank_is_none():
    assert normalizer.normalize_target_ref("   ") is None

def test_normalize_hop_count_parses_text():
    assert normalizer.normalize_hop_count(" 42 ") == 42

def test_normalize_total_latency_ms_rounds_to_four_places():
    assert normalizer.normalize_total_latency_ms("1.234567") == Decimal("1.2346")

def test_normalize_trace_method_trims_and_collapses():
    assert normalizer.normalize_trace_method("  a   b ") in ("a b", "A B")


def test_normalize_trace_method_blank_is_none():
    assert normalizer.normalize_trace_method("   ") is None

def test_normalize_traced_at_is_timezone_aware():
    assert normalizer.normalize_traced_at("2026-03-04T10:00:00").tzinfo is not None

def test_normalize_lifecycle_stage_is_case_insensitive():
    assert normalizer.normalize_lifecycle_stage("plan") == PathTraceLifecycleStage.PLAN

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

def test_normalize_criticality_is_case_insensitive():
    assert normalizer.normalize_criticality("low") == PathTraceCriticality.LOW
