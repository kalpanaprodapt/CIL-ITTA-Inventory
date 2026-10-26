"""Tabular export of Sync Job records."""
from __future__ import annotations

import csv
import io
import json
from collections.abc import Iterable

from itta_core.csvutil import cell as _cell
from itta_api.domains.integration.sync_job.schemas import SyncJobRead

COLUMNS: list[tuple[str, str]] = [
    ("code", "Code"),
    ("name", "Name"),
    ("description", "Description"),
    ("status", "Status"),
    ("external_ref", "External Ref"),
    ("endpoint_id", "Endpoint Id"),
    ("direction", "Direction"),
    ("schedule_cron", "Schedule Cron"),
    ("last_run_at", "Last Run At"),
    ("last_status", "Last Status"),
    ("owner", "Owner"),
    ("criticality", "Criticality"),
    ("cost_center", "Cost Center"),
    ("notes", "Notes"),
    ("effective_from", "Effective From"),
]


def to_row(item: SyncJobRead) -> dict[str, str]:
    return {
        "code": _cell(item.code),
        "name": _cell(item.name),
        "description": _cell(item.description),
        "status": _cell(item.status),
        "external_ref": _cell(item.external_ref),
        "endpoint_id": _cell(item.endpoint_id),
        "direction": _cell(item.direction),
        "schedule_cron": _cell(item.schedule_cron),
        "last_run_at": _cell(item.last_run_at),
        "last_status": _cell(item.last_status),
        "owner": _cell(item.owner),
        "criticality": _cell(item.criticality),
        "cost_center": _cell(item.cost_center),
        "notes": _cell(item.notes),
        "effective_from": _cell(item.effective_from),
    }


def to_csv(items: Iterable[SyncJobRead]) -> str:
    buf = io.StringIO()
    writer = csv.DictWriter(buf, fieldnames=[c[0] for c in COLUMNS], lineterminator="\n")
    writer.writeheader()
    for item in items:
        writer.writerow(to_row(item))
    return buf.getvalue()


def to_json_lines(items: Iterable[SyncJobRead]) -> str:
    return "\n".join(json.dumps(to_row(item), sort_keys=True) for item in items)


def header_labels() -> list[str]:
    return [c[1] for c in COLUMNS]
