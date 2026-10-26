"""Field catalogue tests for Port."""
import pytest

from itta_api.domains.port.port.meta import FIELDS, META, field_names, required_fields

BY_NAME = {f.name: f for f in FIELDS}

EXPECTED = {
    "code": ("Code", True),
    "name": ("Name", True),
    "description": ("Description", False),
    "status": ("Status", True),
    "externalRef": ("External Ref", False),
    "deviceId": ("Device Id", True),
    "cardId": ("Card Id", False),
    "portNumber": ("Port Number", True),
    "speedMbps": ("Speed Mbps", False),
    "mediaType": ("Media Type", False),
    "adminState": ("Admin State", False),
    "sourceSystem": ("Source System", False),
    "region": ("Region", False),
    "owner": ("Owner", False),
    "criticality": ("Criticality", False),
    "costCenter": ("Cost Center", False),
}


def test_entity_identity():
    assert META.entity == "Port"
    assert META.domain == "port"
    assert META.path == "ports"


def test_field_names_are_unique():
    assert len(field_names()) == len(set(field_names())) == 16


def test_required_fields_include_code_and_name():
    assert {"code", "name", "status"} <= set(required_fields())


@pytest.mark.parametrize("name,expected", sorted(EXPECTED.items()))
def test_field_label_and_requiredness(name, expected):
    field = BY_NAME[name]
    assert (field.label, field.required) == expected
