"""Normaliser tests for Audit Record."""
from datetime import date
from decimal import Decimal
from uuid import UUID

from itta_core.models import LifecycleStatus
from itta_api.domains.compliance.audit_record import normalizer
from itta_api.domains.compliance.audit_record.schemas import *  # noqa: F401,F403


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

def test_normalize_actor_trims_and_collapses():
    assert normalizer.normalize_actor("  a   b ") in ("a b", "A B")


def test_normalize_actor_blank_is_none():
    assert normalizer.normalize_actor("   ") is None

def test_normalize_action_trims_and_collapses():
    assert normalizer.normalize_action("  a   b ") in ("a b", "A B")


def test_normalize_action_blank_is_none():
    assert normalizer.normalize_action("   ") is None

def test_normalize_entity_ref_trims_and_collapses():
    assert normalizer.normalize_entity_ref("  a   b ") in ("a b", "A B")


def test_normalize_entity_ref_blank_is_none():
    assert normalizer.normalize_entity_ref("   ") is None

def test_normalize_occurred_at_is_timezone_aware():
    assert normalizer.normalize_occurred_at("2026-03-04T10:00:00").tzinfo is not None

def test_normalize_before_state_trims_and_collapses():
    assert normalizer.normalize_before_state("  a   b ") in ("a b", "A B")


def test_normalize_before_state_blank_is_none():
    assert normalizer.normalize_before_state("   ") is None

def test_normalize_after_state_trims_and_collapses():
    assert normalizer.normalize_after_state("  a   b ") in ("a b", "A B")


def test_normalize_after_state_blank_is_none():
    assert normalizer.normalize_after_state("   ") is None

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
    assert normalizer.normalize_lifecycle_stage("plan") == AuditRecordLifecycleStage.PLAN
