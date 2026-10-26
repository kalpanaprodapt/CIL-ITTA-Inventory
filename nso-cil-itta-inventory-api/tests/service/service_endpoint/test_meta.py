"""Field catalogue tests for Service Endpoint."""
import pytest

from itta_api.domains.service.service_endpoint.meta import FIELDS, META, field_names, required_fields

BY_NAME = {f.name: f for f in FIELDS}

EXPECTED = {
    "code": ("Code", True),
    "name": ("Name", True),
    "description": ("Description", False),
    "status": ("Status", True),
    "externalRef": ("External Ref", False),
    "serviceId": ("Service Id", True),
    "siteId": ("Site Id", False),
    "portId": ("Port Id", False),
    "endpointRole": ("Endpoint Role", False),
    "handoff": ("Handoff", False),
    "notes": ("Notes", False),
    "effectiveFrom": ("Effective From", False),
    "effectiveTo": ("Effective To", False),
    "labels": ("Labels", False),
    "lifecycleStage": ("Lifecycle Stage", False),
}


def test_entity_identity():
    assert META.entity == "ServiceEndpoint"
    assert META.domain == "service"
    assert META.path == "service-endpoints"


def test_field_names_are_unique():
    assert len(field_names()) == len(set(field_names())) == 15


def test_required_fields_include_code_and_name():
    assert {"code", "name", "status"} <= set(required_fields())


@pytest.mark.parametrize("name,expected", sorted(EXPECTED.items()))
def test_field_label_and_requiredness(name, expected):
    field = BY_NAME[name]
    assert (field.label, field.required) == expected
