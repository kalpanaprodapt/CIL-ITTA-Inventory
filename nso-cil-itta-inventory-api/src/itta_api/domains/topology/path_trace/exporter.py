"""Tabular export of Path Trace records."""
from __future__ import annotations

import csv
import io
import json
from collections.abc import Iterable

from itta_core.csvutil import cell as _cell
from itta_api.domains.topology.path_trace.schemas import PathTraceRead

COLUMNS: list[tuple[str, str]] = [
    ("code", "Code"),
    ("name", "Name"),
    ("description", "Description"),
    ("status", "Status"),
    ("external_ref", "External Ref"),
    ("source_ref", "Source Ref"),
    ("target_ref", "Target Ref"),
    ("hop_count", "Hop Count"),
    ("total_latency_ms", "Total Latency Ms"),
    ("trace_method", "Trace Method"),
    ("traced_at", "Traced At"),
    ("lifecycle_stage", "Lifecycle Stage"),
    ("source_system", "Source System"),
    ("region", "Region"),
    ("owner", "Owner"),
    ("criticality", "Criticality"),
]


def to_row(item: PathTraceRead) -> dict[str, str]:
    return {
        "code": _cell(item.code),
        "name": _cell(item.name),
        "description": _cell(item.description),
        "status": _cell(item.status),
        "external_ref": _cell(item.external_ref),
        "source_ref": _cell(item.source_ref),
        "target_ref": _cell(item.target_ref),
        "hop_count": _cell(item.hop_count),
        "total_latency_ms": _cell(item.total_latency_ms),
        "trace_method": _cell(item.trace_method),
        "traced_at": _cell(item.traced_at),
        "lifecycle_stage": _cell(item.lifecycle_stage),
        "source_system": _cell(item.source_system),
        "region": _cell(item.region),
        "owner": _cell(item.owner),
        "criticality": _cell(item.criticality),
    }


def to_csv(items: Iterable[PathTraceRead]) -> str:
    buf = io.StringIO()
    writer = csv.DictWriter(buf, fieldnames=[c[0] for c in COLUMNS], lineterminator="\n")
    writer.writeheader()
    for item in items:
        writer.writerow(to_row(item))
    return buf.getvalue()


def to_json_lines(items: Iterable[PathTraceRead]) -> str:
    return "\n".join(json.dumps(to_row(item), sort_keys=True) for item in items)


def header_labels() -> list[str]:
    return [c[1] for c in COLUMNS]
