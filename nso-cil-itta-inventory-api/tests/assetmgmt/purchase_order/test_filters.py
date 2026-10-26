"""Filter translation tests for Purchase Order."""
from datetime import date, datetime, timezone
from decimal import Decimal
from uuid import UUID

from itta_core.models import LifecycleStatus
from itta_api.domains.assetmgmt.purchase_order.filters import PurchaseOrderFilter, to_query
from itta_api.domains.assetmgmt.purchase_order.schemas import *  # noqa: F401,F403


def test_empty_filter_has_no_params():
    assert to_query(PurchaseOrderFilter()) == {}


def test_free_text_is_trimmed():
    assert to_query(PurchaseOrderFilter(q="  core ")) == {"q": "core"}


def test_status_list_is_comma_joined():
    query = to_query(PurchaseOrderFilter(status=[LifecycleStatus.ACTIVE, LifecycleStatus.PLANNED]))
    assert query["status"] == "ACTIVE,PLANNED"


def test_code_is_trimmed_into_query():
    assert to_query(PurchaseOrderFilter(code="  abc ")) == {"code": "abc"}


def test_code_blank_is_omitted():
    assert "code" not in to_query(PurchaseOrderFilter(code=""))


def test_name_is_trimmed_into_query():
    assert to_query(PurchaseOrderFilter(name="  abc ")) == {"name": "abc"}


def test_name_blank_is_omitted():
    assert "name" not in to_query(PurchaseOrderFilter(name=""))


def test_external_ref_is_trimmed_into_query():
    assert to_query(PurchaseOrderFilter(external_ref="  abc ")) == {"externalRef": "abc"}


def test_external_ref_blank_is_omitted():
    assert "externalRef" not in to_query(PurchaseOrderFilter(external_ref=""))


def test_po_number_is_trimmed_into_query():
    assert to_query(PurchaseOrderFilter(po_number="  abc ")) == {"poNumber": "abc"}


def test_po_number_blank_is_omitted():
    assert "poNumber" not in to_query(PurchaseOrderFilter(po_number=""))


def test_vendor_id_is_stringified():
    value = UUID("00000000-0000-0000-0000-000000000011")
    assert to_query(PurchaseOrderFilter(vendor_id=value))["vendorId"] == str(value)


def test_ordered_on_window_is_iso_formatted():
    query = to_query(PurchaseOrderFilter(ordered_on_from=date(2026, 1, 1), ordered_on_to=date(2026, 1, 1)))
    assert query["orderedOnFrom"].startswith("2026-01-01")
    assert query["orderedOnTo"].startswith("2026-01-01")


def test_total_amount_range_is_sent():
    query = to_query(PurchaseOrderFilter(total_amount_min=Decimal("1.5"), total_amount_max=Decimal("9.5")))
    assert query["totalAmountMin"] == str(Decimal("1.5"))
    assert query["totalAmountMax"] == str(Decimal("9.5"))


def test_total_amount_zero_is_not_dropped():
    assert to_query(PurchaseOrderFilter(total_amount_min=0))["totalAmountMin"] == "0"


def test_currency_is_trimmed_into_query():
    assert to_query(PurchaseOrderFilter(currency="  abc ")) == {"currency": "abc"}


def test_currency_blank_is_omitted():
    assert "currency" not in to_query(PurchaseOrderFilter(currency=""))


def test_owner_is_trimmed_into_query():
    assert to_query(PurchaseOrderFilter(owner="  abc ")) == {"owner": "abc"}


def test_owner_blank_is_omitted():
    assert "owner" not in to_query(PurchaseOrderFilter(owner=""))


def test_criticality_values_are_comma_joined():
    members = list(PurchaseOrderCriticality)[:2]
    query = to_query(PurchaseOrderFilter(criticality=members))
    assert query["criticality"] == ",".join(m.value for m in members)


def test_cost_center_is_trimmed_into_query():
    assert to_query(PurchaseOrderFilter(cost_center="  abc ")) == {"costCenter": "abc"}


def test_cost_center_blank_is_omitted():
    assert "costCenter" not in to_query(PurchaseOrderFilter(cost_center=""))


def test_effective_from_window_is_iso_formatted():
    query = to_query(PurchaseOrderFilter(effective_from_from=date(2026, 1, 1), effective_from_to=date(2026, 1, 1)))
    assert query["effectiveFromFrom"].startswith("2026-01-01")
    assert query["effectiveFromTo"].startswith("2026-01-01")

