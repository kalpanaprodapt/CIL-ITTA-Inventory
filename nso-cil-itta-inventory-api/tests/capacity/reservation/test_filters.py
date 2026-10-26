"""Filter translation tests for Reservation."""
from datetime import date, datetime, timezone
from decimal import Decimal
from uuid import UUID

from itta_core.models import LifecycleStatus
from itta_api.domains.capacity.reservation.filters import ReservationFilter, to_query
from itta_api.domains.capacity.reservation.schemas import *  # noqa: F401,F403


def test_empty_filter_has_no_params():
    assert to_query(ReservationFilter()) == {}


def test_free_text_is_trimmed():
    assert to_query(ReservationFilter(q="  core ")) == {"q": "core"}


def test_status_list_is_comma_joined():
    query = to_query(ReservationFilter(status=[LifecycleStatus.ACTIVE, LifecycleStatus.PLANNED]))
    assert query["status"] == "ACTIVE,PLANNED"


def test_code_is_trimmed_into_query():
    assert to_query(ReservationFilter(code="  abc ")) == {"code": "abc"}


def test_code_blank_is_omitted():
    assert "code" not in to_query(ReservationFilter(code=""))


def test_name_is_trimmed_into_query():
    assert to_query(ReservationFilter(name="  abc ")) == {"name": "abc"}


def test_name_blank_is_omitted():
    assert "name" not in to_query(ReservationFilter(name=""))


def test_external_ref_is_trimmed_into_query():
    assert to_query(ReservationFilter(external_ref="  abc ")) == {"externalRef": "abc"}


def test_external_ref_blank_is_omitted():
    assert "externalRef" not in to_query(ReservationFilter(external_ref=""))


def test_resource_ref_is_trimmed_into_query():
    assert to_query(ReservationFilter(resource_ref="  abc ")) == {"resourceRef": "abc"}


def test_resource_ref_blank_is_omitted():
    assert "resourceRef" not in to_query(ReservationFilter(resource_ref=""))


def test_reserved_for_is_trimmed_into_query():
    assert to_query(ReservationFilter(reserved_for="  abc ")) == {"reservedFor": "abc"}


def test_reserved_for_blank_is_omitted():
    assert "reservedFor" not in to_query(ReservationFilter(reserved_for=""))


def test_quantity_range_is_sent():
    query = to_query(ReservationFilter(quantity_min=Decimal("1.5"), quantity_max=Decimal("9.5")))
    assert query["quantityMin"] == str(Decimal("1.5"))
    assert query["quantityMax"] == str(Decimal("9.5"))


def test_quantity_zero_is_not_dropped():
    assert to_query(ReservationFilter(quantity_min=0))["quantityMin"] == "0"


def test_start_date_window_is_iso_formatted():
    query = to_query(ReservationFilter(start_date_from=date(2026, 1, 1), start_date_to=date(2026, 1, 1)))
    assert query["startDateFrom"].startswith("2026-01-01")
    assert query["startDateTo"].startswith("2026-01-01")


def test_end_date_window_is_iso_formatted():
    query = to_query(ReservationFilter(end_date_from=date(2026, 1, 1), end_date_to=date(2026, 1, 1)))
    assert query["endDateFrom"].startswith("2026-01-01")
    assert query["endDateTo"].startswith("2026-01-01")


def test_effective_from_window_is_iso_formatted():
    query = to_query(ReservationFilter(effective_from_from=date(2026, 1, 1), effective_from_to=date(2026, 1, 1)))
    assert query["effectiveFromFrom"].startswith("2026-01-01")
    assert query["effectiveFromTo"].startswith("2026-01-01")


def test_effective_to_window_is_iso_formatted():
    query = to_query(ReservationFilter(effective_to_from=date(2026, 1, 1), effective_to_to=date(2026, 1, 1)))
    assert query["effectiveToFrom"].startswith("2026-01-01")
    assert query["effectiveToTo"].startswith("2026-01-01")


def test_labels_is_trimmed_into_query():
    assert to_query(ReservationFilter(labels="  abc ")) == {"labels": "abc"}


def test_labels_blank_is_omitted():
    assert "labels" not in to_query(ReservationFilter(labels=""))


def test_lifecycle_stage_values_are_comma_joined():
    members = list(ReservationLifecycleStage)[:2]
    query = to_query(ReservationFilter(lifecycle_stage=members))
    assert query["lifecycleStage"] == ",".join(m.value for m in members)

