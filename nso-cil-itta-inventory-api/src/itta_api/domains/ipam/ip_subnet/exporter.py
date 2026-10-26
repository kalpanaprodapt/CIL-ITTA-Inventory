"""Tabular export of Ip Subnet records."""
from __future__ import annotations

import csv
import io
import json
from collections.abc import Iterable

from itta_core.csvutil import cell as _cell
from itta_api.domains.ipam.ip_subnet.schemas import IpSubnetRead

COLUMNS: list[tuple[str, str]] = [
    ("code", "Code"),
    ("name", "Name"),
    ("description", "Description"),
    ("status", "Status"),
    ("external_ref", "External Ref"),
    ("cidr", "Cidr"),
    ("ip_version", "Ip Version"),
    ("vrf_id", "Vrf Id"),
    ("gateway", "Gateway"),
    ("vlan_id", "Vlan Id"),
    ("utilization_pct", "Utilization Pct"),
    ("owner", "Owner"),
    ("criticality", "Criticality"),
    ("cost_center", "Cost Center"),
    ("notes", "Notes"),
    ("effective_from", "Effective From"),
]


def to_row(item: IpSubnetRead) -> dict[str, str]:
    return {
        "code": _cell(item.code),
        "name": _cell(item.name),
        "description": _cell(item.description),
        "status": _cell(item.status),
        "external_ref": _cell(item.external_ref),
        "cidr": _cell(item.cidr),
        "ip_version": _cell(item.ip_version),
        "vrf_id": _cell(item.vrf_id),
        "gateway": _cell(item.gateway),
        "vlan_id": _cell(item.vlan_id),
        "utilization_pct": _cell(item.utilization_pct),
        "owner": _cell(item.owner),
        "criticality": _cell(item.criticality),
        "cost_center": _cell(item.cost_center),
        "notes": _cell(item.notes),
        "effective_from": _cell(item.effective_from),
    }


def to_csv(items: Iterable[IpSubnetRead]) -> str:
    buf = io.StringIO()
    writer = csv.DictWriter(buf, fieldnames=[c[0] for c in COLUMNS], lineterminator="\n")
    writer.writeheader()
    for item in items:
        writer.writerow(to_row(item))
    return buf.getvalue()


def to_json_lines(items: Iterable[IpSubnetRead]) -> str:
    return "\n".join(json.dumps(to_row(item), sort_keys=True) for item in items)


def header_labels() -> list[str]:
    return [c[1] for c in COLUMNS]
