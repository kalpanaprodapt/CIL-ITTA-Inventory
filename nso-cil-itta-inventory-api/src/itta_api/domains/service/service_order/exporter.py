"""Tabular export of Service Order records."""
from __future__ import annotations

import csv
import io
import json
from collections.abc import Iterable

from itta_core.csvutil import cell as _cell
from itta_api.domains.service.service_order.schemas import ServiceOrderRead

COLUMNS: list[tuple[str, str]] = [
    ("code", "Code"),
    ("name", "Name"),
    ("description", "Description"),
    ("status", "Status"),
    ("external_ref", "External Ref"),
    ("customer_id", "Customer Id"),
    ("order_type", "Order Type"),
    ("requested_on", "Requested On"),
    ("target_date", "Target Date"),
    ("priority", "Priority"),
    ("region", "Region"),
    ("owner", "Owner"),
    ("criticality", "Criticality"),
    ("cost_center", "Cost Center"),
    ("notes", "Notes"),
]


def to_row(item: ServiceOrderRead) -> dict[str, str]:
    return {
        "code": _cell(item.code),
        "name": _cell(item.name),
        "description": _cell(item.description),
        "status": _cell(item.status),
        "external_ref": _cell(item.external_ref),
        "customer_id": _cell(item.customer_id),
        "order_type": _cell(item.order_type),
        "requested_on": _cell(item.requested_on),
        "target_date": _cell(item.target_date),
        "priority": _cell(item.priority),
        "region": _cell(item.region),
        "owner": _cell(item.owner),
        "criticality": _cell(item.criticality),
        "cost_center": _cell(item.cost_center),
        "notes": _cell(item.notes),
    }


def to_csv(items: Iterable[ServiceOrderRead]) -> str:
    buf = io.StringIO()
    writer = csv.DictWriter(buf, fieldnames=[c[0] for c in COLUMNS], lineterminator="\n")
    writer.writeheader()
    for item in items:
        writer.writerow(to_row(item))
    return buf.getvalue()


def to_json_lines(items: Iterable[ServiceOrderRead]) -> str:
    return "\n".join(json.dumps(to_row(item), sort_keys=True) for item in items)


def header_labels() -> list[str]:
    return [c[1] for c in COLUMNS]
