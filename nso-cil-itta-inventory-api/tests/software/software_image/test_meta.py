"""Field catalogue tests for Software Image."""
import pytest

from itta_api.domains.software.software_image.meta import FIELDS, META, field_names, required_fields

BY_NAME = {f.name: f for f in FIELDS}

EXPECTED = {
    "code": ("Code", True),
    "name": ("Name", True),
    "description": ("Description", False),
    "status": ("Status", True),
    "externalRef": ("External Ref", False),
    "vendorId": ("Vendor Id", True),
    "imageName": ("Image Name", True),
    "sizeMb": ("Size Mb", False),
    "checksum": ("Checksum", False),
    "platform": ("Platform", False),
    "storagePath": ("Storage Path", False),
    "lifecycleStage": ("Lifecycle Stage", False),
    "sourceSystem": ("Source System", False),
    "region": ("Region", False),
    "owner": ("Owner", False),
    "criticality": ("Criticality", False),
}


def test_entity_identity():
    assert META.entity == "SoftwareImage"
    assert META.domain == "software"
    assert META.path == "software-images"


def test_field_names_are_unique():
    assert len(field_names()) == len(set(field_names())) == 16


def test_required_fields_include_code_and_name():
    assert {"code", "name", "status"} <= set(required_fields())


@pytest.mark.parametrize("name,expected", sorted(EXPECTED.items()))
def test_field_label_and_requiredness(name, expected):
    field = BY_NAME[name]
    assert (field.label, field.required) == expected
