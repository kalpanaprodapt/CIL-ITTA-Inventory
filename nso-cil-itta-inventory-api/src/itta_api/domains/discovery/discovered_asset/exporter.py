"""Tabular export of Discovered Asset records."""
from __future__ import annotations

import csv
import io
import json
from collections.abc import Iterable

from itta_core.csvutil import cell as _cell
from itta_api.domains.discovery.discovered_asset.schemas import DiscoveredAssetRead

COLUMNS: list[tuple[str, str]] = [
    ("code", "Code"),
    ("name", "Name"),
    ("description", "Description"),
    ("status", "Status"),
    ("external_ref", "External Ref"),
    ("job_id", "Job Id"),
    ("asset_type", "Asset Type"),
    ("fingerprint", "Fingerprint"),
    ("raw_payload", "Raw Payload"),
    ("matched_asset_id", "Matched Asset Id"),
    ("effective_from", "Effective From"),
    ("effective_to", "Effective To"),
    ("labels", "Labels"),
    ("lifecycle_stage", "Lifecycle Stage"),
    ("source_system", "Source System"),
]


def to_row(item: DiscoveredAssetRead) -> dict[str, str]:
    return {
        "code": _cell(item.code),
        "name": _cell(item.name),
        "description": _cell(item.description),
        "status": _cell(item.status),
        "external_ref": _cell(item.external_ref),
        "job_id": _cell(item.job_id),
        "asset_type": _cell(item.asset_type),
        "fingerprint": _cell(item.fingerprint),
        "raw_payload": _cell(item.raw_payload),
        "matched_asset_id": _cell(item.matched_asset_id),
        "effective_from": _cell(item.effective_from),
        "effective_to": _cell(item.effective_to),
        "labels": _cell(item.labels),
        "lifecycle_stage": _cell(item.lifecycle_stage),
        "source_system": _cell(item.source_system),
    }


def to_csv(items: Iterable[DiscoveredAssetRead]) -> str:
    buf = io.StringIO()
    writer = csv.DictWriter(buf, fieldnames=[c[0] for c in COLUMNS], lineterminator="\n")
    writer.writeheader()
    for item in items:
        writer.writerow(to_row(item))
    return buf.getvalue()


def to_json_lines(items: Iterable[DiscoveredAssetRead]) -> str:
    return "\n".join(json.dumps(to_row(item), sort_keys=True) for item in items)


def header_labels() -> list[str]:
    return [c[1] for c in COLUMNS]
