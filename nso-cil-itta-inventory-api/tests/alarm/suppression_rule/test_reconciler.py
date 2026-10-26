"""Reconciler tests for Suppression Rule."""
from datetime import date, datetime, timezone
from decimal import Decimal
from uuid import UUID

from itta_core.diffing import Action
from itta_core.models import LifecycleStatus
from itta_api.domains.alarm.suppression_rule.normalizer import normalize_record
from itta_api.domains.alarm.suppression_rule.reconciler import diff_record, reconcile_many, reconcile_one, to_create, to_update
from itta_api.domains.alarm.suppression_rule.schemas import *  # noqa: F401,F403
from tests.alarm.suppression_rule.factories import ID, raw_record, read_model


def test_identical_record_is_noop():
    item = reconcile_one(read_model(), raw_record())
    assert item.action == Action.NOOP


def test_unknown_code_creates():
    item = reconcile_one(None, raw_record())
    assert item.action == Action.CREATE
    assert to_create(item).code == "SUPPRE-0001"


def test_missing_code_is_rejected():
    record = raw_record()
    record.pop("code")
    assert reconcile_one(None, record).action == Action.REJECT


def test_changed_name_updates_with_version():
    item = reconcile_one(read_model(), raw_record(name="Renamed"))
    assert item.action == Action.UPDATE
    assert item.target_id == ID
    assert to_update(item, version=3).expected_version == 3


def test_duplicate_codes_in_batch_are_rejected():
    items = reconcile_many({}, [raw_record(), raw_record()])
    assert [i.action for i in items] == [Action.CREATE, Action.REJECT]


def test_diff_detects_changed_code():
    current = read_model()
    incoming = normalize_record(raw_record(code="changed-value"))
    assert "code" in {d.field for d in diff_record(current, incoming)}

def test_diff_detects_changed_name():
    current = read_model()
    incoming = normalize_record(raw_record(name="changed-value"))
    assert "name" in {d.field for d in diff_record(current, incoming)}

def test_diff_detects_changed_description():
    current = read_model()
    incoming = normalize_record(raw_record(description="changed-value"))
    assert "description" in {d.field for d in diff_record(current, incoming)}

def test_diff_detects_changed_external_ref():
    current = read_model()
    incoming = normalize_record(raw_record(externalRef="changed-value"))
    assert "externalRef" in {d.field for d in diff_record(current, incoming)}

def test_diff_detects_changed_rule_name():
    current = read_model()
    incoming = normalize_record(raw_record(ruleName="changed-value"))
    assert "ruleName" in {d.field for d in diff_record(current, incoming)}

def test_diff_detects_changed_match_expression():
    current = read_model()
    incoming = normalize_record(raw_record(matchExpression="changed-value"))
    assert "matchExpression" in {d.field for d in diff_record(current, incoming)}

def test_diff_detects_changed_starts_at():
    current = read_model()
    incoming = normalize_record(raw_record(startsAt="2030-12-31T00:00:00Z"))
    assert "startsAt" in {d.field for d in diff_record(current, incoming)}

def test_diff_detects_changed_ends_at():
    current = read_model()
    incoming = normalize_record(raw_record(endsAt="2030-12-31T00:00:00Z"))
    assert "endsAt" in {d.field for d in diff_record(current, incoming)}

def test_diff_detects_changed_reason():
    current = read_model()
    incoming = normalize_record(raw_record(reason="changed-value"))
    assert "reason" in {d.field for d in diff_record(current, incoming)}

def test_diff_detects_changed_source_system():
    current = read_model()
    incoming = normalize_record(raw_record(sourceSystem="changed-value"))
    assert "sourceSystem" in {d.field for d in diff_record(current, incoming)}

def test_diff_detects_changed_region():
    current = read_model()
    incoming = normalize_record(raw_record(region="changed-value"))
    assert "region" in {d.field for d in diff_record(current, incoming)}

def test_diff_detects_changed_owner():
    current = read_model()
    incoming = normalize_record(raw_record(owner="changed-value"))
    assert "owner" in {d.field for d in diff_record(current, incoming)}

def test_diff_detects_changed_criticality():
    current = read_model()
    incoming = normalize_record(raw_record(criticality="CRITICAL"))
    assert "criticality" in {d.field for d in diff_record(current, incoming)}

def test_diff_detects_changed_cost_center():
    current = read_model()
    incoming = normalize_record(raw_record(costCenter="changed-value"))
    assert "costCenter" in {d.field for d in diff_record(current, incoming)}
