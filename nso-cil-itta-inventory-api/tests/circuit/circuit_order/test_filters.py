"""Filter translation tests for Circuit Order."""
from datetime import date, datetime, timezone
from decimal import Decimal
from uuid import UUID

from itta_core.models import LifecycleStatus
from itta_api.domains.circuit.circuit_order.filters import CircuitOrderFilter, to_query
from itta_api.domains.circuit.circuit_order.schemas import *  # noqa: F401,F403


def test_empty_filter_has_no_params():
    assert to_query(CircuitOrderFilter()) == {}


def test_free_text_is_trimmed():
    assert to_query(CircuitOrderFilter(q="  core ")) == {"q": "core"}


def test_status_list_is_comma_joined():
    query = to_query(CircuitOrderFilter(status=[LifecycleStatus.ACTIVE, LifecycleStatus.PLANNED]))
    assert query["status"] == "ACTIVE,PLANNED"


def test_code_is_trimmed_into_query():
    assert to_query(CircuitOrderFilter(code="  abc ")) == {"code": "abc"}


def test_code_blank_is_omitted():
    assert "code" not in to_query(CircuitOrderFilter(code=""))


def test_name_is_trimmed_into_query():
    assert to_query(CircuitOrderFilter(name="  abc ")) == {"name": "abc"}


def test_name_blank_is_omitted():
    assert "name" not in to_query(CircuitOrderFilter(name=""))


def test_external_ref_is_trimmed_into_query():
    assert to_query(CircuitOrderFilter(external_ref="  abc ")) == {"externalRef": "abc"}


def test_external_ref_blank_is_omitted():
    assert "externalRef" not in to_query(CircuitOrderFilter(external_ref=""))


def test_circuit_id_is_stringified():
    value = UUID("00000000-0000-0000-0000-000000000011")
    assert to_query(CircuitOrderFilter(circuit_id=value))["circuitId"] == str(value)


def test_order_number_is_trimmed_into_query():
    assert to_query(CircuitOrderFilter(order_number="  abc ")) == {"orderNumber": "abc"}


def test_order_number_blank_is_omitted():
    assert "orderNumber" not in to_query(CircuitOrderFilter(order_number=""))


def test_ordered_on_window_is_iso_formatted():
    query = to_query(CircuitOrderFilter(ordered_on_from=date(2026, 1, 1), ordered_on_to=date(2026, 1, 1)))
    assert query["orderedOnFrom"].startswith("2026-01-01")
    assert query["orderedOnTo"].startswith("2026-01-01")


def test_requested_date_window_is_iso_formatted():
    query = to_query(CircuitOrderFilter(requested_date_from=date(2026, 1, 1), requested_date_to=date(2026, 1, 1)))
    assert query["requestedDateFrom"].startswith("2026-01-01")
    assert query["requestedDateTo"].startswith("2026-01-01")


def test_committed_date_window_is_iso_formatted():
    query = to_query(CircuitOrderFilter(committed_date_from=date(2026, 1, 1), committed_date_to=date(2026, 1, 1)))
    assert query["committedDateFrom"].startswith("2026-01-01")
    assert query["committedDateTo"].startswith("2026-01-01")


def test_order_status_values_are_comma_joined():
    members = list(CircuitOrderOrderStatus)[:2]
    query = to_query(CircuitOrderFilter(order_status=members))
    assert query["orderStatus"] == ",".join(m.value for m in members)


def test_effective_from_window_is_iso_formatted():
    query = to_query(CircuitOrderFilter(effective_from_from=date(2026, 1, 1), effective_from_to=date(2026, 1, 1)))
    assert query["effectiveFromFrom"].startswith("2026-01-01")
    assert query["effectiveFromTo"].startswith("2026-01-01")


def test_effective_to_window_is_iso_formatted():
    query = to_query(CircuitOrderFilter(effective_to_from=date(2026, 1, 1), effective_to_to=date(2026, 1, 1)))
    assert query["effectiveToFrom"].startswith("2026-01-01")
    assert query["effectiveToTo"].startswith("2026-01-01")


def test_labels_is_trimmed_into_query():
    assert to_query(CircuitOrderFilter(labels="  abc ")) == {"labels": "abc"}


def test_labels_blank_is_omitted():
    assert "labels" not in to_query(CircuitOrderFilter(labels=""))


def test_lifecycle_stage_values_are_comma_joined():
    members = list(CircuitOrderLifecycleStage)[:2]
    query = to_query(CircuitOrderFilter(lifecycle_stage=members))
    assert query["lifecycleStage"] == ",".join(m.value for m in members)


def test_source_system_is_trimmed_into_query():
    assert to_query(CircuitOrderFilter(source_system="  abc ")) == {"sourceSystem": "abc"}


def test_source_system_blank_is_omitted():
    assert "sourceSystem" not in to_query(CircuitOrderFilter(source_system=""))

