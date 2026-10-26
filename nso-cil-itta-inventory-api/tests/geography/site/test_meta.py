"""Field catalogue tests for Site."""
import pytest

from itta_api.domains.geography.site.meta import FIELDS, META, field_names, required_fields

BY_NAME = {f.name: f for f in FIELDS}

EXPECTED = {
    "code": ("Code", True),
    "name": ("Name", True),
    "description": ("Description", False),
    "status": ("Status", True),
    "externalRef": ("External Ref", False),
    "cityId": ("City Id", True),
    "siteType": ("Site Type", False),
    "address": ("Address", False),
    "latitude": ("Latitude", False),
    "longitude": ("Longitude", False),
    "powerKw": ("Power Kw", False),
    "accessHours": ("Access Hours", False),
    "effectiveTo": ("Effective To", False),
    "labels": ("Labels", False),
    "lifecycleStage": ("Lifecycle Stage", False),
    "sourceSystem": ("Source System", False),
    "region": ("Region", False),
}


def test_entity_identity():
    assert META.entity == "Site"
    assert META.domain == "geography"
    assert META.path == "sites"


def test_field_names_are_unique():
    assert len(field_names()) == len(set(field_names())) == 17


def test_required_fields_include_code_and_name():
    assert {"code", "name", "status"} <= set(required_fields())


@pytest.mark.parametrize("name,expected", sorted(EXPECTED.items()))
def test_field_label_and_requiredness(name, expected):
    field = BY_NAME[name]
    assert (field.label, field.required) == expected
