"""Filter translation tests for Circuit Segment."""
from datetime import date, datetime, timezone
from decimal import Decimal
from uuid import UUID

from itta_core.models import LifecycleStatus
from itta_api.domains.circuit.circuit_segment.filters import CircuitSegmentFilter, to_query
from itta_api.domains.circuit.circuit_segment.schemas import *  # noqa: F401,F403


def test_empty_filter_has_no_params():
    assert to_query(CircuitSegmentFilter()) == {}


def test_free_text_is_trimmed():
    assert to_query(CircuitSegmentFilter(q="  core ")) == {"q": "core"}


def test_status_list_is_comma_joined():
    query = to_query(CircuitSegmentFilter(status=[LifecycleStatus.ACTIVE, LifecycleStatus.PLANNED]))
    assert query["status"] == "ACTIVE,PLANNED"


def test_code_is_trimmed_into_query():
    assert to_query(CircuitSegmentFilter(code="  abc ")) == {"code": "abc"}


def test_code_blank_is_omitted():
    assert "code" not in to_query(CircuitSegmentFilter(code=""))


def test_name_is_trimmed_into_query():
    assert to_query(CircuitSegmentFilter(name="  abc ")) == {"name": "abc"}


def test_name_blank_is_omitted():
    assert "name" not in to_query(CircuitSegmentFilter(name=""))


def test_external_ref_is_trimmed_into_query():
    assert to_query(CircuitSegmentFilter(external_ref="  abc ")) == {"externalRef": "abc"}


def test_external_ref_blank_is_omitted():
    assert "externalRef" not in to_query(CircuitSegmentFilter(external_ref=""))


def test_circuit_id_is_stringified():
    value = UUID("00000000-0000-0000-0000-000000000011")
    assert to_query(CircuitSegmentFilter(circuit_id=value))["circuitId"] == str(value)


def test_segment_order_range_is_sent():
    query = to_query(CircuitSegmentFilter(segment_order_min=1, segment_order_max=9))
    assert query["segmentOrderMin"] == str(1)
    assert query["segmentOrderMax"] == str(9)


def test_segment_order_zero_is_not_dropped():
    assert to_query(CircuitSegmentFilter(segment_order_min=0))["segmentOrderMin"] == "0"


def test_a_termination_id_is_stringified():
    value = UUID("00000000-0000-0000-0000-000000000011")
    assert to_query(CircuitSegmentFilter(a_termination_id=value))["aTerminationId"] == str(value)


def test_z_termination_id_is_stringified():
    value = UUID("00000000-0000-0000-0000-000000000011")
    assert to_query(CircuitSegmentFilter(z_termination_id=value))["zTerminationId"] == str(value)


def test_latency_ms_range_is_sent():
    query = to_query(CircuitSegmentFilter(latency_ms_min=Decimal("1.5"), latency_ms_max=Decimal("9.5")))
    assert query["latencyMsMin"] == str(Decimal("1.5"))
    assert query["latencyMsMax"] == str(Decimal("9.5"))


def test_latency_ms_zero_is_not_dropped():
    assert to_query(CircuitSegmentFilter(latency_ms_min=0))["latencyMsMin"] == "0"


def test_source_system_is_trimmed_into_query():
    assert to_query(CircuitSegmentFilter(source_system="  abc ")) == {"sourceSystem": "abc"}


def test_source_system_blank_is_omitted():
    assert "sourceSystem" not in to_query(CircuitSegmentFilter(source_system=""))


def test_region_is_trimmed_into_query():
    assert to_query(CircuitSegmentFilter(region="  abc ")) == {"region": "abc"}


def test_region_blank_is_omitted():
    assert "region" not in to_query(CircuitSegmentFilter(region=""))


def test_owner_is_trimmed_into_query():
    assert to_query(CircuitSegmentFilter(owner="  abc ")) == {"owner": "abc"}


def test_owner_blank_is_omitted():
    assert "owner" not in to_query(CircuitSegmentFilter(owner=""))


def test_criticality_values_are_comma_joined():
    members = list(CircuitSegmentCriticality)[:2]
    query = to_query(CircuitSegmentFilter(criticality=members))
    assert query["criticality"] == ",".join(m.value for m in members)


def test_cost_center_is_trimmed_into_query():
    assert to_query(CircuitSegmentFilter(cost_center="  abc ")) == {"costCenter": "abc"}


def test_cost_center_blank_is_omitted():
    assert "costCenter" not in to_query(CircuitSegmentFilter(cost_center=""))

