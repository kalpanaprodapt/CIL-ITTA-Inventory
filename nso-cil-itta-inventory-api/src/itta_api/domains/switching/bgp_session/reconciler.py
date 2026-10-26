"""Reconciliation of external Bgp Session records against the system of record."""
from __future__ import annotations

from decimal import Decimal

from itta_core.diffing import Action, FieldDiff, ReconciliationItem, compare_decimal, compare_value, plan_for
from itta_api.domains.switching.bgp_session.normalizer import normalize_record
from itta_api.domains.switching.bgp_session.schemas import BgpSessionCreate, BgpSessionRead, BgpSessionUpdate


def diff_record(current: BgpSessionRead, incoming: dict[str, object]) -> list[FieldDiff]:
    """Return the attribute level differences between the stored and the incoming record."""
    diffs: list[FieldDiff] = []
    diffs += compare_value("code", current.code, incoming.get("code"))
    diffs += compare_value("name", current.name, incoming.get("name"))
    diffs += compare_value("description", current.description, incoming.get("description"))
    diffs += compare_value("externalRef", current.external_ref, incoming.get("externalRef"))
    diffs += compare_value("localDeviceId", current.local_device_id, incoming.get("localDeviceId"))
    diffs += compare_value("peerAddress", current.peer_address, incoming.get("peerAddress"))
    diffs += compare_value("localAsn", current.local_asn, incoming.get("localAsn"))
    diffs += compare_value("remoteAsn", current.remote_asn, incoming.get("remoteAsn"))
    diffs += compare_value("sessionState", current.session_state, incoming.get("sessionState"))
    diffs += compare_value("prefixLimit", current.prefix_limit, incoming.get("prefixLimit"))
    diffs += compare_value("region", current.region, incoming.get("region"))
    diffs += compare_value("owner", current.owner, incoming.get("owner"))
    diffs += compare_value("criticality", current.criticality, incoming.get("criticality"))
    diffs += compare_value("costCenter", current.cost_center, incoming.get("costCenter"))
    diffs += compare_value("notes", current.notes, incoming.get("notes"))
    if incoming.get("status") is not None and incoming.get("status") != current.status:
        diffs.append(FieldDiff(field="status", current=current.status, incoming=incoming.get("status")))
    return diffs


def reconcile_one(current: BgpSessionRead | None, raw: dict[str, object]) -> ReconciliationItem:
    """Decide what to do with one incoming record."""
    incoming = normalize_record(raw)
    code = incoming.get("code")
    if code is None:
        return ReconciliationItem(key=None, action=Action.REJECT, reason="missing code")
    if current is None:
        return ReconciliationItem(key=str(code), action=Action.CREATE, payload=incoming)
    diffs = diff_record(current, incoming)
    return plan_for(str(code), current.id, diffs, incoming)


def to_create(item: ReconciliationItem) -> BgpSessionCreate:
    return BgpSessionCreate.model_validate(item.payload or {})


def to_update(item: ReconciliationItem, version: int | None = None) -> BgpSessionUpdate:
    changed = {d.field: d.incoming for d in item.diffs}
    return BgpSessionUpdate.model_validate({**changed, "expectedVersion": version})


def reconcile_many(
    existing: dict[str, BgpSessionRead], records: list[dict[str, object]]
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
