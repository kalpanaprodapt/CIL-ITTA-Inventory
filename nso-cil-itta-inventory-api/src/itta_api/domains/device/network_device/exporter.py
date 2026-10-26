"""Tabular export of Network Device records."""
from __future__ import annotations

import csv
import io
import json
from collections.abc import Iterable

from itta_core.csvutil import cell as _cell
from itta_api.domains.device.network_device.schemas import NetworkDeviceRead

COLUMNS: list[tuple[str, str]] = [
    ("code", "Code"),
    ("name", "Name"),
    ("description", "Description"),
    ("status", "Status"),
    ("external_ref", "External Ref"),
    ("hostname", "Hostname"),
    ("device_role", "Device Role"),
    ("mgmt_ip", "Mgmt Ip"),
    ("serial_number", "Serial Number"),
    ("model_id", "Model Id"),
    ("rack_unit_id", "Rack Unit Id"),
    ("effective_to", "Effective To"),
    ("labels", "Labels"),
    ("lifecycle_stage", "Lifecycle Stage"),
    ("source_system", "Source System"),
    ("region", "Region"),
]


def to_row(item: NetworkDeviceRead) -> dict[str, str]:
    return {
        "code": _cell(item.code),
        "name": _cell(item.name),
        "description": _cell(item.description),
        "status": _cell(item.status),
        "external_ref": _cell(item.external_ref),
        "hostname": _cell(item.hostname),
        "device_role": _cell(item.device_role),
        "mgmt_ip": _cell(item.mgmt_ip),
        "serial_number": _cell(item.serial_number),
        "model_id": _cell(item.model_id),
        "rack_unit_id": _cell(item.rack_unit_id),
        "effective_to": _cell(item.effective_to),
        "labels": _cell(item.labels),
        "lifecycle_stage": _cell(item.lifecycle_stage),
        "source_system": _cell(item.source_system),
        "region": _cell(item.region),
    }


def to_csv(items: Iterable[NetworkDeviceRead]) -> str:
    buf = io.StringIO()
    writer = csv.DictWriter(buf, fieldnames=[c[0] for c in COLUMNS], lineterminator="\n")
    writer.writeheader()
    for item in items:
        writer.writerow(to_row(item))
    return buf.getvalue()


def to_json_lines(items: Iterable[NetworkDeviceRead]) -> str:
    return "\n".join(json.dumps(to_row(item), sort_keys=True) for item in items)


def header_labels() -> list[str]:
    return [c[1] for c in COLUMNS]
