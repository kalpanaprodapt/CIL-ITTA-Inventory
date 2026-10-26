"""Schema tests for Customer Account."""
from datetime import date, datetime, timezone
from decimal import Decimal
from uuid import UUID

import pytest
from pydantic import ValidationError

from itta_core.models import LifecycleStatus
from itta_api.domains.customer.customer_account.schemas import *  # noqa: F401,F403
from tests.customer.customer_account.factories import create_payload, read_model, upstream_json


def test_create_roundtrips_through_json():
    payload = create_payload()
    again = CustomerAccountCreate.model_validate(payload.model_dump(mode="json", by_alias=True))
    assert again == payload


def test_read_parses_upstream_camel_case():
    item = CustomerAccountRead.model_validate(upstream_json())
    assert item.code == "CUSTOM-0001"
    assert item.status == LifecycleStatus.ACTIVE


def test_update_defaults_to_empty():
    assert CustomerAccountUpdate().model_dump(exclude_unset=True) == {}


def test_extra_fields_are_rejected():
    with pytest.raises(ValidationError):
        CustomerAccountCreate.model_validate({**create_payload().model_dump(mode="json", by_alias=True), "bogus": 1})


def test_required_code_missing():
    data = create_payload().model_dump(mode="json", by_alias=True)
    data.pop("code")
    with pytest.raises(ValidationError):
        CustomerAccountCreate.model_validate(data)


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

def test_customer_id_rejects_malformed_uuid():
    with pytest.raises(ValidationError):
        create_payload(customer_id="not-a-uuid")

def test_account_number_rejects_overlong():
    with pytest.raises(ValidationError):
        create_payload(account_number="x" * 256)


def test_account_number_rejects_control_characters():
    with pytest.raises(ValidationError):
        create_payload(account_number="bad\x07value")

def test_billing_cycle_rejects_unknown_value():
    with pytest.raises(ValidationError):
        create_payload(billing_cycle="NOT_A_VALUE")

def test_currency_rejects_overlong():
    with pytest.raises(ValidationError):
        create_payload(currency="x" * 256)


def test_currency_rejects_control_characters():
    with pytest.raises(ValidationError):
        create_payload(currency="bad\x07value")

def test_credit_limit_rejects_negative():
    with pytest.raises(ValidationError):
        create_payload(credit_limit=Decimal("-1"))

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
