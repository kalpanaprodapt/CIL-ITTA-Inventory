"""Field catalogue tests for Forecast Model."""
import pytest

from itta_api.domains.capacity.forecast_model.meta import FIELDS, META, field_names, required_fields

BY_NAME = {f.name: f for f in FIELDS}

EXPECTED = {
    "code": ("Code", True),
    "name": ("Name", True),
    "description": ("Description", False),
    "status": ("Status", True),
    "externalRef": ("External Ref", False),
    "modelName": ("Model Name", True),
    "algorithm": ("Algorithm", False),
    "trainedOn": ("Trained On", False),
    "mape": ("Mape", False),
    "criticality": ("Criticality", False),
    "costCenter": ("Cost Center", False),
    "notes": ("Notes", False),
    "effectiveFrom": ("Effective From", False),
    "effectiveTo": ("Effective To", False),
}


def test_entity_identity():
    assert META.entity == "ForecastModel"
    assert META.domain == "capacity"
    assert META.path == "forecast-models"


def test_field_names_are_unique():
    assert len(field_names()) == len(set(field_names())) == 14


def test_required_fields_include_code_and_name():
    assert {"code", "name", "status"} <= set(required_fields())


@pytest.mark.parametrize("name,expected", sorted(EXPECTED.items()))
def test_field_label_and_requiredness(name, expected):
    field = BY_NAME[name]
    assert (field.label, field.required) == expected
