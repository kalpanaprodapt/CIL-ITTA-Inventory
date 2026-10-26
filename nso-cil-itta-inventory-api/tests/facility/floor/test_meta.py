"""Field catalogue tests for Floor."""
import pytest

from itta_api.domains.facility.floor.meta import FIELDS, META, field_names, required_fields

BY_NAME = {f.name: f for f in FIELDS}

EXPECTED = {
    "code": ("Code", True),
    "name": ("Name", True),
    "description": ("Description", False),
    "status": ("Status", True),
    "externalRef": ("External Ref", False),
    "buildingId": ("Building Id", True),
    "level": ("Level", False),
    "areaSqm": ("Area Sqm", False),
    "loadCapacityKg": ("Load Capacity Kg", False),
    "hasRaisedFloor": ("Has Raised Floor", False),
    "costCenter": ("Cost Center", False),
    "notes": ("Notes", False),
    "effectiveFrom": ("Effective From", False),
    "effectiveTo": ("Effective To", False),
    "labels": ("Labels", False),
}


def test_entity_identity():
    assert META.entity == "Floor"
    assert META.domain == "facility"
    assert META.path == "floors"


def test_field_names_are_unique():
    assert len(field_names()) == len(set(field_names())) == 15


def test_required_fields_include_code_and_name():
    assert {"code", "name", "status"} <= set(required_fields())


@pytest.mark.parametrize("name,expected", sorted(EXPECTED.items()))
def test_field_label_and_requiredness(name, expected):
    field = BY_NAME[name]
    assert (field.label, field.required) == expected
