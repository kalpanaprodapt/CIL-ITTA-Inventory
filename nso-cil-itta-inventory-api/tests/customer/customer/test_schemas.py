"""Schema tests for Customer."""
from datetime import date, datetime, timezone
from decimal import Decimal
from uuid import UUID

import pytest
from pydantic import ValidationError

from itta_core.models import LifecycleStatus
from itta_api.domains.customer.customer.schemas import *  # noqa: F401,F403
from tests.customer.customer.factories import create_payload, read_model, upstream_json


def test_create_roundtrips_through_json():
    payload = create_payload()
    again = CustomerCreate.model_validate(payload.model_dump(mode="json", by_alias=True))
    assert again == payload


def test_read_parses_upstream_camel_case():
    item = CustomerRead.model_validate(upstream_json())
    assert item.code == "CUSTOM-0001"
    assert item.status == LifecycleStatus.ACTIVE


def test_update_defaults_to_empty():
    assert CustomerUpdate().model_dump(exclude_unset=True) == {}


def test_extra_fields_are_rejected():
    with pytest.raises(ValidationError):
        CustomerCreate.model_validate({**create_payload().model_dump(mode="json", by_alias=True), "bogus": 1})


def test_required_code_missing():
    data = create_payload().model_dump(mode="json", by_alias=True)
    data.pop("code")
    with pytest.raises(ValidationError):
        CustomerCreate.model_validate(data)


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

def test_legal_name_rejects_overlong():
    with pytest.raises(ValidationError):
        create_payload(legal_name="x" * 256)


def test_legal_name_rejects_control_characters():
    with pytest.raises(ValidationError):
        create_payload(legal_name="bad\x07value")

def test_segment_rejects_unknown_value():
    with pytest.raises(ValidationError):
        create_payload(segment="NOT_A_VALUE")

def test_tax_id_rejects_overlong():
    with pytest.raises(ValidationError):
        create_payload(tax_id="x" * 256)


def test_tax_id_rejects_control_characters():
    with pytest.raises(ValidationError):
        create_payload(tax_id="bad\x07value")

def test_industry_rejects_overlong():
    with pytest.raises(ValidationError):
        create_payload(industry="x" * 256)


def test_industry_rejects_control_characters():
    with pytest.raises(ValidationError):
        create_payload(industry="bad\x07value")

def test_account_manager_rejects_overlong():
    with pytest.raises(ValidationError):
        create_payload(account_manager="x" * 256)


def test_account_manager_rejects_control_characters():
    with pytest.raises(ValidationError):
        create_payload(account_manager="bad\x07value")

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
