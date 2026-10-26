"""Schema tests for Vlan Pool."""
from datetime import date, datetime, timezone
from decimal import Decimal
from uuid import UUID

import pytest
from pydantic import ValidationError

from itta_core.models import LifecycleStatus
from itta_api.domains.switching.vlan_pool.schemas import *  # noqa: F401,F403
from tests.switching.vlan_pool.factories import create_payload, read_model, upstream_json


def test_create_roundtrips_through_json():
    payload = create_payload()
    again = VlanPoolCreate.model_validate(payload.model_dump(mode="json", by_alias=True))
    assert again == payload


def test_read_parses_upstream_camel_case():
    item = VlanPoolRead.model_validate(upstream_json())
    assert item.code == "VLANPO-0001"
    assert item.status == LifecycleStatus.ACTIVE


def test_update_defaults_to_empty():
    assert VlanPoolUpdate().model_dump(exclude_unset=True) == {}


def test_extra_fields_are_rejected():
    with pytest.raises(ValidationError):
        VlanPoolCreate.model_validate({**create_payload().model_dump(mode="json", by_alias=True), "bogus": 1})


def test_required_code_missing():
    data = create_payload().model_dump(mode="json", by_alias=True)
    data.pop("code")
    with pytest.raises(ValidationError):
        VlanPoolCreate.model_validate(data)


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

def test_pool_name_rejects_overlong():
    with pytest.raises(ValidationError):
        create_payload(pool_name="x" * 256)


def test_pool_name_rejects_control_characters():
    with pytest.raises(ValidationError):
        create_payload(pool_name="bad\x07value")

def test_range_start_rejects_negative():
    with pytest.raises(ValidationError):
        create_payload(range_start=-1)

def test_range_end_rejects_negative():
    with pytest.raises(ValidationError):
        create_payload(range_end=-1)

def test_allocated_rejects_negative():
    with pytest.raises(ValidationError):
        create_payload(allocated=-1)

def test_scope_rejects_overlong():
    with pytest.raises(ValidationError):
        create_payload(scope="x" * 256)


def test_scope_rejects_control_characters():
    with pytest.raises(ValidationError):
        create_payload(scope="bad\x07value")

def test_labels_rejects_overlong():
    with pytest.raises(ValidationError):
        create_payload(labels="x" * 256)


def test_labels_rejects_control_characters():
    with pytest.raises(ValidationError):
        create_payload(labels="bad\x07value")

def test_lifecycle_stage_rejects_unknown_value():
    with pytest.raises(ValidationError):
        create_payload(lifecycle_stage="NOT_A_VALUE")

def test_source_system_rejects_overlong():
    with pytest.raises(ValidationError):
        create_payload(source_system="x" * 256)


def test_source_system_rejects_control_characters():
    with pytest.raises(ValidationError):
        create_payload(source_system="bad\x07value")

def test_region_rejects_overlong():
    with pytest.raises(ValidationError):
        create_payload(region="x" * 256)


def test_region_rejects_control_characters():
    with pytest.raises(ValidationError):
        create_payload(region="bad\x07value")
