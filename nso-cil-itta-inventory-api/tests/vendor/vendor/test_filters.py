"""Filter translation tests for Vendor."""
from datetime import date, datetime, timezone
from decimal import Decimal
from uuid import UUID

from itta_core.models import LifecycleStatus
from itta_api.domains.vendor.vendor.filters import VendorFilter, to_query
from itta_api.domains.vendor.vendor.schemas import *  # noqa: F401,F403


def test_empty_filter_has_no_params():
    assert to_query(VendorFilter()) == {}


def test_free_text_is_trimmed():
    assert to_query(VendorFilter(q="  core ")) == {"q": "core"}


def test_status_list_is_comma_joined():
    query = to_query(VendorFilter(status=[LifecycleStatus.ACTIVE, LifecycleStatus.PLANNED]))
    assert query["status"] == "ACTIVE,PLANNED"


def test_code_is_trimmed_into_query():
    assert to_query(VendorFilter(code="  abc ")) == {"code": "abc"}


def test_code_blank_is_omitted():
    assert "code" not in to_query(VendorFilter(code=""))


def test_name_is_trimmed_into_query():
    assert to_query(VendorFilter(name="  abc ")) == {"name": "abc"}


def test_name_blank_is_omitted():
    assert "name" not in to_query(VendorFilter(name=""))


def test_external_ref_is_trimmed_into_query():
    assert to_query(VendorFilter(external_ref="  abc ")) == {"externalRef": "abc"}


def test_external_ref_blank_is_omitted():
    assert "externalRef" not in to_query(VendorFilter(external_ref=""))


def test_legal_name_is_trimmed_into_query():
    assert to_query(VendorFilter(legal_name="  abc ")) == {"legalName": "abc"}


def test_legal_name_blank_is_omitted():
    assert "legalName" not in to_query(VendorFilter(legal_name=""))


def test_support_email_is_trimmed_into_query():
    assert to_query(VendorFilter(support_email="  abc ")) == {"supportEmail": "abc"}


def test_support_email_blank_is_omitted():
    assert "supportEmail" not in to_query(VendorFilter(support_email=""))


def test_support_phone_is_trimmed_into_query():
    assert to_query(VendorFilter(support_phone="  abc ")) == {"supportPhone": "abc"}


def test_support_phone_blank_is_omitted():
    assert "supportPhone" not in to_query(VendorFilter(support_phone=""))


def test_website_is_trimmed_into_query():
    assert to_query(VendorFilter(website="  abc ")) == {"website": "abc"}


def test_website_blank_is_omitted():
    assert "website" not in to_query(VendorFilter(website=""))


def test_tier_values_are_comma_joined():
    members = list(VendorTier)[:2]
    query = to_query(VendorFilter(tier=members))
    assert query["tier"] == ",".join(m.value for m in members)


def test_effective_from_window_is_iso_formatted():
    query = to_query(VendorFilter(effective_from_from=date(2026, 1, 1), effective_from_to=date(2026, 1, 1)))
    assert query["effectiveFromFrom"].startswith("2026-01-01")
    assert query["effectiveFromTo"].startswith("2026-01-01")


def test_effective_to_window_is_iso_formatted():
    query = to_query(VendorFilter(effective_to_from=date(2026, 1, 1), effective_to_to=date(2026, 1, 1)))
    assert query["effectiveToFrom"].startswith("2026-01-01")
    assert query["effectiveToTo"].startswith("2026-01-01")


def test_labels_is_trimmed_into_query():
    assert to_query(VendorFilter(labels="  abc ")) == {"labels": "abc"}


def test_labels_blank_is_omitted():
    assert "labels" not in to_query(VendorFilter(labels=""))


def test_lifecycle_stage_values_are_comma_joined():
    members = list(VendorLifecycleStage)[:2]
    query = to_query(VendorFilter(lifecycle_stage=members))
    assert query["lifecycleStage"] == ",".join(m.value for m in members)


def test_source_system_is_trimmed_into_query():
    assert to_query(VendorFilter(source_system="  abc ")) == {"sourceSystem": "abc"}


def test_source_system_blank_is_omitted():
    assert "sourceSystem" not in to_query(VendorFilter(source_system=""))

