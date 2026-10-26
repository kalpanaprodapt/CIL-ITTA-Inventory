"""Field catalogue tests for K8s Cluster."""
import pytest

from itta_api.domains.virtual.k8s_cluster.meta import FIELDS, META, field_names, required_fields

BY_NAME = {f.name: f for f in FIELDS}

EXPECTED = {
    "code": ("Code", True),
    "name": ("Name", True),
    "description": ("Description", False),
    "status": ("Status", True),
    "externalRef": ("External Ref", False),
    "clusterName": ("Cluster Name", True),
    "clusterVersion": ("Cluster Version", True),
    "nodeCount": ("Node Count", True),
    "cloudAccountId": ("Cloud Account Id", False),
    "region": ("Region", False),
    "apiEndpoint": ("Api Endpoint", False),
    "effectiveTo": ("Effective To", False),
    "labels": ("Labels", False),
    "lifecycleStage": ("Lifecycle Stage", False),
    "sourceSystem": ("Source System", False),
    "owner": ("Owner", False),
}


def test_entity_identity():
    assert META.entity == "K8sCluster"
    assert META.domain == "virtual"
    assert META.path == "k8s-clusters"


def test_field_names_are_unique():
    assert len(field_names()) == len(set(field_names())) == 16


def test_required_fields_include_code_and_name():
    assert {"code", "name", "status"} <= set(required_fields())


@pytest.mark.parametrize("name,expected", sorted(EXPECTED.items()))
def test_field_label_and_requiredness(name, expected):
    field = BY_NAME[name]
    assert (field.label, field.required) == expected
