"""Field catalogue tests for Mpls Lsp."""
import pytest

from itta_api.domains.mpls.mpls_lsp.meta import FIELDS, META, field_names, required_fields

BY_NAME = {f.name: f for f in FIELDS}

EXPECTED = {
    "code": ("Code", True),
    "name": ("Name", True),
    "description": ("Description", False),
    "status": ("Status", True),
    "externalRef": ("External Ref", False),
    "lspName": ("Lsp Name", True),
    "headEndId": ("Head End Id", True),
    "tailEndId": ("Tail End Id", True),
    "bandwidthMbps": ("Bandwidth Mbps", False),
    "setupPriority": ("Setup Priority", False),
    "pathType": ("Path Type", False),
    "sourceSystem": ("Source System", False),
    "region": ("Region", False),
    "owner": ("Owner", False),
    "criticality": ("Criticality", False),
    "costCenter": ("Cost Center", False),
}


def test_entity_identity():
    assert META.entity == "MplsLsp"
    assert META.domain == "mpls"
    assert META.path == "mpls-lsps"


def test_field_names_are_unique():
    assert len(field_names()) == len(set(field_names())) == 16


def test_required_fields_include_code_and_name():
    assert {"code", "name", "status"} <= set(required_fields())


@pytest.mark.parametrize("name,expected", sorted(EXPECTED.items()))
def test_field_label_and_requiredness(name, expected):
    field = BY_NAME[name]
    assert (field.label, field.required) == expected
