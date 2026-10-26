"""Tabular export of Webhook Subscription records."""
from __future__ import annotations

import csv
import io
import json
from collections.abc import Iterable

from itta_core.csvutil import cell as _cell
from itta_api.domains.integration.webhook_subscription.schemas import WebhookSubscriptionRead

COLUMNS: list[tuple[str, str]] = [
    ("code", "Code"),
    ("name", "Name"),
    ("description", "Description"),
    ("status", "Status"),
    ("external_ref", "External Ref"),
    ("callback_url", "Callback Url"),
    ("event_types", "Event Types"),
    ("secret_ref", "Secret Ref"),
    ("active", "Active"),
    ("retry_limit", "Retry Limit"),
    ("labels", "Labels"),
    ("lifecycle_stage", "Lifecycle Stage"),
    ("source_system", "Source System"),
    ("region", "Region"),
    ("owner", "Owner"),
]


def to_row(item: WebhookSubscriptionRead) -> dict[str, str]:
    return {
        "code": _cell(item.code),
        "name": _cell(item.name),
        "description": _cell(item.description),
        "status": _cell(item.status),
        "external_ref": _cell(item.external_ref),
        "callback_url": _cell(item.callback_url),
        "event_types": _cell(item.event_types),
        "secret_ref": _cell(item.secret_ref),
        "active": _cell(item.active),
        "retry_limit": _cell(item.retry_limit),
        "labels": _cell(item.labels),
        "lifecycle_stage": _cell(item.lifecycle_stage),
        "source_system": _cell(item.source_system),
        "region": _cell(item.region),
        "owner": _cell(item.owner),
    }


def to_csv(items: Iterable[WebhookSubscriptionRead]) -> str:
    buf = io.StringIO()
    writer = csv.DictWriter(buf, fieldnames=[c[0] for c in COLUMNS], lineterminator="\n")
    writer.writeheader()
    for item in items:
        writer.writerow(to_row(item))
    return buf.getvalue()


def to_json_lines(items: Iterable[WebhookSubscriptionRead]) -> str:
    return "\n".join(json.dumps(to_row(item), sort_keys=True) for item in items)


def header_labels() -> list[str]:
    return [c[1] for c in COLUMNS]
