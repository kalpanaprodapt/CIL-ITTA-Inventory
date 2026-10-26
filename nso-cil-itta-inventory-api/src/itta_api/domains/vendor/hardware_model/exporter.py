"""Tabular export of Hardware Model records."""
from __future__ import annotations

import csv
import io
import json
from collections.abc import Iterable

from itta_core.csvutil import cell as _cell
from itta_api.domains.vendor.hardware_model.schemas import HardwareModelRead

COLUMNS: list[tuple[str, str]] = [
    ("code", "Code"),
    ("name", "Name"),
    ("description", "Description"),
    ("status", "Status"),
    ("external_ref", "External Ref"),
    ("vendor_id", "Vendor Id"),
    ("model_number", "Model Number"),
    ("form_factor", "Form Factor"),
    ("power_watts", "Power Watts"),
    ("eol_date", "Eol Date"),
    ("eos_date", "Eos Date"),
    ("labels", "Labels"),
    ("lifecycle_stage", "Lifecycle Stage"),
    ("source_system", "Source System"),
    ("region", "Region"),
    ("owner", "Owner"),
]


def to_row(item: HardwareModelRead) -> dict[str, str]:
    return {
        "code": _cell(item.code),
        "name": _cell(item.name),
        "description": _cell(item.description),
        "status": _cell(item.status),
        "external_ref": _cell(item.external_ref),
        "vendor_id": _cell(item.vendor_id),
        "model_number": _cell(item.model_number),
        "form_factor": _cell(item.form_factor),
        "power_watts": _cell(item.power_watts),
        "eol_date": _cell(item.eol_date),
        "eos_date": _cell(item.eos_date),
        "labels": _cell(item.labels),
        "lifecycle_stage": _cell(item.lifecycle_stage),
        "source_system": _cell(item.source_system),
        "region": _cell(item.region),
        "owner": _cell(item.owner),
    }


def to_csv(items: Iterable[HardwareModelRead]) -> str:
    buf = io.StringIO()
    writer = csv.DictWriter(buf, fieldnames=[c[0] for c in COLUMNS], lineterminator="\n")
    writer.writeheader()
    for item in items:
        writer.writerow(to_row(item))
    return buf.getvalue()


def to_json_lines(items: Iterable[HardwareModelRead]) -> str:
    return "\n".join(json.dumps(to_row(item), sort_keys=True) for item in items)


def header_labels() -> list[str]:
    return [c[1] for c in COLUMNS]
