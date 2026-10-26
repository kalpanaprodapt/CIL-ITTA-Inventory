"""Tabular export of Support Contract records."""
from __future__ import annotations

import csv
import io
import json
from collections.abc import Iterable

from itta_core.csvutil import cell as _cell
from itta_api.domains.vendor.support_contract.schemas import SupportContractRead

COLUMNS: list[tuple[str, str]] = [
    ("code", "Code"),
    ("name", "Name"),
    ("description", "Description"),
    ("status", "Status"),
    ("external_ref", "External Ref"),
    ("vendor_id", "Vendor Id"),
    ("contract_number", "Contract Number"),
    ("start_date", "Start Date"),
    ("end_date", "End Date"),
    ("coverage_level", "Coverage Level"),
    ("annual_cost", "Annual Cost"),
    ("owner", "Owner"),
    ("criticality", "Criticality"),
    ("cost_center", "Cost Center"),
    ("notes", "Notes"),
    ("effective_from", "Effective From"),
]


def to_row(item: SupportContractRead) -> dict[str, str]:
    return {
        "code": _cell(item.code),
        "name": _cell(item.name),
        "description": _cell(item.description),
        "status": _cell(item.status),
        "external_ref": _cell(item.external_ref),
        "vendor_id": _cell(item.vendor_id),
        "contract_number": _cell(item.contract_number),
        "start_date": _cell(item.start_date),
        "end_date": _cell(item.end_date),
        "coverage_level": _cell(item.coverage_level),
        "annual_cost": _cell(item.annual_cost),
        "owner": _cell(item.owner),
        "criticality": _cell(item.criticality),
        "cost_center": _cell(item.cost_center),
        "notes": _cell(item.notes),
        "effective_from": _cell(item.effective_from),
    }


def to_csv(items: Iterable[SupportContractRead]) -> str:
    buf = io.StringIO()
    writer = csv.DictWriter(buf, fieldnames=[c[0] for c in COLUMNS], lineterminator="\n")
    writer.writeheader()
    for item in items:
        writer.writerow(to_row(item))
    return buf.getvalue()


def to_json_lines(items: Iterable[SupportContractRead]) -> str:
    return "\n".join(json.dumps(to_row(item), sort_keys=True) for item in items)


def header_labels() -> list[str]:
    return [c[1] for c in COLUMNS]
