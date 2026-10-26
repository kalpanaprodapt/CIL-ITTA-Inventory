"""Tabular export of Room records."""
from __future__ import annotations

import csv
import io
import json
from collections.abc import Iterable

from itta_core.csvutil import cell as _cell
from itta_api.domains.facility.room.schemas import RoomRead

COLUMNS: list[tuple[str, str]] = [
    ("code", "Code"),
    ("name", "Name"),
    ("description", "Description"),
    ("status", "Status"),
    ("external_ref", "External Ref"),
    ("floor_id", "Floor Id"),
    ("room_type", "Room Type"),
    ("area_sqm", "Area Sqm"),
    ("cooling_tons", "Cooling Tons"),
    ("fire_zone", "Fire Zone"),
    ("effective_from", "Effective From"),
    ("effective_to", "Effective To"),
    ("labels", "Labels"),
    ("lifecycle_stage", "Lifecycle Stage"),
    ("source_system", "Source System"),
]


def to_row(item: RoomRead) -> dict[str, str]:
    return {
        "code": _cell(item.code),
        "name": _cell(item.name),
        "description": _cell(item.description),
        "status": _cell(item.status),
        "external_ref": _cell(item.external_ref),
        "floor_id": _cell(item.floor_id),
        "room_type": _cell(item.room_type),
        "area_sqm": _cell(item.area_sqm),
        "cooling_tons": _cell(item.cooling_tons),
        "fire_zone": _cell(item.fire_zone),
        "effective_from": _cell(item.effective_from),
        "effective_to": _cell(item.effective_to),
        "labels": _cell(item.labels),
        "lifecycle_stage": _cell(item.lifecycle_stage),
        "source_system": _cell(item.source_system),
    }


def to_csv(items: Iterable[RoomRead]) -> str:
    buf = io.StringIO()
    writer = csv.DictWriter(buf, fieldnames=[c[0] for c in COLUMNS], lineterminator="\n")
    writer.writeheader()
    for item in items:
        writer.writerow(to_row(item))
    return buf.getvalue()


def to_json_lines(items: Iterable[RoomRead]) -> str:
    return "\n".join(json.dumps(to_row(item), sort_keys=True) for item in items)


def header_labels() -> list[str]:
    return [c[1] for c in COLUMNS]
