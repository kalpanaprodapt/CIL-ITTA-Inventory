"""Tabular export of Circuit Segment records."""
from __future__ import annotations

import csv
import io
import json
from collections.abc import Iterable

from itta_core.csvutil import cell as _cell
from itta_api.domains.circuit.circuit_segment.schemas import CircuitSegmentRead

COLUMNS: list[tuple[str, str]] = [
    ("code", "Code"),
    ("name", "Name"),
    ("description", "Description"),
    ("status", "Status"),
    ("external_ref", "External Ref"),
    ("circuit_id", "Circuit Id"),
    ("segment_order", "Segment Order"),
    ("a_termination_id", "A Termination Id"),
    ("z_termination_id", "Z Termination Id"),
    ("latency_ms", "Latency Ms"),
    ("source_system", "Source System"),
    ("region", "Region"),
    ("owner", "Owner"),
    ("criticality", "Criticality"),
    ("cost_center", "Cost Center"),
]


def to_row(item: CircuitSegmentRead) -> dict[str, str]:
    return {
        "code": _cell(item.code),
        "name": _cell(item.name),
        "description": _cell(item.description),
        "status": _cell(item.status),
        "external_ref": _cell(item.external_ref),
        "circuit_id": _cell(item.circuit_id),
        "segment_order": _cell(item.segment_order),
        "a_termination_id": _cell(item.a_termination_id),
        "z_termination_id": _cell(item.z_termination_id),
        "latency_ms": _cell(item.latency_ms),
        "source_system": _cell(item.source_system),
        "region": _cell(item.region),
        "owner": _cell(item.owner),
        "criticality": _cell(item.criticality),
        "cost_center": _cell(item.cost_center),
    }


def to_csv(items: Iterable[CircuitSegmentRead]) -> str:
    buf = io.StringIO()
    writer = csv.DictWriter(buf, fieldnames=[c[0] for c in COLUMNS], lineterminator="\n")
    writer.writeheader()
    for item in items:
        writer.writerow(to_row(item))
    return buf.getvalue()


def to_json_lines(items: Iterable[CircuitSegmentRead]) -> str:
    return "\n".join(json.dumps(to_row(item), sort_keys=True) for item in items)


def header_labels() -> list[str]:
    return [c[1] for c in COLUMNS]
