"""Reconciler tests for Room."""
from datetime import date, datetime, timezone
from decimal import Decimal
from uuid import UUID

from itta_core.diffing import Action
from itta_core.models import LifecycleStatus
from itta_api.domains.facility.room.normalizer import normalize_record
from itta_api.domains.facility.room.reconciler import diff_record, reconcile_many, reconcile_one, to_create, to_update
from itta_api.domains.facility.room.schemas import *  # noqa: F401,F403
from tests.facility.room.factories import ID, raw_record, read_model


def test_identical_record_is_noop():
    item = reconcile_one(read_model(), raw_record())
    assert item.action == Action.NOOP


def test_unknown_code_creates():
    item = reconcile_one(None, raw_record())
    assert item.action == Action.CREATE
    assert to_create(item).code == "ROOM-0001"


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

def test_diff_detects_changed_floor_id():
    current = read_model()
    incoming = normalize_record(raw_record(floorId="99999999-9999-9999-9999-999999999999"))
    assert "floorId" in {d.field for d in diff_record(current, incoming)}

def test_diff_detects_changed_room_type():
    current = read_model()
    incoming = normalize_record(raw_record(roomType="NOC"))
    assert "roomType" in {d.field for d in diff_record(current, incoming)}

def test_diff_detects_changed_area_sqm():
    current = read_model()
    incoming = normalize_record(raw_record(areaSqm="99.5"))
    assert "areaSqm" in {d.field for d in diff_record(current, incoming)}

def test_diff_detects_changed_cooling_tons():
    current = read_model()
    incoming = normalize_record(raw_record(coolingTons="99.5"))
    assert "coolingTons" in {d.field for d in diff_record(current, incoming)}

def test_diff_detects_changed_fire_zone():
    current = read_model()
    incoming = normalize_record(raw_record(fireZone="changed-value"))
    assert "fireZone" in {d.field for d in diff_record(current, incoming)}

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
