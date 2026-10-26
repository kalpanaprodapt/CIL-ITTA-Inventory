"""Tabular export of Ip Address records."""
from __future__ import annotations

import csv
import io
import json
from collections.abc import Iterable

from itta_core.csvutil import cell as _cell
from itta_api.domains.ipam.ip_address.schemas import IpAddressRead

COLUMNS: list[tuple[str, str]] = [
    ("code", "Code"),
    ("name", "Name"),
    ("description", "Description"),
    ("status", "Status"),
    ("external_ref", "External Ref"),
    ("subnet_id", "Subnet Id"),
    ("address", "Address"),
    ("assigned_to_id", "Assigned To Id"),
    ("assignment_type", "Assignment Type"),
    ("dns_name", "Dns Name"),
    ("cost_center", "Cost Center"),
    ("notes", "Notes"),
    ("effective_from", "Effective From"),
    ("effective_to", "Effective To"),
    ("labels", "Labels"),
]


def to_row(item: IpAddressRead) -> dict[str, str]:
    return {
        "code": _cell(item.code),
        "name": _cell(item.name),
        "description": _cell(item.description),
        "status": _cell(item.status),
        "external_ref": _cell(item.external_ref),
        "subnet_id": _cell(item.subnet_id),
        "address": _cell(item.address),
        "assigned_to_id": _cell(item.assigned_to_id),
        "assignment_type": _cell(item.assignment_type),
        "dns_name": _cell(item.dns_name),
        "cost_center": _cell(item.cost_center),
        "notes": _cell(item.notes),
        "effective_from": _cell(item.effective_from),
        "effective_to": _cell(item.effective_to),
        "labels": _cell(item.labels),
    }


def to_csv(items: Iterable[IpAddressRead]) -> str:
    buf = io.StringIO()
    writer = csv.DictWriter(buf, fieldnames=[c[0] for c in COLUMNS], lineterminator="\n")
    writer.writeheader()
    for item in items:
        writer.writerow(to_row(item))
    return buf.getvalue()


def to_json_lines(items: Iterable[IpAddressRead]) -> str:
    return "\n".join(json.dumps(to_row(item), sort_keys=True) for item in items)


def header_labels() -> list[str]:
    return [c[1] for c in COLUMNS]
