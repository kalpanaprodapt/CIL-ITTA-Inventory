"""Schema tests for Discovery Profile."""
from datetime import date, datetime, timezone
from decimal import Decimal
from uuid import UUID

import pytest
from pydantic import ValidationError

from itta_core.models import LifecycleStatus
from itta_api.domains.discovery.discovery_profile.schemas import *  # noqa: F401,F403
from tests.discovery.discovery_profile.factories import create_payload, read_model, upstream_json


def test_create_roundtrips_through_json():
    payload = create_payload()
    again = DiscoveryProfileCreate.model_validate(payload.model_dump(mode="json", by_alias=True))
    assert again == payload


def test_read_parses_upstream_camel_case():
    item = DiscoveryProfileRead.model_validate(upstream_json())
    assert item.code == "DISCOV-0001"
    assert item.status == LifecycleStatus.ACTIVE


def test_update_defaults_to_empty():
    assert DiscoveryProfileUpdate().model_dump(exclude_unset=True) == {}


def test_extra_fields_are_rejected():
    with pytest.raises(ValidationError):
        DiscoveryProfileCreate.model_validate({**create_payload().model_dump(mode="json", by_alias=True), "bogus": 1})


def test_required_code_missing():
    data = create_payload().model_dump(mode="json", by_alias=True)
    data.pop("code")
    with pytest.raises(ValidationError):
        DiscoveryProfileCreate.model_validate(data)


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

def test_profile_name_rejects_overlong():
    with pytest.raises(ValidationError):
        create_payload(profile_name="x" * 256)


def test_profile_name_rejects_control_characters():
    with pytest.raises(ValidationError):
        create_payload(profile_name="bad\x07value")

def test_protocol_rejects_unknown_value():
    with pytest.raises(ValidationError):
        create_payload(protocol="NOT_A_VALUE")

def test_credential_ref_rejects_overlong():
    with pytest.raises(ValidationError):
        create_payload(credential_ref="x" * 256)


def test_credential_ref_rejects_control_characters():
    with pytest.raises(ValidationError):
        create_payload(credential_ref="bad\x07value")

def test_scope_cidr_rejects_overlong():
    with pytest.raises(ValidationError):
        create_payload(scope_cidr="x" * 256)


def test_scope_cidr_rejects_control_characters():
    with pytest.raises(ValidationError):
        create_payload(scope_cidr="bad\x07value")

def test_schedule_cron_rejects_overlong():
    with pytest.raises(ValidationError):
        create_payload(schedule_cron="x" * 256)


def test_schedule_cron_rejects_control_characters():
    with pytest.raises(ValidationError):
        create_payload(schedule_cron="bad\x07value")

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

def test_labels_rejects_overlong():
    with pytest.raises(ValidationError):
        create_payload(labels="x" * 256)


def test_labels_rejects_control_characters():
    with pytest.raises(ValidationError):
        create_payload(labels="bad\x07value")
