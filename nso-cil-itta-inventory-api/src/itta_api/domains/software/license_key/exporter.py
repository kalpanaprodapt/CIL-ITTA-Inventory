"""Tabular export of License Key records."""
from __future__ import annotations

import csv
import io
import json
from collections.abc import Iterable

from itta_core.csvutil import cell as _cell
from itta_api.domains.software.license_key.schemas import LicenseKeyRead

COLUMNS: list[tuple[str, str]] = [
    ("code", "Code"),
    ("name", "Name"),
    ("description", "Description"),
    ("status", "Status"),
    ("external_ref", "External Ref"),
    ("pool_id", "Pool Id"),
    ("key_value", "Key Value"),
    ("feature", "Feature"),
    ("expires_on", "Expires On"),
    ("assigned_device_id", "Assigned Device Id"),
    ("notes", "Notes"),
    ("effective_from", "Effective From"),
    ("effective_to", "Effective To"),
    ("labels", "Labels"),
    ("lifecycle_stage", "Lifecycle Stage"),
]


def to_row(item: LicenseKeyRead) -> dict[str, str]:
    return {
        "code": _cell(item.code),
        "name": _cell(item.name),
        "description": _cell(item.description),
        "status": _cell(item.status),
        "external_ref": _cell(item.external_ref),
        "pool_id": _cell(item.pool_id),
        "key_value": _cell(item.key_value),
        "feature": _cell(item.feature),
        "expires_on": _cell(item.expires_on),
        "assigned_device_id": _cell(item.assigned_device_id),
        "notes": _cell(item.notes),
        "effective_from": _cell(item.effective_from),
        "effective_to": _cell(item.effective_to),
        "labels": _cell(item.labels),
        "lifecycle_stage": _cell(item.lifecycle_stage),
    }


def to_csv(items: Iterable[LicenseKeyRead]) -> str:
    buf = io.StringIO()
    writer = csv.DictWriter(buf, fieldnames=[c[0] for c in COLUMNS], lineterminator="\n")
    writer.writeheader()
    for item in items:
        writer.writerow(to_row(item))
    return buf.getvalue()


def to_json_lines(items: Iterable[LicenseKeyRead]) -> str:
    return "\n".join(json.dumps(to_row(item), sort_keys=True) for item in items)


def header_labels() -> list[str]:
    return [c[1] for c in COLUMNS]
