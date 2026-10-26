"""Normaliser tests for Import Batch."""
from datetime import date
from decimal import Decimal
from uuid import UUID

from itta_core.models import LifecycleStatus
from itta_api.domains.integration.import_batch import normalizer
from itta_api.domains.integration.import_batch.schemas import *  # noqa: F401,F403


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

def test_normalize_file_name_trims_and_collapses():
    assert normalizer.normalize_file_name("  a   b ") in ("a b", "A B")


def test_normalize_file_name_blank_is_none():
    assert normalizer.normalize_file_name("   ") is None

def test_normalize_entity_type_trims_and_collapses():
    assert normalizer.normalize_entity_type("  a   b ") in ("a b", "A B")


def test_normalize_entity_type_blank_is_none():
    assert normalizer.normalize_entity_type("   ") is None

def test_normalize_total_rows_parses_text():
    assert normalizer.normalize_total_rows(" 42 ") == 42

def test_normalize_processed_rows_parses_text():
    assert normalizer.normalize_processed_rows(" 42 ") == 42

def test_normalize_failed_rows_parses_text():
    assert normalizer.normalize_failed_rows(" 42 ") == 42

def test_normalize_batch_state_is_case_insensitive():
    assert normalizer.normalize_batch_state("uploaded") == ImportBatchBatchState.UPLOADED

def test_normalize_effective_from_parses_iso_date():
    assert normalizer.normalize_effective_from("2026-03-04") == date(2026, 3, 4)

def test_normalize_effective_to_parses_iso_date():
    assert normalizer.normalize_effective_to("2026-03-04") == date(2026, 3, 4)

def test_normalize_labels_trims_and_collapses():
    assert normalizer.normalize_labels("  a   b ") in ("a b", "A B")


def test_normalize_labels_blank_is_none():
    assert normalizer.normalize_labels("   ") is None

def test_normalize_lifecycle_stage_is_case_insensitive():
    assert normalizer.normalize_lifecycle_stage("plan") == ImportBatchLifecycleStage.PLAN

def test_normalize_source_system_trims_and_collapses():
    assert normalizer.normalize_source_system("  a   b ") in ("a b", "A B")


def test_normalize_source_system_blank_is_none():
    assert normalizer.normalize_source_system("   ") is None
