"""Filter translation tests for Service Catalog Item."""
from datetime import date, datetime, timezone
from decimal import Decimal
from uuid import UUID

from itta_core.models import LifecycleStatus
from itta_api.domains.service.service_catalog_item.filters import ServiceCatalogItemFilter, to_query
from itta_api.domains.service.service_catalog_item.schemas import *  # noqa: F401,F403


def test_empty_filter_has_no_params():
    assert to_query(ServiceCatalogItemFilter()) == {}


def test_free_text_is_trimmed():
    assert to_query(ServiceCatalogItemFilter(q="  core ")) == {"q": "core"}


def test_status_list_is_comma_joined():
    query = to_query(ServiceCatalogItemFilter(status=[LifecycleStatus.ACTIVE, LifecycleStatus.PLANNED]))
    assert query["status"] == "ACTIVE,PLANNED"


def test_code_is_trimmed_into_query():
    assert to_query(ServiceCatalogItemFilter(code="  abc ")) == {"code": "abc"}


def test_code_blank_is_omitted():
    assert "code" not in to_query(ServiceCatalogItemFilter(code=""))


def test_name_is_trimmed_into_query():
    assert to_query(ServiceCatalogItemFilter(name="  abc ")) == {"name": "abc"}


def test_name_blank_is_omitted():
    assert "name" not in to_query(ServiceCatalogItemFilter(name=""))


def test_external_ref_is_trimmed_into_query():
    assert to_query(ServiceCatalogItemFilter(external_ref="  abc ")) == {"externalRef": "abc"}


def test_external_ref_blank_is_omitted():
    assert "externalRef" not in to_query(ServiceCatalogItemFilter(external_ref=""))


def test_item_name_is_trimmed_into_query():
    assert to_query(ServiceCatalogItemFilter(item_name="  abc ")) == {"itemName": "abc"}


def test_item_name_blank_is_omitted():
    assert "itemName" not in to_query(ServiceCatalogItemFilter(item_name=""))


def test_family_values_are_comma_joined():
    members = list(ServiceCatalogItemFamily)[:2]
    query = to_query(ServiceCatalogItemFilter(family=members))
    assert query["family"] == ",".join(m.value for m in members)


def test_base_price_range_is_sent():
    query = to_query(ServiceCatalogItemFilter(base_price_min=Decimal("1.5"), base_price_max=Decimal("9.5")))
    assert query["basePriceMin"] == str(Decimal("1.5"))
    assert query["basePriceMax"] == str(Decimal("9.5"))


def test_base_price_zero_is_not_dropped():
    assert to_query(ServiceCatalogItemFilter(base_price_min=0))["basePriceMin"] == "0"


def test_billing_unit_is_trimmed_into_query():
    assert to_query(ServiceCatalogItemFilter(billing_unit="  abc ")) == {"billingUnit": "abc"}


def test_billing_unit_blank_is_omitted():
    assert "billingUnit" not in to_query(ServiceCatalogItemFilter(billing_unit=""))


def test_orderable_is_lowercase_boolean():
    assert to_query(ServiceCatalogItemFilter(orderable=True))["orderable"] == "true"
    assert to_query(ServiceCatalogItemFilter(orderable=False))["orderable"] == "false"


def test_effective_to_window_is_iso_formatted():
    query = to_query(ServiceCatalogItemFilter(effective_to_from=date(2026, 1, 1), effective_to_to=date(2026, 1, 1)))
    assert query["effectiveToFrom"].startswith("2026-01-01")
    assert query["effectiveToTo"].startswith("2026-01-01")


def test_labels_is_trimmed_into_query():
    assert to_query(ServiceCatalogItemFilter(labels="  abc ")) == {"labels": "abc"}


def test_labels_blank_is_omitted():
    assert "labels" not in to_query(ServiceCatalogItemFilter(labels=""))


def test_lifecycle_stage_values_are_comma_joined():
    members = list(ServiceCatalogItemLifecycleStage)[:2]
    query = to_query(ServiceCatalogItemFilter(lifecycle_stage=members))
    assert query["lifecycleStage"] == ",".join(m.value for m in members)


def test_source_system_is_trimmed_into_query():
    assert to_query(ServiceCatalogItemFilter(source_system="  abc ")) == {"sourceSystem": "abc"}


def test_source_system_blank_is_omitted():
    assert "sourceSystem" not in to_query(ServiceCatalogItemFilter(source_system=""))


def test_region_is_trimmed_into_query():
    assert to_query(ServiceCatalogItemFilter(region="  abc ")) == {"region": "abc"}


def test_region_blank_is_omitted():
    assert "region" not in to_query(ServiceCatalogItemFilter(region=""))

