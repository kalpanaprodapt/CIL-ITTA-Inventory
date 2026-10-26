"""Schema tests for Interface."""
from datetime import date, datetime, timezone
from decimal import Decimal
from uuid import UUID

import pytest
from pydantic import ValidationError

from itta_core.models import LifecycleStatus
from itta_api.domains.port.interface.schemas import *  # noqa: F401,F403
from tests.port.interface.factories import create_payload, read_model, upstream_json


def test_create_roundtrips_through_json():
    payload = create_payload()
    again = InterfaceCreate.model_validate(payload.model_dump(mode="json", by_alias=True))
    assert again == payload


def test_read_parses_upstream_camel_case():
    item = InterfaceRead.model_validate(upstream_json())
    assert item.code == "INTERF-0001"
    assert item.status == LifecycleStatus.ACTIVE


def test_update_defaults_to_empty():
    assert InterfaceUpdate().model_dump(exclude_unset=True) == {}


def test_extra_fields_are_rejected():
    with pytest.raises(ValidationError):
        InterfaceCreate.model_validate({**create_payload().model_dump(mode="json", by_alias=True), "bogus": 1})


def test_required_code_missing():
    data = create_payload().model_dump(mode="json", by_alias=True)
    data.pop("code")
    with pytest.raises(ValidationError):
        InterfaceCreate.model_validate(data)


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

def test_port_id_rejects_malformed_uuid():
    with pytest.raises(ValidationError):
        create_payload(port_id="not-a-uuid")

def test_if_name_rejects_overlong():
    with pytest.raises(ValidationError):
        create_payload(if_name="x" * 256)


def test_if_name_rejects_control_characters():
    with pytest.raises(ValidationError):
        create_payload(if_name="bad\x07value")

def test_if_index_rejects_negative():
    with pytest.raises(ValidationError):
        create_payload(if_index=-1)

def test_mac_address_rejects_overlong():
    with pytest.raises(ValidationError):
        create_payload(mac_address="x" * 256)


def test_mac_address_rejects_control_characters():
    with pytest.raises(ValidationError):
        create_payload(mac_address="bad\x07value")

def test_mtu_rejects_negative():
    with pytest.raises(ValidationError):
        create_payload(mtu=-1)

def test_oper_state_rejects_unknown_value():
    with pytest.raises(ValidationError):
        create_payload(oper_state="NOT_A_VALUE")

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
