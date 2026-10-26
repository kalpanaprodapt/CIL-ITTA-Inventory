"""Tabular export of Maintenance Window records."""
from __future__ import annotations

import csv
import io
import json
from collections.abc import Iterable

from itta_core.csvutil import cell as _cell
from itta_api.domains.change.maintenance_window.schemas import MaintenanceWindowRead

COLUMNS: list[tuple[str, str]] = [
    ("code", "Code"),
    ("name", "Name"),
    ("description", "Description"),
    ("status", "Status"),
    ("external_ref", "External Ref"),
    ("window_name", "Window Name"),
    ("starts_at", "Starts At"),
    ("ends_at", "Ends At"),
    ("scope", "Scope"),
    ("impact_level", "Impact Level"),
    ("effective_to", "Effective To"),
    ("labels", "Labels"),
    ("lifecycle_stage", "Lifecycle Stage"),
    ("source_system", "Source System"),
    ("region", "Region"),
]


def to_row(item: MaintenanceWindowRead) -> dict[str, str]:
    return {
        "code": _cell(item.code),
        "name": _cell(item.name),
        "description": _cell(item.description),
        "status": _cell(item.status),
        "external_ref": _cell(item.external_ref),
        "window_name": _cell(item.window_name),
        "starts_at": _cell(item.starts_at),
        "ends_at": _cell(item.ends_at),
        "scope": _cell(item.scope),
        "impact_level": _cell(item.impact_level),
        "effective_to": _cell(item.effective_to),
        "labels": _cell(item.labels),
        "lifecycle_stage": _cell(item.lifecycle_stage),
        "source_system": _cell(item.source_system),
        "region": _cell(item.region),
    }


def to_csv(items: Iterable[MaintenanceWindowRead]) -> str:
    buf = io.StringIO()
    writer = csv.DictWriter(buf, fieldnames=[c[0] for c in COLUMNS], lineterminator="\n")
    writer.writeheader()
    for item in items:
        writer.writerow(to_row(item))
    return buf.getvalue()


def to_json_lines(items: Iterable[MaintenanceWindowRead]) -> str:
    return "\n".join(json.dumps(to_row(item), sort_keys=True) for item in items)


def header_labels() -> list[str]:
    return [c[1] for c in COLUMNS]
