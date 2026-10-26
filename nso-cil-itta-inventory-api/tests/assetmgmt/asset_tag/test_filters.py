"""Filter translation tests for Asset Tag."""
from datetime import date, datetime, timezone
from decimal import Decimal
from uuid import UUID

from itta_core.models import LifecycleStatus
from itta_api.domains.assetmgmt.asset_tag.filters import AssetTagFilter, to_query
from itta_api.domains.assetmgmt.asset_tag.schemas import *  # noqa: F401,F403


def test_empty_filter_has_no_params():
    assert to_query(AssetTagFilter()) == {}


def test_free_text_is_trimmed():
    assert to_query(AssetTagFilter(q="  core ")) == {"q": "core"}


def test_status_list_is_comma_joined():
    query = to_query(AssetTagFilter(status=[LifecycleStatus.ACTIVE, LifecycleStatus.PLANNED]))
    assert query["status"] == "ACTIVE,PLANNED"


def test_code_is_trimmed_into_query():
    assert to_query(AssetTagFilter(code="  abc ")) == {"code": "abc"}


def test_code_blank_is_omitted():
    assert "code" not in to_query(AssetTagFilter(code=""))


def test_name_is_trimmed_into_query():
    assert to_query(AssetTagFilter(name="  abc ")) == {"name": "abc"}


def test_name_blank_is_omitted():
    assert "name" not in to_query(AssetTagFilter(name=""))


def test_external_ref_is_trimmed_into_query():
    assert to_query(AssetTagFilter(external_ref="  abc ")) == {"externalRef": "abc"}


def test_external_ref_blank_is_omitted():
    assert "externalRef" not in to_query(AssetTagFilter(external_ref=""))


def test_asset_id_is_stringified():
    value = UUID("00000000-0000-0000-0000-000000000011")
    assert to_query(AssetTagFilter(asset_id=value))["assetId"] == str(value)


def test_tag_value_is_trimmed_into_query():
    assert to_query(AssetTagFilter(tag_value="  abc ")) == {"tagValue": "abc"}


def test_tag_value_blank_is_omitted():
    assert "tagValue" not in to_query(AssetTagFilter(tag_value=""))


def test_tag_type_values_are_comma_joined():
    members = list(AssetTagTagType)[:2]
    query = to_query(AssetTagFilter(tag_type=members))
    assert query["tagType"] == ",".join(m.value for m in members)


def test_printed_on_window_is_iso_formatted():
    query = to_query(AssetTagFilter(printed_on_from=date(2026, 1, 1), printed_on_to=date(2026, 1, 1)))
    assert query["printedOnFrom"].startswith("2026-01-01")
    assert query["printedOnTo"].startswith("2026-01-01")


def test_source_system_is_trimmed_into_query():
    assert to_query(AssetTagFilter(source_system="  abc ")) == {"sourceSystem": "abc"}


def test_source_system_blank_is_omitted():
    assert "sourceSystem" not in to_query(AssetTagFilter(source_system=""))


def test_region_is_trimmed_into_query():
    assert to_query(AssetTagFilter(region="  abc ")) == {"region": "abc"}


def test_region_blank_is_omitted():
    assert "region" not in to_query(AssetTagFilter(region=""))


def test_owner_is_trimmed_into_query():
    assert to_query(AssetTagFilter(owner="  abc ")) == {"owner": "abc"}


def test_owner_blank_is_omitted():
    assert "owner" not in to_query(AssetTagFilter(owner=""))


def test_criticality_values_are_comma_joined():
    members = list(AssetTagCriticality)[:2]
    query = to_query(AssetTagFilter(criticality=members))
    assert query["criticality"] == ",".join(m.value for m in members)


def test_cost_center_is_trimmed_into_query():
    assert to_query(AssetTagFilter(cost_center="  abc ")) == {"costCenter": "abc"}


def test_cost_center_blank_is_omitted():
    assert "costCenter" not in to_query(AssetTagFilter(cost_center=""))

