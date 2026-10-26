"""Filter translation tests for Discovered Asset."""
from datetime import date, datetime, timezone
from decimal import Decimal
from uuid import UUID

from itta_core.models import LifecycleStatus
from itta_api.domains.discovery.discovered_asset.filters import DiscoveredAssetFilter, to_query
from itta_api.domains.discovery.discovered_asset.schemas import *  # noqa: F401,F403


def test_empty_filter_has_no_params():
    assert to_query(DiscoveredAssetFilter()) == {}


def test_free_text_is_trimmed():
    assert to_query(DiscoveredAssetFilter(q="  core ")) == {"q": "core"}


def test_status_list_is_comma_joined():
    query = to_query(DiscoveredAssetFilter(status=[LifecycleStatus.ACTIVE, LifecycleStatus.PLANNED]))
    assert query["status"] == "ACTIVE,PLANNED"


def test_code_is_trimmed_into_query():
    assert to_query(DiscoveredAssetFilter(code="  abc ")) == {"code": "abc"}


def test_code_blank_is_omitted():
    assert "code" not in to_query(DiscoveredAssetFilter(code=""))


def test_name_is_trimmed_into_query():
    assert to_query(DiscoveredAssetFilter(name="  abc ")) == {"name": "abc"}


def test_name_blank_is_omitted():
    assert "name" not in to_query(DiscoveredAssetFilter(name=""))


def test_external_ref_is_trimmed_into_query():
    assert to_query(DiscoveredAssetFilter(external_ref="  abc ")) == {"externalRef": "abc"}


def test_external_ref_blank_is_omitted():
    assert "externalRef" not in to_query(DiscoveredAssetFilter(external_ref=""))


def test_job_id_is_stringified():
    value = UUID("00000000-0000-0000-0000-000000000011")
    assert to_query(DiscoveredAssetFilter(job_id=value))["jobId"] == str(value)


def test_asset_type_is_trimmed_into_query():
    assert to_query(DiscoveredAssetFilter(asset_type="  abc ")) == {"assetType": "abc"}


def test_asset_type_blank_is_omitted():
    assert "assetType" not in to_query(DiscoveredAssetFilter(asset_type=""))


def test_fingerprint_is_trimmed_into_query():
    assert to_query(DiscoveredAssetFilter(fingerprint="  abc ")) == {"fingerprint": "abc"}


def test_fingerprint_blank_is_omitted():
    assert "fingerprint" not in to_query(DiscoveredAssetFilter(fingerprint=""))


def test_matched_asset_id_is_stringified():
    value = UUID("00000000-0000-0000-0000-000000000011")
    assert to_query(DiscoveredAssetFilter(matched_asset_id=value))["matchedAssetId"] == str(value)


def test_effective_from_window_is_iso_formatted():
    query = to_query(DiscoveredAssetFilter(effective_from_from=date(2026, 1, 1), effective_from_to=date(2026, 1, 1)))
    assert query["effectiveFromFrom"].startswith("2026-01-01")
    assert query["effectiveFromTo"].startswith("2026-01-01")


def test_effective_to_window_is_iso_formatted():
    query = to_query(DiscoveredAssetFilter(effective_to_from=date(2026, 1, 1), effective_to_to=date(2026, 1, 1)))
    assert query["effectiveToFrom"].startswith("2026-01-01")
    assert query["effectiveToTo"].startswith("2026-01-01")


def test_labels_is_trimmed_into_query():
    assert to_query(DiscoveredAssetFilter(labels="  abc ")) == {"labels": "abc"}


def test_labels_blank_is_omitted():
    assert "labels" not in to_query(DiscoveredAssetFilter(labels=""))


def test_lifecycle_stage_values_are_comma_joined():
    members = list(DiscoveredAssetLifecycleStage)[:2]
    query = to_query(DiscoveredAssetFilter(lifecycle_stage=members))
    assert query["lifecycleStage"] == ",".join(m.value for m in members)


def test_source_system_is_trimmed_into_query():
    assert to_query(DiscoveredAssetFilter(source_system="  abc ")) == {"sourceSystem": "abc"}


def test_source_system_blank_is_omitted():
    assert "sourceSystem" not in to_query(DiscoveredAssetFilter(source_system=""))

