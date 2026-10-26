"""Field catalogue tests for Amplifier."""
import pytest

from itta_api.domains.optical.amplifier.meta import FIELDS, META, field_names, required_fields

BY_NAME = {f.name: f for f in FIELDS}

EXPECTED = {
    "code": ("Code", True),
    "name": ("Name", True),
    "description": ("Description", False),
    "status": ("Status", True),
    "externalRef": ("External Ref", False),
    "systemId": ("System Id", True),
    "ampType": ("Amp Type", False),
    "gainDb": ("Gain Db", True),
    "outputPowerDbm": ("Output Power Dbm", False),
    "noiseFigureDb": ("Noise Figure Db", False),
    "lifecycleStage": ("Lifecycle Stage", False),
    "sourceSystem": ("Source System", False),
    "region": ("Region", False),
    "owner": ("Owner", False),
    "criticality": ("Criticality", False),
}


def test_entity_identity():
    assert META.entity == "Amplifier"
    assert META.domain == "optical"
    assert META.path == "amplifiers"


def test_field_names_are_unique():
    assert len(field_names()) == len(set(field_names())) == 15


def test_required_fields_include_code_and_name():
    assert {"code", "name", "status"} <= set(required_fields())


@pytest.mark.parametrize("name,expected", sorted(EXPECTED.items()))
def test_field_label_and_requiredness(name, expected):
    field = BY_NAME[name]
    assert (field.label, field.required) == expected
