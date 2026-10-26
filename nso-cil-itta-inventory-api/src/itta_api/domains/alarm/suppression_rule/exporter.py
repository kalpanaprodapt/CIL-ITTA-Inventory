"""Tabular export of Suppression Rule records."""
from __future__ import annotations

import csv
import io
import json
from collections.abc import Iterable

from itta_core.csvutil import cell as _cell
from itta_api.domains.alarm.suppression_rule.schemas import SuppressionRuleRead

COLUMNS: list[tuple[str, str]] = [
    ("code", "Code"),
    ("name", "Name"),
    ("description", "Description"),
    ("status", "Status"),
    ("external_ref", "External Ref"),
    ("rule_name", "Rule Name"),
    ("match_expression", "Match Expression"),
    ("starts_at", "Starts At"),
    ("ends_at", "Ends At"),
    ("reason", "Reason"),
    ("source_system", "Source System"),
    ("region", "Region"),
    ("owner", "Owner"),
    ("criticality", "Criticality"),
    ("cost_center", "Cost Center"),
]


def to_row(item: SuppressionRuleRead) -> dict[str, str]:
    return {
        "code": _cell(item.code),
        "name": _cell(item.name),
        "description": _cell(item.description),
        "status": _cell(item.status),
        "external_ref": _cell(item.external_ref),
        "rule_name": _cell(item.rule_name),
        "match_expression": _cell(item.match_expression),
        "starts_at": _cell(item.starts_at),
        "ends_at": _cell(item.ends_at),
        "reason": _cell(item.reason),
        "source_system": _cell(item.source_system),
        "region": _cell(item.region),
        "owner": _cell(item.owner),
        "criticality": _cell(item.criticality),
        "cost_center": _cell(item.cost_center),
    }


def to_csv(items: Iterable[SuppressionRuleRead]) -> str:
    buf = io.StringIO()
    writer = csv.DictWriter(buf, fieldnames=[c[0] for c in COLUMNS], lineterminator="\n")
    writer.writeheader()
    for item in items:
        writer.writerow(to_row(item))
    return buf.getvalue()


def to_json_lines(items: Iterable[SuppressionRuleRead]) -> str:
    return "\n".join(json.dumps(to_row(item), sort_keys=True) for item in items)


def header_labels() -> list[str]:
    return [c[1] for c in COLUMNS]
