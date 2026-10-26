"""Reconciliation of external Circuit Order records against the system of record."""
from __future__ import annotations

from decimal import Decimal

from itta_core.diffing import Action, FieldDiff, ReconciliationItem, compare_decimal, compare_value, plan_for
from itta_api.domains.circuit.circuit_order.normalizer import normalize_record
from itta_api.domains.circuit.circuit_order.schemas import CircuitOrderCreate, CircuitOrderRead, CircuitOrderUpdate


def diff_record(current: CircuitOrderRead, incoming: dict[str, object]) -> list[FieldDiff]:
    """Return the attribute level differences between the stored and the incoming record."""
    diffs: list[FieldDiff] = []
    diffs += compare_value("code", current.code, incoming.get("code"))
    diffs += compare_value("name", current.name, incoming.get("name"))
    diffs += compare_value("description", current.description, incoming.get("description"))
    diffs += compare_value("externalRef", current.external_ref, incoming.get("externalRef"))
    diffs += compare_value("circuitId", current.circuit_id, incoming.get("circuitId"))
    diffs += compare_value("orderNumber", current.order_number, incoming.get("orderNumber"))
    diffs += compare_value("orderedOn", current.ordered_on, incoming.get("orderedOn"))
    diffs += compare_value("requestedDate", current.requested_date, incoming.get("requestedDate"))
    diffs += compare_value("committedDate", current.committed_date, incoming.get("committedDate"))
    diffs += compare_value("orderStatus", current.order_status, incoming.get("orderStatus"))
    diffs += compare_value("effectiveFrom", current.effective_from, incoming.get("effectiveFrom"))
    diffs += compare_value("effectiveTo", current.effective_to, incoming.get("effectiveTo"))
    diffs += compare_value("labels", current.labels, incoming.get("labels"))
    diffs += compare_value("lifecycleStage", current.lifecycle_stage, incoming.get("lifecycleStage"))
    diffs += compare_value("sourceSystem", current.source_system, incoming.get("sourceSystem"))
    if incoming.get("status") is not None and incoming.get("status") != current.status:
        diffs.append(FieldDiff(field="status", current=current.status, incoming=incoming.get("status")))
    return diffs


def reconcile_one(current: CircuitOrderRead | None, raw: dict[str, object]) -> ReconciliationItem:
    """Decide what to do with one incoming record."""
    incoming = normalize_record(raw)
    code = incoming.get("code")
    if code is None:
        return ReconciliationItem(key=None, action=Action.REJECT, reason="missing code")
    if current is None:
        return ReconciliationItem(key=str(code), action=Action.CREATE, payload=incoming)
    diffs = diff_record(current, incoming)
    return plan_for(str(code), current.id, diffs, incoming)


def to_create(item: ReconciliationItem) -> CircuitOrderCreate:
    return CircuitOrderCreate.model_validate(item.payload or {})


def to_update(item: ReconciliationItem, version: int | None = None) -> CircuitOrderUpdate:
    changed = {d.field: d.incoming for d in item.diffs}
    return CircuitOrderUpdate.model_validate({**changed, "expectedVersion": version})


def reconcile_many(
    existing: dict[str, CircuitOrderRead], records: list[dict[str, object]]
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
