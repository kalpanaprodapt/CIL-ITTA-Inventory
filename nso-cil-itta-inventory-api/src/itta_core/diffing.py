"""Field-level diffing and reconciliation planning."""
from __future__ import annotations

from decimal import Decimal
from enum import Enum
from typing import Any
from uuid import UUID

from pydantic import Field

from itta_core.models import ApiModel


class Action(str, Enum):
    CREATE = "create"
    UPDATE = "update"
    NOOP = "noop"
    REJECT = "reject"


class FieldDiff(ApiModel):
    field: str
    current: Any = None
    incoming: Any = None


class ReconciliationItem(ApiModel):
    key: str | None = None
    action: Action
    reason: str | None = None
    target_id: UUID | None = None
    result_id: UUID | None = None
    diffs: list[FieldDiff] = Field(default_factory=list)
    payload: dict[str, Any] | None = None


def _blank(value: Any) -> bool:
    return value is None or (isinstance(value, str) and not value.strip())


def compare_value(field: str, current: Any, incoming: Any) -> list[FieldDiff]:
    """Return a diff when the incoming value is present and differs from the stored one."""
    if incoming is None:
        return []
    if isinstance(current, Enum):
        current = current.value
    if isinstance(incoming, Enum):
        incoming = incoming.value
    if _blank(current) and _blank(incoming):
        return []
    if current == incoming:
        return []
    return [FieldDiff(field=field, current=current, incoming=incoming)]


def compare_decimal(field: str, current: Decimal | None, incoming: Any, tolerance: Decimal) -> list[FieldDiff]:
    if incoming is None:
        return []
    if current is not None and abs(Decimal(current) - Decimal(incoming)) <= tolerance:
        return []
    return [FieldDiff(field=field, current=current, incoming=incoming)]


def plan_for(key: str, target_id: UUID, diffs: list[FieldDiff], incoming: dict[str, Any]) -> ReconciliationItem:
    if not diffs:
        return ReconciliationItem(key=key, action=Action.NOOP, target_id=target_id)
    return ReconciliationItem(key=key, action=Action.UPDATE, target_id=target_id, diffs=diffs, payload=incoming)
