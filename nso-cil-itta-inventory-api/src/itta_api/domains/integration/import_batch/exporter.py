"""Tabular export of Import Batch records."""
from __future__ import annotations

import csv
import io
import json
from collections.abc import Iterable

from itta_core.csvutil import cell as _cell
from itta_api.domains.integration.import_batch.schemas import ImportBatchRead

COLUMNS: list[tuple[str, str]] = [
    ("code", "Code"),
    ("name", "Name"),
    ("description", "Description"),
    ("status", "Status"),
    ("external_ref", "External Ref"),
    ("file_name", "File Name"),
    ("entity_type", "Entity Type"),
    ("total_rows", "Total Rows"),
    ("processed_rows", "Processed Rows"),
    ("failed_rows", "Failed Rows"),
    ("batch_state", "Batch State"),
    ("effective_from", "Effective From"),
    ("effective_to", "Effective To"),
    ("labels", "Labels"),
    ("lifecycle_stage", "Lifecycle Stage"),
    ("source_system", "Source System"),
]


def to_row(item: ImportBatchRead) -> dict[str, str]:
    return {
        "code": _cell(item.code),
        "name": _cell(item.name),
        "description": _cell(item.description),
        "status": _cell(item.status),
        "external_ref": _cell(item.external_ref),
        "file_name": _cell(item.file_name),
        "entity_type": _cell(item.entity_type),
        "total_rows": _cell(item.total_rows),
        "processed_rows": _cell(item.processed_rows),
        "failed_rows": _cell(item.failed_rows),
        "batch_state": _cell(item.batch_state),
        "effective_from": _cell(item.effective_from),
        "effective_to": _cell(item.effective_to),
        "labels": _cell(item.labels),
        "lifecycle_stage": _cell(item.lifecycle_stage),
        "source_system": _cell(item.source_system),
    }


def to_csv(items: Iterable[ImportBatchRead]) -> str:
    buf = io.StringIO()
    writer = csv.DictWriter(buf, fieldnames=[c[0] for c in COLUMNS], lineterminator="\n")
    writer.writeheader()
    for item in items:
        writer.writerow(to_row(item))
    return buf.getvalue()


def to_json_lines(items: Iterable[ImportBatchRead]) -> str:
    return "\n".join(json.dumps(to_row(item), sort_keys=True) for item in items)


def header_labels() -> list[str]:
    return [c[1] for c in COLUMNS]
