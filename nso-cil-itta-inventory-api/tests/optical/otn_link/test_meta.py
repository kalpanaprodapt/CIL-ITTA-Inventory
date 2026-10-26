"""Field catalogue tests for Otn Link."""
import pytest

from itta_api.domains.optical.otn_link.meta import FIELDS, META, field_names, required_fields

BY_NAME = {f.name: f for f in FIELDS}

EXPECTED = {
    "code": ("Code", True),
    "name": ("Name", True),
    "description": ("Description", False),
    "status": ("Status", True),
    "externalRef": ("External Ref", False),
    "aNodeId": ("A Node Id", True),
    "zNodeId": ("Z Node Id", True),
    "odu": ("Odu", False),
    "latencyUs": ("Latency Us", False),
    "protection": ("Protection", False),
    "effectiveTo": ("Effective To", False),
    "labels": ("Labels", False),
    "lifecycleStage": ("Lifecycle Stage", False),
    "sourceSystem": ("Source System", False),
    "region": ("Region", False),
}


def test_entity_identity():
    assert META.entity == "OtnLink"
    assert META.domain == "optical"
    assert META.path == "otn-links"


def test_field_names_are_unique():
    assert len(field_names()) == len(set(field_names())) == 15


def test_required_fields_include_code_and_name():
    assert {"code", "name", "status"} <= set(required_fields())


@pytest.mark.parametrize("name,expected", sorted(EXPECTED.items()))
def test_field_label_and_requiredness(name, expected):
    field = BY_NAME[name]
    assert (field.label, field.required) == expected
