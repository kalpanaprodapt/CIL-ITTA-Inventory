"""Tabular export of Mpls Lsp records."""
from __future__ import annotations

import csv
import io
import json
from collections.abc import Iterable

from itta_core.csvutil import cell as _cell
from itta_api.domains.mpls.mpls_lsp.schemas import MplsLspRead

COLUMNS: list[tuple[str, str]] = [
    ("code", "Code"),
    ("name", "Name"),
    ("description", "Description"),
    ("status", "Status"),
    ("external_ref", "External Ref"),
    ("lsp_name", "Lsp Name"),
    ("head_end_id", "Head End Id"),
    ("tail_end_id", "Tail End Id"),
    ("bandwidth_mbps", "Bandwidth Mbps"),
    ("setup_priority", "Setup Priority"),
    ("path_type", "Path Type"),
    ("source_system", "Source System"),
    ("region", "Region"),
    ("owner", "Owner"),
    ("criticality", "Criticality"),
    ("cost_center", "Cost Center"),
]


def to_row(item: MplsLspRead) -> dict[str, str]:
    return {
        "code": _cell(item.code),
        "name": _cell(item.name),
        "description": _cell(item.description),
        "status": _cell(item.status),
        "external_ref": _cell(item.external_ref),
        "lsp_name": _cell(item.lsp_name),
        "head_end_id": _cell(item.head_end_id),
        "tail_end_id": _cell(item.tail_end_id),
        "bandwidth_mbps": _cell(item.bandwidth_mbps),
        "setup_priority": _cell(item.setup_priority),
        "path_type": _cell(item.path_type),
        "source_system": _cell(item.source_system),
        "region": _cell(item.region),
        "owner": _cell(item.owner),
        "criticality": _cell(item.criticality),
        "cost_center": _cell(item.cost_center),
    }


def to_csv(items: Iterable[MplsLspRead]) -> str:
    buf = io.StringIO()
    writer = csv.DictWriter(buf, fieldnames=[c[0] for c in COLUMNS], lineterminator="\n")
    writer.writeheader()
    for item in items:
        writer.writerow(to_row(item))
    return buf.getvalue()


def to_json_lines(items: Iterable[MplsLspRead]) -> str:
    return "\n".join(json.dumps(to_row(item), sort_keys=True) for item in items)


def header_labels() -> list[str]:
    return [c[1] for c in COLUMNS]
