"""Reconciler tests for Pseudo Wire."""
from datetime import date, datetime, timezone
from decimal import Decimal
from uuid import UUID

from itta_core.diffing import Action
from itta_core.models import LifecycleStatus
from itta_api.domains.mpls.pseudo_wire.normalizer import normalize_record
from itta_api.domains.mpls.pseudo_wire.reconciler import diff_record, reconcile_many, reconcile_one, to_create, to_update
from itta_api.domains.mpls.pseudo_wire.schemas import *  # noqa: F401,F403
from tests.mpls.pseudo_wire.factories import ID, raw_record, read_model


def test_identical_record_is_noop():
    item = reconcile_one(read_model(), raw_record())
    assert item.action == Action.NOOP


def test_unknown_code_creates():
    item = reconcile_one(None, raw_record())
    assert item.action == Action.CREATE
    assert to_create(item).code == "PSEUDO-0001"


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

def test_diff_detects_changed_vc_id():
    current = read_model()
    incoming = normalize_record(raw_record(vcId=9999))
    assert "vcId" in {d.field for d in diff_record(current, incoming)}

def test_diff_detects_changed_a_end_id():
    current = read_model()
    incoming = normalize_record(raw_record(aEndId="99999999-9999-9999-9999-999999999999"))
    assert "aEndId" in {d.field for d in diff_record(current, incoming)}

def test_diff_detects_changed_z_end_id():
    current = read_model()
    incoming = normalize_record(raw_record(zEndId="99999999-9999-9999-9999-999999999999"))
    assert "zEndId" in {d.field for d in diff_record(current, incoming)}

def test_diff_detects_changed_encapsulation():
    current = read_model()
    incoming = normalize_record(raw_record(encapsulation="changed-value"))
    assert "encapsulation" in {d.field for d in diff_record(current, incoming)}

def test_diff_detects_changed_control_word():
    current = read_model()
    incoming = normalize_record(raw_record(controlWord=False))
    assert "controlWord" in {d.field for d in diff_record(current, incoming)}

def test_diff_detects_changed_cost_center():
    current = read_model()
    incoming = normalize_record(raw_record(costCenter="changed-value"))
    assert "costCenter" in {d.field for d in diff_record(current, incoming)}

def test_diff_detects_changed_notes():
    current = read_model()
    incoming = normalize_record(raw_record(notes="changed-value"))
    assert "notes" in {d.field for d in diff_record(current, incoming)}

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
