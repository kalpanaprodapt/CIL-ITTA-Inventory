"""Schema tests for Pseudo Wire."""
from datetime import date, datetime, timezone
from decimal import Decimal
from uuid import UUID

import pytest
from pydantic import ValidationError

from itta_core.models import LifecycleStatus
from itta_api.domains.mpls.pseudo_wire.schemas import *  # noqa: F401,F403
from tests.mpls.pseudo_wire.factories import create_payload, read_model, upstream_json


def test_create_roundtrips_through_json():
    payload = create_payload()
    again = PseudoWireCreate.model_validate(payload.model_dump(mode="json", by_alias=True))
    assert again == payload


def test_read_parses_upstream_camel_case():
    item = PseudoWireRead.model_validate(upstream_json())
    assert item.code == "PSEUDO-0001"
    assert item.status == LifecycleStatus.ACTIVE


def test_update_defaults_to_empty():
    assert PseudoWireUpdate().model_dump(exclude_unset=True) == {}


def test_extra_fields_are_rejected():
    with pytest.raises(ValidationError):
        PseudoWireCreate.model_validate({**create_payload().model_dump(mode="json", by_alias=True), "bogus": 1})


def test_required_code_missing():
    data = create_payload().model_dump(mode="json", by_alias=True)
    data.pop("code")
    with pytest.raises(ValidationError):
        PseudoWireCreate.model_validate(data)


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

def test_vc_id_rejects_negative():
    with pytest.raises(ValidationError):
        create_payload(vc_id=-1)

def test_a_end_id_rejects_malformed_uuid():
    with pytest.raises(ValidationError):
        create_payload(a_end_id="not-a-uuid")

def test_z_end_id_rejects_malformed_uuid():
    with pytest.raises(ValidationError):
        create_payload(z_end_id="not-a-uuid")

def test_encapsulation_rejects_overlong():
    with pytest.raises(ValidationError):
        create_payload(encapsulation="x" * 256)


def test_encapsulation_rejects_control_characters():
    with pytest.raises(ValidationError):
        create_payload(encapsulation="bad\x07value")

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
