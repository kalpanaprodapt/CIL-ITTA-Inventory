"""Schema tests for Discovered Asset."""
from datetime import date, datetime, timezone
from decimal import Decimal
from uuid import UUID

import pytest
from pydantic import ValidationError

from itta_core.models import LifecycleStatus
from itta_api.domains.discovery.discovered_asset.schemas import *  # noqa: F401,F403
from tests.discovery.discovered_asset.factories import create_payload, read_model, upstream_json


def test_create_roundtrips_through_json():
    payload = create_payload()
    again = DiscoveredAssetCreate.model_validate(payload.model_dump(mode="json", by_alias=True))
    assert again == payload


def test_read_parses_upstream_camel_case():
    item = DiscoveredAssetRead.model_validate(upstream_json())
    assert item.code == "DISCOV-0001"
    assert item.status == LifecycleStatus.ACTIVE


def test_update_defaults_to_empty():
    assert DiscoveredAssetUpdate().model_dump(exclude_unset=True) == {}


def test_extra_fields_are_rejected():
    with pytest.raises(ValidationError):
        DiscoveredAssetCreate.model_validate({**create_payload().model_dump(mode="json", by_alias=True), "bogus": 1})


def test_required_code_missing():
    data = create_payload().model_dump(mode="json", by_alias=True)
    data.pop("code")
    with pytest.raises(ValidationError):
        DiscoveredAssetCreate.model_validate(data)


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

def test_job_id_rejects_malformed_uuid():
    with pytest.raises(ValidationError):
        create_payload(job_id="not-a-uuid")

def test_asset_type_rejects_overlong():
    with pytest.raises(ValidationError):
        create_payload(asset_type="x" * 256)


def test_asset_type_rejects_control_characters():
    with pytest.raises(ValidationError):
        create_payload(asset_type="bad\x07value")

def test_fingerprint_rejects_overlong():
    with pytest.raises(ValidationError):
        create_payload(fingerprint="x" * 256)


def test_fingerprint_rejects_control_characters():
    with pytest.raises(ValidationError):
        create_payload(fingerprint="bad\x07value")

def test_raw_payload_rejects_overlong():
    with pytest.raises(ValidationError):
        create_payload(raw_payload="x" * 4001)


def test_raw_payload_rejects_control_characters():
    with pytest.raises(ValidationError):
        create_payload(raw_payload="bad\x07value")

def test_matched_asset_id_rejects_malformed_uuid():
    with pytest.raises(ValidationError):
        create_payload(matched_asset_id="not-a-uuid")

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
