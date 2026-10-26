"""Reconciliation of external Circuit records against the system of record."""
from __future__ import annotations

from decimal import Decimal

from itta_core.diffing import Action, FieldDiff, ReconciliationItem, compare_decimal, compare_value, plan_for
from itta_api.domains.circuit.circuit.normalizer import normalize_record
from itta_api.domains.circuit.circuit.schemas import CircuitCreate, CircuitRead, CircuitUpdate


def diff_record(current: CircuitRead, incoming: dict[str, object]) -> list[FieldDiff]:
    """Return the attribute level differences between the stored and the incoming record."""
    diffs: list[FieldDiff] = []
    diffs += compare_value("code", current.code, incoming.get("code"))
    diffs += compare_value("name", current.name, incoming.get("name"))
    diffs += compare_value("description", current.description, incoming.get("description"))
    diffs += compare_value("externalRef", current.external_ref, incoming.get("externalRef"))
    diffs += compare_value("circuitId", current.circuit_id, incoming.get("circuitId"))
    diffs += compare_value("circuitType", current.circuit_type, incoming.get("circuitType"))
    diffs += compare_value("bandwidthMbps", current.bandwidth_mbps, incoming.get("bandwidthMbps"))
    diffs += compare_value("providerId", current.provider_id, incoming.get("providerId"))
    diffs += compare_value("aSiteId", current.a_site_id, incoming.get("aSiteId"))
    diffs += compare_value("zSiteId", current.z_site_id, incoming.get("zSiteId"))
    diffs += compare_value("labels", current.labels, incoming.get("labels"))
    diffs += compare_value("lifecycleStage", current.lifecycle_stage, incoming.get("lifecycleStage"))
    diffs += compare_value("sourceSystem", current.source_system, incoming.get("sourceSystem"))
    diffs += compare_value("region", current.region, incoming.get("region"))
    diffs += compare_value("owner", current.owner, incoming.get("owner"))
    if incoming.get("status") is not None and incoming.get("status") != current.status:
        diffs.append(FieldDiff(field="status", current=current.status, incoming=incoming.get("status")))
    return diffs


def reconcile_one(current: CircuitRead | None, raw: dict[str, object]) -> ReconciliationItem:
    """Decide what to do with one incoming record."""
    incoming = normalize_record(raw)
    code = incoming.get("code")
    if code is None:
        return ReconciliationItem(key=None, action=Action.REJECT, reason="missing code")
    if current is None:
        return ReconciliationItem(key=str(code), action=Action.CREATE, payload=incoming)
    diffs = diff_record(current, incoming)
    return plan_for(str(code), current.id, diffs, incoming)


def to_create(item: ReconciliationItem) -> CircuitCreate:
    return CircuitCreate.model_validate(item.payload or {})


def to_update(item: ReconciliationItem, version: int | None = None) -> CircuitUpdate:
    changed = {d.field: d.incoming for d in item.diffs}
    return CircuitUpdate.model_validate({**changed, "expectedVersion": version})


def reconcile_many(
    existing: dict[str, CircuitRead], records: list[dict[str, object]]
) -> list[ReconciliationItem]:
    items: list[ReconciliationItem] = []
    seen: set[str] = set()
    for raw in records:
        item = reconcile_one(existing.get(str(normalize_record(raw).get("code"))), raw)
        if item.key is not None:
            if item.key in seen:
                item = ReconciliationItem(key=item.key, action=Action.REJECT, reason="duplicate in batch")
            seen.add(item.key)
        items.append(item)
    return items
