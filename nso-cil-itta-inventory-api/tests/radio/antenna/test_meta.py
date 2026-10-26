"""Field catalogue tests for Antenna."""
import pytest

from itta_api.domains.radio.antenna.meta import FIELDS, META, field_names, required_fields

BY_NAME = {f.name: f for f in FIELDS}

EXPECTED = {
    "code": ("Code", True),
    "name": ("Name", True),
    "description": ("Description", False),
    "status": ("Status", True),
    "externalRef": ("External Ref", False),
    "sectorId": ("Sector Id", True),
    "model": ("Model", False),
    "heightM": ("Height M", False),
    "azimuthDeg": ("Azimuth Deg", False),
    "tiltDeg": ("Tilt Deg", False),
    "gainDbi": ("Gain Dbi", False),
    "sourceSystem": ("Source System", False),
    "region": ("Region", False),
    "owner": ("Owner", False),
    "criticality": ("Criticality", False),
    "costCenter": ("Cost Center", False),
}


def test_entity_identity():
    assert META.entity == "Antenna"
    assert META.domain == "radio"
    assert META.path == "antennas"


def test_field_names_are_unique():
    assert len(field_names()) == len(set(field_names())) == 16


def test_required_fields_include_code_and_name():
    assert {"code", "name", "status"} <= set(required_fields())


@pytest.mark.parametrize("name,expected", sorted(EXPECTED.items()))
def test_field_label_and_requiredness(name, expected):
    field = BY_NAME[name]
    assert (field.label, field.required) == expected
