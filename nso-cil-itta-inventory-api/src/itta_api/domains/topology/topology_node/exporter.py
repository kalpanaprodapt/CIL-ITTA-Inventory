"""Tabular export of Topology Node records."""
from __future__ import annotations

import csv
import io
import json
from collections.abc import Iterable

from itta_core.csvutil import cell as _cell
from itta_api.domains.topology.topology_node.schemas import TopologyNodeRead

COLUMNS: list[tuple[str, str]] = [
    ("code", "Code"),
    ("name", "Name"),
    ("description", "Description"),
    ("status", "Status"),
    ("external_ref", "External Ref"),
    ("view_id", "View Id"),
    ("entity_ref", "Entity Ref"),
    ("node_type", "Node Type"),
    ("x_pos", "X Pos"),
    ("y_pos", "Y Pos"),
    ("layer", "Layer"),
    ("criticality", "Criticality"),
    ("cost_center", "Cost Center"),
    ("notes", "Notes"),
    ("effective_from", "Effective From"),
    ("effective_to", "Effective To"),
]


def to_row(item: TopologyNodeRead) -> dict[str, str]:
    return {
        "code": _cell(item.code),
        "name": _cell(item.name),
        "description": _cell(item.description),
        "status": _cell(item.status),
        "external_ref": _cell(item.external_ref),
        "view_id": _cell(item.view_id),
        "entity_ref": _cell(item.entity_ref),
        "node_type": _cell(item.node_type),
        "x_pos": _cell(item.x_pos),
        "y_pos": _cell(item.y_pos),
        "layer": _cell(item.layer),
        "criticality": _cell(item.criticality),
        "cost_center": _cell(item.cost_center),
        "notes": _cell(item.notes),
        "effective_from": _cell(item.effective_from),
        "effective_to": _cell(item.effective_to),
    }


def to_csv(items: Iterable[TopologyNodeRead]) -> str:
    buf = io.StringIO()
    writer = csv.DictWriter(buf, fieldnames=[c[0] for c in COLUMNS], lineterminator="\n")
    writer.writeheader()
    for item in items:
        writer.writerow(to_row(item))
    return buf.getvalue()


def to_json_lines(items: Iterable[TopologyNodeRead]) -> str:
    return "\n".join(json.dumps(to_row(item), sort_keys=True) for item in items)


def header_labels() -> list[str]:
    return [c[1] for c in COLUMNS]
