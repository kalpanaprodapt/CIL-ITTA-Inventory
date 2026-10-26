"""Reconciliation of external Support Contract records against the system of record."""
from __future__ import annotations

from decimal import Decimal

from itta_core.diffing import Action, FieldDiff, ReconciliationItem, compare_decimal, compare_value, plan_for
from itta_api.domains.vendor.support_contract.normalizer import normalize_record
from itta_api.domains.vendor.support_contract.schemas import SupportContractCreate, SupportContractRead, SupportContractUpdate


def diff_record(current: SupportContractRead, incoming: dict[str, object]) -> list[FieldDiff]:
    """Return the attribute level differences between the stored and the incoming record."""
    diffs: list[FieldDiff] = []
    diffs += compare_value("code", current.code, incoming.get("code"))
    diffs += compare_value("name", current.name, incoming.get("name"))
    diffs += compare_value("description", current.description, incoming.get("description"))
    diffs += compare_value("externalRef", current.external_ref, incoming.get("externalRef"))
    diffs += compare_value("vendorId", current.vendor_id, incoming.get("vendorId"))
    diffs += compare_value("contractNumber", current.contract_number, incoming.get("contractNumber"))
    diffs += compare_value("startDate", current.start_date, incoming.get("startDate"))
    diffs += compare_value("endDate", current.end_date, incoming.get("endDate"))
    diffs += compare_value("coverageLevel", current.coverage_level, incoming.get("coverageLevel"))
    diffs += compare_decimal("annualCost", current.annual_cost, incoming.get("annualCost"), tolerance=Decimal("0.0001"))
    diffs += compare_value("owner", current.owner, incoming.get("owner"))
    diffs += compare_value("criticality", current.criticality, incoming.get("criticality"))
    diffs += compare_value("costCenter", current.cost_center, incoming.get("costCenter"))
    diffs += compare_value("notes", current.notes, incoming.get("notes"))
    diffs += compare_value("effectiveFrom", current.effective_from, incoming.get("effectiveFrom"))
    if incoming.get("status") is not None and incoming.get("status") != current.status:
        diffs.append(FieldDiff(field="status", current=current.status, incoming=incoming.get("status")))
    return diffs


def reconcile_one(current: SupportContractRead | None, raw: dict[str, object]) -> ReconciliationItem:
    """Decide what to do with one incoming record."""
    incoming = normalize_record(raw)
    code = incoming.get("code")
    if code is None:
        return ReconciliationItem(key=None, action=Action.REJECT, reason="missing code")
    if current is None:
        return ReconciliationItem(key=str(code), action=Action.CREATE, payload=incoming)
    diffs = diff_record(current, incoming)
    return plan_for(str(code), current.id, diffs, incoming)


def to_create(item: ReconciliationItem) -> SupportContractCreate:
    return SupportContractCreate.model_validate(item.payload or {})


def to_update(item: ReconciliationItem, version: int | None = None) -> SupportContractUpdate:
    changed = {d.field: d.incoming for d in item.diffs}
    return SupportContractUpdate.model_validate({**changed, "expectedVersion": version})


def reconcile_many(
    existing: dict[str, SupportContractRead], records: list[dict[str, object]]
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
