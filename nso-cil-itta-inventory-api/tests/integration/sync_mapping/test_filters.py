"""Filter translation tests for Sync Mapping."""
from datetime import date, datetime, timezone
from decimal import Decimal
from uuid import UUID

from itta_core.models import LifecycleStatus
from itta_api.domains.integration.sync_mapping.filters import SyncMappingFilter, to_query
from itta_api.domains.integration.sync_mapping.schemas import *  # noqa: F401,F403


def test_empty_filter_has_no_params():
    assert to_query(SyncMappingFilter()) == {}


def test_free_text_is_trimmed():
    assert to_query(SyncMappingFilter(q="  core ")) == {"q": "core"}


def test_status_list_is_comma_joined():
    query = to_query(SyncMappingFilter(status=[LifecycleStatus.ACTIVE, LifecycleStatus.PLANNED]))
    assert query["status"] == "ACTIVE,PLANNED"


def test_code_is_trimmed_into_query():
    assert to_query(SyncMappingFilter(code="  abc ")) == {"code": "abc"}


def test_code_blank_is_omitted():
    assert "code" not in to_query(SyncMappingFilter(code=""))


def test_name_is_trimmed_into_query():
    assert to_query(SyncMappingFilter(name="  abc ")) == {"name": "abc"}


def test_name_blank_is_omitted():
    assert "name" not in to_query(SyncMappingFilter(name=""))


def test_external_ref_is_trimmed_into_query():
    assert to_query(SyncMappingFilter(external_ref="  abc ")) == {"externalRef": "abc"}


def test_external_ref_blank_is_omitted():
    assert "externalRef" not in to_query(SyncMappingFilter(external_ref=""))


def test_job_id_is_stringified():
    value = UUID("00000000-0000-0000-0000-000000000011")
    assert to_query(SyncMappingFilter(job_id=value))["jobId"] == str(value)


def test_source_field_is_trimmed_into_query():
    assert to_query(SyncMappingFilter(source_field="  abc ")) == {"sourceField": "abc"}


def test_source_field_blank_is_omitted():
    assert "sourceField" not in to_query(SyncMappingFilter(source_field=""))


def test_target_field_is_trimmed_into_query():
    assert to_query(SyncMappingFilter(target_field="  abc ")) == {"targetField": "abc"}


def test_target_field_blank_is_omitted():
    assert "targetField" not in to_query(SyncMappingFilter(target_field=""))


def test_transform_is_trimmed_into_query():
    assert to_query(SyncMappingFilter(transform="  abc ")) == {"transform": "abc"}


def test_transform_blank_is_omitted():
    assert "transform" not in to_query(SyncMappingFilter(transform=""))


def test_required_is_lowercase_boolean():
    assert to_query(SyncMappingFilter(required=True))["required"] == "true"
    assert to_query(SyncMappingFilter(required=False))["required"] == "false"


def test_cost_center_is_trimmed_into_query():
    assert to_query(SyncMappingFilter(cost_center="  abc ")) == {"costCenter": "abc"}


def test_cost_center_blank_is_omitted():
    assert "costCenter" not in to_query(SyncMappingFilter(cost_center=""))


def test_effective_from_window_is_iso_formatted():
    query = to_query(SyncMappingFilter(effective_from_from=date(2026, 1, 1), effective_from_to=date(2026, 1, 1)))
    assert query["effectiveFromFrom"].startswith("2026-01-01")
    assert query["effectiveFromTo"].startswith("2026-01-01")


def test_effective_to_window_is_iso_formatted():
    query = to_query(SyncMappingFilter(effective_to_from=date(2026, 1, 1), effective_to_to=date(2026, 1, 1)))
    assert query["effectiveToFrom"].startswith("2026-01-01")
    assert query["effectiveToTo"].startswith("2026-01-01")


def test_labels_is_trimmed_into_query():
    assert to_query(SyncMappingFilter(labels="  abc ")) == {"labels": "abc"}


def test_labels_blank_is_omitted():
    assert "labels" not in to_query(SyncMappingFilter(labels=""))

