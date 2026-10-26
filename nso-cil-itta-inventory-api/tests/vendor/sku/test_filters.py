"""Filter translation tests for Sku."""
from datetime import date, datetime, timezone
from decimal import Decimal
from uuid import UUID

from itta_core.models import LifecycleStatus
from itta_api.domains.vendor.sku.filters import SkuFilter, to_query
from itta_api.domains.vendor.sku.schemas import *  # noqa: F401,F403


def test_empty_filter_has_no_params():
    assert to_query(SkuFilter()) == {}


def test_free_text_is_trimmed():
    assert to_query(SkuFilter(q="  core ")) == {"q": "core"}


def test_status_list_is_comma_joined():
    query = to_query(SkuFilter(status=[LifecycleStatus.ACTIVE, LifecycleStatus.PLANNED]))
    assert query["status"] == "ACTIVE,PLANNED"


def test_code_is_trimmed_into_query():
    assert to_query(SkuFilter(code="  abc ")) == {"code": "abc"}


def test_code_blank_is_omitted():
    assert "code" not in to_query(SkuFilter(code=""))


def test_name_is_trimmed_into_query():
    assert to_query(SkuFilter(name="  abc ")) == {"name": "abc"}


def test_name_blank_is_omitted():
    assert "name" not in to_query(SkuFilter(name=""))


def test_external_ref_is_trimmed_into_query():
    assert to_query(SkuFilter(external_ref="  abc ")) == {"externalRef": "abc"}


def test_external_ref_blank_is_omitted():
    assert "externalRef" not in to_query(SkuFilter(external_ref=""))


def test_model_id_is_stringified():
    value = UUID("00000000-0000-0000-0000-000000000011")
    assert to_query(SkuFilter(model_id=value))["modelId"] == str(value)


def test_sku_code_is_trimmed_into_query():
    assert to_query(SkuFilter(sku_code="  abc ")) == {"skuCode": "abc"}


def test_sku_code_blank_is_omitted():
    assert "skuCode" not in to_query(SkuFilter(sku_code=""))


def test_list_price_range_is_sent():
    query = to_query(SkuFilter(list_price_min=Decimal("1.5"), list_price_max=Decimal("9.5")))
    assert query["listPriceMin"] == str(Decimal("1.5"))
    assert query["listPriceMax"] == str(Decimal("9.5"))


def test_list_price_zero_is_not_dropped():
    assert to_query(SkuFilter(list_price_min=0))["listPriceMin"] == "0"


def test_currency_is_trimmed_into_query():
    assert to_query(SkuFilter(currency="  abc ")) == {"currency": "abc"}


def test_currency_blank_is_omitted():
    assert "currency" not in to_query(SkuFilter(currency=""))


def test_lead_time_days_range_is_sent():
    query = to_query(SkuFilter(lead_time_days_min=1, lead_time_days_max=9))
    assert query["leadTimeDaysMin"] == str(1)
    assert query["leadTimeDaysMax"] == str(9)


def test_lead_time_days_zero_is_not_dropped():
    assert to_query(SkuFilter(lead_time_days_min=0))["leadTimeDaysMin"] == "0"


def test_source_system_is_trimmed_into_query():
    assert to_query(SkuFilter(source_system="  abc ")) == {"sourceSystem": "abc"}


def test_source_system_blank_is_omitted():
    assert "sourceSystem" not in to_query(SkuFilter(source_system=""))


def test_region_is_trimmed_into_query():
    assert to_query(SkuFilter(region="  abc ")) == {"region": "abc"}


def test_region_blank_is_omitted():
    assert "region" not in to_query(SkuFilter(region=""))


def test_owner_is_trimmed_into_query():
    assert to_query(SkuFilter(owner="  abc ")) == {"owner": "abc"}


def test_owner_blank_is_omitted():
    assert "owner" not in to_query(SkuFilter(owner=""))


def test_criticality_values_are_comma_joined():
    members = list(SkuCriticality)[:2]
    query = to_query(SkuFilter(criticality=members))
    assert query["criticality"] == ",".join(m.value for m in members)


def test_cost_center_is_trimmed_into_query():
    assert to_query(SkuFilter(cost_center="  abc ")) == {"costCenter": "abc"}


def test_cost_center_blank_is_omitted():
    assert "costCenter" not in to_query(SkuFilter(cost_center=""))

