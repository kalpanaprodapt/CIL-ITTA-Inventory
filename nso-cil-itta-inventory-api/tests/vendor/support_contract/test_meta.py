"""Field catalogue tests for Support Contract."""
import pytest

from itta_api.domains.vendor.support_contract.meta import FIELDS, META, field_names, required_fields

BY_NAME = {f.name: f for f in FIELDS}

EXPECTED = {
    "code": ("Code", True),
    "name": ("Name", True),
    "description": ("Description", False),
    "status": ("Status", True),
    "externalRef": ("External Ref", False),
    "vendorId": ("Vendor Id", True),
    "contractNumber": ("Contract Number", True),
    "startDate": ("Start Date", True),
    "endDate": ("End Date", True),
    "coverageLevel": ("Coverage Level", False),
    "annualCost": ("Annual Cost", False),
    "owner": ("Owner", False),
    "criticality": ("Criticality", False),
    "costCenter": ("Cost Center", False),
    "notes": ("Notes", False),
    "effectiveFrom": ("Effective From", False),
}


def test_entity_identity():
    assert META.entity == "SupportContract"
    assert META.domain == "vendor"
    assert META.path == "support-contracts"


def test_field_names_are_unique():
    assert len(field_names()) == len(set(field_names())) == 16


def test_required_fields_include_code_and_name():
    assert {"code", "name", "status"} <= set(required_fields())


@pytest.mark.parametrize("name,expected", sorted(EXPECTED.items()))
def test_field_label_and_requiredness(name, expected):
    field = BY_NAME[name]
    assert (field.label, field.required) == expected
