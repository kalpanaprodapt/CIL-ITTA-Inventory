"""Tabular export of Building records."""
from __future__ import annotations

import csv
import io
import json
from collections.abc import Iterable

from itta_core.csvutil import cell as _cell
from itta_api.domains.geography.building.schemas import BuildingRead

COLUMNS: list[tuple[str, str]] = [
    ("code", "Code"),
    ("name", "Name"),
    ("description", "Description"),
    ("status", "Status"),
    ("external_ref", "External Ref"),
    ("site_id", "Site Id"),
    ("floors", "Floors"),
    ("year_built", "Year Built"),
    ("ownership", "Ownership"),
    ("lease_expiry", "Lease Expiry"),
    ("security_level", "Security Level"),
    ("lifecycle_stage", "Lifecycle Stage"),
    ("source_system", "Source System"),
    ("region", "Region"),
    ("owner", "Owner"),
    ("criticality", "Criticality"),
]


def to_row(item: BuildingRead) -> dict[str, str]:
    return {
        "code": _cell(item.code),
        "name": _cell(item.name),
        "description": _cell(item.description),
        "status": _cell(item.status),
        "external_ref": _cell(item.external_ref),
        "site_id": _cell(item.site_id),
        "floors": _cell(item.floors),
        "year_built": _cell(item.year_built),
        "ownership": _cell(item.ownership),
        "lease_expiry": _cell(item.lease_expiry),
        "security_level": _cell(item.security_level),
        "lifecycle_stage": _cell(item.lifecycle_stage),
        "source_system": _cell(item.source_system),
        "region": _cell(item.region),
        "owner": _cell(item.owner),
        "criticality": _cell(item.criticality),
    }


def to_csv(items: Iterable[BuildingRead]) -> str:
    buf = io.StringIO()
    writer = csv.DictWriter(buf, fieldnames=[c[0] for c in COLUMNS], lineterminator="\n")
    writer.writeheader()
    for item in items:
        writer.writerow(to_row(item))
    return buf.getvalue()


def to_json_lines(items: Iterable[BuildingRead]) -> str:
    return "\n".join(json.dumps(to_row(item), sort_keys=True) for item in items)


def header_labels() -> list[str]:
    return [c[1] for c in COLUMNS]
