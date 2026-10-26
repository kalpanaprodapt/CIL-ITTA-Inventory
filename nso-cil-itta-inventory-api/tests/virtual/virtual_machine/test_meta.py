"""Field catalogue tests for Virtual Machine."""
import pytest

from itta_api.domains.virtual.virtual_machine.meta import FIELDS, META, field_names, required_fields

BY_NAME = {f.name: f for f in FIELDS}

EXPECTED = {
    "code": ("Code", True),
    "name": ("Name", True),
    "description": ("Description", False),
    "status": ("Status", True),
    "externalRef": ("External Ref", False),
    "hypervisorId": ("Hypervisor Id", True),
    "vmName": ("Vm Name", True),
    "vcpu": ("Vcpu", True),
    "memoryGb": ("Memory Gb", True),
    "diskGb": ("Disk Gb", False),
    "powerState": ("Power State", False),
    "criticality": ("Criticality", False),
    "costCenter": ("Cost Center", False),
    "notes": ("Notes", False),
    "effectiveFrom": ("Effective From", False),
    "effectiveTo": ("Effective To", False),
}


def test_entity_identity():
    assert META.entity == "VirtualMachine"
    assert META.domain == "virtual"
    assert META.path == "virtual-machines"


def test_field_names_are_unique():
    assert len(field_names()) == len(set(field_names())) == 16


def test_required_fields_include_code_and_name():
    assert {"code", "name", "status"} <= set(required_fields())


@pytest.mark.parametrize("name,expected", sorted(EXPECTED.items()))
def test_field_label_and_requiredness(name, expected):
    field = BY_NAME[name]
    assert (field.label, field.required) == expected
