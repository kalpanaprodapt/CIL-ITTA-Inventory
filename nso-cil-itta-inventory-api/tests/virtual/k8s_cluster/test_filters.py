"""Filter translation tests for K8s Cluster."""
from datetime import date, datetime, timezone
from decimal import Decimal
from uuid import UUID

from itta_core.models import LifecycleStatus
from itta_api.domains.virtual.k8s_cluster.filters import K8sClusterFilter, to_query
from itta_api.domains.virtual.k8s_cluster.schemas import *  # noqa: F401,F403


def test_empty_filter_has_no_params():
    assert to_query(K8sClusterFilter()) == {}


def test_free_text_is_trimmed():
    assert to_query(K8sClusterFilter(q="  core ")) == {"q": "core"}


def test_status_list_is_comma_joined():
    query = to_query(K8sClusterFilter(status=[LifecycleStatus.ACTIVE, LifecycleStatus.PLANNED]))
    assert query["status"] == "ACTIVE,PLANNED"


def test_code_is_trimmed_into_query():
    assert to_query(K8sClusterFilter(code="  abc ")) == {"code": "abc"}


def test_code_blank_is_omitted():
    assert "code" not in to_query(K8sClusterFilter(code=""))


def test_name_is_trimmed_into_query():
    assert to_query(K8sClusterFilter(name="  abc ")) == {"name": "abc"}


def test_name_blank_is_omitted():
    assert "name" not in to_query(K8sClusterFilter(name=""))


def test_external_ref_is_trimmed_into_query():
    assert to_query(K8sClusterFilter(external_ref="  abc ")) == {"externalRef": "abc"}


def test_external_ref_blank_is_omitted():
    assert "externalRef" not in to_query(K8sClusterFilter(external_ref=""))


def test_cluster_name_is_trimmed_into_query():
    assert to_query(K8sClusterFilter(cluster_name="  abc ")) == {"clusterName": "abc"}


def test_cluster_name_blank_is_omitted():
    assert "clusterName" not in to_query(K8sClusterFilter(cluster_name=""))


def test_cluster_version_is_trimmed_into_query():
    assert to_query(K8sClusterFilter(cluster_version="  abc ")) == {"clusterVersion": "abc"}


def test_cluster_version_blank_is_omitted():
    assert "clusterVersion" not in to_query(K8sClusterFilter(cluster_version=""))


def test_node_count_range_is_sent():
    query = to_query(K8sClusterFilter(node_count_min=1, node_count_max=9))
    assert query["nodeCountMin"] == str(1)
    assert query["nodeCountMax"] == str(9)


def test_node_count_zero_is_not_dropped():
    assert to_query(K8sClusterFilter(node_count_min=0))["nodeCountMin"] == "0"


def test_cloud_account_id_is_stringified():
    value = UUID("00000000-0000-0000-0000-000000000011")
    assert to_query(K8sClusterFilter(cloud_account_id=value))["cloudAccountId"] == str(value)


def test_region_is_trimmed_into_query():
    assert to_query(K8sClusterFilter(region="  abc ")) == {"region": "abc"}


def test_region_blank_is_omitted():
    assert "region" not in to_query(K8sClusterFilter(region=""))


def test_api_endpoint_is_trimmed_into_query():
    assert to_query(K8sClusterFilter(api_endpoint="  abc ")) == {"apiEndpoint": "abc"}


def test_api_endpoint_blank_is_omitted():
    assert "apiEndpoint" not in to_query(K8sClusterFilter(api_endpoint=""))


def test_effective_to_window_is_iso_formatted():
    query = to_query(K8sClusterFilter(effective_to_from=date(2026, 1, 1), effective_to_to=date(2026, 1, 1)))
    assert query["effectiveToFrom"].startswith("2026-01-01")
    assert query["effectiveToTo"].startswith("2026-01-01")


def test_labels_is_trimmed_into_query():
    assert to_query(K8sClusterFilter(labels="  abc ")) == {"labels": "abc"}


def test_labels_blank_is_omitted():
    assert "labels" not in to_query(K8sClusterFilter(labels=""))


def test_lifecycle_stage_values_are_comma_joined():
    members = list(K8sClusterLifecycleStage)[:2]
    query = to_query(K8sClusterFilter(lifecycle_stage=members))
    assert query["lifecycleStage"] == ",".join(m.value for m in members)


def test_source_system_is_trimmed_into_query():
    assert to_query(K8sClusterFilter(source_system="  abc ")) == {"sourceSystem": "abc"}


def test_source_system_blank_is_omitted():
    assert "sourceSystem" not in to_query(K8sClusterFilter(source_system=""))


def test_owner_is_trimmed_into_query():
    assert to_query(K8sClusterFilter(owner="  abc ")) == {"owner": "abc"}


def test_owner_blank_is_omitted():
    assert "owner" not in to_query(K8sClusterFilter(owner=""))

