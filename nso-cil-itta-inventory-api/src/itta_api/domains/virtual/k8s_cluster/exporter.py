"""Tabular export of K8s Cluster records."""
from __future__ import annotations

import csv
import io
import json
from collections.abc import Iterable

from itta_core.csvutil import cell as _cell
from itta_api.domains.virtual.k8s_cluster.schemas import K8sClusterRead

COLUMNS: list[tuple[str, str]] = [
    ("code", "Code"),
    ("name", "Name"),
    ("description", "Description"),
    ("status", "Status"),
    ("external_ref", "External Ref"),
    ("cluster_name", "Cluster Name"),
    ("cluster_version", "Cluster Version"),
    ("node_count", "Node Count"),
    ("cloud_account_id", "Cloud Account Id"),
    ("region", "Region"),
    ("api_endpoint", "Api Endpoint"),
    ("effective_to", "Effective To"),
    ("labels", "Labels"),
    ("lifecycle_stage", "Lifecycle Stage"),
    ("source_system", "Source System"),
    ("owner", "Owner"),
]


def to_row(item: K8sClusterRead) -> dict[str, str]:
    return {
        "code": _cell(item.code),
        "name": _cell(item.name),
        "description": _cell(item.description),
        "status": _cell(item.status),
        "external_ref": _cell(item.external_ref),
        "cluster_name": _cell(item.cluster_name),
        "cluster_version": _cell(item.cluster_version),
        "node_count": _cell(item.node_count),
        "cloud_account_id": _cell(item.cloud_account_id),
        "region": _cell(item.region),
        "api_endpoint": _cell(item.api_endpoint),
        "effective_to": _cell(item.effective_to),
        "labels": _cell(item.labels),
        "lifecycle_stage": _cell(item.lifecycle_stage),
        "source_system": _cell(item.source_system),
        "owner": _cell(item.owner),
    }


def to_csv(items: Iterable[K8sClusterRead]) -> str:
    buf = io.StringIO()
    writer = csv.DictWriter(buf, fieldnames=[c[0] for c in COLUMNS], lineterminator="\n")
    writer.writeheader()
    for item in items:
        writer.writerow(to_row(item))
    return buf.getvalue()


def to_json_lines(items: Iterable[K8sClusterRead]) -> str:
    return "\n".join(json.dumps(to_row(item), sort_keys=True) for item in items)


def header_labels() -> list[str]:
    return [c[1] for c in COLUMNS]
