"""Reconciler tests for Alarm Instance."""
from datetime import date, datetime, timezone
from decimal import Decimal
from uuid import UUID

from itta_core.diffing import Action
from itta_core.models import LifecycleStatus
from itta_api.domains.alarm.alarm_instance.normalizer import normalize_record
from itta_api.domains.alarm.alarm_instance.reconciler import diff_record, reconcile_many, reconcile_one, to_create, to_update
from itta_api.domains.alarm.alarm_instance.schemas import *  # noqa: F401,F403
from tests.alarm.alarm_instance.factories import ID, raw_record, read_model


def test_identical_record_is_noop():
    item = reconcile_one(read_model(), raw_record())
    assert item.action == Action.NOOP


def test_unknown_code_creates():
    item = reconcile_one(None, raw_record())
    assert item.action == Action.CREATE
    assert to_create(item).code == "ALARMI-0001"


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

def test_diff_detects_changed_definition_id():
    current = read_model()
    incoming = normalize_record(raw_record(definitionId="99999999-9999-9999-9999-999999999999"))
    assert "definitionId" in {d.field for d in diff_record(current, incoming)}

def test_diff_detects_changed_source_ref():
    current = read_model()
    incoming = normalize_record(raw_record(sourceRef="changed-value"))
    assert "sourceRef" in {d.field for d in diff_record(current, incoming)}

def test_diff_detects_changed_raised_at():
    current = read_model()
    incoming = normalize_record(raw_record(raisedAt="2030-12-31T00:00:00Z"))
    assert "raisedAt" in {d.field for d in diff_record(current, incoming)}

def test_diff_detects_changed_cleared_at():
    current = read_model()
    incoming = normalize_record(raw_record(clearedAt="2030-12-31T00:00:00Z"))
    assert "clearedAt" in {d.field for d in diff_record(current, incoming)}

def test_diff_detects_changed_acked_by():
    current = read_model()
    incoming = normalize_record(raw_record(ackedBy="changed-value"))
    assert "ackedBy" in {d.field for d in diff_record(current, incoming)}

def test_diff_detects_changed_alarm_state():
    current = read_model()
    incoming = normalize_record(raw_record(alarmState="CLEARED"))
    assert "alarmState" in {d.field for d in diff_record(current, incoming)}

def test_diff_detects_changed_effective_from():
    current = read_model()
    incoming = normalize_record(raw_record(effectiveFrom="2030-12-31"))
    assert "effectiveFrom" in {d.field for d in diff_record(current, incoming)}

def test_diff_detects_changed_effective_to():
    current = read_model()
    incoming = normalize_record(raw_record(effectiveTo="2030-12-31"))
    assert "effectiveTo" in {d.field for d in diff_record(current, incoming)}

def test_diff_detects_changed_labels():
    current = read_model()
    incoming = normalize_record(raw_record(labels="changed-value"))
    assert "labels" in {d.field for d in diff_record(current, incoming)}

def test_diff_detects_changed_lifecycle_stage():
    current = read_model()
    incoming = normalize_record(raw_record(lifecycleStage="RETIRE"))
    assert "lifecycleStage" in {d.field for d in diff_record(current, incoming)}

def test_diff_detects_changed_source_system():
    current = read_model()
    incoming = normalize_record(raw_record(sourceSystem="changed-value"))
    assert "sourceSystem" in {d.field for d in diff_record(current, incoming)}
