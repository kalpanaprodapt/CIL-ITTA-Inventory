"""Field catalogue tests for Field Task."""
import pytest

from itta_api.domains.change.field_task.meta import FIELDS, META, field_names, required_fields

BY_NAME = {f.name: f for f in FIELDS}

EXPECTED = {
    "code": ("Code", True),
    "name": ("Name", True),
    "description": ("Description", False),
    "status": ("Status", True),
    "externalRef": ("External Ref", False),
    "workOrderId": ("Work Order Id", True),
    "taskName": ("Task Name", True),
    "sequence": ("Sequence", True),
    "durationMinutes": ("Duration Minutes", False),
    "completedOn": ("Completed On", False),
    "region": ("Region", False),
    "owner": ("Owner", False),
    "criticality": ("Criticality", False),
    "costCenter": ("Cost Center", False),
    "notes": ("Notes", False),
}


def test_entity_identity():
    assert META.entity == "FieldTask"
    assert META.domain == "change"
    assert META.path == "field-tasks"


def test_field_names_are_unique():
    assert len(field_names()) == len(set(field_names())) == 15


def test_required_fields_include_code_and_name():
    assert {"code", "name", "status"} <= set(required_fields())


@pytest.mark.parametrize("name,expected", sorted(EXPECTED.items()))
def test_field_label_and_requiredness(name, expected):
    field = BY_NAME[name]
    assert (field.label, field.required) == expected
