"""Schema tests for Power Feed."""
from datetime import date, datetime, timezone
from decimal import Decimal
from uuid import UUID

import pytest
from pydantic import ValidationError

from itta_core.models import LifecycleStatus
from itta_api.domains.facility.power_feed.schemas import *  # noqa: F401,F403
from tests.facility.power_feed.factories import create_payload, read_model, upstream_json


def test_create_roundtrips_through_json():
    payload = create_payload()
    again = PowerFeedCreate.model_validate(payload.model_dump(mode="json", by_alias=True))
    assert again == payload


def test_read_parses_upstream_camel_case():
    item = PowerFeedRead.model_validate(upstream_json())
    assert item.code == "POWERF-0001"
    assert item.status == LifecycleStatus.ACTIVE


def test_update_defaults_to_empty():
    assert PowerFeedUpdate().model_dump(exclude_unset=True) == {}


def test_extra_fields_are_rejected():
    with pytest.raises(ValidationError):
        PowerFeedCreate.model_validate({**create_payload().model_dump(mode="json", by_alias=True), "bogus": 1})


def test_required_code_missing():
    data = create_payload().model_dump(mode="json", by_alias=True)
    data.pop("code")
    with pytest.raises(ValidationError):
        PowerFeedCreate.model_validate(data)


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

def test_room_id_rejects_malformed_uuid():
    with pytest.raises(ValidationError):
        create_payload(room_id="not-a-uuid")

def test_feed_type_rejects_unknown_value():
    with pytest.raises(ValidationError):
        create_payload(feed_type="NOT_A_VALUE")

def test_voltage_rejects_negative():
    with pytest.raises(ValidationError):
        create_payload(voltage=Decimal("-1"))

def test_amperage_rejects_negative():
    with pytest.raises(ValidationError):
        create_payload(amperage=Decimal("-1"))

def test_phase_rejects_overlong():
    with pytest.raises(ValidationError):
        create_payload(phase="x" * 256)


def test_phase_rejects_control_characters():
    with pytest.raises(ValidationError):
        create_payload(phase="bad\x07value")

def test_breaker_label_rejects_overlong():
    with pytest.raises(ValidationError):
        create_payload(breaker_label="x" * 256)


def test_breaker_label_rejects_control_characters():
    with pytest.raises(ValidationError):
        create_payload(breaker_label="bad\x07value")

def test_redundancy_group_rejects_overlong():
    with pytest.raises(ValidationError):
        create_payload(redundancy_group="x" * 256)


def test_redundancy_group_rejects_control_characters():
    with pytest.raises(ValidationError):
        create_payload(redundancy_group="bad\x07value")

def test_owner_rejects_overlong():
    with pytest.raises(ValidationError):
        create_payload(owner="x" * 256)


def test_owner_rejects_control_characters():
    with pytest.raises(ValidationError):
        create_payload(owner="bad\x07value")

def test_criticality_rejects_unknown_value():
    with pytest.raises(ValidationError):
        create_payload(criticality="NOT_A_VALUE")

def test_cost_center_rejects_overlong():
    with pytest.raises(ValidationError):
        create_payload(cost_center="x" * 256)


def test_cost_center_rejects_control_characters():
    with pytest.raises(ValidationError):
        create_payload(cost_center="bad\x07value")

def test_notes_rejects_overlong():
    with pytest.raises(ValidationError):
        create_payload(notes="x" * 4001)


def test_notes_rejects_control_characters():
    with pytest.raises(ValidationError):
        create_payload(notes="bad\x07value")
