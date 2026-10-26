"""Field catalogue tests for Policy Document."""
import pytest

from itta_api.domains.compliance.policy_document.meta import FIELDS, META, field_names, required_fields

BY_NAME = {f.name: f for f in FIELDS}

EXPECTED = {
    "code": ("Code", True),
    "name": ("Name", True),
    "description": ("Description", False),
    "status": ("Status", True),
    "externalRef": ("External Ref", False),
    "title": ("Title", True),
    "docVersion": ("Doc Version", True),
    "effectiveDate": ("Effective Date", True),
    "owner": ("Owner", False),
    "documentUrl": ("Document Url", False),
    "lifecycleStage": ("Lifecycle Stage", False),
    "sourceSystem": ("Source System", False),
    "region": ("Region", False),
    "criticality": ("Criticality", False),
    "costCenter": ("Cost Center", False),
}


def test_entity_identity():
    assert META.entity == "PolicyDocument"
    assert META.domain == "compliance"
    assert META.path == "policy-documents"


def test_field_names_are_unique():
    assert len(field_names()) == len(set(field_names())) == 15


def test_required_fields_include_code_and_name():
    assert {"code", "name", "status"} <= set(required_fields())


@pytest.mark.parametrize("name,expected", sorted(EXPECTED.items()))
def test_field_label_and_requiredness(name, expected):
    field = BY_NAME[name]
    assert (field.label, field.required) == expected
