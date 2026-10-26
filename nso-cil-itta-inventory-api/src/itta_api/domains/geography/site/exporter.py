"""Tabular export of Site records."""
from __future__ import annotations

import csv
import io
import json
from collections.abc import Iterable

from itta_core.csvutil import cell as _cell
from itta_api.domains.geography.site.schemas import SiteRead

COLUMNS: list[tuple[str, str]] = [
    ("code", "Code"),
    ("name", "Name"),
    ("description", "Description"),
    ("status", "Status"),
    ("external_ref", "External Ref"),
    ("city_id", "City Id"),
    ("site_type", "Site Type"),
    ("address", "Address"),
    ("latitude", "Latitude"),
    ("longitude", "Longitude"),
    ("power_kw", "Power Kw"),
    ("access_hours", "Access Hours"),
    ("effective_to", "Effective To"),
    ("labels", "Labels"),
    ("lifecycle_stage", "Lifecycle Stage"),
    ("source_system", "Source System"),
    ("region", "Region"),
]


def to_row(item: SiteRead) -> dict[str, str]:
    return {
        "code": _cell(item.code),
        "name": _cell(item.name),
        "description": _cell(item.description),
        "status": _cell(item.status),
        "external_ref": _cell(item.external_ref),
        "city_id": _cell(item.city_id),
        "site_type": _cell(item.site_type),
        "address": _cell(item.address),
        "latitude": _cell(item.latitude),
        "longitude": _cell(item.longitude),
        "power_kw": _cell(item.power_kw),
        "access_hours": _cell(item.access_hours),
        "effective_to": _cell(item.effective_to),
        "labels": _cell(item.labels),
        "lifecycle_stage": _cell(item.lifecycle_stage),
        "source_system": _cell(item.source_system),
        "region": _cell(item.region),
    }


def to_csv(items: Iterable[SiteRead]) -> str:
    buf = io.StringIO()
    writer = csv.DictWriter(buf, fieldnames=[c[0] for c in COLUMNS], lineterminator="\n")
    writer.writeheader()
    for item in items:
        writer.writerow(to_row(item))
    return buf.getvalue()


def to_json_lines(items: Iterable[SiteRead]) -> str:
    return "\n".join(json.dumps(to_row(item), sort_keys=True) for item in items)


def header_labels() -> list[str]:
    return [c[1] for c in COLUMNS]
