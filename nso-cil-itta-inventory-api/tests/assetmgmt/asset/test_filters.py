"""Filter translation tests for Asset."""
from datetime import date, datetime, timezone
from decimal import Decimal
from uuid import UUID

from itta_core.models import LifecycleStatus
from itta_api.domains.assetmgmt.asset.filters import AssetFilter, to_query
from itta_api.domains.assetmgmt.asset.schemas import *  # noqa: F401,F403


def test_empty_filter_has_no_params():
    assert to_query(AssetFilter()) == {}


def test_free_text_is_trimmed():
    assert to_query(AssetFilter(q="  core ")) == {"q": "core"}


def test_status_list_is_comma_joined():
    query = to_query(AssetFilter(status=[LifecycleStatus.ACTIVE, LifecycleStatus.PLANNED]))
    assert query["status"] == "ACTIVE,PLANNED"


def test_code_is_trimmed_into_query():
    assert to_query(AssetFilter(code="  abc ")) == {"code": "abc"}


def test_code_blank_is_omitted():
    assert "code" not in to_query(AssetFilter(code=""))


def test_name_is_trimmed_into_query():
    assert to_query(AssetFilter(name="  abc ")) == {"name": "abc"}


def test_name_blank_is_omitted():
    assert "name" not in to_query(AssetFilter(name=""))


def test_external_ref_is_trimmed_into_query():
    assert to_query(AssetFilter(external_ref="  abc ")) == {"externalRef": "abc"}


def test_external_ref_blank_is_omitted():
    assert "externalRef" not in to_query(AssetFilter(external_ref=""))


def test_asset_tag_is_trimmed_into_query():
    assert to_query(AssetFilter(asset_tag="  abc ")) == {"assetTag": "abc"}


def test_asset_tag_blank_is_omitted():
    assert "assetTag" not in to_query(AssetFilter(asset_tag=""))


def test_asset_class_is_trimmed_into_query():
    assert to_query(AssetFilter(asset_class="  abc ")) == {"assetClass": "abc"}


def test_asset_class_blank_is_omitted():
    assert "assetClass" not in to_query(AssetFilter(asset_class=""))


def test_acquired_on_window_is_iso_formatted():
    query = to_query(AssetFilter(acquired_on_from=date(2026, 1, 1), acquired_on_to=date(2026, 1, 1)))
    assert query["acquiredOnFrom"].startswith("2026-01-01")
    assert query["acquiredOnTo"].startswith("2026-01-01")


def test_purchase_price_range_is_sent():
    query = to_query(AssetFilter(purchase_price_min=Decimal("1.5"), purchase_price_max=Decimal("9.5")))
    assert query["purchasePriceMin"] == str(Decimal("1.5"))
    assert query["purchasePriceMax"] == str(Decimal("9.5"))


def test_purchase_price_zero_is_not_dropped():
    assert to_query(AssetFilter(purchase_price_min=0))["purchasePriceMin"] == "0"


def test_current_value_range_is_sent():
    query = to_query(AssetFilter(current_value_min=Decimal("1.5"), current_value_max=Decimal("9.5")))
    assert query["currentValueMin"] == str(Decimal("1.5"))
    assert query["currentValueMax"] == str(Decimal("9.5"))


def test_current_value_zero_is_not_dropped():
    assert to_query(AssetFilter(current_value_min=0))["currentValueMin"] == "0"


def test_custodian_is_trimmed_into_query():
    assert to_query(AssetFilter(custodian="  abc ")) == {"custodian": "abc"}


def test_custodian_blank_is_omitted():
    assert "custodian" not in to_query(AssetFilter(custodian=""))


def test_labels_is_trimmed_into_query():
    assert to_query(AssetFilter(labels="  abc ")) == {"labels": "abc"}


def test_labels_blank_is_omitted():
    assert "labels" not in to_query(AssetFilter(labels=""))


def test_lifecycle_stage_values_are_comma_joined():
    members = list(AssetLifecycleStage)[:2]
    query = to_query(AssetFilter(lifecycle_stage=members))
    assert query["lifecycleStage"] == ",".join(m.value for m in members)


def test_source_system_is_trimmed_into_query():
    assert to_query(AssetFilter(source_system="  abc ")) == {"sourceSystem": "abc"}


def test_source_system_blank_is_omitted():
    assert "sourceSystem" not in to_query(AssetFilter(source_system=""))


def test_region_is_trimmed_into_query():
    assert to_query(AssetFilter(region="  abc ")) == {"region": "abc"}


def test_region_blank_is_omitted():
    assert "region" not in to_query(AssetFilter(region=""))


def test_owner_is_trimmed_into_query():
    assert to_query(AssetFilter(owner="  abc ")) == {"owner": "abc"}


def test_owner_blank_is_omitted():
    assert "owner" not in to_query(AssetFilter(owner=""))

