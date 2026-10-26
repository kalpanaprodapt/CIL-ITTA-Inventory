"""Tabular export of Bgp Session records."""
from __future__ import annotations

import csv
import io
import json
from collections.abc import Iterable

from itta_core.csvutil import cell as _cell
from itta_api.domains.switching.bgp_session.schemas import BgpSessionRead

COLUMNS: list[tuple[str, str]] = [
    ("code", "Code"),
    ("name", "Name"),
    ("description", "Description"),
    ("status", "Status"),
    ("external_ref", "External Ref"),
    ("local_device_id", "Local Device Id"),
    ("peer_address", "Peer Address"),
    ("local_asn", "Local Asn"),
    ("remote_asn", "Remote Asn"),
    ("session_state", "Session State"),
    ("prefix_limit", "Prefix Limit"),
    ("region", "Region"),
    ("owner", "Owner"),
    ("criticality", "Criticality"),
    ("cost_center", "Cost Center"),
    ("notes", "Notes"),
]


def to_row(item: BgpSessionRead) -> dict[str, str]:
    return {
        "code": _cell(item.code),
        "name": _cell(item.name),
        "description": _cell(item.description),
        "status": _cell(item.status),
        "external_ref": _cell(item.external_ref),
        "local_device_id": _cell(item.local_device_id),
        "peer_address": _cell(item.peer_address),
        "local_asn": _cell(item.local_asn),
        "remote_asn": _cell(item.remote_asn),
        "session_state": _cell(item.session_state),
        "prefix_limit": _cell(item.prefix_limit),
        "region": _cell(item.region),
        "owner": _cell(item.owner),
        "criticality": _cell(item.criticality),
        "cost_center": _cell(item.cost_center),
        "notes": _cell(item.notes),
    }


def to_csv(items: Iterable[BgpSessionRead]) -> str:
    buf = io.StringIO()
    writer = csv.DictWriter(buf, fieldnames=[c[0] for c in COLUMNS], lineterminator="\n")
    writer.writeheader()
    for item in items:
        writer.writerow(to_row(item))
    return buf.getvalue()


def to_json_lines(items: Iterable[BgpSessionRead]) -> str:
    return "\n".join(json.dumps(to_row(item), sort_keys=True) for item in items)


def header_labels() -> list[str]:
    return [c[1] for c in COLUMNS]
