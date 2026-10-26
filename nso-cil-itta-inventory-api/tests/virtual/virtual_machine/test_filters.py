"""Filter translation tests for Virtual Machine."""
from datetime import date, datetime, timezone
from decimal import Decimal
from uuid import UUID

from itta_core.models import LifecycleStatus
from itta_api.domains.virtual.virtual_machine.filters import VirtualMachineFilter, to_query
from itta_api.domains.virtual.virtual_machine.schemas import *  # noqa: F401,F403


def test_empty_filter_has_no_params():
    assert to_query(VirtualMachineFilter()) == {}


def test_free_text_is_trimmed():
    assert to_query(VirtualMachineFilter(q="  core ")) == {"q": "core"}


def test_status_list_is_comma_joined():
    query = to_query(VirtualMachineFilter(status=[LifecycleStatus.ACTIVE, LifecycleStatus.PLANNED]))
    assert query["status"] == "ACTIVE,PLANNED"


def test_code_is_trimmed_into_query():
    assert to_query(VirtualMachineFilter(code="  abc ")) == {"code": "abc"}


def test_code_blank_is_omitted():
    assert "code" not in to_query(VirtualMachineFilter(code=""))


def test_name_is_trimmed_into_query():
    assert to_query(VirtualMachineFilter(name="  abc ")) == {"name": "abc"}


def test_name_blank_is_omitted():
    assert "name" not in to_query(VirtualMachineFilter(name=""))


def test_external_ref_is_trimmed_into_query():
    assert to_query(VirtualMachineFilter(external_ref="  abc ")) == {"externalRef": "abc"}


def test_external_ref_blank_is_omitted():
    assert "externalRef" not in to_query(VirtualMachineFilter(external_ref=""))


def test_hypervisor_id_is_stringified():
    value = UUID("00000000-0000-0000-0000-000000000011")
    assert to_query(VirtualMachineFilter(hypervisor_id=value))["hypervisorId"] == str(value)


def test_vm_name_is_trimmed_into_query():
    assert to_query(VirtualMachineFilter(vm_name="  abc ")) == {"vmName": "abc"}


def test_vm_name_blank_is_omitted():
    assert "vmName" not in to_query(VirtualMachineFilter(vm_name=""))


def test_vcpu_range_is_sent():
    query = to_query(VirtualMachineFilter(vcpu_min=1, vcpu_max=9))
    assert query["vcpuMin"] == str(1)
    assert query["vcpuMax"] == str(9)


def test_vcpu_zero_is_not_dropped():
    assert to_query(VirtualMachineFilter(vcpu_min=0))["vcpuMin"] == "0"


def test_memory_gb_range_is_sent():
    query = to_query(VirtualMachineFilter(memory_gb_min=1, memory_gb_max=9))
    assert query["memoryGbMin"] == str(1)
    assert query["memoryGbMax"] == str(9)


def test_memory_gb_zero_is_not_dropped():
    assert to_query(VirtualMachineFilter(memory_gb_min=0))["memoryGbMin"] == "0"


def test_disk_gb_range_is_sent():
    query = to_query(VirtualMachineFilter(disk_gb_min=1, disk_gb_max=9))
    assert query["diskGbMin"] == str(1)
    assert query["diskGbMax"] == str(9)


def test_disk_gb_zero_is_not_dropped():
    assert to_query(VirtualMachineFilter(disk_gb_min=0))["diskGbMin"] == "0"


def test_power_state_values_are_comma_joined():
    members = list(VirtualMachinePowerState)[:2]
    query = to_query(VirtualMachineFilter(power_state=members))
    assert query["powerState"] == ",".join(m.value for m in members)


def test_criticality_values_are_comma_joined():
    members = list(VirtualMachineCriticality)[:2]
    query = to_query(VirtualMachineFilter(criticality=members))
    assert query["criticality"] == ",".join(m.value for m in members)


def test_cost_center_is_trimmed_into_query():
    assert to_query(VirtualMachineFilter(cost_center="  abc ")) == {"costCenter": "abc"}


def test_cost_center_blank_is_omitted():
    assert "costCenter" not in to_query(VirtualMachineFilter(cost_center=""))


def test_effective_from_window_is_iso_formatted():
    query = to_query(VirtualMachineFilter(effective_from_from=date(2026, 1, 1), effective_from_to=date(2026, 1, 1)))
    assert query["effectiveFromFrom"].startswith("2026-01-01")
    assert query["effectiveFromTo"].startswith("2026-01-01")


def test_effective_to_window_is_iso_formatted():
    query = to_query(VirtualMachineFilter(effective_to_from=date(2026, 1, 1), effective_to_to=date(2026, 1, 1)))
    assert query["effectiveToFrom"].startswith("2026-01-01")
    assert query["effectiveToTo"].startswith("2026-01-01")

