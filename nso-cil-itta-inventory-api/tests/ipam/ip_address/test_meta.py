"""Field catalogue tests for Ip Address."""
import pytest

from itta_api.domains.ipam.ip_address.meta import FIELDS, META, field_names, required_fields

BY_NAME = {f.name: f for f in FIELDS}

EXPECTED = {
    "code": ("Code", True),
    "name": ("Name", True),
    "description": ("Description", False),
    "status": ("Status", True),
    "externalRef": ("External Ref", False),
    "subnetId": ("Subnet Id", True),
    "address": ("Address", True),
    "assignedToId": ("Assigned To Id", False),
    "assignmentType": ("Assignment Type", False),
    "dnsName": ("Dns Name", False),
    "costCenter": ("Cost Center", False),
    "notes": ("Notes", False),
    "effectiveFrom": ("Effective From", False),
    "effectiveTo": ("Effective To", False),
    "labels": ("Labels", False),
}


def test_entity_identity():
    assert META.entity == "IpAddress"
    assert META.domain == "ipam"
    assert META.path == "ip-addresses"


def test_field_names_are_unique():
    assert len(field_names()) == len(set(field_names())) == 15


def test_required_fields_include_code_and_name():
    assert {"code", "name", "status"} <= set(required_fields())


@pytest.mark.parametrize("name,expected", sorted(EXPECTED.items()))
def test_field_label_and_requiredness(name, expected):
    field = BY_NAME[name]
    assert (field.label, field.required) == expected
