"""Field catalogue tests for Alarm Instance."""
import pytest

from itta_api.domains.alarm.alarm_instance.meta import FIELDS, META, field_names, required_fields

BY_NAME = {f.name: f for f in FIELDS}

EXPECTED = {
    "code": ("Code", True),
    "name": ("Name", True),
    "description": ("Description", False),
    "status": ("Status", True),
    "externalRef": ("External Ref", False),
    "definitionId": ("Definition Id", True),
    "sourceRef": ("Source Ref", True),
    "raisedAt": ("Raised At", True),
    "clearedAt": ("Cleared At", False),
    "ackedBy": ("Acked By", False),
    "alarmState": ("Alarm State", False),
    "effectiveFrom": ("Effective From", False),
    "effectiveTo": ("Effective To", False),
    "labels": ("Labels", False),
    "lifecycleStage": ("Lifecycle Stage", False),
    "sourceSystem": ("Source System", False),
}


def test_entity_identity():
    assert META.entity == "AlarmInstance"
    assert META.domain == "alarm"
    assert META.path == "alarm-instances"


def test_field_names_are_unique():
    assert len(field_names()) == len(set(field_names())) == 16


def test_required_fields_include_code_and_name():
    assert {"code", "name", "status"} <= set(required_fields())


@pytest.mark.parametrize("name,expected", sorted(EXPECTED.items()))
def test_field_label_and_requiredness(name, expected):
    field = BY_NAME[name]
    assert (field.label, field.required) == expected
