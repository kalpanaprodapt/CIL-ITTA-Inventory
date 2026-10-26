"""Tabular export of Policy Document records."""
from __future__ import annotations

import csv
import io
import json
from collections.abc import Iterable

from itta_core.csvutil import cell as _cell
from itta_api.domains.compliance.policy_document.schemas import PolicyDocumentRead

COLUMNS: list[tuple[str, str]] = [
    ("code", "Code"),
    ("name", "Name"),
    ("description", "Description"),
    ("status", "Status"),
    ("external_ref", "External Ref"),
    ("title", "Title"),
    ("doc_version", "Doc Version"),
    ("effective_date", "Effective Date"),
    ("owner", "Owner"),
    ("document_url", "Document Url"),
    ("lifecycle_stage", "Lifecycle Stage"),
    ("source_system", "Source System"),
    ("region", "Region"),
    ("criticality", "Criticality"),
    ("cost_center", "Cost Center"),
]


def to_row(item: PolicyDocumentRead) -> dict[str, str]:
    return {
        "code": _cell(item.code),
        "name": _cell(item.name),
        "description": _cell(item.description),
        "status": _cell(item.status),
        "external_ref": _cell(item.external_ref),
        "title": _cell(item.title),
        "doc_version": _cell(item.doc_version),
        "effective_date": _cell(item.effective_date),
        "owner": _cell(item.owner),
        "document_url": _cell(item.document_url),
        "lifecycle_stage": _cell(item.lifecycle_stage),
        "source_system": _cell(item.source_system),
        "region": _cell(item.region),
        "criticality": _cell(item.criticality),
        "cost_center": _cell(item.cost_center),
    }


def to_csv(items: Iterable[PolicyDocumentRead]) -> str:
    buf = io.StringIO()
    writer = csv.DictWriter(buf, fieldnames=[c[0] for c in COLUMNS], lineterminator="\n")
    writer.writeheader()
    for item in items:
        writer.writerow(to_row(item))
    return buf.getvalue()


def to_json_lines(items: Iterable[PolicyDocumentRead]) -> str:
    return "\n".join(json.dumps(to_row(item), sort_keys=True) for item in items)


def header_labels() -> list[str]:
    return [c[1] for c in COLUMNS]
