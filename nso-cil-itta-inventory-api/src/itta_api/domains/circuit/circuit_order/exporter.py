"""Tabular export of Circuit Order records."""
from __future__ import annotations

import csv
import io
import json
from collections.abc import Iterable

from itta_core.csvutil import cell as _cell
from itta_api.domains.circuit.circuit_order.schemas import CircuitOrderRead

COLUMNS: list[tuple[str, str]] = [
    ("code", "Code"),
    ("name", "Name"),
    ("description", "Description"),
    ("status", "Status"),
    ("external_ref", "External Ref"),
    ("circuit_id", "Circuit Id"),
    ("order_number", "Order Number"),
    ("ordered_on", "Ordered On"),
    ("requested_date", "Requested Date"),
    ("committed_date", "Committed Date"),
    ("order_status", "Order Status"),
    ("effective_from", "Effective From"),
    ("effective_to", "Effective To"),
    ("labels", "Labels"),
    ("lifecycle_stage", "Lifecycle Stage"),
    ("source_system", "Source System"),
]


def to_row(item: CircuitOrderRead) -> dict[str, str]:
    return {
        "code": _cell(item.code),
        "name": _cell(item.name),
        "description": _cell(item.description),
        "status": _cell(item.status),
        "external_ref": _cell(item.external_ref),
        "circuit_id": _cell(item.circuit_id),
        "order_number": _cell(item.order_number),
        "ordered_on": _cell(item.ordered_on),
        "requested_date": _cell(item.requested_date),
        "committed_date": _cell(item.committed_date),
        "order_status": _cell(item.order_status),
        "effective_from": _cell(item.effective_from),
        "effective_to": _cell(item.effective_to),
        "labels": _cell(item.labels),
        "lifecycle_stage": _cell(item.lifecycle_stage),
        "source_system": _cell(item.source_system),
    }


def to_csv(items: Iterable[CircuitOrderRead]) -> str:
    buf = io.StringIO()
    writer = csv.DictWriter(buf, fieldnames=[c[0] for c in COLUMNS], lineterminator="\n")
    writer.writeheader()
    for item in items:
        writer.writerow(to_row(item))
    return buf.getvalue()


def to_json_lines(items: Iterable[CircuitOrderRead]) -> str:
    return "\n".join(json.dumps(to_row(item), sort_keys=True) for item in items)


def header_labels() -> list[str]:
    return [c[1] for c in COLUMNS]
