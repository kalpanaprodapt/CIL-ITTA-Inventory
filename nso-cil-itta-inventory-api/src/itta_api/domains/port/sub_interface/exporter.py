"""Tabular export of Sub Interface records."""
from __future__ import annotations

import csv
import io
import json
from collections.abc import Iterable

from itta_core.csvutil import cell as _cell
from itta_api.domains.port.sub_interface.schemas import SubInterfaceRead

COLUMNS: list[tuple[str, str]] = [
    ("code", "Code"),
    ("name", "Name"),
    ("description", "Description"),
    ("status", "Status"),
    ("external_ref", "External Ref"),
    ("interface_id", "Interface Id"),
    ("vlan_tag", "Vlan Tag"),
    ("encapsulation", "Encapsulation"),
    ("ipv4_address", "Ipv4 Address"),
    ("ipv6_address", "Ipv6 Address"),
    ("cost_center", "Cost Center"),
    ("notes", "Notes"),
    ("effective_from", "Effective From"),
    ("effective_to", "Effective To"),
    ("labels", "Labels"),
]


def to_row(item: SubInterfaceRead) -> dict[str, str]:
    return {
        "code": _cell(item.code),
        "name": _cell(item.name),
        "description": _cell(item.description),
        "status": _cell(item.status),
        "external_ref": _cell(item.external_ref),
        "interface_id": _cell(item.interface_id),
        "vlan_tag": _cell(item.vlan_tag),
        "encapsulation": _cell(item.encapsulation),
        "ipv4_address": _cell(item.ipv4_address),
        "ipv6_address": _cell(item.ipv6_address),
        "cost_center": _cell(item.cost_center),
        "notes": _cell(item.notes),
        "effective_from": _cell(item.effective_from),
        "effective_to": _cell(item.effective_to),
        "labels": _cell(item.labels),
    }


def to_csv(items: Iterable[SubInterfaceRead]) -> str:
    buf = io.StringIO()
    writer = csv.DictWriter(buf, fieldnames=[c[0] for c in COLUMNS], lineterminator="\n")
    writer.writeheader()
    for item in items:
        writer.writerow(to_row(item))
    return buf.getvalue()


def to_json_lines(items: Iterable[SubInterfaceRead]) -> str:
    return "\n".join(json.dumps(to_row(item), sort_keys=True) for item in items)


def header_labels() -> list[str]:
    return [c[1] for c in COLUMNS]
