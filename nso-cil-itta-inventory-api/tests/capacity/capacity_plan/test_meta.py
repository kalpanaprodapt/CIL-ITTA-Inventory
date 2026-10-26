"""Field catalogue tests for Capacity Plan."""
import pytest

from itta_api.domains.capacity.capacity_plan.meta import FIELDS, META, field_names, required_fields

BY_NAME = {f.name: f for f in FIELDS}

EXPECTED = {
    "code": ("Code", True),
    "name": ("Name", True),
    "description": ("Description", False),
    "status": ("Status", True),
    "externalRef": ("External Ref", False),
    "planName": ("Plan Name", True),
    "horizonMonths": ("Horizon Months", True),
    "targetUtilizationPct": ("Target Utilization Pct", False),
    "owner": ("Owner", False),
    "approvedOn": ("Approved On", False),
    "effectiveTo": ("Effective To", False),
    "labels": ("Labels", False),
    "lifecycleStage": ("Lifecycle Stage", False),
    "sourceSystem": ("Source System", False),
    "region": ("Region", False),
}


def test_entity_identity():
    assert META.entity == "CapacityPlan"
    assert META.domain == "capacity"
    assert META.path == "capacity-plans"


def test_field_names_are_unique():
    assert len(field_names()) == len(set(field_names())) == 15


def test_required_fields_include_code_and_name():
    assert {"code", "name", "status"} <= set(required_fields())


@pytest.mark.parametrize("name,expected", sorted(EXPECTED.items()))
def test_field_label_and_requiredness(name, expected):
    field = BY_NAME[name]
    assert (field.label, field.required) == expected
