"""Tabular export of Compliance Check records."""
from __future__ import annotations

import csv
import io
import json
from collections.abc import Iterable

from itta_core.csvutil import cell as _cell
from itta_api.domains.compliance.compliance_check.schemas import ComplianceCheckRead

COLUMNS: list[tuple[str, str]] = [
    ("code", "Code"),
    ("name", "Name"),
    ("description", "Description"),
    ("status", "Status"),
    ("external_ref", "External Ref"),
    ("rule_id", "Rule Id"),
    ("target_ref", "Target Ref"),
    ("checked_at", "Checked At"),
    ("result", "Result"),
    ("evidence", "Evidence"),
    ("criticality", "Criticality"),
    ("cost_center", "Cost Center"),
    ("notes", "Notes"),
    ("effective_from", "Effective From"),
    ("effective_to", "Effective To"),
]


def to_row(item: ComplianceCheckRead) -> dict[str, str]:
    return {
        "code": _cell(item.code),
        "name": _cell(item.name),
        "description": _cell(item.description),
        "status": _cell(item.status),
        "external_ref": _cell(item.external_ref),
        "rule_id": _cell(item.rule_id),
        "target_ref": _cell(item.target_ref),
        "checked_at": _cell(item.checked_at),
        "result": _cell(item.result),
        "evidence": _cell(item.evidence),
        "criticality": _cell(item.criticality),
        "cost_center": _cell(item.cost_center),
        "notes": _cell(item.notes),
        "effective_from": _cell(item.effective_from),
        "effective_to": _cell(item.effective_to),
    }


def to_csv(items: Iterable[ComplianceCheckRead]) -> str:
    buf = io.StringIO()
    writer = csv.DictWriter(buf, fieldnames=[c[0] for c in COLUMNS], lineterminator="\n")
    writer.writeheader()
    for item in items:
        writer.writerow(to_row(item))
    return buf.getvalue()


def to_json_lines(items: Iterable[ComplianceCheckRead]) -> str:
    return "\n".join(json.dumps(to_row(item), sort_keys=True) for item in items)


def header_labels() -> list[str]:
    return [c[1] for c in COLUMNS]
