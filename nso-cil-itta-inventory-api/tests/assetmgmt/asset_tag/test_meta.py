"""Field catalogue tests for Asset Tag."""
import pytest

from itta_api.domains.assetmgmt.asset_tag.meta import FIELDS, META, field_names, required_fields

BY_NAME = {f.name: f for f in FIELDS}

EXPECTED = {
    "code": ("Code", True),
    "name": ("Name", True),
    "description": ("Description", False),
    "status": ("Status", True),
    "externalRef": ("External Ref", False),
    "assetId": ("Asset Id", True),
    "tagValue": ("Tag Value", True),
    "tagType": ("Tag Type", False),
    "printedOn": ("Printed On", False),
    "sourceSystem": ("Source System", False),
    "region": ("Region", False),
    "owner": ("Owner", False),
    "criticality": ("Criticality", False),
    "costCenter": ("Cost Center", False),
}


def test_entity_identity():
    assert META.entity == "AssetTag"
    assert META.domain == "assetmgmt"
    assert META.path == "asset-tags"


def test_field_names_are_unique():
    assert len(field_names()) == len(set(field_names())) == 14


def test_required_fields_include_code_and_name():
    assert {"code", "name", "status"} <= set(required_fields())


@pytest.mark.parametrize("name,expected", sorted(EXPECTED.items()))
def test_field_label_and_requiredness(name, expected):
    field = BY_NAME[name]
    assert (field.label, field.required) == expected
