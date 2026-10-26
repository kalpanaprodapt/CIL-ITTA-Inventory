"""Tabular export of Vrf Instance records."""
from __future__ import annotations

import csv
import io
import json
from collections.abc import Iterable

from itta_core.csvutil import cell as _cell
from itta_api.domains.ipam.vrf_instance.schemas import VrfInstanceRead

COLUMNS: list[tuple[str, str]] = [
    ("code", "Code"),
    ("name", "Name"),
    ("description", "Description"),
    ("status", "Status"),
    ("external_ref", "External Ref"),
    ("vrf_name", "Vrf Name"),
    ("route_distinguisher", "Route Distinguisher"),
    ("import_targets", "Import Targets"),
    ("export_targets", "Export Targets"),
    ("device_id", "Device Id"),
    ("labels", "Labels"),
    ("lifecycle_stage", "Lifecycle Stage"),
    ("source_system", "Source System"),
    ("region", "Region"),
    ("owner", "Owner"),
]


def to_row(item: VrfInstanceRead) -> dict[str, str]:
    return {
        "code": _cell(item.code),
        "name": _cell(item.name),
        "description": _cell(item.description),
        "status": _cell(item.status),
        "external_ref": _cell(item.external_ref),
        "vrf_name": _cell(item.vrf_name),
        "route_distinguisher": _cell(item.route_distinguisher),
        "import_targets": _cell(item.import_targets),
        "export_targets": _cell(item.export_targets),
        "device_id": _cell(item.device_id),
        "labels": _cell(item.labels),
        "lifecycle_stage": _cell(item.lifecycle_stage),
        "source_system": _cell(item.source_system),
        "region": _cell(item.region),
        "owner": _cell(item.owner),
    }


def to_csv(items: Iterable[VrfInstanceRead]) -> str:
    buf = io.StringIO()
    writer = csv.DictWriter(buf, fieldnames=[c[0] for c in COLUMNS], lineterminator="\n")
    writer.writeheader()
    for item in items:
        writer.writerow(to_row(item))
    return buf.getvalue()


def to_json_lines(items: Iterable[VrfInstanceRead]) -> str:
    return "\n".join(json.dumps(to_row(item), sort_keys=True) for item in items)


def header_labels() -> list[str]:
    return [c[1] for c in COLUMNS]
