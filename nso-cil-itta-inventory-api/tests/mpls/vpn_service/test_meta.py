"""Field catalogue tests for Vpn Service."""
import pytest

from itta_api.domains.mpls.vpn_service.meta import FIELDS, META, field_names, required_fields

BY_NAME = {f.name: f for f in FIELDS}

EXPECTED = {
    "code": ("Code", True),
    "name": ("Name", True),
    "description": ("Description", False),
    "status": ("Status", True),
    "externalRef": ("External Ref", False),
    "vpnName": ("Vpn Name", True),
    "vpnType": ("Vpn Type", False),
    "vrfId": ("Vrf Id", False),
    "customerId": ("Customer Id", False),
    "topology": ("Topology", False),
    "owner": ("Owner", False),
    "criticality": ("Criticality", False),
    "costCenter": ("Cost Center", False),
    "notes": ("Notes", False),
    "effectiveFrom": ("Effective From", False),
}


def test_entity_identity():
    assert META.entity == "VpnService"
    assert META.domain == "mpls"
    assert META.path == "vpn-services"


def test_field_names_are_unique():
    assert len(field_names()) == len(set(field_names())) == 15


def test_required_fields_include_code_and_name():
    assert {"code", "name", "status"} <= set(required_fields())


@pytest.mark.parametrize("name,expected", sorted(EXPECTED.items()))
def test_field_label_and_requiredness(name, expected):
    field = BY_NAME[name]
    assert (field.label, field.required) == expected
