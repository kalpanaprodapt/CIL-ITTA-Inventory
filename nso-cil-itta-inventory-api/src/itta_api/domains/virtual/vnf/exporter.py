"""Tabular export of Vnf records."""
from __future__ import annotations

import csv
import io
import json
from collections.abc import Iterable

from itta_core.csvutil import cell as _cell
from itta_api.domains.virtual.vnf.schemas import VnfRead

COLUMNS: list[tuple[str, str]] = [
    ("code", "Code"),
    ("name", "Name"),
    ("description", "Description"),
    ("status", "Status"),
    ("external_ref", "External Ref"),
    ("vnf_name", "Vnf Name"),
    ("vnf_type", "Vnf Type"),
    ("flavor", "Flavor"),
    ("vcpu", "Vcpu"),
    ("memory_gb", "Memory Gb"),
    ("instance_count", "Instance Count"),
    ("lifecycle_stage", "Lifecycle Stage"),
    ("source_system", "Source System"),
    ("region", "Region"),
    ("owner", "Owner"),
    ("criticality", "Criticality"),
]


def to_row(item: VnfRead) -> dict[str, str]:
    return {
        "code": _cell(item.code),
        "name": _cell(item.name),
        "description": _cell(item.description),
        "status": _cell(item.status),
        "external_ref": _cell(item.external_ref),
        "vnf_name": _cell(item.vnf_name),
        "vnf_type": _cell(item.vnf_type),
        "flavor": _cell(item.flavor),
        "vcpu": _cell(item.vcpu),
        "memory_gb": _cell(item.memory_gb),
        "instance_count": _cell(item.instance_count),
        "lifecycle_stage": _cell(item.lifecycle_stage),
        "source_system": _cell(item.source_system),
        "region": _cell(item.region),
        "owner": _cell(item.owner),
        "criticality": _cell(item.criticality),
    }


def to_csv(items: Iterable[VnfRead]) -> str:
    buf = io.StringIO()
    writer = csv.DictWriter(buf, fieldnames=[c[0] for c in COLUMNS], lineterminator="\n")
    writer.writeheader()
    for item in items:
        writer.writerow(to_row(item))
    return buf.getvalue()


def to_json_lines(items: Iterable[VnfRead]) -> str:
    return "\n".join(json.dumps(to_row(item), sort_keys=True) for item in items)


def header_labels() -> list[str]:
    return [c[1] for c in COLUMNS]
