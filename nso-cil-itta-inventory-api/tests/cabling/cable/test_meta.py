"""Field catalogue tests for Cable."""
import pytest

from itta_api.domains.cabling.cable.meta import FIELDS, META, field_names, required_fields

BY_NAME = {f.name: f for f in FIELDS}

EXPECTED = {
    "code": ("Code", True),
    "name": ("Name", True),
    "description": ("Description", False),
    "status": ("Status", True),
    "externalRef": ("External Ref", False),
    "cableType": ("Cable Type", False),
    "lengthM": ("Length M", False),
    "aEndPortId": ("A End Port Id", False),
    "zEndPortId": ("Z End Port Id", False),
    "color": ("Color", False),
    "label": ("Label", False),
    "criticality": ("Criticality", False),
    "costCenter": ("Cost Center", False),
    "notes": ("Notes", False),
    "effectiveFrom": ("Effective From", False),
    "effectiveTo": ("Effective To", False),
}


def test_entity_identity():
    assert META.entity == "Cable"
    assert META.domain == "cabling"
    assert META.path == "cables"


def test_field_names_are_unique():
    assert len(field_names()) == len(set(field_names())) == 16


def test_required_fields_include_code_and_name():
    assert {"code", "name", "status"} <= set(required_fields())


@pytest.mark.parametrize("name,expected", sorted(EXPECTED.items()))
def test_field_label_and_requiredness(name, expected):
    field = BY_NAME[name]
    assert (field.label, field.required) == expected
