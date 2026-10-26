"""Normalisation of inbound Change Request records coming from external systems."""
from __future__ import annotations

from datetime import date, datetime
from decimal import Decimal
from uuid import UUID

from itta_core.models import LifecycleStatus
from itta_core.normalize import (
    collapse_whitespace,
    to_bool,
    to_date,
    to_datetime,
    to_decimal,
    to_enum,
    to_int,
    to_uuid,
)
from itta_api.domains.change.change_request.schemas import *  # noqa: F401,F403


def normalize_code(value: object) -> str | None:
    """Normalize Code: trim, collapse whitespace, upper-case."""
    if value is None:
        return None
    text = collapse_whitespace(str(value)).upper() if str(value).strip() else None
    return text or None

def normalize_name(value: object) -> str | None:
    """Normalize Name: trim, collapse whitespace."""
    if value is None:
        return None
    text = collapse_whitespace(str(value))
    return text or None

def normalize_description(value: object) -> str | None:
    """Normalize Description: trim, collapse whitespace."""
    if value is None:
        return None
    text = collapse_whitespace(str(value))
    return text or None

def normalize_status(value: object) -> LifecycleStatus | None:
    """Normalize Status to its enum member (case and separator insensitive)."""
    return to_enum(LifecycleStatus, value)

def normalize_external_ref(value: object) -> str | None:
    """Normalize External Ref: trim, collapse whitespace."""
    if value is None:
        return None
    text = collapse_whitespace(str(value))
    return text or None

def normalize_change_number(value: object) -> str | None:
    """Normalize Change Number: trim, collapse whitespace."""
    if value is None:
        return None
    text = collapse_whitespace(str(value))
    return text or None

def normalize_change_type(value: object) -> ChangeRequestChangeType | None:
    """Normalize Change Type to its enum member (case and separator insensitive)."""
    return to_enum(ChangeRequestChangeType, value)

def normalize_risk_level(value: object) -> ChangeRequestRiskLevel | None:
    """Normalize Risk Level to its enum member (case and separator insensitive)."""
    return to_enum(ChangeRequestRiskLevel, value)

def normalize_planned_start(value: object) -> datetime | None:
    """Normalize Planned Start to a timezone aware UTC datetime."""
    return to_datetime(value)

def normalize_planned_end(value: object) -> datetime | None:
    """Normalize Planned End to a timezone aware UTC datetime."""
    return to_datetime(value)

def normalize_notes(value: object) -> str | None:
    """Normalize Notes: trim, collapse whitespace."""
    if value is None:
        return None
    text = collapse_whitespace(str(value))
    return text or None

def normalize_effective_from(value: object) -> date | None:
    """Normalize Effective From from ISO or day-first strings."""
    return to_date(value)

def normalize_effective_to(value: object) -> date | None:
    """Normalize Effective To from ISO or day-first strings."""
    return to_date(value)

def normalize_labels(value: object) -> str | None:
    """Normalize Labels: trim, collapse whitespace."""
    if value is None:
        return None
    text = collapse_whitespace(str(value))
    return text or None

def normalize_lifecycle_stage(value: object) -> ChangeRequestLifecycleStage | None:
    """Normalize Lifecycle Stage to its enum member (case and separator insensitive)."""
    return to_enum(ChangeRequestLifecycleStage, value)

NORMALIZERS = {
    "code": normalize_code,
    "name": normalize_name,
    "description": normalize_description,
    "status": normalize_status,
    "externalRef": normalize_external_ref,
    "changeNumber": normalize_change_number,
    "changeType": normalize_change_type,
    "riskLevel": normalize_risk_level,
    "plannedStart": normalize_planned_start,
    "plannedEnd": normalize_planned_end,
    "notes": normalize_notes,
    "effectiveFrom": normalize_effective_from,
    "effectiveTo": normalize_effective_to,
    "labels": normalize_labels,
    "lifecycleStage": normalize_lifecycle_stage,
}


def normalize_record(raw: dict[str, object]) -> dict[str, object]:
    """Normalise every known attribute of a raw record; unknown keys are dropped."""
    from itta_core.normalize import camelize_keys

    keyed = camelize_keys(raw)
    out: dict[str, object] = {}
    for name, fn in NORMALIZERS.items():
        if name in keyed:
            out[name] = fn(keyed[name])
    return out
