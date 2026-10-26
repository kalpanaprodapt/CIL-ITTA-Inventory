"""Tabular export of Field Task records."""
from __future__ import annotations

import csv
import io
import json
from collections.abc import Iterable

from itta_core.csvutil import cell as _cell
from itta_api.domains.change.field_task.schemas import FieldTaskRead

COLUMNS: list[tuple[str, str]] = [
    ("code", "Code"),
    ("name", "Name"),
    ("description", "Description"),
    ("status", "Status"),
    ("external_ref", "External Ref"),
    ("work_order_id", "Work Order Id"),
    ("task_name", "Task Name"),
    ("sequence", "Sequence"),
    ("duration_minutes", "Duration Minutes"),
    ("completed_on", "Completed On"),
    ("region", "Region"),
    ("owner", "Owner"),
    ("criticality", "Criticality"),
    ("cost_center", "Cost Center"),
    ("notes", "Notes"),
]


def to_row(item: FieldTaskRead) -> dict[str, str]:
    return {
        "code": _cell(item.code),
        "name": _cell(item.name),
        "description": _cell(item.description),
        "status": _cell(item.status),
        "external_ref": _cell(item.external_ref),
        "work_order_id": _cell(item.work_order_id),
        "task_name": _cell(item.task_name),
        "sequence": _cell(item.sequence),
        "duration_minutes": _cell(item.duration_minutes),
        "completed_on": _cell(item.completed_on),
        "region": _cell(item.region),
        "owner": _cell(item.owner),
        "criticality": _cell(item.criticality),
        "cost_center": _cell(item.cost_center),
        "notes": _cell(item.notes),
    }


def to_csv(items: Iterable[FieldTaskRead]) -> str:
    buf = io.StringIO()
    writer = csv.DictWriter(buf, fieldnames=[c[0] for c in COLUMNS], lineterminator="\n")
    writer.writeheader()
    for item in items:
        writer.writerow(to_row(item))
    return buf.getvalue()


def to_json_lines(items: Iterable[FieldTaskRead]) -> str:
    return "\n".join(json.dumps(to_row(item), sort_keys=True) for item in items)


def header_labels() -> list[str]:
    return [c[1] for c in COLUMNS]
