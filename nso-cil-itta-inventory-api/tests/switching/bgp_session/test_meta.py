"""Field catalogue tests for Bgp Session."""
import pytest

from itta_api.domains.switching.bgp_session.meta import FIELDS, META, field_names, required_fields

BY_NAME = {f.name: f for f in FIELDS}

EXPECTED = {
    "code": ("Code", True),
    "name": ("Name", True),
    "description": ("Description", False),
    "status": ("Status", True),
    "externalRef": ("External Ref", False),
    "localDeviceId": ("Local Device Id", True),
    "peerAddress": ("Peer Address", True),
    "localAsn": ("Local Asn", True),
    "remoteAsn": ("Remote Asn", True),
    "sessionState": ("Session State", False),
    "prefixLimit": ("Prefix Limit", False),
    "region": ("Region", False),
    "owner": ("Owner", False),
    "criticality": ("Criticality", False),
    "costCenter": ("Cost Center", False),
    "notes": ("Notes", False),
}


def test_entity_identity():
    assert META.entity == "BgpSession"
    assert META.domain == "switching"
    assert META.path == "bgp-sessions"


def test_field_names_are_unique():
    assert len(field_names()) == len(set(field_names())) == 16


def test_required_fields_include_code_and_name():
    assert {"code", "name", "status"} <= set(required_fields())


@pytest.mark.parametrize("name,expected", sorted(EXPECTED.items()))
def test_field_label_and_requiredness(name, expected):
    field = BY_NAME[name]
    assert (field.label, field.required) == expected
