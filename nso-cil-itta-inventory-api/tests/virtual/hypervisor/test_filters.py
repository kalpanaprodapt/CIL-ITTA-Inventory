"""Filter translation tests for Hypervisor."""
from datetime import date, datetime, timezone
from decimal import Decimal
from uuid import UUID

from itta_core.models import LifecycleStatus
from itta_api.domains.virtual.hypervisor.filters import HypervisorFilter, to_query
from itta_api.domains.virtual.hypervisor.schemas import *  # noqa: F401,F403


def test_empty_filter_has_no_params():
    assert to_query(HypervisorFilter()) == {}


def test_free_text_is_trimmed():
    assert to_query(HypervisorFilter(q="  core ")) == {"q": "core"}


def test_status_list_is_comma_joined():
    query = to_query(HypervisorFilter(status=[LifecycleStatus.ACTIVE, LifecycleStatus.PLANNED]))
    assert query["status"] == "ACTIVE,PLANNED"


def test_code_is_trimmed_into_query():
    assert to_query(HypervisorFilter(code="  abc ")) == {"code": "abc"}


def test_code_blank_is_omitted():
    assert "code" not in to_query(HypervisorFilter(code=""))


def test_name_is_trimmed_into_query():
    assert to_query(HypervisorFilter(name="  abc ")) == {"name": "abc"}


def test_name_blank_is_omitted():
    assert "name" not in to_query(HypervisorFilter(name=""))


def test_external_ref_is_trimmed_into_query():
    assert to_query(HypervisorFilter(external_ref="  abc ")) == {"externalRef": "abc"}


def test_external_ref_blank_is_omitted():
    assert "externalRef" not in to_query(HypervisorFilter(external_ref=""))


def test_host_device_id_is_stringified():
    value = UUID("00000000-0000-0000-0000-000000000011")
    assert to_query(HypervisorFilter(host_device_id=value))["hostDeviceId"] == str(value)


def test_hypervisor_type_values_are_comma_joined():
    members = list(HypervisorHypervisorType)[:2]
    query = to_query(HypervisorFilter(hypervisor_type=members))
    assert query["hypervisorType"] == ",".join(m.value for m in members)


def test_hv_version_is_trimmed_into_query():
    assert to_query(HypervisorFilter(hv_version="  abc ")) == {"hvVersion": "abc"}


def test_hv_version_blank_is_omitted():
    assert "hvVersion" not in to_query(HypervisorFilter(hv_version=""))


def test_vcpu_capacity_range_is_sent():
    query = to_query(HypervisorFilter(vcpu_capacity_min=1, vcpu_capacity_max=9))
    assert query["vcpuCapacityMin"] == str(1)
    assert query["vcpuCapacityMax"] == str(9)


def test_vcpu_capacity_zero_is_not_dropped():
    assert to_query(HypervisorFilter(vcpu_capacity_min=0))["vcpuCapacityMin"] == "0"


def test_memory_gb_capacity_range_is_sent():
    query = to_query(HypervisorFilter(memory_gb_capacity_min=1, memory_gb_capacity_max=9))
    assert query["memoryGbCapacityMin"] == str(1)
    assert query["memoryGbCapacityMax"] == str(9)


def test_memory_gb_capacity_zero_is_not_dropped():
    assert to_query(HypervisorFilter(memory_gb_capacity_min=0))["memoryGbCapacityMin"] == "0"


def test_region_is_trimmed_into_query():
    assert to_query(HypervisorFilter(region="  abc ")) == {"region": "abc"}


def test_region_blank_is_omitted():
    assert "region" not in to_query(HypervisorFilter(region=""))


def test_owner_is_trimmed_into_query():
    assert to_query(HypervisorFilter(owner="  abc ")) == {"owner": "abc"}


def test_owner_blank_is_omitted():
    assert "owner" not in to_query(HypervisorFilter(owner=""))


def test_criticality_values_are_comma_joined():
    members = list(HypervisorCriticality)[:2]
    query = to_query(HypervisorFilter(criticality=members))
    assert query["criticality"] == ",".join(m.value for m in members)


def test_cost_center_is_trimmed_into_query():
    assert to_query(HypervisorFilter(cost_center="  abc ")) == {"costCenter": "abc"}


def test_cost_center_blank_is_omitted():
    assert "costCenter" not in to_query(HypervisorFilter(cost_center=""))

