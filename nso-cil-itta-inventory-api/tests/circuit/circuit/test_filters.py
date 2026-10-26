"""Filter translation tests for Circuit."""
from datetime import date, datetime, timezone
from decimal import Decimal
from uuid import UUID

from itta_core.models import LifecycleStatus
from itta_api.domains.circuit.circuit.filters import CircuitFilter, to_query
from itta_api.domains.circuit.circuit.schemas import *  # noqa: F401,F403


def test_empty_filter_has_no_params():
    assert to_query(CircuitFilter()) == {}


def test_free_text_is_trimmed():
    assert to_query(CircuitFilter(q="  core ")) == {"q": "core"}


def test_status_list_is_comma_joined():
    query = to_query(CircuitFilter(status=[LifecycleStatus.ACTIVE, LifecycleStatus.PLANNED]))
    assert query["status"] == "ACTIVE,PLANNED"


def test_code_is_trimmed_into_query():
    assert to_query(CircuitFilter(code="  abc ")) == {"code": "abc"}


def test_code_blank_is_omitted():
    assert "code" not in to_query(CircuitFilter(code=""))


def test_name_is_trimmed_into_query():
    assert to_query(CircuitFilter(name="  abc ")) == {"name": "abc"}


def test_name_blank_is_omitted():
    assert "name" not in to_query(CircuitFilter(name=""))


def test_external_ref_is_trimmed_into_query():
    assert to_query(CircuitFilter(external_ref="  abc ")) == {"externalRef": "abc"}


def test_external_ref_blank_is_omitted():
    assert "externalRef" not in to_query(CircuitFilter(external_ref=""))


def test_circuit_id_is_trimmed_into_query():
    assert to_query(CircuitFilter(circuit_id="  abc ")) == {"circuitId": "abc"}


def test_circuit_id_blank_is_omitted():
    assert "circuitId" not in to_query(CircuitFilter(circuit_id=""))


def test_circuit_type_values_are_comma_joined():
    members = list(CircuitCircuitType)[:2]
    query = to_query(CircuitFilter(circuit_type=members))
    assert query["circuitType"] == ",".join(m.value for m in members)


def test_bandwidth_mbps_range_is_sent():
    query = to_query(CircuitFilter(bandwidth_mbps_min=1, bandwidth_mbps_max=9))
    assert query["bandwidthMbpsMin"] == str(1)
    assert query["bandwidthMbpsMax"] == str(9)


def test_bandwidth_mbps_zero_is_not_dropped():
    assert to_query(CircuitFilter(bandwidth_mbps_min=0))["bandwidthMbpsMin"] == "0"


def test_provider_id_is_stringified():
    value = UUID("00000000-0000-0000-0000-000000000011")
    assert to_query(CircuitFilter(provider_id=value))["providerId"] == str(value)


def test_a_site_id_is_stringified():
    value = UUID("00000000-0000-0000-0000-000000000011")
    assert to_query(CircuitFilter(a_site_id=value))["aSiteId"] == str(value)


def test_z_site_id_is_stringified():
    value = UUID("00000000-0000-0000-0000-000000000011")
    assert to_query(CircuitFilter(z_site_id=value))["zSiteId"] == str(value)


def test_labels_is_trimmed_into_query():
    assert to_query(CircuitFilter(labels="  abc ")) == {"labels": "abc"}


def test_labels_blank_is_omitted():
    assert "labels" not in to_query(CircuitFilter(labels=""))


def test_lifecycle_stage_values_are_comma_joined():
    members = list(CircuitLifecycleStage)[:2]
    query = to_query(CircuitFilter(lifecycle_stage=members))
    assert query["lifecycleStage"] == ",".join(m.value for m in members)


def test_source_system_is_trimmed_into_query():
    assert to_query(CircuitFilter(source_system="  abc ")) == {"sourceSystem": "abc"}


def test_source_system_blank_is_omitted():
    assert "sourceSystem" not in to_query(CircuitFilter(source_system=""))


def test_region_is_trimmed_into_query():
    assert to_query(CircuitFilter(region="  abc ")) == {"region": "abc"}


def test_region_blank_is_omitted():
    assert "region" not in to_query(CircuitFilter(region=""))


def test_owner_is_trimmed_into_query():
    assert to_query(CircuitFilter(owner="  abc ")) == {"owner": "abc"}


def test_owner_blank_is_omitted():
    assert "owner" not in to_query(CircuitFilter(owner=""))

