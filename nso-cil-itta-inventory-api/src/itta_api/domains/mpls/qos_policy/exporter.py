"""Tabular export of Qos Policy records."""
from __future__ import annotations

import csv
import io
import json
from collections.abc import Iterable

from itta_core.csvutil import cell as _cell
from itta_api.domains.mpls.qos_policy.schemas import QosPolicyRead

COLUMNS: list[tuple[str, str]] = [
    ("code", "Code"),
    ("name", "Name"),
    ("description", "Description"),
    ("status", "Status"),
    ("external_ref", "External Ref"),
    ("policy_name", "Policy Name"),
    ("class_count", "Class Count"),
    ("cir_mbps", "Cir Mbps"),
    ("pir_mbps", "Pir Mbps"),
    ("marking_scheme", "Marking Scheme"),
    ("labels", "Labels"),
    ("lifecycle_stage", "Lifecycle Stage"),
    ("source_system", "Source System"),
    ("region", "Region"),
    ("owner", "Owner"),
]


def to_row(item: QosPolicyRead) -> dict[str, str]:
    return {
        "code": _cell(item.code),
        "name": _cell(item.name),
        "description": _cell(item.description),
        "status": _cell(item.status),
        "external_ref": _cell(item.external_ref),
        "policy_name": _cell(item.policy_name),
        "class_count": _cell(item.class_count),
        "cir_mbps": _cell(item.cir_mbps),
        "pir_mbps": _cell(item.pir_mbps),
        "marking_scheme": _cell(item.marking_scheme),
        "labels": _cell(item.labels),
        "lifecycle_stage": _cell(item.lifecycle_stage),
        "source_system": _cell(item.source_system),
        "region": _cell(item.region),
        "owner": _cell(item.owner),
    }


def to_csv(items: Iterable[QosPolicyRead]) -> str:
    buf = io.StringIO()
    writer = csv.DictWriter(buf, fieldnames=[c[0] for c in COLUMNS], lineterminator="\n")
    writer.writeheader()
    for item in items:
        writer.writerow(to_row(item))
    return buf.getvalue()


def to_json_lines(items: Iterable[QosPolicyRead]) -> str:
    return "\n".join(json.dumps(to_row(item), sort_keys=True) for item in items)


def header_labels() -> list[str]:
    return [c[1] for c in COLUMNS]
