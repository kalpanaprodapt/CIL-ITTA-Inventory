"""Tabular export of Fiber Strand records."""
from __future__ import annotations

import csv
import io
import json
from collections.abc import Iterable

from itta_core.csvutil import cell as _cell
from itta_api.domains.cabling.fiber_strand.schemas import FiberStrandRead

COLUMNS: list[tuple[str, str]] = [
    ("code", "Code"),
    ("name", "Name"),
    ("description", "Description"),
    ("status", "Status"),
    ("external_ref", "External Ref"),
    ("cable_id", "Cable Id"),
    ("strand_number", "Strand Number"),
    ("buffer_tube", "Buffer Tube"),
    ("tube_color", "Tube Color"),
    ("attenuation_db", "Attenuation Db"),
    ("in_use", "In Use"),
    ("region", "Region"),
    ("owner", "Owner"),
    ("criticality", "Criticality"),
    ("cost_center", "Cost Center"),
    ("notes", "Notes"),
]


def to_row(item: FiberStrandRead) -> dict[str, str]:
    return {
        "code": _cell(item.code),
        "name": _cell(item.name),
        "description": _cell(item.description),
        "status": _cell(item.status),
        "external_ref": _cell(item.external_ref),
        "cable_id": _cell(item.cable_id),
        "strand_number": _cell(item.strand_number),
        "buffer_tube": _cell(item.buffer_tube),
        "tube_color": _cell(item.tube_color),
        "attenuation_db": _cell(item.attenuation_db),
        "in_use": _cell(item.in_use),
        "region": _cell(item.region),
        "owner": _cell(item.owner),
        "criticality": _cell(item.criticality),
        "cost_center": _cell(item.cost_center),
        "notes": _cell(item.notes),
    }


def to_csv(items: Iterable[FiberStrandRead]) -> str:
    buf = io.StringIO()
    writer = csv.DictWriter(buf, fieldnames=[c[0] for c in COLUMNS], lineterminator="\n")
    writer.writeheader()
    for item in items:
        writer.writerow(to_row(item))
    return buf.getvalue()


def to_json_lines(items: Iterable[FiberStrandRead]) -> str:
    return "\n".join(json.dumps(to_row(item), sort_keys=True) for item in items)


def header_labels() -> list[str]:
    return [c[1] for c in COLUMNS]
