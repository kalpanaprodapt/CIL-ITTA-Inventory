"""Tabular export of Threshold Policy records."""
from __future__ import annotations

import csv
import io
import json
from collections.abc import Iterable

from itta_core.csvutil import cell as _cell
from itta_api.domains.capacity.threshold_policy.schemas import ThresholdPolicyRead

COLUMNS: list[tuple[str, str]] = [
    ("code", "Code"),
    ("name", "Name"),
    ("description", "Description"),
    ("status", "Status"),
    ("external_ref", "External Ref"),
    ("policy_name", "Policy Name"),
    ("metric", "Metric"),
    ("warn_pct", "Warn Pct"),
    ("crit_pct", "Crit Pct"),
    ("evaluation_window_minutes", "Evaluation Window Minutes"),
    ("region", "Region"),
    ("owner", "Owner"),
    ("criticality", "Criticality"),
    ("cost_center", "Cost Center"),
    ("notes", "Notes"),
]


def to_row(item: ThresholdPolicyRead) -> dict[str, str]:
    return {
        "code": _cell(item.code),
        "name": _cell(item.name),
        "description": _cell(item.description),
        "status": _cell(item.status),
        "external_ref": _cell(item.external_ref),
        "policy_name": _cell(item.policy_name),
        "metric": _cell(item.metric),
        "warn_pct": _cell(item.warn_pct),
        "crit_pct": _cell(item.crit_pct),
        "evaluation_window_minutes": _cell(item.evaluation_window_minutes),
        "region": _cell(item.region),
        "owner": _cell(item.owner),
        "criticality": _cell(item.criticality),
        "cost_center": _cell(item.cost_center),
        "notes": _cell(item.notes),
    }


def to_csv(items: Iterable[ThresholdPolicyRead]) -> str:
    buf = io.StringIO()
    writer = csv.DictWriter(buf, fieldnames=[c[0] for c in COLUMNS], lineterminator="\n")
    writer.writeheader()
    for item in items:
        writer.writerow(to_row(item))
    return buf.getvalue()


def to_json_lines(items: Iterable[ThresholdPolicyRead]) -> str:
    return "\n".join(json.dumps(to_row(item), sort_keys=True) for item in items)


def header_labels() -> list[str]:
    return [c[1] for c in COLUMNS]
