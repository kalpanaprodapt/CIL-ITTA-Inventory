"""Schema tests for Software Image."""
from datetime import date, datetime, timezone
from decimal import Decimal
from uuid import UUID

import pytest
from pydantic import ValidationError

from itta_core.models import LifecycleStatus
from itta_api.domains.software.software_image.schemas import *  # noqa: F401,F403
from tests.software.software_image.factories import create_payload, read_model, upstream_json


def test_create_roundtrips_through_json():
    payload = create_payload()
    again = SoftwareImageCreate.model_validate(payload.model_dump(mode="json", by_alias=True))
    assert again == payload


def test_read_parses_upstream_camel_case():
    item = SoftwareImageRead.model_validate(upstream_json())
    assert item.code == "SOFTWA-0001"
    assert item.status == LifecycleStatus.ACTIVE


def test_update_defaults_to_empty():
    assert SoftwareImageUpdate().model_dump(exclude_unset=True) == {}


def test_extra_fields_are_rejected():
    with pytest.raises(ValidationError):
        SoftwareImageCreate.model_validate({**create_payload().model_dump(mode="json", by_alias=True), "bogus": 1})


def test_required_code_missing():
    data = create_payload().model_dump(mode="json", by_alias=True)
    data.pop("code")
    with pytest.raises(ValidationError):
        SoftwareImageCreate.model_validate(data)


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

def test_vendor_id_rejects_malformed_uuid():
    with pytest.raises(ValidationError):
        create_payload(vendor_id="not-a-uuid")

def test_image_name_rejects_overlong():
    with pytest.raises(ValidationError):
        create_payload(image_name="x" * 256)


def test_image_name_rejects_control_characters():
    with pytest.raises(ValidationError):
        create_payload(image_name="bad\x07value")

def test_size_mb_rejects_negative():
    with pytest.raises(ValidationError):
        create_payload(size_mb=-1)

def test_checksum_rejects_overlong():
    with pytest.raises(ValidationError):
        create_payload(checksum="x" * 256)


def test_checksum_rejects_control_characters():
    with pytest.raises(ValidationError):
        create_payload(checksum="bad\x07value")

def test_platform_rejects_overlong():
    with pytest.raises(ValidationError):
        create_payload(platform="x" * 256)


def test_platform_rejects_control_characters():
    with pytest.raises(ValidationError):
        create_payload(platform="bad\x07value")

def test_storage_path_rejects_overlong():
    with pytest.raises(ValidationError):
        create_payload(storage_path="x" * 256)


def test_storage_path_rejects_control_characters():
    with pytest.raises(ValidationError):
        create_payload(storage_path="bad\x07value")

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

def test_owner_rejects_overlong():
    with pytest.raises(ValidationError):
        create_payload(owner="x" * 256)


def test_owner_rejects_control_characters():
    with pytest.raises(ValidationError):
        create_payload(owner="bad\x07value")

def test_criticality_rejects_unknown_value():
    with pytest.raises(ValidationError):
        create_payload(criticality="NOT_A_VALUE")
