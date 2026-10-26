"""Schema tests for Audit Record."""
from datetime import date, datetime, timezone
from decimal import Decimal
from uuid import UUID

import pytest
from pydantic import ValidationError

from itta_core.models import LifecycleStatus
from itta_api.domains.compliance.audit_record.schemas import *  # noqa: F401,F403
from tests.compliance.audit_record.factories import create_payload, read_model, upstream_json


def test_create_roundtrips_through_json():
    payload = create_payload()
    again = AuditRecordCreate.model_validate(payload.model_dump(mode="json", by_alias=True))
    assert again == payload


def test_read_parses_upstream_camel_case():
    item = AuditRecordRead.model_validate(upstream_json())
    assert item.code == "AUDITR-0001"
    assert item.status == LifecycleStatus.ACTIVE


def test_update_defaults_to_empty():
    assert AuditRecordUpdate().model_dump(exclude_unset=True) == {}


def test_extra_fields_are_rejected():
    with pytest.raises(ValidationError):
        AuditRecordCreate.model_validate({**create_payload().model_dump(mode="json", by_alias=True), "bogus": 1})


def test_required_code_missing():
    data = create_payload().model_dump(mode="json", by_alias=True)
    data.pop("code")
    with pytest.raises(ValidationError):
        AuditRecordCreate.model_validate(data)


def test_code_rejects_overlong():
    with pytest.raises(ValidationError):
        create_payload(code="x" * 256)


def test_code_rejects_control_characters():
    with pytest.raises(ValidationError):
        create_payload(code="bad\x07value")

def test_name_rejects_overlong():
    with pytest.raises(ValidationError):
        create_payload(name="x" * 256)


def test_name_rejects_control_characters():
    with pytest.raises(ValidationError):
        create_payload(name="bad\x07value")

def test_description_rejects_overlong():
    with pytest.raises(ValidationError):
        create_payload(description="x" * 4001)


def test_description_rejects_control_characters():
    with pytest.raises(ValidationError):
        create_payload(description="bad\x07value")

def test_status_rejects_unknown_value():
    with pytest.raises(ValidationError):
        create_payload(status="NOT_A_VALUE")

def test_external_ref_rejects_overlong():
    with pytest.raises(ValidationError):
        create_payload(external_ref="x" * 256)


def test_external_ref_rejects_control_characters():
    with pytest.raises(ValidationError):
        create_payload(external_ref="bad\x07value")

def test_actor_rejects_overlong():
    with pytest.raises(ValidationError):
        create_payload(actor="x" * 256)


def test_actor_rejects_control_characters():
    with pytest.raises(ValidationError):
        create_payload(actor="bad\x07value")

def test_action_rejects_overlong():
    with pytest.raises(ValidationError):
        create_payload(action="x" * 256)


def test_action_rejects_control_characters():
    with pytest.raises(ValidationError):
        create_payload(action="bad\x07value")

def test_entity_ref_rejects_overlong():
    with pytest.raises(ValidationError):
        create_payload(entity_ref="x" * 256)


def test_entity_ref_rejects_control_characters():
    with pytest.raises(ValidationError):
        create_payload(entity_ref="bad\x07value")

def test_before_state_rejects_overlong():
    with pytest.raises(ValidationError):
        create_payload(before_state="x" * 4001)


def test_before_state_rejects_control_characters():
    with pytest.raises(ValidationError):
        create_payload(before_state="bad\x07value")

def test_after_state_rejects_overlong():
    with pytest.raises(ValidationError):
        create_payload(after_state="x" * 4001)


def test_after_state_rejects_control_characters():
    with pytest.raises(ValidationError):
        create_payload(after_state="bad\x07value")

def test_notes_rejects_overlong():
    with pytest.raises(ValidationError):
        create_payload(notes="x" * 4001)


def test_notes_rejects_control_characters():
    with pytest.raises(ValidationError):
        create_payload(notes="bad\x07value")

def test_labels_rejects_overlong():
    with pytest.raises(ValidationError):
        create_payload(labels="x" * 256)


def test_labels_rejects_control_characters():
    with pytest.raises(ValidationError):
        create_payload(labels="bad\x07value")

def test_lifecycle_stage_rejects_unknown_value():
    with pytest.raises(ValidationError):
        create_payload(lifecycle_stage="NOT_A_VALUE")
