"""Tabular export of Rack records."""
from __future__ import annotations

import csv
import io
import json
from collections.abc import Iterable

from itta_core.csvutil import cell as _cell
from itta_api.domains.facility.rack.schemas import RackRead

COLUMNS: list[tuple[str, str]] = [
    ("code", "Code"),
    ("name", "Name"),
    ("description", "Description"),
    ("status", "Status"),
    ("external_ref", "External Ref"),
    ("room_id", "Room Id"),
    ("rack_units", "Rack Units"),
    ("width_mm", "Width Mm"),
    ("depth_mm", "Depth Mm"),
    ("max_weight_kg", "Max Weight Kg"),
    ("row_label", "Row Label"),
    ("position", "Position"),
    ("labels", "Labels"),
    ("lifecycle_stage", "Lifecycle Stage"),
    ("source_system", "Source System"),
    ("region", "Region"),
    ("owner", "Owner"),
]


def to_row(item: RackRead) -> dict[str, str]:
    return {
        "code": _cell(item.code),
        "name": _cell(item.name),
        "description": _cell(item.description),
        "status": _cell(item.status),
        "external_ref": _cell(item.external_ref),
        "room_id": _cell(item.room_id),
        "rack_units": _cell(item.rack_units),
        "width_mm": _cell(item.width_mm),
        "depth_mm": _cell(item.depth_mm),
        "max_weight_kg": _cell(item.max_weight_kg),
        "row_label": _cell(item.row_label),
        "position": _cell(item.position),
        "labels": _cell(item.labels),
        "lifecycle_stage": _cell(item.lifecycle_stage),
        "source_system": _cell(item.source_system),
        "region": _cell(item.region),
        "owner": _cell(item.owner),
    }


def to_csv(items: Iterable[RackRead]) -> str:
    buf = io.StringIO()
    writer = csv.DictWriter(buf, fieldnames=[c[0] for c in COLUMNS], lineterminator="\n")
    writer.writeheader()
    for item in items:
        writer.writerow(to_row(item))
    return buf.getvalue()


def to_json_lines(items: Iterable[RackRead]) -> str:
    return "\n".join(json.dumps(to_row(item), sort_keys=True) for item in items)


def header_labels() -> list[str]:
    return [c[1] for c in COLUMNS]
