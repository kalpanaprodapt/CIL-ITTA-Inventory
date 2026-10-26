"""Normalisation of inbound Reservation records coming from external systems."""
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
from itta_api.domains.capacity.reservation.schemas import *  # noqa: F401,F403


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

def normalize_resource_ref(value: object) -> str | None:
    """Normalize Resource Ref: trim, collapse whitespace."""
    if value is None:
        return None
    text = collapse_whitespace(str(value))
    return text or None

def normalize_reserved_for(value: object) -> str | None:
    """Normalize Reserved For: trim, collapse whitespace."""
    if value is None:
        return None
    text = collapse_whitespace(str(value))
    return text or None

def normalize_quantity(value: object) -> Decimal | None:
    """Normalize Quantity to four decimal places."""
    return to_decimal(value, places=4)

def normalize_start_date(value: object) -> date | None:
    """Normalize Start Date from ISO or day-first strings."""
    return to_date(value)

def normalize_end_date(value: object) -> date | None:
    """Normalize End Date from ISO or day-first strings."""
    return to_date(value)

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

def normalize_lifecycle_stage(value: object) -> ReservationLifecycleStage | None:
    """Normalize Lifecycle Stage to its enum member (case and separator insensitive)."""
    return to_enum(ReservationLifecycleStage, value)

NORMALIZERS = {
    "code": normalize_code,
    "name": normalize_name,
    "description": normalize_description,
    "status": normalize_status,
    "externalRef": normalize_external_ref,
    "resourceRef": normalize_resource_ref,
    "reservedFor": normalize_reserved_for,
    "quantity": normalize_quantity,
    "startDate": normalize_start_date,
    "endDate": normalize_end_date,
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
