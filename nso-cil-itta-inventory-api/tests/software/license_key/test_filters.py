"""Filter translation tests for License Key."""
from datetime import date, datetime, timezone
from decimal import Decimal
from uuid import UUID

from itta_core.models import LifecycleStatus
from itta_api.domains.software.license_key.filters import LicenseKeyFilter, to_query
from itta_api.domains.software.license_key.schemas import *  # noqa: F401,F403


def test_empty_filter_has_no_params():
    assert to_query(LicenseKeyFilter()) == {}


def test_free_text_is_trimmed():
    assert to_query(LicenseKeyFilter(q="  core ")) == {"q": "core"}


def test_status_list_is_comma_joined():
    query = to_query(LicenseKeyFilter(status=[LifecycleStatus.ACTIVE, LifecycleStatus.PLANNED]))
    assert query["status"] == "ACTIVE,PLANNED"


def test_code_is_trimmed_into_query():
    assert to_query(LicenseKeyFilter(code="  abc ")) == {"code": "abc"}


def test_code_blank_is_omitted():
    assert "code" not in to_query(LicenseKeyFilter(code=""))


def test_name_is_trimmed_into_query():
    assert to_query(LicenseKeyFilter(name="  abc ")) == {"name": "abc"}


def test_name_blank_is_omitted():
    assert "name" not in to_query(LicenseKeyFilter(name=""))


def test_external_ref_is_trimmed_into_query():
    assert to_query(LicenseKeyFilter(external_ref="  abc ")) == {"externalRef": "abc"}


def test_external_ref_blank_is_omitted():
    assert "externalRef" not in to_query(LicenseKeyFilter(external_ref=""))


def test_pool_id_is_stringified():
    value = UUID("00000000-0000-0000-0000-000000000011")
    assert to_query(LicenseKeyFilter(pool_id=value))["poolId"] == str(value)


def test_key_value_is_trimmed_into_query():
    assert to_query(LicenseKeyFilter(key_value="  abc ")) == {"keyValue": "abc"}


def test_key_value_blank_is_omitted():
    assert "keyValue" not in to_query(LicenseKeyFilter(key_value=""))


def test_feature_is_trimmed_into_query():
    assert to_query(LicenseKeyFilter(feature="  abc ")) == {"feature": "abc"}


def test_feature_blank_is_omitted():
    assert "feature" not in to_query(LicenseKeyFilter(feature=""))


def test_expires_on_window_is_iso_formatted():
    query = to_query(LicenseKeyFilter(expires_on_from=date(2026, 1, 1), expires_on_to=date(2026, 1, 1)))
    assert query["expiresOnFrom"].startswith("2026-01-01")
    assert query["expiresOnTo"].startswith("2026-01-01")


def test_assigned_device_id_is_stringified():
    value = UUID("00000000-0000-0000-0000-000000000011")
    assert to_query(LicenseKeyFilter(assigned_device_id=value))["assignedDeviceId"] == str(value)


def test_effective_from_window_is_iso_formatted():
    query = to_query(LicenseKeyFilter(effective_from_from=date(2026, 1, 1), effective_from_to=date(2026, 1, 1)))
    assert query["effectiveFromFrom"].startswith("2026-01-01")
    assert query["effectiveFromTo"].startswith("2026-01-01")


def test_effective_to_window_is_iso_formatted():
    query = to_query(LicenseKeyFilter(effective_to_from=date(2026, 1, 1), effective_to_to=date(2026, 1, 1)))
    assert query["effectiveToFrom"].startswith("2026-01-01")
    assert query["effectiveToTo"].startswith("2026-01-01")


def test_labels_is_trimmed_into_query():
    assert to_query(LicenseKeyFilter(labels="  abc ")) == {"labels": "abc"}


def test_labels_blank_is_omitted():
    assert "labels" not in to_query(LicenseKeyFilter(labels=""))


def test_lifecycle_stage_values_are_comma_joined():
    members = list(LicenseKeyLifecycleStage)[:2]
    query = to_query(LicenseKeyFilter(lifecycle_stage=members))
    assert query["lifecycleStage"] == ",".join(m.value for m in members)

