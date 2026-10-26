"""Filter translation tests for Invoice."""
from datetime import date, datetime, timezone
from decimal import Decimal
from uuid import UUID

from itta_core.models import LifecycleStatus
from itta_api.domains.assetmgmt.invoice.filters import InvoiceFilter, to_query
from itta_api.domains.assetmgmt.invoice.schemas import *  # noqa: F401,F403


def test_empty_filter_has_no_params():
    assert to_query(InvoiceFilter()) == {}


def test_free_text_is_trimmed():
    assert to_query(InvoiceFilter(q="  core ")) == {"q": "core"}


def test_status_list_is_comma_joined():
    query = to_query(InvoiceFilter(status=[LifecycleStatus.ACTIVE, LifecycleStatus.PLANNED]))
    assert query["status"] == "ACTIVE,PLANNED"


def test_code_is_trimmed_into_query():
    assert to_query(InvoiceFilter(code="  abc ")) == {"code": "abc"}


def test_code_blank_is_omitted():
    assert "code" not in to_query(InvoiceFilter(code=""))


def test_name_is_trimmed_into_query():
    assert to_query(InvoiceFilter(name="  abc ")) == {"name": "abc"}


def test_name_blank_is_omitted():
    assert "name" not in to_query(InvoiceFilter(name=""))


def test_external_ref_is_trimmed_into_query():
    assert to_query(InvoiceFilter(external_ref="  abc ")) == {"externalRef": "abc"}


def test_external_ref_blank_is_omitted():
    assert "externalRef" not in to_query(InvoiceFilter(external_ref=""))


def test_invoice_number_is_trimmed_into_query():
    assert to_query(InvoiceFilter(invoice_number="  abc ")) == {"invoiceNumber": "abc"}


def test_invoice_number_blank_is_omitted():
    assert "invoiceNumber" not in to_query(InvoiceFilter(invoice_number=""))


def test_po_id_is_stringified():
    value = UUID("00000000-0000-0000-0000-000000000011")
    assert to_query(InvoiceFilter(po_id=value))["poId"] == str(value)


def test_amount_range_is_sent():
    query = to_query(InvoiceFilter(amount_min=Decimal("1.5"), amount_max=Decimal("9.5")))
    assert query["amountMin"] == str(Decimal("1.5"))
    assert query["amountMax"] == str(Decimal("9.5"))


def test_amount_zero_is_not_dropped():
    assert to_query(InvoiceFilter(amount_min=0))["amountMin"] == "0"


def test_due_date_window_is_iso_formatted():
    query = to_query(InvoiceFilter(due_date_from=date(2026, 1, 1), due_date_to=date(2026, 1, 1)))
    assert query["dueDateFrom"].startswith("2026-01-01")
    assert query["dueDateTo"].startswith("2026-01-01")


def test_paid_is_lowercase_boolean():
    assert to_query(InvoiceFilter(paid=True))["paid"] == "true"
    assert to_query(InvoiceFilter(paid=False))["paid"] == "false"


def test_cost_center_is_trimmed_into_query():
    assert to_query(InvoiceFilter(cost_center="  abc ")) == {"costCenter": "abc"}


def test_cost_center_blank_is_omitted():
    assert "costCenter" not in to_query(InvoiceFilter(cost_center=""))


def test_effective_from_window_is_iso_formatted():
    query = to_query(InvoiceFilter(effective_from_from=date(2026, 1, 1), effective_from_to=date(2026, 1, 1)))
    assert query["effectiveFromFrom"].startswith("2026-01-01")
    assert query["effectiveFromTo"].startswith("2026-01-01")


def test_effective_to_window_is_iso_formatted():
    query = to_query(InvoiceFilter(effective_to_from=date(2026, 1, 1), effective_to_to=date(2026, 1, 1)))
    assert query["effectiveToFrom"].startswith("2026-01-01")
    assert query["effectiveToTo"].startswith("2026-01-01")


def test_labels_is_trimmed_into_query():
    assert to_query(InvoiceFilter(labels="  abc ")) == {"labels": "abc"}


def test_labels_blank_is_omitted():
    assert "labels" not in to_query(InvoiceFilter(labels=""))

