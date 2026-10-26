"""Tabular export of Invoice records."""
from __future__ import annotations

import csv
import io
import json
from collections.abc import Iterable

from itta_core.csvutil import cell as _cell
from itta_api.domains.assetmgmt.invoice.schemas import InvoiceRead

COLUMNS: list[tuple[str, str]] = [
    ("code", "Code"),
    ("name", "Name"),
    ("description", "Description"),
    ("status", "Status"),
    ("external_ref", "External Ref"),
    ("invoice_number", "Invoice Number"),
    ("po_id", "Po Id"),
    ("amount", "Amount"),
    ("due_date", "Due Date"),
    ("paid", "Paid"),
    ("cost_center", "Cost Center"),
    ("notes", "Notes"),
    ("effective_from", "Effective From"),
    ("effective_to", "Effective To"),
    ("labels", "Labels"),
]


def to_row(item: InvoiceRead) -> dict[str, str]:
    return {
        "code": _cell(item.code),
        "name": _cell(item.name),
        "description": _cell(item.description),
        "status": _cell(item.status),
        "external_ref": _cell(item.external_ref),
        "invoice_number": _cell(item.invoice_number),
        "po_id": _cell(item.po_id),
        "amount": _cell(item.amount),
        "due_date": _cell(item.due_date),
        "paid": _cell(item.paid),
        "cost_center": _cell(item.cost_center),
        "notes": _cell(item.notes),
        "effective_from": _cell(item.effective_from),
        "effective_to": _cell(item.effective_to),
        "labels": _cell(item.labels),
    }


def to_csv(items: Iterable[InvoiceRead]) -> str:
    buf = io.StringIO()
    writer = csv.DictWriter(buf, fieldnames=[c[0] for c in COLUMNS], lineterminator="\n")
    writer.writeheader()
    for item in items:
        writer.writerow(to_row(item))
    return buf.getvalue()


def to_json_lines(items: Iterable[InvoiceRead]) -> str:
    return "\n".join(json.dumps(to_row(item), sort_keys=True) for item in items)


def header_labels() -> list[str]:
    return [c[1] for c in COLUMNS]
