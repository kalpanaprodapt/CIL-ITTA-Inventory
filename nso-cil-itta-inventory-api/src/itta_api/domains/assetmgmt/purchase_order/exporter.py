"""Tabular export of Purchase Order records."""
from __future__ import annotations

import csv
import io
import json
from collections.abc import Iterable

from itta_core.csvutil import cell as _cell
from itta_api.domains.assetmgmt.purchase_order.schemas import PurchaseOrderRead

COLUMNS: list[tuple[str, str]] = [
    ("code", "Code"),
    ("name", "Name"),
    ("description", "Description"),
    ("status", "Status"),
    ("external_ref", "External Ref"),
    ("po_number", "Po Number"),
    ("vendor_id", "Vendor Id"),
    ("ordered_on", "Ordered On"),
    ("total_amount", "Total Amount"),
    ("currency", "Currency"),
    ("owner", "Owner"),
    ("criticality", "Criticality"),
    ("cost_center", "Cost Center"),
    ("notes", "Notes"),
    ("effective_from", "Effective From"),
]


def to_row(item: PurchaseOrderRead) -> dict[str, str]:
    return {
        "code": _cell(item.code),
        "name": _cell(item.name),
        "description": _cell(item.description),
        "status": _cell(item.status),
        "external_ref": _cell(item.external_ref),
        "po_number": _cell(item.po_number),
        "vendor_id": _cell(item.vendor_id),
        "ordered_on": _cell(item.ordered_on),
        "total_amount": _cell(item.total_amount),
        "currency": _cell(item.currency),
        "owner": _cell(item.owner),
        "criticality": _cell(item.criticality),
        "cost_center": _cell(item.cost_center),
        "notes": _cell(item.notes),
        "effective_from": _cell(item.effective_from),
    }


def to_csv(items: Iterable[PurchaseOrderRead]) -> str:
    buf = io.StringIO()
    writer = csv.DictWriter(buf, fieldnames=[c[0] for c in COLUMNS], lineterminator="\n")
    writer.writeheader()
    for item in items:
        writer.writerow(to_row(item))
    return buf.getvalue()


def to_json_lines(items: Iterable[PurchaseOrderRead]) -> str:
    return "\n".join(json.dumps(to_row(item), sort_keys=True) for item in items)


def header_labels() -> list[str]:
    return [c[1] for c in COLUMNS]
