"""Field catalogue tests for Purchase Order."""
import pytest

from itta_api.domains.assetmgmt.purchase_order.meta import FIELDS, META, field_names, required_fields

BY_NAME = {f.name: f for f in FIELDS}

EXPECTED = {
    "code": ("Code", True),
    "name": ("Name", True),
    "description": ("Description", False),
    "status": ("Status", True),
    "externalRef": ("External Ref", False),
    "poNumber": ("Po Number", True),
    "vendorId": ("Vendor Id", True),
    "orderedOn": ("Ordered On", True),
    "totalAmount": ("Total Amount", True),
    "currency": ("Currency", False),
    "owner": ("Owner", False),
    "criticality": ("Criticality", False),
    "costCenter": ("Cost Center", False),
    "notes": ("Notes", False),
    "effectiveFrom": ("Effective From", False),
}


def test_entity_identity():
    assert META.entity == "PurchaseOrder"
    assert META.domain == "assetmgmt"
    assert META.path == "purchase-orders"


def test_field_names_are_unique():
    assert len(field_names()) == len(set(field_names())) == 15


def test_required_fields_include_code_and_name():
    assert {"code", "name", "status"} <= set(required_fields())


@pytest.mark.parametrize("name,expected", sorted(EXPECTED.items()))
def test_field_label_and_requiredness(name, expected):
    field = BY_NAME[name]
    assert (field.label, field.required) == expected
