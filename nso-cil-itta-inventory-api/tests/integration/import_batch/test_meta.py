"""Field catalogue tests for Import Batch."""
import pytest

from itta_api.domains.integration.import_batch.meta import FIELDS, META, field_names, required_fields

BY_NAME = {f.name: f for f in FIELDS}

EXPECTED = {
    "code": ("Code", True),
    "name": ("Name", True),
    "description": ("Description", False),
    "status": ("Status", True),
    "externalRef": ("External Ref", False),
    "fileName": ("File Name", True),
    "entityType": ("Entity Type", True),
    "totalRows": ("Total Rows", False),
    "processedRows": ("Processed Rows", False),
    "failedRows": ("Failed Rows", False),
    "batchState": ("Batch State", False),
    "effectiveFrom": ("Effective From", False),
    "effectiveTo": ("Effective To", False),
    "labels": ("Labels", False),
    "lifecycleStage": ("Lifecycle Stage", False),
    "sourceSystem": ("Source System", False),
}


def test_entity_identity():
    assert META.entity == "ImportBatch"
    assert META.domain == "integration"
    assert META.path == "import-batchs"


def test_field_names_are_unique():
    assert len(field_names()) == len(set(field_names())) == 16


def test_required_fields_include_code_and_name():
    assert {"code", "name", "status"} <= set(required_fields())


@pytest.mark.parametrize("name,expected", sorted(EXPECTED.items()))
def test_field_label_and_requiredness(name, expected):
    field = BY_NAME[name]
    assert (field.label, field.required) == expected
