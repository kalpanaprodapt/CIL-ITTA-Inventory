"""Filter translation tests for Circuit Termination."""
from datetime import date, datetime, timezone
from decimal import Decimal
from uuid import UUID

from itta_core.models import LifecycleStatus
from itta_api.domains.circuit.circuit_termination.filters import CircuitTerminationFilter, to_query
from itta_api.domains.circuit.circuit_termination.schemas import *  # noqa: F401,F403


def test_empty_filter_has_no_params():
    assert to_query(CircuitTerminationFilter()) == {}


def test_free_text_is_trimmed():
    assert to_query(CircuitTerminationFilter(q="  core ")) == {"q": "core"}


def test_status_list_is_comma_joined():
    query = to_query(CircuitTerminationFilter(status=[LifecycleStatus.ACTIVE, LifecycleStatus.PLANNED]))
    assert query["status"] == "ACTIVE,PLANNED"


def test_code_is_trimmed_into_query():
    assert to_query(CircuitTerminationFilter(code="  abc ")) == {"code": "abc"}


def test_code_blank_is_omitted():
    assert "code" not in to_query(CircuitTerminationFilter(code=""))


def test_name_is_trimmed_into_query():
    assert to_query(CircuitTerminationFilter(name="  abc ")) == {"name": "abc"}


def test_name_blank_is_omitted():
    assert "name" not in to_query(CircuitTerminationFilter(name=""))


def test_external_ref_is_trimmed_into_query():
    assert to_query(CircuitTerminationFilter(external_ref="  abc ")) == {"externalRef": "abc"}


def test_external_ref_blank_is_omitted():
    assert "externalRef" not in to_query(CircuitTerminationFilter(external_ref=""))


def test_circuit_id_is_stringified():
    value = UUID("00000000-0000-0000-0000-000000000011")
    assert to_query(CircuitTerminationFilter(circuit_id=value))["circuitId"] == str(value)


def test_side_values_are_comma_joined():
    members = list(CircuitTerminationSide)[:2]
    query = to_query(CircuitTerminationFilter(side=members))
    assert query["side"] == ",".join(m.value for m in members)


def test_port_id_is_stringified():
    value = UUID("00000000-0000-0000-0000-000000000011")
    assert to_query(CircuitTerminationFilter(port_id=value))["portId"] == str(value)


def test_xconnect_id_is_trimmed_into_query():
    assert to_query(CircuitTerminationFilter(xconnect_id="  abc ")) == {"xconnectId": "abc"}


def test_xconnect_id_blank_is_omitted():
    assert "xconnectId" not in to_query(CircuitTerminationFilter(xconnect_id=""))


def test_patch_info_is_trimmed_into_query():
    assert to_query(CircuitTerminationFilter(patch_info="  abc ")) == {"patchInfo": "abc"}


def test_patch_info_blank_is_omitted():
    assert "patchInfo" not in to_query(CircuitTerminationFilter(patch_info=""))


def test_cost_center_is_trimmed_into_query():
    assert to_query(CircuitTerminationFilter(cost_center="  abc ")) == {"costCenter": "abc"}


def test_cost_center_blank_is_omitted():
    assert "costCenter" not in to_query(CircuitTerminationFilter(cost_center=""))


def test_effective_from_window_is_iso_formatted():
    query = to_query(CircuitTerminationFilter(effective_from_from=date(2026, 1, 1), effective_from_to=date(2026, 1, 1)))
    assert query["effectiveFromFrom"].startswith("2026-01-01")
    assert query["effectiveFromTo"].startswith("2026-01-01")


def test_effective_to_window_is_iso_formatted():
    query = to_query(CircuitTerminationFilter(effective_to_from=date(2026, 1, 1), effective_to_to=date(2026, 1, 1)))
    assert query["effectiveToFrom"].startswith("2026-01-01")
    assert query["effectiveToTo"].startswith("2026-01-01")


def test_labels_is_trimmed_into_query():
    assert to_query(CircuitTerminationFilter(labels="  abc ")) == {"labels": "abc"}


def test_labels_blank_is_omitted():
    assert "labels" not in to_query(CircuitTerminationFilter(labels=""))

