"""Reconciler tests for Support Contract."""
from datetime import date, datetime, timezone
from decimal import Decimal
from uuid import UUID

from itta_core.diffing import Action
from itta_core.models import LifecycleStatus
from itta_api.domains.vendor.support_contract.normalizer import normalize_record
from itta_api.domains.vendor.support_contract.reconciler import diff_record, reconcile_many, reconcile_one, to_create, to_update
from itta_api.domains.vendor.support_contract.schemas import *  # noqa: F401,F403
from tests.vendor.support_contract.factories import ID, raw_record, read_model


def test_identical_record_is_noop():
    item = reconcile_one(read_model(), raw_record())
    assert item.action == Action.NOOP


def test_unknown_code_creates():
    item = reconcile_one(None, raw_record())
    assert item.action == Action.CREATE
    assert to_create(item).code == "SUPPOR-0001"


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

def test_diff_detects_changed_vendor_id():
    current = read_model()
    incoming = normalize_record(raw_record(vendorId="99999999-9999-9999-9999-999999999999"))
    assert "vendorId" in {d.field for d in diff_record(current, incoming)}

def test_diff_detects_changed_contract_number():
    current = read_model()
    incoming = normalize_record(raw_record(contractNumber="changed-value"))
    assert "contractNumber" in {d.field for d in diff_record(current, incoming)}

def test_diff_detects_changed_start_date():
    current = read_model()
    incoming = normalize_record(raw_record(startDate="2030-12-31"))
    assert "startDate" in {d.field for d in diff_record(current, incoming)}

def test_diff_detects_changed_end_date():
    current = read_model()
    incoming = normalize_record(raw_record(endDate="2030-12-31"))
    assert "endDate" in {d.field for d in diff_record(current, incoming)}

def test_diff_detects_changed_coverage_level():
    current = read_model()
    incoming = normalize_record(raw_record(coverageLevel="PLATINUM"))
    assert "coverageLevel" in {d.field for d in diff_record(current, incoming)}

def test_diff_detects_changed_annual_cost():
    current = read_model()
    incoming = normalize_record(raw_record(annualCost="99.5"))
    assert "annualCost" in {d.field for d in diff_record(current, incoming)}

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

def test_diff_detects_changed_notes():
    current = read_model()
    incoming = normalize_record(raw_record(notes="changed-value"))
    assert "notes" in {d.field for d in diff_record(current, incoming)}

def test_diff_detects_changed_effective_from():
    current = read_model()
    incoming = normalize_record(raw_record(effectiveFrom="2030-12-31"))
    assert "effectiveFrom" in {d.field for d in diff_record(current, incoming)}
