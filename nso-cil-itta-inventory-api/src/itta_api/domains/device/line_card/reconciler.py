"""Reconciliation of external Line Card records against the system of record."""
from __future__ import annotations

from decimal import Decimal

from itta_core.diffing import Action, FieldDiff, ReconciliationItem, compare_decimal, compare_value, plan_for
from itta_api.domains.device.line_card.normalizer import normalize_record
from itta_api.domains.device.line_card.schemas import LineCardCreate, LineCardRead, LineCardUpdate


def diff_record(current: LineCardRead, incoming: dict[str, object]) -> list[FieldDiff]:
    """Return the attribute level differences between the stored and the incoming record."""
    diffs: list[FieldDiff] = []
    diffs += compare_value("code", current.code, incoming.get("code"))
    diffs += compare_value("name", current.name, incoming.get("name"))
    diffs += compare_value("description", current.description, incoming.get("description"))
    diffs += compare_value("externalRef", current.external_ref, incoming.get("externalRef"))
    diffs += compare_value("chassisId", current.chassis_id, incoming.get("chassisId"))
    diffs += compare_value("slotNumber", current.slot_number, incoming.get("slotNumber"))
    diffs += compare_value("cardType", current.card_type, incoming.get("cardType"))
    diffs += compare_value("portCount", current.port_count, incoming.get("portCount"))
    diffs += compare_value("serialNumber", current.serial_number, incoming.get("serialNumber"))
    diffs += compare_value("firmware", current.firmware, incoming.get("firmware"))
    diffs += compare_value("region", current.region, incoming.get("region"))
    diffs += compare_value("owner", current.owner, incoming.get("owner"))
    diffs += compare_value("criticality", current.criticality, incoming.get("criticality"))
    diffs += compare_value("costCenter", current.cost_center, incoming.get("costCenter"))
    diffs += compare_value("notes", current.notes, incoming.get("notes"))
    if incoming.get("status") is not None and incoming.get("status") != current.status:
        diffs.append(FieldDiff(field="status", current=current.status, incoming=incoming.get("status")))
    return diffs


def reconcile_one(current: LineCardRead | None, raw: dict[str, object]) -> ReconciliationItem:
    """Decide what to do with one incoming record."""
    incoming = normalize_record(raw)
    code = incoming.get("code")
    if code is None:
        return ReconciliationItem(key=None, action=Action.REJECT, reason="missing code")
    if current is None:
        return ReconciliationItem(key=str(code), action=Action.CREATE, payload=incoming)
    diffs = diff_record(current, incoming)
    return plan_for(str(code), current.id, diffs, incoming)


def to_create(item: ReconciliationItem) -> LineCardCreate:
    return LineCardCreate.model_validate(item.payload or {})


def to_update(item: ReconciliationItem, version: int | None = None) -> LineCardUpdate:
    changed = {d.field: d.incoming for d in item.diffs}
    return LineCardUpdate.model_validate({**changed, "expectedVersion": version})


def reconcile_many(
    existing: dict[str, LineCardRead], records: list[dict[str, object]]
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
